/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.go.generators.parser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.go.models.parser.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.go.utils.GoModelFixturesUtil.*;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BaseTypeParserGenerator_Test {

    private final static BaseTypeParserGenerator generator = new BaseTypeParserGenerator();

    @Test
    public void getPrimitiveTypeParserNamespaceTest() {
        final ImmutableList<String> input = ImmutableList.of(
                "java.lang.Boolean", "boolean",
                "java.lang.Integer", "int",
                "java.lang.String", "java.util.UUID",
                "java.lang.Double", "double",
                "java.lang.Long", "long",
                "java.lang.TestEnum");

        final ImmutableList<String> expected = ImmutableList.of(
                "Bool", "Bool",
                "Int", "Int",
                "String", "String",
                "Float64", "Float64",
                "Int64", "Int64",
                "TestEnum");

        assertThat(input.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertThat(generator.getPrimitiveTypeParserNamespace(input.get(i), false),
                    is(expected.get(i)));

            assertThat(generator.getPrimitiveTypeParserNamespace(input.get(i), true),
                    is("Nullable" + expected.get(i)));
        }
    }

    @Test
    public void getXmlTagNameTest() {
        final ImmutableList<Ds3Element> input = ImmutableList.of(
                STR_ELEMENT,
                STR_PTR_ELEMENT,
                INT_ELEMENT,
                LIST_ELEMENT,
                ENUM_ELEMENT,
                ENUM_PTR_ELEMENT,
                LIST_WITH_ENCAPS_TAG_ELEMENT,
                STR_ATTR,
                STR_PTR_ATTR,
                INT_ATTR,
                ENUM_ATTR,
                ENUM_PTR_ATTR);

        final ImmutableList<String> expected = ImmutableList.of(
                STR_ELEMENT.getName(),
                STR_PTR_ELEMENT.getName(),
                INT_ELEMENT.getName(),
                LIST_ELEMENT.getName(),
                ENUM_ELEMENT.getName(),
                ENUM_PTR_ELEMENT.getName(),
                "CustomMarshaledName",
                STR_ATTR.getName(),
                STR_PTR_ATTR.getName(),
                INT_ATTR.getName(),
                ENUM_ATTR.getName(),
                ENUM_PTR_ATTR.getName());

        assertThat(input.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertThat(generator.getXmlTagName(input.get(i)), is(expected.get(i)));
        }
    }

    @Test
    public void toAttributeTest() {
        final ImmutableList<Ds3Element> input = ImmutableList.of(
                STR_ATTR,
                STR_PTR_ATTR,
                INT_ATTR,
                ENUM_ATTR,
                ENUM_PTR_ATTR);

        final String modelName = "modelName";

        final ImmutableList<ParseElement> expected = ImmutableList.of(
                new ParseStringAttr(STR_ATTR.getName(), modelName, STR_ATTR.getName()),
                new ParseNullableStringAttr(STR_PTR_ATTR.getName(), modelName, STR_PTR_ATTR.getName()),
                new ParseSimpleAttr(INT_ATTR.getName(), modelName, INT_ATTR.getName(), "Int"),
                new ParseEnumAttr(ENUM_ATTR.getName(), modelName, ENUM_ATTR.getName()),
                new ParseNullableEnumAttr(ENUM_PTR_ATTR.getName(), modelName, ENUM_PTR_ATTR.getName()));

        assertThat(input.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertThat(generator.toAttribute(input.get(i), modelName), is(expected.get(i)));
        }
    }

    @Test
    public void toAttributeListEmptyListTest() {
        final ImmutableList<ParseElement> result = generator.toAttributeList(ImmutableList.of(), "modelName");
        assertThat(result.size(), is(0));
    }

    @Test
    public void toAttributeListTest() {
        final ImmutableList<Ds3Element> input = ImmutableList.of(
                STR_ATTR,
                STR_PTR_ATTR,
                INT_ATTR,
                ENUM_ATTR,
                ENUM_PTR_ATTR,
                STR_ELEMENT,
                STR_PTR_ELEMENT,
                INT_ELEMENT,
                LIST_ELEMENT,
                ENUM_ELEMENT,
                ENUM_PTR_ELEMENT,
                LIST_WITH_ENCAPS_TAG_ELEMENT);

        final String modelName = "modelName";

        final ImmutableList<ParseElement> expected = ImmutableList.of(
                new ParseStringAttr(STR_ATTR.getName(), modelName, STR_ATTR.getName()),
                new ParseNullableStringAttr(STR_PTR_ATTR.getName(), modelName, STR_PTR_ATTR.getName()),
                new ParseSimpleAttr(INT_ATTR.getName(), modelName, INT_ATTR.getName(), "Int"),
                new ParseEnumAttr(ENUM_ATTR.getName(), modelName, ENUM_ATTR.getName()),
                new ParseNullableEnumAttr(ENUM_PTR_ATTR.getName(), modelName, ENUM_PTR_ATTR.getName()));

        final ImmutableList<ParseElement> result = generator.toAttributeList(input, modelName);

        assertThat(result.size(), is(expected.size()));
        expected.forEach(item -> assertThat(result, hasItem(item)));
    }

    @Test
    public void isElementEnumEmptyMapTest() {
        assertFalse(generator.isElementEnum("com.test.something", ImmutableMap.of()));
    }

    @Test
    public void isElementEnumTest() {
        final Ds3Type enumType = new Ds3Type("com.test.EnumType", "", ImmutableList.of(), ImmutableList.of(ENUM_CONSTANT));
        final Ds3Type elementType = new Ds3Type("com.test.ElmtType", "", ImmutableList.of(STR_ELEMENT), ImmutableList.of());
        final Ds3Type comboType = new Ds3Type("com.test.ComboType", "", ImmutableList.of(STR_ELEMENT), ImmutableList.of(ENUM_CONSTANT));

        final ImmutableMap<String, Ds3Type> typeMap = ImmutableMap.of(
                enumType.getName(), enumType,
                elementType.getName(), elementType,
                comboType.getName(), comboType);

        assertTrue(generator.isElementEnum(enumType.getName(), typeMap));
        assertFalse(generator.isElementEnum(elementType.getName(), typeMap));
        assertTrue(generator.isElementEnum(comboType.getName(), typeMap));
    }

    @Test
    public void toChildNodeTest() {
        final ImmutableList<Ds3Element> input = ImmutableList.of(
                STR_ELEMENT,
                STR_PTR_ELEMENT,
                INT_ELEMENT,
                LIST_ELEMENT,
                ENUM_ELEMENT,
                ENUM_PTR_ELEMENT,
                LIST_WITH_ENCAPS_TAG_ELEMENT,
                DS3_TYPE_ELEMENT,
                LIST_ENUM_ELEMENT,
                COMMON_PREFIX_ELEMENT);

        final String modelName = "modelName";

        final ImmutableList<ParseElement> expected = ImmutableList.of(
                new ParseChildNodeAsString(STR_ELEMENT.getName(), modelName, STR_ELEMENT.getName(), "String"),
                new ParseChildNodeAsString(STR_PTR_ELEMENT.getName(), modelName, STR_PTR_ELEMENT.getName(), "NullableString"),
                new ParseChildNodeAsPrimitiveType(INT_ELEMENT.getName(), modelName, INT_ELEMENT.getName(), "Int"),
                new ParseChildNodeAddToSlice(LIST_ELEMENT.getName(), modelName, LIST_ELEMENT.getName(), removePath(LIST_ELEMENT.getComponentType())),
                new ParseChildNodeAsEnum(ENUM_ELEMENT.getName(), modelName, ENUM_ELEMENT.getName()),
                new ParseChildNodeAsNullableEnum(ENUM_PTR_ELEMENT.getName(), modelName, ENUM_PTR_ELEMENT.getName()),
                new ParseChildNodeAsSlice("TestCollectionValue", "CustomMarshaledName", modelName, LIST_WITH_ENCAPS_TAG_ELEMENT.getName(), removePath(LIST_WITH_ENCAPS_TAG_ELEMENT.getComponentType())),
                new ParseChildNodeAsDs3Type(DS3_TYPE_ELEMENT.getName(), modelName, DS3_TYPE_ELEMENT.getName()),
                new ParseChildNodeAddEnumToSlice(LIST_ENUM_ELEMENT.getName(), modelName, LIST_ENUM_ELEMENT.getName(), removePath(LIST_ENUM_ELEMENT.getComponentType())),
                new ParseChildNodeAsCommonPrefix(modelName, "CommonPrefixes"));

        final ImmutableMap<String, Ds3Type> typeMape = ImmutableMap.of(
                ENUM_ELEMENT.getType(), new Ds3Type(ENUM_ELEMENT.getType(), "", ImmutableList.of(), ImmutableList.of(ENUM_CONSTANT)));

        assertThat(input.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertThat(generator.toStandardChildNode(input.get(i), modelName, typeMape), is(expected.get(i)));
        }
    }

    @Test
    public void toChildNodeListEmptyListTest() {
        final ImmutableList<ParseElement> result = generator.toChildNodeList(ImmutableList.of(), "modelName", ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toChildNodeListTest() {
        final ImmutableList<Ds3Element> input = ImmutableList.of(
                STR_ATTR,
                STR_PTR_ATTR,
                INT_ATTR,
                ENUM_ATTR,
                ENUM_PTR_ATTR,
                STR_ELEMENT,
                STR_PTR_ELEMENT,
                INT_ELEMENT,
                LIST_ELEMENT,
                ENUM_ELEMENT,
                ENUM_PTR_ELEMENT,
                LIST_WITH_ENCAPS_TAG_ELEMENT,
                DS3_TYPE_ELEMENT);

        final String modelName = "modelName";

        final ImmutableList<ParseElement> expected = ImmutableList.of(
                new ParseChildNodeAsString(STR_ELEMENT.getName(), modelName, STR_ELEMENT.getName(), "String"),
                new ParseChildNodeAsString(STR_PTR_ELEMENT.getName(), modelName, STR_PTR_ELEMENT.getName(), "NullableString"),
                new ParseChildNodeAsPrimitiveType(INT_ELEMENT.getName(), modelName, INT_ELEMENT.getName(), "Int"),
                new ParseChildNodeAddToSlice(LIST_ELEMENT.getName(), modelName, LIST_ELEMENT.getName(), removePath(LIST_ELEMENT.getComponentType())),
                new ParseChildNodeAsEnum(ENUM_ELEMENT.getName(), modelName, ENUM_ELEMENT.getName()),
                new ParseChildNodeAsNullableEnum(ENUM_PTR_ELEMENT.getName(), modelName, ENUM_PTR_ELEMENT.getName()),
                new ParseChildNodeAsSlice("TestCollectionValue", "CustomMarshaledName", modelName, LIST_WITH_ENCAPS_TAG_ELEMENT.getName(), removePath(LIST_WITH_ENCAPS_TAG_ELEMENT.getComponentType())),
                new ParseChildNodeAsDs3Type(DS3_TYPE_ELEMENT.getName(), modelName, DS3_TYPE_ELEMENT.getName()));

        final ImmutableMap<String, Ds3Type> typeMape = ImmutableMap.of(
                ENUM_ELEMENT.getType(), new Ds3Type(ENUM_ELEMENT.getType(), "", ImmutableList.of(), ImmutableList.of(ENUM_CONSTANT)));

        final ImmutableList<ParseElement> result = generator.toChildNodeList(input, modelName, typeMape);

        assertThat(result.size(), is(expected.size()));
        result.forEach(item -> assertThat(expected, hasItem(item)));
    }
}
