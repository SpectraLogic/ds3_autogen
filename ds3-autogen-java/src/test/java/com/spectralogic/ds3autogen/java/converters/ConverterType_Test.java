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

package com.spectralogic.ds3autogen.java.converters;

import org.junit.Test;

import static com.spectralogic.ds3autogen.java.converters.ConvertType.isModelName;
import static com.spectralogic.ds3autogen.java.converters.ConvertType.toModelName;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConverterType_Test {

    @Test
    public void isModelName_Test() {
        assertThat(isModelName(null), is(false));
        assertThat(isModelName(""), is(false));
        assertThat(isModelName("SimpleType"), is(false));
        assertThat(isModelName("java.lang.String"), is(false));
        assertThat(isModelName("com.spectralogic.test.ModelType"), is(true));
    }

    @Test
    public void toModelName_Test() {
        assertThat(toModelName(null), is(""));
        assertThat(toModelName(""), is(""));
        assertThat(toModelName("SimpleType"), is("SimpleType"));
        assertThat(toModelName("java.lang.String"), is("java.lang.String"));
        assertThat(toModelName("com.spectralogic.test.ModelType"), is("com.spectralogic.ds3client.models.ModelType"));
    }
}
