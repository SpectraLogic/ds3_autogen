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

import java.util.List;

public class Ds3Request {

    public enum Classification { amazons3, spectrads3, spectrainternal }
    public enum HttpVerb { GET, DELETE, POST, PUT, HEAD }
    public enum Requirement { REQUIRED, NOT_ALLOWED }

    private String name;
    private HttpVerb httpVerb;
    private Classification classification;
    private Requirement bucketRequirement;
    private Requirement objectRequirement;

    private List<Ds3ResponseCode> ds3ResponseCodes;
    private List<Ds3Param> optionalQueryParams;
    private List<Ds3Param> requiredQueryParams;

    public Ds3Request() { }

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
    public HttpVerb getHttpVerb() {
        return httpVerb;
    }

    public void setHttpVerb(final HttpVerb httpVerb) {
        this.httpVerb = httpVerb;
    }

    public List<Ds3ResponseCode> getDs3ResponseCodes() {
        return ds3ResponseCodes;
    }

    public void setDs3ResponseCodes(final List<Ds3ResponseCode> ds3ResponseCodes) {
        this.ds3ResponseCodes = ds3ResponseCodes;
    }

    public List<Ds3Param> getOptionalQueryParams() {
        return optionalQueryParams;
    }

    public void setOptionalQueryParams(final List<Ds3Param> optionalQueryParams) {
        this.optionalQueryParams = optionalQueryParams;
    }

    public List<Ds3Param> getRequiredQueryParams() {
        return requiredQueryParams;
    }

    public void setRequiredQueryParams(final List<Ds3Param> requiredQueryParams) {
        this.requiredQueryParams = requiredQueryParams;
    }

    public Requirement getBucketRequirement() {
        return bucketRequirement;
    }

    public void setBucketRequirement(final Requirement bucketRequirement) {
        this.bucketRequirement = bucketRequirement;
    }

    public Requirement getObjectRequirement() {
        return objectRequirement;
    }

    public void setObjectRequirement(final Requirement objectRequirement) {
        this.objectRequirement = objectRequirement;
    }
}
