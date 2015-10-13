package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Property;

import java.util.List;

public class EnumConstant {

    @JacksonXmlProperty(isAttribute = true, localName = "Name")
    private String name;

    @JsonProperty("Properties")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Property> properties;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(final List<Property> properties) {
        this.properties = properties;
    }

    public Ds3EnumConstant toDs3EnumConstant() {
        final Ds3EnumConstant ds3EnumConstant = new Ds3EnumConstant(
                name,
                toDs3Properties());

        return ds3EnumConstant;
    }

    private ImmutableList<Ds3Property> toDs3Properties() {
        final ImmutableList.Builder<Ds3Property> ds3PropertyBuilder = ImmutableList.builder();
        for (Property property : properties) {
            ds3PropertyBuilder.add(property.toDs3Property());
        }
        return ds3PropertyBuilder.build();
    }
}
