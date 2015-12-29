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
import com.spectralogic.ds3autogen.api.models.Action;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class Helper_Test {
    @Test
    public void camelCaseToUnderscore_Test() {
        assertEquals(Helper.camelToUnderscore("BumpyCaseWord"), "bumpy_case_word");
    }

    @Test
    public void removeTrailingRequestHandler_Test() {
        assertEquals(Helper.removeTrailingRequestHandler("SomeRequestHandler"), "Some");
    }

    @Test
    public void underscoreToCamel_Test() {
        assertEquals(Helper.underscoreToCamel("DEGRADED_DATA_PERSISTENCE_RULE"), "DegradedDataPersistenceRule");
    }

    @Test
    public void removeTrailingRequestHandlerWithDollarSign_Test() {
        assertEquals(Helper.removeTrailingRequestHandler("SomeRequestHandler$SomeExtensionApiBeanBlah"), "Some");
    }

    @Test
    public void unqualifiedName_Test() {
        assertEquals(Helper.unqualifiedName("some.qualified.name"), "name");
    }

    @Test
    public void getHttpVerb() {
        assertThat(Helper.getHttpVerb(HttpVerb.DELETE, null), is("DELETE"));
        assertThat(Helper.getHttpVerb(HttpVerb.DELETE, Action.CREATE), is("DELETE"));
        assertThat(Helper.getHttpVerb(null, Action.BULK_MODIFY), is("PUT"));
        assertThat(Helper.getHttpVerb(null, Action.CREATE), is("PUT"));
        assertThat(Helper.getHttpVerb(null, Action.DELETE), is("DELETE"));
        assertThat(Helper.getHttpVerb(null, Action.LIST), is("GET"));
        assertThat(Helper.getHttpVerb(null, Action.MODIFY), is("PUT"));
        assertThat(Helper.getHttpVerb(null, Action.SHOW), is("GET"));
        assertThat(Helper.getHttpVerb(null, Action.BULK_DELETE), is("DELETE"));

    }

    @Test
    public void sortConstructorArgs() {
        final ImmutableList<Arguments> expectedResult = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"),
                new Arguments("Type3", "Arg3"));
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type2", "Arg2"),
                new Arguments("String", "ObjectName"),
                new Arguments("Type1", "Arg1"),
                new Arguments("Type3", "Arg3"),
                new Arguments("String", "BucketName"));
        final ImmutableList<Arguments> result = Helper.sortConstructorArgs(arguments);
        for (int i = 0; i < arguments.size(); i++) {
            assertTrue(result.get(i).getName().equals(expectedResult.get(i).getName()));
        }
    }

    @Test
    public void containsArgument_NullList_Test() {
        assertFalse(Helper.containsArgument(null, "ArgName"));
        assertFalse(Helper.containsArgument(null, ""));
        assertFalse(Helper.containsArgument(null, null));
    }

    @Test
    public void containsArgument_EmptyList_Test() {
        assertFalse(Helper.containsArgument(ImmutableList.of(), "ArgName"));
        assertFalse(Helper.containsArgument(ImmutableList.of(), ""));
        assertFalse(Helper.containsArgument(ImmutableList.of(), null));
    }

    @Test
    public void containsArgument_FullList_Test() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"));
        assertTrue(Helper.containsArgument(arguments, "Arg1"));
        assertTrue(Helper.containsArgument(arguments, "Arg2"));

        assertFalse(Helper.containsArgument(arguments, "Arg3"));
        assertFalse(Helper.containsArgument(arguments, ""));
        assertFalse(Helper.containsArgument(arguments, null));
    }

    @Test
    public void addArgumentListsNull() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"));

        final ImmutableList<Arguments> resultAddLists1 = Helper.addArgument(arguments, null);
        assertThat(resultAddLists1.size(), CoreMatchers.is(2));
        assertTrue(Helper.containsArgument(resultAddLists1, "Arg1"));
        assertTrue(Helper.containsArgument(resultAddLists1, "Arg2"));

        final ImmutableList<Arguments> resultAddLists2 = Helper.addArgument(null, arguments);
        assertThat(resultAddLists2.size(), CoreMatchers.is(2));
        assertTrue(Helper.containsArgument(resultAddLists2, "Arg1"));
        assertTrue(Helper.containsArgument(resultAddLists2, "Arg2"));

        final ImmutableList<Arguments> resultAddLists3 = Helper.addArgument(null, null);
        assertThat(resultAddLists3.size(), CoreMatchers.is(0));
    }

    @Test
    public void addArgumentListsEmpty() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"));

        final ImmutableList<Arguments> resultAddLists1 = Helper.addArgument(arguments, ImmutableList.of());
        assertThat(resultAddLists1.size(), CoreMatchers.is(2));
        assertTrue(Helper.containsArgument(resultAddLists1, "Arg1"));
        assertTrue(Helper.containsArgument(resultAddLists1, "Arg2"));

        final ImmutableList<Arguments> resultAddLists2 = Helper.addArgument(ImmutableList.of(), arguments);
        assertThat(resultAddLists2.size(), CoreMatchers.is(2));
        assertTrue(Helper.containsArgument(resultAddLists2, "Arg1"));
        assertTrue(Helper.containsArgument(resultAddLists2, "Arg2"));

        final ImmutableList<Arguments> resultAddLists3 = Helper.addArgument(ImmutableList.of(), ImmutableList.of());
        assertThat(resultAddLists3.size(), CoreMatchers.is(0));
    }

    @Test
    public void addArgumentElementNullOrEmpty() {
        final ImmutableList<Arguments> resultAddElementNull = Helper.addArgument(null, "ArgName", "ArgType");
        assertThat(resultAddElementNull.size(), CoreMatchers.is(1));
        assertTrue(Helper.containsArgument(resultAddElementNull, "ArgName"));

        final ImmutableList<Arguments> resultAddElementEmpty = Helper.addArgument(ImmutableList.of(), "ArgName", "ArgType");
        assertThat(resultAddElementEmpty.size(), CoreMatchers.is(1));
        assertTrue(Helper.containsArgument(resultAddElementEmpty, "ArgName"));
    }

    @Test
    public void addArgumentFull() {
        final ImmutableList<Arguments> arguments1 = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"));
        final ImmutableList<Arguments> arguments2 = ImmutableList.of(
                new Arguments("Type3", "Arg3"),
                new Arguments("Type4", "Arg4"));
        final ImmutableList<Arguments> resultAddLists = Helper.addArgument(arguments1, arguments2);
        assertTrue(Helper.containsArgument(resultAddLists, "Arg1"));
        assertTrue(Helper.containsArgument(resultAddLists, "Arg2"));
        assertTrue(Helper.containsArgument(resultAddLists, "Arg3"));
        assertTrue(Helper.containsArgument(resultAddLists, "Arg4"));

        final ImmutableList<Arguments> resultAddSingle = Helper.addArgument(arguments1, "Arg5", "Type5");
        assertTrue(Helper.containsArgument(resultAddSingle, "Arg1"));
        assertTrue(Helper.containsArgument(resultAddSingle, "Arg2"));
        assertTrue(Helper.containsArgument(resultAddSingle, "Arg5"));
    }

    @Test
    public void removeArgument() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("Type1", "Arg1"),
                new Arguments("Type2", "Arg2"),
                new Arguments("Type3", "Arg3"));
        final ImmutableList<Arguments> result = Helper.removeArgument(arguments, "Arg2");

        assertFalse(Helper.containsArgument(result, "Arg2"));
        assertTrue(Helper.containsArgument(result, "Arg1"));
        assertTrue(Helper.containsArgument(result, "Arg3"));
    }

    @Test
    public void getResponseCodes() {
        final String expectedResult = "200, 206, 307, 400";
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(307, null),
                new Ds3ResponseCode(206, null),
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(400, null));

        final String result = Helper.getResponseCodes(responseCodes);
        assertThat(result, CoreMatchers.is(expectedResult));
    }

    @Test
    public void stripPath() {
        final String expectedResult = "BlobApiBean";
        final String result = Helper.stripPath("com.spectralogic.s3.common.platform.domain.BlobApiBean");
        assertThat(result, CoreMatchers.is(expectedResult));

        final String result2 = Helper.stripPath("BlobApiBean");
        assertThat(result2, CoreMatchers.is(expectedResult));
    }

    @Test
    public void addVoidArgument() {
        final Arguments voidArg = new Arguments("void", "ArgName");
        assertTrue(Helper.addVoidArgument(voidArg, Helper.SelectRemoveVoidType.SELECT_VOID));
        assertFalse(Helper.addVoidArgument(voidArg, Helper.SelectRemoveVoidType.REMOVE_VOID));

        final Arguments intArg = new Arguments("int", "ArgName");
        assertFalse(Helper.addVoidArgument(intArg, Helper.SelectRemoveVoidType.SELECT_VOID));
        assertTrue(Helper.addVoidArgument(intArg, Helper.SelectRemoveVoidType.REMOVE_VOID));
    }

    @Test
    public void adjustVoidArguments() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("void", "VoidArg1"),
                new Arguments("void", "VoidArg2"),
                new Arguments("int", "IntArg"),
                new Arguments("double", "DoubleArg"));

        final ImmutableList<Arguments> voidArgs = Helper
                .selectOrRemoveVoidArguments(arguments, Helper.SelectRemoveVoidType.SELECT_VOID);
        assertThat(voidArgs.size(), CoreMatchers.is(2));
        assertTrue(voidArgs.get(0).getType().equals("void"));
        assertTrue(voidArgs.get(1).getType().equals("void"));

        final ImmutableList<Arguments> nonVoidArgs = Helper
                .selectOrRemoveVoidArguments(arguments, Helper.SelectRemoveVoidType.REMOVE_VOID);
        assertThat(nonVoidArgs.size(), CoreMatchers.is(2));
        assertFalse(nonVoidArgs.get(0).getType().equals("void"));
        assertFalse(nonVoidArgs.get(1).getType().equals("void"));
    }

    @Test
    public void getVoidArguments() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("void", "VoidArg1"),
                new Arguments("void", "VoidArg2"),
                new Arguments("int", "IntArg"),
                new Arguments("double", "DoubleArg"));

        final ImmutableList<Arguments> result = Helper.getVoidArguments(arguments);
        assertThat(result.size(), CoreMatchers.is(2));
        assertTrue(result.get(0).getType().equals("void"));
        assertTrue(result.get(1).getType().equals("void"));
    }

    @Test
    public void removeVoidArguments() {
        final ImmutableList<Arguments> arguments = ImmutableList.of(
                new Arguments("void", "VoidArg1"),
                new Arguments("void", "VoidArg2"),
                new Arguments("int", "IntArg"),
                new Arguments("double", "DoubleArg"));

        final ImmutableList<Arguments> result = Helper.removeVoidArguments(arguments);
        assertThat(result.size(), CoreMatchers.is(2));
        assertFalse(result.get(0).getType().equals("void"));
        assertFalse(result.get(1).getType().equals("void"));
    }
}
