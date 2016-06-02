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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
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
        final String name_underscores = getNameUnderscores(name);
        if (name_underscores.startsWith("ds3_")) { // some special-cased types have already been renamed with ds3_ prefix
            return name_underscores;
        }
        return "ds3_" + name_underscores;
    }

    public static String getResponseTypeName(final String name) {
        return getDs3TypeName(name) + "_response";
    }

    public static String getParserFunctionName(final String type, final boolean isTopLevel) {
        if (isTopLevel) {
            return "_parse_top_level_" + type;
        }
        return "_parse_" + type;
    }

    /**
     * Determine if a parser requires a ds3_error to track the response of a sub-parser
     */
    public static boolean hasComplexMembers(final Struct struct) {
        for (final StructMember structMember : struct.getStructMembers()) {
            if (!structMember.getType().isPrimitive()) {
                if (structMember.getType().getTypeName().equals("ds3_str")) continue; // ds3_str is not an auto-generated API typeName.

                return true;
            } else if (structMember.getType().isArray()) {
                return true;
            }
        }
        return false;
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

    /**
     * Return Structs which contain only primitive types first, and then cascade for Complex Structs that contain other Structs
     *
     * @throws java.text.ParseException
     */
    public static ImmutableList<Struct> getStructsOrderedList(final ImmutableList<Struct> allStructs,
                                                              final ImmutableSet<String> enumNames) throws ParseException {
        final Queue<Struct> structsQueue = new LinkedList<>(allStructs);
        final Set<String> existingStructs = new HashSet<>();
        final ImmutableList.Builder<Struct> orderedStructsBuilder = ImmutableList.builder();
        int skippedStructsCount = 0;
        while (!structsQueue.isEmpty()) {
            final int structsQueueSize = structsQueue.size();
            final Struct structEntry = structsQueue.remove();

            if (!StructHelper.requiresNewCustomParser(structEntry, existingStructs, enumNames)) {
                existingStructs.add(structEntry.getName());
                orderedStructsBuilder.add(structEntry);
            } else {  // move to end to come back to
                structsQueue.add(structEntry);
            }

            if (structsQueueSize == allStructs.size()) {
                skippedStructsCount++;
                if (skippedStructsCount == structsQueueSize) {
                    LOG.warn("Unable to progress on remaining structs, aborting!");
                    LOG.warn("  Remaining structs[" + allStructs.size() + "]");
                    for (final Struct struct : allStructs) {
                        LOG.warn("    " + struct.toString() + "\n");
                    }
                    break;
                }
            }
        }

        return orderedStructsBuilder.build();
    }

    public static String generateStructMemberParserLine(final StructMember structMember, final String parserFunction) throws ParseException {
        return indent(3) + "response->" + Helper.camelToUnderscore(structMember.getName()) + " = " + parserFunction + "\n";
    }

    public static String generateStructMemberDs3StrArrayBlock(final StructMember structMember) {
        return indent(3) + "xmlNodePtr loop_node;\n"
             + indent(3) + "int num_nodes = 0;\n"
             + indent(3) + "for (loop_node = child_node->xmlChildrenNode; loop_node != NULL; loop_node = loop_node->next, num_nodes++) {\n"
             + indent(4) + "response->" + structMember.getName() + "[num_nodes] = xml_get_string(doc, loop_node);\n"
             + indent(3) + "}\n"
             + indent(3) + "response->num_" + structMember.getName() + " = num_nodes;\n";
    }

    public static String generateStructMemberArrayParserBlock(final StructMember structMember) throws ParseException {
        return indent(3) + "GPtrArray* " + structMember.getName() + "_array;\n"
             + indent(3) + "error = _parse_" + structMember.getType().getTypeName() + "_array(client, doc, child_node, &" + structMember.getName() + "_array);\n"
             + indent(3) + "response->" + structMember.getName() + " = (" + structMember.getType().getTypeName() + "**)" + structMember.getName() + "_array->pdata;\n"
             + indent(3) + "response->num_" + structMember.getName() + " = " + structMember.getName() + "_array->len;\n"
             + indent(3) + "g_ptr_array_free(" + structMember.getName() + "_array, FALSE);\n";
    }

    public static String generateStructMemberEnumParserBlock(final StructMember structMember) {
        return indent(3) + "xmlChar* text = xmlNodeListGetString(doc, child_node, 1);\n"
             + indent(3) + "if (text == NULL) {\n"
             + indent(4) + "continue;\n"
             + indent(3) + "}\n"
             + indent(3) + "response->" + structMember.getName() + " = _match_" + structMember.getType().getTypeName() + "(client->log, text);\n";
    }

    public static String generateStructMemberEnumArrayParserBlock(final StructMember structMember) {
        return indent(3) + "xmlNodePtr loop_node;\n"
             + indent(3) + "int num_nodes = 0;\n"
             + indent(3) + "GByteArray* enum_array = g_byte_array_new();\n"
             + indent(3) + structMember.getType().getTypeName() + " " + structMember.getName() + ";\n"
             + indent(3) + "for (loop_node = child_node->xmlChildrenNode; loop_node != NULL; loop_node = loop_node->next, num_nodes++) {\n"
             + indent(4) + "xmlChar* text = xmlNodeListGetString(doc, loop_node, 1);\n"
             + indent(4) + "if (text == NULL) {\n"
             + indent(5) + "continue;\n"
             + indent(4) + "}\n"
             + indent(4) + structMember.getName() + " = _match_" + structMember.getType().getTypeName() + "(client->log, text);\n"
             + indent(4) + "g_byte_array_append(enum_array, (const guint8*) &" + structMember.getName() + ", sizeof(" + structMember.getType().getTypeName() + "));\n"
             + indent(3) + "}\n"
             + indent(3) + "response->" + structMember.getName() + " = (" + structMember.getType().getTypeName() + "*)enum_array->data;\n"
             + indent(3) + "response->num_" + structMember.getName() + " = enum_array->len;\n"
             + indent(3) + "g_byte_array_free(enum_array, FALSE);\n";
    }

    public static String getParseStructMemberBlock(final StructMember structMember,
                                                   final boolean isTopLevel) throws ParseException {
        if (structMember.getType().isPrimitive()) {
            switch (structMember.getType().getTypeName()) {
                case "uint64_t":
                case "size_t":
                case "double":
                case "float":
                case "long":
                    return generateStructMemberParserLine(structMember, "xml_get_uint64(doc, child_node);");
                case "int":
                    return generateStructMemberParserLine(structMember, "xml_get_uint16(doc, child_node);");
                case "ds3_bool":
                    // TODO c_sdk inconsistent: xml_get_bool is the only func to log a parse error
                    return generateStructMemberParserLine(structMember, "xml_get_bool(client->log, doc, child_node);");
                default: // Enum
                    if (structMember.getType().isArray()) {
                        return generateStructMemberEnumArrayParserBlock(structMember);
                    }
                    return generateStructMemberEnumParserBlock(structMember);
            }
        } else if (structMember.getType().getTypeName().equals("ds3_str")) { // special case
            if (structMember.getType().isArray()) {
                return generateStructMemberDs3StrArrayBlock(structMember);
            }
            return generateStructMemberParserLine(structMember, "xml_get_string(doc, child_node);");
        } else if (structMember.getType().isArray()) {
            return generateStructMemberArrayParserBlock(structMember);
        }

        if (isTopLevel) {
            return indent(3) + "error = " + getParserFunctionName(structMember.getType().getTypeName(), true) + "(client, request, response, &response->" + structMember.getName() + ");\n";
        } else {
            return indent(3) + "error = " + getParserFunctionName(structMember.getType().getTypeName(), false) + "(client, doc, child_node, &response->" + structMember.getName() + ");\n";
        }
    }

    public static String generateResponseParser(final ImmutableList<StructMember> structMembers,
                                                final boolean isTopLevel) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (int structMemberIndex = 0; structMemberIndex < structMembers.size(); structMemberIndex++) {
            final StructMember currentStructMember = structMembers.get(structMemberIndex);
            if (currentStructMember.getName().startsWith("num_")) continue; // skip - these are used for array iteration and are not a part of the response

            outputBuilder.append(indent(2));

            if (structMemberIndex > 0) {
                outputBuilder.append("} else ");
            }

            final String structMemberName = currentStructMember.getName().equalsIgnoreCase("id") ? "ID" : Helper.underscoreToCamel(currentStructMember.getName());
            outputBuilder.append("if (element_equal(child_node, \"").append(structMemberName).append("\")) {").append("\n");
            outputBuilder.append(getParseStructMemberBlock(currentStructMember, isTopLevel));
        }

        outputBuilder.append(indent(2)).append("} else {").append("\n");
        outputBuilder.append(indent(3)).append("ds3_log_message(client->log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);").append("\n");
        outputBuilder.append(indent(2)).append("}").append("\n");
        outputBuilder.append("\n");
        outputBuilder.append(indent(2)).append("if (error != NULL) {\n");
        outputBuilder.append(indent(3)).append("break;\n");
        outputBuilder.append(indent(2)).append("}\n");

        return outputBuilder.toString();
    }

    public static String generateFreeArrayStructMember(final StructMember structMember) {
        return indent(1) + "for (index = 0; index < response->num_" + structMember.getName() + "; index++) {\n"
             + indent(2) + structMember.getType().getTypeName() + "_free(response->" + structMember.getName() + "[index]);\n"
             + indent(1) + "}\n"
             + indent(1) + "g_free(response->" + structMember.getName() + ");\n";
    }

    public static String generateFreeStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember structMember : structMembers) {
            if (structMember.getType().isPrimitive()) { // PrimitiveTypes only need to free the base pointer
                if (structMember.getType().isArray()) {
                    outputBuilder.append(indent(1)).append("g_free(response->").append(structMember.getName()).append(");\n");
                }
                // else do nothing for single PrimitiveType
            } else if (structMember.getType().isArray()) { // FreeableType needs to free each element
                outputBuilder.append(generateFreeArrayStructMember(structMember));
            } else {
                outputBuilder.append(indent(1)).append(structMember.getType().getTypeName()).append("_free(response->").append(structMember.getName()).append(");\n");
            }
        }

        return outputBuilder.toString();
    }

    public static String generateStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember member : structMembers) {
            outputBuilder.append(indent(1)).append(member.getType()).append(" ").append(member.getName()).append(";\n");
        }

        return outputBuilder.toString();
    }
}
