package com.spectralogic.ds3autogen.java.models;

public class Arguments {
    final private String type;
    final private String name;

    public Arguments(final String type, final String name) {
        this.type = type;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
