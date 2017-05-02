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
        assertTrue(typeCode.contains("case \"CRITICAL\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_CRITICAL"));
        assertTrue(typeCode.contains("case \"LOW\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_LOW"));
        assertTrue(typeCode.contains("case \"NEAR_LOW\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW"));
        assertTrue(typeCode.contains("case \"NORMAL\": *databasePhysicalSpaceState = DATABASE_PHYSICAL_SPACE_STATE_NORMAL"));

        // Test conversion to string
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_CRITICAL: return \"CRITICAL\", nil"));
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_LOW: return \"LOW\", nil"));
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_NEAR_LOW: return \"NEAR_LOW\", nil"));
        assertTrue(typeCode.contains("case DATABASE_PHYSICAL_SPACE_STATE_NORMAL: return \"NORMAL\", nil"));
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

        assertTrue(typeCode.contains("CancelOccurred bool `xml:\"CancelOccurred\"`"));
        assertTrue(typeCode.contains("JobId string `xml:\"JobId\"`"));
        assertTrue(typeCode.contains("NotificationGenerationDate string `xml:\"NotificationGenerationDate\"`"));
        assertTrue(typeCode.contains("ObjectsNotPersisted []BulkObject `xml:\"ObjectsNotPersisted>Object\"`"));
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

        assertTrue(typeCode.contains("Aggregating bool `xml:\"Aggregating,attr\"`"));
        assertTrue(typeCode.contains("BucketName *string `xml:\"BucketName,attr\"`"));
        assertTrue(typeCode.contains("CachedSizeInBytes int64 `xml:\"CachedSizeInBytes,attr\"`"));
        assertTrue(typeCode.contains("ChunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee `xml:\"ChunkClientProcessingOrderGuarantee,attr\"`"));
        assertTrue(typeCode.contains("CompletedSizeInBytes int64 `xml:\"CompletedSizeInBytes,attr\"`"));
        assertTrue(typeCode.contains("EntirelyInCache bool `xml:\"EntirelyInCache,attr\"`"));
        assertTrue(typeCode.contains("JobId string `xml:\"JobId,attr\"`"));
        assertTrue(typeCode.contains("Naked bool `xml:\"Naked,attr\"`"));
        assertTrue(typeCode.contains("Name *string `xml:\"Name,attr\"`"));
        assertTrue(typeCode.contains("Nodes []JobNode `xml:\"Nodes>Node\"`"));
        assertTrue(typeCode.contains("OriginalSizeInBytes int64 `xml:\"OriginalSizeInBytes,attr\"`"));
        assertTrue(typeCode.contains("Priority Priority `xml:\"Priority,attr\"`"));
        assertTrue(typeCode.contains("RequestType JobRequestType `xml:\"RequestType,attr\"`"));
        assertTrue(typeCode.contains("StartDate string `xml:\"StartDate,attr\"`"));
        assertTrue(typeCode.contains("Status JobStatus `xml:\"Status,attr\"`"));
        assertTrue(typeCode.contains("UserId string `xml:\"UserId,attr\"`"));
        assertTrue(typeCode.contains("UserName *string `xml:\"UserName,attr\"`"));
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

        assertTrue(typeCode.contains("EndPoint *string `xml:\"EndPoint,attr\"`"));
        assertTrue(typeCode.contains("HttpPort *int `xml:\"HttpPort,attr\"`"));
        assertTrue(typeCode.contains("HttpsPort *int `xml:\"HttpsPort,attr\"`"));
        assertTrue(typeCode.contains("Id string `xml:\"Id,attr\"`"));
    }
}
