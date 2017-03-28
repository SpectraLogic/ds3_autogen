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
import com.spectralogic.ds3autogen.go.models.request.Constructor
import com.spectralogic.ds3autogen.go.models.request.VariableInterface
import com.spectralogic.ds3autogen.go.models.request.WithConstructor

interface RequestModelGeneratorUtil {

    /**
     * Creates the request handler constructor
     */
    fun toConstructor(ds3Request: Ds3Request): Constructor

    /**
     * Creates the comma separated list of constructor parameters.
     */
    fun toConstructorParams(ds3Request: Ds3Request): String

    /**
     * Creates the list of constructor parameters. Note that the Arguments type
     * is used for ensuring proper sort order: BucketName, ObjectName, Alphabetical.
     */
    fun toConstructorParamsList(ds3Request: Ds3Request): ImmutableList<Arguments>

    /**
     * Creates the list of variables that are added to the query parameters list
     * within the request handler constructor.
     */
    fun toQueryParamsList(requiredParams: ImmutableList<Ds3Param>?, operation: Operation?): ImmutableList<VariableInterface>

    /**
     * Creates the list of variables that are assigned to the request handler struct
     * within the constructor.
     */
    fun toStructAssignmentParams(ds3Request: Ds3Request): ImmutableList<VariableInterface>

    /**
     * Creates the list of arguments that composes the request handler struct
     */
    fun toStructParams(ds3Request: Ds3Request): ImmutableList<Arguments>

    /**
     * Creates the list of request handler with-constructors that have non-nullable parameters.
     * Each constructor sets an optional request parameter
     */
    fun toWithConstructors(optionalParams: ImmutableList<Ds3Param>?): ImmutableList<WithConstructor>

    /**
     * Creates the list of request handler with-constructors that have nullable parameters.
     * Each constructor sets an optional request parameter
     */
    fun toNullableWithConstructors(optionalParams: ImmutableList<Ds3Param>?): ImmutableList<WithConstructor>
}
