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

package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public final class SourceConverter {
    private static final Logger LOG = LoggerFactory.getLogger(SourceConverter.class);

    public static Source toSource(
            final ImmutableList<Enum> allEnums,
            final ImmutableList<Struct> allStructs,
            final ImmutableList<Request> allRequests) throws ParseException {
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        LOG.info("**** allOrderedStructs:" + allOrderedStructs.size() + " ****");
        for (final Struct currentStruct : allOrderedStructs) {
            LOG.info(currentStruct.toString());
        }

        final ImmutableSet<C_Type> arrayTypes = getArrayStructMemberTypes(allOrderedStructs);
        LOG.info("**** arrayTypes:" + arrayTypes.size() + " ****");
        for (final C_Type currentType : arrayTypes) {
            LOG.info(currentType.toString());
        }
        final ImmutableList<Struct> arrayStructs = StructHelper.filterTopLevelStructs(
                getArrayStructs(allOrderedStructs, arrayTypes));
        LOG.info("**** arrayStructs:" + arrayStructs.size() + " ****");
        for (final Struct currentStruct : arrayStructs) {
            LOG.info(currentStruct.toString());
        }
        final ImmutableList<Struct> embeddedStructs = StructHelper.filterTopLevelStructs(
                filterArrayStructs(allOrderedStructs, arrayTypes));
        LOG.info("**** embeddedStructs:" + embeddedStructs.size() + " ****");
        for (final Struct currentStruct : embeddedStructs) {
            LOG.info(currentStruct.toString());
        }
        final ImmutableList<Struct> topLevelStructs = StructHelper.getTopLevelStructs(
                filterArrayStructs(allOrderedStructs, arrayTypes));
        LOG.info("**** topLevelStructs:" + topLevelStructs.size() + " ****");
        for (final Struct currentStruct : topLevelStructs) {
            LOG.info(currentStruct.toString());
        }
        final ImmutableSet<Enum> queryParamEnums = filterQueryParamEnums(allEnums, allRequests);
        return new Source( allEnums,
                queryParamEnums,
                arrayTypes,
                arrayStructs,
                embeddedStructs,
                topLevelStructs,
                allRequests);
    }

    /**
     * Find the Set of C_Types that are an array
     */
    public static ImmutableSet<C_Type> getArrayStructMemberTypes(final ImmutableList<Struct> allOrderedStructs) {
        return allOrderedStructs.stream()
                .filter(Struct::isTopLevel)
                .flatMap(currentStruct -> currentStruct.getStructMembers().stream())
                .filter(currentStructMember -> currentStructMember.getType().isArray())
                .map(StructMember::getType)
                .collect(GuavaCollectors.immutableSet());
    }

    /**
     * Get all Structs that are used as an Array element inside of another Struct, to be used by
     * StructHelper::generateStructMemberArrayParserBlock
     */
    private static ImmutableList<Struct> getArrayStructs(
            final ImmutableList<Struct> allOrderedStructs,
            final ImmutableSet<C_Type> arrayTypes) {
        final ImmutableSet<String> typeKeys = arrayTypes.stream()
                .map(C_Type::getTypeName)
                .collect(GuavaCollectors.immutableSet());

        return allOrderedStructs.stream()
                .filter(currentStruct -> typeKeys.contains(currentStruct.getName()))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Remove Structs from allOrderedStructs that exist in arrayTypes
     */
    private static ImmutableList<Struct> filterArrayStructs(
            final ImmutableList<Struct> allOrderedStructs,
            final ImmutableSet<C_Type> arrayTypes) {
        final ImmutableSet<String> typeKeys = arrayTypes.stream()
                .map(C_Type::getTypeName)
                .collect(GuavaCollectors.immutableSet());

        return allOrderedStructs.stream()
                .filter(currentStruct -> !typeKeys.contains(currentStruct.getName()))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * All Request query parameters that are Enums require a _get_enum_str() function to be generated
     */
    public static ImmutableSet<Enum> filterQueryParamEnums(final ImmutableList<Enum> allEnums, final ImmutableList<Request> allRequests) {
        final ImmutableSet<String> enumTypes = allEnums.stream()
                .map(Enum::getName)
                .collect(GuavaCollectors.immutableSet());

        final ImmutableSet.Builder<Enum> queryEnumsBuilder = ImmutableSet.builder();
        for (final Request currentRequest : allRequests) {
            currentRequest.getRequiredQueryParams().stream()
                    .filter(currentRequiredParam -> enumTypes.contains(currentRequiredParam.getParameterType()))
                    .forEach(currentRequiredParam -> allEnums.stream()
                            .filter(currentEnum -> currentEnum.getName().equals(currentRequiredParam.getParameterType()))
                            .forEach(queryEnumsBuilder::add));
            currentRequest.getOptionalQueryParams().stream()
                    .filter(currentOptionalParam -> enumTypes.contains(currentOptionalParam.getParameterType()))
                    .forEach(currentOptionalParam -> allEnums.stream()
                            .filter(currentEnum -> currentEnum.getName().equals(currentOptionalParam.getParameterType()))
                            .forEach(queryEnumsBuilder::add));
        }
        return queryEnumsBuilder.build();
    }

}
