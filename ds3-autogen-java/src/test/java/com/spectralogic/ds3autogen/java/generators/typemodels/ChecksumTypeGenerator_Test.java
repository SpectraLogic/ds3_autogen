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

package com.spectralogic.ds3autogen.java.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.java.models.EnumConstant;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ChecksumTypeGenerator_Test {

    private static final ChecksumTypeGenerator generator = new ChecksumTypeGenerator();

    @Test
    public void getAllImports_Test() {
        final ImmutableList<Ds3Element> ds3Elements = ImmutableList.of(
                new Ds3Element("ElementName1", "com.spectralogic.test.ElementType1", "ElementComponentType1"),
                new Ds3Element("ElementName2", "ElementType2", "com.spectralogic.test.ElementComponentType2"),
                new Ds3Element("ElementName3", "ElementType3", null));

        final Ds3Type ds3Type = new Ds3Type(
                "TypeName",
                null,
                ds3Elements,
                null);

        final ImmutableList<String> result = generator.getAllImports(ds3Type);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstantList_NullList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstantList_EmptyList_Test() {
        final ImmutableList<EnumConstant> result = generator.toEnumConstantList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toEnumConstantList_FullList_Test() {
        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(
                new Ds3EnumConstant("CRC_32", null),
                new Ds3EnumConstant("CRC_32C", null),
                new Ds3EnumConstant("MD5", null),
                new Ds3EnumConstant("SHA_256", null),
                new Ds3EnumConstant("SHA_512", null));

        final ImmutableList<EnumConstant> result = generator.toEnumConstantList(enumConstants);
        assertThat(result.size(), is(6));
        assertThat(result.get(0).getName(), is("CRC_32"));
        assertThat(result.get(1).getName(), is("CRC_32C"));
        assertThat(result.get(2).getName(), is("MD5"));
        assertThat(result.get(3).getName(), is("SHA_256"));
        assertThat(result.get(4).getName(), is("SHA_512"));
        assertThat(result.get(5).getName(), is("NONE"));
    }
}
