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

package com.spectralogic.ds3autogen.net;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.NetHelper.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NetHelper_Test {

    @Test
    public void constructor_NullList_Test() {
        final String result = constructor(null);
        assertThat(result, is(""));
    }

    @Test
    public void constructor_EmptyList_Test() {
        final String result = constructor(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void constructor_FullList_Test() {
        final String expected = "int intArg, string stringArg";
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("int", "IntArg"),
                new Arguments("String", "StringArg"));
        final String result = constructor(args);
        assertThat(result, is(expected));
    }

    @Test
    public void getType_Test() {
        assertThat(getType(new Arguments(null, "ArgName")), is(""));
        assertThat(getType(new Arguments("", "ArgName")), is(""));
        assertThat(getType(new Arguments("void", "ArgName")), is("bool"));
        assertThat(getType(new Arguments("Void", "ArgName")), is("bool"));
        assertThat(getType(new Arguments("boolean", "ArgName")), is("bool"));
        assertThat(getType(new Arguments("Boolean", "ArgName")), is("bool"));
        assertThat(getType(new Arguments("Integer", "ArgName")), is("int"));
        assertThat(getType(new Arguments("int", "ArgName")), is("int"));
        assertThat(getType(new Arguments("String", "ArgName")), is("string"));
        assertThat(getType(new Arguments("string", "ArgName")), is("string"));
        assertThat(getType(new Arguments("UUID", "ArgName")), is("Guid"));
        assertThat(getType(new Arguments("ChecksumType", "ArgName")), is("ChecksumType.Type"));
        assertThat(getType(new Arguments("Date", "ArgName")), is("DateTime"));
        assertThat(getType(new Arguments("OtherType", "ArgName")), is("OtherType"));
    }

    @Test
    public void toNetType_Test() {
        assertThat(toNetType(null), is(""));
        assertThat(toNetType(""), is(""));
        assertThat(toNetType("void"), is("bool"));
        assertThat(toNetType("Void"), is("bool"));
        assertThat(toNetType("boolean"), is("bool"));
        assertThat(toNetType("Boolean"), is("bool"));
        assertThat(toNetType("Integer"), is("int"));
        assertThat(toNetType("int"), is("int"));
        assertThat(toNetType("String"), is("string"));
        assertThat(toNetType("string"), is("string"));
        assertThat(toNetType("UUID"), is("Guid"));
        assertThat(toNetType("ChecksumType"), is("ChecksumType.Type"));
        assertThat(toNetType("Date"), is("DateTime"));
        assertThat(toNetType("OtherType"), is("OtherType"));
        assertThat(toNetType("Long"), is("long"));
    }

    @Test
    public void argToString_Test() {
        assertThat(argToString(new Arguments(null, "ArgName")), is(""));
        assertThat(argToString(new Arguments("", "ArgName")), is(""));
        assertThat(argToString(new Arguments("void", "ArgName")), is("null"));
        assertThat(argToString(new Arguments("Void", "ArgName")), is("null"));
        assertThat(argToString(new Arguments("boolean", "ArgName")), is("null"));
        assertThat(argToString(new Arguments("Boolean", "ArgName")), is("null"));
        assertThat(argToString(new Arguments("Integer", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("int", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("Double", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("double", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("Long", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("long", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("String", "ArgName")), is("ArgName"));
        assertThat(argToString(new Arguments("string", "ArgName")), is("ArgName"));
        assertThat(argToString(new Arguments("UUID", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("ChecksumType", "ArgName")), is("ArgName.ToString()"));
        assertThat(argToString(new Arguments("OtherType", "ArgName")), is("ArgName.ToString()"));
    }

    @Test
    public void paramAssignmentRightValue_Test() {
        assertThat(paramAssignmentRightValue(new Arguments(null, null)), is(""));
        assertThat(paramAssignmentRightValue(new Arguments("", "")), is(""));
        assertThat(paramAssignmentRightValue(new Arguments("String", "ArgName")), is("argName"));
        assertThat(paramAssignmentRightValue(new Arguments("UUID", "ArgName")), is("argName.ToString()"));
        assertThat(paramAssignmentRightValue(new Arguments("int", "ArgName")), is("argName"));
        assertThat(paramAssignmentRightValue(new Arguments("IEnumerable<SomeArg>", "ArgName")), is("argName.ToList()"));
    }

    @Test
    public void getEnumValues_NullList_Test() {
        final String result = getEnumValues(null, 0);
        assertThat(result, is(""));
    }

    @Test
    public void getEnumValues_EmptyList_Test() {
        final String result = getEnumValues(ImmutableList.of(), 0);
        assertThat(result, is(""));
    }

    @Test
    public void getEnumValues_FullList_Test() {
        final String expected = "one,\ntwo,\nthree";

        final ImmutableList<EnumConstant> enumConstants = ImmutableList.of(
                new EnumConstant("one"),
                new EnumConstant("two"),
                new EnumConstant("three"));

        final String result = getEnumValues(enumConstants, 0);
        assertThat(result, is(expected));
    }

    @Test
    public void commaSeparateStrings_NullList_Test() {
        final String result = commaSeparateStrings(null, 0);
        assertThat(result, is(""));
    }

    @Test
    public void commaSeparateStrings_EmptyList_Test() {
        final String result = commaSeparateStrings(ImmutableList.of(), 0);
        assertThat(result, is(""));
    }

    @Test
    public void commaSeparateStrings_FullList_Test() {
        final String expected = "One,\n    Two,\n    Three";
        final ImmutableList<String> input = ImmutableList.of("One", "Two", "Three");
        final String result = commaSeparateStrings(input, 1);
        assertThat(result, is(expected));
    }
}
