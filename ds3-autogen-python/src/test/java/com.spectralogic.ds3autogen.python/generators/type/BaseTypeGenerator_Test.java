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

package com.spectralogic.ds3autogen.python.generators.type;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.python.model.type.TypeAttribute;
import com.spectralogic.ds3autogen.python.model.type.TypeElement;
import com.spectralogic.ds3autogen.python.model.type.TypeElementList;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.generators.type.BaseTypeGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3TypeTestData;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BaseTypeGenerator_Test {

    /**
     * Creates a list of Ds3Elements
     */
    private static ImmutableList<Ds3Element> createDs3Elements() {
        return ImmutableList.of(
                createDs3Element(),
                createListDs3Element(),
                createEncapsulatedListDs3Element(),
                createDs3ElementAttribute());
    }

    /**
     * Creates a Ds3Element that represents an attribute
     */
    private static Ds3Element createDs3ElementAttribute() {
        return new Ds3Element(
                "Attribute",
                "com.test.ElementType",
                null,
                ImmutableList.of(new Ds3Annotation("com.spectralogic.util.marshal.MarshalXmlAsAttribute", null)),
                false);
    }

    /**
     * Creates a simple Ds3Element
     */
    private static Ds3Element createDs3Element() {
        return new Ds3Element("Element", "com.test.ElementType", null, false);
    }

    /**
     * Creates a simple Ds3Element that represents a non-encapsulated list
     */
    private static Ds3Element createListDs3Element() {
        return new Ds3Element("ListElement", "array", "com.test.ListType", false);
    }

    /**
     * Creates a simple Ds3Element that represents an encapsulated list
     */
    private static Ds3Element createEncapsulatedListDs3Element() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "ListTypes", "java.lang.String"),
                new Ds3AnnotationElement("CollectionValueRenderingMode", "SINGLE_BLOCK_FOR_ALL_ELEMENTS", "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "ListType", "java.lang.String"));
        return new Ds3Element(
                "EncapsulatedListElement",
                "array",
                "com.test.ListType",
                ImmutableList.of(
                        new Ds3Annotation(
                                "com.spectralogic.util.marshal.CustomMarshaledName",
                                annotationElements)),
                false);
    }

    /**
     * Creates the test TypeMap
     */
    private static ImmutableMap<String, Ds3Type> createTestTypeMap() {
        final Ds3Type listType = createDs3TypeTestData("com.test.ListType");
        final Ds3Type elementType = createDs3TypeTestData("com.test.ElementType");
        return ImmutableMap.of(
                listType.getName(), listType,
                elementType.getName(), elementType);
    }

    @Test
    public void toElementList_NullMap_Test() {
        final TypeElementList result = toElementList(createListDs3Element(), null);
        assertThat(result.toPythonCode(), is("('ListElement', None, None)"));
    }

    @Test
    public void toElementList_EmptyMap_Test() {
        final TypeElementList result = toElementList(createListDs3Element(), ImmutableMap.of());
        assertThat(result.toPythonCode(), is("('ListElement', None, None)"));
    }

    @Test
    public void toElementList_Test() {
        final TypeElementList result = toElementList(createListDs3Element(), createTestTypeMap());
        assertThat(result.toPythonCode(), is("('ListElement', None, ListType())"));
    }

    @Test
    public void toElementList_EncapsulatedList_Test() {
        final TypeElementList result = toElementList(createEncapsulatedListDs3Element(), createTestTypeMap());
        assertThat(result.toPythonCode(), is("('ListType', 'ListTypes', ListType())"));
    }

    @Test
    public void toElementLists_NullList_Test() {
        final ImmutableList<String> result = toElementLists(null, null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElementLists_EmptyList_Test() {
        final ImmutableList<String> result = toElementLists(ImmutableList.of(), ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElementLists_Test() {
        final ImmutableList<String> result = toElementLists(createDs3Elements(), createTestTypeMap());
        assertThat(result.size(), is(2));
        assertThat(result, hasItem("('ListElement', None, ListType())"));
        assertThat(result, hasItem("('ListType', 'ListTypes', ListType())"));
    }

    @Test
    public void getTypeModelName_NullMap_Test() {
        final String result = getTypeModelName("com.test.ListType", null, null);
        assertThat(result, is("None"));
    }

    @Test
    public void getTypeModelName_EmptyMap_Test() {
        final String result = getTypeModelName("com.test.ListType", null, ImmutableMap.of());
        assertThat(result, is("None"));
    }

    @Test
    public void getTypeModelName_ComponentType_Test() {
        final String result = getTypeModelName("array", "com.test.ListType", createTestTypeMap());
        assertThat(result, is("ListType"));
    }

    @Test
    public void getTypeModelName_Test() {
        final String result = getTypeModelName("com.test.ListType", null, createTestTypeMap());
        assertThat(result, is("ListType"));
    }

    @Test
    public void toElement_NullMap_Test() {
        final TypeElement result = toElement(createDs3Element(), null);
        assertThat(result.toPythonCode(), is("'Element' : None"));
    }

    @Test
    public void toElement_EmptyMap_Test() {
        final TypeElement result = toElement(createDs3Element(), ImmutableMap.of());
        assertThat(result.toPythonCode(), is("'Element' : None"));
    }

    @Test
    public void toElement_Test() {
        final TypeElement result = toElement(createDs3Element(), createTestTypeMap());
        assertThat(result.toPythonCode(), is("'Element' : ElementType()"));
    }

    @Test
    public void toElements_NullList_Test() {
        final ImmutableList<String> result = toElements(null, null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElements_EmptyList_Test() {
        final ImmutableList<String> result = toElements(ImmutableList.of(), ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toElements_Test() {
        final ImmutableList<String> result = toElements(createDs3Elements(), createTestTypeMap());
        assertThat(result.size(), is(1));
        assertThat(result.get(0), is("'Element' : ElementType()"));
    }

    @Test
    public void toAttribute_Test() {
        final TypeAttribute result = toAttribute(createDs3ElementAttribute());
        assertThat(result.toPythonCode(), is("'Attribute'"));
    }

    @Test
    public void toAttributes_NullList_Test() {
        final ImmutableList<String> result = toAttributes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toAttributes_EmptyList_Test() {
        final ImmutableList<String> result = toAttributes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toAttributes_Test() {
        final ImmutableList<String> result = toAttributes(createDs3Elements());
        assertThat(result.size(), is(1));
        assertThat(result, hasItem("'Attribute'"));
    }
}
