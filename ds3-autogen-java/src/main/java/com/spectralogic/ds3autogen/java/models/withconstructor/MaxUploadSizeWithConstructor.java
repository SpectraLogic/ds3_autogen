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

package com.spectralogic.ds3autogen.java.models.withconstructor;

import com.spectralogic.ds3autogen.api.models.Arguments;

import static com.spectralogic.ds3autogen.java.utils.WithConstructorUtil.putQueryParamLine;
import static com.spectralogic.ds3autogen.java.utils.WithConstructorUtil.withConstructorFirstLine;
import static com.spectralogic.ds3autogen.utils.Helper.indent;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;

/**
 * Creates the java with-constructor for the Max Upload Size
 * query parameter.
 */
public class MaxUploadSizeWithConstructor implements WithConstructor {

    private final Arguments param;
    private final String requestName;

    public MaxUploadSizeWithConstructor(final Arguments param, final String requestName) {
        this.param = param;
        this.requestName = requestName;
    }

    @Override
    public String toJavaCode() {
        return withConstructorFirstLine(param, requestName) +
                indent(2) + "if (" + uncapFirst(param.getName()) + " > MIN_UPLOAD_SIZE_IN_BYTES) {\n" +
                indent(3) + putQueryParamLine(param) + "\n" +
                indent(2) + "} else {\n" +
                indent(3) + putQueryParamLine(param.getName(), "MAX_UPLOAD_SIZE_IN_BYTES") + "\n" +
                indent(2) + "}\n" +
                indent(2) + "return this;\n" +
                indent(1) + "}\n";
    }
}
