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
import com.spectralogic.ds3autogen.net.model.type.EnumConstant;

/**
 * This generator is used to add the enum constant NONE to a type
 */
public class NoneEnumGenerator extends BaseTypeGenerator {

    /**
     * Converts a list of Ds3EnumConstants into a list of Enum Constants and
     * adds the enum constant NONE
     */
    @Override
    public ImmutableList<EnumConstant> toEnumConstantsList(final ImmutableList<Ds3EnumConstant> ds3EnumConstants) {
        final ImmutableList.Builder<EnumConstant> builder = ImmutableList.builder();
        builder.addAll(getEnumConstantsList(ds3EnumConstants));
        builder.add(new EnumConstant("NONE"));
        return builder.build();
    }
}
