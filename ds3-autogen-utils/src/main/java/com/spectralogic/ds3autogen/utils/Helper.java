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

package com.spectralogic.ds3autogen.utils;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public final class Helper {

    private static final Helper helper = new Helper();
    private static final String INDENT = "    ";

    protected enum SelectRemoveVoidType { SELECT_VOID, REMOVE_VOID }

    private Helper() {}

    public static Helper getInstance() {
        return helper;
    }

    public static String indent(final int depth) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            stringBuilder.append(INDENT);
        }
        return stringBuilder.toString();
    }

    public static String capFirst(final String str) {
        return StringUtils.capitalize(str);
    }

    public static String uncapFirst(final String str) {
        return StringUtils.uncapitalize(str);
    }

    public static String camelToUnderscore(final String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }

    public static String underscoreToCamel(final String str) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, str);
    }

    public static String getHttpVerb(final HttpVerb httpVerb, final Action action) {
        if(httpVerb != null) {
            return httpVerb.toString();
        }

        switch (action) {
            case BULK_MODIFY:
            case CREATE:
            case MODIFY:
                return "PUT";
            case DELETE:
            case BULK_DELETE:
                return "DELETE";
            case LIST:
            case SHOW:
                return "GET";
            default:
                return null;
        }
    }

    public static String getBulkVerb(final Operation operation) {
        if (operation == Operation.START_BULK_GET) {
            return "GET";
        } else if (operation == Operation.START_BULK_PUT) {
            return "PUT";
        } else {
            return null;
        }
    }

    /**
     * If the given string contains "RequestHandler",
     * then chop it off and anything after, returning the root substring.
     * @param name - "SomeRequestHandler$SomeRequestApiBean"
     * @return String - "Some"
     */
    public static String removeTrailingRequestHandler(final String name) {
        if (false == name.contains("RequestHandler")) {
            return name;
        }
        return name.substring(0, name.lastIndexOf("RequestHandler"));
    }

    /**
     * Remove the last part of a string that contains a .
     * @param name - com.spectralogic.blah.SomeRequestHandler
     * @return String - SomeRequestHandler
     */
    public static String unqualifiedName(final String name) {
        return name.substring(name.lastIndexOf('.') + 1);
    }

    /**
     * Removes an Argument by name from a list of Arguments.
     * @param arguments List of Arguments
     * @param name The name of the Argument to be removed from the Argument list
     * @return The list of Arguments minus the specified Argument.
     */
    public static ImmutableList<Arguments> removeArgument(final List<Arguments> arguments, final String name) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        for (final Arguments arg : arguments) {
            if (!arg.getName().equals(name)) {
                builder.add(arg);
            }
        }
        return builder.build();
    }

    /**
     * Combines two lists of Arguments
     * @param arguments List of Arguments
     * @param additionalArguments List of Arguments
     * @return Combined list of Arguments
     */
    public static ImmutableList<Arguments> addArgument(
            final ImmutableList<Arguments> arguments,
            final ImmutableList<Arguments> additionalArguments) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        if (hasContent(arguments)) {
            builder.addAll(arguments);
        }
        if (hasContent(additionalArguments)) {
            builder.addAll(additionalArguments);
        }
        return builder.build();
    }

    /**
     * Adds an Argument to a list of Arguments
     * @param arguments List of Arguments
     * @param argName Name of Argument being added
     * @param argType Type of Argument being added
     * @return List of arguments
     */
    public static ImmutableList<Arguments> addArgument(
            final ImmutableList<Arguments> arguments,
            final String argName,
            final String argType) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        if (hasContent(arguments)) {
            builder.addAll(arguments);
        }
        builder.add(new Arguments(argType, argName));
        return builder.build();
    }

    /**
     * Determines if a an argument exists within a list of arguments.
     * @param arguments A list of Arguments
     * @param argName The name of the Argument being searched for
     */
    public static boolean containsArgument(
            final ImmutableList<Arguments> arguments,
            final String argName) {
        if (isEmpty(arguments) || isEmpty(argName)) {
            return false;
        }

        for (final Arguments arg : arguments) {
            if (arg.getName().equalsIgnoreCase(argName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Sorts a list of Arguments by name.  Used for sorting constructor arguments for consistency.
     * @param arguments List of Arguments
     * @return Sorted list of Arguments
     */
    public static ImmutableList<Arguments> sortConstructorArgs(final ImmutableList<Arguments> arguments) {
        final List<Arguments> sortable = new ArrayList<>();
        sortable.addAll(arguments);
        Collections.sort(sortable, new CustomArgumentComparator());

        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(sortable);
        return builder.build();
    }

    /**
     * Removes all void arguments from the provided list.  This is used with the required params
     * list to prevent the unnecessary inclusion of void variables into variable list and constructors.
     */
    public static ImmutableList<Arguments> removeVoidArguments(
            final ImmutableList<Arguments> arguments) {
        return selectOrRemoveVoidArguments(arguments, SelectRemoveVoidType.REMOVE_VOID);
    }

    /**
     * Gets all void arguments from the provided list.  This is used within constructors to add
     * query parameters that are always required.
     */
    public static ImmutableList<Arguments> getVoidArguments(
            final ImmutableList<Arguments> arguments) {
        return selectOrRemoveVoidArguments(arguments, SelectRemoveVoidType.SELECT_VOID);
    }

    /**
     * Selects the specified Arguments and returns them in list form.  Selection options are
     * to retrieve all void arguments, or retrieve all non-void arguments.
     * @param arguments List of arguments
     * @param selectRemoveVoidType The selection choice
     * @return A list of the selected Arguments
     */
    protected static ImmutableList<Arguments> selectOrRemoveVoidArguments(
            final ImmutableList<Arguments> arguments,
            final SelectRemoveVoidType selectRemoveVoidType) {
        if (isEmpty(arguments)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        for (final Arguments arg : arguments) {
            if (addVoidArgument(arg, selectRemoveVoidType)) {
                builder.add(arg);
            }
        }
        return builder.build();
    }

    /**
     * Determines if an Argument should be added based on type and selection choice.
     * @param arg An Argument
     * @param selectRemoveVoidType The selection choice
     * @return True if the Argument should be selected, else false
     */
    protected static boolean addVoidArgument(
            final Arguments arg,
            final SelectRemoveVoidType selectRemoveVoidType) {
        if (selectRemoveVoidType.equals(SelectRemoveVoidType.REMOVE_VOID)) {
            return !arg.getType().equals("void");
        }
        return arg.getType().equals("void");
    }

    /**
     * Creates a sorted comma separated list of response codes
     */
    public static String getResponseCodes(final ImmutableList<Ds3ResponseCode> responseCodes) {
        final List<String> sortable = new ArrayList<>();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            sortable.add(Integer.toString(responseCode.getCode()));
        }
        Collections.sort(sortable);
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.addAll(sortable);
        return builder.build()
                .stream()
                .map(i -> i)
                .collect(Collectors.joining(", "));
    }

    /**
     * Strips the path from a given string, returning the request/type name.
     * For example, com.spectralogic.ds3autogen.Utils.Helper would return Helper
     */
    public static String stripPath(final String str) {
        //Wrapping function so that it is accessible from within models
        return NormalizingContractNamesUtil.removePath(str);
    }


    public static boolean isPrimitiveType(final String type) {
        switch (type) {
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
            case "double":
            case "java.lang.Long":
            case "long":
            case "java.lang.Integer":
            case "int":
            case "boolean":
                return true;
            case "java.util.Set":
            case "array":
            default:
                // any complex sub element such as "com.spectralogic.s3.server.domain.UserApiBean"
                return false;
        }
    }
}
