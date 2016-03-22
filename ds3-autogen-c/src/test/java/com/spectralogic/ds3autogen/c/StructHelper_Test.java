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
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.StructConverter;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
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
        final Struct testStruct = new Struct("testStruct", "Data", testStructMembers);
        assertFalse(StructHelper.requiresNewCustomParser(testStruct, existingStructs, enumNames.build()));
    }

    @Test
    public void testTypeRequiresNewParser2() {
        final Set<String> existingStructs = new HashSet<>();
        final ImmutableSet.Builder<String> enumNames = ImmutableSet.builder();
        final StructMember testStruct1 = new StructMember(new PrimitiveType("ds3_bool", false), "boolMember");
        final StructMember testStruct2 = new StructMember(new FreeableType("ds3_user_api_bean_response", false), "beanMember");
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStruct1, testStruct2);
        final Struct testStruct = new Struct("testStruct", "Data", testStructMembers);
        assertTrue(StructHelper.requiresNewCustomParser(testStruct, existingStructs, enumNames.build()));
    }
    @Test
    public void testEnumTypeDoesNotRequireNewParser() {
        final Set<String> existingStructs = new HashSet<>();
        final ImmutableSet.Builder<String> enumNames = ImmutableSet.builder();
        existingStructs.add("ds3_tape_type");

        final StructMember testStruct1 = new StructMember(new PrimitiveType("ds3_bool", false), "boolMember");
        final StructMember testStruct2 = new StructMember(new FreeableType("ds3_tape_type", false), "tapeTypeMember");
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStruct1, testStruct2);
        final Struct testStruct = new Struct("testStruct", "Data", testStructMembers);
        assertFalse(StructHelper.requiresNewCustomParser(testStruct, existingStructs, enumNames.build()));
    }

    @Test
    public void testGetArrayStructMemberTypes() {
        final C_Type ds3BoolArray = new PrimitiveType("ds3_bool", true);
        final StructMember testStructMember1 = new StructMember(ds3BoolArray, "boolArrayMember");
        final Struct testStructWithArrayMembers = new Struct("testStruct", "Data", ImmutableList.of(testStructMember1));
        final ImmutableSet<C_Type>  arrayStructMemberTypes = StructHelper.getArrayStructMemberTypes(ImmutableList.of(testStructWithArrayMembers));
        assertTrue(arrayStructMemberTypes.contains(ds3BoolArray));
        assertEquals(arrayStructMemberTypes.size(), 1);
    }

    @Test
    public void testGetArrayStructMembersUnique() {
        final C_Type ds3BoolArray = new PrimitiveType("ds3_bool", true);
        final C_Type ds3TapeTypeArray = new FreeableType("ds3_tape_type", true);
        final StructMember testArrayStructMember1 = new StructMember(ds3BoolArray, "boolArrayMember");
        final StructMember testArrayStructMember2 = new StructMember(ds3TapeTypeArray, "tapeTypeArrayMember1");
        final StructMember testArrayStructMember3 = new StructMember(ds3TapeTypeArray, "tapeTypeArrayMember2");
        final StructMember testStructMember3 = new StructMember(new FreeableType("ds3_bucket", false), "bucketTypeArrayMember");
        final ImmutableList<StructMember> testArrayStructMembers1 = ImmutableList.of(testArrayStructMember1, testArrayStructMember2);
        final ImmutableList<StructMember> testArrayStructMembers2 = ImmutableList.of(testArrayStructMember2, testArrayStructMember3);
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStructMember3);
        final Struct testStructWithArrayMembers1 = new Struct("testStruct", "Data", testArrayStructMembers1);
        final Struct testStructWithArrayMembers2 = new Struct("testStruct", "Data", testArrayStructMembers2);
        final Struct testStructWithoutArrayMember = new Struct("testStructWithoutArrayMembers", "Data", testStructMembers);
        final ImmutableSet<C_Type>  arrayStructMemberTypes = StructHelper.getArrayStructMemberTypes(ImmutableList.of(testStructWithArrayMembers1, testStructWithArrayMembers2, testStructWithoutArrayMember));
        assertTrue(arrayStructMemberTypes.contains(ds3BoolArray));
        assertTrue(arrayStructMemberTypes.contains(ds3TapeTypeArray));
        assertEquals(arrayStructMemberTypes.size(), 2);
    }

    @Test
    public void testGetArrayStructMembersUnique2() {
        final C_Type ds3BoolArray = new PrimitiveType("ds3_bool", true);
        final C_Type ds3TapeTypeArray = new FreeableType("ds3_tape_type", true);
        final StructMember testStructMember1 = new StructMember(ds3BoolArray, "boolArrayMember");
        final StructMember testStructMember2 = new StructMember(ds3TapeTypeArray, "tapeTypeArrayMember");
        final StructMember testStructMember3 = new StructMember(new FreeableType("ds3_bucket", false), "bucketTypeArrayMember");
        final ImmutableList<StructMember> testArrayStructMemberTypes = ImmutableList.of(testStructMember1, testStructMember2);
        final ImmutableList<StructMember> testStructMembersWithoutArrays = ImmutableList.of(testStructMember3);
        final Struct testStructWithArrayTypeMembers = new Struct("testStruct", "Data", testArrayStructMemberTypes);
        final Struct testStructWithoutArrayTypeMember = new Struct("testStructWithoutArrayMembers", "Data", testStructMembersWithoutArrays);
        final ImmutableSet<C_Type>  arrayStructMemberTypes = StructHelper.getArrayStructMemberTypes(ImmutableList.of(testStructWithArrayTypeMembers, testStructWithoutArrayTypeMember));
        assertTrue(arrayStructMemberTypes.contains(ds3BoolArray));
        assertTrue(arrayStructMemberTypes.contains(ds3TapeTypeArray));
        assertEquals(arrayStructMemberTypes.size(), 2);
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
        assertTrue(output.contains("        _response->bool_element = xml_get_bool(doc, child_node);"));
        assertTrue(output.contains("    } else if (element_equal(child_node, \"BeanElement\")) {"));
        assertTrue(output.contains("        _response->bean_element = _parse_ds3_bean_element_response(log, doc, child_node);"));
        assertTrue(output.contains("    } else {"));
        assertTrue(output.contains("        ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);"));
        assertTrue(output.contains("    }"));
    }

    @Test
    public void testGenerateGetSystemInformationParser() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/ResponseTypeGetSystemInfo.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "ResponseParser.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("static ds3_error* _parse_ds3_system_information_response(const ds3_log* log, const ds3_system_information_response** response) {"));
        assertTrue(output.contains("    xmlDocPtr doc;"));
        assertTrue(output.contains("    xmlNodePtr root;"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_error* error;"));
        assertTrue(output.contains("    ds3_system_information_response* _response = *response;"));

        assertTrue(output.contains("    error = _get_request_xml_nodes(client, request, &doc, &root, \"Data\");"));
        assertTrue(output.contains("    if (error != NULL) {"));
        assertTrue(output.contains("        return error;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"ApiVersion\")) {"));
        assertTrue(output.contains("            _response->api_version = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"BackendActivated\")) {"));
        assertTrue(output.contains("            _response->backend_activated = xml_get_bool(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"BuildInformation\")) {"));
        assertTrue(output.contains("            _response->build_information = _parse_ds3_build_information_response(log, doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"InstanceId\")) {"));
        assertTrue(output.contains("            _response->instance_id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"SerialNumber\")) {"));
        assertTrue(output.contains("            _response->serial_number = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);"));
        assertTrue(output.contains("        }"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    xmlFreeDoc(doc);"));
        assertTrue(output.contains("    return NULL;"));
        assertTrue(output.contains("}"));
    }
}
