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
import com.spectralogic.ds3autogen.api.models.Ds3Request;

import java.util.List;

public class Request {

    @JacksonXmlProperty(isAttribute = true, localName = "BucketRequirement")
    private Ds3Request.Requirement bucketRequirement;

    @JacksonXmlProperty(isAttribute = true, localName = "HttpVerb")
    private Ds3Request.HttpVerb httpVerb;

    @JacksonXmlProperty(isAttribute = true, localName = "ObjectRequirement")
    private Ds3Request.Requirement objectRequirement;

    @JsonProperty("OptionalQueryParams")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Param> optionalQueryParams;

    @JsonProperty("RequiredQueryParams")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Param> requiredQueryParams;

    public Ds3Request.Requirement getBucketRequirement() {
        return bucketRequirement;
    }

    public void setBucketRequirement(final Ds3Request.Requirement bucketRequirement) {
        this.bucketRequirement = bucketRequirement;
    }

    public Ds3Request.HttpVerb getHttpVerb() {
        return httpVerb;
    }

    public void setHttpVerb(final Ds3Request.HttpVerb httpVerb) {
        this.httpVerb = httpVerb;
    }

    public Ds3Request.Requirement getObjectRequirement() {
        return objectRequirement;
    }

    public void setObjectRequirement(final Ds3Request.Requirement objectRequirement) {
        this.objectRequirement = objectRequirement;
    }
    public List<Param> getOptionalQueryParams() {
        return optionalQueryParams;
    }

    public void setOptionalQueryParams(final List<Param> optionalQueryParams) {
        this.optionalQueryParams = optionalQueryParams;
    }

    public List<Param> getRequiredQueryParams() {
        return requiredQueryParams;
    }

    public void setRequiredQueryParams(final List<Param> requiredQueryParams) {
        this.requiredQueryParams = requiredQueryParams;
    }
}
