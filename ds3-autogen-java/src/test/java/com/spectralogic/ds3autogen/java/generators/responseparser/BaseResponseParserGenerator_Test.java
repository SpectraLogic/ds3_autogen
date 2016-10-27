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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.java.models.ResponseCode;
import com.spectralogic.ds3autogen.java.models.parseresponse.BaseParseResponse;
import com.spectralogic.ds3autogen.java.models.parseresponse.EmptyParseResponse;
import com.spectralogic.ds3autogen.java.models.parseresponse.NullParseResponse;
import com.spectralogic.ds3autogen.java.models.parseresponse.StringParseResponse;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.responseparser.BaseResponseParserGenerator.*;
import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedErrorResponseCode;
import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedResponseCode;
import static com.spectralogic.ds3autogen.java.utils.ResponseParserGeneratorTestUtil.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3Request;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseResponseParserGenerator_Test {

    private final static BaseResponseParserGenerator generator = new BaseResponseParserGenerator();

    private static void hasRequiredInputs(final ImmutableSet<String> imports) {
        assertTrue(imports.contains("java.io.IOException"));
        assertTrue(imports.contains("com.spectralogic.ds3client.networking.WebResponse"));
        assertTrue(imports.contains("com.spectralogic.ds3client.commands.TestResponse"));
        assertTrue(imports.contains("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser"));
        assertTrue(imports.contains("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils"));
    }

    @Test
    public void toResponseCodes_Test() {
        final String expectedProcessingCode = "try (final InputStream inputStream = response.getResponseStream()) {\n" +
                "                    final DefaultType result = XmlOutput.fromXml(inputStream, DefaultType.class);\n" +
                "                    return new TestResponse(result, this.getChecksum(), this.getChecksumType());\n" +
                "                }\n";

        final ImmutableList<Ds3ResponseType> defaultResponseType = ImmutableList.of(
                new Ds3ResponseType("DefaultType", null));

        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, defaultResponseType),
                new Ds3ResponseCode(206, defaultResponseType),
                new Ds3ResponseCode(307, defaultResponseType),
                new Ds3ResponseCode(400, defaultResponseType),
                new Ds3ResponseCode(503, defaultResponseType));

        final ImmutableList<ResponseCode> result = generator.toResponseCodeList(responseCodes, "TestResponse", false);

        assertThat(result.size(), is(3));
        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(0).getProcessingCode(), is(expectedProcessingCode));
        assertThat(result.get(1).getCode(), is(206));
        assertThat(result.get(1).getProcessingCode(), is(expectedProcessingCode));
        assertThat(result.get(2).getCode(), is(307));
        assertThat(result.get(2).getProcessingCode(), is(expectedProcessingCode));
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

        assertThat(result.size(), is(9));
        hasRequiredInputs(result);
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v1"));
        assertTrue(result.contains("com.spectralogic.ds3client.models.Type_v2"));
        assertTrue(result.contains("com.spectralogic.ds3client.serializer.XmlOutput"));
        assertTrue(result.contains("java.io.InputStream"));
    }

    @Test
    public void requiredImportList_Test() {
        final ImmutableSet<String> result = requiredImportList();

        assertThat(result.size(), is(2));
        assertTrue(result.contains("java.io.IOException"));
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

    @Test (expected = IllegalArgumentException.class)
    public void toParseResponse_NullTypesList_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200, null);
        toParseResponse(responseCode, "TestResponse", false, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void toParseResponse_EmptyTypesList_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200, ImmutableList.of());
        toParseResponse(responseCode, "TestResponse", false, false);
    }

    @Test
    public void toParseResponse_Test() {
        final String responseName = "TestResponse";

        assertThat(toParseResponse(getNullResponseCode(), responseName, false, false),
                instanceOf(EmptyParseResponse.class));

        assertThat(toParseResponse(getNullResponseCode(), responseName, true, false),
                instanceOf(NullParseResponse.class));

        assertThat(toParseResponse(getStringResponseCode(), responseName, true, false),
                instanceOf(StringParseResponse.class));

        assertThat(toParseResponse(getBaseResponseCode(), responseName, true, false),
                instanceOf(BaseParseResponse.class));
    }

    @Test
    public void toResponseCode_EmptyResponse_Test() {
        final String expected = "//There is no payload, return an empty response handler\n" +
                "                return new TestResponse(this.getChecksum(), this.getChecksumType());\n";
        final ResponseCode result = toResponseCode(getNullResponseCode(), "TestResponse", false, false);
        assertThat(result.getCode(), is(200));
        assertThat(result.getProcessingCode(), is(expected));
    }

    @Test
    public void toResponseCode_NullResponse_Test() {
        final String expected = "//There is no payload associated with this code, return a null response\n" +
                "                return new TestResponse(null, this.getChecksum(), this.getChecksumType());\n";
        final ResponseCode result = toResponseCode(getNullResponseCode(), "TestResponse", true, false);
        assertThat(result.getCode(), is(200));
        assertThat(result.getProcessingCode(), is(expected));
    }

    @Test
    public void toResponseCode_StringResponse_Test() {
        final String expected = "try (final InputStream inputStream = response.getResponseStream()) {\n" +
                "                    final String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);\n" +
                "                    return new TestResponse(result, this.getChecksum(), this.getChecksumType());\n" +
                "                }\n";
        final ResponseCode result = toResponseCode(getStringResponseCode(), "TestResponse", true, false);
        assertThat(result.getCode(), is(201));
        assertThat(result.getProcessingCode(), is(expected));
    }

    @Test
    public void toResponseCode_BaseResponse_Test() {
        final String expected = "try (final InputStream inputStream = response.getResponseStream()) {\n" +
                "                    final Type result = XmlOutput.fromXml(inputStream, Type.class);\n" +
                "                    return new TestResponse(result, this.getChecksum(), this.getChecksumType());\n" +
                "                }\n";
        final ResponseCode result = toResponseCode(getBaseResponseCode(), "TestResponse", true, false);
        assertThat(result.getCode(), is(202));
        assertThat(result.getProcessingCode(), is(expected));
    }

    @Test
    public void toResponseCodeList_NullList_Test() {
        final ImmutableList<ResponseCode> result = generator.toResponseCodeList(null, "TestResponse", false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toResponseCodeList_EmptyList_Test() {
        final ImmutableList<ResponseCode> result = generator.toResponseCodeList(ImmutableList.of(), "TestResponse", false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toResponseCodeList_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                getNullResponseCode(),
                getStringResponseCode(),
                getBaseResponseCode());

        final ImmutableList<ResponseCode> result = generator.toResponseCodeList(responseCodes, "TestResponse", false);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(1).getCode(), is(201));
        assertThat(result.get(2).getCode(), is(202));
    }

    @Test
    public void toStatusCodeList_NullList_Test() {
        final String result = toStatusCodeList(null);
        assertThat(result, is(""));
    }

    @Test
    public void toStatusCodeList_EmptyList_Test() {
        final String result = toStatusCodeList(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void toStatusCodeList_FullList_Test() {
        final ImmutableList<ResponseCode> responseCodes = ImmutableList.of(
                new ResponseCode(201, ""),
                new ResponseCode(202, ""),
                new ResponseCode(203, "")
        );
        final String result = toStatusCodeList(responseCodes);
        assertThat(result, is("201, 202, 203"));
    }


}
