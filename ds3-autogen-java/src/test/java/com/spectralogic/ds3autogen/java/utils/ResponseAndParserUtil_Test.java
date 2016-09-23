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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import org.junit.Test;

import java.util.NoSuchElementException;

import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.*;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ResponseAndParserUtil_Test {

    @Test
    public void getImportFromResponseCode_Test() {
        final Ds3ResponseCode responseCode = createPopulatedResponseCode("");
        final String result = getImportFromResponseCode(responseCode);
        assertThat(result, is("com.spectralogic.ds3client.models.Type"));
    }

    @Test
    public void getImportFromResponseCode_NullReturnType_Test() {
        final Ds3ResponseCode responseCode = createPopulatedNullResponseCode();
        final String result = getImportFromResponseCode(responseCode);
        assertThat(result, is(""));
    }

    @Test
    public void getImportFromResponseCode_ErrorResponseCode_Test() {
        final Ds3ResponseCode responseCode = createPopulatedErrorResponseCode("");
        final String result = getImportFromResponseCode(responseCode);
        assertThat(result, is(""));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getImportFromResponseCode_ErrorComponentType_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                400,
                ImmutableList.of(
                        new Ds3ResponseType("array", "com.spectralogic.Test.Type")));
        getImportFromResponseCode(responseCode);
    }

    @Test
    public void getImportListFromResponseCodes_NullList_Test() {
        final ImmutableSet<String> result = getImportListFromResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getImportListFromResponseCodes_EmptyList_Test() {
        final ImmutableSet<String> result = getImportListFromResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getImportListFromResponseCodes_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode("_v1"),
                createPopulatedResponseCode("_v2"),
                createPopulatedErrorResponseCode("_v3"));

        final ImmutableSet<String> result = getImportListFromResponseCodes(responseCodes);
        assertThat(result.size(), is(2));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v1"));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v2"));
    }

    @Test
    public void removeErrorResponseCodes_NullList_Test() {
        final ImmutableList<Ds3ResponseCode> result = removeErrorResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeErrorResponseCodes_EmptyList_Test() {
        final ImmutableList<Ds3ResponseCode> result = removeErrorResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeErrorResponseCodes_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(206, null),
                new Ds3ResponseCode(307, null),
                new Ds3ResponseCode(400, null),
                new Ds3ResponseCode(503, null));

        final ImmutableList<Ds3ResponseCode> result = removeErrorResponseCodes(responseCodes);

        assertThat(result.size(), is(3));
        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(1).getCode(), is(206));
        assertThat(result.get(2).getCode(), is(307));
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

    @Test (expected = NoSuchElementException.class)
    public void getDs3ResponseCode_NullList_Test() {
        getDs3ResponseCode(null, 200);
    }

    @Test (expected = NoSuchElementException.class)
    public void getDs3ResponseCode_EmptyList_Test() {
        getDs3ResponseCode(ImmutableList.of(), 200);
    }

    @Test (expected = NoSuchElementException.class)
    public void getDs3ResponseCode_NotInList_Test() {
        getDs3ResponseCode(getTestResponseCodes(), 100);
    }

    @Test
    public void getDs3ResponseCode_Test() {
        final Ds3ResponseCode result = getDs3ResponseCode(getTestResponseCodes(), 200);
        assertThat(result.getCode(), is(200));
    }
}
