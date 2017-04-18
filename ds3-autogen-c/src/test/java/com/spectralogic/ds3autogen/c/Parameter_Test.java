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

package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.c.helpers.ParameterHelper;
import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.ParameterModifier;
import com.spectralogic.ds3autogen.c.models.ParameterPointerType;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Parameter_Test {
    @Test
    public void testConstParameterToString() {
        final Parameter param1 = new Parameter(ParameterModifier.CONST, "ds3_request", "request", ParameterPointerType.NONE, false);
        assertThat(param1.toString(), is("const ds3_request request"));
    }

    @Test
    public void testParameterToStringWithPointer() {
        final Parameter param1 = new Parameter(ParameterModifier.NONE, "ds3_error", "ds3Error", ParameterPointerType.SINGLE_POINTER, false);
        assertThat(param1.toString(), is("ds3_error* ds3Error"));
    }

    @Test
    public void testParameterToStringWithDoublePointer() {
        final Parameter param1 = new Parameter(ParameterModifier.NONE, "ds3_widget", "myWidget", ParameterPointerType.DOUBLE_POINTER, false);
        assertThat(param1.toString(), is("ds3_widget** myWidget"));
    }

    @Test
    public void testSetQueryParamSignatureDs3Bool() {
        final Parameter parm = new Parameter(ParameterModifier.CONST, "ds3_bool", "force", ParameterPointerType.NONE, false);
        final String parmSig = ParameterHelper.generateSetQueryParamSignature(parm);
        assertThat(parmSig, is("void ds3_request_set_force(const ds3_request* request, ds3_bool value)"));
    }

    @Test
    public void testSetQueryParamSignatureString() {
        final Parameter parm = new Parameter(ParameterModifier.CONST, "ds3_str", "bucket_id", ParameterPointerType.SINGLE_POINTER, false);
        final String parmSig = ParameterHelper.generateSetQueryParamSignature(parm);
        assertThat(parmSig, is("void ds3_request_set_bucket_id(const ds3_request* request, const char * const value)"));
    }

    @Test
    public void testSetQueryParamSignatureUint64_t() {
        final Parameter parm = new Parameter(ParameterModifier.CONST, "uint64_t", "offset", ParameterPointerType.NONE, false);
        final String parmSig = ParameterHelper.generateSetQueryParamSignature(parm);
        assertThat(parmSig, is("void ds3_request_set_offset(const ds3_request* request, const uint64_t value)"));
    }

    @Test
    public void testSetQueryParamSignatureInt() {
        final Parameter parm = new Parameter(ParameterModifier.CONST, "int", "max_keys", ParameterPointerType.NONE, false);
        final String parmSig = ParameterHelper.generateSetQueryParamSignature(parm);
        System.out.println(parmSig);
        assertThat(parmSig, is("void ds3_request_set_max_keys(const ds3_request* request, const int value)"));
    }
}
