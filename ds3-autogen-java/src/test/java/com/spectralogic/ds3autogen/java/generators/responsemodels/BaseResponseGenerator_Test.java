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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedErrorResponseCode;
import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedResponseCode;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getObjectsDetailsRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3Request;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseResponseGenerator_Test {

    private static final BaseResponseGenerator generator = new BaseResponseGenerator();

    @Test
    public void getAllImports_NullList_Test() {
        final ImmutableList<String> result = generator.getAllImports(
                createEmptyDs3Request(),
                null,
                "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_EmptyList_Test() {
        final ImmutableList<String> result = generator.getAllImports(
                createEmptyDs3Request(),
                ImmutableList.of(),
                "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode("_v1"),
                createPopulatedResponseCode("_v2"),
                createPopulatedErrorResponseCode("_v3"));

        final ImmutableList<String> result = generator.getAllImports(
                createEmptyDs3Request(),
                responseCodes,
                "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(4));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v1"));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v2"));
        assertTrue(result.contains("com.spectralogic.ds3client.serializer.XmlOutput"));
        assertTrue(result.contains("com.spectralogic.ds3client.commands.interfaces.AbstractResponse"));
    }

    @Test
    public void getParentImport_Test() {
        final String expected = "com.spectralogic.ds3client.commands.interfaces.AbstractResponse";
        assertThat(generator.getParentImport(createEmptyDs3Request()), is(expected));
    }

    @Test
    public void getParentClass_Test() {
        assertThat(generator.getParentClass(createEmptyDs3Request()), is("AbstractResponse"));
        assertThat(generator.getParentClass(getObjectsDetailsRequest()), is("AbstractPaginationResponse"));
    }
}
