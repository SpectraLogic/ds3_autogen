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

package com.spectralogic.ds3autogen.models.xml.rawspec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.spectralogic.ds3autogen.api.models.enums.*;

import java.util.List;

public class Request {

    @JacksonXmlProperty(isAttribute = true, localName = "BucketRequirement")
    private Requirement bucketRequirement;

    @JacksonXmlProperty(isAttribute = true, localName = "HttpVerb")
    private HttpVerb httpVerb;

    @JacksonXmlProperty(isAttribute = true, localName = "ObjectRequirement")
    private Requirement objectRequirement;

    @JacksonXmlProperty(isAttribute = true, localName = "Action")
    private Action action;

    @JacksonXmlProperty(isAttribute = true, localName = "Resource")
    private Resource resource;

    @JacksonXmlProperty(isAttribute = true, localName = "ResourceType")
    private ResourceType resourceType;

    @JacksonXmlProperty(isAttribute = true, localName = "Operation")
    private Operation operation;

    @JacksonXmlProperty(isAttribute = true, localName = "IncludeIdInPath")
    private boolean includeIdInPath;

    @JsonProperty("OptionalQueryParams")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Param> optionalQueryParams;

    @JsonProperty("RequiredQueryParams")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Param> requiredQueryParams;

    public Requirement getBucketRequirement() {
        return bucketRequirement;
    }

    public void setBucketRequirement(final Requirement bucketRequirement) {
        this.bucketRequirement = bucketRequirement;
    }

    public HttpVerb getHttpVerb() {
        return httpVerb;
    }

    public void setHttpVerb(final HttpVerb httpVerb) {
        this.httpVerb = httpVerb;
    }

    public Requirement getObjectRequirement() {
        return objectRequirement;
    }

    public void setObjectRequirement(final Requirement objectRequirement) {
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

    public Action getAction() {
        return action;
    }

    public void setAction(final Action action) {
        this.action = action;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(final Resource resource) {
        this.resource = resource;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(final ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(final Operation operation) {
        this.operation = operation;
    }

    public boolean includeIdInPath() {
        return includeIdInPath;
    }

    public void setIncludeIdInPath(final boolean includeIdInPath) {
        this.includeIdInPath = includeIdInPath;
    }
}
