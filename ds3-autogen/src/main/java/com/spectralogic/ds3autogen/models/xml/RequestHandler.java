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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;

import java.util.List;

public class RequestHandler {

    @JacksonXmlProperty(isAttribute = true, localName = "Classification")
    private Classification classification;

    @JacksonXmlProperty(isAttribute = true, localName = "Name")
    private String name;

    @JsonProperty("Request")
    private Request request;

    @JsonProperty("ResponseCodes")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<ResponseCode> responseCodes;

    public Classification getClassification() {
        return classification;
    }

    public void setClassification(final Classification classification) {
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
        final Ds3Request ds3request = new Ds3Request(
                name,
                request.getHttpVerb(),
                classification,
                request.getBucketRequirement(),
                request.getObjectRequirement(),
                request.getAction(),
                request.getResource(),
                request.getResourceType(),
                request.getOperation(),
                toDs3ResponseCodes(),
                toDs3Params(request.getOptionalQueryParams()),
                toDs3Params(request.getRequiredQueryParams()));

        return ds3request;
    }

    private ImmutableList<Ds3ResponseCode> toDs3ResponseCodes() {
        final ImmutableList.Builder<Ds3ResponseCode> responseCodeBuilder = ImmutableList.builder();

        for (final ResponseCode responseCode : responseCodes) {
            responseCodeBuilder.add(responseCode.toDs3ResponseCode());
        }
        return responseCodeBuilder.build();
    }

    private ImmutableList<Ds3Param> toDs3Params(final List<Param> params) {
        if (params == null) {
            return null;
        }

        final ImmutableList.Builder<Ds3Param> ds3ParamsBuilder = ImmutableList.builder();
        for (final Param param : params) {
            ds3ParamsBuilder.add(param.toDs3Param());
        }
        return ds3ParamsBuilder.build();
    }
}
