package com.spectralogic.ds3autogen.go.generators.request

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.go.models.request.Variable
import com.spectralogic.ds3autogen.go.models.request.VariableInterface
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator

/**
 * Generates the Go request handler for CompleteBlob command.
 * Special case: include checksum and metadata as optional parameters in request.
 */
class CompleteBlobRequestGenerator : BaseRequestGenerator() {

    /**
     * Creates the list parameters in the request struct.
     * Include checksum and metadata.
     */
    override fun toStructParams(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder = ImmutableList.builder<Arguments>()
        builder.addAll(structParamsFromRequest(ds3Request))
        builder.add(Arguments("Checksum", "Checksum"))
        builder.add(Arguments("map[string]string", "Metadata"))

        // Sort the arguments
        return ImmutableList.sortedCopyOf(CustomArgumentComparator(), builder.build())
    }

    /**
     * Creates the list of parameters assigned in the constructor.
     * Include empty initialization for checksum and metadata.
     */
    override fun toStructAssignmentParams(ds3Request: Ds3Request): ImmutableList<VariableInterface> {
        val builder = ImmutableList.builder<VariableInterface>()
        builder.addAll(structAssignmentParamsFromRequest(ds3Request))
        builder.add(Variable("Checksum", "NewNoneChecksum()"))
        builder.add(Variable("Metadata", "make(map[string]string)"))

        return builder.build()
    }
}