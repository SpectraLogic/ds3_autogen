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
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.MODEL, LOG);

    /**
     * Collapses runs of whitespace in {@code haystack} and {@code needle} to a single
     * space before checking containment. This lets assertions written as
     * {@code "Foo Bar"} continue to match against gofmt-aligned output where Foo and
     * Bar may be separated by multiple spaces.
     */
    private static boolean containsNormalized(final String haystack, final String needle) {
        return normalize(haystack).contains(normalize(needle));
    }

    private static String normalize(final String s) {
        return s.replaceAll("\\s+", " ");
    }

    @Test
    public void simpleRequestNoPayload() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/simpleRequestNoPayload.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewSimpleNoPayloadResponse(webResponse WebResponse) (*SimpleNoPayloadResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "return &SimpleNoPayloadResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(containsNormalized(responseCode, "parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) SimpleNoPayload(ctx context.Context, request *models.SimpleNoPayloadRequest) (*models.SimpleNoPayloadResponse, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(containsNormalized(client, "response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(containsNormalized(client, "WithPath(\"/\"+request.BucketName)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"id\", request.Id)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"upload_id\", request.UploadId)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"offset\", networking.Int64PtrToStrPtr(request.Offset))."));

        // Client must not import strconv when no command uses strconv-based conversions.
        assertFalse(containsNormalized(client, "\"strconv\""));
    }

    @Test
    public void simpleRequestWithPayload() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/simpleRequestWithPayload.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Bucket Bucket"));
        assertFalse(containsNormalized(responseCode, "`xml:\"Bucket\"`"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewSimpleWithPayloadResponse(webResponse WebResponse) (*SimpleWithPayloadResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{204}"));
        assertTrue(containsNormalized(responseCode, "var body SimpleWithPayloadResponse"));
        assertTrue(containsNormalized(responseCode, "if err := body.parse(webResponse); err != nil {"));
        assertTrue(containsNormalized(responseCode, "body.Headers = webResponse.Header()"));
        assertTrue(containsNormalized(responseCode, "return &body, nil"));

        assertTrue(containsNormalized(responseCode, "func (simpleWithPayloadResponse *SimpleWithPayloadResponse) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &simpleWithPayloadResponse.Bucket)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) SimpleWithPayload(ctx context.Context, request *models.SimpleWithPayloadRequest) (*models.SimpleWithPayloadResponse, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_DELETE)."));
        assertTrue(containsNormalized(client, "WithPath(\"/\"+request.BucketName+\"/\"+request.ObjectName)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"void_param\", \"\")."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"place_holder\", request.PlaceHolder.String())."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"optional_place_holder\", networking.IntPtrToStrPtr(request.OptionalPlaceHolder))."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"operation\", \"start_bulk_get\")."));
    }

    @Test
    public void requestWithResourceInPath() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/requestWithResourceInPath.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertFalse(containsNormalized(requestCode, "\"strconv\""));
        assertTrue(containsNormalized(requestCode, "func NewDeleteBucketAclSpectraS3Request(bucketAcl string) *DeleteBucketAclSpectraS3Request {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewDeleteBucketAclSpectraS3Response(webResponse WebResponse) (*DeleteBucketAclSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{204}"));
        assertTrue(containsNormalized(responseCode, "return &DeleteBucketAclSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertFalse(containsNormalized(responseCode, "parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) DeleteBucketAclSpectraS3(ctx context.Context, request *models.DeleteBucketAclSpectraS3Request) (*models.DeleteBucketAclSpectraS3Response, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_DELETE)"));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/bucket_acl/\"+request.BucketAcl)."));
    }

    @Test
    public void verifyPhysicalPlacement() throws IOException, TemplateModelException {
        // Test for request with payload of Ds3Object list
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/verifyPhysicalPlacement.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "Objects []Ds3GetObject"));
        assertTrue(containsNormalized(requestCode, "StorageDomain *string"));

        assertTrue(containsNormalized(requestCode, "func NewVerifyPhysicalPlacementForObjectsSpectraS3Request(bucketName string, objectNames []string) *VerifyPhysicalPlacementForObjectsSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "Objects: buildDs3GetObjectSliceFromNames(objectNames),"));

        assertTrue(containsNormalized(requestCode, "func NewVerifyPhysicalPlacementForObjectsSpectraS3RequestWithPartialObjects(bucketName string, objects []Ds3GetObject) *VerifyPhysicalPlacementForObjectsSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "Objects: objects,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "PhysicalPlacement PhysicalPlacement"));
        assertFalse(containsNormalized(responseCode, "`xml:\"PhysicalPlacement\"`"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewVerifyPhysicalPlacementForObjectsSpectraS3Response(webResponse WebResponse) (*VerifyPhysicalPlacementForObjectsSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "var body VerifyPhysicalPlacementForObjectsSpectraS3Response"));
        assertTrue(containsNormalized(responseCode, "if err := body.parse(webResponse); err != nil {"));
        assertTrue(containsNormalized(responseCode, "body.Headers = webResponse.Header()"));
        assertTrue(containsNormalized(responseCode, "return &body, nil"));

        assertTrue(containsNormalized(responseCode, "func (verifyPhysicalPlacementForObjectsSpectraS3Response *VerifyPhysicalPlacementForObjectsSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &verifyPhysicalPlacementForObjectsSpectraS3Response.PhysicalPlacement)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(containsNormalized(client, "func (client *Client) VerifyPhysicalPlacementForObjectsSpectraS3(ctx context.Context, request *models.VerifyPhysicalPlacementForObjectsSpectraS3Request) (*models.VerifyPhysicalPlacementForObjectsSpectraS3Response, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(containsNormalized(client, "response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/bucket/\"+request.BucketName)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"storage_domain\", request.StorageDomain)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"operation\", \"verify_physical_placement\")."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildDs3GetObjectListStream(request.Objects))."));
    }

    @Test
    public void replicatePutJob() throws IOException, TemplateModelException {
        // Test for request with string payload
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/replicatePutJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(containsNormalized(requestCode, "\"strconv\""));
        assertTrue(containsNormalized(requestCode, "RequestPayload string"));
        assertTrue(containsNormalized(requestCode, "RequestPayload: requestPayload,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "MasterObjectList *MasterObjectList"));
        assertFalse(containsNormalized(responseCode, "`xml:\"MasterObjectList\"`"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewReplicatePutJobSpectraS3Response(webResponse WebResponse) (*ReplicatePutJobSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200, 204}"));
        assertTrue(containsNormalized(responseCode, "var body ReplicatePutJobSpectraS3Response"));
        assertTrue(containsNormalized(responseCode, "if err := body.parse(webResponse); err != nil {"));
        assertTrue(containsNormalized(responseCode, "body.Headers = webResponse.Header()"));
        assertTrue(containsNormalized(responseCode, "return &body, nil"));

        assertTrue(containsNormalized(responseCode, "return &ReplicatePutJobSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertTrue(containsNormalized(responseCode, "func (replicatePutJobSpectraS3Response *ReplicatePutJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, replicatePutJobSpectraS3Response.MasterObjectList)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(containsNormalized(client, "func (client *Client) ReplicatePutJobSpectraS3(ctx context.Context, request *models.ReplicatePutJobSpectraS3Request) (*models.ReplicatePutJobSpectraS3Response, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/bucket/\"+request.BucketName)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"replicate\", \"\")."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"operation\", \"start_bulk_put\")."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildStreamFromString(request.RequestPayload))."));
    }

    @Test
    public void completeMultipartUpload() throws IOException, TemplateModelException {
        // Test for request with CompleteMultipartUpload payload
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/completeMultipartUpload.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(containsNormalized(requestCode, "\"strconv\""));

        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "ObjectName string"));
        assertTrue(containsNormalized(requestCode, "Parts []Part"));
        assertTrue(containsNormalized(requestCode, "UploadId string"));

        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "ObjectName: objectName,"));
        assertTrue(containsNormalized(requestCode, "UploadId: uploadId,"));
        assertTrue(containsNormalized(requestCode, "Parts: parts,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));
        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "CompleteMultipartUploadResult CompleteMultipartUploadResult"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertFalse(containsNormalized(responseCode, "`xml:\"CompleteMultipartUploadResult\"`"));
        assertTrue(containsNormalized(responseCode, "func NewCompleteMultiPartUploadResponse(webResponse WebResponse) (*CompleteMultiPartUploadResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "var body CompleteMultiPartUploadResponse"));
        assertTrue(containsNormalized(responseCode, "if err := body.parse(webResponse); err != nil {"));
        assertTrue(containsNormalized(responseCode, "body.Headers = webResponse.Header()"));
        assertTrue(containsNormalized(responseCode, "return &body, nil"));

        assertTrue(containsNormalized(responseCode, "func (completeMultiPartUploadResponse *CompleteMultiPartUploadResponse) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &completeMultiPartUploadResponse.CompleteMultipartUploadResult)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.POST);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(containsNormalized(client, "func (client *Client) CompleteMultiPartUpload(ctx context.Context, request *models.CompleteMultiPartUploadRequest) (*models.CompleteMultiPartUploadResponse, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(containsNormalized(client, "WithPath(\"/\"+request.BucketName+\"/\"+request.ObjectName)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"upload_id\", request.UploadId)."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildPartsListStream(request.Parts))."));
    }

    @Test
    public void deleteObjects() throws IOException, TemplateModelException {
        // Test for request with object name list payload and optional void parameter
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/deleteObjects.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(containsNormalized(requestCode, "\"strconv\""));

        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "ObjectNames []string"));
        assertTrue(containsNormalized(requestCode, "RollBack bool"));

        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "ObjectNames: objectNames,"));

        assertTrue(containsNormalized(requestCode, "func (deleteObjectsRequest *DeleteObjectsRequest) WithRollBack() *DeleteObjectsRequest {"));
        assertTrue(containsNormalized(requestCode, "deleteObjectsRequest.RollBack = true"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "DeleteResult DeleteResult"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));
        assertFalse(containsNormalized(responseCode, "`xml:\"DeleteResult\"`"));

        assertTrue(containsNormalized(responseCode, "func NewDeleteObjectsResponse(webResponse WebResponse) (*DeleteObjectsResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "var body DeleteObjectsResponse"));
        assertTrue(containsNormalized(responseCode, "if err := body.parse(webResponse); err != nil {"));
        assertTrue(containsNormalized(responseCode, "body.Headers = webResponse.Header()"));
        assertTrue(containsNormalized(responseCode, "return &body, nil"));

        assertTrue(containsNormalized(responseCode, "func (deleteObjectsResponse *DeleteObjectsResponse) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &deleteObjectsResponse.DeleteResult)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.POST);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(containsNormalized(client, "func (client *Client) DeleteObjects(ctx context.Context, request *models.DeleteObjectsRequest) (*models.DeleteObjectsResponse, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(containsNormalized(client, "WithPath(\"/\"+request.BucketName)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"delete\", \"\")."));
        assertTrue(containsNormalized(client, "WithOptionalVoidQueryParam(\"roll_back\", request.RollBack)."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildDeleteObjectsPayload(request.ObjectNames))."));
    }

    @Test
    public void getJobToReplicate() throws IOException, TemplateModelException {
        // Test for request with string response payload
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/getJobToReplicate.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));
        assertFalse(containsNormalized(requestCode, "\"strconv\""));

        assertTrue(containsNormalized(requestCode, "JobId string"));
        assertTrue(containsNormalized(requestCode, "JobId: jobId,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Content string"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewGetJobToReplicateSpectraS3Response(webResponse WebResponse) (*GetJobToReplicateSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "return &GetJobToReplicateSpectraS3Response{Content: content, Headers: webResponse.Header()}, nil"));

        assertFalse(containsNormalized(responseCode, "parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(containsNormalized(client, "func (client *Client) GetJobToReplicateSpectraS3(ctx context.Context, request *models.GetJobToReplicateSpectraS3Request) (*models.GetJobToReplicateSpectraS3Response, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(containsNormalized(client, "response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/job/\"+request.JobId)"));
        assertTrue(containsNormalized(client, "WithQueryParam(\"replicate\", \"\")."));
    }

    @Test
    public void getObject() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/getObject.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request imports
        assertTrue(containsNormalized(requestCode, "\"fmt\""));
        assertTrue(containsNormalized(requestCode, "\"strings\""));
        assertFalse(containsNormalized(requestCode, "\"ds3/networking\""));

        // test request struct
        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "ObjectName string"));
        assertTrue(containsNormalized(requestCode, "Checksum Checksum"));
        assertTrue(containsNormalized(requestCode, "Job *string"));
        assertTrue(containsNormalized(requestCode, "Offset *int64"));
        assertTrue(containsNormalized(requestCode, "Metadata map[string]string"));

        assertTrue(containsNormalized(requestCode, "type Range struct {"));

        // test constructor and with-constructors
        assertTrue(containsNormalized(requestCode, "func NewGetObjectRequest(bucketName string, objectName string) *GetObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "ObjectName: objectName,"));
        assertTrue(containsNormalized(requestCode, "Checksum: NewNoneChecksum(),"));
        assertTrue(containsNormalized(requestCode, "Metadata: make(map[string]string)"));

        assertTrue(containsNormalized(requestCode, "func (getObjectRequest *GetObjectRequest) WithRanges(ranges ...Range) *GetObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "getObjectRequest.Metadata[\"Range\"] = fmt.Sprintf(\"bytes=%s\", strings.Join(rangeElements[:], \",\"))"));
        assertFalse(containsNormalized(requestCode, "func (getObjectRequest *GetObjectRequest) WithRange(start, end int) *GetObjectRequest {"));
        assertFalse(containsNormalized(requestCode, "getObjectRequest.Metadata[\"Range\"] = fmt.Sprintf(\"bytes=%d-%d\", start, end)"));

        assertTrue(containsNormalized(requestCode, "func (getObjectRequest *GetObjectRequest) WithChecksum(contentHash string, checksumType ChecksumType) *GetObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "getObjectRequest.Checksum.ContentHash = contentHash"));
        assertTrue(containsNormalized(requestCode, "getObjectRequest.Checksum.Type = checksumType"));

        assertTrue(containsNormalized(requestCode, "func (getObjectRequest *GetObjectRequest) WithJob(job string) *GetObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "getObjectRequest.Job = &job"));

        assertTrue(containsNormalized(requestCode, "func (getObjectRequest *GetObjectRequest) WithOffset(offset int64) *GetObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "getObjectRequest.Offset = &offset"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(containsNormalized(responseCode, "\"io\""));
        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Content io.ReadCloser"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewGetObjectResponse(webResponse WebResponse) (*GetObjectResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200, 206}"));
        assertTrue(containsNormalized(responseCode, "return &GetObjectResponse{Content: webResponse.Body(), Headers: webResponse.Header()}, nil"));
        assertFalse(responseCode.contains("return &GetObjectResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(containsNormalized(responseCode, "parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));
        assertTrue(containsNormalized(client, "func (client *Client) GetObject(ctx context.Context, request *models.GetObjectRequest) (*models.GetObjectResponse, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(containsNormalized(client, "WithPath(\"/\"+request.BucketName+\"/\"+request.ObjectName)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"job\", request.Job)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"offset\", networking.Int64PtrToStrPtr(request.Offset))."));
        assertTrue(containsNormalized(client, "WithChecksum(request.Checksum)."));
        assertTrue(containsNormalized(client, "WithHeaders(request.Metadata)."));
    }

    @Test
    public void getAzureDataReplicationRules() throws IOException, TemplateModelException {
        // This tests generation of request with "type" optional parameter keyword conflict
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/getAzureDataReplicationRules.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request struct
        assertTrue(containsNormalized(requestCode, "DataPolicyId *string"));
        assertTrue(containsNormalized(requestCode, "DataReplicationRuleType DataReplicationRuleType"));
        assertTrue(containsNormalized(requestCode, "PageLength *int"));
        assertTrue(containsNormalized(requestCode, "PageOffset *int"));
        assertTrue(containsNormalized(requestCode, "PageStartMarker *string"));
        assertTrue(containsNormalized(requestCode, "ReplicateDeletes *bool"));
        assertTrue(containsNormalized(requestCode, "State DataPlacementRuleState"));
        assertTrue(containsNormalized(requestCode, "TargetId *string"));
        assertTrue(containsNormalized(requestCode, "LastPage bool"));

        // test constructor and with-constructors
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithDataPolicyId(dataPolicyId string) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithPageLength(pageLength int) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithPageOffset(pageOffset int) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithPageStartMarker(pageStartMarker string) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithReplicateDeletes(replicateDeletes bool) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithState(state DataPlacementRuleState) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithTargetId(targetId string) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithDataReplicationRuleType(dataReplicationRuleType DataReplicationRuleType) *GetAzureDataReplicationRulesSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (getAzureDataReplicationRulesSpectraS3Request *GetAzureDataReplicationRulesSpectraS3Request) WithLastPage() *GetAzureDataReplicationRulesSpectraS3Request {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "AzureDataReplicationRuleList AzureDataReplicationRuleList"));
        assertFalse(containsNormalized(responseCode, "`xml:\"AzureDataReplicationRuleList\"`"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewGetAzureDataReplicationRulesSpectraS3Response(webResponse WebResponse) (*GetAzureDataReplicationRulesSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "if err := body.parse(webResponse); err != nil {"));
        assertTrue(containsNormalized(responseCode, "body.Headers = webResponse.Header()"));
        assertTrue(containsNormalized(responseCode, "return &body, nil"));

        assertTrue(containsNormalized(responseCode, "func (getAzureDataReplicationRulesSpectraS3Response *GetAzureDataReplicationRulesSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &getAzureDataReplicationRulesSpectraS3Response.AzureDataReplicationRuleList)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.GET);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) GetAzureDataReplicationRulesSpectraS3(ctx context.Context, request *models.GetAzureDataReplicationRulesSpectraS3Request) (*models.GetAzureDataReplicationRulesSpectraS3Response, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "httpRedirectDecorator := networking.NewHttpTempRedirectDecorator(networkRetryDecorator, client.clientPolicy.maxRedirect)"));
        assertTrue(containsNormalized(client, "response, requestErr := httpRedirectDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_GET)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/azure_data_replication_rule\")."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"data_policy_id\", request.DataPolicyId)."));
        assertTrue(containsNormalized(client, "WithOptionalVoidQueryParam(\"last_page\", request.LastPage)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"page_length\", networking.IntPtrToStrPtr(request.PageLength))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"page_offset\", networking.IntPtrToStrPtr(request.PageOffset))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"page_start_marker\", request.PageStartMarker)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"replicate_deletes\", networking.BoolPtrToStrPtr(request.ReplicateDeletes))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"state\", networking.InterfaceToStrPtr(request.State))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"target_id\", request.TargetId)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"type\", networking.InterfaceToStrPtr(request.DataReplicationRuleType))."));
    }

    @Test
    public void putAzureDataReplicationRule() throws IOException, TemplateModelException {
        // This tests generation of request with "type" required parameter keyword conflict
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/putAzureDataReplicationRule.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request struct
        assertTrue(containsNormalized(requestCode, "DataPolicyId string"));
        assertTrue(containsNormalized(requestCode, "DataReplicationRuleType DataReplicationRuleType"));
        assertTrue(containsNormalized(requestCode, "MaxBlobPartSizeInBytes *int64"));
        assertTrue(containsNormalized(requestCode, "ReplicateDeletes *bool"));
        assertTrue(containsNormalized(requestCode, "TargetId string"));

        // test constructor and
        assertTrue(containsNormalized(requestCode, "func NewPutAzureDataReplicationRuleSpectraS3Request(dataPolicyId string, dataReplicationRuleType DataReplicationRuleType, targetId string) *PutAzureDataReplicationRuleSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "DataPolicyId: dataPolicyId,"));
        assertTrue(containsNormalized(requestCode, "TargetId: targetId,"));
        assertTrue(containsNormalized(requestCode, "DataReplicationRuleType: dataReplicationRuleType,"));

        // test with-constructors
        assertTrue(containsNormalized(requestCode, "func (putAzureDataReplicationRuleSpectraS3Request *PutAzureDataReplicationRuleSpectraS3Request) WithMaxBlobPartSizeInBytes(maxBlobPartSizeInBytes int64) *PutAzureDataReplicationRuleSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "func (putAzureDataReplicationRuleSpectraS3Request *PutAzureDataReplicationRuleSpectraS3Request) WithReplicateDeletes(replicateDeletes bool) *PutAzureDataReplicationRuleSpectraS3Request {"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "AzureDataReplicationRule AzureDataReplicationRule"));
        assertFalse(containsNormalized(responseCode, "`xml:\"AzureDataReplicationRule\"`"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewPutAzureDataReplicationRuleSpectraS3Response(webResponse WebResponse) (*PutAzureDataReplicationRuleSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{201}"));
        assertTrue(containsNormalized(responseCode, "if err := body.parse(webResponse); err != nil {"));
        assertTrue(containsNormalized(responseCode, "body.Headers = webResponse.Header()"));
        assertTrue(containsNormalized(responseCode, "return &body, nil"));

        assertTrue(containsNormalized(responseCode, "func (putAzureDataReplicationRuleSpectraS3Response *PutAzureDataReplicationRuleSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &putAzureDataReplicationRuleSpectraS3Response.AzureDataReplicationRule)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.POST);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) PutAzureDataReplicationRuleSpectraS3(ctx context.Context, request *models.PutAzureDataReplicationRuleSpectraS3Request) (*models.PutAzureDataReplicationRuleSpectraS3Response, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_POST)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/azure_data_replication_rule\")."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"data_policy_id\", request.DataPolicyId)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"target_id\", request.TargetId)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"type\", request.DataReplicationRuleType.String())."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"max_blob_part_size_in_bytes\", networking.Int64PtrToStrPtr(request.MaxBlobPartSizeInBytes))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"replicate_deletes\", networking.BoolPtrToStrPtr(request.ReplicateDeletes))."));
    }

    @Test
    public void deleteJobCreatedNotificationRegistration() throws IOException, TemplateModelException {
        // This tests correct generation of notificationId required parameter from resource
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/deleteJobCreatedNotificationRegistration.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request struct
        assertTrue(containsNormalized(requestCode, "NotificationId string"));

        // test constructor
        assertTrue(containsNormalized(requestCode, "func NewDeleteJobCreatedNotificationRegistrationSpectraS3Request(notificationId string) *DeleteJobCreatedNotificationRegistrationSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "NotificationId: notificationId,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewDeleteJobCreatedNotificationRegistrationSpectraS3Response(webResponse WebResponse) (*DeleteJobCreatedNotificationRegistrationSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{204}"));
        assertTrue(containsNormalized(responseCode, "return &DeleteJobCreatedNotificationRegistrationSpectraS3Response{Headers: webResponse.Header()}, nil"));

        assertFalse(containsNormalized(responseCode, "parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.DELETE);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) DeleteJobCreatedNotificationRegistrationSpectraS3(ctx context.Context, request *models.DeleteJobCreatedNotificationRegistrationSpectraS3Request) (*models.DeleteJobCreatedNotificationRegistrationSpectraS3Response, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_DELETE)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/job_created_notification_registration/\"+request.NotificationId)."));
    }

    @Test
    public void putMultiPartUploadPart() throws IOException, TemplateModelException {
        // This tests correct generation of request with ReaderWithSizeDecorator payload
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/putMultiPartUploadPart.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request imports
        assertFalse(containsNormalized(requestCode, "\"ds3/networking\""));

        // test request struct
        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "ObjectName string"));
        assertTrue(containsNormalized(requestCode, "Content ReaderWithSizeDecorator"));
        assertTrue(containsNormalized(requestCode, "PartNumber int"));
        assertTrue(containsNormalized(requestCode, "UploadId string"));

        // test constructor
        assertTrue(containsNormalized(requestCode, "func NewPutMultiPartUploadPartRequest(bucketName string, objectName string, content ReaderWithSizeDecorator, partNumber int, uploadId string) *PutMultiPartUploadPartRequest {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "ObjectName: objectName,"));
        assertTrue(containsNormalized(requestCode, "PartNumber: partNumber,"));
        assertTrue(containsNormalized(requestCode, "UploadId: uploadId,"));
        assertTrue(containsNormalized(requestCode, "Content: content,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewPutMultiPartUploadPartResponse(webResponse WebResponse) (*PutMultiPartUploadPartResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "return &PutMultiPartUploadPartResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(containsNormalized(responseCode, "parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) PutMultiPartUploadPart(ctx context.Context, request *models.PutMultiPartUploadPartRequest) (*models.PutMultiPartUploadPartResponse, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(containsNormalized(client, "WithPath(\"/\"+request.BucketName+\"/\"+request.ObjectName)."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"part_number\", strconv.Itoa(request.PartNumber))."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"upload_id\", request.UploadId)."));
        assertTrue(containsNormalized(client, "WithReader(request.Content)."));

        // Client must import strconv when any command uses strconv-based conversions.
        assertTrue(containsNormalized(client, "\"strconv\""));
    }

    @Test
    public void putObject() throws IOException, TemplateModelException {
        // This tests correct generation of request with ReaderWithSizeDecorator payload
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/putObject.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        // test request imports
        assertFalse(containsNormalized(requestCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(requestCode, "strings"));

        // test request struct
        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "ObjectName string"));
        assertTrue(containsNormalized(requestCode, "Job *string"));
        assertTrue(containsNormalized(requestCode, "Offset *int64"));
        assertTrue(containsNormalized(requestCode, "Content ReaderWithSizeDecorator"));
        assertTrue(containsNormalized(requestCode, "Checksum Checksum"));
        assertTrue(containsNormalized(requestCode, "Metadata map[string]string"));

        // test constructor
        assertTrue(containsNormalized(requestCode, "func NewPutObjectRequest(bucketName string, objectName string, content ReaderWithSizeDecorator) *PutObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "ObjectName: objectName,"));
        assertTrue(containsNormalized(requestCode, "Content: content,"));
        assertTrue(containsNormalized(requestCode, "Checksum: NewNoneChecksum()"));
        assertTrue(containsNormalized(requestCode, "Metadata: make(map[string]string),"));

        assertTrue(containsNormalized(requestCode, "func (putObjectRequest *PutObjectRequest) WithJob(job string) *PutObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "putObjectRequest.Job = &job"));
        assertTrue(containsNormalized(requestCode, "func (putObjectRequest *PutObjectRequest) WithOffset(offset int64) *PutObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "putObjectRequest.Offset = &offset"));

        // test checksum
        assertTrue(containsNormalized(requestCode, "func (putObjectRequest *PutObjectRequest) WithChecksum(contentHash string, checksumType ChecksumType) *PutObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "putObjectRequest.Checksum.ContentHash = contentHash"));
        assertTrue(containsNormalized(requestCode, "putObjectRequest.Checksum.Type = checksumType"));

        // test metadata
        assertFalse(containsNormalized(requestCode, "const ( AMZ_META_HEADER = \"x-amz-meta-\" )"));
        assertTrue(containsNormalized(requestCode, "func (putObjectRequest *PutObjectRequest) WithMetaData(key string, values ...string) *PutObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "if strings.HasPrefix(strings.ToLower(key), AMZ_META_HEADER) {"));
        assertTrue(containsNormalized(requestCode, "putObjectRequest.Metadata[key] = strings.Join(values, \",\")"));
        assertTrue(containsNormalized(requestCode, "putObjectRequest.Metadata[strings.ToLower(AMZ_META_HEADER+key)] = strings.Join(values, \",\")"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewPutObjectResponse(webResponse WebResponse) (*PutObjectResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "return &PutObjectResponse{Headers: webResponse.Header()}, nil"));

        assertFalse(containsNormalized(responseCode, "parse(webResponse WebResponse)"));

        // Verify response payload type file was not generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(isEmpty(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "func (client *Client) PutObject(ctx context.Context, request *models.PutObjectRequest) (*models.PutObjectResponse, error) {"));
        assertTrue(containsNormalized(client, "networkRetryDecorator := networking.NewNetworkRetryDecorator(client.sendNetwork, client.clientPolicy.maxRetries)"));
        assertTrue(containsNormalized(client, "response, requestErr := networkRetryDecorator.Invoke(httpRequest)"));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(containsNormalized(client, "WithPath(\"/\"+request.BucketName+\"/\"+request.ObjectName)"));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"job\", request.Job)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"offset\", networking.Int64PtrToStrPtr(request.Offset))."));
        assertTrue(containsNormalized(client, "WithReader(request.Content)."));
        assertTrue(containsNormalized(client, "WithChecksum(request.Checksum)."));
        assertTrue(containsNormalized(client, "WithHeaders(request.Metadata)."));
    }

    @Test
    public void clearSuspectBlobAzureTargetsSpectraS3() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/clearSuspectBlobAzureTargetsSpectraS3.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(containsNormalized(requestCode, "type ClearSuspectBlobAzureTargetsSpectraS3Request struct {"));
        assertTrue(containsNormalized(requestCode, "Ids []string"));
        assertTrue(containsNormalized(requestCode, "Force bool"));

        assertTrue(containsNormalized(requestCode, "func NewClearSuspectBlobAzureTargetsSpectraS3Request(ids []string) *ClearSuspectBlobAzureTargetsSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "Ids: ids,"));

        assertTrue(containsNormalized(requestCode, "func (clearSuspectBlobAzureTargetsSpectraS3Request *ClearSuspectBlobAzureTargetsSpectraS3Request) WithForce() *ClearSuspectBlobAzureTargetsSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "clearSuspectBlobAzureTargetsSpectraS3Request.Force = true"));

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

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_DELETE)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/suspect_blob_azure_target\")"));
        assertTrue(containsNormalized(client, "WithOptionalVoidQueryParam(\"force\", request.Force)."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildIdListPayload(request.Ids))."));
    }

    @Test
    public void putBulkJobSpectraS3Request() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/putBulkJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "Aggregating *bool"));
        assertTrue(containsNormalized(requestCode, "ImplicitJobIdResolution *bool"));
        assertTrue(containsNormalized(requestCode, "MaxUploadSize *int64"));
        assertTrue(containsNormalized(requestCode, "MinimizeSpanningAcrossMedia *bool"));
        assertTrue(containsNormalized(requestCode, "Name *string"));
        assertTrue(containsNormalized(requestCode, "Priority Priority"));
        assertTrue(containsNormalized(requestCode, "VerifyAfterWrite *bool"));
        assertTrue(containsNormalized(requestCode, "Objects []Ds3PutObject"));
        assertTrue(containsNormalized(requestCode, "IgnoreNamingConflicts bool"));
        assertTrue(containsNormalized(requestCode, "Force bool"));

        assertTrue(containsNormalized(requestCode, "func NewPutBulkJobSpectraS3Request(bucketName string, objects []Ds3PutObject) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "Objects: objects,"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithAggregating(aggregating bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.Aggregating = &aggregating"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithImplicitJobIdResolution(implicitJobIdResolution bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.ImplicitJobIdResolution = &implicitJobIdResolution"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithMaxUploadSize(maxUploadSize int64) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.MaxUploadSize = &maxUploadSize"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithMinimizeSpanningAcrossMedia(minimizeSpanningAcrossMedia bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.MinimizeSpanningAcrossMedia = &minimizeSpanningAcrossMedia"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithPriority(priority Priority) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.Priority = priority"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithVerifyAfterWrite(verifyAfterWrite bool) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.VerifyAfterWrite = &verifyAfterWrite"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithName(name string) *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.Name = &name"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithForce() *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.Force = true"));

        assertTrue(containsNormalized(requestCode, "func (putBulkJobSpectraS3Request *PutBulkJobSpectraS3Request) WithIgnoreNamingConflicts() *PutBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBulkJobSpectraS3Request.IgnoreNamingConflicts = true"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "MasterObjectList MasterObjectList"));

        assertTrue(containsNormalized(responseCode, "func (putBulkJobSpectraS3Response *PutBulkJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &putBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(containsNormalized(responseCode, "func NewPutBulkJobSpectraS3Response(webResponse WebResponse) (*PutBulkJobSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "case 200:"));
        assertTrue(containsNormalized(responseCode, "var body PutBulkJobSpectraS3Response"));

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/bucket/\"+request.BucketName)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"aggregating\", networking.BoolPtrToStrPtr(request.Aggregating))."));
        assertTrue(containsNormalized(client, "WithOptionalVoidQueryParam(\"force\", request.Force)."));
        assertTrue(containsNormalized(client, "WithOptionalVoidQueryParam(\"ignore_naming_conflicts\", request.IgnoreNamingConflicts)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"implicit_job_id_resolution\", networking.BoolPtrToStrPtr(request.ImplicitJobIdResolution))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"max_upload_size\", networking.Int64PtrToStrPtr(request.MaxUploadSize))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"minimize_spanning_across_media\", networking.BoolPtrToStrPtr(request.MinimizeSpanningAcrossMedia))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"name\", request.Name)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"verify_after_write\", networking.BoolPtrToStrPtr(request.VerifyAfterWrite))."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"operation\", \"start_bulk_put\")."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildDs3PutObjectListStream(request.Objects))."));
    }

    @Test
    public void getBulkJobSpectraS3Request() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/getBulkJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertFalse(containsNormalized(requestCode, "import \"ds3\""));

        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "Aggregating *bool"));
        assertTrue(containsNormalized(requestCode, "ChunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee"));
        assertTrue(containsNormalized(requestCode, "ImplicitJobIdResolution *bool"));
        assertTrue(containsNormalized(requestCode, "Name *string"));
        assertTrue(containsNormalized(requestCode, "Priority Priority"));
        assertTrue(containsNormalized(requestCode, "Objects []Ds3GetObject"));

        assertTrue(containsNormalized(requestCode, "func NewGetBulkJobSpectraS3Request(bucketName string, objectNames []string) *GetBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "Objects: buildDs3GetObjectSliceFromNames(objectNames),"));

        assertTrue(containsNormalized(requestCode, "func NewGetBulkJobSpectraS3RequestWithPartialObjects(bucketName string, objects []Ds3GetObject) *GetBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "Objects: objects,"));

        assertTrue(containsNormalized(requestCode, "func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithAggregating(aggregating bool) *GetBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "getBulkJobSpectraS3Request.Aggregating = &aggregating"));

        assertTrue(containsNormalized(requestCode, "func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithChunkClientProcessingOrderGuarantee(chunkClientProcessingOrderGuarantee JobChunkClientProcessingOrderGuarantee) *GetBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "getBulkJobSpectraS3Request.ChunkClientProcessingOrderGuarantee = chunkClientProcessingOrderGuarantee"));

        assertTrue(containsNormalized(requestCode, "func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithImplicitJobIdResolution(implicitJobIdResolution bool) *GetBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "getBulkJobSpectraS3Request.ImplicitJobIdResolution = &implicitJobIdResolution"));

        assertTrue(containsNormalized(requestCode, "func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithPriority(priority Priority) *GetBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "getBulkJobSpectraS3Request.Priority = priority"));

        assertTrue(containsNormalized(requestCode, "func (getBulkJobSpectraS3Request *GetBulkJobSpectraS3Request) WithName(name string) *GetBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "getBulkJobSpectraS3Request.Name = &name"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "MasterObjectList MasterObjectList"));

        assertTrue(containsNormalized(responseCode, "func (getBulkJobSpectraS3Response *GetBulkJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &getBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(containsNormalized(responseCode, "func NewGetBulkJobSpectraS3Response(webResponse WebResponse) (*GetBulkJobSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "case 200:"));
        assertTrue(containsNormalized(responseCode, "var body GetBulkJobSpectraS3Response"));

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/bucket/\"+request.BucketName)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"aggregating\", networking.BoolPtrToStrPtr(request.Aggregating))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"chunk_client_processing_order_guarantee\", networking.InterfaceToStrPtr(request.ChunkClientProcessingOrderGuarantee))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"implicit_job_id_resolution\", networking.BoolPtrToStrPtr(request.ImplicitJobIdResolution))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"name\", request.Name)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"operation\", \"start_bulk_get\")."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildDs3GetObjectListStream(request.Objects))."));
    }

    @Test
    public void verifyBulkJobSpectraS3Request() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/verifyBulkJob.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertFalse(containsNormalized(requestCode, "import \"ds3\""));

        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "Aggregating *bool"));
        assertTrue(containsNormalized(requestCode, "Name *string"));
        assertTrue(containsNormalized(requestCode, "Priority Priority"));
        assertTrue(containsNormalized(requestCode, "Objects []Ds3GetObject"));

        assertTrue(containsNormalized(requestCode, "func NewVerifyBulkJobSpectraS3Request(bucketName string, objectNames []string) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "Objects: buildDs3GetObjectSliceFromNames(objectNames),"));

        assertTrue(containsNormalized(requestCode, "func NewVerifyBulkJobSpectraS3RequestWithPartialObjects(bucketName string, objects []Ds3GetObject) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "Objects: objects,"));

        assertTrue(containsNormalized(requestCode, "func (verifyBulkJobSpectraS3Request *VerifyBulkJobSpectraS3Request) WithAggregating(aggregating bool) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "verifyBulkJobSpectraS3Request.Aggregating = &aggregating"));

        assertTrue(containsNormalized(requestCode, "func (verifyBulkJobSpectraS3Request *VerifyBulkJobSpectraS3Request) WithPriority(priority Priority) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "verifyBulkJobSpectraS3Request.Priority = priority"));

        assertTrue(containsNormalized(requestCode, "func (verifyBulkJobSpectraS3Request *VerifyBulkJobSpectraS3Request) WithName(name string) *VerifyBulkJobSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "verifyBulkJobSpectraS3Request.Name = &name"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertFalse(containsNormalized(responseCode, "\"ds3/networking\""));
        assertTrue(containsNormalized(responseCode, "\"net/http\""));

        assertTrue(containsNormalized(responseCode, "MasterObjectList MasterObjectList"));

        assertTrue(containsNormalized(responseCode, "func (verifyBulkJobSpectraS3Response *VerifyBulkJobSpectraS3Response) parse(webResponse WebResponse) error {"));
        assertTrue(containsNormalized(responseCode, "return parseResponsePayload(webResponse, &verifyBulkJobSpectraS3Response.MasterObjectList)"));

        assertTrue(containsNormalized(responseCode, "func NewVerifyBulkJobSpectraS3Response(webResponse WebResponse) (*VerifyBulkJobSpectraS3Response, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "case 200:"));
        assertTrue(containsNormalized(responseCode, "var body VerifyBulkJobSpectraS3Response"));

        // Verify response payload type file was generated
        final String typeCode = codeGenerator.getTypeCode();
        CODE_LOGGER.logFile(typeCode, FileTypeToLog.MODEL);
        assertTrue(hasContent(typeCode));

        // Verify that the client code was generated
        final String client = codeGenerator.getClientCode(HttpVerb.PUT);
        CODE_LOGGER.logFile(client, FileTypeToLog.CLIENT);
        assertTrue(hasContent(client));

        assertTrue(containsNormalized(client, "WithHttpVerb(HTTP_VERB_PUT)."));
        assertTrue(containsNormalized(client, "WithPath(\"/_rest_/bucket/\"+request.BucketName)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"aggregating\", networking.BoolPtrToStrPtr(request.Aggregating))."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"name\", request.Name)."));
        assertTrue(containsNormalized(client, "WithOptionalQueryParam(\"priority\", networking.InterfaceToStrPtr(request.Priority))."));
        assertTrue(containsNormalized(client, "WithQueryParam(\"operation\", \"start_bulk_verify\")."));
        assertTrue(containsNormalized(client, "WithReadCloser(buildDs3GetObjectListStream(request.Objects))."));
    }

    @Test
    public void putBucketSpectraS3Test() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/putBucketSpectraS3.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(containsNormalized(requestCode, "type PutBucketSpectraS3Request struct {"));
        assertTrue(containsNormalized(requestCode, "DataPolicyId *string"));
        assertTrue(containsNormalized(requestCode, "Id *string"));
        assertTrue(containsNormalized(requestCode, "Name string"));
        assertTrue(containsNormalized(requestCode, "UserId *string"));

        assertTrue(containsNormalized(requestCode, "func NewPutBucketSpectraS3Request(name string) *PutBucketSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "Name: name,"));

        assertTrue(containsNormalized(requestCode, "func (putBucketSpectraS3Request *PutBucketSpectraS3Request) WithDataPolicyId(dataPolicyId string) *PutBucketSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBucketSpectraS3Request.DataPolicyId = &dataPolicyId"));

        assertTrue(containsNormalized(requestCode, "func (putBucketSpectraS3Request *PutBucketSpectraS3Request) WithId(id string) *PutBucketSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBucketSpectraS3Request.Id = &id"));

        assertTrue(containsNormalized(requestCode, "func (putBucketSpectraS3Request *PutBucketSpectraS3Request) WithUserId(userId string) *PutBucketSpectraS3Request {"));
        assertTrue(containsNormalized(requestCode, "putBucketSpectraS3Request.UserId = &userId"));
    }

    @Test
    public void headObjectTest() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final GoTestCodeUtil codeGenerator = new GoTestCodeUtil(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/headObjectRequest.xml");

        // Verify Request file was generated
        final String requestCode = codeGenerator.getRequestCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);
        assertTrue(hasContent(requestCode));

        assertTrue(containsNormalized(requestCode, "type HeadObjectRequest struct {"));
        assertTrue(containsNormalized(requestCode, "BucketName string"));
        assertTrue(containsNormalized(requestCode, "ObjectName string"));

        assertTrue(containsNormalized(requestCode, "func NewHeadObjectRequest(bucketName string, objectName string) *HeadObjectRequest {"));
        assertTrue(containsNormalized(requestCode, "BucketName: bucketName,"));
        assertTrue(containsNormalized(requestCode, "ObjectName: objectName,"));

        // Verify Response file was generated
        final String responseCode = codeGenerator.getResponseCode();
        CODE_LOGGER.logFile(responseCode, FileTypeToLog.RESPONSE);
        assertTrue(hasContent(responseCode));

        assertTrue(containsNormalized(responseCode, "type HeadObjectResponse struct {"));
        assertTrue(containsNormalized(responseCode, "BlobChecksumType ChecksumType"));
        assertTrue(containsNormalized(responseCode, "BlobChecksums map[int64]string"));
        assertTrue(containsNormalized(responseCode, "Headers *http.Header"));

        assertTrue(containsNormalized(responseCode, "func NewHeadObjectResponse(webResponse WebResponse) (*HeadObjectResponse, error) {"));
        assertTrue(containsNormalized(responseCode, "expectedStatusCodes := []int{200}"));
        assertTrue(containsNormalized(responseCode, "checksumType, err := getBlobChecksumType(webResponse.Header())"));
        assertTrue(containsNormalized(responseCode, "checksumMap, err := getBlobChecksumMap(webResponse.Header())"));
        assertTrue(containsNormalized(responseCode, "return &HeadObjectResponse{BlobChecksumType: checksumType, BlobChecksums: checksumMap, Headers: webResponse.Header()}, nil"));
    }
}
