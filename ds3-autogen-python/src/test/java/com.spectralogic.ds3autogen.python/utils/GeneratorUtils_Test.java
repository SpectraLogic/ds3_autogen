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

package com.spectralogic.ds3autogen.python.utils;

import org.junit.Test;

import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.getAmazonS3RequestPath;
import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.getSpectraDs3RequestPath;
import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.toRequestPath;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GeneratorUtils_Test {

    @Test
    public void toRequestPath_Test() {
        //SpectraS3 Requests
        assertThat(toRequestPath(getRequestDeleteNotification()), is("'/_rest_/job_created_notification_registration/' + notification_id"));
        assertThat(toRequestPath(getRequestCreateNotification()), is("'/_rest_/job_created_notification_registration'"));
        assertThat(toRequestPath(getRequestGetNotification()), is("'/_rest_/job_completed_notification_registration/' + notification_id"));
        assertThat(toRequestPath(getRequestVerifyPhysicalPlacement()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(toRequestPath(getRequestBulkGet()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(toRequestPath(getRequestBulkPut()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(toRequestPath(getRequestSpectraS3GetObject()), is("'/_rest_/object/' + object_name"));
        assertThat(toRequestPath(getRequestGetJob()), is("'/_rest_/job/' + job_id"));
        assertThat(toRequestPath(getReplicatePutJob()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(toRequestPath(getGetBlobPersistence()), is("'/_rest_/blob_persistence'"));
        assertThat(toRequestPath(getEjectStorageDomainRequest()), is("'/_rest_/tape'"));
        assertThat(toRequestPath(getAllocateJobChunkRequest()), is("'/_rest_/job_chunk/' + job_chunk_id"));
        assertThat(toRequestPath(getJobChunksReadyForClientProcessingRequest()), is("'/_rest_/job_chunk'"));
        assertThat(toRequestPath(getBucketsRequest()), is("'/_rest_/bucket'"));
        assertThat(toRequestPath(getSystemInformationRequest()), is("'/_rest_/system_information'"));
        assertThat(toRequestPath(createBucketSpectraS3Request()), is("'/_rest_/bucket'"));
        assertThat(toRequestPath(deleteBucketRequest()), is("'/_rest_/bucket/' + bucket_name"));

        //Amazon Requests
        assertThat(toRequestPath(getRequestMultiFileDelete()), is("'/' + bucket_name"));
        assertThat(toRequestPath(getRequestCreateObject()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(toRequestPath(getRequestAmazonS3GetObject()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(toRequestPath(getCreateMultiPartUploadPart()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(toRequestPath(getCompleteMultipartUploadRequest()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(toRequestPath(getHeadBucketRequest()), is("'/' + bucket_name"));
        assertThat(toRequestPath(getBucketRequest()), is("'/' + bucket_name"));
        assertThat(toRequestPath(createBucketRequest()), is("'/' + bucket_name"));
    }

    @Test
    public void getAmazonS3RequestPath_Test() {
        //Amazon Requests
        assertThat(getAmazonS3RequestPath(getRequestMultiFileDelete()), is("'/' + bucket_name"));
        assertThat(getAmazonS3RequestPath(getRequestCreateObject()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(getAmazonS3RequestPath(getRequestAmazonS3GetObject()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(getAmazonS3RequestPath(getCreateMultiPartUploadPart()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(getAmazonS3RequestPath(getCompleteMultipartUploadRequest()), is("'/' + bucket_name + '/' + object_name"));
        assertThat(getAmazonS3RequestPath(getHeadBucketRequest()), is("'/' + bucket_name"));
        assertThat(getAmazonS3RequestPath(getBucketRequest()), is("'/' + bucket_name"));
        assertThat(getAmazonS3RequestPath(createBucketRequest()), is("'/' + bucket_name"));

        //SpectraS3 Requests
        assertThat(getAmazonS3RequestPath(getRequestDeleteNotification()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestCreateNotification()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestGetNotification()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestVerifyPhysicalPlacement()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestBulkGet()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestBulkPut()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestSpectraS3GetObject()), is(""));
        assertThat(getAmazonS3RequestPath(getRequestGetJob()), is(""));
        assertThat(getAmazonS3RequestPath(getReplicatePutJob()), is(""));
        assertThat(getAmazonS3RequestPath(getGetBlobPersistence()), is(""));
        assertThat(getAmazonS3RequestPath(getEjectStorageDomainRequest()), is(""));
        assertThat(getAmazonS3RequestPath(getAllocateJobChunkRequest()), is(""));
        assertThat(getAmazonS3RequestPath(getJobChunksReadyForClientProcessingRequest()), is(""));
        assertThat(getAmazonS3RequestPath(getBucketsRequest()), is(""));
        assertThat(getAmazonS3RequestPath(getSystemInformationRequest()), is(""));
        assertThat(getAmazonS3RequestPath(createBucketSpectraS3Request()), is(""));
        assertThat(getAmazonS3RequestPath(deleteBucketRequest()), is(""));
    }

    @Test
    public void getSpectraDs3RequestPath_Test() {
        //SpectraS3 Requests
        assertThat(getSpectraDs3RequestPath(getRequestDeleteNotification()), is("'/_rest_/job_created_notification_registration/' + notification_id"));
        assertThat(getSpectraDs3RequestPath(getRequestCreateNotification()), is("'/_rest_/job_created_notification_registration'"));
        assertThat(getSpectraDs3RequestPath(getRequestGetNotification()), is("'/_rest_/job_completed_notification_registration/' + notification_id"));
        assertThat(getSpectraDs3RequestPath(getRequestVerifyPhysicalPlacement()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(getSpectraDs3RequestPath(getRequestBulkGet()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(getSpectraDs3RequestPath(getRequestBulkPut()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(getSpectraDs3RequestPath(getRequestSpectraS3GetObject()), is("'/_rest_/object/' + object_name"));
        assertThat(getSpectraDs3RequestPath(getRequestGetJob()), is("'/_rest_/job/' + job_id"));
        assertThat(getSpectraDs3RequestPath(getReplicatePutJob()), is("'/_rest_/bucket/' + bucket_name"));
        assertThat(getSpectraDs3RequestPath(getGetBlobPersistence()), is("'/_rest_/blob_persistence'"));
        assertThat(getSpectraDs3RequestPath(getEjectStorageDomainRequest()), is("'/_rest_/tape'"));
        assertThat(getSpectraDs3RequestPath(getAllocateJobChunkRequest()), is("'/_rest_/job_chunk/' + job_chunk_id"));
        assertThat(getSpectraDs3RequestPath(getJobChunksReadyForClientProcessingRequest()), is("'/_rest_/job_chunk'"));
        assertThat(getSpectraDs3RequestPath(getBucketsRequest()), is("'/_rest_/bucket'"));
        assertThat(getSpectraDs3RequestPath(getSystemInformationRequest()), is("'/_rest_/system_information'"));
        assertThat(getSpectraDs3RequestPath(createBucketSpectraS3Request()), is("'/_rest_/bucket'"));
        assertThat(getSpectraDs3RequestPath(deleteBucketRequest()), is("'/_rest_/bucket/' + bucket_name"));

        //Amazon Requests
        assertThat(getSpectraDs3RequestPath(getRequestMultiFileDelete()), is(""));
        assertThat(getSpectraDs3RequestPath(getRequestCreateObject()), is(""));
        assertThat(getSpectraDs3RequestPath(getRequestAmazonS3GetObject()), is(""));
        assertThat(getSpectraDs3RequestPath(getCreateMultiPartUploadPart()), is(""));
        assertThat(getSpectraDs3RequestPath(getCompleteMultipartUploadRequest()), is(""));
        assertThat(getSpectraDs3RequestPath(getHeadBucketRequest()), is(""));
        assertThat(getSpectraDs3RequestPath(getBucketRequest()), is(""));
        assertThat(getSpectraDs3RequestPath(createBucketRequest()), is(""));
    }
}
