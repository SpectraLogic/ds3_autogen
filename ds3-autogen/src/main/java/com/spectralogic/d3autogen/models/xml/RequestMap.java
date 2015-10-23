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

import java.util.List;

public class RequestMap {

    @JsonProperty("Name")
    private String requestName;

    @JsonProperty("ParamMap")
    private List<ParamMap> params;

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(final String requestName) {
        this.requestName = requestName;
    }

    public List<ParamMap> getParams() {
        return params;
    }

    public void setParams(final List<ParamMap> params) {
        this.params = params;
    }
}
