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

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.converters.RequestConverter.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class RequestConverter_Test {

    @Test
    public void isResourceAnArg_Test() {
        assertFalse(isResourceAnArg(null, null));
        assertFalse(isResourceAnArg(null, ResourceType.NON_SINGLETON));
        assertFalse(isResourceAnArg(Resource.BUCKET, null));
        assertFalse(isResourceAnArg(Resource.BUCKET, ResourceType.SINGLETON));
        assertFalse(isResourceAnArg(Resource.GENERIC_DAO_NOTIFICATION_REGISTRATION, ResourceType.NON_SINGLETON));
        assertTrue(isResourceAnArg(Resource.BUCKET, ResourceType.NON_SINGLETON));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getArgFromResource_Singleton_Test() {
        getArgFromResource(Resource.SYSTEM_HEALTH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getArgFromResource_Notification_Test() {
        getArgFromResource(Resource.JOB_COMPLETED_NOTIFICATION_REGISTRATION);
    }

    @Test
    public void getArgFromResource_Test() {
        final Arguments bucketArg = getArgFromResource(Resource.BUCKET);
        assertThat(bucketArg.getName(), is("BucketName"));
        assertThat(bucketArg.getType(), is("String"));

        final Arguments jobChunkArg = getArgFromResource(Resource.JOB_CHUNK);
        assertThat(jobChunkArg.getName(), is("JobChunkId"));
        assertThat(jobChunkArg.getType(), is("UUID"));

        final Arguments tapePartitionArg = getArgFromResource(Resource.TAPE_PARTITION);
        assertThat(tapePartitionArg.getName(), is("TapePartition"));
        assertThat(tapePartitionArg.getType(), is("String"));

        final Arguments cacheFilesystemArg = getArgFromResource(Resource.CACHE_FILESYSTEM);
        assertThat(cacheFilesystemArg.getName(), is("CacheFilesystem"));
        assertThat(cacheFilesystemArg.getType(), is("String"));
    }

    @Test
    public void getRequiredArgsFromRequestHeader_Null_Test() {
        final Ds3Request ds3Request = new Ds3Request(
                "RequestName",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        final ImmutableList<Arguments> result = getRequiredArgsFromRequestHeader(ds3Request);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getRequiredArgsFromRequestHeader_Singleton_Test() {
        final Ds3Request ds3Request = new Ds3Request(
                "RequestName",
                null,
                null,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                Resource.CAPACITY_SUMMARY,
                ResourceType.SINGLETON,
                null,
                null,
                null,
                null
        );
        final ImmutableList<Arguments> result = getRequiredArgsFromRequestHeader(ds3Request);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(1).getType(), is("String"));
    }

    @Test
    public void getRequiredArgsFromRequestHeader_Notification_Test() {
        final Ds3Request ds3Request = new Ds3Request(
                "RequestName",
                null,
                null,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                Resource.GENERIC_DAO_NOTIFICATION_REGISTRATION,
                ResourceType.NON_SINGLETON,
                null,
                null,
                null,
                null
        );
        final ImmutableList<Arguments> result = getRequiredArgsFromRequestHeader(ds3Request);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(1).getType(), is("String"));
    }

    @Test
    public void getRequiredArgsFromRequestHeader_Full_Test() {
        final Ds3Request ds3Request = new Ds3Request(
                "RequestName",
                null,
                null,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                null,
                Resource.JOB,
                ResourceType.NON_SINGLETON,
                null,
                null,
                null,
                null
        );
        final ImmutableList<Arguments> result = getRequiredArgsFromRequestHeader(ds3Request);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(1).getType(), is("String"));
        assertThat(result.get(2).getName(), is("JobId"));
        assertThat(result.get(2).getType(), is("UUID"));
    }

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
