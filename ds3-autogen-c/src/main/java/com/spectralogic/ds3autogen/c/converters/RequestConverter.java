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

package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;

import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.isResourceAnArg;

public final class RequestConverter {
    private static final Logger LOG = LoggerFactory.getLogger(RequestConverter.class);

    public static Request toRequest(final Ds3Request ds3Request) {
        return new Request(
                ds3Request.getName(),
                ds3Request.getClassification(),
                ds3Request.getHttpVerb(),
                getRequestPath(ds3Request),
                ds3Request.getOperation(),
                ds3Request.getAction(),
                isResourceRequired(ds3Request),
                isResourceIdRequired(ds3Request),
                getRequiredArgs(ds3Request),
                getOptionalArgs(ds3Request),
                ds3Request.getDs3ResponseCodes());
    }

    private static String getRequestPath(final Ds3Request ds3Request) {
        if (ds3Request.getClassification() == Classification.amazons3) {
            return getAmazonS3BuildPathArgs(ds3Request);
        } else if (ds3Request.getClassification() == Classification.spectrads3) {
            return getSpectraS3BuildPathArgs(ds3Request);
        } else {
            LOG.warn("Skipping internal or unknown Request " + ds3Request.getName() + " of Classification " + ds3Request.getClassification().toString());
            throw new InvalidParameterException("Invalid Request Classification " + ds3Request.getClassification().toString());
        }
    }

    private static String getAmazonS3BuildPathArgs(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"/\"");

        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
            builder.append(", bucket_name");

            if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
                builder.append(", object_name");
            } else {
                builder.append(", NULL");
            }
        } else {
            builder.append(", NULL, NULL");
        }

        return builder.toString();
    }

    private static String getSpectraS3BuildPathArgs(final Ds3Request ds3Request) {
        if (ds3Request.getResource() == null) {
            return "\"/_rest_/\"";
        }

        final StringBuilder builder = new StringBuilder();
        builder.append("\"/_rest_/").append(ds3Request.getResource().toString().toLowerCase()).append("\"");

        if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())) {
            // _build_path() will URL escape the resource_id
            builder.append(", resource_id, NULL");
        } else {
            builder.append(", NULL, NULL");
        }

        return builder.toString();
    }

    private static ImmutableList<Arguments> getRequiredArgs(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgsBuilder = ImmutableList.builder();
        LOG.debug("Getting required args...");
        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
            LOG.debug("\tbucket name REQUIRED.");
            requiredArgsBuilder.add(new Arguments("String", "bucketName"));
            if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
                LOG.debug("\tobject name REQUIRED.");
                requiredArgsBuilder.add(new Arguments("String", "objectName"));
            }
        }

        LOG.debug("Getting required query params...");
        if (ConverterUtil.isEmpty(ds3Request.getRequiredQueryParams())) {
            return requiredArgsBuilder.build();
        }

        for (final Ds3Param ds3Param : ds3Request.getRequiredQueryParams()) {
            if (ds3Param == null) break;

            LOG.debug("\tquery param " + ds3Param.getType());
            final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
            LOG.debug("\tparam " + paramType + " is required.");
            requiredArgsBuilder.add(new Arguments(paramType, ds3Param.getName()));
        }

        return requiredArgsBuilder.build();
    }

    private static ImmutableList<Arguments> getOptionalArgs(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> optionalArgsBuilder = ImmutableList.builder();
        LOG.debug("Getting optional args...");
        if (ConverterUtil.isEmpty(ds3Request.getOptionalQueryParams())) {
            return optionalArgsBuilder.build();
        }

        for (final Ds3Param ds3Param : ds3Request.getOptionalQueryParams()) {
            final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
            LOG.debug("\tparam " + ds3Param.getName() + ":" + paramType + " is optional. " + ds3Param.getType());
            optionalArgsBuilder.add(new Arguments(paramType, ds3Param.getName()));
        }

        return optionalArgsBuilder.build();
    }

    private static boolean isResourceRequired(final Ds3Request ds3Request) {
        if (ds3Request.getClassification() == Classification.amazons3) {
            return ds3Request.getBucketRequirement() == Requirement.REQUIRED;
        }

        return RequestConverterUtil.isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath());
    }

    private static boolean isResourceIdRequired(final Ds3Request ds3Request) {
        if (ds3Request.getClassification() == Classification.amazons3) {
            return ds3Request.getBucketRequirement() == Requirement.REQUIRED
                && ds3Request.getObjectRequirement() == Requirement.REQUIRED;
        }

        return !RequestConverterUtil.isResourceSingleton(ds3Request.getResource());
    }
}
