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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.*;
import com.spectralogic.ds3autogen.utils.models.NotificationType;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.enumsEqual;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getNotificationType;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.isResourceNotification;

public final class Ds3RequestClassificationUtil {

    private Ds3RequestClassificationUtil() { }

    /**
     * Determines if this request is a Notification request
     * @param ds3Request A request
     * @return True if the request is a Notification, else false
     */
    public static boolean isNotificationRequest(final Ds3Request ds3Request) {
        return ds3Request.getResource() != null
                && isResourceNotification(ds3Request.getResource());
    }

    /**
     * Determines if this request is a Notification request with a DELETE NotificationType
     * @param ds3Request A request
     * @return True if the request is a Notification and it is a DELETE NotificationType,
     *         else false
     */
    public static boolean isDeleteNotificationRequest(final Ds3Request ds3Request) {
        return isNotificationRequest(ds3Request)
                && getNotificationType(ds3Request) == NotificationType.DELETE;
    }

    /**
     * Determines if this request is a Notification request with a CREATE NotificationType
     * @param ds3Request A request
     * @return True if the request is a Notification and it is a CREATE NotificationType,
     *         else false
     */
    public static boolean isCreateNotificationRequest(final Ds3Request ds3Request) {
        return isNotificationRequest(ds3Request)
                && getNotificationType(ds3Request) == NotificationType.CREATE;
    }

    /**
     * Determines if this request is a Notification request with a GET NotificationType
     * @param ds3Request A request
     * @return True if the request is a Notification and it is a GET NotificationType,
     *         else false
     */
    public static boolean isGetNotificationRequest(final Ds3Request ds3Request) {
        return isNotificationRequest(ds3Request)
                && getNotificationType(ds3Request) == NotificationType.GET;
    }

    /**
     * Determines if this request has a List of Ds3Object request payload
     */
    public static boolean hasListObjectsRequestPayload(final Ds3Request ds3Request) {
        return isPhysicalPlacementRequest(ds3Request) || isEjectStorageDomainBlobsRequest(ds3Request);
    }

    /**
     * Determines if this request is the Spectra S3 EjectStorageDomainBlobsRequest
     */
    public static boolean isEjectStorageDomainBlobsRequest(final Ds3Request ds3Request) {
        return ds3Request.getAction() == Action.BULK_MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && !ds3Request.getIncludeInPath()
                && ds3Request.getOperation() == Operation.EJECT
                && ds3Request.getResource() == Resource.TAPE
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON
                && paramListContainsParam(ds3Request.getRequiredQueryParams(), "StorageDomain", "java.lang.String")
                && paramListContainsParam(ds3Request.getRequiredQueryParams(), "Blobs", "void");
    }

    /**
     * Determines if this request is a Physical Placement request
     * @param ds3Request A request
     * @return True if the request has an Operation of value GET_PHYSICAL_PLACEMENT,
     *         VERIFY_PHYSICAL_PLACEMENT, or START_BULK_VERIFY. Else it returns false.
     */
    public static boolean isPhysicalPlacementRequest(final Ds3Request ds3Request) {
        return ds3Request.getOperation() != null
                && (ds3Request.getOperation() == Operation.GET_PHYSICAL_PLACEMENT
                        || ds3Request.getOperation() == Operation.VERIFY_PHYSICAL_PLACEMENT
                        || ds3Request.getOperation() == Operation.START_BULK_VERIFY);
    }

    /**
     * Determines if this request is SpectraS3 StageObjectsJobRequestHandler
     */
    public static boolean isStageObjectsJob(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getIncludeInPath()
                && ds3Request.getOperation() == Operation.START_BULK_STAGE
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if this request is a Bulk request
     * @param ds3Request A request
     * @return True if the request has an Operation of value START_BULK_GET or
     *         START_BULK_PUT. Else it returns false.
     */
    public static boolean isBulkRequest(final Ds3Request ds3Request) {
        return isBulkPutRequest(ds3Request) || isBulkGetRequest(ds3Request);
    }

    /**
     * Determines if the request is a CreatePutJobRequestHandler also known as BulkPutJobSpectraS3Request
     */
    public static boolean isBulkPutRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getOperation() == Operation.START_BULK_PUT
                && ds3Request.getAction() == Action.MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON
                && !paramListContainsParam(ds3Request.getRequiredQueryParams(), "Replicate", "void");
    }

