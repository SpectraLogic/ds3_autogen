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
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedComponentResponseCode;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedModelCode;
import com.spectralogic.ds3autogen.java.utils.TestHelper;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.spectralogic.ds3autogen.java.utils.TestHelper.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class JavaCodeGenerator_Models_Test {

    private static final Logger LOG = LoggerFactory.getLogger(JavaCodeGenerator_Models_Test.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void bucketAclType() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String modelName = "BucketAcl";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/BucketAclType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(hasModelVariable("BucketId", "UUID", modelGeneratedCode));
        assertTrue(hasModelVariable("GroupId", "UUID", modelGeneratedCode));
        assertTrue(hasModelVariable("Id", "UUID", modelGeneratedCode));
        assertTrue(hasModelVariable("Permission", "BucketAclPermission", modelGeneratedCode));
        assertTrue(hasModelVariable("UserId", "UUID", modelGeneratedCode));

        assertTrue(hasImport("com.fasterxml.jackson.annotation.JsonProperty", modelGeneratedCode));
        assertTrue(hasImport("java.util.UUID", modelGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.models.BucketAclPermission", modelGeneratedCode));
        assertFalse(hasImport("java.util.List", modelGeneratedCode));

        final ImmutableList<Element> constructorArgs = ImmutableList.of(
                new Element("BucketId", "UUID", ""),
                new Element("GroupId", "UUID", ""),
                new Element("Id", "UUID", ""),
                new Element("Permission", "BucketAclPermission", ""),
                new Element("UserId", "UUID", ""));
        assertFalse(hasModelConstructor(modelName, constructorArgs, modelGeneratedCode));
        assertTrue(hasModelConstructor(modelName, ImmutableList.<Element>of(), modelGeneratedCode));
    }

    @Test
    public void physicalPlacementApiBeanModel() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String modelName = "PhysicalPlacement";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/PhysicalPlacementApiBeanType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(hasModelVariable("Pools", "Pools", "List<Pool>", true, modelGeneratedCode));
        assertTrue(hasModelVariable("Tapes", "Tapes", "List<Tape>", true, modelGeneratedCode));

        assertTrue(hasImport("com.fasterxml.jackson.annotation.JsonProperty", modelGeneratedCode));
        assertTrue(hasImport("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper", modelGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.models.Pool", modelGeneratedCode));
        assertFalse(hasImport("com.spectralogic.ds3client.models.Tape", modelGeneratedCode));
        assertTrue(hasImport("java.util.List", modelGeneratedCode));

        final ImmutableList<Element> constructorArgs = ImmutableList.of(
                new Element("Pools", "array", "Pool"),
                new Element("Tapes", "array", "Tape"));
        assertFalse(hasModelConstructor(modelName, constructorArgs, modelGeneratedCode));
        assertTrue(hasModelConstructor(modelName, ImmutableList.<Element>of(), modelGeneratedCode));
    }

    @Test
    public void requestType() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String modelName = "RequestType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/requestType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(TestHelper.isEnumClass(modelName, modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("DELETE", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("GET", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("HEAD", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("POST", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("PUT", modelGeneratedCode));
    }

    @Test
    public void checksumType() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String modelName = "ChecksumType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/checksumType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));
        assertTrue(modelGeneratedCode.contains("public abstract class " + modelName + " {"));

        assertTrue(hasImport("org.apache.commons.codec.binary.Base64", modelGeneratedCode));
        assertTrue(hasImport("org.apache.commons.codec.binary.Base64", modelGeneratedCode));

        assertTrue(TestHelper.enumContainsValue("CRC_32", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("CRC_32C", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("MD5", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("SHA_256", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("SHA_512", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("NONE", modelGeneratedCode));
    }

    @Test
    public void blobStoreTaskPriority() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String modelName = "Priority";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/BlobStoreTaskPriorityType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(TestHelper.isEnumClass(modelName, modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("CRITICAL", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("URGENT", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("HIGH", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("NORMAL", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("LOW", modelGeneratedCode));
        assertTrue(TestHelper.enumContainsValue("BACKGROUND", modelGeneratedCode));
    }

    @Test
    public void s3Object_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String modelName = "S3Object";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/s3objectType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(hasModelVariable("BucketId", "UUID", modelGeneratedCode));
        assertTrue(hasModelVariable("CreationDate", "Date", modelGeneratedCode));
        assertTrue(hasModelVariable("Id", "UUID", modelGeneratedCode));
        assertTrue(hasModelVariable("Latest", "boolean", modelGeneratedCode));
        assertTrue(hasModelVariable("Name", "String", modelGeneratedCode));
        assertTrue(hasModelVariable("Type", "S3ObjectType", modelGeneratedCode));
        assertTrue(hasModelVariable("Version", "long", modelGeneratedCode));

        assertTrue(hasImport("com.fasterxml.jackson.annotation.JsonProperty", modelGeneratedCode));
        assertTrue(hasImport("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement", modelGeneratedCode));
        assertTrue(hasImport("java.util.Objects", modelGeneratedCode));
        assertTrue(hasImport("java.util.UUID", modelGeneratedCode));
        assertTrue(hasImport("java.util.Date", modelGeneratedCode));

        assertTrue(hasModelConstructor(modelName, ImmutableList.<Element>of(), modelGeneratedCode));

        assertTrue(modelGeneratedCode.contains("public int hashCode()"));
        assertTrue(modelGeneratedCode.contains("public boolean equals(final Object obj)"));
    }

    @Test
    public void bulkObject_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String modelName = "BulkObject";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/bulkObjectType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(hasModelVariable("Id", "UUID", modelGeneratedCode));
        assertTrue(hasParamAttribute("InCache", "Boolean", modelGeneratedCode));
        assertTrue(hasParamAttribute("Latest", "boolean", modelGeneratedCode));
        assertTrue(hasParamAttribute("Length", "long", modelGeneratedCode));
        assertTrue(hasParamAttribute("Name", "String", modelGeneratedCode));
        assertTrue(hasParamAttribute("Offset", "long", modelGeneratedCode));
        assertTrue(hasModelVariable("PhysicalPlacement", "PhysicalPlacement", modelGeneratedCode));
        assertTrue(hasParamAttribute("Version", "long", modelGeneratedCode));

        assertTrue(hasImport("com.fasterxml.jackson.annotation.JsonProperty", modelGeneratedCode));
        assertTrue(hasImport("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement", modelGeneratedCode));
        assertTrue(hasImport("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty", modelGeneratedCode));
        assertTrue(hasImport("java.util.UUID", modelGeneratedCode));

        assertTrue(hasModelConstructor(modelName, ImmutableList.<Element>of(), modelGeneratedCode));

        assertTrue(modelGeneratedCode.contains("public int hashCode()"));
        assertTrue(modelGeneratedCode.contains("public boolean equals(final Object obj)"));
        assertTrue(modelGeneratedCode.contains("public String toString()"));
    }

    @Test
    public void httpErrorResultApiBean_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String modelName = "Error";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/httpErrorResultApiBean.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(hasModelVariable("Code", "String", modelGeneratedCode));
        assertTrue(hasModelVariable("HttpErrorCode", "int", modelGeneratedCode));
        assertTrue(hasModelVariable("Message", "String", modelGeneratedCode));
        assertTrue(hasModelVariable("Resource", "String", modelGeneratedCode));
        assertTrue(hasModelVariable("ResourceId", "long", modelGeneratedCode));

        assertTrue(hasImport("com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement", modelGeneratedCode));
        assertTrue(hasImport("com.fasterxml.jackson.annotation.JsonProperty", modelGeneratedCode));
        assertTrue(hasImport("com.fasterxml.jackson.annotation.JsonIgnoreProperties", modelGeneratedCode));

        assertTrue(modelGeneratedCode.contains("@JsonIgnoreProperties(ignoreUnknown = true)"));
        assertTrue(modelGeneratedCode.contains("public String toString()"));
    }

    @Test
    public void JobsApiBean_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        //This Type is special cased AND renamed: generation relies on name detection in isJobsApiBean within JavaCodeGenerator
        final String modelName = "JobList";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/jobsApiBean.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);

        assertTrue(hasCopyright(modelGeneratedCode));
        assertTrue(isOfPackage("com.spectralogic.ds3client.models", modelGeneratedCode));

        assertTrue(modelGeneratedCode.contains("@JacksonXmlRootElement(namespace = \"Jobs\")"));
        assertTrue(modelGeneratedCode.contains("public class JobList {"));

        assertTrue(modelGeneratedCode.contains("@JsonProperty(\"Job\")"));
        assertTrue(modelGeneratedCode.contains("@JacksonXmlElementWrapper(useWrapping = false)"));
        assertTrue(modelGeneratedCode.contains("private List<Job> jobs = new ArrayList<>();"));
    }

    @Test
    public void NamedDetailedTapeList_Test() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String requestName = "PlaceHolderRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedComponentResponseCode testGeneratedCode = new TestGeneratedComponentResponseCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/",
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/NamedDetailedTapeList.java");

        testGeneratedCode.generateCode(fileUtils, "/input/namedDetailedTapeList.xml");

        final String modelCode = testGeneratedCode.getEncapsulatingTypeGeneratedCode();
        LOG.info("Generated code:\n" + modelCode);

        assertFalse(modelCode.contains("@JacksonXmlRootElement(namespace = "));
        assertFalse(modelCode.contains("@JsonProperty(\"NamedDetailedTape\")"));
        assertTrue(modelCode.contains("@JsonProperty(\"Tape\")"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyType() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String modelName = "EmptyType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/emptyType.xml");
    }

    @Test
    public void unusedType() throws IOException, ParserException, ResponseTypeNotFoundException, TypeRenamingConflictException, TemplateModelException {
        final String modelName = "RequestType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedModelCode testGeneratedModelCode = new TestGeneratedModelCode(
                fileUtils,
                modelName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/models/");

        testGeneratedModelCode.generateCode(fileUtils, "/input/unusedType.xml");

        final String modelGeneratedCode = testGeneratedModelCode.getModelGeneratedCode();
        LOG.info("Generated code:\n" + modelGeneratedCode);
        assertTrue(modelGeneratedCode.isEmpty());
    }
}
