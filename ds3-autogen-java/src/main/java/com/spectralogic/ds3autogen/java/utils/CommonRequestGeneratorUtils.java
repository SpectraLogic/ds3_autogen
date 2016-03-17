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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;

/**
 * Contains static util methods used by some of the Java Request Generators, but not
 * by all. Methods in this class should have no clear placement within the request
 * generator inheritance structure.
 */
public class CommonRequestGeneratorUtils {

    /**
     * Creates a constructor with the provided parameters in addition to
     * adding the required parameter Stream for parsing a request payload.
     */
    public static RequestConstructor createInputStreamConstructor(
            final ImmutableList<Arguments> parameters,
            final ImmutableList<Arguments> queryParams) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        if (hasContent(parameters)) {
            builder.addAll(parameters);
        }
        builder.add(new Arguments("InputStream", "Stream"));

        final ImmutableList<Arguments> updatedParameters = builder.build();
        return new RequestConstructor(
                updatedParameters,
                updatedParameters,
                queryParams);
    }

    /**
     * Creates a constructor with the provided parameters in addition to
     * adding the required parameter Channel for parsing a request payload.
     */
    public static RequestConstructor createChannelConstructor(
            final ImmutableList<Arguments> parameters,
            final ImmutableList<Arguments> queryParams) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(parameters);
        builder.add(new Arguments("SeekableByteChannel", "Channel"));

        final ImmutableList<String> additionalLines = ImmutableList.of(
                "this.stream = new SeekableByteChannelInputStream(channel);");

        final ImmutableList<Arguments> updatedParameters = builder.build();
        return new RequestConstructor(
                false,
                additionalLines,
                updatedParameters,
                updatedParameters,
                queryParams);
    }
}
