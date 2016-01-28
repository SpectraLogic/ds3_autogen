package com.spectralogic.ds3autogen.java.models;

public class Variable {

    final private String type;
    final private String name;
    final private boolean isRequired;

    public Variable(final String name, final String type, final boolean isRequired) {
        this.name = name;
        this.type = type;
        this.isRequired = isRequired;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return isRequired;
    }
}
