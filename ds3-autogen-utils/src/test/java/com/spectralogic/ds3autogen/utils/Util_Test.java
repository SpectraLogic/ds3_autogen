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
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Util_Test {

    @Test
    public void converterUtilNullList() {
        final ImmutableList<String> nullList = null;
        assertTrue(isEmpty(nullList));
        assertFalse(hasContent(nullList));
    }

    @Test
    public void convertUtilEmptyList() {
        final ImmutableList<String> emptyList = ImmutableList.of();
        assertTrue(isEmpty(emptyList));
        assertFalse(hasContent(emptyList));
    }
    @Test
    public void convertUtilFullList() {
        final ImmutableList<String> fullList = ImmutableList.of("One", "Two", "Three");
        assertTrue(hasContent(fullList));
        assertFalse(isEmpty(fullList));
    }

    @Test
    public void convertUtilNullMap() {
        final ImmutableMap<String, String> nullMap = null;
        assertTrue(isEmpty(nullMap));
        assertFalse(hasContent(nullMap));
    }
    @Test
    public void convertUtilEmptyMap() {
        final ImmutableMap<String, String> emptyMap = ImmutableMap.of();
        assertTrue(isEmpty(emptyMap));
        assertFalse(hasContent(emptyMap));
    }

    @Test
    public void convertUtilFullMap() {
        final ImmutableMap<String, String> fullMap = ImmutableMap.of(
                "key1", "value1",
                "key2", "value2");
        assertTrue(hasContent(fullMap));
        assertFalse(isEmpty(fullMap));
    }

    @Test
    public void convertUtilNullString() {
        final String nullString = null;
        assertTrue(isEmpty(nullString));
        assertFalse(hasContent(nullString));
    }

    @Test
    public void convertUtilEmptyString() {
        final String emptyString = "";
        assertTrue(isEmpty(emptyString));
        assertFalse(hasContent(emptyString));
    }

    @Test
    public void convertUtilFullString() {
        final String fullString = "Hello World";
        assertTrue(hasContent(fullString));
        assertFalse(isEmpty(fullString));
    }

    @Test
    public void removeSpectraInternalRequestsNull() {
        final ImmutableList<Ds3Request> nullResult = removeSpectraInternalRequests(null);
        assertTrue(nullResult.isEmpty());
    }

    @Test
    public void removeSpectraInternalRequestsEmpty() {
        final ImmutableList<Ds3Request> emptyResult = removeSpectraInternalRequests(ImmutableList.of());
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void removeSpectraInternalRequestsFull() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                new Ds3Request("Request1", null, Classification.amazons3, null, null, null, null, null, null, null, null, null),
                new Ds3Request("Request2", null, Classification.spectrainternal, null, null, null, null, null, null, null, null, null),
                new Ds3Request("Request3", null, Classification.spectrads3, null, null, null, null, null, null, null, null, null));
        final ImmutableList<Ds3Request> result = removeSpectraInternalRequests(requests);
        assertThat(result.size(), is(2));
        assertTrue(result.get(0).getClassification() != Classification.spectrainternal);
        assertTrue(result.get(1).getClassification() != Classification.spectrainternal);
    }
}
