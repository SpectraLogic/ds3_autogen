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

package com.spectralogic.ds3autogen.python.generators.response;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Generates the python response model for the Head Object and Head Bucket commands
 */
public class HeadResponseGenerator extends BaseResponseGenerator {

    /**
     * Gets all response codes
     */
    @Override
    public ImmutableList<Integer> getStatusCodes(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String requestName) {
        if (isEmpty(ds3ResponseCodes)) {
            throw new IllegalArgumentException(requestName + " must have response codes 200, and 404");
        }
        final ImmutableList<Integer> codes = ds3ResponseCodes.stream()
                .map(Ds3ResponseCode::getCode)
                .collect(GuavaCollectors.immutableList());

        if (!codes.contains(200) ||  !codes.contains(404)) {
            throw new IllegalArgumentException(requestName + " should contain the response codes 200, and 404. Actual: " + codes);
        }

        if (codes.contains(403)) {
            return codes;
        }

        //If 403 is not in expected codes, add it, and sort the list
        final ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        builder.addAll(codes).add(403);
        return builder.build()
                .stream()
                .sorted()
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the python code that will parse the response payload
     */
    @Override
    public String toParseResponsePayload(final Ds3Request ds3Request) {
        return "self.status_code = self.response.status\n" +
                pythonIndent(2) + "if self.response.status == 200:\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.EXISTS\n" +
                pythonIndent(2) + "elif self.response.status == 403:\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.NOTAUTHORIZED\n" +
                pythonIndent(2) + "elif self.response.status == 404:\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.DOESNTEXIST\n" +
                pythonIndent(2) + "else:\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.UNKNOWN";
    }
}
