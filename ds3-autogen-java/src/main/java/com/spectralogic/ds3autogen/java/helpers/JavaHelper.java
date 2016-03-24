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

package com.spectralogic.ds3autogen.java.helpers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.models.*;
import com.spectralogic.ds3autogen.utils.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.BaseRequestGenerator.isSpectraDs3;
import static com.spectralogic.ds3autogen.java.generators.requestmodels.BulkRequestGenerator.isBulkRequestArg;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.*;

/**
 * Series of static functions that are used within the Java module template files
 * to help generate the Java SDK code.
 */
public final class JavaHelper {

    private final static JavaHelper javaHelper = new JavaHelper();

    private JavaHelper() {}

    public static JavaHelper getInstance() {
        return javaHelper;
    }

    /**
     * Creates the Java code associated with private class variables for bulk requests. If
     * the parameter is required, it will be made private and final. If the parameter is
     * optional, it will only be made private (not final). If the parameter is inherited from
     * the bulk class, then a class variable isn't needed, and an empty string is returned.
     */
    public static String createBulkVariable(final Arguments arg, final boolean isRequired) {
        if (isBulkRequestArg(arg.getName())) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append("private ");
        if (isRequired) {
            builder.append("final ");
        }
        builder.append(getType(arg)).append(" ").append(uncapFirst(arg.getName())).append(";");
        return builder.toString();
    }

    /**
     * Creates the Java code associated with a With-Constructor used to set optional parameters.
     */
    public static String createWithConstructor(final Arguments arg, final String requestName) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (arg.getType().equals("void")) {
            stringBuilder.append(voidWithConstructor(arg, requestName));
        } else {
            stringBuilder.append(withConstructor(arg, requestName));
        }

