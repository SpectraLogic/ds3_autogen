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

    /**
     * Determine if a Struct has any StructMembers which need to be parsed as an attribute
     * @param structEntry
     * @return boolean
     */
    public static boolean hasAttributes(final Struct structEntry) {
        return structEntry.getStructMembers().stream()
                .anyMatch(StructMember::isAttribute);
    }

    /**
     * Determine if a Struct has any StructMembers which need to be parsed as a child-node
     * @param structEntry
     * @return boolean
     */
    public static boolean hasChildNodes(final Struct structEntry) {
        return structEntry.getStructMembers().stream()
                .anyMatch(sm -> !sm.isAttribute());
    }
}
