/*
 * ******************************************************************************
 *   Copyright 2014-2018 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.generators.client;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectCommandGenerator_Test {

    private static final GetObjectCommandGenerator generator = new GetObjectCommandGenerator();

    @Test
    public void getFunctionParametersTest() {
        final String expected = "self, request, buffer_size=1048576";
        assertThat(generator.getFunctionParameters(), is(expected));
    }

    @Test
    public void getResponseParametersTest() {
        final String expected = "self.net_client.get_response(request), request, buffer_size";
        assertThat(generator.getResponseParameters(), is(expected));
    }
}
