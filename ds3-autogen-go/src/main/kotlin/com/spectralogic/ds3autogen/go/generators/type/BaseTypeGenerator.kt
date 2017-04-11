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

package com.spectralogic.ds3autogen.go.generators.type

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type
import com.spectralogic.ds3autogen.go.models.type.Type
import com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors

open class BaseTypeGenerator : TypeModelGenerator<Type>, TypeModelGeneratorUtil {

    override fun generate(ds3Type: Ds3Type): Type {
        val name = NormalizingContractNamesUtil.removePath(ds3Type.name)
        val enumConstants = toEnumConstantsList(ds3Type.enumConstants)

        return Type(name, enumConstants)
    }

    /**
     * Retrieves the list of enum constants that makes up this type, in upper-cased format
     */
    fun toEnumConstantsList(ds3EnumConstants: ImmutableList<Ds3EnumConstant>?): ImmutableList<String> {
        if (isEmpty(ds3EnumConstants)) {
            return ImmutableList.of()
        }
        return ds3EnumConstants!!.stream()
                .map { constant -> constant.name.toUpperCase() }
                .collect(GuavaCollectors.immutableList())
    }
}
