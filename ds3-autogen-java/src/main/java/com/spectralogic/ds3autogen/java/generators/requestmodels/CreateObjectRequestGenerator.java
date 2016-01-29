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
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;

import static com.spectralogic.ds3autogen.utils.Helper.removeVoidArguments;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getRequiredArgsFromRequestHeader;

public class CreateObjectRequestGenerator extends BaseRequestGenerator {

    /**
     * Gets the list of Arguments needed to create the request constructor. This
     * includes all non-void required parameters, and arguments described within
     * the request header.
     */
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
    public ImmutableList<Arguments> toQueryParamsList(final Ds3Request ds3Request) {
        return toArgumentsList(ds3Request.getOptionalQueryParams());
    }

    /**
     * Gets the list of constructor models from a Ds3Request. For the base request, the
     * constructor list will be of size one.
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> constructorArgs = toConstructorArgumentsList(ds3Request);
        final ImmutableList<Arguments> optionalArgs = toArgumentsList(ds3Request.getOptionalQueryParams());
        final ImmutableList<Arguments> queryParams = toQueryParamsList(ds3Request);

        final RequestConstructor depreciatedConstructor = createDeprecatedConstructor(
                constructorArgs,
                ImmutableList.of());

        final RequestConstructor channelConstructor = createChannelConstructor(
                constructorArgs,
                optionalArgs,
                queryParams);

        final RequestConstructor inputStreamConstructor = createInputStreamConstructor(
                constructorArgs,
                optionalArgs,
                queryParams);

        return ImmutableList.of(
                depreciatedConstructor,
                channelConstructor,
                inputStreamConstructor);
    }

    /**
     * Creates the create object request constructor that has the required
     * parameters Stream
     */
    protected static RequestConstructor createInputStreamConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<Arguments> queryParams) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.addAll(optionalArgs);
        builder.add(new Arguments("InputStream", "Stream"));

        return new RequestConstructor(
                builder.build(),
                builder.build(),
                queryParams);
    }

    /**
     * Creates the create object request constructor that has the required
     * parameters Channel
     */
    protected static RequestConstructor createChannelConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<Arguments> queryParams) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.addAll(optionalArgs);
        builder.add(new Arguments("SeekableByteChannel", "Channel"));

        final ImmutableList<String> additionalLines = ImmutableList.of(
                "this.stream = new SeekableByteChannelInputStream(channel);");

        return new RequestConstructor(
                false,
                additionalLines,
                builder.build(),
                builder.build(),
                queryParams);
    }

    /**
     * Creates the create deprecated object request constructor that
     * uses the Channel parameter
     */
    protected static RequestConstructor createDeprecatedConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> queryParams) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.add(new Arguments("SeekableByteChannel", "Channel"));

        final ImmutableList<String> additionalLines = ImmutableList.of(
                "this.stream = new SeekableByteChannelInputStream(channel);");

        return new RequestConstructor(
                true,
                additionalLines,
                builder.build(),
                builder.build(),
                queryParams);
    }
}
