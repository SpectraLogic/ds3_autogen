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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public class C_TypeHelper {
    private static final Logger LOG = LoggerFactory.getLogger(C_TypeHelper.class);
    private C_TypeHelper() {}

    private final static C_TypeHelper c_typeHelper = new C_TypeHelper();

    public static C_TypeHelper getInstance() {
        return c_typeHelper;
    }

    public static C_Type convertDs3ElementType(final Ds3Element element) throws ParseException {
        switch (element.getType()) {
            case "boolean":
                return new C_Type("ds3_bool", true, false);

            case "double":
                return new C_Type("double", true, false);  // ex: 0.82

            case "java.lang.Long":
            case "long":
                return new C_Type("uint64_t", true, false);

            case "java.lang.Integer":
            case "int":
                return new C_Type("int", true, false);

            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return new C_Type("ds3_str", false, false);

            case "java.util.Set":
            case "array":
                return new C_Type(StructHelper.getResponseTypeName(element.getComponentType()), false, true);

            default:
                return new C_Type(StructHelper.getResponseTypeName(element.getType()), false, false);
        }
    }
}
