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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getCompleteMultipartUploadRequest;

public class PartsRequestPayloadGenerator_Test {

    private static final PartsRequestPayloadGenerator generator = new PartsRequestPayloadGenerator();

    @Test
    public void toConstructorArgsListTest() {
        final ImmutableList<Arguments> expected = ImmutableList.of(
                new Arguments("String", "BucketName"),
                new Arguments("String", "ObjectName"),
                new Arguments("UUID", "UploadId"),
                new Arguments("IEnumerable<Part>", "Parts")
        );

        final ImmutableList<Arguments> result = generator.toConstructorArgsList(getCompleteMultipartUploadRequest());

        assertThat(expected.size(), is(result.size()));

        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getName(), is(expected.get(i).getName()));
            assertThat(result.get(i).getType(), is(expected.get(i).getType()));
        }
    }
}
