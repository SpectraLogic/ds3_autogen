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

/**
 * Contains the attribute implementations of ParseElement
 */


//TODO add tests that touch all parsers
/**
 * Creates the Go code for parsing most attributes with primitive types
 */
data class ParseSimpleAttr(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String,
        val parserNamespace: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse${parserNamespace}FromString(attr.Value, aggErr)" }
}

/**
 * Creates the Go code for parsing a string from an attribute
 */
data class ParseStringAttr(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = attr.Value" }
}

/**
 * Creates the Go code for parsing a nullable string from an attribute
 */
data class ParseNullableStringAttr(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String,
        val parserNamespace: String) : ParseElement {

    override val parsingCode: String
        get() { return "$modelName.$paramName = parse${parserNamespace}FromString(attr.Value)" }
}


/**
 * Creates the Go code for parsing an enum from an attribute
 */
data class ParseEnumAttr(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "parseEnumFromString(attr.Value, &$modelName.$paramName, aggErr)" }
}

/**
 * Creates the Go code for parsing a nullable enum from an attribute
 */
data class ParseNullableEnumAttr(
        override val xmlTag: String,
        val modelName: String,
        val paramName: String) : ParseElement {

    override val parsingCode: String
        get() { return "parseNullableEnumFromString(attr.Value, $modelName.$paramName, aggErr)" }
}