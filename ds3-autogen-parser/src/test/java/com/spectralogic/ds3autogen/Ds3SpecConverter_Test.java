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

package com.spectralogic.ds3autogen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.*;
import com.spectralogic.ds3autogen.api.models.enums.*;
import org.junit.Test;

import java.io.IOException;

import static com.spectralogic.ds3autogen.Ds3SpecConverter.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3TypeTestData;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class Ds3SpecConverter_Test {

    private static final String TEST_NAME_MAPPER_FILE = "/testTypeNameMap.json";

    @Test
    public void convertAllEnumConstants_NullList_Test() throws IOException {
        final ImmutableList<Ds3EnumConstant> result = convertAllEnumConstants(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertAllEnumConstants_EmptyList_Test() throws IOException {
        final ImmutableList<Ds3EnumConstant> result = convertAllEnumConstants(ImmutableList.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertAllEnumConstants_FullList_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3EnumConstant> input = ImmutableList.of(
                new Ds3EnumConstant("com.spectralogic.s3.common.dao.domain.ds3.Bucket", null));
        final ImmutableList<Ds3EnumConstant> result = convertAllEnumConstants(input, nameMapper);

        assertThat(result.get(0).getName(), is(input.get(0).getName()));
    }

    @Test
    public void convertAllProperties_NullList_Test() throws IOException {
        final ImmutableList<Ds3Property> result = Ds3SpecConverter.convertAllProperties(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertAllProperties_EmptyList_Test() throws IOException {
        final ImmutableList<Ds3Property> result = Ds3SpecConverter.convertAllProperties(ImmutableList.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertAllProperties_FullList_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Property> input = ImmutableList.of(
                new Ds3Property(
                        "testName",
                        "testValue",
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket"),
                new Ds3Property(
                        "testName",
                        "testValue",
                        "Integer"));
        final ImmutableList<Ds3Property> result = Ds3SpecConverter.convertAllProperties(input, nameMapper);

        assertThat(result.get(0).getValueType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
        assertThat(result.get(1).getValueType(), is(input.get(1).getValueType()));
    }

    @Test
    public void convertAllElements_NullList_Test() throws IOException {
        final ImmutableList<Ds3Element> result = convertAllElements(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertAllElements_EmptyList_Test() throws IOException {
        final ImmutableList<Ds3Element> result = convertAllElements(ImmutableList.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertAllElements_FullList_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Element> input = ImmutableList.of(
                new Ds3Element(
                        "testName",
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket",
                        "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority",
                        null,
                        false));
        final ImmutableList<Ds3Element> result = convertAllElements(input, nameMapper);

        assertThat(result.get(0).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
        assertThat(result.get(0).getComponentType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority"));
    }

    @Test
    public void convertTypes_NullMap_Test() throws IOException {
        final ImmutableMap<String, Ds3Type> result = convertTypes(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertTypes_EmptyMap_Test() throws IOException {
        final ImmutableMap<String, Ds3Type> result = convertTypes(ImmutableMap.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertTypes_FullMap_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final Ds3Type type = createDs3TypeTestData("com.spectralogic.s3.common.dao.domain.ds3.Bucket");
        final ImmutableMap.Builder<String, Ds3Type> input = ImmutableMap.builder();
        input.put("com.spectralogic.s3.common.dao.domain.ds3.Bucket", type);
        final ImmutableMap<String, Ds3Type> result = convertTypes(input.build(), nameMapper);

        assertThat(result.get("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"), is(notNullValue()));
        assertThat(result.get("com.spectralogic.s3.common.dao.domain.ds3.Bucket"), is(nullValue()));
        assertThat(result.get("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket").getName(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
    }

    @Test
    public void convertResponseCode_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3ResponseType> responseTypes = ImmutableList.of(
                new Ds3ResponseType(
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket",
                        "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority"));
        final Ds3ResponseCode input = new Ds3ResponseCode(200, responseTypes);
        final Ds3ResponseCode result = convertResponseCode(input, nameMapper);

        assertThat(result.getDs3ResponseTypes().get(0).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
        assertThat(result.getDs3ResponseTypes().get(0).getComponentType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority"));
    }

    @Test
    public void convertAllResponseCodes_NullList_Test() throws IOException {
        final ImmutableList<Ds3ResponseCode> result = convertAllResponseCodes(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertAllResponseCodes_EmptyList_Test() throws IOException {
        final ImmutableList<Ds3ResponseCode> result = convertAllResponseCodes(ImmutableList.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertAllResponseCodes_FullList_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3ResponseCode> input = ImmutableList.of(
                new Ds3ResponseCode(200, null));
        final ImmutableList<Ds3ResponseCode> result = convertAllResponseCodes(input, nameMapper);

        assertThat(result, is(notNullValue()));
    }

    @Test
    public void convertAllParams_NullList_Test() throws IOException {
        final ImmutableList<Ds3Param> result = convertAllParams(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void convertAllParams_EmptyList_Test() throws IOException {
        final ImmutableList<Ds3Param> result = convertAllParams(ImmutableList.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertAllParams_FullList_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Param> input = ImmutableList.of(
                new Ds3Param(
                        "testName",
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket",
                        false));
        final ImmutableList<Ds3Param> result = convertAllParams(input, nameMapper);

        assertThat(result.get(0).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
    }

    @Test
    public void convertRequests_NullList_Test() throws IOException {
        final ImmutableList<Ds3Request> result = convertRequests(null, null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertRequests_EmptyList_Test() throws IOException {
        final ImmutableList<Ds3Request> result = convertRequests(ImmutableList.of(), null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void convertRequests_FullList_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Request> input = ImmutableList.of(
                new Ds3Request(
                        "testName",
                        HttpVerb.GET,
                        Classification.amazons3,
                        Requirement.REQUIRED,
                        Requirement.NOT_ALLOWED,
                        Action.CREATE,
                        Resource.BEANS_RETRIEVER,
                        ResourceType.NON_SINGLETON,
                        Operation.ALLOCATE,
                        false,
                        null,
                        null,
                        null));
        final ImmutableList<Ds3Request> result = convertRequests(input, nameMapper);

        assertThat(result, is(notNullValue()));
    }

    /**
     * Creates a Ds3Request that is empty except for the request name and optional params
     */
    public static Ds3Request createDs3RequestTestData(
            final String requestName,
            final ImmutableList<Ds3Param> optionalParams) {
        return new Ds3Request(
                requestName,
                null,
                Classification.spectrads3,
                null, null, null, null, null, null, false, null,
                optionalParams,
                null);
    }

    @Test
    public void swapNames_Requests_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Param1", "Swap1", false),
                new Ds3Param("Param2", "Swap2", false));
        final Ds3Request request1 = createDs3RequestTestData("com.spectralogic.test.RequestSwap1", params);
        final Ds3Request request2 = createDs3RequestTestData("com.spectralogic.test.RequestSwap2", null);
        final ImmutableList<Ds3Request> requests = ImmutableList.of(request1, request2);

        final ImmutableList<Ds3Request> result = convertRequests(requests, nameMapper);
        assertThat(result.size(), is(2));

        assertThat(result.get(0).getName(), is("com.spectralogic.test.RequestSwap2"));
        assertThat(result.get(0).getOptionalQueryParams().get(0).getName(), is("Param1"));
        assertThat(result.get(0).getOptionalQueryParams().get(0).getType(), is("Swap2"));
        assertThat(result.get(0).getOptionalQueryParams().get(1).getName(), is("Param2"));
        assertThat(result.get(0).getOptionalQueryParams().get(1).getType(), is("Swap1"));

        assertThat(result.get(1).getName(), is("com.spectralogic.test.RequestSwap1"));
        assertThat(result.get(1).getOptionalQueryParams(), is(nullValue()));
    }

    @Test
    public void swapNames_Types_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String typeName1 = "com.test.one.Swap1";
        final String typeName2 = "com.test.two.Swap2";
        final Ds3Type type1 = new Ds3Type(typeName1, null);
        final Ds3Type type2 = new Ds3Type(typeName2, null);

        final ImmutableMap<String, Ds3Type> types = ImmutableMap.of(
                typeName1, type1,
                typeName2, type2);

        final ImmutableMap<String, Ds3Type> result = convertTypes(types, nameMapper);
        assertThat(result.size(), is(2));

        assertTrue(result.containsKey("com.test.one.Swap2"));
        assertTrue(result.containsKey("com.test.two.Swap1"));
    }

    @Test
    public void stripPathAndDollarSign_Test() {
        assertThat(stripPathAndDollarSign(null), is(""));
        assertThat(stripPathAndDollarSign(""), is(""));
        assertThat(stripPathAndDollarSign("SimpleName"), is("SimpleName"));
        assertThat(stripPathAndDollarSign("com.test.Name"), is("Name"));
        assertThat(stripPathAndDollarSign("com.test.useless$Name"), is("Name"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void stripPathAndDollarSign_Error_Test() {
        stripPathAndDollarSign("com.test.illegal$argument$Name");
    }

    @Test
    public void getOriginalType_Test() throws IOException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        assertThat(getOriginalType(null, nameMapper), is(nullValue()));
        assertThat(getOriginalType("", nameMapper), is(nullValue()));
        assertThat(getOriginalType("NotRenamedType", nameMapper), is(nullValue()));
        assertThat(getOriginalType("Bucket", nameMapper), is("Bucket"));
    }

    @Test
    public void toNameToMarshal_Test() {
        assertThat(toNameToMarshal(null), is("Data"));
        assertThat(toNameToMarshal(""), is(""));
        assertThat(toNameToMarshal("MyNameToMarshal"), is("MyNameToMarshal"));
    }
}
