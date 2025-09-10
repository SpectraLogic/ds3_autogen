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

import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent
import com.spectralogic.ds3autogen.api.models.apispec.decapitalize

open class ConstructorParam(
        name: String,
        type: String,
        val annotation: String) : Arguments(type, name) {

    /**
     * Converts the param into a java constructor parameter with an optional annotation.
     */
    fun toJavaCode(): String {
        if (hasContent(annotation)) {
            return "@$annotation final $type " + name.decapitalize()
        }
        return "final $type " + name.decapitalize()
    }
}

/**
 * Represents a simple constructor parameter with no annotations
 */
class SimpleConstructorParam(name: String, type: String) : ConstructorParam(name, type, "")

/**
 * Represents a constructor parameter with the Nonnull annotation
 */
class NonnullConstructorParam(name: String, type: String) : ConstructorParam(name, type, "Nonnull")