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

package com.spectralogic.ds3autogen.python3.generators.request;

import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestCreateObject;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class P3PutObjectRequestGenerator_Test {

    private static final P3PutObjectRequestGenerator generator = new P3PutObjectRequestGenerator();

    @Test
    public void getAdditionalContent_Test() {
        final String expected = "if headers is not None:\n" +
                "      for key, val in headers.items():\n" +
                "        if val:\n" +
                "          self.headers[key] = val\n" +
                "    self.headers['Content-Length'] = length\n" +
                "    self.object_name = typeCheckString(object_name)\n" +
                "    object_data = StreamWithLength(stream, length)\n" +
                "    self.body = object_data\n";
        final String result = generator.getAdditionalContent(getRequestCreateObject(), "PutObjectRequest");
        assertThat(result, is(expected));
    }
}
