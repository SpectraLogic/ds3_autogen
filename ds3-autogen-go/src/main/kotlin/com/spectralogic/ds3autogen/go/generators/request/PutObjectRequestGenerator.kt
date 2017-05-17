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
import com.google.common.collect.ImmutableSet
import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.go.models.request.SimpleVariable
import com.spectralogic.ds3autogen.go.models.request.Variable
import com.spectralogic.ds3autogen.go.models.request.VariableInterface
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator

/**
 * Generates the Go request handler for Amazon PutObject command
 */
class PutObjectRequestGenerator : BaseRequestGenerator() {

    /**
     * Retrieves imports that are not present in all request files
     */
    override fun toImportSet(ds3Request: Ds3Request): ImmutableSet<String> {
        val builder = ImmutableSet.builder<String>()
        builder.add("strings")
        if (isStrconvImportRequired(ds3Request.requiredQueryParams) || isStrconvImportRequired(ds3Request.optionalQueryParams)) {
            builder.add("strconv")
        }
        return builder.build()
    }

    /**
     * Creates the list of arguments that composes the request handler struct,
     * including the content reader, checksum, and headers.
     */
    override fun toStructParams(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder = ImmutableList.builder<Arguments>()
        builder.addAll(structParamsFromRequest(ds3Request))
        builder.add(Arguments("networking.ReaderWithSizeDecorator", "content"))
        builder.add(Arguments("networking.Checksum", "checksum"))
        builder.add(Arguments("*http.Header", "headers"))

        // Sort the arguments
        return builder.build().stream()
                .sorted(CustomArgumentComparator())
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Creates the list of constructor parameters, including the content reader
     */
    override fun toConstructorParamsList(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder = ImmutableList.builder<Arguments>()
        builder.addAll(paramsListFromRequest(ds3Request))
        builder.add(Arguments("networking.ReaderWithSizeDecorator", "content"))
        return builder.build()
    }

    /**
     * Creates the list of variables that are assigned to the request handler struct
     * within the constructor. This includes the content reader and setting the
     * default checksum and header values.
     */
    override fun toStructAssignmentParams(ds3Request: Ds3Request): ImmutableList<VariableInterface> {
        val builder = ImmutableList.builder<VariableInterface>()
        builder.addAll(structAssignmentParamsFromRequest(ds3Request))
        builder.add(SimpleVariable("content"))
        builder.add(Variable("checksum", "networking.NewNoneChecksum()"))
        builder.add(Variable("headers", "&http.Header{}"))

        return builder.build()
    }
}