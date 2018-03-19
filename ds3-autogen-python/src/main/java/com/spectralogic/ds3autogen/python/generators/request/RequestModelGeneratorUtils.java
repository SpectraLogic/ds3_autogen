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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;

public interface RequestModelGeneratorUtils {

    /**
     * Gets the list of parameters that are assigned within the request constructor
     */
    ImmutableList<String> toAssignments(final Ds3Request ds3Request);

    /**
     * Gets the list of optional Arguments for the Ds3Request
     */
    ImmutableList<Arguments> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalParams);

    /**
     * Gets the list of constructor parameters
     */
    ImmutableList<String> toConstructorParams(final Ds3Request ds3Request);

    /**
     * Converts all required params in a Ds3Request to constructor params
     */
    ImmutableList<ConstructorParam> toRequiredConstructorParams(final Ds3Request ds3Request);

    /**
     * Converts all optional params to constructor params
     */
    ImmutableList<ConstructorParam> toOptionalConstructorParams(final Ds3Request ds3Request);

    /**
     * Gets any additional python code for special casing, or null if no additional code is required
     */
    String getAdditionalContent(final String requestName);
}
