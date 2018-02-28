/*
 * ******************************************************************************
 *   Copyright 2014-2018 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode
import com.spectralogic.ds3autogen.go.models.response.ResponseCode
import com.spectralogic.ds3autogen.go.utils.goIndent

class HeadObjectResponseGenerator : BaseResponseGenerator() {

    override fun toResponsePayloadStruct(expectedResponseCodes: ImmutableList<Ds3ResponseCode>?): String {
        return "BlobChecksumType ChecksumType\n" +
                goIndent(1) + "BlobChecksums map[int64]string"
    }

    /**
     * Converts a Ds3ResponseCode into a ResponseCode model which contains the Go
     * code for parsing the specified response.
     */
    override fun toResponseCode(ds3ResponseCode: Ds3ResponseCode, responseName: String): ResponseCode {
        if (ds3ResponseCode.code == 200) {
            val parsingCode = "checksumType, err := getBlobChecksumType(webResponse.Header())\n" +
                    goIndent(2) + "if err != nil {\n" +
                    goIndent(3) + "return nil, err\n" +
                    goIndent(2) + "}\n" +
                    goIndent(2) + "checksumMap, err := getBlobChecksumMap(webResponse.Header())\n" +
                    goIndent(2) + "if err != nil {\n" +
                    goIndent(3) + "return nil, err\n" +
                    goIndent(2) + "}\n" +
                    goIndent(2) + "return &$responseName{BlobChecksumType: checksumType, BlobChecksums: checksumMap, Headers: webResponse.Header()}, nil"
            return ResponseCode(ds3ResponseCode.code, parsingCode)
        }
        return toStandardResponseCode(ds3ResponseCode, responseName)
    }
}