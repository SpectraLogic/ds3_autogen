/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.ds3autogen.net.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.Requirement;
import com.spectralogic.ds3autogen.net.NetHelper;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
import static com.spectralogic.ds3autogen.utils.Helper.capFirst;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

/**
 * Provides a series of static utility functions that are used within
 * the Net package generators to convert the data from Ds3Requests and
 * Ds3Types into models that are then used within the templates
 */
public final class GeneratorUtils {

    private GeneratorUtils() {
        //pass
    }

    /**
     * Gets the name of the type/model parser
     */
    public static String toModelParserName(final String modelName) {
        if (isEmpty(modelName)) {
            return "";
        }
        return "Parse" + removePath(modelName);
    }

    /**
     * Creates the C# request path code for a Ds3 request
     */
    public static String toRequestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();

        if (ds3Request.getClassification() == Classification.amazons3) {
            builder.append(getAmazonS3RequestPath(ds3Request));
        } else if (ds3Request.getClassification() == Classification.spectrads3) {
            builder.append(getSpectraDs3RequestPath(ds3Request));
        }

        return builder.toString();
    }

    /**
     * Creates the C# request path code for an AmazonS3 request
     * @param ds3Request A request
     * @return The .net request path code for an AmazonS3 request
     */
    protected static String getAmazonS3RequestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        if (ds3Request.getClassification() != Classification.amazons3) {
            return builder.toString();
        }
        builder.append("\"/\"");
        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
            builder.append(" + BucketName");
        }
        if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
            builder.append(" + \"/\" + ObjectName");
        }
        return builder.toString();
    }

    /**
     * Creates the .net request path code for a SpectraS3 request
     * @param ds3Request A request
     * @return The .net request path code for an SpectraS3 request
     */
    protected static String getSpectraDs3RequestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        if (ds3Request.getClassification() != Classification.spectrads3) {
            return builder.toString();
        }
        if (ds3Request.getResource() == null) {
            return builder.append("\"/_rest_/\"").toString();
        }

        builder.append("\"/_rest_/").append(ds3Request.getResource().toString().toLowerCase());
        if (isNotificationRequest(ds3Request)
                && ds3Request.getIncludeInPath()
                && (getNotificationType(ds3Request) == NotificationType.DELETE
                || getNotificationType(ds3Request) == NotificationType.GET)) {
            builder.append("/\"").append(" + NotificationId.ToString()");
        } else if (hasBucketNameInPath(ds3Request)) {
            builder.append("/\"").append(" + BucketName");
        } else if (isResourceAnArg(ds3Request.getResource(), ds3Request.getIncludeInPath())) {
            final Arguments resourceArg = getArgFromResource(ds3Request.getResource());
            builder.append("/\"").append(" + ").append(pathArgToString(resourceArg));
        } else {
            builder.append("\"");
        }
        return builder.toString();
    }

    /**
     * Creates the Net code for converting an argument into a string for use in creating the
     * request handler path. If the argument is of type UUID (which translates to Guid), it is
     * treated as a string since all Guids are stored as strings inside the Net request handlers.
     */
    private static String pathArgToString(final Arguments resourceArg) {
        if (resourceArg.getType().equals("UUID")) {
            return capFirst(resourceArg.getName());
        }
        return capFirst(NetHelper.argToString(resourceArg));
    }

    /**
     * Retrieves a list of arguments from the Ds3Request, including converting the required
     * parameters to arguments, and retrieving the arguments from the request header info
     */
    public static ImmutableList<Arguments> getRequiredArgs(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();
        requiredArgs.addAll(RequestConverterUtil.getRequiredArgsFromRequestHeader(ds3Request));
        requiredArgs.addAll(getArgsFromParamList(ds3Request.getRequiredQueryParams()));
        if (ds3Request.getIncludeInPath() && isResourceNotification(ds3Request.getResource())) {
            requiredArgs.add(new Arguments("Guid", "NotificationId"));
        }
        return requiredArgs.build();
    }

    /**
     * Gets the .net type that represents the described type
     * @param type The type of the element without path
     * @param componentType The component type of the element without path, if one exists
     */
    public static String getNetType(final String type, final String componentType) {
        if (hasContent(componentType)) {
            return "IEnumerable<" + NetHelper.toNetType(componentType) + ">";
        }
        return NetHelper.toNetType(type);
    }

    /**
     * Determines if a Ds3Request should generate a response handler and parser despite
     * not having a response payload
     */
    public static boolean hasResponseHandlerAndParser(final Ds3Request ds3Request) {
        return isGetObjectAmazonS3Request(ds3Request)
                || isHeadBucketRequest(ds3Request)
                || isHeadObjectRequest(ds3Request);
    }
}
