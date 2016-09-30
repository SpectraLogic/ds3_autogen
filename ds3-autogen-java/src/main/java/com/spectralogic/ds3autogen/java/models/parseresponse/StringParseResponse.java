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
 * Creates the java code for parsing a String response payload
 */
public class StringParseResponse implements ParseResponse {

    private final static int INDENT = 4;

    private final String responseName;
    private final boolean hasPaginationHeaders;

    public StringParseResponse(final String responseName) {
        this(responseName, false);
    }

    public StringParseResponse(final String responseName, final boolean hasPaginationHeaders) {
        this.responseName = responseName;
        this.hasPaginationHeaders = hasPaginationHeaders;
    }

    @Override
    public String toJavaCode() {
        return "try (final InputStream inputStream = response.getResponseStream()) {\n"
                + indent(INDENT + 1) + "final String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);\n"
                + indent(INDENT + 1) + "return new " + responseName + "(" + getConstructorParams(hasPaginationHeaders) + ");\n"
                + indent(INDENT) + "}\n";
    }

    private static String getConstructorParams(final boolean hasPaginationHeaders) {
        if (hasPaginationHeaders) {
            return "result, pagingTotalResultCount, pagingTruncated";
        }
        return "result";
    }
}
