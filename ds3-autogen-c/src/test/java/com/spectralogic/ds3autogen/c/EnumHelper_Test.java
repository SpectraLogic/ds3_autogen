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
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EnumHelper_Test {
    @Test
    public void testConvertDs3EnumConstants() {
        final Ds3EnumConstant alpha = new Ds3EnumConstant("ALPHA", null);
        final Ds3EnumConstant bravo = new Ds3EnumConstant("BRAVO", null);
        final ImmutableList.Builder<Ds3EnumConstant> enumsBuilder = ImmutableList.builder();
        enumsBuilder.add(alpha);
        enumsBuilder.add(bravo);
        final ImmutableList<Ds3EnumConstant> enumConstants = enumsBuilder.build();

        final ImmutableList<String> stringsList = EnumHelper.convertDs3EnumConstants(enumConstants);
        assertFalse(stringsList.isEmpty());
        assertEquals(2, stringsList.size());
        assertEquals("ALPHA", stringsList.get(0));
        assertEquals("BRAVO", stringsList.get(1));
    }
    @Test
    public void testGetEnumValues() {
        final Ds3EnumConstant alpha = new Ds3EnumConstant("ALPHA", null);
        final Ds3EnumConstant bravo = new Ds3EnumConstant("BRAVO", null);
        final ImmutableList.Builder<Ds3EnumConstant> enumsBuilder = ImmutableList.builder();
        enumsBuilder.add(alpha);
        enumsBuilder.add(bravo);
        final ImmutableList<Ds3EnumConstant> enumConstants = enumsBuilder.build();

        final ImmutableList<String> stringsList = EnumHelper.convertDs3EnumConstants(enumConstants);

        final String expectedResult =
                "    ALPHA,\n" +
                "    BRAVO";

        final String result = EnumHelper.getEnumValues(stringsList);
        assertThat(result, is(expectedResult));
    }
}
