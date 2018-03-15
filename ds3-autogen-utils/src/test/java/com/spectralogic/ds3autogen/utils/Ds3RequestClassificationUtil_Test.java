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
        assertFalse(isAmazonCreateObjectRequest(getObjectsDetailsRequest()));
        assertFalse(isAmazonCreateObjectRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isAmazonCreateObjectRequest(createVerifyJobRequest()));
        assertFalse(isAmazonCreateObjectRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isAmazonCreateObjectRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isAmazonCreateObjectRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isAmazonCreateObjectRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isAmazonCreateObjectRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isNotificationRequest(getObjectsDetailsRequest()));
        assertFalse(isNotificationRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isNotificationRequest(createVerifyJobRequest()));
        assertFalse(isNotificationRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isNotificationRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isNotificationRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isNotificationRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isNotificationRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isGetObjectsDetailsRequest_Test() {
        assertTrue(isGetObjectsDetailsRequest(getObjectsDetailsRequest()));

        assertFalse(isGetObjectsDetailsRequest(getRequestDeleteNotification()));
        assertFalse(isGetObjectsDetailsRequest(getReplicatePutJob()));
        assertFalse(isGetObjectsDetailsRequest(getRequestCreateNotification()));
        assertFalse(isGetObjectsDetailsRequest(getRequestGetNotification()));
        assertFalse(isGetObjectsDetailsRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectsDetailsRequest(getRequestBulkGet()));
        assertFalse(isGetObjectsDetailsRequest(getRequestMultiFileDelete()));
        assertFalse(isGetObjectsDetailsRequest(getRequestCreateObject()));
        assertFalse(isGetObjectsDetailsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetObjectsDetailsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isGetObjectsDetailsRequest(getGetBlobPersistence()));
        assertFalse(isGetObjectsDetailsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isGetObjectsDetailsRequest(getEjectStorageDomainRequest()));
        assertFalse(isGetObjectsDetailsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isGetObjectsDetailsRequest(getUsersSpectraS3Request()));
        assertFalse(isGetObjectsDetailsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetObjectsDetailsRequest(createVerifyJobRequest()));
        assertFalse(isGetObjectsDetailsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetObjectsDetailsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetObjectsDetailsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetObjectsDetailsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetObjectsDetailsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isGetUsersSpectraS3Request_Test() {
        assertTrue(isGetUsersSpectraS3Request(getUsersSpectraS3Request()));

        assertFalse(isGetUsersSpectraS3Request(getObjectsDetailsRequest()));
        assertFalse(isGetUsersSpectraS3Request(getRequestDeleteNotification()));
        assertFalse(isGetUsersSpectraS3Request(getReplicatePutJob()));
        assertFalse(isGetUsersSpectraS3Request(getRequestCreateNotification()));
        assertFalse(isGetUsersSpectraS3Request(getRequestGetNotification()));
        assertFalse(isGetUsersSpectraS3Request(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetUsersSpectraS3Request(getRequestBulkGet()));
        assertFalse(isGetUsersSpectraS3Request(getRequestMultiFileDelete()));
        assertFalse(isGetUsersSpectraS3Request(getRequestCreateObject()));
        assertFalse(isGetUsersSpectraS3Request(getRequestAmazonS3GetObject()));
        assertFalse(isGetUsersSpectraS3Request(getRequestSpectraS3GetObject()));
        assertFalse(isGetUsersSpectraS3Request(getGetBlobPersistence()));
        assertFalse(isGetUsersSpectraS3Request(getCreateMultiPartUploadPart()));
        assertFalse(isGetUsersSpectraS3Request(getEjectStorageDomainRequest()));
        assertFalse(isGetUsersSpectraS3Request(getCompleteMultipartUploadRequest()));
        assertFalse(isGetUsersSpectraS3Request(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetUsersSpectraS3Request(createVerifyJobRequest()));
        assertFalse(isGetUsersSpectraS3Request(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetUsersSpectraS3Request(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetUsersSpectraS3Request(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetUsersSpectraS3Request(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetUsersSpectraS3Request(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void supportsPaginationRequest_Test() {
        assertTrue(supportsPaginationRequest(getUsersSpectraS3Request()));
        assertTrue(supportsPaginationRequest(getObjectsDetailsRequest()));
        assertTrue(supportsPaginationRequest(getObjectsWithFullDetailsRequest()));

        assertFalse(supportsPaginationRequest(getRequestDeleteNotification()));
        assertFalse(supportsPaginationRequest(getReplicatePutJob()));
        assertFalse(supportsPaginationRequest(getRequestCreateNotification()));
        assertFalse(supportsPaginationRequest(getRequestGetNotification()));
        assertFalse(supportsPaginationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(supportsPaginationRequest(getRequestBulkGet()));
        assertFalse(supportsPaginationRequest(getRequestMultiFileDelete()));
        assertFalse(supportsPaginationRequest(getRequestCreateObject()));
        assertFalse(supportsPaginationRequest(getRequestAmazonS3GetObject()));
        assertFalse(supportsPaginationRequest(getRequestSpectraS3GetObject()));
        assertFalse(supportsPaginationRequest(getGetBlobPersistence()));
        assertFalse(supportsPaginationRequest(getCreateMultiPartUploadPart()));
        assertFalse(supportsPaginationRequest(getEjectStorageDomainRequest()));
        assertFalse(supportsPaginationRequest(getCompleteMultipartUploadRequest()));
        assertFalse(supportsPaginationRequest(createVerifyJobRequest()));
        assertFalse(supportsPaginationRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(supportsPaginationRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(supportsPaginationRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(supportsPaginationRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(supportsPaginationRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isDeleteNotificationRequest(getObjectsDetailsRequest()));
        assertFalse(isDeleteNotificationRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isDeleteNotificationRequest(createVerifyJobRequest()));
        assertFalse(isDeleteNotificationRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isDeleteNotificationRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isDeleteNotificationRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isDeleteNotificationRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isDeleteNotificationRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isCreateNotificationRequest(getObjectsDetailsRequest()));
        assertFalse(isCreateNotificationRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isCreateNotificationRequest(createVerifyJobRequest()));
        assertFalse(isCreateNotificationRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isCreateNotificationRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isCreateNotificationRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isCreateNotificationRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isCreateNotificationRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isGetNotificationRequest(getObjectsDetailsRequest()));
        assertFalse(isGetNotificationRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetNotificationRequest(createVerifyJobRequest()));
        assertFalse(isGetNotificationRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetNotificationRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetNotificationRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetNotificationRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetNotificationRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isPhysicalPlacementRequest_test() {
        assertTrue(isPhysicalPlacementRequest(getRequestVerifyPhysicalPlacement()));
        assertTrue(isPhysicalPlacementRequest(createVerifyJobRequest()));

        assertTrue(isPhysicalPlacementRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertTrue(isPhysicalPlacementRequest(getBlobsOnTapeSpectraS3Request()));
        assertTrue(isPhysicalPlacementRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertTrue(isPhysicalPlacementRequest(getBlobsOnPoolSpectraS3Request()));
        assertTrue(isPhysicalPlacementRequest(getBlobsOnDs3TargetSpectraS3Request()));

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
        assertFalse(isPhysicalPlacementRequest(getObjectsDetailsRequest()));
        assertFalse(isPhysicalPlacementRequest(getObjectsWithFullDetailsRequest()));
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
        assertFalse(isBulkRequest(getObjectsDetailsRequest()));
        assertFalse(isBulkRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isBulkRequest(createVerifyJobRequest()));
        assertFalse(isBulkRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isBulkRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isBulkRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isBulkRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isBulkRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isBulkPutRequest(getObjectsDetailsRequest()));
        assertFalse(isBulkPutRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isBulkPutRequest(createVerifyJobRequest()));
        assertFalse(isBulkPutRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isBulkPutRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isBulkPutRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isBulkPutRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isBulkPutRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isBulkReplicateRequest(getCreateMultiPartUploadPart()));
        assertFalse(isBulkReplicateRequest(getEjectStorageDomainRequest()));
        assertFalse(isBulkReplicateRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isBulkReplicateRequest(getObjectsDetailsRequest()));
        assertFalse(isBulkReplicateRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isBulkReplicateRequest(createVerifyJobRequest()));
        assertFalse(isBulkReplicateRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isBulkReplicateRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isBulkReplicateRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isBulkReplicateRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isBulkReplicateRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isBulkGetRequest(getObjectsDetailsRequest()));
        assertFalse(isBulkGetRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isBulkGetRequest(createVerifyJobRequest()));
        assertFalse(isBulkGetRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isBulkGetRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isBulkGetRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isBulkGetRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isBulkGetRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isMultiFileDeleteRequest(getObjectsDetailsRequest()));
        assertFalse(isMultiFileDeleteRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isMultiFileDeleteRequest(createVerifyJobRequest()));
        assertFalse(isMultiFileDeleteRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isMultiFileDeleteRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isMultiFileDeleteRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isMultiFileDeleteRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isMultiFileDeleteRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isCreateObjectRequest(getObjectsDetailsRequest()));
        assertFalse(isCreateObjectRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isCreateObjectRequest(createVerifyJobRequest()));
        assertFalse(isCreateObjectRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isCreateObjectRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isCreateObjectRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isCreateObjectRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isCreateObjectRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isGetObjectRequest(getObjectsDetailsRequest()));
        assertFalse(isGetObjectRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetObjectRequest(createVerifyJobRequest()));
        assertFalse(isGetObjectRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetObjectRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetObjectRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetObjectRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetObjectRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isGetObjectSpectraS3Request(getObjectsDetailsRequest()));
        assertFalse(isGetObjectSpectraS3Request(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetObjectSpectraS3Request(createVerifyJobRequest()));
        assertFalse(isGetObjectSpectraS3Request(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetObjectSpectraS3Request(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetObjectSpectraS3Request(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetObjectSpectraS3Request(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetObjectSpectraS3Request(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isGetObjectAmazonS3Request(getObjectsDetailsRequest()));
        assertFalse(isGetObjectAmazonS3Request(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetObjectAmazonS3Request(createVerifyJobRequest()));
        assertFalse(isGetObjectAmazonS3Request(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetObjectAmazonS3Request(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetObjectAmazonS3Request(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetObjectAmazonS3Request(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetObjectAmazonS3Request(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isGetJobRequest(getObjectsDetailsRequest()));
        assertFalse(isGetJobRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetJobRequest(createVerifyJobRequest()));
        assertFalse(isGetJobRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetJobRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetJobRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetJobRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetJobRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isGetBlobPersistenceRequest(getObjectsDetailsRequest()));
        assertFalse(isGetBlobPersistenceRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetBlobPersistenceRequest(createVerifyJobRequest()));
        assertFalse(isGetBlobPersistenceRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetBlobPersistenceRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetBlobPersistenceRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetBlobPersistenceRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetBlobPersistenceRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(hasStringRequestPayload(getCreateMultiPartUploadPart()));
        assertFalse(hasStringRequestPayload(getEjectStorageDomainRequest()));
        assertFalse(hasStringRequestPayload(getCompleteMultipartUploadRequest()));
        assertFalse(hasStringRequestPayload(getObjectsDetailsRequest()));
        assertFalse(hasStringRequestPayload(getObjectsWithFullDetailsRequest()));
        assertFalse(hasStringRequestPayload(createVerifyJobRequest()));
        assertFalse(hasStringRequestPayload(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(hasStringRequestPayload(getBlobsOnTapeSpectraS3Request()));
        assertFalse(hasStringRequestPayload(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(hasStringRequestPayload(getBlobsOnPoolSpectraS3Request()));
        assertFalse(hasStringRequestPayload(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isEjectStorageDomainBlobsRequest_Test() {
        assertTrue(isEjectStorageDomainBlobsRequest(getEjectStorageDomainBlobsRequest()));

        assertFalse(isEjectStorageDomainBlobsRequest(getEjectStorageDomainRequest()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestGetJob()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestDeleteNotification()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestCreateNotification()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestGetNotification()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestBulkGet()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestMultiFileDelete()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestCreateObject()));
        assertFalse(isEjectStorageDomainBlobsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isEjectStorageDomainBlobsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isEjectStorageDomainBlobsRequest(getObjectsDetailsRequest()));
        assertFalse(isEjectStorageDomainBlobsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isEjectStorageDomainBlobsRequest(createVerifyJobRequest()));
        assertFalse(isEjectStorageDomainBlobsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isEjectStorageDomainBlobsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isEjectStorageDomainBlobsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isEjectStorageDomainBlobsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isEjectStorageDomainBlobsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void hasListObjectsRequestPayload_Test() {
        assertTrue(hasListObjectsRequestPayload(getEjectStorageDomainBlobsRequest()));
        assertTrue(hasListObjectsRequestPayload(getRequestVerifyPhysicalPlacement()));
        assertTrue(hasListObjectsRequestPayload(createVerifyJobRequest()));

        assertTrue(hasListObjectsRequestPayload(getBlobsOnAzureTargetSpectraS3Request()));
        assertTrue(hasListObjectsRequestPayload(getBlobsOnTapeSpectraS3Request()));
        assertTrue(hasListObjectsRequestPayload(getBlobsOnS3TargetSpectraS3Request()));
        assertTrue(hasListObjectsRequestPayload(getBlobsOnPoolSpectraS3Request()));
        assertTrue(hasListObjectsRequestPayload(getBlobsOnDs3TargetSpectraS3Request()));

        assertFalse(hasListObjectsRequestPayload(getEjectStorageDomainRequest()));
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
        assertFalse(hasListObjectsRequestPayload(getObjectsDetailsRequest()));
        assertFalse(hasListObjectsRequestPayload(getObjectsWithFullDetailsRequest()));
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
        assertFalse(isCreateMultiPartUploadPartRequest(getObjectsDetailsRequest()));
        assertFalse(isCreateMultiPartUploadPartRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isCreateMultiPartUploadPartRequest(createVerifyJobRequest()));
        assertFalse(isCreateMultiPartUploadPartRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isCreateMultiPartUploadPartRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isCreateMultiPartUploadPartRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isCreateMultiPartUploadPartRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isCreateMultiPartUploadPartRequest(getBlobsOnDs3TargetSpectraS3Request()));
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
        assertFalse(isCompleteMultiPartUploadRequest(getObjectsDetailsRequest()));
        assertFalse(isCompleteMultiPartUploadRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isCompleteMultiPartUploadRequest(createVerifyJobRequest()));
        assertFalse(isCompleteMultiPartUploadRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isCompleteMultiPartUploadRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isCompleteMultiPartUploadRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isCompleteMultiPartUploadRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isCompleteMultiPartUploadRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isGetObjectsWithFullDetails_Test() {
        assertTrue(isGetObjectsWithFullDetails(getObjectsWithFullDetailsRequest()));

        assertFalse(isGetObjectsWithFullDetails(getCompleteMultipartUploadRequest()));
        assertFalse(isGetObjectsWithFullDetails(getCreateMultiPartUploadPart()));
        assertFalse(isGetObjectsWithFullDetails(getRequestGetJob()));
        assertFalse(isGetObjectsWithFullDetails(getRequestAmazonS3GetObject()));
        assertFalse(isGetObjectsWithFullDetails(getRequestDeleteNotification()));
        assertFalse(isGetObjectsWithFullDetails(getRequestCreateNotification()));
        assertFalse(isGetObjectsWithFullDetails(getRequestGetNotification()));
        assertFalse(isGetObjectsWithFullDetails(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectsWithFullDetails(getRequestBulkGet()));
        assertFalse(isGetObjectsWithFullDetails(getRequestMultiFileDelete()));
        assertFalse(isGetObjectsWithFullDetails(getRequestCreateObject()));
        assertFalse(isGetObjectsWithFullDetails(getRequestSpectraS3GetObject()));
        assertFalse(isGetObjectsWithFullDetails(getObjectsDetailsRequest()));
        assertFalse(isGetObjectsWithFullDetails(createVerifyJobRequest()));
        assertFalse(isGetObjectsWithFullDetails(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetObjectsWithFullDetails(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetObjectsWithFullDetails(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetObjectsWithFullDetails(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetObjectsWithFullDetails(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isClearSuspectBlobAzureTargetsRequest_Test() {
        assertTrue(isClearSuspectBlobAzureTargetsRequest(clearSuspectBlobAzureTargetsRequest()));

        assertFalse(isClearSuspectBlobAzureTargetsRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isClearSuspectBlobAzureTargetsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestGetJob()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestDeleteNotification()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestCreateNotification()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestGetNotification()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestBulkGet()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestMultiFileDelete()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestCreateObject()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getObjectsDetailsRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(createVerifyJobRequest()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isClearSuspectBlobAzureTargetsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isClearSuspectBlobPoolsRequest_Test() {
        assertTrue(isClearSuspectBlobPoolsRequest(clearSuspectBlobPoolsRequest()));

        assertFalse(isClearSuspectBlobPoolsRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isClearSuspectBlobPoolsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestGetJob()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestDeleteNotification()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestCreateNotification()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestGetNotification()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestBulkGet()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestMultiFileDelete()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestCreateObject()));
        assertFalse(isClearSuspectBlobPoolsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isClearSuspectBlobPoolsRequest(getObjectsDetailsRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(createVerifyJobRequest()));
        assertFalse(isClearSuspectBlobPoolsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobPoolsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isClearSuspectBlobPoolsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobPoolsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isClearSuspectBlobPoolsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isClearSuspectBlobS3TargetsRequest_Test() {
        assertTrue(isClearSuspectBlobS3TargetsRequest(clearSuspectBlobS3TargetsRequest()));

        assertFalse(isClearSuspectBlobS3TargetsRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isClearSuspectBlobS3TargetsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestGetJob()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestDeleteNotification()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestCreateNotification()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestGetNotification()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestBulkGet()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestMultiFileDelete()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestCreateObject()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getObjectsDetailsRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(createVerifyJobRequest()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isClearSuspectBlobS3TargetsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isClearSuspectBlobTapesRequest_Test() {
        assertTrue(isClearSuspectBlobTapesRequest(clearSuspectBlobTapesRequest()));

        assertFalse(isClearSuspectBlobTapesRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isClearSuspectBlobTapesRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(getCreateMultiPartUploadPart()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestGetJob()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestAmazonS3GetObject()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestDeleteNotification()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestCreateNotification()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestGetNotification()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestBulkGet()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestMultiFileDelete()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestCreateObject()));
        assertFalse(isClearSuspectBlobTapesRequest(getRequestSpectraS3GetObject()));
        assertFalse(isClearSuspectBlobTapesRequest(getObjectsDetailsRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(createVerifyJobRequest()));
        assertFalse(isClearSuspectBlobTapesRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobTapesRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isClearSuspectBlobTapesRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isClearSuspectBlobTapesRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isClearSuspectBlobTapesRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isMarkSuspectBlobAzureTargetsAsDegradedRequest_Test() {
        assertTrue(isMarkSuspectBlobAzureTargetsAsDegradedRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getCreateMultiPartUploadPart()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestGetJob()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestAmazonS3GetObject()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestDeleteNotification()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestCreateNotification()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestGetNotification()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestBulkGet()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestMultiFileDelete()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestCreateObject()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getRequestSpectraS3GetObject()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getObjectsDetailsRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(createVerifyJobRequest()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isMarkSuspectBlobAzureTargetsAsDegradedRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isMarkSuspectBlobDs3TargetsAsDegradedRequest_Test() {
        assertTrue(isMarkSuspectBlobDs3TargetsAsDegradedRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getCreateMultiPartUploadPart()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestGetJob()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestAmazonS3GetObject()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestDeleteNotification()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestCreateNotification()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestGetNotification()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestBulkGet()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestMultiFileDelete()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestCreateObject()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getRequestSpectraS3GetObject()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getObjectsDetailsRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(createVerifyJobRequest()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isMarkSuspectBlobDs3TargetsAsDegradedRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isMarkSuspectBlobPoolsAsDegradedRequest_Test() {
        assertTrue(isMarkSuspectBlobPoolsAsDegradedRequest(markSuspectBlobPoolsAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getCreateMultiPartUploadPart()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestGetJob()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestAmazonS3GetObject()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestDeleteNotification()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestCreateNotification()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestGetNotification()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestBulkGet()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestMultiFileDelete()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestCreateObject()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getRequestSpectraS3GetObject()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getObjectsDetailsRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(createVerifyJobRequest()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isMarkSuspectBlobPoolsAsDegradedRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isMarkSuspectBlobS3TargetsAsDegradedRequest_Test() {
        assertTrue(isMarkSuspectBlobS3TargetsAsDegradedRequest(markSuspectBlobS3TargetsAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getCreateMultiPartUploadPart()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestGetJob()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestAmazonS3GetObject()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestDeleteNotification()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestCreateNotification()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestGetNotification()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestBulkGet()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestMultiFileDelete()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestCreateObject()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getRequestSpectraS3GetObject()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getObjectsDetailsRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(createVerifyJobRequest()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isMarkSuspectBlobS3TargetsAsDegradedRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isMarkSuspectBlobTapesAsDegradedRequest_Test() {
        assertTrue(isMarkSuspectBlobTapesAsDegradedRequest(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(markSuspectBlobS3TargetsAsDegradedRequest()));

        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getCreateMultiPartUploadPart()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestGetJob()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestAmazonS3GetObject()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestDeleteNotification()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestCreateNotification()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestGetNotification()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestBulkGet()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestMultiFileDelete()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestCreateObject()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getRequestSpectraS3GetObject()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getObjectsDetailsRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(createVerifyJobRequest()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isMarkSuspectBlobTapesAsDegradedRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void hasIdsRequestPayload_Test() {
        assertTrue(hasIdsRequestPayload(clearSuspectBlobAzureTargetsRequest()));
        assertTrue(hasIdsRequestPayload(clearSuspectBlobPoolsRequest()));
        assertTrue(hasIdsRequestPayload(clearSuspectBlobDs3TargetsRequest()));
        assertTrue(hasIdsRequestPayload(clearSuspectBlobS3TargetsRequest()));
        assertTrue(hasIdsRequestPayload(clearSuspectBlobTapesRequest()));
        assertTrue(hasIdsRequestPayload(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertTrue(hasIdsRequestPayload(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertTrue(hasIdsRequestPayload(markSuspectBlobPoolsAsDegradedRequest()));
        assertTrue(hasIdsRequestPayload(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertTrue(hasIdsRequestPayload(markSuspectBlobTapesAsDegradedRequest()));

        assertFalse(hasIdsRequestPayload(getObjectsWithFullDetailsRequest()));
        assertFalse(hasIdsRequestPayload(getCompleteMultipartUploadRequest()));
        assertFalse(hasIdsRequestPayload(getCreateMultiPartUploadPart()));
        assertFalse(hasIdsRequestPayload(getRequestGetJob()));
        assertFalse(hasIdsRequestPayload(getRequestAmazonS3GetObject()));
        assertFalse(hasIdsRequestPayload(getRequestDeleteNotification()));
        assertFalse(hasIdsRequestPayload(getRequestCreateNotification()));
        assertFalse(hasIdsRequestPayload(getRequestGetNotification()));
        assertFalse(hasIdsRequestPayload(getRequestVerifyPhysicalPlacement()));
        assertFalse(hasIdsRequestPayload(getRequestBulkGet()));
        assertFalse(hasIdsRequestPayload(getRequestMultiFileDelete()));
        assertFalse(hasIdsRequestPayload(getRequestCreateObject()));
        assertFalse(hasIdsRequestPayload(getRequestSpectraS3GetObject()));
        assertFalse(hasIdsRequestPayload(getObjectsDetailsRequest()));
        assertFalse(hasIdsRequestPayload(createVerifyJobRequest()));
        assertFalse(hasIdsRequestPayload(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(hasIdsRequestPayload(getBlobsOnTapeSpectraS3Request()));
        assertFalse(hasIdsRequestPayload(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(hasIdsRequestPayload(getBlobsOnPoolSpectraS3Request()));
        assertFalse(hasIdsRequestPayload(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void hasPutObjectsWithSizeRequestPayload_Test() {
        assertTrue(hasPutObjectsWithSizeRequestPayload(getRequestBulkPut()));

        assertFalse(hasPutObjectsWithSizeRequestPayload(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(clearSuspectBlobPoolsRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(clearSuspectBlobS3TargetsRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(clearSuspectBlobTapesRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getObjectsWithFullDetailsRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getCompleteMultipartUploadRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getCreateMultiPartUploadPart()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestGetJob()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestAmazonS3GetObject()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestDeleteNotification()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestCreateNotification()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestGetNotification()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestVerifyPhysicalPlacement()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestBulkGet()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestMultiFileDelete()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestCreateObject()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getRequestSpectraS3GetObject()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getObjectsDetailsRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(createVerifyJobRequest()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getBlobsOnTapeSpectraS3Request()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getBlobsOnPoolSpectraS3Request()));
        assertFalse(hasPutObjectsWithSizeRequestPayload(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void hasGetObjectsWithLengthOffsetRequestPayload_Test() {
        assertTrue(hasGetObjectsWithLengthOffsetRequestPayload(getRequestBulkGet()));
        assertTrue(hasGetObjectsWithLengthOffsetRequestPayload(createVerifyJobRequest()));

        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestBulkPut()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(clearSuspectBlobPoolsRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(clearSuspectBlobS3TargetsRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(clearSuspectBlobTapesRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getObjectsWithFullDetailsRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getCompleteMultipartUploadRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getCreateMultiPartUploadPart()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestGetJob()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestAmazonS3GetObject()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestDeleteNotification()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestCreateNotification()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestGetNotification()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestVerifyPhysicalPlacement()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestMultiFileDelete()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestCreateObject()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getRequestSpectraS3GetObject()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getObjectsDetailsRequest()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getBlobsOnTapeSpectraS3Request()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getBlobsOnPoolSpectraS3Request()));
        assertFalse(hasGetObjectsWithLengthOffsetRequestPayload(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isCreateVerifyJobRequest_Test() {
        assertTrue(isCreateVerifyJobRequest(createVerifyJobRequest()));

        assertFalse(isCreateVerifyJobRequest(getRequestBulkGet()));
        assertFalse(isCreateVerifyJobRequest(getRequestBulkPut()));
        assertFalse(isCreateVerifyJobRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isCreateVerifyJobRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isCreateVerifyJobRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isCreateVerifyJobRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isCreateVerifyJobRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isCreateVerifyJobRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isCreateVerifyJobRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isCreateVerifyJobRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isCreateVerifyJobRequest(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(isCreateVerifyJobRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isCreateVerifyJobRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isCreateVerifyJobRequest(getCreateMultiPartUploadPart()));
        assertFalse(isCreateVerifyJobRequest(getRequestGetJob()));
        assertFalse(isCreateVerifyJobRequest(getRequestAmazonS3GetObject()));
        assertFalse(isCreateVerifyJobRequest(getRequestDeleteNotification()));
        assertFalse(isCreateVerifyJobRequest(getRequestCreateNotification()));
        assertFalse(isCreateVerifyJobRequest(getRequestGetNotification()));
        assertFalse(isCreateVerifyJobRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isCreateVerifyJobRequest(getRequestMultiFileDelete()));
        assertFalse(isCreateVerifyJobRequest(getRequestCreateObject()));
        assertFalse(isCreateVerifyJobRequest(getRequestSpectraS3GetObject()));
        assertFalse(isCreateVerifyJobRequest(getObjectsDetailsRequest()));
        assertFalse(isCreateVerifyJobRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isCreateVerifyJobRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isCreateVerifyJobRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isCreateVerifyJobRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isCreateVerifyJobRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void hasSimpleObjectsRequestPayload_Test() {
        assertTrue(hasSimpleObjectsRequestPayload(getPhysicalPlacementForObjects()));
        assertTrue(hasSimpleObjectsRequestPayload(getPhysicalPlacementForObjectsWithFullDetails()));
        assertTrue(hasSimpleObjectsRequestPayload(verifyPhysicalPlacementForObjectsRequest()));
        assertTrue(hasSimpleObjectsRequestPayload(verifyPhysicalPlacementForObjectsWithFullDetailsRequest()));
        assertTrue(hasSimpleObjectsRequestPayload(getEjectStorageDomainBlobsRequest()));

        assertFalse(hasSimpleObjectsRequestPayload(createVerifyJobRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestBulkGet()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestBulkPut()));
        assertFalse(hasSimpleObjectsRequestPayload(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(clearSuspectBlobPoolsRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(clearSuspectBlobS3TargetsRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(clearSuspectBlobTapesRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(getObjectsWithFullDetailsRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(getCompleteMultipartUploadRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(getCreateMultiPartUploadPart()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestGetJob()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestAmazonS3GetObject()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestDeleteNotification()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestCreateNotification()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestGetNotification()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestMultiFileDelete()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestCreateObject()));
        assertFalse(hasSimpleObjectsRequestPayload(getRequestSpectraS3GetObject()));
        assertFalse(hasSimpleObjectsRequestPayload(getObjectsDetailsRequest()));
        assertFalse(hasSimpleObjectsRequestPayload(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(hasSimpleObjectsRequestPayload(getBlobsOnTapeSpectraS3Request()));
        assertFalse(hasSimpleObjectsRequestPayload(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(hasSimpleObjectsRequestPayload(getBlobsOnPoolSpectraS3Request()));
        assertFalse(hasSimpleObjectsRequestPayload(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isGetPhysicalPlacementForObjectsRequest_Test() {
        assertTrue(isGetPhysicalPlacementForObjectsRequest(getPhysicalPlacementForObjects()));

        assertFalse(isGetPhysicalPlacementForObjectsRequest(getPhysicalPlacementForObjectsWithFullDetails()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(verifyPhysicalPlacementForObjectsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(verifyPhysicalPlacementForObjectsWithFullDetailsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getEjectStorageDomainBlobsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(createVerifyJobRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestBulkGet()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestBulkPut()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestGetJob()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestDeleteNotification()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestCreateNotification()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestGetNotification()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestMultiFileDelete()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestCreateObject()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getObjectsDetailsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isGetPhysicalPlacementForObjectsWithFullDetailsRequest_Test() {
        assertTrue(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getPhysicalPlacementForObjectsWithFullDetails()));

        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getPhysicalPlacementForObjects()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(verifyPhysicalPlacementForObjectsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(verifyPhysicalPlacementForObjectsWithFullDetailsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getEjectStorageDomainBlobsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(createVerifyJobRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestBulkGet()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestBulkPut()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestGetJob()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestDeleteNotification()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestCreateNotification()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestGetNotification()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestMultiFileDelete()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestCreateObject()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getObjectsDetailsRequest()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isGetPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isVerifyPhysicalPlacementForObjectsRequest_Test() {
        assertTrue(isVerifyPhysicalPlacementForObjectsRequest(verifyPhysicalPlacementForObjectsRequest()));

        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getPhysicalPlacementForObjects()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getPhysicalPlacementForObjectsWithFullDetails()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(verifyPhysicalPlacementForObjectsWithFullDetailsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getEjectStorageDomainBlobsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(createVerifyJobRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestBulkGet()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestBulkPut()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestGetJob()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestDeleteNotification()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestCreateNotification()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestGetNotification()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestMultiFileDelete()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestCreateObject()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getObjectsDetailsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }

    @Test
    public void isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest_Test() {
        assertTrue(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(verifyPhysicalPlacementForObjectsWithFullDetailsRequest()));

        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getPhysicalPlacementForObjectsWithFullDetails()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getPhysicalPlacementForObjects()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(verifyPhysicalPlacementForObjectsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getEjectStorageDomainBlobsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(createVerifyJobRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestBulkGet()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestBulkPut()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobAzureTargetsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobPoolsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobS3TargetsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(clearSuspectBlobTapesRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobAzureTargetsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobDs3TargetsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobPoolsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobS3TargetsAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(markSuspectBlobTapesAsDegradedRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getObjectsWithFullDetailsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getCompleteMultipartUploadRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getCreateMultiPartUploadPart()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestGetJob()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestAmazonS3GetObject()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestDeleteNotification()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestCreateNotification()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestGetNotification()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestMultiFileDelete()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestCreateObject()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getRequestSpectraS3GetObject()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getObjectsDetailsRequest()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnAzureTargetSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnTapeSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnS3TargetSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnPoolSpectraS3Request()));
        assertFalse(isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(getBlobsOnDs3TargetSpectraS3Request()));
    }
}
