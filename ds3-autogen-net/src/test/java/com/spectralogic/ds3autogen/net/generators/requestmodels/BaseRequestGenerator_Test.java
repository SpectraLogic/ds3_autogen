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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import com.spectralogic.ds3autogen.net.model.request.RequestConstructor;
import com.spectralogic.ds3autogen.net.model.request.WithConstructorVariable;
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

    private static Ds3DocSpec getTestDocSpec() {
        return new Ds3DocSpecImpl(ImmutableMap.of(), ImmutableMap.of());
    }

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
        //Note: optional parameters are always nullable
        final Ds3Param param = new Ds3Param("MyParam", "java.lang.Integer", false);
        final NetNullableVariable result = toNullableArgument(param, ImmutableMap.of());
        assertThat(result.getNetType(), is("int?"));
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

        final RequestConstructor constructor = new RequestConstructor(args, args, Operation.ALLOCATE, "My Documentation");
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

        final RequestConstructor constructor = new RequestConstructor(args, args, Operation.ALLOCATE, "My Documentation");

        final ImmutableList<RequestConstructor> result = splitGuidConstructor(constructor);
        assertThat(result.size(), is(2));
        assertTrue(containsType(result.get(0).getConstructorArgs(), "UUID"));
        assertFalse(containsType(result.get(1).getConstructorArgs(), "UUID"));
        assertTrue(containsType(result.get(1).getConstructorArgs(), "String"));
    }

    @Test
    public void splitGuidConstructor_WithOutUuid_Test() {
        final ImmutableList<Arguments> args = ImmutableList.of(new Arguments("int", "IntArg"));

        final RequestConstructor constructor = new RequestConstructor(args, args, Operation.ALLOCATE, "My Documentation");

        final ImmutableList<RequestConstructor> result = splitGuidConstructor(constructor);
        assertThat(result.size(), is(1));
        assertTrue(containsType(result.get(0).getConstructorArgs(), "int"));
    }

    @Test
    public void toConstructorList_Test() {
        final ImmutableList<RequestConstructor> result = generator.toConstructorList(getAllocateJobChunkRequest(), "MyRequest", getTestDocSpec());
        assertThat(result.size(), is(2));
        assertTrue(containsType(result.get(0).getConstructorArgs(), "UUID"));
        assertFalse(containsType(result.get(1).getConstructorArgs(), "UUID"));
    }

    @Test
    public void convertGuidToString_Test() {
        final NetNullableVariable guidVar = new NetNullableVariable("GuidVar", "Guid", true, true);
        final NetNullableVariable guidResult = convertGuidToString(guidVar);
        assertThat(guidResult.getType(), is("string"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void convertGuidToString_Error_Test() {
        convertGuidToString(new NetNullableVariable("IntVar", "int", true, true));
    }

    @Test
    public void toWithConstructorList_NullList_Test() {
        final ImmutableList<WithConstructorVariable> result = toWithConstructorList(null, "requestName", getTestDocSpec());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructorList_EmptyList_Test() {
        final ImmutableList<WithConstructorVariable> result = toWithConstructorList(ImmutableList.of(), "requestName", getTestDocSpec());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructorList_FullList_Test() {
        final ImmutableList<NetNullableVariable> vars = ImmutableList.of(
                new NetNullableVariable("GuidVar", "Guid", true, true),
                new NetNullableVariable("IntVar", "int", true, true));

        final ImmutableList<WithConstructorVariable> result = toWithConstructorList(vars, "requestName", getTestDocSpec());
        assertThat(result.size(), is(3));

        assertThat(result.get(0).getName(), is("GuidVar"));
        assertThat(result.get(0).getType(), is("Guid"));
        assertThat(result.get(1).getName(), is("GuidVar"));
        assertThat(result.get(1).getType(), is("string"));
        assertThat(result.get(2).getName(), is("IntVar"));
        assertThat(result.get(2).getType(), is("int"));
    }

    @Test
    public void convertGuidToStringList_NullList_Test() {
        final ImmutableList<NetNullableVariable> result = convertGuidToStringList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertGuidToStringList_EmptyList_Test() {
        final ImmutableList<NetNullableVariable> result = convertGuidToStringList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertGuidToStringList_FullList_Test() {
        final ImmutableList<NetNullableVariable> vars = ImmutableList.of(
                new NetNullableVariable("GuidVar", "Guid", true, true),
                new NetNullableVariable("IntVar", "int", true, true));

        final ImmutableList<NetNullableVariable> result = convertGuidToStringList(vars);
        assertThat(result.size(), is(2));

        assertThat(result.get(0).getName(), is("GuidVar"));
        assertThat(result.get(0).getType(), is("string"));
        assertThat(result.get(1).getName(), is("IntVar"));
        assertThat(result.get(1).getType(), is("int"));
    }

    @Test
    public void toWithConstructor_Test() {
        final String expectedDoc = "/// <summary>\n" +
                "        /// This is how you use Test Request\n" +
                "        /// </summary>\n" +
                "        /// <param name=\"TestParam\">This is how you use Test Param</param>\n";

        final String requestName = "TestRequest";
        final String paramName = "TestParam";
        final Ds3DocSpec docSpec = new Ds3DocSpecImpl(
                ImmutableMap.of(
                        requestName, "This is how you use Test Request"),
                ImmutableMap.of(
                        paramName, "This is how you use Test Param"));

        final NetNullableVariable netVar = new NetNullableVariable(paramName, "int", true, true);
        final WithConstructorVariable result = toWithConstructor(netVar, requestName, docSpec);
        assertThat(result.getName(), is(netVar.getName()));
        assertThat(result.getType(), is(netVar.getType()));
        assertThat(result.getNetType(), is(netVar.getNetType()));

        assertThat(result.getDocumentation(), is(expectedDoc));
    }
}
