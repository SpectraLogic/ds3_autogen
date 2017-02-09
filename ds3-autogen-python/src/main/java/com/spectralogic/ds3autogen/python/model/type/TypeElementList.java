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
 * Describes a list of elements within a type
 */
public class TypeElementList implements TypeContent {

    private final String xmlTag;
    private final String encapsulatingTag;
    /** The name of the type model to use for parsing the list of elements; if primitive type, then 'None' */
    private final String typeModel;

    public TypeElementList(final String xmlTag, final String encapsulatingTag, final String typeModel) {
        this.xmlTag = xmlTag;
        this.encapsulatingTag = encapsulatingTag;
        this.typeModel = typeModel;
    }

    public String toPythonCode() {
        final StringBuilder builder = new StringBuilder();
        builder.append("('").append(capFirst(xmlTag)).append("', ");
        if (encapsulatingTag.equals("None")) {
            builder.append(capFirst(encapsulatingTag));
        } else {
            builder.append("'").append(capFirst(encapsulatingTag)).append("'");
        }
        builder.append(", ").append(typeModel);
        if (!typeModel.equals("None")) {
            builder.append("()");
        }
        return builder.append(")").toString();
    }
}
