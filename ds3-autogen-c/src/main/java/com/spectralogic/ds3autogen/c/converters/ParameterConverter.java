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

package com.spectralogic.ds3autogen.c.converters;

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.ParameterModifier;
import com.spectralogic.ds3autogen.c.models.ParameterPointerType;

public class ParameterConverter {

    public static Parameter toParameter(final Ds3Param ds3Param,
                                        final boolean isRequired,
                                        final String documentation) {
        return new Parameter(
                ParameterModifier.CONST,
                getParameterType(ds3Param),
                StructHelper.getNameUnderscores(ds3Param.getName()),
                getPointerType(ds3Param),
                isRequired,
                documentation);
    }

    public static String getParameterType(final Ds3Param ds3Param) {
        switch (ds3Param.getType()) {
            case "void":
                return "void";
            case "java.lang.Boolean":
            case "boolean":
                return "ds3_bool";

            case "java.lang.Integer":
            case "int":
                return "int";

            case "java.lang.Long":
            case "long":
                return "uint64_t";

            case "java.lang.Double":
            case "double":
                return "float";

            case "java.lang.Object":
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return "char";

            default:
                return StructHelper.getDs3TypeName(ds3Param.getType());
        }
    }

    public static ParameterPointerType getPointerType(final Ds3Param ds3Param) {
        switch (ds3Param.getType()) {
            case "void":
            case "java.lang.Boolean":
            case "boolean":

            case "java.lang.Integer":
            case "int":

            case "java.lang.Long":
            case "long":

            case "java.lang.Double":
            case "double":
                return ParameterPointerType.NONE;

            case "java.lang.Object":
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return ParameterPointerType.SINGLE_POINTER;
            default:
                return ParameterPointerType.NONE;

        }
    }
}
