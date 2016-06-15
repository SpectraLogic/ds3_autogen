/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.python.model.request.BaseRequest;
import com.spectralogic.ds3autogen.python.model.request.RequestPayload;
import com.spectralogic.ds3autogen.python.model.request.queryparam.BaseQueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.OperationQueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.QueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.VoidQueryParam;
import com.spectralogic.ds3autogen.python.utils.GeneratorUtils;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

public class BaseRequestGenerator implements RequestModelGenerator<BaseRequest>, RequestModelGeneratorUtils {

    @Override
    public BaseRequest generate(final Ds3Request ds3Request) {
        final String name = NormalizingContractNamesUtil.removePath(ds3Request.getName());
        final String path = GeneratorUtils.toRequestPath(ds3Request);
        final RequestPayload requestPayload = toRequestPayload(ds3Request, name);
        final ImmutableList<Arguments> requiredArgs = toRequiredArgumentsList(ds3Request);
        final ImmutableList<Arguments> optionalArgs = toOptionalArgumentsList(ds3Request.getOptionalQueryParams());
        final ImmutableList<QueryParam> queryParams = toQueryParamList(ds3Request.getOperation(), ds3Request.getRequiredQueryParams());

        return new BaseRequest(
                name,
                path,
                ds3Request.getHttpVerb(),
                requestPayload,
                requiredArgs,
                optionalArgs,
                queryParams);
    }

    /**
     * Non-special-cased requests do not contain request payloads
     */
    @Override
    public RequestPayload toRequestPayload(final Ds3Request ds3Request, final String requestName) {
        return null;
    }

    /**
     * Creates the list of non-optional query params assigned in the constructor
     */
    public static ImmutableList<QueryParam> toQueryParamList(
            final Operation operation,
            final ImmutableList<Ds3Param> requiredParams) {
        final ImmutableList.Builder<QueryParam> builder = ImmutableList.builder();
        if (operation != null) {
            builder.add(new OperationQueryParam(operation.toString().toLowerCase()));
        }
        builder.addAll(toRequiredQueryParamList(requiredParams));
        return builder.build();
    }

    /**
     * Converts a list of required parameters to a list of query parameters
     */
    public static ImmutableList<QueryParam> toRequiredQueryParamList(
            final ImmutableList<Ds3Param> requiredParams) {
        return getArgsFromParamList(requiredParams).stream()
                .map(BaseRequestGenerator::toQueryParam)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3Param into a query param
     */
    public static QueryParam toQueryParam(final Arguments arg) {
        if (arg.getType().equals("void")) {
            return new VoidQueryParam(camelToUnderscore(arg.getName()));
        }
        return new BaseQueryParam(camelToUnderscore(arg.getName()));
    }

    /**
     * Gets the list of required Arguments for the Ds3Request
     */
    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getNonVoidArgsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getRequiredArgsFromRequestHeader(ds3Request));
        if (ds3Request.includeIdInPath() && isResourceNotification(ds3Request.getResource())) {
            builder.add(new Arguments("UUID", "NotificationId"));
        }
        return builder.build();
    }

    /**
     * Gets the list of optional Arguments for the Ds3Request
     */
    @Override
    public ImmutableList<Arguments> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalParams) {
        return getArgsFromParamList(optionalParams);
    }
}
