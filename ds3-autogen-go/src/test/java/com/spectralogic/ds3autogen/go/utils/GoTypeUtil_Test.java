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

import static com.spectralogic.ds3autogen.go.utils.GoTypeUtilKt.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
        assertThat(toGoType("java.util.Date"), is("string"));
        assertThat(toGoType("ChecksumType"), is("ChecksumType"));
        assertThat(toGoType("OtherType"), is("OtherType"));
    }

    @Test
    public void toGoRequestType_Nullable_Test() {
        // Not nullable
        assertThat(toGoRequestType("void", false), is(""));
        assertThat(toGoRequestType("Void", false), is(""));
        assertThat(toGoRequestType("boolean", false), is("bool"));
        assertThat(toGoRequestType("java.lang.Boolean", false), is("bool"));
        assertThat(toGoRequestType("Integer", false), is("int"));
        assertThat(toGoRequestType("int", false), is("int"));
        assertThat(toGoRequestType("java.lang.Double", false), is("float64"));
        assertThat(toGoRequestType("double", false), is("float64"));
        assertThat(toGoRequestType("java.lang.Long", false), is("int64"));
        assertThat(toGoRequestType("long", false), is("int64"));
        assertThat(toGoRequestType("java.lang.String", false), is("string"));
        assertThat(toGoRequestType("string", false), is("string"));
        assertThat(toGoRequestType("java.util.UUID", false), is("string"));
        assertThat(toGoRequestType("java.util.Date", false), is("string"));
        assertThat(toGoRequestType("ChecksumType", false), is("ChecksumType"));
        assertThat(toGoRequestType("OtherType", false), is("OtherType"));

        // Nullable
        assertThat(toGoRequestType("void", true), is(""));
        assertThat(toGoRequestType("Void", true), is(""));
        assertThat(toGoRequestType("boolean", true), is("*bool"));
        assertThat(toGoRequestType("java.lang.Boolean", true), is("*bool"));
        assertThat(toGoRequestType("Integer", true), is("*int"));
        assertThat(toGoRequestType("int", true), is("*int"));
        assertThat(toGoRequestType("java.lang.Double", true), is("*float64"));
        assertThat(toGoRequestType("double", true), is("*float64"));
        assertThat(toGoRequestType("java.lang.Long", true), is("*int64"));
        assertThat(toGoRequestType("long", true), is("*int64"));
        assertThat(toGoRequestType("java.lang.String", true), is("*string"));
        assertThat(toGoRequestType("string", true), is("*string"));
        assertThat(toGoRequestType("java.util.UUID", true), is("*string"));
        assertThat(toGoRequestType("java.util.Date", true), is("*string"));
        assertThat(toGoRequestType("ChecksumType", true), is("ChecksumType"));
        assertThat(toGoRequestType("com.test.OtherType", true), is("OtherType"));
    }

    @Test
    public void toGoResponseType_Nullable_Test() {
        // Not nullable
        assertThat(toGoResponseType("void", false), is(""));
        assertThat(toGoResponseType("Void", false), is(""));
        assertThat(toGoResponseType("boolean", false), is("bool"));
        assertThat(toGoResponseType("java.lang.Boolean", false), is("bool"));
        assertThat(toGoResponseType("Integer", false), is("int"));
        assertThat(toGoResponseType("int", false), is("int"));
        assertThat(toGoResponseType("java.lang.Double", false), is("float64"));
        assertThat(toGoResponseType("double", false), is("float64"));
        assertThat(toGoResponseType("java.lang.Long", false), is("int64"));
        assertThat(toGoResponseType("long", false), is("int64"));
        assertThat(toGoResponseType("java.lang.String", false), is("string"));
        assertThat(toGoResponseType("string", false), is("string"));
        assertThat(toGoResponseType("java.util.UUID", false), is("string"));
        assertThat(toGoResponseType("java.util.Date", false), is("string"));
        assertThat(toGoResponseType("ChecksumType", false), is("ChecksumType"));
        assertThat(toGoResponseType("OtherType", false), is("OtherType"));

        // Nullable
        assertThat(toGoResponseType("void", true), is(""));
        assertThat(toGoResponseType("Void", true), is(""));
        assertThat(toGoResponseType("boolean", true), is("*bool"));
        assertThat(toGoResponseType("java.lang.Boolean", true), is("*bool"));
        assertThat(toGoResponseType("Integer", true), is("*int"));
        assertThat(toGoResponseType("int", true), is("*int"));
        assertThat(toGoResponseType("java.lang.Double", true), is("*float64"));
        assertThat(toGoResponseType("double", true), is("*float64"));
        assertThat(toGoResponseType("java.lang.Long", true), is("*int64"));
        assertThat(toGoResponseType("long", true), is("*int64"));
        assertThat(toGoResponseType("java.lang.String", true), is("*string"));
        assertThat(toGoResponseType("string", true), is("*string"));
        assertThat(toGoResponseType("java.util.UUID", true), is("*string"));
        assertThat(toGoResponseType("java.util.Date", true), is("*string"));
        assertThat(toGoResponseType("ChecksumType", true), is("*ChecksumType"));
        assertThat(toGoResponseType("com.test.OtherType", true), is("*OtherType"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toGoResponseType_NullType_Test() {
        toGoResponseType(null, null, false);
    }

    @Test (expected = IllegalArgumentException.class)
    public void toGoResponseType_EmptyType_Test() {
        toGoResponseType("", "", false);
    }

    @Test
    public void toGoResponseType_CompoundType_Test() {
        assertThat(toGoResponseType("TestType", null, false), is("TestType"));
        assertThat(toGoResponseType("TestType", "", false), is("TestType"));
        assertThat(toGoResponseType("array", "", false), is("array"));
        assertThat(toGoResponseType("array", "CompoundType", false), is("[]CompoundType"));
    }

    @Test
    public void isGoPrimitiveTypeTest() {
        assertTrue(isGoPrimitiveType("bool"));
        assertTrue(isGoPrimitiveType("int"));
        assertTrue(isGoPrimitiveType("string"));
        assertTrue(isGoPrimitiveType("float64"));
        assertTrue(isGoPrimitiveType("int64"));

        assertFalse(isGoPrimitiveType("SpectraType"));
    }
}
