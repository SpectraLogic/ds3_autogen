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

package com.spectralogic.ds3autogen.java.generators.responseparser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.java.models.ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestAmazonS3GetObject;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectParserGenerator_Test {

    private static final GetObjectParserGenerator generator = new GetObjectParserGenerator();

    @Test
    public void toImportList_Test() {
        final Ds3Request request = getRequestAmazonS3GetObject();
        final ImmutableSet<String> result = generator.toImportList(
                "GetObjectResponse",
                request,
                request.getDs3ResponseCodes());

        assertThat(result.size(), is(12));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.GetObjectResponse"));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.interfaces.MetadataImpl"));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser"));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils"));
        assertThat(result, hasItem("com.spectralogic.ds3client.exceptions.ContentLengthNotMatchException"));
        assertThat(result, hasItem("com.spectralogic.ds3client.networking.Metadata"));
        assertThat(result, hasItem("com.spectralogic.ds3client.networking.WebResponse"));
        assertThat(result, hasItem("com.spectralogic.ds3client.utils.IOUtils"));
        assertThat(result, hasItem("com.spectralogic.ds3client.utils.PerformanceUtils"));
        assertThat(result, hasItem("java.io.IOException"));
        assertThat(result, hasItem("java.io.InputStream"));
        assertThat(result, hasItem("java.nio.channels.WritableByteChannel"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toResponseCodeList_Exception_Test() {
        generator.toResponseCodeList(ImmutableList.of(), "TestResponse", false);
    }

    @Test
    public void toResponseCodeList_Test() {
        final ImmutableList<Ds3ResponseCode> ds3ResponseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(206, null));

        final ImmutableList<ResponseCode> result = generator.toResponseCodeList(ds3ResponseCodes, "TestResponse", false);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(1).getCode(), is(206));
    }
}
