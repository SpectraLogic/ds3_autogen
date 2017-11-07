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
        builder.add(Arguments("map[string]string", "Metadata"))
        builder.add(Arguments("networking.Checksum", "Checksum"))

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
        builder.add(Variable("Checksum", "networking.NewNoneChecksum()"))

        return builder.build()
    }
}
