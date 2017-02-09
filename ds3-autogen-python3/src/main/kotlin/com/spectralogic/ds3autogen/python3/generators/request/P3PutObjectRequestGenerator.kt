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

package com.spectralogic.ds3autogen.python3.generators.request

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.python.generators.request.PutObjectRequestGenerator
import com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent

/**
 * Creates the Python 3 request model for the Amazon request Put Object
 */
class P3PutObjectRequestGenerator : PutObjectRequestGenerator() {

    /**
     * Gets the Python 3 code that handles processing the request payload and headers
     */
    override fun getAdditionalContent(ds3Request: Ds3Request, requestName : String) : String {
        return "if headers is not None:\n" +
                pythonIndent(3) + "for key, val in headers.items():\n" +
                pythonIndent(4) + "if val:\n" +
                pythonIndent(5) + "self.headers[key] = val\n" +
                pythonIndent(2) + "self.headers['Content-Length'] = length\n" +
                pythonIndent(2) + "self.object_name = typeCheckString(object_name)\n" +
                pythonIndent(2) + "object_data = StreamWithLength(stream, length)\n" +
                pythonIndent(2) + "self.body = object_data\n"
    }
}