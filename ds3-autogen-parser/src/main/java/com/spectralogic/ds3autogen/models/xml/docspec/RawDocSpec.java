/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.models.xml.docspec;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RawDocSpec {

    @JsonProperty("RequestDescriptors")
    private List<RequestDescriptor> requestDescriptors;

    @JsonProperty("ParamDescriptors")
    private List<ParamDescriptor> paramDescriptors;

    public List<RequestDescriptor> getRequestDescriptors() {
        return requestDescriptors;
    }

    public void setRequestDescriptors(final List<RequestDescriptor> requestDescriptors) {
        this.requestDescriptors = requestDescriptors;
    }

    public List<ParamDescriptor> getParamDescriptors() {
        return paramDescriptors;
    }

    public void setParamDescriptors(final List<ParamDescriptor> paramDescriptors) {
        this.paramDescriptors = paramDescriptors;
    }
}
