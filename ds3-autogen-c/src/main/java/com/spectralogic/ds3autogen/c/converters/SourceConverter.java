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
    private static final Logger LOG = LoggerFactory.getLogger(RequestConverter.class);

    public static Source toSource(
            final ImmutableList<Enum> allEnums,
            final ImmutableList<Struct> allStructs,
            final ImmutableList<Request> allRequests) throws ParseException {
        final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);
        final ImmutableList<Struct> allOrderedStructs = StructHelper.getStructsOrderedList(allStructs, enumNames);
        final ImmutableSet<C_Type> arrayTypes = getArrayStructMemberTypes(allOrderedStructs);
        final ImmutableList<Struct> arrayStructs = getArrayStructs(allOrderedStructs, arrayTypes);
        final ImmutableList<Struct> filteredOrderedStructs = filterArrayStructs(allOrderedStructs, arrayTypes);

        return new Source( allEnums,
                arrayTypes,
                arrayStructs,
                filteredOrderedStructs,
                allRequests);
    }

    /**
     * Find the Set of C_Types that are an array
     */
    public static ImmutableSet<C_Type> getArrayStructMemberTypes(final ImmutableList<Struct> allOrderedStructs) {
        return allOrderedStructs.stream()
                .flatMap(currentStruct -> currentStruct.getStructMembers().stream())
                .filter(currentStructMember -> currentStructMember.getType().isArray())
                .map(StructMember::getType)
                .collect(GuavaCollectors.immutableSet());
    }

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

}
