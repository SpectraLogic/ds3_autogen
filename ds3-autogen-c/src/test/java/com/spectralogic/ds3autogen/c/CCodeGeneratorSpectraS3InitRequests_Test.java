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

import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.c.models.Source;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.assertTrue;

public class CCodeGeneratorSpectraS3InitRequests_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGeneratorSpectraS3InitRequests_Test.class);

    @Test
    public void testGenerateInitSpectraS3DeleteBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/SpectraS3DeleteBucketRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "request-templates/SpectraS3InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* init_delete_bucket_spectra_s3_request(const char* resource_id) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_DELETE, _build_path(\"/_rest_/bucket\", resource_id, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitSpectraS3PutBucketRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/SpectraS3PutBucketRequest_WithResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "request-templates/SpectraS3InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* init_create_bucket_spectra_s3_request(const char* resource_id) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_POST, _build_path(\"/_rest_/bucket\", NULL, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitSpectraS3GetSystemInfoRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/SpectraS3GetSystemInfoRequest_WithResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "request-templates/SpectraS3InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* init_get_system_information_spectra_s3_request(void) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_GET, _build_path(\"/_rest_/system_information\", NULL, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitSpectraS3GetBucketsRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/SpectraS3GetBucketsRequest_WithArrayResponsePayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "request-templates/SpectraS3InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* init_get_buckets_spectra_s3_request(const char* resource_id) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_GET, _build_path(\"/_rest_/bucket\", NULL, NULL));"));
        assertTrue(output.contains("}"));
    }
}

