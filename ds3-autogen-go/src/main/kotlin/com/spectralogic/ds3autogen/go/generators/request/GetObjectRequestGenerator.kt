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
import com.spectralogic.ds3autogen.go.models.request.Variable
import com.spectralogic.ds3autogen.go.models.request.VariableInterface
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator

/**
 * Request generator for Amazon Get Object command
 */
open class GetObjectRequestGenerator : BaseRequestGenerator() {

    /**
     * Retrieves the list of parameters that make up the Go request struct,
     * including checksum and range headers which are not represented within
     * the Ds3Request
     */
    override fun toStructParams(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val structParams = structParamsFromRequest(ds3Request)

        val builder = ImmutableList.builder<Arguments>()
        builder.addAll(structParams)
        builder.add(Arguments("*rangeHeader", "rangeHeader"))
        builder.add(Arguments("networking.Checksum", "checksum"))

        // Sort the arguments
        return ImmutableList.sortedCopyOf(CustomArgumentComparator(), builder.build())
    }

    /**
     * Retrieves the list of struct assignments that are performed within the
     * Go request constructor, including checksum which is not represented within
     * the Ds3Request
     */
    override fun toStructAssignmentParams(ds3Request: Ds3Request): ImmutableList<VariableInterface> {
        val assignments = structAssignmentParamsFromRequest(ds3Request)

        val builder = ImmutableList.builder<VariableInterface>()
        builder.addAll(assignments)
        builder.add(Variable("checksum", "networking.NewNoneChecksum()"))

        return builder.build()
    }

    /**
     * Retrieves imports that are not present in all request files
     */
    override fun toImportSet(ds3Request: Ds3Request): ImmutableSet<String> {
        val builder = ImmutableSet.Builder<String>()
        builder.add("fmt")
        if (isStrconvImportRequired(ds3Request.requiredQueryParams) || isStrconvImportRequired(ds3Request.optionalQueryParams)) {
            builder.add("strconv")
        }
        return builder.build()
    }
}
