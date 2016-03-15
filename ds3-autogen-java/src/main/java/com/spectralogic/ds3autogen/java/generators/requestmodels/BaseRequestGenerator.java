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
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.models.Variable;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.containsType;
import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.modifyType;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isNotificationRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestUtils.hasBucketNameInPath;
import static com.spectralogic.ds3autogen.utils.Helper.removeVoidArguments;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

public class BaseRequestGenerator implements RequestModelGenerator<Request>, RequestGeneratorUtils {

    private final static String ABSTRACT_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.AbstractRequest";

    @Override
    public Request generate(final Ds3Request ds3Request, final String packageName) {
        final Ds3Request updatedRequest = updateDs3RequestParamTypes(ds3Request);
        final String requestName = NormalizingContractNamesUtil.removePath(updatedRequest.getName());
        final String requestPath = getRequestPath(updatedRequest);

        final ImmutableList<Arguments> optionalArguments =
                splitAllUuidOptionalArguments(toOptionalArgumentsList(updatedRequest.getOptionalQueryParams()));

        final ImmutableList<RequestConstructor> constructors =
                splitAllUuidConstructors(toConstructorList(updatedRequest));

        final ImmutableList<Variable> classVariableArguments =
                convertAllUuidClassVariables(toClassVariableArguments(updatedRequest));

        final ImmutableList<String> imports = getAllImports(updatedRequest, packageName);

        return new Request(
                packageName,
                requestName,
                updatedRequest.getHttpVerb(),
                requestPath,
                updatedRequest.getOperation(),
                updatedRequest.getAction(),
                optionalArguments,
                constructors,
                classVariableArguments,
                imports);
    }

    /**
     * Updates the Ds3Request's parameter: BucketId is changed from type UUID
     * to String if said parameter exists within the request
     */
    protected static Ds3Request updateDs3RequestParamTypes(final Ds3Request ds3Request) {
        return new Ds3Request(
                ds3Request.getName(),
                ds3Request.getHttpVerb(),
                ds3Request.getClassification(),
                ds3Request.getBucketRequirement(),
                ds3Request.getObjectRequirement(),
                ds3Request.getAction(),
                ds3Request.getResource(),
                ds3Request.getResourceType(),
                ds3Request.getOperation(),
                ds3Request.includeIdInPath(),
                ds3Request.getDs3ResponseCodes(),
                updateDs3ParamListTypes(
                        ds3Request.getOptionalQueryParams()),
                updateDs3ParamListTypes(
                        ds3Request.getRequiredQueryParams()));
    }

