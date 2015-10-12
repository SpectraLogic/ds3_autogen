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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;

import java.util.ArrayList;
import java.util.List;

public class RequestHandler {

    @JacksonXmlProperty(isAttribute = true, localName = "Classification")
    private Ds3Request.Classification classification;

    @JacksonXmlProperty(isAttribute = true, localName = "Name")
    private String name;

    @JsonProperty("Request")
    private Request request;

    @JsonProperty("ResponseCodes")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<ResponseCode> responseCodes;

    public Ds3Request.Classification getClassification() {
        return classification;
    }

    public void setClassification(final Ds3Request.Classification classification) {
        this.classification = classification;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(final Request request) {
        this.request = request;
    }

    public List<ResponseCode> getResponseCodes() {
        return responseCodes;
    }

    public void setResponseCodes(final List<ResponseCode> responseCodes) {
        this.responseCodes = responseCodes;
    }

    public Ds3Request toDs3Request() {
        Ds3Request ds3request = new Ds3Request();

        ds3request.setClassification(classification);
        ds3request.setName(name);
        ds3request.setDs3ResponseCodes(toDs3ResponseCodes());

        ds3request.setBucketRequirement(request.getBucketRequirement());
        ds3request.setHttpVerb(request.getHttpVerb());
        ds3request.setObjectRequirement(request.getObjectRequirement());
        ds3request.setOptionalQueryParams(toDs3Params(request.getOptionalQueryParams()));
        ds3request.setRequiredQueryParams(toDs3Params(request.getRequiredQueryParams()));

        return ds3request;
    }

    private List<Ds3ResponseCode> toDs3ResponseCodes() {
        List<Ds3ResponseCode> ds3ResponseCodes = new ArrayList<Ds3ResponseCode>();
        for (final ResponseCode responseCode : responseCodes) {
            ds3ResponseCodes.add(responseCode.toDs3ResponseCode());
        }
        return ds3ResponseCodes;
    }

    private List<Ds3Param> toDs3Params(final List<Param> params) {
        if (params == null) {
            return null;
        }
        List<Ds3Param> ds3Params = new ArrayList<Ds3Param>();
        for (final Param param : params) {
            ds3Params.add(param.toDs3Param());
        }
        return ds3Params;
    }
}
