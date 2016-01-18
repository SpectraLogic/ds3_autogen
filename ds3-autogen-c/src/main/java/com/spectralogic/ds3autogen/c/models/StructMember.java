package com.spectralogic.ds3autogen.c.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StructMember {
    private static final Logger LOG = LoggerFactory.getLogger(StructMember.class);

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
