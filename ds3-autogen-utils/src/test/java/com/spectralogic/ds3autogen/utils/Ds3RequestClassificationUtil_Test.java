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
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Ds3RequestClassificationUtil_Test {

    @Test
    public void isNotificationRequest_test() {
        assertTrue(isNotificationRequest(getRequestDeleteNotification()));
        assertTrue(isNotificationRequest(getRequestCreateNotification()));
        assertTrue(isNotificationRequest(getRequestGetNotification()));

        assertFalse(isNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isNotificationRequest(getRequestBulk()));
        assertFalse(isNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isNotificationRequest(getRequestCreateObject()));
        assertFalse(isNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isNotificationRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isDeleteNotificationRequest_test() {
        assertTrue(isDeleteNotificationRequest(getRequestDeleteNotification()));

        assertFalse(isDeleteNotificationRequest(getRequestCreateNotification()));
        assertFalse(isDeleteNotificationRequest(getRequestGetNotification()));
        assertFalse(isDeleteNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isDeleteNotificationRequest(getRequestBulk()));
        assertFalse(isDeleteNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isDeleteNotificationRequest(getRequestCreateObject()));
        assertFalse(isDeleteNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isDeleteNotificationRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isCreateNotificationRequest_test() {
        assertTrue(isCreateNotificationRequest(getRequestCreateNotification()));

        assertFalse(isCreateNotificationRequest(getRequestDeleteNotification()));
        assertFalse(isCreateNotificationRequest(getRequestGetNotification()));
        assertFalse(isCreateNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isCreateNotificationRequest(getRequestBulk()));
        assertFalse(isCreateNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isCreateNotificationRequest(getRequestCreateObject()));
        assertFalse(isCreateNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isCreateNotificationRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isGetNotificationRequest_test() {
        assertTrue(isGetNotificationRequest(getRequestGetNotification()));

        assertFalse(isGetNotificationRequest(getRequestDeleteNotification()));
        assertFalse(isGetNotificationRequest(getRequestCreateNotification()));
        assertFalse(isGetNotificationRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetNotificationRequest(getRequestBulk()));
        assertFalse(isGetNotificationRequest(getRequestMultiFileDelete()));
        assertFalse(isGetNotificationRequest(getRequestCreateObject()));
        assertFalse(isGetNotificationRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetNotificationRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isPhysicalPlacementRequest_test() {
        assertTrue(isPhysicalPlacementRequest(getRequestVerifyPhysicalPlacement()));

        assertFalse(isPhysicalPlacementRequest(getRequestDeleteNotification()));
        assertFalse(isPhysicalPlacementRequest(getRequestCreateNotification()));
        assertFalse(isPhysicalPlacementRequest(getRequestGetNotification()));
        assertFalse(isPhysicalPlacementRequest(getRequestBulk()));
        assertFalse(isPhysicalPlacementRequest(getRequestMultiFileDelete()));
        assertFalse(isPhysicalPlacementRequest(getRequestCreateObject()));
        assertFalse(isPhysicalPlacementRequest(getRequestAmazonS3GetObject()));
        assertFalse(isPhysicalPlacementRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isBulkRequest_test() {
        assertTrue(isBulkRequest(getRequestBulk()));

        assertFalse(isBulkRequest(getRequestDeleteNotification()));
        assertFalse(isBulkRequest(getRequestCreateNotification()));
        assertFalse(isBulkRequest(getRequestGetNotification()));
        assertFalse(isBulkRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isBulkRequest(getRequestMultiFileDelete()));
        assertFalse(isBulkRequest(getRequestCreateObject()));
        assertFalse(isBulkRequest(getRequestAmazonS3GetObject()));
        assertFalse(isBulkRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isMultiFileDeleteRequest_test() {
        assertTrue(isMultiFileDeleteRequest(getRequestMultiFileDelete()));

        assertFalse(isMultiFileDeleteRequest(getRequestDeleteNotification()));
        assertFalse(isMultiFileDeleteRequest(getRequestCreateNotification()));
        assertFalse(isMultiFileDeleteRequest(getRequestGetNotification()));
        assertFalse(isMultiFileDeleteRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isMultiFileDeleteRequest(getRequestBulk()));
        assertFalse(isMultiFileDeleteRequest(getRequestCreateObject()));
        assertFalse(isMultiFileDeleteRequest(getRequestAmazonS3GetObject()));
        assertFalse(isMultiFileDeleteRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isCreateObjectRequest_test() {
        assertTrue(isCreateObjectRequest(getRequestCreateObject()));

        assertFalse(isCreateObjectRequest(getRequestDeleteNotification()));
        assertFalse(isCreateObjectRequest(getRequestCreateNotification()));
        assertFalse(isCreateObjectRequest(getRequestGetNotification()));
        assertFalse(isCreateObjectRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isCreateObjectRequest(getRequestBulk()));
        assertFalse(isCreateObjectRequest(getRequestMultiFileDelete()));
        assertFalse(isCreateObjectRequest(getRequestAmazonS3GetObject()));
        assertFalse(isCreateObjectRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isGetObjectRequest_test() {
        assertTrue(isGetObjectRequest(getRequestAmazonS3GetObject()));
        assertTrue(isGetObjectRequest(getRequestSpectraS3GetObject()));

        assertFalse(isGetObjectRequest(getRequestDeleteNotification()));
        assertFalse(isGetObjectRequest(getRequestCreateNotification()));
        assertFalse(isGetObjectRequest(getRequestGetNotification()));
        assertFalse(isGetObjectRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectRequest(getRequestBulk()));
        assertFalse(isGetObjectRequest(getRequestMultiFileDelete()));
        assertFalse(isGetObjectRequest(getRequestCreateObject()));
    }

    @Test
    public void isGetObjectSpectraS3Request_test() {
        assertTrue(isGetObjectSpectraS3Request(getRequestSpectraS3GetObject()));

        assertFalse(isGetObjectSpectraS3Request(getRequestDeleteNotification()));
        assertFalse(isGetObjectSpectraS3Request(getRequestCreateNotification()));
        assertFalse(isGetObjectSpectraS3Request(getRequestGetNotification()));
        assertFalse(isGetObjectSpectraS3Request(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectSpectraS3Request(getRequestBulk()));
        assertFalse(isGetObjectSpectraS3Request(getRequestMultiFileDelete()));
        assertFalse(isGetObjectSpectraS3Request(getRequestCreateObject()));
        assertFalse(isGetObjectSpectraS3Request(getRequestAmazonS3GetObject()));
    }

    @Test
    public void isGetObjectAmazonS3Request_test() {
        assertTrue(isGetObjectAmazonS3Request(getRequestAmazonS3GetObject()));

        assertFalse(isGetObjectAmazonS3Request(getRequestDeleteNotification()));
        assertFalse(isGetObjectAmazonS3Request(getRequestCreateNotification()));
        assertFalse(isGetObjectAmazonS3Request(getRequestGetNotification()));
        assertFalse(isGetObjectAmazonS3Request(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetObjectAmazonS3Request(getRequestBulk()));
        assertFalse(isGetObjectAmazonS3Request(getRequestMultiFileDelete()));
        assertFalse(isGetObjectAmazonS3Request(getRequestCreateObject()));
        assertFalse(isGetObjectAmazonS3Request(getRequestSpectraS3GetObject()));
    }

    @Test
    public void isGetJobRequest_Test() {
        assertTrue(isGetJobRequest(getRequestGetJob()));

        assertFalse(isGetJobRequest(getRequestAmazonS3GetObject()));
        assertFalse(isGetJobRequest(getRequestDeleteNotification()));
        assertFalse(isGetJobRequest(getRequestCreateNotification()));
        assertFalse(isGetJobRequest(getRequestGetNotification()));
        assertFalse(isGetJobRequest(getRequestVerifyPhysicalPlacement()));
        assertFalse(isGetJobRequest(getRequestBulk()));
        assertFalse(isGetJobRequest(getRequestMultiFileDelete()));
        assertFalse(isGetJobRequest(getRequestCreateObject()));
        assertFalse(isGetJobRequest(getRequestSpectraS3GetObject()));
    }

    @Test
    public void paramListContainsParam_test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Name1", "Type1"),
                new Ds3Param("Name2", "Type2"));

        assertTrue(paramListContainsParam(params, "Name1", "Type1"));
        assertTrue(paramListContainsParam(params, "Name2", "Type2"));

        assertFalse(paramListContainsParam(params, "Name1", "Type2"));
        assertFalse(paramListContainsParam(params, "Name2", "Type1"));
        assertFalse(paramListContainsParam(null, "Name1", "Type1"));
    }
}
