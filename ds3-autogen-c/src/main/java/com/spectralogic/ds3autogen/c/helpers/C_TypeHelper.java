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

package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.models.C_Type;
import com.spectralogic.ds3autogen.c.models.FreeableType;
import com.spectralogic.ds3autogen.c.models.PrimitiveType;

import java.text.ParseException;

public final class C_TypeHelper {
    private C_TypeHelper() {}
    private final static C_TypeHelper cTypeHelper = new C_TypeHelper();

    public static C_TypeHelper getInstance() {
        return cTypeHelper;
    }

    private static C_Type createType(final String type, final boolean isArray, final ImmutableSet<String> enumNames) {
        final String ds3TypeName = StructHelper.getDs3TypeName(type);
        if (enumNames.contains(ds3TypeName)) {
            return new PrimitiveType(ds3TypeName, isArray);
        }

        switch (type) {
            case "java.lang.Boolean":
            case "boolean":
                return new PrimitiveType("ds3_bool", isArray);

            case "java.lang.Integer":
            case "int":
                return new PrimitiveType("int", isArray);

            case "java.lang.Long":
            case "long":
                return new PrimitiveType("uint64_t", isArray);

            case "java.lang.Double":
            case "double":
                return new PrimitiveType("float", isArray);

            case "java.lang.Object":
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return new FreeableType("ds3_str", isArray);

            default:
                return new FreeableType(StructHelper.getResponseTypeName(type), isArray);
        }
    }

    public static C_Type convertDs3ElementType(final Ds3Element element, final ImmutableSet<String> enumNames) throws ParseException {
        switch (element.getType()) {

            case "java.util.Set":
            case "array":
                return createType(element.getComponentType(), true, enumNames);

            default:
                return createType(element.getType(), false, enumNames);
        }
    }
}
