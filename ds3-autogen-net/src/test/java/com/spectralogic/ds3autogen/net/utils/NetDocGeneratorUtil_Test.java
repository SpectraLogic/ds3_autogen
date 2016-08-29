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

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import org.junit.Test;

import java.io.IOException;

import static com.spectralogic.ds3autogen.net.utils.NetDocGeneratorUtil.toCommandDocumentation;
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
        assertThat(toCommandDocumentation("TestOneRequest", getEmptyDocSpec(), 1), is(""));
    }

    @Test
    public void toCommandDocumentation_Test() throws IOException, ParserException {
        final String expected = "/// <summary>\n" +
                "    /// This is how you use test one request\n" +
                "    /// </summary>\n" +
                "    /// <param name=\"request\"></param>\n" +
                "    /// <returns></returns>\n";

        final String result = toCommandDocumentation("TestOneRequest", getTestDocSpec(), 1);
        assertThat(result, is(expected));
    }
}
