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

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

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
     * Creates a comma separated list of constructor parameters which always starts with 'self'
     */
    public static String toRequestInitList(final ImmutableList<String> strings) {
        if (isEmpty(strings)) {
            return "self";
        }
        return "self, " + strings.stream()
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
