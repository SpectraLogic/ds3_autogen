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

package com.spectralogic.ds3autogen.net.utils;

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;

import static com.spectralogic.ds3autogen.net.utils.GeneratorUtils.getNetType;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isEnumType;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

/**
 * Static utility functions that create Nullable Variables. This is used to
 * properly generate nullable .net types
 */
public final class NetNullableVariableUtils {

    private NetNullableVariableUtils() {
        //pass
    }

    /**
     * Creates a NullableVariable from the provided parameters
     */
    public static NetNullableVariable createNullableVariable(
            final String name,
            final String type,
            final boolean nullable,
            final ImmutableMap<String, Ds3Type> typeMap) {
        return createNullableVariable(name, type, null, nullable, typeMap);
    }

    /**
     * Crates a NullableVariable from the provided parameters
     */
    public static NetNullableVariable createNullableVariable(
            final String name,
            final String type,
            final String componentType,
            final boolean nullable,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final boolean questionMarkForNullable = isEnumType(type, typeMap) || isPrimitive(type);
        final String netType = getNetType(stripPath(type), stripPath(componentType));
        return new NetNullableVariable(
                name,
                netType,
                questionMarkForNullable,
                nullable);
    }

    /**
     * Determines if a contract type is a primitive .net type. This is
     * used to determine if the type requires a '?' to make it nullable
     */
    protected static boolean isPrimitive(final String type) {
        if (isEmpty(type)) {
            return false;
        }
        switch (removePath(type.toLowerCase())) {
            case "int":
            case "integer":
            case "boolean":
            case "bool":
            case "void":
            case "long":
            case "double":
            case "date":
            case "uuid":
                return true;
            default:
                return false;
        }
    }
}
