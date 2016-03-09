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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import org.junit.Test;

import static com.spectralogic.ds3autogen.converters.RemoveSpectraInternalConverter.removeInternalRequestsFromSpec;
import static com.spectralogic.ds3autogen.converters.RemoveSpectraInternalConverter.removeSpectraInternalRequests;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RemoveSpectraInternalConverter_Test {

    private static ImmutableList<Ds3Request> createTestRequests() {
        return ImmutableList.of(
                createDs3RequestTestData("Request1", Classification.amazons3),
                createDs3RequestTestData("Request2", Classification.spectrainternal),
                createDs3RequestTestData("Request3", Classification.spectrads3));
    }

    @Test
    public void removeSpectraInternalRequests_NullList_Test() {
        final ImmutableList<Ds3Request> nullResult = removeSpectraInternalRequests(null);
        assertTrue(nullResult.isEmpty());
    }

    @Test
    public void removeSpectraInternalRequests_EmptyList_Test() {
        final ImmutableList<Ds3Request> emptyResult = removeSpectraInternalRequests(ImmutableList.of());
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    public void removeSpectraInternalRequests_FullList_Test() {
        final ImmutableList<Ds3Request> requests = createTestRequests();
        final ImmutableList<Ds3Request> result = removeSpectraInternalRequests(requests);
        assertThat(result.size(), is(2));
        assertTrue(result.get(0).getClassification() != Classification.spectrainternal);
        assertTrue(result.get(1).getClassification() != Classification.spectrainternal);
    }

    @Test
    public void removeInternalRequestsFromSpec_KeepRequests_Test() {
        final ImmutableList<Ds3Request> requests = createTestRequests();

        final Ds3ApiSpec spec = new Ds3ApiSpec(requests, null);
        final Ds3ApiSpec result = removeInternalRequestsFromSpec(spec, false);
        assertThat(result.getRequests().size(), is(3));
    }

    @Test
    public void removeInternalRequestsFromSpec_RemoveRequests_Test() {
        final ImmutableList<Ds3Request> requests = createTestRequests();

        final Ds3ApiSpec spec = new Ds3ApiSpec(requests, null);
        final Ds3ApiSpec result = removeInternalRequestsFromSpec(spec, true);
        assertThat(result.getRequests().size(), is(2));
        for (final Ds3Request request : result.getRequests()) {
            assertThat(request.getClassification(), not(Classification.spectrainternal));
        }
    }
}
