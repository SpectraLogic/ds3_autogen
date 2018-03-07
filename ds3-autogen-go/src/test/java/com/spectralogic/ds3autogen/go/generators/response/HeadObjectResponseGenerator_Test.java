/*
 * ******************************************************************************
 *   Copyright 2014-2018 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.go.models.response.ResponseCode;

import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getHeadObjectRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeadObjectResponseGenerator_Test {

    private final HeadObjectResponseGenerator generator = new HeadObjectResponseGenerator();

    @Test
    public void toResponsePayloadStructTest() {
        final String expected = "BlobChecksumType ChecksumType\n" +
                "    BlobChecksums map[int64]string";

        assertThat(generator.toResponsePayloadStruct(getHeadObjectRequest().getDs3ResponseCodes()), is(expected));
    }

    @Test
    public void toResponseCodeTest() {
        final String expected = "checksumType, err := getBlobChecksumType(webResponse.Header())\n" +
                "        if err != nil {\n" +
                "            return nil, err\n" +
                "        }\n" +
                "        checksumMap, err := getBlobChecksumMap(webResponse.Header())\n" +
                "        if err != nil {\n" +
                "            return nil, err\n" +
                "        }\n" +
                "        return &HeadObjectResponse{BlobChecksumType: checksumType, BlobChecksums: checksumMap, Headers: webResponse.Header()}, nil";

        final Ds3ResponseCode ds3ResponseCode = new Ds3ResponseCode(200,
                ImmutableList.of(new Ds3ResponseType("null", null)));

        final ResponseCode result = generator.toResponseCode(ds3ResponseCode, "HeadObjectResponse");
        assertThat(result.getCode(), is(200));
        assertThat(result.getParseResponse(), is(expected));
    }
}
