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

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestCreateObject;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PutObjectRequestGenerator_Test {

    private static final PutObjectRequestGenerator generator = new PutObjectRequestGenerator();

    @Test
    public void toOptionalConstructorParams_Test() {
        final ImmutableList<ConstructorParam> optParams = generator
                .toOptionalConstructorParams(getRequestCreateObject());
        final ImmutableList<String> result = optParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());

        assertThat(result.size(), is(5));
        assertThat(result, hasItem("job=None"));
        assertThat(result, hasItem("offset=None"));
        assertThat(result, hasItem("real_file_name=None"));
        assertThat(result, hasItem("headers=None"));
        assertThat(result, hasItem("stream=None"));
    }

    @Test
    public void getAdditionalContent_Test() {
        final String expected = "if headers is not None:\n" +
                "      for key, val in headers.iteritems():\n" +
                "        if val:\n" +
                "          self.headers[key] = val\n" +
                "    self.object_name = typeCheckString(object_name)\n" +
                "    object_data = None\n" +
                "    if stream:\n" +
                "      object_data = stream\n" +
                "    else:\n" +
                "      effectiveFileName = self.object_name\n" +
                "      if real_file_name:\n" +
                "        effectiveFileName = typeCheckString(real_file_name)\n" +
                "      object_data = open(effectiveFileName, \"rb\")\n" +
                "    if offset:\n" +
                "      object_data.seek(offset, 0)\n" +
                "    self.body = object_data\n";
        final String result = generator.getAdditionalContent(null, null);
        assertThat(result, is(expected));
    }
}
