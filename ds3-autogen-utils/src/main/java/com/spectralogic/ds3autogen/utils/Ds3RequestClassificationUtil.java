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
import com.spectralogic.ds3autogen.api.models.*;
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
        return ds3Request.getOperation() != null
                && (ds3Request.getOperation() == Operation.START_BULK_GET
                        || ds3Request.getOperation() == Operation.START_BULK_PUT);
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
     * Determines if this request is a Get Object request
     * @param ds3Request A request
     * @return True if the request is a Get Object request, else false
     */
    public static boolean isGetObjectRequest(final Ds3Request ds3Request) {
        return isGetObjectSpectraS3Request(ds3Request) || isGetObjectAmazonS3Request(ds3Request);
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
    protected static boolean isGetObjectAmazonS3Request(final Ds3Request ds3Request) {
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
}