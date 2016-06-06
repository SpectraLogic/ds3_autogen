/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.generators.request;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.RequestPayload;

/**
 * Creates the python request model for commands with string request payloads
 */
public class StringPayloadGenerator extends BaseRequestGenerator {

    private static final String PAYLOAD_NAME = "request_payload";
    private static final String ASSIGN_PAYLOAD = "self.body = " + PAYLOAD_NAME;

    /**
     * Gets the request payload model that handles string and stream payload types
     */
    @Override
    public RequestPayload toRequestPayload(final Ds3Request ds3Request, final String requestName) {
        return new RequestPayload(PAYLOAD_NAME, ASSIGN_PAYLOAD, false);
    }
}
