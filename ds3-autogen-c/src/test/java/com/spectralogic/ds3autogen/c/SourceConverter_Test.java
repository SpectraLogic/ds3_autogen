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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.c.converters.SourceConverter;
import com.spectralogic.ds3autogen.c.models.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SourceConverter_Test {
    @Test
    public void testGetArrayStructMemberTypes() {
        final C_Type ds3BoolArray = new PrimitiveType("ds3_bool", true);
        final StructMember testStructMember1 = new StructMember(ds3BoolArray, "boolArrayMember");
        final Struct testStructWithArrayMembers = new Struct("testStruct", "Data", ImmutableList.of(testStructMember1), false);
        final ImmutableSet<C_Type> arrayStructMemberTypes = SourceConverter.getArrayStructMemberTypes(ImmutableList.of(testStructWithArrayMembers));
        assertTrue(arrayStructMemberTypes.contains(ds3BoolArray));
        assertEquals(arrayStructMemberTypes.size(), 1);
    }

    @Test
    public void testGetArrayStructMembersUnique() {
        final C_Type ds3BoolArray = new PrimitiveType("ds3_bool", true);
        final C_Type ds3TapeTypeArray = new FreeableType("ds3_tape_type", true);
        final StructMember testArrayStructMember1 = new StructMember(ds3BoolArray, "boolArrayMember");
        final StructMember testArrayStructMember2 = new StructMember(ds3TapeTypeArray, "tapeTypeArrayMember1");
        final StructMember testArrayStructMember3 = new StructMember(ds3TapeTypeArray, "tapeTypeArrayMember2");
        final StructMember testStructMember3 = new StructMember(new FreeableType("ds3_bucket", false), "bucketTypeArrayMember");
        final ImmutableList<StructMember> testArrayStructMembers1 = ImmutableList.of(testArrayStructMember1, testArrayStructMember2);
        final ImmutableList<StructMember> testArrayStructMembers2 = ImmutableList.of(testArrayStructMember2, testArrayStructMember3);
        final ImmutableList<StructMember> testStructMembers = ImmutableList.of(testStructMember3);
        final Struct testStructWithArrayMembers1 = new Struct("testStruct", "Data", testArrayStructMembers1, false);
        final Struct testStructWithArrayMembers2 = new Struct("testStruct", "Data", testArrayStructMembers2, false);
        final Struct testStructWithoutArrayMember = new Struct("testStructWithoutArrayMembers", "Data", testStructMembers, false);
        final ImmutableSet<C_Type>  arrayStructMemberTypes = SourceConverter.getArrayStructMemberTypes(
                ImmutableList.of(testStructWithArrayMembers1,
                        testStructWithArrayMembers2,
                        testStructWithoutArrayMember));
        assertTrue(arrayStructMemberTypes.contains(ds3BoolArray));
        assertTrue(arrayStructMemberTypes.contains(ds3TapeTypeArray));
        assertEquals(arrayStructMemberTypes.size(), 2);
    }

    @Test
    public void testGetArrayStructMembersUnique2() {
        final C_Type ds3BoolArray = new PrimitiveType("ds3_bool", true);
        final C_Type ds3TapeTypeArray = new FreeableType("ds3_tape_type", true);
        final StructMember testStructMember1 = new StructMember(ds3BoolArray, "boolArrayMember");
        final StructMember testStructMember2 = new StructMember(ds3TapeTypeArray, "tapeTypeArrayMember");
        final StructMember testStructMember3 = new StructMember(new FreeableType("ds3_bucket", false), "bucketTypeArrayMember");
        final ImmutableList<StructMember> testArrayStructMemberTypes = ImmutableList.of(testStructMember1, testStructMember2);
        final ImmutableList<StructMember> testStructMembersWithoutArrays = ImmutableList.of(testStructMember3);
        final Struct testStructWithArrayTypeMembers = new Struct("testStruct", "Data", testArrayStructMemberTypes, false);
        final Struct testStructWithoutArrayTypeMember = new Struct("testStructWithoutArrayMembers", "Data", testStructMembersWithoutArrays, false);
        final ImmutableSet<C_Type>  arrayStructMemberTypes = SourceConverter.getArrayStructMemberTypes(
                ImmutableList.of(testStructWithArrayTypeMembers, testStructWithoutArrayTypeMember));
        assertTrue(arrayStructMemberTypes.contains(ds3BoolArray));
        assertTrue(arrayStructMemberTypes.contains(ds3TapeTypeArray));
        assertEquals(arrayStructMemberTypes.size(), 2);
    }
}
