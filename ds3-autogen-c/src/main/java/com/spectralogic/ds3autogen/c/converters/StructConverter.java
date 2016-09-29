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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.c.helpers.C_TypeHelper;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.Ds3ElementUtil;

import java.text.ParseException;

public final class StructConverter {
    private StructConverter() {}

    public static Struct toStruct(final Ds3Type ds3Type,
                                  final ImmutableSet<String> enumNames,
                                  final ImmutableSet<String> responseTypes,
                                  final ImmutableSet<String> arrayMemberTypes,
                                  final ImmutableSet<String> unwrappedArrayMemberTypes,
                                  final ImmutableSet<String> embeddedTypes,
                                  final ImmutableSet<String> paginatedTypes) throws ParseException {
        final String responseTypeName = StructHelper.getResponseTypeName(ds3Type.getName());
        final ImmutableList<StructMember> structMembersList = convertDs3Elements(ds3Type.getElements(), enumNames, paginatedTypes.contains(responseTypeName));
        return new Struct(
                responseTypeName,
                convertNameToMarshall(ds3Type),
                structMembersList,
                responseTypes.contains(responseTypeName),
                arrayMemberTypes.contains(responseTypeName),
                unwrappedArrayMemberTypes.contains(responseTypeName),
                hasComplexArrayMembers(structMembersList),
                embeddedTypes.contains(responseTypeName));
    }

    private static ImmutableList<StructMember> convertDs3Elements(final ImmutableList<Ds3Element> elementsList,
                                                                  final ImmutableSet<String> enumNames,
                                                                  final boolean isPaginated) throws ParseException {
        final ImmutableList.Builder<StructMember> builder = ImmutableList.builder();
        for (final Ds3Element currentElement : elementsList) {
            final C_Type elementType = C_TypeHelper.convertDs3ElementType(currentElement, enumNames);
            builder.add(new StructMember(
                    elementType,
                    StructHelper.getNameUnderscores(currentElement.getName()),
                    Ds3ElementUtil.getXmlTagName(currentElement),
                    Ds3ElementUtil.getEncapsulatingTagAnnotations(currentElement.getDs3Annotations()),
                    Ds3ElementUtil.isAttribute(currentElement.getDs3Annotations()),
                    hasWrapper(currentElement)));

            if (elementType.isArray()) {
                builder.add(new StructMember(
                        new PrimitiveType("size_t", false),
                        "num_" + StructHelper.getNameUnderscores(currentElement.getName()))
                );
            }
            if (isPaginated) {
                builder.add(new StructMember(new FreeableType("ds3_paging", false), "paging"));
            }
        }

        return builder.build();
    }

    private static String convertNameToMarshall(final Ds3Type ds3Type) {
        if (ds3Type.getName().endsWith("JobList")) {
            return "Jobs";
        } else if (ConverterUtil.isEmpty(ds3Type.getNameToMarshal())) {
            return "Data";
        }
        return ds3Type.getNameToMarshal();
    }

    /**
     * Determine if a free_struct() function requires an index to traverse free'ing an array of elements
     */
    public static boolean hasComplexArrayMembers(final ImmutableList<StructMember> structMembersList) {
        for (final StructMember structMember : structMembersList) {
            if (structMember.getType().isArray() && !structMember.getType().isPrimitive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine if the parser for an array of this type is encapsulated in a List element
     * <Objects>
     *     <Object name="obj1"></Object>
     * </Objects>
     */
    private static boolean hasWrapper(final Ds3Element ds3Element) {
        if (ds3Element.getName().endsWith("Jobs") || ds3Element.getName().equals("CommonPrefixes")) {
            return false;
        }
        return Ds3ElementUtil.hasWrapperAnnotations(ds3Element.getDs3Annotations());
    }
}
