/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.c.models;

import com.spectralogic.ds3autogen.c.helpers.StructHelper;

public class StructMember {
    private final C_Type type;
    private final String name;
    private final StructHelper structHelper;

    public StructMember(
            final C_Type type,
            final String name) {
        this.type = type;
        this.name = name;
        this.structHelper = StructHelper.getInstance();
    }

    public C_Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public StructHelper getStructHelper() {
        return structHelper;
    }

    public String toString() {
        return getType().toString() + "    " + getName();
    }
}
