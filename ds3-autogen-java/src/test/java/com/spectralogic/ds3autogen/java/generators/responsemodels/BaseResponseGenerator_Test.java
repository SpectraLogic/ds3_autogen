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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import org.junit.Test;

import java.util.Optional;

import static com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator.createDs3ResponseTypeParamName;
import static com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator.toConstructorParams;
import static com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator.toParam;
import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedErrorResponseCode;
import static com.spectralogic.ds3autogen.java.test.helpers.Ds3ResponseCodeFixtureTestHelper.createPopulatedResponseCode;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3Request;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseResponseGenerator_Test {

    private static final BaseResponseGenerator generator = new BaseResponseGenerator();

    @Test
    public void getAllImports_NullList_Test() {
        final ImmutableSet<String> result = generator.getAllImports(createEmptyDs3Request());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_EmptyList_Test() {
        final ImmutableSet<String> result = generator.getAllImports(
                createDs3RequestTestData(false, ImmutableList.of(), null, null));
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllImports_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                createPopulatedResponseCode("_v1"),
                createPopulatedResponseCode("_v2"),
                createPopulatedErrorResponseCode("_v3"));

        final ImmutableSet<String> result = generator.getAllImports(
                createDs3RequestTestData(false, responseCodes, null, null));
        assertThat(result.size(), is(3));
        assertThat(result, hasItem("com.spectralogic.ds3client.models.Type_v1"));
        assertThat(result, hasItem("com.spectralogic.ds3client.models.Type_v2"));
        assertThat(result, hasItem("com.spectralogic.ds3client.commands.interfaces.AbstractResponse"));
    }

    @Test
    public void getParentImport_Test() {
        final String expected = "com.spectralogic.ds3client.commands.interfaces.AbstractResponse";
        assertThat(generator.getParentImport(createEmptyDs3Request()), is(expected));
    }

    @Test
    public void getParentClass_Test() {
        assertThat(generator.getParentClass(createEmptyDs3Request()), is("AbstractResponse"));
    }

    @Test
    public void createDs3ResponseTypeParamName_Test() {
        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("null", null)),
                is(""));

        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.ds3.BucketAcl", null)),
                is("bucketAclResult"));

        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("array", "SimpleComponentType")),
                is("simpleComponentTypeListResult"));

        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("array", "com.spectralogic.s3.common.dao.domain.ds3.BucketAcl")),
                is("bucketAclListResult"));

        assertThat(
                createDs3ResponseTypeParamName(new Ds3ResponseType("SimpleTypeResult", null)),
                is("simpleTypeResult"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toParam_Exception_Test() {
        toParam(new Ds3ResponseCode(200, null));
    }

    @Test
    public void toParam_NullPayload_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200,
                ImmutableList.of(new Ds3ResponseType("null", null)));

        final Optional<Arguments> result = toParam(responseCode);
        assertFalse(result.isPresent());
    }

    @Test
    public void toParam_ComponentType_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200,
                ImmutableList.of(new Ds3ResponseType("array", "com.test.ComponentType")));

        final Optional<Arguments> result = toParam(responseCode);
        assertTrue(result.isPresent());
        assertThat(result.get().getName(), is("componentTypeListResult"));
        assertThat(result.get().getType(), is("List<ComponentType>"));
    }

    @Test
    public void toParam_SimpleType_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200,
                ImmutableList.of(new Ds3ResponseType("com.test.TestType", null)));

        final Optional<Arguments> result = toParam(responseCode);
        assertTrue(result.isPresent());
        assertThat(result.get().getName(), is("testTypeResult"));
        assertThat(result.get().getType(), is("TestType"));
    }

    @Test
    public void toParamList_NullList_Test() {
        final ImmutableList<Arguments> result = generator.toParamList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toParamList_EmptyList_Test() {
        final ImmutableList<Arguments> result = generator.toParamList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toParamList_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200,
                        ImmutableList.of(new Ds3ResponseType("com.test.TestTypeB", null))),
                new Ds3ResponseCode(201,
                        ImmutableList.of(new Ds3ResponseType("null", null))),
                new Ds3ResponseCode(203,
                        ImmutableList.of(new Ds3ResponseType("com.test.TestTypeA", null))),
                new Ds3ResponseCode(400,
                        ImmutableList.of(new Ds3ResponseType("com.test.TestTypeC", null))));

        final ImmutableList<Arguments> result = generator.toParamList(responseCodes);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("testTypeAResult"));
        assertThat(result.get(1).getName(), is("testTypeBResult"));
    }

    @Test
    public void toConstructorParams_NullList_Test() {
        final String result = toConstructorParams(null);
        assertThat(result, is(""));
    }

    @Test
    public void toConstructorParams_EmptyList_Test() {
        final String result = toConstructorParams(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void toConstructorParams_FullList_Test() {
        final String expected = "final TypeOne argOne, final TypeTwo argTwo, final TypeThree argThree";

        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("TypeOne", "ArgOne"),
                new Arguments("TypeTwo", "ArgTwo"),
                new Arguments("TypeThree", "ArgThree"));

        final String result = toConstructorParams(args);
        assertThat(result, is(expected));
    }
}
