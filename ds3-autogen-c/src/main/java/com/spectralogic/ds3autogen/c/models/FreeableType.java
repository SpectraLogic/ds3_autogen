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

package com.spectralogic.ds3autogen.c.models;

/**
 * A FreeableType indicates a StructMember that requires a free,
 * in other words a Struct inside of a Struct.
 */
public class FreeableType extends C_Type {
    public FreeableType(
            final String type,
            final boolean isArray) {
        this.typeName = type;
        this.isArray = isArray;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getTypeName() {
        return super.getTypeName();
    }

    @Override
    public boolean isPrimitive() {
        return false;
    }
}
