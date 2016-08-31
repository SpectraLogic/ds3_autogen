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

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ds3DocSpecImpl_Test {

    private static final String requestName1 = "TestOneRequest";
    private static final String requestName2 = "TestTwoRequest";
    private static final String requestDescriptor1 = "This is how you use test one";
    private static final String requestDescriptor2 = "This is how you use test two";

    private static final String paramName1 = "ParamOne";
    private static final String paramName2 = "ParamTwo";
    private static final String paramDescriptor1 = "This is how you use param one";
    private static final String paramDescriptor2 = "This is how you use param two";

    private static Ds3DocSpec getTestDocSpec() {
        return new Ds3DocSpecImpl(
                ImmutableMap.of(
                        requestName1, requestDescriptor1,
                        requestName2, requestDescriptor2),
                ImmutableMap.of(
                        paramName1, paramDescriptor1,
                        paramName2, paramDescriptor2));
    }

    @Test
    public void getRequestDocumentation_Test() {
        final Ds3DocSpec docSpec = getTestDocSpec();

        final Optional<String> request1 = docSpec.getRequestDocumentation(requestName1);
        assertThat(request1.isPresent(), is(true));
        assertThat(request1.get(), is(requestDescriptor1));

        final Optional<String> request2 = docSpec.getRequestDocumentation(requestName2);
        assertThat(request2.isPresent(), is(true));
        assertThat(request2.get(), is(requestDescriptor2));

        assertThat(docSpec.getRequestDocumentation(paramName1).isPresent(), is(false));
        assertThat(docSpec.getRequestDocumentation(paramName2).isPresent(), is(false));
    }

    @Test
    public void getParamDocumentation_Test() {
        final Ds3DocSpec docSpec = getTestDocSpec();

        final Optional<String> request1 = docSpec.getParamDocumentation(paramName1);
        assertThat(request1.isPresent(), is(true));
        assertThat(request1.get(), is(paramDescriptor1));

        final Optional<String> request2 = docSpec.getParamDocumentation(paramName2);
        assertThat(request2.isPresent(), is(true));
        assertThat(request2.get(), is(paramDescriptor2));

        assertThat(docSpec.getParamDocumentation(requestName1).isPresent(), is(false));
        assertThat(docSpec.getParamDocumentation(requestName2).isPresent(), is(false));
    }
}
