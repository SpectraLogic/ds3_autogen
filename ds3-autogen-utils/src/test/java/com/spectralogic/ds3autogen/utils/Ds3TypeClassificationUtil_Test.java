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
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3TypeTestData;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3Type;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Ds3TypeClassificationUtil_Test {

    @Test
    public void isHttpErrorType_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Code", "java.lang.String", null),
                new Ds3Element("HttpErrorCode", "int", null),
                new Ds3Element("Message", "java.lang.String", null),
                new Ds3Element("Resource", "java.lang.String", null),
                new Ds3Element("ResourceId", "long", null));

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
                new Ds3Element("Code", "java.lang.String", null),
                new Ds3Element("HttpErrorCode", "int", null));

        final Ds3Type type = createDs3TypeTestData("TestType", elements);
        assertTrue(containsElement(type, "Code"));
        assertTrue(containsElement(type, "HttpErrorCode"));
        assertFalse(containsElement(type, "Resource"));
        assertFalse(containsElement(type, "ResourceId"));
    }

    @Test
    public void getElementNames_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Code", "java.lang.String", null),
                new Ds3Element("HttpErrorCode", "int", null));
        final Ds3Type type = createDs3TypeTestData("TestType", elements);

        final ImmutableList<String> result = getElementNames(type);
        assertThat(result.size(), is(2));
        assertTrue(result.contains("Code"));
        assertTrue(result.contains("HttpErrorCode"));
    }

    @Test
    public void isCommonPrefixesType_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("CommonPrefixes", "array", "java.lang.String"));
        final Ds3Type type = createDs3TypeTestData("TestType", elements);
        assertTrue(isCommonPrefixesType(type));
    }

    @Test
    public void isChecksumType_Test() {
        assertTrue(isChecksumType(createDs3TypeTestData("com.spectralogic.util.security.ChecksumType")));
        assertFalse(isChecksumType(createDs3TypeTestData("com.spectralogic.util.security.ChecksumTypeApiBean")));
    }
}
