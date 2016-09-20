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

package com.spectralogic.ds3autogen.java.models.parseresponse;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

/**
 * Generates the java code for when there is no payload
 */
public class EmptyParseResponse implements ParseResponse {

    private final static int INDENT = 4;

    private final String responseName;

    public EmptyParseResponse(final String responseName) {
        this.responseName = responseName;
    }

    @Override
    public String toJavaCode() {
        return "//There is no payload, return an empty response handler\n"
                + indent(INDENT) + "return new " + responseName + "();\n";
    }
}
