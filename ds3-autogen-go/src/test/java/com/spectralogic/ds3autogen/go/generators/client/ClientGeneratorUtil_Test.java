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
import com.spectralogic.ds3autogen.api.models.enums.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.go.generators.client.ClientGeneratorUtilKt.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientGeneratorUtil_Test {

    @Test
    public void toRequestPath_Test() {
        //Spectra requests
        assertThat(toRequestPath(getRequestDeleteNotification()))
                .isEqualTo("\"/_rest_/job_created_notification_registration/\" + request.NotificationId");
        assertThat(toRequestPath(getRequestCreateNotification()))
                .isEqualTo("\"/_rest_/job_created_notification_registration\"");
        assertThat(toRequestPath(getRequestGetNotification()))
                .isEqualTo("\"/_rest_/job_completed_notification_registration/\" + request.NotificationId");
        assertThat(toRequestPath(getRequestVerifyPhysicalPlacement())).
                isEqualTo("\"/_rest_/bucket/\" + request.BucketName");
        assertThat(toRequestPath(getRequestBulkGet()))
                .isEqualTo("\"/_rest_/bucket/\" + request.BucketName");
        assertThat(toRequestPath(getRequestBulkPut()))
                .isEqualTo("\"/_rest_/bucket/\" + request.BucketName");
        assertThat(toRequestPath(getRequestSpectraS3GetObject()))
                .isEqualTo("\"/_rest_/object/\" + request.ObjectName");
        assertThat(toRequestPath(getRequestGetJob()))
                .isEqualTo("\"/_rest_/job/\" + request.JobId");

        //Amazon requests
        assertThat(toRequestPath(getRequestMultiFileDelete()))
                .isEqualTo("\"/\" + request.BucketName");
        assertThat(toRequestPath(getRequestCreateObject()))
                .isEqualTo("\"/\" + request.BucketName + \"/\" + request.ObjectName");
        assertThat(toRequestPath(getRequestAmazonS3GetObject()))
                .isEqualTo("\"/\" + request.BucketName + \"/\" + request.ObjectName");
    }

    @Test (expected = IllegalArgumentException.class)
    public void toRequestPath_Exception_Test() {
        final Ds3Request request = new Ds3Request(
                "Test",
                HttpVerb.GET,
                Classification.spectrainternal,
                Requirement.NOT_ALLOWED,
                Requirement.NOT_ALLOWED,
                Action.BULK_DELETE,
                Resource.ACTIVE_JOB,
                ResourceType.NON_SINGLETON,
                Operation.ALLOCATE,
                false,
                ImmutableList.of(),
                ImmutableList.of(),
                ImmutableList.of());

        toRequestPath(request);
    }

    @Test
    public void getAmazonS3RequestPath_Test() {
        //Amazon requests
        assertThat(getAmazonS3RequestPath(getRequestMultiFileDelete()))
                .isEqualTo("\"/\" + request.BucketName");
        assertThat(getAmazonS3RequestPath(getRequestCreateObject()))
                .isEqualTo("\"/\" + request.BucketName + \"/\" + request.ObjectName");
        assertThat(getAmazonS3RequestPath(getRequestAmazonS3GetObject()))
                .isEqualTo("\"/\" + request.BucketName + \"/\" + request.ObjectName");

        //Spectra requests
        assertThat(getAmazonS3RequestPath(getRequestDeleteNotification())).isEmpty();
        assertThat(getAmazonS3RequestPath(getRequestCreateNotification())).isEmpty();
        assertThat(getAmazonS3RequestPath(getRequestGetNotification())).isEmpty();
        assertThat(getAmazonS3RequestPath(getRequestVerifyPhysicalPlacement())).isEmpty();
        assertThat(getAmazonS3RequestPath(getRequestBulkGet())).isEmpty();
        assertThat(getAmazonS3RequestPath(getRequestBulkPut())).isEmpty();
        assertThat(getAmazonS3RequestPath(getRequestSpectraS3GetObject())).isEmpty();
        assertThat(getAmazonS3RequestPath(getRequestGetJob())).isEmpty();
    }

    @Test
    public void getSpectraDs3RequestPath_Test() {
        //Spectra requests
        assertThat(getSpectraDs3RequestPath(getRequestDeleteNotification()))
                .isEqualTo("\"/_rest_/job_created_notification_registration/\" + request.NotificationId");
        assertThat(getSpectraDs3RequestPath(getRequestCreateNotification()))
                .isEqualTo("\"/_rest_/job_created_notification_registration\"");
        assertThat(getSpectraDs3RequestPath(getRequestGetNotification()))
                .isEqualTo("\"/_rest_/job_completed_notification_registration/\" + request.NotificationId");
        assertThat(getSpectraDs3RequestPath(getRequestVerifyPhysicalPlacement()))
                .isEqualTo("\"/_rest_/bucket/\" + request.BucketName");
        assertThat(getSpectraDs3RequestPath(getRequestBulkGet()))
                .isEqualTo("\"/_rest_/bucket/\" + request.BucketName");
        assertThat(getSpectraDs3RequestPath(getRequestBulkPut()))
                .isEqualTo("\"/_rest_/bucket/\" + request.BucketName");
        assertThat(getSpectraDs3RequestPath(getRequestSpectraS3GetObject()))
                .isEqualTo("\"/_rest_/object/\" + request.ObjectName");
        assertThat(getSpectraDs3RequestPath(getRequestGetJob()))
                .isEqualTo("\"/_rest_/job/\" + request.JobId");

        //Amazon requests
        assertThat(getSpectraDs3RequestPath(getRequestMultiFileDelete())).isEmpty();
        assertThat(getSpectraDs3RequestPath(getRequestCreateObject())).isEmpty();
        assertThat(getSpectraDs3RequestPath(getRequestAmazonS3GetObject())).isEmpty();
    }
}