        stringBuilder.append(indent(2)).append("return this;\n").append(indent(1)).append("}\n");
        return stringBuilder.toString();
    }

    /**
     * Creates the Java code for a class variable getter function.
     */
    public static String createGetter(final String argName, final String argType) {
        return "public " + argType + " get" + capFirst(argName) + "() {\n"
                + indent(2) + "return this." + uncapFirst(argName) + ";\n"
                + indent(1) + "}\n";
    }

    /**
     * Creates the Java code for With-Constructors for optional arguments within
     * bulk request handlers. If said argument is defined within the base BulkRequest
     * handler, then the With-Constructor is generated with "@Override".  A special
     * With-Constructor is created for the parameter MaxUploadSize.
     */
    public static String createWithConstructorBulk(final Arguments arg, final String requestName) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (isBulkRequestArg(arg.getName())) {
            stringBuilder.append(indent(1)).append("@Override\n").append(withConstructorFirstLine(arg, requestName)).append(indent(2)).append("super.with").append(capFirst(arg.getName())).append("(").append(uncapFirst(arg.getName())).append(");\n");
        } else if (arg.getName().equals("MaxUploadSize")) {
            stringBuilder.append(maxUploadSizeWithConstructor(arg, requestName));
        } else if (arg.getType().equals("void")) {
            stringBuilder.append(voidWithConstructor(arg, requestName));
        } else {
            stringBuilder.append(withConstructor(arg, requestName));
        }

        stringBuilder.append(indent(2)).append("return this;\n").append(indent(1)).append("}\n");
        return stringBuilder.toString();
    }

    /**
     * Creates the Java code for a simple With-Constructor that sets the class variable to
     * the user provided variable, and adds said variable to the query param list.
     */
    protected static String withConstructor(final Arguments arg, final String requestName) {
        return withConstructorFirstLine(arg, requestName)
                + indent(2) + argAssignmentLine(arg)
                + indent(2) + updateQueryParamLine(arg.getName(), uncapFirst(arg.getName()));
    }

    /**
     * Creates the Java code for the With-Constructor for the special cased MaxUploadSize
     * parameter. This constructor ensures that the user defined MaxUploadSize is within
     * proper min and max bounds before adding it to the query param list.
     */
    private static String maxUploadSizeWithConstructor(final Arguments arg, final String requestName) {
        return withConstructorFirstLine(arg, requestName)
                + indent(2) + "if (" + uncapFirst(arg.getName()) + " > MIN_UPLOAD_SIZE_IN_BYTES) {\n"
                + indent(3) + putQueryParamLine(arg) + "\n"
                + indent(2) + "} else {\n"
                + indent(3) + putQueryParamLine(arg.getName(), "MAX_UPLOAD_SIZE_IN_BYTES") + "\n"
                + indent(2) + "}\n";
    }

    /**
     * Creates the Java code for With-Constructors that take boolean arguments. If the
     * user passes in true, the constructor adds the argument to the query params list.
     * However, if the user passes in false, the constructor removes the param from list
     */
    private static String voidWithConstructor(final Arguments arg, final String requestName) {
        return withConstructorFirstLine(arg, requestName)
                + indent(2) + "this." + uncapFirst(arg.getName()) + " = " + uncapFirst(arg.getName()) + ";\n"
                + indent(2) + "if (this." + uncapFirst(arg.getName()) + ") {\n"
                + indent(3) + putQueryParamLine(arg.getName(), "null") + "\n"
                + indent(2) + "} else {\n"
                + indent(3) + removeQueryParamLine(arg.getName())
                + indent(2) + "}\n";
    }

    /***
     * Creates the Java code for the first line of a with constructor.
     * Example: public MyRequestName withMyOptionalParameter(final MyArgType myArg) {
     */
    private static String withConstructorFirstLine(final Arguments arg, final String requestName) {
        return indent(1) + "public " + requestName + " with" + capFirst(arg.getName()) + "(final " + getType(arg) + " " + uncapFirst(arg.getName()) + ") {\n";
    }

    /**
     * Creates the Java code for assigning a class variable to a function
     * parameter of the same name.
     * Example: this.myVariable = myVariable;
     */
    private static String argAssignmentLine(final Arguments arg) {
        return "this." + uncapFirst(arg.getName()) + " = " + paramAssignmentRHS(arg) + ";\n";
    }

    /**
     * Creates the Java code for removing a query param from the query params list.
     * Example: this.getQueryParams().remove(\"myArg\");
     */
    private static String removeQueryParamLine(final String name) {
        return "this.getQueryParams().remove(\"" + Helper.camelToUnderscore(name) + "\");\n";
    }

    /**
     * Creates the Java code for putting a query param to the query params list.
     * Example: this.getQueryParams().put("myArg", MyArgType.toString());
     */
    public static String putQueryParamLine(final Arguments arg) {
        return putQueryParamLine(arg.getName(), queryParamArgToString(arg));
    }

    /**
     * Creates the Java code for putting a query param to the query params list.
     * Example: this.getQueryParams().put("myArg", MyArgType.toString());
     */
    protected static String putQueryParamLine(final String name, final String type) {
        final StringBuilder builder = new StringBuilder();
        builder.append("this.getQueryParams().put(\"");
        if (name.equalsIgnoreCase("BucketName")) {
            builder.append("bucket_id");
        } else {
            builder.append(Helper.camelToUnderscore(name));
        }
        builder.append("\", ")
                .append(type)
                .append(");");
        return builder.toString();
    }

    /**
     * Creates the Java code for converting an Argument within a query param line.
     * If the argument is Delimiter, then it is not escaped. If the argument is
     * of type String, then it is escaped. All other arguments return a string
     * containing the Java code for converting said argument to a String.
     */
    protected static String queryParamArgToString(final Arguments arg) {
        if (arg.getName().equalsIgnoreCase("Delimiter")
                || arg.getName().equalsIgnoreCase("BucketId")
                || arg.getName().equalsIgnoreCase("BucketName")) {
            return uncapFirst(arg.getName());
        }
        if (arg.getType().endsWith("String")) {
            return "UrlEscapers.urlFragmentEscaper().escape(" + uncapFirst(arg.getName()) + ").replace(\"+\", \"%2B\")";
        }
        return argToString(arg);
    }

    /**
     * Creates the Java code for updating the query param list.
     * Example: this.updateQueryParam("myArg", MyArgType.toString());
     */
    private static String updateQueryParamLine(final String name, final String type) {
        return "this.updateQueryParam(\"" + Helper.camelToUnderscore(name) + "\", " + type + ");\n";
    }

    /**
     * Creates the Java code for converting an object list into an Xml formatted String.
     * @param outputStringName The name of the String that will contain the Xml formatted
     *                         object list
     * @param objectListName The name of the object list that is being converted
     * @param operation The Operation of the Ds3Request whose generation utilizes this function
     */
    public static String toXmlLine(
            final String outputStringName,
            final String objectListName,
            final Operation operation) {
        final StringBuilder builder = new StringBuilder();
        builder.append("final String ").append(outputStringName).append(" = XmlOutput.toXml(").append(objectListName).append(", ");
        if (operation == Operation.START_BULK_PUT) {
            return builder.append("true);").toString();
        }
        return  builder.append("false);").toString();
    }

    /**
     * Creates the Java code for converting an argument to a String.
     */
    public static String argToString(final Arguments arg) {
        switch (arg.getType().toLowerCase()) {
            case "boolean":
            case "integer":
                return "String.valueOf(" + uncapFirst(arg.getName()) + ")";
            case "void":
                return "null";
            case "string":
                return uncapFirst(arg.getName());
            case "double":
            case "long":
                return capFirst(arg.getType()) + ".toString(" + uncapFirst(arg.getName()) + ")";
            case "int":
                return "Integer.toString(" + uncapFirst(arg.getName()) + ")";
            case "date":
                return "Long.toString(" + uncapFirst(arg.getName()) + ".getTime())";
            default:
                return uncapFirst(arg.getName()) + ".toString()";
        }
    }

    /**
     * Creates a comma separated list of sorted argument types.  This is used in
     * the generation of javadocs for creating links from depreciated constructors
     * to supported constructors.
     * @param arguments List of Arguments
     * @return Comma separated list of sorted argument types
     */
    public static String argTypeList(final ImmutableList<Arguments> arguments) {
        if (isEmpty(arguments)) {
            return "";
        }
        return sortConstructorArgs(arguments)
                .stream()
                .map(Arguments::getType).collect(Collectors.joining(", "));
    }

    /**
     * Creates a comma separated list of argument names.
     * @param arguments List of Arguments
     * @return Comma separated list of argument names.
     */
    public static String argsToList(final List<Arguments> arguments) {
        if (isEmpty(arguments)) {
            return "";
        }
        return arguments
                .stream()
                .map(i -> uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Gets the type of a variable, converting the type from Contract type to Java type.
     * @param var A variable
     * @return The Java type of the Variable
     */
    public static String getType(final Variable var) {
        final Arguments arg = new Arguments(var.getType(), var.getName());
        return getType(arg);
    }

    /**
     * Gets the type of an argument, converting the type from Contract type to Java type.
     * @param arg An Argument
     * @return The Java type of the Argument
     */
    public static String getType(final Arguments arg) {
        if (arg.getType() == null) {
            return "";
        }

        switch (arg.getType()) {
            case "void":
                return "boolean";
            case "ChecksumType":
                return arg.getType() + ".Type";
            default:
                return arg.getType();
        }
    }

    /**
     * Creates a comma separated parameter list from a list of Arguments.
     * Used for building Java constructors.
     * @param requiredArguments List of Arguments
     * @return Comma separated list of function parameters
     */
    public static String constructorArgs(final ImmutableList<Arguments> requiredArguments) {
        if (isEmpty(requiredArguments)) {
            return "";
        }
        return sortConstructorArgs(requiredArguments)
                .stream()
                .map(i -> "final " + getType(i) + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates a comma separated list of argument names, while changing one argument name to a specified value
     */
    public static String modifiedArgNameList(
            final ImmutableList<Arguments> arguments,
            final String modifyArgName,
            final String toArgName) {
        if (isEmpty(arguments)) {
            return "";
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Arguments arg : sortConstructorArgs(arguments)) {
            if (arg.getName().equals(modifyArgName)) {
                builder.add(toArgName);
            } else {
                builder.add(uncapFirst(arg.getName()));
            }
        }
        return builder.build()
                .stream()
                .map(i -> i)
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates the template lines for variables within Java generated models
     */
    public static String getModelVariable(final Element element) {
        final StringBuilder builder = new StringBuilder();

        if (element.isAttribute()) {
            builder.append(indent(1))
                    .append("@JacksonXmlProperty(isAttribute = true, localName = \"")
                    .append(element.getXmlTagName())
                    .append("\")\n");
        } else if (element.hasWrapper()) {
            builder.append(indent(1)).append("@JsonProperty(\"").append(capFirst(element.getName())).append("\")\n");
            if (element.getComponentType() != null) {
                builder.append(indent(1)).append("@JacksonXmlElementWrapper(useWrapping = true)\n");
            }
        } else if (!element.hasWrapper()) {
            builder.append(indent(1)).append("@JsonProperty(\"").append(capFirst(element.getXmlTagName())).append("\")\n");
            if (element.getComponentType() != null) {
                builder.append(indent(1)).append("@JacksonXmlElementWrapper(useWrapping = false)\n");
            }
        }
        builder.append(indent(1)).append("private ").append(convertType(element)).append(" ").append(uncapFirst(element.getName()));
        if (hasContent(element.getComponentType())) {
            builder.append(" = new ArrayList<>()");
        }
        builder.append(";");
        return builder.toString();
    }

    /**
     * Creates the Java type from elements, converting component types into a List.
     */
    public static String convertType(final Element element) throws IllegalArgumentException {
        return convertType(element.getType(), element.getComponentType());
    }

    /**
     * Creates the Java type from elements, converting component types into a List,
     * and ChecksumType into ChecksumType.Type
     */
    public static String convertType(final String type, final String componentType) throws IllegalArgumentException {
        if (isEmpty(componentType)) {
            final String typeNoPath = stripPath(type);
            switch (typeNoPath.toLowerCase()) {
                case "checksumtype":
                    return "ChecksumType.Type";
                default:
                    return typeNoPath;
            }
        }
        if (type.equalsIgnoreCase("array")) {
            return "List<" + stripPath(componentType) + ">";
        }
        throw new IllegalArgumentException("Unknown element type: " + type);
    }

    /**
     * Creates a comma separated list of Elements.  This is used in the Java model generation.
     */
    public static String getModelConstructorArgs(final ImmutableList<Element> elements) {
        if (isEmpty(elements)) {
            return "";
        }
        return sortModelConstructorArgs(elements)
                .stream()
                .map(i -> "final " + convertType(i) + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Sorts Elements. Used in Java model generation.
     */
    public static ImmutableList<Element> sortModelConstructorArgs(final ImmutableList<Element> elements) {
        if (isEmpty(elements)) {
            return ImmutableList.of();
        }
        final List<Element> sortable = new ArrayList<>();
        sortable.addAll(elements);
        Collections.sort(sortable, new CustomElementComparator());

        final ImmutableList.Builder<Element> builder = ImmutableList.builder();
        builder.addAll(sortable);
        return builder.build();
    }

    /**
     * Creates a comma separated list of enum constants. Used in Java model generation.
     */
    public static String getEnumValues(
            final ImmutableList<EnumConstant> enumConstants,
            final int indent) {
        if (isEmpty(enumConstants)) {
            return "";
        }
        return enumConstants
                .stream()
                .map(i -> indent(indent) + i.getName())
                .collect(Collectors.joining(",\n"));
    }

    /**
     * Adds an EnumConstant to a list of EnumConstants. Used to include additional enum values, such as NONE.
     * @param enumConstants List of EnumConstants
     * @param newEnumValue New enum value
     */
    public static ImmutableList<EnumConstant> addEnum(
            final ImmutableList<EnumConstant> enumConstants,
            final String newEnumValue) {
        final ImmutableList.Builder<EnumConstant> builder = ImmutableList.builder();
        if (hasContent(enumConstants)) {
            builder.addAll(enumConstants);
        }
        builder.add(new EnumConstant(newEnumValue));
        return builder.build();
    }

    /**
     * Determines if this package is SpectraDs3 and/or a Notification. This is used to determine if
     * request/response handlers need to include an import to parent class.
     * @return True if package is either SpectraDs3 and/or a Notification, else false
     */
    public static boolean isSpectraDs3OrNotification(final String packageName) {
        return isSpectraDs3(packageName) || packageName.contains(Constants.NOTIFICATION_PACKAGE);
    }

    /**
     * Creates the Java code associated with processing a given response code
     * @param responseCode A Ds3ResponseCode
     * @param indent The level of indentation needed to properly align the generated code
     */
    public static String processResponseCodeLines(final Ds3ResponseCode responseCode, final int indent) {
        final Ds3ResponseType ds3ResponseType = responseCode.getDs3ResponseTypes().get(0);
        final String responseType = stripPath(
                convertType(ds3ResponseType.getType(), ds3ResponseType.getComponentType()));
        if (responseType.equalsIgnoreCase("null")) {
            return "//Do nothing, payload is null\n"
                    + indent(indent) + "break;";
        }
        final StringBuilder builder = new StringBuilder();
        builder.append("try (final InputStream content = getResponse().getResponseStream()) {\n")
                .append(indent(indent + 1))
                .append("this.")
                .append(createDs3ResponseTypeParamName(ds3ResponseType));
        if (responseType.equalsIgnoreCase("String")) {
            builder.append(" = IOUtils.toString(content, StandardCharsets.UTF_8);\n");
        } else {
            builder.append(" = XmlOutput.fromXml(content, ")
                    .append(responseType)
                    .append(".class);\n");
        }
        builder.append(indent(indent)).append("}\n")
                .append(indent(indent)).append("break;");
        return builder.toString();
    }

    /**
     * Creates the Java code for getter functions for all response results
     */
    public static String createAllResponseResultGetters(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return "";
        }
        final ImmutableMap<String, Ds3ResponseType> map = createUniqueDs3ResponseTypesMap(responseCodes);
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Map.Entry<String, Ds3ResponseType> entry : map.entrySet()) {
            builder.add(createResponseResultGetter(entry.getKey(), entry.getValue()));
        }
        return builder.build()
                .stream()
                .map(i -> i)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Creates the Java code for the getter function for a response result
     * @param paramName The name of the response result param
     * @param responseType The response type
     */
    protected static String createResponseResultGetter(
            final String paramName,
            final Ds3ResponseType responseType) {
        if (isEmpty(paramName) || isEmpty(responseType.getType())) {
            return "";
        }
        final String returnType = convertType(responseType.getType(), responseType.getComponentType());
        return indent(1) + "public " +  returnType + " get" + capFirst(paramName) + "() {\n" +
                indent(2) + "return this." + paramName + ";\n" +
                indent(1) + "}\n";
    }

    /**
     * Creates the Java code for the class parameters associated with the response payloads
     * @param responseCodes List of Ds3ResponseCodes whose response types will be turned into
     *                      class parameters
     */
    public static String createAllResponseResultClassVars(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return "";
        }
        final ImmutableMap<String, Ds3ResponseType> map = createUniqueDs3ResponseTypesMap(responseCodes);
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Map.Entry<String, Ds3ResponseType> entry : map.entrySet()) {
            builder.add("private " + convertType(entry.getValue().getType(), entry.getValue().getComponentType())
                            + " " + entry.getKey() + ";");
        }
        return builder.build()
                .stream()
                .map(i -> indent(1) + i)
                .collect(Collectors.joining("\n"));
    }

    /**
     * Creates a map containing all unique Ds3Response types found within a list of
     * response codes. The map key consists of the response type parameter names,
     * and values are the Ds3ResponseType associated with that parameter name.
     */
    protected static ImmutableMap<String, Ds3ResponseType> createUniqueDs3ResponseTypesMap(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableMap.of();
        }
        final ImmutableMap.Builder<String, Ds3ResponseType> builder = ImmutableMap.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            for (final Ds3ResponseType responseType : responseCode.getDs3ResponseTypes()) {
                final String responseParamName = createDs3ResponseTypeParamName(responseType);
                if (hasContent(responseParamName)
                        && !builder.build().containsKey(responseParamName)) {
                    builder.put(responseParamName, responseType);
                }
            }
        }
        return builder.build();
    }

    /**
     * Creates the parameter name associated with a response type. Component types contain
     * name spacing of "List", and all type names end with "Result"
     * Example:
     *   Type is null:  null -> ""
     *   No Component Type:  MyType -> myTypeResult
     *   With Component Type:  MyComponentType -> myComponentTypeListResult
     */
    protected static String createDs3ResponseTypeParamName(final Ds3ResponseType responseType) {
        if (stripPath(responseType.getType()).equalsIgnoreCase("null")) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        if (hasContent(responseType.getComponentType())) {
            builder.append(uncapFirst(stripPath(responseType.getComponentType())) + "List");
        } else {
            builder.append(uncapFirst(stripPath(responseType.getType())));
        }
        if (!builder.toString().toLowerCase().endsWith("result")) {
            builder.append("Result");
        }
        return builder.toString();
    }

    /**
     * Removes response codes that are associated with errors from the list.
     * Error response codes are associated with values greater or equal to 400.
     */
    public static ImmutableList<Ds3ResponseCode> removeErrorResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            if (responseCode.getCode() < 400) {
                builder.add(responseCode);
            }
        }
        return builder.build();
    }

    /**
     * Removes a specified variable from a list of variables
     */
    public static ImmutableList<Variable> removeVariable(
            final ImmutableList<Variable> vars,
            final String varName) {
        if (isEmpty(vars)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();

        for (final Variable var : vars) {
            if (!var.getName().equals(varName)) {
                builder.add(var);
            }
        }
        return builder.build();
    }

    /**
     * Gets the right-hand-side (RHS) of the assignment for request constructor parameters.
     * For UUID parameters, the parameter will be converted to a string. For all other
     * parameters, the RHS will be the parameter name.
     */
    public static String paramAssignmentRHS(final Arguments arg) {
        if (arg.getType().equals("UUID")) {
            return argToString(arg);
        }
        return uncapFirst(arg.getName());
    }

    /**
     * Creates the java code for annotating a command in the client. if the annotation
     * info is null, then an empty string is returned.
     */
    public static String toAnnotation(final AnnotationInfo annotationInfo) {
        if (annotationInfo == null) {
            return "";
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        if (hasContent(annotationInfo.getResponsePayloadModel())) {
            builder.add(createAnnotation("ResponsePayloadModel", annotationInfo.getResponsePayloadModel()));
        }
        if (hasContent(annotationInfo.getAction())) {
            builder.add(createAnnotation("Action", annotationInfo.getAction()));
        }
        if (hasContent(annotationInfo.getResource())) {
            builder.add(createAnnotation("Resource", annotationInfo.getResource()));
        }
        return builder.build()
                .stream()
                .map(i -> i)
                .collect(Collectors.joining("\n" + indent(1)));
    }

    /**
     * Creates the line for a specific annotation
     */
    protected static String createAnnotation(
            final String annotationName,
            final String annotationValue) {
        if (isEmpty(annotationValue) || isEmpty(annotationName)) {
            return "";
        }
        return "@" + annotationName + "(\"" + annotationValue + "\")";
    }
}
