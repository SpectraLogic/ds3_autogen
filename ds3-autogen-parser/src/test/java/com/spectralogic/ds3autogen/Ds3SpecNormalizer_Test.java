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

package com.spectralogic.ds3autogen;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import org.junit.Test;

import static com.spectralogic.ds3autogen.Ds3SpecNormalizer.verifySingleResponsePayload;
import static com.spectralogic.ds3autogen.Ds3SpecNormalizer.verifySingleResponsePayloadRequests;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;

public class Ds3SpecNormalizer_Test {


    private static ImmutableList<Ds3ResponseCode> createValidResponseCodeList() {
        return ImmutableList.of(
                new Ds3ResponseCode(
                        200,
                        ImmutableList.of(new Ds3ResponseType("Type1", "ComponentType1"))),
                new Ds3ResponseCode(
                        201,
                        ImmutableList.of(new Ds3ResponseType("Type2", "ComponentType2"))));
    }

    private static ImmutableList<Ds3ResponseCode> createInvalidResponseCodeList() {
        return ImmutableList.of(
                new Ds3ResponseCode(
                        200,
                        ImmutableList.of(new Ds3ResponseType("Type1", "ComponentType1"))),
                new Ds3ResponseCode(
                        201,
                        ImmutableList.of(
                                new Ds3ResponseType("Type2", "ComponentType2"),
                                new Ds3ResponseType("Type3", "ComponentType3"))));
    }

    @Test
    public void verifySingleResponsePayload_NullList_Test() {
        verifySingleResponsePayload(null, "RequestName");
    }

    @Test
    public void verifySingleResponsePayload_EmptyList_Test() {
        verifySingleResponsePayload(ImmutableList.of(), "RequestName");
    }

    @Test
    public void verifySingleResponsePayload_ValidList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = createValidResponseCodeList();
        verifySingleResponsePayload(responseCodes, "RequestName");
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifySingleResponsePayload_InvalidList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = createInvalidResponseCodeList();
        verifySingleResponsePayload(responseCodes, "RequestName");
    }

    @Test
    public void verifySingleResponsePayloadRequests_NullList_Test() {
        verifySingleResponsePayloadRequests(null);
    }

    @Test
    public void verifySingleResponsePayloadRequests_EmptyList_Test() {
        verifySingleResponsePayloadRequests(ImmutableList.of());
    }

    @Test
    public void verifySingleResponsePayloadRequests_ValidList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createDs3RequestTestData(false, createValidResponseCodeList(), null, null));
        verifySingleResponsePayloadRequests(requests);
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifySingleResponsePayloadRequests_InvalidList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createDs3RequestTestData(false, createInvalidResponseCodeList(), null, null));
        verifySingleResponsePayloadRequests(requests);
    }
}
