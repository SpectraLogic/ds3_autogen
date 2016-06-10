/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.generators.request;

import com.spectralogic.ds3autogen.python.model.request.RequestPayload;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PutObjectRequestGenerator_Test {

    private static final PutObjectRequestGenerator generator = new PutObjectRequestGenerator();

    @Test
    public void toRequestPayload_Test() {
        final String expected = "self.object_name = typeCheckString(object_name)\n" +
                "    effectiveFileName = self.object_name\n" +
                "    if real_file_name:\n" +
                "      effectiveFileName = typeCheckString(real_file_name)\n\n" +
                "    localFile = open(effectiveFileName, \"rb\")\n" +
                "    localFile.seek(offset, 0)\n" +
                "    self.body = localFile.read()\n" +
                "    localFile.close()";

        final RequestPayload result = generator.toRequestPayload(null, null);
        assertThat(result.getAssignmentCode(), is(expected));
        assertThat(result.isOptional(), is(true));
        assertThat(result.getName(), is("real_file_name"));
    }
}
