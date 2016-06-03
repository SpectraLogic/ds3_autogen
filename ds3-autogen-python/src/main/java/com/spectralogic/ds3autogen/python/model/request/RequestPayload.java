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

package com.spectralogic.ds3autogen.python.model.request;

public class RequestPayload {

    private final String name;

    /** Python code for assigning the request payload to request body */
    private final String assignmentCode;

    private final boolean optional;

    public RequestPayload(final String name, final String assignmentCode, final boolean optional) {
        this.name = name;
        this.assignmentCode = assignmentCode;
        this.optional = optional;
    }

    public String getName() {
        return name;
    }

    public String getAssignmentCode() {
        return assignmentCode;
    }

    public boolean isOptional() {
        return optional;
    }
}
