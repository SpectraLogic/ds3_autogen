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

package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.indent;

public final class RequestHelper {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHelper.class);
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

    public static String getAmazonS3InitParams(final boolean isBucketRequired, final boolean isObjectRequired) {
        if (!isBucketRequired) {
            return "void";
        } else if (isObjectRequired) {
            return "const char* bucket_name, const char* object_name";
        }
        return "const char* bucket_name";
    }

    public static String getSpectraS3InitParams(final boolean isResourceIdRequired) {
        if (isResourceIdRequired) {
            return "const char* resource_id";
        }
        return "void";
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
        if (isEmpty(stringsList)) return "";

        return stringsList.stream().collect(Collectors.joining(", "));
    }

    public static String generateInitRequestFunctionSignature(final Request request) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();

        if (request.getClassification() == Classification.amazons3) {
            builder.add(getAmazonS3InitParams(request.isResourceRequired(), request.isResourceIdRequired()));
        } else {
            builder.add(getSpectraS3InitParams(request.isResourceIdRequired()));
        }

        builder.addAll(request.getRequiredQueryParams().stream()
                .filter(parm -> !parm.getParameterType().equals("ds3_bool")) // for required bool / void query param nothing to specify
                .map(Parameter::toString)
                .collect(GuavaCollectors.immutableList()));
        builder.addAll(request.getOptionalQueryParams().stream()
                .map(Parameter::toString)
                .collect(GuavaCollectors.immutableList()));
        final ImmutableList<String> allParams = builder.build();

        return "ds3_request* init_" + getNameRootUnderscores(request.getName()) + "(" + joinStrings(allParams)+ ")";
    }

    public static String generateRequestFunctionSignature(final Request request) {
        return "ds3_error* " + getNameRootUnderscores(request.getName()) + "(" + paramListToString(request.getParamList()) + ")";
    }

    public static String generateParameterCheckingBlock(final Request request) {
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
                builder.append(indent(1)).append("int num_slashes = num_chars_in_ds3_str(request->path, '/');\n");
                builder.append(indent(1)).append("if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {\n");
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
                builder.append(indent(1)).append("int num_slashes = num_chars_in_ds3_str(request->path, '/');\n");
                builder.append(indent(1)).append("if (g_ascii_strncasecmp(request->path->value, \"//\", 2) == 0) {\n");
                builder.append(indent(2)).append("return ds3_create_error(DS3_ERROR_MISSING_ARGS, \"The resource type parameter is required.\");\n");
                builder.append(indent(1)).append("}");
            }
        }

        return builder.toString();
    }
}
