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
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.converters.SourceConverter;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Source;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import freemarker.template.TemplateModelException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CCodeGeneratorAmazonS3Requests_Test {

    @Test
    public void testGenerateAmazonS3HeadBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/AmazonS3HeadBucketRequest.xml";
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

        assertTrue(output.contains("ds3_error* head_bucket_request(const ds3_client* client, const ds3_request* request) {"));
        assertTrue(output.contains("    ds3_error* error = NULL;"));

        assertTrue(output.contains("    ds3_string_multimap* return_headers = NULL;"));
        assertTrue(output.contains("    ds3_metadata* metadata = NULL;"));

        assertTrue(output.contains("    if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The bucket name parameter is required.\");"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    error = _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, &return_headers);"));
        assertTrue(output.contains("    if (error == NULL) {"));
        assertTrue(output.contains("        metadata = _init_metadata(return_headers);"));
        assertTrue(output.contains("        *response = metadata;"));
        assertTrue(output.contains("        ds3_string_multimap_free(return_headers);"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return error;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateAmazonS3HeadObjectRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/AmazonS3HeadObjectRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes);
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_error* head_object_request(const ds3_client* client, const ds3_request* request) {"));
        assertTrue(output.contains("    ds3_error* error = NULL;"));

        assertTrue(output.contains("    ds3_string_multimap* return_headers = NULL;"));
        assertTrue(output.contains("    ds3_metadata* metadata = NULL;"));

        assertTrue(output.contains("    int num_slashes = num_chars_in_ds3_str(request->path, '/');"));
        assertTrue(output.contains("    if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The object name parameter is required.\");"));
        assertTrue(output.contains("    } else if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The bucket name parameter is required.\");"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    error = _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, &return_headers);"));
        assertTrue(output.contains("    if (error == NULL) {"));
        assertTrue(output.contains("        metadata = _init_metadata(return_headers);"));
        assertTrue(output.contains("        *response = metadata;"));
        assertTrue(output.contains("        ds3_string_multimap_free(return_headers);"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return error;"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateAmazonS3DeleteBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
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

        assertTrue(output.contains("ds3_error* delete_bucket_request(const ds3_client* client, const ds3_request* request) {"));

        assertTrue(output.contains("    if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The bucket name parameter is required.\");"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);"));

        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateAmazonS3GetBucketsRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/AmazonS3GetBucketsRequest_WithResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final ImmutableList<Request> allRequests = CCodeGenerator.getAllRequests(spec);
        final ImmutableList<Enum> allEnums = CCodeGenerator.getAllEnums(spec);
        final ImmutableSet<String> arrayMemberTypes = CCodeGenerator.getArrayMemberTypes(spec);
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
        final ImmutableList<Struct> allStructs = CCodeGenerator.getAllStructs(spec, enumNames, responseTypes, arrayMemberTypes);
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final Source source = SourceConverter.toSource(allEnums, allOrderedStructs, CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_error* get_service_request(const ds3_client* client, const ds3_request* request, const ds3_list_all_my_buckets_result_response** response) {"));

        assertTrue(output.contains("    return _parse_top_level_ds3_list_all_my_buckets_result_response(client, request, response, xml_blob);"));

        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateAmazonS3GetBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();
        final Request requestEntry = RequestConverter.toRequest(Ds3ModelFixtures.getBucketRequest());
        testMap.put("requestEntry", requestEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "request-templates/RequestWithResponsePayload.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_error* get_bucket(const ds3_client* client, const ds3_request* request, const ds3_list_bucket_result_response** response) {"));

        assertTrue(output.contains("    if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The bucket name parameter is required.\");"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return _parse_top_level_ds3_list_bucket_result_response(client, request, response, xml_blob);"));

        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateAmazonS3GetBucketRequestPrototype() throws IOException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();
        final Request requestEntry = RequestConverter.toRequest(Ds3ModelFixtures.getBucketRequest());
        testMap.put("requestEntry", requestEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "header-templates/RequestPrototype.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "LIBRARY_API ds3_error* get_bucket(const ds3_client* client, const ds3_request* request, const ds3_list_bucket_result_response** response);\n";
        assertEquals(expectedOutput, output);
    }
}

