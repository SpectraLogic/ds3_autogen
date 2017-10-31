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

package com.spectralogic.ds3autogen.go.generators.client

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb
import com.spectralogic.ds3autogen.go.generators.client.command.BaseCommandGenerator
import com.spectralogic.ds3autogen.go.generators.client.command.CommandModelGenerator
import com.spectralogic.ds3autogen.go.generators.client.command.GetObjectCommandGenerator
import com.spectralogic.ds3autogen.go.generators.client.command.PutObjectCommandGenerator
import com.spectralogic.ds3autogen.go.models.client.Client
import com.spectralogic.ds3autogen.go.models.client.Command
import com.spectralogic.ds3autogen.utils.ConverterUtil
import com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isAmazonCreateObjectRequest
import com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isGetObjectAmazonS3Request
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors

open class BaseClientGenerator : ClientModelGenerator<Client> {

    override fun generate(ds3Requests: ImmutableList<Ds3Request>): Client {
        val commandsNoRedirect = toCommandList(ds3Requests, false)
        val commandsWithRedirect = toCommandList(ds3Requests, true)

        return Client(commandsNoRedirect, commandsWithRedirect)
    }

    /**
     * Converts a list of Ds3Requests into a list of Command objects filtering
     * the commands based on whether they handle http redirects or not within
     * the generated Go client code
     */
    fun toCommandList(ds3Requests: ImmutableList<Ds3Request>, httpRedirect: Boolean): ImmutableList<Command> {
        if (ConverterUtil.isEmpty(ds3Requests)) {
            return ImmutableList.of()
        }

        return ds3Requests.stream()
                .filter { request -> hasHttpRedirect(request) == httpRedirect }
                .map { request -> toCommand(request) }
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Determines if a command should handle http temporary redirects within generated
     * Go client code
     */
    fun hasHttpRedirect(ds3Request: Ds3Request): Boolean {
        return ds3Request.httpVerb == HttpVerb.GET && !isGetObjectAmazonS3Request(ds3Request)
    }

    /**
     * Converts a Ds3Request into a Command
     */
    fun toCommand(ds3Request: Ds3Request): Command {
        val generator = getCommandGenerator(ds3Request)
        return generator.generate(ds3Request)
    }

    /**
     * Retrieves the command generator for the specified Ds3Request.
     */
    fun getCommandGenerator(ds3Request: Ds3Request): CommandModelGenerator<*> {
        return when {
            isAmazonCreateObjectRequest(ds3Request) -> PutObjectCommandGenerator()
            isGetObjectAmazonS3Request(ds3Request) -> GetObjectCommandGenerator()
            else -> BaseCommandGenerator()
        }
    }
}
