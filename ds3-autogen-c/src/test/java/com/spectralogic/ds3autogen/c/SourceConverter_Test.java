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
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.c.converters.SourceConverter;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.*;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SourceConverter_Test {
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
                        null,
                        "ds3_pool",
                        false,
                        null));
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
                        null,
                        "ds3_pool",
                        false,
                        null));
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
                        null,
                        "ds3_widget",
                        false,
                        null));
        final ImmutableSet<Enum> filteredEnums = SourceConverter.filterQueryParamEnums(allEnums, allRequests);

        assertThat(filteredEnums.size(), is(2));
        assertTrue(filteredEnums.contains(keepEnum1));
        assertTrue(filteredEnums.contains(keepEnum2));
        assertFalse(filteredEnums.contains(filterEnum));
    }
}
