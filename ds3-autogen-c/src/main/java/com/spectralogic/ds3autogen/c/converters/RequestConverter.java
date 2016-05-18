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
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.ParameterModifier;
import com.spectralogic.ds3autogen.c.models.ParameterPointerType;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.isResourceAnArg;

public final class RequestConverter {
    private static final Logger LOG = LoggerFactory.getLogger(RequestConverter.class);
    private static final ImmutableMap<String, Parameter> hasRequestPayload = buildRequestPayloadMap();

    public static Request toRequest(final Ds3Request ds3Request) {
        final String requestName = RequestHelper.getNameRootUnderscores(ds3Request.getName());
        LOG.debug("Request: " + requestName);
        final String responseType = getResponseType(ds3Request.getDs3ResponseCodes());
        return new Request(
                requestName,
                ds3Request.getClassification(),
                ds3Request.getHttpVerb(),
                getRequestPath(ds3Request),
                ds3Request.getOperation(),
                ds3Request.getAction(),
                getParamList(responseType),
                getRequiredQueryParams(ds3Request),
                getOptionalQueryParams(ds3Request),
                isResourceRequired(ds3Request),
                isResourceIdRequired(ds3Request),
                hasRequestPayload.get(RequestHelper.getNameRoot(ds3Request.getName())),
                responseType);
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
            // _build_path() will URL escape the resource_id at runtime
            builder.append(", resource_id, NULL");
        } else {
            builder.append(", NULL, NULL");
        }

        return builder.toString();
    }
    private static ImmutableList<Parameter> getRequiredQueryParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Parameter> requiredArgsBuilder = ImmutableList.builder();

        if (hasContent(ds3Request.getRequiredQueryParams())) {
            for (final Ds3Param ds3Param : ds3Request.getRequiredQueryParams()) {
                if (ds3Param == null) continue;

                if (ds3Request.getClassification() == Classification.spectrads3
                        && ds3Request.getOperation() != null
                        && ds3Param.getName().equalsIgnoreCase("Operation")) { // Special Case - Parameter.Type needs to be Request.Operation value
                    LOG.debug("\tRequired QueryParam: " + ds3Param.getName() + "=" + ds3Request.getOperation().name());
                    requiredArgsBuilder.add(new Parameter(ParameterModifier.CONST, "operation", ds3Request.getOperation().name(), ParameterPointerType.NONE, true));
                } else {
                    final Parameter requiredParam = ParameterConverter.toParameter(ds3Param, true);
                    LOG.debug("\tRequired QueryParam: " + requiredParam.toString());
                    requiredArgsBuilder.add(requiredParam);
                }
            }
        }

        // Add 'length' if 'offset' is given
        if (hasContent(ds3Request.getOptionalQueryParams())) {
            for (final Ds3Param ds3Param : ds3Request.getOptionalQueryParams()) {
                if (ds3Param == null) continue;

                if (ds3Param.getName().equalsIgnoreCase("Offset")) {
                    final Parameter lengthParam = new Parameter(
                            ParameterModifier.CONST, "uint64_t", "length", ParameterPointerType.SINGLE_POINTER, true);
                    LOG.debug("\tFound Optional Offset, adding Length QueryParam:\n" + lengthParam.toString());
                    requiredArgsBuilder.add(lengthParam);
                }
            }
        }

        return requiredArgsBuilder.build();
    }

    private static ImmutableList<Parameter> getOptionalQueryParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Parameter> optionalArgsBuilder = ImmutableList.builder();
        if (isEmpty(ds3Request.getOptionalQueryParams())) {
            return optionalArgsBuilder.build();
        }

        for (final Ds3Param ds3Param : ds3Request.getOptionalQueryParams()) {
            final Parameter optionalQueryParam = ParameterConverter.toParameter(ds3Param, true);
            LOG.debug("\tOptional QueryParam: " + optionalQueryParam.toString());
            optionalArgsBuilder.add(ParameterConverter.toParameter(ds3Param, false));
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

    public static String getResponseType(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) return "";

        String responseTypeName = "";
        for (final Ds3ResponseCode responseCode : responseCodes) {
            final int rc = responseCode.getCode();
            if (rc < 200 || rc >= 300) continue;

            for (final Ds3ResponseType responseType : responseCode.getDs3ResponseTypes()) {
                if (ConverterUtil.hasContent(responseType.getType())
                 && !responseType.getType().contentEquals("null")) {
                    responseTypeName = responseType.getType();
                } else if (ConverterUtil.hasContent(responseType.getComponentType())
                        && !responseType.getComponentType().contentEquals("null")) {
                    responseTypeName = responseType.getComponentType();
                }
            }
        }

        if (responseTypeName.equalsIgnoreCase("java.lang.String")) {
            return "ds3_str";
        } else if (!responseTypeName.isEmpty()) {
            return StructHelper.getResponseTypeName(responseTypeName);
        }
        return "";
    }

    public static ImmutableList<Parameter> getParamList(final String responseType) {
        final ImmutableList.Builder<Parameter> builder = ImmutableList.builder();
        builder.add(new Parameter(ParameterModifier.CONST, "ds3_client", "client", ParameterPointerType.SINGLE_POINTER, true));
        builder.add(new Parameter(ParameterModifier.CONST, "ds3_request", "request", ParameterPointerType.SINGLE_POINTER, true));

        if (!responseType.isEmpty()) {
            if (responseType.equalsIgnoreCase("ds3_str")) {
                builder.add(new Parameter(ParameterModifier.CONST, "ds3_str", "response", ParameterPointerType.SINGLE_POINTER, true));
            } else {
                builder.add(new Parameter(ParameterModifier.CONST, responseType, "response", ParameterPointerType.DOUBLE_POINTER, true));
            }
        }

        return builder.build();
    }

    public static ImmutableMap<String, Parameter> buildRequestPayloadMap() {
        final ImmutableMap.Builder<String, Parameter> requestPayloadMap =  ImmutableMap.builder();
        requestPayloadMap.put("CreateGetJob",
                new Parameter(
                        ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("CreatePutJob",
                new Parameter(ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("CreateVerifyJob",
                new Parameter(ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("EjectStorageDomainBlobs",
                new Parameter(ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("EjectStorageDomain",
                new Parameter(ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("GetPhysicalPlacementForObjects",
                new Parameter(ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("VerifyPhysicalPlacementForObjects",
                new Parameter(ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("VerifyPhysicalPlacementForObjectsWithFullDetails",
                new Parameter(ParameterModifier.CONST, "ds3_bulk_object_list", "object_list", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("CompleteMultiPartUpload",
                new Parameter(ParameterModifier.CONST, "ds3_complete_multipart_upload_result", "complete_mpu_payload", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("CreateMultiPartUploadPart",
                new Parameter(ParameterModifier.CONST, "ds3_request_payload", "payload", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("CreateObject",
                new Parameter(ParameterModifier.CONST, "ds3_request_payload", "payload", ParameterPointerType.SINGLE_POINTER, true));
        requestPayloadMap.put("DeleteObjects",
                new Parameter(ParameterModifier.CONST, "ds3_metadata_keys_result", "payload", ParameterPointerType.SINGLE_POINTER, true)); // List<String>
        requestPayloadMap.put("GetBlobPersistence",
                new Parameter(ParameterModifier.CONST, "ds3_str", "payload", ParameterPointerType.SINGLE_POINTER, true)); // String
        requestPayloadMap.put("ReplicatePutJob",
                new Parameter(ParameterModifier.CONST, "ds3_str", "payload", ParameterPointerType.SINGLE_POINTER, true)); // String
        return requestPayloadMap.build();
    }
}
