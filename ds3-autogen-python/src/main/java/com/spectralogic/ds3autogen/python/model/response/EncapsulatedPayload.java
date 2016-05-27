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

package com.spectralogic.ds3autogen.python.model.response;

/**
 * Creates the python code for parsing the response payload that
 * is within a parent xml element
 */
public class EncapsulatedPayload implements ParsePayload {

    private final String typeModel;
    private final String parentTag;

    public EncapsulatedPayload(
            final String typeModel,
            final String  parentTag) {
        this.typeModel = typeModel;
        this.parentTag = parentTag;
    }

    @Override
    public String toPythonCode() {
        final StringBuilder builder = new StringBuilder();
        builder.append("parseModel(xmldom.fromstring(response.read()).find(")
                .append(parentTag).append("), ").append(typeModel);
        if (!typeModel.equals("None")) {
            builder.append("()");
        }
        return builder.append(")").toString();
    }
}
