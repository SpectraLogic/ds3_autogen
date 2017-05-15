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

package com.spectralogic.ds3autogen.go.generators.request

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.go.models.request.Variable
import com.spectralogic.ds3autogen.go.models.request.VariableInterface
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator

/**
 * Abstract generator for requests with payloads
 */
abstract class RequestPayloadGenerator : BaseRequestGenerator() {

    /**
     * Creates the list of arguments that composes the request handler struct,
     * including contents which contains the request payload in the generated code.
     */
    override fun toStructParams(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder = ImmutableList.builder<Arguments>()
        builder.addAll(structParamsFromRequest(ds3Request))
        builder.add(Arguments("networking.ReaderWithSizeDecorator", "content"))

        // Sort the arguments
        return builder.build().stream()
                .sorted(CustomArgumentComparator())
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Creates the list of constructor parameters, including a list of Ds3Objects.
     */
    override fun toConstructorParamsList(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder = ImmutableList.builder<Arguments>()
        builder.addAll(paramsListFromRequest(ds3Request))
        builder.add(getPayloadConstructorArg())
        return builder.build()
    }

    /**
     * Creates the list of variables that are assigned to the request handler struct
     * within the constructor. In the generated code, the Ds3Object list is converted
     * into a stream before being assigned to the content variable.
     */
    override fun toStructAssignmentParams(ds3Request: Ds3Request): ImmutableList<VariableInterface> {
        val builder = ImmutableList.builder<VariableInterface>()
        builder.addAll(structAssignmentParamsFromRequest(ds3Request))
        builder.add(getStructAssignmentVariable())

        return builder.build()
    }

    /**
     * Retrieves the parameter representing the request payload passed into the constructor
     */
    abstract fun getPayloadConstructorArg(): Arguments

    /**
     * Retrieves the variable that holds the struct assignment for the request payload type
     */
    abstract fun getStructAssignmentVariable(): Variable
}