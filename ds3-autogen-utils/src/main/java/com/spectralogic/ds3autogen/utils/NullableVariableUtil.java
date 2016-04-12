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
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Utils that determine if a variable should be nullable within the generated code
 */
public final class NullableVariableUtil {

    /**
     * Determines if the specified type should be nullable in the generated code
     */
    public static boolean isNullableType(final String type) {
        if (isEmpty(type)) {
            return false;
        }
        switch (type) {
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.lang.String":
            case "java.lang.Double":
            case "java.lang.Boolean":
                return true;
            default:
                return false;
        }
    }

    /**
     * Determines if an element describes a variable that should be nullable
     */
    public static boolean isNullableElement(
            final String elementType,
            final ImmutableList<Ds3Annotation> annotations) {
        if (isNullableType(elementType)) {
            return true;
        }
        if (isEmpty(annotations)) {
            return false;
        }
        for (final Ds3Annotation annotation : annotations) {
            if (annotation.getName().endsWith(".Optional")) {
                return true;
            }
        }
        return false;
    }
}
