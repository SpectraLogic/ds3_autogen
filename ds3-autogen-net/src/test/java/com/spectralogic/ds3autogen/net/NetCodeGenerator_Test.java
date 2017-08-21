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

package com.spectralogic.ds3autogen.net;

import com.spectralogic.ds3autogen.net.generators.requestmodels.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.NetCodeGenerator.getTemplateModelGenerator;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class NetCodeGenerator_Test {

    @Test
    public void getTemplateModelGeneratorTest() {

        // Request with ReaderWithSizeDecorator payload
        assertThat(getTemplateModelGenerator(getCreateMultiPartUploadPart()), instanceOf(StreamRequestPayloadGenerator.class));

        // Amazon Put Object
        assertThat(getTemplateModelGenerator(getRequestCreateObject()), instanceOf(PutObjectRequestGenerator.class));

        // Amazon Get Object request
        assertThat(getTemplateModelGenerator(getRequestAmazonS3GetObject()), instanceOf(GetObjectRequestGenerator.class));

        // Request with payload List<Ds3Object>
        assertThat(getTemplateModelGenerator(getRequestBulkPut()), instanceOf(BulkPutRequestGenerator.class));

        // Request with payload List<Ds3Partial>
        assertThat(getTemplateModelGenerator(getRequestBulkGet()), instanceOf(BulkGetRequestGenerator.class));
        assertThat(getTemplateModelGenerator(createVerifyJobRequest()), instanceOf(PartialObjectRequestPayloadGenerator.class));

        // Requests with payloads List<string>
        assertThat(getTemplateModelGenerator(getEjectStorageDomainBlobsRequest()), instanceOf(ObjectsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestVerifyPhysicalPlacement()), instanceOf(ObjectsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(getPhysicalPlacementForObjects()), instanceOf(ObjectsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(verifyPhysicalPlacementForObjectsWithFullDetailsRequest()), instanceOf(ObjectsRequestPayloadGenerator.class));

        // Request with object name list payload
        assertThat(getTemplateModelGenerator(getRequestMultiFileDelete()), instanceOf(ObjectsRequestPayloadGenerator.class));

        //Requests with string payloads
        assertThat(getTemplateModelGenerator(getGetBlobPersistence()), instanceOf(StringRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(getReplicatePutJob()), instanceOf(StringRequestPayloadGenerator.class));

        // Request with CompleteMultipartUpload request payload
        assertThat(getTemplateModelGenerator(getCompleteMultipartUploadRequest()), instanceOf(PartsRequestPayloadGenerator.class));

        // Requests with ids payload
        assertThat(getTemplateModelGenerator(clearSuspectBlobAzureTargetsRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(clearSuspectBlobPoolsRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(clearSuspectBlobS3TargetsRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(clearSuspectBlobTapesRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(markSuspectBlobAzureTargetsAsDegradedRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(markSuspectBlobDs3TargetsAsDegradedRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(markSuspectBlobPoolsAsDegradedRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(markSuspectBlobS3TargetsAsDegradedRequest()), instanceOf(IdsRequestPayloadGenerator.class));
        assertThat(getTemplateModelGenerator(markSuspectBlobTapesAsDegradedRequest()), instanceOf(IdsRequestPayloadGenerator.class));

        // Non-special cased requests
        assertThat(getTemplateModelGenerator(getGetBlobPersistence()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestBulkPut()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestBulkGet()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getEjectStorageDomainRequest()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestCreateNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestGetNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestDeleteNotification()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getRequestMultiFileDelete()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getBucketRequest()), instanceOf(BaseRequestGenerator.class));

        assertThat(getTemplateModelGenerator(getBlobsOnAzureTargetSpectraS3Request()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getBlobsOnTapeSpectraS3Request()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getBlobsOnS3TargetSpectraS3Request()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getBlobsOnPoolSpectraS3Request()), instanceOf(BaseRequestGenerator.class));
        assertThat(getTemplateModelGenerator(getBlobsOnDs3TargetSpectraS3Request()), instanceOf(BaseRequestGenerator.class));
    }
}
