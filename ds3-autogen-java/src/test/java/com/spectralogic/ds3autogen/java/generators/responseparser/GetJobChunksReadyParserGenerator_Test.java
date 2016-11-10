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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.java.models.ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.responseparser.GetJobChunksReadyParserGenerator.toParsePayloadCode;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getJobChunksReadyForClientProcessingRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetJobChunksReadyParserGenerator_Test {

    final GetJobChunksReadyParserGenerator generator = new GetJobChunksReadyParserGenerator();

    @Test
    public void toParsePayloadCode_Test() {
        final String expected = "try (final InputStream inputStream = response.getResponseStream()) {\n" +
                "                    final JobWithChunksApiBean result = XmlOutput.fromXml(inputStream, JobWithChunksApiBean.class);\n" +
                "                    if (isNullOrEmpty(result.getObjects())) {\n" +
                "                        return new TestResponse(result, parseRetryAfter(response), TestResponse.Status.RETRYLATER, this.getChecksum(), this.getChecksumType());\n" +
                "                    }\n" +
                "                    return new TestResponse(result, 0, TestResponse.Status.AVAILABLE, this.getChecksum(), this.getChecksumType());\n" +
                "                }\n";

        final Ds3ResponseCode ds3ResponseCode = new Ds3ResponseCode(
                200,
                ImmutableList.of(new Ds3ResponseType("com.spectralogic.s3.server.domain.JobWithChunksApiBean", null)));

        final String result = toParsePayloadCode(ds3ResponseCode, "TestResponse");
        assertThat(result, is(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponseCodeList_Exception_Test() {
        generator.toResponseCodeList(null, "TestResponse", false);
    }

    @Test
    public void toResponseCodeList_Test() {
        final Ds3Request ds3Request = getJobChunksReadyForClientProcessingRequest();
        final ImmutableList<ResponseCode> result = generator
                .toResponseCodeList(ds3Request.getDs3ResponseCodes(), "TestResponse", false);

        assertThat(result.size(), is(1));
        assertThat(result.get(0).getCode(), is(200));
    }
}
