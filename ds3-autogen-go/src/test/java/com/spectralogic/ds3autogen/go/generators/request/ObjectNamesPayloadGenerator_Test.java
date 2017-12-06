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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.go.models.request.SimpleVariable;
import com.spectralogic.ds3autogen.go.models.request.Variable;
import com.spectralogic.ds3autogen.go.models.request.VariableInterface;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestVerifyPhysicalPlacement;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ObjectNamesPayloadGenerator_Test {

    private final ObjectNamesPayloadGenerator generator = new ObjectNamesPayloadGenerator();

    private final Ds3Request testRequest = getRequestVerifyPhysicalPlacement();

    @Test
    public void toStructParamsTest() {
        final ImmutableList<Arguments> expected = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("bool", "FullDetails"),
                new Arguments("[]string", "ObjectNames"),
                new Arguments("*string", "StorageDomainId")
        );

        final ImmutableList<Arguments> result = generator.toStructParams(testRequest);
        assertThat(result.size(), is(expected.size()));

        for (int i = 0; i < expected.size(); i ++) {
            assertThat(result.get(i).getName(), is(expected.get(i).getName()));
            assertThat(result.get(i).getType(), is(expected.get(i).getType()));
        }
    }

    @Test
    public void toStructAssignmentParamsTest() {
        final ImmutableList<VariableInterface> expected = ImmutableList.of(
                new SimpleVariable("bucketName"),
                new Variable("ObjectNames", "objectNames")
        );

        final ImmutableList<VariableInterface> result = generator.toStructAssignmentParams(testRequest);
        assertThat(result.size(), is(expected.size()));
        expected.forEach(var -> assertThat(result, hasItem(var)));
    }

    @Test
    public void toConstructorParamsListTest() {
        final ImmutableList<Arguments> expected = ImmutableList.of(
                new Arguments("string", "BucketName"),
                new Arguments("[]string", "ObjectNames")
        );

        final ImmutableList<Arguments> result = generator.toConstructorParamsList(testRequest);
        assertThat(result.size(), is(expected.size()));

        for (int i = 0; i < expected.size(); i ++) {
            assertThat(result.get(i).getName(), is(expected.get(i).getName()));
            assertThat(result.get(i).getType(), is(expected.get(i).getType()));
        }
    }

    @Test
    public void getPayloadConstructorArgTest() {
        final Arguments result = generator.getPayloadArgument();
        assertThat(result.getName(), is("ObjectNames"));
        assertThat(result.getType(), is("[]string"));
    }

    @Test
    public void getStructAssignmentVariableTest() {
        final Variable result = generator.getStructAssignmentVariable();
        assertThat(result.getName(), is("ObjectNames"));
        assertThat(result.getAssignment(), is("objectNames"));
    }
}
