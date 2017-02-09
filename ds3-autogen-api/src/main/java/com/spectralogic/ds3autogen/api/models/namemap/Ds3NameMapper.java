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

package com.spectralogic.ds3autogen.api.models.namemap;

import com.google.common.collect.ImmutableMap;

public class Ds3NameMapper {

    /**
     * Contains a map of contract names (key) to sdk names (value)
     */
    private final ImmutableMap<String, TypeRename> typeNameMapper;

    public Ds3NameMapper(final ImmutableMap<String, TypeRename> typeNameMapper) {
        this.typeNameMapper = typeNameMapper;
    }

    /**
     * Determines if the provided contract name exists within the name mapper.
     * This assumes that no path is included in the name lookup
     */
    public boolean containsType(final String contractName) {
        return typeNameMapper.containsKey(contractName);
    }

    /**
     * Retrieves the SDK name associated with the provided contract name. If the
     * contract name does not exist within the name mapper, then the original
     * contract name is returned.
     */
    public String getConvertedName(final String contractName, final NameMapperType type) {
        if (contractName.contains(".")) {
            throw new IllegalArgumentException("The contract name contains a path: " + contractName);
        }
        if (!containsType(contractName)) {
            return contractName;
        }
        final TypeRename rename = typeNameMapper.get(contractName);
        if (type == NameMapperType.NONE || type == rename.getType()) {
            return rename.getSdkName();
        }
        return contractName;
    }
}
