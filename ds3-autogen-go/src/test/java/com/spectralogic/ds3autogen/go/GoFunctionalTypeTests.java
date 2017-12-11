/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.go;

import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.go.utils.GoTestCodeUtil;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import freemarker.template.TemplateModelException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class GoFunctionalTypeTests {

    private final static Logger LOG = LoggerFactory.getLogger(GoFunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.MODEL, LOG);

    private final static String requestName = "PlaceHolderRequest";

    @Test
    public void simpleEnumGeneration() throws IOException, TemplateModelException {
        final String typeName = "DatabasePhysicalSpaceState";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, typeName);

        codeGenerator.generateCode(fileUtils, "/input/simpleEnum.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Test const definitions
        assertTrue(typeCode.contains("DATABASE_PHYSICAL_SPACE_STATE_CRITICAL DatabasePhysicalSpaceState = 1 + iota"));
        assertTrue(typeCode.contains("DATABASE_PHYSICAL_SPACE_STATE_LOW DatabasePhysicalSpaceState = 1 + iota"));
        assertTrue(typeCode.contains("DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW DatabasePhysicalSpaceState = 1 + iota"));
        assertTrue(typeCode.contains("DATABASE_PHYSICAL_SPACE_STATE_NORMAL DatabasePhysicalSpaceState = 1 + iota"));

        // Test un-marshaling
        assertTrue(typeCode.contains("case \"\": *databasePhysicalSpaceState = UNDEFINED"));
        assertTrue(typeCode.contains("case \"CRITICAL\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_CRITICAL"));
        assertTrue(typeCode.contains("case \"LOW\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_LOW"));
        assertTrue(typeCode.contains("case \"NEAR_LOW\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW"));
        assertTrue(typeCode.contains("case \"NORMAL\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_NORMAL"));

        // Test conversion to string
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_CRITICAL: return \"CRITICAL\""));
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_LOW: return \"LOW\""));
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW: return \"NEAR_LOW\""));
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_NORMAL: return \"NORMAL\""));

        // Verify type parser file was not generated for enum type
        final String typeParserCode = codeGenerator.getTypeParserCode();
        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);
        assertTrue(isEmpty(typeParserCode));
    }

    @Test
    public void checksumTypeEnum() throws IOException, TemplateModelException {
        final String typeName = "ChecksumType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, typeName);

        codeGenerator.generateCode(fileUtils, "/input/checksumType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Test const definitions
        assertTrue(typeCode.contains("CHECKSUM_TYPE_CRC_32 ChecksumType = 1 + iota"));
        assertTrue(typeCode.contains("CHECKSUM_TYPE_CRC_32C ChecksumType = 1 + iota"));
        assertTrue(typeCode.contains("CHECKSUM_TYPE_MD5 ChecksumType = 1 + iota"));
        assertTrue(typeCode.contains("CHECKSUM_TYPE_SHA_256 ChecksumType = 1 + iota"));
        assertTrue(typeCode.contains("CHECKSUM_TYPE_SHA_512 ChecksumType = 1 + iota"));
        assertTrue(typeCode.contains("NONE ChecksumType = 1 + iota"));

        // Test un-marshaling
        assertTrue(typeCode.contains("case \"\": *checksumType = UNDEFINED"));
        assertTrue(typeCode.contains("case \"CRC_32\": *checksumType = CHECKSUM_TYPE_CRC_32"));
        assertTrue(typeCode.contains("case \"CRC_32C\": *checksumType = CHECKSUM_TYPE_CRC_32C"));
        assertTrue(typeCode.contains("case \"MD5\": *checksumType = CHECKSUM_TYPE_MD5"));
        assertTrue(typeCode.contains("case \"SHA_256\": *checksumType = CHECKSUM_TYPE_SHA_256"));
        assertTrue(typeCode.contains("case \"SHA_512\": *checksumType = CHECKSUM_TYPE_SHA_512"));

        // Test conversion to string
        assertTrue(typeCode.contains("case CHECKSUM_TYPE_CRC_32: return \"CRC_32\""));
        assertTrue(typeCode.contains("case CHECKSUM_TYPE_CRC_32C: return \"CRC_32C\""));
        assertTrue(typeCode.contains("case CHECKSUM_TYPE_MD5: return \"MD5\""));
        assertTrue(typeCode.contains("case CHECKSUM_TYPE_SHA_256: return \"SHA_256\""));
        assertTrue(typeCode.contains("case CHECKSUM_TYPE_SHA_512: return \"SHA_512\""));

        // Verify type parser file was not generated for enum type
        final String typeParserCode = codeGenerator.getTypeParserCode();
        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);
        assertTrue(isEmpty(typeParserCode));
    }

    @Test
    public void simpleTypeGeneration() throws IOException, TemplateModelException {
        final String typeName = "JobCompletedNotificationPayload";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, typeName);

        codeGenerator.generateCode(fileUtils, "/input/simpleType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(typeCode.contains("CancelOccurred bool"));
        assertTrue(typeCode.contains("JobId string"));
        assertTrue(typeCode.contains("NotificationGenerationDate string"));
        assertTrue(typeCode.contains("ObjectsNotPersisted []BulkObject"));
        assertTrue(typeCode.contains("ListedElements []TestType"));

        assertFalse(typeCode.contains("`xml:\"CancelOccurred\"`"));
        assertFalse(typeCode.contains("`xml:\"JobId\"`"));
        assertFalse(typeCode.contains("`xml:\"NotificationGenerationDate\"`"));
        assertFalse(typeCode.contains("`xml:\"ObjectsNotPersisted>Object\"`"));
        assertFalse(typeCode.contains("`xml:\"ListedElements\"`"));

        // Verify type parser file was generated
        final String typeParserCode = codeGenerator.getTypeParserCode();
        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(typeParserCode));

        assertTrue(typeParserCode.contains("jobCompletedNotificationPayload.CancelOccurred = parseBool(child.Content, aggErr)"));
        assertTrue(typeParserCode.contains("jobCompletedNotificationPayload.JobId = parseString(child.Content)"));
        assertTrue(typeParserCode.contains("jobCompletedNotificationPayload.NotificationGenerationDate = parseString(child.Content)"));
        assertTrue(typeParserCode.contains("jobCompletedNotificationPayload.ObjectsNotPersisted = parseBulkObjectSlice(\"Object\", child.Children, aggErr)"));
        assertTrue(typeParserCode.contains("jobCompletedNotificationPayload.ListedElements = append(jobCompletedNotificationPayload.ListedElements, model)"));
        assertTrue(typeParserCode.contains("log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing JobCompletedNotificationPayload.\", child.XMLName.Local)"));
    }

    @Test
    public void jobType() throws IOException, TemplateModelException {
        final String typeName = "Job";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, typeName);

        codeGenerator.generateCode(fileUtils, "/input/jobType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(typeCode.contains("Aggregating bool"));
        assertTrue(typeCode.contains("BucketName *string"));
        assertTrue(typeCode.contains("CachedSizeInBytes int64"));
        assertTrue(typeCode.contains("ChunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee"));
        assertTrue(typeCode.contains("CompletedSizeInBytes int64"));
        assertTrue(typeCode.contains("EntirelyInCache bool"));
        assertTrue(typeCode.contains("JobId string"));
        assertTrue(typeCode.contains("Naked bool"));
        assertTrue(typeCode.contains("Name *string"));
        assertTrue(typeCode.contains("Nodes []JobNode"));
        assertTrue(typeCode.contains("OriginalSizeInBytes int64"));
        assertTrue(typeCode.contains("Priority Priority"));
        assertTrue(typeCode.contains("RequestType JobRequestType"));
        assertTrue(typeCode.contains("StartDate string"));
        assertTrue(typeCode.contains("Status JobStatus"));
        assertTrue(typeCode.contains("UserId string"));
        assertTrue(typeCode.contains("UserName *string"));

        assertFalse(typeCode.contains("`xml:\"Aggregating,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"BucketName,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"CachedSizeInBytes,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"ChunkClientProcessingOrderGuarantee,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"CompletedSizeInBytes,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"EntirelyInCache,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"JobId,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"Naked,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"Name,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"Nodes>Node\"`"));
        assertFalse(typeCode.contains("`xml:\"OriginalSizeInBytes,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"Priority,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"RequestType,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"StartDate,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"Status,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"UserId,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"UserName,attr\"`"));


        // Verify type parser file was generated
        final String typeParserCode = codeGenerator.getTypeParserCode();
        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(typeParserCode));

        assertTrue(typeParserCode.contains("job.Aggregating = parseBoolFromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("job.BucketName = parseNullableStringFromString(attr.Value)"));
        assertTrue(typeParserCode.contains("job.CachedSizeInBytes = parseInt64FromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("parseEnumFromString(attr.Value, &job.ChunkClientProcessingOrderGuarantee, aggErr)"));
        assertTrue(typeParserCode.contains("job.CompletedSizeInBytes = parseInt64FromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("job.EntirelyInCache = parseBoolFromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("job.JobId = attr.Value"));
        assertTrue(typeParserCode.contains("job.Naked = parseBoolFromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("job.Name = parseNullableStringFromString(attr.Value)"));
        assertTrue(typeParserCode.contains("job.OriginalSizeInBytes = parseInt64FromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("parseEnumFromString(attr.Value, &job.Priority, aggErr)"));
        assertTrue(typeParserCode.contains("parseEnumFromString(attr.Value, &job.RequestType, aggErr)"));
        assertTrue(typeParserCode.contains("job.StartDate = attr.Value"));
        assertTrue(typeParserCode.contains("parseEnumFromString(attr.Value, &job.Status, aggErr)"));
        assertTrue(typeParserCode.contains("job.UserId = attr.Value"));
        assertTrue(typeParserCode.contains("job.UserName = parseNullableStringFromString(attr.Value)"));
        assertTrue(typeParserCode.contains("log.Printf(\"WARNING: unable to parse unknown attribute '%s' while parsing Job.\", attr.Name.Local)"));

        assertTrue(typeParserCode.contains("job.Nodes = parseJobNodeSlice(\"Node\", child.Children, aggErr)"));
        assertTrue(typeParserCode.contains("log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing Job.\", child.XMLName.Local)"));
    }

    @Test
    public void jobNodeType() throws IOException, TemplateModelException {
        final String typeName = "JobNode";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, typeName);

        codeGenerator.generateCode(fileUtils, "/input/jobNodeType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(typeCode.contains("EndPoint *string"));
        assertTrue(typeCode.contains("HttpPort *int"));
        assertTrue(typeCode.contains("HttpsPort *int"));
        assertTrue(typeCode.contains("Id string"));

        assertFalse(typeCode.contains("`xml:\"EndPoint,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"HttpPort,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"HttpsPort,attr\"`"));
        assertFalse(typeCode.contains("`xml:\"Id,attr\"`"));

        // Verify type parser file was generated
        final String typeParserCode = codeGenerator.getTypeParserCode();
        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(typeParserCode));

        assertTrue(typeParserCode.contains("jobNode.EndPoint = parseNullableStringFromString(attr.Value)"));
        assertTrue(typeParserCode.contains("jobNode.HttpPort = parseNullableIntFromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("jobNode.HttpsPort = parseNullableIntFromString(attr.Value, aggErr)"));
        assertTrue(typeParserCode.contains("jobNode.Id = attr.Value"));
        assertTrue(typeParserCode.contains("log.Printf(\"WARNING: unable to parse unknown attribute '%s' while parsing JobNode.\", attr.Name.Local)"));
    }

    @Test
    public void jobList() throws IOException, TemplateModelException {
        final String typeName = "JobList";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, typeName);

        codeGenerator.generateCode(fileUtils, "/input/jobListType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(typeCode.contains("Jobs []Job"));

        assertFalse(typeCode.contains("`xml:\"Job\"`"));

        // Verify type parser file was generated
        final String typeParserCode = codeGenerator.getTypeParserCode();
        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(typeParserCode));

        assertFalse(typeParserCode.contains("case \"Jobs\":"));
        assertFalse(typeParserCode.contains("jobList.Jobs = parseJobSlice(\"Job\", child.Children, aggErr)"));

        assertTrue(typeParserCode.contains("case \"Job\":"));
        assertTrue(typeParserCode.contains("var model Job"));
        assertTrue(typeParserCode.contains("model.parse(&child, aggErr)"));
        assertTrue(typeParserCode.contains("jobList.Jobs = append(jobList.Jobs, model)"));
        assertTrue(typeParserCode.contains("log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing JobList.\", child.XMLName.Local)"));
    }

    @Test
    public void listBucketResultTest() throws IOException, TemplateModelException {
        final String typeName = "ListBucketResult";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, typeName);

        codeGenerator.generateCode(fileUtils, "/input/listBucketResult.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(typeCode.contains("CommonPrefixes []string"));
        assertTrue(typeCode.contains("CreationDate *string"));
        assertTrue(typeCode.contains("Delimiter *string"));
        assertTrue(typeCode.contains("Marker *string"));
        assertTrue(typeCode.contains("MaxKeys int"));
        assertTrue(typeCode.contains("Name *string"));
        assertTrue(typeCode.contains("NextMarker *string"));
        assertTrue(typeCode.contains("Objects []Contents"));
        assertTrue(typeCode.contains("Prefix *string"));
        assertTrue(typeCode.contains("Truncated bool"));

        // Verify type parser file was generated
        final String typeParserCode = codeGenerator.getTypeParserCode();
        CODE_LOGGER.logFile(typeParserCode, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(typeParserCode));

        assertTrue(typeParserCode.contains("case \"CommonPrefixes\":"));
        assertTrue(typeParserCode.contains("var prefixes []string"));
        assertTrue(typeParserCode.contains("prefixes = parseStringSlice(\"Prefix\", child.Children, aggErr)"));
        assertTrue(typeParserCode.contains("listBucketResult.CommonPrefixes = append(listBucketResult.CommonPrefixes, prefixes...)"));

        assertTrue(typeParserCode.contains("listBucketResult.CreationDate = parseNullableString(child.Content)"));
        assertTrue(typeParserCode.contains("listBucketResult.Delimiter = parseNullableString(child.Content)"));
        assertTrue(typeParserCode.contains("listBucketResult.Marker = parseNullableString(child.Content)"));
        assertTrue(typeParserCode.contains("listBucketResult.MaxKeys = parseInt(child.Content, aggErr)"));
        assertTrue(typeParserCode.contains("listBucketResult.Name = parseNullableString(child.Content)"));
        assertTrue(typeParserCode.contains("listBucketResult.NextMarker = parseNullableString(child.Content)"));
        assertTrue(typeParserCode.contains("listBucketResult.Objects = append(listBucketResult.Objects, model)"));
        assertTrue(typeParserCode.contains("listBucketResult.Prefix = parseNullableString(child.Content)"));
        assertTrue(typeParserCode.contains("listBucketResult.Truncated = parseBool(child.Content, aggErr)"));
        assertTrue(typeParserCode.contains("log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing ListBucketResult.\", child.XMLName.Local)"));
    }
}
