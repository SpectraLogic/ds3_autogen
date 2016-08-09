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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3AnnotationElement;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3AnnotationMarshaledNameFixture.*;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Ds3ElementUtil_Test {

    @Test
    public void getXmlTagFromAnnotation_CustomName_Test() {
        final Ds3Annotation annotation = createCustomMarshaledNameAnnotation();
        final String result = getXmlTagFromAnnotation(annotation);
        assertThat(result, CoreMatchers.is("Error"));
    }

    @Test
    public void getXmlTagFromAnnotation_SimpleName_Test() {
        final Ds3Annotation annotation = createSimpleNameAnnotation();
        final String result = getXmlTagFromAnnotation(annotation);
        assertThat(result, CoreMatchers.is(""));
    }

    @Test
    public void getXmlTagFromAnnotation_NonCustomName_Test() {
        final Ds3Annotation annotation = createNonCustomMarshaledNameAnnotation();
        final String result = getXmlTagFromAnnotation(annotation);
        assertThat(result, CoreMatchers.is(""));
    }

    @Test
    public void getXmlTagFromAllAnnotations_NullList_Test() {
        final String result = getXmlTagFromAllAnnotations(null, "ElementName");
        assertThat(result, CoreMatchers.is(""));
    }

    @Test
    public void getXmlTagFromAllAnnotations_EmptyList_Test() {
        final String result = getXmlTagFromAllAnnotations(ImmutableList.of(), "ElementName");
        assertThat(result, CoreMatchers.is(""));
    }

    @Test
    public void getXmlTagFromAllAnnotations_FullList_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                createSimpleNameAnnotation(),
                createNonCustomMarshaledNameAnnotation(),
                createCustomMarshaledNameAnnotation());
        final String result = getXmlTagFromAllAnnotations(annotations, "ElementName");
        assertThat(result, CoreMatchers.is("Error"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getXmlTagFromAllAnnotations_Exception_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                createCustomMarshaledNameAnnotation(),
                createCustomMarshaledNameAnnotation());
        getXmlTagFromAllAnnotations(annotations, "ElementName");
    }

    @Test
    public void getXmlTagName_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                createSimpleNameAnnotation(),
                createNonCustomMarshaledNameAnnotation(),
                createCustomMarshaledNameAnnotation());
        final Ds3Element element = new Ds3Element("ElementName", "Type", "ComponentType", annotations, false);
        final String result = getXmlTagName(element);
        assertThat(result, CoreMatchers.is("Error"));
    }

    @Test
    public void hasWrapperAnnotationElements_NullList_Test() {
        assertThat(hasWrapperAnnotationElements(null), is(false));
    }

    @Test
    public void hasWrapperAnnotationElements_EmptyList_Test() {
        assertThat(hasWrapperAnnotationElements(ImmutableList.of()), is(false));
    }

    @Test
    public void hasWrapperAnnotationElements_HasWrapper_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "Nodes", "java.lang.String"),
                new Ds3AnnotationElement(
                        "CollectionValueRenderingMode",
                        "SINGLE_BLOCK_FOR_ALL_ELEMENTS",
                        "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "Node", "java.lang.String"));
        assertThat(hasWrapperAnnotationElements(annotationElements), is(true));
    }

    @Test
    public void hasWrapperAnnotationElements_NoWrapper_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "", "java.lang.String"),
                new Ds3AnnotationElement(
                        "CollectionValueRenderingMode",
                        "UNDEFINED",
                        "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "object", "java.lang.String"));
        assertThat(hasWrapperAnnotationElements(annotationElements), is(false));
    }

    @Test
    public void hasWrapperAnnotations_NullList_Test() {
        assertThat(hasWrapperAnnotations(null), is(false));
    }

    @Test
    public void hasWrapperAnnotations_EmptyList_Test() {
        assertThat(hasWrapperAnnotations(ImmutableList.of()), is(false));
    }

    @Test
    public void hasWrapperAnnotations_HasWrapper_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "Nodes", "java.lang.String"),
                new Ds3AnnotationElement(
                        "CollectionValueRenderingMode",
                        "SINGLE_BLOCK_FOR_ALL_ELEMENTS",
                        "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "Node", "java.lang.String"));

        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.marshal.CustomMarshaledName", annotationElements));

        assertThat(hasWrapperAnnotations(annotations), is(true));
    }

    @Test
    public void hasWrapperAnnotations_NoWrapper_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "", "java.lang.String"),
                new Ds3AnnotationElement(
                        "CollectionValueRenderingMode",
                        "UNDEFINED",
                        "com.spectralogic.util.marshal.CustomMarshaledName$CollectionNameRenderingMode"),
                new Ds3AnnotationElement("Value", "object", "java.lang.String"));

        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.marshal.CustomMarshaledName", annotationElements));

        assertThat(hasWrapperAnnotations(annotations), is(false));
    }

    @Test
    public void toElementAsAttribute_NullList_Test() {
        assertThat(isAttribute(null), is(false));
    }

    @Test
    public void toElementAsAttribute_EmptyList_Test() {
        assertThat(isAttribute(ImmutableList.of()), is(false));
    }

    @Test
    public void toElementAsAttribute_MarshalXmlAsAttribute_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.marshal.MarshalXmlAsAttribute", null));
        assertThat(isAttribute(annotations), is(true));
    }

    @Test
    public void toElementAsAttribute_FullList_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                createCustomMarshaledNameAnnotation(),
                createNonCustomMarshaledNameAnnotation(),
                createSimpleNameAnnotation());
        assertThat(isAttribute(annotations), is(false));
    }

    @Test
    public void getEncapsulatingTagAnnotationElements_NullList_Test() {
        final String result = getEncapsulatingTagAnnotationElements(null);
        assertThat(result, is(""));
    }

    @Test
    public void getEncapsulatingTagAnnotationElements_EmptyList_Test() {
        final String result = getEncapsulatingTagAnnotationElements(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void getEncapsulatingTagAnnotationElements_FullList_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = ImmutableList.of(
                new Ds3AnnotationElement("CollectionValue", "TestTag", "java.lang.String"),
                new Ds3AnnotationElement("CollectionValueRenderingMode", "UNDEFINED", "com.test.Collection"),
                new Ds3AnnotationElement("Value", "object", "java.lang.String"));

        final String result = getEncapsulatingTagAnnotationElements(annotationElements);
        assertThat(result, is("TestTag"));
    }

    @Test
    public void getEncapsulatingTagAnnotations_NullList_Test() {
        final String result = getEncapsulatingTagAnnotations(null);
        assertThat(result, is(""));
    }

    @Test
    public void getEncapsulatingTagAnnotations_EmptyList_Test() {
        final String result = getEncapsulatingTagAnnotations(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void getEncapsulatingTagAnnotations_FullList_Test() {
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                createCustomMarshaledNameAnnotation(),
                createNonCustomMarshaledNameAnnotation(),
                createSimpleNameAnnotation());

        final String result = getEncapsulatingTagAnnotations(annotations);
        assertThat(result, is("TestCollectionValue"));
    }
}
