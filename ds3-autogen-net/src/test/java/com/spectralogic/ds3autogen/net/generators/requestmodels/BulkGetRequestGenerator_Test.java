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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BulkGetRequestGenerator_Test {

    private static final BulkGetRequestGenerator generator = new BulkGetRequestGenerator();

    @Test
    public void toOptionalArgumentsList_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("IntArg", "int", false),
                new Ds3Param("StringArg", "java.lang.String", true),
                new Ds3Param("ChunkClientProcessingOrderGuarantee", "com.spectralogic.s3.common.dao.domain.ds3.JobChunkClientProcessingOrderGuarantee", false));

        final ImmutableList<NetNullableVariable> result = generator.toOptionalArgumentsList(params, ImmutableMap.of());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("IntArg"));
        assertThat(result.get(0).getNetType(), is("int?"));
        assertThat(result.get(1).getName(), is("StringArg"));
        assertThat(result.get(1).getNetType(), is("string"));
    }
}
