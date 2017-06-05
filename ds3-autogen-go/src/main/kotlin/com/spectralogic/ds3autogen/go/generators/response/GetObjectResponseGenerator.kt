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

package com.spectralogic.ds3autogen.go.generators.response

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.google.common.collect.ImmutableSet
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type
import com.spectralogic.ds3autogen.go.models.response.ResponseCode

/**
 * Generates the Go Amazon Get Object response handler
 */
class GetObjectResponseGenerator : BaseResponseGenerator() {

    override fun toImportSet(): ImmutableSet<String> {
        return ImmutableSet.of("io")
    }

    /**
     * Retrieves the response payload struct content, which is an io.ReadCloser
     */
    override fun toResponsePayloadStruct(expectedResponseCodes: ImmutableList<Ds3ResponseCode>?): String {
        return "Content io.ReadCloser"
    }

    /**
     * Converts a Ds3ResponseCode into a ResponseCode model which contains the Go
     * code for parsing the specified response.
     */
    override fun toResponseCode(ds3ResponseCode: Ds3ResponseCode, responseName: String): ResponseCode {
        if (ds3ResponseCode.code == 200) {
            return ResponseCode(ds3ResponseCode.code, "return &$responseName{ Content: webResponse.Body(), Headers: webResponse.Header() }, nil")
        }
        return toStandardResponseCode(ds3ResponseCode, responseName)
    }
}