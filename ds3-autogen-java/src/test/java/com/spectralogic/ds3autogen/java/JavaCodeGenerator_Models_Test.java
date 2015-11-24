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
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.utils.TestGeneratedModelCode;
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
    public void bucketAclType() throws IOException, ParserException {
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
        assertTrue(hasImport("com.spectralogic.ds3client.models.BucketAclPermission", modelGeneratedCode));
        assertFalse(hasImport("java.util.List", modelGeneratedCode));

        final ImmutableList<Element> constructorArgs = ImmutableList.of(
                new Element("BucketId", "UUID", ""),
                new Element("GroupId", "UUID", ""),
                new Element("Id", "UUID", ""),
                new Element("Permission", "BucketAclPermission", ""),
                new Element("UserId", "UUID", ""));
        assertTrue(hasModelConstructor(modelName, constructorArgs, modelGeneratedCode));
    }

    @Test
    public void physicalPlacementApiBeanModel() throws IOException, ParserException {
        final String modelName = "PhysicalPlacementApiBean";
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

        assertTrue(hasModelVariable("Pools", "List<Pool>", true, modelGeneratedCode));
        assertTrue(hasModelVariable("Tapes", "List<Tape>", true, modelGeneratedCode));

        assertTrue(hasImport("com.fasterxml.jackson.annotation.JsonProperty", modelGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Pool", modelGeneratedCode));
        assertTrue(hasImport("com.spectralogic.ds3client.models.Tape", modelGeneratedCode));
        assertTrue(hasImport("java.util.List", modelGeneratedCode));

        final ImmutableList<Element> constructorArgs = ImmutableList.of(
                new Element("Pools", "array", "Pool"),
                new Element("Tapes", "array", "Tape"));
        assertTrue(hasModelConstructor(modelName, constructorArgs, modelGeneratedCode));
    }
}