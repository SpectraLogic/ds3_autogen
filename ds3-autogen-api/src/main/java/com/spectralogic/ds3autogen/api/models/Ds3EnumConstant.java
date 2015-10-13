package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableList;

public class Ds3EnumConstant {

    private final String name;
    private final ImmutableList<Ds3Property> ds3Properties;

    public Ds3EnumConstant(
            final String name,
            final ImmutableList<Ds3Property> ds3Properties) {
        this.name = name;
        this.ds3Properties = ds3Properties;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Ds3Property> getDs3Properties() {
        return ds3Properties;
    }
}
