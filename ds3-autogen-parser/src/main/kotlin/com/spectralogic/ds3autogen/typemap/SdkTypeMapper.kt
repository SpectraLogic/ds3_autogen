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

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.guava.GuavaModule
import com.spectralogic.ds3autogen.models.xml.typemap.TypeMap
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty

private const val DEFAULT_TYPE_MAP_FILE = "/typeMap.json"

/**
 * Used to map contract types into sdk types. This is used when the
 * provided contract type is to be overridden with a manually determined type.
 */
class SdkTypeMapper(private val typeMapFileName: String) {
    constructor() : this(DEFAULT_TYPE_MAP_FILE)

    /** map of contract names to be changed (keys), into sdk types (values) */
    private lateinit var typeMapper: Map<String, String>

    /**
     * Initialize the type mapper via parsing the json file containing the type re-mapping
     */
    fun init() {
        val stream = SdkTypeMapper::class.java.getResourceAsStream(typeMapFileName)
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(GuavaModule())
        typeMapper = objectMapper.readValue<TypeMap>(stream, TypeMap::class.java).toMap()
    }

    /**
     * Returns the sdk type to be generated from the contract type. If there is no type
     * mapping, then the original string is returned.
     */
    fun toType(contractType: String): String {
        val curType = contractType.substringAfterLast('.')
        return if (typeMapper.containsKey(curType)) typeMapper[curType]!! else contractType
    }

    fun toNullableType(contractType: String?): String? {
        if (isEmpty(contractType)) {
            return contractType
        }
        return toType(contractType!!)
    }

    /**
     * Retrieves the underlying map used in the re-typing process. This is used for testing.
     */
    fun getMap(): Map<String, String> {
        return typeMapper
    }
}