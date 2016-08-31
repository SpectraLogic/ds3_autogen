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
import com.spectralogic.ds3autogen.api.models.apispec.*;

import java.util.Map;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Converts all param and response type names within the Ds3ApiSpec to
 * remove all instances of the dollar symbol '$'
 */
public final class RemoveDollarSignConverter {

    private RemoveDollarSignConverter() { }

    /**
     * Removes all instances of '$' from type names within the Ds3ApiSpec
     */
    public static Ds3ApiSpec removeDollarSigns(final Ds3ApiSpec ds3ApiSpec) {
        return new Ds3ApiSpec(
                removeDollarSignFromAllRequests(ds3ApiSpec.getRequests()),
                removeDollarSignFromTypeMap(ds3ApiSpec.getTypes()));
    }

    /**
     * Removes all instances of '$' from type names within a Ds3Request
     */
    protected static ImmutableList<Ds3Request> removeDollarSignFromAllRequests(
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request request : requests) {
            builder.add(removeDollarSignFromRequest(request));
        }
        return builder.build();
    }

    /**
     * Removes all instances of '$' from request name, Params, and Response types
     */
    protected static Ds3Request removeDollarSignFromRequest(final Ds3Request request) {
        return new Ds3Request(
                removeDollarSignFromName(request.getName()),
                request.getHttpVerb(),
                request.getClassification(),
                request.getBucketRequirement(),
                request.getObjectRequirement(),
                request.getAction(),
                request.getResource(),
                request.getResourceType(),
                request.getOperation(),
                request.includeIdInPath(),
                removeDollarSignFromAllResponseCodes(request.getDs3ResponseCodes()),
                removeDollarSignFromAllParams(request.getOptionalQueryParams()),
                removeDollarSignFromAllParams(request.getRequiredQueryParams()));
    }

    /**
     * Removes all instances of '$' from a list of Ds3ResponseCodes
     */
    protected static ImmutableList<Ds3ResponseCode> removeDollarSignFromAllResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.add(removeDollarSignFromResponseCode(responseCode));
        }
        return builder.build();
    }

    /**
     * Removes all instances of '$' from response types within Ds3ResponseCode
     */
    protected static Ds3ResponseCode removeDollarSignFromResponseCode(
            final Ds3ResponseCode responseCode) {
        return new Ds3ResponseCode(
                responseCode.getCode(),
                removeDollarSignFromAllResponseTypes(responseCode.getDs3ResponseTypes()));
    }

    /**
     * Removes all instances of '$' from list of Ds3ResponseTypes
     */
    protected static ImmutableList<Ds3ResponseType> removeDollarSignFromAllResponseTypes(
            final ImmutableList<Ds3ResponseType> responseTypes) {
        if (isEmpty(responseTypes)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3ResponseType> builder = ImmutableList.builder();
        for (final Ds3ResponseType responseType : responseTypes) {
            builder.add(removeDollarSignFromResponseType(responseType));
        }
        return builder.build();
    }

    /**
     * Removes '$' from type and component type if it is present
     */
    protected static Ds3ResponseType removeDollarSignFromResponseType(
            final Ds3ResponseType responseType) {
        return new Ds3ResponseType(
                removeDollarSignFromName(responseType.getType()),
                removeDollarSignFromName(responseType.getComponentType()),
                responseType.getOriginalTypeName());
    }

    /**
     * Removes all instances of '$' from list of Ds3Params
     */
    protected static ImmutableList<Ds3Param> removeDollarSignFromAllParams(
            final ImmutableList<Ds3Param> params) {
        if (isEmpty(params)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        for (final Ds3Param param : params) {
            builder.add(removeDollarSignFromParam(param));
        }
        return builder.build();
    }

    /**
     * Removes '$' from Ds3Param type if it is present
     */
    protected static Ds3Param removeDollarSignFromParam(final Ds3Param param) {
        return new Ds3Param(
                param.getName(),
                removeDollarSignFromName(param.getType()),
                param.isNullable());
    }

    /**
     * Removes all instances of '$' from Type Map, including the key value and
     * the Ds3Type. This also checks to see if the renaming of a type conflicts
     * with any other type within the map. If there is a conflict, an exception
     * is thrown.
     */
    public static ImmutableMap<String, Ds3Type> removeDollarSignFromTypeMap(
            final ImmutableMap<String, Ds3Type> map) {
        if (isEmpty(map)) {
            return ImmutableMap.of();
        }
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();
        for (final Map.Entry<String, Ds3Type> entry : map.entrySet()) {
            final String typeName = removeDollarSignFromName(entry.getKey());
            final Ds3Type type = removeDollarSignFromType(entry.getValue());
            if (!containsType(typeName, entry.getValue(), builder.build())) {
                builder.put(typeName, type);
            }
        }
        return builder.build();
    }

    /**
     * Checks if a type exists within the type map
     * @throws TypeRenamingConflictException Exception thrown if the map contains
     *         a type with the same name but different type
     */
    protected static boolean containsType(
            final String typeName,
            final Ds3Type type,
            final ImmutableMap<String, Ds3Type> map) {
        if (isEmpty(map)) {
            return false;
        }
        final Ds3Type mapType = map.get(typeName);
        if (mapType == null) {
            return false;
        }
        if (mapType.equals(type)) {
            return true;
        }
        throw new TypeRenamingConflictException(typeName);
    }

    /**
     * Removes '$' from Ds3Type name and all Ds3Elements
     */
    public static Ds3Type removeDollarSignFromType(final Ds3Type type) {
        return new Ds3Type(
                removeDollarSignFromName(type.getName()),
                type.getNameToMarshal(),
                removeDollarSignFromAllElements(type.getElements()),
                type.getEnumConstants());
    }

    /**
     * Removes all instances of '$' from list of Ds3Elements
     */
    public static ImmutableList<Ds3Element> removeDollarSignFromAllElements(
            final ImmutableList<Ds3Element> elements) {
        if (isEmpty(elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        for (final Ds3Element element : elements) {
            builder.add(removeDollarSignFromElement(element));
        }
        return builder.build();
    }

    /**
     * Removes '$' from Ds3Element type, component type, and annotations
     */
    protected static Ds3Element removeDollarSignFromElement(final Ds3Element element) {
        return new Ds3Element(
                element.getName(),
                removeDollarSignFromName(element.getType()),
                removeDollarSignFromName(element.getComponentType()),
                removeDollarSignFromAllAnnotations(element.getDs3Annotations()),
                element.isNullable());
    }

    /**
     * Removes all instances of '$' from list of Ds3Annotations
     */
    protected static ImmutableList<Ds3Annotation> removeDollarSignFromAllAnnotations(
            final ImmutableList<Ds3Annotation> annotations) {
        if (isEmpty(annotations)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Annotation> builder = ImmutableList.builder();
        for (final Ds3Annotation annotation : annotations) {
            builder.add(removeDollarSignFromAnnotation(annotation));
        }
        return builder.build();
    }

    /**
     * Removes '$' from Ds3Annotation name and list of Ds3AnnotationElements
     */
    protected static Ds3Annotation removeDollarSignFromAnnotation(final Ds3Annotation annotation) {
        return new Ds3Annotation(
                removeDollarSignFromName(annotation.getName()),
                removeDollarSignFromAllAnnotationElements(annotation.getDs3AnnotationElements()));
    }

    /**
     * Removes all instances of '$' from list of Ds3AnnotationElements
     */
    protected static ImmutableList<Ds3AnnotationElement> removeDollarSignFromAllAnnotationElements(
            final ImmutableList<Ds3AnnotationElement> annotationElements) {
        if (isEmpty(annotationElements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3AnnotationElement> builder = ImmutableList.builder();
        for (final Ds3AnnotationElement annotationElement : annotationElements) {
            builder.add(removeDollarSignFromAnnotationElement(annotationElement));
        }
        return builder.build();
    }

    /**
     * Removes '$' from Ds3AnnotationElement's value type
     */
    protected static Ds3AnnotationElement removeDollarSignFromAnnotationElement(
            final Ds3AnnotationElement annotationElement) {
        return new Ds3AnnotationElement(
                annotationElement.getName(),
                annotationElement.getValue(),
                removeDollarSignFromName(annotationElement.getValueType()));
    }

    /**
     * Renames a given type if it contains a '$' character. The path will remain the same,
     * but the type name will be changed to what is located after the '$' character. This
     * is used to create proper type names within the generated code.
     * Example:
     *   input:  com.test.package.One$Two
     *   output: com.test.package.Two
     */
    protected static String removeDollarSignFromName(final String typeName) {
        if (isEmpty(typeName) || !typeName.contains("$")) {
            return typeName;
        }
        return getPathOfType(typeName, '.') + typeName.substring(typeName.lastIndexOf('$') + 1);
    }

    /**
     * Gets the path from a string. This is used to get the path associated with types.
     * @param typeName The Type Name, which may include a path
     * @param pathDelimiter The path delimiter character
     */
    public static String getPathOfType(final String typeName, final char pathDelimiter) {
        if (isEmpty(typeName)) {
            return "";
        }
        return typeName.substring(0, typeName.lastIndexOf(pathDelimiter) + 1);
    }
}
