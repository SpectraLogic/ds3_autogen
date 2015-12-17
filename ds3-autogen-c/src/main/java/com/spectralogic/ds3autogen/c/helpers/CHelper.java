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

package com.spectralogic.ds3autogen.c.helpers;


import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.utils.Helper;

import java.text.ParseException;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class CHelper {
    private final static String INDENT = "    ";

    private CHelper() {}

    public static String indent(final int depth) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            stringBuilder.append(INDENT);
        }
        return stringBuilder.toString();
    }

    public static String getEnumValues(final ImmutableList<Ds3EnumConstant> enumConstants) {
        if (isEmpty(enumConstants)) {
            return "";
        }
        return enumConstants
                .stream()
                .map(i -> indent(1) + i.getName())
                .collect(Collectors.joining(",\n"));
    }

    public static String elementTypeToString(final Ds3Element element) throws ParseException {
        switch (element.getType()) {
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return "ds3_str*";
            case "double":
                return "double";   //? ex: 0.82
            case "java.lang.Long":
            case "long":
                return "uint64_t"; // size_t
            case "java.lang.Integer":
            case "int":
                return "int";
            case "java.util.Set":
            case "array":
                return ""; // TODO ???
            case "boolean":
                return "ds3_bool";

            default:
                final StringBuilder elementTypeBuilder = new StringBuilder();
                elementTypeBuilder.append("ds3_");
                elementTypeBuilder.append(Helper.camelToUnderscore(Helper.unqualifiedName(element.getType())));
                elementTypeBuilder.append("*");
                return elementTypeBuilder.toString();
                //throw new ParseException("Unknown element: " + element, 0);
        }
    }
}
