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

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;

/**
 * Generates the python response model for the Amazon Get Object command
 */
public class GetObjectResponseGenerator extends BaseResponseGenerator {

    /**
     * Gets the python code that will parse the response payload
     */
    @Override
    public String toParseResponsePayload(final Ds3Request ds3Request) {
        return "stream = self.request.stream\n" +
                pythonIndent(2) + "try:\n" +
                pythonIndent(3) + "bytes_read = response.read(self.buffer_size)\n" +
                pythonIndent(3) + "while bytes_read:\n" +
                pythonIndent(4) + "stream.write(bytes_read)\n" +
                pythonIndent(4) + "bytes_read = response.read(self.buffer_size)\n" +
                pythonIndent(2) + "finally:\n" +
                pythonIndent(3) + "stream.close()\n" +
                pythonIndent(3) + "response.close()\n";
    }

    @Override
    public String toInitResponse() {
        return "def __init__(self, response, request, buffer_size=None):\n" +
                pythonIndent(2) + "self.buffer_size = buffer_size\n" +
                pythonIndent(2) + "super(self.__class__, self).__init__(response, request)\n";
    }
}
