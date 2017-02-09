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
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class PaginationResponseGenerator_Test {

    private static final PaginationResponseGenerator generator = new PaginationResponseGenerator();

    @Test
    public void getParentImport_Test() {
        assertThat(generator.getParentImport(),
                is("com.spectralogic.ds3client.commands.interfaces.AbstractPaginationResponse"));
    }

    @Test
    public void toConstructorParams_NullList_Test() {
        final String expected = "final Integer pagingTotalResultCount, final Integer pagingTruncated";
        assertThat(generator.toConstructorParams(null), is(expected));
    }

    @Test
    public void toConstructorParams_EmptyList_Test() {
        final String expected = "final Integer pagingTotalResultCount, final Integer pagingTruncated";
        assertThat(generator.toConstructorParams(ImmutableList.of()), is(expected));
    }

    @Test
    public void toConstructorParams_FullList_Test() {
        final String expected = "final TypeOne argOne, final TypeTwo argTwo, final Integer pagingTotalResultCount, final Integer pagingTruncated";

        final ImmutableList<Arguments> args = ImmutableList.of(
                new Arguments("TypeOne", "ArgOne"),
                new Arguments("TypeTwo", "ArgTwo"));

        assertThat(generator.toConstructorParams(args), is(expected));
    }
}
