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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;

/**
 * Contains Ds3ResponseCode fixtures designed to test response parser generation
 */
public final class ResponseParserGeneratorTestUtil {

    public static Ds3ResponseCode getNullResponseCode() {
        return new Ds3ResponseCode(200, ImmutableList.of(new Ds3ResponseType("null", null)));
    }

    public static Ds3ResponseCode getStringResponseCode() {
        return new Ds3ResponseCode(201, ImmutableList.of(new Ds3ResponseType("java.lang.String", null)));
    }

    public static Ds3ResponseCode getBaseResponseCode() {
        return new Ds3ResponseCode(202, ImmutableList.of(new Ds3ResponseType("com.test.Type", null)));
    }
}
