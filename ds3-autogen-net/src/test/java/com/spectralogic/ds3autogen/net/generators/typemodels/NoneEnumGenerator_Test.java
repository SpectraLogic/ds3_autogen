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

package com.spectralogic.ds3autogen.net.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NoneEnumGenerator_Test {

    final static NoneEnumGenerator generator = new NoneEnumGenerator();

    @Test
    public void toEnumConstants_NullList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantsList(null);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("NONE"));
    }

    @Test
    public void toEnumConstants_EmptyList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantsList(ImmutableList.of());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("NONE"));
    }

    @Test
    public void toEnumConstants_FullList_Test() {
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(
                new Ds3EnumConstant("one", null),
                new Ds3EnumConstant("two", null),
                new Ds3EnumConstant("three", null));

        final ImmutableList<EnumConstant> result = generator.toEnumConstantsList(enumConstants);
        assertThat(result.size(), is(4));
        assertThat(result.get(0).getName(), is("one"));
        assertThat(result.get(1).getName(), is("two"));
        assertThat(result.get(2).getName(), is("three"));
        assertThat(result.get(3).getName(), is("NONE"));
    }
}
