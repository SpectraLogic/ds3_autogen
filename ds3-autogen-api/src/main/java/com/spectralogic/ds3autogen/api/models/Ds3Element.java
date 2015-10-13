package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableList;

public class Ds3Element {

    private final String name;
    private final String type;
    private final String componentType;
    private final ImmutableList<Ds3Annotation> ds3Annotations;

    public Ds3Element(
            final String name,
            final String type,
            final String componentType,
            final ImmutableList<Ds3Annotation> ds3Annotations) {
        this.name = name;
        this.type = type;
        this.componentType = componentType;
        this.ds3Annotations = ds3Annotations;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public ImmutableList<Ds3Annotation> getDs3Annotations() {
        return ds3Annotations;
    }

    public String getComponentType() {
        return componentType;
    }
}
