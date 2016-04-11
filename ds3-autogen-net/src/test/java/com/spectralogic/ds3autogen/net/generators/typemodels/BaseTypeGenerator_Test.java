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

package com.spectralogic.ds3autogen.net.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.net.model.type.Element;
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.typemodels.BaseTypeGenerator.containsOptionalAnnotation;
import static com.spectralogic.ds3autogen.net.generators.typemodels.BaseTypeGenerator.getEnumConstantsList;
import static com.spectralogic.ds3autogen.net.generators.typemodels.BaseTypeGenerator.toElement;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BaseTypeGenerator_Test {

    final static BaseTypeGenerator generator = new BaseTypeGenerator();

    @Test
    public void toEnumConstants_NullList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstants_EmptyList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstants_FullList_Test() {
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(
                new Ds3EnumConstant("one", null),
                new Ds3EnumConstant("two", null),
                new Ds3EnumConstant("three", null));

        final ImmutableList<EnumConstant> result = generator.toEnumConstantsList(enumConstants);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("one"));
        assertThat(result.get(1).getName(), is("two"));
        assertThat(result.get(2).getName(), is("three"));
    }

    @Test
    public void toElementsList_NullList_Test() {
        final ImmutableList<Element> result = generator.toElementsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElementsList_EmptyList_Test() {
        final ImmutableList<Element> result = generator.toElementsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElementsList_FullList_Test() {
        final ImmutableList<Ds3Annotation> optionalAnnotation = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.bean.lang.Optional", null));

        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element("Element1", "SimpleType", null, false),
                new Ds3Element("Element2", "SimpleType", null, optionalAnnotation, false),
                new Ds3Element("Element3", "array", "ComponentType", false));

        final ImmutableList<Element> result = generator.toElementsList(ds3Elements);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("Element1"));
        assertThat(result.get(0).getType(), is("SimpleType"));
        assertThat(result.get(1).getName(), is("Element2"));
        assertThat(result.get(1).getType(), is("SimpleType?"));
        assertThat(result.get(2).getName(), is("Element3"));
        assertThat(result.get(2).getType(), is("IEnumerable<ComponentType>"));
    }

    @Test
    public void toElement_SimpleElement_Test() {
        final Ds3Element ds3Element = new Ds3Element(
                "TestElement",
                "com.spectralogic.test.TestType",
                null,
                false);

        final Element result = toElement(ds3Element);
        assertThat(result.getName(), is("TestElement"));
        assertThat(result.getType(), is("TestType"));
    }

    @Test
    public void toElement_OptionalElement_Test() {
        final Ds3Element ds3Element = new Ds3Element(
                "TestElement",
                "com.spectralogic.test.TestType",
                null,
                ImmutableList.of(
                        new Ds3Annotation("com.spectralogic.util.bean.lang.Optional", null)),
                false);

        final Element result = toElement(ds3Element);
        assertThat(result.getName(), is("TestElement"));
        assertThat(result.getType(), is("TestType?"));
    }

    @Test
    public void toElement_ComponentElement_Test() {
        final Ds3Element ds3Element = new Ds3Element(
                "TestElement",
                "array",
                "com.spectralogic.test.TestComponentType",
                false);

        final Element result = toElement(ds3Element);
        assertThat(result.getName(), is("TestElement"));
        assertThat(result.getType(), is("IEnumerable<TestComponentType>"));
    }

    @Test
    public void containsOptionalAnnotation_NullList_Test() {
        assertFalse(containsOptionalAnnotation(null));
    }

    @Test
    public void containsOptionalAnnotation_EmptyList_Test() {
        assertFalse(containsOptionalAnnotation(ImmutableList.of()));
    }

    @Test
    public void containsOptionalAnnotation_WithoutOptional_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.marshal.CustomMarshaledName", null),
                new Ds3Annotation("com.spectralogic.util.marshal.ExcludeFromMarshaler", null));

        assertFalse(containsOptionalAnnotation(annotations));
    }

    @Test
    public void containsOptionalAnnotation_WithOptional_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.marshal.CustomMarshaledName", null),
                new Ds3Annotation("com.spectralogic.util.marshal.ExcludeFromMarshaler", null),
                new Ds3Annotation("com.spectralogic.util.bean.lang.Optional", null));

        assertTrue(containsOptionalAnnotation(annotations));
    }

    @Test
    public void getEnumConstants_NullList_Test() {
        final ImmutableList<EnumConstant> result = getEnumConstantsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getEnumConstants_EmptyList_Test() {
        final ImmutableList<EnumConstant> result = getEnumConstantsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getEnumConstants_FullList_Test() {
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(
                new Ds3EnumConstant("one", null),
                new Ds3EnumConstant("two", null),
                new Ds3EnumConstant("three", null));

        final ImmutableList<EnumConstant> result = getEnumConstantsList(enumConstants);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("one"));
        assertThat(result.get(1).getName(), is("two"));
        assertThat(result.get(2).getName(), is("three"));
    }
}
