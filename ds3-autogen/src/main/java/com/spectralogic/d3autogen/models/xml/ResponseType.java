package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseType {

    @JacksonXmlProperty(isAttribute = true, localName = "Type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }
}
