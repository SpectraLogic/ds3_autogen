/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;

/**
 * Series of static functions that are used within the Python module template files
 * to help generate the Python SDK code.
 */
public final class PythonHelper {

    private final static PythonHelper pythonHelper = new PythonHelper();
    private final static String PYTHON_INDENT = "  ";

    private PythonHelper() {
        //pass
    }

    public static PythonHelper getInstance() {
        return pythonHelper;
    }

    /**
     * Creates a comma separated list of all required and optional arguments
     * for use in the request handler init function.
     */
    public static String toRequestInitList(
            final ImmutableList<Arguments> requiredArgs,
            final ImmutableList<Arguments> optionalArgs) {
        final String optionalInits = toOptionalArgInitList(optionalArgs);
        if (isEmpty(optionalInits)) {
            return toRequiredArgInitList(requiredArgs);
        }
        return toRequiredArgInitList(requiredArgs) + ", " + optionalInits;
    }

    /**
     * Creates a comma separated list of all optional arguments for use in the
     * request handler init function. Optional arguments are set to None.
     */
    protected static String toOptionalArgInitList(final ImmutableList<Arguments> optionalArgs) {
        if (isEmpty(optionalArgs)) {
            return "";
        }
        return optionalArgs.stream()
                .map(i -> camelToUnderscore(i.getName()) + "=None")
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates a comma separated list of all required arguments for use in the
     * request handler init function. This list will always be started with 'self'
     */
    protected static String toRequiredArgInitList(final ImmutableList<Arguments> requiredArgs) {
        if (isEmpty(requiredArgs)) {
            return "self";
        }
        return "self, " + requiredArgs.stream()
                .map(i -> camelToUnderscore(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates comma separated lines for a list of strings. This is used to
     * generate the lists within the python model descriptors.
     */
    public static String toCommaSeparatedLines(final ImmutableList<String> lines, final int depth) {
        if (isEmpty(lines)) {
            return "";
        }
        return "\n" + pythonIndent(depth) + lines.stream()
                .collect(Collectors.joining(",\n" + pythonIndent(depth))) + "\n" + pythonIndent(depth - 1);
    }

    /**
     * Creates a comma separated list of integers
     */
    public static String toCommaSeparatedList(final ImmutableList<Integer> ints) {
        if (isEmpty(ints)) {
            return "";
        }
        return ints.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates indents for python code. This is different than for the standard
     * Helper implementation of indentation because it uses 2 spaces instead of 4
     * per depth level.
     */
    public static String pythonIndent(final int depth) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            builder.append(PYTHON_INDENT);
        }
        return builder.toString();
    }
}
