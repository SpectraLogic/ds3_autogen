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
import com.google.common.collect.ImmutableSet
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode
import com.spectralogic.ds3autogen.go.models.response.Response
import com.spectralogic.ds3autogen.go.models.response.ResponseCode
import com.spectralogic.ds3autogen.go.utils.goIndent
import com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath
import com.spectralogic.ds3autogen.utils.ResponsePayloadUtil
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import java.util.stream.Collectors

open class BaseResponseGenerator : ResponseModelGenerator<Response>, ResponseModelGeneratorUtil {

    /** Status codes are considered errors at this value and higher */
    private val ERROR_THRESHOLD = 300

    override fun generate(ds3Request: Ds3Request): Response {

        val name = NormalizingContractNamesUtil.toResponseName(ds3Request.name)
        val payloadStruct = toResponsePayloadStruct(ds3Request.ds3ResponseCodes)
        val expectedCodes = toExpectedStatusCodes(ds3Request.ds3ResponseCodes)
        val parseResponseMethod = toParseResponseMethod(name, payloadStruct, ds3Request.ds3ResponseCodes)
        val responseCodes = toResponseCodeList(ds3Request.ds3ResponseCodes, name)
        val imports = toImportSet()

        return Response(name, payloadStruct, expectedCodes, parseResponseMethod, responseCodes, imports)
    }

    /**
     * Creates the Go code for the response parse method if one is needed (i.e. there is a response payload
     * which is defined by a Ds3Type). Else an empty string is returned.
     */
    fun toParseResponseMethod(name: String, payloadStruct: String, ds3ResponseCodes: ImmutableList<Ds3ResponseCode>?): String {
        if (isEmpty(ds3ResponseCodes) || !ResponsePayloadUtil.hasResponsePayload(ds3ResponseCodes)) {
            return ""
        }

        val elementName = removePath(getResponsePayload(ds3ResponseCodes))
        if (elementName == "String" || elementName == "java.lang.String") {
            return ""
        }

        val modelName = name.decapitalize()
        val dereference = toDereferenceResponsePayload(payloadStruct)
        return "func ($modelName *$name) parse(webResponse WebResponse) error {\n" +
                goIndent(1) + "    return parseResponsePayload(webResponse, $dereference$modelName.$elementName)\n" +
                "}"
    }

    /**
     * Determines if a response payload needs to be de-referenced (i.e. not a pointer), or if
     * it needs de-referencing in order to be parsed. Returns an ampersand '&' if de-referencing
     * is needed, else it returns an empty string.
     */
    fun toDereferenceResponsePayload(payloadStruct: String): String {
        if (payloadStruct.contains("*")) {
            return ""
        }
        return "&"
    }
    
    /**
     * The standard response has all of its imports specified within the template file
     */
    override fun toImportSet(): ImmutableSet<String> {
        return ImmutableSet.of()
    }

