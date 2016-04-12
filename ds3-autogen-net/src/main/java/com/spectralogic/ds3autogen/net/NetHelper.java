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

package com.spectralogic.ds3autogen.net;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.*;

/**
 * Series of static functions that are used within the Net module template
 * files to help generate the .Net SDK code
 */
public final class NetHelper {

    private final static Logger LOG = LoggerFactory.getLogger(NetHelper.class);

    private NetHelper() {
        // pass
    }

    /**
     * Creates a comma separated list of constructor arguments
     */
    public static String constructor(final ImmutableList<Arguments> args) {
        if (isEmpty(args)) {
            return "";
        }
        return sortConstructorArgs(args)
                .stream()
                .map(i -> getType(i) + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Converts a camel cased name to an underscored name.
     * This is a wrapper function because Helper is not currently accessible
     * from within the template
     */
    public static String camelToUnderscore(final String str) {
        return Helper.camelToUnderscore(str);
    }

    /**
     * Gets the type of an argument, converting the type from Contract type to a .net type.
     * @param arg An Argument
     * @return The Java type of the Argument
     */
    public static String getType(final Arguments arg) {
        if (isEmpty(arg.getType())) {
            LOG.error("Argument does not have a type: " + arg.getName());
            return "";
        }
        return toNetType(arg.getType());
    }

    /**
     * Converts a contract type into a .net type
     */
    public static String toNetType(final String contractType) {
        if (isEmpty(contractType)) {
            return "";
        }
        switch (contractType.toLowerCase()) {
            case "void":
            case "boolean":
                return "bool";
            case "integer":
                return "int";
            case "string":
                return "string";
            case "uuid":
                return "Guid";
            case "checksumtype":
                return contractType + ".Type";
            case "date":
                return "DateTime";
            default:
                return contractType;
        }
    }

    //TODO delete once removed from all templates
    /**
     * Gets the nullable type of an argument, converting the argument from a Contract
     * type to a nullable .net type.
     */
    public static String getNullableType(final Arguments arg) {
        return getNullableType(arg.getType());
    }

    /**
     * Gets the nullable .net type from the provided Contract type
     */
    public static String getNullableType(final String type) {
        if (isEmpty(type)) {
            return "";
        }
        final String netType = toNetType(type);
        switch (netType) {
            case "":
                return "";
            case "string":
                return netType;
            default:
                return netType + "?";
        }
    }

    /**
     * Creates the .net code for converting an argument to a String.
     */
    public static String argToString(final Arguments arg) {
        if (isEmpty(arg.getType())) {
            LOG.error("Argument does not have a type: " + arg.getName());
            return "";
        }
        switch (arg.getType().toLowerCase()) {
            case "boolean":
            case "void":
                return "null";
            case "string":
                return capFirst(arg.getName());
            case "double":
            case "integer":
            case "int":
            case "long":
                return  capFirst(arg.getName()) + ".ToString()";
            default:
                return uncapFirst(arg.getName()) + ".ToString()";
        }
    }

    /**
     * Determines if a list of arguments contains the specified argument.
     * This is a wrapper function because Helper is not currently accessible
     * from within the template
     */
    public static boolean containsArgument(final ImmutableList<Arguments> args, final String argName) {
        return Helper.containsArgument(args, argName);
    }

    /**
     * Returns the .net code for the right-hand-side of an assignment. This is
     * used to assign required variables within a constructor. Return examples:
     * IEnumerable list: myList.ToList()
     * Default: myArgument
     */
    public static String paramAssignmentRightValue(final Arguments arg) {
        if (isEmpty(arg.getName()) || isEmpty(arg.getType())) {
            return "";
        }
        final Pattern patternIEnumerable = Pattern.compile("IEnumerable<\\w+>");
        if (patternIEnumerable.matcher(arg.getType()).find()) {
            return Helper.uncapFirst(arg.getName()) + ".ToList()";
        }
        return Helper.uncapFirst(arg.getName());
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

    private final static NetHelper instance = new NetHelper();

    public static NetHelper getInstance() {
        return instance;
    }
}
