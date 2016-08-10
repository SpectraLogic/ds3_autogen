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


package com.spectralogic.ds3autogen.models.xml.rawspec;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3AnnotationElement;

import java.util.List;

public class Annotation {

    @JacksonXmlProperty(isAttribute = true, localName = "Name")
    private String name;

    @JsonProperty("AnnotationElements")
    @JacksonXmlElementWrapper(useWrapping = true)
    private List<AnnotationElement> annotationElements;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<AnnotationElement> getAnnotationElements() {
        return annotationElements;
    }

    public void setAnnotationElements(final List<AnnotationElement> annotationElements) {
        this.annotationElements = annotationElements;
    }

    public Ds3Annotation toDs3Annotation() {
        final Ds3Annotation ds3Annotation = new Ds3Annotation(
                name,
                toDs3AnnotationElements());

        return ds3Annotation;
    }

    private ImmutableList<Ds3AnnotationElement> toDs3AnnotationElements() {
        if (annotationElements == null) {
            return null;
        }
        final ImmutableList.Builder<Ds3AnnotationElement> ds3AnnotationElementBuilder = ImmutableList.builder();
        for (final AnnotationElement annotationElement : annotationElements) {
            ds3AnnotationElementBuilder.add(annotationElement.toDs3AnnotationElement());
        }
        return ds3AnnotationElementBuilder.build();
    }
}
