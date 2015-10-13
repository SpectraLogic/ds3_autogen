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
import com.spectralogic.ds3autogen.api.models.Enums;

import java.util.List;

public class Request {

    @JacksonXmlProperty(isAttribute = true, localName = "BucketRequirement")
    private Enums.Requirement bucketRequirement;

    @JacksonXmlProperty(isAttribute = true, localName = "HttpVerb")
    private Enums.HttpVerb httpVerb;

    @JacksonXmlProperty(isAttribute = true, localName = "ObjectRequirement")
    private Enums.Requirement objectRequirement;

    @JacksonXmlProperty(isAttribute = true, localName = "Action")
    private Enums.Action action;

    @JacksonXmlProperty(isAttribute = true, localName = "Resource")
    private Enums.Resource resource;

    @JacksonXmlProperty(isAttribute = true, localName = "ResourceType")
    private Enums.ResourceType resourceType;

    @JacksonXmlProperty(isAttribute = true, localName = "Operation")
    private Enums.Operation operation;

    @JsonProperty("OptionalQueryParams")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Param> optionalQueryParams;

    @JsonProperty("RequiredQueryParams")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Param> requiredQueryParams;

    public Enums.Requirement getBucketRequirement() {
        return bucketRequirement;
    }

    public void setBucketRequirement(final Enums.Requirement bucketRequirement) {
        this.bucketRequirement = bucketRequirement;
    }

    public Enums.HttpVerb getHttpVerb() {
        return httpVerb;
    }

    public void setHttpVerb(final Enums.HttpVerb httpVerb) {
        this.httpVerb = httpVerb;
    }

    public Enums.Requirement getObjectRequirement() {
        return objectRequirement;
    }

    public void setObjectRequirement(final Enums.Requirement objectRequirement) {
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

    public Enums.Action getAction() {
        return action;
    }

    public void setAction(final Enums.Action action) {
        this.action = action;
    }

    public Enums.Resource getResource() {
        return resource;
    }

    public void setResource(final Enums.Resource resource) {
        this.resource = resource;
    }

    public Enums.ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(final Enums.ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Enums.Operation getOperation() {
        return operation;
    }

    public void setOperation(final Enums.Operation operation) {
        this.operation = operation;
    }
}
