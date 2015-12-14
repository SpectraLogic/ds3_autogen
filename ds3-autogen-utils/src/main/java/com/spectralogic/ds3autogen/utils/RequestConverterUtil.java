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

public class RequestConverterUtil {

    public static boolean isNotificationRequest(final Ds3Request ds3Request) {
        return ds3Request.getResource() != null && isResourceNotification(ds3Request.getResource());
    }

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

    public static boolean isResourceAnArg(final Resource resource, final ResourceType resourceType) {
        return resource != null
                && resourceType != null
                && resourceType == ResourceType.NON_SINGLETON
                && !isResourceNotification(resource);
    }

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

    private static boolean isResourceNamed(final Resource resource) {
        switch (resource) {
            case BUCKET:
            case OBJECT:
                return true;
            default:
                return false;
        }
    }

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
}
