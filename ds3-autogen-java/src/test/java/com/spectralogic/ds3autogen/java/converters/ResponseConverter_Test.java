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
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.converters.ResponseConverter.*;
import static com.spectralogic.ds3autogen.java.test.helpers.ResponseConverterTestHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ResponseConverter_Test {

    @Test
    public void getImportFromResponseCode_Test() {
        final Ds3ResponseCode responseCode = createPopulatedResponseCode("");
        final String result = getImportFromResponseCode(responseCode);
        assertThat(result, is("com.spectralogic.Test.Type"));
    }

    @Test
    public void getImportFromResponseCode_NullReturnType_Test() {
        final Ds3ResponseCode responseCode = createPopulatedNullResponseCode();
        final String result = getImportFromResponseCode(responseCode);
        assertThat(result, is(""));
    }

    @Test
    public void getImportFromResponseCode_ErrorResponseCode_Test() {
        final Ds3ResponseCode responseCode = createPopulatedErrorResponseCode("");
        final String result = getImportFromResponseCode(responseCode);
        assertThat(result, is(""));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getImportFromResponseCode_ErrorComponentType_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(
                400,
                ImmutableList.of(
                        new Ds3ResponseType("array", "com.spectralogic.Test.Type")));
        getImportFromResponseCode(responseCode);
    }

    @Test
    public void getAllImports_NullList_Test() {
        final ImmutableList<String> result = getAllImports(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_EmptyList_Test() {
        final ImmutableList<String> result = getAllImports(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode("_v1"),
                createPopulatedResponseCode("_v2"),
                createPopulatedErrorResponseCode("_v3"));

        final ImmutableList<String> result = getAllImports(responseCodes);
        assertThat(result.size(), is(3));
        assertTrue(result.contains("com.spectralogic.Test.Type_v1"));
        assertTrue(result.contains("com.spectralogic.Test.Type_v2"));
        assertTrue(result.contains("com.spectralogic.ds3client.serializer.XmlOutput"));
    }

    @Test
    public void toResponseName_Test() {
        final String result = toResponseName("com.spectralogic.s3.server.handler.reqhandler.amazons3.HeadObjectRequest");
        assertThat(result, is("HeadObjectResponse"));
    }
}
