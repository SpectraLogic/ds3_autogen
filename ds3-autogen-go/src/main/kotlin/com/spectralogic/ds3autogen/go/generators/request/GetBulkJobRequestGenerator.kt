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
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator

/**
 * Go generator for request handler GetBulkJobSpectraS3Request which has two constructors
 * special cased within template get_bulk_job_request.
 */
class GetBulkJobRequestGenerator : BaseRequestGenerator() {

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
}