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

import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil

/**
 * Contains utils for converting contract types into their corresponding Go types
 */


/**
 * Retrieves the Go type associated with the specific contract type. If the
 * type is nullable, then the Go type is a pointer to the specified type.
 */
//TODO test
fun toGoType(contractType: String, nullable: Boolean): String {
    val goType: String = toGoType(contractType)
    if (nullable) {
        return "*" + goType
    } else {
        return goType
    }
}

/**
 * Retrieves the Go type associated with the specified contract type. Nullability
 * is not taken into account
 */
//TODO test
fun toGoType(contractType: String): String {
    val type: String = NormalizingContractNamesUtil.removePath(contractType)
    when (type.toLowerCase()) {
        "boolean" -> return "bool"
        "integer", "int" -> return "int"
        "string", "uuid" -> return "string"
        "double" -> return "float64"
        "long" -> return "int64"
        "void" -> return "" //TODO ???
        else -> return type
    }
}
