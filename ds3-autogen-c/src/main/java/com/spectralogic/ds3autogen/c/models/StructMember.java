package com.spectralogic.ds3autogen.c.models;

public class StructMember {
    private final String type;
    private final String name;

    public StructMember(
            final String type,
            final String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
