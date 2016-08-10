/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import com.spectralogic.ds3autogen.net.model.request.RequestConstructor;

public interface RequestModelGeneratorUtils {

    /**
     * Gets the list of required Arguments from a Ds3Request
     */
    ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request);

    /**
     * Gets the list of optional Arguments from the Ds3Request list of optional Ds3Param
     */
    ImmutableList<NetNullableVariable> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalparams, final ImmutableMap<String, Ds3Type> typeMap);

    /**
     * Gets the list of Arguments for creating the constructor
     */
    ImmutableList<Arguments> toConstructorArgsList(final Ds3Request ds3Request);

    /**
     * Gets the list of Arguments that are added to query params list in constructor
     */
    ImmutableList<Arguments> toQueryParamsList(final Ds3Request ds3Request);

    /**
     * Gets the list of constructors for this request
     */
    ImmutableList<RequestConstructor> toConstructorList(final Ds3Request ds3Request);
}
