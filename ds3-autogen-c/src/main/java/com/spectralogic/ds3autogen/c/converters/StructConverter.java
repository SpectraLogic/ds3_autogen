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
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.helpers.C_TypeHelper;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public final class StructConverter {
    private static final Logger LOG = LoggerFactory.getLogger(StructConverter.class);
    private StructConverter() {}

    public static Struct toStruct(final Ds3Type ds3Type,
                                  final ImmutableSet<String> enumNames,
                                  final ImmutableList<Request> allRequests) throws ParseException {
        final ImmutableList<StructMember> variablesList = convertDs3Elements(ds3Type.getElements(), enumNames);
        final String responseTypeName = StructHelper.getResponseTypeName(ds3Type.getName());
        return new Struct(
                responseTypeName,
                convertNameToMarshall(ds3Type.getNameToMarshal()),
                variablesList,
                isTopLevelStruct(responseTypeName, allRequests));
    }

    private static ImmutableList<StructMember> convertDs3Elements(final ImmutableList<Ds3Element> elementsList,
                                                                  final ImmutableSet<String> enumNames) throws ParseException {
        final ImmutableList.Builder<StructMember> builder = ImmutableList.builder();
        for (final Ds3Element currentElement : elementsList) {
            final C_Type elementType = C_TypeHelper.convertDs3ElementType(currentElement, enumNames);
            builder.add(new StructMember(
                    elementType,
                    StructHelper.getNameUnderscores(currentElement.getName())));

            if (elementType.isArray()) {
                builder.add(new StructMember(
                        new PrimitiveType("size_t", false),
                        "num_" + StructHelper.getNameUnderscores(currentElement.getName()))
                );
            }
        }
        return builder.build();
    }

    private static String convertNameToMarshall(final String nameToMarshall) {
        if (ConverterUtil.isEmpty(nameToMarshall)) {
            return "Data";
        }
        return nameToMarshall;
    }

    /**
     * Determine if the parser for this type should _get_request_xml_nodes() for the request response
     */
    private static boolean isTopLevelStruct(final String responseTypeName, final ImmutableList<Request> allRequests) {
        for (final Request currentRequest : allRequests) {
            if (currentRequest.getResponseType().equals(responseTypeName))
                return true;
        }
        return false;
    }
}
