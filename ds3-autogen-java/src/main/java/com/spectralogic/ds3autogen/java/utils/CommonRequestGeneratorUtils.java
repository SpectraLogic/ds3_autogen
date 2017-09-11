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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.java.models.*;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.getType;
import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toConstructorDocs;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Contains static util methods used by some of the Java Request Generators, but not
 * by all. Methods in this class should have no clear placement within the request
 * generator inheritance structure.
 */
public class CommonRequestGeneratorUtils {

    /**
     * Creates a constructor with the provided parameters in addition to
     * adding the required parameter Stream for parsing a request payload.
     */
    public static RequestConstructor createInputStreamConstructor(
            final ImmutableList<Arguments> parameters,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        if (hasContent(parameters)) {
            builder.addAll(parameters);
        }
        builder.add(new Arguments("InputStream", "Stream"));

        final ImmutableList<Arguments> updatedParameters = builder.build();
        final ImmutableList<String> argNames = updatedParameters.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        final ImmutableList<ConstructorParam> constructorParams = toConstructorParams(updatedParameters);

        final ImmutableList<Precondition> preconditions = toPreconditions(constructorParams);

        return new RequestConstructor(
                constructorParams,
                updatedParameters,
                queryParams,
                preconditions,
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }

    /**
     * Creates a constructor with the provided parameters in addition to
     * adding the required parameter Channel for parsing a request payload.
     */
    public static RequestConstructor createChannelConstructor(
            final ImmutableList<Arguments> parameters,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(parameters);
        builder.add(new Arguments("SeekableByteChannel", "Channel"));

        final ImmutableList<String> additionalLines = ImmutableList.of(
                "this.stream = new SeekableByteChannelInputStream(channel);");

        final ImmutableList<Arguments> updatedParameters = builder.build();

        final ImmutableList<String> argNames = updatedParameters.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        final ImmutableList<ConstructorParam> constructorParams = toConstructorParams(updatedParameters);

        final ImmutableList<Precondition> preconditions = toPreconditions(constructorParams);

        return new RequestConstructor(
                false,
                additionalLines,
                constructorParams,
                updatedParameters,
                queryParams,
                preconditions,
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }

    /**
     * Converts a list of arguments into a list of query params
     */
    public static ImmutableList<QueryParam> argsToQueryParams(final ImmutableList<Arguments> args) {
        if (isEmpty(args)) {
            return ImmutableList.of();
        }
        return args.stream()
                .map(QueryParam::new)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a list of arguments into a sorted list of constructor parameters.
     */
    public static ImmutableList<ConstructorParam> toConstructorParams(final ImmutableList<Arguments> arguments) {
        return arguments.stream()
                .sorted(new CustomArgumentComparator())
                .map(CommonRequestGeneratorUtils::toConstructorParam)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts an Argument into a Constructor Param of the correct type
     */
    static ConstructorParam toConstructorParam(final Arguments arg) {
        final String type = getType(arg);

        switch (type) {
            case "WritableByteChannel":
            case "SeekableByteChannel":
            case "OutputStream":
            case "InputStream":
                return new NonnullConstructorParam(arg.getName(), type);
            default:
                return new SimpleConstructorParam(arg.getName(), type);
        }

    }

    /**
     * Determines if any of the constructor parameters has the specified type.
     */
    public static boolean containsType(final ImmutableList<ConstructorParam> constructorParams, final String type) {
        return constructorParams.stream()
                .anyMatch(param -> param.getType().equals(type));
    }

    /**
     * Gets the list of preconditions for a constructor based on the constructor parameters.
     */
    public static ImmutableList<Precondition> toPreconditions(final ImmutableList<ConstructorParam> constructorParams) {
        return constructorParams.stream()
                .filter(param -> param instanceof NonnullConstructorParam)
                .map(param -> new NotNullPrecondition(param.getName()))
                .collect(GuavaCollectors.immutableList());
    }
}
