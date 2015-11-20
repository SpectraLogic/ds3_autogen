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

package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3NameMap;

import java.util.List;

public class TypeNameMap {

    @JsonProperty("TypeNameMap")
    private List<NameMap> types;

    public List<NameMap> getTypes() {
        return types;
    }

    public void setTypes(final List<NameMap> types) {
        this.types = types;
    }

    public ImmutableMap<String, Ds3NameMap> getTypeMap() {
        final ImmutableMap.Builder<String, Ds3NameMap> builder = ImmutableMap.builder();
        for (final NameMap nameMap : types) {
            final Ds3NameMap ds3NameMap = new Ds3NameMap(
                    nameMap.getContractName(),
                    nameMap.getSdkName());
            builder.put(nameMap.getContractPath(), ds3NameMap);
        }
        return builder.build();
    }
}
