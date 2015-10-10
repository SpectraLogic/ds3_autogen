package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class ResponseCode {

    @JsonProperty("Code")
    private String code;

    @JsonProperty("ResponseTypes")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<ResponseType> responseTypes;

    public String getCode() {
        return code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public List<ResponseType> getResponseTypes() {
        return responseTypes;
    }

    public void setResponseTypes(final List<ResponseType> responseTypes) {
        this.responseTypes = responseTypes;
    }
}
