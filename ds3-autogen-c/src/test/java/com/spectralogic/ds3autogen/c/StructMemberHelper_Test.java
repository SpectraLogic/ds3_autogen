/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.c.helpers.StructMemberHelper;
import com.spectralogic.ds3autogen.c.models.PrimitiveType;
import com.spectralogic.ds3autogen.c.models.StructMember;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StructMemberHelper_Test {
    @Test
    public void testGetUnwrappedListChildNodesExclude() {
        final StructMember testSm1 = new StructMember(
            new PrimitiveType("int", false),
            "testSm1");
        final ImmutableList<StructMember> structMembers = ImmutableList.of(testSm1);
        final ImmutableList<StructMember> unwrappedTypes = StructMemberHelper.getUnwrappedListChildNodes(structMembers);
        assertThat(unwrappedTypes.size(), is(0));
    }

    @Test
    public void testGetUnwrappedListChildNodesInclude() {
        final StructMember testSm1 = new StructMember(
                new PrimitiveType("Ds3Type", true), // must be an array element
                "testSm1",
                "Data",
                "EncapsulatingTag",
                false,
                false);
        final ImmutableList<StructMember> structMembers = ImmutableList.of(testSm1);
        final ImmutableList<StructMember> unwrappedTypes = StructMemberHelper.getUnwrappedListChildNodes(structMembers);
        assertThat(unwrappedTypes.size(), is(1));
    }

    @Test
    public void testGetUnwrappedListChildNodesFilter() {
        final StructMember testSm1 = new StructMember(
                new PrimitiveType("int", false),
                "testSm1");
        final StructMember testSm2 = new StructMember(
                new PrimitiveType("Ds3Type", true), // must be an array element
                "testSm2",
                "Data",
                "EncapsulatingTag",
                false,
                false);
        final ImmutableList<StructMember> structMembers = ImmutableList.of(testSm1, testSm2);
        final ImmutableList<StructMember> unwrappedTypes = StructMemberHelper.getUnwrappedListChildNodes(structMembers);
        assertThat(unwrappedTypes.size(), is(1));
    }

    @Test
    public void testGetWrappedListChildNodesInclude() {
        final StructMember testSm1 = new StructMember(
                new PrimitiveType("Ds3Type", true),
                "testSm1");
        final ImmutableList<StructMember> structMembers = ImmutableList.of(testSm1);
        final ImmutableList<StructMember> wrappedTypes = StructMemberHelper.getWrappedListChildNodes(structMembers);
        assertThat(wrappedTypes.size(), is(1));
    }

    @Test
    public void testGetWrappedListChildNodesExclude() {
        final StructMember testSm1 = new StructMember(
                new PrimitiveType("Ds3Type", true), // must be an array element
                "testSm1",
                "Data",
                "EncapsulatingTag",
                false,
                false);
        final ImmutableList<StructMember> structMembers = ImmutableList.of(testSm1);
        final ImmutableList<StructMember> wrappedTypes = StructMemberHelper.getWrappedListChildNodes(structMembers);
        assertThat(wrappedTypes.size(), is(0));
    }

    @Test
    public void testGetWrappedListChildNodesFilter() {
        final StructMember testSm1 = new StructMember(
                new PrimitiveType("int", false),
                "testSm1");
        final StructMember testSm2 = new StructMember(
                new PrimitiveType("Ds3Type", true), // must be an array element
                "testSm2",
                "Data",
                "EncapsulatingTag",
                false,
                true);
        final ImmutableList<StructMember> structMembers = ImmutableList.of(testSm1, testSm2);
        final ImmutableList<StructMember> wrappedTypes = StructMemberHelper.getWrappedListChildNodes(structMembers);
        assertThat(wrappedTypes.size(), is(1));
    }
}
