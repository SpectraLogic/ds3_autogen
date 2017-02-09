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

package com.spectralogic.ds3autogen.java.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.typemodels.BaseTypeGenerator.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseTypeGenerator_Test {

    private static final BaseTypeGenerator generator = new BaseTypeGenerator();

    @Test
    public void toElement_Test() {
        final Ds3Element ds3Element = new Ds3Element("Name", "Type", "ComponentType", false);
        final Element result = generator.toElement(ds3Element);
        assertThat(result.getName(), is("Name"));
        assertThat(result.getType(), is("Type"));
        assertThat(result.getComponentType(), is("ComponentType"));
    }

    @Test
    public void toElementList_NullList_Test() {
        final ImmutableList<Element> result = generator.toElementList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElementList_EmptyList_Test() {
        final ImmutableList<Element> result = generator.toElementList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElementList_FullList_Test() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element("Name1", "Type1", "ComponentType1", false),
                new Ds3Element("Name2", "Type2", "ComponentType2", false)
        );

        final ImmutableList<Element> result = generator.toElementList(ds3Elements);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("Name1"));
        assertThat(result.get(1).getName(), is("Name2"));
    }

    @Test
    public void toEnumConstant_Test() {
        final Ds3EnumConstant ds3EnumConstant = new Ds3EnumConstant("Name", null);
        final EnumConstant result = toEnumConstant(ds3EnumConstant);
        assertThat(result.getName(), is("Name"));
    }

    @Test
    public void toEnumConstantList_NullList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstantList_EmptyList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstantList_FullList_Test() {
        final ImmutableList<Ds3EnumConstant> ds3EnumConstants = ImmutableList.of(
                new Ds3EnumConstant("Name1", null),
                new Ds3EnumConstant("Name2", null));

        final ImmutableList<EnumConstant> result = generator.toEnumConstantList(ds3EnumConstants);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("Name1"));
        assertThat(result.get(1).getName(), is("Name2"));
    }

    @Test
    public void getImportsFromDs3Elements_NullList_Test() {
        final ImmutableList<String> result = getImportsFromDs3Elements(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getImportsFromDs3Elements_EmptyList_Test() {
        final ImmutableList<String> result = getImportsFromDs3Elements(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getImportsFromDs3Elements_FullList_Test() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element("Name1", "com.spectralogic.test.Type1", "ComponentType1", false),
                new Ds3Element("Name2", "Type2", "com.spectralogic.test.ComponentType2", false),
                new Ds3Element("Name3", "Type3", null, false));

        final ImmutableList<String> result = getImportsFromDs3Elements(ds3Elements);
        assertThat(result.size(), is(3));
        assertTrue(result.contains("java.util.List"));
        assertTrue(result.contains("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper"));
        assertTrue(result.contains("java.util.ArrayList"));
    }

    @Test
    public void getAllImports_EnumType_Test() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element("ElementName1", "com.spectralogic.test.ElementType1", "ElementComponentType1", false),
                new Ds3Element("ElementName2", "ElementType2", "com.spectralogic.test.ElementComponentType2", false),
                new Ds3Element("ElementName3", "ElementType3", null, false));

        final ImmutableList<Ds3EnumConstant> ds3EnumConstants = ImmutableList.of(
                new Ds3EnumConstant("EnumConstName1", null),
                new Ds3EnumConstant("EnumConstName2", null));

        final Ds3Type enumType = new Ds3Type(
                "TypeName",
                null,
                ds3Elements,
                ds3EnumConstants);

        final ImmutableList<String> result = generator.getAllImports(enumType);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_NonEnumType_Test() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element("ElementName1", "com.spectralogic.test.ElementType1", "ElementComponentType1", false),
                new Ds3Element("ElementName2", "ElementType2", "com.spectralogic.test.ElementComponentType2", false),
                new Ds3Element("ElementName3", "ElementType3", null, false));

        final Ds3Type ds3Type = new Ds3Type(
                "TypeName",
                null,
                ds3Elements,
                null);

        final ImmutableList<String> result = generator.getAllImports(ds3Type);
        assertThat(result.size(), is(3));
        assertTrue(result.contains("java.util.List"));
        assertTrue(result.contains("java.util.ArrayList"));
        assertTrue(result.contains("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper"));
    }

    @Test
    public void toNameToMarshal_NullValue_Test() {
        final Ds3Type type = new Ds3Type("Name", null, null, null);
        final String result = generator.toNameToMarshal(type);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void toNameToMarshal_EmptyValue_Test() {
        final Ds3Type type = new Ds3Type("Name", "", null, null);
        final String result = generator.toNameToMarshal(type);
        assertThat(result, is(""));
    }

    @Test
    public void toNameToMarshal_Value_Test() {
        final Ds3Type type = new Ds3Type("Name", "Objects", null, null);
        final String result = generator.toNameToMarshal(type);
        assertThat(result, is("Objects"));
    }
}
