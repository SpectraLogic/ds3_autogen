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

package com.spectralogic.ds3autogen.utils.test.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;

/**
 * This class provides utilities for testing the ds3-autogen-utils package
 */
public final class UtilTestHelper {

    private UtilTestHelper() { }

    /**
     * Creates the SpectraDs3 Delete Notification request DeleteJobCreatedNotificationRegistrationRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Delete Notification request
     */
    public static Ds3Request getRequestDeleteNotification() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.notification.DeleteJobCreatedNotificationRegistrationRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.DELETE,
                Resource.JOB_CREATED_NOTIFICATION_REGISTRATION,
                ResourceType.NON_SINGLETON,
                null,
                null, //Request has response codes in Contract, but they are currently omitted
                null,
                null
        );
    }

    /**
     * Creates the SpectraDs3 Create Notification request CreateJobCreatedNotificationRegistrationRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Create Notification request
     */
    public static Ds3Request getRequestCreateNotification() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.notification.CreateJobCreatedNotificationRegistrationRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.CREATE,
                Resource.JOB_CREATED_NOTIFICATION_REGISTRATION,
                ResourceType.NON_SINGLETON,
                null,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("Format", "com.spectralogic.util.http.HttpResponseFormatType"),
                        new Ds3Param("NamingConvention", "com.spectralogic.util.lang.NamingConventionType"),
                        new Ds3Param("NotificationHttpMethod", "com.spectralogic.util.http.RequestType")),
                ImmutableList.of(
                        new Ds3Param("NotificationEndPoint","java.lang.String"))
        );
    }

    /**
     * Creates the SpectraDs3 Get Notification request GetJobCompletedNotificationRegistrationRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Get Notification request
     */
    public static Ds3Request getRequestGetNotification() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.notification.GetJobCompletedNotificationRegistrationRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.SHOW,
                Resource.JOB_COMPLETED_NOTIFICATION_REGISTRATION,
                ResourceType.NON_SINGLETON,
                null,
                null, //Request has response codes in Contract, but they are currently omitted
                null,
                null
        );
    }

    /**
     * Creates the SpectraDs3 Physical Placement request VerifyPhysicalPlacementForObjectsRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Physical Placement request
     */
    public static Ds3Request getRequestPhysicalPlacement() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.VerifyPhysicalPlacementForObjectsRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.SHOW,
                Resource.BUCKET,
                ResourceType.NON_SINGLETON,
                Operation.VERIFY_PHYSICAL_PLACEMENT,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("FullDetails", "void"),
                        new Ds3Param("StorageDomainId", "java.util.UUID")),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType"))
        );
    }

    /**
     * Creates the SpectraDs3 Bulk request CreateGetJobRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Bulk request
     */
    public static Ds3Request getRequestBulk() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.CreateGetJobRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.MODIFY,
                Resource.BUCKET,
                ResourceType.NON_SINGLETON,
                Operation.START_BULK_GET,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("ChunkClientProcessingOrderGuarantee", "com.spectralogic.s3.common.dao.domain.ds3.JobChunkClientProcessingOrderGuarantee"),
                        new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority")),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType"))
        );
    }

    /**
     * Creates the AmazonS3 Multi File Delete request DeleteObjectsRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Multi File Delete request
     */
    public static Ds3Request getRequestMultiFileDelete() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.DeleteObjectsRequestHandler",
                HttpVerb.POST,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.NOT_ALLOWED,
                null,
                null,
                null,
                null,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("RollBack", "void")),
                ImmutableList.of(
                        new Ds3Param("Delete", "void"))
        );
    }

    /**
     * Creates the AmazonS3 Create Object request CreateObjectRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Create Object request
     */
    public static Ds3Request getRequestCreateObject() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.CreateObjectRequestHandler",
                HttpVerb.PUT,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("Job", "java.util.UUID"),
                        new Ds3Param("Offset", "long")),
                null
        );
    }

    /**
     * Creates the AmazonS3 Get Object request GetObjectRequestHandler
     * as described in the Contract, excluding the response codes
     * @return An AmazonS3 Get Object request
     */
    public static Ds3Request getRequestAmazonS3GetObject() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.GetObjectRequestHandler",
                HttpVerb.GET,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("Job", "java.util.UUID"),
                        new Ds3Param("Offset", "long")),
                null
        );
    }

    /**
     * Creates the SpectraDs3 Get Object request GetObjectRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A SpectraDs3 Get Object request
     */
    public static Ds3Request getRequestSpectraS3GetObject() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.GetObjectRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.SHOW,
                Resource.OBJECT,
                ResourceType.NON_SINGLETON,
                null,
                null, //Request has response codes in Contract, but they are currently omitted
                null,
                ImmutableList.of(
                        new Ds3Param("BucketId", "java.util.UUID"))
        );
    }
}
