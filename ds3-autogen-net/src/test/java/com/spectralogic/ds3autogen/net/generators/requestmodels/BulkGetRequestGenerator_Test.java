/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestBulkGet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BulkGetRequestGenerator_Test {

    private static final BulkGetRequestGenerator generator = new BulkGetRequestGenerator();

    @Test
    public void toOptionalArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("IntArg", "int", false),
                new Ds3Param("StringArg", "java.lang.String", true),
                new Ds3Param("ChunkClientProcessingOrderGuarantee", "com.spectralogic.s3.common.dao.domain.ds3.JobChunkClientProcessingOrderGuarantee", false));

        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(params);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("IntArg"));
        assertThat(result.get(0).getType(), is("int"));
        assertThat(result.get(1).getName(), is("StringArg"));
        assertThat(result.get(1).getType(), is("String"));
    }

    @Test
    public void toRequiredArgumentsList_Test() {
        final ImmutableList<Arguments> result = generator.toRequiredArgumentsList(getRequestBulkGet());
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(0).getType(), is("String"));
        assertThat(result.get(1).getName(), is("FullObjects"));
        assertThat(result.get(1).getType(), is("IEnumerable<string>"));
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
        assertThat(result.get(1).getType(), is("IEnumerable<string>"));
        assertThat(result.get(2).getName(), is("PartialObjects"));
        assertThat(result.get(2).getType(), is("IEnumerable<Ds3PartialObject>"));
    }
}
