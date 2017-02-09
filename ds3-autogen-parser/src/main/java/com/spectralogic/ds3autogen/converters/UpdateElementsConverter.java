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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.*;

import java.util.Map;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Updates the Ds3Elements within Ds3Types where:
 * Element with ExcludedFromMarshaler = ALWAYS will be removed from elements list
 * Element with ExcludedFromMarshaler = VALUE_IS_NULL will be marked as nullable
 */
public class UpdateElementsConverter {

    protected enum ExcludeType { VALUE_IS_NULL, ALWAYS }

    private UpdateElementsConverter() {
        //pass
    }

    /**
     * Updates the Ds3ApiSpec's Ds3Elements to properly denote nullablility or exclusion
     */
    public static Ds3ApiSpec updateElementsInSpec(final Ds3ApiSpec ds3ApiSpec) {
        return new Ds3ApiSpec(
                ds3ApiSpec.getRequests(),
                updateElementsInTypeMap(ds3ApiSpec.getTypes()));
    }

    /**
     * Updates all Ds3Type's Ds3Elements to properly denote nullability or exclusion
     */
    protected static ImmutableMap<String, Ds3Type> updateElementsInTypeMap(final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeMap)) {
            return ImmutableMap.of();
        }
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();
        for (final Map.Entry<String, Ds3Type> entry : typeMap.entrySet()) {
            builder.put(entry.getKey(), updateElementsInType(entry.getValue()));
        }
        return builder.build();
    }

    /**
     * Updates the Ds3Type's Ds3Elements to properly denote nullability or exclusion
     */
    protected static Ds3Type updateElementsInType(final Ds3Type ds3Type) {
        return new Ds3Type(
                ds3Type.getName(),
                ds3Type.getNameToMarshal(),
                updateAllElements(ds3Type.getElements()),
                ds3Type.getEnumConstants());
    }

    /**
     * Updates the Ds3Elements. Elements with a value of exclude-always will be removed from the list,
     * and elements with exclude-when-value-is-null will  be marked as nullable
     */
    protected static ImmutableList<Ds3Element> updateAllElements(final ImmutableList<Ds3Element> elements) {
        if (isEmpty(elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        for (final Ds3Element element : elements) {
            final Ds3Element updated = updateElement(element);
            if (updated != null) {
                builder.add(updated);
            }
        }
        return builder.build();
    }

    /**
     * Updates the nullability of an element when marked as exclude-when-value-is-null. If the
     * element is marked as exclude-always, then null is returned. Else, the element is returned
     * unmodified.
     */
    protected static Ds3Element updateElement(final Ds3Element element) {
        if (!hasExcludeValue(element.getDs3Annotations())) {
            return element;
        }
        final ExcludeType type = getExcludeValueFromAnnotationsList(element.getDs3Annotations());
        switch (type) {
            case ALWAYS:
                return null;
            case VALUE_IS_NULL:
                return new Ds3Element(
                        element.getName(),
                        element.getType(),
                        element.getComponentType(),
                        element.getDs3Annotations(),
                        true);
            default:
                throw new IllegalArgumentException("Invalid value for ExcludeFromMarshaler: " + type.toString()); //Should never happen
        }
    }

    /**
     * Retrieves the type of ExcludeFromMarshaler within a list of ds3Annotations
     */
    protected static ExcludeType getExcludeValueFromAnnotationsList(final ImmutableList<Ds3Annotation> annotations) {
        if (isEmpty(annotations)) {
            throw new IllegalArgumentException("Ds3Element does not have any Ds3Annotations");
        }
        for (final Ds3Annotation annotation : annotations) {
            if (annotation.getName().endsWith("ExcludeFromMarshaler")) {
                return getExcludeValueFromElementsList(annotation.getDs3AnnotationElements());
            }
        }
        throw new IllegalArgumentException("Ds3Element did not contain the ExcludeFromMarshaler annotation");
    }

    /**
     * Retrieves the type of ExcludeFromMarshaler within a ds3Annotation
     */
    protected static ExcludeType getExcludeValueFromElementsList(final ImmutableList<Ds3AnnotationElement> annotationElements) {
        if (isEmpty(annotationElements)) {
            throw new IllegalArgumentException("Ds3Annotation does not have any Ds3AnnotationElements");
        }
        for (final Ds3AnnotationElement annotationElement : annotationElements) {
            if (annotationElement.getValue().equals("VALUE_IS_NULL")) {
                return ExcludeType.VALUE_IS_NULL;
            }
            switch (annotationElement.getValue()) {
                case "VALUE_IS_NULL":
                    return ExcludeType.VALUE_IS_NULL;
                case "ALWAYS":
                    return ExcludeType.ALWAYS;
                default:
                    //do nothing
            }
        }
        throw new IllegalArgumentException("Ds3Annotation does not contain an ExcludeFromMarshaler value");
    }

    /**
     * Determines if a list of annotations contains the ExcludeFromMarshaler annotation
     */
    protected static boolean hasExcludeValue(final ImmutableList<Ds3Annotation> annotations) {
        if (isEmpty(annotations)) {
            return false;
        }
        for (final Ds3Annotation annotation : annotations) {
            if (annotation.getName().endsWith("ExcludeFromMarshaler")) {
                return true;
            }
        }
        return false;
    }
}
