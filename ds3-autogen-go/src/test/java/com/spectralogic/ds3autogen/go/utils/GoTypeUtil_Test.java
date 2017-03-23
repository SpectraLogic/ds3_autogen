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

package com.spectralogic.ds3autogen.go.utils;
import org.junit.Test;

import static com.spectralogic.ds3autogen.go.utils.GoTypeUtilKt.toGoType;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GoTypeUtil_Test {

    @Test
    public void toGoType_Test() {
        assertThat(toGoType("void"), is(""));
        assertThat(toGoType("Void"), is(""));
        assertThat(toGoType("boolean"), is("bool"));
        assertThat(toGoType("java.lang.Boolean"), is("bool"));
        assertThat(toGoType("Integer"), is("int"));
        assertThat(toGoType("int"), is("int"));
        assertThat(toGoType("java.lang.Double"), is("float64"));
        assertThat(toGoType("double"), is("float64"));
        assertThat(toGoType("java.lang.Long"), is("int64"));
        assertThat(toGoType("long"), is("int64"));
        assertThat(toGoType("java.lang.String"), is("string"));
        assertThat(toGoType("string"), is("string"));
        assertThat(toGoType("java.util.UUID"), is("string"));
        assertThat(toGoType("ChecksumType"), is("ChecksumType"));
        assertThat(toGoType("OtherType"), is("OtherType"));
    }

    @Test
    public void toGoType_Nullable_Test() {
        // Not nullable
        assertThat(toGoType("void", false), is(""));
        assertThat(toGoType("Void", false), is(""));
        assertThat(toGoType("boolean", false), is("bool"));
        assertThat(toGoType("java.lang.Boolean", false), is("bool"));
        assertThat(toGoType("Integer", false), is("int"));
        assertThat(toGoType("int", false), is("int"));
        assertThat(toGoType("java.lang.Double", false), is("float64"));
        assertThat(toGoType("double", false), is("float64"));
        assertThat(toGoType("java.lang.Long", false), is("int64"));
        assertThat(toGoType("long", false), is("int64"));
        assertThat(toGoType("java.lang.String", false), is("string"));
        assertThat(toGoType("string", false), is("string"));
        assertThat(toGoType("java.util.UUID", false), is("string"));
        assertThat(toGoType("ChecksumType", false), is("ChecksumType"));
        assertThat(toGoType("OtherType", false), is("OtherType"));

        // Nullable
        assertThat(toGoType("void", true), is(""));
        assertThat(toGoType("Void", true), is(""));
        assertThat(toGoType("boolean", true), is("*bool"));
        assertThat(toGoType("java.lang.Boolean", true), is("*bool"));
        assertThat(toGoType("Integer", true), is("*int"));
        assertThat(toGoType("int", true), is("*int"));
        assertThat(toGoType("java.lang.Double", true), is("*float64"));
        assertThat(toGoType("double", true), is("*float64"));
        assertThat(toGoType("java.lang.Long", true), is("*int64"));
        assertThat(toGoType("long", true), is("*int64"));
        assertThat(toGoType("java.lang.String", true), is("*string"));
        assertThat(toGoType("string", true), is("*string"));
        assertThat(toGoType("java.util.UUID", true), is("*string"));
        assertThat(toGoType("ChecksumType", true), is("*ChecksumType"));
        assertThat(toGoType("OtherType", true), is("*OtherType"));
    }
}
