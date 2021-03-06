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

package com.spectralogic.ds3autogen.python.generators.response;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectResponseGenerator_Test {

    private final static GetObjectResponseGenerator generator = new GetObjectResponseGenerator();

    @Test
    public void toParseResponsePayloadTest() {
        final String expected = "stream = self.request.stream\n" +
                "        try:\n" +
                "            bytes_read = response.read(self.buffer_size)\n" +
                "            while bytes_read:\n" +
                "                stream.write(bytes_read)\n" +
                "                bytes_read = response.read(self.buffer_size)\n" +
                "        finally:\n" +
                "            stream.close()\n" +
                "            response.close()\n";
        final String result = generator.toParseResponsePayload(null);
        assertThat(result, is(expected));
    }

    @Test
    public void toInitResponseTest() {
        final String expected = "def __init__(self, response, request, buffer_size=None):\n" +
                "        self.buffer_size = buffer_size\n" +
                "        super(self.__class__, self).__init__(response, request)\n";

        final String result = generator.toInitResponse();
        assertThat(result, is(expected));
    }
}
