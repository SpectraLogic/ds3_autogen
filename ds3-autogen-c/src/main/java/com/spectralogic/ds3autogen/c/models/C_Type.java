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

import com.spectralogic.ds3autogen.c.helpers.C_TypeHelper;

public abstract class C_Type {
    protected String typeName;
    protected boolean isArray;

    private final C_TypeHelper cTypeHelper = C_TypeHelper.getInstance();

    /**
     * An alternate to toString
     * @return The typeName without any qualifiers (*)
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * The Type property when printed in a Typedef Struct,
     * with proper pointer qualifier for a sub-Struct or
     * array.
     * @return Printable typedef struct field; also suitable for logging.
     */
    @Override
    public String toString() {
        String ret = typeName;
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

    public C_TypeHelper getcTypeHelper() {
        return cTypeHelper;
    }
}

