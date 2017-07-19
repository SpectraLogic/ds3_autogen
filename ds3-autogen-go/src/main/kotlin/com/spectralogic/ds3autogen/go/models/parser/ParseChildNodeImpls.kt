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

/**
 * Creates the Go code for parsing most child nodes with primitive types.
 */
data class ParseChildNodeAsPrimitiveType(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String,
        val parserNamespace: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse$parserNamespace(child.Content, aggErr)" }
}

/**
 * Creates the Go code for parsing child nodes of type string and string pointer.
 */
data class ParseChildNodeAsString(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String,
        val parserNamespace: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse$parserNamespace(child.Content)" }
}


/**
 * Creates the Go code for parsing child nodes that are a Ds3 defined type.
 */
data class ParseChildNodeAsDs3Type(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName.parse(&child, aggErr)" }
}

/**
 * Creates the Go code for parsing multiple child nodes of the same type, which are
 * encapsulated within a parent xml tag.
 */
data class ParseChildNodeAsSlice(
        override val xmlTag: String,
        val childXmlTag: String,
        val modelName: String,
        val paramName: String,
        val childType: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse${childType}Slice(\"$childXmlTag\", child.Children, aggErr)" }
}

/**
 * Creates the Go code for parsing a single child node and adding it to a slice. This
 * is used when there are multiple child nodes of the same type with no encapsulating
 * xml tag.
 */
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

/**
 * Creates the Go code for parsing any non-nullable Ds3 defined enum.
 */
data class ParseChildNodeAsEnum(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "parseEnum(child.Content, &$modelName.$paramName, aggErr)" }
}

/**
 * Creates the Go code for parsing any nullable Ds3 defined enum.
 */
data class ParseChildNodeAsNullableEnum(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "parseNullableEnum(child.Content, $modelName.$paramName, aggErr)" }
}