    /**
     * Determines if the request handler should have a payload of type String
     */
    public static boolean hasStringRequestPayload(final Ds3Request ds3Request) {
        return isBulkReplicateRequest(ds3Request) || isGetBlobPersistenceRequest(ds3Request);
    }

    /**
     * Determines if the request is a Get Blob Persistence Request
     */
    public static boolean isGetBlobPersistenceRequest(final Ds3Request ds3Request) {
        return ds3Request.getAction() == Action.LIST
                && ds3Request.getHttpVerb() == HttpVerb.GET
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.BLOB_PERSISTENCE
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if the request is a Bulk Replicate request
     */
    public static boolean isBulkReplicateRequest(final Ds3Request ds3Request) {
        return ds3Request.getOperation() != null
                && ds3Request.getOperation() == Operation.START_BULK_PUT
                && paramListContainsParam(ds3Request.getRequiredQueryParams(), "Replicate", "void");
    }

    /**
     * Determines if the request is a CreateGetJobRequestHandler also known as GetBulkJobSpectraS3Request
     */
    public static boolean isBulkGetRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getOperation() == Operation.START_BULK_GET
                && ds3Request.getAction() == Action.MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if this request is Multi File Delete request
     * @param ds3Request A request
     * @return True if the request is a Multi File Delete request, else false
     */
    public static boolean isMultiFileDeleteRequest(final Ds3Request ds3Request) {
        return ds3Request.getHttpVerb() == HttpVerb.POST
                && ds3Request.getObjectRequirement() != null
                && ds3Request.getObjectRequirement() == Requirement.NOT_ALLOWED
                && paramListContainsParam(ds3Request.getRequiredQueryParams(), "Delete", "void");
    }

    /**
     * Determines if this request is a Create Object request
     * @param ds3Request A request
     * @return True if the request is a Create Object request, else false
     */
    public static boolean isCreateObjectRequest(final Ds3Request ds3Request) {
        return ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getBucketRequirement() == Requirement.REQUIRED
                && ds3Request.getObjectRequirement() == Requirement.REQUIRED
                && !paramListContainsParam(ds3Request.getRequiredQueryParams(), "PartNumber", "int");
    }

    /**
     * Determines if this request is an AmazonS3 Crate Object request
     */
    public static boolean isAmazonCreateObjectRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.amazons3
                && isCreateObjectRequest(ds3Request);
    }

    /**
     * Determines if this request is an Amazon Get Object request
     */
    public static boolean isGetObjectRequest(final Ds3Request ds3Request) {
        return isGetObjectAmazonS3Request(ds3Request);
    }

    /**
     * Determines if this request is a SpectraDs3 Get Object request
     * @param ds3Request A request
     * @return True if the request is a SpectraDs3 Get Object request, else false
     */
    protected static boolean isGetObjectSpectraS3Request(final Ds3Request ds3Request) {
        return enumsEqual(ds3Request.getClassification(), Classification.spectrads3)
                && enumsEqual(ds3Request.getAction(), Action.SHOW)
                && enumsEqual(ds3Request.getResource(), Resource.OBJECT)
                && enumsEqual(ds3Request.getResourceType(), ResourceType.NON_SINGLETON);
    }

    /**
     * Determines if this request is an AmazonS3 Get Object request
     * @param ds3Request A request
     * @return True if the request is an AmazonS3 Get Object request, else false
     */
    public static boolean isGetObjectAmazonS3Request(final Ds3Request ds3Request) {
        return enumsEqual(ds3Request.getClassification(), Classification.amazons3)
                && enumsEqual(ds3Request.getHttpVerb(), HttpVerb.GET)
                && enumsEqual(ds3Request.getBucketRequirement(), Requirement.REQUIRED)
                && enumsEqual(ds3Request.getObjectRequirement(), Requirement.REQUIRED)
                && !paramListContainsParam(ds3Request.getRequiredQueryParams(), "UploadId", "java.util.UUID");
    }

