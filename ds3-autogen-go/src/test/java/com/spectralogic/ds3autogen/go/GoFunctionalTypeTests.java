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

    private static boolean containsNormalized(final String haystack, final String needle) {
        return normalize(haystack).contains(normalize(needle));
    }

    private static String normalize(final String s) {
        return s.replaceAll("\\s+", " ");
    }


    @Test
    public void simpleEnumGeneration() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/simpleEnum.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Test const definitions
        assertTrue(containsNormalized(typeCode, "DATABASE_PHYSICAL_SPACE_STATE_CRITICAL DatabasePhysicalSpaceState = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "DATABASE_PHYSICAL_SPACE_STATE_LOW DatabasePhysicalSpaceState = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW DatabasePhysicalSpaceState = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "DATABASE_PHYSICAL_SPACE_STATE_NORMAL DatabasePhysicalSpaceState = 1 + iota"));

        // Test un-marshaling
        assertTrue(containsNormalized(typeCode, "case \"\": *databasePhysicalSpaceState = UNDEFINED"));
        assertTrue(containsNormalized(typeCode, "case \"CRITICAL\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_CRITICAL"));
        assertTrue(containsNormalized(typeCode, "case \"LOW\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_LOW"));
        assertTrue(containsNormalized(typeCode, "case \"NEAR_LOW\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW"));
        assertTrue(containsNormalized(typeCode, "case \"NORMAL\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_NORMAL"));

        // Test conversion to string
        assertTrue(containsNormalized(typeCode, "case DATABASE_PHYSICAL_SPACE_STATE_CRITICAL: return \"CRITICAL\""));
        assertTrue(containsNormalized(typeCode, "case DATABASE_PHYSICAL_SPACE_STATE_LOW: return \"LOW\""));
        assertTrue(containsNormalized(typeCode, "case DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW: return \"NEAR_LOW\""));
        assertTrue(containsNormalized(typeCode, "case DATABASE_PHYSICAL_SPACE_STATE_NORMAL: return \"NORMAL\""));

        // Verify conversion to string pointer
        assertTrue(containsNormalized(typeCode, "func (databasePhysicalSpaceState DatabasePhysicalSpaceState) StringPtr() *string {"));

        // Verify type parser code was not generated for enum type
        assertFalse(containsNormalized(typeCode, "parse("));
    }

    @Test
    public void checksumTypeEnum() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/checksumType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Test const definitions
        assertTrue(containsNormalized(typeCode, "CHECKSUM_TYPE_CRC_32 ChecksumType = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "CHECKSUM_TYPE_CRC_32C ChecksumType = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "CHECKSUM_TYPE_MD5 ChecksumType = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "CHECKSUM_TYPE_SHA_256 ChecksumType = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "CHECKSUM_TYPE_SHA_512 ChecksumType = 1 + iota"));
        assertTrue(containsNormalized(typeCode, "NONE ChecksumType = 1 + iota"));

        // Test un-marshaling
        assertTrue(containsNormalized(typeCode, "case \"\": *checksumType = UNDEFINED"));
        assertTrue(containsNormalized(typeCode, "case \"CRC_32\": *checksumType = CHECKSUM_TYPE_CRC_32"));
        assertTrue(containsNormalized(typeCode, "case \"CRC_32C\": *checksumType = CHECKSUM_TYPE_CRC_32C"));
        assertTrue(containsNormalized(typeCode, "case \"MD5\": *checksumType = CHECKSUM_TYPE_MD5"));
        assertTrue(containsNormalized(typeCode, "case \"SHA_256\": *checksumType = CHECKSUM_TYPE_SHA_256"));
        assertTrue(containsNormalized(typeCode, "case \"SHA_512\": *checksumType = CHECKSUM_TYPE_SHA_512"));

        // Test conversion to string
        assertTrue(containsNormalized(typeCode, "case CHECKSUM_TYPE_CRC_32: return \"CRC_32\""));
        assertTrue(containsNormalized(typeCode, "case CHECKSUM_TYPE_CRC_32C: return \"CRC_32C\""));
        assertTrue(containsNormalized(typeCode, "case CHECKSUM_TYPE_MD5: return \"MD5\""));
        assertTrue(containsNormalized(typeCode, "case CHECKSUM_TYPE_SHA_256: return \"SHA_256\""));
        assertTrue(containsNormalized(typeCode, "case CHECKSUM_TYPE_SHA_512: return \"SHA_512\""));

        // Verify conversion to string pointer
        assertTrue(containsNormalized(typeCode, "func (checksumType ChecksumType) StringPtr() *string {"));

        // Verify type parser code was not generated for enum type
        assertFalse(containsNormalized(typeCode, "parse("));
    }

    @Test
    public void simpleTypeGeneration() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/simpleType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(containsNormalized(typeCode, "CancelOccurred bool"));
        assertTrue(containsNormalized(typeCode, "JobId string"));
        assertTrue(containsNormalized(typeCode, "NotificationGenerationDate string"));
        assertTrue(containsNormalized(typeCode, "ObjectsNotPersisted []BulkObject"));
        assertTrue(containsNormalized(typeCode, "ListedElements []TestType"));

        assertFalse(containsNormalized(typeCode, "`xml:\"CancelOccurred\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"JobId\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"NotificationGenerationDate\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"ObjectsNotPersisted>Object\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"ListedElements\"`"));

        // Verify type parser code was generated
        assertTrue(containsNormalized(typeCode, "jobCompletedNotificationPayload.CancelOccurred = parseBool(child.Content, aggErr)"));
        assertTrue(containsNormalized(typeCode, "jobCompletedNotificationPayload.JobId = parseString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "jobCompletedNotificationPayload.NotificationGenerationDate = parseString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "jobCompletedNotificationPayload.ObjectsNotPersisted = parseBulkObjectSlice(\"Object\", child.Children, aggErr)"));
        assertTrue(containsNormalized(typeCode, "jobCompletedNotificationPayload.ListedElements = append(jobCompletedNotificationPayload.ListedElements, model)"));
        assertTrue(containsNormalized(typeCode, "log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing JobCompletedNotificationPayload.\", child.XMLName.Local)"));
    }

    @Test
    public void jobType() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/jobType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(containsNormalized(typeCode, "Aggregating bool"));
        assertTrue(containsNormalized(typeCode, "BucketName *string"));
        assertTrue(containsNormalized(typeCode, "CachedSizeInBytes int64"));
        assertTrue(containsNormalized(typeCode, "ChunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee"));
        assertTrue(containsNormalized(typeCode, "CompletedSizeInBytes int64"));
        assertTrue(containsNormalized(typeCode, "EntirelyInCache bool"));
        assertTrue(containsNormalized(typeCode, "JobId string"));
        assertTrue(containsNormalized(typeCode, "Naked bool"));
        assertTrue(containsNormalized(typeCode, "Name *string"));
        assertTrue(containsNormalized(typeCode, "Nodes []JobNode"));
        assertTrue(containsNormalized(typeCode, "OriginalSizeInBytes int64"));
        assertTrue(containsNormalized(typeCode, "Priority Priority"));
        assertTrue(containsNormalized(typeCode, "RequestType JobRequestType"));
        assertTrue(containsNormalized(typeCode, "StartDate string"));
        assertTrue(containsNormalized(typeCode, "Status JobStatus"));
        assertTrue(containsNormalized(typeCode, "UserId string"));
        assertTrue(containsNormalized(typeCode, "UserName *string"));

        assertFalse(containsNormalized(typeCode, "`xml:\"Aggregating,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"BucketName,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"CachedSizeInBytes,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"ChunkClientProcessingOrderGuarantee,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"CompletedSizeInBytes,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"EntirelyInCache,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"JobId,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"Naked,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"Name,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"Nodes>Node\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"OriginalSizeInBytes,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"Priority,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"RequestType,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"StartDate,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"Status,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"UserId,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"UserName,attr\"`"));


        // Verify type parser code was generated
        assertTrue(containsNormalized(typeCode, "job.Aggregating = parseBoolFromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "job.BucketName = parseNullableStringFromString(attr.Value)"));
        assertTrue(containsNormalized(typeCode, "job.CachedSizeInBytes = parseInt64FromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "parseEnumFromString(attr.Value, &job.ChunkClientProcessingOrderGuarantee, aggErr)"));
        assertTrue(containsNormalized(typeCode, "job.CompletedSizeInBytes = parseInt64FromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "job.EntirelyInCache = parseBoolFromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "job.JobId = attr.Value"));
        assertTrue(containsNormalized(typeCode, "job.Naked = parseBoolFromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "job.Name = parseNullableStringFromString(attr.Value)"));
        assertTrue(containsNormalized(typeCode, "job.OriginalSizeInBytes = parseInt64FromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "parseEnumFromString(attr.Value, &job.Priority, aggErr)"));
        assertTrue(containsNormalized(typeCode, "parseEnumFromString(attr.Value, &job.RequestType, aggErr)"));
        assertTrue(containsNormalized(typeCode, "job.StartDate = attr.Value"));
        assertTrue(containsNormalized(typeCode, "parseEnumFromString(attr.Value, &job.Status, aggErr)"));
        assertTrue(containsNormalized(typeCode, "job.UserId = attr.Value"));
        assertTrue(containsNormalized(typeCode, "job.UserName = parseNullableStringFromString(attr.Value)"));
        assertTrue(containsNormalized(typeCode, "log.Printf(\"WARNING: unable to parse unknown attribute '%s' while parsing Job.\", attr.Name.Local)"));

        assertTrue(containsNormalized(typeCode, "job.Nodes = parseJobNodeSlice(\"Node\", child.Children, aggErr)"));
        assertTrue(containsNormalized(typeCode, "log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing Job.\", child.XMLName.Local)"));
    }

    @Test
    public void jobNodeType() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/jobNodeType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(containsNormalized(typeCode, "EndPoint *string"));
        assertTrue(containsNormalized(typeCode, "HttpPort *int"));
        assertTrue(containsNormalized(typeCode, "HttpsPort *int"));
        assertTrue(containsNormalized(typeCode, "Id string"));

        assertFalse(containsNormalized(typeCode, "`xml:\"EndPoint,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"HttpPort,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"HttpsPort,attr\"`"));
        assertFalse(containsNormalized(typeCode, "`xml:\"Id,attr\"`"));

        // Verify type parser code was generated
        assertTrue(containsNormalized(typeCode, "jobNode.EndPoint = parseNullableStringFromString(attr.Value)"));
        assertTrue(containsNormalized(typeCode, "jobNode.HttpPort = parseNullableIntFromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "jobNode.HttpsPort = parseNullableIntFromString(attr.Value, aggErr)"));
        assertTrue(containsNormalized(typeCode, "jobNode.Id = attr.Value"));
        assertTrue(containsNormalized(typeCode, "log.Printf(\"WARNING: unable to parse unknown attribute '%s' while parsing JobNode.\", attr.Name.Local)"));
    }

    @Test
    public void jobList() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/jobListType.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(containsNormalized(typeCode, "Jobs []Job"));

        assertFalse(containsNormalized(typeCode, "`xml:\"Job\"`"));

        // Verify type parser code was generated
        assertFalse(containsNormalized(typeCode, "case \"Jobs\":"));
        assertFalse(containsNormalized(typeCode, "jobList.Jobs = parseJobSlice(\"Job\", child.Children, aggErr)"));

        assertTrue(containsNormalized(typeCode, "case \"Job\":"));
        assertTrue(containsNormalized(typeCode, "var model Job"));
        assertTrue(containsNormalized(typeCode, "model.parse(&child, aggErr)"));
        assertTrue(containsNormalized(typeCode, "jobList.Jobs = append(jobList.Jobs, model)"));
        assertTrue(containsNormalized(typeCode, "log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing JobList.\", child.XMLName.Local)"));
    }

    @Test
    public void listBucketResultTest() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/listBucketResult.xml");

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        assertTrue(containsNormalized(typeCode, "CommonPrefixes []string"));
        assertTrue(containsNormalized(typeCode, "CreationDate *string"));
        assertTrue(containsNormalized(typeCode, "Delimiter *string"));
        assertTrue(containsNormalized(typeCode, "Marker *string"));
        assertTrue(containsNormalized(typeCode, "MaxKeys int"));
        assertTrue(containsNormalized(typeCode, "Name *string"));
        assertTrue(containsNormalized(typeCode, "NextMarker *string"));
        assertTrue(containsNormalized(typeCode, "Objects []Contents"));
        assertTrue(containsNormalized(typeCode, "Prefix *string"));
        assertTrue(containsNormalized(typeCode, "Truncated bool"));

        // Verify type parser code was generated
        assertTrue(containsNormalized(typeCode, "case \"CommonPrefixes\":"));
        assertTrue(containsNormalized(typeCode, "var prefixes []string"));
        assertTrue(containsNormalized(typeCode, "prefixes = parseStringSlice(\"Prefix\", child.Children, aggErr)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.CommonPrefixes = append(listBucketResult.CommonPrefixes, prefixes...)"));

        assertTrue(containsNormalized(typeCode, "listBucketResult.CreationDate = parseNullableString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.Delimiter = parseNullableString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.Marker = parseNullableString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.MaxKeys = parseInt(child.Content, aggErr)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.Name = parseNullableString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.NextMarker = parseNullableString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.Objects = append(listBucketResult.Objects, model)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.Prefix = parseNullableString(child.Content)"));
        assertTrue(containsNormalized(typeCode, "listBucketResult.Truncated = parseBool(child.Content, aggErr)"));
        assertTrue(containsNormalized(typeCode, "log.Printf(\"WARNING: unable to parse unknown xml tag '%s' while parsing ListBucketResult.\", child.XMLName.Local)"));
    }
}
