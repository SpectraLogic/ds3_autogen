package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class RequestHandler {

    public enum Classification { amazons3, spectrads3, spectrainternal }

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
}
