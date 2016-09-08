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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.models.Variable;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;

import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.createChannelConstructor;
import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.createInputStreamConstructor;
import static com.spectralogic.ds3autogen.utils.Helper.removeVoidArguments;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getRequiredArgsFromRequestHeader;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.isResourceAnArg;

/**
 * Used to generate requests that have a payload which is specified by either
 * a Stream or Channel.
 */
public class StreamRequestPayloadGenerator extends BaseRequestGenerator {

    /**
     * Gets the list of Arguments needed to create the request constructor. This
     * includes all non-void required parameters, arguments described within the
     * request header, and a Size Argument.
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
     * Gets the list of constructor models from a Ds3Request. This includes a
     * constructor with Stream parameter and one with Channel parameter for
     * specifying the request payload.
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(final Ds3Request ds3Request) {
        final ImmutableList<Arguments> constructorArgs = toConstructorArgumentsList(ds3Request);
        final ImmutableList<QueryParam> queryParams = toQueryParamsList(ds3Request);

        final ImmutableList.Builder<RequestConstructor> builder = ImmutableList.builder();
        builder.add(createChannelConstructor(constructorArgs, queryParams));
        builder.add(createInputStreamConstructor(constructorArgs, queryParams));

        return builder.build();
    }

    /**
     * Gets all the required imports that the Request will need in order to properly
     * generate the Java request code, including the Stream and Channel imports
     */
    @Override
    public ImmutableList<String> getAllImports(
            final Ds3Request ds3Request,
            final String packageName) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        builder.add(getParentImport(ds3Request));
        builder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));

        if (isResourceAnArg(ds3Request.getResource(), ds3Request.includeIdInPath())) {
            if (RequestConverterUtil.isResourceId(ds3Request.getResource())) {
                builder.add("java.util.UUID");
            }
            builder.add("com.google.common.net.UrlEscapers");
        }
        builder.add("com.spectralogic.ds3client.utils.SeekableByteChannelInputStream");
        builder.add("java.nio.channels.SeekableByteChannel");
        builder.add("java.io.InputStream");

        return builder.build().asList();
    }

    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions, including Stream and Channel
     */
    @Override
    public ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();
        for (final Arguments arg : toConstructorArgumentsList(ds3Request)) {
            builder.add(new Variable(arg.getName(), arg.getType(), true));
        }
        for (final Arguments arg : toOptionalArgumentsList(ds3Request.getOptionalQueryParams())) {
            builder.add(new Variable(arg.getName(), arg.getType(), false));
        }
        builder.add(new Variable("Stream", "InputStream", true));
        builder.add(new Variable("Channel", "SeekableByteChannel", false));
        return builder.build();
    }
}
