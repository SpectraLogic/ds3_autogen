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
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toConstructorDocs;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;

public class MultiFileDeleteRequestGenerator extends BaseRequestGenerator {

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
        final ImmutableList<QueryParam> queryParams = toQueryParamsList(ds3Request);
        final ImmutableList.Builder<RequestConstructor> builder = ImmutableList.builder();

        builder.add(createObjectsConstructor(constructorArgs, queryParams, requestName, docSpec));
        builder.add(createIterableConstructor(constructorArgs, queryParams, requestName, docSpec));

        return builder.build();
    }

    /**
     * Creates the Multi File Delete constructor that uses objects
     */
    protected static RequestConstructor createObjectsConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(constructorArgs);
        builder.add(new Arguments("List<String>", "Objects"));

        final ImmutableList<Arguments> updatedArgs = builder.build();
        final ImmutableList<String> argNames = updatedArgs.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        return new RequestConstructor(
                updatedArgs,
                updatedArgs,
                queryParams,
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }

    /**
     * Creates the Multi File Delete constructor that uses Iterables
     */
    protected static RequestConstructor createIterableConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<QueryParam> queryParams,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();

        if (hasContent(constructorArgs)) {
            constructorArgs.stream()
                    .filter(arg -> !arg.getName().equalsIgnoreCase("Delete"))
                    .forEach(builder::add);
        }

        builder.add(new Arguments("Iterable<Contents>", "Objs"));

        final ImmutableList<String> additionalLines = ImmutableList.of(
                "this.objects = contentsToString(objs);");

        final ImmutableList<String> argNames = builder.build().stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        return new RequestConstructor(
                false,
                additionalLines,
                builder.build(),
                constructorArgs,
                queryParams,
                toConstructorDocs(requestName, argNames, docSpec, 1));
    }
}
