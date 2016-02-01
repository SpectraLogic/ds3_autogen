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
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isAllocateJobChunkRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isHeadBucketRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isHeadObjectRequest;

/**
 * This response generator is used to verify that the response codes contain
 * the expected response codes based on the Ds3Request
 */
public class CodesResponseGenerator extends BaseResponseGenerator {

    protected static final ImmutableList<Integer> HEAD_OBJECT_EXPECTED_RESPONSE_CODES = ImmutableList.of(200, 404);
    protected static final ImmutableList<Integer> ALLOCATE_JOB_CHUNK_EXPECTED_RESPONSE_CODES = ImmutableList.of(200, 400, 404);
    protected static final ImmutableList<Integer> HEAD_BUCKET_EXPECTED_RESPONSE_CODES = ImmutableList.of(200, 403, 404);

    /**
     * Gets the response codes required to generate this response. It also verifies that
     * this request is either AllocateJobChunk, HeadObject or HeadBucket, and that it
     * contains the expected response codes.
     */
    @Override
    public ImmutableList<Ds3ResponseCode> toResponseCodes(
            final Ds3Request request) {
        if (!isAllocateJobChunkRequest(request)
                && !isHeadObjectRequest(request)
                && !isHeadBucketRequest(request)) {
            throw new IllegalArgumentException("This generator does not support the request: " + request.getName());
        }

        if (isAllocateJobChunkRequest(request)
                && !hasExpectedResponseCodes(ALLOCATE_JOB_CHUNK_EXPECTED_RESPONSE_CODES, request.getDs3ResponseCodes())) {
            throw new IllegalArgumentException(
                    "Invalid AllocatedJobChunkRequest- does not contain expected response codes: "
                    + ALLOCATE_JOB_CHUNK_EXPECTED_RESPONSE_CODES.toString());
        }

        if (isHeadObjectRequest(request)
                && !hasExpectedResponseCodes(HEAD_OBJECT_EXPECTED_RESPONSE_CODES, request.getDs3ResponseCodes())) {
            throw new IllegalArgumentException(
                    "Invalid HeadObjectRequest- does not contain expected response codes: "
                            + HEAD_OBJECT_EXPECTED_RESPONSE_CODES.toString());
        }

        if (isHeadBucketRequest(request)
                && !hasExpectedResponseCodes(HEAD_BUCKET_EXPECTED_RESPONSE_CODES, request.getDs3ResponseCodes())) {
            throw new IllegalArgumentException(
                    "Invalid HeadBucketRequest- does not contain expected response codes: "
                            + HEAD_BUCKET_EXPECTED_RESPONSE_CODES.toString());
        }

        return removeErrorResponseCodes(request.getDs3ResponseCodes());
    }

    /**
     * Check if a given response code exists within the list of response codes
     */
    protected static boolean hasExpectedResponseCodes(
            final ImmutableList<Integer> expectedCodes,
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes) {
        if (isEmpty(ds3ResponseCodes)) {
            return false;
        }
        final ImmutableList<Integer> responseCodes = getResponseCodes(ds3ResponseCodes);
        for (final Integer code : expectedCodes) {
            if (!responseCodes.contains(code)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets a list of response codes from within a list of Ds3ResponseCodes
     */
    protected static ImmutableList<Integer> getResponseCodes(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.add(responseCode.getCode());
        }
        return builder.build();
    }
}
