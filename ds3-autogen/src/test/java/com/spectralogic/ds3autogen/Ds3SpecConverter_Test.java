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

package com.spectralogic.ds3autogen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.d3autogen.Ds3SpecConverter;
import com.spectralogic.d3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ds3SpecConverter_Test {

    private static final String TEST_NAME_MAPPER_FILE = "/testTypeNameMap.json";

    @Test
    public void convertName() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String expectedResult = "com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority";
        final String input = "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority";
        final String result = Ds3SpecConverter.convertName(input, nameMapper);

        assertThat(expectedResult, is(result));
    }

    @Test
    public void convertNameWithDollarSign() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String expectedResult = "com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority$Test";
        final String input = "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority$Test";
        final String result = Ds3SpecConverter.convertNameWithDollarSign(input, nameMapper);

        assertThat(expectedResult, is(result));
    }

    @Test
    public void convertAllAnnotationElements() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3AnnotationElement> input = ImmutableList.of(
                new Ds3AnnotationElement(
                        "testName",
                        "TEST_VALUE",
                        "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority$Test"),
                new Ds3AnnotationElement(
                        "testName",
                        "TEST_VALUE",
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket"));
        final ImmutableList<Ds3AnnotationElement> result = Ds3SpecConverter.convertAllAnnotationElements(input, nameMapper);

        assertThat(result.get(0).getValueType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority$Test"));
        assertThat(result.get(1).getValueType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
    }

    @Test
    public void convertAllAnnotations() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Annotation> input = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.s3.common.dao.domain.ds3.Bucket", null),
                new Ds3Annotation("Integer", null));
        final ImmutableList<Ds3Annotation> result = Ds3SpecConverter.convertAllAnnotations(input, nameMapper);

        assertThat(result.get(0).getName(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
        assertThat(result.get(1).getName(), is(input.get(1).getName()));
    }

    @Test
    public void convertAllEnumConstants() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3EnumConstant> input = ImmutableList.of(
                new Ds3EnumConstant("com.spectralogic.s3.common.dao.domain.ds3.Bucket", null));
        final ImmutableList<Ds3EnumConstant> result = Ds3SpecConverter.convertAllEnumConstants(input, nameMapper);

        assertThat(result.get(0).getName(), is(input.get(0).getName()));
    }

    @Test
    public void convertAllProperties() throws IOException, ParserException {
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
    public void convertAllElements() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Element> input = ImmutableList.of(
                new Ds3Element(
                        "testName",
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket",
                        "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority",
                        null));
        final ImmutableList<Ds3Element> result = Ds3SpecConverter.convertAllElements(input, nameMapper);

        assertThat(result.get(0).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
        assertThat(result.get(0).getComponentType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority"));
    }

    @Test
    public void convertTypes() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final Ds3Type type = new Ds3Type("com.spectralogic.s3.common.dao.domain.ds3.Bucket", null, null);
        final ImmutableMap.Builder<String, Ds3Type> input = ImmutableMap.builder();
        input.put("com.spectralogic.s3.common.dao.domain.ds3.Bucket", type);
        final ImmutableMap<String, Ds3Type> result = Ds3SpecConverter.convertTypes(input.build(), nameMapper);

        assertThat(result.get("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"), is(notNullValue()));
        assertThat(result.get("com.spectralogic.s3.common.dao.domain.ds3.Bucket"), is(nullValue()));
        assertThat(result.get("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket").getName(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
    }

    @Test
    public void convertResponseCode() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3ResponseType> responseTypes = ImmutableList.of(
                new Ds3ResponseType(
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket",
                        "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority"));
        final Ds3ResponseCode input = new Ds3ResponseCode(200, responseTypes);
        final Ds3ResponseCode result = Ds3SpecConverter.convertResponseCode(input, nameMapper);

        assertThat(result.getDs3ResponseTypes().get(0).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
        assertThat(result.getDs3ResponseTypes().get(0).getComponentType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority"));
    }

    @Test
    public void convertAllResponseCodes() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3ResponseCode> input = ImmutableList.of(
                new Ds3ResponseCode(200, null));
        final ImmutableList<Ds3ResponseCode> result = Ds3SpecConverter.convertAllResponseCodes(input, nameMapper);

        assertThat(result, is(notNullValue()));
    }

    @Test
    public void convertAllParams() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final ImmutableList<Ds3Param> input = ImmutableList.of(
                new Ds3Param(
                        "testName",
                        "com.spectralogic.s3.common.dao.domain.ds3.Bucket"));
        final ImmutableList<Ds3Param> result = Ds3SpecConverter.convertAllParams(input, nameMapper);

        assertThat(result.get(0).getType(), is("com.spectralogic.s3.common.dao.domain.ds3.TEST_Bucket"));
    }

    @Test
    public void convertRequests() throws IOException, ParserException {
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
                        null,
                        null,
                        null));
        final ImmutableList<Ds3Request> result = Ds3SpecConverter.convertRequests(input, nameMapper);

        assertThat(result, is(notNullValue()));
    }
}
