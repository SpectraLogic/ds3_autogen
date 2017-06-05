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

package com.spectralogic.ds3autogen.go.generators.response;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.go.models.response.ResponseCode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectResponseGenerator_Test {

    private final GetObjectResponseGenerator generator = new GetObjectResponseGenerator();

    @Test
    public void toResponseCode_200_Test() {
        final ResponseCode expected = new ResponseCode(200, "return &GetObjectResponse{ Content: webResponse.Body(), Headers: webResponse.Header() }, nil");

        final Ds3ResponseCode code = new Ds3ResponseCode(200,
                ImmutableList.of(new Ds3ResponseType("null", null)));

        final ResponseCode result = generator.toResponseCode(code, "GetObjectResponse");

        assertThat(result, is(expected));
    }

    @Test
    public void toResponseCode_206_Test() {
        final ResponseCode expected = new ResponseCode(206, "return &GetObjectResponse{Headers: webResponse.Header()}, nil");

        final Ds3ResponseCode code = new Ds3ResponseCode(206,
                ImmutableList.of(new Ds3ResponseType("null", null)));

        final ResponseCode result = generator.toResponseCode(code, "GetObjectResponse");

        assertThat(result, is(expected));
    }

    @Test
    public void toResponsePayloadStruct_Test() {
        assertThat(generator.toResponsePayloadStruct(ImmutableList.of()), is("Content io.ReadCloser"));
    }

    @Test
    public void toImportSet_Test() {
        final ImmutableSet<String> expectedImports = ImmutableSet.of("io");

        final ImmutableSet<String> result = generator.toImportSet();

        assertThat(result.size(), is(expectedImports.size()));
        expectedImports.forEach(expected -> assertThat(result, hasItem(expected)));
    }
}
