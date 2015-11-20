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

package com.spectralogic.d3autogen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;

public class Ds3SpecConverter {

    private static final NameMapper nameMapper = NameMapper.getInstance();

    public static ImmutableList<Ds3Request> convertRequests(
            final ImmutableList<Ds3Request> requests) {
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();

        for (final Ds3Request request : requests) {
            final Ds3Request convertedRequest = new Ds3Request(
                    request.getName(),
                    request.getHttpVerb(),
                    request.getClassification(),
                    request.getBucketRequirement(),
                    request.getObjectRequirement(),
                    request.getAction(),
                    request.getResource(),
                    request.getResourceType(),
                    request.getOperation(),
                    convertAllResponseCodes(request.getDs3ResponseCodes()),
                    convertAllParams(request.getOptionalQueryParams()),
                    convertAllParams(request.getRequiredQueryParams()));
            builder.add(convertedRequest);
        }

        return builder.build();
    }

    private static ImmutableList<Ds3Param> convertAllParams(final ImmutableList<Ds3Param> params) {
        if (params == null || params.isEmpty()) {
            return params;
        }
        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        for (final Ds3Param param : params) {
            final Ds3Param convertedParam = new Ds3Param(
                    param.getName(),
                    convertName(param.getType()));
            builder.add(convertedParam);
        }
        return builder.build();
    }

    private static ImmutableList<Ds3ResponseCode> convertAllResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (responseCodes == null || responseCodes.isEmpty()) {
            return responseCodes;
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.add(convertResponseCode(responseCode));
        }
        return builder.build();
    }

    private static Ds3ResponseCode convertResponseCode(final Ds3ResponseCode responseCode) {
        final ImmutableList.Builder<Ds3ResponseType> builder = ImmutableList.builder();
        for (final Ds3ResponseType responseType : responseCode.getDs3ResponseTypes()) {
            builder.add(new Ds3ResponseType(
                    convertName(responseType.getType()),
                    convertName(responseType.getComponentType())));
        }
        return new Ds3ResponseCode(responseCode.getCode(), builder.build());
    }

    public static ImmutableMap<String, Ds3Type> convertTypes(
            final ImmutableMap<String, Ds3Type> types) {
        if (types == null || types.isEmpty()) {
            return types;
        }
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();

        for (final ImmutableMap.Entry<String, Ds3Type> entry : types.entrySet()) {
            final Ds3Type ds3Type = new Ds3Type(
                    convertName(entry.getValue().getName()),
                    convertAllElements(entry.getValue().getElements()),
                    convertAllEnumConstants(entry.getValue().getEnumConstants()));
            builder.put(convertName(entry.getKey()), ds3Type);
        }

        return builder.build();
    }

    private static ImmutableList<Ds3EnumConstant> convertAllEnumConstants(
            final ImmutableList<Ds3EnumConstant> enumConstants) {
        if (enumConstants == null || enumConstants.isEmpty()) {
            return enumConstants;
        }
        ImmutableList.Builder<Ds3EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant enumConstant : enumConstants) {
            final Ds3EnumConstant convertedEnumConstant = new Ds3EnumConstant(
                    enumConstant.getName(),
                    convertAllProperties(enumConstant.getDs3Properties()));
            builder.add(convertedEnumConstant);
        }
        return builder.build();
    }

    private static ImmutableList<Ds3Property> convertAllProperties(
            final ImmutableList<Ds3Property> properties) {
        if (properties == null || properties.isEmpty()) {
            return properties;
        }
        ImmutableList.Builder<Ds3Property> builder = ImmutableList.builder();
        for (final Ds3Property property : properties) {
            final Ds3Property convertedProperty = new Ds3Property(
                    property.getName(),
                    property.getValue(),
                    convertName(property.getValueType()));
            builder.add(convertedProperty);
        }
        return builder.build();
    }

    private static ImmutableList<Ds3Element> convertAllElements(final ImmutableList<Ds3Element> elements) {
        if (elements == null || elements.isEmpty()) {
            return elements;
        }
        ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        for (final Ds3Element element : elements) {
            final Ds3Element convertedElement = new Ds3Element(
                    element.getName(),
                    convertName(element.getType()),
                    element.getComponentType(),
                    convertAllAnnotations(element.getDs3Annotations()));
            builder.add(convertedElement);
        }
        return builder.build();
    }

    private static ImmutableList<Ds3Annotation> convertAllAnnotations(
            final ImmutableList<Ds3Annotation> annotations) {
        if (annotations == null || annotations.isEmpty()) {
            return annotations;
        }
        ImmutableList.Builder<Ds3Annotation> builder = ImmutableList.builder();
        for (final Ds3Annotation annotation : annotations) {
            final Ds3Annotation convertedAnnotation = new Ds3Annotation(
                    convertName(annotation.getName()),
                    convertAllAnnotationElements(annotation.getDs3AnnotationElements()));
            builder.add(convertedAnnotation);
        }
        return builder.build();
    }

    private static ImmutableList<Ds3AnnotationElement> convertAllAnnotationElements(
            final ImmutableList<Ds3AnnotationElement> annotationElements) {
        if (annotationElements == null || annotationElements.isEmpty()) {
            return annotationElements;
        }
        ImmutableList.Builder<Ds3AnnotationElement> builder = ImmutableList.builder();
        for (final Ds3AnnotationElement annotationElement : annotationElements) {
            final Ds3AnnotationElement convertedElement = new Ds3AnnotationElement(
                    annotationElement.getName(),
                    convertName(annotationElement.getValue()),
                    convertNameWithDollarSign(annotationElement.getValueType()));
            builder.add(convertedElement);
        }
        return builder.build();
    }

    private static String convertNameWithDollarSign(final String name) {
        final String[] parts = name.split("\\$");
        if (parts.length < 2) {
            return convertName(name);
        }
        return convertName(parts[0]) + "$" + parts[1];
    }

    private static String convertName(final String name) {
        if (nameMapper.containsName(name)) {
            return nameMapper.getConvertedName(name);
        }
        return name;
    }
}
