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
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath

/**
 * Contains utils for naming elements to Go conventions and avoiding
 * key word conflicts.
 */


/**
 * Converts a parameter name into a Go compatible name. If the name
 * is the Go keyword "Type", then the parameter is named after its
 * type.
 */
fun toGoParamName(contractName: String, type: String): String {
    return toGoParamName(contractName, type, "")
}

/**
 * Converts a parameter name into a Go compatible name. If the name
 * is the Go keyword "Type", then the parameter is named after its
 * type. If the type is an array, then "List" is appended to the
 * component type name.
 */
fun toGoParamName(contractName: String, type: String, componentType: String): String {
    if (!contractName.equals("Type", ignoreCase = true)) {
        return contractName
    }
    if (hasContent(componentType)) {
        return removePath(componentType) + "List"
    }
    return removePath(type)
}
