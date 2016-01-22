package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.c.helpers.EnumHelper;

public class Enum {
    private final String name;
    private final ImmutableList<String> values;
    private final EnumHelper enumHelper;

    public Enum(
            final String name,
            final ImmutableList<String> values) {
        this.name = name;
        this.values = values;
        this.enumHelper = EnumHelper.getInstance();
    }

    public String getName() {
        return this.name;
    }

    public ImmutableList<String> getValues() {
        return values;
    }

    public EnumHelper getEnumHelper() {
        return enumHelper;
    }
}
