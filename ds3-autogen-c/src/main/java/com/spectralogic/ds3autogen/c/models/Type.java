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

package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;

import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.helpers.CHelper;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public class Type {
    private static final Logger LOG = LoggerFactory.getLogger(Type.class);

    private final String name;
    private final ImmutableList<Ds3EnumConstant> enumConstants;
    private final ImmutableList<Ds3Element> elements;


    public Type(
            final String name,
            final ImmutableList<Ds3EnumConstant> enumConstants,
            final ImmutableList<Ds3Element> elements) {
        this.name = name;
        this.enumConstants = enumConstants;
        this.elements = elements;
    }

    public String getName() {
        return this.name;
    }

    public ImmutableList<Ds3EnumConstant> getEnumConstants() {
        return enumConstants;
    }

    public String getEnumConstantsList() {
        return CHelper.getEnumValues(enumConstants);
    }

    public String getUnqualifiedName() {
        return Helper.unqualifiedName(name);
    }

    public String getNameUnderscores() {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(getUnqualifiedName()));
    }

    public String getResponseTypeName() {
        final StringBuilder responseName = new StringBuilder();
        responseName.append("ds3_");
        responseName.append(getNameUnderscores());
        responseName.append("_response");
        return responseName.toString();
    }

    public String generateMatcher() {
        final StringBuilder outputBuilder = new StringBuilder();
        final int numConstants = this.enumConstants.size();

        for (int currentIndex = 0; currentIndex < numConstants; currentIndex++) {
            outputBuilder.append(CHelper.indent(1));

            if (currentIndex > 0) {
                outputBuilder.append("} else ");
            }

            final String currentEnumString = this.enumConstants.get(currentIndex).getName();
            outputBuilder.append("if (xmlStrcmp(text, (const xmlChar*) \"").append(currentEnumString).append("\") == 0) {").append(System.lineSeparator());
            outputBuilder.append(CHelper.indent(2)).append("return ").append(currentEnumString).append(";").append(System.lineSeparator());
        }

        outputBuilder.append(CHelper.indent(1)).append("} else {").append(System.lineSeparator()); // Shouldn't need this else, since we are autogenerating from all possible values.
        outputBuilder.append(CHelper.indent(2)).append("ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown ").append(getNameUnderscores()).append(" value of '%s'.  Returning ").append(getEnumConstants().get(0).getName()).append(" for safety.").append(System.lineSeparator());
        outputBuilder.append(CHelper.indent(2)).append("return ").append(getEnumConstants().get(0).getName()).append(";").append(System.lineSeparator()); // Special case? How do we determine default "safe" response enum?  Probably not always element 0
        outputBuilder.append(CHelper.indent(1)).append("}").append(System.lineSeparator());
        return outputBuilder.toString();
    }

    public String getTypeElementsList() throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final Ds3Element element : this.elements) {
            outputBuilder.append(CHelper.indent(1))
                    .append(CHelper.elementTypeToString(element))
                    .append(" ")
                    .append(Helper.camelToUnderscore(Helper.unqualifiedName(element.getName())))
                    .append(";")
                    .append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }

    public String generateFreeTypeElementMembers() throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final Ds3Element element : this.elements) {
            final String freeFunc = CHelper.elementTypeToFreeFunctionString(element);
            if (freeFunc.length() == 0) continue;

            outputBuilder.append(CHelper.indent(1))
                    .append(freeFunc).append("(response_data->")
                    .append(Helper.camelToUnderscore(Helper.unqualifiedName(element.getName()))).append(");")
                    .append(System.lineSeparator());
        }

        return outputBuilder.toString();
    }
}
