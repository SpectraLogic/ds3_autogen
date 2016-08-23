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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.StructConverter;
import com.spectralogic.ds3autogen.c.models.Struct;
import org.junit.Test;

import java.text.ParseException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StructConverter_Test {
    @Test
    public void testConvertNameToMarshallEmpty() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("BoolElement", "boolean", null, false);
        final Ds3Element testElement2 = new Ds3Element("BeanElement", "com.spectralogic.s3.server.domain.UserApiBean", null, false);
        final ImmutableList<Ds3Element> elementsList = ImmutableList.of(testElement1, testElement2);
        final Ds3Type ds3Type = new Ds3Type("testDs3Type", elementsList);
        final Struct testStruct = StructConverter.toStruct(
                ds3Type,
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of());
        assertThat(testStruct.getNameToMarshall(), is("Data"));
    }

    @Test
    public void testConvertNameToMarshallSetValid() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("BoolElement", "boolean", null, false);
        final Ds3Element testElement2 = new Ds3Element("BeanElement", "com.spectralogic.s3.server.domain.UserApiBean", null, false);
        final ImmutableList<Ds3Element> elementsList = ImmutableList.of(testElement1, testElement2);
        final Ds3Type ds3Type = new Ds3Type(
                "testDs3Type",
                "testDs3TypeMarshallName",
                elementsList,
                ImmutableList.of());
        final Struct testStruct = StructConverter.toStruct(
                ds3Type,
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of());
        assertThat(testStruct.getNameToMarshall(), is("testDs3TypeMarshallName"));
    }

    @Test
    public void testConvertNameToMarshallJobsListSpecialCase() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("BoolElement", "boolean", null, false);
        final Ds3Element testElement2 = new Ds3Element("BeanElement", "com.spectralogic.s3.server.domain.UserApiBean", null, false);
        final ImmutableList<Ds3Element> elementsList = ImmutableList.of(testElement1, testElement2);
        final Ds3Type ds3Type = new Ds3Type(
                "ds3JobList",
                "overrideMarshallName",
                elementsList,
                ImmutableList.of());
        final Struct testStruct = StructConverter.toStruct(
                ds3Type,
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of());
        assertThat(testStruct.getNameToMarshall(), is("Jobs"));
    }

    @Test
    public void testConvertNameToMarshallCommonPrefixSpecialCase() throws ParseException {
        final Ds3Element testElement1 = new Ds3Element("CommonPrefixes", "array", "java.lang.String", false);
        final ImmutableList<Ds3Element> elementsList = ImmutableList.of(testElement1);
        final Ds3Type ds3Type = new Ds3Type(
                "com.spectralogic.s3.server.domain.BucketObjectsApiBean",
                "ListBucketResult",
                elementsList,
                ImmutableList.of());
        final Struct testStruct = StructConverter.toStruct(
                ds3Type,
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of(),
                ImmutableSet.of());
        assertThat(testStruct.getNameToMarshall(), is("ListBucketResult"));
        assertThat(testStruct.getStructMembers().get(0).getNameToMarshall(), is("CommonPrefixes"));
    }
}
