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

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getCreateMultiPartUploadPart;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringPayloadGenerator_Test {

    private static final StringPayloadGenerator generator = new StringPayloadGenerator();

    @Test
    public void getAdditionalContent_Test() {
        final String expected = "self.body = request_payload\n";
        final String result = generator.getAdditionalContent(null, null);
        assertThat(result, is(expected));
    }

    @Test
    public void toRequiredConstructorParams_Test() {
        final ImmutableList<ConstructorParam> reqParams = generator
                .toRequiredConstructorParams(getCreateMultiPartUploadPart());
        final ImmutableList<String> result = reqParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());

        assertThat(result.size(), is(5));
        assertThat(result, hasItem("bucket_name"));
        assertThat(result, hasItem("object_name"));
        assertThat(result, hasItem("part_number"));
        assertThat(result, hasItem("request_payload"));
        assertThat(result, hasItem("upload_id"));
    }
}
