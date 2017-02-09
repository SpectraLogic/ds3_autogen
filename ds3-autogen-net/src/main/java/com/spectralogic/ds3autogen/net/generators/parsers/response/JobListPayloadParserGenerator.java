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

package com.spectralogic.ds3autogen.net.generators.parsers.response;

/**
 * Generates the BaseParser model for requests that have a JobList response payload.
 */
public class JobListPayloadParserGenerator extends BaseResponseParserGenerator {
    
    /**
     * The name to marshal value for the JobList payload is Jobs, which is not
     * specified within the contract
     */
    @Override
    public String toNameToMarshal(final String nameToMarshal, final String typeName) {
        return "Jobs";
    }
}
