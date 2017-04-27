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

package com.spectralogic.ds3autogen.go.generators.client;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.go.models.client.Command;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseClientGenerator_Test {

    private final BaseClientGenerator generator = new BaseClientGenerator();

    @Test
    public void hasHttpRedirectTest() {
        assertTrue(generator.hasHttpRedirect(getRequestGetNotification()));
        assertTrue(generator.hasHttpRedirect(getRequestVerifyPhysicalPlacement()));
        assertTrue(generator.hasHttpRedirect(getRequestGetJob()));
        assertTrue(generator.hasHttpRedirect(getGetBlobPersistence()));
        assertTrue(generator.hasHttpRedirect(getJobChunksReadyForClientProcessingRequest()));
        assertTrue(generator.hasHttpRedirect(getBucketRequest()));
        assertTrue(generator.hasHttpRedirect(getBucketsSpectraS3Request()));
        assertTrue(generator.hasHttpRedirect(getBucketsRequest()));
        assertTrue(generator.hasHttpRedirect(getSystemInformationRequest()));
        assertTrue(generator.hasHttpRedirect(getObjectsDetailsRequest()));
        assertTrue(generator.hasHttpRedirect(getRequestSpectraS3GetObject()));

        assertFalse(generator.hasHttpRedirect(getRequestAmazonS3GetObject()));
        assertFalse(generator.hasHttpRedirect(getRequestDeleteNotification()));
        assertFalse(generator.hasHttpRedirect(getRequestCreateNotification()));
        assertFalse(generator.hasHttpRedirect(getRequestBulkGet()));
        assertFalse(generator.hasHttpRedirect(getRequestBulkPut()));
        assertFalse(generator.hasHttpRedirect(getRequestMultiFileDelete()));
        assertFalse(generator.hasHttpRedirect(getRequestCreateObject()));
        assertFalse(generator.hasHttpRedirect(getReplicatePutJob()));
        assertFalse(generator.hasHttpRedirect(getCreateMultiPartUploadPart()));
        assertFalse(generator.hasHttpRedirect(getEjectStorageDomainRequest()));
        assertFalse(generator.hasHttpRedirect(getEjectStorageDomainBlobsRequest()));
        assertFalse(generator.hasHttpRedirect(getCompleteMultipartUploadRequest()));
        assertFalse(generator.hasHttpRedirect(getAllocateJobChunkRequest()));
        assertFalse(generator.hasHttpRedirect(getHeadBucketRequest()));
        assertFalse(generator.hasHttpRedirect(getHeadObjectRequest()));
        assertFalse(generator.hasHttpRedirect(createBucketRequest()));
        assertFalse(generator.hasHttpRedirect(createBucketSpectraS3Request()));
        assertFalse(generator.hasHttpRedirect(deleteBucketRequest()));
    }

    @Test
    public void toCommand_Test() {
        final Command expected = new Command("GetBucketHandler");
        assertThat(generator.toCommand(getBucketRequest()), is(expected));
    }

    @Test
    public void toCommandList_EmptyList_Test() {
        final ImmutableList<Command> result = generator.toCommandList(ImmutableList.of(), true);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toCommandList_HttpRedirectTrue_Test() {
        final ImmutableList<Command> expected = ImmutableList.of(
                new Command("GetBucketsHandler"),
                new Command("GetObjectsHandler")
        );

        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getBucketsRequest(),
                deleteBucketRequest(),
                getObjectsDetailsRequest(),
                getRequestBulkGet()
        );

        final ImmutableList<Command> result = generator.toCommandList(requests, true);
        assertThat(result.size(), is(expected.size()));

        expected.forEach(cmd -> assertThat(result, hasItem(cmd)));
    }

    @Test
    public void toCommandList_HttpRedirectFalse_Test() {
        final ImmutableList<Command> expected = ImmutableList.of(
                new Command("DeleteBucketHandler"),
                new Command("CreateGetJobHandler")
        );

        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getBucketsRequest(),
                deleteBucketRequest(),
                getObjectsDetailsRequest(),
                getRequestBulkGet()
        );

        final ImmutableList<Command> result = generator.toCommandList(requests, false);
        assertThat(result.size(), is(expected.size()));

        expected.forEach(cmd -> assertThat(result, hasItem(cmd)));
    }
}
