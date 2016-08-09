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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.Requirement;
import com.spectralogic.ds3autogen.api.models.enums.Resource;
import com.spectralogic.ds3autogen.api.models.enums.ResourceType;
import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class RequestConverterUtil_Test {

    @Test
    public void isResourceAnArg_Test() {
        assertFalse(isResourceAnArg(null, false));
        assertFalse(isResourceAnArg(null, true));
        assertFalse(isResourceAnArg(Resource.BUCKET, false));
        assertTrue(isResourceAnArg(Resource.BUCKET, true));
        assertFalse(isResourceAnArg(Resource.GENERIC_DAO_NOTIFICATION_REGISTRATION, true));
        assertFalse(isResourceAnArg(Resource.GENERIC_DAO_NOTIFICATION_REGISTRATION, false));
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
                false,
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
                false,
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
                false,
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
                null,
                null,
                true,
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
    public void toArgument_Test() {
        final Ds3Param simpleParam = new Ds3Param("ArgName", "SimpleType", false);
        final Arguments simpleArg = toArgument(simpleParam);
        assertThat(simpleArg.getName(), is("ArgName"));
        assertThat(simpleArg.getType(), is("SimpleType"));

        final Ds3Param pathParam = new Ds3Param("ArgName", "com.spectralogic.test.TypeWithPath", false);
        final Arguments pathArg = toArgument(pathParam);
        assertThat(pathArg.getName(), is("ArgName"));
        assertThat(pathArg.getType(), is("TypeWithPath"));
    }

    @Test
    public void getArgsFromParamList_NullList_Test() {
        final ImmutableList<Arguments> result = getArgsFromParamList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getArgsFromParamList_EmptyList_Test() {
        final ImmutableList<Arguments> result = getArgsFromParamList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getArgsFromParamList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("SimpleArg", "SimpleType", false),
                new Ds3Param("ArgWithPath", "com.test.TypeWithPath", false),
                new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false));

        final ImmutableList<Arguments> result = getArgsFromParamList(params);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("SimpleArg"));
        assertThat(result.get(0).getType(), is("SimpleType"));
        assertThat(result.get(1).getName(), is("ArgWithPath"));
        assertThat(result.get(1).getType(), is("TypeWithPath"));
    }

    @Test
    public void getNonVoidArgsFromParamList_NullList_Test() {
        final ImmutableList<Arguments> result = getNonVoidArgsFromParamList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getNonVoidArgsFromParamList_EmptyList_Test() {
        final ImmutableList<Arguments> result = getNonVoidArgsFromParamList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getNonVoidArgsFromParamList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("SimpleArg", "SimpleType", false),
                new Ds3Param("VoidArg", "void", false));

        final ImmutableList<Arguments> result = getNonVoidArgsFromParamList(params);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getType(), is(not("void")));
    }

    @Test
    public void getVoidArgsFromParamList_NullList_Test() {
        final ImmutableList<Arguments> result = getVoidArgsFromParamList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getVoidArgsFromParamList_EmptyList_Test() {
        final ImmutableList<Arguments> result = getVoidArgsFromParamList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getVoidArgsFromParamList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("SimpleArg", "SimpleType", false),
                new Ds3Param("VoidArg", "void", false));

        final ImmutableList<Arguments> result = getVoidArgsFromParamList(params);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getType(), is("void"));
    }
}
