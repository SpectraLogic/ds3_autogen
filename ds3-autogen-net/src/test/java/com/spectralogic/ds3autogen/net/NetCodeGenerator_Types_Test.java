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
import com.spectralogic.ds3autogen.net.utils.TestGenerateCode;
import freemarker.template.TemplateModelException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class NetCodeGenerator_Types_Test {

    private final static String PLACEHOLDER_REQUEST_NAME = "PlaceHolderRequest";
    private final static Logger LOG = LoggerFactory.getLogger(NetCodeGenerator_Types_Test.class);

    @Test
    public void databasePhysicalSpaceState() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String typeName = "DatabasePhysicalSpaceState";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                PLACEHOLDER_REQUEST_NAME,
                "./Ds3/Calls/",
                typeName);

        codeGenerator.generateCode(fileUtils, "/input/types/databasePhysicalSpaceState.xml");
        final String typeCode = codeGenerator.getTypeCode();

        LOG.info("Generated code:\n" + typeCode);

        assertTrue(typeCode.contains("public enum " + typeName));
        assertTrue(typeCode.contains("CRITICAL,"));
        assertTrue(typeCode.contains("LOW,"));
        assertTrue(typeCode.contains("NEAR_LOW,"));
        assertTrue(typeCode.contains("NORMAL"));
    }

    @Test
    public void testElementsType() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String typeName = "TestElementsType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                PLACEHOLDER_REQUEST_NAME,
                "./Ds3/Calls/",
                typeName);

        final ImmutableList<String> ignorableFiles = ImmutableList.of("./Ds3/Models/ChecksumType.cs");
        codeGenerator.withIgnorableFiles(fileUtils, ignorableFiles);

        codeGenerator.generateCode(fileUtils, "/input/types/testElementsType.xml");
        final String typeCode = codeGenerator.getTypeCode();

        LOG.info("Generated code:\n" + typeCode);

        assertTrue(typeCode.contains("public class " + typeName));
        assertTrue(typeCode.contains("public IEnumerable<Pool> Pools { get; set; }"));
        assertTrue(typeCode.contains("public long ByteOffset { get; set; }"));
        assertTrue(typeCode.contains("public string Checksum { get; set; }"));
        assertTrue(typeCode.contains("public ChecksumType.Type? ChecksumType { get; set; }"));
        assertTrue(typeCode.contains("public Guid Id { get; set; }"));
        assertTrue(typeCode.contains("public long Length { get; set; }"));
        assertTrue(typeCode.contains("public Guid ObjectId { get; set; }"));
    }

    @Test
    public void checksumType() throws IOException, TypeRenamingConflictException, ParserException, ResponseTypeNotFoundException, TemplateModelException {
        final String typeName = "ChecksumType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                PLACEHOLDER_REQUEST_NAME,
                "./Ds3/Calls/",
                typeName);

        codeGenerator.generateCode(fileUtils, "/input/types/checksumType.xml");
        final String typeCode = codeGenerator.getTypeCode();

        LOG.info("Generated code:\n" + typeCode);

        assertTrue(typeCode.contains("public abstract class " + typeName));
        assertTrue(typeCode.contains("CRC_32,"));
        assertTrue(typeCode.contains("CRC_32C,"));
        assertTrue(typeCode.contains("MD5,"));
        assertTrue(typeCode.contains("SHA_256,"));
        assertTrue(typeCode.contains("SHA_512,"));
        assertTrue(typeCode.contains("NONE"));

    }

    @Test
    public void bucket_Test() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String typeName = "Bucket";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                PLACEHOLDER_REQUEST_NAME,
                "./Ds3/Calls/",
                typeName);

        codeGenerator.generateCode(fileUtils, "/input/types/bucket.xml");
        final String typeCode = codeGenerator.getTypeCode();

        LOG.info("Generated code:\n" + typeCode);

        assertTrue(typeCode.contains("public DateTime CreationDate { get; set; }"));
        assertTrue(typeCode.contains("public Guid DataPolicyId { get; set; }"));
        assertTrue(typeCode.contains("public Guid Id { get; set; }"));
        assertTrue(typeCode.contains("public long? LastPreferredChunkSizeInBytes { get; set; }"));
        assertTrue(typeCode.contains("public long? LogicalUsedCapacity { get; set; }"));
        assertTrue(typeCode.contains("public string Name { get; set; }"));
        assertTrue(typeCode.contains("public Guid UserId { get; set; }"));
    }

    @Test
    public void jobChunkApiBean_Test() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String typeName = "Objects";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                PLACEHOLDER_REQUEST_NAME,
                "./Ds3/Calls/",
                typeName);

        codeGenerator.withIgnorableFiles(fileUtils, ImmutableList.of("./Ds3/Models/BulkObject.cs"));

        codeGenerator.generateCode(fileUtils, "/input/types/jobChunkApiBean.xml");
        final String typeCode = codeGenerator.getTypeCode();

        LOG.info("Generated code:\n" + typeCode);

        assertTrue(typeCode.contains("public Guid ChunkId { get; set; }"));
        assertTrue(typeCode.contains("public int ChunkNumber { get; set; }"));
        assertTrue(typeCode.contains("public Guid? NodeId { get; set; }"));
        assertTrue(typeCode.contains("public IEnumerable<BulkObject> ObjectsList { get; set; }"));
    }
}
