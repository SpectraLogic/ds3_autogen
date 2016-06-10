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

package com.spectralogic.ds3autogen.python.model.type;

import static com.spectralogic.ds3autogen.utils.Helper.capFirst;

/**
 * Describes an attribute of type
 */
public class TypeAttribute implements TypeContent {

    private final String name;

    public TypeAttribute(final String name) {
        this.name = name;
    }

    public String toPythonCode() {
        return "'" + capFirst(name) + "'";
    }
}
