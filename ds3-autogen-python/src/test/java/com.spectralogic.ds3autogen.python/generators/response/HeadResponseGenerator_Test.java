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

package com.spectralogic.ds3autogen.python.generators.response;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getHeadBucketRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getHeadObjectRequest;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeadResponseGenerator_Test {
    private final static HeadResponseGenerator generator = new HeadResponseGenerator();

    @Test
    public void toParseResponsePayload_Test() {
        final String expected = "self.status_code = self.response.status\n" +
                "    if self.response.status == 200:\n" +
                "      self.result = HeadRequestStatus.EXISTS\n" +
                "    elif self.response.status == 403:\n" +
                "      self.result = HeadRequestStatus.NOTAUTHORIZED\n" +
                "    elif self.response.status == 404:\n" +
                "      self.result = HeadRequestStatus.DOESNTEXIST\n" +
                "    else:\n" +
                "      self.result = HeadRequestStatus.UNKNOWN";
        assertThat(generator.toParseResponsePayload(null), is(expected));
        assertThat(generator.toParseResponsePayload(getHeadObjectRequest()), is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatusCodes_NullList_Test() {
        generator.getStatusCodes(null, "HeadObjectRequest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatusCodes_EmptyList_Test() {
        generator.getStatusCodes(ImmutableList.of(), "HeadObjectRequest");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStatusCodes_MissingExpectedCode_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, null));

        generator.getStatusCodes(responseCodes, "HeadObjectRequest");
    }

    @Test
    public void getStatusCodes_HeadObject_Test() {
        final Ds3Request headObject = getHeadObjectRequest();
        final ImmutableList<Integer> result = generator.getStatusCodes(headObject.getDs3ResponseCodes(), headObject.getName());
        assertThat(result.size(), is(3));
        assertThat(result, hasItem(200));
        assertThat(result, hasItem(403));
        assertThat(result, hasItem(404));
    }

    @Test
    public void getStatusCodes_HeadBucket_Test() {
        final Ds3Request headBucket = getHeadBucketRequest();
        final ImmutableList<Integer> result = generator.getStatusCodes(headBucket.getDs3ResponseCodes(), headBucket.getName());
        assertThat(result.size(), is(3));
        assertThat(result, hasItem(200));
        assertThat(result, hasItem(403));
        assertThat(result, hasItem(404));
    }
}
