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

package com.spectralogic.ds3autogen.python.generators.response;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Generates the python response model for the Head Bucket request.
 */
public class HeadBucketResponseGenerator extends BaseResponseGenerator {

    /**
     * Gets all error response codes
     */
    @Override
    public ImmutableList<Integer> getStatusCodes(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String requestName) {
        if (isEmpty(ds3ResponseCodes)) {
            throw new IllegalArgumentException(requestName + " must have response codes 200, 403, and 404");
        }
        final ImmutableList<Integer> codes = ds3ResponseCodes.stream()
                .map(Ds3ResponseCode::getCode)
                .collect(GuavaCollectors.immutableList());

        if (!codes.contains(200) || !codes.contains(403) || !codes.contains(404)) {
            throw new IllegalArgumentException(requestName + " should contain the response codes 200, 403, and 404. Actual: " + codes);
        }

        return codes;
    }

    /**
     * Gets the python code that will parse the response payload
     */
    @Override
    public String toParseResponsePayload(
            final Ds3Request ds3Request,
            final ImmutableMap<String, Ds3Type> typeMap) {
        return "self.status_code = self.response.status\n" +
                pythonIndent(2) + "if self.response.status == 200:\n" +
                pythonIndent(3) + "self.result = HeadBucketStatus.EXISTS\n" +
                pythonIndent(2) + "elif self.response.status == 403:\n" +
                pythonIndent(3) + "self.result = HeadBucketStatus.NOTAUTHORIZED\n" +
                pythonIndent(2) + "elif self.response.status == 404:\n" +
                pythonIndent(3) + "self.result = HeadBucketStatus.DOESNTEXIST\n" +
                pythonIndent(2) + "else:\n" +
                pythonIndent(3) + "self.result = HeadBucketStatus.UNKNOWN";
    }
}
