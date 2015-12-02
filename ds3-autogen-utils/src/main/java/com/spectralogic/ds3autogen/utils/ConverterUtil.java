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
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;

import java.util.List;
import java.util.Map;

public class ConverterUtil {

    public static boolean hasContent(final List<?> list) {
        return !isEmpty(list);
    }

    public static boolean hasContent(final Map<?,?> map) {
        return !isEmpty(map);
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

    public static boolean isEmpty(final String string) {
        return string == null || string.isEmpty();
    }

    public static ImmutableMap<String, Ds3Type> removeUnusedTypes(
            final ImmutableMap<String, Ds3Type> types,
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(types) || isEmpty(requests)) {
            return ImmutableMap.of();
        }
        final ImmutableSet<String> usedTypes = getUsedTypes(requests);
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();
        for (final Map.Entry<String, Ds3Type> entry : types.entrySet()) {
            if (usedTypes.contains(entry.getKey())) {
                builder.put(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    protected static ImmutableSet<String> getUsedTypes(final ImmutableList<Ds3Request> requests) {
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
            if (param.getType().startsWith("com.spectralogic.")) {
                builder.add(param.getType());
            }
        }
        return builder.build();
    }
}
