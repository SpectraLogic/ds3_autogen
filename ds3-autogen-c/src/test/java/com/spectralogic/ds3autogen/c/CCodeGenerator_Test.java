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

import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CCodeGenerator_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    @Test
    public void testGenerateSingleDeleteRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/SingleRequestHandler.xml"));
        final CodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("/tmp"));

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* ds3_init_delete_bucket_request(const char* bucket_name) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_DELETE, _build_path(\"/\", bucket_name, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateSingleTypedefEnum() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypedefEnum.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.setFileUtils(fileUtils);
        codeGenerator.setSpec(spec);
        codeGenerator.generateEnums(fileUtils.getOutputStream());

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
    public void testGenerateSingleTypeEnumConstantMatcher() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypedefEnum.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.setFileUtils(fileUtils);
        codeGenerator.setSpec(spec);
        codeGenerator.generateEnumMatchers(fileUtils.getOutputStream());

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
        assertTrue(output.contains("    }"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateSimpleTypedefStruct() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/SimpleTypedefStruct.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.setFileUtils(fileUtils);
        codeGenerator.setSpec(spec);
        codeGenerator.generateTypedefStructs(fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("typedef struct {"));
        assertTrue(output.contains("    ds3_str* display_name;"));
        assertTrue(output.contains("    ds3_str* id;"));
        assertTrue(output.contains("}ds3_user_api_bean_response;"));
    }

    @Test
    public void testSingleFreeStructPrototype() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/SimpleTypedefStruct.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.setFileUtils(fileUtils);
        codeGenerator.setSpec(spec);
        codeGenerator.generateFreeResponseStructPrototypes(fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("void ds3_user_api_bean_response_free(ds3_user_api_bean_response* response_data);"));
    }

    @Test
    public void testSingleFreeTypedefStruct() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/SimpleTypedefStruct.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.setFileUtils(fileUtils);
        codeGenerator.setSpec(spec);
        codeGenerator.generateStructFreeFunctions(fileUtils.getOutputStream());

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
    public void testSimpleTypdefStructResponseParser() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/SimpleTypedefStruct.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.setFileUtils(fileUtils);
        codeGenerator.setSpec(spec);
        codeGenerator.generateResponseStructParsers(fileUtils.getOutputStream());

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
        /*
        assertTrue(output.contains("        } else {"));
        assertTrue(output.contains("            ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);"));
        */
        assertTrue(output.contains("        }"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return response;"));
        assertTrue(output.contains("}"));

    }

    @Test
    public void testEmbeddedResponseStructParser() throws IOException, ParserException, TypeRenamingConflictException, ResponseTypeNotFoundException, ParseException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/ComplexTypedefStruct.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.setFileUtils(fileUtils);
        codeGenerator.setSpec(spec);
        codeGenerator.generateResponseStructParsers(fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("static GPtrArray* _parse_ds3_buckets_api_bean_response(const ds3_log* log, xmlDocPtr doc, xmlNodePtr root) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));

        assertTrue(output.contains("    GPtrArray* buckets_array = g_ptr_array_new();"));

        assertTrue(output.contains("    for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        g_ptr_array_add(buckets_array, _parse_ds3_bucket_api_bean_response(log, doc, child_node));"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return buckets_array;"));
        assertTrue(output.contains("}"));

        assertTrue(output.contains("static ds3_buckets_api_bean_response* _parse_ds3_buckets_api_bean_response(const ds3_log* log, const xmlDocPtr doc, const xmlNodePtr root_node) {"));
        assertTrue(output.contains("    xmlNodePtr child_node;"));
        assertTrue(output.contains("    ds3_buckets_api_bean_response* response = g_new0(ds3_buckets_api_bean_response, 1);"));

        assertTrue(output.contains("    for (child_node = root_node->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {"));
        assertTrue(output.contains("        if (element_equal(child_node, \"Buckets\")) {"));
        assertTrue(output.contains("            GPtrArray* buckets_array = _parse_ds3_buckets_api_bean_response(log, doc, child_node);"));
        assertTrue(output.contains("            response->buckets = (ds3_bucket_api_bean_response**)buckets_array->pdata;"));
        assertTrue(output.contains("            response->num_buckets = buckets_array->len;"));
        assertTrue(output.contains("            g_ptr_array_free(buckets_array, FALSE);"));
        assertTrue(output.contains("        } else if (element_equal(child_node, \"Owner\")) {"));
        assertTrue(output.contains("            response->owner = _parse_ds3_owner_response(log, doc, child_node);"));
        assertTrue(output.contains("        }"));

        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return response;"));
        assertTrue(output.contains("}"));
    }
}
