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
import org.assertj.core.groups.Tuple;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void toConstructorParamsList_Test() {
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("string", "ObjectName"),
                new Arguments("string", "ActiveJobId"),
                new Arguments("int", "IntReqParam"),
                new Arguments("string", "StringReqParam")
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
                new Arguments("string", "BucketName"),
                new Arguments("string", "ObjectName"),
                new Arguments("string", "ActiveJobId"),
                new Arguments("int", "IntReqParam"),
                new Arguments("string", "StringReqParam")
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
        assertThat(result.getStructParams().size(), is(expectedStructAssignments.size()));
        expectedStructAssignments.forEach(expected -> assertThat(result.getStructParams(), hasItem(expected)));
    }

    @Test
    public void toStructParams_Test() {
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("string", "ObjectName"),
                new Arguments("string", "ActiveJobId"),
                new Arguments("*int", "IntegerOptParam"),
                new Arguments("*int", "IntOptParam"),
                new Arguments("int", "IntReqParam"),
                new Arguments("*string", "StringOptParam"),
                new Arguments("string", "StringReqParam"),
                new Arguments("bool", "VoidOptParam")
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
                new Arguments("string", "BucketName"),
                new Arguments("string", "ObjectName"),
                new Arguments("string", "ActiveJobId"),
                new Arguments("int", "IntReqParam"),
                new Arguments("string", "StringReqParam"),
                new Arguments("*int", "IntOptParam"),
                new Arguments("*int", "IntegerOptParam"),
                new Arguments("*string", "StringOptParam"),
                new Arguments("bool", "VoidOptParam")
        );

        final ImmutableList<Arguments> result = generator.structParamsFromRequest(testRequest);

        assertThat(result.size(), is(expectedArgs.size()));
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getName(), is(expectedArgs.get(i).getName()));
            assertThat(result.get(i).getType(), is(expectedArgs.get(i).getType()));
        }
    }

    @Test
    public void toOptionalStructParamsNullListTest() {
        final ImmutableList<Arguments> result = generator.toOptionalStructParams(null);
        assertThat(result).hasSize(0);
    }

    @Test
    public void toOptionalStructParamsEmptyListTest() {
        final ImmutableList<Arguments> result = generator.toOptionalStructParams(ImmutableList.of());
        assertThat(result).hasSize(0);
    }

    @Test
    public void toOptionalStructParamsTest() {
        final ImmutableList<Ds3Param> input = ImmutableList.of(
                new Ds3Param("VoidParam", "void", false),
                new Ds3Param("BoolParam", "bool", false),
                new Ds3Param("IntType", "java.lang.Integer", true)
        );

        final ImmutableList<Arguments> result = generator.toOptionalStructParams(input);

        assertThat(result).hasSize(3)
                .extracting("type", "name")
                .contains(
                        Tuple.tuple("bool", "VoidParam"),
                        Tuple.tuple("*bool", "BoolParam"),
                        Tuple.tuple("*int", "IntType")
                );
    }

    @Test
    public void toWithConstructorVoidParamTest() {
        final Ds3Param param = new Ds3Param("VoidParam", "void", false);
        final WithConstructor result = generator.toWithConstructor(param);

        assertThat(result)
                .isEqualTo(new VoidWithConstructor("voidParam"));
    }

    @Test
    public void toWithConstructorGoPrimitiveParamTest() {
        final Ds3Param param = new Ds3Param("IntParam", "java.lang.Integer", false);
        final WithConstructor result = generator.toWithConstructor(param);

        assertThat(result)
                .isEqualTo(new PrimitivePointerWithConstructor("intParam", "int"));
    }

    @Test
    public void toWithConstructorInterfaceParamTest() {
        final Ds3Param param = new Ds3Param("InterfaceParam", "com.test.SpectraType", false);
        final WithConstructor result = generator.toWithConstructor(param);

        assertThat(result)
                .isEqualTo(new InterfaceWithConstructor("interfaceParam", "SpectraType"));
    }

    @Test
    public void toWithConstructorsNullListTest() {
        final ImmutableList<WithConstructor> result = generator.toWithConstructors(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructorsEmptyListTest() {
        final ImmutableList<WithConstructor> result = generator.toWithConstructors(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toWithConstructorsTest() {
        final ImmutableList<Ds3Param> input = ImmutableList.of(
                new Ds3Param("VoidParam", "void", false),
                new Ds3Param("IntParam", "java.lang.Integer", false),
                new Ds3Param("InterfaceParam", "com.test.SpectraType", false)
        );

        final ImmutableList<WithConstructor> result = generator.toWithConstructors(input);
        assertThat(result).hasSize(3)
                .contains(
                        new VoidWithConstructor("voidParam"),
                        new PrimitivePointerWithConstructor("intParam", "int"),
                        new InterfaceWithConstructor("interfaceParam", "SpectraType")
                );
    }
}
