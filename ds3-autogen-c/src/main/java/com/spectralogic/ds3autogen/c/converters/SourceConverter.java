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
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Source;
import com.spectralogic.ds3autogen.c.models.Struct;
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
        final ImmutableSet<Enum> queryParamEnums = filterQueryParamEnums(allEnums, allRequests);

        return new Source( allEnums,
                queryParamEnums,
                allOrderedStructs,
                allRequests);
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
