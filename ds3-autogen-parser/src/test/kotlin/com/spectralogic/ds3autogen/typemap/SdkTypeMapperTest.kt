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

package com.spectralogic.ds3autogen.typemap

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.Test

class SdkTypeMapperTest {

    @Test
    fun userSpecifiedTypeMapTest() {
        val mapper = SdkTypeMapper("/testTypeMap.json")
        mapper.init()

        // verify entries were parsed correctly
        assertThat(mapper.getMap()).containsOnly(
                entry("ContractType1", "SdkType1"),
                entry("ContractType2", "SdkType2"),
                entry("ContractType3", "SdkType3"))

        // verify paths are properly handled
        assertThat(mapper.toType("com.test.ContractType1")).isEqualTo("SdkType1")

        // verify non-mapped entries are not modified
        assertThat(mapper.toType("NonMappedType")).isEqualTo("NonMappedType")
        assertThat(mapper.toType("com.test.NonMappedType")).isEqualTo("com.test.NonMappedType")
    }

    @Test
    fun defaultTypeMapTest() {
        val mapper = SdkTypeMapper()
        mapper.init()

        // verify entries were parsed correctly
        assertThat(mapper.getMap()).containsOnly(
                entry("TapeType", "java.lang.String"))

        // verify paths are properly handled
        assertThat(mapper.toType("com.test.TapeType")).isEqualTo("java.lang.String")

        // verify non-mapped entries are not modified
        assertThat(mapper.toType("NonMappedType")).isEqualTo("NonMappedType")
        assertThat(mapper.toType("com.test.NonMappedType")).isEqualTo("com.test.NonMappedType")
    }
}