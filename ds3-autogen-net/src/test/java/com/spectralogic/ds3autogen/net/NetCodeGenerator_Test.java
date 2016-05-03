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
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import com.spectralogic.ds3autogen.net.utils.TestGenerateCode;
import com.spectralogic.ds3autogen.net.utils.TestHelper;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.spectralogic.ds3autogen.net.utils.TestHelper.parserHasPayload;
import static com.spectralogic.ds3autogen.net.utils.TestHelper.parserHasResponseCode;
import static com.spectralogic.ds3autogen.net.utils.TestHelper.hasOptionalChecksum;
import static com.spectralogic.ds3autogen.net.utils.TestHelper.hasOptionalMetadata;
import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.modifyType;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class NetCodeGenerator_Test {

    private final static Logger LOG = LoggerFactory.getLogger(NetCodeGenerator_Test.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.PARSER, LOG);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void simpleRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/singleRequestHandler.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass("GetObjectRequest", "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
    }

    @Test
    public void getBucketRequest_Test() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String requestName = "GetBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "ListBucketResult");

        codeGenerator.generateCode(fileUtils, "/input/getBucketRequest.xml");

        //Generate Request code
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Delimiter", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "MaxKeys", "int", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Prefix", "string", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(new Arguments("String", "BucketName"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "ListBucketResult";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("ListBucketResult", "ListBucketResult", parserCode));
    }

    @Test
    public void getObjectRequest_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/getObjectRequest.xml");

        //Generate Request code
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("ObjectName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Job", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Offset", "long", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("Stream", "DestinationStream"),
                new Arguments("UUID", "Job"),
                new Arguments("long", "Offset"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));
        assertTrue(TestHelper.hasConstructor(requestName, modifyType(constructorArgs, "Guid", "string"), requestCode));

        assertTrue(requestCode.contains("QueryParams.Add(\"job\", job.ToString());"));
        assertTrue(requestCode.contains("QueryParams.Add(\"offset\", offset.ToString());"));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(clientCode.contains("public GetObjectResponse GetObject(GetObjectRequest request)"));
        assertTrue(clientCode.contains("return new GetObjectResponseParser(_netLayer.CopyBufferSize).Parse(request, _netLayer.Invoke(request));"));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses (should be empty due to no response payload)
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));
        assertTrue(responseCode.contains("public GetObjectResponse(IDictionary<string, string> metadata)"));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
    }

    @Test
    public void getObjectSpectraS3Request_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "GetObjectSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "S3Object");

        codeGenerator.generateCode(fileUtils, "/input/getObjectRequestSpectraS3.xml");

        //Generate Request code
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("ObjectName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("BucketId", "string", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "ObjectName"),
                new Arguments("UUID", "BucketId"),
                new Arguments("Stream", "DestinationStream"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));
        assertTrue(TestHelper.hasConstructor(requestName, modifyType(constructorArgs, "Guid", "string"), requestCode));

        assertFalse(requestCode.contains("QueryParams.Add(\"job\", job.ToString());"));
        assertFalse(requestCode.contains("QueryParams.Add(\"offset\", offset.ToString());"));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "S3Object";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("S3Object", "Data", parserCode));
    }

    @Test
    public void CreatePutJobRequest_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "PutBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/createPutJobRequest.xml");

        //Generate Request code
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));
        assertTrue(TestHelper.hasRequiredParam("MaxUploadSize", "long?", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        assertTrue(requestCode.contains("this.QueryParams.Add(\"operation\", \"start_bulk_put\");"));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "MasterObjectList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("MasterObjectList", "MasterObjectList", parserCode));
    }

    @Test
    public void getJobsRequest_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "GetJobsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "JobList");

        codeGenerator.generateCode(fileUtils, "/input/getJobsRequest.xml");

        //Generate Request code
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "BucketId", "Guid", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "FullDetails", "bool", requestCode));

        assertTrue(TestHelper.hasConstructor(requestName, ImmutableList.of(), requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "JobList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("JobList", "Jobs", parserCode));
    }

    @Test
    public void createGetJobRequest_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "GetBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/createGetJobRequest.xml");

        //Generate Request code
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("FullObjects", "IEnumerable<string>", requestCode));
        assertTrue(TestHelper.hasRequiredParam("PartialObjects", "IEnumerable<Ds3PartialObject>", requestCode));
        assertTrue(TestHelper.hasRequiredParam("ChunkClientProcessingOrderGuarantee", "JobChunkClientProcessingOrderGuarantee?", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "Aggregating", "bool?", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Name", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Priority", "Priority?", requestCode));
        assertFalse(TestHelper.hasOptionalParam(requestName, "ChunkClientProcessingOrderGuarantee?", "JobChunkClientProcessingOrderGuarantee", requestCode));

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

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "MasterObjectList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("MasterObjectList", "MasterObjectList", parserCode));
    }

    @Test
    public void createObjectRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String requestName = "PutObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/createObjectRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("ObjectName", "string", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "Job", "Guid", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Offset", "long", requestCode));

        assertTrue(requestCode.contains("private readonly Stream RequestPayload;"));
        assertTrue(requestCode.contains("internal override Stream GetContentStream()"));
        assertTrue(hasOptionalChecksum(requestName, requestCode));
        assertTrue(hasOptionalMetadata(requestName, requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("Stream", "RequestPayload"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasVoidCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses (should be empty due to no response payload)
        final String responseCode = codeGenerator.getResponseCode();
        assertTrue(isEmpty(responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(isEmpty(parserCode));
    }

    @Test
    public void createMultiPartUploadPartRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String requestName = "PutMultiPartUploadPartRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/createMultiPartUploadPartRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("ObjectName", "string", requestCode));

        assertTrue(requestCode.contains("private readonly Stream RequestPayload;"));
        assertTrue(requestCode.contains("internal override Stream GetContentStream()"));
        assertFalse(hasOptionalChecksum(requestName, requestCode));
        assertFalse(hasOptionalMetadata(requestName, requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("Stream", "RequestPayload"),
                new Arguments("Guid", "UploadId"),
                new Arguments("int", "PartNumber"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasVoidCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses (should be empty due to no response payload)
        final String responseCode = codeGenerator.getResponseCode();
        assertTrue(isEmpty(responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(isEmpty(parserCode));
    }

    @Test
    public void createVerifyJobRequestHandler() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "VerifyBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/createVerifyJobRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "Aggregating", "bool", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Name", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Priority", "Priority", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "MasterObjectList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("MasterObjectList", "MasterObjectList", parserCode));
    }

    @Test
    public void ejectStorageDomainBlobsRequestHandler() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "EjectStorageDomainBlobsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/ejectStorageDomainBlobsRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertFalse(TestHelper.hasRequiredParam("Blobs", "bool", requestCode));
        assertTrue(TestHelper.hasRequiredParam("BucketId", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("StorageDomainId", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "EjectLabel", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "EjectLocation", "string", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("Guid", "BucketId"),
                new Arguments("Guid", "StorageDomainId"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));
        assertTrue(TestHelper.hasConstructor(requestName, modifyType(constructorArgs, "Guid", "string"), requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasVoidCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses (should be empty due to no response payload)
        final String responseCode = codeGenerator.getResponseCode();
        assertTrue(isEmpty(responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(isEmpty(parserCode));
    }

    @Test
    public void ejectStorageDomainRequestHandler() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "EjectStorageDomainSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "TapeFailureList");

        codeGenerator.generateCode(fileUtils, "/input/ejectStorageDomainRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("StorageDomainId", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "EjectLabel", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "EjectLocation", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "BucketId", "Guid", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("Guid", "StorageDomainId"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));
        assertTrue(TestHelper.hasConstructor(requestName, modifyType(constructorArgs, "Guid", "string"), requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "TapeFailureList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(207, parserCode));
        assertTrue(parserHasPayload("TapeFailureList", "Data", parserCode));
    }

    @Test
    public void getPhysicalPlacementForObjectsRequestHandler() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "GetPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "PhysicalPlacement");

        codeGenerator.generateCode(fileUtils, "/input/getPhysicalPlacementForObjects.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "StorageDomainId", "Guid", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "PhysicalPlacement";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("PhysicalPlacement", "Data", parserCode));
    }

    @Test
    public void verifyPhysicalPlacementForObjectsRequestHandler() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "VerifyPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "PhysicalPlacement");

        codeGenerator.generateCode(fileUtils, "/input/verifyPhysicalPlacementForObjects.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "StorageDomainId", "Guid", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "PhysicalPlacement";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("PhysicalPlacement", "Data", parserCode));
    }

    @Test
    public void verifyPhysicalPlacementForObjectsWithFullDetailsRequestHandler() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "VerifyPhysicalPlacementForObjectsWithFullDetailsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "BulkObjectList");

        codeGenerator.generateCode(fileUtils, "/input/verifyPhysicalPlacementForObjectsFullDetails.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));
        assertFalse(TestHelper.hasRequiredParam("FullDetails", "bool", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "StorageDomainId", "Guid", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "BulkObjectList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("BulkObjectList", "Data", parserCode));
    }

    @Test
    public void DeleteObjectsRequestHandler() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "DeleteObjectsRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "DeleteResult");

        codeGenerator.generateCode(fileUtils, "/input/deleteObjectsRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("Objects", "IEnumerable<Ds3Object>", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "RollBack", "bool", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("IEnumerable<Ds3Object>", "Objects"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "DeleteResult";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("DeleteResult", "DeleteResult", parserCode));
    }

    @Test
    public void GetBlobPersistenceRequest() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "GetBlobPersistenceSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/");

        codeGenerator.generateCode(fileUtils, "/input/getBlobPersistenceRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("RequestPayload", "string", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("string", "RequestPayload"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "string";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("String", "Data", parserCode));
    }

    @Test
    public void ReplicatePutJobRequest() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String requestName = "ReplicatePutJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/replicatePutJobRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("BucketName", "string", requestCode));
        assertTrue(TestHelper.hasRequiredParam("RequestPayload", "string", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "ConflictResolutionMode", "ReplicationConflictResolutionMode", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Priority", "Priority", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("string", "RequestPayload"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "MasterObjectList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("MasterObjectList", "MasterObjectList", parserCode));
    }

    @Test
    public void getTapesWithFullDetails_Test() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String requestName = "GetTapesWithFullDetailsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "NamedDetailedTapeList");

        codeGenerator.generateCode(fileUtils, "/input/getTapesWithFullDetails.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasOptionalParam(requestName, "AssignedToStorageDomain", "bool", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "BarCode", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "BucketId", "Guid", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "EjectLabel", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "EjectLocation", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "FullOfData", "bool", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "LastPage", "bool", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "PageLength", "int", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "PageOffset", "int", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "PageStartMarker", "Guid", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "PartitionId", "Guid", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "PreviousState", "TapeState", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "SerialNumber", "string", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "State", "TapeState", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "StorageDomainId", "Guid", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "Type", "TapeType", requestCode));
        assertTrue(TestHelper.hasOptionalParam(requestName, "WriteProtected", "bool", requestCode));

        assertTrue(TestHelper.hasConstructor(requestName, ImmutableList.of(), requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responseName = NormalizingContractNamesUtil.toResponseName(requestName);
        final String responsePayloadType = "NamedDetailedTapeList";
        assertTrue(TestHelper.hasConstructor(
                responseName,
                ImmutableList.of(new Arguments(responsePayloadType, "ResponsePayload")),
                responseCode));
        assertTrue(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertTrue(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("NamedDetailedTapeList", "Data", parserCode));
    }

    @Test
    public void allocateJobChunkRequest_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "AllocateJobChunkSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "Objects");

        codeGenerator.generateCode(fileUtils, "/input/allocateJobChunkRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("JobChunkId", "string", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("Guid", "JobChunkId"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));
        assertTrue(TestHelper.hasConstructor(requestName, modifyType(constructorArgs, "Guid", "string"), requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responsePayloadType = "Objects";
        assertFalse(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertFalse(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("Objects", "Objects", parserCode));
    }

    @Test
    public void getJobChunksReadyForClientProcessingRequest_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "GetJobChunksReadyForClientProcessingSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                requestName,
                "./Ds3/Calls/",
                "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/getJobChunksRequest.xml");
        final String requestCode = codeGenerator.getRequestCode();

        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(TestHelper.extendsClass(requestName, "Ds3Request", requestCode));
        assertTrue(TestHelper.hasProperty("Verb", "HttpVerb", requestCode));
        assertTrue(TestHelper.hasProperty("Path", "string", requestCode));

        assertTrue(TestHelper.hasRequiredParam("Job", "string", requestCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("Guid", "Job"));
        assertTrue(TestHelper.hasConstructor(requestName, constructorArgs, requestCode));
        assertTrue(TestHelper.hasConstructor(requestName, modifyType(constructorArgs, "Guid", "string"), requestCode));

        //Generate Client code
        final String commandName = requestName.replace("Request", "");
        final String clientCode = codeGenerator.getClientCode();
        CODE_LOGGER.logFile(clientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasPayloadCommand(commandName, clientCode));

        final String idsClientCode = codeGenerator.getIdsClientCode();
        CODE_LOGGER.logFile(idsClientCode, FileTypeToLog.CLIENT);

        assertTrue(TestHelper.hasIDsCommand(commandName, idsClientCode));

        //Generate Responses
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);

        final String responsePayloadType = "Objects";
        assertFalse(TestHelper.hasRequiredParam("ResponsePayload", responsePayloadType, responseCode));

        //Generate Parser
        final String parserCode = codeGenerator.getParserCode();
        CODE_LOGGER.logFile(parserCode, FileTypeToLog.PARSER);
        assertTrue(hasContent(parserCode));
        assertFalse(parserHasResponseCode(200, parserCode));
        assertTrue(parserHasPayload("MasterObjectList", "MasterObjectList", parserCode));
    }
}
