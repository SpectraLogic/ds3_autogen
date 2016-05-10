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

package com.spectralogic.ds3autogen.net.utils;

import com.spectralogic.ds3autogen.net.generators.elementparsers.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.utils.NetNullableElementUtils.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NetNullableElementUtils_Test {

    @Test
    public void getParserName_Test() {
        assertThat(getParserName("type", false), is("ParseType"));
        assertThat(getParserName("Type", false), is("ParseType"));
        assertThat(getParserName("type", true), is("ParseNullableType"));
        assertThat(getParserName("Type", true), is("ParseNullableType"));

        assertThat(getParserName("ChecksumType.Type", false), is("ParseChecksumType"));
        assertThat(getParserName("ChecksumType.Type", true), is("ParseNullableChecksumType"));

        assertThat(getParserName("Object", false), is("ParseString"));
        assertThat(getParserName("Object", true), is("ParseNullableString"));
    }

    @Test
    public void toNullableAttributeElement_Nullable_Test() {
        final NullableElement element = toNullableAttributeElement("Name", "Type", true, "XmlTag");
        assertThat(element.printParseElement(), is("Name = ParseNullableType(element.AttributeTextOrNull(\"XmlTag\"))"));
    }

    @Test
    public void toNullableAttributeElement_Test() {
        final NullableElement element = toNullableAttributeElement("Name", "Type", false, "XmlTag");
        assertThat(element.printParseElement(), is("Name = ParseType(element.AttributeText(\"XmlTag\"))"));
    }

    @Test
    public void toNullableEncapsulatedListElement_Test() {
        final NullableElement element = toNullableEncapsulatedListElement("Name", "ComponentType", "XmlTag", "OuterTag");
        assertThat(element.printParseElement(), is("Name = ParseEncapsulatedList(element, \"XmlTag\", \"OuterTag\", ParseComponentType)"));
    }

    @Test
    public void toBaseNullableElement_Nullable_Test() {
        final NullableElement element = toBaseNullableElement("Name", "Type", true, "XmlTag");
        assertThat(element.printParseElement(), is("Name = ParseNullableType(element.Element(\"XmlTag\"))"));
    }

    @Test
    public void toBaseNullableElement_Test() {
        final NullableElement element = toBaseNullableElement("Name", "Type", false, "XmlTag");
        assertThat(element.printParseElement(), is("Name = ParseType(element.Element(\"XmlTag\"))"));
    }

    @Test
    public void toNullableListElement_Test() {
        final NullableElement element = toNullableListElement("Name", "ComponentType", "XmlTag");
        assertThat(element.printParseElement(), is("Name = element.Elements(\"XmlTag\").Select(ParseComponentType).ToList()"));
    }

    @Test
    public void createNullableElement_NullableEncapsulatedList_Test() {
        final NullableElement element = createNullableElement("Name", "Type", "ComponentType", false, "XmlTag", "OuterTag", false);
        assertThat(element, instanceOf(NullableEncapsulatedListElement.class));
    }

    @Test
    public void createNullableElement_NullableList_Test() {
        final NullableElement element = createNullableElement("Name", "Type", "ComponentType", false, "XmlTag", "", false);
        assertThat(element, instanceOf(NullableListElement.class));
    }

    @Test
    public void createNullableElement_NullableAttribute_Test() {
        final NullableElement element = createNullableElement("Name", "Type", "", false, "XmlTag", "", true);
        assertThat(element, instanceOf(NullableAttributeElement.class));
    }

    @Test
    public void createNullableElement_BaseNullableElement_Test() {
        final NullableElement element = createNullableElement("Name", "Type", "", false, "XmlTag", "", false);
        assertThat(element, instanceOf(BaseNullableElement.class));
    }
}
