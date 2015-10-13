package com.spectralogic.ds3autogen.api.models;

public class Ds3AnnotationElement {

    private final String name;
    private final String value;
    private final String valueType;

    public Ds3AnnotationElement(
            final String name,
            final String value,
            final String valueType) {
        this.name = name;
        this.value = value;
        this.valueType = valueType;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getValueType() {
        return valueType;
    }
}
