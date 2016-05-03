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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import com.spectralogic.ds3autogen.net.model.request.RequestConstructor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.requestmodels.BaseRequestGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getAllocateJobChunkRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestMultiFileDelete;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3EnumConstantList;
import static com.spectralogic.ds3autogen.utils.ArgumentsUtil.containsType;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseRequestGenerator_Test {

    private static final BaseRequestGenerator generator = new BaseRequestGenerator();

    @Test
    public void toRequiredArgumentsList_Test() {
        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(getRequestMultiFileDelete());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("Delete"));
    }

    @Test
    public void toConstructorArgsList_Test() {
        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(getRequestMultiFileDelete());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("Delete"));
    }

    @Test
    public void toOptionalArgumentsList_NullList_Test() {
        final ImmutableList<NetNullableVariable> result = generator.toOptionalArgumentsList(null, null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_EmptyList_Test() {
        final ImmutableList<NetNullableVariable> result = generator.toOptionalArgumentsList(ImmutableList.of(), ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("SimpleArg", "SimpleType", false),
                new Ds3Param("ArgWithPath", "com.test.TypeWithPath", false));

        final ImmutableList<NetNullableVariable> result = generator.toOptionalArgumentsList(params, ImmutableMap.of());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("SimpleArg"));
        assertThat(result.get(0).getNetType(), is("SimpleType"));
        assertThat(result.get(1).getName(), is("ArgWithPath"));
        assertThat(result.get(1).getNetType(), is("TypeWithPath"));
    }

    @Test
    public void toQueryParamsList_Test() {
        final ImmutableList<Arguments> result = generator.toQueryParamsList(getRequestMultiFileDelete());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Delete"));
        assertThat(result.get(0).getType(), is("void"));
    }

    @Test
    public void toNullableArgument_NullablePrimitive_Test() {
        final Ds3Param param = new Ds3Param("MyParam", "java.lang.Integer", true);
        final NetNullableVariable result = toNullableArgument(param, ImmutableMap.of());
        assertThat(result.getNetType(), is("int?"));
    }

    @Test
    public void toNullableArgument_NonNullablePrimitive_Test() {
        final Ds3Param param = new Ds3Param("MyParam", "java.lang.Integer", false);
        final NetNullableVariable result = toNullableArgument(param, ImmutableMap.of());
        assertThat(result.getNetType(), is("int"));
    }

    @Test
    public void toNullableArgument_NullableSpectraType_Test() {
        final Ds3Param param = new Ds3Param("MyParam", "com.spectra.TestType", true);
        final NetNullableVariable result = toNullableArgument(param, ImmutableMap.of());
        assertThat(result.getNetType(), is("TestType"));
    }

    @Test
    public void toNullableArgument_NullableEnumType_Test() {
        final Ds3Type testType = new Ds3Type("com.spectra.TestType", "", null, createEmptyDs3EnumConstantList());
        final ImmutableMap<String, Ds3Type> testTypeMap = ImmutableMap.of(testType.getName(), testType);

        final Ds3Param param = new Ds3Param("MyParam", testType.getName(), true);
        final NetNullableVariable result = toNullableArgument(param, testTypeMap);
        assertThat(result.getNetType(), is("TestType?"));
    }

    @Test
    public void convertUuidToString_NullList_Test() {
        final ImmutableList<Arguments> result = convertUuidToString(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertUuidToString_EmptyList_Test() {
        final ImmutableList<Arguments> result = convertUuidToString(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertUuidToString_FullList_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("UUID", "UuidArg"),
                new Arguments("String", "StringArg"),
                new Arguments("int", "IntArg"));

        final ImmutableList<Arguments> result = convertUuidToString(args);
        assertThat(result.size(), is(3));

        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getType(), is("String"));
        assertThat(result.get(2).getType(), is("int"));
    }

    @Test
    public void convertGuidToStringConstructor_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("UUID", "UuidArg"),
                new Arguments("int", "IntArg"));

        final RequestConstructor constructor = new RequestConstructor(args, args, Operation.ALLOCATE);
        final RequestConstructor result = convertGuidToStringConstructor(constructor);

        assertFalse(containsType(result.getConstructorArgs(), "UUID"));
        assertTrue(containsType(result.getConstructorArgs(), "String"));
        assertTrue(containsType(result.getConstructorArgs(), "int"));

        assertFalse(containsType(result.getQueryParams(), "UUID"));
        assertTrue(containsType(result.getQueryParams(), "String"));
        assertTrue(containsType(result.getQueryParams(), "int"));
    }

    @Test
    public void splitGuidConstructor_WithUuid_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(new Arguments("UUID", "UuidArg"));

        final RequestConstructor constructor = new RequestConstructor(args, args, Operation.ALLOCATE);

        final ImmutableList<RequestConstructor> result = splitGuidConstructor(constructor);
        assertThat(result.size(), is(2));
        assertTrue(containsType(result.get(0).getConstructorArgs(), "UUID"));
        assertFalse(containsType(result.get(1).getConstructorArgs(), "UUID"));
        assertTrue(containsType(result.get(1).getConstructorArgs(), "String"));
    }

    @Test
    public void splitGuidConstructor_WithOutUuid_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(new Arguments("int", "IntArg"));

        final RequestConstructor constructor = new RequestConstructor(args, args, Operation.ALLOCATE);

        final ImmutableList<RequestConstructor> result = splitGuidConstructor(constructor);
        assertThat(result.size(), is(1));
        assertTrue(containsType(result.get(0).getConstructorArgs(), "int"));
    }

    @Test
    public void toConstructorList_Test() {
        final ImmutableList<RequestConstructor> result = generator.toConstructorList(getAllocateJobChunkRequest());
        assertThat(result.size(), is(2));
        assertTrue(containsType(result.get(0).getConstructorArgs(), "UUID"));
        assertFalse(containsType(result.get(1).getConstructorArgs(), "UUID"));
    }
}
