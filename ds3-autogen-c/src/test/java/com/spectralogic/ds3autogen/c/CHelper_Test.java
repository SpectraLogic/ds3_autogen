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

package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.helpers.CHelper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CHelper_Test {

    @Test
    public void testIndent() {
        final String expectedResult = "    ";
        final String result = CHelper.indent(1);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void testGetEnumValues() {
        Ds3EnumConstant alpha = new Ds3EnumConstant("ALPHA", null);
        Ds3EnumConstant bravo = new Ds3EnumConstant("BRAVO", null);
        final ImmutableList.Builder<Ds3EnumConstant> builder = ImmutableList.builder();
        builder.add(alpha);
        builder.add(bravo);
        final ImmutableList<Ds3EnumConstant> enumConstants = builder.build();
        final String expectedResult =
                "    ALPHA,\n" +
                "    BRAVO";
        final String result = CHelper.getEnumValues(enumConstants);
        assertThat(result, is(expectedResult));
    }

}
