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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.containsType;
import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.modifyType;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.getAllArgumentTypes;
import static org.junit.Assert.assertTrue;

public class ArgumentsUtil_Test {

    @Test
    public void getAllArgumentTypes_NullList_Test() {
        final ImmutableSet<String> result = getAllArgumentTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllArgumentTypes_EmptyList_Test() {
        final ImmutableSet<String> result = getAllArgumentTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllArgumentTypes_FullList_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("String", "StringArg"),
                new Arguments("int", "IntArg"),
                new Arguments("String", "StringArg2"),
                new Arguments("Integer", "IntegerArg"),
                new Arguments("boolean", "BooleanArg"));

        final ImmutableSet<String> result = getAllArgumentTypes(args);
        assertThat(result.size(), is(4));
        assertThat(result, hasItem("String"));
        assertThat(result, hasItem("int"));
        assertThat(result, hasItem("Integer"));
        assertThat(result, hasItem("boolean"));
    }

    @Test
    public void containsType_NullList_Test() {
        assertFalse(containsType(null, "Type"));
    }

    @Test
    public void containsType_EmptyList_Test() {
        assertFalse(containsType(ImmutableList.of(), "Type"));
    }

    @Test
    public void containsType_FullList_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("String", "StringArg"),
                new Arguments("int", "IntArg"),
                new Arguments("boolean", "BooleanArg"));

        assertTrue(containsType(args, "String"));
        assertTrue(containsType(args, "int"));
        assertTrue(containsType(args, "boolean"));

        assertFalse(containsType(args, null));
        assertFalse(containsType(args, ""));
        assertFalse(containsType(args, "double"));
    }

    @Test
    public void modifyType_NullList_Test() {
        final ImmutableList<Arguments> result = modifyType(null, "Type1", "Type2");
        assertThat(result.size(), is(0));
    }

    @Test
    public void modifyType_EmptyList_Test() {
        final ImmutableList<Arguments> result = modifyType(ImmutableList.of(), "Type1", "Type2");
        assertThat(result.size(), is(0));
    }

    @Test
    public void modifyType_FullList_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("Integer", "IntegerArg"),
                new Arguments("UUID", "UuidArg"));

        final ImmutableList<Arguments> result = modifyType(args, "UUID", "String");
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("IntegerArg"));
        assertThat(result.get(0).getType(), is("Integer"));
        assertThat(result.get(1).getName(), is("UuidArg"));
        assertThat(result.get(1).getType(), is("String"));
    }
}
