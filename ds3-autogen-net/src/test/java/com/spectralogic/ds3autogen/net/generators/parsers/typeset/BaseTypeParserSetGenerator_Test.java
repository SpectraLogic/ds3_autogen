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

package com.spectralogic.ds3autogen.net.generators.parsers.typeset;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.model.typeparser.TypeParser;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.parsers.typeset.BaseTypeParserSetGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3TypeTestData;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createEmptyDs3EnumConstantList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseTypeParserSetGenerator_Test {

    @Test
    public void toTypeParser_Test() {
        final TypeParser result = toTypeParser(createDs3TypeTestData("TypeName"));
        assertThat(result.getName(), is("TypeName"));
    }

    @Test
    public void toTypeParserList_NullList_Test() {
        final ImmutableList<TypeParser> result = toTypeParserList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toTypeParserList_EmptyList_Test() {
        final ImmutableList<TypeParser> result = toTypeParserList(ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toTypeParserList_FullList_Test() {
        final Ds3Type type1 = createDs3TypeTestData("TypeName1");
        final Ds3Type type2 = createDs3TypeTestData("TypeName2");
        final Ds3Type enumType = new Ds3Type(
                "EnumTypeName",
                null,
                null,
                createEmptyDs3EnumConstantList());

        final ImmutableMap<String, Ds3Type> typeMap = ImmutableMap.of(
                type1.getName(), type1,
                type2.getName(), type2,
                enumType.getName(), enumType);

        final ImmutableList<TypeParser> result = toTypeParserList(typeMap);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is(type1.getName()));
        assertThat(result.get(1).getName(), is(type2.getName()));
    }

    @Test
    public void toEnumList_NullList_Test() {
        final ImmutableList<String> result = toEnumList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumList_EmptyList_Test() {
        final ImmutableList<String> result = toEnumList(ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumList_FullList_Test() {
        final Ds3Type nonEnumType = createDs3TypeTestData("TypeName1");
        final Ds3Type enumType = new Ds3Type(
                "EnumTypeName",
                null,
                null,
                createEmptyDs3EnumConstantList());

        final ImmutableMap<String, Ds3Type> typeMap = ImmutableMap.of(
                nonEnumType.getName(), nonEnumType,
                enumType.getName(), enumType);

        final ImmutableList<String> result = toEnumList(typeMap);
        assertThat(result.size(), is(1));
        assertThat(result, hasItem(enumType.getName()));
    }
}