    /**
     * Converts a list of Ds3ResponseCodes into a list of ResponseCode models
     */
    fun toResponseCodeList(ds3ResponseCodes: ImmutableList<Ds3ResponseCode>?, responseName: String): ImmutableList<ResponseCode> {
        if (isEmpty(ds3ResponseCodes)) {
            throw IllegalArgumentException("There must be at least one non-error response code")
        }
        return ds3ResponseCodes!!.stream()
                .filter{ code -> code.code < ERROR_THRESHOLD }
                .map { code -> toResponseCode(code, responseName) }
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Converts a Ds3ResponseCode into a ResponseCode model which contains the Go
     * code for parsing the specified response.
     */
    override fun toResponseCode(ds3ResponseCode: Ds3ResponseCode, responseName: String): ResponseCode {
        return toStandardResponseCode(ds3ResponseCode, responseName)
    }

    /**
     * Converts a Ds3ResponseCode into one of the standard ResponseCode configurations
     */
    fun toStandardResponseCode(ds3ResponseCode: Ds3ResponseCode, responseName: String): ResponseCode {
        if (isEmpty(ds3ResponseCode.ds3ResponseTypes)) {
            throw IllegalArgumentException("Ds3ResponseCode does not have any response types " + ds3ResponseCode.code)
        }
        if (ds3ResponseCode.ds3ResponseTypes!![0].type.equals("null", ignoreCase = true)) {
            return toNullPayloadResponseCode(ds3ResponseCode.code, responseName)
        }
        if (ds3ResponseCode.ds3ResponseTypes!![0].type.equals("java.lang.String", ignoreCase = true)) {
            return toStringPayloadResponseCode(ds3ResponseCode.code, responseName)
        }
        return toPayloadResponseCode(ds3ResponseCode.code, responseName)
    }

    /**
     * Creates the Go code for returning an empty response when there is no payload
     */
    fun toNullPayloadResponseCode(code: Int, responseName: String): ResponseCode {
        return ResponseCode(code, "return &$responseName{Headers: webResponse.Header()}, nil")
    }

    /**
     * Creates the Go code for returning a string response payload
     */
    fun toStringPayloadResponseCode(code: Int, responseName: String): ResponseCode {
        val parseResponse = "content, err := getResponseBodyAsString(webResponse)\n" +
                goIndent(2) + "if err != nil {\n" +
                goIndent(3) + "return nil, err\n" +
                goIndent(2) + "}\n" +
                goIndent(2) + "return &$responseName{Content: content, Headers: webResponse.Header()}, nil"
        return ResponseCode(code, parseResponse)
    }

    /**
     * Creates the Go code for parsing a response with a payload
     */
    fun toPayloadResponseCode(code: Int, responseName: String): ResponseCode {
        val parseResponse = "var body $responseName\n" +
                goIndent(2) + "if err := body.parse(webResponse); err != nil {\n" +
                goIndent(3) + "return nil, err\n" +
                goIndent(2) + "}\n" +
                goIndent(2) + "body.Headers = webResponse.Header()\n" +
                goIndent(2) + "return &body, nil"

        return ResponseCode(code, parseResponse)
    }

    /**
     * Retrieves the comma separated list of expected status codes
     */
    fun toExpectedStatusCodes(responseCodes: ImmutableList<Ds3ResponseCode>?): String {
        if (isEmpty(responseCodes)) {
            return ""
        }
        return responseCodes!!.stream()
                .filter { code -> code.code < ERROR_THRESHOLD }
                .map { code -> code.code.toString() }
                .collect(Collectors.joining(", "))
    }

    /**
     * Retrieves the content of the response struct, which assumes a spectra defined
     * Ds3Type.
     */
    override fun toResponsePayloadStruct(expectedResponseCodes: ImmutableList<Ds3ResponseCode>?): String {
        if (isEmpty(expectedResponseCodes)) {
            throw IllegalArgumentException("Expected at least one response payload")
        }
        val responsePayloadType = getResponsePayload(expectedResponseCodes)

        // Check if response payload is a string
        if (responsePayloadType.equals("java.lang.String", ignoreCase = true)
                || responsePayloadType.equals("string", ignoreCase = true)) {
            return "Content string"
        }

        val responseName = removePath(responsePayloadType)
        val responseType = toResponsePayloadType(responsePayloadType, expectedResponseCodes)
        return "$responseName $responseType"
    }

    /**
     * Determines if a response payload is a struct or a pointer to a struct. When there
     * are multiple response codes, then the type should be a pointer to a struct.
     */
    fun toResponsePayloadType(responsePayload: String, responseCodes: ImmutableList<Ds3ResponseCode>?): String {
        if (isEmpty(responseCodes)) {
            throw IllegalArgumentException("Expected at least one response code")
        }
        if (responseCodes!!.filter { code -> code.code < ERROR_THRESHOLD }.size == 1) {
            return removePath(responsePayload)
        }
        return "*" + removePath(responsePayload)
    }

    /**
     * Retreives the response payload type. There should be exactly one possible response payload
     * per command, and if not, an exception is thrown.
     */
    fun getResponsePayload(ds3ResponseCodes: ImmutableList<Ds3ResponseCode>?): String {
        if (isEmpty(ds3ResponseCodes)) {
            throw IllegalArgumentException("There must exist at least one non-error response code")
        }
        val codeWithPayload = ds3ResponseCodes!!.stream()
                .filter{ code -> code.code < ERROR_THRESHOLD
                        && hasContent(code.ds3ResponseTypes)
                        && !code.ds3ResponseTypes!![0].type.equals("null", ignoreCase = true) }
                .collect(GuavaCollectors.immutableList())

        when(codeWithPayload.size) {
            1 -> return codeWithPayload[0].ds3ResponseTypes!![0].type
            else -> throw IllegalArgumentException("Expected 1 response type with payload, but found " + codeWithPayload.size)
        }
    }
}
