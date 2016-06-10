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

package com.spectralogic.ds3autogen.python.generators.request;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.RequestPayload;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;

/**
 * Creates the python request model for the Amazon request Get Object which has the optional
 * request payload of real_file_name, and which opens the specified file
 */
public class PutObjectRequestGenerator extends BaseRequestGenerator {

    /** Used to specify the actual file name on the local machine if it differs from the BP name */
    private static final String PAYLOAD_NAME = "real_file_name";

    /**
     * Gets the request payload model that handles string and stream payload types
     */
    @Override
    public RequestPayload toRequestPayload(final Ds3Request ds3Request, final String requestName) {
        final String assignment = "self.object_name = typeCheckString(object_name)\n" +
                pythonIndent(2) + "effectiveFileName = self.object_name\n" +
                pythonIndent(2) + "if " + PAYLOAD_NAME + ":\n" +
                pythonIndent(3) + "effectiveFileName = typeCheckString(" + PAYLOAD_NAME + ")\n\n" +
                pythonIndent(2) + "localFile = open(effectiveFileName, \"rb\")\n" +
                pythonIndent(2) + "localFile.seek(offset, 0)\n" +
                pythonIndent(2) + "self.body = localFile.read()\n" +
                pythonIndent(2) + "localFile.close()";
        return new RequestPayload(PAYLOAD_NAME, assignment, true);
    }
}
