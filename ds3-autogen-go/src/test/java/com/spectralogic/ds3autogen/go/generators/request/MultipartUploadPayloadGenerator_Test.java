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

package com.spectralogic.ds3autogen.go.generators.request;

import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.go.models.request.Variable;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultipartUploadPayloadGenerator_Test {

    private final PartsRequestPayloadGenerator generator = new PartsRequestPayloadGenerator();

    @Test
    public void getPayloadConstructorArgTest() {
        final Arguments result = generator.getPayloadArgument();
        assertThat(result.getName(), is("parts"));
        assertThat(result.getType(), is("[]Part"));
    }

    @Test
    public void getStructAssignmentVariableTest() {
        final Variable result = generator.getStructAssignmentVariable();
        assertThat(result.getName(), is("Parts"));
        assertThat(result.getAssignment(), is("parts"));
    }
}
