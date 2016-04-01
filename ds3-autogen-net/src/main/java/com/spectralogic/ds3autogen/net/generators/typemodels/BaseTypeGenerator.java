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

package com.spectralogic.ds3autogen.net.generators.typemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.model.type.BaseType;
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseTypeGenerator implements TypeModelGenerator<BaseType>, TypeModelGeneratorUtils {

    @Override
    public BaseType generate(final Ds3Type ds3Type) {
        final String name = NormalizingContractNamesUtil.removePath(ds3Type.getName());
        final ImmutableList<EnumConstant> enumConstants = toEnumConstants(ds3Type.getEnumConstants());

        return new BaseType(
                name,
                enumConstants);
    }

    /**
     * Converts a list of Ds3EnumConstants into a list of Enum Constants
     */
    @Override
    public ImmutableList<EnumConstant> toEnumConstants(final ImmutableList<Ds3EnumConstant> ds3EnumConstants) {
        if (isEmpty(ds3EnumConstants)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant ds3EnumConstant : ds3EnumConstants) {
            builder.add(new EnumConstant(ds3EnumConstant.getName()));
        }
        return builder.build();
    }
}
