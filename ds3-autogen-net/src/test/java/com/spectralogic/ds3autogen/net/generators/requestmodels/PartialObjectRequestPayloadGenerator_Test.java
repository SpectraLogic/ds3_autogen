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
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestBulkGet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PartialObjectRequestPayloadGenerator_Test {

    private static final PartialObjectRequestPayloadGenerator generator = new PartialObjectRequestPayloadGenerator();

    @Test
    public void toOptionalArgumentsList_NullList_Test() {
        final ImmutableList<NetNullableVariable> result = generator.toOptionalArgumentsList(null, null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_EmptyList_Test() {
        final ImmutableList<NetNullableVariable> result = generator.toOptionalArgumentsList(ImmutableList.of(), ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredArgumentsList_Test() {
        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(getRequestBulkGet());
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getName(), is("FullObjects"));
        assertThat(result.get(1).getType(), is("IEnumerable<Ds3Object>"));
        assertThat(result.get(2).getName(), is("PartialObjects"));
        assertThat(result.get(2).getType(), is("IEnumerable<Ds3PartialObject>"));
    }

    @Test
    public void toConstructorArgsList_Test() {
        final ImmutableList<Arguments> result = generator.toConstructorArgsList(getRequestBulkGet());
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getName(), is("FullObjects"));
        assertThat(result.get(1).getType(), is("IEnumerable<Ds3Object>"));
        assertThat(result.get(2).getName(), is("PartialObjects"));
        assertThat(result.get(2).getType(), is("IEnumerable<Ds3PartialObject>"));
    }
}
