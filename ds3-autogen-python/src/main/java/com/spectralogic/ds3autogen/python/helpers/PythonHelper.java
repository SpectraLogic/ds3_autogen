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
import com.spectralogic.ds3autogen.python.model.request.RequestPayload;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

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
            final ImmutableList<Arguments> optionalArgs,
            final RequestPayload requestPayload) {
        final ImmutableList<String> requiredInits = toRequiredArgInitList(requiredArgs);
        final ImmutableList<String> optionalInits = toOptionalArgInitList(optionalArgs);

        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.addAll(requiredInits);
        if (requestPayload != null) {
            if (requestPayload.isOptional()) {
                builder.add(requestPayload.getName() + "=None");
            } else {
                builder.add(requestPayload.getName());
            }
        }
        builder.addAll(optionalInits);
        return builder.build().stream()
                .collect(Collectors.joining(", "));
    }

    /**
     * Creates a comma separated list of all optional arguments for use in the
     * request handler init function. Optional arguments are set to None.
     */
    protected static ImmutableList<String> toOptionalArgInitList(final ImmutableList<Arguments> optionalArgs) {
        if (isEmpty(optionalArgs)) {
            return ImmutableList.of();
        }
        return optionalArgs.stream()
                .sorted(new CustomArgumentComparator())
                .map(i -> camelToUnderscore(i.getName()) + "=None")
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Creates a comma separated list of all required arguments for use in the
     * request handler init function. This list will always be started with 'self'
     */
    protected static ImmutableList<String> toRequiredArgInitList(final ImmutableList<Arguments> requiredArgs) {
        if (isEmpty(requiredArgs)) {
            return ImmutableList.of("self");
        }
        final ImmutableList<String> requiredInits = requiredArgs.stream()
                .sorted(new CustomArgumentComparator())
                .map(i -> camelToUnderscore(i.getName()))
                .collect(GuavaCollectors.immutableList());

        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        builder.add("self")
                .addAll(requiredInits);

        return builder.build();
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
