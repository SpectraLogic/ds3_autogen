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

public class CCodeGeneratorInitRequests_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    /************************* AMAZON S3 *****************************/

    @Test
    public void testGenerateInitAmazonS3DeleteRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/AmazonS3DeleteRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "AmazonS3InitRequestHandler.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* s3_init_delete_bucket_request(const char* bucket_name) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_DELETE, _build_path(\"/\", bucket_name, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitAmazonS3GetRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/AmazonS3GetBucketRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "AmazonS3InitRequestHandler.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* s3_init_get_bucket_request(const char* bucket_name) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_GET, _build_path(\"/\", bucket_name, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitAmazonS3PutRequestNoPayloadHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/AmazonS3PutRequest_NoPayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "AmazonS3InitRequestHandler.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* s3_init_create_bucket_request(const char* bucket_name) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_PUT, _build_path(\"/\", bucket_name, NULL));"));
        assertTrue(output.contains("}"));
    }

    /************************* SPECTRA S3 *****************************/

    @Test
    public void testGenerateInitSpectraS3PutRequestNoPayloadHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/SpectraS3PutRequest_NoPayload.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "SpectraS3InitRequestHandler.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* init_create_bucket_spectra_s3_request(const char* resource_id) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_POST, _build_path(\"/_rest_/bucket\", NULL, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitSpectraS3GetRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/SpectraS3GetSystemInfoRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "SpectraS3InitRequestHandler.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* init_get_system_information_spectra_s3_request(const char* resource_id) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_GET, _build_path(\"/_rest_/system_information\", NULL, NULL));"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testGenerateInitSpectraS3DeleteRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, ParseException {
        final String inputSpecFile = "/input/SpectraS3DeleteRequest.xml";
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream(inputSpecFile));

        final Source source = new Source(CCodeGenerator.getAllEnums(spec), CCodeGenerator.getAllStructs(spec, ImmutableSet.of()), CCodeGenerator.getAllRequests(spec));

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        codeGenerator.processTemplate(source, "SpectraS3InitRequestHandler.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());
        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("ds3_request* init_delete_bucket_spectra_s3_request(const char* resource_id) {"));
        assertTrue(output.contains("    return (ds3_request*) _common_request_init(HTTP_DELETE, _build_path(\"/_rest_/bucket\", resource_id, NULL));"));
        assertTrue(output.contains("}"));
    }
}
