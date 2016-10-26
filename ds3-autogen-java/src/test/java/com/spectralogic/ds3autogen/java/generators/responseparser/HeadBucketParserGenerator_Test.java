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
import com.spectralogic.ds3autogen.java.models.ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.responseparser.HeadBucketParserGenerator.toReturnCode;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getHeadBucketRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestDeleteNotification;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeadBucketParserGenerator_Test {

    final HeadBucketParserGenerator generator = new HeadBucketParserGenerator();

    @Test
    public void toReturnCode_Test() {
        final String expected = "return new MyResponse(MyResponse.Status.MyStatus);\n";
        assertThat(toReturnCode("MyResponse", "MyStatus"), is(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponseCodeList_Error_Test() {
        generator.toResponseCodeList(getRequestDeleteNotification().getDs3ResponseCodes(), "TestResponse", false);
    }

    @Test
    public void toResponseCodeList_Test() {
        final ImmutableList<ResponseCode> result = generator.toResponseCodeList(
                getHeadBucketRequest().getDs3ResponseCodes(),
                "TestResponse",
                false);

        assertThat(result.size(), is(3));

        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(0).getProcessingCode(),
                is("return new TestResponse(TestResponse.Status.EXISTS);\n"));

        assertThat(result.get(1).getCode(), is(403));
        assertThat(result.get(1).getProcessingCode(),
                is("return new TestResponse(TestResponse.Status.NOTAUTHORIZED);\n"));

        assertThat(result.get(2).getCode(), is(404));
        assertThat(result.get(2).getProcessingCode(),
                is("return new TestResponse(TestResponse.Status.DOESNTEXIST);\n"));
    }
}