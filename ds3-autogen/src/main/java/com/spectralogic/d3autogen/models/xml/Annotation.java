package com.spectralogic.d3autogen.models.xml;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.api.models.Ds3AnnotationElement;

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
