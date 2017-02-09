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

/**
 * Represents an element within a .net model that contains
 * a list of elements that have an encapsulating xml tag
 */
public class NullableEncapsulatedListElement  extends BaseNullableElement {

    /** The encapsulating xml tag for the list described by this element */
    private final String encapsulatingTag;

    public NullableEncapsulatedListElement(
            final String name,
            final String xmlTag,
            final String parserName,
            final String encapsulatingTag) {
        super(name, xmlTag, parserName);
        this.encapsulatingTag = encapsulatingTag;
    }

    /**
     * Gets the .net code for parsing this encapsulated list of elements
     */
    @Override
    public String printParseElement() {
        return getName() + " = ParseEncapsulatedList(element, \""
                + getXmlTag() + "\", \"" + encapsulatingTag + "\", " + getParserName() + ")";
    }
}
