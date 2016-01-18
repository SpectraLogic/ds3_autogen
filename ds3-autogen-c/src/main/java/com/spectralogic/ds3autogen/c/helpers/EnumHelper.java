package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.indent;

public class EnumHelper {
    private static final Logger LOG = LoggerFactory.getLogger(EnumHelper.class);
    private EnumHelper() {}

    private final static EnumHelper enumHelper = new EnumHelper();

    public static EnumHelper getInstance() {
        return enumHelper;
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

    public static ImmutableList<String> convertDs3EnumConstants(final ImmutableList<Ds3EnumConstant> enums) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Ds3EnumConstant currentEnum : enums) {
            builder.add(currentEnum.getName());
        }
        return builder.build();
    }

    public static String generateMatcher(final ImmutableList<String> enumValues) {
        final StringBuilder outputBuilder = new StringBuilder();
        final int numConstants = enumValues.size();

        if (numConstants <= 0) {
            return "";
        }

        for (int currentIndex = 0; currentIndex < numConstants; currentIndex++) {
            outputBuilder.append(indent(1));

            if (currentIndex > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumName = enumValues.get(currentIndex);
            outputBuilder.append("if (xmlStrcmp(text, (const xmlChar*) \"").
                    append(currentEnumName).
                    append("\") == 0) {").append("\n");
            outputBuilder.append(indent(2)).append("return ").
                    append(currentEnumName).
                    append(";").append("\n");
        }

        final String enumName = enumValues.get(0);
        outputBuilder.append(indent(1)).append("} else {").append("\n"); // Shouldn't need this else, since we are autogenerating from all possible values.
        outputBuilder.append(indent(2)).append("ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown value of '%s'.  Returning ").
                append(enumName).
                append(" for safety.").append("\n");
        outputBuilder.append(indent(2)).append("return ").
                append(enumName).
                append(";").append("\n"); // Special case? How do we determine default "safe" response enum?  Probably not always element 0
        outputBuilder.append(indent(1)).append("}").append("\n");
        return outputBuilder.toString();
    }
}
