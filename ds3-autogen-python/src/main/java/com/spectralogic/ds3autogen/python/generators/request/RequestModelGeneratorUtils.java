/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.RequestPayload;

public interface RequestModelGeneratorUtils {

    /**
     * Gets the list of required Arguments for the Ds3Request
     */
    ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request);

    /**
     * Gets the list of optional Arguments for the Ds3Request
     */
    ImmutableList<Arguments> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalParams);

    /**
     * Gets the request payload model, or null if one does not exist
     */
    RequestPayload toRequestPayload(final Ds3Request ds3Request, final String requestName);
}
