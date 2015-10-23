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

import java.util.List;

public class TypeMap {

    @JsonProperty("RequestMap")
    private List<RequestMap> requests;

    public List<RequestMap> getRequests() {
        return requests;
    }

    public void setRequests(final List<RequestMap> requests) {
        this.requests = requests;
    }

    public ImmutableMap<String, ImmutableMap<String, String>> getTypeMap() {
        final ImmutableMap.Builder<String, ImmutableMap<String, String>> typeMapBuilder = ImmutableMap.builder();
        for (final RequestMap req : requests) {
            final ImmutableMap.Builder<String, String> paramBuilder = ImmutableMap.builder();
            for (final ParamMap param : req.getParams()) {
                paramBuilder.put(param.getName(), param.getType());
            }
            typeMapBuilder.put(req.getRequestName(), paramBuilder.build());
        }
        return typeMapBuilder.build();
    }
}
