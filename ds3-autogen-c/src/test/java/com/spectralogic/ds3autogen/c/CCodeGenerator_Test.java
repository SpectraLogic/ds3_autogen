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
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.Header;
import com.spectralogic.ds3autogen.c.models.Source;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CCodeGenerator_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    @Test
    public void testGenerateSingleTypedefEnum() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/TypedefEnum.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Header header = new Header(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(header, "TypedefEnum.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("typedef enum {"));
        assertTrue(output.contains("    IN_PROGRESS,"));
        assertTrue(output.contains("    COMPLETED,"));
        assertTrue(output.contains("    CANCELED"));
        assertFalse(output.contains("    CANCELED,")); // verify no trailing comma
        assertTrue(output.contains("}ds3_job_status;"));
    }

    @Test
    public void testGenerateSingleTypeEnumConstantMatcher() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/TypedefEnum.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "TypedefEnumMatcher.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("static ds3_job_status _match_ds3_job_status(const ds3_log* log, const xmlChar* text) {"));
        assertTrue(output.contains("    if (xmlStrcmp(text, (const xmlChar*) \"IN_PROGRESS\") == 0) {"));
        assertTrue(output.contains("        return IN_PROGRESS;"));
        assertTrue(output.contains("    } else if (xmlStrcmp(text, (const xmlChar*) \"COMPLETED\") == 0) {"));
        assertTrue(output.contains("        return COMPLETED;"));
        assertTrue(output.contains("    } else if (xmlStrcmp(text, (const xmlChar*) \"CANCELED\") == 0) {"));
        assertTrue(output.contains("        return CANCELED;"));
        assertTrue(output.contains("    } else {"));
        assertTrue(output.contains("        ds3_log_message(log, DS3_ERROR, \"ERROR: Unknown value of '%s'.  Returning IN_PROGRESS for safety.\", text);"));
        assertTrue(output.contains("        return CANCELED;"));
        assertTrue(output.contains("    }"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateSimpleTypedefStruct() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/SimpleTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Header header = new Header(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(header, "TypedefStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("typedef struct {"));
        assertTrue(output.contains("    ds3_str* display_name;"));
        assertTrue(output.contains("    ds3_str* id;"));
        assertTrue(output.contains("}ds3_user_api_bean_response;"));
    }

    @Test
    public void testGenerateComplexTypedefStruct() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Header header = new Header(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(header, "TypedefStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("typedef struct {"));
        assertTrue(output.contains("    ds3_ds3_bucket_response** buckets;"));
        assertTrue(output.contains("    size_t num_buckets;"));
        assertTrue(output.contains("    ds3_user_api_bean_response* owner;"));
        assertTrue(output.contains("}ds3_list_all_my_buckets_result_response;"));
    }

    @Test
    public void testFreeStructPrototype() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/SimpleTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Header header = new Header(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(header, "FreeStructPrototype.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("void ds3_user_api_bean_response_free(ds3_user_api_bean_response* response_data);"));
    }

    @Test
    public void testSimpleFreeTypedefStruct() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/SimpleTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "FreeStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("void ds3_user_api_bean_response_free(ds3_user_api_bean_response* response_data) {"));
        assertTrue(output.contains("    if (response_data == NULL) {"));
        assertTrue(output.contains("        return;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    ds3_str_free(response_data->display_name);"));
        assertTrue(output.contains("    ds3_str_free(response_data->id);"));

        assertTrue(output.contains("    g_free(response_data);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testComplexFreeTypedefStruct() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "FreeStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("void ds3_list_all_my_buckets_result_response_free(ds3_list_all_my_buckets_result_response* response_data) {"));
        assertTrue(output.contains("    if (response_data == NULL) {"));
        assertTrue(output.contains("        return;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    for (index = 0; index < response->num_buckets; index++) {"));
        assertTrue(output.contains("        ds3_ds3_bucket_response_free(response_data->buckets[index]);"));
        assertTrue(output.contains("    }"));
        assertTrue(output.contains("    g_free(response_data->buckets);"));

        assertTrue(output.contains("    ds3_user_api_bean_response_free(response_data->owner);"));

        assertTrue(output.contains("    g_free(response_data);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testComplexFreeTypedefStructWithEnum() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/ComplexTypedefStructWithEnum.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = ImmutableSet.copyOf(allEnums.stream().map(Enum::getName).collect(Collectors.toSet()));

        final Source source = new Source(allEnums, CCodeGenerator.getAllStructs(spec, enumNames), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "FreeStruct.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("void ds3_blob_response_free(ds3_blob_response* response_data) {"));
        assertTrue(output.contains("    if (response_data == NULL) {"));
        assertTrue(output.contains("        return;"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    ds3_str_free(response_data->checksum);"));
        assertTrue(output.contains("    ds3_str_free(response_data->id);"));
        assertTrue(output.contains("    ds3_str_free(response_data->object_id);"));

        assertTrue(output.contains("    g_free(response_data);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testSimpleTypdefStructResponseParser() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/SimpleTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "ResponseParser.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("static ds3_user_api_bean_response* _parse_ds3_user_api_bean_response(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_user_api_bean_response* response = g_new0(ds3_user_api_bean_response, 1);"));

        assertTrue(output.contains("    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"DisplayName\")) {"));
        assertTrue(output.contains("            response->display_name = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Id\")) {"));
        assertTrue(output.contains("            response->id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);"));
        assertTrue(output.contains("        }"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return response;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testComplexResponseStructParser() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/ComplexTypedefStruct.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "ResponseParser.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("static GPtrArray* _parse_ds3_list_all_my_buckets_result_response_array(const ds3_log* log, xmlDocPtr doc, xmlNodePtr root) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));

        assertTrue(output.contains("    GPtrArray* buckets_array = g_ptr_array_new();"));

        assertTrue(output.contains("    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        g_ptr_array_add(buckets_array, _parse_ds3_ds3_bucket_response(log, doc, child_node));"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return buckets_array;"));
        assertTrue(output.contains("}"));

        assertTrue(output.contains("static ds3_list_all_my_buckets_result_response* _parse_ds3_list_all_my_buckets_result_response(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_list_all_my_buckets_result_response* response = g_new0(ds3_list_all_my_buckets_result_response, 1);"));

        assertTrue(output.contains("    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"Buckets\")) {"));
        assertTrue(output.contains("            GPtrArray* buckets_array = _parse_ds3_list_all_my_buckets_result_response_array(log, doc, child_node);"));
        assertTrue(output.contains("            response->buckets = (ds3_ds3_bucket_response**)buckets_array->pdata;"));
        assertTrue(output.contains("            response->num_buckets = buckets_array->len;"));
        assertTrue(output.contains("            g_ptr_array_free(buckets_array, FALSE);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Owner\")) {"));
        assertTrue(output.contains("            response->owner = _parse_ds3_owner_response(log, doc, child_node);"));
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);"));
        assertTrue(output.contains("        }"));

        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return response;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testComplexResponseStructParserWithEnum() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/ComplexTypedefStructWithEnum.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> enumNames = ImmutableSet.copyOf(allEnums.stream().map(Enum::getName).collect(Collectors.toSet()));

        final Source source = new Source(allEnums, CCodeGenerator.getAllStructs(spec, enumNames), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "ResponseParser.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("static ds3_blob_response* _parse_ds3_blob_response(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_blob_response* response = g_new0(ds3_blob_response, 1);"));

        assertTrue(output.contains("    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"ByteOffset\")) {"));
        assertTrue(output.contains("            response->byte_offset = xml_get_uint64(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Checksum\")) {"));
        assertTrue(output.contains("            response->checksum = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"ChecksumType\")) {"));
        assertTrue(output.contains("            xmlChar* text = xmlNodeListGetString(doc, child_node, 1);"));
        assertTrue(output.contains("            if (text == NULL) {"));
        assertTrue(output.contains("                continue;"));
        assertTrue(output.contains("            }"));
        assertTrue(output.contains("            response->checksum_type = _match_ds3_checksum_type(log, text);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Id\")) {"));
        assertTrue(output.contains("            response->id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Length\")) {"));
        assertTrue(output.contains("            response->length = xml_get_uint64(doc, child_node);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"ObjectId\")) {"));
        assertTrue(output.contains("            response->object_id = xml_get_string(doc, child_node);"));
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);"));
        assertTrue(output.contains("        }"));

        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return response;"));
        assertTrue(output.contains("}"));
    }


    /* Parsing of AllTypedefEnums.xml is hanging currently.
    @Test
    public void testAllEnums() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/AllTypedefEnums.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        try {
            final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec), CCodeGenerator.getAllRequests(spec));
            for (final Enum enumEntry : source.getEnums()) {
                codeGenerator.processTemplate(enumEntry, "TypedefEnum.ftl", fileUtils.getOutputStream());
            }

            final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
            final String output = new String(bstream.toByteArray());

            LOG.info("Generated code:\n" + output);
        } catch (final ParseException e) {
            LOG.error("Error parsing " + inputSpecFile, e);
        }
    }
    */

    /* Parsing of full api contract is not currently working - exception thrown for multiple responses for the same request.
    @Test
    public void testGenerateHeader() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final String inputSpecFile = "/input/CompleteApiContract.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        try {
            final Header header = new Header(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getStructsOrderedList(spec), CCodeGenerator.getAllRequests(spec));
            codeGenerator.processTemplate(header, "ds3_h.ftl", fileUtils.getOutputStream());

            final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
            final String output = new String(bstream.toByteArray());

            LOG.info("Generated code:\n" + output);
        } catch (final ParseException e) {
            LOG.error("Error parsing " + inputSpecFile, e);
        }
    }
    */
}
