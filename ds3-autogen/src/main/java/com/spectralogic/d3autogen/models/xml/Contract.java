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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.spectralogic.ds3autogen.api.models.Ds3Request;

import java.util.ArrayList;
import java.util.List;

public class Contract {

    @JsonProperty("RequestHandlers")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<RequestHandler> requestHandlers;

    public List<RequestHandler> getRequestHandlers() {
        return requestHandlers;
    }

    public void setRequestHandlers(final List<RequestHandler> requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    public List<Ds3Request> getDs3Requests() {
        List<Ds3Request> ds3Requests = new ArrayList<Ds3Request>();
        for (final RequestHandler requestHandler : requestHandlers) {
            ds3Requests.add(requestHandler.toDs3Request());
        }
        return ds3Requests;
    }
}
