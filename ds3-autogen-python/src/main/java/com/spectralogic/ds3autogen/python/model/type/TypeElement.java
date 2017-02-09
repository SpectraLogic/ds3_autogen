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

package com.spectralogic.ds3autogen.python.model.type;

import static com.spectralogic.ds3autogen.utils.Helper.capFirst;

/**
 * Describes a single xml element child of a given type
 */
public class TypeElement implements TypeContent {

    private final String xmlTag;
    /** The name of the type model to use for parsing this element; if primitive type, then 'None' */
    private final String typeModel;

    public TypeElement(final String xmlTag, final String typeModel) {
        this.xmlTag = xmlTag;
        this.typeModel = typeModel;
    }

    public String toPythonCode() {
        final StringBuilder builder = new StringBuilder();
        builder.append("'").append(capFirst(xmlTag)).append("' : ").append(typeModel);
        if (!typeModel.equals("None")) {
            builder.append("()");
        }
        return  builder.toString();
    }
}
