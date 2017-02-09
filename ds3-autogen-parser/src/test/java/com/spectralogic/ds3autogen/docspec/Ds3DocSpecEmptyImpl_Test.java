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

package com.spectralogic.ds3autogen.docspec;

import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Ds3DocSpecEmptyImpl_Test {

    @Test
    public void getRequestDocumentation_Test() {
        final Ds3DocSpec docSpec = new Ds3DocSpecEmptyImpl();

        assertThat(docSpec.getRequestDocumentation(null).isPresent(), is(false));
        assertThat(docSpec.getRequestDocumentation("").isPresent(), is(false));
        assertThat(docSpec.getRequestDocumentation("SomeRequest").isPresent(), is(false));
    }

    @Test
    public void getParamDocumentation_Test() {
        final Ds3DocSpec docSpec = new Ds3DocSpecEmptyImpl();

        assertThat(docSpec.getParamDocumentation(null).isPresent(), is(false));
        assertThat(docSpec.getParamDocumentation("").isPresent(), is(false));
        assertThat(docSpec.getParamDocumentation("SomeParam").isPresent(), is(false));
    }
}
