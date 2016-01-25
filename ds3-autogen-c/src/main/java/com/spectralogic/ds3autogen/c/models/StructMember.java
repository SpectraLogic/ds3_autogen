package com.spectralogic.ds3autogen.c.models;

import com.spectralogic.ds3autogen.c.helpers.StructHelper;

public class StructMember {
    private final String type;
    private final String name;
    private final StructHelper structHelper;

    public StructMember(
            final String type,
            final String name) {
        this.type = type;
        this.name = name;
        this.structHelper = StructHelper.getInstance();
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public StructHelper getStructHelper() {
        return structHelper;
    }
}
