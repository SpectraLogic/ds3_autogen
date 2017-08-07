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

import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.go.generators.request.*;
import com.spectralogic.ds3autogen.go.generators.response.BaseResponseGenerator;
import com.spectralogic.ds3autogen.go.generators.response.GetObjectResponseGenerator;
import com.spectralogic.ds3autogen.go.generators.response.NoResponseGenerator;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.io.IOException;

import static com.spectralogic.ds3autogen.go.GoCodeGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class GoCodeGenerator_Test {

    @Test
    public void getRequestGeneratorTest() {

        // Request with ReaderWithSizeDecorator payload
        assertThat(getRequestGenerator(getCreateMultiPartUploadPart()), instanceOf(ReaderRequestPayloadGenerator.class));

        // Amazon Put Object
        assertThat(getRequestGenerator(getRequestCreateObject()), instanceOf(PutObjectRequestGenerator.class));

        // Amazon Get Object request
        assertThat(getRequestGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectRequestGenerator.class));

        // Request with payload List<Ds3PutObject>
        assertThat(getRequestGenerator(getRequestBulkPut()), instanceOf(Ds3PutObjectPayloadGenerator.class));

        // Request with payload List<Ds3GetObject>
        assertThat(getRequestGenerator(getRequestBulkGet()), instanceOf(GetBulkJobRequestGenerator.class));

        // Requests with payloads List<Ds3Object>
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
        assertThat(getRequestGenerator(getCompleteMultipartUploadRequest()), instanceOf(PartsRequestPayloadGenerator.class));

        // Requests with ids payload
        assertThat(getRequestGenerator(clearSuspectBlobAzureTargetsRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(clearSuspectBlobPoolsRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(clearSuspectBlobS3TargetsRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(clearSuspectBlobTapesRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(markSuspectBlobAzureTargetsAsDegradedRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(markSuspectBlobDs3TargetsAsDegradedRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(markSuspectBlobPoolsAsDegradedRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(markSuspectBlobS3TargetsAsDegradedRequest()), instanceOf(IdsPayloadRequestGenerator.class));
        assertThat(getRequestGenerator(markSuspectBlobTapesAsDegradedRequest()), instanceOf(IdsPayloadRequestGenerator.class));

        // Non-special cased requests
        assertThat(getRequestGenerator(getGetBlobPersistence()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestBulkPut()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestBulkGet()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getEjectStorageDomainRequest()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestCreateNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestGetNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestDeleteNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getRequestMultiFileDelete()), instanceOf(BaseRequestGenerator.class));
        assertThat(getRequestGenerator(getBucketRequest()), instanceOf(BaseRequestGenerator.class));
    }

    @Test
    public void getResponseGeneratorTest() {
        // Amazon Get Object
        assertThat(getResponseGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectResponseGenerator.class));

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

    @Test
    public void getTypesParsedAsSlicesTest() throws IOException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(GoCodeGenerator_Test.class.getResourceAsStream("/input/getTypesParsedAsSliceTestInput.xml"));
        MatcherAssert.assertThat(spec, is(notNullValue()));
        assertThat(spec.getTypes().size(), is(4));

        final ImmutableSet<String> expected = ImmutableSet.of(
                "com.spectralogic.s3.server.domain.Job",
                "com.spectralogic.s3.server.domain.JobNode");

        final ImmutableSet<String> result = getTypesParsedAsSlices(spec.getTypes());

        assertThat(result.size(), is(expected.size()));
        result.forEach(item -> assertThat(expected, hasItem(item)));
    }
}
