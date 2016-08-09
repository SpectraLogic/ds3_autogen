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

package com.spectralogic.ds3autogen.net.generators.parsers.type;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.net.generators.parsers.element.BaseNullableElement;
import com.spectralogic.ds3autogen.net.generators.parsers.element.NullableElement;
import com.spectralogic.ds3autogen.net.generators.parsers.element.NullableListElement;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.parsers.type.BaseTypeParserGenerator.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseTypeParserGenerator_Test {

    private static final BaseTypeParserGenerator generator = new BaseTypeParserGenerator();

    @Test
    public void toNullableElement_Test() {
        final Ds3Element element = new Ds3Element("Name", "int", "", false);
        final NullableElement result = toNullableElement(element, false);
        assertThat(result, instanceOf(BaseNullableElement.class));
    }

    @Test
    public void toNullableElementsList_NullList_Test() {
        final ImmutableList<NullableElement> result = generator.toNullableElementsList(null, false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toNullableElementsList_EmptyList_Test() {
        final ImmutableList<NullableElement> result = generator.toNullableElementsList(ImmutableList.of(), false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toNullableElementsList_FullList_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Name1", "int", "", false),
                new Ds3Element("Name2", "Type", "Component", false));

        final ImmutableList<NullableElement> result = generator.toNullableElementsList(elements, false);
        assertThat(result.size(), is(2));
        assertThat(result.get(0), instanceOf(BaseNullableElement.class));
        assertThat(result.get(1), instanceOf(NullableListElement.class));
    }

    @Test
    public void toParseElements_NullList_Test() {
        final ImmutableList<String> result = generator.toParseElements(null, false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toParseElements_EmptyList_Test() {
        final ImmutableList<String> result = generator.toParseElements(ImmutableList.of(), false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toParseElements_FullList_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Name1", "int", "", false),
                new Ds3Element("Name2", "Type", "Component", false));

        final ImmutableList<String> result = generator.toParseElements(elements, false);
        assertThat(result.size(), is(2));
        assertThat(result, hasItem("Name1 = ParseInt(element.Element(\"Name1\"))"));
        assertThat(result, hasItem("Name2 = element.Elements(\"Name2\").Select(ParseComponent).ToList()"));
    }

    @Test
    public void toNullableElementName_Test() {
        assertThat(toNullableElementName("Objects", true), is("ObjectsList"));
        assertThat(toNullableElementName("ObjectsApiBean", true), is("ObjectsApiBean"));
        assertThat(toNullableElementName("Objects", false), is("Objects"));
        assertThat(toNullableElementName("ObjectsApiBean", false), is("ObjectsApiBean"));
    }
}
