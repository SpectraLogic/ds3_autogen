/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.net.NetHelper;
import com.spectralogic.ds3autogen.net.model.common.NullableVariable;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
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

        builder.append("\"/_rest_/").append(ds3Request.getResource().toString().toLowerCase()).append("/\"");
        if (isNotificationRequest(ds3Request)
                && (getNotificationType(ds3Request) == NotificationType.DELETE
                || getNotificationType(ds3Request) == NotificationType.GET)) {
            builder.append(" + NotificationId.ToString()");
        } else if (hasBucketNameInPath(ds3Request)) {
            builder.append(" + BucketName");
        } else if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())) {
            final Arguments resourceArg = getArgFromResource(ds3Request.getResource());
            builder.append(" + ").append(NetHelper.argToString(resourceArg));
        }
        return builder.toString();
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
        return requiredArgs.build();
    }

    /**
     * Converts a list of Ds3Params into a list of Arguments, excluding the Operations param
     */
    public static ImmutableList<Arguments> getArgsFromParamList(final ImmutableList<Ds3Param> paramList) {
        if(isEmpty(paramList)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Arguments> argsBuilder = ImmutableList.builder();
        for (final Ds3Param ds3Param : paramList) {
            if (!ds3Param.getName().equals("Operation")) {
                argsBuilder.add(toArgument(ds3Param));
            }
        }
        return argsBuilder.build();
    }

    /**
     * Converts a Ds3Param into an argument
     */
    public static Arguments toArgument(final Ds3Param ds3Param) {
        final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
        return new Arguments(paramType, ds3Param.getName());
    }

    //TODO unit test
    /**
     * Creates a NullableVariable from the provided parameters
     */
    public static NullableVariable createNullableVariable(
            final String name,
            final String type,
            final String componentType,
            final ImmutableMap<String, Ds3Type> typeMap) {
        /*
        if (hasContent(componentType)) {
            final String netType = "IEnumerable<" + stripPath(componentType) + ">";
            return new NullableVariable(name, "IEnumerable<")
        }

        return new NullableVariable(name, "", false);
        */
        //TODO
        return null;
    }
}
