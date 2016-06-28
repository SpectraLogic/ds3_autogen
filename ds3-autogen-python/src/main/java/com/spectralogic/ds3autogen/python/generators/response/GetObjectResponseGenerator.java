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

import com.spectralogic.ds3autogen.api.models.Ds3Request;

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
        return "localFile = None\n" +
                pythonIndent(2) + "if self.request.stream:\n" +
                pythonIndent(3) + "localFile = self.request.stream\n" +
                pythonIndent(2) + "else:\n" +
                pythonIndent(3) + "localFile = open(self.request.effective_file_name, \"wb\")\n" +
                pythonIndent(2) + "if self.request.offset:\n" +
                pythonIndent(3) + "localFile.seek(self.request.offset, 0)\n" +
                pythonIndent(2) + "localFile.write(response.read())\n" +
                pythonIndent(2) + "localFile.close()\n";
    }
}
