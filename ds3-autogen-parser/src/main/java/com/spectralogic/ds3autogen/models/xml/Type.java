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

package com.spectralogic.ds3autogen.models.xml;

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
        if (elements == null) {
            return null;
        }
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
