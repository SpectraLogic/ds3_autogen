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

package com.spectralogic.ds3autogen.go;

import com.spectralogic.ds3autogen.go.generators.request.*;
import com.spectralogic.ds3autogen.go.generators.response.BaseResponseGenerator;
import com.spectralogic.ds3autogen.go.generators.response.NoResponseGenerator;
import org.junit.Test;

import static com.spectralogic.ds3autogen.go.GoCodeGenerator.getRequestGenerator;
import static com.spectralogic.ds3autogen.go.GoCodeGenerator.getResponseGenerator;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class GoCodeGenerator_Test {

    @Test
    public void getRequestGeneratorTest() {
        // Amazon Get Object request
        assertThat(getRequestGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectRequestGenerator.class));

        // Requests with payloads List<Ds3Object>
        assertThat(getRequestGenerator(getRequestBulkGet()), instanceOf(RequiredObjectsPayloadGenerator.class));
        assertThat(getRequestGenerator(getRequestBulkPut()), instanceOf(RequiredObjectsPayloadGenerator.class));
        assertThat(getRequestGenerator(getEjectStorageDomainBlobsRequest()), instanceOf(RequiredObjectsPayloadGenerator.class));
        assertThat(getRequestGenerator(getRequestVerifyPhysicalPlacement()), instanceOf(RequiredObjectsPayloadGenerator.class));
        assertThat(getRequestGenerator(createVerifyJobRequest()), instanceOf(RequiredObjectsPayloadGenerator.class));
        assertThat(getRequestGenerator(getPhysicalPlacementForObjects()), instanceOf(RequiredObjectsPayloadGenerator.class));
        assertThat(getRequestGenerator(verifyPhysicalPlacementForObjectsWithFullDetails()), instanceOf(RequiredObjectsPayloadGenerator.class));

        // Request with object name list payload
        assertThat(getRequestGenerator(getRequestMultiFileDelete()), instanceOf(DeleteObjectsRequestGenerator.class));

        //Requests with string payloads
        assertThat(getRequestGenerator(getGetBlobPersistence()), instanceOf(StringRequestPayloadGenerator.class));
        assertThat(getRequestGenerator(getReplicatePutJob()), instanceOf(StringRequestPayloadGenerator.class));

        // Request with CompleteMultipartUpload request payload
        assertThat(getRequestGenerator(getCompleteMultipartUploadRequest()), instanceOf(MultipartUploadPayloadGenerator.class));

        // Non-special cased requests
        assertThat(getRequestGenerator(getGetBlobPersistence()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestBulkPut()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestBulkGet()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getEjectStorageDomainRequest()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestCreateObject()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestCreateNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestGetNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestDeleteNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestMultiFileDelete()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getCreateMultiPartUploadPart()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getCompleteMultipartUploadRequest()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getBucketRequest()), instanceOf(BaseRequestGenerator.class));
    }

    @Test
    public void getResponseGeneratorTest() {
        // Commands with no response payload
        assertThat(getResponseGenerator(getRequestDeleteNotification()), instanceOf(NoResponseGenerator.class));
        assertThat(getResponseGenerator(getHeadObjectRequest()), instanceOf(NoResponseGenerator.class));

        // Commands with a standard response payload
        assertThat(getResponseGenerator(getRequestAmazonS3GetObject()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseGenerator(getRequestGetJob()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseGenerator(getAllocateJobChunkRequest()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseGenerator(getHeadBucketRequest()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseGenerator(getJobChunksReadyForClientProcessingRequest()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseGenerator(getBucketRequest()), instanceOf(BaseResponseGenerator.class));
        assertThat(getResponseGenerator(getBucketsSpectraS3Request()), instanceOf(BaseResponseGenerator.class));
    }
}
