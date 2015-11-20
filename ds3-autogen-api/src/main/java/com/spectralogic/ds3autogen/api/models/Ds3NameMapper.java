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

package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ds3NameMapper {

    private static final Logger LOG = LoggerFactory.getLogger(Ds3NameMapper.class);
    private final ImmutableMap<String, Ds3NameMap> typeNameMapper;

    public Ds3NameMapper(final ImmutableMap<String, Ds3NameMap> typeNameMapper) {
        this.typeNameMapper = typeNameMapper;
    }

    public boolean containsType(final String namePath) {
        return typeNameMapper.containsKey(namePath);
    }

    public String getConvertedName(final String namePath) {
        final Ds3NameMap ds3NameMap = typeNameMapper.get(namePath);
        return namePath.replace(
                ds3NameMap.getContractName(),
                ds3NameMap.getSdkName());
    }
}
