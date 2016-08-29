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

package com.spectralogic.ds3autogen.net.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import org.junit.Test;

import java.io.IOException;

import static com.spectralogic.ds3autogen.net.utils.NetDocGeneratorUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NetDocGeneratorUtil_Test {

    private static Ds3DocSpec getTestDocSpec() throws IOException, ParserException {
        return new Ds3DocSpecImpl(
                ImmutableMap.of(
                        "TestOneRequest", "This is how you use test one request"),
                ImmutableMap.of(
                        "ParamOne", "This is how you use param one"));
    }

    private static Ds3DocSpec getEmptyDocSpec() {
        return new Ds3DocSpecImpl(ImmutableMap.of(), ImmutableMap.of());
    }

    @Test
    public void toCommandDocumentation_EmptyDocSpec_Test() {
        assertThat(toCommandDocs("TestOneRequest", getEmptyDocSpec(), 1), is(""));
    }

    @Test
    public void toCommandDocumentation_Test() throws IOException, ParserException {
        final String expected = "/// <summary>\n" +
                "    /// This is how you use test one request\n" +
                "    /// </summary>\n" +
                "    /// <param name=\"request\"></param>\n" +
                "    /// <returns></returns>\n";

        final String result = toCommandDocs("TestOneRequest", getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toSummary_Test() {
        final String expected = "/// <summary>\n" +
                "    /// This is my summary string\n" +
                "    /// </summary>\n";
        final String result = toSummaryDocs("This is my summary string", 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toParam_Test() {
        final String expected = "    /// <param name=\"ParamName\">This is my param</param>\n";
        final String result = toParamDocs("ParamName", "This is my param", 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toParamListDocs_NullList_Test() {
        final String result = toParamListDocs(null, getEmptyDocSpec(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toParamListDocs_EmptyList_Test() {
        final String result = toParamListDocs(ImmutableList.of(), getEmptyDocSpec(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toParamListDocs_FullList_Test() throws IOException, ParserException {
        final String expected =
                "    /// <param name=\"ParamOne\">This is how you use param one</param>\n" +
                "    /// <param name=\"NoDocParam\"></param>\n";

        final ImmutableList<String> params = ImmutableList.of("ParamOne", "NoDocParam");
        final String result = toParamListDocs(params, getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toConstructorDocs_EmptyDocSpec_Test() {
        final String result = toConstructorDocs("RequestName", ImmutableList.of(), getEmptyDocSpec(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toConstructorDocs_NullParams_Test() throws IOException, ParserException {
        final String expected = "/// <summary>\n" +
                "    /// This is how you use test one request\n" +
                "    /// </summary>\n";

        final String result = toConstructorDocs("TestOneRequest", null, getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toConstructorDocs_EmptyParams_Test() throws IOException, ParserException {
        final String expected = "/// <summary>\n" +
                "    /// This is how you use test one request\n" +
                "    /// </summary>\n";

        final String result = toConstructorDocs("TestOneRequest", ImmutableList.of(), getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toConstructorDocs_Test() throws IOException, ParserException {
        final String expected = "/// <summary>\n" +
                "    /// This is how you use test one request\n" +
                "    /// </summary>\n" +
                "    /// <param name=\"ParamOne\">This is how you use param one</param>\n" +
                "    /// <param name=\"NoDocParam\"></param>\n";

        final ImmutableList<String> params = ImmutableList.of("ParamOne", "NoDocParam");
        final String result = toConstructorDocs("TestOneRequest", params, getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }
}
