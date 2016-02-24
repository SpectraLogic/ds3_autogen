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
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.net.util.GeneratorUtils;

public class GetObjectRequestGenerator extends BaseRequestGenerator {

    /**
     * The get object request has no optional arguments
     */
    @Override
    public ImmutableList<Arguments> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalparams) {
        return ImmutableList.of();
    }

    /**
     * The get object request has both optional and required params as required arguments.
     */
    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(GeneratorUtils.getRequiredArgs(ds3Request));
        builder.addAll(GeneratorUtils.getArgsFromParamList(ds3Request.getOptionalQueryParams()));
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
