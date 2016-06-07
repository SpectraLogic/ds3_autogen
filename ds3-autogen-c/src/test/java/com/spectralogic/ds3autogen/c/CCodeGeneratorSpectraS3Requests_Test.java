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
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.converters.SourceConverter;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Source;
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

public class CCodeGeneratorSpectraS3Requests_Test {

    @Test
    public void testGenerateSpectraS3DeleteBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/SpectraS3DeleteBucketRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_error* ds3_delete_bucket_spectra_s3_request(const ds3_client* client, const ds3_request* request) {"));
        assertTrue(output.contains("    int num_slashes = num_chars_in_ds3_str(request->path, '/');"));
        assertTrue(output.contains("    if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource id parameter is required.\");"));
        assertTrue(output.contains("    } else if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource type parameter is required.\");"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return _internal_request_dispatcher(client, request, NULL, NULL, NULL, NULL, NULL);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateSpectraS3GetSystemInformationRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/SpectraS3GetSystemInfoRequest_WithResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_error* ds3_get_system_information_spectra_s3_request(const ds3_client* client, const ds3_request* request, ds3_system_information_response** response) {"));
        assertTrue(output.contains("    return _parse_top_level_ds3_system_information_response(client, request, response, xml_blob);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateSpectraS3GetBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/SpectraS3GetBucketRequest_WithResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_error* ds3_get_bucket_spectra_s3_request(const ds3_client* client, const ds3_request* request, ds3_bucket_response** response) {"));
        assertTrue(output.contains("    int num_slashes = num_chars_in_ds3_str(request->path, '/');"));
        assertTrue(output.contains("    if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource id parameter is required.\");"));
        assertTrue(output.contains("    } else if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource type parameter is required.\");"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return _parse_top_level_ds3_bucket_response(client, request, response, xml_blob);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateSpectraS3GetBucketsRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException, TemplateModelException {
        final String inputSpecFile = "/input/SpectraS3GetBucketsRequest_WithArrayResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = SourceConverter.toSource(CCodeGenerator.getAllEnums(spec),
                CCodeGenerator.getAllStructs(spec, ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of(), ImmutableSet.of()),
                CCodeGenerator.getAllRequests(spec));
        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        assertTrue(output.contains("ds3_error* ds3_get_buckets_spectra_s3_request(const ds3_client* client, const ds3_request* request, ds3_bucket_list_response** response) {"));
        assertTrue(output.contains("    if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {"));
        assertTrue(output.contains("        return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource type parameter is required.\");"));
        assertTrue(output.contains("    }"));

        assertTrue(output.contains("    return _parse_top_level_ds3_bucket_list_response(client, request, response, xml_blob);"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateSpectraS3GetBucketRequestPrototype() throws IOException, TemplateModelException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Map<String,Object> testMap = new HashMap<>();
        final String responseType = StructHelper.getResponseTypeName("ListBucketResult");
        final Request requestEntry = new Request(
                "get_bucket_request",
                Classification.spectrads3,
                HttpVerb.GET,
                "\"/_rest_/\", bucket_name, NULL",
                null,
                null,
                RequestConverter.getParamList(responseType),
                ImmutableList.of(),
                ImmutableList.of(),
                true,
                false,
                null,
                responseType
        );
        testMap.put("requestEntry", requestEntry);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(testMap, "header-templates/RequestPrototype.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "LIBRARY_API ds3_error* ds3_get_bucket_request(const ds3_client* client, const ds3_request* request, ds3_list_bucket_result_response** response);";
        assertEquals(expectedOutput, output);
    }
}
