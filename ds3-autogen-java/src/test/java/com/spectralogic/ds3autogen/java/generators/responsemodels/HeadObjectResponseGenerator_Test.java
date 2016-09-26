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

public class HeadObjectResponseGenerator_Test {

    private static final HeadObjectResponseGenerator generator = new HeadObjectResponseGenerator();

    @Test
    public void getAllImports_Test() {
        final ImmutableSet<String> result = generator.getAllImports(null);
        assertThat(result.size(), is(2));
        assertThat(result, hasItem("com.spectralogic.ds3client.networking.Metadata"));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.interfaces.AbstractResponse"));
    }

    @Test
    public void toParamList_Test() {
        final ImmutableList<Arguments> result = generator.toParamList(null);
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("Metadata"));
        assertThat(result.get(0).getType(), is("Metadata"));
        assertThat(result.get(1).getName(), is("ObjectSize"));
        assertThat(result.get(1).getType(), is("long"));
        assertThat(result.get(2).getName(), is("Status"));
        assertThat(result.get(2).getType(), is("Status"));
    }
}
