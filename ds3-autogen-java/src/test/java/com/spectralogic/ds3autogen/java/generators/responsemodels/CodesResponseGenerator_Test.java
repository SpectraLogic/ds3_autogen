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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.responsemodels.CodesResponseGenerator.getResponseCodes;
import static com.spectralogic.ds3autogen.java.generators.responsemodels.CodesResponseGenerator.hasExpectedResponseCodes;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestMultiFileDelete;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CodesResponseGenerator_Test {

    private final static CodesResponseGenerator generator = new CodesResponseGenerator();

    private static ImmutableList<Ds3ResponseCode> getTestResponseCodes() {
        return ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(206, null),
                new Ds3ResponseCode(307, null),
                new Ds3ResponseCode(400, null),
                new Ds3ResponseCode(404, null));
    }

    @Test
    public void getResponseCodes_NullList_Test() {
        final ImmutableList<Integer> result = getResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseCodes_EmptyList_Test() {
        final ImmutableList<Integer> result = getResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getResponseCodes_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = getTestResponseCodes();

        final ImmutableList<Integer> result = getResponseCodes(responseCodes);
        assertThat(result.size(), is(5));
        assertTrue(result.contains(200));
        assertTrue(result.contains(206));
        assertTrue(result.contains(307));
        assertTrue(result.contains(400));
        assertTrue(result.contains(404));
    }

    @Test
    public void hasExpectedResponseCodes_NullList_Test() {
        assertFalse(hasExpectedResponseCodes(
                CodesResponseGenerator.EXPECTED_RESPONSE_CODES,
                null));
    }

    @Test
    public void hasExpectedResponseCodes_EmptyList_Test() {
        assertFalse(hasExpectedResponseCodes(
                CodesResponseGenerator.EXPECTED_RESPONSE_CODES,
                ImmutableList.of()));
    }

    @Test
    public void hasExpectedResponseCodes_FullList_Test() {
        assertTrue(hasExpectedResponseCodes(
                CodesResponseGenerator.EXPECTED_RESPONSE_CODES,
                getTestResponseCodes()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void toResponseCodes_Exception1_Test() {
        final Ds3Request multiFileDelete = getRequestMultiFileDelete();
        generator.toResponseCodes(multiFileDelete);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toResponseCodes_Exception2_Test() {
        final Ds3Request malformedAllocateJobChunk = new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.AllocateJobChunkRequestHandler",
                HttpVerb.PUT,
                Classification.spectrads3,
                null,
                null,
                Action.MODIFY,
                Resource.JOB_CHUNK,
                ResourceType.NON_SINGLETON,
                Operation.ALLOCATE,
                true,
                ImmutableList.of(
                        new Ds3ResponseCode(200, null),
                        new Ds3ResponseCode(400, null),
                        new Ds3ResponseCode(504, null)),
                null,
                null);
        generator.toResponseCodes(malformedAllocateJobChunk);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toResponseCodes_Exception3_Test() {
        final Ds3Request malformedHeadObject = new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.HeadObjectRequestHandler",
                HttpVerb.HEAD,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                false,
                ImmutableList.of(
                        new Ds3ResponseCode(200, null),
                        new Ds3ResponseCode(504, null)),
                null,
                null);

        generator.toResponseCodes(malformedHeadObject);
    }

    @Test(expected = IllegalArgumentException.class)
    public void toResponseCodes_Exception4_Test() {
        final Ds3Request malformedHeadBucket = new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.HeadBucketRequestHandler",
                HttpVerb.HEAD,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.NOT_ALLOWED,
                null,
                null,
                null,
                null,
                false,
                ImmutableList.of(
                        new Ds3ResponseCode(200, null),
                        new Ds3ResponseCode(403, null),
                        new Ds3ResponseCode(504, null)),
                null,
                null);

        generator.toResponseCodes(malformedHeadBucket);
    }

    @Test
    public void toResponseCodes_AllocateJobChunk_Test() {
        final Ds3Request allocateJobChunk = new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.AllocateJobChunkRequestHandler",
                HttpVerb.PUT,
                Classification.spectrads3,
                null,
                null,
                Action.MODIFY,
                Resource.JOB_CHUNK,
                ResourceType.NON_SINGLETON,
                Operation.ALLOCATE,
                true,
                ImmutableList.of(
                        new Ds3ResponseCode(200, null),
                        new Ds3ResponseCode(400, null),
                        new Ds3ResponseCode(404, null)),
                null,
                null);
        final ImmutableList<Ds3ResponseCode> result = generator.toResponseCodes(allocateJobChunk);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getCode(), is(200));
    }

    @Test
    public void toResponseCodes_HeadObject_Test() {
        final Ds3Request headObject = new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.HeadObjectRequestHandler",
                HttpVerb.HEAD,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                false,
                ImmutableList.of(
                        new Ds3ResponseCode(200, null),
                        new Ds3ResponseCode(404, null)),
                null,
                null);

        final ImmutableList<Ds3ResponseCode> result = generator.toResponseCodes(headObject);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getCode(), is(200));
    }

    @Test
    public void toResponseCodes_HeadBucket_Test() {
        final Ds3Request headBucket = new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.amazons3.HeadBucketRequestHandler",
                HttpVerb.HEAD,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.NOT_ALLOWED,
                null,
                null,
                null,
                null,
                false,
                ImmutableList.of(
                        new Ds3ResponseCode(200, null),
                        new Ds3ResponseCode(403, null),
                        new Ds3ResponseCode(404, null)),
                null,
                null);

        final ImmutableList<Ds3ResponseCode> result = generator.toResponseCodes(headBucket);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getCode(), is(200));
    }
}
