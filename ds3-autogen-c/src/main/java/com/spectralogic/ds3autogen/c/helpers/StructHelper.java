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

    public static String getParserFunctionName(final String ds3TypeName) {
        return "_parse_" + ds3TypeName;
    }

    public static String getXmlTag(final StructMember structMember) {
        if (structMember.hasWrapper() || structMember.getName().equals("common_prefixes")) {
            return structMember.getEncapsulatingTag();
        }
        return Helper.capFirst(structMember.getNameToMarshall());
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
     * Determine if a Struct has any StructMembers which need to be parsed as an attribute
     */
    public static boolean hasAttributes(final Struct structEntry) {
        return structEntry.getStructMembers().stream()
                .anyMatch(StructMember::isAttribute);
    }

    /**
     * Determine if a Struct has any StructMembers which need to be parsed as a child-node
     */
    public static boolean hasChildNodes(final Struct structEntry) {
        return structEntry.getStructMembers().stream()
                .anyMatch(sm -> !sm.isAttribute());
    }

    /**
     * Determine if any array StructMembers are not wrapped in an enclosing tag:
     * <Objects ID="some_id">
     *     <Object name="obj1"></Object>
     * </Objects>
     */
    public static boolean hasUnwrappedChildNodes(final Struct structEntry) {
        return structEntry.getStructMembers().stream()
                .filter(sm -> !sm.getType().getTypeName().equals("ds3_tape_type")) // enum list
                .filter(sm -> !sm.getType().getTypeName().equals("ds3_tape_drive_type")) // enum list
                .anyMatch(sm -> (!sm.isAttribute() && sm.hasWrapper()));
    }
    /**
     * Determine if a Struct requires a custom parser.
     */

    public static boolean requiresNewCustomParser(final Struct struct, final Set<String> existingTypes, final ImmutableSet<String> enumNames) {
        for (final StructMember member : struct.getStructMembers()) {
            if (!member.getType().isPrimitive()) {
                if (member.getType().getTypeName().equals("ds3_str")) continue; // ds3_str is not an auto-generated API typeName.

                if (member.getType().getTypeName().equals("ds3_paging")) continue; // ds3_paging is not an auto-generated API typeName.

                if (existingTypes.contains(member.getType().getTypeName())) continue;

                if (enumNames.contains(member.getType().getTypeName())) continue;

                LOG.warn("Unknown type[" + member.getType().getTypeName() + "]");
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
                LOG.info(structEntry.toString());
                orderedStructsBuilder.add(structEntry);
            } else {  // move to end to come back to
                structsQueue.add(structEntry);
            }

            if (structsQueueSize == structsQueue.size()) {
                skippedStructsCount++;
                if (skippedStructsCount == structsQueueSize) {
                    LOG.warn("Unable to progress on remaining structs, aborting!");
                    LOG.warn("  Remaining structs[" + structsQueue.size() + "]");
                    for (final Struct struct : allStructs) {
                        LOG.warn("    " + struct.toString() + "\n");
                    }
                    throw new ParseException("Unable to Order provided structs.", 0);
                }
            } else {
                skippedStructsCount = 0;
            }
        }

        return orderedStructsBuilder.build();
    }

    public static String generateResponseParser(final String structName, final ImmutableList<StructMember> structMembers) throws ParseException {
        boolean firstElement = true;
        final StringBuilder outputBuilder = new StringBuilder();

        for (int structMemberIndex = 0; structMemberIndex < structMembers.size(); structMemberIndex++) {
            final StructMember currentStructMember = structMembers.get(structMemberIndex);
            if (currentStructMember.isAttribute()) continue; // only parsing child nodes for a specific node
            if (currentStructMember.getName().startsWith("num_")) continue; // skip - these are used for array iteration and are not a part of the response
            if (currentStructMember.getName().equals("paging")) continue; // skip - parsed from pagination headers

            outputBuilder.append(indent(2));

            if (!firstElement) {
                outputBuilder.append("} else ");
            } else {
                firstElement = false;
            }

            outputBuilder.append("if (element_equal(child_node, \"").append(getXmlTag(currentStructMember)).append("\")) {").append("\n");
            outputBuilder.append(StructMemberHelper.getParseStructMemberBlock(currentStructMember));
        }

        outputBuilder.append(indent(2)).append("} else {").append("\n");
        outputBuilder.append(indent(3)).append("ds3_log_message(client->log, DS3_ERROR, \"Unknown node[%s] of " + structName + " [%s]\\n\", child_node->name, root->name);").append("\n");
        outputBuilder.append(indent(2)).append("}").append("\n");
        outputBuilder.append("\n");
        outputBuilder.append(indent(2)).append("if (error != NULL) {\n");
        outputBuilder.append(indent(3)).append("break;\n");
        outputBuilder.append(indent(2)).append("}\n");

        return outputBuilder.toString();
    }

    public static String generateResponseAttributesParser(final String structName, final ImmutableList<StructMember> structMembers) throws ParseException {
        boolean firstElement = true;
        final StringBuilder outputBuilder = new StringBuilder();

        for (int structMemberIndex = 0; structMemberIndex < structMembers.size(); structMemberIndex++) {
            final StructMember currentStructMember = structMembers.get(structMemberIndex);
            if (!currentStructMember.isAttribute()) continue; // only parsing attributes for a specific node

            outputBuilder.append(indent(2));

            if (!firstElement) {
                outputBuilder.append("} else ");
            } else {
                firstElement = false;
            }

            outputBuilder.append("if (attribute_equal(attribute, \"").append(Helper.capFirst(currentStructMember.getNameToMarshall())).append("\") == true) {").append("\n");
            outputBuilder.append(StructMemberHelper.getParseStructMemberAttributeBlock(currentStructMember));
        }

        outputBuilder.append(indent(2)).append("} else {").append("\n");
        outputBuilder.append(indent(3)).append("ds3_log_message(client->log, DS3_ERROR, \"Unknown attribute[%s] of " + structName + " [%s]\\n\", attribute->name, root->name);").append("\n");
        outputBuilder.append(indent(2)).append("}").append("\n");
        outputBuilder.append("\n");
        outputBuilder.append(indent(2)).append("if (error != NULL) {\n");
        outputBuilder.append(indent(3)).append("break;\n");
        outputBuilder.append(indent(2)).append("}\n");

        return outputBuilder.toString();
    }
}
