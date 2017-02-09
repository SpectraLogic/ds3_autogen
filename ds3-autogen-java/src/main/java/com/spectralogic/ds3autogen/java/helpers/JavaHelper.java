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

package com.spectralogic.ds3autogen.java.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.java.models.AnnotationInfo;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import com.spectralogic.ds3autogen.java.models.Variable;
import com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
     * Creates the Java code for a class variable getter function.
     */
    public static String createGetter(final String argName, final String argType) {
        return "public " + argType + " get" + capFirst(argName) + "() {\n"
                + indent(2) + "return this." + uncapFirst(argName) + ";\n"
                + indent(1) + "}\n";
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
        builder.append(indent(1)).append("private ").append(ResponseAndParserUtils.convertType(element)).append(" ").append(uncapFirst(element.getName()));
        if (hasContent(element.getComponentType())) {
            builder.append(" = new ArrayList<>()");
        }
        builder.append(";");
        return builder.toString();
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
                .map(i -> "final " + ResponseAndParserUtils.convertType(i) + " " + uncapFirst(i.getName()))
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
     * Removes a specified variable from a list of variables
     */
    public static ImmutableList<Variable> removeVariable(
            final ImmutableList<Variable> vars,
            final String varName) {
        if (isEmpty(vars)) {
            return ImmutableList.of();
        }
        return vars.stream()
                .filter(var -> !var.getName().equals(varName))
                .collect(GuavaCollectors.immutableList());
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

    /**
     * Determines if a string has content. This is a wrapper for a utility function so that
     * it is accessible from within the templates
     */
    public static boolean stringHasContent(final String string) {
        return hasContent(string);
    }

    /**
     * Creates the Java type from elements, converting component types into a List.
     * This is a wrapper for a utility function so that it is accessible from within the templates.
     */
    public static String convertType(final Element element) throws IllegalArgumentException {
        return ResponseAndParserUtils.convertType(element);
    }
}
