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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.python.model.request.BaseRequest;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.BaseQueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.OperationQueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.QueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.VoidQueryParam;
import com.spectralogic.ds3autogen.python.utils.GeneratorUtils;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

import static com.spectralogic.ds3autogen.python.utils.PythonDocGeneratorUtil.toConstructorDocs;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;

public class BaseRequestGenerator implements RequestModelGenerator<BaseRequest>, RequestModelGeneratorUtils {

    @Override
    public BaseRequest generate(final Ds3Request ds3Request, final Ds3DocSpec docSpec) {
        final String name = NormalizingContractNamesUtil.removePath(ds3Request.getName());
        final String path = GeneratorUtils.toRequestPath(ds3Request);
        final String additionalContent = getAdditionalContent(name);
        final ImmutableList<String> assignments = toAssignments(ds3Request);
        final ImmutableList<String> constructorParams = toConstructorParams(ds3Request);
        final ImmutableList<Arguments> optionalArgs = toOptionalArgumentsList(ds3Request.getOptionalQueryParams());
        final ImmutableList<QueryParam> queryParams = toQueryParamList(ds3Request.getOperation(), ds3Request.getRequiredQueryParams());
        final String documentation = toDocumentation(name, constructorParams, docSpec);

        return new BaseRequest(
                name,
                path,
                ds3Request.getHttpVerb(),
                additionalContent,
                assignments,
                constructorParams,
                optionalArgs,
                queryParams,
                documentation);
    }

    /**
     * Creates python documentation for the request constructor
     * @param requestName The normalized request name without path
     */
    protected static String toDocumentation(
            final String requestName,
            final ImmutableList<String> constructorParams,
            final Ds3DocSpec docSpec) {
        if (isEmpty(constructorParams)) {
            return "";
        }
        final ImmutableList<String> normalizedParams = constructorParams.stream()
                .map(i -> i.replace("=None", ""))
                .collect(GuavaCollectors.immutableList());
        return toConstructorDocs(requestName, normalizedParams, docSpec, 1);
    }

    /**
     * Gets all constructor parameters for a Ds3Request
     */
    @Override
    public ImmutableList<String> toConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<ConstructorParam> builder = ImmutableList.builder();
        builder.addAll(toRequiredConstructorParams(ds3Request));
        builder.addAll(toOptionalConstructorParams(ds3Request));

        return builder.build().stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the sorted list of required constructor parameters for a Ds3Request
     */
    @Override
    public ImmutableList<ConstructorParam> toRequiredConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getNonVoidArgsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getAssignmentArguments(ds3Request));

        return builder.build().stream()
                .sorted(new CustomArgumentComparator())
                .map(arg -> new ConstructorParam(camelToUnderscore(arg.getName()), false))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the arguments that are assigned within the constructor. This includes arguments
     * defined in the request header, and NotificationId when appropriate
     */
    protected static ImmutableList<Arguments> getAssignmentArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getRequiredArgsFromRequestHeader(ds3Request));
        if (ds3Request.getIncludeInPath() && isResourceNotification(ds3Request.getResource())) {
            builder.add(new Arguments("UUID", "NotificationId"));
        }
        return builder.build();
    }

    /**
     * Gets the sorted list of optional constructor parameters for a Ds3Request
     */
    @Override
    public ImmutableList<ConstructorParam> toOptionalConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> optionalArgs = toOptionalArgumentsList(ds3Request.getOptionalQueryParams());
        return optionalArgs.stream()
                .sorted(new CustomArgumentComparator())
                .map(arg -> new ConstructorParam(camelToUnderscore(arg.getName()), true))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Non-special-cased requests do not contain any additional code
     */
    @Override
    public String getAdditionalContent(final String requestName) {
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
    public ImmutableList<String> toAssignments(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> args = getAssignmentArguments(ds3Request);

        return args.stream()
                .map(param -> camelToUnderscore(param.getName()))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the list of optional Arguments for the Ds3Request
     */
    @Override
    public ImmutableList<Arguments> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalParams) {
        return getArgsFromParamList(optionalParams);
    }
}
