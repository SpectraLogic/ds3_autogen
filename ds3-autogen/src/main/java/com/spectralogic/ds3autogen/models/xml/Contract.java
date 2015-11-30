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

package com.spectralogic.ds3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;

import java.util.List;

public class Contract {

    @JsonProperty("RequestHandlers")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<RequestHandler> requestHandlers;

    @JsonProperty("Types")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Type> types;

    public List<RequestHandler> getRequestHandlers() {
        return requestHandlers;
    }

    public void setRequestHandlers(final List<RequestHandler> requestHandlers) {
        this.requestHandlers = requestHandlers;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(final List<Type> types) {
        this.types = types;
    }

    public ImmutableList<Ds3Request> getDs3Requests() {
        if (requestHandlers == null) {
            return null;
        }
        final ImmutableList.Builder<Ds3Request> ds3RequestBuilder = ImmutableList.builder();
        for (final RequestHandler requestHandler : requestHandlers) {
            ds3RequestBuilder.add(requestHandler.toDs3Request());
        }
        return ds3RequestBuilder.build();
    }

    public ImmutableMap<String, Ds3Type> getDs3Types() {
        if (types == null) {
            return null;
        }
        final ImmutableMap.Builder<String, Ds3Type> ds3TypeBuilder = ImmutableMap.builder();
        for (final Type type : types) {
            ds3TypeBuilder.put(type.getName(), type.toDs3Type());
        }
        return ds3TypeBuilder.build();
    }
}
