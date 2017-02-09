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

package com.spectralogic.ds3autogen.net.model.common;

import com.spectralogic.ds3autogen.api.models.Arguments;

public class NetNullableVariable extends Arguments {

    /** Denotes if the type must be followed by a '?' in order for the type to be nullable */
    private final boolean questionMarkForNullable;

    /** Denotes if the type should be nullable */
    private final boolean nullable;

    public NetNullableVariable(
            final String name,
            final String type,
            final boolean questionMarkForNullable,
            final boolean nullable) {
        super(type, name);
        this.questionMarkForNullable = questionMarkForNullable;
        this.nullable = nullable;
    }

    /**
     * Retrieves the .net type described by this variable. It the
     * type is both nullable and primitive, then the type will be
     * followed by a '?' for nullability
     */
    public String getNetType() {
        if (questionMarkForNullable && nullable) {
            return getType() + "?";
        }
        return getType();
    }

    public boolean isQuestionMarkForNullable() {
        return questionMarkForNullable;
    }

    public boolean isNullable() {
        return nullable;
    }
}
