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
        return isPhysicalPlacementRequest(ds3Request) || isEjectStorageDomainRequest(ds3Request);
    }

    /**
     * Determines if this request is an Eject Storage Domain (Blobs) request.
     */
    public static boolean isEjectStorageDomainRequest(final Ds3Request ds3Request) {
        return ds3Request.getAction() == Action.BULK_MODIFY
                && ds3Request.getHttpVerb() == HttpVerb.PUT
                && ds3Request.getOperation() == Operation.EJECT
                && ds3Request.getResource() == Resource.TAPE
                && ds3Request.getResourceType() == ResourceType.NON_SINGLETON
                && paramListContainsParam(ds3Request.getRequiredQueryParams(), "StorageDomainId", "java.util.UUID");
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
     * Determines if this request is a Bulk request
     * @param ds3Request A request
     * @return True if the request has an Operation of value START_BULK_GET or
     *         START_BULK_PUT. Else it returns false.
     */
    public static boolean isBulkRequest(final Ds3Request ds3Request) {
        return isBulkPutRequest(ds3Request) || isBulkGetRequest(ds3Request);
    }

    /**
     * Determines if the request is a Bulk Put request
     */
    public static boolean isBulkPutRequest(final Ds3Request ds3Request) {
        return ds3Request.getOperation() != null
                && ds3Request.getOperation() == Operation.START_BULK_PUT
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
                && !ds3Request.includeIdInPath()
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
     * Determines if the request is a Bulk Get request
     */
    public static boolean isBulkGetRequest(final Ds3Request ds3Request) {
        return ds3Request.getOperation() != null
                && ds3Request.getOperation() == Operation.START_BULK_GET;
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
                && request.includeIdInPath()
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
                && !request.includeIdInPath()
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
                && !request.includeIdInPath()
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
                && !request.includeIdInPath()
                && request.getResource() == Resource.OBJECT
                && request.getResourceType() == ResourceType.NON_SINGLETON
                && !paramListContainsParam(request.getRequiredQueryParams(), "FullDetails", "void");
    }
}
