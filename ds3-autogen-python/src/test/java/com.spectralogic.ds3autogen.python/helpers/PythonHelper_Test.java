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

package com.spectralogic.ds3autogen.python.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.python.model.type.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PythonHelper_Test {

    @Test
    public void toRequiredArgInitList_NullList_Test() {
        final String result = toRequiredArgInitList(null);
        assertThat(result, is("self"));
    }

    @Test
    public void toRequiredArgInitList_EmptyList_Test() {
        final String result = toRequiredArgInitList(ImmutableList.of());
        assertThat(result, is("self"));
    }

    @Test
    public void toRequiredArgInitList_FullList_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("TypeOne", "ArgOne"),
                new Arguments("TypeTwo", "ArgTwo"));

        final String result = toRequiredArgInitList(args);
        assertThat(result, is("self, arg_one, arg_two"));
    }

    @Test
    public void toOptionalArgInitList_NullList_Test() {
        final String result = toOptionalArgInitList(null);
        assertThat(result, is(""));
    }

    @Test
    public void toOptionalArgInitList_EmptyList_Test() {
        final String result = toOptionalArgInitList(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void toOptionalArgInitList_FullList_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("TypeOne", "ArgOne"),
                new Arguments("TypeTwo", "ArgTwo"));

        final String result = toOptionalArgInitList(args);
        assertThat(result, is("arg_one=None, arg_two=None"));
    }

    @Test
    public void toRequestInitList_EmptyLists_Test() {
        assertThat(toRequestInitList(null, null), is("self"));
        assertThat(toRequestInitList(ImmutableList.of(), ImmutableList.of()), is("self"));
    }

    @Test
    public void toRequestInitList_Test() {
        final ImmutableList<Arguments> reqArgs = ImmutableList.of(
                new Arguments("TypeOne", "ArgOne"),
                new Arguments("TypeTwo", "ArgTwo"));

        final ImmutableList<Arguments> optArgs = ImmutableList.of(
                new Arguments("TypeThree", "ArgThree"),
                new Arguments("TypeFour", "ArgFour"));

        final String result = toRequestInitList(reqArgs, optArgs);
        assertThat(result, is("self, arg_one, arg_two, arg_three=None, arg_four=None"));
    }

    @Test
    public void pythonIndent_Test() {
        assertThat(pythonIndent(-1), is(""));
        assertThat(pythonIndent(0), is(""));
        assertThat(pythonIndent(1), is("  "));
        assertThat(pythonIndent(2), is("    "));
        assertThat(pythonIndent(3), is("      "));
    }

    @Test
    public void toTypeContentLines_NullList_Test() {
        final String result = toCommaSeparatedLines(null, 1);
        assertThat(result, is(""));
    }

    @Test
    public void toTypeContentLines_EmptyList_Test() {
        final String result = toCommaSeparatedLines(ImmutableList.of(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toTypeContentLines_Attribute_Test() {
        final String expected = "\n  Line1,\n  Line2\n";
        final ImmutableList<String> typeContents = ImmutableList.of("Line1", "Line2");

        final String result = toCommaSeparatedLines(typeContents, 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toCommaSeparatedList_NullList_Test() {
        final String result = toCommaSeparatedList(null);
        assertThat(result, is(""));
    }

    @Test
    public void toCommaSeparatedList_EmptyList_Test() {
        final String result = toCommaSeparatedList(null);
        assertThat(result, is(""));
    }

    @Test
    public void toCommaSeparatedList_Test() {
        final String expected = "1, 2, 3, 4";
        final ImmutableList<Integer> ints = ImmutableList.of(1, 2, 3, 4);
        final String result = toCommaSeparatedList(ints);
        assertThat(result, is(expected));
    }
}
