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

public abstract class C_Type {
    protected String type;
    protected boolean isArray;

    public String getTypeRoot() {
        return type;
    }

    @Override
    public String toString() {
        String ret = type;
        if (!isPrimitive()) {
            ret += "*";
        }
        if (isArray()) {
            ret += "*";
        }

        return ret;
    }

    /**
     * Any StructMember can be an array, regardless of Type
     * @return boolean isArray
     */
    public boolean isArray() {
        return isArray;
    }

    abstract public boolean isPrimitive();
}

