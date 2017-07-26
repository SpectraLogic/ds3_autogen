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

package com.spectralogic.ds3autogen.go.generators.type;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.go.models.type.StructElement;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseTypeGenerator_Test {

    private final BaseTypeGenerator generator = new BaseTypeGenerator();

    private static final Ds3Element elementWithWrapper = new Ds3Element(
            "ElementWithWrapper",
            "Type",
            "",
            ImmutableList.of(
                    new Ds3Annotation(
                            "com.spectralogic.util.marshal.CustomMarshaledName",
                            ImmutableList.of(
                                    new Ds3AnnotationElement("CollectionValue", "OuterTag", "java.lang.String"),
                                    new Ds3AnnotationElement("Value", "InnerTag", "java.lang.String")))),
            false
    );

    private static final Ds3Element elementAsAttribute = new Ds3Element(
            "ElementAsAttribute",
            "Type",
            "",
            ImmutableList.of(
                    new Ds3Annotation("com.spectralogic.util.marshal.MarshalXmlAsAttribute", null)),
            false
    );

    private static final Ds3Element simpleElement = new Ds3Element(
            "SimpleElement",
            "Type",
            "",
            ImmutableList.of(),
            false
    );

    @Test
    public void toEnumConstantsList_NullList_Test() {
        final ImmutableList<String> result = generator.toEnumConstantsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstantsList_EmptyList_Test() {
        final ImmutableList<String> result = generator.toEnumConstantsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstantsList_FullList_Test() {
        final ImmutableList<String> expectedEnums = ImmutableList.of("ONE", "TWO", "THREE");

        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(
                new Ds3EnumConstant("one", ImmutableList.of()),
                new Ds3EnumConstant("Two", ImmutableList.of()),
                new Ds3EnumConstant("THREE", ImmutableList.of())
        );

        final ImmutableList<String> result = generator.toEnumConstantsList(enumConstants);

        assertThat(result.size(), is(expectedEnums.size()));
        expectedEnums.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void toStructElementsList_NullList_Test() {
        final ImmutableList<StructElement> result = generator.toStructElementsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toStructElementsList_EmptyList_Test() {
        final ImmutableList<StructElement> result = generator.toStructElementsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toStructElementsList_FullList_Test() {
        final ImmutableList<StructElement> expectedElements = ImmutableList.of(
                new StructElement("ElementWithWrapper", "Type"),
                new StructElement("ElementAsAttribute", "Type"),
                new StructElement("SimpleElement", "Type")
        );

        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                elementWithWrapper,
                elementAsAttribute,
                simpleElement
        );

        final ImmutableList<StructElement> result = generator.toStructElementsList(elements);
        assertThat(result.size(), is(expectedElements.size()));

        expectedElements.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void toEnumPrefix_Test() {
        assertThat(generator.toEnumPrefix("testLowerCamelName"), is("TEST_LOWER_CAMEL_NAME_"));
        assertThat(generator.toEnumPrefix("TestUpperCamelName"), is("TEST_UPPER_CAMEL_NAME_"));
    }
}
