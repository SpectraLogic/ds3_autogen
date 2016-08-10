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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;

import static com.spectralogic.ds3autogen.utils.Helper.removeVoidArguments;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getRequiredArgsFromRequestHeader;

public class GetObjectRequestGenerator extends BaseRequestGenerator {

    /**
     * Gets the list of Arguments needed to create the request constructor. This
     * includes all non-void required parameters, arguments described within
     * the request header, and the Channel argument
     */
    @Override
    public ImmutableList<Arguments> toConstructorArgumentsList(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getRequiredArgsFromRequestHeader(ds3Request));
        builder.add(new Arguments("WritableByteChannel", "Channel"));
        builder.addAll(removeVoidArguments(toArgumentsList(ds3Request.getRequiredQueryParams())));
        return builder.build();
    }

    /**
     * Gets the list of constructor models from a get object Ds3Request. If it is an
     * amazon request, then the old depreciated constructor is also created.
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> constructorArgs = toConstructorArgumentsList(ds3Request);
        final ImmutableList<Arguments> optionalArgs = toOptionalArgumentsList(ds3Request.getOptionalQueryParams());
        final ImmutableList<Arguments> queryParams = toQueryParamsList(ds3Request);

        final ImmutableList.Builder<RequestConstructor> constructorBuilder = ImmutableList.builder();

        if (ds3Request.getClassification() == Classification.amazons3) {
            constructorBuilder.add(
                    createDeprecatedConstructor(
                            constructorArgs,
                            queryParams));
        }

        constructorBuilder.add(
                createRegularConstructor(
                        constructorArgs,
                        optionalArgs,
                        queryParams));

        return constructorBuilder.build();
    }

    /**
     * Creates the depreciated constructor for the get object request
     */
    protected static RequestConstructor createDeprecatedConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> queryParams) {
        return new RequestConstructor(
                true,
                ImmutableList.of(),
                constructorArgs,
                constructorArgs,
                queryParams);
    }

    /**
     * Creates the regular constructor for the get object request
     */
    protected static RequestConstructor createRegularConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<Arguments> queryParams) {
        final ImmutableList.Builder<Arguments> constructorArgBuilder = ImmutableList.builder();
        constructorArgBuilder.addAll(constructorArgs);
        constructorArgBuilder.addAll(optionalArgs);

        final ImmutableList.Builder<Arguments> queryParamsBuilder = ImmutableList.builder();
        queryParamsBuilder.addAll(queryParams);
        queryParamsBuilder.addAll(optionalArgs);

        final ImmutableList<Arguments> updatedConstructorArgs = constructorArgBuilder.build();
        return new RequestConstructor(
                updatedConstructorArgs,
                updatedConstructorArgs,
                queryParamsBuilder.build());
    }
}
