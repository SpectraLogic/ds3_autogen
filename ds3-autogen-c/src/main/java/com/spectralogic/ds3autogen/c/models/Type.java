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

import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.helpers.CHelper;
import com.spectralogic.ds3autogen.utils.Helper;

public class Type {
    private final String name;
    private final ImmutableList<Ds3EnumConstant> enumConstants;
    private final Helper helper;
    private final CHelper cHelper;

    public Type(
            final String name,
            final ImmutableList<Ds3EnumConstant> enumConstants) {
        this.name = name;
        this.enumConstants = enumConstants;
        this.helper = Helper.getInstance();
        this.cHelper = CHelper.getInstance();
    }

    public ImmutableList<Ds3EnumConstant> getEnumConstants() {
        return enumConstants;
    }

    public String getEnumConstantsList() {
        return cHelper.getEnumValues(enumConstants);
    }

    public String getUnqualifiedName() {
        return helper.unqualifiedName(name);
    }

    public String getNameUnderscores() {
        return helper.camelToUnderscore(getUnqualifiedName());
    }

    public String generateMatcher() {
        String output = new String();
        final int numConstants = this.enumConstants.size();
        for (int currentIndex = 0; currentIndex < numConstants; currentIndex++) {
            output += "    ";

            if (currentIndex > 0) {
                output += "} else ";
            }

            final String currentEnumString = this.enumConstants.get(currentIndex).getName();
            output += "if (xmlStrcmp(text, (const xmlChar*) \"" + currentEnumString + "\") == 0) {" + System.lineSeparator();
            output += "        return " + currentEnumString + ";" + System.lineSeparator();
        }

        output += "    } else {" + System.lineSeparator(); // Shouldn't need this else, since we are autogenerating from all possible values.
        output += "        ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown " + getNameUnderscores() + " value of '%s'.  Returning " + getEnumConstants().get(0).getName() + " for safety." + System.lineSeparator();
        output += "        " + "return " + getEnumConstants().get(0).getName() + ";" + System.lineSeparator(); // Special case? How do we determine default "safe" response enum?  Probably not always element 0
        output += "    }" + System.lineSeparator();
        return output;
    }
}
