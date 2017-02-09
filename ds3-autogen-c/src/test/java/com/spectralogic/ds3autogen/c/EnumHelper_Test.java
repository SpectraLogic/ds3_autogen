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

package com.spectralogic.ds3autogen.c;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.EnumConverter;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import com.spectralogic.ds3autogen.c.models.Enum;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class EnumHelper_Test {
    @Test
    public void testConvertDs3EnumConstants() {
        final Ds3EnumConstant alpha = new Ds3EnumConstant("Alpha", null);
        final Ds3EnumConstant bravo = new Ds3EnumConstant("Bravo", null);
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(alpha, bravo);
        final Ds3Type testDs3Type = new Ds3Type("Type", null, null, enumConstants);

        final ImmutableList<String> stringsList = EnumHelper.convertDs3EnumConstants(testDs3Type);
        assertFalse(stringsList.isEmpty());
        assertEquals(2, stringsList.size());
        assertEquals("DS3_TYPE_ALPHA", stringsList.get(0));
        assertEquals("DS3_TYPE_BRAVO", stringsList.get(1));
    }

    @Test
    public void testGetEnumValues() {
        final Ds3EnumConstant alpha = new Ds3EnumConstant("Alpha", null);
        final Ds3EnumConstant bravo = new Ds3EnumConstant("Bravo", null);
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(alpha, bravo);
        final Ds3Type testDs3Type = new Ds3Type("Type", null, null, enumConstants);

        final ImmutableList<String> stringsList = EnumHelper.convertDs3EnumConstants(testDs3Type);

        final String expectedResult =
                "    DS3_TYPE_ALPHA,\n" +
                "    DS3_TYPE_BRAVO";

        final String result = EnumHelper.getEnumValues(stringsList);
        assertThat(result, is(expectedResult));
    }

    @Test
    public void testEnumToString() {
        final Ds3EnumConstant alpha = new Ds3EnumConstant("Charlie", null);
        final Ds3EnumConstant bravo = new Ds3EnumConstant("Delta", null);
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(alpha, bravo);
        final Ds3Type testDs3Type = new Ds3Type("TypePrefix", null, null, enumConstants);
        final Enum testEnum= EnumConverter.toEnum(testDs3Type);

        final String expectedOutput = "    if (input == DS3_TYPE_PREFIX_CHARLIE) {"      + "\n"
                + "        return \"CHARLIE\";"      + "\n"
                + "    } else if (input == DS3_TYPE_PREFIX_DELTA) {" + "\n"
                + "        return \"DELTA\";"        + "\n"
                + "    } else {"                     + "\n"
                + "        return \"\";"             + "\n"
                + "    }"                            + "\n";
        assertThat(EnumHelper.generateToString(testEnum), is(expectedOutput));
    }

    @Test
    public void testNameSpaceType() {
        assertThat(EnumHelper.getDs3Type("TestType"), is("ds3_test_type"));
    }

    @Test
    public void testNameAlreadyBeginningWithDs3DoesNotDouble() {
        assertThat(EnumHelper.getDs3Type("Ds3TestType"), is("ds3_test_type"));
    }

    @Test
    public void testRequiresMatcher() {
        final Ds3EnumConstant alpha = new Ds3EnumConstant("Alpha", null);
        final Ds3EnumConstant bravo = new Ds3EnumConstant("Bravo", null);
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(alpha, bravo);
        final Ds3Type testDs3Type = new Ds3Type("TypePrefix", null, null, enumConstants);
        final Enum testEnum = EnumConverter.toEnum(testDs3Type);

        assertTrue(testEnum.requiresMatcher());
    }

    @Test
    public void testDoesNotRequireMatcher() {
        final Ds3EnumConstant alpha = new Ds3EnumConstant("Alpha", null);
        final Ds3EnumConstant bravo = new Ds3EnumConstant("Bravo", null);
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(alpha, bravo);
        final Ds3Type testDs3Type = new Ds3Type("com.spectralogic.util.db.lang.SqlOperation", null, null, enumConstants);
        final Enum testEnum = EnumConverter.toEnum(testDs3Type);

        assertFalse(testEnum.requiresMatcher());
    }
}
