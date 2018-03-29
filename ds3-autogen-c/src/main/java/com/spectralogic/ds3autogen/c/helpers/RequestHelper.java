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

package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import java.security.InvalidParameterException;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.indent;
import static java.util.Comparator.comparing;

public final class RequestHelper {
    private final static RequestHelper requestHelper = new RequestHelper();

    public static RequestHelper getInstance() {
        return requestHelper;
    }

    public static String getNameRoot(final String name) {
        return Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name));
    }

    public static String getNameRootUnderscores(final String name) {
        return Helper.camelToUnderscore(getNameRoot(name));
    }

    /**
     * Find all response types of all requests
     */
    public static ImmutableSet<String> getResponseTypes(final ImmutableList<Request> allRequests) {
        return allRequests.stream()
                .map(Request::getResponseType)
                .collect(GuavaCollectors.immutableSet());
    }

    /**
     * Create a sorted (by name) set of all optional query Parameters of all Requests
     */
    public static ImmutableSet<Parameter> getAllOptionalQueryParams(final ImmutableList<Request> allRequest) {
        return allRequest.stream()
                .flatMap(r ->r.getOptionalQueryParams().stream())
                .sorted(comparing(Parameter::getName))
                .collect(GuavaCollectors.immutableSet());
    }

    public static String getAmazonS3InitParams(final boolean isBucketRequired, final boolean isObjectRequired) {
        if (!isBucketRequired) {
            return "";
        } else if (isObjectRequired) {
            return "const char *const bucket_name, const char *const object_name";
        }
        return "const char *const bucket_name";
    }

    public static String getSpectraS3InitParams(final boolean isResourceRequired) {
        if (isResourceRequired) {
            return "const char *const resource_id";
        }
        return "";
    }

    public static String paramListToString(final ImmutableList<Parameter> paramList) {
        if (isEmpty(paramList)) {
            return "";
        }

        return paramList.stream()
                .map(Parameter::toString)
                .collect(Collectors.joining(", "));
    }

    public static String joinStrings(final ImmutableList<String> stringsList) {
        if (isEmpty(stringsList)) return "void";

        return stringsList.stream().collect(Collectors.joining(", "));
    }

    public static String generateInitRequestFunctionSignature(final Request request) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        if (request.getClassification() == Classification.amazons3) {
            final String amazonS3InitParams = getAmazonS3InitParams(request.isResourceRequired(), request.isResourceIdRequired());
            if (!amazonS3InitParams.isEmpty()) {
                builder.add(amazonS3InitParams);
            }
        } else {
            final String spectraS3InitParams = getSpectraS3InitParams(request.isResourceRequired());
            if (!spectraS3InitParams.isEmpty()) {
                builder.add(spectraS3InitParams);
            }
        }

        builder.addAll(request.getRequiredQueryParams().stream()
                .filter(parm -> !parm.getParameterType().equals("ds3_bool")) // for required bool / void query param nothing to specify
                .filter(parm -> !parm.getParameterType().equals("operation")) // for required spectrads3 operation query param nothing to specify
                .map(Parameter::toString)
                .collect(GuavaCollectors.immutableList()));

        if (request.hasRequestPayload()
        && !request.getName().equalsIgnoreCase("ds3_put_object_request")
        && !request.getName().equalsIgnoreCase("ds3_put_multi_part_upload_part_request")) {
            builder.add(request.getRequestPayload().toString());
        }

        final ImmutableList<String> allParams = builder.build();

        return "ds3_request* " + request.getInitName() + "(" + joinStrings(allParams)+ ")";
    }

    public static String generateRequestFunctionSignature(final Request request) {
        if (request.getName().equalsIgnoreCase("ds3_put_object_request")
         || request.getName().equalsIgnoreCase("ds3_get_object_request")
         || request.getName().equalsIgnoreCase("ds3_put_multi_part_upload_part_request")) {
            return "ds3_error* " + request.getName() + "(" + paramListToString(request.getParamList()) + ", void* user_data, size_t (*callback)(void*, size_t, size_t, void*))";
        }

        return "ds3_error* " + request.getName() + "(" + paramListToString(request.getParamList()) + ")";
    }

    public static String generateParameterValidationBlock(final Request request) {
        final StringBuilder builder = new StringBuilder();

        if (request.getClassification() == Classification.amazons3) {
            if (request.isResourceIdRequired()) {
                builder.append(indent(1)).append("int num_slashes = num_chars_in_ds3_str(request->path, '/');\n");
                builder.append(indent(1)).append("if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {\n");
                builder.append(indent(2)).append("return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The bucket name parameter is required.\");\n");
                builder.append(indent(1)).append("} else if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {\n");
                builder.append(indent(2)).append("return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The object name parameter is required.\");\n");
                builder.append(indent(1)).append("}");
            } else if(request.isResourceRequired()) {
                builder.append(indent(1)).append("if (request->path->size < 2) {\n");
                builder.append(indent(2)).append("return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The bucket name parameter is required.\");\n");
                builder.append(indent(1)).append("}");
            }
        } else if(request.getClassification() == Classification.spectrads3) {
            if (request.isResourceRequired()) {
                builder.append(indent(1)).append("int num_slashes = num_chars_in_ds3_str(request->path, '/');\n");
                builder.append(indent(1)).append("if (num_slashes < 2 || ((num_slashes == 2) && ('/' == request->path->value[request->path->size-1]))) {\n");
                builder.append(indent(2)).append("return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource type parameter is required.\");\n");
                builder.append(indent(1)).append("} else if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {\n");
                builder.append(indent(2)).append("return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource id parameter is required.\");\n");
                builder.append(indent(1)).append("}");
            } else if(request.isResourceIdRequired()) {
                builder.append(indent(1)).append("if (request->path->size < 2) {\n");
                builder.append(indent(2)).append("return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource type parameter is required.\");\n");
                builder.append(indent(1)).append("}");
            }
        }

        return builder.toString();
    }

    public String getRequestObjectListType(final String requestName) {
        switch(requestName) {
            case "ds3_get_bulk_job_spectra_s3_request":
            case "ds3_stage_objects_job_spectra_s3_request":
                return "BULK_GET";

            case "ds3_put_bulk_job_spectra_s3_request":
                return "BULK_PUT";

            case "ds3_verify_bulk_job_spectra_s3_request":
            case "ds3_eject_storage_domain_spectra_s3_request":
            case "ds3_eject_storage_domain_blobs_spectra_s3_request":
            case "ds3_get_physical_placement_for_objects_spectra_s3_request":
            case "ds3_get_physical_placement_for_objects_with_full_details_spectra_s3_request":
            case "ds3_verify_physical_placement_for_objects_spectra_s3_request":
            case "ds3_verify_physical_placement_for_objects_with_full_details_spectra_s3_request":
                return "GET_PHYSICAL_PLACEMENT";

            case "ds3_complete_multi_part_upload_request":
                return "COMPLETE_MPU";

            case "ds3_put_multi_part_upload_part_request":
            case "ds3_put_object_request":
                return "DATA";

            case "ds3_delete_objects_request":
                return "STRING_LIST";

            case "ds3_get_blob_persistence_spectra_s3_request":
            case "ds3_replicate_put_job_spectra_s3_request":
                return "STRING";

            default:
                throw new InvalidParameterException("Unknown request:" + requestName);
        }
    }
}
