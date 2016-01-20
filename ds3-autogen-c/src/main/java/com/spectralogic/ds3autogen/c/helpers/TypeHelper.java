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
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.converters.ElementConverter;
import com.spectralogic.ds3autogen.c.models.Element;
import com.spectralogic.ds3autogen.c.models.Type;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

public final class TypeHelper {
    private static final Logger LOG = LoggerFactory.getLogger(TypeHelper.class);
    private TypeHelper() {}

    private final static TypeHelper typeHelper = new TypeHelper();

    public static TypeHelper getInstance() {
        return typeHelper;
    }

    public static String getNameUnderscores(final String name) {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name)));
    }

    public static String getDs3Type(final String name) {
        return "ds3_" + getNameUnderscores(name);
    }

    public static String getResponseTypeName(final String name) {
        return getDs3Type(name) + "_response";
    }

    public static String getParserFunctionName(final String name) {
        return "_parse_" + getResponseTypeName(name);
    }

    public static String getFreeFunctionName(final String name) {
        return getResponseTypeName(name) + "_free";
    }

    /*
    public static String getEnumValues(final ImmutableList<Ds3EnumConstant> enumConstants) {
        if (isEmpty(enumConstants)) {
            return "";
        }
        return enumConstants
                .stream()
                .map(i -> indent(1) + i.getName())
                .collect(Collectors.joining(",\n"));
    }
    */

    /*
    public static String getTypeElementsList(final ImmutableList<Element> elements) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final Element element : elements) {
            outputBuilder.append(indent(1)).
                    append(ElementHelper.getDs3TypeName(element)).
                    append(" ").
                    append(ElementHelper.getNameUnderscores(element.getName())).
                    append(";").
                    append("\n");
        }

        return outputBuilder.toString();
    }
    */

    /*
    public static String generateMatcher(final ImmutableList<Ds3EnumConstant> enumConstants) {
        final StringBuilder outputBuilder = new StringBuilder();
        final int numConstants = enumConstants.size();

        if (numConstants <= 0) {
            return "";
        }

        for (int currentIndex = 0; currentIndex < numConstants; currentIndex++) {
            outputBuilder.append(indent(1));

            if (currentIndex > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumName = enumConstants.get(currentIndex).getName();
            outputBuilder.append("if (xmlStrcmp(text, (const xmlChar*) \"").
                    append(currentEnumName).
                    append("\") == 0) {").append("\n");
            outputBuilder.append(indent(2)).append("return ").
                    append(currentEnumName).
                    append(";").append("\n");
        }

        final String enumName = enumConstants.get(0).getName();
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
    */

    public static String generateFreeTypeElementMembers(final ImmutableList<Element> elements) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final Element element : elements) {
            final String freeFunc = ElementHelper.getFreeFunction(element);
            if (freeFunc.length() == 0) continue;

            outputBuilder.append(indent(1)).
                    append(freeFunc).
                    append("(response_data->").
                    append(ElementHelper.getNameUnderscores(element.getName())).
                    append(");").
                    append("\n");
        }

        return outputBuilder.toString();
    }

    public static String generateResponseParser(final String typeName, final ImmutableList<Element> elements) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (int currentIndex = 0; currentIndex < elements.size(); currentIndex++) {
            outputBuilder.append(indent(1));

            if (currentIndex > 0) {
                outputBuilder.append(indent(2)).append("} else ");
            }

            final String currentElementName = elements.get(currentIndex).getName();

            outputBuilder.append("if (element_equal(child_node, \"").append(currentElementName).append("\")) {").append("\n");
            outputBuilder.append(indent(4)).
                    append(TypeHelper.getResponseTypeName(typeName)).
                    append("->").
                    append(Helper.camelToUnderscore(currentElementName)).
                    append(" = ").
                    append(ElementHelper.getParser(elements.get(currentIndex))).append("\n");
        }
        outputBuilder.append(indent(3)).append("} else {").append("\n");
        outputBuilder.append(indent(4)).
                append("ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);").append("\n");
        outputBuilder.append(indent(3)).append("}").append("\n");

        return outputBuilder.toString();
    }


    public static ImmutableList<Element> convertDs3Elements(final ImmutableList<Ds3Element> elements) {
        final ImmutableList.Builder<Element> builder = ImmutableList.builder();
        for (final Ds3Element currentElement : elements) {
            builder.add(ElementConverter.toElement(currentElement));
        }
        return builder.build();
    }

    public static boolean isPrimitiveType(final Type type) {
        for (final Element element : type.getElements()) {
            if (!Helper.isPrimitiveType(element.getType())) {
                return false;
            }
        }
        return true;
    }

    public static boolean containsExistingElements(final Type type, final ImmutableSet<String> existingElements) {
        for (final Element element : type.getElements()) {
            if (element.getType().equals("array")) {
                if (!existingElements.contains(element.getComponentType())) {
                    return false;
                }
            } else if (!existingElements.contains(element.getType())) {
                return false;
            }
        }
        return true;
    }
}
