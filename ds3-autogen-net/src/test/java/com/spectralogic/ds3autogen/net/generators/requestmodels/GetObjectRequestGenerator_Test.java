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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestAmazonS3GetObject;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestSpectraS3GetObject;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectRequestGenerator_Test {

    private static final GetObjectRequestGenerator generator = new GetObjectRequestGenerator();

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
    public void toOptionalArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("SimpleArg", "SimpleType"),
                new Ds3Param("ArgWithPath", "com.test.TypeWithPath"));

        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(params);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredArgumentsList_AmazonS3_Test() {
        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(getRequestAmazonS3GetObject());
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("Job"));
        assertThat(result.get(3).getName(), is("Offset"));
    }

    @Test
    public void toRequiredArgumentsList_SpectraS3_Test() {
        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(getRequestSpectraS3GetObject());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("ObjectName"));
        assertThat(result.get(1).getName(), is("BucketId"));
    }

    @Test
    public void toConstructorArgsList_AmazonS3_Test() {
        final ImmutableList<Arguments> result = generator.toConstructorArgsList(getRequestAmazonS3GetObject());
        assertThat(result.size(), is(5));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
        assertThat(result.get(2).getName(), is("Job"));
        assertThat(result.get(3).getName(), is("Offset"));
        assertThat(result.get(4).getName(), is("DestinationStream"));
        assertThat(result.get(4).getType(), is("Stream"));
    }

    @Test
    public void toConstructorArgsList_SpectraS3_Test() {
        final ImmutableList<Arguments> result = generator.toConstructorArgsList(getRequestSpectraS3GetObject());
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("ObjectName"));
        assertThat(result.get(1).getName(), is("BucketId"));
        assertThat(result.get(2).getName(), is("DestinationStream"));
        assertThat(result.get(2).getType(), is("Stream"));
    }
}
