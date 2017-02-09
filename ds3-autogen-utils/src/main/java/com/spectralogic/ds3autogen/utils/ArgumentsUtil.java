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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Contains utilities for handling and modifying Arguments and lists of Arguments
 */
public class ArgumentsUtil {

    /**
     * Determines if a list of Arguments contains a given type
     */
    public static boolean containsType(final ImmutableList<Arguments> args, final String type) {
        return !(isEmpty(args) || isEmpty(type)) && getAllArgumentTypes(args).contains(type);
    }

    /**
     * Retrieves the set of all types within a list of Arguments
     */
    public static ImmutableSet<String> getAllArgumentTypes(final ImmutableList<Arguments> args) {
        if (isEmpty(args)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Arguments arg : args) {
            builder.add(arg.getType());
        }
        return builder.build();
    }

    /**
     * Changes the type of all arguments with the specified type
     */
    public static ImmutableList<Arguments> modifyType(
            final ImmutableList<Arguments> args,
            final String curType,
            final String newType) {
        if (isEmpty(args)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        for (final Arguments arg : args) {
            if (arg.getType().equals(curType)) {
                builder.add(new Arguments(newType, arg.getName()));
            } else {
                builder.add(arg);
            }
        }
        return builder.build();
    }
}
