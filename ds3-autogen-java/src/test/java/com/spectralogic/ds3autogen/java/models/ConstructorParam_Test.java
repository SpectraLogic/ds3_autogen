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

package com.spectralogic.ds3autogen.java.models;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConstructorParam_Test {

    private static final String PARAM_NAME = "ParamName";
    private static final String PARAM_TYPE = "ParamType";
    private static final String ANNOTATION = "ParamAnnotation";

    private static final String EXPECTED_NO_ANNOTATION = "final ParamType paramName";

    @Test
    public void simpleConstructorParamTest() {
        final ConstructorParam constructorParam = new SimpleConstructorParam(PARAM_NAME, PARAM_TYPE);
        assertThat(constructorParam.toJavaCode(), is(EXPECTED_NO_ANNOTATION));
    }

    @Test
    public void nonnullConstructorParamTest() {
        final String expected = "@Nonnull final ParamType paramName";
        final ConstructorParam constructorParam = new NonnullConstructorParam(PARAM_NAME, PARAM_TYPE);
        assertThat(constructorParam.toJavaCode(), is(expected));
    }

    @Test
    public void constructorParamWithoutAnnotationTest() {
        final ConstructorParam constructorParam = new ConstructorParam(PARAM_NAME, PARAM_TYPE, "");
        assertThat(constructorParam.toJavaCode(), is(EXPECTED_NO_ANNOTATION));
    }

    @Test
    public void constructorParamWithAnnotationTest() {
        final String expected = "@ParamAnnotation final ParamType paramName";
        final ConstructorParam constructorParam = new ConstructorParam(PARAM_NAME, PARAM_TYPE, ANNOTATION);
        assertThat(constructorParam.toJavaCode(), is(expected));
    }
}
