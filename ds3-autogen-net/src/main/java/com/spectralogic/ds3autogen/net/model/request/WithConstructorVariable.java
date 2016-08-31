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

package com.spectralogic.ds3autogen.net.model.request;

import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;

/**
 * Contains the documentation and variable info required to create a .Net
 * with-constructor
 */
public class WithConstructorVariable extends NetNullableVariable {

    private final String documentation;

    public WithConstructorVariable(
            final String name,
            final String type,
            final boolean questionMarkForNullable,
            final boolean nullable,
            final String documentation) {
        super(name, type, questionMarkForNullable, nullable);
        this.documentation = documentation;
    }

    public WithConstructorVariable(final NetNullableVariable var, final String documentation) {
        super(var.getName(), var.getType(), var.isQuestionMarkForNullable(), var.isNullable());
        this.documentation = documentation;
    }

    public String getDocumentation() {
        return documentation;
    }
}
