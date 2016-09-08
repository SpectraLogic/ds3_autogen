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

package com.spectralogic.ds3autogen.java.utils;

import com.spectralogic.ds3autogen.api.models.Arguments;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.utils.WithConstructorUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class WithConstructorUtil_Test {

    @Test
    public void putQueryParamLine_ArgInput_Test() {
        final Arguments delimiter = new Arguments("java.lang.String", "Delimiter");
        assertThat(putQueryParamLine(delimiter),
                is("this.getQueryParams().put(\"delimiter\", delimiter);"));

        final Arguments bucketId = new Arguments("java.lang.String", "BucketId");
        assertThat(putQueryParamLine(bucketId),
                is("this.getQueryParams().put(\"bucket_id\", bucketId);"));

        final Arguments bucketName = new Arguments("java.lang.String", "BucketName");
        assertThat(putQueryParamLine(bucketName),
                is("this.getQueryParams().put(\"bucket_id\", bucketName);"));

        final Arguments stringTest = new Arguments("java.lang.String", "StringTest");
        assertThat(putQueryParamLine(stringTest),
                is("this.getQueryParams().put(\"string_test\", UrlEscapers.urlFragmentEscaper().escape(stringTest).replace(\"+\", \"%2B\"));"));

        final Arguments intTest = new Arguments("int", "IntTest");
        assertThat(putQueryParamLine(intTest),
                is("this.getQueryParams().put(\"int_test\", Integer.toString(intTest));"));
    }

    @Test
    public void putQueryParamLine_StringInput_Test() {
        assertThat(putQueryParamLine("BucketId", "bucketId"),
                is("this.getQueryParams().put(\"bucket_id\", bucketId);"));

        assertThat(putQueryParamLine("BucketName", "bucketName"),
                is("this.getQueryParams().put(\"bucket_id\", bucketName);"));
    }

    @Test
    public void queryParamArgToString_Test() {
        final Arguments delimiter = new Arguments("java.lang.String", "Delimiter");
        assertThat(queryParamArgToString(delimiter), is("delimiter"));

        final Arguments bucketId = new Arguments("java.lang.String", "BucketId");
        assertThat(queryParamArgToString(bucketId), is("bucketId"));

        final Arguments bucketName = new Arguments("java.lang.String", "BucketName");
        assertThat(queryParamArgToString(bucketName), is("bucketName"));

        final Arguments stringTest = new Arguments("java.lang.String", "StringTest");
        assertThat(queryParamArgToString(stringTest), is("UrlEscapers.urlFragmentEscaper().escape(stringTest).replace(\"+\", \"%2B\")"));

        final Arguments intTest = new Arguments("int", "IntTest");
        assertThat(queryParamArgToString(intTest), is("Integer.toString(intTest)"));
    }
}
