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

package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableList;

public class Ds3Request {

    private final String name;
    private final HttpVerb httpVerb;
    private final Classification classification;
    private final Requirement bucketRequirement;
    private final Requirement objectRequirement;
    private final Action action;
    private final Resource resource;
    private final ResourceType resourceType;
    private final Operation operation;

    private final ImmutableList<Ds3ResponseCode> ds3ResponseCodes;
    private final ImmutableList<Ds3Param> optionalQueryParams;
    private final ImmutableList<Ds3Param> requiredQueryParams;

    public Ds3Request(
            final String name,
            final HttpVerb httpVerb,
            final Classification classification,
            final Requirement bucketRequirement,
            final Requirement objectRequirement,
            final Action action,
            final Resource resource,
            final ResourceType resourceType,
            final Operation operation,
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final ImmutableList<Ds3Param> optionalQueryParams,
            final ImmutableList<Ds3Param> requiredQueryParams) {
        this.name = name;
        this.httpVerb = httpVerb;
        this.classification = classification;
        this.bucketRequirement = bucketRequirement;
        this.objectRequirement = objectRequirement;
        this.action = action;
        this.resource = resource;
        this.resourceType = resourceType;
        this.operation = operation;
        this.ds3ResponseCodes = ds3ResponseCodes;
        this.requiredQueryParams = requiredQueryParams;
        this.optionalQueryParams = optionalQueryParams;
    }

    public Classification getClassification() {
        return classification;
    }

    public String getName() { return name; }

    public HttpVerb getHttpVerb() {
        return httpVerb;
    }

    public ImmutableList<Ds3ResponseCode> getDs3ResponseCodes() { return ds3ResponseCodes; }

    public ImmutableList<Ds3Param> getOptionalQueryParams() {
        return optionalQueryParams;
    }

    public ImmutableList<Ds3Param> getRequiredQueryParams() { return requiredQueryParams; }

    public Requirement getBucketRequirement() {
        return bucketRequirement;
    }

    public Requirement getObjectRequirement() { return objectRequirement; }

    public Action getAction() { return action; }

    public Resource getResource() { return resource; }

    public ResourceType getResourceType() { return resourceType; }

    public Operation getOperation() {
        return operation;
    }
}
