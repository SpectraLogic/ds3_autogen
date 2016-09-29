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

package com.spectralogic.ds3autogen.java.models.parseresponse;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ParseResponse_Test {

    @Test
    public void emptyParserResponse_Test() {
        final String expected = "//There is no payload, return an empty response handler\n" +
                "                return new TestResponse();\n";
        final EmptyParseResponse result = new EmptyParseResponse("TestResponse");
        assertThat(result.toJavaCode(), is(expected));
    }

    @Test
    public void emptyParserResponse_WithPagination_Test() {
        final String expected = "//There is no payload, return an empty response handler\n" +
                "                return new TestResponse(pagingTotalResultCount, pagingTruncated);\n";
        final EmptyParseResponse result = new EmptyParseResponse("TestResponse", true);
        assertThat(result.toJavaCode(), is(expected));
    }

    @Test
    public void nullParserResponse_Test() {
        final String expected = "//There is no payload associated with this code, return a null response\n" +
                "                return new TestResponse(null);\n";
        final NullParseResponse result = new NullParseResponse("TestResponse");
        assertThat(result.toJavaCode(), is(expected));
    }

    @Test
    public void nullParserResponse_WithPagination_Test() {
        final String expected = "//There is no payload associated with this code, return a null response\n" +
                "                return new TestResponse(null, pagingTotalResultCount, pagingTruncated);\n";
        final NullParseResponse result = new NullParseResponse("TestResponse", true);
        assertThat(result.toJavaCode(), is(expected));
    }

    @Test
    public void stringParseResponse_Test() {
        final String expected = "try (final InputStream inputStream = new ReadableByteChannelInputStream(blockingByteChannel)) {\n" +
                "                    final String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);\n" +
                "                    return new TestResponse(result);\n" +
                "                }\n";
        final StringParseResponse result = new StringParseResponse("TestResponse");
        assertThat(result.toJavaCode(), is(expected));
    }

    @Test
    public void stringParseResponse_WithPagination_Test() {
        final String expected = "try (final InputStream inputStream = new ReadableByteChannelInputStream(blockingByteChannel)) {\n" +
                "                    final String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);\n" +
                "                    return new TestResponse(result, pagingTotalResultCount, pagingTruncated);\n" +
                "                }\n";
        final StringParseResponse result = new StringParseResponse("TestResponse", true);
        assertThat(result.toJavaCode(), is(expected));
    }

    @Test
    public void baseParseResponse_Test() {
        final String expected = "try (final InputStream inputStream = new ReadableByteChannelInputStream(blockingByteChannel)) {\n" +
                "                    final TestType result = XmlOutput.fromXml(inputStream, TestType.class);\n" +
                "                    return new TestResponse(result);\n" +
                "                }\n";
        final BaseParseResponse result = new BaseParseResponse("TestResponse", "TestType");
        assertThat(result.toJavaCode(), is(expected));
    }

    @Test
    public void baseParseResponse_WithPagination_Test() {
        final String expected = "try (final InputStream inputStream = new ReadableByteChannelInputStream(blockingByteChannel)) {\n" +
                "                    final TestType result = XmlOutput.fromXml(inputStream, TestType.class);\n" +
                "                    return new TestResponse(result, pagingTotalResultCount, pagingTruncated);\n" +
                "                }\n";
        final BaseParseResponse result = new BaseParseResponse("TestResponse", "TestType", true);
        assertThat(result.toJavaCode(), is(expected));
    }
}
