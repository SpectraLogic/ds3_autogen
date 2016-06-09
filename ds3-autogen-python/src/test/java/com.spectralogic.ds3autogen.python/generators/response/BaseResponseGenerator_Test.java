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

package com.spectralogic.ds3autogen.python.generators.response;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.python.model.response.BaseParsePayload;
import com.spectralogic.ds3autogen.python.model.response.NoPayload;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.generators.response.BaseResponseGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getBucketRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestAmazonS3GetObject;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestGetJob;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class BaseResponseGenerator_Test {

    private final static BaseResponseGenerator generator = new BaseResponseGenerator();

    private static Ds3Type typeWithoutNameToMarshal() {
        return new Ds3Type("com.test.TypeWithoutNameToMarshal", null, null, null);
    }

    private static Ds3Type typeWithNameToMarshal() {
        return new Ds3Type("com.test.TypeWithNameToMarshal", "MyNameToMarshal", null, null);
    }

    private static Ds3Type typeEmptyListBucketResult() {
        return new Ds3Type("com.spectralogic.s3.server.domain.ListBucketResult", null, null, null);
    }

    private static Ds3Type typeEmptyJobWithChunks() {
        return new Ds3Type("com.spectralogic.s3.server.domain.JobWithChunksApiBean", "JobsTag", null, null);
    }

    private static ImmutableMap<String, Ds3Type> testTypeMap() {
        final Ds3Type type1 = typeWithNameToMarshal();
        final Ds3Type type2 = typeWithoutNameToMarshal();
        final Ds3Type type3 = typeEmptyListBucketResult();
        final Ds3Type type4 = typeEmptyJobWithChunks();
        return ImmutableMap.of(
                type1.getName(), type1,
                type2.getName(), type2,
                type3.getName(), type3,
                type4.getName(), type4);
    }

    @Test
    public void getNameToMarshal_Test() {
        final ImmutableMap<String, Ds3Type> typeMap = testTypeMap();
        assertThat(getNameToMarshal(null, null), is(nullValue()));
        assertThat(getNameToMarshal("", ImmutableMap.of()), is(nullValue()));
        assertThat(getNameToMarshal("com.test.NotInMapType", typeMap), is(nullValue()));
        assertThat(getNameToMarshal("com.test.TypeWithoutNameToMarshal", typeMap), is(nullValue()));
        assertThat(getNameToMarshal("com.test.TypeWithNameToMarshal", typeMap), is("MyNameToMarshal"));
    }

    @Test
    public void getParsePayload_NoPayload_Test() {
        final Ds3Request emptyRequest = createDs3RequestTestData("com.test.Request", Classification.amazons3);
        assertThat(getParsePayload(emptyRequest), instanceOf(NoPayload.class));
    }

    @Test
    public void getParsePayload_NoNameToMarshal_Test() {
        final Ds3Request request = getBucketRequest();
        assertThat(getParsePayload(request), instanceOf(BaseParsePayload.class));
    }

    @Test
    public void toParseResponsePayload_NoPayload_Test() {
        final Ds3Request request = createDs3RequestTestData("com.test.Request", Classification.amazons3);
        assertThat(generator.toParseResponsePayload(request), is(""));
    }

    @Test
    public void toParseResponsePayload_NoNameToMarshal_Test() {
        final String expected = "if self.response.status == 200:\n" +
                "      self.result = parseModel(xmldom.fromstring(response.read()), ListBucketResult())";

        final Ds3Request request = getBucketRequest();
        assertThat(generator.toParseResponsePayload(request), is(expected));
    }

    @Test
    public void toParseResponsePayload_WithNameToMarshal_Test() {
        final String expected = "if self.response.status == 200:\n" +
                "      self.result = parseModel(xmldom.fromstring(response.read()), JobWithChunksApiBean())";

        final Ds3Request request = getRequestGetJob();
        assertThat(generator.toParseResponsePayload(request), is(expected));
    }

    @Test
    public void getStatusCodes_NullList_Test() {
        final ImmutableList<Integer> result = generator.getStatusCodes(null, "com.test.Request");
        assertThat(result.size(), is(0));
    }

    @Test
    public void getStatusCodes_EmptyList_Test() {
        final ImmutableList<Integer> result = generator.getStatusCodes(null, "com.test.Request");
        assertThat(result.size(), is(0));
    }

    @Test
    public void getStatusCodes_Test() {
        final Ds3Request request = getRequestAmazonS3GetObject();
        final ImmutableList<Integer> result = generator.getStatusCodes(request.getDs3ResponseCodes(), request.getName());
        assertThat(result, hasItem(200));
        assertThat(result, hasItem(206));
    }
}
