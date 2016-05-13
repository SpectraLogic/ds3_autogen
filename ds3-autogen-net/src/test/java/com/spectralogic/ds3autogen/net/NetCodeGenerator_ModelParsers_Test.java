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
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.spectralogic.ds3autogen.net.utils.TestHelper.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class NetCodeGenerator_ModelParsers_Test {

    private final static Logger LOG = LoggerFactory.getLogger(NetCodeGenerator_Test.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.MODEL_PARSERS, LOG);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void bucketObjectApiBean_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                "GetBucketRequest",
                "./Ds3/Calls/",
                "ListBucketResult");

        codeGenerator.generateCode(fileUtils, "/input/modelparsing/modelParsingSimpleType.xml");
        final String typeParserCode = codeGenerator.getTypeParser();

        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);

        assertTrue(typeParserCode.contains("public static ListBucketResult ParseListBucketResult(XElement element)"));
        assertTrue(typeParserCode.contains("public static ListBucketResult ParseNullableListBucketResult(XElement element)"));

        assertTrue(typeParserCode.contains("BucketName = ParseNullableString(element.AttributeTextOrNull(\"BucketName\"))"));
        assertTrue(typeParserCode.contains("Truncated = ParseBool(element.Element(\"IsTruncated\"))"));
        assertTrue(typeParserCode.contains("TruncatedNullable = ParseNullableBool(element.Element(\"IsTruncated\"))"));
        assertTrue(typeParserCode.contains("MaxKeys = ParseInt(element.Element(\"MaxKeys\"))"));
        assertTrue(typeParserCode.contains("MaxKeysNullable = ParseNullableInt(element.Element(\"MaxKeysNullable\"))"));
        assertTrue(typeParserCode.contains("CreationDate = ParseDateTime(element.Element(\"CreationDate\"))"));
        assertTrue(typeParserCode.contains("CreationDateNullable = ParseNullableDateTime(element.Element(\"CreationDateNullable\"))"));
        assertTrue(typeParserCode.contains("Delimiter = ParseNullableString(element.Element(\"Delimiter\"))"));
        assertTrue(typeParserCode.contains("CommonPrefixes = ParseEncapsulatedList(element, \"Prefix\", \"CommonPrefixes\", ParseString)"));
        assertTrue(typeParserCode.contains("Objects = element.Elements(\"Contents\").Select(ParseContents).ToList()"));
        assertTrue(typeParserCode.contains("Buckets = ParseEncapsulatedList(element, \"Bucket\", \"Buckets\", ParseBucketDetails)"));
    }

    @Test
    public void checksumType_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final String enumName = "ChecksumType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                "PlaceHolderRequest",
                "./Ds3/Calls/",
                enumName);

        codeGenerator.generateCode(fileUtils, "/input/modelparsing/modelParsingEnumType.xml");
        final String typeParserCode = codeGenerator.getTypeParser();

        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);

        assertFalse(typeParserCode.contains("AlgorithmName"));
        assertFalse(typeParserCode.contains("HttpHeaderName"));

        assertFalse(parseNullableEnumString(enumName, typeParserCode));
        assertFalse(parseEnumString(enumName, typeParserCode));
        assertFalse(parseNullableEnumElement(enumName, typeParserCode));
        assertFalse(parseEnumElement(enumName, typeParserCode));
    }

    @Test
    public void getTapesWithFullDetails_Test() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String typeName = "NamedDetailedTapeList";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                "GetTapesWithFullDetailsSpectraS3Request",
                "./Ds3/Calls/",
                typeName);

        codeGenerator.generateCode(fileUtils, "/input/getTapesWithFullDetails.xml");
        final String typeParserCode = codeGenerator.getTypeParser();

        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);

        assertTrue(typeParserCode.contains("public static NamedDetailedTapeList ParseNamedDetailedTapeList(XElement element)"));
        assertTrue(typeParserCode.contains("public static NamedDetailedTapeList ParseNullableNamedDetailedTapeList(XElement element)"));

        assertTrue(typeParserCode.contains("NamedDetailedTapes = element.Elements(\"Tape\").Select(ParseNamedDetailedTape).ToList()"));
    }

    @Test
    public void jobChunkApiBean_Test() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String typeName = "Objects";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                "PlaceHolderRequest",
                "./Ds3/Calls/",
                typeName);

        codeGenerator.withIgnorableFiles(fileUtils, ImmutableList.of("./Ds3/Models/BulkObject.cs"));

        codeGenerator.generateCode(fileUtils, "/input/types/jobChunkApiBean.xml");
        final String typeParserCode = codeGenerator.getTypeParser();

        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);

        assertTrue(typeParserCode.contains("public static Objects ParseObjects(XElement element)"));
        assertTrue(typeParserCode.contains("public static Objects ParseNullableObjects(XElement element)"));

        assertTrue(typeParserCode.contains("ChunkId = ParseGuid(element.AttributeText(\"ChunkId\"))"));
        assertTrue(typeParserCode.contains("ChunkNumber = ParseInt(element.AttributeText(\"ChunkNumber\"))"));
        assertTrue(typeParserCode.contains("NodeId = ParseNullableGuid(element.AttributeTextOrNull(\"NodeId\"))"));
        assertTrue(typeParserCode.contains("ObjectsList = element.Elements(\"Object\").Select(ParseBulkObject).ToList()"));
    }
}
