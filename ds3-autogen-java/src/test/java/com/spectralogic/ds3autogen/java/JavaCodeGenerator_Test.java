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

package com.spectralogic.ds3autogen.java;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedComponentResponseCode;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedSplitRequestCode;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedCode;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.java.test.helpers.JavaCodeGeneratorTestHelper.testDs3Client;
import static com.spectralogic.ds3autogen.java.test.helpers.JavaCodeGeneratorTestHelper.testDs3ClientImpl;
import static com.spectralogic.ds3autogen.java.test.helpers.JavaCodeGeneratorTestHelper.testHasChecksumCode;
import static com.spectralogic.ds3autogen.java.utils.TestHelper.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class JavaCodeGenerator_Test {

    private static final Logger LOG = LoggerFactory.getLogger(JavaCodeGenerator_Test.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void singleRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/singleRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));

        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));

        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void getBucketRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/getBucketRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOptParamOfType("Delimiter", "String", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Marker", "String", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("MaxKeys", "int", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Prefix", "String", requestName, requestGeneratedCode, true));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BucketObjectsApiBean", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void modifyBucketRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "ModifyBucketSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/modifyBucketRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("DataPolicyId", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("UserId", "UUID", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Bucket", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void createVerifyJobRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "CreateVerifyJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/createVerifyJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Priority", "BlobStoreTaskPriority", requestName, requestGeneratedCode, true));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BlobStoreTaskPriority", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobWithChunksContainerApiBean", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void createGetJobRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "CreateGetJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/createGetJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "BulkRequest", requestGeneratedCode));
        assertTrue(hasMethod("getCommand", "BulkCommand", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(isOptParamOfType("ChunkClientProcessingOrderGuarantee", "JobChunkClientProcessingOrderGuarantee", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Priority", "BlobStoreTaskPriority", requestName, requestGeneratedCode, true));
        assertFalse(isReqVariable("BucketName", "String", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.models.JobChunkClientProcessingOrderGuarantee", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.BulkCommand", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BlobStoreTaskPriority", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlProcessingException", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.BulkRequest", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));

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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "BulkResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.BulkResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void createPutJobRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "CreatePutJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/createPutJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "BulkRequest", requestGeneratedCode));
        assertTrue(hasMethod("getCommand", "BulkCommand", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(isOptParamOfType("IgnoreNamingConflicts", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("MaxUploadSize", "long", requestName, requestGeneratedCode, true));
        assertTrue(isOptParamOfType("Priority", "BlobStoreTaskPriority", requestName, requestGeneratedCode, true));
        assertFalse(hasGetter("MaxUploadSize", "long", requestGeneratedCode));
        assertFalse(isReqVariable("BucketName", "String", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.BulkCommand", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BlobStoreTaskPriority", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlProcessingException", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.BulkRequest", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));

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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "BulkResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.BulkResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void getPhysicalPlacementForObjectsRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedSplitRequestCode testGeneratedCode = new TestGeneratedSplitRequestCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/",
                "FullDetails");

        testGeneratedCode.generateCode(fileUtils, "/input/getPhysicalPlacementRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertFalse(isOptParamOfType("FullDetails", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getContentStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.PhysicalPlacementApiBean", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void getPhysicalPlacementForObjectsRequestHandler_FullDetails() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedSplitRequestCode testGeneratedCode = new TestGeneratedSplitRequestCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/",
                "FullDetails");

        testGeneratedCode.generateCode(fileUtils, "/input/getPhysicalPlacementRequest.xml");

        final String fullDetailsRequestName = "GetPhysicalPlacementForObjectsFullDetailsSpectraS3Request";
        final String requestGeneratedCode = testGeneratedCode.getSplitRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(fullDetailsRequestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", fullDetailsRequestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", fullDetailsRequestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "UUID", fullDetailsRequestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getContentStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(requestGeneratedCode.contains("this.getQueryParams().put(\"full_details\", null)"));
        assertFalse(doesConstructorContainParam("FullDetails", "void", fullDetailsRequestName, requestGeneratedCode));
        assertFalse(isReqVariable("FullDetails", "void", requestGeneratedCode));
        assertFalse(hasGetter("FullDetails", "void", requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", fullDetailsRequestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.GET_PHYSICAL_PLACEMENT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(fullDetailsRequestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getSplitResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = fullDetailsRequestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BlobApiBeansContainer", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(fullDetailsRequestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(fullDetailsRequestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void verifyPhysicalPlacementForObjectsRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "VerifyPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedSplitRequestCode testGeneratedCode = new TestGeneratedSplitRequestCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/",
                "FullDetails");

        testGeneratedCode.generateCode(fileUtils, "/input/verifyPhysicalPlacementRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", requestName, requestGeneratedCode, false));
        assertFalse(isOptParamOfType("FullDetails", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getContentStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.PhysicalPlacementApiBean", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void verifyPhysicalPlacementForObjectsRequestHandler_FullDetails() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "VerifyPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedSplitRequestCode testGeneratedCode = new TestGeneratedSplitRequestCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/",
                "FullDetails");

        testGeneratedCode.generateCode(fileUtils, "/input/verifyPhysicalPlacementRequest.xml");

        final String fullDetailsRequestName = "VerifyPhysicalPlacementForObjectsFullDetailsSpectraS3Request";
        final String requestGeneratedCode = testGeneratedCode.getSplitRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(fullDetailsRequestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isReqParamOfType("BucketName", "String", fullDetailsRequestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<Ds3Object>", fullDetailsRequestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "UUID", fullDetailsRequestName, requestGeneratedCode, false));
        assertTrue(hasMethod("getContentStream", "InputStream", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(requestGeneratedCode.contains("this.getQueryParams().put(\"full_details\", null)"));
        assertFalse(doesConstructorContainParam("FullDetails", "void", fullDetailsRequestName, requestGeneratedCode));
        assertFalse(isReqVariable("FullDetails", "void", requestGeneratedCode));
        assertFalse(hasGetter("FullDetails", "void", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3ObjectList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.VERIFY_PHYSICAL_PLACEMENT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(fullDetailsRequestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getSplitResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = fullDetailsRequestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BlobApiBeansContainer", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(fullDetailsRequestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(fullDetailsRequestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void deleteObjectsRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "DeleteObjectsRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/deleteObjectsRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOptParamOfType("RollBack", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Quiet", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<String>", requestName, requestGeneratedCode, false));
        assertFalse(isReqParamOfType("Delete", "boolean", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Contents", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.delete.Delete", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.delete.DeleteObject", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", requestGeneratedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("java.util.ArrayList", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.DeleteResultApiBean", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void createObjectRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "CreateObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/createObjectRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOptParamOfType("Job", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Offset", "long", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Size", "long", requestName, requestGeneratedCode, false));
        assertTrue(hasMethod("withChecksum", requestName, Scope.PUBLIC, requestGeneratedCode));
        assertTrue(hasMethod("withMetaData", requestName, Scope.PUBLIC, requestGeneratedCode));
        assertTrue(isOptVariable("Channel", "SeekableByteChannel", requestGeneratedCode));
        assertTrue(hasGetter("Channel", "SeekableByteChannel", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", requestGeneratedCode));
        assertTrue(hasImport("java.nio.channels.SeekableByteChannel", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

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

        final ImmutableList.Builder<Arguments> streamBuilder = ImmutableList.builder();
        streamBuilder.addAll(commonConstructorArgs);
        streamBuilder.add(new Arguments("UUID", "Job"));
        streamBuilder.add(new Arguments("long", "Offset"));
        streamBuilder.add(new Arguments("InputStream", "Stream"));
        assertTrue(hasConstructor(requestName, streamBuilder.build(), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void getObjectRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/getObjectRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(hasStaticMethod("buildRangeHeaderText", "String", Scope.PRIVATE, requestGeneratedCode));
        assertTrue(isOptParamOfType("Job", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Offset", "long", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("ByteRanges", "Collection<Range>", requestName, requestGeneratedCode, true));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Channel", "WritableByteChannel", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.google.common.base.Joiner", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.collect.ImmutableCollection", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.collect.ImmutableList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Range", requestGeneratedCode));
        assertTrue(hasImport("org.apache.http.entity.ContentType", requestGeneratedCode));
        assertTrue(hasImport("java.nio.channels.WritableByteChannel", requestGeneratedCode));
        assertTrue(hasImport("java.util.Collection", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

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

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.HttpErrorResultApiBean", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }
    
    @Test
    public void getObjectSpectraS3RequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetObjectSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getObjectSpectraS3RequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(hasStaticMethod("buildRangeHeaderText", "String", Scope.PRIVATE, requestGeneratedCode));
        assertFalse(isOptParamOfType("Job", "UUID", requestName, requestGeneratedCode, false));
        assertFalse(isOptParamOfType("Offset", "long", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("ByteRanges", "Collection<Range>", requestName, requestGeneratedCode, true));
        assertTrue(isReqParamOfType("BucketId", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("Channel", "WritableByteChannel", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.google.common.base.Joiner", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.collect.ImmutableCollection", requestGeneratedCode));
        assertTrue(hasImport("com.google.common.collect.ImmutableList", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Range", requestGeneratedCode));
        assertTrue(hasImport("org.apache.http.entity.ContentType", requestGeneratedCode));
        assertTrue(hasImport("java.nio.channels.WritableByteChannel", requestGeneratedCode));
        assertTrue(hasImport("java.util.Collection", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));

        testHasChecksumCode(requestGeneratedCode, requestName);

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/object/\" + objectName", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("UUID", "BucketId"),
                new Arguments("String", "ObjectName"),
                new Arguments("WritableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.add(new Arguments("UUID", "Job"));
        builder.add(new Arguments("long", "Offset"));
        assertFalse(hasConstructor(requestName, builder.build(), requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.S3Object", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void deleteJobCreatedNotificationRegistrationRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "DeleteJobCreatedNotificationRegistrationSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/notifications/");

        testGeneratedCode.generateCode(fileUtils, "/input/deleteJobCreatedNotificationRegistrationRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractDeleteNotificationRequest", requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_created_notification_registration/\" + this.getNotificationId().toString()", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.notifications.AbstractDeleteNotificationRequest", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void createJobCompletedNotificationRegistrationRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "CreateJobCompletedNotificationRegistrationSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/notifications/");

        testGeneratedCode.generateCode(fileUtils, "/input/createJobCompletedNotificationRegistrationRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractCreateNotificationRequest", requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_completed_notification_registration\"", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", requestGeneratedCode));

        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.HttpResponseFormatType", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.NamingConventionType", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.RequestType", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.notifications.AbstractCreateNotificationRequest", requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        assertTrue(isOptParamOfType("Format", "HttpResponseFormatType", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("NamingConvention", "NamingConventionType", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("NotificationHttpMethod", "RequestType", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("JobId", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(hasCopyright(requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobCompletedNotificationRegistration", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void getJobCompletedNotificationRegistrationsRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "GetJobCompletedNotificationRegistrationsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedComponentResponseCode testGeneratedCode = new TestGeneratedComponentResponseCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/notifications/",
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/JobCompletedNotificationRegistrationList.java");

        testGeneratedCode.generateCode(fileUtils, "/input/getJobCompletedNotificationRegistrationsRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractGetNotificationRequest", requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_completed_notification_registration/\" + this.getNotificationId().toString()", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", requestGeneratedCode));

        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.notifications.AbstractGetNotificationRequest", requestGeneratedCode));

        assertTrue(isOptParamOfType("LastPage", "boolean", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("PageLength", "int", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("PageOffset", "int", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("PageStartMarker", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("UserId", "UUID", requestName, requestGeneratedCode, false));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Test", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Test", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Test", "boolean", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3.notifications", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobCompletedNotificationRegistrationList", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void replicatePutJobRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "ReplicatePutJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/replicatePutJobRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "BulkRequest", requestGeneratedCode));
        assertTrue(hasMethod("getCommand", "BulkCommand", Scope.PUBLIC, requestGeneratedCode));

        assertTrue(isOptParamOfType("ConflictResolutionMode", "ReplicationConflictResolutionMode", requestName, requestGeneratedCode, false));
        assertTrue(isOptParamOfType("Priority", "BlobStoreTaskPriority", requestName, requestGeneratedCode, true));
        assertFalse(isReqVariable("BucketName", "String", requestGeneratedCode));
        assertFalse(isReqVariable("Replicate", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.BulkCommand", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.BlobStoreTaskPriority", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlProcessingException", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.BulkRequest", requestGeneratedCode));
        assertTrue(hasImport("java.util.List", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.ReplicationConflictResolutionMode", requestGeneratedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertFalse(requestGeneratedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_PUT, requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));
        assertTrue(constructorHasVoidQueryParam("replicate", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "BulkResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.BulkResponse", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void initiateMultiPartUploadRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "InitiateMultiPartUploadRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/initiateMultiPartUploadRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));

        assertFalse(doesConstructorContainParam("Uploads", "boolean", requestName, requestGeneratedCode));
        assertFalse(isReqVariable("Uploads", "boolean", requestGeneratedCode));
        assertFalse(hasGetter("Uploads", "boolean", requestGeneratedCode));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
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
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.InitiateMultipartUploadResultApiBean", responseGeneratedCode));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void allocateJobChunkRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "AllocateJobChunkSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/allocateJobChunkRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("JobChunkId", "UUID", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));

        assertTrue(hasOperation(Operation.ALLOCATE, requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_chunk/\" + jobChunkId.toString()", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("UUID", "JobChunkId"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobChunkContainerApiBean", responseGeneratedCode));

        assertTrue(hasStaticMethod("parseRetryAfter", "int", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("ALLOCATED, RETRYLATER"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void getJobChunksReadyForClientProcessingRequest() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException {
        final String requestName = "GetJobChunksReadyForClientProcessingSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/");

        testGeneratedCode.generateCode(fileUtils, "/input/getJobChunksReadyForClientProcessingRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("Job", "UUID", requestName, requestGeneratedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));
        assertTrue(hasImport("java.util.UUID", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/_rest_/job_chunk\"", requestGeneratedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("UUID", "Job"));
        assertTrue(hasConstructor(requestName, constructorArgs, requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.JobWithChunksContainerApiBean", responseGeneratedCode));

        assertTrue(hasStaticMethod("parseRetryAfter", "int", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("AVAILABLE, RETRYLATER"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void headBucketRequest() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException {
        final String requestName = "HeadBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/headBucketRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));

        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.Logger", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.LoggerFactory", responseGeneratedCode));

        assertTrue(hasMethod("setStatus", "void", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("EXISTS, DOESNTEXIST, NOTAUTHORIZED, UNKNOWN"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void headObjectRequest() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException {
        final String requestName = "HeadObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/headObjectRequest.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);

        assertTrue(extendsClass(requestName, "AbstractRequest", requestGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", requestGeneratedCode));
        assertTrue(hasCopyright(requestGeneratedCode));

        assertTrue(isReqParamOfType("BucketName", "String", requestName, requestGeneratedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, requestGeneratedCode, false));

        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractRequest", requestGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", requestGeneratedCode));

        assertTrue(doesNotHaveOperation(requestGeneratedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", requestGeneratedCode));

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);

        final String responseName = requestName.replace("Request", "Response");
        assertTrue(extendsClass(responseName, "AbstractResponse", responseGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", responseGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", responseGeneratedCode));
        assertTrue(hasImport("java.io.IOException", responseGeneratedCode));
        assertTrue(hasImport("java.io.InputStream", responseGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.commands.AbstractResponse", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.Logger", responseGeneratedCode));
        assertTrue(hasImport("org.slf4j.LoggerFactory", responseGeneratedCode));

        assertTrue(hasMethod("setStatus", "void", Scope.PRIVATE, responseGeneratedCode));
        assertTrue(responseGeneratedCode.contains("public enum Status"));
        assertTrue(responseGeneratedCode.contains("EXISTS, DOESNTEXIST, UNKNOWN"));

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        testDs3Client(requestName, ds3ClientGeneratedCode);

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        testDs3ClientImpl(requestName, ds3ClientImplGeneratedCode);
    }

    @Test
    public void spectraInternalRequest() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final String requestName = "CreateFakeTapeEnvironmentRequestHandler";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode testGeneratedCode = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

        testGeneratedCode.generateCode(fileUtils, "/input/spectraInternalRequestHandler.xml");

        final String requestGeneratedCode = testGeneratedCode.getRequestGeneratedCode();
        LOG.info("Generated code:\n" + requestGeneratedCode);
        assertTrue(requestGeneratedCode.isEmpty());

        //Test the generated response
        final String responseGeneratedCode = testGeneratedCode.getResponseGeneratedCode();
        LOG.info("Generated code:\n" + responseGeneratedCode);
        assertTrue(responseGeneratedCode.isEmpty());

        //Test the Ds3Client
        final String ds3ClientGeneratedCode = testGeneratedCode.getDs3ClientGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientGeneratedCode);
        assertTrue(ds3ClientGeneratedCode.isEmpty());

        final String ds3ClientImplGeneratedCode = testGeneratedCode.getDs3ClientImplGeneratedCode();
        LOG.info("Generated code:\n" + ds3ClientImplGeneratedCode);
        assertTrue(ds3ClientImplGeneratedCode.isEmpty());
    }

    @Test
    public void wholeXmlDoc() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        final FileUtils fileUtils = new TestFileUtilsImpl();
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/fullXml.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

    }
}
