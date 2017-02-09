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

package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
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
        final String name_underscores = getNameUnderscores(name);
        if (name_underscores.startsWith("ds3_")) {
            return name_underscores;
        }
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
        return allEnums.stream().map(Enum::getName).sorted().collect(GuavaCollectors.immutableSet());
    }

    public static ImmutableList<String> convertDs3EnumConstants(final Ds3Type ds3Type) {
        final ImmutableList.Builder<String> stringListBuilder = ImmutableList.builder();
        for (final Ds3EnumConstant currentEnum : ds3Type.getEnumConstants()) {
            stringListBuilder.add(getDs3Type(ds3Type.getName()).toUpperCase() + '_' + currentEnum.getName().toUpperCase());
        }
        return stringListBuilder.build();
    }

    public static String generateMatcher(final Enum enumEntry) {
        if (isEmpty(enumEntry.getValues())) {
            return "";
        }

        final StringBuilder outputBuilder = new StringBuilder();
        final int numEnumValues = enumEntry.getValues().size();
        for (int currentEnum = 0; currentEnum < numEnumValues; currentEnum++) {
            outputBuilder.append(indent(1));

            if (currentEnum > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumName = enumEntry.getValues().get(currentEnum);
            final int prefix_length = enumEntry.getName().length() + 1; // to +1 to account for trailing underbar
            outputBuilder.append("if (xmlStrcmp(text, (const xmlChar*) \"").append(currentEnumName.substring(prefix_length)).append("\") == 0) {\n");
            outputBuilder.append(indent(2)).append("return ").append(currentEnumName).append(";\n");
        }

        final String enumName = enumEntry.getValues().get(0);
        outputBuilder.append(indent(1)).append("} else {").append("\n");
        outputBuilder.append(indent(2)).append("ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown value of '%s'.  Returning ").append(enumName).append(" for safety.\", text);\n");
        outputBuilder.append(indent(2)).append("return ").append(enumName).append(";\n");

        outputBuilder.append(indent(1)).append("}");
        return outputBuilder.toString();
    }

    public static String generateToString(final Enum enumEntry) {
        if (isEmpty(enumEntry.getValues())) {
            LOG.warn("Empty enumValues list.");
            return "";
        }

        final int numEnumValues = enumEntry.getValues().size();
        final StringBuilder outputBuilder = new StringBuilder();
        for (int currentEnum = 0; currentEnum < numEnumValues; currentEnum++) {
            outputBuilder.append(indent(1));

            if (currentEnum > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumName = enumEntry.getValues().get(currentEnum);
            outputBuilder.append("if (input == ").append(currentEnumName).append(") {\n");
            final int prefix_length = enumEntry.getName().length() + 1; // to +1 to account for trailing underbar
            outputBuilder.append(indent(2)).append("return \"").append(currentEnumName.substring(prefix_length)).append("\";\n"); // to query param strip off ds3_type namespace prefix
        }

        outputBuilder.append(indent(1)).append("} else {\n");
        outputBuilder.append(indent(2)).append("return \"\";\n");
        outputBuilder.append(indent(1)).append("}\n");

        return outputBuilder.toString();
    }

}
