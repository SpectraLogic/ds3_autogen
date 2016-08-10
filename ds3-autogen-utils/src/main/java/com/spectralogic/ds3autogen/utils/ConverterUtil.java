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
import com.spectralogic.ds3autogen.api.models.apispec.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;

public final class ConverterUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ConverterUtil.class);
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

    /**
     * Removes all unused types from the Ds3Type map. Types are considered to be used if
     * they are used within a Ds3Request, and/or if they are used within another type that
     * is also used.
     * @param types A Ds3Type map
     * @param requests A list of Ds3Requests
     */
    public static ImmutableMap<String, Ds3Type> removeUnusedTypes(
            final ImmutableMap<String, Ds3Type> types,
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(types) || isEmpty(requests)) {
            return ImmutableMap.of();
        }

        final ImmutableSet.Builder<String> usedTypesBuilder = ImmutableSet.builder();
        usedTypesBuilder.addAll(getUsedTypesFromRequests(requests));
        usedTypesBuilder.addAll(getUsedTypesFromAllTypes(types, usedTypesBuilder.build()));
        final ImmutableSet<String> usedTypes = usedTypesBuilder.build();

        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();
        for (final Map.Entry<String, Ds3Type> entry : types.entrySet()) {
            if (usedTypes.contains(entry.getKey())) {
                builder.put(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * Gets a set of type names used within a list of Ds3Types
     */
    protected static ImmutableSet<String> getUsedTypesFromAllTypes(
            final ImmutableMap<String, Ds3Type> typeMap,
            final ImmutableSet<String> usedTypes) {
        if (isEmpty(usedTypes) || isEmpty(typeMap)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.addAll(usedTypes);
        for (final String type : usedTypes) {
            final Ds3Type ds3Type = typeMap.get(type);
            if (ds3Type != null) {
                builder.addAll(getUsedTypesFromType(ds3Type));
            } else {
                //Log but do not throw an exception because there are cases where a type
                //doesn't need to be generated. Especially true during testing.
                LOG.error("Could not find used type in Type Map: " + type);
            }
        }
        final ImmutableSet<String> newUsedTypes = builder.build();
        if (newUsedTypes.size() > usedTypes.size()) {
            return getUsedTypesFromAllTypes(typeMap, newUsedTypes);
        }
        return newUsedTypes;
    }

    /**
     * Gets a set of type names used within a Ds3Type
     */
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

    /**
     * Determines if a Ds3Type is describing an Enum
     */
    public static boolean isEnum(final Ds3Type ds3Type) {
        return hasContent(ds3Type.getEnumConstants());
    }

    /**
     * Determines if a type name is a Spectra defined type
     */
    protected static boolean includeType(final String type) {
        return hasContent(type) && type.startsWith(CONTRACT_DEFINED_TYPE);
    }

    /**
     * Gets a set of type names used within a list of Ds3Requests. This includes all Spectra defined
     * parameter types and response types used within the requests.
     */
    protected static ImmutableSet<String> getUsedTypesFromRequests(final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3Request request : requests) {
            builder.addAll(getUsedTypesFromParams(request.getRequiredQueryParams()));
            builder.addAll(getUsedTypesFromParams(request.getOptionalQueryParams()));
            builder.addAll(getUsedTypesFromResponseCodes(request.getDs3ResponseCodes()));
        }
        return builder.build();
    }

    /**
     * Gets a set of type names used within a list of Ds3Params
     */
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

    /**
     * Gets a set of type names used within a list of Ds3ResponseCodes
     */
    protected static ImmutableSet<String> getUsedTypesFromResponseCodes(final ImmutableList<Ds3ResponseCode> responseCodes){
        if (isEmpty(responseCodes)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            if (hasContent(responseCode.getDs3ResponseTypes())) {
                builder.addAll(getUsedTypesFromResponseTypes(responseCode.getDs3ResponseTypes()));
            }
        }
        return builder.build();
    }

    /**
     * Gets a set of type names used within a list of Ds3ResponseTypes
     */
    protected static ImmutableSet<String> getUsedTypesFromResponseTypes(final ImmutableList<Ds3ResponseType> responseTypes) {
        if (isEmpty(responseTypes)) {
            return ImmutableSet.of();
        }
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3ResponseType responseType : responseTypes) {
            if (includeResponseType(responseType)) {
                builder.add(getTypeFromResponseType(responseType));
            }
        }
        return builder.build();
    }

    /**
     * Determines if a Response Type contains a Spectra defined type.
     */
    protected static boolean includeResponseType(final Ds3ResponseType responseType) {
        return includeType(responseType.getType()) || includeType(responseType.getComponentType());
    }

    /**
     * Retrieves the Spectra defined type within the Response Type. Throws an error if
     * neither type nor component type is a Spectra defined type.
     */
    protected static String getTypeFromResponseType(final Ds3ResponseType responseType) {
        if (includeType(responseType.getType())) {
            return responseType.getType();
        }
        if (includeType(responseType.getComponentType())) {
            return responseType.getComponentType();
        }
        throw new IllegalArgumentException("Cannot get Spectra type name if neither the Response Type nor the Response Component Type is a Spectra defined type");
    }
}
