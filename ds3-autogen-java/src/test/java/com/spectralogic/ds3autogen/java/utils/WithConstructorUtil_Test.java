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

    @Test
    public void removeQueryParamLine_Test() {
        final String expected = "this.getQueryParams().remove(\"param_name\");\n";

        final String result = removeQueryParamLine("ParamName");
        assertThat(result, is(expected));
    }

    @Test
    public void putQueryParamLine_StringBucketName_Test() {
        final String expected = "this.getQueryParams().put(\"bucket_id\", ArgType);";
        final String result = putQueryParamLine("BucketName", "ArgType");
        assertThat(result, is(expected));
    }

    @Test
    public void putQueryParamLine_String_Test() {
        final String expected = "this.getQueryParams().put(\"arg_name\", ArgType);";
        final String result = putQueryParamLine("ArgName", "ArgType");
        assertThat(result, is(expected));
    }

    @Test
    public void putQueryParamLine_StringArguments_Test() {
        final String expected = "this.getQueryParams().put(\"arg_name\", UrlEscapers.urlFragmentEscaper().escape(argName).replace(\"+\", \"%2B\"));";
        final String result = putQueryParamLine(new Arguments("String", "ArgName"));
        assertThat(result, is(expected));
    }

    @Test
    public void putQueryParamLine_IntArguments_Test() {
        final String expected = "this.getQueryParams().put(\"arg_name\", Integer.toString(argName));";
        final String result = putQueryParamLine(new Arguments("int", "ArgName"));
        assertThat(result, is(expected));
    }

    @Test
    public void updateQueryParamLine_Test() {
        final String expected = "this.updateQueryParam(\"arg_name\", ArgType);\n";
        final String result = updateQueryParamLine("ArgName", "ArgType");
        assertThat(result, is(expected));
    }

    @Test
    public void argAssignmentLine_Arguments_Test() {
        final String expected = "this.argName = argName;\n";
        final String result = argAssignmentLine(new Arguments("ArgType", "ArgName"));
        assertThat(result, is(expected));
    }

    @Test
    public void withConstructorFirstLine_Test() {
        final String expected = "    public TestRequest withArgName(final ArgType argName) {\n";
        final String result = withConstructorFirstLine(new Arguments("ArgType", "ArgName"), "TestRequest");
        assertThat(result, is(expected));
    }
}
