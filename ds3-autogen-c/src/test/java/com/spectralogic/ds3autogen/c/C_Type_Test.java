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

package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.c.helpers.C_TypeHelper;
import com.spectralogic.ds3autogen.c.models.C_Type;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class C_Type_Test {
    @Test
    public void testStructTypeDs3ResponseToString() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("BuildInformation", "com.spectralogic.s3.common.platform.lang.BuildInformation", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("ds3_build_information_response"));
        assertThat(c_type.toString(), is("ds3_build_information_response*"));
    }
    @Test
    public void testStructMemberTypeStringToString() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("stringElement", "java.lang.String", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("ds3_str"));
        assertThat(c_type.toString(), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeDateToString() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("dateElement", "java.util.Date", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("ds3_str"));
        assertThat(c_type.toString(), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeUuidToString() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("uuidElement", "java.util.UUID", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("ds3_str"));
        assertThat(c_type.toString(), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeDoubleToString() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("doubleElement", "double", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("float"));
        assertThat(c_type.toString(), is("float"));
    }
    @Test
    public void testStructMemberTypeLongToString1() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("longElement1", "java.lang.Long", null, true);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("uint64_t"));
        assertThat(c_type.toString(), is("uint64_t"));
    }
    @Test
    public void testStructTypeLongToString2() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("longElement2", "long", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("uint64_t"));
        assertThat(c_type.toString(), is("uint64_t"));
    }
    @Test
    public void testStructTypeIntToString1() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("intStruct1", "int", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("int"));
        assertThat(c_type.toString(), is("int"));
    }
    @Test
    public void testStructTypeIntToString2() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("intStruct2", "java.lang.Integer", null, true);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("int"));
        assertThat(c_type.toString(), is("int"));
    }
    @Test
    public void testStructTypeBooleanToString2() throws java.text.ParseException {
        final Ds3Element testElement = new Ds3Element("booleanStruct", "boolean", null, false);
        final C_Type c_type = C_TypeHelper.convertDs3ElementType(testElement, ImmutableSet.of());
        assertThat(c_type.getTypeName(), is("ds3_bool"));
        assertThat(c_type.toString(), is("ds3_bool"));
    }
}
