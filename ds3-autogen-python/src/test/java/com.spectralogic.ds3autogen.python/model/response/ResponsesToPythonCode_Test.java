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

package com.spectralogic.ds3autogen.python.model.response;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ResponsesToPythonCode_Test {

    @Test
    public void noPayload_Test() {
        assertThat(new NoPayload().toPythonCode(), is(""));
    }

    @Test
    public void baseParsePayload_PrimitiveType_Test() {
        final String expected = "if self.response.status == 200:\n" +
                "            self.result = parseModel(xmldom.fromstring(response.read()), None)";

        assertThat(new BaseParsePayload("None", 200).toPythonCode(), is(expected));
    }

    @Test
    public void baseParsePayloadType_WithType_Test() {
        final String expected = "if self.response.status == 200:\n" +
                "            self.result = parseModel(xmldom.fromstring(response.read()), TestType())";

        assertThat(new BaseParsePayload("TestType", 200).toPythonCode(), is(expected));
    }
}
