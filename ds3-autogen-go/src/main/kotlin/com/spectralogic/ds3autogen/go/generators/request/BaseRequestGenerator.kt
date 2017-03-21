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
import com.spectralogic.ds3autogen.api.models.enums.Operation
import com.spectralogic.ds3autogen.api.models.enums.Requirement
import com.spectralogic.ds3autogen.go.models.request.*
import com.spectralogic.ds3autogen.go.utils.toGoType
import com.spectralogic.ds3autogen.utils.ConverterUtil
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty
import com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore
import com.spectralogic.ds3autogen.utils.Helper.uncapFirst
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil
import com.spectralogic.ds3autogen.utils.RequestConverterUtil
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator

class BaseRequestGenerator : RequestModelGenerator<Request>, RequestModelGeneratorUtil {

    override fun generate(ds3Request: Ds3Request): Request {
        val name: String = NormalizingContractNamesUtil.removePath(ds3Request.name)
        val constructor: Constructor = toConstructor(ds3Request)
        val structParams: ImmutableList<Arguments> = toStructParams(ds3Request)
        val withConstructors: ImmutableList<WithConstructor> = toWithConstructors(ds3Request.optionalQueryParams)

        return Request(
                name,
                constructor,
                getHttpVerb(ds3Request.httpVerb),
                toRequestPath(ds3Request),
                structParams,
                withConstructors)
    }

    //TODO test
    override fun toWithConstructors(optionalParams: ImmutableList<Ds3Param>?): ImmutableList<WithConstructor> {
        if (isEmpty(optionalParams)) {
            return ImmutableList.of()
        }
        return optionalParams!!.stream()
                .map { param -> WithConstructor(
                        param.name,
                        toGoType(param.type, param.nullable),
                        camelToUnderscore(param.name)) }
                .collect(GuavaCollectors.immutableList())
    }

    //TODO test
    override fun toStructParams(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder: ImmutableList.Builder<Arguments> = ImmutableList.builder()
        if (ds3Request.bucketRequirement != null && ds3Request.bucketRequirement == Requirement.REQUIRED) {
            builder.add(Arguments("string", "bucketName"))
        }
        if (ds3Request.objectRequirement != null && ds3Request.objectRequirement == Requirement.REQUIRED) {
            builder.add(Arguments("string", "objectName"))
        }
        if (RequestConverterUtil.isResourceAnArg(ds3Request.resource, ds3Request.includeInPath)) {
            val resourceArg: Arguments = RequestConverterUtil.getArgFromResource(ds3Request.resource)
            builder.add(Arguments(toGoType(resourceArg.type), resourceArg.name))
        }

        builder.addAll(toGoArgumentsList(removeVoidDs3Params(ds3Request.requiredQueryParams)))
        builder.addAll(toGoArgumentsList(removeVoidDs3Params(ds3Request.optionalQueryParams)))

        // Sort the arguments
        return builder.build().stream()
                .sorted(CustomArgumentComparator())
                .collect(GuavaCollectors.immutableList())
    }

    //TODO test
    override fun toConstructor(ds3Request: Ds3Request): Constructor {
        val constructorParams: String = toConstructorParams(ds3Request)
        val queryParams: ImmutableList<VariableInterface> = toQueryParamsList(ds3Request.requiredQueryParams, ds3Request.operation)
        val structParams: ImmutableList<VariableInterface> = toStructAssignmentParams(ds3Request)

        return Constructor(constructorParams, queryParams, structParams)
    }

    //TODO test
    override fun toConstructorParams(ds3Request: Ds3Request): String {
        val constructorArgs: ImmutableList<Arguments> = toConstructorParamsList(ds3Request)
        return toFunctionInput(constructorArgs)
    }

    //TODO test
    override fun toConstructorParamsList(ds3Request: Ds3Request): ImmutableList<Arguments> {
        val builder: ImmutableList.Builder<Arguments> = ImmutableList.builder()
        if (ds3Request.bucketRequirement != null && ds3Request.bucketRequirement == Requirement.REQUIRED) {
            builder.add(Arguments("string", "bucketName"))
        }
        if (ds3Request.objectRequirement != null && ds3Request.objectRequirement == Requirement.REQUIRED) {
            builder.add(Arguments("string", "objectName"))
        }
        if (RequestConverterUtil.isResourceAnArg(ds3Request.resource, ds3Request.includeInPath)) {
            val resourceArg: Arguments = RequestConverterUtil.getArgFromResource(ds3Request.resource)
            builder.add(Arguments(toGoType(resourceArg.type), resourceArg.name))
        }

        builder.addAll(toGoArgumentsList(removeVoidDs3Params(ds3Request.requiredQueryParams)))
        return builder.build()
    }

    //TODO test
    override fun toQueryParamsList(requiredParams: ImmutableList<Ds3Param>?, operation: Operation?): ImmutableList<VariableInterface> {
        if (ConverterUtil.isEmpty(requiredParams)) {
            return ImmutableList.of()
        }
        val builder: ImmutableList.Builder<VariableInterface> = ImmutableList.builder()

        if (operation != null) {
            builder.add(Variable("operation", "\"" + operation.toString().toLowerCase() + "\""))
        }

        builder.addAll(toQueryParamVars(requiredParams))

        return builder.build()
    }

    //TODO test
    override fun toStructAssignmentParams(ds3Request: Ds3Request): ImmutableList<VariableInterface> {
        val builder: ImmutableList.Builder<VariableInterface> = ImmutableList.builder()
        if (ds3Request.bucketRequirement != null && ds3Request.bucketRequirement == Requirement.REQUIRED) {
            builder.add(SimpleVariable("bucketName"))
        }
        if (ds3Request.objectRequirement != null && ds3Request.objectRequirement == Requirement.REQUIRED) {
            builder.add(SimpleVariable("objectName"))
        }
        if (RequestConverterUtil.isResourceAnArg(ds3Request.resource, ds3Request.includeInPath)) {
            val resourceArg: Arguments = RequestConverterUtil.getArgFromResource(ds3Request.resource)
            builder.add(SimpleVariable(uncapFirst(resourceArg.name)))
        }

        builder.addAll(toSimpleVariables(ds3Request.requiredQueryParams))

        return builder.build()
    }
}
