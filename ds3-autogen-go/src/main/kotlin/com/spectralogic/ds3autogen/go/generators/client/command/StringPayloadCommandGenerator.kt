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

import com.spectralogic.ds3autogen.go.models.client.ReadCloserBuildLine
import com.spectralogic.ds3autogen.go.models.client.RequestBuildLine
import java.util.*

/**
 * The Go generator for client commands that have a string request payload
 * that is not specified within the contract.
 *
 * Used to generate the commands:
 *   GetBlobPersistenceRequest
 *   ReplicatePutJobRequest
 */
class StringPayloadCommandGenerator : BaseCommandGenerator() {

    /**
     * Retrieves the request builder line for adding the request payload in string format.
     */
    override fun toReaderBuildLine(): Optional<RequestBuildLine> {
        return Optional.of(ReadCloserBuildLine("buildStreamFromString(request.RequestPayload)"))
    }
}