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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import org.junit.Test;

import static com.spectralogic.ds3autogen.converters.NameConverter.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class NameConverter_Test {

    @Test
    public void stripHandlerFromName_Test() {
        assertThat(NameConverter.stripHandlerFromName("com.spectralogic.ds3client.commands.GetBucketRequestHandler"),
                is("com.spectralogic.ds3client.commands.GetBucketRequest"));

        assertThat(NameConverter.stripHandlerFromName("com.spectralogic.ds3client.commands.GetHandlerBucketRequest"),
                is("com.spectralogic.ds3client.commands.GetHandlerBucketRequest"));

        assertThat(NameConverter.stripHandlerFromName(null), is(nullValue()));
        assertThat(NameConverter.stripHandlerFromName(""), is(nullValue()));
    }

    @Test
    public void toUpdatedDs3RequestName_SpectraS3_Test() {
        final String result = toUpdatedDs3RequestName(
                "com.spectralogic.test.MyTestRequestHandler",
                Classification.spectrads3);
        assertThat(result, is("com.spectralogic.test.MyTestSpectraS3Request"));
    }

    @Test
    public void toUpdatedDs3RequestName_AmazonS3_Test() {
        final String result = toUpdatedDs3RequestName(
                "com.spectralogic.test.MyTestRequestHandler",
                Classification.amazons3);
        assertThat(result, is("com.spectralogic.test.MyTestRequest"));
    }

    @Test
    public void toUpdatedDs3Request_SpectraS3_Test() {
        final Ds3Request request = new Ds3Request(
                "com.spectralogic.test.MyTestRequestHandler",
                null,
                Classification.spectrads3,
                null, null, null, null, null, null, false, null, null, null);
        final Ds3Request result = toUpdatedDs3Request(request);
        assertThat(result.getName(), is("com.spectralogic.test.MyTestSpectraS3Request"));
    }

    @Test
    public void toUpdatedDs3Request_AmazonS3_Test() {
        final Ds3Request request = new Ds3Request(
                "com.spectralogic.test.MyTestRequestHandler",
                null,
                Classification.amazons3,
                null, null, null, null, null, null, false, null, null, null);
        final Ds3Request result = toUpdatedDs3Request(request);
        assertThat(result.getName(), is("com.spectralogic.test.MyTestRequest"));
    }

    @Test
    public void renameRequests_NullList_Test() {
        final Ds3ApiSpec result = renameRequests(new Ds3ApiSpec(null, null));
        assertThat(result.getRequests(), is(nullValue()));
    }

    @Test
    public void renameRequests_EmptyList_Test() {
        final Ds3ApiSpec result = renameRequests(new Ds3ApiSpec(ImmutableList.of(), null));
        assertThat(result.getRequests().size(), is(0));
    }

    @Test
    public void renameRequests_FullList_Test() {
        final Ds3ApiSpec spec = new Ds3ApiSpec(
                ImmutableList.of(
                        new Ds3Request(
                                "com.spectralogic.test.MyTestOneRequestHandler",
                                null,
                                Classification.spectrads3,
                                null, null, null, null, null, null, false, null, null, null),
                        new Ds3Request(
                                "com.spectralogic.test.MyTestTwoRequestHandler",
                                null,
                                Classification.amazons3,
                                null, null, null, null, null, null, false, null, null, null)),
                null);
        final Ds3ApiSpec result = renameRequests(spec);
        assertThat(result.getRequests().size(), is(2));
        assertThat(result.getRequests().get(0).getName(), is("com.spectralogic.test.MyTestOneSpectraS3Request"));
        assertThat(result.getRequests().get(1).getName(), is("com.spectralogic.test.MyTestTwoRequest"));
    }

    @Test
    public void changeCreateToPut_Test() {
        assertThat(changeCreateToPut(null), is(nullValue()));
        assertThat(changeCreateToPut(""), is(nullValue()));
        assertThat(changeCreateToPut("SimpleRequest"), is("SimpleRequest"));
        assertThat(changeCreateToPut("com.spectra.RequestWithPath"), is("com.spectra.RequestWithPath"));
        assertThat(changeCreateToPut("CreateSimpleRequest"), is("PutSimpleRequest"));
        assertThat(changeCreateToPut("com.spectra.CreateRequestWithPath"), is("com.spectra.PutRequestWithPath"));
        assertThat(changeCreateToPut("com.spectra.NotCreateRequest"), is("com.spectra.NotCreateRequest"));
    }

    @Test
    public void updateName_Test() {
        assertThat(updateName(null), is(nullValue()));
        assertThat(updateName(""), is(nullValue()));
        assertThat(updateName("SimpleRequestHandler"), is("SimpleRequest"));
        assertThat(updateName("com.spectra.RequestWithPathHandler"), is("com.spectra.RequestWithPath"));
        assertThat(updateName("CreateSimpleRequestHandler"), is("PutSimpleRequest"));
        assertThat(updateName("com.spectra.CreateRequestWithPathHandler"), is("com.spectra.PutRequestWithPath"));
        assertThat(updateName("com.spectra.NotCreateRequestHandler"), is("com.spectra.NotCreateRequest"));
    }
}
