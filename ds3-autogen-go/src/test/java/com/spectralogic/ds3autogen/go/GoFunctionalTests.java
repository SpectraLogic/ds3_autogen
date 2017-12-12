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
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class GoFunctionalTests {

    private final static Logger LOG = LoggerFactory.getLogger(GoFunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.RESPONSE, LOG);

    @Test
    public void simpleRequestNoPayload() throws IOException, TemplateModelException {
        final String requestName = "SimpleNoPayloadRequest";
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

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewSimpleNoPayloadResponse(webResponse WebResponse) (*SimpleNoPayloadResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &SimpleNoPayloadResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) SimpleNoPayload(request *models.SimpleNoPayloadRequest) (*models.SimpleNoPayloadResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(client.contains("response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName)."));
        assertTrue(client.contains("WithQueryParam(\"id\", request.Id)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"upload_id\", request.UploadId)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"offset\", networking.Int64PtrToStrPtr(request.Offset))."));
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

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Bucket Bucket"));
        assertFalse(responseCode.contains("`xml:\"Bucket\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewSimpleWithPayloadResponse(webResponse WebResponse) (*SimpleWithPayloadResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 204 }"));
        assertTrue(responseCode.contains("var body SimpleWithPayloadResponse"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (simpleWithPayloadResponse *SimpleWithPayloadResponse) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &simpleWithPayloadResponse.Bucket)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) SimpleWithPayload(request *models.SimpleWithPayloadRequest) (*models.SimpleWithPayloadResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_DELETE)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName + \"/\" + request.ObjectName)."));
        assertTrue(client.contains("WithQueryParam(\"void_param\", \"\")."));
        assertTrue(client.contains("WithQueryParam(\"place_holder\", request.PlaceHolder.String())."));
        assertTrue(client.contains("WithOptionalQueryParam(\"optional_place_holder\", networking.IntPtrToStrPtr(request.OptionalPlaceHolder))."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"start_bulk_get\")."));
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

        assertFalse(requestCode.contains("\"strconv\""));
        assertTrue(requestCode.contains("func NewDeleteBucketAclSpectraS3Request(bucketAcl string) *DeleteBucketAclSpectraS3Request {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewDeleteBucketAclSpectraS3Response(webResponse WebResponse) (*DeleteBucketAclSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 204 }"));
        assertTrue(responseCode.contains("return &DeleteBucketAclSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) DeleteBucketAclSpectraS3(request *models.DeleteBucketAclSpectraS3Request) (*models.DeleteBucketAclSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_DELETE)"));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket_acl/\" + request.BucketAcl)."));
    }

    @Test
    public void verifyPhysicalPlacement() throws IOException, TemplateModelException {
        // Test for request with payload of Ds3Object list
        final String requestName = "VerifyPhysicalPlacementForObjectsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "PhysicalPlacement");

        codeGenerator.generateCode(fileUtils, "/input/verifyPhysicalPlacement.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("ObjectNames []string"));
        assertTrue(requestCode.contains("StorageDomainId *string"));

        assertTrue(requestCode.contains("func NewVerifyPhysicalPlacementForObjectsSpectraS3Request(bucketName string, objectNames []string) *VerifyPhysicalPlacementForObjectsSpectraS3Request {"));
        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("ObjectNames: objectNames,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("PhysicalPlacement PhysicalPlacement"));
        assertFalse(responseCode.contains("`xml:\"PhysicalPlacement\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewVerifyPhysicalPlacementForObjectsSpectraS3Response(webResponse WebResponse) (*VerifyPhysicalPlacementForObjectsSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("var body VerifyPhysicalPlacementForObjectsSpectraS3Response"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (verifyPhysicalPlacementForObjectsSpectraS3Response *VerifyPhysicalPlacementForObjectsSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &verifyPhysicalPlacementForObjectsSpectraS3Response.PhysicalPlacement)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) VerifyPhysicalPlacementForObjectsSpectraS3(request *models.VerifyPhysicalPlacementForObjectsSpectraS3Request) (*models.VerifyPhysicalPlacementForObjectsSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(client.contains("response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket/\" + request.BucketName)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"storage_domain_id\", request.StorageDomainId)."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"verify_physical_placement\")."));
        assertTrue(client.contains("WithReadCloser(buildDs3ObjectStreamFromNames(request.ObjectNames))."));
    }

    @Test
    public void replicatePutJob() throws IOException, TemplateModelException {
        // Test for request with string payload
        final String requestName = "ReplicatePutJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/replicatePutJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(requestCode.contains("\"strconv\""));
        assertTrue(requestCode.contains("RequestPayload string"));
        assertTrue(requestCode.contains("RequestPayload: requestPayload,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList *MasterObjectList"));
        assertFalse(responseCode.contains("`xml:\"MasterObjectList\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewReplicatePutJobSpectraS3Response(webResponse WebResponse) (*ReplicatePutJobSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200, 204 }"));
        assertTrue(responseCode.contains("var body ReplicatePutJobSpectraS3Response"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("return &ReplicatePutJobSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertTrue(responseCode.contains("func (replicatePutJobSpectraS3Response *ReplicatePutJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, replicatePutJobSpectraS3Response.MasterObjectList)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) ReplicatePutJobSpectraS3(request *models.ReplicatePutJobSpectraS3Request) (*models.ReplicatePutJobSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket/\" + request.BucketName)."));
        assertTrue(client.contains("WithQueryParam(\"replicate\", \"\")."));
        assertTrue(client.contains("WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"start_bulk_put\")."));
        assertTrue(client.contains("WithReadCloser(buildStreamFromString(request.RequestPayload))."));
    }

    @Test
    public void completeMultipartUpload() throws IOException, TemplateModelException {
        // Test for request with CompleteMultipartUpload payload
        final String requestName = "CompleteMultiPartUploadRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "CompleteMultipartUploadResult");

        codeGenerator.generateCode(fileUtils, "/input/completeMultipartUpload.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(requestCode.contains("\"strconv\""));

        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("ObjectName string"));
        assertTrue(requestCode.contains("Parts []Part"));
        assertTrue(requestCode.contains("UploadId string"));

        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("ObjectName: objectName,"));
        assertTrue(requestCode.contains("UploadId: uploadId,"));
        assertTrue(requestCode.contains("Parts: parts,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));
        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("CompleteMultipartUploadResult CompleteMultipartUploadResult"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertFalse(responseCode.contains("`xml:\"CompleteMultipartUploadResult\"`"));
        assertTrue(responseCode.contains("func NewCompleteMultiPartUploadResponse(webResponse WebResponse) (*CompleteMultiPartUploadResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("var body CompleteMultiPartUploadResponse"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (completeMultiPartUploadResponse *CompleteMultiPartUploadResponse) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &completeMultiPartUploadResponse.CompleteMultipartUploadResult)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.POST);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) CompleteMultiPartUpload(request *models.CompleteMultiPartUploadRequest) (*models.CompleteMultiPartUploadResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName + \"/\" + request.ObjectName)."));
        assertTrue(client.contains("WithQueryParam(\"upload_id\", request.UploadId)."));
        assertTrue(client.contains("WithReadCloser(buildPartsListStream(request.Parts))."));
    }

    @Test
    public void deleteObjects() throws IOException, TemplateModelException {
        // Test for request with object name list payload and optional void parameter
        final String requestName = "DeleteObjectsRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "DeleteResult");

        codeGenerator.generateCode(fileUtils, "/input/deleteObjects.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(requestCode.contains("\"strconv\""));

        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("ObjectNames []string"));
        assertTrue(requestCode.contains("RollBack bool"));

        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("ObjectNames: objectNames,"));

        assertTrue(requestCode.contains("func (deleteObjectsRequest *DeleteObjectsRequest) WithRollBack() *DeleteObjectsRequest {"));
        assertTrue(requestCode.contains("deleteObjectsRequest.RollBack = true"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("DeleteResult DeleteResult"));
        assertTrue(responseCode.contains("Headers *http.Header"));
        assertFalse(responseCode.contains("`xml:\"DeleteResult\"`"));

        assertTrue(responseCode.contains("func NewDeleteObjectsResponse(webResponse WebResponse) (*DeleteObjectsResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("var body DeleteObjectsResponse"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (deleteObjectsResponse *DeleteObjectsResponse) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &deleteObjectsResponse.DeleteResult)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.POST);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) DeleteObjects(request *models.DeleteObjectsRequest) (*models.DeleteObjectsResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName)."));
        assertTrue(client.contains("WithQueryParam(\"delete\", \"\")."));
        assertTrue(client.contains("WithOptionalVoidQueryParam(\"roll_back\", request.RollBack)."));
        assertTrue(client.contains("WithReadCloser(buildDeleteObjectsPayload(request.ObjectNames))."));
    }

    @Test
    public void getJobToReplicate() throws IOException, TemplateModelException {
        // Test for request with string response payload
        final String requestName = "GetJobToReplicateSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/getJobToReplicate.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(requestCode.contains("\"strconv\""));

        assertTrue(requestCode.contains("JobId string"));
        assertTrue(requestCode.contains("JobId: jobId,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Content string"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewGetJobToReplicateSpectraS3Response(webResponse WebResponse) (*GetJobToReplicateSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &GetJobToReplicateSpectraS3Response{Content: content, Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) GetJobToReplicateSpectraS3(request *models.GetJobToReplicateSpectraS3Request) (*models.GetJobToReplicateSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(client.contains("response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(client.contains("WithPath(\"/_rest_/job/\" + request.JobId)"));
        assertTrue(client.contains("WithQueryParam(\"replicate\", \"\")."));
    }

    @Test
    public void getObject() throws IOException, TemplateModelException {
        final String requestName = "GetObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/getObject.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request imports
        assertTrue(requestCode.contains("\"fmt\""));
        assertFalse(requestCode.contains("\"ds3/networking\""));

        // test request struct
        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("ObjectName string"));
        assertTrue(requestCode.contains("Checksum Checksum"));
        assertTrue(requestCode.contains("Job *string"));
        assertTrue(requestCode.contains("Offset *int64"));
        assertTrue(requestCode.contains("Metadata map[string]string"));

        assertTrue(requestCode.contains("type rangeHeader struct {"));

        // test constructor and with-constructors
        assertTrue(requestCode.contains("func NewGetObjectRequest(bucketName string, objectName string) *GetObjectRequest {"));
        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("ObjectName: objectName,"));
        assertTrue(requestCode.contains("Checksum: NewNoneChecksum(),"));
        assertTrue(requestCode.contains("Metadata: make(map[string]string)"));

        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithRange(start, end int) *GetObjectRequest {"));
        assertTrue(requestCode.contains("getObjectRequest.Metadata[\"Range\"] = fmt.Sprintf(\"bytes=%d-%d\", start, end)"));

        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithChecksum(contentHash string, checksumType ChecksumType) *GetObjectRequest {"));
        assertTrue(requestCode.contains("getObjectRequest.Checksum.ContentHash = contentHash"));
        assertTrue(requestCode.contains("getObjectRequest.Checksum.Type = checksumType"));

        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithJob(job string) *GetObjectRequest {"));
        assertTrue(requestCode.contains("getObjectRequest.Job = &job"));

        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithOffset(offset int64) *GetObjectRequest {"));
        assertTrue(requestCode.contains("getObjectRequest.Offset = &offset"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"io\""));
        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Content io.ReadCloser"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewGetObjectResponse(webResponse WebResponse) (*GetObjectResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200, 206 }"));
        assertTrue(responseCode.contains("return &GetObjectResponse{ Content: webResponse.Body(), Headers: webResponse.Header() }, nil"));
        assertFalse(responseCode.contains("return &GetObjectResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) GetObject(request *models.GetObjectRequest) (*models.GetObjectResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName + \"/\" + request.ObjectName)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"job\", request.Job)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"offset\", networking.Int64PtrToStrPtr(request.Offset))."));
        assertTrue(client.contains("WithChecksum(request.Checksum)."));
        assertTrue(client.contains("WithHeaders(request.Metadata)."));
    }

    @Test
    public void getAzureDataReplicationRules() throws IOException, TemplateModelException {
        // This tests generation of request with "type" optional parameter keyword conflict
        final String requestName = "GetAzureDataReplicationRulesSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "AzureDataReplicationRule");

        codeGenerator.generateCode(fileUtils, "/input/getAzureDataReplicationRules.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request struct
        assertTrue(requestCode.contains("DataPolicyId *string"));
        assertTrue(requestCode.contains("DataReplicationRuleType DataReplicationRuleType"));
        assertTrue(requestCode.contains("PageLength *int"));
        assertTrue(requestCode.contains("PageOffset *int"));
        assertTrue(requestCode.contains("PageStartMarker *string"));
        assertTrue(requestCode.contains("ReplicateDeletes *bool"));
        assertTrue(requestCode.contains("State DataPlacementRuleState"));
        assertTrue(requestCode.contains("TargetId *string"));
        assertTrue(requestCode.contains("LastPage bool"));

        // test constructor and with-constructors
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithDataPolicyId(dataPolicyId string) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithPageLength(pageLength int) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithPageOffset(pageOffset int) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithPageStartMarker(pageStartMarker string) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithReplicateDeletes(replicateDeletes bool) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithState(state DataPlacementRuleState) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithTargetId(targetId string) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithDataReplicationRuleType(dataReplicationRuleType DataReplicationRuleType) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(requestCode.contains("func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithLastPage() *GetAzureDataReplicationRulesSpectraS3Request {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("AzureDataReplicationRuleList AzureDataReplicationRuleList"));
        assertFalse(responseCode.contains("`xml:\"AzureDataReplicationRuleList\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewGetAzureDataReplicationRulesSpectraS3Response(webResponse WebResponse) (*GetAzureDataReplicationRulesSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (getAzureDataReplicationRulesSpectraS3Response *GetAzureDataReplicationRulesSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &getAzureDataReplicationRulesSpectraS3Response.AzureDataReplicationRuleList)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) GetAzureDataReplicationRulesSpectraS3(request *models.GetAzureDataReplicationRulesSpectraS3Request) (*models.GetAzureDataReplicationRulesSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(client.contains("response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(client.contains("WithPath(\"/_rest_/azure_data_replication_rule\")."));
        assertTrue(client.contains("WithOptionalQueryParam(\"data_policy_id\", request.DataPolicyId)."));
        assertTrue(client.contains("WithOptionalVoidQueryParam(\"last_page\", request.LastPage)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"page_length\", networking.IntPtrToStrPtr(request.PageLength))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"page_offset\", networking.IntPtrToStrPtr(request.PageOffset))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"page_start_marker\", request.PageStartMarker)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"replicate_deletes\", networking.BoolPtrToStrPtr(request.ReplicateDeletes))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"state\", networking.InterfaceToStrPtr(request.State))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"target_id\", request.TargetId)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"type\", networking.InterfaceToStrPtr(request.DataReplicationRuleType))."));
    }

    @Test
    public void putAzureDataReplicationRule() throws IOException, TemplateModelException {
        // This tests generation of request with "type" required parameter keyword conflict
        final String requestName = "PutAzureDataReplicationRuleSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "AzureDataReplicationRule");

        codeGenerator.generateCode(fileUtils, "/input/putAzureDataReplicationRule.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request struct
        assertTrue(requestCode.contains("DataPolicyId string"));
        assertTrue(requestCode.contains("DataReplicationRuleType DataReplicationRuleType"));
        assertTrue(requestCode.contains("MaxBlobPartSizeInBytes *int64"));
        assertTrue(requestCode.contains("ReplicateDeletes *bool"));
        assertTrue(requestCode.contains("TargetId string"));

        // test constructor and
        assertTrue(requestCode.contains("func NewPutAzureDataReplicationRuleSpectraS3Request(dataPolicyId string, dataReplicationRuleType DataReplicationRuleType, targetId string) *PutAzureDataReplicationRuleSpectraS3Request {"));
        assertTrue(requestCode.contains("DataPolicyId: dataPolicyId,"));
        assertTrue(requestCode.contains("TargetId: targetId,"));
        assertTrue(requestCode.contains("DataReplicationRuleType: dataReplicationRuleType,"));

        // test with-constructors
        assertTrue(requestCode.contains("func (putAzureDataReplicationRuleSpectraS3Request *PutAzureDataReplicationRuleSpectraS3Request) WithMaxBlobPartSizeInBytes(maxBlobPartSizeInBytes int64) *PutAzureDataReplicationRuleSpectraS3Request {"));
        assertTrue(requestCode.contains("func (putAzureDataReplicationRuleSpectraS3Request *PutAzureDataReplicationRuleSpectraS3Request) WithReplicateDeletes(replicateDeletes bool) *PutAzureDataReplicationRuleSpectraS3Request {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("AzureDataReplicationRule AzureDataReplicationRule"));
        assertFalse(responseCode.contains("`xml:\"AzureDataReplicationRule\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewPutAzureDataReplicationRuleSpectraS3Response(webResponse WebResponse) (*PutAzureDataReplicationRuleSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 201 }"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (putAzureDataReplicationRuleSpectraS3Response *PutAzureDataReplicationRuleSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &putAzureDataReplicationRuleSpectraS3Response.AzureDataReplicationRule)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.POST);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) PutAzureDataReplicationRuleSpectraS3(request *models.PutAzureDataReplicationRuleSpectraS3Request) (*models.PutAzureDataReplicationRuleSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(client.contains("WithPath(\"/_rest_/azure_data_replication_rule\")."));
        assertTrue(client.contains("WithQueryParam(\"data_policy_id\", request.DataPolicyId)."));
        assertTrue(client.contains("WithQueryParam(\"target_id\", request.TargetId)."));
        assertTrue(client.contains("WithQueryParam(\"type\", request.DataReplicationRuleType.String())."));
        assertTrue(client.contains("WithOptionalQueryParam(\"max_blob_part_size_in_bytes\", networking.Int64PtrToStrPtr(request.MaxBlobPartSizeInBytes))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"replicate_deletes\", networking.BoolPtrToStrPtr(request.ReplicateDeletes))."));
    }

    @Test
    public void deleteJobCreatedNotificationRegistration() throws IOException, TemplateModelException {
        // This tests correct generation of notificationId required parameter from resource
        final String requestName = "DeleteJobCreatedNotificationRegistrationSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/deleteJobCreatedNotificationRegistration.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request struct
        assertTrue(requestCode.contains("NotificationId string"));

        // test constructor
        assertTrue(requestCode.contains("func NewDeleteJobCreatedNotificationRegistrationSpectraS3Request(notificationId string) *DeleteJobCreatedNotificationRegistrationSpectraS3Request {"));
        assertTrue(requestCode.contains("NotificationId: notificationId,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewDeleteJobCreatedNotificationRegistrationSpectraS3Response(webResponse WebResponse) (*DeleteJobCreatedNotificationRegistrationSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 204 }"));
        assertTrue(responseCode.contains("return &DeleteJobCreatedNotificationRegistrationSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) DeleteJobCreatedNotificationRegistrationSpectraS3(request *models.DeleteJobCreatedNotificationRegistrationSpectraS3Request) (*models.DeleteJobCreatedNotificationRegistrationSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_DELETE)."));
        assertTrue(client.contains("WithPath(\"/_rest_/job_created_notification_registration/\" + request.NotificationId)."));
    }

    @Test
    public void putMultiPartUploadPart() throws IOException, TemplateModelException {
        // This tests correct generation of request with ReaderWithSizeDecorator payload
        final String requestName = "PutMultiPartUploadPartRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/putMultiPartUploadPart.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request imports
        assertFalse(requestCode.contains("\"ds3/networking\""));

        // test request struct
        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("ObjectName string"));
        assertTrue(requestCode.contains("Content ReaderWithSizeDecorator"));
        assertTrue(requestCode.contains("PartNumber int"));
        assertTrue(requestCode.contains("UploadId string"));

        // test constructor
        assertTrue(requestCode.contains("func NewPutMultiPartUploadPartRequest(bucketName string, objectName string, content ReaderWithSizeDecorator, partNumber int, uploadId string) *PutMultiPartUploadPartRequest {"));
        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("ObjectName: objectName,"));
        assertTrue(requestCode.contains("PartNumber: partNumber,"));
        assertTrue(requestCode.contains("UploadId: uploadId,"));
        assertTrue(requestCode.contains("Content: content,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewPutMultiPartUploadPartResponse(webResponse WebResponse) (*PutMultiPartUploadPartResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &PutMultiPartUploadPartResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) PutMultiPartUploadPart(request *models.PutMultiPartUploadPartRequest) (*models.PutMultiPartUploadPartResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName + \"/\" + request.ObjectName)."));
        assertTrue(client.contains("WithQueryParam(\"part_number\", strconv.Itoa(request.PartNumber))."));
        assertTrue(client.contains("WithQueryParam(\"upload_id\", request.UploadId)."));
        assertTrue(client.contains("WithReader(request.Content)."));
    }

    @Test
    public void putObject() throws IOException, TemplateModelException {
        // This tests correct generation of request with ReaderWithSizeDecorator payload
        final String requestName = "PutObjectRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/putObject.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request imports
        assertFalse(requestCode.contains("\"ds3/networking\""));
        assertTrue(requestCode.contains("strings"));

        // test request struct
        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("ObjectName string"));
        assertTrue(requestCode.contains("Job *string"));
        assertTrue(requestCode.contains("Offset *int64"));
        assertTrue(requestCode.contains("Content ReaderWithSizeDecorator"));
        assertTrue(requestCode.contains("Checksum Checksum"));
        assertTrue(requestCode.contains("Metadata map[string]string"));

        // test constructor
        assertTrue(requestCode.contains("func NewPutObjectRequest(bucketName string, objectName string, content ReaderWithSizeDecorator) *PutObjectRequest {"));
        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("ObjectName: objectName,"));
        assertTrue(requestCode.contains("Content: content,"));
        assertTrue(requestCode.contains("Checksum: NewNoneChecksum()"));
        assertTrue(requestCode.contains("Metadata: make(map[string]string),"));

        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) WithJob(job string) *PutObjectRequest {"));
        assertTrue(requestCode.contains("putObjectRequest.Job = &job"));
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) WithOffset(offset int64) *PutObjectRequest {"));
        assertTrue(requestCode.contains("putObjectRequest.Offset = &offset"));

        // test checksum
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) WithChecksum(contentHash string, checksumType ChecksumType) *PutObjectRequest {"));
        assertTrue(requestCode.contains("putObjectRequest.Checksum.ContentHash = contentHash"));
        assertTrue(requestCode.contains("putObjectRequest.Checksum.Type = checksumType"));

        // test metadata
        assertTrue(requestCode.contains("const ( AMZ_META_HEADER = \"x-amz-meta-\" )"));
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) WithMetaData(key string, values ...string) *PutObjectRequest {"));
        assertTrue(requestCode.contains("if strings.HasPrefix(strings.ToLower(key), AMZ_META_HEADER) {"));
        assertTrue(requestCode.contains("putObjectRequest.Metadata[key] = strings.Join(values, \",\")"));
        assertTrue(requestCode.contains("putObjectRequest.Metadata[strings.ToLower(AMZ_META_HEADER + key)] = strings.Join(values, \",\")"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewPutObjectResponse(webResponse WebResponse) (*PutObjectResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &PutObjectResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) PutObject(request *models.PutObjectRequest) (*models.PutObjectResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName + \"/\" + request.ObjectName)"));
        assertTrue(client.contains("WithOptionalQueryParam(\"job\", request.Job)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"offset\", networking.Int64PtrToStrPtr(request.Offset))."));
        assertTrue(client.contains("WithReader(request.Content)."));
        assertTrue(client.contains("WithChecksum(request.Checksum)."));
        assertTrue(client.contains("WithHeaders(request.Metadata)."));
    }

    @Test
    public void clearSuspectBlobAzureTargetsSpectraS3() throws IOException, TemplateModelException {
        final String requestName = "ClearSuspectBlobAzureTargetsSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName);

        codeGenerator.generateCode(fileUtils, "/input/clearSuspectBlobAzureTargetsSpectraS3.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(requestCode.contains("type ClearSuspectBlobAzureTargetsSpectraS3Request struct {"));
        assertTrue(requestCode.contains("Ids []string"));
        assertTrue(requestCode.contains("Force bool"));

        assertTrue(requestCode.contains("func NewClearSuspectBlobAzureTargetsSpectraS3Request(ids []string) *ClearSuspectBlobAzureTargetsSpectraS3Request {"));
        assertTrue(requestCode.contains("Ids: ids,"));

        assertTrue(requestCode.contains("func (clearSuspectBlobAzureTargetsSpectraS3Request *ClearSuspectBlobAzureTargetsSpectraS3Request) WithForce() *ClearSuspectBlobAzureTargetsSpectraS3Request {"));
        assertTrue(requestCode.contains("clearSuspectBlobAzureTargetsSpectraS3Request.Force = true"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_DELETE)."));
        assertTrue(client.contains("WithPath(\"/_rest_/suspect_blob_azure_target\")"));
        assertTrue(client.contains("WithOptionalVoidQueryParam(\"force\", request.Force)."));
        assertTrue(client.contains("WithReadCloser(buildIdListPayload(request.Ids))."));
    }

    @Test
    public void putBulkJobSpectraS3Request() throws IOException, TemplateModelException {
        final String requestName = "PutBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/putBulkJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("Aggregating *bool"));
        assertTrue(requestCode.contains("ImplicitJobIdResolution *bool"));
        assertTrue(requestCode.contains("MaxUploadSize *int64"));
        assertTrue(requestCode.contains("MinimizeSpanningAcrossMedia *bool"));
        assertTrue(requestCode.contains("Name *string"));
        assertTrue(requestCode.contains("Priority Priority"));
        assertTrue(requestCode.contains("VerifyAfterWrite *bool"));
        assertTrue(requestCode.contains("Objects []Ds3PutObject"));
        assertTrue(requestCode.contains("IgnoreNamingConflicts bool"));
        assertTrue(requestCode.contains("Force bool"));

        assertTrue(requestCode.contains("func NewPutBulkJobSpectraS3Request(bucketName string, objects []Ds3PutObject) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("Objects: objects,"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithAggregating(aggregating bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.Aggregating = &aggregating"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithImplicitJobIdResolution(implicitJobIdResolution bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.ImplicitJobIdResolution = &implicitJobIdResolution"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithMaxUploadSize(maxUploadSize int64) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.MaxUploadSize = &maxUploadSize"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithMinimizeSpanningAcrossMedia(minimizeSpanningAcrossMedia bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.MinimizeSpanningAcrossMedia = &minimizeSpanningAcrossMedia"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithPriority(priority Priority) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.Priority = priority"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithVerifyAfterWrite(verifyAfterWrite bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.VerifyAfterWrite = &verifyAfterWrite"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithName(name string) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.Name = &name"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithForce() *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.Force = true"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithIgnoreNamingConflicts() *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("putBulkJobSpectraS3Request.IgnoreNamingConflicts = true"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList MasterObjectList"));

        assertTrue(responseCode.contains("func (putBulkJobSpectraS3Response *PutBulkJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &putBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(responseCode.contains("func NewPutBulkJobSpectraS3Response(webResponse WebResponse) (*PutBulkJobSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("case 200:"));
        assertTrue(responseCode.contains("var body PutBulkJobSpectraS3Response"));

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket/\" + request.BucketName)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"aggregating\", networking.BoolPtrToStrPtr(request.Aggregating))."));
        assertTrue(client.contains("WithOptionalVoidQueryParam(\"force\", request.Force)."));
        assertTrue(client.contains("WithOptionalVoidQueryParam(\"ignore_naming_conflicts\", request.IgnoreNamingConflicts)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"implicit_job_id_resolution\", networking.BoolPtrToStrPtr(request.ImplicitJobIdResolution))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"max_upload_size\", networking.Int64PtrToStrPtr(request.MaxUploadSize))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"minimize_spanning_across_media\", networking.BoolPtrToStrPtr(request.MinimizeSpanningAcrossMedia))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"name\", request.Name)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"verify_after_write\", networking.BoolPtrToStrPtr(request.VerifyAfterWrite))."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"start_bulk_put\")."));
        assertTrue(client.contains("WithReadCloser(buildDs3PutObjectListStream(request.Objects))."));
    }

    @Test
    public void getBulkJobSpectraS3Request() throws IOException, TemplateModelException {
        final String requestName = "GetBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/getBulkJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertFalse(requestCode.contains("import \"ds3\""));

        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("Aggregating *bool"));
        assertTrue(requestCode.contains("ChunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee"));
        assertTrue(requestCode.contains("ImplicitJobIdResolution *bool"));
        assertTrue(requestCode.contains("Name *string"));
        assertTrue(requestCode.contains("Priority Priority"));
        assertTrue(requestCode.contains("Objects []Ds3GetObject"));

        assertTrue(requestCode.contains("func NewGetBulkJobSpectraS3Request(bucketName string, objectNames []string) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("Objects: buildDs3GetObjectSliceFromNames(objectNames),"));

        assertTrue(requestCode.contains("func NewGetBulkJobSpectraS3RequestWithPartialObjects(bucketName string, objects []Ds3GetObject) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("Objects: objects,"));

        assertTrue(requestCode.contains("func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithAggregating(aggregating bool) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("getBulkJobSpectraS3Request.Aggregating = &aggregating"));

        assertTrue(requestCode.contains("func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithChunkClientProcessingOrderGuarantee(chunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("getBulkJobSpectraS3Request.ChunkClientProcessingOrderGuarantee = chunkClientProcessingOrderGuarantee"));

        assertTrue(requestCode.contains("func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithImplicitJobIdResolution(implicitJobIdResolution bool) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("getBulkJobSpectraS3Request.ImplicitJobIdResolution = &implicitJobIdResolution"));

        assertTrue(requestCode.contains("func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithPriority(priority Priority) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("getBulkJobSpectraS3Request.Priority = priority"));

        assertTrue(requestCode.contains("func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithName(name string) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("getBulkJobSpectraS3Request.Name = &name"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList MasterObjectList"));

        assertTrue(responseCode.contains("func (getBulkJobSpectraS3Response *GetBulkJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &getBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(responseCode.contains("func NewGetBulkJobSpectraS3Response(webResponse WebResponse) (*GetBulkJobSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("case 200:"));
        assertTrue(responseCode.contains("var body GetBulkJobSpectraS3Response"));

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket/\" + request.BucketName)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"aggregating\", networking.BoolPtrToStrPtr(request.Aggregating))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"chunk_client_processing_order_guarantee\", networking.InterfaceToStrPtr(request.ChunkClientProcessingOrderGuarantee))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"implicit_job_id_resolution\", networking.BoolPtrToStrPtr(request.ImplicitJobIdResolution))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"name\", request.Name)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"start_bulk_get\")."));
        assertTrue(client.contains("WithReadCloser(buildDs3GetObjectListStream(request.Objects))."));
    }

    @Test
    public void verifyBulkJobSpectraS3Request() throws IOException, TemplateModelException {
        final String requestName = "VerifyBulkJobSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "MasterObjectList");

        codeGenerator.generateCode(fileUtils, "/input/verifyBulkJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertFalse(requestCode.contains("import \"ds3\""));

        assertTrue(requestCode.contains("BucketName string"));
        assertTrue(requestCode.contains("Aggregating *bool"));
        assertTrue(requestCode.contains("Name *string"));
        assertTrue(requestCode.contains("Priority Priority"));
        assertTrue(requestCode.contains("Objects []Ds3GetObject"));

        assertTrue(requestCode.contains("func NewVerifyBulkJobSpectraS3Request(bucketName string, objectNames []string) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("BucketName: bucketName,"));
        assertTrue(requestCode.contains("Objects: buildDs3GetObjectSliceFromNames(objectNames),"));

        assertTrue(requestCode.contains("func NewVerifyBulkJobSpectraS3RequestWithPartialObjects(bucketName string, objects []Ds3GetObject) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("Objects: objects,"));

        assertTrue(requestCode.contains("func (verifyBulkJobSpectraS3Request *VerifyBulkJobSpectraS3Request) WithAggregating(aggregating bool) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("verifyBulkJobSpectraS3Request.Aggregating = &aggregating"));

        assertTrue(requestCode.contains("func (verifyBulkJobSpectraS3Request *VerifyBulkJobSpectraS3Request) WithPriority(priority Priority) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("verifyBulkJobSpectraS3Request.Priority = priority"));

        assertTrue(requestCode.contains("func (verifyBulkJobSpectraS3Request *VerifyBulkJobSpectraS3Request) WithName(name string) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("verifyBulkJobSpectraS3Request.Name = &name"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList MasterObjectList"));

        assertTrue(responseCode.contains("func (verifyBulkJobSpectraS3Response *VerifyBulkJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &verifyBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(responseCode.contains("func NewVerifyBulkJobSpectraS3Response(webResponse WebResponse) (*VerifyBulkJobSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("case 200:"));
        assertTrue(responseCode.contains("var body VerifyBulkJobSpectraS3Response"));

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket/\" + request.BucketName)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"aggregating\", networking.BoolPtrToStrPtr(request.Aggregating))."));
        assertTrue(client.contains("WithOptionalQueryParam(\"name\", request.Name)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"start_bulk_verify\")."));
        assertTrue(client.contains("WithReadCloser(buildDs3GetObjectListStream(request.Objects))."));
    }

    @Test
    public void putBucketSpectraS3Test() throws IOException, TemplateModelException {
        final String requestName = "PutBucketSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils, requestName, "Bucket");

        codeGenerator.generateCode(fileUtils, "/input/putBucketSpectraS3.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(requestCode.contains("type PutBucketSpectraS3Request struct {"));
        assertTrue(requestCode.contains("DataPolicyId *string"));
        assertTrue(requestCode.contains("Id *string"));
        assertTrue(requestCode.contains("Name string"));
        assertTrue(requestCode.contains("UserId *string"));

        assertTrue(requestCode.contains("func NewPutBucketSpectraS3Request(name string) *PutBucketSpectraS3Request {"));
        assertTrue(requestCode.contains("Name: name,"));

        assertTrue(requestCode.contains("func (putBucketSpectraS3Request *PutBucketSpectraS3Request) WithDataPolicyId(dataPolicyId string) *PutBucketSpectraS3Request {"));
        assertTrue(requestCode.contains("putBucketSpectraS3Request.DataPolicyId = &dataPolicyId"));

        assertTrue(requestCode.contains("func (putBucketSpectraS3Request *PutBucketSpectraS3Request) WithId(id string) *PutBucketSpectraS3Request {"));
        assertTrue(requestCode.contains("putBucketSpectraS3Request.Id = &id"));

        assertTrue(requestCode.contains("func (putBucketSpectraS3Request *PutBucketSpectraS3Request) WithUserId(userId string) *PutBucketSpectraS3Request {"));
        assertTrue(requestCode.contains("putBucketSpectraS3Request.UserId = &userId"));
    }
}
