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

package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.ParameterModifier;
import com.spectralogic.ds3autogen.c.models.ParameterPointerType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Parameter_Test {
    @Test
    public void testConstParameterToString() {
        final Parameter param1 = new Parameter(ParameterModifier.CONST, "ds3_request", "request", ParameterPointerType.NONE);
        assertThat(param1.toString(), is("const ds3_request request"));
    }

    @Test
    public void testParameterToStringWithPointer() {
        final Parameter param1 = new Parameter(ParameterModifier.NONE, "ds3_error", "ds3Error", ParameterPointerType.SINGLE_POINTER);
        assertThat(param1.toString(), is("ds3_error* ds3Error"));
    }

    @Test
    public void testParameterToStringWithDoublePointer() {
        final Parameter param1 = new Parameter(ParameterModifier.NONE, "ds3_widget", "myWidget", ParameterPointerType.DOUBLE_POINTER);
        assertThat(param1.toString(), is("ds3_widget** myWidget"));
    }
}
