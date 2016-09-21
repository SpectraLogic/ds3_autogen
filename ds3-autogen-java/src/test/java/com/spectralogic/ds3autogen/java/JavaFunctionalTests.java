/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.models.parseresponse.BaseParseResponse;
import com.spectralogic.ds3autogen.java.models.parseresponse.EmptyParseResponse;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedCode;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedComponentResponseCode;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.java.test.helpers.JavaCodeGeneratorTestHelper.*;
import static com.spectralogic.ds3autogen.java.utils.TestHelper.*;
import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.modifyType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class JavaFunctionalTests {

    private static final Logger LOG = LoggerFactory.getLogger(JavaFunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.PARSER, LOG);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void singleRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/singleRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));

        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));

        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void completeMultiPartUploadRequest() throws IOException, TemplateModelException {
        final String requestName = "CompleteMultiPartUploadRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/completeMultiPartUploadRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("UploadId", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("RequestPayload", "CompleteMultipartUpload", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.multipart.CompleteMultipartUpload", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.net.UrlEscapers", requestGeneratedCode));
        assertTrue(hasImport("java.nio.charset.Charset", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("UUID", "UploadId"),
                new Arguments("CompleteMultipartUpload", "RequestPayload"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(constructorArgs, "UUID", "String"), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.CompleteMultipartUploadResult", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void getBucketRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "GetBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/getBucketRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOptParamOfType("Delimiter", "String", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Marker", "String", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("MaxKeys", "int", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Prefix", "String", requestName, requestGeneratedCode, true));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.ListBucketResult", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.GetBucketResponse", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.ListBucketResult", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.ReadableByteChannelInputStream", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.io.InputStream", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        final BaseParseResponse expectedParsing = new BaseParseResponse(responseName, "ListBucketResult");
        assertTrue(responseParserCode.contains(expectedParsing.toJavaCode()));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200};"));
    }

    @Test
    public void modifyBucketRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "ModifyBucketSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/modifyBucketRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("DataPolicyId", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("UserId", "String", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Bucket", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"Bucket\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));


        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void createVerifyJobRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "VerifyBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/createVerifyJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Priority", "Priority", requestName, requestGeneratedCode, true));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Priority", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_VERIFY, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobWithChunksContainerApiBean", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"JobWithChunksContainerApiBean\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.spectrads3." + responseName, responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        final BaseParseResponse expectedParsing = new BaseParseResponse(responseName, "JobWithChunksContainerApiBean");

        assertTrue(responseParserCode.contains(expectedParsing.toJavaCode()));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200};"));
    }

    @Test
    public void createGetJobRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "GetBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/createGetJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "BulkRequest", requestGeneratedCode));
        assertTrue(hasMethod("getCommand", "BulkCommand", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(isOptParamOfType("ChunkClientProcessingOrderGuarantee", "JobChunkClientProcessingOrderGuarantee", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Priority", "Priority", requestName, requestGeneratedCode, true));
        assertFalse(isReqVariable("BucketName", "String", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.models.JobChunkClientProcessingOrderGuarantee", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.BulkCommand", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Priority", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.BulkRequest", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlProcessingException", requestGeneratedCode));

        assertFalse(requestGeneratedCode.contains("XmlProcessingException"));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_GET, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "BulkResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.BulkResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"JobWithChunksContainerApiBean\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        checkBulkResponseParserCode(responseName, responseParserCode);
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200};"));
    }

    @Test
    public void createPutJobRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "PutBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/createPutJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "BulkRequest", requestGeneratedCode));
        assertTrue(hasMethod("getCommand", "BulkCommand", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(isOptParamOfType("IgnoreNamingConflicts", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("MaxUploadSize", "long", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Priority", "Priority", requestName, requestGeneratedCode, true));
        assertFalse(hasGetter("MaxUploadSize", "long", requestGeneratedCode));
        assertFalse(isReqVariable("BucketName", "String", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.BulkCommand", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Priority", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.BulkRequest", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));

        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlProcessingException", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("XmlProcessingException"));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_PUT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "BulkResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.BulkResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"JobWithChunksContainerApiBean\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        checkBulkResponseParserCode(responseName, responseParserCode);
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200};"));
    }

    @Test
    public void getPhysicalPlacementForObjectsRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "GetPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getPhysicalPlacementRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertFalse(isOptParamOfType("FullDetails", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "String", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.GET_PHYSICAL_PLACEMENT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.PhysicalPlacement", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"PhysicalPlacement\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void getPhysicalPlacementForObjectsRequestHandler_FullDetails() throws IOException, TemplateModelException {
        final String requestName = "GetPhysicalPlacementForObjectsWithFullDetailsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getPhysicalPlacementFullDetailsRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "String", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(requestGeneratedCode.contains("this.getQueryParams().put(\"full_details\", null)"));
        assertFalse(doesConstructorContainParam("FullDetails", "void", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("FullDetails", "void", requestGeneratedCode));
        assertFalse(hasGetter("FullDetails", "void", requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("java.nio.charset.Charset", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.GET_PHYSICAL_PLACEMENT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BulkObjectList", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"BulkObjectList\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void verifyPhysicalPlacementForObjectsRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "VerifyPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/verifyPhysicalPlacementRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertFalse(isOptParamOfType("FullDetails", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "String", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("java.nio.charset.Charset", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.VERIFY_PHYSICAL_PLACEMENT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.PhysicalPlacement", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"PhysicalPlacement\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"SHOW\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void verifyPhysicalPlacementForObjectsRequestHandler_FullDetails() throws IOException, TemplateModelException {
        final String requestName = "VerifyPhysicalPlacementForObjectsWithFullDetailsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/verifyPhysicalPlacementFullDetailsRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "String", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(requestGeneratedCode.contains("this.getQueryParams().put(\"full_details\", null)"));
        assertFalse(doesConstructorContainParam("FullDetails", "void", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("FullDetails", "void", requestGeneratedCode));
        assertFalse(hasGetter("FullDetails", "void", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("java.nio.charset.Charset", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.VERIFY_PHYSICAL_PLACEMENT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BulkObjectList", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"BulkObjectList\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"SHOW\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void deleteObjectsRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "DeleteObjectsRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/deleteObjectsRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOptParamOfType("RollBack", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Quiet", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<String>", requestName, requestGeneratedCode, false));
        assertFalse(isReqParamOfType("Delete", "boolean", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Contents", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.delete.Delete", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.delete.DeleteObject", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("java.util.ArrayList", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
        assertTrue(hasImport("java.nio.charset.Charset", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<String>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.DeleteResult", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void deleteObject_Test() throws IOException, TemplateModelException {
        final String requestName = "DeleteObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/deleteObject.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Replicate", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("RollBack", "boolean", requestName, requestGeneratedCode, false));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.DeleteObjectResponse", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        final EmptyParseResponse expectedParsing = new EmptyParseResponse(responseName);
        assertTrue(responseParserCode.contains(expectedParsing.toJavaCode()));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{204};"));
    }

    @Test
    public void createObjectRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "PutObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/createObjectRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertFalse(requestGeneratedCode.contains("CreateObjectRequest"));

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOptParamOfType("Job", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Offset", "long", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Size", "long", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("withChecksum", requestName, Scope.PUBLIC, requestGeneratedCode));
        assertTrue(hasMethod("withMetaData", requestName, Scope.PUBLIC, requestGeneratedCode));
        assertTrue(isOptVariable("Channel", "SeekableByteChannel", requestGeneratedCode));
        assertTrue(hasGetter("Channel", "SeekableByteChannel", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("java.nio.channels.SeekableByteChannel", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.SeekableByteChannelInputStream", requestGeneratedCode));

        testHasChecksumCode(requestGeneratedCode, requestName);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", requestGeneratedCode));

        final ImmutableList<Arguments> commonConstructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("long", "Size"));

        final ImmutableList.Builder<Arguments> deprecatedBuilder = ImmutableList.builder();
        deprecatedBuilder.addAll(commonConstructorArgs);
        deprecatedBuilder.add(new Arguments("SeekableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, deprecatedBuilder.build(), requestGeneratedCode));

        final ImmutableList.Builder<Arguments> channelBuilder = ImmutableList.builder();
        channelBuilder.addAll(commonConstructorArgs);
        channelBuilder.add(new Arguments("UUID", "Job"));
        channelBuilder.add(new Arguments("long", "Offset"));
        channelBuilder.add(new Arguments("SeekableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, channelBuilder.build(), requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(channelBuilder.build(), "UUID", "String"), requestGeneratedCode));

        final ImmutableList.Builder<Arguments> streamBuilder = ImmutableList.builder();
        streamBuilder.addAll(commonConstructorArgs);
        streamBuilder.add(new Arguments("UUID", "Job"));
        streamBuilder.add(new Arguments("long", "Offset"));
        streamBuilder.add(new Arguments("InputStream", "Stream"));
        assertTrue(hasConstructor(requestName, streamBuilder.build(), requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(streamBuilder.build(), "UUID", "String"), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertFalse(hasImport("java.io.InputStream", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.PutObjectResponse", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        final EmptyParseResponse expectedParsing = new EmptyParseResponse(responseName);
        assertTrue(responseParserCode.contains(expectedParsing.toJavaCode()));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200};"));
    }

    @Test
    public void getObjectRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/getObjectRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(hasStaticMethod("buildRangeHeaderText", "String", Scope.PRIVATE, requestGeneratedCode));
        assertTrue(isOptParamOfType("Job", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Offset", "long", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("ByteRanges", "Collection<Range>", requestName, requestGeneratedCode, true));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Channel", "WritableByteChannel", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.google.common.base.Joiner", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.collect.ImmutableCollection", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.collect.ImmutableList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.common.Range", requestGeneratedCode));
        assertTrue(hasImport("org.apache.http.entity.ContentType", requestGeneratedCode));
        assertTrue(hasImport("java.nio.channels.WritableByteChannel", requestGeneratedCode));
        assertTrue(hasImport("java.util.Collection", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));

        testHasChecksumCode(requestGeneratedCode, requestName);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("WritableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.add(new Arguments("UUID", "Job"));
        builder.add(new Arguments("long", "Offset"));
        assertTrue(hasConstructor(requestName, builder.build(), requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(builder.build(), "UUID", "String"), requestGeneratedCode));

        assertTrue(requestGeneratedCode.contains("this.getQueryParams().put(\"job\", job.toString())"));
        assertTrue(requestGeneratedCode.contains("this.getQueryParams().put(\"offset\", Long.toString(offset))"));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Error", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.Metadata", responseGeneratedCode));
        assertTrue(hasImport("java.nio.channels.WritableByteChannel", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.IOUtils", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.PerformanceUtils", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.exceptions.ContentLengthNotMatchException", responseGeneratedCode));

        assertTrue(responseGeneratedCode.contains("final long totalBytes = IOUtils.copy(responseStream, destinationChannel, bufferSize, objName, false)"));
        assertFalse(responseGeneratedCode.contains("final long totalBytes = IOUtils.copy(responseStream, destinationChannel, bufferSize);"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }
    
    @Test
    public void getObjectSpectraS3RequestHandler() throws IOException, TemplateModelException {
        final String requestName = "GetObjectDetailsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getObjectSpectraS3RequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketId", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));

        assertFalse(hasStaticMethod("buildRangeHeaderText", "String", Scope.PRIVATE, requestGeneratedCode));
        assertFalse(isOptParamOfType("ByteRanges", "Collection<Range>", requestName, requestGeneratedCode, true));
        assertFalse(isReqParamOfType("Channel", "WritableByteChannel", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));

        assertFalse(hasImport("com.google.common.base.Joiner", requestGeneratedCode));
        assertFalse(hasImport("com.google.common.collect.ImmutableCollection", requestGeneratedCode));
        assertFalse(hasImport("com.google.common.collect.ImmutableList", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.models.common.Range", requestGeneratedCode));
        assertFalse(hasImport("org.apache.http.entity.ContentType", requestGeneratedCode));
        assertFalse(hasImport("java.nio.channels.WritableByteChannel", requestGeneratedCode));
        assertFalse(hasImport("java.util.Collection", requestGeneratedCode));


        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/object/\" + objectName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketId"),
                new Arguments("String", "ObjectName"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.S3Object", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.exceptions.ContentLengthNotMatchException", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"S3Object\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"SHOW\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"OBJECT\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void deleteJobCreatedNotificationRegistrationRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "DeleteJobCreatedNotificationRegistrationSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/notifications/");

        testGeneratedCode.generateCode(fileUtils, "/input/deleteJobCreatedNotificationRegistrationRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractDeleteNotificationRequest", requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_created_notification_registration/\" + this.getNotificationId().toString()", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractDeleteNotificationRequest", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("UUID", "NotificationId"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(constructorArgs, "UUID", "String"), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertFalse(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"DELETE\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"JOB_CREATED_NOTIFICATION_REGISTRATION\")"));
        assertFalse(ds3ClientGeneratedCode.contains("@ResponsePayloadModel("));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void createJobCompletedNotificationRegistrationRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "PutJobCompletedNotificationRegistrationSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/notifications/");

        testGeneratedCode.generateCode(fileUtils, "/input/createJobCompletedNotificationRegistrationRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractCreateNotificationRequest", requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_completed_notification_registration\"", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", requestGeneratedCode));

        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.HttpResponseFormatType", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.NamingConventionType", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.RequestType", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractCreateNotificationRequest", requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        assertTrue(isOptParamOfType("Format", "HttpResponseFormatType", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("NamingConvention", "NamingConventionType", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("NotificationHttpMethod", "RequestType", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("JobId", "String", requestName, requestGeneratedCode, false));
        assertTrue(hasCopyright(requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "NotificationEndPoint"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("this.getQueryParams().put(\"notification_end_point\", "));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobCompletedNotificationRegistration", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"JobCompletedNotificationRegistration\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"CREATE\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"JOB_COMPLETED_NOTIFICATION_REGISTRATION\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void getJobCompletedNotificationRegistrationsRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "GetJobCompletedNotificationRegistrationsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedComponentResponseCode testGeneratedCode = new TestGeneratedComponentResponseCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/notifications/",
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/JobCompletedNotificationRegistrationList.java");

        testGeneratedCode.generateCode(fileUtils, "/input/getJobCompletedNotificationRegistrationsRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractPaginationRequest", requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_completed_notification_registration\"", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", requestGeneratedCode));

        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractPaginationRequest", requestGeneratedCode));

        assertTrue(isOptParamOfType("LastPage", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("PageLength", "int", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("PageOffset", "int", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("PageStartMarker", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("UserId", "String", requestName, requestGeneratedCode, false));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        assertTrue(hasConstructor(requestName, ImmutableList.of(), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractPaginationResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractPaginationResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobCompletedNotificationRegistrationList", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"JobCompletedNotificationRegistrationList\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"LIST\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"JOB_COMPLETED_NOTIFICATION_REGISTRATION\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void replicatePutJobRequest() throws IOException, TemplateModelException {
        final String requestName = "ReplicatePutJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/replicatePutJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));

        assertTrue(isOptParamOfType("ConflictResolutionMode", "ReplicationConflictResolutionMode", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Priority", "Priority", requestName, requestGeneratedCode, true));
        assertTrue(isReqVariable("BucketName", "String", requestGeneratedCode));
        assertTrue(isReqVariable("RequestPayload", "String", requestGeneratedCode));
        assertFalse(isReqVariable("Replicate", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.Guard", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.ReplicationConflictResolutionMode", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Priority", requestGeneratedCode));
        assertTrue(hasImport("java.nio.charset.Charset", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_PUT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "RequestPayload"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(constructorHasVoidQueryParam("replicate", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.MasterObjectList", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"MasterObjectList\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void initiateMultiPartUploadRequest() throws IOException, TemplateModelException {
        final String requestName = "InitiateMultiPartUploadRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/initiateMultiPartUploadRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Uploads", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Uploads", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Uploads", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(constructorHasVoidQueryParam("uploads", requestGeneratedCode));
        assertFalse(constructorHasVarAssignment("uploads", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.InitiateMultipartUploadResult", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void allocateJobChunkRequestHandler() throws IOException, TemplateModelException {
        final String requestName = "AllocateJobChunkSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/allocateJobChunkRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("JobChunkId", "String", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));

        assertTrue(hasOperation(Operation.ALLOCATE, requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_chunk/\" + jobChunkId", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("UUID", "JobChunkId"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(constructorArgs, "UUID", "String"), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Objects", responseGeneratedCode));

        assertTrue(hasStaticMethod("parseRetryAfter", "int", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("ALLOCATED, RETRYLATER"));
        assertTrue(responseGeneratedCode.contains("this.checkStatusCode(200, 307, 503);"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"Objects\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"MODIFY\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"JOB_CHUNK\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));

        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.spectrads3." + responseName, responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        assertTrue(hasImport("com.spectralogic.ds3client.models.Objects", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.ReadableByteChannelInputStream", responseParserCode));
        assertTrue(hasImport("java.io.InputStream", responseParserCode));

        assertTrue(responseParserCode.contains("return new AllocateJobChunkSpectraS3Response(result, 0, Status.ALLOCATED);"));
        assertTrue(responseParserCode.contains("return new AllocateJobChunkSpectraS3Response(null, parseRetryAfter(response), Status.RETRYLATER);"));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200, 307, 503};"));
    }

    @Test
    public void getJobChunksReadyForClientProcessingRequest() throws IOException, TemplateModelException {
        final String requestName = "GetJobChunksReadyForClientProcessingSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getJobChunksReadyForClientProcessingRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("Job", "String", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_chunk\"", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("UUID", "Job"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(constructorArgs, "UUID", "String"), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobWithChunksContainerApiBean", responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("import static com.spectralogic.ds3client.utils.Guard.isNullOrEmpty;"));

        assertTrue(hasStaticMethod("parseRetryAfter", "int", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("AVAILABLE, RETRYLATER"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"JobWithChunksContainerApiBean\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"LIST\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"JOB_CHUNK\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void headBucketRequest() throws IOException, TemplateModelException {
        final String requestName = "HeadBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/headBucketRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));

        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertFalse(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.Logger", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.LoggerFactory", responseGeneratedCode));

        assertTrue(hasMethod("setStatus", "void", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("EXISTS, DOESNTEXIST, NOTAUTHORIZED, UNKNOWN"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));

        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands." + responseName, responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        assertTrue(responseParserCode.contains("return new HeadBucketResponse(Status.EXISTS);"));
        assertTrue(responseParserCode.contains("return new HeadBucketResponse(Status.NOTAUTHORIZED);"));
        assertTrue(responseParserCode.contains("return new HeadBucketResponse(Status.DOESNTEXIST);"));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200, 403, 404};"));
    }

    @Test
    public void headObjectRequest() throws IOException, TemplateModelException {
        final String requestName = "HeadObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/headObjectRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));

        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertFalse(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.Logger", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.LoggerFactory", responseGeneratedCode));

        assertTrue(hasMethod("setStatus", "void", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("EXISTS, DOESNTEXIST, UNKNOWN"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void getJobRequest() throws IOException, TemplateModelException {
        final String requestName = "GetJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("JobId", "String", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job/\" + jobId", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("UUID", "JobId"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(constructorArgs, "UUID", "String"), requestGeneratedCode));

        assertTrue(requestGeneratedCode.contains("public int hashCode()"));
        assertTrue(requestGeneratedCode.contains("public boolean equals(final Object obj)"));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.MasterObjectList", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"MasterObjectList\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"SHOW\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"JOB\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void GetBlobPersistenceRequest_Test() throws IOException, TemplateModelException {
        final String requestName = "GetBlobPersistenceSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getBlobPersistenceRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));

        assertTrue(isReqVariable("RequestPayload", "String", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.Guard", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("java.nio.charset.Charset", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "RequestPayload"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.interfaces.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("org.apache.commons.io.IOUtils", responseGeneratedCode));
        assertTrue(hasImport("java.nio.charset.StandardCharsets", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void getBucketsAMZ_Test() throws IOException, TemplateModelException {
        final String requestName = "GetServiceRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/getBucketsAMZ.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        //Verify that name mapped from GetBuckets to GetService
        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.ListAllMyBucketsResult", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.ReadableByteChannelInputStream", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.io.InputStream", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.GetServiceResponse", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        final BaseParseResponse expectedParsing = new BaseParseResponse(responseName, "ListAllMyBucketsResult");
        assertTrue(responseParserCode.contains(expectedParsing.toJavaCode()));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{200};"));
    }

    @Test
    public void deleteBucket_Test() throws IOException, TemplateModelException {
        final String requestName = "DeleteBucketSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/deleteBucket.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Replicate", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Force", "boolean", requestName, requestGeneratedCode, false));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseParserCode));
        assertTrue(hasImport("java.io.IOException", responseParserCode));
        assertTrue(hasImport("java.nio.channels.ReadableByteChannel", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.spectrads3.DeleteBucketSpectraS3Response", responseParserCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", responseParserCode));

        final EmptyParseResponse expectedParsing = new EmptyParseResponse(responseName);
        assertTrue(responseParserCode.contains(expectedParsing.toJavaCode()));
        assertTrue(responseParserCode.contains("private final int[] expectedStatusCodes = new int[]{204};"));
    }

    @Test
    public void getBucketsSpectraS3_Test() throws IOException, TemplateModelException {
        final String requestName = "GetBucketsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedComponentResponseCode testGeneratedCode = new TestGeneratedComponentResponseCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/",
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/BucketList.java");

        testGeneratedCode.generateCode(fileUtils, "/input/getBucketsSpectraS3.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        //Verify that name mapped from GetBuckets to GetService
        assertTrue(extendsClass(requestName, "AbstractPaginationRequest", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractPaginationResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"BucketList\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"LIST\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"BUCKET\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void createMultiPartUploadPartRequest() throws IOException, TemplateModelException {
        final String requestName = "PutMultiPartUploadPartRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/createMultiPartUploadPartRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("PartNumber", "int", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("UploadId", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Stream", "InputStream", requestName, requestGeneratedCode, false));

        assertTrue(isOptVariable("Channel", "SeekableByteChannel", requestGeneratedCode));
        assertTrue(hasGetter("Channel", "SeekableByteChannel", requestGeneratedCode));

        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.net.UrlEscapers", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.utils.SeekableByteChannelInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.nio.channels.SeekableByteChannel", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", requestGeneratedCode));

        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.add(new Arguments("String", "BucketName"));
        builder.add(new Arguments("String", "ObjectName"));
        builder.add(new Arguments("int", "PartNumber"));
        builder.add(new Arguments("long", "Size"));
        builder.add(new Arguments("UUID", "UploadId"));

        final ImmutableList.Builder<Arguments> channelBuilder = ImmutableList.builder();
        channelBuilder.addAll(builder.build());
        channelBuilder.add(new Arguments("SeekableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, channelBuilder.build(), requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(channelBuilder.build(), "UUID", "String"), requestGeneratedCode));

        final ImmutableList.Builder<Arguments> streamBuilder = ImmutableList.builder();
        streamBuilder.addAll(builder.build());
        streamBuilder.add(new Arguments("InputStream", "Stream"));
        assertTrue(hasConstructor(requestName, channelBuilder.build(), requestGeneratedCode));
        assertTrue(hasConstructor(requestName, modifyType(channelBuilder.build(), "UUID", "String"), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void spectraInternalRequest() throws IOException, TemplateModelException {
        final String requestName = "CreateFakeTapeEnvironmentRequestHandler";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/spectraInternalRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);
        assertTrue(requestGeneratedCode.isEmpty());

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        assertTrue(responseGeneratedCode.isEmpty());

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        assertTrue(ds3ClientGeneratedCode.isEmpty());

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        assertTrue(ds3ClientImplGeneratedCode.isEmpty());

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        assertTrue(responseParserCode.isEmpty());
    }

    @Test
    public void getObjectsDetailsRequest_Test() throws IOException, TemplateModelException {
        final String requestName = "GetObjectsDetailsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedComponentResponseCode testGeneratedCode = new TestGeneratedComponentResponseCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/",
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/S3ObjectList.java");

        testGeneratedCode.generateCode(fileUtils, "/input/getObjectsDetailsRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestGeneratedCode, FileTypeToLog.REQUEST);

        //Verify that name mapped from GetObjects to GetObjectsDetailsSpectraS3
        assertTrue(extendsClass(requestName, "AbstractPaginationRequest", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        CODE_LOGGER.logFile(responseGeneratedCode, FileTypeToLog.RESPONSE);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractPaginationResponse", responseGeneratedCode));

        assertFalse(responseGeneratedCode.contains("private Integer pagingTruncated;"));
        assertFalse(responseGeneratedCode.contains("private Integer pagingTotalResultCount;"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientGeneratedCode, FileTypeToLog.CLIENT);
        testDs3Client(requestName, ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.contains("@ResponsePayloadModel(\"S3ObjectList\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Action(\"LIST\")"));
        assertTrue(ds3ClientGeneratedCode.contains("@Resource(\"OBJECT\")"));

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientImplGeneratedCode, FileTypeToLog.CLIENT);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);

        //Test the response parser
        final String responseParserCode = testGeneratedCode.getResponseParserGeneratedCode();
        CODE_LOGGER.logFile(responseParserCode, FileTypeToLog.PARSER);
        //TODO test
    }

    @Test
    public void wholeXmlDoc() throws IOException, TemplateModelException {
        final FileUtils fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaFunctionalTests.class.getResourceAsStream("/input/fullXml.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."), new Ds3DocSpecEmptyImpl());

    }
}
