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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Ds3RequestClassificationUtil_Test {

    @Test
    public void isAmazonCreateObjectRequest_test() {
        assertTrue(isAmazonCreateObjectRequest(getRequestCreateObject()));

        assertFalse(isAmazonCreateObjectRequest(getRequestDeleteNotification()));
        assertFalse(isAmazonCreateObjectRequest(getRequestCreateNotification()));
        assertFalse(isAmazonCreateObjectRequest(getRequestGetNotification()));
        assertFalse(isAmazonCreateObjectRequest(getReplicatePutJob()));
        assertFalse(isAmazonCreateObjectRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isAmazonCreateObjectRequest(getRequestBulkGet()));
        assertFalse(isAmazonCreateObjectRequest(getRequestMultiFileDelete()));
        assertFalse(isAmazonCreateObjectRequest(getRequestAmazonS3GetObject()));
        assertFalse(isAmazonCreateObjectRequest(getRequestSpectraS3GetObject()));
        assertFalse(isAmazonCreateObjectRequest(getGetBlobPersistence()));
        assertFalse(isAmazonCreateObjectRequest(getCreateMultiPartUploadPart()));
        assertFalse(isAmazonCreateObjectRequest(getEjectStorageDomainRequest()));
        assertFalse(isAmazonCreateObjectRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isNotificationRequest_test() {
        assertTrue(isNotificationRequest(getRequestDeleteNotification()));
        assertTrue(isNotificationRequest(getRequestCreateNotification()));
        assertTrue(isNotificationRequest(getRequestGetNotification()));

        assertFalse(isNotificationRequest(getReplicatePutJob()));
        assertFalse(isNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isNotificationRequest(getRequestBulkGet()));
        assertFalse(isNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isNotificationRequest(getRequestCreateObject()));
        assertFalse(isNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isNotificationRequest(getRequestSpectraS3GetObject()));
        assertFalse(isNotificationRequest(getGetBlobPersistence()));
        assertFalse(isNotificationRequest(getCreateMultiPartUploadPart()));
        assertFalse(isNotificationRequest(getEjectStorageDomainRequest()));
        assertFalse(isNotificationRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isDeleteNotificationRequest_test() {
        assertTrue(isDeleteNotificationRequest(getRequestDeleteNotification()));

        assertFalse(isDeleteNotificationRequest(getReplicatePutJob()));
        assertFalse(isDeleteNotificationRequest(getRequestCreateNotification()));
        assertFalse(isDeleteNotificationRequest(getRequestGetNotification()));
        assertFalse(isDeleteNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isDeleteNotificationRequest(getRequestBulkGet()));
        assertFalse(isDeleteNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isDeleteNotificationRequest(getRequestCreateObject()));
        assertFalse(isDeleteNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isDeleteNotificationRequest(getRequestSpectraS3GetObject()));
        assertFalse(isDeleteNotificationRequest(getGetBlobPersistence()));
        assertFalse(isDeleteNotificationRequest(getCreateMultiPartUploadPart()));
        assertFalse(isDeleteNotificationRequest(getEjectStorageDomainRequest()));
        assertFalse(isDeleteNotificationRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isCreateNotificationRequest_test() {
        assertTrue(isCreateNotificationRequest(getRequestCreateNotification()));

        assertFalse(isCreateNotificationRequest(getReplicatePutJob()));
        assertFalse(isCreateNotificationRequest(getRequestDeleteNotification()));
        assertFalse(isCreateNotificationRequest(getRequestGetNotification()));
        assertFalse(isCreateNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isCreateNotificationRequest(getRequestBulkGet()));
        assertFalse(isCreateNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isCreateNotificationRequest(getRequestCreateObject()));
        assertFalse(isCreateNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isCreateNotificationRequest(getRequestSpectraS3GetObject()));
        assertFalse(isCreateNotificationRequest(getGetBlobPersistence()));
        assertFalse(isCreateNotificationRequest(getCreateMultiPartUploadPart()));
        assertFalse(isCreateNotificationRequest(getEjectStorageDomainRequest()));
        assertFalse(isCreateNotificationRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isGetNotificationRequest_test() {
        assertTrue(isGetNotificationRequest(getRequestGetNotification()));

        assertFalse(isGetNotificationRequest(getReplicatePutJob()));
        assertFalse(isGetNotificationRequest(getRequestDeleteNotification()));
        assertFalse(isGetNotificationRequest(getRequestCreateNotification()));
        assertFalse(isGetNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetNotificationRequest(getRequestBulkGet()));
        assertFalse(isGetNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isGetNotificationRequest(getRequestCreateObject()));
        assertFalse(isGetNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetNotificationRequest(getRequestSpectraS3GetObject()));
        assertFalse(isGetNotificationRequest(getGetBlobPersistence()));
        assertFalse(isGetNotificationRequest(getCreateMultiPartUploadPart()));
        assertFalse(isGetNotificationRequest(getEjectStorageDomainRequest()));
        assertFalse(isGetNotificationRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isPhysicalPlacementRequest_test() {
        assertTrue(isPhysicalPlacementRequest(getRequestVerifyPhysicalPlacement()));

        assertFalse(isPhysicalPlacementRequest(getReplicatePutJob()));
        assertFalse(isPhysicalPlacementRequest(getRequestDeleteNotification()));
        assertFalse(isPhysicalPlacementRequest(getRequestCreateNotification()));
        assertFalse(isPhysicalPlacementRequest(getRequestGetNotification()));
        assertFalse(isPhysicalPlacementRequest(getRequestBulkGet()));
        assertFalse(isPhysicalPlacementRequest(getRequestMultiFileDelete()));
        assertFalse(isPhysicalPlacementRequest(getRequestCreateObject()));
        assertFalse(isPhysicalPlacementRequest(getRequestAmazonS3GetObject()));
        assertFalse(isPhysicalPlacementRequest(getRequestSpectraS3GetObject()));
        assertFalse(isPhysicalPlacementRequest(getGetBlobPersistence()));
        assertFalse(isPhysicalPlacementRequest(getCreateMultiPartUploadPart()));
        assertFalse(isPhysicalPlacementRequest(getEjectStorageDomainRequest()));
        assertFalse(isPhysicalPlacementRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isBulkRequest_test() {
        assertTrue(isBulkRequest(getRequestBulkGet()));
        assertTrue(isBulkRequest(getRequestBulkPut()));

        assertFalse(isBulkRequest(getReplicatePutJob()));
        assertFalse(isBulkRequest(getRequestDeleteNotification()));
        assertFalse(isBulkRequest(getRequestCreateNotification()));
        assertFalse(isBulkRequest(getRequestGetNotification()));
        assertFalse(isBulkRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isBulkRequest(getRequestMultiFileDelete()));
        assertFalse(isBulkRequest(getRequestCreateObject()));
        assertFalse(isBulkRequest(getRequestAmazonS3GetObject()));
        assertFalse(isBulkRequest(getRequestSpectraS3GetObject()));
        assertFalse(isBulkRequest(getGetBlobPersistence()));
        assertFalse(isBulkRequest(getCreateMultiPartUploadPart()));
        assertFalse(isBulkRequest(getEjectStorageDomainRequest()));
        assertFalse(isBulkRequest(getCompleteMultipartUploadRequest()));
    }

    @Test public void isBulkPutRequest_Test() {
        assertTrue(isBulkPutRequest(getRequestBulkPut()));

        assertFalse(isBulkPutRequest(getReplicatePutJob()));
        assertFalse(isBulkPutRequest(getRequestBulkGet()));
        assertFalse(isBulkPutRequest(getRequestDeleteNotification()));
        assertFalse(isBulkPutRequest(getRequestCreateNotification()));
        assertFalse(isBulkPutRequest(getRequestGetNotification()));
        assertFalse(isBulkPutRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isBulkPutRequest(getRequestMultiFileDelete()));
        assertFalse(isBulkPutRequest(getRequestCreateObject()));
        assertFalse(isBulkPutRequest(getRequestAmazonS3GetObject()));
        assertFalse(isBulkPutRequest(getRequestSpectraS3GetObject()));
        assertFalse(isBulkPutRequest(getGetBlobPersistence()));
    }

    @Test public void isBulkReplicateRequest_Test() {
        assertTrue(isBulkReplicateRequest(getReplicatePutJob()));

        assertFalse(isBulkReplicateRequest(getRequestBulkPut()));
        assertFalse(isBulkReplicateRequest(getRequestBulkGet()));
        assertFalse(isBulkReplicateRequest(getRequestDeleteNotification()));
        assertFalse(isBulkReplicateRequest(getRequestCreateNotification()));
        assertFalse(isBulkReplicateRequest(getRequestGetNotification()));
        assertFalse(isBulkReplicateRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isBulkReplicateRequest(getRequestMultiFileDelete()));
        assertFalse(isBulkReplicateRequest(getRequestCreateObject()));
        assertFalse(isBulkReplicateRequest(getRequestAmazonS3GetObject()));
        assertFalse(isBulkReplicateRequest(getRequestSpectraS3GetObject()));
        assertFalse(isBulkReplicateRequest(getGetBlobPersistence()));
        assertFalse(isBulkPutRequest(getCreateMultiPartUploadPart()));
        assertFalse(isBulkPutRequest(getEjectStorageDomainRequest()));
        assertFalse(isBulkPutRequest(getCompleteMultipartUploadRequest()));
    }

    @Test public void isBulkGetRequest_Test() {
        assertTrue(isBulkGetRequest(getRequestBulkGet()));

        assertFalse(isBulkGetRequest(getReplicatePutJob()));
        assertFalse(isBulkGetRequest(getRequestBulkPut()));
        assertFalse(isBulkGetRequest(getRequestDeleteNotification()));
        assertFalse(isBulkGetRequest(getRequestCreateNotification()));
        assertFalse(isBulkGetRequest(getRequestGetNotification()));
        assertFalse(isBulkGetRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isBulkGetRequest(getRequestMultiFileDelete()));
        assertFalse(isBulkGetRequest(getRequestCreateObject()));
        assertFalse(isBulkGetRequest(getRequestAmazonS3GetObject()));
        assertFalse(isBulkGetRequest(getRequestSpectraS3GetObject()));
        assertFalse(isBulkGetRequest(getGetBlobPersistence()));
        assertFalse(isBulkGetRequest(getCreateMultiPartUploadPart()));
        assertFalse(isBulkGetRequest(getEjectStorageDomainRequest()));
        assertFalse(isBulkGetRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isMultiFileDeleteRequest_test() {
        assertTrue(isMultiFileDeleteRequest(getRequestMultiFileDelete()));

        assertFalse(isMultiFileDeleteRequest(getReplicatePutJob()));
        assertFalse(isMultiFileDeleteRequest(getRequestDeleteNotification()));
        assertFalse(isMultiFileDeleteRequest(getRequestCreateNotification()));
        assertFalse(isMultiFileDeleteRequest(getRequestGetNotification()));
        assertFalse(isMultiFileDeleteRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isMultiFileDeleteRequest(getRequestBulkGet()));
        assertFalse(isMultiFileDeleteRequest(getRequestCreateObject()));
        assertFalse(isMultiFileDeleteRequest(getRequestAmazonS3GetObject()));
        assertFalse(isMultiFileDeleteRequest(getRequestSpectraS3GetObject()));
        assertFalse(isMultiFileDeleteRequest(getGetBlobPersistence()));
        assertFalse(isMultiFileDeleteRequest(getCreateMultiPartUploadPart()));
        assertFalse(isMultiFileDeleteRequest(getEjectStorageDomainRequest()));
        assertFalse(isMultiFileDeleteRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isCreateObjectRequest_test() {
        assertTrue(isCreateObjectRequest(getRequestCreateObject()));

        assertFalse(isCreateObjectRequest(getReplicatePutJob()));
        assertFalse(isCreateObjectRequest(getRequestDeleteNotification()));
        assertFalse(isCreateObjectRequest(getRequestCreateNotification()));
        assertFalse(isCreateObjectRequest(getRequestGetNotification()));
        assertFalse(isCreateObjectRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isCreateObjectRequest(getRequestBulkGet()));
        assertFalse(isCreateObjectRequest(getRequestMultiFileDelete()));
        assertFalse(isCreateObjectRequest(getRequestAmazonS3GetObject()));
        assertFalse(isCreateObjectRequest(getRequestSpectraS3GetObject()));
        assertFalse(isCreateObjectRequest(getGetBlobPersistence()));
        assertFalse(isCreateObjectRequest(getCreateMultiPartUploadPart()));
        assertFalse(isCreateObjectRequest(getEjectStorageDomainRequest()));
        assertFalse(isCreateObjectRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isGetObjectRequest_test() {
        assertTrue(isGetObjectRequest(getRequestAmazonS3GetObject()));

        assertFalse(isGetObjectRequest(getRequestSpectraS3GetObject()));
        assertFalse(isGetObjectRequest(getReplicatePutJob()));
        assertFalse(isGetObjectRequest(getRequestDeleteNotification()));
        assertFalse(isGetObjectRequest(getRequestCreateNotification()));
        assertFalse(isGetObjectRequest(getRequestGetNotification()));
        assertFalse(isGetObjectRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectRequest(getRequestBulkGet()));
        assertFalse(isGetObjectRequest(getRequestMultiFileDelete()));
        assertFalse(isGetObjectRequest(getRequestCreateObject()));
        assertFalse(isGetObjectRequest(getGetBlobPersistence()));
        assertFalse(isGetObjectRequest(getCreateMultiPartUploadPart()));
        assertFalse(isGetObjectRequest(getEjectStorageDomainRequest()));
        assertFalse(isGetObjectRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isGetObjectSpectraS3Request_test() {
        assertTrue(isGetObjectSpectraS3Request(getRequestSpectraS3GetObject()));

        assertFalse(isGetObjectSpectraS3Request(getReplicatePutJob()));
        assertFalse(isGetObjectSpectraS3Request(getRequestDeleteNotification()));
        assertFalse(isGetObjectSpectraS3Request(getRequestCreateNotification()));
        assertFalse(isGetObjectSpectraS3Request(getRequestGetNotification()));
        assertFalse(isGetObjectSpectraS3Request(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectSpectraS3Request(getRequestBulkGet()));
        assertFalse(isGetObjectSpectraS3Request(getRequestMultiFileDelete()));
        assertFalse(isGetObjectSpectraS3Request(getRequestCreateObject()));
        assertFalse(isGetObjectSpectraS3Request(getRequestAmazonS3GetObject()));
        assertFalse(isGetObjectSpectraS3Request(getGetBlobPersistence()));
        assertFalse(isGetObjectSpectraS3Request(getCreateMultiPartUploadPart()));
        assertFalse(isGetObjectSpectraS3Request(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isGetObjectAmazonS3Request_test() {
        assertTrue(isGetObjectAmazonS3Request(getRequestAmazonS3GetObject()));

        assertFalse(isGetObjectAmazonS3Request(getReplicatePutJob()));
        assertFalse(isGetObjectAmazonS3Request(getRequestDeleteNotification()));
        assertFalse(isGetObjectAmazonS3Request(getRequestCreateNotification()));
        assertFalse(isGetObjectAmazonS3Request(getRequestGetNotification()));
        assertFalse(isGetObjectAmazonS3Request(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectAmazonS3Request(getRequestBulkGet()));
        assertFalse(isGetObjectAmazonS3Request(getRequestMultiFileDelete()));
        assertFalse(isGetObjectAmazonS3Request(getRequestCreateObject()));
        assertFalse(isGetObjectAmazonS3Request(getRequestSpectraS3GetObject()));
        assertFalse(isGetObjectAmazonS3Request(getGetBlobPersistence()));
        assertFalse(isGetObjectAmazonS3Request(getCreateMultiPartUploadPart()));
        assertFalse(isGetObjectAmazonS3Request(getEjectStorageDomainRequest()));
        assertFalse(isGetObjectAmazonS3Request(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isGetJobRequest_Test() {
        assertTrue(isGetJobRequest(getRequestGetJob()));

        assertFalse(isGetJobRequest(getReplicatePutJob()));
        assertFalse(isGetJobRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetJobRequest(getRequestDeleteNotification()));
        assertFalse(isGetJobRequest(getRequestCreateNotification()));
        assertFalse(isGetJobRequest(getRequestGetNotification()));
        assertFalse(isGetJobRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetJobRequest(getRequestBulkGet()));
        assertFalse(isGetJobRequest(getRequestMultiFileDelete()));
        assertFalse(isGetJobRequest(getRequestCreateObject()));
        assertFalse(isGetJobRequest(getRequestSpectraS3GetObject()));
        assertFalse(isGetJobRequest(getGetBlobPersistence()));
    }

    @Test
    public void isGetBlobsPersistenceRequest_Test() {
        assertTrue(isGetBlobPersistenceRequest(getGetBlobPersistence()));

        assertFalse(isGetBlobPersistenceRequest(getRequestGetJob()));
        assertFalse(isGetBlobPersistenceRequest(getReplicatePutJob()));
        assertFalse(isGetBlobPersistenceRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetBlobPersistenceRequest(getRequestDeleteNotification()));
        assertFalse(isGetBlobPersistenceRequest(getRequestCreateNotification()));
        assertFalse(isGetBlobPersistenceRequest(getRequestGetNotification()));
        assertFalse(isGetBlobPersistenceRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetBlobPersistenceRequest(getRequestBulkGet()));
        assertFalse(isGetBlobPersistenceRequest(getRequestMultiFileDelete()));
        assertFalse(isGetBlobPersistenceRequest(getRequestCreateObject()));
        assertFalse(isGetBlobPersistenceRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void hasStringRequestPayload_Test() {
        assertTrue(hasStringRequestPayload(getGetBlobPersistence()));
        assertTrue(hasStringRequestPayload(getReplicatePutJob()));

        assertFalse(hasStringRequestPayload(getRequestGetJob()));
        assertFalse(hasStringRequestPayload(getRequestAmazonS3GetObject()));
        assertFalse(hasStringRequestPayload(getRequestDeleteNotification()));
        assertFalse(hasStringRequestPayload(getRequestCreateNotification()));
        assertFalse(hasStringRequestPayload(getRequestGetNotification()));
        assertFalse(hasStringRequestPayload(getRequestVerifyPhysicalPlacement()));
        assertFalse(hasStringRequestPayload(getRequestBulkGet()));
        assertFalse(hasStringRequestPayload(getRequestMultiFileDelete()));
        assertFalse(hasStringRequestPayload(getRequestCreateObject()));
        assertFalse(hasStringRequestPayload(getRequestSpectraS3GetObject()));
        assertFalse(isGetJobRequest(getCreateMultiPartUploadPart()));
        assertFalse(isGetJobRequest(getEjectStorageDomainRequest()));
        assertFalse(isGetJobRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isEjectStorageDomainRequest_Test() {
        assertTrue(isEjectStorageDomainRequest(getEjectStorageDomainRequest()));

        assertFalse(isEjectStorageDomainRequest(getRequestGetJob()));
        assertFalse(isEjectStorageDomainRequest(getRequestAmazonS3GetObject()));
        assertFalse(isEjectStorageDomainRequest(getRequestDeleteNotification()));
        assertFalse(isEjectStorageDomainRequest(getRequestCreateNotification()));
        assertFalse(isEjectStorageDomainRequest(getRequestGetNotification()));
        assertFalse(isEjectStorageDomainRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isEjectStorageDomainRequest(getRequestBulkGet()));
        assertFalse(isEjectStorageDomainRequest(getRequestMultiFileDelete()));
        assertFalse(isEjectStorageDomainRequest(getRequestCreateObject()));
        assertFalse(isEjectStorageDomainRequest(getRequestSpectraS3GetObject()));
        assertFalse(isEjectStorageDomainRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void hasListObjectsRequestPayload_Test() {
        assertTrue(hasListObjectsRequestPayload(getEjectStorageDomainRequest()));
        assertTrue(hasListObjectsRequestPayload(getRequestVerifyPhysicalPlacement()));

        assertFalse(hasListObjectsRequestPayload(getRequestGetJob()));
        assertFalse(hasListObjectsRequestPayload(getRequestAmazonS3GetObject()));
        assertFalse(hasListObjectsRequestPayload(getRequestDeleteNotification()));
        assertFalse(hasListObjectsRequestPayload(getRequestCreateNotification()));
        assertFalse(hasListObjectsRequestPayload(getRequestGetNotification()));
        assertFalse(hasListObjectsRequestPayload(getRequestBulkGet()));
        assertFalse(hasListObjectsRequestPayload(getRequestMultiFileDelete()));
        assertFalse(hasListObjectsRequestPayload(getRequestCreateObject()));
        assertFalse(hasListObjectsRequestPayload(getRequestSpectraS3GetObject()));
        assertFalse(hasListObjectsRequestPayload(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void paramListContainsParam_test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Name1", "Type1", false),
                new Ds3Param("Name2", "Type2", false));

        assertTrue(paramListContainsParam(params, "Name1", "Type1"));
        assertTrue(paramListContainsParam(params, "Name2", "Type2"));

        assertFalse(paramListContainsParam(params, "Name1", "Type2"));
        assertFalse(paramListContainsParam(params, "Name2", "Type1"));
        assertFalse(paramListContainsParam(null, "Name1", "Type1"));
    }

    @Test
    public void isCreateMultiPartUploadPartRequest_Test() {
        assertTrue(isCreateMultiPartUploadPartRequest(getCreateMultiPartUploadPart()));

        assertFalse(isCreateMultiPartUploadPartRequest(getRequestGetJob()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestAmazonS3GetObject()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestDeleteNotification()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestCreateNotification()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestGetNotification()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestBulkGet()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestMultiFileDelete()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestCreateObject()));
        assertFalse(isCreateMultiPartUploadPartRequest(getRequestSpectraS3GetObject()));
        assertFalse(isCreateMultiPartUploadPartRequest(getCompleteMultipartUploadRequest()));
    }

    @Test
    public void isCompleteMultiPartUploadRequest_Test() {
        assertTrue(isCompleteMultiPartUploadRequest(getCompleteMultipartUploadRequest()));

        assertFalse(isCompleteMultiPartUploadRequest(getCreateMultiPartUploadPart()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestGetJob()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestAmazonS3GetObject()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestDeleteNotification()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestCreateNotification()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestGetNotification()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestBulkGet()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestMultiFileDelete()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestCreateObject()));
        assertFalse(isCompleteMultiPartUploadRequest(getRequestSpectraS3GetObject()));
    }
}
