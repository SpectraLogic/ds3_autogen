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

package com.spectralogic.ds3autogen.go.utils

import com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil

/**
 * Contains utils for converting contract types into their corresponding Go types
 */


/**
 * Retrieves the Go type associated with the specific contract type/compoundType.
 */
fun toGoResponseType(contractType: String?, compoundContractType: String?, nullable: Boolean): String {
    if (isEmpty(contractType)) {
        throw IllegalArgumentException("The contract type cannot be null or an empty string")
    }
    if (!contractType.equals("array", ignoreCase = true) || isEmpty(compoundContractType)) {
        return toGoResponseType(contractType!!, nullable)
    }
    return "[]" + toGoType(compoundContractType!!)
}

/**
 * Retrieves the Go type associated with the specific contract type for a request.
 * If the type is nullable and a primitive, then the Go type is a pointer.
 */
fun toGoRequestType(contractType: String, nullable: Boolean): String {
    val goType: String = toGoType(contractType)
    if (nullable && hasContent(goType) && isGoPrimitiveType(goType)) {
        return "*" + goType
    }
    return goType
}

/**
 * Determines if a Go type is a primitive type.
 */
fun isGoPrimitiveType(goType: String): Boolean {
    return when (goType) {
        "bool", "int", "string", "float64", "int64" -> true
        else -> false
    }
}

/**
 * Retrieves the Go type associated with the specific contract type. If the
 * type is nullable, then the Go type is a pointer to the specified type.
 */
fun toGoResponseType(contractType: String, nullable: Boolean): String {
    val goType: String = toGoType(contractType)
    if (nullable && hasContent(goType)) {
        return "*" + goType
    }
    return goType
}

/**
 * Retrieves the Go type associated with the specified contract type. Nullability
 * is not taken into account
 */
fun toGoType(contractType: String): String {
    val type: String = NormalizingContractNamesUtil.removePath(contractType)
    return when (type.lowercase()) {
        "boolean" -> "bool"
        "integer", "int" -> "int"
        "string", "uuid", "date" -> "string"
        "double" -> "float64"
        "long" -> "int64"
        "void" -> ""
        else -> type
    }
}
