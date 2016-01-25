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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.test.helpers.BaseResponseGeneratorTestHelper.createPopulatedResponseCode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BulkResponseGenerator_Test {

    private final static BulkResponseGenerator generator = new BulkResponseGenerator();

    @Test
    public void getParentImport_Test() {
        assertThat(generator.getParentImport(), is("com.spectralogic.ds3client.commands.BulkResponse"));
    }

    @Test
    public void getAllImports_NullList_Test() {
        final ImmutableList<String> result = generator.getAllImports(null, "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_EmptyList_Test() {
        final ImmutableList<String> result = generator.getAllImports(
                ImmutableList.of(),
                "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_SpectraS3_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode(""),
                createPopulatedResponseCode("_v1"));

        final ImmutableList<String> result = generator.getAllImports(
                responseCodes,
                "com.spectralogic.ds3client.commands.spectrads3");
        assertThat(result.size(), is(1));
        assertTrue(result.contains("com.spectralogic.ds3client.commands.BulkResponse"));
    }

    @Test
    public void getAllImports_NonSpectraS3_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode(""),
                createPopulatedResponseCode("_v1"));

        final ImmutableList<String> result = generator.getAllImports(
                responseCodes,
                "com.spectralogic.ds3client.commands");
        assertThat(result.size(), is(0));
    }
}
