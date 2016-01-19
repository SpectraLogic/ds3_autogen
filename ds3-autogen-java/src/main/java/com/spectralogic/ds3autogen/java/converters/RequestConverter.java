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

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

/**
 * Converts a Ds3Request into a Request model used for generating the
 * request handler Java SDK code.
 */
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
        this.requiredConstructorArguments = toRequiredArgumentsList(ds3Request);
        this.optionalArguments = toOptionalArgumentsList(ds3Request.getOptionalQueryParams());
        this.imports = getAllImports(ds3Request);
    }

    /**
     * Converts data sotred within this RequestConvert into a Request model
     * @return A Request model
     */
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

    /**
     * Converts a Ds3Request and a package name into a Request model
     * @param ds3Request A Ds3Request
     * @param packageName The name of the Java package for the generated request and response
     * @return A Request model containing the information of the Ds3Request and package name
     */
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

        builder.append("\"/_rest_/").append(ds3Request.getResource().toString().toLowerCase());
        if (isNotificationRequest(ds3Request)
                && (getNotificationType(ds3Request) == NotificationType.DELETE
                    || getNotificationType(ds3Request) == NotificationType.GET)) {
            builder.append("/\"").append(" + this.getNotificationId().toString()");
        } else if (hasBucketNameInPath(ds3Request)) {
            builder.append("/\"").append(" + this.bucketName");
        } else if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())) {
            final Arguments resourceArg = getArgFromResource(ds3Request.getResource());
            builder.append("/\"").append(" + ").append(JavaHelper.argToString(resourceArg));
        } else {
            builder.append("\"");
        }
        return builder.toString();
    }

    /**
     * Gets the list of required Arguments from a Ds3Request
     * @param ds3Request A Ds3Request
     * @return A list of required Arguments
     */
    private static ImmutableList<Arguments> toRequiredArgumentsList(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();
        requiredArgs.addAll(getRequiredArgsFromRequestHeader(ds3Request));
        requiredArgs.addAll(toArgumentsList(ds3Request.getRequiredQueryParams()));
        return requiredArgs.build();
    }

    /**
     * Converts a list of optional Ds3Params into a list of optional Arguments
     * @param optionalDs3Params A list of optional Ds3Params
     * @return A list of optional Arguments
     */
    private static ImmutableList<Arguments> toOptionalArgumentsList(
            final ImmutableList<Ds3Param> optionalDs3Params) {
        if (isEmpty(optionalDs3Params)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> optionalArgs = ImmutableList.builder();
        optionalArgs.addAll(toArgumentsList(optionalDs3Params));
        return optionalArgs.build();
    }

    /**
     * Converts a list of Ds3Params into a list of Arguments
     * @param ds3Params A list of Ds3Params
     * @return A list of Arguments
     */
    private static ImmutableList<Arguments> toArgumentsList(
            final ImmutableList<Ds3Param> ds3Params) {
        if(isEmpty(ds3Params)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> argsBuilder = ImmutableList.builder();
        for (final Ds3Param ds3Param : ds3Params) {
            if (!ds3Param.getName().equals("Operation")) {
                final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
                argsBuilder.add(new Arguments(paramType, ds3Param.getName()));
            }
        }
        return argsBuilder.build();
    }

    /**
     * Gets all the required imports that the Request will need in order to properly
     * generate the Java request code
     * @param ds3Request A Ds3Request
     * @return The list of all imports that the Request requires for generating the
     *         Java request code
     */
    private static ImmutableList<String> getAllImports(final Ds3Request ds3Request) {
        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();

        importsBuilder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        importsBuilder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));

        if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())
                && RequestConverterUtil.isResourceId(ds3Request.getResource())) {
            importsBuilder.add("java.util.UUID");
        }

        return importsBuilder.build().asList();
    }

    /**
     * Gets the required imports that are needed to ensure that all generated models
     * within the this Ds3Param list are included in the request generated Java code
     * @param ds3Params A list of Ds3Params
     * @return The list of imports necessary for including all generated models within
     *         the Ds3Params list
     */
    private static ImmutableSet<String> getImportsFromParamList(final ImmutableList<Ds3Param> ds3Params) {
        if (isEmpty(ds3Params)) {
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();
        for (final Ds3Param ds3Param : ds3Params) {
            if (!ds3Param.getName().equals("Operation")
                    && ds3Param.getType().contains(".")) {
                importsBuilder.add(ConvertType.toModelName(ds3Param.getType()));
            }
        }
        return importsBuilder.build();
    }
}
