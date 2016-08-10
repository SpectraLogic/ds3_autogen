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
import com.spectralogic.ds3autogen.net.utils.GeneratorUtils;
import com.spectralogic.ds3autogen.utils.RequestConverterUtil;

public class GetObjectRequestGenerator extends BaseRequestGenerator {

    /**
     * The get object request has no optional arguments
     */
    @Override
    public ImmutableList<NetNullableVariable> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalParams, final ImmutableMap<String, Ds3Type> typeMap) {
        return ImmutableList.of();
    }

    /**
     * The get object request has both optional and required params as required arguments.
     */
    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(GeneratorUtils.getRequiredArgs(ds3Request));
        builder.addAll(RequestConverterUtil.getArgsFromParamList(ds3Request.getOptionalQueryParams()));
        return builder.build();
    }

    /**
     * Gets the optional and required params, and adds DestinationStream
     */
    @Override
    public ImmutableList<Arguments> toConstructorArgsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(toRequiredArgumentsList(ds3Request));
        builder.add(new Arguments("Stream", "DestinationStream"));
        return builder.build();
    }
}
