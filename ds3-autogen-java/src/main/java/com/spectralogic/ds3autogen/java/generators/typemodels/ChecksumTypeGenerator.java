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

package com.spectralogic.ds3autogen.java.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.java.models.EnumConstant;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class ChecksumTypeGenerator extends BaseTypeGenerator {

    /**
     * Returns an empty list as the ChecksumType does not have any imports
     */
    @Override
    protected ImmutableList<String> getAllImports(final Ds3Type ds3Type) {
        return ImmutableList.of();
    }

    /**
     * Converts a list of Ds3EnumConstants into a list of EnumConstant models
     * @param ds3EnumConstants A list of Ds3EnumConstants
     * @return A list of EnumConstant models
     */
    @Override
    protected ImmutableList<EnumConstant> toEnumConstantList(
            final ImmutableList<Ds3EnumConstant> ds3EnumConstants) {
        if (isEmpty(ds3EnumConstants)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant ds3EnumConstant : ds3EnumConstants) {
            builder.add(toEnumConstant(ds3EnumConstant));
        }

        builder.add(new EnumConstant("NONE"));

        return builder.build();
    }
}
