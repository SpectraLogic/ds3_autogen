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

package com.spectralogic.ds3autogen.java.test.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;

/**
 * This class provides utilities for creating Ds3ResponseCodes for testing
 */
public final class Ds3ResponseCodeFixtureTestHelper {

    /**
     * Creates a populated Ds3ResponseCode with a non-error response code
     */
    public static Ds3ResponseCode createPopulatedResponseCode(final String variation) {
        return new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.Test.Type" + variation, null)));
    }

    /**
     * Creates a populated Ds3ResponseCode with a null response type
     */
    public static Ds3ResponseCode createPopulatedNullResponseCode() {
        return new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("null", null)));
    }

    /**
     * Creates a populated Ds3ResponseCode with a 400 response code
     */
    public static Ds3ResponseCode createPopulatedErrorResponseCode(final String variation) {
        return new Ds3ResponseCode(
                400,
                ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.Test.Type" + variation, null)));
    }

    public static ImmutableList<Ds3ResponseCode> getTestResponseCodes() {
        return ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(206, null),
                new Ds3ResponseCode(307, null),
                new Ds3ResponseCode(400, null),
                new Ds3ResponseCode(404, null));
    }
}
