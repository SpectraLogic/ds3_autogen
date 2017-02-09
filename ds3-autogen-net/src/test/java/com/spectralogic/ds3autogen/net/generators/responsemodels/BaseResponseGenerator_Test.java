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

package com.spectralogic.ds3autogen.net.generators.responsemodels;

import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.responsemodels.BaseResponseGenerator.toNetResponseType;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseResponseGenerator_Test {

    @Test
    public void toNetResponseType_Test() {
        assertThat(toNetResponseType(null), is(""));
        assertThat(toNetResponseType(""), is(""));
        assertThat(toNetResponseType("void"), is("bool"));
        assertThat(toNetResponseType("Void"), is("bool"));
        assertThat(toNetResponseType("boolean"), is("bool"));
        assertThat(toNetResponseType("Boolean"), is("bool"));
        assertThat(toNetResponseType("Integer"), is("int"));
        assertThat(toNetResponseType("int"), is("int"));
        assertThat(toNetResponseType("String"), is("string"));
        assertThat(toNetResponseType("string"), is("string"));
        assertThat(toNetResponseType("UUID"), is("Guid"));
        assertThat(toNetResponseType("ChecksumType"), is("ChecksumType.Type"));
        assertThat(toNetResponseType("Date"), is("DateTime"));
        assertThat(toNetResponseType("OtherType"), is("OtherType"));
        assertThat(toNetResponseType("com.test.TypeWithPath"), is("TypeWithPath"));
    }
}
