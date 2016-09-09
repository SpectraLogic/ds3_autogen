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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.models.apispec.*;
import com.spectralogic.ds3autogen.c.converters.EnumConverter;
import com.spectralogic.ds3autogen.c.converters.SourceConverter;
import com.spectralogic.ds3autogen.c.converters.StructConverter;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import freemarker.template.TemplateModelException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class CCodeGenerator_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    @Test
    public void testGenerateSingleTypedefEnum() throws IOException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();
        final Enum enumEntry = new Enum("ds3_job_status", ImmutableList.of("IN_PROGRESS", "COMPLETED", "CANCELED"));
        testMap.put("enumEntry", enumEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "header-templates/TypedefEnum.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "typedef enum {" + "\n"
                                    + "    IN_PROGRESS," + "\n"
                                    + "    COMPLETED," + "\n"
                                    + "    CANCELED" + "\n"
                                    + "}ds3_job_status;" + "\n";
        assertThat(expectedOutput, is(output));
        assertFalse(output.contains("    CANCELED,")); // verify no trailing comma
    }

    @Test
    public void testGenerateSingleTypeEnumConstantMatcher() throws IOException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();
        final Ds3EnumConstant inProgress = new Ds3EnumConstant("IN_PROGRESS", null);
        final Ds3EnumConstant completed = new Ds3EnumConstant("COMPLETED", null);
        final Ds3EnumConstant canceled = new Ds3EnumConstant("CANCELED", null);
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(inProgress, completed, canceled);
        final Enum enumEntry = EnumConverter.toEnum(new Ds3Type("JobStatus", null, null, enumConstants));
        testMap.put("enumEntry", enumEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "source-templates/TypedefEnumMatcher.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "static ds3_job_status _match_ds3_job_status(const ds3_log* log, const xmlChar* text) {" + "\n"
                                    + "    if (xmlStrcmp(text, (const xmlChar*) \"IN_PROGRESS\") == 0) {" + "\n"
                                    + "        return DS3_JOB_STATUS_IN_PROGRESS;" + "\n"
                                    + "    } else if (xmlStrcmp(text, (const xmlChar*) \"COMPLETED\") == 0) {" + "\n"
                                    + "        return DS3_JOB_STATUS_COMPLETED;" + "\n"
                                    + "    } else if (xmlStrcmp(text, (const xmlChar*) \"CANCELED\") == 0) {" + "\n"
                                    + "        return DS3_JOB_STATUS_CANCELED;" + "\n"
                                    + "    } else {" + "\n"
                                    + "        ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown value of '%s'.  Returning DS3_JOB_STATUS_IN_PROGRESS for safety.\", text);" + "\n"
                                    + "        return DS3_JOB_STATUS_IN_PROGRESS;" + "\n"
                                    + "    }" + "\n"
                                    + "}" + "\n";

        assertThat(expectedOutput, is(output));
    }

    @Test
    public void testGenerateSimpleTypedefStruct() throws IOException, ParseException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();
        final Struct structEntry = new Struct("ds3_bucket_response",
                ImmutableList.of(
                        new StructMember( new FreeableType("ds3_str", false), "creation_date"),
                        new StructMember( new FreeableType("ds3_str", false), "data_policy_id"),
                        new StructMember( new FreeableType("ds3_str", false), "id"),
                        new StructMember( new PrimitiveType("uint64_t", false), "last_preferred_chunk_size_in_bytes"),
                        new StructMember( new PrimitiveType("uint64_t", false), "logical_used_capacity"),
                        new StructMember( new FreeableType("ds3_str", false), "name"),
                        new StructMember( new FreeableType("ds3_str", false), "user_id")
                ));
        testMap.put("structEntry", structEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "header-templates/TypedefStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());


        final String expectedOutput = "typedef struct {" + "\n"
                                    + "    ds3_str* creation_date;" + "\n"
                                    + "    ds3_str* data_policy_id;" + "\n"
                                    + "    ds3_str* id;" + "\n"
                                    + "    uint64_t last_preferred_chunk_size_in_bytes;" + "\n"
                                    + "    uint64_t logical_used_capacity;" + "\n"
                                    + "    ds3_str* name;" + "\n"
                                    + "    ds3_str* user_id;" + "\n"
                                    + "}ds3_bucket_response;" + "\n";

        assertThat(expectedOutput, is(output));
    }

    @Test
    public void testGenerateComplexTypedefStruct() throws IOException, ParseException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();

        final Struct structEntry = StructConverter.toStruct(
                new Ds3Type("ListAllMyBucketsResult",
                        ImmutableList.of(
                                new Ds3Element("Buckets", "array", "Bucket", false),
                                new Ds3Element("Owner", "User", null, false))),
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of());
        testMap.put("structEntry", structEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "header-templates/TypedefStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "typedef struct {" + "\n"
                                    + "    ds3_bucket_response** buckets;" + "\n"
                                    + "    size_t num_buckets;" + "\n" // Verify that the number of array elements is added to the model
                                    + "    ds3_user_response* owner;" + "\n"
                                    + "}ds3_list_all_my_buckets_result_response;" + "\n";

        assertThat(expectedOutput, is(output));
    }

    @Test
    public void testFreeStructPrototype() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/SimpleTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Header header = new Header(
                CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec,
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec),
                ImmutableSet.of());

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(header, "header-templates/ds3_h.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("void ds3_bucket_response_free(ds3_bucket_response* response_data);"));
    }

    @Test
    public void testSimpleFreeTypedefStruct() throws IOException, ParseException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();
        final Struct structEntry = new Struct("ds3_bucket_response",
                ImmutableList.of(
                        new StructMember( new FreeableType("ds3_str", false), "creation_date"),
                        new StructMember( new FreeableType("ds3_str", false), "data_policy_id"),
                        new StructMember( new FreeableType("ds3_str", false), "id"),
                        new StructMember( new PrimitiveType("uint64_t", false), "last_preferred_chunk_size_in_bytes"),
                        new StructMember( new PrimitiveType("uint64_t", false), "logical_used_capacity"),
                        new StructMember( new FreeableType("ds3_str", false), "name"),
                        new StructMember( new FreeableType("ds3_str", false), "user_id")
                ));
        testMap.put("structEntry", structEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "source-templates/FreeStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "void ds3_bucket_response_free(ds3_bucket_response* response) {" + "\n"
                                    + "    if (response == NULL) {" + "\n"
                                    + "        return;" + "\n"
                                    + "    }" + "\n"
                                    + "\n"
                                    + "    ds3_str_free(response->creation_date);" + "\n"
                                    + "    ds3_str_free(response->data_policy_id);" + "\n"
                                    + "    ds3_str_free(response->id);" + "\n"
                                    + "    ds3_str_free(response->name);" + "\n"
                                    + "    ds3_str_free(response->user_id);" + "\n"
                                    + "\n"
                                    + "    g_free(response);" + "\n"
                                    + "}" + "\n";

        assertEquals(expectedOutput, output);
    }

    @Test
    public void testComplexFreeTypedefStruct() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of(), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("void ds3_list_all_my_buckets_result_response_free(ds3_list_all_my_buckets_result_response* response) {"));
        assertTrue(output.contains("    if (response == NULL) {"));
        assertTrue(output.contains("        return;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    for (index = 0; index < response->num_buckets; index++) {"));
        assertTrue(output.contains("        ds3_bucket_details_response_free(response->buckets[index]);"));
        assertTrue(output.contains("    }"));
        assertTrue(output.contains("    g_free(response->buckets);"));

        assertTrue(output.contains("    ds3_user_response_free(response->owner);"));

        assertTrue(output.contains("    g_free(response);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testComplexFreeTypedefStructWithEnum() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/ComplexTypedefStructWithEnum.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of(), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("void ds3_blob_response_free(ds3_blob_response* response) {"));
        assertTrue(output.contains("    if (response == NULL) {"));
        assertTrue(output.contains("        return;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    ds3_str_free(response->checksum);"));
        assertTrue(output.contains("    ds3_str_free(response->id);"));
        assertTrue(output.contains("    ds3_str_free(response->object_id);"));

        assertTrue(output.contains("    g_free(response);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testSimpleTypdefStructResponseParser() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/SimpleTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of(), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("static ds3_error* _parse_top_level_ds3_bucket_response(const ds3_client* client, const ds3_request* request, ds3_bucket_response** _response, GByteArray* xml_blob) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_error* error;"));
        assertTrue(output.contains("    ds3_bucket_response* response;"));

        assertTrue(output.contains("    error = _get_request_xml_nodes(xml_blob, &doc, &root, \"Data\");"));
        assertTrue(output.contains("    if (error != NULL) {"));
        assertTrue(output.contains("        return error;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    response = g_new0(ds3_bucket_response, 1)"));
        assertTrue(output.contains("    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"CreationDate\")) {"));
        assertTrue(output.contains("            response->creation_date = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"DataPolicyId\")) {"));
        assertTrue(output.contains("            response->data_policy_id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Id\")) {"));
        assertTrue(output.contains("            response->id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"LastPreferredChunkSizeInBytes\")) {"));
        assertTrue(output.contains("            response->last_preferred_chunk_size_in_bytes = xml_get_uint64(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"LogicalUsedCapacity\")) {"));
        assertTrue(output.contains("            response->logical_used_capacity = xml_get_uint64(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Name\")) {"));
        assertTrue(output.contains("            response->name = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"UserId\")) {"));
        assertTrue(output.contains("            response->user_id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(client->log, DS3_ERROR, \"Unknown node[%s] of ds3_bucket_response [%s]\\n\", child_node->name, root->name);"));
        assertTrue(output.contains("        }"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    xmlFreeDoc(doc);"));

        assertTrue(output.contains("    if (error == NULL) {"));
        assertTrue(output.contains("        *_response = response;"));
        assertTrue(output.contains("    } else {"));
        assertTrue(output.contains("        ds3_bucket_response_free(response);"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return error;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testComplexResponseStructParser() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of(), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("static ds3_error* _parse_top_level_ds3_list_all_my_buckets_result_response(const ds3_client* client, const ds3_request* request, ds3_list_all_my_buckets_result_response** _response, GByteArray* xml_blob) {"));
        assertTrue(output.contains("    xmlDocPtr doc;"));
        assertTrue(output.contains("    xmlNodePtr root;"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_error* error;"));
        assertTrue(output.contains("    ds3_list_all_my_buckets_result_response* response;"));

        assertTrue(output.contains("    error = _get_request_xml_nodes(xml_blob, &doc, &root, \"Data\");"));
        assertTrue(output.contains("    if (error != NULL) {"));
        assertTrue(output.contains("        return error;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"Buckets\")) {"));
        assertTrue(output.contains("            GPtrArray* buckets_array;"));
        assertTrue(output.contains("            error = _parse_ds3_bucket_details_response_array(client, doc, child_node, &buckets_array);"));
        assertTrue(output.contains("            response->buckets = (ds3_bucket_details_response**)buckets_array->pdata;"));
        assertTrue(output.contains("            response->num_buckets = buckets_array->len;"));
        assertTrue(output.contains("            g_ptr_array_free(buckets_array, FALSE);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Owner\")) {"));
        assertTrue(output.contains("            error = _parse_ds3_user_response(client, doc, child_node, &response->owner);"));
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(client->log, DS3_ERROR, \"Unknown node[%s] of ds3_list_all_my_buckets_result_response [%s]\\n\", child_node->name, root->name);"));
        assertTrue(output.contains("        }"));

        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    xmlFreeDoc(doc);"));

        assertTrue(output.contains("    if (error == NULL) {"));
        assertTrue(output.contains("        *_response = response;"));
        assertTrue(output.contains("    } else {"));
        assertTrue(output.contains("        ds3_list_all_my_buckets_result_response_free(response);"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return error;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testComplexResponseStructParserWithEnum() throws Exception {
        final String inputSpecFile = "/input/ComplexTypedefStructWithEnum.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of("ds3_blob_response"), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("static ds3_error* _parse_ds3_blob_response(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, ds3_blob_response** _response) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_blob_response* response;"));

        assertTrue(output.contains("    response = g_new0(ds3_blob_response, 1);"));
        assertTrue(output.contains("    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"ByteOffset\")) {"));
        assertTrue(output.contains("            response->byte_offset = xml_get_uint64(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Checksum\")) {"));
        assertTrue(output.contains("            response->checksum = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"ChecksumType\")) {"));
        assertTrue(output.contains("            xmlChar* text = xmlNodeListGetString(doc, child_node, 1);"));
        assertTrue(output.contains("            if (text == NULL) {"));
        assertTrue(output.contains("                continue;"));
        assertTrue(output.contains("            }"));
        assertTrue(output.contains("            response->checksum_type = _match_ds3_checksum_type(client->log, text);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Id\")) {"));
        assertTrue(output.contains("            response->id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Length\")) {"));
        assertTrue(output.contains("            response->length = xml_get_uint64(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"ObjectId\")) {"));
        assertTrue(output.contains("            response->object_id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(client->log, DS3_ERROR, \"Unknown node[%s] of ds3_blob_response [%s]\\n\", child_node->name, root->name);"));
        assertTrue(output.contains("        }"));

        assertTrue(output.contains("        if (error != NULL) {"));
        assertTrue(output.contains("            break;"));
        assertTrue(output.contains("        }"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    *_response = response;"));

        assertTrue(output.contains("    return error;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testArrayStructMemberParser() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of(), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("static ds3_error* _parse_ds3_bucket_details_response_array(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, GPtrArray** _response) {"));
        assertTrue(output.contains("    ds3_error* error = NULL;"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    GPtrArray* ds3_bucket_details_response_array = g_ptr_array_new();"));

        assertTrue(output.contains("    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        ds3_bucket_details_response* response = NULL;"));
        assertTrue(output.contains("        error = _parse_ds3_bucket_details_response(client, doc, child_node, &response)"));
        assertTrue(output.contains("        g_ptr_array_add(ds3_bucket_details_response_array, response);"));

        assertTrue(output.contains("        if (error != NULL) {"));
        assertTrue(output.contains("            break;"));
        assertTrue(output.contains("        }"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    *_response = response;"));

        assertTrue(output.contains("    return error;"));
        assertTrue(output.contains("}"));

    }

    @Test
    public void testComplexResponseStructParserOrdering() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(
                spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of("ds3_bucket_details_response", "ds3_list_all_my_buckets_result_response"), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.indexOf("_parse_ds3_bucket_details_response_array") > output.indexOf("_parse_ds3_bucket_response"));
        assertTrue(output.indexOf("_parse_ds3_list_all_my_buckets_result_response") > output.indexOf("_parse_ds3_bucket_response_array"));
    }

    @Test
    public void testParserUnique() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(
                spec, enumNames, responseTypes, arrayMemberTypes, ImmutableSet.of("ds3_user_response"), ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final Pattern arrayStructParserPattern = Pattern.compile(Pattern.quote(
                "static ds3_error* _parse_ds3_bucket_details_response_array(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, GPtrArray** _response) {"));
        final Pattern arrayParserPattern = Pattern.compile(Pattern.quote(
                "static ds3_error* _parse_ds3_user_response(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, ds3_user_response** _response) {"));
        final Pattern structParserPattern = Pattern.compile(Pattern.quote(
                "static ds3_error* _parse_top_level_ds3_list_all_my_buckets_result_response(const ds3_client* client, const ds3_request* request, ds3_list_all_my_buckets_result_response** _response, GByteArray* xml_blob) {"));

        final Matcher arrayStructParserMatcher = arrayStructParserPattern.matcher(output);
        final Matcher arrayParserMatcher = arrayParserPattern.matcher(output);
        final Matcher structParserMatcher = structParserPattern.matcher(output);

        int arrayStructParserCount = 0;
        int arrayParserCount = 0;
        int structParserCount = 0;

        while (arrayStructParserMatcher.find()){
            arrayStructParserCount +=1;
        }
        while (arrayParserMatcher.find()){
    	    arrayParserCount +=1;
        }
        while (structParserMatcher.find()){
            structParserCount +=1;
        }

        assert(arrayStructParserCount == 1);
        assert(arrayParserCount == 1);
        assert(structParserCount == 1);
    }

    @Test
    public void testCompleteApiContractSource() throws IOException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/CompleteApiContract_r3_0_2-6_23_16.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));
        final CCodeGenerator codeGenerator = new CCodeGenerator();

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> embeddedTypes = CCodeGenerator.getEmbeddedTypes(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, embeddedTypes, ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());
    }

    @Test
    public void testGenerateCompleteApiContractHeader() throws IOException, TemplateModelException, ParseException {
        final String inputSpecFile = "/input/CompleteApiContract_r3_0_2-6_23_16.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));
        final CCodeGenerator codeGenerator = new CCodeGenerator();

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> embeddedTypes = CCodeGenerator.getEmbeddedTypes(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec, enumNames);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes, embeddedTypes, ImmutableSet.of());
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Header header = new Header(allEnums,
                allOrderedStructs,
                allRequests,
                ImmutableSet.of());
        codeGenerator.processTemplate(header, "header-templates/ds3_h.ftl", fileUtils.getOutputStream());
    }

    @Test
    public void testEmbeddedTypesSet() {
        final Ds3Type type1 = new Ds3Type("Ds3Bucket", ImmutableList.of(
                                new Ds3Element("ChecksumType", "Ds3ChecksumType", null, true),
                                new Ds3Element("Size", "Ds3Size", null, true)));
        final Ds3Type type2 = new Ds3Type("Ds3Object", ImmutableList.of(
                new Ds3Element("Name", "java.lang.String", null, true),
                new Ds3Element("Size", "Ds3Size", null, true)));
        final Ds3ApiSpec embeddedTypesSet = new Ds3ApiSpec(
                ImmutableList.of(),
                ImmutableMap.of(
                        type1.getName(), type1,
                        type2.getName(), type2));
        final ImmutableSet<String> embeddedTypes = CCodeGenerator.getEmbeddedTypes(embeddedTypesSet);
        assertEquals(embeddedTypes.size(), 3);
        assertTrue(embeddedTypes.contains("ds3_string_response"));
    }

    @Test
    public void testPaginatedTypesSet() {
        final Ds3ApiSpec getBucketsSpec = new Ds3ApiSpec(
                ImmutableList.of(Ds3ModelFixtures.getBucketsSpectraS3Request()),
                ImmutableMap.of());
        final ImmutableSet<String> paginatedTypes = CCodeGenerator.getPaginatedTypes(getBucketsSpec);
        assertEquals(paginatedTypes.size(), 1);
        assertTrue(paginatedTypes.contains("ds3_bucket_list_response"));
    }
}


