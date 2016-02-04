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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Set;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

public final class StructHelper {
    private static final Logger LOG = LoggerFactory.getLogger(StructHelper.class);
    private StructHelper() {}

    private final static StructHelper structHelper = new StructHelper();

    public static StructHelper getInstance() {
        return structHelper;
    }

    public static String getNameUnderscores(final String name) {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name)));
    }

    public static String getDs3TypeName(final String name) {
        return "ds3_" + getNameUnderscores(name);
    }

    public static String getResponseTypeName(final String name) {
        return getDs3TypeName(name) + "_response";
    }

    public static String getParserFunctionName(final String name) {
        return "_parse_" + getResponseTypeName(name);
    }

    /**
     * Determine if a Struct requires a custom parser.
     * @param struct a valid Struct to examine
     * @return true if no StructMembers require a custom parser.
     */
    public static boolean requiresNewCustomParser(final Struct struct, final Set<String> existingTypes, final ImmutableSet<String> enumNames) {
        for (final StructMember member : struct.getStructMembers()) {
            if (!member.getType().isPrimitive()) {
                if (member.getType().getTypeName().equals("ds3_str")) continue; // ds3_str is not an auto-generated API typeName.

                if (existingTypes.contains(member.getType().getTypeName())) continue;

                if (enumNames.contains(member.getType().getTypeName())) continue;

                return true;
            }
        }
        return false;
    }

    public static String generateStructMemberParserLine(final StructMember structMember, final String parserFunction) throws ParseException {
        return indent(3) + "response->" + Helper.camelToUnderscore(structMember.getName()) + " = " + parserFunction + "\n";
    }


    public static String generateStructMemberArrayParserBlock(final String structResponseTypeName, final StructMember structMember) throws ParseException {
        return indent(3) + "GPtrArray* " + structMember.getName() + "_array = _parse_" + structResponseTypeName + "_array(log, doc, child_node);\n"
             + indent(3) + "response->" + structMember.getName() + " = (" + structMember.getType() + ")" + structMember.getName() + "_array->pdata;\n"
             + indent(3) + "response->num_" + structMember.getName() + " = " + structMember.getName() + "_array->len;\n"
             + indent(3) + "g_ptr_array_free(" + structMember.getName() + "_array, FALSE);\n";
    }

    public static String generateStructMemberEnumParserBlock(final StructMember structMember) {
        return indent(3) + "xmlChar* text = xmlNodeListGetString(doc, child_node, 1);\n"
             + indent(3) + "if (text == NULL) {\n"
             + indent(3) + "    continue;\n"
             + indent(3) + "}\n"
             + indent(3) + "response->" + structMember.getName() + " = _match_" + structMember.getType().getTypeName() + "(log, text);\n";
    }

    public static String getParseStructMemberBlock(final String structName, final StructMember structMember) throws ParseException {
        if (structMember.getType().isPrimitive()) {
            switch (structMember.getType().getTypeName()) {
                case "uint64_t":
                case "double":
                case "long":
                    return generateStructMemberParserLine(structMember, "xml_get_uint64(doc, child_node);");
                case "int":
                    return generateStructMemberParserLine(structMember, "xml_get_uint16(doc, child_node);");
                case "ds3_bool":
                    return generateStructMemberParserLine(structMember, "xml_get_bool(doc, child_node);");
                default: // Enum
                    return generateStructMemberEnumParserBlock(structMember);
            }
        } else if (structMember.getType().isArray()) {
            return generateStructMemberArrayParserBlock(structName, structMember);
        } else if (structMember.getType().getTypeName().equals("ds3_str")) { // special case
            return generateStructMemberParserLine(structMember, "xml_get_string(doc, child_node);");
        }

        return generateStructMemberParserLine(structMember, getParserFunctionName(structMember.getName()) + "(log, doc, child_node);");
    }

    public static String generateResponseParser(final String structName, final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (int structMemberIndex = 0; structMemberIndex < structMembers.size(); structMemberIndex++) {
            outputBuilder.append(indent(2));

            if (structMemberIndex > 0) {
                outputBuilder.append("} else ");
            }

            final StructMember currentStructMember = structMembers.get(structMemberIndex);
            outputBuilder.append("if (element_equal(child_node, \"").append(Helper.underscoreToCamel(currentStructMember.getName())).append("\")) {").append("\n");
            outputBuilder.append(getParseStructMemberBlock(structName, currentStructMember));
        }

        outputBuilder.append(indent(2)).append("} else {").append("\n");
        outputBuilder.append(indent(3)).append("ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);").append("\n");
        outputBuilder.append(indent(2)).append("}").append("\n");

        return outputBuilder.toString();
    }

    public static String generateFreeArrayStructMember(final StructMember structMember) {
        return indent(1) + "for (index = 0; index < response->num_" + structMember.getName() + "; index++) {\n"
             + indent(2) + structMember.getType().getTypeName() + "_free(response_data->" + structMember.getName() + "[index]);\n"
             + indent(1) + "}\n"
             + indent(1) + "g_free(response_data->" + structMember.getName() + ");\n\n";
    }

    public static String generateFreeStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember structMember : structMembers) {
            if (structMember.getType().isPrimitive()) continue;

            if (structMember.getType().isArray()) {
                outputBuilder.append(generateFreeArrayStructMember(structMember));
            } else {
                outputBuilder.append(indent(1)).
                        append(structMember.getType().getTypeName()).
                        append("_free(response_data->").
                        append(structMember.getName()).
                        append(");").
                        append("\n");
            }
        }

        return outputBuilder.toString();
    }

    public static String generateStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember member : structMembers) {
            outputBuilder.append(indent(1)).
                    append(member.getType()).
                    append(" ").
                    append(member.getName()).
                    append(";").
                    append("\n");

            if (member.getType().isArray()) {
                outputBuilder.append(indent(1)).append("size_t").append(" num_").append(member.getName()).append(";\n");
            }
        }

        return outputBuilder.toString();
    }
}
