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

package com.spectralogic.ds3autogen.python.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import org.junit.Test;

import java.io.IOException;

import static com.spectralogic.ds3autogen.python.utils.PythonDocGeneratorUtil.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class PythonDocGeneratorUtil_Test {

    private static Ds3DocSpec getTestDocSpec() throws IOException {
        return new Ds3DocSpecImpl(
                ImmutableMap.of(
                        "TestOneRequest", "This is how you use test one request"),
                ImmutableMap.of(
                        "ParamOne", "This is how you use param one"));
    }

    @Test
    public void toParamDocs_Test() {
        final String expected = "  `param_one` Descriptor for param one\n";
        final String result = toParamDocs("param_one", "Descriptor for param one", 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toParamListDocs_NullList_Test() throws IOException {
        final String result = toParamListDocs(null, getTestDocSpec(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toParamListDocs_EmptyList_Test() throws IOException {
        final String result = toParamListDocs(ImmutableList.of(), getTestDocSpec(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toParamListDocs_FullList_Test() throws IOException {
        final String expected =
                "  `param_one` This is how you use param one\n" +
                "  `does_not_exist` \n";

        final ImmutableList<String> params = ImmutableList.of("param_one", "does_not_exist");
        final String result = toParamListDocs(params, getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toConstructorDocs_NoRequestDescriptor_Test() throws IOException {
        final String result = toConstructorDocs("DoesNotExist", ImmutableList.of("param_one"), getTestDocSpec(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toConstructorDocs_Test() throws IOException {
        final String expected = "'''\n" +
                "  This is how you use test one request\n" +
                "  `param_one` This is how you use param one\n" +
                "  `does_not_exist` \n" +
                "  '''\n";

        final ImmutableList<String> params = ImmutableList.of("param_one", "does_not_exist");
        final String result = toConstructorDocs("TestOneRequest", params, getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }

    @Test
    public void toCommandDocs_NoRequestDescriptor_Test() throws IOException {
        final String result = toCommandDocs("DoesNotExist", getTestDocSpec(), 1);
        assertThat(result, is(""));
    }

    @Test
    public void toCommandDocs_Test() throws IOException {
        final String expected = "'''\n" +
                "  This is how you use test one request\n" +
                "  '''\n";

        final String result = toCommandDocs("TestOneRequest", getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }
}
