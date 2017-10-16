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

package com.spectralogic.ds3autogen.testutil;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;

/**
 * Contains static utilities for testing the BaseClientGenerator
 */
public class Ds3ResponseCodeFixture {

    /**
     * Creates a list of populated response codes for testing purposes. If hasPayload is true,
     * then one of the response types will be non-null. Else, both non-error response types
     * will have a type of "null"
     */
    public static ImmutableList<Ds3ResponseCode> createTestResponseCodes(final boolean hasPayload) {
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();

        if (hasPayload) {
            builder.add(new Ds3ResponseCode(
                    200,
                    ImmutableList.of(
                            new Ds3ResponseType("com.spectralogic.s3.server.domain.JobWithChunksApiBean", null))));
        } else {
            builder.add(new Ds3ResponseCode(
                    200,
                    ImmutableList.of(
                            new Ds3ResponseType("null", null))));
        }

        builder.add(new Ds3ResponseCode(
                204,
                ImmutableList.of(
                        new Ds3ResponseType("null", null))));

        builder.add(new Ds3ResponseCode(
                404,
                ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))));

        return builder.build();
    }

    /**
     * Creates a Ds3Request with the specified name and response codes
     */
    public static Ds3Request createTestRequestWithResponseCodes(final String requestName, final boolean hasPayload) {
        return new Ds3Request(
                requestName,
                HttpVerb.GET, Classification.amazons3, null, null, null, null, null, null, false,
                createTestResponseCodes(hasPayload),
                null, null);
    }
}
