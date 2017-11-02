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
 * Go generator for client commands that take in a list of IDs and
 * converts them into the request payload:
 *
 * Used to generate the commands:
 *   ClearSuspectBlobAzureTargetsRequestHandler
 *   ClearSuspectBlobPoolsRequestHandler
 *   ClearSuspectBlobS3TargetsRequestHandler
 *   ClearSuspectBlobTapesRequestHandler
 *   MarkSuspectBlobAzureTargetsAsDegradedRequestHandler
 *   MarkSuspectBlobDs3TargetsAsDegradedRequestHandler
 *   MarkSuspectBlobPoolsAsDegradedRequestHandler
 *   MarkSuspectBlobS3TargetsAsDegradedRequestHandler
 *   MarkSuspectBlobTapesAsDegradedRequestHandler
 */
class IdsPayloadCommandGenerator : BaseCommandGenerator() {

    /**
     * Retrieves the request builder line for adding the request payload,
     * which is an xml marshaled list of ids.
     */
    override fun toReaderBuildLine(): Optional<RequestBuildLine> {
        return Optional.of(ReadCloserBuildLine("buildIdListPayload(request.Ids)"))
    }
}