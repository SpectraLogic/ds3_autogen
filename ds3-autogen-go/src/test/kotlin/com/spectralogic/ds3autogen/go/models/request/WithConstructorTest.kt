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

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class WithConstructorTest {

    @Test
    fun voidWithConstructorTest() {
        val constructor = VoidWithConstructor("ParamName")
        assertThat(constructor)
                .extracting("name", "type", "constructorParams", "assignment")
                .contains("ParamName", "bool", "", "true")
    }

    @Test
    fun primitivePointerWithConstructorTest() {
        val constructor = PrimitivePointerWithConstructor("ParamName", "int")
        assertThat(constructor)
                .extracting("name", "type", "constructorParams", "assignment")
                .contains("ParamName", "int", "ParamName int", "&ParamName")
    }

    @Test
    fun interfaceWithConstructorTest() {
        val constructor = InterfaceWithConstructor("ParamName", "SpectraType")
        assertThat(constructor)
                .extracting("name", "type", "constructorParams", "assignment")
                .contains("ParamName", "SpectraType", "ParamName SpectraType", "ParamName")
    }
}