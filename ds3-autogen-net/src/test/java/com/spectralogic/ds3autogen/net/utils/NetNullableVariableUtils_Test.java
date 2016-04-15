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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.utils.NetNullableVariableUtils.createNullableVariable;
import static com.spectralogic.ds3autogen.net.utils.NetNullableVariableUtils.isEnumType;
import static com.spectralogic.ds3autogen.net.utils.NetNullableVariableUtils.isPrimitive;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class NetNullableVariableUtils_Test {

    /**
     * Creates a type map with testing values
     */
    private static ImmutableMap<String, Ds3Type> getTestTypeMap() {
        final Ds3Type enumType = new Ds3Type(
                "TestEnumType",
                "",
                ImmutableList.of(),
                ImmutableList.of(
                        new Ds3EnumConstant("ONE", null)));

        final Ds3Type elementType = new Ds3Type(
                "TestElementType",
                ImmutableList.of(
                        new Ds3Element("Element", "int", null, false)));

        return ImmutableMap.of(
                enumType.getName(), enumType,
                elementType.getName(), elementType);
    }

    @Test
    public void createNullableVariable_Primitive_Test() {
        final NetNullableVariable var = createNullableVariable("Name", "java.lang.Integer", false, null);
        assertThat(var.getNetType(), is("int"));
    }

    @Test
    public void createNullableVariable_Enum_Test() {
        final NetNullableVariable var = createNullableVariable("Name", "TestEnumType", false, getTestTypeMap());
        assertThat(var.getNetType(), is("TestEnumType"));
    }

    @Test
    public void createNullableVariable_SpectraType_Test() {
        final NetNullableVariable var = createNullableVariable("Name", "TestElementType", false, getTestTypeMap());
        assertThat(var.getNetType(), is("TestElementType"));
    }

    @Test
    public void createNullableVariable_NullablePrimitive_Test() {
        final NetNullableVariable var = createNullableVariable("Name", "java.lang.Integer", true, null);
        assertThat(var.getNetType(), is("int?"));
    }

    @Test
    public void createNullableVariable_NullableEnum_Test() {
        final NetNullableVariable var = createNullableVariable("Name", "TestEnumType", true, getTestTypeMap());
        assertThat(var.getNetType(), is("TestEnumType?"));
    }

    @Test
    public void createNullableVariable_NullableSpectraType_Test() {
        final NetNullableVariable var = createNullableVariable("Name", "TestElementType", true, getTestTypeMap());
        assertThat(var.getNetType(), is("TestElementType"));
    }

    @Test
    public void isEnumType_NullValues_Test() {
        assertFalse(isEnumType(null, null));
        assertFalse(isEnumType(null, getTestTypeMap()));
        assertFalse(isEnumType("TypeName", null));
    }

    @Test
    public void isEnumType_EmptyValues_Test() {
        assertFalse(isEnumType("", ImmutableMap.of()));
        assertFalse(isEnumType("", getTestTypeMap()));
        assertFalse(isEnumType("TypeName", ImmutableMap.of()));
    }

    @Test
    public void isEnumType_Test() {
        final ImmutableMap<String, Ds3Type> typeMap = getTestTypeMap();
        assertTrue(isEnumType("TestEnumType", typeMap));

        assertFalse(isEnumType("java.lang.Integer", typeMap));
        assertFalse(isEnumType("TypeName", typeMap));
        assertFalse(isEnumType("TestElementType", typeMap));
    }

    @Test
    public void isPrimitive_Test() {
        assertTrue(isPrimitive("int"));
        assertTrue(isPrimitive("java.lang.Integer"));
        assertTrue(isPrimitive("boolean"));
        assertTrue(isPrimitive("java.lang.Boolean"));
        assertTrue(isPrimitive("long"));
        assertTrue(isPrimitive("java.lang.Long"));
        assertTrue(isPrimitive("double"));
        assertTrue(isPrimitive("java.lang.Double"));
        assertTrue(isPrimitive("java.util.Date"));

        assertFalse(isPrimitive(null));
        assertFalse(isPrimitive(""));
        assertFalse(isPrimitive("java.lang.String"));
        assertFalse(isPrimitive("com.test.SpectraType"));
        assertFalse(isPrimitive("SpectraType"));
    }
}
