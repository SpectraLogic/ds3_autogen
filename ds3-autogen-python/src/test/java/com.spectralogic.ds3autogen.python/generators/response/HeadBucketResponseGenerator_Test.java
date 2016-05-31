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

package com.spectralogic.ds3autogen.python.generators.response;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getHeadBucketRequest;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class HeadBucketResponseGenerator_Test {

    private final static HeadBucketResponseGenerator generator = new HeadBucketResponseGenerator();

    @Test
    public void toParseResponsePayload_Test() {
        final String expected = "self.status_code = self.response.status\n" +
                "    if self.response.status == 200:\n" +
                "      self.result = HeadBucketStatus.EXISTS\n" +
                "    elif self.response.status == 403:\n" +
                "      self.result = HeadBucketStatus.NOTAUTHORIZED\n" +
                "    elif self.response.status == 404:\n" +
                "      self.result = HeadBucketStatus.DOESNTEXIST\n" +
                "    else:\n" +
                "      self.result = HeadBucketStatus.UNKNOWN";
        assertThat(generator.toParseResponsePayload(null, null), is(expected));
        assertThat(generator.toParseResponsePayload(getHeadBucketRequest(), ImmutableMap.of()), is(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getStatusCodes_NullList_Test() {
        generator.getStatusCodes(null, "HeadBucketRequest");
    }

    @Test (expected = IllegalArgumentException.class)
    public void getStatusCodes_EmptyList_Test() {
        generator.getStatusCodes(ImmutableList.of(), "HeadBucketRequest");
    }

    @Test (expected = IllegalArgumentException.class)
    public void getStatusCodes_MissingExpectedCode_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(404, null));

        generator.getStatusCodes(responseCodes, "HeadBucketRequest");
    }

    @Test
    public void getStatusCodes_Test() {
        final Ds3Request headBucket = getHeadBucketRequest();
        generator.getStatusCodes(headBucket.getDs3ResponseCodes(), headBucket.getName());
    }
}
