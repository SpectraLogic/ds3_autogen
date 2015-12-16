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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;

import java.util.Collection;
import java.util.Map;

public final class ConverterUtil {

    private static final String CONTRACT_DEFINED_TYPE = "com.spectralogic.";

    private ConverterUtil() { }

    public static boolean hasContent(final Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean hasContent(final Map<?,?> map) {
        return !isEmpty(map);
    }

    public static boolean hasContent(final String string) {
        return !isEmpty(string);
    }

    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    /**
     * Checks if two enums of the same class have the same value
     * @param leftEnum An enum
     * @param rightEnum An enum
     * @param <E> The class of leftEnum and rightEnum
     * @return True if both enums are of the same non-null value. False is returned if either
     *         enum is null or if the enums do not have the same non-null value.
     */
    protected static <E extends Enum<E>> boolean enumsEqual(final E leftEnum, final E rightEnum) {
        if (leftEnum == null || rightEnum == null) {
            return false;
        }
        return leftEnum == rightEnum;
    }

    public static ImmutableList<Ds3Request> removeSpectraInternalRequests(
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request request : requests) {
            if (request.getClassification() != Classification.spectrainternal) {
                builder.add(request);
            }
        }
        return builder.build();
    }

    public static ImmutableMap<String, Ds3Type> removeUnusedTypes(
            final ImmutableMap<String, Ds3Type> types,
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(types) || isEmpty(requests)) {
            return ImmutableMap.of();
        }
        final ImmutableSet<String> usedTypes = getUsedTypesFromRequests(requests);
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();
        for (final Map.Entry<String, Ds3Type> entry : types.entrySet()) {
            if (usedTypes.contains(entry.getKey())) {
                builder.put(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    protected static ImmutableSet<String> getUsedTypesFromAllTypes(
            final ImmutableMap<String, Ds3Type> typeMap,
            final ImmutableSet<String> usedTypes) {
        if (isEmpty(usedTypes)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.addAll(usedTypes);
        for (final String type : usedTypes) {
            final Ds3Type ds3Type = typeMap.get(type);
            builder.addAll(getUsedTypesFromType(ds3Type));
        }
        final ImmutableSet<String> newUsedTypes = builder.build();
        if (newUsedTypes.size() > usedTypes.size()) {
            return getUsedTypesFromAllTypes(typeMap, newUsedTypes);
        }
        return newUsedTypes;
    }

    protected static ImmutableSet<String> getUsedTypesFromType(final Ds3Type ds3Type) {
        if (isEnum(ds3Type) || isEmpty(ds3Type.getElements())) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3Element ds3Element : ds3Type.getElements()) {
            if (includeType(ds3Element.getType())) {
                builder.add(ds3Element.getType());
            }
            if (hasContent(ds3Element.getComponentType())
                    && includeType(ds3Element.getComponentType())) {
                builder.add(ds3Element.getComponentType());
            }
        }
        return builder.build();
    }

    protected static boolean isEnum(final Ds3Type ds3Type) {
        return hasContent(ds3Type.getEnumConstants());
    }

    protected static boolean includeType(final String type) {
        return hasContent(type) && type.startsWith(CONTRACT_DEFINED_TYPE);
    }

    protected static ImmutableSet<String> getUsedTypesFromRequests(final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3Request request : requests) {
            builder.addAll(getUsedTypesFromParams(request.getRequiredQueryParams()));
            builder.addAll(getUsedTypesFromParams(request.getOptionalQueryParams()));
        }
        return builder.build();
    }

    protected static ImmutableSet<String> getUsedTypesFromParams(final ImmutableList<Ds3Param> params) {
        if (isEmpty(params)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3Param param : params) {
            if (includeType(param.getType())) {
                builder.add(param.getType());
            }
        }
        return builder.build();
    }
}
