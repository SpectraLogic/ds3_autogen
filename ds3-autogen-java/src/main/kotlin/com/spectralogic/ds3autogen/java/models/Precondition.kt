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

package com.spectralogic.ds3autogen.java.models

import com.spectralogic.ds3autogen.api.models.apispec.decapitalize

/**
 * Represents a Java precondition. Used to perform certain input validation
 * in Java request handler constructors.
 */
interface Precondition {
    fun toJavaCode(): String
}



/**
 * Represents a precondition check that a given parameter is not null.
 */
data class NotNullPrecondition(private val name: String) : Precondition {

    override fun toJavaCode(): String {
        val paramName = name.decapitalize()
        return "Preconditions.checkNotNull($paramName, \"$name may not be null.\");"
    }
}