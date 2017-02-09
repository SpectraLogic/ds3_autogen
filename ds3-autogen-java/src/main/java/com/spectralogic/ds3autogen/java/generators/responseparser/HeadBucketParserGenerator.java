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

package com.spectralogic.ds3autogen.java.generators.responseparser;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.java.models.ResponseCode;

import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getResponseCodes;

/**
 * Response parser generator for Head Bucket command
 */
public class HeadBucketParserGenerator extends BaseResponseParserGenerator {

    protected static final ImmutableList<Integer> EXPECTED_RESPONSE_CODES = ImmutableList.of(200, 403, 404);

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

        final ResponseCode code200 = new ResponseCode(200, toReturnCode(responseName, "EXISTS"));
        final ResponseCode code403 = new ResponseCode(403, toReturnCode(responseName, "NOTAUTHORIZED"));
        final ResponseCode code404 = new ResponseCode(404, toReturnCode(responseName, "DOESNTEXIST"));

        return ImmutableList.of(code200, code403, code404);
    }

    /**
     * Gets the java code for returning the response handler with the specified status
     */
    protected static String toReturnCode(final String responseName, final String status) {
        return "return new " + responseName + "(" + responseName + ".Status." + status + ", this.getChecksum(), this.getChecksumType());\n";
    }
}
