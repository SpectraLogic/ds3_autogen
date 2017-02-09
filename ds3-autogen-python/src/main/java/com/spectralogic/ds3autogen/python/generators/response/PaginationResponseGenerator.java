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

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.python.model.response.ParsePayload;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;

/**
 * Generates the python response model for commands that have pagination headers
 * that need parsing
 */
public class PaginationResponseGenerator extends BaseResponseGenerator {

    /**
     * Gets the python code that initializes the response payload
     */
    @Override
    public String toInitResponse() {
        return "def __init__(self, response, request):\n" +
                pythonIndent(2) + "self.paging_truncated = None\n" +
                pythonIndent(2) + "self.paging_total_result_count = None\n" +
                pythonIndent(2) + "super(self.__class__, self).__init__(response, request)\n";
    }

    /**
     * Gets the python code that will parse the response payload
     */
    @Override
    public String toParseResponsePayload(final Ds3Request ds3Request) {
        final ParsePayload parsePayload = getParsePayload(ds3Request);
        return parsePayload.toPythonCode() + "\n"
                + pythonIndent(3) + "self.paging_truncated = self.parse_int_header('page-truncated', response.getheaders())\n"
                + pythonIndent(3) + "self.paging_total_result_count = self.parse_int_header('total-result-count', response.getheaders())";
    }
}
