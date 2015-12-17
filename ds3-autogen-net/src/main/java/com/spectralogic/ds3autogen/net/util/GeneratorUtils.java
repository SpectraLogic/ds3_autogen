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

package com.spectralogic.ds3autogen.net.util;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.net.NetHelper;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getArgFromResource;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getNotificationType;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.isResourceAnArg;

public final class GeneratorUtils {

    private GeneratorUtils() {
        //pass
    }

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
        } else if (isResourceAnArg(ds3Request.getResource(), ds3Request.getResourceType())) {
            final Arguments resourceArg = getArgFromResource(ds3Request.getResource());
            builder.append(" + ").append(NetHelper.argToString(resourceArg));
        }
        return builder.toString();
    }

    public static ImmutableList<Arguments> getRequiredArgs(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();

        requiredArgs.addAll(RequestConverterUtil.getRequiredArgsFromRequestHeader(ds3Request));

        requiredArgs.addAll(getArgsFromParamList(ds3Request.getRequiredQueryParams()));
        return requiredArgs.build();
    }

    private static ImmutableList<Arguments> getArgsFromParamList(final ImmutableList<Ds3Param> paramList) {
        if(paramList == null) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> argsBuilder = ImmutableList.builder();
        for (final Ds3Param ds3Param : paramList) {
            if (!ds3Param.getName().equals("Operation")) {
                final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
                argsBuilder.add(new Arguments(paramType, ds3Param.getName()));
            }
        }
        return argsBuilder.build();
    }


    public static String toRequestName(final Ds3Request ds3Request) {
        final String name = ds3Request.getName();
        final int lastIndex = name.lastIndexOf(".");

        return name.substring(lastIndex + 1);
    }

    public static String toResponseName(final Ds3Request ds3Request) {
        return toRequestName(ds3Request).replace("Request", "Response");
    }
}
