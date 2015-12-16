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

package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import com.spectralogic.ds3autogen.java.models.Request;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

public class RequestConverter {

    private final Ds3Request ds3Request;
    private final String packageName;
    private final ImmutableList<Arguments> requiredConstructorArguments;
    private final ImmutableList<Arguments> optionalArguments;
    private final ImmutableList<String> imports;

    private RequestConverter(
            final Ds3Request ds3Request,
            final String packageName) {
        this.ds3Request = ds3Request;
        this.packageName = packageName;
        this.requiredConstructorArguments = getRequiredArgs(ds3Request);
        this.optionalArguments = getOptionalArgs(ds3Request);
        this.imports = getImports(ds3Request);
    }

    private Request convert() {
        final String[] classParts = ds3Request.getName().split("\\.");

        return new Request(packageName,
                classParts[classParts.length - 1],
                ds3Request.getHttpVerb(),
                requestPath(ds3Request),
                ds3Request.getOperation(),
                ds3Request.getAction(),
                requiredConstructorArguments,
                optionalArguments,
                imports);
    }

    public static Request toRequest(
            final Ds3Request ds3Request,
            final String packageName) {
        final RequestConverter converter = new RequestConverter(ds3Request, packageName);

        return converter.convert();
    }

    /**
     * Creates the Java code for the Java SDK request path
     * @param ds3Request A request
     * @return The Java code for the request path
     */
    protected static String requestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();

        if (ds3Request.getClassification() == Classification.amazons3) {
            builder.append(getAmazonS3RequestPath(ds3Request));
        } else if (ds3Request.getClassification() == Classification.spectrads3) {
            builder.append(getSpectraDs3RequestPath(ds3Request));
        }

        return builder.toString();
    }

    /**
     * Creates the Java request path code for an AmazonS3 request
     * @param ds3Request A request
     * @return The Java request path code for an AmazonS3 request
     */
    protected static String getAmazonS3RequestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        if (ds3Request.getClassification() != Classification.amazons3) {
            return builder.toString();
        }
        builder.append("\"/\"");
        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
            builder.append(" + this.bucketName");
        }
        if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
            builder.append(" + \"/\" + this.objectName");
        }
        return builder.toString();
    }

    /**
     * Creates the Java request path code for a SpectraS3 request
     * @param ds3Request A request
     * @return The Java request path code for an SpectraS3 request
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
            builder.append(" + this.getNotificationId().toString()");
        } else if (hasBucketNameInPath(ds3Request)) {
            builder.append(" + this.bucketName");
        } else if (isResourceAnArg(ds3Request.getResource(), ds3Request.getResourceType())) {
            final Arguments resourceArg = getArgFromResource(ds3Request.getResource());
            builder.append(" + ").append(JavaHelper.argToString(resourceArg));
        }
        return builder.toString();
    }


    private static ImmutableList<Arguments> getRequiredArgs(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();

        requiredArgs.addAll(RequestConverterUtil.getRequiredArgsFromRequestHeader(ds3Request));

        requiredArgs.addAll(getArgsFromParamList(ds3Request.getRequiredQueryParams()));
        return requiredArgs.build();
    }

    private static ImmutableList<Arguments> getOptionalArgs(
            final Ds3Request ds3Request) {
        if (ds3Request.getOptionalQueryParams() == null) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> optionalArgs = ImmutableList.builder();
        optionalArgs.addAll(getArgsFromParamList(ds3Request.getOptionalQueryParams()));
        return optionalArgs.build();
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

    private static ImmutableList<String> getImports(final Ds3Request ds3Request) {
        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();

        importsBuilder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        importsBuilder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));

        if (isResourceAnArg(ds3Request.getResource(), ds3Request.getResourceType())
                && RequestConverterUtil.isResourceId(ds3Request.getResource())) {
            importsBuilder.add("java.util.UUID");
        }

        return importsBuilder.build().asList();
    }

    private static ImmutableSet<String> getImportsFromParamList(final ImmutableList<Ds3Param> paramList) {
        if (paramList == null) {
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();
        for (final Ds3Param ds3Param : paramList) {
            if (!ds3Param.getName().equals("Operation")
                    && ds3Param.getType().contains(".")) {
                importsBuilder.add(ConvertType.convertType(ds3Param.getType()));
            }
        }
        return importsBuilder.build();
    }
}
