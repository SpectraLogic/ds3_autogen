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

package com.spectralogic.ds3autogen.java;

import com.spectralogic.ds3autogen.java.generators.requestmodels.*;
import com.spectralogic.ds3autogen.java.generators.responsemodels.AllocateChunkResponseGenerator;
import com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator;
import com.spectralogic.ds3autogen.java.generators.responsemodels.HeadBucketResponseGenerator;
import com.spectralogic.ds3autogen.java.generators.responsemodels.HeadObjectResponseGenerator;
import com.spectralogic.ds3autogen.java.generators.responseparser.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.JavaCodeGenerator.getResponseParserGenerator;
import static com.spectralogic.ds3autogen.java.JavaCodeGenerator.getResponseTemplateModelGenerator;
import static com.spectralogic.ds3autogen.java.JavaCodeGenerator.getTemplateModelGenerator;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class JavaCodeGenerator_Test {

    @Test
    public void getResponseTemplateModelGenerator_Test() {
        assertThat(getResponseTemplateModelGenerator(createBucketRequest()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseTemplateModelGenerator(getHeadObjectRequest()), instanceOf(HeadObjectResponseGenerator.class));
        assertThat(getResponseTemplateModelGenerator(getHeadBucketRequest()), instanceOf(HeadBucketResponseGenerator.class));
        assertThat(getResponseTemplateModelGenerator(getAllocateJobChunkRequest()), instanceOf(AllocateChunkResponseGenerator.class));
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
    public void getTemplateModelGenerator_Test() {
        assertThat(getTemplateModelGenerator(getGetBlobPersistence()), instanceOf(StringRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestBulkPut()), instanceOf(BulkRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestBulkGet()), instanceOf(BulkRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getEjectStorageDomainRequest()), instanceOf(ObjectsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestVerifyPhysicalPlacement()), instanceOf(ObjectsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestCreateObject()), instanceOf(CreateObjectRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestCreateNotification()), instanceOf(CreateNotificationRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestGetNotification()), instanceOf(NotificationRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestDeleteNotification()), instanceOf(NotificationRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestMultiFileDelete()), instanceOf(MultiFileDeleteRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getCreateMultiPartUploadPart()), instanceOf(StreamRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(getCompleteMultipartUploadRequest()), instanceOf(CompleteMultipartUploadRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getBucketRequest()), instanceOf(BaseRequestGenerator.class));
    }
}
