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

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConverterUtil {

    private static final String CONTRACT_DEFINED_TYPE = "com.spectralogic.";

    public static boolean hasContent(final List<?> list) {
        return !isEmpty(list);
    }

    public static boolean hasContent(final Map<?,?> map) {
        return !isEmpty(map);
    }

    public static boolean hasContent(final Set<?> set) {
        return !isEmpty(set);
    }

    public static boolean hasContent(final String string) {
        return !isEmpty(string);
    }

    public static boolean isEmpty(final List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(final Map<?,?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(final Set<?> set) {
        return set == null || set.isEmpty();
    }

    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
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
