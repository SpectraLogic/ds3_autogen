/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.converters;

import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.converters.RequestConverter.requestPath;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RequestConverter_Test {

    @Test
    public void requestPath_Empty_Test() {
        final String expectedPath = "\"/\"";
        final Ds3Request request = new Ds3Request(
                "RequestName",
                null,
                Classification.amazons3,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        final String requestPath = requestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void requestPath_Bucket_Test() {
        final String expectedPath = "\"/\" + this.bucketName";
        final Ds3Request request = new Ds3Request(
                "RequestName",
                null,
                Classification.amazons3,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        final String requestPath = requestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void requestPath_BucketAndObject_Test() {
        final String expectedPath = "\"/\" + this.bucketName + \"/\" + this.objectName";
        final Ds3Request request = new Ds3Request(
                "RequestName",
                null,
                Classification.amazons3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        final String requestPath = requestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void requestPath_SpectraS3_Test() {
        final String expectedPath = "\"/_rest_/\"";
        final Ds3Request request = new Ds3Request(
                "RequestName",
                null,
                Classification.spectrads3,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        final String requestPath = requestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void requestPath_SpectraS3Resource_Test() {
        final String expectedPath = "\"/_rest_/active_job/\"";
        final Ds3Request request = new Ds3Request(
                "RequestName",
                null,
                Classification.spectrads3,
                null,
                null,
                null,
                Resource.ACTIVE_JOB,
                null,
                null,
                null,
                null,
                null);

        final String requestPath = requestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void requestPath_SpectraS3ResourceWithBucket_Test() {
        final String expectedPath = "\"/_rest_/bucket/\" + this.bucketName";
        final Ds3Request request = new Ds3Request(
                "RequestName",
                null,
                Classification.spectrads3,
                null,
                null,
                null,
                Resource.BUCKET,
                null,
                null,
                null,
                null,
                null);

        final String requestPath = requestPath(request);
        assertThat(requestPath, is(expectedPath));
    }

    @Test
    public void requestPath_SpectraS3ResourceWithJobChunkId_Test() {
        final String expectedPath = "\"/_rest_/job_chunk/\" + jobChunkId.toString()";
        final Ds3Request request = new Ds3Request(
                "RequestName",
                null,
                Classification.spectrads3,
                null,
                null,
                null,
                Resource.JOB_CHUNK,
                ResourceType.NON_SINGLETON,
                null,
                null,
                null,
                null);

        final String requestPath = requestPath(request);
        assertThat(requestPath, is(expectedPath));
    }
}
