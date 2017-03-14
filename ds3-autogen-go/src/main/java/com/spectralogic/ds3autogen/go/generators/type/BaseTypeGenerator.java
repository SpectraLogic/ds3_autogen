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

package com.spectralogic.ds3autogen.go.generators.type;

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.go.models.type.Type;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;

public class BaseTypeGenerator implements TypeModelGenerator<Type>, TypeModelGeneratorUtil {
    @Override
    public Type generate(final Ds3Type ds3Type) {
        final String name = NormalizingContractNamesUtil.removePath(ds3Type.getName());

        return new Type(name);
    }
}
