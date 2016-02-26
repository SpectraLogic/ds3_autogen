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

package com.spectralogic.ds3autogen.net;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.net.utils.TestGenerateCode;
import com.spectralogic.ds3autogen.net.utils.TestHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NetCodeGenerator_Test {

    private final static Logger LOG = LoggerFactory.getLogger(NetCodeGenerator_Test.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void simpleRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./Ds3/Calls/GetObjectRequest.cs");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(
                NetCodeGenerator_Test.class.getResourceAsStream("/input/singleRequestHandler.xml"));
        final CodeGenerator codeGenerator = new NetCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        assertTrue(TestHelper.extendsClass("GetObjectRequest", "Ds3Request", generatedCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", generatedCode));
        assertTrue(TestHelper.hasProperty("Path", "string", generatedCode));
    }

    @Test
    public void getBucketRequest_Test() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/getBucketRequest.xml");

        final String requestCode = codeGenerator.getRequestCode();
        LOG.info("Generated code:\n" + requestCode);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Delimiter", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "MaxKeys", "int", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Prefix", "string", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(new Arguments("String", "BucketName"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));
    }

    @Test
    public void getObjectRequest_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/getObjectRequest.xml");

        final String requestCode = codeGenerator.getRequestCode();
        LOG.info("Generated code:\n" + requestCode);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("ObjectName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Job", "Guid", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Offset", "long", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("Stream", "DestinationStream"),
                new Arguments("UUID", "Job"),
                new Arguments("long", "Offset"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        assertTrue(requestCode.contains("QueryParams.Add(\"job\", job.ToString());"));
        assertTrue(requestCode.contains("QueryParams.Add(\"offset\", offset.ToString());"));
    }

    @Test
    public void getObjectSpectraS3Request_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException {
        final String requestName = "GetObjectSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/getObjectRequestSpectraS3.xml");

        final String requestCode = codeGenerator.getRequestCode();
        LOG.info("Generated code:\n" + requestCode);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("ObjectName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("BucketId", "Guid", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "ObjectName"),
                new Arguments("UUID", "BucketId"),
                new Arguments("Stream", "DestinationStream"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        assertFalse(requestCode.contains("QueryParams.Add(\"job\", job.ToString());"));
        assertFalse(requestCode.contains("QueryParams.Add(\"offset\", offset.ToString());"));
    }

    @Test
    public void CreatePutJobRequest_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException {
        final String requestName = "PutBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/createPutJobRequest.xml");

        final String requestCode = codeGenerator.getRequestCode();
        LOG.info("Generated code:\n" + requestCode);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "List<Ds3Object>", requestCode));
        assertTrue(TestHelper.hasRequiredParam("MaxUploadSize", "long?", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        assertTrue(requestCode.contains("this.QueryParams.Add(\"operation\", \"start_bulk_put\");"));
    }

    @Test
    public void createGetJobRequest_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException {
        final String requestName = "GetBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/createGetJobRequest.xml");

        final String requestCode = codeGenerator.getRequestCode();
        LOG.info("Generated code:\n" + requestCode);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("FullObjects", "IEnumerable<string>", requestCode));
        assertTrue(TestHelper.hasRequiredParam("PartialObjects", "IEnumerable<Ds3PartialObject>", requestCode));
        assertTrue(TestHelper.hasRequiredParam("ChunkClientProcessingOrderGuarantee", "JobChunkClientProcessingOrderGuarantee?", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "Aggregating", "bool", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Name", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Priority", "Priority", requestCode));
        assertFalse(TestHelper.hasOptionalParam(requestName, "ChunkClientProcessingOrderGuarantee", "JobChunkClientProcessingOrderGuarantee", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("IEnumerable<string>", "FullObjects"),
                new Arguments("IEnumerable<Ds3PartialObject>", "PartialObjects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        final ImmutableList<Arguments> secondConstructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, secondConstructorArgs, requestCode));

        assertTrue(requestCode.contains("this.QueryParams.Add(\"operation\", \"start_bulk_get\");"));
        assertTrue(requestCode.contains("public GetBulkJobSpectraS3Request WithChunkClientProcessingOrderGuarantee(JobChunkClientProcessingOrderGuarantee chunkClientProcessingOrderGuarantee)"));
        assertTrue(requestCode.contains("internal override Stream GetContentStream()"));
        assertTrue(requestCode.contains("private static string BuildChunkOrderingEnumString(JobChunkClientProcessingOrderGuarantee chunkClientProcessingOrderGuarantee)"));
    }
}
