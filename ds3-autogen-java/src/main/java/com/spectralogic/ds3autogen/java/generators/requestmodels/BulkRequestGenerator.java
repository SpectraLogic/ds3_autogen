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
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import com.spectralogic.ds3autogen.java.models.Variable;
import com.spectralogic.ds3autogen.java.models.withconstructor.BaseWithConstructor;
import com.spectralogic.ds3autogen.java.models.withconstructor.BulkWithConstructor;
import com.spectralogic.ds3autogen.java.models.withconstructor.MaxUploadSizeWithConstructor;
import com.spectralogic.ds3autogen.java.models.withconstructor.VoidWithConstructor;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toConstructorDocs;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BulkRequestGenerator extends BaseRequestGenerator {

    private final static String BULK_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.interfaces.BulkRequest";

    /**
     * List of class arguments that are defined within the parent class BulkRequest.
     * This is used to determine which arguments should not be generated as child
     * class variables with associated getters.
     */
    private final static ImmutableSet<String> bulkBaseClassArgs = ImmutableSet.of(
            "Priority",
            "WriteOptimization",
            "BucketName",
            "Objects");

    /**
     * Returns the import for the parent class for bulk requests, which is BulkRequest
     */
    @Override
    public String getParentImport(final Ds3Request ds3Request) {
        return BULK_REQUEST_IMPORT;
    }

    /**
     * Gets the list of Arguments need to create the request constructor. This includes
     * all non-void required parameters, arguments described within the request header
     * information, and a list of Ds3Objects called Objects.
     */
    @Override
    public ImmutableList<Arguments> toConstructorArgumentsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(super.toConstructorArgumentsList(ds3Request));

        final Arguments objects = new Arguments("Iterable<Ds3Object>", "Objects");
        builder.add(objects);

        return builder.build();
    }

    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions. This includes all arguments described within the optional
     * query parameters and constructor arguments, excluding the arguments that are
     * defined within the parent class BulkRequest
     */
    @Override
    public ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();

        toConstructorArgumentsList(ds3Request).stream()
                .filter(arg -> !isBulkRequestArg(arg.getName()))
                .forEach(arg -> builder.add(new Variable(arg.getName(), arg.getType(), true)));

        toOptionalArgumentsList(ds3Request.getOptionalQueryParams()).stream()
                .filter(arg -> !isBulkRequestArg(arg.getName()) && !arg.getName().equals("MaxUploadSize"))
                .forEach(arg -> builder.add(new Variable(arg.getName(), arg.getType(), false)));

        return builder.build();
    }

    /**
     * Determines if the name of an argument is a parameter within the parent class BulkRequest.
     * This is used to determine if a private variable needs to be created within a bulk child
     * class or if said variable is inherited from the parent BulkRequest class.
     */
    public static boolean isBulkRequestArg(final String argName) {
        return bulkBaseClassArgs.contains(argName);
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
     * constructor. This excludes all arguments passed to the super constructor
     */
    protected static ImmutableList<Arguments> toConstructorAssignmentList(
            final ImmutableList<Arguments> constructorArguments) {
        if (isEmpty(constructorArguments)) {
            return ImmutableList.of();
        }
        return constructorArguments.stream()
                .filter(arg -> !bulkBaseClassArgs.contains(arg.getName()))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Creates the Java code for With-Constructors for optional arguments within
     * bulk request handlers. If said argument is defined within the base BulkRequest
     * handler, then the With-Constructor is generated with "@Override".  A special
     * With-Constructor is created for the parameter MaxUploadSize.
     */
    @Override
    public String toWithConstructor(
            final Arguments param,
            final String requestName,
            final Ds3DocSpec docSpec) {
        final String documentation = toConstructorDocs(requestName, ImmutableList.of(param.getName()), docSpec, 1);
        if (isBulkRequestArg(param.getName())) {
            return formatDocumentation(documentation) + new BulkWithConstructor(param, requestName).toJavaCode();
        }
        if (param.getName().equals("MaxUploadSize")) {
            return formatDocumentation(documentation) + new MaxUploadSizeWithConstructor(param, requestName).toJavaCode();
        }
        if (param.getType().equals("void")) {
            return formatDocumentation(documentation) + new VoidWithConstructor(param, requestName).toJavaCode();
        }
        return formatDocumentation(documentation) + new BaseWithConstructor(param, requestName).toJavaCode();
    }
}
