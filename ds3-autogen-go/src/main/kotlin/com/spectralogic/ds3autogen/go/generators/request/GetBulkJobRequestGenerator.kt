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