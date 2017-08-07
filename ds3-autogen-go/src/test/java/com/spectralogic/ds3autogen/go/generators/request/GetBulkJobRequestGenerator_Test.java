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

package com.spectralogic.ds3autogen.go.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestBulkGet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetBulkJobRequestGenerator_Test {

    private static final GetBulkJobRequestGenerator generator = new GetBulkJobRequestGenerator();

    @Test
    public void toStructParams_Test() {
        final ImmutableList<Arguments> expected = ImmutableList.of(
                new Arguments("string", "bucketName"),
                new Arguments("JobChunkClientProcessingOrderGuarantee", "chunkClientProcessingOrderGuarantee"),
                new Arguments("networking.ReaderWithSizeDecorator", "content"),
                new Arguments("BlobStoreTaskPriority", "priority")
        );

        final ImmutableList<Arguments> result = generator.toStructParams(getRequestBulkGet());

        assertThat(result.size(), is(expected.size()));

        for (int i = 0; i < expected.size(); i++) {
            assertThat(result.get(i).getName(), is(expected.get(i).getName()));
            assertThat(result.get(i).getType(), is(expected.get(i).getType()));
        }
    }
}
