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

package com.spectralogic.ds3autogen.net.generators.parsers.response;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import com.spectralogic.ds3autogen.net.generators.parsers.response.BaseResponseParserGenerator;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.parsers.response.BaseResponseParserGenerator.getParserName;
import static com.spectralogic.ds3autogen.net.generators.parsers.response.BaseResponseParserGenerator.getResponseCode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseResponseParserGenerator_Test {

    private static final BaseResponseParserGenerator generator = new BaseResponseParserGenerator();

    @Test
    public void toNameToMarshal_Test() {
        assertThat(generator.toNameToMarshal(null, "TypeName"), is("Data"));
        assertThat(generator.toNameToMarshal("", "TypeName"), is("Data"));
        assertThat(generator.toNameToMarshal("ResponsePayload", "TypeName"), is("ResponsePayload"));
    }

    @Test
    public void getParserName_Test() {
        assertThat(getParserName("TestingRequest"), is("TestingResponseParser"));
        assertThat(getParserName("com.test.TestingRequest"), is("TestingResponseParser"));
    }

    @Test
    public void getResponseCode_Test() {
        final ImmutableList<Ds3ResponseCode> codes = ImmutableList.of(
                new Ds3ResponseCode(
                        200,
                        ImmutableList.of(
                                new Ds3ResponseType("com.test.ResponsePayload", null))),
                new Ds3ResponseCode(
                        400,
                        ImmutableList.of(
                                new Ds3ResponseType("com.test.ErrorPayload", null))));

        final Integer result = getResponseCode(codes);
        assertThat(result, is(200));
    }
}
