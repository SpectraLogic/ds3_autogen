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

package com.spectralogic.ds3autogen.java.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.argsToQueryParams;
import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toConstructorDocs;
import static com.spectralogic.ds3autogen.utils.Helper.removeVoidArguments;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getRequiredArgsFromRequestHeader;

public class CreateObjectRequestGenerator extends BaseRequestGenerator {

    /**
     * Gets the list of Arguments needed to create the request constructor. This
     * includes all non-void required parameters, and arguments described within
     * the request header.
     */
    @Override
    public ImmutableList<Arguments> toConstructorArgumentsList(
            final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getRequiredArgsFromRequestHeader(ds3Request));
        builder.addAll(removeVoidArguments(toArgumentsList(ds3Request.getRequiredQueryParams())));
        builder.add(new Arguments("long", "Size"));
        return builder.build();
    }

    /**
     * Creates the list of arguments that are added to the query params within
     * the constructors, which is composed of optional query params
     */
    @Override
    public ImmutableList<QueryParam> toQueryParamsList(final Ds3Request ds3Request) {
        return argsToQueryParams(toArgumentsList(ds3Request.getOptionalQueryParams()));
    }

    /**
     * Gets the list of constructor models from a Ds3Request. This includes the
     * Create Object deprecated constructor, a constructor that uses the parameter
     * Channel, and a constructor that has an InputStream parameter
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(
            final Ds3Request ds3Request,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList<Arguments> constructorArgs = toConstructorArgumentsList(ds3Request);
        final ImmutableList<Arguments> optionalArgs = toArgumentsList(ds3Request.getOptionalQueryParams());
        final ImmutableList<QueryParam> queryParams = toQueryParamsList(ds3Request);

        final RequestConstructor depreciatedConstructor = createDeprecatedConstructor(
                constructorArgs,
                requestName,
                docSpec);

        final RequestConstructor channelConstructor = getChannelConstructor(
                constructorArgs,
                optionalArgs,
                queryParams,
                requestName,
                docSpec);

        final RequestConstructor inputStreamConstructor = getInputStreamConstructor(
                constructorArgs,
                optionalArgs,
                queryParams,
                requestName,
                docSpec);

        return ImmutableList.of(
                depreciatedConstructor,
                channelConstructor,
                inputStreamConstructor);
    }

    /**
     * Creates the create object request constructor that has the required
     * parameters Stream
     */
    protected static RequestConstructor getInputStreamConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.addAll(optionalArgs);
        return CommonRequestGeneratorUtils.createInputStreamConstructor(builder.build(), queryParams, requestName, docSpec);
    }

    /**
     * Creates the create object request constructor that has the required
     * parameters Channel
     */
    protected static RequestConstructor getChannelConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.addAll(optionalArgs);
        return CommonRequestGeneratorUtils.createChannelConstructor(builder.build(), queryParams, requestName, docSpec);
    }

    /**
     * Creates the create deprecated object request constructor that
     * uses the Channel parameter
     */
    protected static RequestConstructor createDeprecatedConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.add(new Arguments("SeekableByteChannel", "Channel"));

        final ImmutableList<String> additionalLines = ImmutableList.of(
                "this.stream = new SeekableByteChannelInputStream(channel);");

        final ImmutableList<Arguments> updatedArgs = builder.build();
        final ImmutableList<String> argNames = updatedArgs.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        return new RequestConstructor(
                true,
                additionalLines,
                updatedArgs,
                updatedArgs,
                ImmutableList.of(),
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }
}
