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

import java.util.NoSuchElementException;

import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getResponseCodes;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.indent;

/**
 * Response parser generator for Allocate Job Chunk SpectraS3 command
 */
public class AllocateJobChunkParserGenerator extends BaseResponseParserGenerator {

    protected static final ImmutableList<Integer> EXPECTED_RESPONSE_CODES = ImmutableList.of(200);

    /**
     * Gets the non-error response codes required to generate this response
     */
    @Override
    public ImmutableList<ResponseCode> toResponseCodeList(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String responseName) {
        //Verify that the expected status codes are present
        final ImmutableList<Integer> codes = getResponseCodes(ds3ResponseCodes);
        if (!codes.containsAll(EXPECTED_RESPONSE_CODES)) {
            throw new IllegalArgumentException("Does not contain expected response codes: " + EXPECTED_RESPONSE_CODES.toString());
        }

        final ResponseCode code200 = new ResponseCode(
                200,
                toParsePayloadCode(getDs3ResponseCode(ds3ResponseCodes, 200), responseName));

        // The switch case for 307 should fall through to the 503 handling
        final ResponseCode code307 = new ResponseCode(307, "");

        final ResponseCode code503 = new ResponseCode(503, toRetryLaterCode(responseName));

        return ImmutableList.of(code200, code307, code503);
    }

    /**
     * Creates the java code for parsing the response payload
     */
    protected static String toParsePayloadCode(
            final Ds3ResponseCode ds3ResponseCode,
            final String responseName) {
        final String responseModelName = getResponseModelName(ds3ResponseCode.getDs3ResponseTypes().get(0));

        return "try (final InputStream inputStream = new ReadableByteChannelInputStream(blockingByteChannel)) {\n"
                + indent(5) + "final " + responseModelName + " result = XmlOutput.fromXml(inputStream, " + responseModelName + ".class);\n"
                + indent(5) + "return new " + responseName + "(result, 0, Status.ALLOCATED);\n"
                + indent(4) + "}\n";
    }

    /**
     * Creates the java code for retry-later response
     */
    protected static String toRetryLaterCode(final String responseName) {
        return "return new " + responseName + "(null, parseRetryAfter(response), Status.RETRYLATER);";
    }

    /**
     * Retrieves the fist instance of Ds3ResponseCode with the specified code
     * @throws NoSuchElementException
     */
    protected static Ds3ResponseCode getDs3ResponseCode(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final int code) {
        if (isEmpty(ds3ResponseCodes)) {
            throw new NoSuchElementException("Ds3ResponseCode list is empty");
        }
        return ds3ResponseCodes.stream()
                .filter(i -> i.getCode() == code)
                .findFirst()
                .get();
    }
}
