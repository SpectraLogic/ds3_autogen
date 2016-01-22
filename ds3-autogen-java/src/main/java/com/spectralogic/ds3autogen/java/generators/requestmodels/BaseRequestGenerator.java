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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.java.converters.ConvertType;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import com.spectralogic.ds3autogen.java.models.Constants;
import com.spectralogic.ds3autogen.java.models.Request;
import com.spectralogic.ds3autogen.java.models.Variable;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
import static com.spectralogic.ds3autogen.utils.Helper.removeVoidArguments;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

public class BaseRequestGenerator implements RequestModelGenerator<Request> {

    private final static String ABSTRACT_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.AbstractRequest";

    @Override
    public Request generate(final Ds3Request ds3Request, final String packageName) {
        final String requestName = getRequestName(ds3Request.getName());
        final String requestPath = getRequestPath(ds3Request);
        final ImmutableList<Arguments> requiredArguments = toRequiredArgumentsList(ds3Request);
        final ImmutableList<Arguments> optionalArguments = toOptionalArgumentsList(ds3Request.getOptionalQueryParams());
        final ImmutableList<Arguments> constructorArguments = toConstructorArgumentsList(ds3Request);
        final ImmutableList<Variable> classVariableArguments = toClassVariableArguments(ds3Request);
        final ImmutableList<String> imports = getAllImports(ds3Request, packageName);

        return new Request(
                packageName,
                requestName,
                ds3Request.getHttpVerb(),
                requestPath,
                ds3Request.getOperation(),
                ds3Request.getAction(),
                requiredArguments,
                optionalArguments,
                constructorArguments,
                classVariableArguments,
                imports);
    }

    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions. This consists of all constructor arguments and optional
     * arguments being converted into variables.
     */
    protected ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();
        for (final Arguments arg : toConstructorArgumentsList(ds3Request)) {
            builder.add(new Variable(arg.getName(), arg.getType(), true));
        }
        for (final Arguments arg : toOptionalArgumentsList(ds3Request.getOptionalQueryParams())) {
            builder.add(new Variable(arg.getName(), arg.getType(), false));
        }
        return builder.build();
    }

    /**
     * Gets all the required imports that the Request will need in order to properly
     * generate the Java request code
     * @param ds3Request A Ds3Request
     * @return The list of all imports that the Request requires for generating the
     *         Java request code
     */
    protected ImmutableList<String> getAllImports(
            final Ds3Request ds3Request,
            final String packageName) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        if (isSpectraDs3(packageName)) {
            builder.add(getParentImport(ds3Request));
        }

        builder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));

        if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())
                && RequestConverterUtil.isResourceId(ds3Request.getResource())) {
            builder.add("java.util.UUID");
        }

        return builder.build().asList();
    }

    /**
     * Returns the import for the parent class for standard requests, which
     * is AbstractRequest
     */
    protected String getParentImport(final Ds3Request ds3Request) {
        return ABSTRACT_REQUEST_IMPORT;
    }

    /**
     * Gets the required imports that are needed to ensure that all generated models
     * within the this Ds3Param list are included in the request generated Java code
     * @param ds3Params A list of Ds3Params
     * @return The list of imports necessary for including all generated models within
     *         the Ds3Params list
     */
    protected static ImmutableSet<String> getImportsFromParamList(final ImmutableList<Ds3Param> ds3Params) {
        if (isEmpty(ds3Params)) {
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();
        for (final Ds3Param ds3Param : ds3Params) {
            if (!ds3Param.getName().equals("Operation")
                    && ds3Param.getType().contains(".")
                    && !ds3Param.getType().equals("java.lang.String")) {
                importsBuilder.add(ConvertType.toModelName(ds3Param.getType()));
            }
        }
        return importsBuilder.build();
    }

    /**
     * Gets the list of Arguments needed to create the request constructor. This
     * includes all non-void required parameters, and arguments described within
     * the request header.
     */
    protected ImmutableList<Arguments> toConstructorArgumentsList(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getRequiredArgsFromRequestHeader(ds3Request));
        builder.addAll(removeVoidArguments(toArgumentsList(ds3Request.getRequiredQueryParams())));
        return builder.build();
    }

    /**
     * Gets the list of required Arguments from a Ds3Request
     * @param ds3Request A Ds3Request
     * @return A list of required Arguments
     */
    protected ImmutableList<Arguments> toRequiredArgumentsList(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();
        requiredArgs.addAll(toArgumentsList(ds3Request.getRequiredQueryParams()));
        return requiredArgs.build();
    }

    /**
     * Converts a list of optional Ds3Params into a list of optional Arguments
     * @param optionalDs3Params A list of optional Ds3Params
     * @return A list of optional Arguments
     */
    protected static ImmutableList<Arguments> toOptionalArgumentsList(
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
    protected static ImmutableList<Arguments> toArgumentsList(
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
     * Creates the Java code for the Java SDK request path
     * @param ds3Request A request
     * @return The Java code for the request path
     */
    protected static String getRequestPath(final Ds3Request ds3Request) {
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
     * Retrieves the request name without the request path
     */
    protected static String getRequestName(final String requestName) {
        if (isEmpty(requestName)) {
            return "";
        }
        final String[] classParts = requestName.split("\\.");
        return classParts[classParts.length - 1];
    }

    /**
     * Determines of package is SpectraDs3. This is used to determine if request/response handlers
     * need to include an import to parent class.
     * @return True if package is part of SpectraDs3, else false
     */
    public static boolean isSpectraDs3(final String packageName) {
        return packageName.contains(Constants.SPECTRA_DS3_PACKAGE);
    }
}
