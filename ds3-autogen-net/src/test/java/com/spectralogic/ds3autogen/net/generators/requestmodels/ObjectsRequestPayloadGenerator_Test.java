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

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestBulkPut;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ObjectsRequestPayloadGenerator_Test {

    private static final ObjectsRequestPayloadGenerator generator = new ObjectsRequestPayloadGenerator();

    @Test
    public void toConstructorArgsList_Test() {
        final ImmutableList<Arguments> result = generator.toConstructorArgsList(getRequestBulkPut());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("Objects"));
    }

    @Test
    public void toRequiredArgumentsList_Test() {
        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(getRequestBulkPut());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("Objects"));
    }
}
