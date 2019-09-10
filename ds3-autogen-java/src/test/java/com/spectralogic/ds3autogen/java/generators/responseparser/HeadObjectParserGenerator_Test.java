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

package com.spectralogic.ds3autogen.java.generators.responseparser;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.java.models.ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.generators.responseparser.HeadObjectParserGenerator.toDoesntExistReturnCode;
import static com.spectralogic.ds3autogen.java.generators.responseparser.HeadObjectParserGenerator.toExistsReturnCode;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getHeadBucketRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestDeleteNotification;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeadObjectParserGenerator_Test {

    private final HeadObjectParserGenerator generator = new HeadObjectParserGenerator();

    @Test
    public void toExistsReturnCode_Test() {
        final String expected = "final ChecksumType.Type blobChecksumType = getBlobChecksumType(response.getHeaders());\n" +
                "                final ImmutableMap<Long, String> blobChecksumMap = getBlobChecksumMap(response.getHeaders());\n" +
                "                final ZonedDateTime creationDate = getCreationDate(response.getHeaders());\n" +
                "                final UUID versionId = getVersionId(response.getHeaders());\n" +
                "                return new MyResponse(blobChecksumMap, blobChecksumType, creationDate, metadata, objectSize, MyResponse.Status.EXISTS, versionId, this.getChecksum(), this.getChecksumType());\n";
        assertThat(toExistsReturnCode("MyResponse"), is(expected));
    }

    @Test
    public void toDoesntExistReturnCode_Test() {
        final String expected = "return new MyResponse(ImmutableMap.of(), ChecksumType.Type.NONE, null, metadata, objectSize, MyResponse.Status.DOESNTEXIST, null, this.getChecksum(), this.getChecksumType());\n";
        assertThat(toDoesntExistReturnCode("MyResponse"), is(expected));
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

        assertThat(result.size(), is(2));

        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(0).getProcessingCode(),
                is("final ChecksumType.Type blobChecksumType = getBlobChecksumType(response.getHeaders());\n" +
                        "                final ImmutableMap<Long, String> blobChecksumMap = getBlobChecksumMap(response.getHeaders());\n" +
                        "                final ZonedDateTime creationDate = getCreationDate(response.getHeaders());\n" +
                        "                final UUID versionId = getVersionId(response.getHeaders());\n" +
                        "                return new TestResponse(blobChecksumMap, blobChecksumType, creationDate, metadata, objectSize, TestResponse.Status.EXISTS, versionId, this.getChecksum(), this.getChecksumType());\n"));

        assertThat(result.get(1).getCode(), is(404));
        assertThat(result.get(1).getProcessingCode(),
                is("return new TestResponse(ImmutableMap.of(), ChecksumType.Type.NONE, null, metadata, objectSize, TestResponse.Status.DOESNTEXIST, null, this.getChecksum(), this.getChecksumType());\n"));
    }
}
