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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getAllocateJobChunkRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RetryAfterResponseGenerator_Test {

    private static final RetryAfterResponseGenerator generator = new RetryAfterResponseGenerator();

    @Test
    public void toParamList_NullList_Test() {
        final ImmutableList<Arguments> result = generator.toParamList(null);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("RetryAfterSeconds"));
        assertThat(result.get(0).getType(), is("int"));
        assertThat(result.get(1).getName(), is("Status"));
        assertThat(result.get(1).getType(), is("Status"));
    }

    @Test
    public void toParamList_Test() {
        final Ds3Request allocateRequest = getAllocateJobChunkRequest();
        final ImmutableList<Arguments> result = generator.toParamList(allocateRequest.getDs3ResponseCodes());
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("jobChunkApiBeanResult"));
        assertThat(result.get(0).getType(), is("JobChunkApiBean"));
        assertThat(result.get(1).getName(), is("RetryAfterSeconds"));
        assertThat(result.get(1).getType(), is("int"));
        assertThat(result.get(2).getName(), is("Status"));
        assertThat(result.get(2).getType(), is("Status"));
    }
}
