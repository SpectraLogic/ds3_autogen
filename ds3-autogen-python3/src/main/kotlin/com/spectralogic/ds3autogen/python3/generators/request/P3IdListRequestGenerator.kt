/*
 * ******************************************************************************
 *   Copyright 2014-2018 Spectra Logic Corporation. All Rights Reserved.
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

import com.spectralogic.ds3autogen.python.generators.request.IdListRequestPayloadGenerator
import com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent

/**
 * Creates the Python 3 request model for the clear blob and mark blob commands
 */
class P3IdListRequestGenerator : IdListRequestPayloadGenerator() {

    /**
     * Gets the Python 3 code that handles processing the request payload and headers
     */
    override fun getAdditionalContent(requestName : String) : String {
        return "if $PAYLOAD_NAME is not None:\n" +
                pythonIndent(3) + "if not (isinstance(cur_id, str) for cur_id in $PAYLOAD_NAME):\n" +
                pythonIndent(4) + "raise TypeError(\n" +
                pythonIndent(5) + "'" + requestName + " should have request payload of type: list of strings')\n" +
                pythonIndent(3) + "xml_id_list = IdsList($PAYLOAD_NAME)\n" +
                pythonIndent(3) + "self.body = xmldom.tostring(xml_id_list.to_xml())\n"
    }
}