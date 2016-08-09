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

package com.spectralogic.ds3autogen.models.xml.namemap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.namemap.TypeRename;

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

    /**
     * Gets a map where the key is the contract name and the value
     * is the sdk name and type of object being renamed. There are
     * no paths included within these names. This is used to convert
     * names into the sdk naming scheme.
     */
    public ImmutableMap<String, TypeRename> getTypeMap() {
        final ImmutableMap.Builder<String, TypeRename> builder = ImmutableMap.builder();
        for (final NameMap nameMap : types) {
            final TypeRename rename = new TypeRename(nameMap.getSdkName(), nameMap.getType());
            builder.put(nameMap.getContractName(), rename);
        }
        return builder.build();
    }
}
