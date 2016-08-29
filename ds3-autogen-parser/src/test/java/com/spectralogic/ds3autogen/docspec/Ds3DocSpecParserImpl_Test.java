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

package com.spectralogic.ds3autogen.docspec;

import com.spectralogic.ds3autogen.Ds3DocSpecParserImpl;
import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.Ds3DocSpecParser;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ds3DocSpecParserImpl_Test {

    private static final String TEST_NAME_MAPPER_FILE = "/testTypeNameMap.json";
    private static final String TEST_COMMAND_DOCS_FILE = "/testCommandDocumentation.json";

    @Test
    public void getDocSpec_Test() throws IOException, ParserException {
        final InputStream inputStream = Ds3DocSpecParserImpl_Test.class.getResourceAsStream(TEST_COMMAND_DOCS_FILE);
        final NameMapper nameMapper = new NameMapper(TEST_NAME_MAPPER_FILE);
        final Ds3DocSpecParser parser = new Ds3DocSpecParserImpl(nameMapper);
        final Ds3DocSpec result = parser.getDocSpec(inputStream);

        assertThat(result.getParamDocumentation("Param1").get(), is("This is the effect of Param1"));
        assertThat(result.getParamDocumentation("Param2").get(), is("This is the effect of Param2"));
        assertThat(result.getParamDocumentation("Param3").get(), is("This is the effect of Param3"));

        assertThat(result.getRequestDocumentation("Test1Request").get(), is("This is how you use Request1"));
        assertThat(result.getRequestDocumentation("Test2Request").get(), is("This is how you use Request2"));
        assertThat(result.getRequestDocumentation("Test3Request").get(), is("This is how you use Amazon Request3"));
        assertThat(result.getRequestDocumentation("Test3SpectraS3Request").get(), is("This is how you use SpectraS3 Request3"));
    }

    @Test
    public void defaultConstructor_Test() throws IOException, ParserException {
        final InputStream inputStream = Ds3DocSpecParserImpl_Test.class.getResourceAsStream(TEST_COMMAND_DOCS_FILE);
        final Ds3DocSpecParser parser = new Ds3DocSpecParserImpl();
        final Ds3DocSpec result = parser.getDocSpec(inputStream);

        assertThat(result.getRequestDocumentation("GetBucketsRequest").isPresent(), is(false));
        assertThat(result.getRequestDocumentation("GetServiceRequest").isPresent(), is(true));
        assertThat(result.getRequestDocumentation("GetServiceRequest").get(), is("This is how you use get service"));
    }
}
