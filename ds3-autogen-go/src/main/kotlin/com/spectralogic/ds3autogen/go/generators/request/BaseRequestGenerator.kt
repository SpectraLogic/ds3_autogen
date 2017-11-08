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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.api.models.enums.Requirement
import com.spectralogic.ds3autogen.go.models.request.*
import com.spectralogic.ds3autogen.go.utils.*
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty
import com.spectralogic.ds3autogen.utils.Helper.uncapFirst
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator

open class BaseRequestGenerator : RequestModelGenerator<Request>, RequestModelGeneratorUtil {

    override fun generate(ds3Request: Ds3Request): Request {
        val name = NormalizingContractNamesUtil.removePath(ds3Request.name)
        val constructor = toConstructor(ds3Request)
        val structParams = toStructParams(ds3Request)
        val withConstructors = toWithConstructors(ds3Request.optionalQueryParams)

        return Request(
                name,
                constructor,
                structParams,
                withConstructors)
    }

    /**
     * Converts the list of optional request parameters into a list of request handler constructors.
     */
    override fun toWithConstructors(optionalParams: ImmutableList<Ds3Param>?): ImmutableList<WithConstructor> {
        if (isEmpty(optionalParams)) {
            return ImmutableList.of()
        }

        return optionalParams!!.stream()
                .map { param -> toWithConstructor(param) }
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Converts an optional request parameter into a request handler constructor
     */
    protected fun toWithConstructor(ds3Param: Ds3Param): WithConstructor {
        val goName = uncapFirst(toGoParamName(ds3Param.name, ds3Param.type))
        if (ds3Param.type == "void") {
            return VoidWithConstructor(goName)
        }
        val goType = toGoType(ds3Param.type)
        if (isGoPrimitiveType(goType)) {
            return PrimitivePointerWithConstructor(goName, goType)
        }
        return InterfaceWithConstructor(goName, goType)
    }

    /**
     * Retrieves the list of parameters that make up the Go request struct
     */
    override fun toStructParams(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val structParams = structParamsFromRequest(ds3Request)

        // Sort the arguments
        return ImmutableList.sortedCopyOf(CustomArgumentComparator(), structParams)
    }

    /**
     * Retrieves the list of parameters that make up the Go request struct
     * from the Ds3Request
     */
    protected fun structParamsFromRequest(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder: ImmutableList.Builder<Arguments> = ImmutableList.builder()
        builder.addAll(paramsListFromRequest(ds3Request))
        builder.addAll(toOptionalStructParams(ds3Request.optionalQueryParams))
        return builder.build()
    }

    /**
     * Retrieves the list of optional parameters for the Go request struct
     * and ensures they are nullable. Optional void parameters are converted
     * to non-nullable booleans.
     */
    protected fun toOptionalStructParams(optionalParams: ImmutableList<Ds3Param>?): ImmutableList<Arguments> {
        if (isEmpty(optionalParams)) {
            return ImmutableList.of()
        }
        return optionalParams!!.stream()
                .map { param ->
                    when(param.type) {
                        "void" -> Arguments("bool", toGoParamName(param.name, param.type))
                        else -> Arguments(toGoRequestType(param.type, nullable = true), toGoParamName(param.name, param.type))
                    } }
                .collect(GuavaCollectors.immutableList())
    }

    override fun toConstructor(ds3Request: Ds3Request): Constructor {
        val constructorParams: String = toConstructorParams(ds3Request)
        val structParams: ImmutableList<VariableInterface> = toStructAssignmentParams(ds3Request)

        return Constructor(constructorParams, structParams)
    }

    override fun toConstructorParams(ds3Request: Ds3Request): String {
        val constructorArgs: ImmutableList<Arguments> = toConstructorParamsList(ds3Request)
        return toFunctionInput(constructorArgs)
    }

    override fun toConstructorParamsList(ds3Request: Ds3Request): ImmutableList<Arguments> {
        return paramsListFromRequest(ds3Request)
    }

    /**
     * Retrieves constructor param list from the Ds3Request. This is reused by special-cased
     * generators to fetch always-present constructor params.
     */
    fun paramsListFromRequest(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder: ImmutableList.Builder<Arguments> = ImmutableList.builder()
        if (ds3Request.bucketRequirement != null && ds3Request.bucketRequirement == Requirement.REQUIRED) {
            builder.add(Arguments("string", "BucketName"))
        }
        if (ds3Request.objectRequirement != null && ds3Request.objectRequirement == Requirement.REQUIRED) {
            builder.add(Arguments("string", "ObjectName"))
        }
        if (ds3Request.isGoResourceAnArg()) {
            val resourceArg: Arguments = ds3Request.getGoArgFromResource()
            builder.add(Arguments(toGoType(resourceArg.type), resourceArg.name))
        }

        if (isEmpty(ds3Request.requiredQueryParams)) {
            return builder.build()
        }

        ds3Request.requiredQueryParams!!.stream()
                .filter{ param -> !param.type.equals("void", ignoreCase = true) //filter out void parameters
                        && !param.name.equals("Operation", ignoreCase = true) } //filter out Operation parameter
                .forEach { param ->
                    // Convert to a non-nullable Go argument and add to builder
                    val goType = toGoRequestType(param.type, false)
                    val goName = toGoParamName(param.name, param.type)
                    builder.add(Arguments(goType, goName))
                }

        return builder.build()
    }

    /**
     * Retrieves the list of struct assignments that are performed within the
     * Go request constructor
     */
    override fun toStructAssignmentParams(ds3Request: Ds3Request): ImmutableList<VariableInterface> {
        return structAssignmentParamsFromRequest(ds3Request)
    }

    /**
     * Retrieves the list of struct assignments from the Ds3Request
     */
    protected fun structAssignmentParamsFromRequest(ds3Request: Ds3Request): ImmutableList<VariableInterface> {
        val builder: ImmutableList.Builder<VariableInterface> = ImmutableList.builder()
        if (ds3Request.bucketRequirement != null && ds3Request.bucketRequirement == Requirement.REQUIRED) {
            builder.add(SimpleVariable("bucketName"))
        }
        if (ds3Request.objectRequirement != null && ds3Request.objectRequirement == Requirement.REQUIRED) {
            builder.add(SimpleVariable("objectName"))
        }
        if (ds3Request.isGoResourceAnArg()) {
            val resourceArg: Arguments = ds3Request.getGoArgFromResource()
            builder.add(SimpleVariable(uncapFirst(resourceArg.name)))
        }

        if (isEmpty(ds3Request.requiredQueryParams)) {
            return builder.build()
        }

        ds3Request.requiredQueryParams!!.stream()
                .filter{ param -> !param.type.equals("void", ignoreCase = true) //filter out void parameters
                        && !param.name.equals("Operation", ignoreCase = true) } //filter out Operation parameter
                .forEach { param ->
                    // Create a simple variable from the parameter
                    builder.add(SimpleVariable(uncapFirst(toGoParamName(param.name, param.type))))
                }

        return builder.build()
    }
}
