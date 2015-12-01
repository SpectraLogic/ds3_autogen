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

package com.spectralogic.ds3autogen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;

import static com.spectralogic.ds3autogen.api.utils.ConverterUtil.isEmpty;

class Ds3SpecConverter {

    protected static ImmutableList<Ds3Request> convertRequests(
            final ImmutableList<Ds3Request> requests,
            final NameMapper nameMapper) {
        if (isEmpty(requests)) {
            return ImmutableList.of();
        }
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
                    convertAllResponseCodes(request.getDs3ResponseCodes(), nameMapper),
                    convertAllParams(request.getOptionalQueryParams(), nameMapper),
                    convertAllParams(request.getRequiredQueryParams(), nameMapper));
            builder.add(convertedRequest);
        }

        return builder.build();
    }

    protected static ImmutableList<Ds3Param> convertAllParams(
            final ImmutableList<Ds3Param> params,
            final NameMapper nameMapper) {
        if (isEmpty(params)) {
            return params;
        }
        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        for (final Ds3Param param : params) {
            final Ds3Param convertedParam = new Ds3Param(
                    param.getName(),
                    convertName(param.getType(), nameMapper));
            builder.add(convertedParam);
        }
        return builder.build();
    }

    protected static ImmutableList<Ds3ResponseCode> convertAllResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes,
            final NameMapper nameMapper) {
        if (isEmpty(responseCodes)) {
            return responseCodes;
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.add(convertResponseCode(responseCode, nameMapper));
        }
        return builder.build();
    }

    protected static Ds3ResponseCode convertResponseCode(
            final Ds3ResponseCode responseCode,
            final NameMapper nameMapper) {
        if (isEmpty(responseCode.getDs3ResponseTypes())) {
            return responseCode;
        }
        final ImmutableList.Builder<Ds3ResponseType> builder = ImmutableList.builder();
        for (final Ds3ResponseType responseType : responseCode.getDs3ResponseTypes()) {
            builder.add(new Ds3ResponseType(
                    convertName(responseType.getType(), nameMapper),
                    convertName(responseType.getComponentType(), nameMapper)));
        }
        return new Ds3ResponseCode(responseCode.getCode(), builder.build());
    }

    protected static ImmutableMap<String, Ds3Type> convertTypes(
            final ImmutableMap<String, Ds3Type> types,
            final NameMapper nameMapper) {
        if (isEmpty(types)) {
            return types;
        }
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();

        for (final ImmutableMap.Entry<String, Ds3Type> entry : types.entrySet()) {
            final Ds3Type ds3Type = new Ds3Type(
                    convertName(entry.getValue().getName(), nameMapper),
                    convertAllElements(entry.getValue().getElements(), nameMapper),
                    convertAllEnumConstants(entry.getValue().getEnumConstants(), nameMapper));
            builder.put(convertName(convertName(entry.getKey(), nameMapper), nameMapper), ds3Type);
        }

        return builder.build();
    }

    protected static ImmutableList<Ds3EnumConstant> convertAllEnumConstants(
            final ImmutableList<Ds3EnumConstant> enumConstants,
            final NameMapper nameMapper) {
        if (isEmpty(enumConstants)) {
            return enumConstants;
        }
        final ImmutableList.Builder<Ds3EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant enumConstant : enumConstants) {
            final Ds3EnumConstant convertedEnumConstant = new Ds3EnumConstant(
                    enumConstant.getName(),
                    convertAllProperties(enumConstant.getDs3Properties(), nameMapper));
            builder.add(convertedEnumConstant);
        }
        return builder.build();
    }

    protected static ImmutableList<Ds3Property> convertAllProperties(
            final ImmutableList<Ds3Property> properties,
            final NameMapper nameMapper) {
        if (isEmpty(properties)) {
            return properties;
        }
        final ImmutableList.Builder<Ds3Property> builder = ImmutableList.builder();
        for (final Ds3Property property : properties) {
            final Ds3Property convertedProperty = new Ds3Property(
                    property.getName(),
                    property.getValue(),
                    convertName(property.getValueType(), nameMapper));
            builder.add(convertedProperty);
        }
        return builder.build();
    }

    protected static ImmutableList<Ds3Element> convertAllElements(
            final ImmutableList<Ds3Element> elements,
            final NameMapper nameMapper) {
        if (isEmpty(elements)) {
            return elements;
        }
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        for (final Ds3Element element : elements) {
            final Ds3Element convertedElement = new Ds3Element(
                    element.getName(),
                    convertName(element.getType(), nameMapper),
                    convertName(element.getComponentType(), nameMapper),
                    convertAllAnnotations(element.getDs3Annotations(), nameMapper));
            builder.add(convertedElement);
        }
        return builder.build();
    }

    protected static ImmutableList<Ds3Annotation> convertAllAnnotations(
            final ImmutableList<Ds3Annotation> annotations,
            final NameMapper nameMapper) {
        if (isEmpty(annotations)) {
            return annotations;
        }
        final ImmutableList.Builder<Ds3Annotation> builder = ImmutableList.builder();
        for (final Ds3Annotation annotation : annotations) {
            final Ds3Annotation convertedAnnotation = new Ds3Annotation(
                    convertName(annotation.getName(), nameMapper),
                    convertAllAnnotationElements(annotation.getDs3AnnotationElements(), nameMapper));
            builder.add(convertedAnnotation);
        }
        return builder.build();
    }

    protected static ImmutableList<Ds3AnnotationElement> convertAllAnnotationElements(
            final ImmutableList<Ds3AnnotationElement> annotationElements,
            final NameMapper nameMapper) {
        if (isEmpty(annotationElements)) {
            return annotationElements;
        }
        final ImmutableList.Builder<Ds3AnnotationElement> builder = ImmutableList.builder();
        for (final Ds3AnnotationElement annotationElement : annotationElements) {
            final Ds3AnnotationElement convertedElement = new Ds3AnnotationElement(
                    annotationElement.getName(),
                    convertName(annotationElement.getValue(), nameMapper),
                    convertNameWithDollarSign(annotationElement.getValueType(), nameMapper));
            builder.add(convertedElement);
        }
        return builder.build();
    }

    protected static String convertNameWithDollarSign(final String name, final NameMapper nameMapper) {
        final String[] parts = name.split("\\$");
        if (parts.length < 2) {
            return convertName(name, nameMapper);
        }
        return convertName(parts[0], nameMapper) + "$" + parts[1];
    }

    protected static String convertName(final String name, final NameMapper nameMapper) {
        if (nameMapper.containsName(name)) {
            return nameMapper.getConvertedName(name);
        }
        return name;
    }
}
