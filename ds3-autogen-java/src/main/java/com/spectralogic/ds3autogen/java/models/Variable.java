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

package com.spectralogic.ds3autogen.java.models;

public class Variable {

    final private String type;
    final private String name;
    final private boolean isRequired;

    public Variable(final String name, final String type, final boolean isRequired) {
        this.name = name;
        this.type = type;
        this.isRequired = isRequired;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getInternalName() {
        if (name.equalsIgnoreCase("protected")) {
            return name+"Flag";
        }
        return name;
    }

    public boolean isRequired() {
        return isRequired;
    }
}
