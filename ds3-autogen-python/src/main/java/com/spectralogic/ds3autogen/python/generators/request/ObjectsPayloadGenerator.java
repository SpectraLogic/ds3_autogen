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
import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.hasFileObjectListPayload;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;

/**
 * Creates the python request model for commands with request payloads of objects list.
 */
public class ObjectsPayloadGenerator extends BaseRequestGenerator {

    private static final String OBJECTS_NAME = "object_list";
    private static final String FILE_OBJECTS_TYPE = "FileObjectList";
    private static final String DELETE_OBJECTS_TYPE = "DeleteObjectList";

    private static final String PARTS_NAME = "part_list";
    private static final String PARTS_TYPE = "PartList";

    //TODO test
    /**
     * Creates the request payload model for a list of file objects
     */
    @Override
    public RequestPayload toRequestPayload(final Ds3Request ds3Request, final String requestName) {
        if (isEjectStorageDomainRequest(ds3Request)) {
            return toObjectsRequestPayload(requestName, OBJECTS_NAME, FILE_OBJECTS_TYPE, true);
        }
        if (hasFileObjectListPayload(ds3Request)) {
            return toObjectsRequestPayload(requestName, OBJECTS_NAME, FILE_OBJECTS_TYPE, false);
        }
        if (isMultiFileDeleteRequest(ds3Request)) {
            return toObjectsRequestPayload(requestName, OBJECTS_NAME, DELETE_OBJECTS_TYPE, false);
        }
        if (isCompleteMultiPartUploadRequest(ds3Request)) {
            return toObjectsRequestPayload(requestName, PARTS_NAME, PARTS_TYPE, false);
        }
        throw new IllegalArgumentException("The Ds3Request does not have an objects request payload: " + ds3Request.getName());
    }

    //TODO test
    /**
     * Creates the python code for assigning the specified payload type to the body of the request
     */
    protected static RequestPayload toObjectsRequestPayload(
            final String requestName,
            final String payloadName,
            final String payloadType,
            final boolean optional) {
        final String assignmentCode = "if " + payloadName + " is not None:\n"
                + pythonIndent(3) + "if " + payloadName + " not isinstance(" + payloadName + ", " + payloadType + "):\n"
                + pythonIndent(4) + "raise TypeError('" + requestName + " should have request payload of type: " + payloadType + "')\n"
                + pythonIndent(3) + "self.body = xmldom.tostring(" + payloadName + ".to_xml())";
        return new RequestPayload(payloadName, assignmentCode, optional);
    }
}
