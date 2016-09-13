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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.models.Variable;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.argsToQueryParams;
import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toConstructorDocs;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class CreateNotificationRequestGenerator extends BaseRequestGenerator {

    private final static String ABSTRACT_CREATE_NOTIFICATION_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.interfaces.AbstractCreateNotificationRequest";

    /**
     * Returns the import for the parent class for the create notification request,
     * which is AbstractCreateNotificationRequest
     */
    @Override
    public String getParentImport(final Ds3Request ds3Request) {
        return ABSTRACT_CREATE_NOTIFICATION_REQUEST_IMPORT;
    }

    /**
     * Gets the list of required Arguments from a Ds3Request, excluding the parameter
     * passed to the abstract class: NotificationEndPoint. This is done to prevent it
     * from being added to the query parameters within the constructor.
     * @param ds3Request A Ds3Request
     * @return A list of required Arguments
     */
    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request) {
        return toArgumentsList(ds3Request.getRequiredQueryParams()).stream()
                .filter(arg -> !arg.getName().equals("NotificationEndPoint"))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions, excluding the NotificationEndPoint variable
     */

    @Override
    public ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();

        toConstructorArgumentsList(ds3Request).stream()
                .filter(arg -> !arg.getName().equals("NotificationEndPoint"))
                .forEach(arg -> builder.add(new Variable(arg.getName(), arg.getType(), true)));

        toOptionalArgumentsList(ds3Request.getOptionalQueryParams()).stream()
                .filter(arg -> !arg.getName().equals("NotificationEndPoint"))
                .forEach(arg -> builder.add(new Variable(arg.getName(), arg.getType(), false)));

        return builder.build();
    }

    /**
     * Gets the list of constructor models from a Ds3Request
     */
    @Override
    public ImmutableList<RequestConstructor> toConstructorList(
            final Ds3Request ds3Request,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final ImmutableList<Arguments> constructorArguments = toConstructorArgumentsList(ds3Request);

        final ImmutableList<String> argNames = constructorArguments.stream()
                .map(Arguments::getName)
                .collect(GuavaCollectors.immutableList());

        final RequestConstructor constructor = new RequestConstructor(
                constructorArguments,
                toConstructorAssignmentList(constructorArguments),
                toQueryParamsList(ds3Request),
                toConstructorDocs(requestName, argNames, docSpec, 1));

        return ImmutableList.of(constructor);
    }

    /**
     * Gets the list of constructor arguments that need to be assigned within the
     * constructor. This excludes the argument NotificationEndPoint which is passed
     * to the super constructor
     */
    protected static ImmutableList<Arguments> toConstructorAssignmentList(
            final ImmutableList<Arguments> constructorArguments) {
        if (isEmpty(constructorArguments)) {
            return ImmutableList.of();
        }
        return constructorArguments.stream()
                .filter(arg -> !arg.getName().equalsIgnoreCase("NotificationEndPoint"))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Creates the list of arguments that are added to the query params within
     * the constructors
     */
    @Override
    public ImmutableList<QueryParam> toQueryParamsList(final Ds3Request ds3Request) {
        return argsToQueryParams(toRequiredArgumentsList(ds3Request));
    }
}
