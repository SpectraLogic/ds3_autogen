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

import com.spectralogic.ds3autogen.c.models.Parameter;

import java.security.InvalidParameterException;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

public class ParameterHelper {
    private final static ParameterHelper parameterHelper = new ParameterHelper();

    public static ParameterHelper getInstance() {
        return parameterHelper;
    }

    public static String getParameterTypeConversionSpecifier(final Parameter parm) {
        switch (parm.getParameterType()) {
            case "ds3_bool":
                return "VOID";

            case "int":
                return "%d";

            case "uint64_t":
                return "%llu";

            case "float":
            case "double":
                return "%e";

            default:
                return null;
        }
    }

    public static String generateInitParamBlock(final Parameter parm) {
        if (requiresParameterCondition(parm)) {
            return getParameterCondition(parm, 1)
                    + getSetQueryParamBlock(parm, 2)
                    + indent(1) + "}";
        }

        return getSetQueryParamBlock(parm, 1);
    }

    public static boolean requiresParameterCondition(final Parameter parm) {
        if (parm.getName().equalsIgnoreCase("length")) {
            return false;
        } else if (parm.getParameterType().equalsIgnoreCase("ds3_master_object_list_response")) { // special case - bulk request payload, renamed from JobWithChunksApiBean
            return false;
        } else if (parm.getParameterType().equalsIgnoreCase("ds3_chunk_order")) { // special case - currently an enum property of ds3_request
            return false;
        } else if (parm.getParameterType().equalsIgnoreCase("ds3_bool") && parm.isRequired()) {
            return false;
        } else if (parm.getParameterType().equalsIgnoreCase("operation")) {
            return false;
        }
        return true;
    }

    public static String getParameterCondition(final Parameter parm, final int depth) {
        switch(parm.getParameterType()) {
            case "ds3_bool":
                if (parm.isRequired()) {
                    throw new InvalidParameterException(parm.getName() + " Required bool query param should not require a condition: " + parm.getParameterType());
                }
                return indent(depth) + "if (" + parm.getName() + " && *" + parm.getName() + ") {\n";
            case "uint64_t":
            case "float":
            case "int":
            case "char":
                return indent(depth) + "if (" + parm.getName() + " != NULL) {\n";
            default:
                if (parm.getParameterType().startsWith("ds3_")) {  // enum type
                    return indent(depth) + "if (" + parm.getName() + " != NULL) {\n";
                }

                throw new InvalidParameterException(parm.getName() + " Unknown type: " + parm.getParameterType());
        }
    }

    public static String getSetQueryParamBlock(final Parameter parm, final int depth) {
        if (parm.getName().equalsIgnoreCase("length")) {
            return indent(depth) + "request->length = *length;\n";
        }
        switch(parm.getParameterType()) {
            case "ds3_bool":
                return indent(depth) + "_set_query_param((ds3_request*) request, \"" + parm.getName() + "\", NULL);\n";
            case "operation":
                return indent(depth) + "_set_query_param((ds3_request*) request, \"operation\", \"" + parm.getName() + "\");\n";
            case "uint64_t":
                return indent(depth) + "char tmp_buff[32];\n"
                     + indent(depth) + "sprintf(tmp_buff, \"%llu\", (unsigned long long) *" + parm.getName() +");\n"
                     + indent(depth) + "_set_query_param((ds3_request*) request, \"" + parm.getName() + "\", tmp_buff);\n";
            case "float":
                return indent(depth) + "char tmp_buff[32];\n"
                     + indent(depth) + "sprintf(tmp_buff, \"%f\", *" + parm.getName() +");\n"
                     + indent(depth) + "_set_query_param((ds3_request*) request, \"" + parm.getName() + "\", tmp_buff);\n";
            case "int":
                return indent(depth) + "char tmp_buff[32];\n"
                     + indent(depth) + "sprintf(tmp_buff, \"%d\", *" + parm.getName() +");\n"
                     + indent(depth) + "_set_query_param((ds3_request*) request, \"" + parm.getName() + "\", tmp_buff);\n";
            case "char":
                return indent(depth) + "_set_query_param((ds3_request*) request, \"" + parm.getName() + "\", " + parm.getName() + ");\n";
            default:
                if (parm.getParameterType().startsWith("ds3_")) {  // enum type
                    return indent(depth) + "_set_query_param((ds3_request*) request, \"" + parm.getName() + "\", _get_" + parm.getParameterType() + "_str(*" + parm.getName() + "));\n";
                }

                throw new InvalidParameterException(parm.getName() + " Unknown type: " + parm.getParameterType());
        }
    }
}
