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

package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.java.models.NotificationType;
import com.spectralogic.ds3autogen.java.models.Request;

public class RequestConverter {

    private final static String MODELS_PACKAGE = "com.spectralogic.ds3client.models.";

    private final Ds3Request ds3Request;
    private final String packageName;
    private final ImmutableList<Arguments> requiredConstructorArguments;
    private final ImmutableList<Arguments> optionalArguments;
    private final ImmutableList<String> imports;

    private RequestConverter(
            final Ds3Request ds3Request,
            final String packageName) {
        this.ds3Request = ds3Request;
        this.packageName = packageName;
        this.requiredConstructorArguments = getRequiredArgs(ds3Request);
        this.optionalArguments = getOptionalArgs(ds3Request);
        this.imports = getImports(ds3Request);
    }

    private Request convert() {
        final String[] classParts = ds3Request.getName().split("\\.");

        return new Request(packageName,
                classParts[classParts.length - 1],
                ds3Request.getHttpVerb(),
                requestPath(ds3Request),
                ds3Request.getOperation(),
                ds3Request.getAction(),
                requiredConstructorArguments,
                optionalArguments,
                imports);
    }

    public static Request toRequest(
            final Ds3Request ds3Request,
            final String packageName) {
        final RequestConverter converter = new RequestConverter(ds3Request, packageName);

        return converter.convert();
    }

    private static String requestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();

        if (ds3Request.getClassification() == Classification.amazons3) {
            builder.append("\"/\"");
            if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
                builder.append(" + this.bucketName");

                if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
                    builder.append(" + \"/\" + this.objectName");
                }
            }
        } else {
            if (ds3Request.getResource() != null) {
                builder.append("\"/_rest_/" + ds3Request.getResource().toString().toLowerCase() + "/\"");
                if (isNotificationRequest(ds3Request)) {
                    if (getNotificationType(ds3Request) == NotificationType.DELETE
                            || getNotificationType(ds3Request) == NotificationType.GET) {
                        builder.append(" + this.getNotificationId().toString()");
                    }
                } else if (!queryParamsContain(ds3Request.getRequiredQueryParams(), "Name")) {
                    builder.append(" + this.bucketName");
                }
            } else {
                builder.append("\"/_rest_/\"");
            }
        }

        return builder.toString();
    }

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

    public static boolean isNotificationRequest(final Ds3Request ds3Request) {
        if (ds3Request.getResource() == null) {
            return false;
        }
        switch (ds3Request.getResource()) {
            case GENERIC_DAO_NOTIFICATION_REGISTRATION:
            case JOB_COMPLETED_NOTIFICATION_REGISTRATION:
            case OBJECT_CACHED_NOTIFICATION_REGISTRATION:
            case OBJECT_LOST_NOTIFICATION_REGISTRATION:
            case OBJECT_PERSISTED_NOTIFICATION_REGISTRATION:
            case POOL_FAILURE_NOTIFICATION_REGISTRATION:
            case STORAGE_DOMAIN_FAILURE_NOTIFICATION_REGISTRATION:
            case TAPE_FAILURE_NOTIFICATION_REGISTRATION:
            case TAPE_PARTITION_FAILURE_NOTIFICATION_REGISTRATION:
            case JOB_CREATED_NOTIFICATION_REGISTRATION:
                return true;
            default:
                return false;
        }
    }

    private static boolean queryParamsContain(
            final ImmutableList<Ds3Param> params,
            final String name) {
        if (params == null) {
            return false;
        }
        for (final Ds3Param param : params) {
            if (param.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private static ImmutableList<Arguments> getRequiredArgs(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();

        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED
                || ds3Request.getResource() == Resource.BUCKET) {
            requiredArgs.add(new Arguments("String", "BucketName"));
            if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
                requiredArgs.add(new Arguments("String", "ObjectName"));
            }
        }

        requiredArgs.addAll(getArgsFromParamList(ds3Request.getRequiredQueryParams()));
        return requiredArgs.build();
    }

    private static ImmutableList<Arguments> getOptionalArgs(
            final Ds3Request ds3Request) {
        if (ds3Request.getOptionalQueryParams() == null) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> optionalArgs = ImmutableList.builder();
        optionalArgs.addAll(getArgsFromParamList(ds3Request.getOptionalQueryParams()));
        return optionalArgs.build();
    }

    private static ImmutableList<Arguments> getArgsFromParamList(final ImmutableList<Ds3Param> paramList) {
        if(paramList == null) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> argsBuilder = ImmutableList.builder();
        for (final Ds3Param ds3Param : paramList) {
            if (!ds3Param.getName().equals("Operation")) {
                final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
                argsBuilder.add(new Arguments(paramType, ds3Param.getName()));
            }
        }
        return argsBuilder.build();
    }

    private static ImmutableList<String> getImports(final Ds3Request ds3Request) {
        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();

        importsBuilder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        importsBuilder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));

        return importsBuilder.build().asList();
    }

    private static ImmutableSet<String> getImportsFromParamList(final ImmutableList<Ds3Param> paramList) {
        if (paramList == null) {
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();
        for (final Ds3Param ds3Param : paramList) {
            if (!ds3Param.getName().equals("Operation")
                    && ds3Param.getType().contains(".")) {
                importsBuilder.add(convertParamType(ds3Param.getType()));
            }
        }
        return importsBuilder.build();
    }

    private static String convertParamType(final String type) {
        if (type.startsWith("com.spectralogic.s3.common.dao.domain")) {
            final String[] packageParts = type.split("\\.");
            return MODELS_PACKAGE + packageParts[packageParts.length - 1];
        }
        return type;
    }
}
