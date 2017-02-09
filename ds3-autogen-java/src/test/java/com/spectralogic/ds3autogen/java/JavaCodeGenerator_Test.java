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

package com.spectralogic.ds3autogen.java;

import com.spectralogic.ds3autogen.java.generators.requestmodels.*;
import com.spectralogic.ds3autogen.java.generators.responsemodels.*;
import com.spectralogic.ds3autogen.java.generators.responseparser.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.JavaCodeGenerator.getResponseParserGenerator;
import static com.spectralogic.ds3autogen.java.JavaCodeGenerator.getResponseGenerator;
import static com.spectralogic.ds3autogen.java.JavaCodeGenerator.getRequestGenerator;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class JavaCodeGenerator_Test {

    @Test
    public void getResponseGenerator_Test() {
        assertThat(getResponseGenerator(createBucketRequest()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseGenerator(getHeadObjectRequest()), instanceOf(HeadObjectResponseGenerator.class));
        assertThat(getResponseGenerator(getHeadBucketRequest()), instanceOf(HeadBucketResponseGenerator.class));
        assertThat(getResponseGenerator(getAllocateJobChunkRequest()), instanceOf(RetryAfterResponseGenerator.class));
        assertThat(getResponseGenerator(getJobChunksReadyForClientProcessingRequest()), instanceOf(RetryAfterResponseGenerator.class));
        assertThat(getResponseGenerator(getRequestBulkGet()), instanceOf(BulkResponseGenerator.class));
        assertThat(getResponseGenerator(getRequestBulkPut()), instanceOf(BulkResponseGenerator.class));
        assertThat(getResponseGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectResponseGenerator.class));
        assertThat(getResponseGenerator(getBucketsSpectraS3Request()), instanceOf(PaginationResponseGenerator.class));
    }

    @Test
    public void getResponseParserGenerator_Test() {
        assertThat(getResponseParserGenerator(createBucketRequest()), instanceOf(BaseResponseParserGenerator.class));
        assertThat(getResponseParserGenerator(getHeadBucketRequest()), instanceOf(HeadBucketParserGenerator.class));
        assertThat(getResponseParserGenerator(getHeadObjectRequest()), instanceOf(HeadObjectParserGenerator.class));
        assertThat(getResponseParserGenerator(getAllocateJobChunkRequest()), instanceOf(AllocateJobChunkParserGenerator.class));
        assertThat(getResponseParserGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectParserGenerator.class));
        assertThat(getResponseParserGenerator(getJobChunksReadyForClientProcessingRequest()), instanceOf(GetJobChunksReadyParserGenerator.class));
    }

    @Test
    public void getRequestGenerator_Test() {
        assertThat(getRequestGenerator(getGetBlobPersistence()), instanceOf(StringRequestPayloadGenerator.class));
        assertThat(getRequestGenerator(getRequestBulkPut()), instanceOf(BulkRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestBulkGet()), instanceOf(BulkRequestGenerator.class));
        assertThat(getRequestGenerator(getEjectStorageDomainRequest()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestVerifyPhysicalPlacement()), instanceOf(ObjectsRequestPayloadGenerator.class));
        assertThat(getRequestGenerator(getRequestCreateObject()), instanceOf(CreateObjectRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestCreateNotification()), instanceOf(CreateNotificationRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestGetNotification()), instanceOf(NotificationRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestDeleteNotification()), instanceOf(NotificationRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestMultiFileDelete()), instanceOf(MultiFileDeleteRequestGenerator.class));
        assertThat(getRequestGenerator(getCreateMultiPartUploadPart()), instanceOf(StreamRequestPayloadGenerator.class));
        assertThat(getRequestGenerator(getCompleteMultipartUploadRequest()), instanceOf(CompleteMultipartUploadRequestGenerator.class));
        assertThat(getRequestGenerator(getBucketRequest()), instanceOf(BaseRequestGenerator.class));
    }
}
