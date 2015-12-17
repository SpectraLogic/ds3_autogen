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

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.Helper.capFirst;
import static com.spectralogic.ds3autogen.utils.Helper.sortConstructorArgs;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;

public final class NetHelper {
    private NetHelper() {
        // pass
    }

    public static String constructor(final ImmutableList<Arguments> args) {
        return sortConstructorArgs(args)
                .stream()
                .map(i -> getType(i) + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Gets the type of an argument, converting the type from Contract type to a .net type.
     * @param arg An Argument
     * @return The Java type of the Argument
     */
    public static String getType(final Arguments arg) {
        if (arg.getType() == null) {
            return "";
        }

        switch (arg.getType()) {
            case "void":
                return "bool";
            case "Integer":
                return "int";
            case "String":
                return "string";
            case "UUID":
                return "Guid";
            case "ChecksumType":
                return arg.getType() + ".Type";
            default:
                return arg.getType();
        }
    }

    /**
     * Creates the .net code for converting an argument to a String.
     */
    public static String argToString(final Arguments arg) {
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

    private final static NetHelper instance = new NetHelper();

    public static NetHelper getInstance() {
        return instance;
    }
}
