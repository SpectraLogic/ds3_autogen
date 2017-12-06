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

package com.spectralogic.ds3autogen.go.generators.client.command

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.api.models.enums.Operation
import com.spectralogic.ds3autogen.go.generators.client.goPtrQueryParamToStringPtr
import com.spectralogic.ds3autogen.go.generators.client.goQueryParamToString
import com.spectralogic.ds3autogen.go.generators.client.toRequestPath
import com.spectralogic.ds3autogen.go.models.client.*
import com.spectralogic.ds3autogen.go.utils.toGoParamName
import com.spectralogic.ds3autogen.go.utils.toGoRequestType
import com.spectralogic.ds3autogen.utils.ClientGeneratorUtil
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty
import com.spectralogic.ds3autogen.utils.Helper
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import java.util.*

open class BaseCommandGenerator : CommandModelGenerator<Command>, CommandGeneratorUtil {

    override fun generate(ds3Request: Ds3Request): Command {
        return Command(
                ClientGeneratorUtil.toCommandName(ds3Request.name),
                toRequestBuildLines(ds3Request))
    }

    /**
     * Creates the list of request handler build lines required to construct the
     * http request using a builder within the client. This excludes the request
     * builder initialization (first line) and the build command (last line).
     */
    fun toRequestBuildLines(ds3Request: Ds3Request): ImmutableList<RequestBuildLine> {
        val builder = ImmutableList.builder<RequestBuildLine>()
        builder.add(HttpVerbBuildLine(ds3Request.httpVerb))
        builder.add(PathBuildLine(ds3Request.toRequestPath()))
        builder.addAll(toRequiredQueryParamBuildLines(ds3Request.requiredQueryParams))
        builder.addAll(toOptionalQueryParamBuildLines(ds3Request.optionalQueryParams))

        // Add optional build lines
        toOperationBuildLine(ds3Request.operation).ifPresent { line -> builder.add(line) }
        toReaderBuildLine().ifPresent{ line -> builder.add(line) }
        toChecksumBuildLine().ifPresent{ line -> builder.add(line) }
        toHeadersBuildLine().ifPresent { line -> builder.add(line) }
        return builder.build()
    }

    /**
     * Retrieves the request builder lines for adding required query parameters.
     */
    override fun toRequiredQueryParamBuildLines(requiredParams: ImmutableList<Ds3Param>?): ImmutableList<RequestBuildLine> {
        if (isEmpty(requiredParams)) {
            return ImmutableList.of()
        }

        return requiredParams!!.stream()
                .filter { param -> param.name != "Operation" }
                .map { param -> toRequiredQueryParamBuildLine(param) }
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Generates the request builder line for adding this required query parameter.
     */
    fun toRequiredQueryParamBuildLine(ds3Param: Ds3Param): RequestBuildLine {
        val goType = toGoRequestType(ds3Param.type, nullable = false)
        val goName = toGoParamName(ds3Param.name, ds3Param.type)
        val key = Helper.camelToUnderscore(ds3Param.name)
        val value = goQueryParamToString(goName, goType)
        return QueryParamBuildLine(key, value)
    }

    /**
     * Retrieves the request builder lines for adding optional query parameters.
     */
    override fun toOptionalQueryParamBuildLines(optionalParams: ImmutableList<Ds3Param>?): ImmutableList<RequestBuildLine> {
        if (isEmpty(optionalParams)) {
            return ImmutableList.of()
        }

        return optionalParams!!.stream()
                .map { param -> toOptionalQueryParamBuildLine(param) }
                .collect(GuavaCollectors.immutableList())
    }


    /**
     * Converts a Ds3Param into an optional query parameter RequestBuildLine.
     */
    fun toOptionalQueryParamBuildLine(ds3Param: Ds3Param): RequestBuildLine {
        val goType = toGoRequestType(ds3Param.type, nullable = true)
        val goName = toGoParamName(ds3Param.name, ds3Param.type)
        val key = Helper.camelToUnderscore(ds3Param.name)

        if (ds3Param.type.toLowerCase() == "void") {
            return VoidOptionalQueryParamBuildLine(key, goName)
        }

        val value = goPtrQueryParamToStringPtr(goName, goType)
        return OptionalQueryParamBuildLine(key, value)
    }

    /**
     * Retrieves the request builder line for adding the operation query param if the command
     * specifies an operation. Else, it returns an empty optional.
     */
    fun toOperationBuildLine(operation: Operation?): Optional<RequestBuildLine> {
        if (operation == null) {
            return Optional.empty()
        }
        return Optional.of(OperationBuildLine(operation))
    }

    /**
     * Retrieves the request builder line for adding a reader/read closer if the command has
     * a request payload. The base command does not have a request payload.
     */
    override fun toReaderBuildLine(): Optional<RequestBuildLine> {
        return Optional.empty()
    }

    /**
     * Retrieves the request builder line for adding checksum if the command supports checksumming.
     * The base command does not have checksumming.
     */
    override fun toChecksumBuildLine(): Optional<RequestBuildLine> {
        return Optional.empty()
    }

    /**
     * Retrieves the request builder line for adding headers if the command has headers.
     * The base command does not have any headers.
     */
    override fun toHeadersBuildLine(): Optional<RequestBuildLine> {
        return Optional.empty()
    }
}
