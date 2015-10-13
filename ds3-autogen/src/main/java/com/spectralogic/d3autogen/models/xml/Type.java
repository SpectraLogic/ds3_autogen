package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.Ds3Type;

import java.util.List;

public class Type {

    @JacksonXmlProperty(isAttribute = true, localName = "Name")
    private String name;

    @JsonProperty("Elements")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Element> elements;

    @JsonProperty("EnumConstants")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<EnumConstant> enumConstants;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(final List<Element> elements) {
        this.elements = elements;
    }

    public List<EnumConstant> getEnumConstants() {
        return enumConstants;
    }

    public void setEnumConstants(final List<EnumConstant> enumConstants) {
        this.enumConstants = enumConstants;
    }

    public Ds3Type toDs3Type() {
        final Ds3Type ds3Type = new Ds3Type(
                name,
                toDs3Elements(),
                toDs3EnumConstant());

        return ds3Type;
    }

    private ImmutableList<Ds3Element> toDs3Elements() {
        final ImmutableList.Builder<Ds3Element> ds3ElementBuilder = ImmutableList.builder();
        for (final Element element : elements) {
            ds3ElementBuilder.add(element.toDs3Element());
        }
        return ds3ElementBuilder.build();
    }

    private ImmutableList<Ds3EnumConstant> toDs3EnumConstant() {
        if (enumConstants == null) {
            return null;
        }
        final ImmutableList.Builder<Ds3EnumConstant> ds3EnumConstantBuilder = ImmutableList.builder();
        for (final EnumConstant enumConstant : enumConstants) {
            ds3EnumConstantBuilder.add(enumConstant.toDs3EnumConstant());
        }
        return ds3EnumConstantBuilder.build();
    }
}
