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

import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.getType;
import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.paramAssignmentRHS;
import static com.spectralogic.ds3autogen.java.utils.WithConstructorUtil.*;
import static com.spectralogic.ds3autogen.utils.Helper.*;

/**
 * Creates a basic java with-constructor. This is used in the
 * generation of request handlers.
 */
public class BaseWithConstructor implements WithConstructor {

    private final Arguments param;
    private final String requestName;

    public BaseWithConstructor(final Arguments param, final String requestName) {
        this.param = param;
        this.requestName = requestName;
    }

    @Override
    public String toJavaCode() {
        return withConstructorFirstLine(param, requestName) +
                indent(2) + argAssignmentLine(param) +
                indent(2) + updateQueryParamLine(param.getName(), uncapFirst(param.getInternalName())) +
                indent(2) + "return this;\n" +
                indent(1) + "}\n";
    }
}
