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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3TypeTestData;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3Type;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Ds3TypeClassificationUtil_Test {

    /**
     * Creates a type map with testing values
     */
    private static ImmutableMap<String, Ds3Type> getTestTypeMap() {
        final Ds3Type enumType = new Ds3Type(
                "TestEnumType",
                "",
                ImmutableList.of(),
                ImmutableList.of(
                        new Ds3EnumConstant("ONE", null)));

        final Ds3Type elementType = new Ds3Type(
                "TestElementType",
                ImmutableList.of(
                        new Ds3Element("Element", "int", null, false)));

        return ImmutableMap.of(
                enumType.getName(), enumType,
                elementType.getName(), elementType);
    }

    @Test
    public void isHttpErrorType_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Code", "java.lang.String", null, true),
                new Ds3Element("HttpErrorCode", "int", null, false),
                new Ds3Element("Message", "java.lang.String", null, true),
                new Ds3Element("Resource", "java.lang.String", null, true),
                new Ds3Element("ResourceId", "long", null, false));

        final Ds3Type errorType = new Ds3Type(
                "com.spectralogic.s3.server.domain.HttpErrorResultApiBean",
                "Error",
                elements,
                null);

        assertTrue(isHttpErrorType(errorType));
        assertFalse(isHttpErrorType(createEmptyDs3Type()));
        assertFalse(isHttpErrorType(createDs3TypeTestData("TestType")));
        assertFalse(isHttpErrorType(createDs3TypeTestData("TestType", elements)));
    }

    @Test
    public void containsElement_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Code", "java.lang.String", null, true),
                new Ds3Element("HttpErrorCode", "int", null, false));

        final Ds3Type type = createDs3TypeTestData("TestType", elements);
        assertTrue(containsElement(type, "Code"));
        assertTrue(containsElement(type, "HttpErrorCode"));
        assertFalse(containsElement(type, "Resource"));
        assertFalse(containsElement(type, "ResourceId"));
    }

    @Test
    public void getElementNames_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Code", "java.lang.String", null, true),
                new Ds3Element("HttpErrorCode", "int", null, false));
        final Ds3Type type = createDs3TypeTestData("TestType", elements);

        final ImmutableList<String> result = getElementNames(type);
        assertThat(result.size(), is(2));
        assertTrue(result.contains("Code"));
        assertTrue(result.contains("HttpErrorCode"));
    }

    @Test
    public void isCommonPrefixesType_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("CommonPrefixes", "array", "java.lang.String", true));
        final Ds3Type type = createDs3TypeTestData("TestType", elements);
        assertTrue(isCommonPrefixesType(type));
    }

    @Test
    public void isJobsApiBean_Test() {
        assertTrue(isJobsApiBean(createDs3TypeTestData("JobList")));
        assertTrue(isJobsApiBean(createDs3TypeTestData("com.test.JobList")));
        assertFalse(isJobsApiBean(createDs3TypeTestData("JobListApiBean")));
    }

    @Test
    public void isChecksumType_Test() {
        assertTrue(isChecksumType(createDs3TypeTestData("ChecksumType")));
        assertTrue(isChecksumType(createDs3TypeTestData("com.spectralogic.util.security.ChecksumType")));
        assertFalse(isChecksumType(createDs3TypeTestData("com.spectralogic.util.security.ChecksumTypeApiBean")));
    }

    @Test
    public void isObjectsType_Test() {
        assertTrue(isObjectsType(createDs3TypeTestData("Objects")));
        assertFalse(isObjectsType(createDs3TypeTestData("com.test.ObjectsList")));
        assertFalse(isObjectsType(createDs3TypeTestData("ObjectsApiBean")));
    }

    @Test
    public void isEnumType_NullValues_Test() {
        assertFalse(isEnumType(null, null));
        assertFalse(isEnumType(null, getTestTypeMap()));
        assertFalse(isEnumType("TypeName", null));
    }

    @Test
    public void isEnumType_EmptyValues_Test() {
        assertFalse(isEnumType("", ImmutableMap.of()));
        assertFalse(isEnumType("", getTestTypeMap()));
        assertFalse(isEnumType("TypeName", ImmutableMap.of()));
    }

    @Test
    public void isEnumType_Test() {
        final ImmutableMap<String, Ds3Type> typeMap = getTestTypeMap();
        assertTrue(isEnumType("TestEnumType", typeMap));

        assertFalse(isEnumType("java.lang.Integer", typeMap));
        assertFalse(isEnumType("TypeName", typeMap));
        assertFalse(isEnumType("TestElementType", typeMap));
    }
}
