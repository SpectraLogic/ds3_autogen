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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseRequestGenerator_Test {

    private final static BaseRequestGenerator generator = new BaseRequestGenerator();

    @Test
    public void toOptionalArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }
    @Test
    public void toOptionalArgumentsList_List_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ArgOne", "TypeOne", true),
                new Ds3Param("ArgTwo", "TypeTwo", false)
        );
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(params);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("ArgOne"));
        assertThat(result.get(0).getType(), is("TypeOne"));
        assertThat(result.get(1).getName(), is("ArgTwo"));
        assertThat(result.get(1).getType(), is("TypeTwo"));
    }

    @Test
    public void toRequiredArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = generator
                .toRequiredArgumentsList(createDs3RequestTestData(false, null, null));
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = generator
                .toRequiredArgumentsList(createDs3RequestTestData(false, ImmutableList.of(), ImmutableList.of()));
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredArgumentsList_ListWithVoidParam_Test() {
        final ImmutableList<Arguments> result = generator
                .toRequiredArgumentsList(exampleRequestWithOptionalAndRequiredBooleanQueryParam());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredArgumentsList_NotificationRequest_Test() {
        final ImmutableList<Arguments> result = generator
                .toRequiredArgumentsList(getRequestDeleteNotification());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("NotificationId"));
        assertThat(result.get(0).getType(), is("UUID"));
    }

    @Test
    public void toRequiredArgumentsList_Test() {
        final ImmutableList<Arguments> result = generator
                .toRequiredArgumentsList(getCompleteMultipartUploadRequest());
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("UploadId"));
        assertThat(result.get(1).getName(), is("BucketName"));
        assertThat(result.get(2).getName(), is("ObjectName"));
    }

    @Test
    public void toVoidArgumentsList_NullList_Test() {
        final ImmutableList<String> result = generator.toVoidArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toVoidArgumentsList_EmptyList_Test() {
        final ImmutableList<String> result = generator.toVoidArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toVoidArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ArgOne", "TypeOne", true),
                new Ds3Param("VoidArg", "void", false));

        final ImmutableList<String> result = generator.toVoidArgumentsList(params);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is("VoidArg"));
    }
}
