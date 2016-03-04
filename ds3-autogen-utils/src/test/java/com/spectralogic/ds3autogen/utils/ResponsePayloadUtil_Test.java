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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ResponseCodeFixture.createTestResponseCodes;
import static com.spectralogic.ds3autogen.utils.ResponsePayloadUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ResponsePayloadUtil_Test {

    @Test
    public void isErrorCode_Test() {
        assertFalse(isErrorCode(100));
        assertFalse(isErrorCode(200));
        assertFalse(isErrorCode(299));
        assertTrue(isErrorCode(300));
        assertTrue(isErrorCode(400));
    }

    @Test
    public void isNonErrorCode_Test() {
        assertTrue(isNonErrorCode(100));
        assertTrue(isNonErrorCode(200));
        assertTrue(isNonErrorCode(299));
        assertFalse(isNonErrorCode(300));
        assertFalse(isNonErrorCode(400));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponseType_NullList_Test() {
        getResponseType(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponseType_EmptyList_Test() {
        getResponseType(ImmutableList.of());
    }

    @Test
    public void getResponseType_ListSizeOne_Test() {
        final String result = getResponseType(ImmutableList.of(
                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null)));
        assertThat(result, is("com.spectralogic.s3.server.domain.HttpErrorResultApiBean"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponseType_ListSizeTwo_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = ImmutableList.of(
                new Ds3ResponseType("null", null),
                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null));

        getResponseType(responseTypes);
    }

    @Test
    public void getAllResponseTypes_NullList_Test() {
        final ImmutableList<String> result = getAllResponseTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllResponseTypes_EmptyList_Test() {
        final ImmutableList<String> result = getAllResponseTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllResponseTypes_FullList_Test() {
        final ImmutableList<String> result = getAllResponseTypes(createTestResponseCodes(true));
        assertThat(result.size(), is(2));
        assertTrue(result.contains("com.spectralogic.s3.server.domain.JobWithChunksApiBean"));
        assertTrue(result.contains("null"));
    }

    @Test
    public void hasResponsePayload_NullResponses_Test() {
        assertFalse(hasResponsePayload(createTestResponseCodes(false)));
    }

    @Test
    public void hasResponsePayload_PayloadResponse_Test() {
        assertTrue(hasResponsePayload(createTestResponseCodes(true)));
    }
}
