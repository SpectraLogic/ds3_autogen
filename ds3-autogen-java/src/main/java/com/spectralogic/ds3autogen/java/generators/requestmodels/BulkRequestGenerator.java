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
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.Variable;

public class BulkRequestGenerator extends BaseRequestGenerator {

    private final static String BULK_REQUEST_IMPORT = "com.spectralogic.ds3client.commands.BulkRequest";

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

    //TODO unit test
    /**
     * Returns the import for the parent class for bulk requests, which is BulkRequest
     */
    @Override
    protected String getParentImport() {
        return BULK_REQUEST_IMPORT;
    }

    //TODO unit test
    /**
     * Gets the list of Arguments need to create the request constructor. This includes
     * all non-void required parameters, arguments described within the request header
     * information, and a list of Ds3Objects called Objects.
     */
    @Override
    protected ImmutableList<Arguments> toConstructorArgumentsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(super.toConstructorArgumentsList(ds3Request));

        final Arguments objects = new Arguments("List<Ds3Object>", "Objects");
        builder.add(objects);

        return builder.build();
    }

    //TODO unit test
    /**
     * Gets all the class variables to properly generate the variables and their
     * getter functions. This includes all arguments described within the optional
     * query parameters and constructor arguments, excluding the arguments that are
     * defined within the parent class BulkRequest
     */
    @Override
    protected ImmutableList<Variable> toClassVariableArguments(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Variable> builder = ImmutableList.builder();

        for (final Arguments arg : toConstructorArgumentsList(ds3Request)) {
            if (!isBulkRequestArg(arg.getName())) {
                builder.add(new Variable(arg.getName(), arg.getType(), true));
            }
        }
        for (final Arguments arg : toOptionalArgumentsList(ds3Request.getOptionalQueryParams())) {
            if (!isBulkRequestArg(arg.getName()) && !arg.getName().equals("MaxUploadSize")) {
                builder.add(new Variable(arg.getName(), arg.getType(), false));
            }
        }
        return builder.build();
    }

    //TODO unit test
    /**
     * Determines if the name of an argument is a parameter within the parent class BulkRequest.
     * This is used to determine if a private variable needs to be created within a bulk child
     * class or if said variable is inherited from the parent BulkRequest class.
     */
    public static boolean isBulkRequestArg(final String argName) {
        return bulkBaseClassArgs.contains(argName);
    }
}
