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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.converters.UpdateElementsConverter.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3TypeTestData;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class UpdateElementsConverter_Test {

    private static Ds3Annotation createExcludeAlways() {
        return new Ds3Annotation(
                "com.spectralogic.util.marshal.ExcludeFromMarshaler",
                ImmutableList.of(createAnnotationElementAlways()));
    }

    private static Ds3Annotation createExcludeWhenNull() {
        return new Ds3Annotation(
                "com.spectralogic.util.marshal.ExcludeFromMarshaler",
                ImmutableList.of(createAnnotationElementWhenNull()));
    }

    private static Ds3Annotation createCustomMarshaledName() {
        return new Ds3Annotation("com.spectralogic.util.marshal.CustomMarshaledName", null);
    }

    private static Ds3AnnotationElement createAnnotationElementAlways() {
        return new Ds3AnnotationElement(
                "Value",
                "ALWAYS",
                "com.spectralogic.util.marshal.ExcludeFromMarshaler$When");
    }

    private static Ds3AnnotationElement createAnnotationElementWhenNull() {
        return new Ds3AnnotationElement(
                "Value",
                "VALUE_IS_NULL",
                "com.spectralogic.util.marshal.ExcludeFromMarshaler$When");
    }

    private static ImmutableList<Ds3Element> createElementList() {
        return ImmutableList.of(
                new Ds3Element(
                        "CustomName", "Type", null, ImmutableList.of(createCustomMarshaledName()), false),
                new Ds3Element(
                        "ExcludeNull", "Type", null, ImmutableList.of(createExcludeWhenNull()), false),
                new Ds3Element(
                        "ExcludeAlways", "Type", null, ImmutableList.of(createExcludeAlways()), false));
    }

    private static void verifyElements(final ImmutableList<Ds3Element> elements) {
        assertThat(elements.size(), is(2));
        assertThat(elements.get(0).getName(), is("CustomName"));
        assertThat(elements.get(0).isNullable(), is(false));
        assertThat(elements.get(1).getName(), is("ExcludeNull"));
        assertThat(elements.get(1).isNullable(), is(true));
    }

    @Test
    public void hasExcludeValue_Test() {
        assertThat(hasExcludeValue(null), is(false));
        assertThat(hasExcludeValue(ImmutableList.of()), is(false));
        assertThat(hasExcludeValue(ImmutableList.of(createCustomMarshaledName())), is(false));
        assertThat(hasExcludeValue(ImmutableList.of(createExcludeAlways())), is(true));
        assertThat(hasExcludeValue(ImmutableList.of(createExcludeWhenNull())), is(true));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getExcludeValueFromElementsList_NullList_Test() {
        getExcludeValueFromElementsList(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getExcludeValueFromElementsList_EmptyList_Test() {
        getExcludeValueFromElementsList(ImmutableList.of());
    }

    @Test (expected = IllegalArgumentException.class)
    public void getExcludeValueFromElementsList_NoExcludeValue_Test() {
        final ImmutableList<Ds3AnnotationElement> elements = ImmutableList.of(
                new Ds3AnnotationElement("Value", "1", "javalang.String"));
        getExcludeValueFromElementsList(elements);
    }

    @Test
    public void getExcludeValueFromElementsList_Always_Test() {
        final ImmutableList<Ds3AnnotationElement> elements = ImmutableList.of(
                createAnnotationElementAlways());
        assertThat(getExcludeValueFromElementsList(elements), is(ExcludeType.ALWAYS));
    }

    @Test
    public void getExcludeValueFromElementsList_WhenNull_Test() {
        final ImmutableList<Ds3AnnotationElement> elements = ImmutableList.of(
                createAnnotationElementWhenNull());
        assertThat(getExcludeValueFromElementsList(elements), is(ExcludeType.VALUE_IS_NULL));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getExcludeValueFromAnnotationsList_NullList_Test() {
        getExcludeValueFromAnnotationsList(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getExcludeValueFromAnnotationsList_EmptyList_Test() {
        getExcludeValueFromAnnotationsList(ImmutableList.of());
    }

    @Test (expected = IllegalArgumentException.class)
    public void getExcludeValueFromAnnotationsList_NoExclude_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                createCustomMarshaledName());
        getExcludeValueFromAnnotationsList(annotations);
    }

    @Test
    public void getExcludeValueFromAnnotationsList_FullList_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                createExcludeWhenNull());
        assertThat(getExcludeValueFromAnnotationsList(annotations), is(ExcludeType.VALUE_IS_NULL));
    }

    @Test
    public void updateElement_NoChange_Test() {
        final Ds3Element element = new Ds3Element(
                "Name", "Type", null, ImmutableList.of(createCustomMarshaledName()), false);
        final Ds3Element result = updateElement(element);
        assertThat(result, is(notNullValue()));
        assertThat(result.isNullable(), is(false));
    }

    @Test
    public void updateElement_WhenNull_Test() {
        final Ds3Element element = new Ds3Element(
                "Name", "Type", null, ImmutableList.of(createExcludeWhenNull()), false);
        final Ds3Element result = updateElement(element);
        assertThat(result, is(notNullValue()));
        assertThat(result.isNullable(), is(true));
    }

    @Test
    public void updateElement_Always_Test() {
        final Ds3Element element = new Ds3Element(
                "Name", "Type", null, ImmutableList.of(createExcludeAlways()), false);
        assertThat(updateElement(element), is(nullValue()));
    }

    @Test
    public void updateAllElements_NullList_Test() {
        final ImmutableList<Ds3Element> result = updateAllElements(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void updateAllElements_EmptyList_Test() {
        final ImmutableList<Ds3Element> result = updateAllElements(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void updateAllElements_FullList_Test() {
        final ImmutableList<Ds3Element> result = updateAllElements(createElementList());
        verifyElements(result);
    }

    @Test
    public void updateElementsInType_Test() {
        final Ds3Type type = createDs3TypeTestData("com.test.Type", createElementList());
        final Ds3Type result = updateElementsInType(type);
        verifyElements(result.getElements());
    }

    @Test
    public void updateElementsInTypeMap_NullMap_Test() {
        final ImmutableMap<String, Ds3Type> result = updateElementsInTypeMap(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void updateElementsInTypeMap_EmptyMap_Test() {
        final ImmutableMap<String, Ds3Type> result = updateElementsInTypeMap(ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void updateElementsInTypeMap_FullMap_Test() {
        final Ds3Type type = createDs3TypeTestData("com.test.Type", createElementList());
        final ImmutableMap<String, Ds3Type> typeMap = ImmutableMap.of(
                type.getName(), type);

        final ImmutableMap<String, Ds3Type> result = updateElementsInTypeMap(typeMap);
        assertThat(result.size(), is(1));
        verifyElements(result.get(type.getName()).getElements());
    }

    @Test
    public void updateElementsInSpec_Test() {
        final Ds3Type type = createDs3TypeTestData("com.test.Type", createElementList());
        final Ds3ApiSpec spec = new Ds3ApiSpec(null, ImmutableMap.of(type.getName(), type));

        final Ds3ApiSpec result = updateElementsInSpec(spec);
        verifyElements(result.getTypes().get(type.getName()).getElements());
    }
}
