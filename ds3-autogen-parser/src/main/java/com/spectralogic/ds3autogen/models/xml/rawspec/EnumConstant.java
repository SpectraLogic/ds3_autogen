/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.models.xml.rawspec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3EnumConstant;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Property;

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
        if (properties == null) {
            return null;
        }
        final ImmutableList.Builder<Ds3Property> ds3PropertyBuilder = ImmutableList.builder();
        for (final Property property : properties) {
            ds3PropertyBuilder.add(property.toDs3Property());
        }
        return ds3PropertyBuilder.build();
    }
}
