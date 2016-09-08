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

package com.spectralogic.ds3autogen.java.models.withconstructor;

import com.spectralogic.ds3autogen.api.models.Arguments;

import static com.spectralogic.ds3autogen.java.utils.WithConstructorUtil.withConstructorFirstLine;
import static com.spectralogic.ds3autogen.utils.Helper.capFirst;
import static com.spectralogic.ds3autogen.utils.Helper.indent;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;

/**
 * Created java with-constructor for optional query parameters
 * that are defined within the abstract bulk requests.
 */
public class BulkWithConstructor implements WithConstructor {

    private final Arguments param;
    private final String requestName;

    public BulkWithConstructor(final Arguments param, final String requestName) {
        this.param = param;
        this.requestName = requestName;
    }

    //TODO
    @Override
    public String toJavaCode() {
        return indent(1) + "@Override\n" +
                withConstructorFirstLine(param, requestName) +
                indent(2) + "super.with" + capFirst(param.getName()) + "(" + uncapFirst(param.getName()) + ");\n" +
                indent(2) + "return this;\n" +
                indent(1) + "}\n";
    }
}
