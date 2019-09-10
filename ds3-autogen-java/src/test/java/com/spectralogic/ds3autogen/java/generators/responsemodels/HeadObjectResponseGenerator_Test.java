/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

public class HeadObjectResponseGenerator_Test {

    private static final HeadObjectResponseGenerator generator = new HeadObjectResponseGenerator();

    @Test
    public void getAllImports_Test() {
        final ImmutableSet<String> result = generator.getAllImports(null);
        assertThat(result.size(), is(6));
        assertThat(result, hasItem("com.spectralogic.ds3client.networking.Metadata"));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.interfaces.AbstractResponse"));
        assertThat(result, hasItem("com.spectralogic.ds3client.models.ChecksumType"));
        assertThat(result, hasItem("com.google.common.collect.ImmutableMap"));
        assertThat(result, hasItem("java.time.ZonedDateTime"));
        assertThat(result, hasItem("java.util.UUID"));
    }

    @Test
    public void toParamList_Test() {
        final ImmutableList<Arguments> result = generator.toParamList(null);
        assertThat(result.size(), is(7));

        // Verify that parameters were sorted alphabetically by name
        assertThat(result.get(0).getName(), is("BlobChecksums"));
        assertThat(result.get(0).getType(), is("ImmutableMap<Long, String>"));

        assertThat(result.get(1).getName(), is("BlobChecksumType"));
        assertThat(result.get(1).getType(), is("ChecksumType.Type"));

        assertThat(result.get(2).getName(), is("CreationDate"));
        assertThat(result.get(2).getType(), is("ZonedDateTime"));

        assertThat(result.get(3).getName(), is("Metadata"));
        assertThat(result.get(3).getType(), is("Metadata"));

        assertThat(result.get(4).getName(), is("ObjectSize"));
        assertThat(result.get(4).getType(), is("long"));

        assertThat(result.get(5).getName(), is("Status"));
        assertThat(result.get(5).getType(), is("Status"));

        assertThat(result.get(6).getName(), is("VersionId"));
        assertThat(result.get(6).getType(), is("UUID"));
    }
}
