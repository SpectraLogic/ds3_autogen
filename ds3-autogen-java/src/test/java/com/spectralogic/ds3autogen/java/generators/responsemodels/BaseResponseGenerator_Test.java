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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator.getAllImportsFromResponseCodes;
import static com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator.getImportFromResponseCode;
import static com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator.removeErrorResponseCodes;
import static com.spectralogic.ds3autogen.java.test.helpers.BaseResponseGeneratorTestHelper.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseResponseGenerator_Test {

    private static final BaseResponseGenerator generator = new BaseResponseGenerator();

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
    public void getAllImports_NullList_Test() {
        final ImmutableList<String> result = generator.getAllImports(
                null,
                "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_EmptyList_Test() {
        final ImmutableList<String> result = generator.getAllImports(
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
                responseCodes,
                "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(5));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v1"));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v2"));
        assertTrue(result.contains("com.spectralogic.ds3client.serializer.XmlOutput"));
        assertTrue(result.contains("com.spectralogic.ds3client.commands.interfaces.AbstractResponse"));
        assertTrue(result.contains("java.io.InputStream"));
    }

    @Test
    public void getParentImport_Test() {
        assertThat(generator.getParentImport(), is("com.spectralogic.ds3client.commands.interfaces.AbstractResponse"));
    }

    @Test
    public void getAllImportsFromResponseCodes_NullList_Test() {
        final ImmutableSet<String> result = getAllImportsFromResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImportsFromResponseCodes_EmptyList_Test() {
        final ImmutableSet<String> result = getAllImportsFromResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImportsFromResponseCodes_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode("_v1"),
                createPopulatedResponseCode("_v2"),
                createPopulatedErrorResponseCode("_v3"));

        final ImmutableSet<String> result = getAllImportsFromResponseCodes(responseCodes);
        assertThat(result.size(), is(3));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v1"));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v2"));
        assertTrue(result.contains("java.io.InputStream"));
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
    public void toResponseCodes_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(206, null),
                new Ds3ResponseCode(307, null),
                new Ds3ResponseCode(400, null),
                new Ds3ResponseCode(503, null));

        final Ds3Request request = createDs3RequestTestData(true, responseCodes, null, null);
        final ImmutableList<Ds3ResponseCode> result = generator.toResponseCodes(request);

        assertThat(result.size(), is(3));
        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(1).getCode(), is(206));
        assertThat(result.get(2).getCode(), is(307));
    }
}
