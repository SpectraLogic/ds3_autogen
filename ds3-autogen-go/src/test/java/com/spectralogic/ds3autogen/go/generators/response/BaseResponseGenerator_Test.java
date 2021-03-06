/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.go.generators.response;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.go.models.response.ResponseCode;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseResponseGenerator_Test {

    private final BaseResponseGenerator generator = new BaseResponseGenerator();

    @Test (expected = IllegalArgumentException.class)
    public void getResponsePayload_NullList_Test() {
        generator.getResponsePayload(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponsePayload_EmptyList_Test() {
        generator.getResponsePayload(ImmutableList.of());
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponsePayload_NoPayload_Test() {
        final ImmutableList<Ds3ResponseCode> input = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of()),
                new Ds3ResponseCode(204, ImmutableList.of(new Ds3ResponseType("null", ""))),
                new Ds3ResponseCode(300, ImmutableList.of(new Ds3ResponseType("com.test.Type", "")))
        );

        generator.getResponsePayload(input);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponsePayload_MultiplePayloads_Test() {
        final ImmutableList<Ds3ResponseCode> input = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of(new Ds3ResponseType("com.test.Type1", ""))),
                new Ds3ResponseCode(204, ImmutableList.of(new Ds3ResponseType("com.test.Type2", "")))
        );

        generator.getResponsePayload(input);
    }

    @Test
    public void getResponsePayload_Test() {
        final String expected = "com.test.Type";

        final ImmutableList<Ds3ResponseCode> input = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of(new Ds3ResponseType("com.test.Type", ""))),
                new Ds3ResponseCode(300, ImmutableList.of(new Ds3ResponseType("com.test.ErrorType", "")))
        );

        final String result = generator.getResponsePayload(input);
        assertThat(result, is(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponsePayloadType_NullList_Test() {
        generator.toResponsePayloadType("com.test.Type", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponsePayloadType_EmptyList_Test() {
        generator.toResponsePayloadType("com.test.Type", ImmutableList.of());
    }

    @Test
    public void toResponsePayloadType_MultipleCodes_Test() {
        final String expected = "*Type";

        final ImmutableList<Ds3ResponseCode> input = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of()),
                new Ds3ResponseCode(204, ImmutableList.of()),
                new Ds3ResponseCode(300, ImmutableList.of())
        );

        final String result = generator.toResponsePayloadType("com.test.Type", input);
        assertThat(result, is(expected));
    }

    @Test
    public void toResponsePayloadType_SingleCode_Test() {
        final String expected = "Type";

        final ImmutableList<Ds3ResponseCode> input = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of()),
                new Ds3ResponseCode(300, ImmutableList.of())
        );

        final String result = generator.toResponsePayloadType("com.test.Type", input);
        assertThat(result, is(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponsePayloadStruct_Exception_Test() {
        generator.toResponsePayloadStruct(ImmutableList.of());
    }

    @Test
    public void toResponsePayloadStruct_StringPayload_Test() {
        final String expected = "Content string";

        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of(new Ds3ResponseType("java.lang.String", ""))),
                new Ds3ResponseCode(204, ImmutableList.of(new Ds3ResponseType("null", ""))));

        final String result = generator.toResponsePayloadStruct(responseCodes);
        assertThat(result, is(expected));
    }

    @Test
    public void toResponsePayloadStruct_Test() {
        final String expected = "TypeName *TypeName";

        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of(new Ds3ResponseType("com.test.TypeName", ""))),
                new Ds3ResponseCode(204, ImmutableList.of(new Ds3ResponseType("null", ""))));

        final String result = generator.toResponsePayloadStruct(responseCodes);
        assertThat(result, is(expected));
    }

    @Test
    public void toExpectedStatusCodes_NullList_Test() {
        final String result = generator.toExpectedStatusCodes(null);
        assertThat(result, is(""));
    }

    @Test
    public void toExpectedStatusCodes_EmptyList_Test() {
        final String result = generator.toExpectedStatusCodes(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void toExpectedStatusCodes_Test() {
        final String expected = "200, 204";

        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of()),
                new Ds3ResponseCode(204, ImmutableList.of()),
                new Ds3ResponseCode(300, ImmutableList.of()));

        final String result = generator.toExpectedStatusCodes(responseCodes);
        assertThat(result, is(expected));
    }

    @Test
    public void toPayloadResponseCode_Test() {
        final String expectedGoCode = "var body ResponseName\n" +
                "        if err := body.parse(webResponse); err != nil {\n" +
                "            return nil, err\n" +
                "        }\n" +
                "        body.Headers = webResponse.Header()\n" +
                "        return &body, nil";

        final ResponseCode result = generator.toPayloadResponseCode(200, "ResponseName");
        assertThat(result.getCode(), is(200));
        assertThat(result.getParseResponse(), is(expectedGoCode));
    }

    @Test
    public void toNullPayloadResponseCode_Test() {
        final String expectedGoCode = "return &ResponseName{Headers: webResponse.Header()}, nil";

        final ResponseCode result = generator.toNullPayloadResponseCode(200, "ResponseName");
        assertThat(result.getCode(), is(200));
        assertThat(result.getParseResponse(), is(expectedGoCode));
    }

    @Test
    public void toStringPayloadResponseCode() {
        final String expect = "content, err := getResponseBodyAsString(webResponse)\n" +
                "        if err != nil {\n" +
                "            return nil, err\n" +
                "        }\n" +
                "        return &ResponseName{Content: content, Headers: webResponse.Header()}, nil";

        final ResponseCode result = generator.toStringPayloadResponseCode(200, "ResponseName");
        assertThat(result.getCode(), is(200));
        assertThat(result.getParseResponse(), is(expect));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponseCode_Exception_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200, ImmutableList.of());
        generator.toResponseCode(responseCode, "ResponseName");
    }

    @Test
    public void toResponseCode_NullPayload_Test() {
        final String expectedGoCode = "return &ResponseName{Headers: webResponse.Header()}, nil";
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(new Ds3ResponseType("null", "")));

        final ResponseCode result = generator.toResponseCode(responseCode, "ResponseName");
        assertThat(result.getCode(), is(200));
        assertThat(result.getParseResponse(), is(expectedGoCode));
    }

    @Test
    public void toResponseCode_StringPayload_Test() {
        final String expectedGoCode = "content, err := getResponseBodyAsString(webResponse)\n" +
                "        if err != nil {\n" +
                "            return nil, err\n" +
                "        }\n" +
                "        return &ResponseName{Content: content, Headers: webResponse.Header()}, nil";

        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(new Ds3ResponseType("java.lang.String", "")));

        final ResponseCode result = generator.toResponseCode(responseCode, "ResponseName");
        assertThat(result.getCode(), is(200));
        assertThat(result.getParseResponse(), is(expectedGoCode));
    }

    @Test
    public void toResponseCode_Test() {
        final String expectedGoCode = "var body ResponseName\n" +
                "        if err := body.parse(webResponse); err != nil {\n" +
                "            return nil, err\n" +
                "        }\n" +
                "        body.Headers = webResponse.Header()\n" +
                "        return &body, nil";

        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(new Ds3ResponseType("com.test.TypeName", "")));

        final ResponseCode result = generator.toResponseCode(responseCode, "ResponseName");
        assertThat(result.getCode(), is(200));
        assertThat(result.getParseResponse(), is(expectedGoCode));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponseCodeList_NullList_Test() {
        generator.toResponseCodeList(null, "ResponseName");
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponseCodeList_EmptyList_Test() {
        generator.toResponseCodeList(ImmutableList.of(), "ResponseName");
    }

    @Test
    public void toResponseCodeList_Test() {
        final ImmutableList<ResponseCode> expectedCodes = ImmutableList.of(
                new ResponseCode(200, "var body ResponseName\n" +
                        "        if err := body.parse(webResponse); err != nil {\n" +
                        "            return nil, err\n" +
                        "        }\n" +
                        "        body.Headers = webResponse.Header()\n" +
                        "        return &body, nil"),
                new ResponseCode(204, "return &ResponseName{Headers: webResponse.Header()}, nil")
        );

        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, ImmutableList.of(new Ds3ResponseType("com.test.TypeName", ""))),
                new Ds3ResponseCode(204, ImmutableList.of(new Ds3ResponseType("null", "")))
        );

        final ImmutableList<ResponseCode> result = generator.toResponseCodeList(responseCodes, "ResponseName");

        expectedCodes.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void toImportSet_Test() {
        final ImmutableSet<String> result = generator.toImportSet();
        assertThat(result.size(), is(0));
    }

    @Test
    public void toDereferenceResponsePayload_Test() {
        assertThat(generator.toDereferenceResponsePayload("TapeList TapeList"), is("&"));
        assertThat(generator.toDereferenceResponsePayload("TapeList *TapeList"), is(""));
    }

    @Test
    public void toParseResponseMethod_EmptyList_Test() {
        final String result = generator.toParseResponseMethod("Name", "PayloadStruct PayloadStruct", ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void toParseResponseMethod_NoPayload_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("null", "")));

        final String result = generator.toParseResponseMethod(
                "Name",
                "PayloadStruct PayloadStruct",
                ImmutableList.of(responseCode));

        assertThat(result, is(""));
    }

    @Test
    public void toParseResponseMethod_StringPayload_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("java.lang.String", "")));

        final String result = generator.toParseResponseMethod(
                "Name",
                "PayloadStruct PayloadStruct",
                ImmutableList.of(responseCode));

        assertThat(result, is(""));
    }

    @Test
    public void toParseResponseMethod_WithDereference_Test() {
        final String expected = "func (modelName *ModelName) parse(webResponse WebResponse) error {\n" +
                "        return parseResponsePayload(webResponse, &modelName.TestType)\n" +
                "}";

        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("com.test.TestType", "")));

        final String result = generator.toParseResponseMethod(
                "ModelName",
                "PayloadStruct PayloadStruct",
                ImmutableList.of(responseCode));

        assertThat(result, is(expected));
    }

    @Test
    public void toParseResponseMethod_WithoutDereference_Test() {
        final String expected = "func (modelName *ModelName) parse(webResponse WebResponse) error {\n" +
                "        return parseResponsePayload(webResponse, modelName.TestType)\n" +
                "}";

        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(
                        new Ds3ResponseType("com.test.TestType", "")));

        final String result = generator.toParseResponseMethod(
                "ModelName",
                "PayloadStruct *PayloadStruct",
                ImmutableList.of(responseCode));

        assertThat(result, is(expected));
    }
}
