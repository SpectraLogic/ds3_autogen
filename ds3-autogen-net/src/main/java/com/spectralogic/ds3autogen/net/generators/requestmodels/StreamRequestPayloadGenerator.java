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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.net.utils.GeneratorUtils;

/**
 * Used with requests that have a required Stream request payload
 */
public class StreamRequestPayloadGenerator extends BaseRequestGenerator {

    /**
     * Gets the list of Arguments for creating the constructor, which is comprised of the
     * required parameters and the request payload stream
     */
    @Override
    public ImmutableList<Arguments> toConstructorArgsList(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(GeneratorUtils.getRequiredArgs(ds3Request));
        builder.add(new Arguments("Stream", "RequestPayload"));
        return builder.build();
    }
}
