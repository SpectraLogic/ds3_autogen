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
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.go.generators.client.command.*;
import com.spectralogic.ds3autogen.go.models.client.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
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
        final Command expected = new Command(
                "GetBucketHandler",
                ImmutableList.of(
                        new HttpVerbBuildLine(HttpVerb.GET),
                        new PathBuildLine("\"/\" + request.BucketName"),
                        new OptionalQueryParamBuildLine("delimiter", "request.Delimiter"),
                        new OptionalQueryParamBuildLine("marker", "request.Marker"),
                        new OptionalQueryParamBuildLine("max_keys", "networking.IntPtrToStrPtr(request.MaxKeys)"),
                        new OptionalQueryParamBuildLine("prefix", "request.Prefix")
                ));

        assertThat(generator.toCommand(getBucketRequest())).isEqualTo(expected);
    }

    @Test
    public void toCommandList_EmptyList_Test() {
        final ImmutableList<Command> result = generator.toCommandList(ImmutableList.of(), true);
        assertThat(result).hasSize(0);
    }

    @Test
    public void toCommandList_HttpRedirectTrue_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getBucketsRequest(),
                deleteBucketRequest(),
                getObjectsDetailsRequest(),
                getRequestBulkGet()
        );

        final ImmutableList<Command> result = generator.toCommandList(requests, true);
        assertThat(result).containsExactlyInAnyOrder(
                new Command(
                        "GetBucketsHandler",
                        ImmutableList.of(
                                new HttpVerbBuildLine(HttpVerb.GET),
                                new PathBuildLine("\"/_rest_/bucket\""),
                                new OptionalQueryParamBuildLine("data_policy_id", "request.DataPolicyId"),
                                new VoidOptionalQueryParamBuildLine("last_page", "LastPage"),
                                new OptionalQueryParamBuildLine("name", "request.Name"),
                                new OptionalQueryParamBuildLine("page_length", "networking.IntPtrToStrPtr(request.PageLength)"),
                                new OptionalQueryParamBuildLine("page_offset", "networking.IntPtrToStrPtr(request.PageOffset)"),
                                new OptionalQueryParamBuildLine("page_start_marker", "request.PageStartMarker"),
                                new OptionalQueryParamBuildLine("user_id", "request.UserId")
                        )),
                new Command(
                        "GetObjectsHandler",
                        ImmutableList.of(
                                new HttpVerbBuildLine(HttpVerb.GET),
                                new PathBuildLine("\"/_rest_/object\""),
                                new OptionalQueryParamBuildLine("bucket_id", "request.BucketId"),
                                new OptionalQueryParamBuildLine("folder", "request.Folder"),
                                new VoidOptionalQueryParamBuildLine("last_page", "LastPage"),
                                new OptionalQueryParamBuildLine("latest", "networking.BoolPtrToStrPtr(request.Latest)"),
                                new OptionalQueryParamBuildLine("name", "request.Name"),
                                new OptionalQueryParamBuildLine("page_length", "networking.IntPtrToStrPtr(request.PageLength)"),
                                new OptionalQueryParamBuildLine("page_offset", "networking.IntPtrToStrPtr(request.PageOffset)"),
                                new OptionalQueryParamBuildLine("page_start_marker", "request.PageStartMarker"),
                                new OptionalQueryParamBuildLine("type", "networking.InterfaceToStrPtr(request.S3ObjectType)"),
                                new OptionalQueryParamBuildLine("version", "networking.Int64PtrToStrPtr(request.Version)")
                        ))
                );
    }

    @Test
    public void toCommandList_HttpRedirectFalse_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getBucketsRequest(),
                deleteBucketRequest(),
                getObjectsDetailsRequest(),
                getRequestBulkGet()
        );

        final ImmutableList<Command> result = generator.toCommandList(requests, false);

        assertThat(result).containsExactlyInAnyOrder(
                new Command(
                        "DeleteBucketHandler",
                        ImmutableList.of(
                                new HttpVerbBuildLine(HttpVerb.DELETE),
                                new PathBuildLine("\"/_rest_/bucket/\" + request.BucketName"),
                                new VoidOptionalQueryParamBuildLine("force", "Force")
                        )),
                new Command(
                        "CreateGetJobHandler",
                        ImmutableList.of(
                                new HttpVerbBuildLine(HttpVerb.PUT),
                                new PathBuildLine("\"/_rest_/bucket/\" + request.BucketName"),
                                new OptionalQueryParamBuildLine("chunk_client_processing_order_guarantee", "networking.InterfaceToStrPtr(request.ChunkClientProcessingOrderGuarantee)"),
                                new OptionalQueryParamBuildLine("priority", "networking.InterfaceToStrPtr(request.Priority)"),
                                new OperationBuildLine(Operation.START_BULK_GET)
                        ))
                );
    }

    @Test
    public void getCommandGeneratorTest() {
        assertThat(generator.getCommandGenerator(getBucketsRequest()))
                .isInstanceOf(BaseCommandGenerator.class);

        assertThat(generator.getCommandGenerator(getRequestCreateObject()))
                .isInstanceOf(PutObjectCommandGenerator.class);

        assertThat(generator.getCommandGenerator(getRequestAmazonS3GetObject()))
                .isInstanceOf(GetObjectCommandGenerator.class);

        assertThat(generator.getCommandGenerator(getCreateMultiPartUploadPart()))
                .isInstanceOf(ReaderPayloadCommandGenerator.class);

        assertThat(generator.getCommandGenerator(getRequestBulkPut()))
                .isInstanceOf(Ds3PutObjectPayloadCommandGenerator.class);

        assertThat(generator.getCommandGenerator(getRequestBulkGet()))
                .isInstanceOf(Ds3GetObjectPayloadCommandGenerator.class);
    }
}
