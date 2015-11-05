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
import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.d3autogen.Ds3TypeMapperParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3TypeMapper;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.utils.TestFileUtilImpl;
import com.spectralogic.ds3autogen.java.utils.TestHelper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.java.utils.TestHelper.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JavaCodeGenerator_Test {

    private static final Logger LOG = LoggerFactory.getLogger(JavaCodeGenerator_Test.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void singleRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/GetObjectRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/singleRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        assertTrue(extendsClass("GetObjectRequestHandler", "AbstractRequest", generatedCode));
    }

    @Test
    public void getBucketRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/GetBucketRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/getBucketRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "GetBucketRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(isOptParamOfType("Delimiter", "String", requestName, generatedCode, true));
        assertTrue(isOptParamOfType("Marker", "String", requestName, generatedCode, true));
        assertTrue(isOptParamOfType("MaxKeys", "int", requestName, generatedCode, true));
        assertTrue(isOptParamOfType("Prefix", "String", requestName, generatedCode, true));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", generatedCode));
        assertTrue(doesNotHaveOperation(generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/\" + this.bucketName", generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void modifyBucketRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/ModifyBucketRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/modifyBucketRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "ModifyBucketRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("DataPolicyId", "UUID", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("UserId", "UUID", requestName, generatedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));
        assertTrue(hasImport("java.util.UUID", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", generatedCode));
        assertTrue(doesNotHaveOperation(generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void createVerifyJobRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/CreateVerifyJobRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/createVerifyJobRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "CreateVerifyJobRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(isOptParamOfType("Priority", "Priority", requestName, generatedCode, true));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Priority", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", generatedCode));
        assertFalse(generatedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_VERIFY, generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<String>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void createGetJobRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/CreateGetJobRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/createGetJobRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "CreateGetJobRequestHandler";
        assertTrue(extendsClass(requestName, "BulkRequest", generatedCode));
        assertTrue(hasMethod("getCommand", "BulkCommand", TestHelper.Scope.PUBLIC, generatedCode));

        assertTrue(isOptParamOfType("ChunkClientProcessingOrderGuarantee", "JobChunkClientProcessingOrderGuarantee", requestName, generatedCode, true));
        assertTrue(isOptParamOfType("Priority", "Priority", requestName, generatedCode, true));

        assertTrue(hasImport("com.spectralogic.s3.common.dao.domain.ds3.JobChunkClientProcessingOrderGuarantee", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.BulkCommand", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Priority", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlProcessingException", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", generatedCode));
        assertFalse(generatedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_GET, generatedCode));
        assertTrue(hasCopyright(generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void createPutJobRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/CreatePutJobRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/createPutJobRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "CreatePutJobRequestHandler";
        assertTrue(extendsClass(requestName, "BulkRequest", generatedCode));
        assertTrue(hasMethod("getCommand", "BulkCommand", TestHelper.Scope.PUBLIC, generatedCode));

        assertTrue(isOptParamOfType("IgnoreNamingConflicts", "boolean", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("MaxUploadSize", "long", requestName, generatedCode, true));
        assertTrue(isOptParamOfType("Priority", "Priority", requestName, generatedCode, true));

        assertTrue(hasImport("com.spectralogic.ds3client.BulkCommand", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Priority", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.bulk.Ds3Object", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlProcessingException", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", generatedCode));
        assertFalse(generatedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.START_BULK_PUT, generatedCode));
        assertTrue(hasCopyright(generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<Ds3Object>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void getPhysicalPlacementForObjectsRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/GetPhysicalPlacementForObjectsRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/getPhysicalPlacementRequest.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "GetPhysicalPlacementForObjectsRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<String>", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("FullDetails", "boolean", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "UUID", requestName, generatedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));
        assertTrue(hasImport("java.util.UUID", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", generatedCode));
        assertFalse(generatedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.GET_PHYSICAL_PLACEMENT, generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<String>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void verifyPhysicalPlacementForObjectsRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/spectrads3/VerifyPhysicalPlacementForObjectsRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/verifyPhysicalPlacementRequest.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "VerifyPhysicalPlacementForObjectsRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<String>", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("FullDetails", "boolean", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("StorageDomainId", "UUID", requestName, generatedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));
        assertTrue(hasImport("java.util.UUID", generatedCode));
        assertTrue(hasImport("java.util.List", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.spectrads3", generatedCode));
        assertFalse(generatedCode.contains("RestOperationType"));
        assertTrue(hasOperation(Operation.VERIFY_PHYSICAL_PLACEMENT, generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/_rest_/bucket/\" + this.bucketName", generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<String>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void deleteObjectsRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/DeleteObjectsRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/deleteObjectsRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "DeleteObjectsRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(isOptParamOfType("RollBack", "boolean", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("Quiet", "boolean", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("Objects", "List<String>", requestName, generatedCode, false));
        assertFalse(isReqParamOfType("Delete", "boolean", requestName, generatedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Contents", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.delete.Delete", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.delete.DeleteObject", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.serializer.XmlOutput", generatedCode));
        assertTrue(hasImport("java.io.ByteArrayInputStream", generatedCode));
        assertTrue(hasImport("java.io.InputStream", generatedCode));
        assertTrue(hasImport("java.util.ArrayList", generatedCode));
        assertTrue(hasImport("java.util.List", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", generatedCode));
        assertTrue(doesNotHaveOperation(generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/\" + this.bucketName", generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("List<String>", "Objects"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));
    }

    @Test
    public void createObjectRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/CreateObjectRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/createObjectRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "CreateObjectRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(isOptParamOfType("Job", "UUID", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("Offset", "long", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("Channel", "SeekableByteChannel", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("Size", "long", requestName, generatedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Checksum", generatedCode));
        assertTrue(hasImport("java.io.InputStream", generatedCode));
        assertTrue(hasImport("java.nio.channels.SeekableByteChannel", generatedCode));
        assertTrue(hasImport("java.util.UUID", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", generatedCode));
        assertTrue(doesNotHaveOperation(generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", generatedCode));

        final ImmutableList<Arguments> commonConstructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("long", "Size"));

        final ImmutableList.Builder<Arguments> deprecatedBuilder = ImmutableList.builder();
        deprecatedBuilder.addAll(commonConstructorArgs);
        deprecatedBuilder.add(new Arguments("SeekableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, deprecatedBuilder.build(), generatedCode));

        final ImmutableList.Builder<Arguments> channelBuilder = ImmutableList.builder();
        channelBuilder.addAll(commonConstructorArgs);
        channelBuilder.add(new Arguments("UUID", "Job"));
        channelBuilder.add(new Arguments("long", "Offset"));
        channelBuilder.add(new Arguments("SeekableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, channelBuilder.build(), generatedCode));

        final ImmutableList.Builder<Arguments> streamBuilder = ImmutableList.builder();
        streamBuilder.addAll(commonConstructorArgs);
        streamBuilder.add(new Arguments("UUID", "Job"));
        streamBuilder.add(new Arguments("long", "Offset"));
        streamBuilder.add(new Arguments("InputStream", "Stream"));
        assertTrue(hasConstructor(requestName, streamBuilder.build(), generatedCode));
    }

    @Test
    public void getObjectRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/GetObjectRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/getObjectRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "GetObjectRequestHandler";
        assertTrue(extendsClass(requestName, "AbstractRequest", generatedCode));
        assertTrue(hasStaticMethod("buildRangeHeaderText", "String", TestHelper.Scope.PRIVATE, generatedCode));
        assertTrue(isOptParamOfType("Job", "UUID", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("Offset", "long", requestName, generatedCode, false));
        assertTrue(isOptParamOfType("ByteRange", "Range", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("BucketName", "String", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("ObjectName", "String", requestName, generatedCode, false));
        assertTrue(isReqParamOfType("Channel", "WritableByteChannel", requestName, generatedCode, false));

        assertTrue(hasImport("com.spectralogic.ds3client.HttpVerb", generatedCode));
        assertTrue(hasImport("org.apache.http.entity.ContentType", generatedCode));
        assertTrue(hasImport("java.nio.channels.WritableByteChannel", generatedCode));
        assertTrue(hasImport("java.util.UUID", generatedCode));

        assertTrue(isOfPackage("com.spectralogic.ds3client.commands", generatedCode));
        assertTrue(doesNotHaveOperation(generatedCode));
        assertTrue(hasCopyright(generatedCode));
        assertTrue(hasPath("\"/\" + this.bucketName + \"/\" + this.objectName", generatedCode));

        final ImmutableList<Arguments> constructorArgs = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("WritableByteChannel", "Channel"));
        assertTrue(hasConstructor(requestName, constructorArgs, generatedCode));

        final ImmutableList.Builder builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.add(new Arguments("UUID", "Job"));
        builder.add(new Arguments("long", "Offset"));
        assertTrue(hasConstructor(requestName, builder.build(), generatedCode));
    }

    @Test
    public void wholeXmlDoc() throws IOException, ParserException {
        final FileUtils fileUtils = new TestFileUtilImpl(tempFolder);
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/fullXml.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

    }
}
