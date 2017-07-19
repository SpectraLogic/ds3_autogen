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

package com.spectralogic.ds3autogen.go.models.parser

import com.spectralogic.ds3autogen.go.utils.indent

/**
 * Contains the ParseElement implementation for all non-attribute elements
 * in Go models.
 */

//todo
data class ParseChildNodeAsPrimitiveType(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String,
        val parserNamespace: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse$parserNamespace(child.Content, aggErr)" }
}

//todo
data class ParseChildNodeAsString(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String,
        val parserNamespace: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse$parserNamespace(child.Content)" }
}


//todo
data class ParseChildNodeAsDs3Type(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName.parse(&child, aggErr)" }
}

//todo
data class ParseChildNodeAsSlice(
        override val xmlTag: String,
        val childXmlTag: String,
        val modelName: String,
        val paramName: String,
        val childType: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse${childType}Slice(\"$childXmlTag\", child.Children, aggErr)" }
}

//todo
data class ParseChildNodeAddToSlice(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String,
        val childType: String) : ParseElement {

    override val parsingCode: String
        get() {
            return "var model $childType\n" +
                    indent(3) + "model.parse(&child, aggErr)\n" +
                    indent(3) + "$modelName.$paramName = append($modelName.$paramName, model)"
        }
}

//todo
data class ParseChildNodeAsEnum(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "parseEnum(child.Content, &$modelName.$paramName, aggErr)" }
}

//todo
data class ParseChildNodeAsNullableEnum(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "parseNullableEnum(child.Content, $modelName.$paramName, aggErr)" }
}