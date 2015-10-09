package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class Contract {

    @JsonProperty("RequestHandlers")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<RequestHandler> requestHandlers;

    public List<RequestHandler> getRequestHandlers() {
        return requestHandlers;
    }

    public void setRequestHandlers(List<RequestHandler> requestHandlers) {
        this.requestHandlers = requestHandlers;
    }
}
