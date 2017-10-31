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

import static com.spectralogic.ds3autogen.go.utils.GoFunctionalTestUtil.returnsStream;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class GoFunctionalTests {

    private final static Logger LOG = LoggerFactory.getLogger(GoFunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.CLIENT, LOG);

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

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewSimpleNoPayloadResponse(webResponse networking.WebResponse) (*SimpleNoPayloadResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &SimpleNoPayloadResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse networking.WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) SimpleNoPayload(request *models.SimpleNoPayloadRequest) (*models.SimpleNoPayloadResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(&networkRetryDecorator, client.clientPolicy.maxRedirect)"));
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
        assertTrue(requestCode.contains("\"strconv\""));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Bucket Bucket"));
        assertFalse(responseCode.contains("`xml:\"Bucket\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewSimpleWithPayloadResponse(webResponse networking.WebResponse) (*SimpleWithPayloadResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 204 }"));
        assertTrue(responseCode.contains("var body SimpleWithPayloadResponse"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (simpleWithPayloadResponse *SimpleWithPayloadResponse) parse(webResponse networking.WebResponse) error {"));
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
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
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
        assertFalse(returnsStream(requestName, requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewDeleteBucketAclSpectraS3Response(webResponse networking.WebResponse) (*DeleteBucketAclSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 204 }"));
        assertTrue(responseCode.contains("return &DeleteBucketAclSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse networking.WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) DeleteBucketAclSpectraS3(request *models.DeleteBucketAclSpectraS3Request) (*models.DeleteBucketAclSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
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

        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));
        assertFalse(requestCode.contains("\"strconv\""));

        assertTrue(requestCode.contains("bucketName string"));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator"));
        assertTrue(requestCode.contains("storageDomainId string"));

        assertTrue(requestCode.contains("func NewVerifyPhysicalPlacementForObjectsSpectraS3Request(bucketName string, objectNames []string) *VerifyPhysicalPlacementForObjectsSpectraS3Request {"));
        assertTrue(requestCode.contains("queryParams.Set(\"operation\", \"verify_physical_placement\")"));
        assertTrue(requestCode.contains("bucketName: bucketName,"));
        assertTrue(requestCode.contains("content: buildDs3ObjectStreamFromNames(objectNames),"));

        assertTrue(returnsStream(requestName, requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("PhysicalPlacement PhysicalPlacement"));
        assertFalse(responseCode.contains("`xml:\"PhysicalPlacement\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewVerifyPhysicalPlacementForObjectsSpectraS3Response(webResponse networking.WebResponse) (*VerifyPhysicalPlacementForObjectsSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("var body VerifyPhysicalPlacementForObjectsSpectraS3Response"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (verifyPhysicalPlacementForObjectsSpectraS3Response *VerifyPhysicalPlacementForObjectsSpectraS3Response) parse(webResponse networking.WebResponse) error {"));
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
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(&networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(client.contains("response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket/\" + request.BucketName)."));
        assertTrue(client.contains("WithOptionalQueryParam(\"storage_domain_id\", request.StorageDomainId)."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"verify_physical_placement\")."));
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
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator")); //content is in request struct
        assertTrue(requestCode.contains("content: buildStreamFromString(requestPayload),")); //content is assigned a stream
        assertTrue(returnsStream(requestName, requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList *MasterObjectList"));
        assertFalse(responseCode.contains("`xml:\"MasterObjectList\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewReplicatePutJobSpectraS3Response(webResponse networking.WebResponse) (*ReplicatePutJobSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200, 204 }"));
        assertTrue(responseCode.contains("var body ReplicatePutJobSpectraS3Response"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("return &ReplicatePutJobSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertTrue(responseCode.contains("func (replicatePutJobSpectraS3Response *ReplicatePutJobSpectraS3Response) parse(webResponse networking.WebResponse) error {"));
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
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(client.contains("WithPath(\"/_rest_/bucket/\" + request.BucketName)."));
        assertTrue(client.contains("WithQueryParam(\"replicate\", \"\")."));
        assertTrue(client.contains("WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(client.contains("WithQueryParam(\"operation\", \"start_bulk_put\")."));
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
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator")); //content is in request struct
        assertTrue(requestCode.contains("content: buildPartsListStream(parts),")); //content is assigned a stream
        assertTrue(returnsStream(requestName, requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));
        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("CompleteMultipartUploadResult CompleteMultipartUploadResult"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertFalse(responseCode.contains("`xml:\"CompleteMultipartUploadResult\"`"));
        assertTrue(responseCode.contains("func NewCompleteMultiPartUploadResponse(webResponse networking.WebResponse) (*CompleteMultiPartUploadResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("var body CompleteMultiPartUploadResponse"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (completeMultiPartUploadResponse *CompleteMultiPartUploadResponse) parse(webResponse networking.WebResponse) error {"));
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
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName + \"/\" + request.ObjectName)."));
        assertTrue(client.contains("WithQueryParam(\"upload_id\", request.UploadId)."));
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
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator")); //content is in request struct
        assertTrue(requestCode.contains("content: buildDeleteObjectsPayload(objectNames),")); //content is assigned a stream
        assertTrue(returnsStream(requestName, requestCode));

        // verify with-constructor of void param type is generated correctly
        assertTrue(requestCode.contains("func (deleteObjectsRequest *DeleteObjectsRequest) WithRollBack() *DeleteObjectsRequest {"));
        assertFalse(requestCode.contains("deleteObjectsRequest.rollBack = rollBack"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("DeleteResult DeleteResult"));
        assertTrue(responseCode.contains("Headers *http.Header"));
        assertFalse(responseCode.contains("`xml:\"DeleteResult\"`"));

        assertTrue(responseCode.contains("func NewDeleteObjectsResponse(webResponse networking.WebResponse) (*DeleteObjectsResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("var body DeleteObjectsResponse"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (deleteObjectsResponse *DeleteObjectsResponse) parse(webResponse networking.WebResponse) error {"));
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
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(client.contains("WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(client.contains("WithPath(\"/\" + request.BucketName)."));
        assertTrue(client.contains("WithQueryParam(\"delete\", \"\")."));
        assertTrue(client.contains("WithOptionalVoidQueryParam(\"roll_back\", request.RollBack)."));
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
        assertTrue(requestCode.contains("jobId string"));
        assertTrue(requestCode.contains("jobId: jobId,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Content string"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewGetJobToReplicateSpectraS3Response(webResponse networking.WebResponse) (*GetJobToReplicateSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &GetJobToReplicateSpectraS3Response{Content: content, Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse networking.WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) GetJobToReplicateSpectraS3(request *models.GetJobToReplicateSpectraS3Request) (*models.GetJobToReplicateSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(&networkRetryDecorator, client.clientPolicy.maxRedirect)"));
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
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));
        assertTrue(requestCode.contains("\"strconv\""));
        // test request struct
        assertTrue(requestCode.contains("rangeHeader *rangeHeader"));
        assertTrue(requestCode.contains("checksum networking.Checksum"));
        assertTrue(requestCode.contains("type rangeHeader struct {"));
        // test constructor and with-constructors
        assertTrue(requestCode.contains("func NewGetObjectRequest(bucketName string, objectName string) *GetObjectRequest {"));
        assertTrue(requestCode.contains("checksum: networking.NewNoneChecksum(),"));
        assertTrue(requestCode.contains(""));
        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithRange(start, end int) *GetObjectRequest {"));
        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithChecksum(contentHash string, checksumType networking.ChecksumType) *GetObjectRequest {"));
        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithJob(job string) *GetObjectRequest {"));
        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) WithOffset(offset int64) *GetObjectRequest {"));
        // test header and checksum functions
        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) Header() *http.Header {"));
        assertTrue(requestCode.contains("return &http.Header{ \"Range\": []string{ rng } }"));
        assertTrue(requestCode.contains("func (getObjectRequest *GetObjectRequest) GetChecksum() networking.Checksum {"));
        assertTrue(requestCode.contains("return getObjectRequest.checksum"));
        assertTrue(requestCode.contains("func (GetObjectRequest) GetContentStream() networking.ReaderWithSizeDecorator {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"io\""));
        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Content io.ReadCloser"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewGetObjectResponse(webResponse networking.WebResponse) (*GetObjectResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200, 206 }"));
        assertTrue(responseCode.contains("return &GetObjectResponse{ Content: webResponse.Body(), Headers: webResponse.Header() }, nil"));
        assertTrue(responseCode.contains("return &GetObjectResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse networking.WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(client.contains("func (client *Client) GetObject(request *models.GetObjectRequest) (*models.GetObjectResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
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

        // test request imports
        assertTrue(requestCode.contains("\"strconv\""));
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));

        // test request struct
        assertTrue(requestCode.contains("dataPolicyId string"));
        assertTrue(requestCode.contains("pageLength int"));
        assertTrue(requestCode.contains("pageOffset int"));
        assertTrue(requestCode.contains("pageStartMarker string"));
        assertTrue(requestCode.contains("replicateDeletes bool"));
        assertTrue(requestCode.contains("state DataPlacementRuleState"));
        assertTrue(requestCode.contains("targetId string"));
        assertTrue(requestCode.contains("dataReplicationRuleType DataReplicationRuleType"));

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

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("AzureDataReplicationRuleList AzureDataReplicationRuleList"));
        assertFalse(responseCode.contains("`xml:\"AzureDataReplicationRuleList\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewGetAzureDataReplicationRulesSpectraS3Response(webResponse networking.WebResponse) (*GetAzureDataReplicationRulesSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (getAzureDataReplicationRulesSpectraS3Response *GetAzureDataReplicationRulesSpectraS3Response) parse(webResponse networking.WebResponse) error {"));
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
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
        assertTrue(client.contains("httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(&networkRetryDecorator, client.clientPolicy.maxRedirect)"));
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

        // test request imports
        assertTrue(requestCode.contains("\"strconv\""));
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));

        // test request struct
        assertTrue(requestCode.contains("dataPolicyId string"));
        assertTrue(requestCode.contains("dataReplicationRuleType DataReplicationRuleType"));
        assertTrue(requestCode.contains("maxBlobPartSizeInBytes int64"));
        assertTrue(requestCode.contains("replicateDeletes bool"));
        assertTrue(requestCode.contains("targetId string"));

        // test constructor and
        assertTrue(requestCode.contains("func NewPutAzureDataReplicationRuleSpectraS3Request(dataPolicyId string, dataReplicationRuleType DataReplicationRuleType, targetId string) *PutAzureDataReplicationRuleSpectraS3Request {"));
        assertTrue(requestCode.contains("queryParams.Set(\"type\", dataReplicationRuleType.String())"));
        assertTrue(requestCode.contains("dataReplicationRuleType: dataReplicationRuleType,"));

        // test with-constructors
        assertTrue(requestCode.contains("func (putAzureDataReplicationRuleSpectraS3Request *PutAzureDataReplicationRuleSpectraS3Request) WithMaxBlobPartSizeInBytes(maxBlobPartSizeInBytes int64) *PutAzureDataReplicationRuleSpectraS3Request {"));
        assertTrue(requestCode.contains("func (putAzureDataReplicationRuleSpectraS3Request *PutAzureDataReplicationRuleSpectraS3Request) WithReplicateDeletes(replicateDeletes bool) *PutAzureDataReplicationRuleSpectraS3Request {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("AzureDataReplicationRule AzureDataReplicationRule"));
        assertFalse(responseCode.contains("`xml:\"AzureDataReplicationRule\"`"));
        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewPutAzureDataReplicationRuleSpectraS3Response(webResponse networking.WebResponse) (*PutAzureDataReplicationRuleSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 201 }"));
        assertTrue(responseCode.contains("if err := body.parse(webResponse); err != nil {"));
        assertTrue(responseCode.contains("body.Headers = webResponse.Header()"));
        assertTrue(responseCode.contains("return &body, nil"));

        assertTrue(responseCode.contains("func (putAzureDataReplicationRuleSpectraS3Response *PutAzureDataReplicationRuleSpectraS3Response) parse(webResponse networking.WebResponse) error {"));
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
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
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

        // test request imports
        assertFalse(requestCode.contains("\"strconv\""));
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));

        // test request struct
        assertTrue(requestCode.contains("notificationId string"));
        assertTrue(requestCode.contains("queryParams *url.Values"));

        // test constructor
        assertTrue(requestCode.contains("func NewDeleteJobCreatedNotificationRegistrationSpectraS3Request(notificationId string) *DeleteJobCreatedNotificationRegistrationSpectraS3Request {"));
        assertTrue(requestCode.contains("notificationId: notificationId,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewDeleteJobCreatedNotificationRegistrationSpectraS3Response(webResponse networking.WebResponse) (*DeleteJobCreatedNotificationRegistrationSpectraS3Response, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 204 }"));
        assertTrue(responseCode.contains("return &DeleteJobCreatedNotificationRegistrationSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse networking.WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) DeleteJobCreatedNotificationRegistrationSpectraS3(request *models.DeleteJobCreatedNotificationRegistrationSpectraS3Request) (*models.DeleteJobCreatedNotificationRegistrationSpectraS3Response, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
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
        assertTrue(requestCode.contains("\"strconv\""));
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));

        // test request struct
        assertTrue(requestCode.contains("bucketName string"));
        assertTrue(requestCode.contains("objectName string"));
        assertTrue(requestCode.contains("partNumber int"));
        assertTrue(requestCode.contains("uploadId string"));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator"));

        // test constructor
        assertTrue(requestCode.contains("func NewPutMultiPartUploadPartRequest(bucketName string, objectName string, content networking.ReaderWithSizeDecorator, partNumber int, uploadId string) *PutMultiPartUploadPartRequest {"));
        assertTrue(requestCode.contains("bucketName: bucketName,"));
        assertTrue(requestCode.contains("objectName: objectName,"));
        assertTrue(requestCode.contains("partNumber: partNumber,"));
        assertTrue(requestCode.contains("uploadId: uploadId,"));
        assertTrue(requestCode.contains("content: content,"));

        // test content stream
        assertTrue(requestCode.contains("func (putMultiPartUploadPartRequest *PutMultiPartUploadPartRequest) GetContentStream() networking.ReaderWithSizeDecorator {"));
        assertTrue(requestCode.contains("return putMultiPartUploadPartRequest.content"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewPutMultiPartUploadPartResponse(webResponse networking.WebResponse) (*PutMultiPartUploadPartResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &PutMultiPartUploadPartResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse networking.WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) PutMultiPartUploadPart(request *models.PutMultiPartUploadPartRequest) (*models.PutMultiPartUploadPartResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
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
        assertTrue(requestCode.contains("\"strconv\""));
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));
        assertTrue(requestCode.contains("strings"));

        // test request struct
        assertTrue(requestCode.contains("bucketName string"));
        assertTrue(requestCode.contains("objectName string"));
        assertTrue(requestCode.contains("job string"));
        assertTrue(requestCode.contains("offset int64"));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator"));
        assertTrue(requestCode.contains("checksum networking.Checksum"));
        assertTrue(requestCode.contains("headers *http.Header"));

        // test constructor
        assertTrue(requestCode.contains("func NewPutObjectRequest(bucketName string, objectName string, content networking.ReaderWithSizeDecorator) *PutObjectRequest {"));
        assertTrue(requestCode.contains("bucketName: bucketName,"));
        assertTrue(requestCode.contains("objectName: objectName,"));
        assertTrue(requestCode.contains("content: content,"));
        assertTrue(requestCode.contains("checksum: networking.NewNoneChecksum()"));
        assertTrue(requestCode.contains("headers: &http.Header{},"));

        // test content stream
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) GetContentStream() networking.ReaderWithSizeDecorator {"));
        assertTrue(requestCode.contains("return putObjectRequest.content"));

        // test checksum
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) WithChecksum(contentHash string, checksumType networking.ChecksumType) *PutObjectRequest {"));
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) GetChecksum() networking.Checksum {"));
        assertTrue(requestCode.contains("return putObjectRequest.checksum"));

        // test metadata
        assertTrue(requestCode.contains("const ( AMZ_META_HEADER = \"x-amz-meta-\" )"));
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) WithMetaData(key string, value string) *PutObjectRequest {"));
        assertTrue(requestCode.contains("func (putObjectRequest *PutObjectRequest) Header() *http.Header {"));
        assertTrue(requestCode.contains("return putObjectRequest.headers"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("Headers *http.Header"));

        assertTrue(responseCode.contains("func NewPutObjectResponse(webResponse networking.WebResponse) (*PutObjectResponse, error) {"));
        assertTrue(responseCode.contains("expectedStatusCodes := []int { 200 }"));
        assertTrue(responseCode.contains("return &PutObjectResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(responseCode.contains("parse(webResponse networking.WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(client.contains("func (client *Client) PutObject(request *models.PutObjectRequest) (*models.PutObjectResponse, error) {"));
        assertTrue(client.contains("networkRetryDecorator := networking.NewNetworkRetryDecorator(&(client.sendNetwork), client.clientPolicy.maxRetries)"));
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

        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));

        assertTrue(requestCode.contains("type ClearSuspectBlobAzureTargetsSpectraS3Request struct {"));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator"));

        assertTrue(requestCode.contains("func NewClearSuspectBlobAzureTargetsSpectraS3Request(ids []string) *ClearSuspectBlobAzureTargetsSpectraS3Request {"));
        assertTrue(requestCode.contains("content: buildIdListPayload(ids),"));

        assertTrue(requestCode.contains("func (clearSuspectBlobAzureTargetsSpectraS3Request *ClearSuspectBlobAzureTargetsSpectraS3Request) WithForce() *ClearSuspectBlobAzureTargetsSpectraS3Request {"));
        assertTrue(requestCode.contains("clearSuspectBlobAzureTargetsSpectraS3Request.queryParams.Set(\"force\", \"\")"));

        assertTrue(requestCode.contains("func (clearSuspectBlobAzureTargetsSpectraS3Request *ClearSuspectBlobAzureTargetsSpectraS3Request) GetContentStream() networking.ReaderWithSizeDecorator {"));
        assertTrue(requestCode.contains("return clearSuspectBlobAzureTargetsSpectraS3Request.content"));

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
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));
        assertTrue(requestCode.contains("\"strconv\""));

        assertTrue(requestCode.contains("bucketName string"));
        assertTrue(requestCode.contains("aggregating bool"));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator"));
        assertTrue(requestCode.contains("implicitJobIdResolution bool"));
        assertTrue(requestCode.contains("maxUploadSize int64"));
        assertTrue(requestCode.contains("minimizeSpanningAcrossMedia bool"));
        assertTrue(requestCode.contains("name *string"));
        assertTrue(requestCode.contains("priority Priority"));
        assertTrue(requestCode.contains("verifyAfterWrite bool"));
        assertTrue(requestCode.contains("queryParams *url.Values"));

        assertTrue(requestCode.contains("func NewPutBulkJobSpectraS3Request(bucketName string, objects []Ds3PutObject) *PutBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("queryParams.Set(\"operation\", \"start_bulk_put\")"));
        assertTrue(requestCode.contains("bucketName: bucketName,"));
        assertTrue(requestCode.contains("content: buildDs3PutObjectListStream(objects),"));

        assertTrue(requestCode.contains("func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) GetContentStream() networking.ReaderWithSizeDecorator {"));
        assertTrue(requestCode.contains("return putBulkJobSpectraS3Request.content"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList MasterObjectList"));

        assertTrue(responseCode.contains("func (putBulkJobSpectraS3Response *PutBulkJobSpectraS3Response) parse(webResponse networking.WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &putBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(responseCode.contains("func NewPutBulkJobSpectraS3Response(webResponse networking.WebResponse) (*PutBulkJobSpectraS3Response, error) {"));
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
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));
        assertTrue(requestCode.contains("\"strconv\""));

        assertTrue(requestCode.contains("bucketName string"));
        assertTrue(requestCode.contains("aggregating bool"));
        assertTrue(requestCode.contains("chunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee"));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator"));
        assertTrue(requestCode.contains("implicitJobIdResolution bool"));
        assertTrue(requestCode.contains("name *string"));
        assertTrue(requestCode.contains("priority Priority"));
        assertTrue(requestCode.contains("queryParams *url.Values"));

        assertTrue(requestCode.contains("func NewGetBulkJobSpectraS3Request(bucketName string, objectNames []string) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("queryParams.Set(\"operation\", \"start_bulk_get\")"));
        assertTrue(requestCode.contains("bucketName: bucketName,"));
        assertTrue(requestCode.contains("content: buildDs3ObjectStreamFromNames(objectNames),"));

        assertTrue(requestCode.contains("func NewGetBulkJobSpectraS3RequestWithPartialObjects(bucketName string, objects []Ds3GetObject) *GetBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("content: buildDs3GetObjectListStream(objects),"));

        assertTrue(requestCode.contains("func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) GetContentStream() networking.ReaderWithSizeDecorator {"));
        assertTrue(requestCode.contains("return getBulkJobSpectraS3Request.content"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList MasterObjectList"));

        assertTrue(responseCode.contains("func (getBulkJobSpectraS3Response *GetBulkJobSpectraS3Response) parse(webResponse networking.WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &getBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(responseCode.contains("func NewGetBulkJobSpectraS3Response(webResponse networking.WebResponse) (*GetBulkJobSpectraS3Response, error) {"));
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
        assertTrue(requestCode.contains("\"net/url\""));
        assertTrue(requestCode.contains("\"net/http\""));
        assertTrue(requestCode.contains("\"ds3/networking\""));
        assertTrue(requestCode.contains("\"strconv\""));

        assertTrue(requestCode.contains("bucketName string"));
        assertTrue(requestCode.contains("aggregating bool"));
        assertTrue(requestCode.contains("content networking.ReaderWithSizeDecorator"));
        assertTrue(requestCode.contains("name *string"));
        assertTrue(requestCode.contains("priority Priority"));

        assertTrue(requestCode.contains("func NewVerifyBulkJobSpectraS3Request(bucketName string, objectNames []string) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("queryParams.Set(\"operation\", \"start_bulk_verify\")"));
        assertTrue(requestCode.contains("bucketName: bucketName,"));
        assertTrue(requestCode.contains("content: buildDs3ObjectStreamFromNames(objectNames),"));

        assertTrue(requestCode.contains("func NewVerifyBulkJobSpectraS3RequestWithPartialObjects(bucketName string, objects []Ds3GetObject) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(requestCode.contains("content: buildDs3GetObjectListStream(objects),"));

        assertTrue(requestCode.contains("func (verifyBulkJobSpectraS3Request *VerifyBulkJobSpectraS3Request) GetContentStream() networking.ReaderWithSizeDecorator {"));
        assertTrue(requestCode.contains("return verifyBulkJobSpectraS3Request.content"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(responseCode.contains("\"ds3/networking\""));
        assertTrue(responseCode.contains("\"net/http\""));

        assertTrue(responseCode.contains("MasterObjectList MasterObjectList"));

        assertTrue(responseCode.contains("func (verifyBulkJobSpectraS3Response *VerifyBulkJobSpectraS3Response) parse(webResponse networking.WebResponse) error {"));
        assertTrue(responseCode.contains("return parseResponsePayload(webResponse, &verifyBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(responseCode.contains("func NewVerifyBulkJobSpectraS3Response(webResponse networking.WebResponse) (*VerifyBulkJobSpectraS3Response, error) {"));
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
    }
}
