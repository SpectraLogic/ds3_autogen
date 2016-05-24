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
import com.spectralogic.ds3autogen.c.converters.HeaderConverter;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class HeaderConverter_Test {
    @Test
    public void testToHeader() throws ParseException {
        final ImmutableList<Request> allRequests = ImmutableList.of(
                RequestConverter.toRequest(Ds3ModelFixtures.createBucketRequest()),
                RequestConverter.toRequest(Ds3ModelFixtures.deleteBucketRequest()),
                RequestConverter.toRequest(Ds3ModelFixtures.getBucketsRequest()));

        final ImmutableList<Enum> allEnums = ImmutableList.of(
                new Enum("TestEnum", ImmutableList.of(
                        "TestEnumValue1",
                        "TestEnumValue2")),
                new Enum("TestEnum2", ImmutableList.of(
                        "TestEnumValue3",
                        "TestEnumValue4")));

        final ImmutableList<Struct> allStructs = ImmutableList.of(
                new Struct("testStruct1", null, ImmutableList.of(
                        new StructMember(new PrimitiveType("int", false), "structMember1"),
                        new StructMember(new PrimitiveType("ds3_str", true), "structMember2")),
                        true,
                        false,
                        false),
                new Struct("testStruct2", "marshallStruct2", ImmutableList.of(
                        new StructMember(new PrimitiveType("int", false), "structMember3")),
                        false,
                        true,
                        false));

        final Header testHeader = HeaderConverter.toHeader(allEnums, allStructs, allRequests);
        assertEquals(testHeader.getEnums(), allEnums);
        assertEquals(testHeader.getRequests(), allRequests);
        assertEquals(testHeader.getStructs(), allStructs);
    }
}
