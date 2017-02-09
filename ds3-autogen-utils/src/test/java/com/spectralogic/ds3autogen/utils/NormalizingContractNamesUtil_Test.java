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

package com.spectralogic.ds3autogen.utils;

import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NormalizingContractNamesUtil_Test {

    @Test
    public void removePath_Test() {
        assertThat(removePath(null), is(""));
        assertThat(removePath(""), is(""));
        assertThat(removePath("SimpleString"), is("SimpleString"));
        assertThat(removePath("com.spectralogic.test.StringWithPath"), is("StringWithPath"));
    }

    @Test
    public void toResponseName_Test() {
        assertThat(toResponseName(null), is(""));
        assertThat(toResponseName(""), is(""));
        assertThat(toResponseName("SimpleRequest"), is("SimpleResponse"));
        assertThat(toResponseName("com.spectralogic.test.PathRequest"), is("PathResponse"));
    }

    @Test
    public void toResponseParserName_Test() {
        assertThat(toResponseParserName(null), is(""));
        assertThat(toResponseParserName(""), is(""));
        assertThat(toResponseParserName("TestingRequest"), is("TestingResponseParser"));
        assertThat(toResponseParserName("com.test.TestingRequest"), is("TestingResponseParser"));
    }
}
