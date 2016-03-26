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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

public final class C_TypeHelper {
    private static final Logger LOG = LoggerFactory.getLogger(C_TypeHelper.class);
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

    public static String generateArrayMemberParser(final C_Type cType) {
        final StringBuilder outputBuilder = new StringBuilder();

        outputBuilder.append("static ds3_error* _parse_").append(cType.getTypeName()).append("_array(const ds3_client* client, const xmlDocPtr doc, const xmlNodePtr root, GPtrArray** _response) {\n");
        outputBuilder.append(indent(1)).append("ds3_error* error = NULL;\n");
        outputBuilder.append(indent(1)).append("xmlNodePtr child_node;\n");
        outputBuilder.append(indent(1)).append("GPtrArray* ").append(cType.getTypeName()).append("_array = g_ptr_array_new();\n");
        outputBuilder.append("\n");
        outputBuilder.append(indent(1)).append("for (child_node = root->xmlChildrenNode; child_node != NULL; child_node = child_node->next) {\n");
        outputBuilder.append(indent(2)).append(cType.getTypeName()).append("* response;\n");
        outputBuilder.append(indent(2)).append("error = _parse_").append(cType.getTypeName()).append("(client, doc, child_node, &response);\n");
        outputBuilder.append(indent(2)).append("g_ptr_array_add(").append(cType.getTypeName()).append("_array, response);\n");
        outputBuilder.append("\n");
        outputBuilder.append(indent(2)).append("if (error != NULL) {\n");
        outputBuilder.append(indent(3)).append("break;\n");
        outputBuilder.append(indent(2)).append("}\n");
        outputBuilder.append(indent(1)).append("}\n");
        outputBuilder.append("\n");
        outputBuilder.append(indent(1)).append("*_response = response;\n");
        outputBuilder.append("\n");
        outputBuilder.append(indent(1)).append("return error;\n");
        outputBuilder.append("}\n");

        return outputBuilder.toString();
    }
}
