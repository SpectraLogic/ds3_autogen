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
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.c.helpers.TypeHelper;
import com.spectralogic.ds3autogen.c.models.Type;
import org.junit.Test;

import java.text.ParseException;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TypeHelper_Test {
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
        final String result = TypeHelper.getEnumValues(enumConstants);
        assertThat(result, is(expectedResult));
    }
    @Test
    public void testTypeIsPrimitive() throws ParseException {
        Ds3Element testElement1 = new Ds3Element("intElement", "int", null, null);
        Ds3Element testElement2 = new Ds3Element("boolElement", "boolean", null, null);
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        builder.add(testElement1);
        builder.add(testElement2);
        Type testType = new Type("testType", null, builder.build());
        assertTrue(TypeHelper.isPrimitiveType(testType));
    }
    @Test
    public void testTypeIsNotPrimitive() throws ParseException {
        Ds3Element testElement1 = new Ds3Element("boolElement", "boolean", null, null);
        Ds3Element testElement2 = new Ds3Element("complexElement", "com.spectralogic.s3.server.domain.UserApiBean", null, null);
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        builder.add(testElement1);
        builder.add(testElement2);
        Type testType = new Type("testType", null, builder.build());
        assertFalse(TypeHelper.isPrimitiveType(testType));
    }

    /* Commented out until functionality for Arrays and Sets is implemented.
    @Test
    public void testElementTypeArrayToString() throws ParseException {
        Element stringElement = new Element("arrayElement", "array", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    @Test
    public void testElementTypeSetToString() throws ParseException {
        Element stringElement = new Element("setElement", "java.util.Set", null, null);
        assertThat(CHelper.elementTypeToString(stringElement), is(""));
    }
    */
}
