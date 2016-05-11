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

package com.spectralogic.ds3autogen.testutil;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;

/**
 * This class provides a series of fixtures that auto-populate Ds3 Models.
 * This is used for testing
 */
public class Ds3ModelFixtures {

    /**
     * Creates a populated list of Ds3ResponseTypes with the ability to set a variation to append
     * to type and component type names to ensure name uniqueness.
     */
    public static ImmutableList<Ds3ResponseType> createPopulatedDs3ResponseTypeList(final String variation) {
        return ImmutableList.of(
                new Ds3ResponseType("SimpleType" + variation, null),
                new Ds3ResponseType("array", "SimpleComponentType" + variation),
                new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.tape.TapePartition" + variation, null),
                new Ds3ResponseType("array", "com.spectralogic.s3.common.dao.domain.ds3.BucketAcl" + variation));
    }

    /**
     * Creates a populated list of Ds3ResponseCodes utilizing the createPopulatedDs3ResponseTypeList
     * to generate some of the data.
     */
    public static ImmutableList<Ds3ResponseCode> createPopulatedDs3ResponseCodeList(
            final String firstVariation,
            final String secondVariation) {
        return ImmutableList.of(
                new Ds3ResponseCode(
                        200,
                        createPopulatedDs3ResponseTypeList(firstVariation)),
                new Ds3ResponseCode(
                        205,
                        createPopulatedDs3ResponseTypeList(secondVariation)));
    }

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
                true,
                ImmutableList.of(
                        new Ds3ResponseCode(
                                204,
                                ImmutableList.of(
                                        new Ds3ResponseType("null", null)))),
                null,
                null);
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
                false,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("Format", "com.spectralogic.util.http.HttpResponseFormatType", false),
                        new Ds3Param("NamingConvention", "com.spectralogic.util.lang.NamingConventionType", false),
                        new Ds3Param("NotificationHttpMethod", "com.spectralogic.util.http.RequestType", false)),
                ImmutableList.of(
                        new Ds3Param("NotificationEndPoint","java.lang.String", true)));
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
                true,
                null, //Request has response codes in Contract, but they are currently omitted
                null,
                null);
    }

    /**
     * Creates the SpectraDs3 Physical Placement request VerifyPhysicalPlacementForObjectsRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Physical Placement request
     */
    public static Ds3Request getRequestVerifyPhysicalPlacement() {
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
                true,
                ImmutableList.of(
                        new Ds3ResponseCode(
                                200,
                                ImmutableList.of(
                                        new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.PhysicalPlacementApiBean", null),
                                        new Ds3ResponseType("com.spectralogic.s3.common.platform.domain.BlobApiBeansContainer", null))),
                        new Ds3ResponseCode(
                                404,
                                ImmutableList.of(
                                        new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null)))),
                ImmutableList.of(
                        new Ds3Param("FullDetails", "void", false),
                        new Ds3Param("StorageDomainId", "java.util.UUID", false)),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false)));
    }

    /**
     * Creates the SpectraDs3 Bulk Get request CreateGetJobRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Bulk request
     */
    public static Ds3Request getRequestBulkGet() {
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
                true,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("ChunkClientProcessingOrderGuarantee", "com.spectralogic.s3.common.dao.domain.ds3.JobChunkClientProcessingOrderGuarantee", false),
                        new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false)),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false)));
    }

    /**
     * Creates the SpectraDs3 Bulk Put request CreatePutJobRequestHandler
     * as described in the Contract, excluding the response codes
     * @return A Bulk request
     */
    public static Ds3Request getRequestBulkPut() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.CreatePutJobRequestHandler",
                HttpVerb.PUT,
                Classification.spectrads3,
                null,
                null,
                Action.MODIFY,
                Resource.BUCKET,
                ResourceType.NON_SINGLETON,
                Operation.START_BULK_PUT,
                true,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("Aggregating", "boolean", false),
                        new Ds3Param("IgnoreNamingConflicts", "void", false),
                        new Ds3Param("MaxUploadSize", "long", false),
                        new Ds3Param("Name", "java.lang.String", true),
                        new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false)),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false)));
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
                false,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("RollBack", "void", false)),
                ImmutableList.of(
                        new Ds3Param("Delete", "void", false)));
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
                false,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("Job", "java.util.UUID", false),
                        new Ds3Param("Offset", "long", false)),
                null);
    }

    /**
     * Creates the AmazonS3 Get Object request GetObjectRequestHandler
     * as described in the Contract
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
                false,
                ImmutableList.of(
                        new Ds3ResponseCode(200,
                                ImmutableList.of(new Ds3ResponseType("null", null))),
                        new Ds3ResponseCode(206,
                                ImmutableList.of(new Ds3ResponseType("null", null))),
                        new Ds3ResponseCode(307,
                                ImmutableList.of(new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))),
                        new Ds3ResponseCode(400,
                                ImmutableList.of(new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))),
                        new Ds3ResponseCode(503,
                                ImmutableList.of(new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null)))),
                ImmutableList.of(
                        new Ds3Param("Job", "java.util.UUID", false),
                        new Ds3Param("Offset", "long", false)),
                null);
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
                true,
                null, //Request has response codes in Contract, but they are currently omitted
                null,
                ImmutableList.of(
                        new Ds3Param("BucketId", "java.util.UUID", false)));
    }

    /**
     * Creates the SpectraDs3 Get Job request GetJobRequestHandler as
     * described in the Contract, excluding the response codes
     * @return A SpectraDs3 GetJob request
     */
    public static Ds3Request getRequestGetJob() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.GetJobRequestHandler",
                HttpVerb.GET,
                Classification.spectrads3,
                null,
                null,
                Action.SHOW,
                Resource.JOB,
                ResourceType.NON_SINGLETON,
                null,
                true,
                ImmutableList.of(
                        new Ds3ResponseCode(
                                200,
                                ImmutableList.of(
                                        new Ds3ResponseType(
                                                "com.spectralogic.s3.server.domain.JobWithChunksApiBean", null)))),

                ImmutableList.of(
                        new Ds3Param("Aggregating", "boolean", false),
                        new Ds3Param("ChunkClientProcessingOrderGuarantee", "com.spectralogic.s3.common.dao.domain.ds3.JobChunkClientProcessingOrderGuarantee", false),
                        new Ds3Param("Name", "java.lang.String", true),
                        new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false)), // optionalQueryParams
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", true)));// requiredQueryParams
    }

    /**
     * Creates the SpectraDs3 Replicate Put Job request handler as
     * described in the Contract, excluding the response codes
     */
    public static Ds3Request getReplicatePutJob() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.ReplicatePutJobRequestHandler",
                HttpVerb.PUT,
                Classification.spectrads3,
                null,
                null,
                Action.MODIFY,
                Resource.BUCKET,
                ResourceType.NON_SINGLETON,
                Operation.START_BULK_PUT,
                true,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("ConflictResolutionMode", "com.spectralogic.s3.common.dao.domain.shared.ReplicationConflictResolutionMode", false),
                        new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false)),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false),
                        new Ds3Param("Replicate", "void", false)));
    }

    /**
     * Creates the SpectraDs3 Get Blob Persistence request handler as
     * described in the Contract
     */
    public static Ds3Request getGetBlobPersistence() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.GetBlobPersistenceRequestHandler",
                HttpVerb.GET,
                Classification.spectrads3,
                null,
                null,
                Action.LIST,
                Resource.BLOB_PERSISTENCE,
                ResourceType.NON_SINGLETON,
                null,
                false,
                ImmutableList.of(
                        new Ds3ResponseCode(
                                200,
                                ImmutableList.of(new Ds3ResponseType("java.lang.String", null)))),
                null,
                null);
    }

    /**
     * Creates the AmazonS3  CreateMultiPartUploadPartRequestHandler as
     * described in the contract, excluding the response codes
     */
    public static Ds3Request getCreateMultiPartUploadPart() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.CreateMultiPartUploadPartRequestHandler",
                HttpVerb.PUT,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                false,
                null, //Request has response codes in Contract, but they are currently omitted
                null,
                ImmutableList.of(
                        new Ds3Param("PartNumber", "int", false),
                        new Ds3Param("UploadId", "java.util.UUID", false)));
    }

    /**
     * Creates the SpectraDs3 Eject Storage Domain Request Handler as described
     * in the contract, excluding the response codes.
     */
    public static Ds3Request getEjectStorageDomainRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.EjectStorageDomainRequestHandler",
                HttpVerb.PUT,
                Classification.spectrads3,
                null,
                null,
                Action.BULK_MODIFY,
                Resource.TAPE,
                ResourceType.NON_SINGLETON,
                Operation.EJECT,
                false,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("BucketId", "java.util.UUID", false),
                        new Ds3Param("EjectLabel", "java.lang.String", true),
                        new Ds3Param("EjectLocation", "java.lang.String", true)),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false),
                        new Ds3Param("StorageDomainId", "java.util.UUID", false)));
    }

    /**
     * Creates the AmazonS3 Complete Multi Part Upload Request Handler as described
     * in the contract, excluding the response codes
     */
    public static Ds3Request getCompleteMultipartUploadRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.CompleteMultiPartUploadRequestHandler",
                HttpVerb.POST,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                false,
                null, //Request has response codes in Contract, but they are currently omitted
                null,
                ImmutableList.of(
                        new Ds3Param("UploadId", "java.util.UUID", false)));
    }

    /**
     * Gets the SpectraS3 Allocate Job Chunk Request Handler as described in the contract,
     * excluding the response codes
     */
    public static Ds3Request getAllocateJobChunkRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.AllocateJobChunkRequestHandler",
                HttpVerb.PUT,
                Classification.spectrads3,
                null,
                null,
                Action.MODIFY,
                Resource.JOB_CHUNK,
                ResourceType.NON_SINGLETON,
                Operation.ALLOCATE,
                true,
                null,
                null,
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false))
        );
    }

    /**
     * Gets the AmazonS3 Head Bucket Request Handler as described in the contract
     */
    public static Ds3Request getHeadBucketRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.HeadBucketRequestHandler",
                HttpVerb.HEAD,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.NOT_ALLOWED,
                null,
                null,
                null,
                null,
                false,
                ImmutableList.of(
                        new Ds3ResponseCode(200,
                                ImmutableList.of(new Ds3ResponseType("null", null))),
                        new Ds3ResponseCode(403,
                                ImmutableList.of(new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))),
                        new Ds3ResponseCode(404,
                                ImmutableList.of(new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null)))),
                null,
                null
        );
    }

    /**
     * Gets the SpectraS3 GetJobChunksReadyForClientProcessingRequest Handler as
     * described in the contract, excluding the response codes
     */
    public static Ds3Request getJobChunksReadyForClientProcessingRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.GetJobChunksReadyForClientProcessingRequestHandler",
                HttpVerb.GET,
                Classification.spectrads3,
                null,
                null,
                Action.LIST,
                Resource.JOB_CHUNK,
                ResourceType.NON_SINGLETON,
                null,
                false,
                null, //Request has response codes in Contract, but they are currently omitted
                ImmutableList.of(
                        new Ds3Param("PreferredNumberOfChunks", "int", true)),
                ImmutableList.of(
                        new Ds3Param("Job", "java.util.UUID", false))
        );
    }

    public static Ds3Request getBucketRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.bucket.GetBucketRequestHandler",
                HttpVerb.GET,
                Classification.amazons3,
                Requirement.REQUIRED, // bucketRequirement
                Requirement.NOT_ALLOWED, // objectRequirement
                null, // action
                null, // resource
                null, // resourceType
                null, // operation
                false,// includeIdInPath
                ImmutableList.of(
                        new Ds3ResponseCode(200,
                                ImmutableList.of(new Ds3ResponseType("com.spectralogic.s3.server.domain.ListBucketResult", null, null)))), // ds3ResponseCodes - using NameToMarshal
                ImmutableList.of(
                        new Ds3Param("Delimiter", "java.lang.String", true),
                        new Ds3Param("Marker", "java.lang.String", true),
                        new Ds3Param("MaxKeys", "int", false),
                        new Ds3Param("Prefix", "java.lang.String", true)), // optionalQueryParams
                ImmutableList.of()); // requiredQueryParams
    }

    public static Ds3Request getBucketsRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.bucket.GetBucketsRequestHandler",
                HttpVerb.GET,
                Classification.spectrads3,
                null, // bucketRequirement
                null, // objectRequirement
                Action.LIST, // action
                Resource.BUCKET, // resource
                ResourceType.NON_SINGLETON, // resourceType
                null, // operation
                false,// includeIdInPath
                null, // ds3ResponseCodes are in the Contract, but are currently omitted
                ImmutableList.of(
                        new Ds3Param("DataPolicyId", "java.util.UUID", false),
                        new Ds3Param("LastPage", "void", false),
                        new Ds3Param("Name", "java.lang.String", false),
                        new Ds3Param("PageLength", "int", false),
                        new Ds3Param("PageOffset", "int", false),
                        new Ds3Param("PageStartMarker", "java.util.UUID", false),
                        new Ds3Param("UserId", "java.util.UUID", false)), // optionalQueryParams
                ImmutableList.of()); // requiredQueryParams
    }

    public static Ds3Request getSystemInformationRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.system.GetSystemInformationRequestHandler",
                HttpVerb.GET,
                Classification.spectrads3,
                null, // bucketRequirement
                null, // objectRequirement
                Action.LIST, // action
                Resource.SYSTEM_INFORMATION, // resource
                ResourceType.SINGLETON, // resourceType
                null, // operation
                false,// includeIdInPath
                null, // ds3ResponseCodes are in the Contract, but are currently omitted
                ImmutableList.of(), // optionalQueryParams
                ImmutableList.of()); // requiredQueryParams
    }

    public static Ds3Request createBucketRequest() {
        return new Ds3Request("CreateBucketRequestHandler",
                HttpVerb.PUT,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.NOT_ALLOWED,
                null, // Action
                null, //Resource
                null, // ResourceType
                null, // Operation
                false, // includeIdInPath
                null, // ds3ResponseCodes,
                ImmutableList.of(), // optionalQueryParams
                ImmutableList.of());// requiredQueryParams
    }

    public static Ds3Request createBucketSpectraS3Request() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.bucket.CreateBucketRequestHandler",
                HttpVerb.POST,
                Classification.spectrads3,
                null, // bucketRequirement
                null, // objectRequirement
                Action.CREATE, // action
                Resource.BUCKET, // resource
                ResourceType.NON_SINGLETON, // resourceType
                null, // operation
                false,// includeIdInPath
                null, // ds3ResponseCodes
                ImmutableList.of(
                        new Ds3Param("DataPolicyId", "java.util.UUID", false),
                        new Ds3Param("UserId", "java.util.UUID", false)), // optionalQueryParams
                ImmutableList.of(new Ds3Param("Name", "java.lang.String", false)));
    }

    public static Ds3Request deleteBucketRequest() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.bucket.DeleteBucketRequestHandler",
                HttpVerb.DELETE,
                Classification.spectrads3,
                null,
                null,
                Action.DELETE,
                Resource.BUCKET, // resource
                ResourceType.NON_SINGLETON, // resourceType
                null, // operation
                true,// includeIdInPath
                null, // ds3ResponseCodes
                ImmutableList.of(
                        new Ds3Param("Force", "void", false)), // optional query params
                ImmutableList.of()); // required query params
    }

    public static Ds3Request exampleRequestWithOptionalAndRequiredBooleanQueryParam() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.bucket.ExampleRequestHandler",
                HttpVerb.GET,
                Classification.spectrads3,
                null, // bucketRequirement
                null, // objectRequirement
                Action.LIST, // action
                Resource.BUCKET, // resource
                ResourceType.NON_SINGLETON, // resourceType
                null, // operation
                false,// includeIdInPath
                null, // ds3ResponseCodes
                ImmutableList.of(new Ds3Param("OptionalBool", "void", false)), // optionalQueryParams
                ImmutableList.of(new Ds3Param("RequiredBool", "void", false))); // requiredQueryParams
    }
}
