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
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3Element;

import java.util.List;

public class Element {

    @JacksonXmlProperty(isAttribute = true, localName = "Name")
    private String name;

    @JacksonXmlProperty(isAttribute = true, localName = "Type")
    private String type;

    @JacksonXmlProperty(isAttribute = true, localName = "ComponentType")
    private String componentType;

    @JsonProperty("Annotations")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<Annotation> annotations;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(final List<Annotation> annotations) {
        this.annotations = annotations;
    }

    public String getComponentType() {
        return componentType;
    }

    public void setComponentType(final String componentType) {
        this.componentType = componentType;
    }

    public Ds3Element toDs3Element() {
        final Ds3Element ds3Element = new Ds3Element(
                name,
                type,
                componentType,
                toDs3Annotations());

        return ds3Element;
    }

    private ImmutableList<Ds3Annotation> toDs3Annotations() {
        if (annotations == null) {
            return null;
        }
        final ImmutableList.Builder<Ds3Annotation> ds3AnnotationBuilder = ImmutableList.builder();
        for (final Annotation annotation : annotations) {
            ds3AnnotationBuilder.add(annotation.toDs3Annotation());
        }
        return ds3AnnotationBuilder.build();
    }
}
