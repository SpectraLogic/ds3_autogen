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
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.converters.SourceConverter;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Source;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CCodeGeneratorAmazonS3InitRequests_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGeneratorAmazonS3InitRequests_Test.class);

    @Test
    public void testGenerateInitAmazonS3DeleteBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/AmazonS3DeleteBucketRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_request* init_delete_bucket_request(const char* bucket_name) {"));
        assertTrue(output.contains("    struct _ds3_request* request = _common_request_init(HTTP_DELETE, _build_path(\"/\", bucket_name, NULL));"));
        assertTrue(output.contains("    return request;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitAmazonS3GetBucketRequest_WithResponsePayload() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/AmazonS3GetBucketRequest_WithResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));

        final Request requestEntry = source.getRequests().get(0);
        final Map<String,Object> testMap = new HashMap<>();
        testMap.put("requestEntry", requestEntry);
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_request* init_get_bucket_request(const char* bucket_name, const char* delimiter, const char* marker, const int* max_keys, const char* prefix) {"));
        assertTrue(output.contains("    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/\", bucket_name, NULL));"));
        assertTrue(output.contains("    "));
        assertTrue(output.contains("    return (ds3_request*) request;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitAmazonS3PutBucketRequest_NoPayload() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/AmazonS3PutBucketRequest_NoPayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_request* init_put_bucket_request(const char* bucket_name) {"));
        assertTrue(output.contains("    struct _ds3_request* request = _common_request_init(HTTP_PUT, _build_path(\"/\", bucket_name, NULL));"));
        assertTrue(output.contains("    return (ds3_request*) request;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitAmazonS3GetBucketsRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/AmazonS3GetBucketsRequest_WithResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info(output);

        assertTrue(output.contains("ds3_request* init_get_service_request(void) {"));
        assertTrue(output.contains("    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/\", NULL, NULL));"));
        assertTrue(output.contains("    return (ds3_request*) request;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitAmazonS3WithOptionalQueryParams() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.CreateObjectRequestHandler",
                HttpVerb.PUT,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                false,
                null,
                ImmutableList.of(
                        new Ds3Param("Job", "java.util.UUID", true),
                        new Ds3Param("Offset", "long", false)),
                ImmutableList.of()));
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_create_object(const char* bucket_name, const char* object_name, const uint64_t* length, const char* job, const uint64_t* offset) {" + "\n"
                                    + "    struct _ds3_request* request = _common_request_init(HTTP_PUT, _build_path(\"/\", bucket_name, object_name));" + "\n"
                                    + "    request->length = *length;"                                                                                   + "\n"
                                    + "\n"
                                    + "    if (job != NULL) {"                                                                                           + "\n"
                                    + "        _set_query_param((ds3_request*) request, \"job\", job);"                                                  + "\n"
                                    + "    }"                                                                                                            + "\n"
                                    + "    if (offset != NULL) {"                                                                                        + "\n"
                                    + "        char tmp_buff[32];"                                                                                       + "\n"
                                    + "        sprintf(tmp_buff, \"%llu\", (unsigned long long) *offset);"                                               + "\n"
                                    + "        _set_query_param((ds3_request*) request, \"offset\", tmp_buff);"                                          + "\n"
                                    + "    }"                                                                                                            + "\n"
                                    + "\n"
                                    + "    return (ds3_request*) request;"                                                                               + "\n"
                                    + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitAmazonS3WithRequiredQueryParams() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.InitiateMultiPartUploadRequestHandler",
                HttpVerb.POST,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                false,
                null,
                ImmutableList.of(
                        new Ds3Param("Job", "java.util.UUID", true),
                        new Ds3Param("Offset", "long", false)),
                ImmutableList.of()));
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_initiate_multi_part_upload(const char* bucket_name, const char* object_name, const uint64_t* length, const char* job, const uint64_t* offset) {" + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_POST, _build_path(\"/\", bucket_name, object_name));" + "\n"
                + "    request->length = *length;"                                                                                    + "\n"
                + "\n"
                + "    if (job != NULL) {"                                                                                            + "\n"
                + "        _set_query_param((ds3_request*) request, \"job\", job);"                                                   + "\n"
                + "    }"                                                                                                             + "\n"
                + "    if (offset != NULL) {"                                                                                         + "\n"
                + "        char tmp_buff[32];"                                                                                        + "\n"
                + "        sprintf(tmp_buff, \"%llu\", (unsigned long long) *offset);"                                                + "\n"
                + "        _set_query_param((ds3_request*) request, \"offset\", tmp_buff);"                                           + "\n"
                + "    }"                                                                                                             + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                                + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }
}
