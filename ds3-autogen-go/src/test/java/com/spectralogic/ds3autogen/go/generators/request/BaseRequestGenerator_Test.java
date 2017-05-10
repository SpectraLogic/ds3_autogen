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

package com.spectralogic.ds3autogen.go.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.*;
import com.spectralogic.ds3autogen.go.models.request.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseRequestGenerator_Test {

    private final BaseRequestGenerator generator = new BaseRequestGenerator();

    private final Ds3Request testRequest =
            new Ds3Request("com.test.TestRequest",
            HttpVerb.DELETE,
            Classification.amazons3,
            Requirement.REQUIRED,
            Requirement.REQUIRED,
            Action.BULK_DELETE,
            Resource.ACTIVE_JOB,
            ResourceType.SINGLETON,
            Operation.CANCEL_EJECT,
            true,
            ImmutableList.of(),
            ImmutableList.of( //optional query params
                    new Ds3Param("IntOptParam", "int", false),
                    new Ds3Param("IntegerOptParam", "java.lang.Integer", true),
                    new Ds3Param("StringOptParam", "java.lang.String", false),
                    new Ds3Param("VoidOptParam", "void", false)
            ),
            ImmutableList.of( //required query params
                    new Ds3Param("IntReqParam", "int", false),
                    new Ds3Param("StringReqParam", "java.lang.String", false),
                    new Ds3Param("VoidReqParam", "void", false),
                    new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false)
            ));

    private final String expectedConstructorParams = "bucketName string, objectName string, activeJobId string, intReqParam int, stringReqParam string";

    private final ImmutableList<VariableInterface> expectedQueryParams = ImmutableList.of(
            new Variable("operation", "\"cancel_eject\""),
            new Variable("int_req_param", "strconv.Itoa(intReqParam)"),
            new Variable("string_req_param", "stringReqParam"),
            new Variable("void_req_param", "\"\"")
    );

    private final ImmutableList<VariableInterface> expectedStructAssignments = ImmutableList.of(
            new SimpleVariable("bucketName"),
            new SimpleVariable("objectName"),
            new SimpleVariable("intReqParam"),
            new SimpleVariable("stringReqParam"),
            new SimpleVariable("activeJobId")
    );

    @Test
    public void toStructAssignmentParams_Test() {
        final ImmutableList<VariableInterface> result = generator.toStructAssignmentParams(testRequest);
        assertThat(result.size(), is(expectedStructAssignments.size()));
        expectedStructAssignments.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void structAssignmentParamsFromRequest_Test() {
        final ImmutableList<VariableInterface> result = generator.structAssignmentParamsFromRequest(testRequest);
        assertThat(result.size(), is(expectedStructAssignments.size()));
        expectedStructAssignments.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void toQueryParamsList_NullListWithOperation_Test() {
        final Variable expected = new Variable("operation", "\"allocate\"");

        final ImmutableList<VariableInterface> result = generator.toQueryParamsList(null, Operation.ALLOCATE);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is(expected));
    }

    @Test
    public void toQueryParamsList_EmptyListWithoutOperation_Test() {
        final ImmutableList<VariableInterface> result = generator.toQueryParamsList(ImmutableList.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toQueryParamsList_FullContent_Test() {
        final ImmutableList<VariableInterface> result = generator.toQueryParamsList(
                testRequest.getRequiredQueryParams(),
                testRequest.getOperation());

        assertThat(result.size(), is(expectedQueryParams.size()));
        expectedQueryParams.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void toConstructorParamsList_Test() {
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("string", "bucketName"),
                new Arguments("string", "objectName"),
                new Arguments("string", "activeJobId"),
                new Arguments("int", "intReqParam"),
                new Arguments("string", "stringReqParam")
        );

        final ImmutableList<Arguments> result = generator.toConstructorParamsList(testRequest);

        assertThat(result.size(), is(expectedArgs.size()));
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getName(), is(expectedArgs.get(i).getName()));
            assertThat(result.get(i).getType(), is(expectedArgs.get(i).getType()));
        }
    }

    @Test
    public void paramsListFromRequest_Test() {
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("string", "bucketName"),
                new Arguments("string", "objectName"),
                new Arguments("string", "activeJobId"),
                new Arguments("int", "intReqParam"),
                new Arguments("string", "stringReqParam")
        );

        final ImmutableList<Arguments> result = generator.paramsListFromRequest(testRequest);

        assertThat(result.size(), is(expectedArgs.size()));
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getName(), is(expectedArgs.get(i).getName()));
            assertThat(result.get(i).getType(), is(expectedArgs.get(i).getType()));
        }
    }

    @Test
    public void toConstructorParams_Test() {
        final String result = generator.toConstructorParams(testRequest);
        assertThat(result, is(expectedConstructorParams));
    }

    @Test
    public void toConstructor_Test() {
        final Constructor result = generator.toConstructor(testRequest);

        assertThat(result.getConstructorParams(), is(expectedConstructorParams));
        assertThat(result.getQueryParams().size(), is(expectedQueryParams.size()));
        expectedQueryParams.forEach(expected -> assertThat(result.getQueryParams(), hasItem(expected)));
        assertThat(result.getStructParams().size(), is(expectedStructAssignments.size()));
        expectedStructAssignments.forEach(expected -> assertThat(result.getStructParams(), hasItem(expected)));
    }

    @Test
    public void toStructParams_Test() {
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("string", "bucketName"),
                new Arguments("string", "objectName"),
                new Arguments("string", "activeJobId"),
                new Arguments("*int", "integerOptParam"),
                new Arguments("int", "intOptParam"),
                new Arguments("int", "intReqParam"),
                new Arguments("string", "stringOptParam"),
                new Arguments("string", "stringReqParam")
        );

        final ImmutableList<Arguments> result = generator.toStructParams(testRequest);

        assertThat(result.size(), is(expectedArgs.size()));
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getName(), is(expectedArgs.get(i).getName()));
            assertThat(result.get(i).getType(), is(expectedArgs.get(i).getType()));
        }
    }

    @Test
    public void structParamsFromRequest_Test() {
        // Not sorted
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("string", "bucketName"),
                new Arguments("string", "objectName"),
                new Arguments("string", "activeJobId"),
                new Arguments("int", "intReqParam"),
                new Arguments("string", "stringReqParam"),
                new Arguments("int", "intOptParam"),
                new Arguments("*int", "integerOptParam"),
                new Arguments("string", "stringOptParam")
        );

        final ImmutableList<Arguments> result = generator.structParamsFromRequest(testRequest);

        assertThat(result.size(), is(expectedArgs.size()));
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getName(), is(expectedArgs.get(i).getName()));
            assertThat(result.get(i).getType(), is(expectedArgs.get(i).getType()));
        }
    }

    @Test
    public void toWithConstructors_NullList_Test() {
        final ImmutableList<WithConstructor> result = generator.toWithConstructors(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructors_EmptyList_Test() {
        final ImmutableList<WithConstructor> result = generator.toWithConstructors(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructors_FullList_Test() {
        final ImmutableList<WithConstructor> expectedConst = ImmutableList.of(
                new WithConstructor("IntOptParam", "int", "int_opt_param", "strconv.Itoa(intOptParam)"),
                new WithConstructor("StringOptParam", "string", "string_opt_param", "stringOptParam")
        );

        final ImmutableList<WithConstructor> result = generator.toWithConstructors(testRequest.getOptionalQueryParams());
        assertThat(result.size(), is(expectedConst.size()));

        expectedConst.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void toNullableWithConstructors_NullList_Test() {
        final ImmutableList<WithConstructor> result = generator.toNullableWithConstructors(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toNullableWithConstructors_EmptyList_Test() {
        final ImmutableList<WithConstructor> result = generator.toNullableWithConstructors(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toNullableWithConstructors_FullList_Test() {
        final ImmutableList<WithConstructor> expectedConst = ImmutableList.of(
                new WithConstructor("IntegerOptParam", "*int", "integer_opt_param", "strconv.Itoa(*integerOptParam)")
        );

        final ImmutableList<WithConstructor> result = generator.toNullableWithConstructors(testRequest.getOptionalQueryParams());
        assertThat(result.size(), is(expectedConst.size()));

        expectedConst.forEach(expected -> assertThat(result, hasItem(expected)));
    }

    @Test
    public void toVoidWithConstructors_NullList_Test() {
        final ImmutableList<WithConstructor> result = generator.toVoidWithConstructors(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toVoidWithConstructors_EmptyList_Test() {
        final ImmutableList<WithConstructor> result = generator.toVoidWithConstructors(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toVoidWithConstructors_FullList_Test() {
        final ImmutableList<WithConstructor> expectedConst = ImmutableList.of(
                new WithConstructor("VoidOptParam", "", "void_opt_param", "")
        );

        final ImmutableList<WithConstructor> result = generator.toVoidWithConstructors(testRequest.getOptionalQueryParams());

        assertThat(result.size(), is(expectedConst.size()));
        expectedConst.forEach(expected -> assertThat(result, hasItem(expected)));
    }
}
