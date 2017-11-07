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

import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.go.models.request.Variable

/**
 * The Go generator for request handlers that have a CompleteMultipartUpload
 * request payload not specified within the contract.
 */

class PartsRequestPayloadGenerator : RequestPayloadGenerator() {

    /**
     * Retrieves the CompleteMultipartUpload request payload
     */
    override fun getPayloadArgument(): Arguments {
        return Arguments("[]Part", "parts")
    }

    /**
     * Retrieves the struct assignment for the CompleteMultipartUpload request payload
     */
    override fun getStructAssignmentVariable(): Variable {
        return Variable("Parts", "parts")
    }
}