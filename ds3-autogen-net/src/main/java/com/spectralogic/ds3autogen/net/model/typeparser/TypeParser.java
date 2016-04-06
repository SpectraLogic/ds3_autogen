package com.spectralogic.ds3autogen.net.model.typeparser;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.net.model.common.NullableVariable;

public class TypeParser {

    private final String name;
    private final ImmutableList<NullableVariable> variables;

    public TypeParser(final String name, final ImmutableList<NullableVariable> variables) {
        this.name = name;
        this.variables = variables;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<NullableVariable> getVariables() {
        return variables;
    }
}
