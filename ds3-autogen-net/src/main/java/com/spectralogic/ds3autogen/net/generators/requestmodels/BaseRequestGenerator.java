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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import com.spectralogic.ds3autogen.net.model.request.BaseRequest;
import com.spectralogic.ds3autogen.net.model.request.RequestConstructor;
import com.spectralogic.ds3autogen.net.utils.GeneratorUtils;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;

import static com.spectralogic.ds3autogen.net.utils.NetNullableVariableUtils.createNullableVariable;
import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.containsType;
import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.modifyType;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseRequestGenerator implements RequestModelGenerator<BaseRequest>, RequestModelGeneratorUtils {

    @Override
    public BaseRequest generate(final Ds3Request ds3Request, final ImmutableMap<String, Ds3Type> typeMap) {

        final String name = NormalizingContractNamesUtil.removePath(ds3Request.getName());
        final String path = GeneratorUtils.toRequestPath(ds3Request);
        final ImmutableList<Arguments> requiredArgs = convertUuidToString(Helper.removeVoidArguments(toRequiredArgumentsList(ds3Request)));
        final ImmutableList<NetNullableVariable> optionalArgs = toOptionalArgumentsList(ds3Request.getOptionalQueryParams(), typeMap);
        final ImmutableList<RequestConstructor> constructors = toConstructorList(ds3Request);

        return new BaseRequest(
                name,
                path,
                ds3Request.getHttpVerb(),
                requiredArgs,
                optionalArgs,
                constructors);
    }

    /**
     * Creates the request constructors. There will be multiple constructors if there are
     * constructor parameters of type Guid
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> constructorArgs = Helper.removeVoidArguments(toConstructorArgsList(ds3Request));
        final ImmutableList<Arguments> queryParams = toQueryParamsList(ds3Request);

        final RequestConstructor standardConstructor = new RequestConstructor(
                constructorArgs,
                queryParams,
                ds3Request.getOperation());

        return splitGuidConstructor(standardConstructor);
    }

    /**
     * Splits a constructor in two if the original constructor contains a parameter of type Guid
     */
    protected static ImmutableList<RequestConstructor> splitGuidConstructor(final RequestConstructor constructor) {
        final ImmutableList.Builder<RequestConstructor> builder = ImmutableList.builder();
        builder.add(constructor);
        if (!containsType(constructor.getConstructorArgs(), "UUID")) {
            return builder.build();
        }
        builder.add(convertGuidToStringConstructor(constructor));
        return builder.build();
    }

    /**
     * Converts all Guid types into strings within a given request constructor
     */
    protected static RequestConstructor convertGuidToStringConstructor(final RequestConstructor  constructor) {
        return new RequestConstructor(
                convertUuidToString(constructor.getConstructorArgs()),
                convertUuidToString(constructor.getQueryParams()),
                constructor.getOperation());
    }

    /**
     * Modifies a list of Arguments from type UUID to String
     */
    protected static ImmutableList<Arguments> convertUuidToString(final ImmutableList<Arguments> arguments) {
        final String curType = "UUID";
        final String newType = "String";
        return modifyType(arguments, curType, newType);
    }

    /**
     * Gets the list of Arguments that are added to the query params list within the constructor
     */
    @Override
    public ImmutableList<Arguments> toQueryParamsList(final Ds3Request ds3Request) {
        return GeneratorUtils.getArgsFromParamList(ds3Request.getRequiredQueryParams());
    }

    /**
     * Gets the list of optional Arguments from the Ds3Request list of optional Ds3Param
     */
    @Override
    public ImmutableList<NetNullableVariable> toOptionalArgumentsList(
            final ImmutableList<Ds3Param> optionalParams,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if(isEmpty(optionalParams)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<NetNullableVariable> argsBuilder = ImmutableList.builder();
        for (final Ds3Param ds3Param : optionalParams) {
            argsBuilder.add(toNullableArgument(ds3Param, typeMap));
        }
        return argsBuilder.build();
    }

    /**
     * Converts a Ds3Param into a NetNullableVariable
     */
    protected static NetNullableVariable toNullableArgument(
            final Ds3Param optionalParam,
            final ImmutableMap<String, Ds3Type> typeMap) {
        return createNullableVariable(
                optionalParam.getName(),
                optionalParam.getType(),
                optionalParam.isNullable(),
                typeMap);
    }

    /**
     * Gets the list of Arguments for creating the constructor, which is derived from the
     * required parameters for standard commands
     */
    @Override
    public ImmutableList<Arguments> toConstructorArgsList(final Ds3Request ds3Request) {
        return GeneratorUtils.getRequiredArgs(ds3Request);
    }

    /**
     * Gets the list of required Arguments from a Ds3Request
     */
    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request) {
        return GeneratorUtils.getRequiredArgs(ds3Request);
    }
}
