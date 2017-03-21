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
 * Interface for a variable and its assignment.
 */
interface VariableInterface {
    val name: String
    val assignment: String
}

/**
 * A variable where the name and assignment value are the same.
 */
class SimpleVariable(override val name: String) : VariableInterface {
    override val assignment: String get() { return name }
}

/**
 * A query param where the name and assignment values are different.
 */
class Variable(override val name: String, override val assignment: String) : VariableInterface
