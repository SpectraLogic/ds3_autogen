package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableList;

public class Ds3Annotation {

    private final String name;
    private final ImmutableList<Ds3AnnotationElement> ds3AnnotationElements;

    public Ds3Annotation(
            final String name,
            final ImmutableList<Ds3AnnotationElement> ds3AnnotationElements) {
        this.name = name;
        this.ds3AnnotationElements = ds3AnnotationElements;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Ds3AnnotationElement> getDs3AnnotationElements() {
        return ds3AnnotationElements;
    }
}
