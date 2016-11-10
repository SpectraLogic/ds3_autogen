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

package com.spectralogic.ds3autogen.java.generators.responseparser;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.java.models.ResponseCode;

import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getDs3ResponseCode;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getResponseCodes;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getResponseModelName;
import static com.spectralogic.ds3autogen.utils.Helper.indent;

/**
 * Response parser generator for Get Job Chunks Ready for Client Processing SpectraS3 command
 */
public class GetJobChunksReadyParserGenerator extends BaseResponseParserGenerator {

    protected static final ImmutableList<Integer> EXPECTED_RESPONSE_CODES = ImmutableList.of(200);

    /**
     * Gets the non-error response codes required to generate this response
     */
    @Override
    public ImmutableList<ResponseCode> toResponseCodeList(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String responseName,
            final boolean hasPaginationHeaders) {
        //Verify that the expected status codes are present
        final ImmutableList<Integer> codes = getResponseCodes(ds3ResponseCodes);
        if (!codes.containsAll(EXPECTED_RESPONSE_CODES)) {
            throw new IllegalArgumentException("Does not contain expected response codes: " + EXPECTED_RESPONSE_CODES.toString());
        }

        final Ds3ResponseCode ds3ResponseCode = getDs3ResponseCode(ds3ResponseCodes, 200);
        final ResponseCode code200 = new ResponseCode(200, toParsePayloadCode(ds3ResponseCode, responseName));

        return ImmutableList.of(code200);
    }

    /**
     * Creates the java code for parsing the response payload
     */
    protected static String toParsePayloadCode(
            final Ds3ResponseCode ds3ResponseCode,
            final String responseName) {
        final String responseModelName = getResponseModelName(ds3ResponseCode.getDs3ResponseTypes().get(0));

        return "try (final InputStream inputStream = response.getResponseStream()) {\n" +
                indent(5) + "final " + responseModelName + " result = XmlOutput.fromXml(inputStream, " + responseModelName + ".class);\n" +
                indent(5) + "if (isNullOrEmpty(result.getObjects())) {\n" +
                indent(6) + "return new " + responseName + "(result, parseRetryAfter(response), " + responseName +".Status.RETRYLATER, this.getChecksum(), this.getChecksumType());\n" +
                indent(5) + "}\n" +

                indent(5) + "return new " + responseName + "(result, 0, " + responseName +".Status.AVAILABLE, this.getChecksum(), this.getChecksumType());\n" +
                indent(4) + "}\n";
    }
}
