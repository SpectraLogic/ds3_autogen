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

import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.d3autogen.Ds3TypeMapperParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3TypeMapper;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JavaCodeGenerator_Test {

    private static final Logger LOG = LoggerFactory.getLogger(JavaCodeGenerator_Test.class);
    private static final TestHelper testHelper = TestHelper.getInstance();

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

        assertThat(testHelper.extendsClass("GetObjectRequestHandler", "AbstractRequest", generatedCode), is(true));
    }

    @Test
    public void getBucket() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/GetBucketRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/singleRequestMissingParamTypes.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "GetBucketRequestHandler";
        assertThat(testHelper.extendsClass(requestName, "AbstractRequest", generatedCode), is(true));
        assertThat(testHelper.isOptParamOfType("Delimiter", "String", requestName, generatedCode, true), is(true));
        assertThat(testHelper.isOptParamOfType("Marker", "String", requestName, generatedCode, true), is(true));
        assertThat(testHelper.isOptParamOfType("MaxKeys", "int", requestName, generatedCode, true), is(true));
        assertThat(testHelper.isOptParamOfType("Prefix", "String", requestName, generatedCode, true), is(true));
    }

    @Test
    public void bulkRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/CreatePutJobRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/bulkRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "CreatePutJobRequestHandler";
        assertThat(testHelper.extendsClass(requestName, "BulkRequest", generatedCode), is(true));
        assertThat(testHelper.hasMethod("getCommand", "BulkCommand", TestHelper.Scope.PUBLIC, generatedCode), is(true));
        assertThat(testHelper.isOptParamOfType("IgnoreNamingConflicts", "boolean", requestName, generatedCode, false), is(true));
        assertThat(testHelper.isOptParamOfType("MaxUploadSize", "long", requestName, generatedCode, true), is(true));
        assertThat(testHelper.isOptParamOfType("Priority", "Priority", requestName, generatedCode, true), is(true));
        assertThat(testHelper.isReqParamOfType("Operation", "RestOperationType", requestName, generatedCode, true), is(true));
    }

    @Test
    public void physicalPlacementRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/VerifyPhysicalPlacementForObjectsRequestHandler.java");
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
        assertThat(testHelper.extendsClass(requestName, "AbstractRequest", generatedCode), is(true));
        assertThat(testHelper.isReqParamOfType("Operation", "RestOperationType", requestName, generatedCode, false), is(true));
        assertThat(testHelper.isOptParamOfType("FullDetails", "boolean", requestName, generatedCode, false), is(true));
        assertThat(testHelper.isOptParamOfType("StorageDomainId", "UUID", requestName, generatedCode, false), is(true));
    }

    @Test
    public void multiFileDeleteHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/DeleteObjectsRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3TypeMapperParser typeParser = new Ds3TypeMapperParserImpl();
        final Ds3TypeMapper typeMapper = typeParser.getMap();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/multiFileDeleteRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, typeMapper, fileUtils, Paths.get("."));

        final String generatedCode = new String(outputStream.toByteArray());
        LOG.info("Generated code:\n" + generatedCode);

        final String requestName = "DeleteObjectsRequestHandler";
        assertThat(testHelper.extendsClass(requestName, "AbstractRequest", generatedCode), is(true));
        assertThat(testHelper.isOptParamOfType("RollBack", "boolean", requestName, generatedCode, false), is(true));
        assertThat(testHelper.isOptParamOfType("Quiet", "boolean", requestName, generatedCode, false), is(true));
        assertThat(testHelper.isReqParamOfType("Objects", "List<String>", requestName, generatedCode, false), is(true));
        assertThat(testHelper.isReqParamOfType("Delete", "boolean", requestName, generatedCode, false), is(false));
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
