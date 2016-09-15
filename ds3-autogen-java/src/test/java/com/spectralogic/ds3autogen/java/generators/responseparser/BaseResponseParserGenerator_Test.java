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

package com.spectralogic.ds3autogen.java.generators.responseparser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.responseparser.BaseResponseParserGenerator.requiredImportList;
import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedErrorResponseCode;
import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedResponseCode;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3Request;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseResponseParserGenerator_Test {

    private final static BaseResponseParserGenerator generator = new BaseResponseParserGenerator();

    private static void hasRequiredInputs(final ImmutableSet<String> imports) {
        assertTrue(imports.contains("java.io.IOException"));
        assertTrue(imports.contains("java.nio.channels.ReadableByteChannel"));
        assertTrue(imports.contains("com.spectralogic.ds3client.networking.WebResponse"));
        assertTrue(imports.contains("com.spectralogic.ds3client.commands.TestResponse"));
        assertTrue(imports.contains("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser"));
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

    @Test
    public void toImportList_NullList_Test() {
        final ImmutableSet<String> result = generator.toImportList(
                "TestResponse",
                createEmptyDs3Request(),
                null);

        assertThat(result.size(), is(5));
        hasRequiredInputs(result);
    }

    @Test
    public void toImportList_EmptyList_Test() {
        final ImmutableSet<String> result = generator.toImportList(
                "TestResponse",
                createEmptyDs3Request(),
                ImmutableList.of());

        assertThat(result.size(), is(5));
        hasRequiredInputs(result);
    }

    @Test
    public void toImportList_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode("_v1"),
                createPopulatedResponseCode("_v2"),
                createPopulatedErrorResponseCode("_v3"));

        final ImmutableSet<String> result = generator.toImportList(
                "TestResponse",
                createEmptyDs3Request(),
                responseCodes);

        assertThat(result.size(), is(10));
        hasRequiredInputs(result);
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v1"));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v2"));
        assertTrue(result.contains("com.spectralogic.ds3client.serializer.XmlOutput"));
        assertTrue(result.contains("com.spectralogic.ds3client.utils.ReadableByteChannelInputStream"));
        assertTrue(result.contains("java.io.InputStream"));
    }

    @Test
    public void requiredImportList_Test() {
        final ImmutableSet<String> result = requiredImportList();

        assertThat(result.size(), is(3));
        assertTrue(result.contains("java.io.IOException"));
        assertTrue(result.contains("java.nio.channels.ReadableByteChannel"));
        assertTrue(result.contains("com.spectralogic.ds3client.networking.WebResponse"));
    }

    @Test
    public void getParentClass_Test() {
        final String result = generator.getParentClass();
        assertThat(result, is("AbstractResponseParser"));
    }

    @Test
    public void getParentClassImport_Test() {
        final String result = generator.getParentClassImport();
        assertThat(result, is("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser"));
    }
}
