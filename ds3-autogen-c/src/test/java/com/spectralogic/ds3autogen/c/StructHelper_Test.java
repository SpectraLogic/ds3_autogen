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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.StructConverter;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.ComplexType;
import com.spectralogic.ds3autogen.c.models.PrimitiveType;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class StructHelper_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    @Test
    public void testTypeRequiresNewParser() {
        final Set<String> existingStructs = new HashSet<>();
        final ImmutableSet.Builder<String> enumNames = ImmutableSet.builder();
        final StructMember testStruct1 = new StructMember(new PrimitiveType("int", false), "intMember");
        final StructMember testStruct2 = new StructMember(new PrimitiveType("ds3_bool", false), "boolMember");
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStruct1, testStruct2);
        final Struct testStruct = new Struct("testStruct", testStructMembers);
        assertFalse(StructHelper.requiresNewCustomParser(testStruct, existingStructs, enumNames.build()));
    }

    @Test
    public void testTypeRequiresNewParser2() {
        final Set<String> existingStructs = new HashSet<>();
        final ImmutableSet.Builder<String> enumNames = ImmutableSet.builder();
        final StructMember testStruct1 = new StructMember(new PrimitiveType("ds3_bool", false), "boolMember");
        final StructMember testStruct2 = new StructMember(new ComplexType("ds3_user_api_bean_response", false), "beanMember");
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStruct1, testStruct2);
        final Struct testStruct = new Struct("testStruct", testStructMembers);
        assertTrue(StructHelper.requiresNewCustomParser(testStruct, existingStructs, enumNames.build()));
    }
    @Test
    public void testEnumTypeDoesNotRequireNewParser() {
        final Set<String> existingStructs = new HashSet<>();
        final ImmutableSet.Builder<String> enumNames = ImmutableSet.builder();
        existingStructs.add("ds3_tape_type");

        final StructMember testStruct1 = new StructMember(new PrimitiveType("ds3_bool", false), "boolMember");
        final StructMember testStruct2 = new StructMember(new ComplexType("ds3_tape_type", false), "tapeTypeMember");
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStruct1, testStruct2);
        final Struct testStruct = new Struct("testStruct", testStructMembers);
        assertFalse(StructHelper.requiresNewCustomParser(testStruct, existingStructs, enumNames.build()));
    }

    @Test
    public void testGenerateStructMembers() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("boolElement", "boolean", null);
        final Ds3Element testElement2 = new Ds3Element("beanElement", "com.spectralogic.s3.server.domain.UserApiBean", null);
        final ImmutableList<Ds3Element> elementsList = ImmutableList.of(testElement1, testElement2);
        final Ds3Type ds3Type = new Ds3Type("testDs3Type", elementsList);

        final ImmutableSet.Builder<String> enumNames = ImmutableSet.builder();
        final Struct testStruct = StructConverter.toStruct(ds3Type, enumNames.build());
        final String output = StructHelper.generateStructMembers(testStruct.getStructMembers());
        assertTrue(output.contains("ds3_bool bool_element;"));
        assertTrue(output.contains("ds3_user_api_bean_response* bean_element;"));
    }

    @Test
    public void testGenerateResponseParser() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("BoolElement", "boolean", null);
        final Ds3Element testElement2 = new Ds3Element("BeanElement", "com.spectralogic.s3.server.domain.UserApiBean", null);
        final ImmutableList<Ds3Element> elementsList = ImmutableList.of(testElement1, testElement2);
        final Ds3Type ds3Type = new Ds3Type("testDs3Type", elementsList);

        final ImmutableSet.Builder<String> enumNames = ImmutableSet.builder();
        final Struct testStruct = StructConverter.toStruct(ds3Type, enumNames.build());
        final String output = StructHelper.generateResponseParser(testStruct.getName(), testStruct.getStructMembers());
        LOG.info("Generated code:\n" + output);
        assertTrue(output.contains("    if (element_equal(child_node, \"BoolElement\")) {"));
        assertTrue(output.contains("        response->bool_element = xml_get_bool(doc, child_node);"));
        assertTrue(output.contains("    } else if (element_equal(child_node, \"BeanElement\")) {"));
        assertTrue(output.contains("        response->bean_element = _parse_ds3_bean_element_response(log, doc, child_node);"));
        assertTrue(output.contains("    } else {"));
        assertTrue(output.contains("        ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);"));
        assertTrue(output.contains("    }"));
    }

}
