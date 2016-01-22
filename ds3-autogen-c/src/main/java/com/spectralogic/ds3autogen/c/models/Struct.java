package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;

public class Struct {
    private final String name;
    private final ImmutableList<StructMember> variables; // members?
    private final StructHelper structHelper;

    public Struct(
            final String name,
            final ImmutableList<StructMember> variables) {
        this.name = name;
        this.variables = variables;
        this.structHelper = StructHelper.getInstance();
    }

    public String getName() {
        return this.name;
    }

    public ImmutableList<StructMember> getVariables() {
        return variables;
    }

    public StructHelper getStructHelper() {
        return structHelper;
    }
}
