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

package com.spectralogic.ds3autogen.converters

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.spectralogic.ds3autogen.api.models.apispec.*
import com.spectralogic.ds3autogen.api.models.enums.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.entry
import org.junit.Test

class TypeConverterTest {

    @Test
    fun modifyTypesTest() {
        val request = Ds3Request(
                "com.test.TestRequest",
                HttpVerb.PUT,
                Classification.spectrads3,
                null,
                null,
                Action.BULK_MODIFY,
                Resource.TAPE,
                ResourceType.NON_SINGLETON,
                Operation.EJECT,
                false,
                ImmutableList.of(
                        Ds3ResponseCode(200,
                                ImmutableList.of(Ds3ResponseType("com.test.TapeType", null)))),
                ImmutableList.of(
                        Ds3Param("TapeType", "com.test.TapeType", true),
                        Ds3Param("EjectLocation", "java.lang.String", true)),
                ImmutableList.of(
                    Ds3Param("Blobs", "void", false),
                    Ds3Param("TapeType", "com.test.TapeType", false)))

        val type = Ds3Type(
                "com.test.TestType",
                ImmutableList.of(
                        Ds3Element("TapeTypeElement", "com.test.TapeType", null, false),
                        Ds3Element("TapeTypeArray", "array", "com.test.TapeType", false),
                        Ds3Element("IntElement", "int", null, false)
                )
        )

        val spec = Ds3ApiSpec(ImmutableList.of(request), ImmutableMap.of(type.name, type))

        val converter = TypeConverter()

        val result = converter.modifyTypes(spec)

        assertThat(result.requests).containsExactly(
                Ds3Request(
                        "com.test.TestRequest",
                        HttpVerb.PUT,
                        Classification.spectrads3,
                        null,
                        null,
                        Action.BULK_MODIFY,
                        Resource.TAPE,
                        ResourceType.NON_SINGLETON,
                        Operation.EJECT,
                        false,
                        ImmutableList.of(
                                Ds3ResponseCode(200,
                                        ImmutableList.of(Ds3ResponseType("java.lang.String", null)))),
                        ImmutableList.of(
                                Ds3Param("TapeType", "java.lang.String", true),
                                Ds3Param("EjectLocation", "java.lang.String", true)),
                        ImmutableList.of(
                                Ds3Param("Blobs", "void", false),
                                Ds3Param("TapeType", "java.lang.String", false)))
        )

        assertThat(result.types).containsExactly(
                entry(type.name, Ds3Type(
                        "com.test.TestType",
                        ImmutableList.of(
                                Ds3Element("TapeTypeElement", "java.lang.String", null, false),
                                Ds3Element("TapeTypeArray", "array", "java.lang.String", false),
                                Ds3Element("IntElement", "int", null, false)
                        )))
        )
    }
}