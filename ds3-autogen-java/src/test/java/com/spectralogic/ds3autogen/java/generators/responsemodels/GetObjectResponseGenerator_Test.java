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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectResponseGenerator_Test {

    private static final GetObjectResponseGenerator generator = new GetObjectResponseGenerator();

    @Test
    public void getAllImports_Test() {
        final ImmutableSet<String> result = generator.getAllImports(null);
        assertThat(result.size(), is(3));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.interfaces.AbstractResponse"));
        assertThat(result, hasItem("com.spectralogic.ds3client.models.ChecksumType"));
        assertThat(result, hasItem("com.spectralogic.ds3client.networking.Metadata"));
    }

    @Test
    public void toConstructorParams_NullList_Test() {
        final String expected = "";
        assertThat(generator.toConstructorParams(null), is(expected));
    }

    @Test
    public void toConstructorParams_EmptyList_Test() {
        final String expected = "";
        assertThat(generator.toConstructorParams(ImmutableList.of()), is(expected));
    }

    @Test
    public void toConstructorParams_FullList_Test() {
        final String expected = "final Metadata metadata, final long objectSize";
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("Metadata", "Metadata"),
                new Arguments("long", "ObjectSize"));

        assertThat(generator.toConstructorParams(args), is(expected));
    }

    @Test
    public void toConstructorParamsWithChecksum_NullList_Test() {
        final String expected = "final String checksum, final ChecksumType.Type checksumType";
        assertThat(generator.toConstructorParamsWithChecksum(null), is(expected));
    }

    @Test
    public void toConstructorParamsWithChecksum_EmptyList_Test() {
        final String expected = "final String checksum, final ChecksumType.Type checksumType";
        assertThat(generator.toConstructorParamsWithChecksum(ImmutableList.of()), is(expected));
    }

    @Test
    public void toConstructorParamsWithChecksum_FullList_Test() {
        final String expected = "final Metadata metadata, final long objectSize, final String checksum, final ChecksumType.Type checksumType";
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("Metadata", "Metadata"),
                new Arguments("long", "ObjectSize"));

        assertThat(generator.toConstructorParamsWithChecksum(args), is(expected));
    }

    @Test
    public void toParamList_Test() {
        final ImmutableList<Arguments> result = generator.toParamList(null);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("Metadata"));
        assertThat(result.get(0).getType(), is("Metadata"));
        assertThat(result.get(1).getName(), is("ObjectSize"));
        assertThat(result.get(1).getType(), is("long"));
    }
}
