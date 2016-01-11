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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.converters.RemoveDollarSignConverter.*;
import static com.spectralogic.ds3autogen.test.helpers.RemoveDollarSignConverterHelper.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class RemoveDollarSignConverter_Test {

    @Test
    public void getPathOfType_Test() {
        assertThat(getPathOfType(null, '.'), is(""));
        assertThat(getPathOfType("", '.'), is(""));
        assertThat(getPathOfType("SimpleType", '.'), is(""));

        assertThat(
                getPathOfType("com.spectralogic.s3.common.dao.domain.ds3.SystemFailure", '.'),
                is("com.spectralogic.s3.common.dao.domain.ds3."));
        assertThat(
                getPathOfType("com/spectralogic/s3/common/dao/domain/ds3/SystemFailure", '/'),
                is("com/spectralogic/s3/common/dao/domain/ds3/"));
        assertThat(
                getPathOfType("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.TapeFailuresResponseBuilder$TapeFailuresApiBean", '.'),
                is("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape."));
    }

    @Test
    public void removeDollarSignFromName_Test() {
        assertThat(removeDollarSignFromName(null), is(nullValue()));
        assertThat(removeDollarSignFromName(""), is(""));
        assertThat(removeDollarSignFromName("SimpleType"), is("SimpleType"));

        assertThat(
                removeDollarSignFromName("com.spectralogic.s3.common.dao.domain.ds3.SystemFailure"),
                is("com.spectralogic.s3.common.dao.domain.ds3.SystemFailure"));
        assertThat(
                removeDollarSignFromName("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.TapeFailuresResponseBuilder$TapeFailuresApiBean"),
                is("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.TapeFailuresApiBean"));
    }

    @Test
    public void removeDollarSignFromAnnotationElement_Test() {
        final Ds3AnnotationElement simpleAnnotationElement = new Ds3AnnotationElement("Name", "Vale", "ValueType");
        final Ds3AnnotationElement simpleResult = removeDollarSignFromAnnotationElement(simpleAnnotationElement);
        assertThat(simpleResult.getValueType(), is("ValueType"));

        final Ds3AnnotationElement annotationElement = new Ds3AnnotationElement("Name", "Value", "com.test.package.One$Two");
        final Ds3AnnotationElement result = removeDollarSignFromAnnotationElement(annotationElement);
        assertThat(result.getValueType(), is("com.test.package.Two"));
    }

    @Test
    public void removeDollarSignFromAllAnnotationElements_NullList_Test() {
        final ImmutableList<Ds3AnnotationElement> result = removeDollarSignFromAllAnnotationElements(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllAnnotationElements_EmptyList_Test() {
        final ImmutableList<Ds3AnnotationElement> result = removeDollarSignFromAllAnnotationElements(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllAnnotationElements_FullList_Test() {
        final ImmutableList<Ds3AnnotationElement> annotationElements = createPopulatedAnnotationElements();
        final ImmutableList<Ds3AnnotationElement> result = removeDollarSignFromAllAnnotationElements(annotationElements);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getValueType(), is(annotationElements.get(0).getValueType()));
        assertThat(result.get(1).getValueType(), is("com.spectralogic.util.marshal.CollectionNameRenderingMode"));
    }

    @Test
    public void removeDollarSignFromAnnotation_Test() {
        final Ds3Annotation annotation = new Ds3Annotation(
                "com.test.package.One$Two",
                createPopulatedAnnotationElements());

        final Ds3Annotation result = removeDollarSignFromAnnotation(annotation);
        assertThat(result.getName(), is("com.test.package.Two"));

        assertThat(result.getDs3AnnotationElements().size(), is(2));
        assertThat(result.getDs3AnnotationElements().get(0).getValueType(),
                is(annotation.getDs3AnnotationElements().get(0).getValueType()));
        assertThat(result.getDs3AnnotationElements().get(1).getValueType(),
                is("com.spectralogic.util.marshal.CollectionNameRenderingMode"));
    }

    @Test
    public void removeDollarSignFromAllAnnotations_NullList_Test() {
        final ImmutableList<Ds3Annotation> result = removeDollarSignFromAllAnnotations(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllAnnotations_EmptyList_Test() {
        final ImmutableList<Ds3Annotation> result = removeDollarSignFromAllAnnotations(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllAnnotations_FullList_Test() {
        final ImmutableList<Ds3Annotation> annotations = createPopulatedAnnotations();
        final ImmutableList<Ds3Annotation> result = removeDollarSignFromAllAnnotations(annotations);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("com.spectralogic.util.bean.lang.Direction"));
        assertThat(result.get(1).getName(), is(annotations.get(1).getName()));

        final ImmutableList<Ds3AnnotationElement> elements = result.get(1).getDs3AnnotationElements();
        assertThat(elements.size(), is(2));
        assertThat(elements.get(0).getValueType(),
                is(annotations.get(1).getDs3AnnotationElements().get(0).getValueType()));
        assertThat(elements.get(1).getValueType(),
                is("com.spectralogic.util.marshal.CollectionNameRenderingMode"));
    }

    @Test
    public void removeDollarSignFromElement_Test() {
        final Ds3Element element = new Ds3Element(
                "Name",
                "com.test.package.One$Two",
                "com.test.package.Three$Four",
                createPopulatedAnnotations());

        final Ds3Element result = removeDollarSignFromElement(element);
        assertThat(result.getName(), is(element.getName()));
        assertThat(result.getType(), is("com.test.package.Two"));
        assertThat(result.getComponentType(), is("com.test.package.Four"));

        assertThat(result.getDs3Annotations().size(), is(2));
        assertThat(result.getDs3Annotations().get(0).getName(), is("com.spectralogic.util.bean.lang.Direction"));
    }

    @Test
    public void removeDollarSignFromAllElements_NullList_Test() {
        final ImmutableList<Ds3Element> result = removeDollarSignFromAllElements(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllElements_EmptyList_Test() {
        final ImmutableList<Ds3Element> result = removeDollarSignFromAllElements(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllElements_FullList_Test() {
        final ImmutableList<Ds3Element> elements = createPopulatedElements();
        final ImmutableList<Ds3Element> result = removeDollarSignFromAllElements(elements);

        assertThat(result.size(), is(2));
        assertThat(result.get(0).getType(), is("com.test.package.Two"));
        assertThat(result.get(0).getComponentType(), is("com.test.package.Four"));

        assertThat(result.get(1).getType(), is("com.test.package.Six"));
        assertThat(result.get(1).getComponentType(), is("com.test.package.Eight"));

        assertThat(result.get(1).getDs3Annotations().size(), is(2));
        assertThat(result.get(1).getDs3Annotations().get(0).getName(), is("com.spectralogic.util.bean.lang.Direction"));
    }

    @Test
    public void removeDollarSignFromType_Test() {
        final Ds3Type type = createPopulatedType("");
        final Ds3Type result = removeDollarSignFromType(type);
        checkAutoPopulatedType(result, "");
    }

    @Test
    public void containsType_NullMap_Test() throws TypeRenamingConflictException {
        assertFalse(containsType("typeName", new Ds3Type("", null, null), null));
    }

    @Test
    public void containsType_EmptyMap_Test() throws TypeRenamingConflictException {
        assertFalse(containsType("typeName", new Ds3Type("", null, null), ImmutableMap.of()));
    }

    @Test
    public void containsType_FullMap_Test() throws TypeRenamingConflictException {
        final Ds3Type type1 =  new Ds3Type(
                "com.test.package.Type1",
                createElementsForMap(""),
                createEnumConstantsForMap(""));
        final Ds3Type type2 = new Ds3Type(
                "com.test.package.Type2",
                createElementsForMap("_v1"),
                createEnumConstantsForMap("_v1"));

        final ImmutableMap<String, Ds3Type> map = ImmutableMap.of(
                type1.getName(), type1,
                type2.getName(), type2);

        assertTrue(containsType(type1.getName(), type1, map));
        assertTrue(containsType(type2.getName(), type2, map));

        final Ds3Type type3 = new Ds3Type(
                "com.test.package.Type3",
                createElementsForMap("_v2"),
                createEnumConstantsForMap("_v2"));

        assertFalse(containsType(type3.getName(), type3, map));
    }

    @Test (expected = TypeRenamingConflictException.class)
    public void containsType_Exception_Test() throws TypeRenamingConflictException {
        final Ds3Type type =  new Ds3Type(
                "com.test.package.Type",
                createElementsForMap(""),
                createEnumConstantsForMap(""));

        final ImmutableMap<String, Ds3Type> map = ImmutableMap.of(type.getName(), type);

        final Ds3Type typeWithSameName =  new Ds3Type(
                "com.test.package.Type",
                createElementsForMap("_v1"),
                createEnumConstantsForMap("_v1"));

        containsType(typeWithSameName.getName(), typeWithSameName, map);
    }

    @Test
    public void removeDollarSignFromTypeMap_NullMap_Test() throws TypeRenamingConflictException {
        final ImmutableMap<String, Ds3Type> map = removeDollarSignFromTypeMap(null);
        assertThat(map.size(), is(0));
    }

    @Test
    public void removeDollarSignFromTypeMap_EmptyMap_Test() throws TypeRenamingConflictException {
        final ImmutableMap<String, Ds3Type> map = removeDollarSignFromTypeMap(ImmutableMap.of());
        assertThat(map.size(), is(0));
    }

    @Test
    public void removeDollarSignFromTypeMap_FullMap_Test() throws TypeRenamingConflictException {
        final ImmutableMap<String, Ds3Type> map = createPopulatedMap();
        final ImmutableMap<String, Ds3Type> result = removeDollarSignFromTypeMap(map);
        checkAutoPopulatedMap(result);
    }

    @Test (expected = TypeRenamingConflictException.class)
    public void removeDollarSignFromTypeMap_Exception_Test() throws TypeRenamingConflictException {
        final Ds3Type type1 =  new Ds3Type(
                "com.test.package.Type",
                createElementsForMap("_v1"),
                createEnumConstantsForMap("_v1"));
        final Ds3Type type2 = new Ds3Type(
                "com.test.package.Base$Type",
                createElementsForMap("_v2"),
                createEnumConstantsForMap("_v2"));

        final ImmutableMap<String, Ds3Type> map = ImmutableMap.of(
                type1.getName(), type1,
                type2.getName(), type2);

        removeDollarSignFromTypeMap(map);
    }

    @Test
    public void removeDollarSignFromParam_Test() {
        final Ds3Param param = new Ds3Param("Name", "com.test.package.Base$Type");
        final Ds3Param result = removeDollarSignFromParam(param);
        assertThat(result.getName(), is(param.getName()));
        assertThat(result.getType(), is("com.test.package.Type"));
    }

    @Test
    public void removeDollarSignFromAllParams_NullList_Test() {
        final ImmutableList<Ds3Param> result = removeDollarSignFromAllParams(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllParams_EmptyList_Test() {
        final ImmutableList<Ds3Param> result = removeDollarSignFromAllParams(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllParams_FullList_Test() {
        final ImmutableList<Ds3Param> params = createPopulatedParams("");
        final ImmutableList<Ds3Param> result = removeDollarSignFromAllParams(params);
        checkAutoPopulatedParams(result, "");
    }

    @Test
    public void removeDollarSignFromResponseType_Test() {
        final Ds3ResponseType responseType = new Ds3ResponseType(
                "com.test.package.Base$Type1",
                "com.test.package.Base$Type2");
        final Ds3ResponseType result = removeDollarSignFromResponseType(responseType);
        assertThat(result.getType(), is("com.test.package.Type1"));
        assertThat(result.getComponentType(), is("com.test.package.Type2"));
    }

    @Test
    public void removeDollarSignFromAllResponseTypes_NullList_Test() {
        final ImmutableList<Ds3ResponseType> result = removeDollarSignFromAllResponseTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllResponseTypes_EmptyList_Test() {
        final ImmutableList<Ds3ResponseType> result = removeDollarSignFromAllResponseTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllResponseTypes_FullList_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = createPopulatedResponseTypes();
        final ImmutableList<Ds3ResponseType> result = removeDollarSignFromAllResponseTypes(responseTypes);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getType(), is("com.test.package.Type1"));
        assertThat(result.get(0).getComponentType(), is("com.test.package.Type2"));
        assertThat(result.get(1).getType(), is("com.test.package.Type3"));
        assertThat(result.get(1).getComponentType(), is("com.test.package.Type4"));
    }

    @Test
    public void removeDollarSignFromResponseCode_Test() {
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200, createPopulatedResponseTypes());
        final Ds3ResponseCode result = removeDollarSignFromResponseCode(responseCode);
        assertThat(result.getDs3ResponseTypes().size(), is(2));
        assertThat(result.getDs3ResponseTypes().get(0).getType(), is("com.test.package.Type1"));
        assertThat(result.getDs3ResponseTypes().get(0).getComponentType(), is("com.test.package.Type2"));
        assertThat(result.getDs3ResponseTypes().get(1).getType(), is("com.test.package.Type3"));
        assertThat(result.getDs3ResponseTypes().get(1).getComponentType(), is("com.test.package.Type4"));
    }

    @Test
    public void removeDollarSignFromAllResponseCodes_NullList_Test() {
        final ImmutableList<Ds3ResponseCode> result = removeDollarSignFromAllResponseCodes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllResponseCodes_EmptyList_Test() {
        final ImmutableList<Ds3ResponseCode> result = removeDollarSignFromAllResponseCodes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllResponseCodes_FullList_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = createPopulatedResponseCodes();
        final ImmutableList<Ds3ResponseCode> result = removeDollarSignFromAllResponseCodes(responseCodes);
        checkAutoPopulatedResponseCodes(result);
    }

    @Test
    public void removeDollarSignFromRequest_Test() {
        final Ds3Request request = createPopulatedRequest("");
        final Ds3Request result = removeDollarSignFromRequest(request);
        assertThat(result.getName(), is("com.test.package.RequestType"));
        checkAutoPopulatedRequest(result, "");
    }

    @Test
    public void removeDollarSignFromAllRequests_NullList_Test() {
        final ImmutableList<Ds3Request> result = removeDollarSignFromAllRequests(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllRequests_EmptyList_Test() {
        final ImmutableList<Ds3Request> result = removeDollarSignFromAllRequests(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDollarSignFromAllRequests_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createPopulatedRequest("_v1"),
                createPopulatedRequest("_v2"));
        final ImmutableList<Ds3Request> result = removeDollarSignFromAllRequests(requests);
        assertThat(result.size(), is(2));
        checkAutoPopulatedRequest(result.get(0), "_v1");
        checkAutoPopulatedRequest(result.get(1), "_v2");
    }

    @Test
    public void removeDollarSigns_Test() throws TypeRenamingConflictException {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createPopulatedRequest("_v1"),
                createPopulatedRequest("_v2"));

        final Ds3ApiSpec spec = new Ds3ApiSpec(requests, createPopulatedMap());
        final Ds3ApiSpec result = removeDollarSigns(spec);

        assertThat(result.getRequests().size(), is(2));
        checkAutoPopulatedRequest(result.getRequests().get(0), "_v1");
        checkAutoPopulatedRequest(result.getRequests().get(1), "_v2");
        checkAutoPopulatedMap(result.getTypes());
    }
}
