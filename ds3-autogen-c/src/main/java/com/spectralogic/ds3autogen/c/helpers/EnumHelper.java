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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.indent;

public final class EnumHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EnumHelper.class);
    private EnumHelper() {}

    private final static EnumHelper enumHelper = new EnumHelper();

    public static EnumHelper getInstance() {
        return enumHelper;
    }

    public static String getNameUnderscores(final String name) {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name)));
    }

    public static String getDs3Type(final String name) {
        return "ds3_" + getNameUnderscores(name);
    }

    public static String getEnumValues(final ImmutableList<String> enumValues) {
        if (isEmpty(enumValues)) {
            return "";
        }
        return enumValues
                .stream()
                .map(value -> indent(1) + value)
                .collect(Collectors.joining(",\n"));
    }

    public static ImmutableSet<String> getEnumNamesSet(final ImmutableList<Enum> allEnums) {
        return ImmutableSet.copyOf(allEnums.stream().map(Enum::getName).collect(Collectors.toSet()));
    }

    public static ImmutableList<String> convertDs3EnumConstants(final ImmutableList<Ds3EnumConstant> enums) {
        final ImmutableList.Builder<String> stringListBuilder = ImmutableList.builder();
        for (final Ds3EnumConstant currentEnum : enums) {
            stringListBuilder.add(currentEnum.getName());
        }
        return stringListBuilder.build();
    }

    public static String generateMatcher(final ImmutableList<String> enumValues) {
        if (isEmpty(enumValues)) {
            return "";
        }

        final StringBuilder outputBuilder = new StringBuilder();
        final int numEnumValues = enumValues.size();
        for (int currentEnum = 0; currentEnum < numEnumValues; currentEnum++) {
            outputBuilder.append(indent(1));

            if (currentEnum > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumName = enumValues.get(currentEnum);
            outputBuilder.append("if (xmlStrcmp(text, (const xmlChar*) \"").append(currentEnumName).append("\") == 0) {\n");
            outputBuilder.append(indent(2)).append("return ").append(currentEnumName).append(";\n");
        }

        final String enumName = enumValues.get(0);
        outputBuilder.append(indent(1)).append("} else {").append("\n");
        outputBuilder.append(indent(2)).append("ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown value of '%s'.  Returning ").append(enumName).append(" for safety.\", text);\n");
        outputBuilder.append(indent(2)).append("return ").append(enumName).append(";\n");

        outputBuilder.append(indent(1)).append("}");
        return outputBuilder.toString();
    }

    public static String generateToString(final ImmutableList<String> enumValues) {
        final StringBuilder outputBuilder = new StringBuilder();
        final int numEnumValues = enumValues.size();

        if (numEnumValues <= 0) {
            LOG.warn("Empty enumValues list.");
            return "";
        }

        for (int currentEnum = 0; currentEnum < numEnumValues; currentEnum++) {
            outputBuilder.append(indent(1));

            if (currentEnum > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumName = enumValues.get(currentEnum);
            outputBuilder.append("if (input == ").append(currentEnumName).append(") {\n");
            outputBuilder.append(indent(2)).append("return \"").append(currentEnumName).append("\";\n");
        }

        outputBuilder.append(indent(1)).append("} else {\n");
        outputBuilder.append(indent(2)).append("return \"\";\n");
        outputBuilder.append(indent(1)).append("}\n");

        return outputBuilder.toString();
    }
}
