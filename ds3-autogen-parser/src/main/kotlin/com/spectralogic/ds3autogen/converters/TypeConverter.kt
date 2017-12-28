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
import com.spectralogic.ds3autogen.typemap.SdkTypeMapper
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors

/**
 * Modifies contract types into sdk types as specified within the Ds3ApiSpec.
 * This affects types within request parameters, response types, and response payload elements.
 * @param typeMapper maps contract type names to be changed into sdk types for code generation
 */
class TypeConverter(private val typeMapper: SdkTypeMapper) {
    constructor(): this(SdkTypeMapper()) // Uses the default type mapper

    fun modifyTypes(spec: Ds3ApiSpec): Ds3ApiSpec {
        typeMapper.init()
        return Ds3ApiSpec(updateDs3Requests(spec.requests), updateDs3TypeMap(spec.types))
    }

    private fun updateDs3Requests(ds3requests: ImmutableList<Ds3Request>?): ImmutableList<Ds3Request> {
        if (isEmpty(ds3requests)) {
            return ImmutableList.of()
        }
        return ds3requests!!.stream()
                .map(this::updateDs3Request)
                .collect(GuavaCollectors.immutableList())
    }

    private fun updateDs3Request(ds3Request: Ds3Request): Ds3Request {
        return Ds3Request(
                ds3Request.name,
                ds3Request.httpVerb,
                ds3Request.classification,
                ds3Request.bucketRequirement,
                ds3Request.objectRequirement,
                ds3Request.action,
                ds3Request.resource,
                ds3Request.resourceType,
                ds3Request.operation,
                ds3Request.includeInPath,
                updateResposneCodes(ds3Request.ds3ResponseCodes),
                updateParams(ds3Request.optionalQueryParams),
                updateParams(ds3Request.requiredQueryParams)
        )
    }

    private fun updateParams(params: ImmutableList<Ds3Param>?): ImmutableList<Ds3Param> {
        if (isEmpty(params)) {
            return ImmutableList.of()
        }

        return params!!.stream()
                .map { param -> Ds3Param(param.name, typeMapper.toType(param.type), param.nullable) }
                .collect(GuavaCollectors.immutableList())
    }

    private fun updateResposneCodes(responseCodes: ImmutableList<Ds3ResponseCode>?): ImmutableList<Ds3ResponseCode> {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of()
        }

        return responseCodes!!.stream()
                .map { code -> Ds3ResponseCode(code.code, updateResponseTypes(code.ds3ResponseTypes)) }
                .collect(GuavaCollectors.immutableList())
    }

    private fun updateResponseTypes(responseTypes: ImmutableList<Ds3ResponseType>?): ImmutableList<Ds3ResponseType> {
        if (isEmpty(responseTypes)) {
            return ImmutableList.of()
        }

        return responseTypes!!.stream()
                .map { responseType -> Ds3ResponseType(
                        typeMapper.toType(responseType.type),
                        typeMapper.toNullableType(responseType.componentType),
                        responseType.originalTypeName) }
                .collect(GuavaCollectors.immutableList())
    }

    private fun updateDs3TypeMap(typeMap: ImmutableMap<String, Ds3Type>?): ImmutableMap<String, Ds3Type> {
        if (isEmpty(typeMap)) {
            return ImmutableMap.of()
        }

        val builder = ImmutableMap.builder<String, Ds3Type>()
        typeMap!!.forEach { name, type -> builder.put(name, updateDs3Type(type)) }

        return builder.build()
    }

    private fun updateDs3Type(ds3Type: Ds3Type): Ds3Type {
        return Ds3Type(
                ds3Type.name,
                ds3Type.nameToMarshal,
                updateDs3Elements(ds3Type.elements),
                ds3Type.enumConstants
        )
    }

    private fun updateDs3Elements(elements: ImmutableList<Ds3Element>?): ImmutableList<Ds3Element> {
        if (isEmpty(elements)) {
            return ImmutableList.of()
        }

        return elements!!.stream()
                .map { element -> Ds3Element(
                        element.name,
                        typeMapper.toNullableType(element.type),
                        typeMapper.toNullableType(element.componentType),
                        element.ds3Annotations,
                        element.nullable
                ) }
                .collect(GuavaCollectors.immutableList())
    }
}