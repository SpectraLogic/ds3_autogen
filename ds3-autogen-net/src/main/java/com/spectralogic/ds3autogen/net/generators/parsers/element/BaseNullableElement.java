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

package com.spectralogic.ds3autogen.net.generators.parsers.element;

import static com.spectralogic.ds3autogen.utils.Helper.capFirst;

/**
 * Represents an element within a .net model parser whose data
 * is within an xml tag
 */
public class BaseNullableElement implements NullableElement {

    private final String name;
    private final String xmlTag;

    /** The name of the model parser associated with this type */
    private final String parserName;

    public BaseNullableElement(
            final String name,
            final String xmlTag,
            final String parserName) {
        this.name = name;
        this.xmlTag = xmlTag;
        this.parserName = parserName;
    }

    public String getName() {
        return name;
    }

    public String getXmlTag() {
        return xmlTag;
    }

    public String getParserName() {
        return parserName;
    }

    /**
     * Gets the .net code for parsing this element
     */
    @Override
    public String printParseElement() {
        return name + " = " + parserName + "(element.Element(\"" + capFirst(xmlTag) + "\"))";
    }
}
