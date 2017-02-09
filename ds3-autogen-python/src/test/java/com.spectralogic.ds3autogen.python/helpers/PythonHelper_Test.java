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

package com.spectralogic.ds3autogen.python.helpers;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PythonHelper_Test {

    @Test
    public void toRequiredArgInitList_NullList_Test() {
        final String result = toRequestInitList(null);
        assertThat(result, is("self"));
    }

    @Test
    public void toRequiredArgInitList_EmptyList_Test() {
        final String result = toRequestInitList(ImmutableList.of());
        assertThat(result, is("self"));
    }

    @Test
    public void toRequiredArgInitList_FullList_Test() {
        final ImmutableList<String> strings = ImmutableList.of("one", "two", "three");
        final String result = toRequestInitList(strings);
        assertThat(result, is("self, one, two, three"));
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
