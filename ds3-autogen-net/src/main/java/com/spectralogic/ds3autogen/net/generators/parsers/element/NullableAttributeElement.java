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

package com.spectralogic.ds3autogen.net.generators.parsers.element;

/**
 * Represents an element within a .net model parser whose data is within
 * an attribute of the current xml tag
 */
public class NullableAttributeElement extends BaseNullableElement {

    private final String parseAttributeFunc;

    public NullableAttributeElement(
            final String name,
            final String xmlTag,
            final String parserName,
            final String parseAttributeFunc) {
        super(name, xmlTag, parserName);
        this.parseAttributeFunc = parseAttributeFunc;
    }

    /**
     * Gets the .net code for parsing this element from an element's attribute
     */
    @Override
    public String printParseElement() {
        return getName() + " = " + getParserName() + "(element." + parseAttributeFunc + "(\"" + getXmlTag() + "\"))";
    }
}
