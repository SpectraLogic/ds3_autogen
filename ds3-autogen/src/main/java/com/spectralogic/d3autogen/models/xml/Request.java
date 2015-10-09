package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Request {

    public enum Requirement { REQUIRED, NOT_ALLOWED }
    public enum HttpVerb { GET, DELETE, POST, PUT, HEAD }

    @JacksonXmlProperty(isAttribute = true, localName = "BucketRequirement")
    private Requirement bucketRequirement;

    @JacksonXmlProperty(isAttribute = true, localName = "HttpVerb")
    private HttpVerb httpVerb;

    @JacksonXmlProperty(isAttribute = true, localName = "ObjectRequirement")
    private Requirement objectRequirement;

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
}
