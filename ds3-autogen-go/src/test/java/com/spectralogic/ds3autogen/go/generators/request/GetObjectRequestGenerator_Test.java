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
import com.spectralogic.ds3autogen.go.models.request.SimpleVariable;
import com.spectralogic.ds3autogen.go.models.request.Variable;
import com.spectralogic.ds3autogen.go.models.request.VariableInterface;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestAmazonS3GetObject;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GetObjectRequestGenerator_Test {

    private final GetObjectRequestGenerator generator = new GetObjectRequestGenerator();

    @Test
    public void toStructParams_Test() {
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("string", "bucketName"),
                new Arguments("string", "objectName"),
                new Arguments("networking.Checksum", "checksum"),
                new Arguments("string", "job"),
                new Arguments("int64", "offset"),
                new Arguments("*rangeHeader", "rangeHeader")
        );

        final ImmutableList<Arguments> result = generator.toStructParams(getRequestAmazonS3GetObject());

        assertThat(result.size(), is(expectedArgs.size()));
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i).getName(), is(expectedArgs.get(i).getName()));
            assertThat(result.get(i).getType(), is(expectedArgs.get(i).getType()));
        }
    }

    @Test
    public void toStructAssignmentParams_Test() {
        final ImmutableList<VariableInterface> expectedVars = ImmutableList.of(
                new SimpleVariable("bucketName"),
                new SimpleVariable("objectName"),
                new Variable("checksum", "networking.NewNoneChecksum()")
        );

        final ImmutableList<VariableInterface> result = generator.toStructAssignmentParams(getRequestAmazonS3GetObject());

        assertThat(result.size(), is(expectedVars.size()));
        expectedVars.forEach(expected -> assertThat(result, hasItem(expected)));
    }
}