    /**
     * Updates the Ds3Param list where all instances of BucketId have their
     * type updated to String
     */
    protected static ImmutableList<Ds3Param> updateDs3ParamListTypes(final ImmutableList<Ds3Param> params) {
        if (isEmpty(params)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        for (final Ds3Param param : params) {
            if (param.getName().equalsIgnoreCase("BucketId")) {
                builder.add(new Ds3Param("BucketId", "String"));
            } else {
                builder.add(param);
            }
        }
        return builder.build();
    }

    /**
     * Gets the list of constructor models from a Ds3Request. For the base request, the
     * constructor list will be of size one.
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> constructorArgs = toConstructorArgumentsList(ds3Request);
        final RequestConstructor constructor = new RequestConstructor(
                constructorArgs,
                constructorArgs,
                toQueryParamsList(ds3Request));

        return ImmutableList.of(constructor);
    }
    
    /**
     * Takes a list of constructors and splits all constructors containing at least one UUID into two
     * constructors: an original, and a constructor where all UUIDs are replaced with String type.
     */
    protected static ImmutableList<RequestConstructor> splitAllUuidConstructors(
            final ImmutableList<RequestConstructor> constructors) {
        if (isEmpty(constructors)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<RequestConstructor> builder = ImmutableList.builder();
        for (final RequestConstructor constructor : constructors) {
            builder.addAll(splitUuidConstructor(constructor));
        }
        return builder.build();
    }

    /**
     * Splits a constructor in two if the constructor contains a parameter of type UUID. The first
     * constructor is unchanged, while the second constructor has all UUID parameters converted into
     * strings. If the constructor does not have a UUID parameter, then the original constructor is
     * returned.
     */
    protected static ImmutableList<RequestConstructor> splitUuidConstructor(final RequestConstructor constructor) {
        final ImmutableList.Builder<RequestConstructor> builder = ImmutableList.builder();
        builder.add(constructor);
        if (!containsType(constructor.getParameters(), "UUID")) {
            return builder.build();
        }
        builder.add(convertUuidConstructorToStringConstructor(constructor));
        return builder.build();
    }

    /**
     * Converts all UUID types into Strings within a given request constructor
     */
    protected static RequestConstructor convertUuidConstructorToStringConstructor(
            final RequestConstructor constructor) {
        final String curType = "UUID";
        final String newType = "String";
        return new RequestConstructor(
                constructor.isDeprecated(),
                constructor.getAdditionalLines(),
                modifyType(constructor.getParameters(), curType, newType),
                modifyType(constructor.getAssignments(), curType, newType),
                modifyType(constructor.getQueryParams(), curType, newType));
    }

    /**
     * Creates the list of arguments that are added to the query params within
     * the constructors
     */
    @Override
    public ImmutableList<Arguments> toQueryParamsList(final Ds3Request ds3Request) {
        return toRequiredArgumentsList(ds3Request);
    }

    /**
     * Converts all UUID class variable arguments from type UUID to type String.
     * All other variables are left un-modified.
     */
    public static ImmutableList<Variable> convertAllUuidClassVariables(final ImmutableList<Variable> variables) {
        if (isEmpty(variables)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();
        for (final Variable var : variables) {
            builder.add(convertUuidClassVariable(var));
        }
        return builder.build();
    }

    /**
     * Converts a UUID class variable argument's type to String. If the variable
     * is not of type UUID, then the variable is not modified.
     */
    public static Variable convertUuidClassVariable(final Variable var) {
        if (var.getType().equals("UUID")) {
            return new Variable(var.getName(), "String", var.isRequired());
        }
        return var;
    }

    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions. This consists of all constructor arguments and optional
     * arguments being converted into variables.
     */
    @Override
    public ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
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
    @Override
    public ImmutableList<String> getAllImports(
            final Ds3Request ds3Request,
            final String packageName) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        if (isSpectraDs3(packageName)) {
            builder.add(getParentImport(ds3Request));
        }

        builder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));

        if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())) {
            if (RequestConverterUtil.isResourceId(ds3Request.getResource())) {
                builder.add("java.util.UUID");
            }
            builder.add("com.google.common.net.UrlEscapers");
        }

        return builder.build().asList();
    }

    /**
     * Returns the import for the parent class for standard requests, which
     * is AbstractRequest
     */
    @Override
    public String getParentImport(final Ds3Request ds3Request) {
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
            if (ds3Param.getType().endsWith("String")) {
                importsBuilder.add("com.google.common.net.UrlEscapers");
            }
        }
        return importsBuilder.build();
    }

    /**
     * Gets the list of Arguments needed to create the request constructor. This
     * includes all non-void required parameters, and arguments described within
     * the request header.
     */
    @Override
    public ImmutableList<Arguments> toConstructorArgumentsList(
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
    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();
        requiredArgs.addAll(toArgumentsList(ds3Request.getRequiredQueryParams()));
        return requiredArgs.build();
    }

    /**
     * Splits all UUID arguments into two arguments (UUID and String) for support of both
     * UUID and String with-constructors. All non-UUID arguments are left unmodified.
     */
    protected static ImmutableList<Arguments> splitAllUuidOptionalArguments(final ImmutableList<Arguments> arguments) {
        if (isEmpty(arguments)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        for (final Arguments arg : arguments) {
            builder.addAll(splitUuidOptionalArgument(arg));
        }
        return builder.build();
    }

    /**
     * Splits UUID arguments into two arguments, one UUID and one String. This is done
     * so that both a UUID and String with-constructor will be crated for this parameter
     */
    protected static ImmutableList<Arguments> splitUuidOptionalArgument(final Arguments arg) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.add(arg);
        if (arg.getType().equals("UUID")) {
            builder.add(new Arguments("String", arg.getName()));
        }
        return builder.build();
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
        if (ds3Request.getClassification() == Classification.amazons3) {
            return getAmazonS3RequestPath(ds3Request);
        }
        //Spectra internal requests have the same path construction as SpectraDs3
        return getSpectraDs3RequestPath(ds3Request);
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
        if (ds3Request.getClassification() != Classification.spectrads3
                && ds3Request.getClassification() != Classification.spectrainternal) {
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
            builder.append("/\"").append(" + ").append(resourceArgToString(resourceArg));
        } else {
            builder.append("\"");
        }
        return builder.toString();
    }

    /**
     * Gets the java code for turning a resource argument into a string. This is used
     * to prevent previously UUID parameters (which are now stored as Strings) from
     * being incorrectly converted.
     */
    protected static String resourceArgToString(final Arguments arg) {
        if (arg.getType().equals("UUID")) {
            return Helper.uncapFirst(arg.getName());
        }
        return JavaHelper.argToString(arg);
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
