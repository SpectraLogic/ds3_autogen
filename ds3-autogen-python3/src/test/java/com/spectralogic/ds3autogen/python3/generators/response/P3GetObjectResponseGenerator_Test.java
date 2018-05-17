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

package com.spectralogic.ds3autogen.python3.generators.response;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class P3GetObjectResponseGenerator_Test {

    private static final P3GetObjectResponseGenerator generator = new P3GetObjectResponseGenerator();

    @Test
    public void toInitResponseTest() {
        final String result = generator.toInitResponse();

        assertThat(result)
                .isEqualTo("def __init__(self, response, request, buffer_size=None):\n" +
                        "        self.buffer_size = buffer_size\n" +
                        "        super(GetObjectResponse, self).__init__(response, request)\n");
    }
}