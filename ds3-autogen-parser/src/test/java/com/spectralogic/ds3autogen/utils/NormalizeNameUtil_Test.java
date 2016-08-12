/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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


import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import org.junit.Test;

import java.io.IOException;

import static com.spectralogic.ds3autogen.utils.NormalizeNameUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NormalizeNameUtil_Test {

    private static final String TEST_NAME_MAPPER_FILE = "/testTypeNameMap.json";

    @Test
    public void getPathFromName_Test() throws IOException, ParserException {
        assertThat(getPathFromName(null), is(""));
        assertThat(getPathFromName(""), is(""));
        assertThat(getPathFromName("test"), is(""));
        assertThat(getPathFromName("com.spectralogic.test"), is("com.spectralogic."));
        assertThat(getPathFromName("com.spectralogic.test$test2"), is("com.spectralogic."));
    }

    @Test
    public void getNameFromPath_Test() throws IOException, ParserException {
        assertThat(getNameFromPath(null), is(""));
        assertThat(getNameFromPath(""), is(""));
        assertThat(getNameFromPath("test"), is("test"));
        assertThat(getNameFromPath("com.spectralogic.test"), is("test"));
        assertThat(getNameFromPath("com.spectralogic.test$test2"), is("test$test2"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getNameFromPath_Exception_Test() throws IOException, ParserException {
        getNameFromPath("test.");
    }

    @Test
    public void toSdkName_NullName_Test() throws IOException, ParserException {
        final String result = toSdkName(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void toSdkName_EmptyName_Test() throws IOException, ParserException {
        final String result = toSdkName("", null);
        assertThat(result, is(""));
    }

    @Test
    public void toSdkName_NoPath_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String expectedResult = "TEST_BlobStoreTaskPriority";
        final String input = "BlobStoreTaskPriority";
        final String result = toSdkName(input, nameMapper);

        assertThat(result, is(expectedResult));
    }

    @Test
    public void toSdkName_WithPath_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String expectedResult = "com.spectralogic.s3.common.dao.domain.ds3.TEST_BlobStoreTaskPriority";
        final String input = "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority";
        final String result = toSdkName(input, nameMapper);

        assertThat(result, is(expectedResult));
    }

    @Test
    public void toSdkName_WithDollarSign_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String expectedResult = "com.spectralogic.s3.common.dao.domain.ds3.Test$TEST_BlobStoreTaskPriority";
        final String input = "com.spectralogic.s3.common.dao.domain.ds3.Test$BlobStoreTaskPriority";
        final String result = toSdkName(input, nameMapper);

        assertThat(result, is(expectedResult));
    }

    @Test
    public void normalizeRequestName_Amazon_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String expected = "com.test.AmazonAfterRequest";
        final String input = "com.test.AmazonBeforeRequestHandler";
        final String result = normalizeRequestName(input, Classification.amazons3, nameMapper);

        assertThat(result, is(expected));
    }

    @Test
    public void normalizeRequestName_SpectraS3_Test() throws IOException, ParserException {
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);

        final String expected = "com.test.SpectraAfterSpectraS3Request";
        final String input = "com.test.SpectraBeforeRequestHandler";
        final String result = normalizeRequestName(input, Classification.spectrads3, nameMapper);

        assertThat(result, is(expected));
    }
}
