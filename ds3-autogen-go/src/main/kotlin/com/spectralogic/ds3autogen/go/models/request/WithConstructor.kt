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

package com.spectralogic.ds3autogen.go.models.request

/**
 * Represents a with-constructor used to set optional parameters
 * in a request handler
 */
interface WithConstructor {
    val name: String // name of the optional parameter
    val type: String // type of the optional parameter
    val constructorParams: String // contains the constructor parameters
    val assignment: String // The value to assign to the request struct
}

/**
 * Represents an optional 'void' query parameter which is translated
 * into a Go bool type to determine if the query parameter should be set.
 */
data class VoidWithConstructor(override val name: String) : WithConstructor {
    override val type: String = "bool"
    override val constructorParams: String = ""
    override val assignment: String = "true"
}

/**
 * Represents an optional query parameter of a primitive type, which
 * needs to be referenced when storing the primitive as a pointer.
 */
data class PrimitivePointerWithConstructor(override val name: String, override val type: String) : WithConstructor {
    override val constructorParams: String = "$name $type"
    override val assignment: String = "&$name"
}

/**
 * Represents an optional query parameter with a Spectra defined type.
 * Since interfaces default to nil, no referencing is needed to maintain nullability in Go type.
 */
data class InterfaceWithConstructor(override val name: String, override val type: String) : WithConstructor {
    override val constructorParams: String = "$name $type"
    override val assignment: String = name
}