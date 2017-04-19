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

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb

data class Request(
        val name: String,
        val constructor: Constructor,
        val httpVerb: HttpVerb,
        val path: String,
        val structParams: ImmutableList<Arguments>, // Parameters that make up the request handler struct
        val withConstructors: ImmutableList<WithConstructor>, // Optional constructors with non-null type
        val nullableWithConstructors: ImmutableList<WithConstructor>, // Optional constructors that can be null
        val voidWithConstructors: ImmutableList<WithConstructor>) // Optional constructor for a void type optional param