    /**
     * Determines if an immutable parameter list contains a given parameter
     * @param params ImmutableList of parameters
     * @param paramName Name of the parameter which is being checked for
     * @param paramType Type of parameter which is being checked for
     * @return True if the parameter list contains a parameter with both the
     *         specified name and type. Else it returns false.
     */
    protected static boolean paramListContainsParam(
            final ImmutableList<Ds3Param> params,
            final String paramName,
            final String paramType) {
        if (isEmpty(params)) {
            return false;
        }
        for (final Ds3Param param : params) {
            if (param.getName().equalsIgnoreCase(paramName)
                    && param.getType().equalsIgnoreCase(paramType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if a Ds3Request is Head Bucket Request
     */
    public static boolean isHeadBucketRequest(final Ds3Request request) {
        return request.getClassification() == Classification.amazons3
                && request.getBucketRequirement() == Requirement.REQUIRED
                && request.getHttpVerb() == HttpVerb.HEAD
                && request.getObjectRequirement() == Requirement.NOT_ALLOWED;
    }

    /**
     * Determines if a Ds3Request is Head Object Request
     */
    public static boolean isHeadObjectRequest(final Ds3Request request) {
        return request.getClassification() == Classification.amazons3
                && request.getBucketRequirement() == Requirement.REQUIRED
                && request.getHttpVerb() == HttpVerb.HEAD
                && request.getObjectRequirement() == Requirement.REQUIRED;
    }

    /**
     * Determines if a Ds3Request is Allocate Job Chunk Request
     */
    public static boolean isAllocateJobChunkRequest(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getResource() == Resource.JOB_CHUNK
                && request.getAction() == Action.MODIFY
                && request.getHttpVerb() == HttpVerb.PUT
                && request.getOperation() == Operation.ALLOCATE;
    }

    /**
     * Determines if a Ds3Request is Get Job Chunks Ready For Client Processing Request
     */
    public static boolean isGetJobChunksReadyForClientProcessingRequest(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getResource() == Resource.JOB_CHUNK
                && request.getAction() == Action.LIST
                && request.getHttpVerb() == HttpVerb.GET;
    }

    /**
     * Determines if a Ds3Request is the SpectraS3 Get Job Request
     */
    public static boolean isGetJobRequest(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getAction() == Action.SHOW
                && request.getHttpVerb() == HttpVerb.GET
                && request.getIncludeInPath()
                && request.getResource() == Resource.JOB
                && request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the AmazonS3 Create Multi Part Upload Part Request
     */
    public static boolean isCreateMultiPartUploadPartRequest(final Ds3Request request) {
        return request.getClassification() == Classification.amazons3
                && request.getBucketRequirement() == Requirement.REQUIRED
                && request.getObjectRequirement() == Requirement.REQUIRED
                && request.getHttpVerb() == HttpVerb.PUT
                && !request.getIncludeInPath()
                && paramListContainsParam(request.getRequiredQueryParams(), "UploadId", "java.util.UUID")
                && paramListContainsParam(request.getRequiredQueryParams(), "PartNumber", "int");
    }

    /**
     * Determines if a Ds3Request is the AmazonS3 Complete Multi Part Upload request
     */
    public static boolean isCompleteMultiPartUploadRequest(final Ds3Request request) {
        return request.getClassification() == Classification.amazons3
                && request.getBucketRequirement() == Requirement.REQUIRED
                && request.getHttpVerb() == HttpVerb.POST
                && !request.getIncludeInPath()
                && request.getObjectRequirement() == Requirement.REQUIRED
                && isEmpty(request.getOptionalQueryParams())
                && paramListContainsParam(request.getRequiredQueryParams(), "UploadId", "java.util.UUID");
    }

    /**
     * Determines if a Ds3Request is the SpectraS3 Get Objects Details request
     */
    public static boolean isGetObjectsDetailsRequest(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getHttpVerb() == HttpVerb.GET
                && !request.getIncludeInPath()
                && request.getResource() == Resource.OBJECT
                && request.getResourceType() == ResourceType.NON_SINGLETON
                && !paramListContainsParam(request.getRequiredQueryParams(), "FullDetails", "void");
    }

    /**
     * Determines if a Ds3Request is the SpectraS3 Get Users request
     */
    public static boolean isGetUsersSpectraS3Request(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getHttpVerb() == HttpVerb.GET
                && !request.getIncludeInPath()
                && request.getResource() == Resource.USER
                && request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is a SpectraS3 Get Objects With Full Details
     */
    public static boolean isGetObjectsWithFullDetails(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getHttpVerb() == HttpVerb.GET
                && !request.getIncludeInPath()
                && request.getResource() == Resource.OBJECT
                && request.getResourceType() == ResourceType.NON_SINGLETON
                && paramListContainsParam(request.getRequiredQueryParams(), "FullDetails", "void");
    }

    /**
     * Determines if a Ds3Request supports pagination. This is used to determine
     * which response handlers need to parse pagination headers.
     */
    public static boolean supportsPaginationRequest(final Ds3Request request) {
        return paramListContainsParam(request.getOptionalQueryParams(), "LastPage", "void")
                && paramListContainsParam(request.getOptionalQueryParams(), "PageLength", "int")
                && paramListContainsParam(request.getOptionalQueryParams(), "PageOffset", "int")
                && paramListContainsParam(request.getOptionalQueryParams(), "PageStartMarker", "java.util.UUID");
    }

    /**
     * Determines if a Ds3Request has the payload: <Ids><id>id1</id><id>id2</id>...</Ids>
     *
     * @return true if request is one of the following:
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.ClearSuspectBlobAzureTargetsRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.ClearSuspectBlobPoolsRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.ClearSuspectBlobS3TargetsRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.ClearSuspectBlobTapesRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.MarkSuspectBlobAzureTargetsAsDegradedRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.MarkSuspectBlobDs3TargetsAsDegradedRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.MarkSuspectBlobPoolsAsDegradedRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.MarkSuspectBlobS3TargetsAsDegradedRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.degradation.MarkSuspectBlobTapesAsDegradedRequestHandler
     */
    public static boolean hasIdsRequestPayload(final Ds3Request ds3Request) {
        return isClearSuspectBlobAzureTargetsRequest(ds3Request)
                || isClearSuspectBlobPoolsRequest(ds3Request)
                || isClearSuspectBlobDs3TargetsRequest(ds3Request)
                || isClearSuspectBlobS3TargetsRequest(ds3Request)
                || isClearSuspectBlobTapesRequest(ds3Request)
                || isMarkSuspectBlobAzureTargetsAsDegradedRequest(ds3Request)
                || isMarkSuspectBlobDs3TargetsAsDegradedRequest(ds3Request)
                || isMarkSuspectBlobPoolsAsDegradedRequest(ds3Request)
                || isMarkSuspectBlobS3TargetsAsDegradedRequest(ds3Request)
                || isMarkSuspectBlobTapesAsDegradedRequest(ds3Request);
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command ClearSuspectBlobDs3TargetsRequestHandler
     */
    static boolean isClearSuspectBlobDs3TargetsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_DELETE
                && ds3Request.getHttpVerb() == HttpVerb.DELETE
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_DS3_TARGET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command ClearSuspectBlobAzureTargetsRequestHandler
     */
    static boolean isClearSuspectBlobAzureTargetsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_DELETE
                && ds3Request.getHttpVerb() == HttpVerb.DELETE
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_AZURE_TARGET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command ClearSuspectBlobPoolsRequestHandler
     */
    static boolean isClearSuspectBlobPoolsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_DELETE
                && ds3Request.getHttpVerb() == HttpVerb.DELETE
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_POOL
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command ClearSuspectBlobS3TargetsRequestHandler
     */
    static boolean isClearSuspectBlobS3TargetsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_DELETE
                && ds3Request.getHttpVerb() == HttpVerb.DELETE
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_S3_TARGET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command ClearSuspectBlobTapesRequestHandler
     */
    static boolean isClearSuspectBlobTapesRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_DELETE
                && ds3Request.getHttpVerb() == HttpVerb.DELETE
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_TAPE
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command MarkSuspectBlobAzureTargetsAsDegradedRequestHandler
     */
    static boolean isMarkSuspectBlobAzureTargetsAsDegradedRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_AZURE_TARGET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command MarkSuspectBlobDs3TargetsAsDegradedRequestHandler
     */
    static boolean isMarkSuspectBlobDs3TargetsAsDegradedRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_DS3_TARGET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command MarkSuspectBlobPoolsAsDegradedRequestHandler
     */
    static boolean isMarkSuspectBlobPoolsAsDegradedRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_POOL
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command MarkSuspectBlobS3TargetsAsDegradedRequestHandler
     */
    static boolean isMarkSuspectBlobS3TargetsAsDegradedRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_S3_TARGET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command MarkSuspectBlobTapesAsDegradedRequestHandler
     */
    static boolean isMarkSuspectBlobTapesAsDegradedRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.BULK_MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && !ds3Request.getIncludeInPath()
                && ds3Request.getResource() == Resource.SUSPECT_BLOB_TAPE
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request has the payload:
     * <Objects><Object Name="o1" Size="1" /><Object Name="o2" Size="2" />...</Objects>
     *
     * @return true if request is one of the following:
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.CreatePutJobRequestHandler
     */
    public static boolean hasPutObjectsWithSizeRequestPayload(final Ds3Request ds3Request) {
        return isBulkPutRequest(ds3Request);
    }

    /**
     * Determines if a Ds3Request has the payload:
     * <Objects><Object Name="o1" Length="1" Offset="2" /><Object Name="o2" Length="3" Offset="4" />...</Objects>
     *
     * @return true if request is one of the following:
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.CreateGetJobRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.CreateVerifyJobRequestHandler
     */
    public static boolean hasGetObjectsWithLengthOffsetRequestPayload(final Ds3Request ds3Request) {
        return isBulkGetRequest(ds3Request) || isCreateVerifyJobRequest(ds3Request);
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command CreateVerifyJobRequest also known
     * as VerifyBulkJobSpectraS3Request
     */
    public static boolean isCreateVerifyJobRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getIncludeInPath()
                && ds3Request.getOperation() == Operation.START_BULK_VERIFY
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON;
    }

    /**
     * Determines if a Ds3Request has the request payload:
     * <Objects><Object Name="o1" /><Object Name="o2" />...</Objects>
     *
     * @return true if request is one of the following:
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.GetPhysicalPlacementForObjectsRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.GetPhysicalPlacementForObjectsWithFullDetailsRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.VerifyPhysicalPlacementForObjectsRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.VerifyPhysicalPlacementForObjectsWithFullDetailsRequestHandler
     *   com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.EjectStorageDomainBlobsRequestHandler
     */
    public static boolean hasSimpleObjectsRequestPayload(final Ds3Request ds3Request) {
        return isGetPhysicalPlacementForObjectsRequest(ds3Request)
                || isGetPhysicalPlacementForObjectsWithFullDetailsRequest(ds3Request)
                || isVerifyPhysicalPlacementForObjectsRequest(ds3Request)
                || isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(ds3Request)
                || isEjectStorageDomainBlobsRequest(ds3Request);
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command GetPhysicalPlacementForObjectsRequest
     */
    static boolean isGetPhysicalPlacementForObjectsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getIncludeInPath()
                && ds3Request.getOperation() == Operation.GET_PHYSICAL_PLACEMENT
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON
                && !paramListContainsParam(ds3Request.getRequiredQueryParams(), "FullDetails", "void");
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command GetPhysicalPlacementForObjectsWithFullDetailsRequest
     */
    static boolean isGetPhysicalPlacementForObjectsWithFullDetailsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getIncludeInPath()
                && ds3Request.getOperation() == Operation.GET_PHYSICAL_PLACEMENT
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON
                && paramListContainsParam(ds3Request.getRequiredQueryParams(), "FullDetails", "void");
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command VerifyPhysicalPlacementForObjectsRequest
     */
    static boolean isVerifyPhysicalPlacementForObjectsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.SHOW
                && ds3Request.getHttpVerb() == HttpVerb.GET
                && ds3Request.getIncludeInPath()
                && ds3Request.getOperation() == Operation.VERIFY_PHYSICAL_PLACEMENT
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON
                && !paramListContainsParam(ds3Request.getRequiredQueryParams(), "FullDetails", "void");
    }

    /**
     * Determines if a Ds3Request is the SpectraDs3 command VerifyPhysicalPlacementForObjectsWithFullDetailsRequest
     */
    static boolean isVerifyPhysicalPlacementForObjectsWithFullDetailsRequest(final Ds3Request ds3Request) {
        return ds3Request.getClassification() == Classification.spectrads3
                && ds3Request.getAction() == Action.SHOW
                && ds3Request.getHttpVerb() == HttpVerb.GET
                && ds3Request.getIncludeInPath()
                && ds3Request.getOperation() == Operation.VERIFY_PHYSICAL_PLACEMENT
                && ds3Request.getResource() == Resource.BUCKET
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON
                && paramListContainsParam(ds3Request.getRequiredQueryParams(), "FullDetails", "void");
    }
}
