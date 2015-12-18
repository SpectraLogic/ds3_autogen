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

/**
 * Series of static utility functions to help in converting Ds3Requests
 * to language specific request models
 */
public final class RequestConverterUtil {

    private RequestConverterUtil() { }

    /**
     * Converts the Action of a Ds3Request into a NotificationType
     */
    public static NotificationType getNotificationType(final Ds3Request ds3Request) {
        switch (ds3Request.getAction()) {
            case BULK_MODIFY:
            case CREATE:
            case MODIFY:
                return NotificationType.CREATE;
            case DELETE:
                return NotificationType.DELETE;
            case LIST:
            case SHOW:
                return NotificationType.GET;
            default:
                return NotificationType.NONE;
        }
    }

    /**
     * Retrieves a list of required arguments that are described in the Contract's
     * Request Handlers header information. This includes BucketName, ObjectName,
     * and arguments described by Resource/ResourceType.
     */
    public static ImmutableList<Arguments> getRequiredArgsFromRequestHeader(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        if (ds3Request.getBucketRequirement() != null
                && ds3Request.getBucketRequirement().equals(Requirement.REQUIRED)) {
            builder.add(new Arguments("String", "BucketName"));
        }
        if (ds3Request.getObjectRequirement() != null
                && ds3Request.getObjectRequirement().equals(Requirement.REQUIRED)) {
            builder.add(new Arguments("String", "ObjectName"));
        }
        if (isResourceAnArg(ds3Request.getResource(), ds3Request.getResourceType())) {
            builder.add(getArgFromResource(ds3Request.getResource()));
        }
        return builder.build();
    }

    /**
     * Determines if a Ds3Request's Resource/ResourceType combination describes a
     * required argument or not.
     */
    public static boolean isResourceAnArg(final Resource resource, final ResourceType resourceType) {
        return resource != null
                && resourceType == ResourceType.NON_SINGLETON
                && !isResourceNotification(resource);
    }

    /**
     * Creates an argument from a resource. If the resource does not describe a valid argument,
     * such as it is a singleton or notification resource, an error is thrown.
     * @param resource A Ds3Request's Resource
     */
    public static Arguments getArgFromResource(final Resource resource) {
        if (isResourceSingleton(resource)) {
            throw new IllegalArgumentException("Cannot create an argument from a singleton resource: " + resource.toString());
        }
        if (isResourceNotification(resource)) {
            throw new IllegalArgumentException("Cannot create an argument from a notification resource: " + resource.toString());
        }
        if (isResourceNamed(resource)) {
            return new Arguments("String", Helper.underscoreToCamel(resource.toString()) + "Name");
        }
        if (isResourceId(resource)) {
            return new Arguments("UUID", Helper.underscoreToCamel(resource.toString()) + "Id");
        }
        return new Arguments("String", Helper.underscoreToCamel(resource.toString()));
    }

    /**
     * Determines if a resource describes an argument of type UUID
     */
    public static boolean isResourceId(final Resource resource) {
        switch (resource) {
            case ACTIVE_JOB:
            case JOB:
            case JOB_CHUNK:
            case TAPE:
            case TAPE_DRIVE:
            case TAPE_LIBRARY:
            case USER:
                return true;
        }
        return false;
    }

    /**
     * Determines if a resource describes an argument of type String and whose
     * name should be name spaced to include "Name". For example, Resource.BUCKET
     * describes an argument whose name should be "BucketName" with type String.
     */
    private static boolean isResourceNamed(final Resource resource) {
        switch (resource) {
            case BUCKET:
            case OBJECT:
                return true;
            default:
                return false;
        }
    }

    /**
     * Determines if a resource describes a singleton, meaning that this resource
     * does not describe a valid argument
     */
    private static boolean isResourceSingleton(final Resource resource) {
        switch (resource) {
            case CAPACITY_SUMMARY:
            case DATA_PATH:
            case DATA_PATH_BACKEND:
            case POOL_ENVIRONMENT:
            case SYSTEM_HEALTH:
            case SYSTEM_INFORMATION:
            case TAPE_ENVIRONMENT:
                return true;
            default:
                return false;
        }
    }

    /**
     * Determines if a resource describes a notification request. This has the additional
     * meaning that this resource does not describe a valid argument.
     */
    public static boolean isResourceNotification(final Resource resource) {
        switch (resource) {
            case GENERIC_DAO_NOTIFICATION_REGISTRATION:
            case JOB_COMPLETED_NOTIFICATION_REGISTRATION:
            case OBJECT_CACHED_NOTIFICATION_REGISTRATION:
            case OBJECT_LOST_NOTIFICATION_REGISTRATION:
            case OBJECT_PERSISTED_NOTIFICATION_REGISTRATION:
            case POOL_FAILURE_NOTIFICATION_REGISTRATION:
            case STORAGE_DOMAIN_FAILURE_NOTIFICATION_REGISTRATION:
            case SYSTEM_FAILURE_NOTIFICATION_REGISTRATION:
            case TAPE_FAILURE_NOTIFICATION_REGISTRATION:
            case TAPE_PARTITION_FAILURE_NOTIFICATION_REGISTRATION:
            case JOB_CREATED_NOTIFICATION_REGISTRATION:
                return true;
            default:
                return false;
        }
    }
}
