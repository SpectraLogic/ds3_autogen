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
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.c.converters.SourceConverter;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.c.models.Enum;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

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

    @Test
    public void testGetQueryParamEnumsOptional() {
        final Enum keepEnum = new Enum("ds3_pool_type", ImmutableList.of("NEARLINE", "ONLINE"));
        final Enum filterEnum = new Enum("ds3_other_enum", ImmutableList.of("ALPHA", "BRAVO", "CHARLIE"));
        final ImmutableList<Enum> allEnums = ImmutableList.of(keepEnum, filterEnum);
        final ImmutableList<Request> allRequests = ImmutableList.of(
                new Request("GetPoolRequestHandler",
                        Classification.spectrads3,
                        HttpVerb.GET,
                        null,
                        null,
                        null,
                        ImmutableList.of(),
                        ImmutableList.of(),
                        ImmutableList.of(
                                new Parameter(ParameterModifier.CONST, "ds3_pool_type", "type", ParameterPointerType.NONE, false)),
                        false,
                        false,
                        "ds3_pool"));
        final ImmutableSet<Enum> filteredEnums = SourceConverter.filterQueryParamEnums(allEnums, allRequests);

        assertThat(filteredEnums.size(), is(1));
        assertTrue(filteredEnums.contains(keepEnum));
        assertFalse(filteredEnums.contains(filterEnum));
    }

    @Test
    public void testGetQueryParamEnumsRequired() {
        final Enum keepEnum = new Enum("ds3_pool_type", ImmutableList.of("NEARLINE", "ONLINE"));
        final Enum filterEnum = new Enum("ds3_other_enum", ImmutableList.of("ALPHA", "BRAVO", "CHARLIE"));
        final ImmutableList<Enum> allEnums = ImmutableList.of(keepEnum, filterEnum);
        final ImmutableList<Request> allRequests = ImmutableList.of(
                new Request("GetPoolRequestHandler",
                        Classification.spectrads3,
                        HttpVerb.GET,
                        null,
                        null,
                        null,
                        ImmutableList.of(),
                        ImmutableList.of(
                                new Parameter(ParameterModifier.CONST, "ds3_pool_type", "type", ParameterPointerType.NONE, false)),
                        ImmutableList.of(),
                        false,
                        false,
                        "ds3_pool"));
        final ImmutableSet<Enum> filteredEnums = SourceConverter.filterQueryParamEnums(allEnums, allRequests);

        assertThat(filteredEnums.size(), is(1));
        assertTrue(filteredEnums.contains(keepEnum));
        assertFalse(filteredEnums.contains(filterEnum));
    }

    @Test
    public void testGetQueryParamEnumsRequiredAndOptional() {
        final Enum keepEnum1 = new Enum("ds3_pool_type", ImmutableList.of("NEARLINE", "ONLINE"));
        final Enum keepEnum2 = new Enum("ds3_checksum_type", ImmutableList.of("CRC_32", "CRC_32C", "MD5", "SHA_256", "SHA_512"));
        final Enum filterEnum = new Enum("ds3_other_enum", ImmutableList.of("ALPHA", "BRAVO", "CHARLIE"));
        final ImmutableList<Enum> allEnums = ImmutableList.of(keepEnum1, keepEnum2, filterEnum);
        final ImmutableList<Request> allRequests = ImmutableList.of(
                new Request("GetWidgetRequestHandler",
                        Classification.spectrads3,
                        HttpVerb.GET,
                        null,
                        null,
                        null,
                        ImmutableList.of(),
                        ImmutableList.of(
                                new Parameter(ParameterModifier.CONST, "ds3_pool_type", "type", ParameterPointerType.NONE, false)),
                        ImmutableList.of(
                                new Parameter(ParameterModifier.CONST, "ds3_checksum_type", "type", ParameterPointerType.NONE, false)),
                        false,
                        false,
                        "ds3_widget"));
        final ImmutableSet<Enum> filteredEnums = SourceConverter.filterQueryParamEnums(allEnums, allRequests);

        assertThat(filteredEnums.size(), is(2));
        assertTrue(filteredEnums.contains(keepEnum1));
        assertTrue(filteredEnums.contains(keepEnum2));
        assertFalse(filteredEnums.contains(filterEnum));
    }
}
