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
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.go.utils.GoTestCodeUtil;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.spectralogic.ds3autogen.go.utils.GoFunctionalTestUtil.returnsStream;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class GoFunctionalTests {

    private final static Logger LOG = LoggerFactory.getLogger(GoFunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.REQUEST, LOG);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void simpleRequestNoPayload() throws IOException, TemplateModelException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/simpleRequestNoPayload.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
    }

    @Test
    public void simpleRequestWithPayload() throws IOException, TemplateModelException {
        final String requestName = "SimpleWithPayloadRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "Bucket");

        codeGenerator.generateCode(fileUtils, "/input/simpleRequestWithPayload.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
    }

    @Test
    public void requestWithResourceInPath() throws IOException, TemplateModelException {
        final String requestName = "DeleteBucketAclSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/requestWithResourceInPath.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(requestCode.contains("func NewDeleteBucketAclSpectraS3Request(bucketAcl string) *DeleteBucketAclSpectraS3Request {"));
        assertTrue(requestCode.contains("return \"/_rest_/bucket_acl/\" + deleteBucketAclSpectraS3Request.bucketAcl"));
        assertFalse(returnsStream(requestName, requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
    }

    @Test
    public void verifyPhysicalPlacement() throws IOException, TemplateModelException {
        // Test for request with payload of Ds3Object list
        final String requestName = "VerifyPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/verifyPhysicalPlacement.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator")); //content is in request struct
        assertTrue(requestCode.contains("content: buildDs3ObjectListStream(objects),")); //content is assigned a stream
        assertTrue(returnsStream(requestName, requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
    }

    @Test
    public void replicatePutJob() throws IOException, TemplateModelException {
        // Test for request with string payload
        final String requestName = "ReplicatePutJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/replicatePutJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator")); //content is in request struct
        assertTrue(requestCode.contains("content: buildStreamFromString(requestPayload),")); //content is assigned a stream
        assertTrue(returnsStream(requestName, requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
    }
}
