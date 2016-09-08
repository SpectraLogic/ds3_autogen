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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.models.QueryParam;
import com.spectralogic.ds3autogen.java.models.RequestConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.utils.CommonRequestGeneratorUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommonRequestGeneratorUtils_Test {

    @Test
    public void createInputStreamConstructor_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("Type1", "Arg1"));

        final RequestConstructor result = createInputStreamConstructor(args, argsToQueryParams(args), "", new Ds3DocSpecEmptyImpl());
        assertThat(result.getParameters().size(), is(2));
        assertThat(result.getParameters().get(0).getName(), is("Arg1"));
        assertThat(result.getParameters().get(1).getName(), is("Stream"));

        assertThat(result.getAssignments().size(), is(2));
        assertThat(result.getAssignments().get(0).getName(), is("Arg1"));
        assertThat(result.getAssignments().get(1).getName(), is("Stream"));

        assertThat(result.getQueryParams().size(), is(1));
        assertThat(result.getQueryParams().get(0).getName(), is("Arg1"));

        assertThat(result.getAdditionalLines().size(), is(0));
    }

    @Test
    public void createChannelConstructor_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("Type1", "Arg1"));

        final RequestConstructor result = createChannelConstructor(args, argsToQueryParams(args), "", new Ds3DocSpecEmptyImpl());
        assertThat(result.getParameters().size(), is(2));
        assertThat(result.getParameters().get(0).getName(), is("Arg1"));
        assertThat(result.getParameters().get(1).getName(), is("Channel"));

        assertThat(result.getAssignments().size(), is(2));
        assertThat(result.getAssignments().get(0).getName(), is("Arg1"));
        assertThat(result.getAssignments().get(1).getName(), is("Channel"));

        assertThat(result.getQueryParams().size(), is(1));
        assertThat(result.getQueryParams().get(0).getName(), is("Arg1"));

        assertThat(result.getAdditionalLines().size(), is(1));
        assertThat(result.getAdditionalLines().get(0), is("this.stream = new SeekableByteChannelInputStream(channel);"));
    }

    @Test
    public void argsToQueryParams_NullList_Test() {
        final ImmutableList<QueryParam> result = argsToQueryParams(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void argsToQueryParams_EmptyList_Test() {
        final ImmutableList<QueryParam> result = argsToQueryParams(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void argsToQueryParams_FullList_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("TypeOne", "NameOne"),
                new Arguments("TypeTwo", "NameTwo"));

        final ImmutableList<QueryParam> result = argsToQueryParams(args);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("NameOne"));
        assertThat(result.get(0).getType(), is("TypeOne"));
        assertThat(result.get(1).getName(), is("NameTwo"));
        assertThat(result.get(1).getType(), is("TypeTwo"));
    }
}
