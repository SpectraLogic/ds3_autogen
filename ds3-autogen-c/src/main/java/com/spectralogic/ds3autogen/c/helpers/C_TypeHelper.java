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

import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.models.C_Type;
import com.spectralogic.ds3autogen.c.models.ComplexType;
import com.spectralogic.ds3autogen.c.models.PrimitiveType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Set;

public final class C_TypeHelper {
    private static final Logger LOG = LoggerFactory.getLogger(C_TypeHelper.class);
    private C_TypeHelper() {}

    private static C_Type createType(final String type, final boolean isArray, final Set<String> enumNames) {
        final String ds3TypeName = StructHelper.getDs3TypeName(type);
        if (enumNames.contains(ds3TypeName)) {
            return new PrimitiveType(ds3TypeName, isArray);
        }

        switch (type) {
            case "boolean":
                return new PrimitiveType("ds3_bool", isArray);

            case "double":
            case "java.lang.Long":
            case "long":
                return new PrimitiveType("uint64_t", isArray);

            case "java.lang.Integer":
            case "int":
                return new PrimitiveType("int", isArray);

            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return new ComplexType("ds3_str", isArray);

            default:
                return new ComplexType(StructHelper.getResponseTypeName(type), isArray);
        }
    }

    public static C_Type convertDs3ElementType(final Ds3Element element, final Set<String> enumNames) throws ParseException {
        switch (element.getType()) {

            case "java.util.Set":
            case "array":
                return createType(element.getComponentType(), true, enumNames);

            default:
                return createType(element.getType(), false, enumNames);
        }
    }
}