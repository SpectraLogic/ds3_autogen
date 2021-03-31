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
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.java.models.*;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.argsToQueryParams;
import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.toConstructorParams;
import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.toPreconditions;
import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toConstructorDocs;

public class GetObjectRequestGenerator extends BaseRequestGenerator {

    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions. This consists of all constructor arguments and optional
     * arguments being converted into variables, including the Channel variable.
     */
    @Override
    public ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();
        builder.add(new Variable("Channel", "WritableByteChannel", true));

        for (final Arguments arg : toConstructorArgumentsList(ds3Request)) {
            builder.add(new Variable(arg.getName(), arg.getType(), true));
        }
        for (final Arguments arg : toOptionalArgumentsList(ds3Request.getOptionalQueryParams())) {
            builder.add(new Variable(arg.getName(), arg.getType(), false));
        }
        return builder.build();
    }

    /**
     * Gets the list of constructor models from a get object Ds3Request. If it is an
     * amazon request, then the old depreciated constructor is also created.
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(
            final Ds3Request ds3Request,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList<Arguments> constructorArgs = toConstructorArgumentsList(ds3Request);

        // Add the Job and Offset optional params to non-deprecated constructors
        final ImmutableList<Arguments> optionalConstructorArgs = toOptionalArgumentsList(ds3Request.getOptionalQueryParams()).stream()
                .filter(arg -> arg.getName().equalsIgnoreCase("Job") || arg.getName().equalsIgnoreCase("Offset"))
                .collect(GuavaCollectors.immutableList());

        final ImmutableList<QueryParam> queryParams = toQueryParamsList(ds3Request);

        final ImmutableList.Builder<RequestConstructor> constructorBuilder = ImmutableList.builder();

        if (ds3Request.getClassification() == Classification.amazons3) {
            constructorBuilder.add(
                    createDeprecatedConstructor(
                            constructorArgs,
                            queryParams,
                            requestName,
                            docSpec));
        }

        constructorBuilder.add(
                createChannelConstructor(
                        constructorArgs,
                        optionalConstructorArgs,
                        queryParams,
                        requestName,
                        docSpec));

        constructorBuilder.add(
                createOutputStreamConstructor(
                        constructorArgs,
                        optionalConstructorArgs,
                        queryParams,
                        requestName,
                        docSpec));

        return constructorBuilder.build();
    }

    /**
     * Creates the depreciated constructor for the get object request
     */
    protected static RequestConstructor createDeprecatedConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> constructorArgBuilder = ImmutableList.builder();
        constructorArgBuilder.addAll(constructorArgs);
        constructorArgBuilder.add(new Arguments("WritableByteChannel", "Channel"));

        final ImmutableList<Arguments> updatedConstructorArgs = constructorArgBuilder.build();
        final ImmutableList<String> argNames = updatedConstructorArgs.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        final ImmutableList<ConstructorParam> constructorParams = toConstructorParams(updatedConstructorArgs);

        final ImmutableList<Precondition> preconditions = toPreconditions(constructorParams);

        return new RequestConstructor(
                true,
                ImmutableList.of(),
                constructorParams,
                updatedConstructorArgs,
                queryParams,
                preconditions,
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }

    /**
     * Creates the constructor for the get object request that uses WritableByteChannel
     */
    protected static RequestConstructor createChannelConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> constructorArgBuilder = ImmutableList.builder();
        constructorArgBuilder.addAll(constructorArgs);
        constructorArgBuilder.addAll(optionalArgs);
        constructorArgBuilder.add(new Arguments("WritableByteChannel", "Channel"));

        final ImmutableList.Builder<QueryParam> queryParamsBuilder = ImmutableList.builder();
        queryParamsBuilder.addAll(queryParams);
        queryParamsBuilder.addAll(argsToQueryParams(optionalArgs));

        final ImmutableList<Arguments> updatedConstructorArgs = constructorArgBuilder.build();

        final ImmutableList<String> argNames = updatedConstructorArgs.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        final ImmutableList<ConstructorParam> constructorParams = toConstructorParams(updatedConstructorArgs);

        final ImmutableList<Precondition> preconditions = toPreconditions(constructorParams);

        return new RequestConstructor(
                constructorParams,
                updatedConstructorArgs,
                queryParamsBuilder.build(),
                preconditions,
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }

    /**
     * Creates the constructor for the get object request that uses OutputStream
     */
    protected static RequestConstructor createOutputStreamConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> constructorArgBuilder = ImmutableList.builder();
        constructorArgBuilder.addAll(constructorArgs);
        constructorArgBuilder.addAll(optionalArgs);
        constructorArgBuilder.add(new Arguments("OutputStream", "Stream"));

        final ImmutableList.Builder<QueryParam> queryParamsBuilder = ImmutableList.builder();
        queryParamsBuilder.addAll(queryParams);
        queryParamsBuilder.addAll(argsToQueryParams(optionalArgs));

        final ImmutableList.Builder<Arguments> assignmentsBuilder = ImmutableList.builder();
        assignmentsBuilder.addAll(constructorArgs);
        assignmentsBuilder.addAll(optionalArgs);

        final ImmutableList<Arguments> updatedConstructorArgs = constructorArgBuilder.build();

        final ImmutableList<String> argNames = updatedConstructorArgs.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        final ImmutableList<String> additionalLines = ImmutableList.of("this.channel = Channels.newChannel(stream);");

        final ImmutableList<ConstructorParam> constructorParams = toConstructorParams(updatedConstructorArgs);

        final ImmutableList<Precondition> preconditions = toPreconditions(constructorParams);

        return new RequestConstructor(
                false,
                additionalLines,
                constructorParams,
                assignmentsBuilder.build(),
                queryParamsBuilder.build(),
                preconditions,
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }
}
