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

package com.spectralogic.ds3autogen.go.generators.request

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.utils.Helper.uncapFirst
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator
import java.util.stream.Collectors

/**
 * Contains utilities used in by Go request generators
 */

/**
 * Transforms a list of Arguments into a comma-separated list of function input parameters.
 * The parameters are sorted according to Argument name: BucketName, ObjectName, alphabetical.
 */
fun toFunctionInput(arguments: ImmutableList<Arguments>): String {
    return arguments.stream()
            .sorted(CustomArgumentComparator())
            .map { arg -> uncapFirst(arg.name) + " " + arg.type }
            .collect(Collectors.joining(", "))
}

//TODO move to client to determine import of Strconv
/**
 * Determines if the Go conversion of the specified contract type into a string
 * requires the import of strconv
 */
fun usesStrconv(contractType: String): Boolean {
    val type = NormalizingContractNamesUtil.removePath(contractType)
    when (type.toLowerCase()) {
        "boolean", "integer", "int", "double", "long" -> return true
        else -> return false
    }
}
