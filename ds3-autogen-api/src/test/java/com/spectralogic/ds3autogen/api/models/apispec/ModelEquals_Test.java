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

package com.spectralogic.ds3autogen.api.models.apispec;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ModelEquals_Test {

    @Test
    public void equals_Ds3Element_Test() {
        final Ds3Element element = new Ds3Element("Name", "Type", "ComponentType", false);
        assertTrue(element.equals(element));
        assertFalse(element.equals(null));
        assertFalse(element.equals(new Ds3EnumConstant("EnumConstant", null)));

        final Ds3Element elementCopy = new Ds3Element("Name", "Type", "ComponentType", false);
        assertTrue(element.equals(elementCopy));
        assertTrue(elementCopy.equals(element));

        final Ds3Element elementDiff = new Ds3Element("Name", "Type", "DiffComponentType", false);
        assertFalse(element.equals(elementDiff));
        assertFalse(elementDiff.equals(element));
    }

    @Test
    public void equals_Ds3EnumConstant_Test() {
        final Ds3EnumConstant enumConstant = new Ds3EnumConstant("Name", null);
        assertTrue(enumConstant.equals(enumConstant));
        assertFalse(enumConstant.equals(null));
        assertFalse(enumConstant.equals(new Ds3Element("Name", null, null, false)));

        final Ds3EnumConstant enumConstantCopy = new Ds3EnumConstant("Name", null);
        assertTrue(enumConstant.equals(enumConstantCopy));
        assertTrue(enumConstantCopy.equals(enumConstant));

        final Ds3EnumConstant enumConstantDiff = new Ds3EnumConstant("DiffName", null);
        assertFalse(enumConstant.equals(enumConstantDiff));
        assertFalse(enumConstantDiff.equals(enumConstant));
    }

    @Test
    public void equals_Ds3Type_Test() {
        final ImmutableList<Ds3Element> elements = ImmutableList.of(
                new Ds3Element("Name1", "Type1", "ComponentType1", false),
                new Ds3Element("Name2", "Type2", "ComponentType2", false));

        final ImmutableList<Ds3EnumConstant> enumConstants = ImmutableList.of(
                new Ds3EnumConstant("Name1", null),
                new Ds3EnumConstant("Name2", null));

        final Ds3Type type = new Ds3Type("Name", null, elements, enumConstants);
        assertTrue(type.equals(type));
        assertFalse(type.equals(null));
        assertFalse(type.equals(new Ds3Element("Name", "Type", "ComponentType", false)));

        final Ds3Type typeJumbled = new Ds3Type(
                "Name",
                null,
                ImmutableList.of(
                        new Ds3Element("Name2", "Type2", "ComponentType2", false),
                        new Ds3Element("Name1", "Type1", "ComponentType1", false)),
                enumConstants);
        assertFalse(type.equals(typeJumbled));
        assertFalse(typeJumbled.equals(type));

        final Ds3Type typeDiffName = new Ds3Type("NameDiff", null, elements, enumConstants);
        assertFalse(type.equals(typeDiffName));
        assertFalse(typeDiffName.equals(type));

        final Ds3Type typeDiffElements = new Ds3Type(
                "Name",
                null,
                ImmutableList.of(
                        new Ds3Element("Name2", "Type2", "ComponentType2", false),
                        new Ds3Element("Name3", "Type3", "ComponentType3", false)),
                enumConstants);
        assertFalse(type.equals(typeDiffElements));
        assertFalse(typeDiffElements.equals(type));
    }
}
