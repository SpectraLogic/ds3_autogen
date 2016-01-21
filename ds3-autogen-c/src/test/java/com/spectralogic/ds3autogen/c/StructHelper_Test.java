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

package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StructHelper_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    @Test
    public void testStructMemberTypeStringToString() throws ParseException {
        final Ds3Element testElement = new Ds3Element("stringElement", "java.lang.String", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeDateToString() throws ParseException {
        final Ds3Element testElement = new Ds3Element("dateElement", "java.util.Date", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeUuidToString() throws ParseException {
        final Ds3Element testElement = new Ds3Element("uuidElement", "java.util.UUID", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("ds3_str*"));
    }
    @Test
    public void testStructMemberTypeDoubleToString() throws ParseException {
        final Ds3Element testElement = new Ds3Element("doubleElement", "double", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("double"));
    }
    @Test
    public void testStructMemberTypeLongToString1() throws ParseException {
        final Ds3Element testElement = new Ds3Element("longElement1", "java.lang.Long", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("uint64_t"));
    }
    @Test
    public void testStructTypeLongToString2() throws ParseException {
        final Ds3Element testElement = new Ds3Element("longElement2", "long", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("uint64_t"));
    }
    @Test
    public void testStructTypeIntToString1() throws ParseException {
        final Ds3Element testElement = new Ds3Element("intStruct1", "int", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("int"));
    }
    @Test
    public void testStructTypeIntToString2() throws ParseException {
        final Ds3Element testElement = new Ds3Element("intStruct2", "java.lang.Integer", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("int"));
    }
    @Test
    public void testStructTypeBooleanToString2() throws ParseException {
        final Ds3Element testElement = new Ds3Element("booleanStruct", "boolean", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("ds3_bool"));
    }
    @Test
    public void testStructTypeDs3ResponseToString2() throws ParseException {
        final Ds3Element testElement = new Ds3Element("BuildInformation", "com.spectralogic.s3.common.platform.lang.BuildInformation", null);
        assertThat(StructHelper.convertDs3ElementType(testElement), is("ds3_build_information_response*"));
    }

    @Test
    public void testTypeIsPrimitive() throws ParseException {
        final StructMember testStruct1 = new StructMember("int", "intMember");
        final StructMember testStruct2 = new StructMember("ds3_bool", "boolMember");
        final ImmutableList.Builder<StructMember> structMemberBuilder = ImmutableList.builder();
        structMemberBuilder.add(testStruct1);
        structMemberBuilder.add(testStruct2);
        final Struct testStruct = new Struct("testStruct", structMemberBuilder.build());
        assertTrue(StructHelper.isPrimitive(testStruct));
    }
    @Test
    public void testTypeIsNotPrimitive() throws ParseException {
        final StructMember testStruct1 = new StructMember("ds3_bool", "boolMember");
        final StructMember testStruct2 = new StructMember("ds3_user_api_bean_response", "beanMember");
        final ImmutableList.Builder<StructMember> structMemberBuilder = ImmutableList.builder();
        structMemberBuilder.add(testStruct1);
        structMemberBuilder.add(testStruct2);
        final Struct testStruct = new Struct("testStruct", structMemberBuilder.build());
        assertFalse(StructHelper.isPrimitive(testStruct));
    }

    @Test
    public void testGenerateStructMembers() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("boolElement", "boolean", null);
        final Ds3Element testElement2 = new Ds3Element("beanElement", "com.spectralogic.s3.server.domain.UserApiBean", null);
        final ImmutableList.Builder<Ds3Element> ds3ElementBuilder = ImmutableList.builder();
        ds3ElementBuilder.add(testElement1);
        ds3ElementBuilder.add(testElement2);
        final ImmutableList<Ds3Element> elementsList = ds3ElementBuilder.build();

        final Struct testStruct = new Struct("testStruct", StructHelper.convertDs3Elements(elementsList));
        final String output = StructHelper.generateStructMembers(testStruct.getVariables());
        assertTrue(output.contains("ds3_bool bool_element;"));
        assertTrue(output.contains("ds3_user_api_bean_response* bean_element;"));
    }

    @Test
    public void testGenerateResponseParser() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("BoolElement", "boolean", null);
        final Ds3Element testElement2 = new Ds3Element("BeanElement", "com.spectralogic.s3.server.domain.UserApiBean", null);
        final ImmutableList.Builder<Ds3Element> ds3ElementBuilder = ImmutableList.builder();
        ds3ElementBuilder.add(testElement1);
        ds3ElementBuilder.add(testElement2);
        final ImmutableList<Ds3Element> elementsList = ds3ElementBuilder.build();

        final Struct testStruct = new Struct("testStruct", StructHelper.convertDs3Elements(elementsList));
        final String output = StructHelper.generateResponseParser(testStruct.getName(), testStruct.getVariables());
        LOG.info("Generated code:\n" + output);
        assertTrue(output.contains("    if (element_equal(child_node, \"BoolElement\")) {"));
        assertTrue(output.contains("        response->bool_element = xml_get_bool(doc, child_node);"));
        assertTrue(output.contains("    } else if (element_equal(child_node, \"BeanElement\")) {"));
        assertTrue(output.contains("        response->bean_element = _parse_ds3_bean_element_response(log, doc, child_node);"));
        assertTrue(output.contains("    }"));
    }

    /* Commented out until functionality for Arrays and Sets is implemented.
    @Test
    public void testElementTypeArrayToString() throws ParseException {
        Element stringElement = new Element("arrayElement", "array", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    @Test
    public void testElementTypeSetToString() throws ParseException {
        Element stringElement = new Element("setElement", "java.util.Set", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    */
}
