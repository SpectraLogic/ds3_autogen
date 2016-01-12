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

import java.text.ParseException;

public class TypeHelper {
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

    public static String getTypeElementsList(final Type type) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final Element element : type.getElements()) {
            outputBuilder.append(CHelper.indent(1)).
                    append(ElementHelper.getDs3Type(element)).
                    append(" ").
                    append(ElementHelper.getNameUnderscores(element.getName())).
                    append(";").
                    append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }

    public static String generateMatcher(final Type type) {
        final StringBuilder outputBuilder = new StringBuilder();
        final int numConstants = type.getEnumConstants().size();

        for (int currentIndex = 0; currentIndex < numConstants; currentIndex++) {
            outputBuilder.append(CHelper.indent(1));

            if (currentIndex > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumName = type.getEnumConstants().get(currentIndex).getName();
            outputBuilder.append("if (xmlStrcmp(text, (const xmlChar*) \"").append(currentEnumName).append("\") == 0) {").append(System.lineSeparator());
            outputBuilder.append(CHelper.indent(2)).append("return ").append(currentEnumName).append(";").append(System.lineSeparator());
        }

        final String enumName = type.getEnumConstants().get(0).getName();
        outputBuilder.append(CHelper.indent(1)).append("} else {").append(System.lineSeparator()); // Shouldn't need this else, since we are autogenerating from all possible values.
        outputBuilder.append(CHelper.indent(2)).append("ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown ").
                append(TypeHelper.getNameUnderscores(enumName)).
                append(" value of '%s'.  Returning ").
                append(enumName).
                append(" for safety.").append(System.lineSeparator());
        outputBuilder.append(CHelper.indent(2)).append("return ").
                append(enumName).
                append(";").append(System.lineSeparator()); // Special case? How do we determine default "safe" response enum?  Probably not always element 0
        outputBuilder.append(CHelper.indent(1)).append("}").append(System.lineSeparator());
        return outputBuilder.toString();
    }

    public static String generateFreeTypeElementMembers(final Type type) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final Element element : type.getElements()) {
            final String freeFunc = ElementHelper.getFreeFunction(element);
            if (freeFunc.length() == 0) continue;

            outputBuilder.append(CHelper.indent(1)).
                    append(freeFunc).
                    append("(response_data->").
                    append(ElementHelper.getNameUnderscores(element.getName())).
                    append(");").
                    append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }

    public static String generateResponseParser(final Type type) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (int currentIndex = 0; currentIndex < type.getElements().size(); currentIndex++) {
            outputBuilder.append(CHelper.indent(1));

            if (currentIndex > 0) {
                outputBuilder.append(CHelper.indent(2)).append("} else ");
            }

            final String currentElementName = type.getElements().get(currentIndex).getName();

            outputBuilder.append("if (element_equal(child_node, \"").append(currentElementName).append("\")) {").append(System.lineSeparator());
            outputBuilder.append(CHelper.indent(4)).
                    append(TypeHelper.getResponseTypeName(currentElementName)).
                    append("->").
                    append(Helper.camelToUnderscore(currentElementName)).
                    append(" = ").
                    append(ElementHelper.getParser(type.getElements().get(currentIndex))).append(System.lineSeparator());
        }
        outputBuilder.append(CHelper.indent(3)).append("} else {").append(System.lineSeparator());
        outputBuilder.append(CHelper.indent(4)).
                append("ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);").append(System.lineSeparator());
        outputBuilder.append(CHelper.indent(3)).append("}").append(System.lineSeparator());

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
            if (Helper.isPrimitiveType(element.getType())) {
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
