package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;

public class Source {
    final ImmutableList<Enum> enums;
    final ImmutableList<Struct> structs;
    final ImmutableList<Request> requests;

    public Source(
            final ImmutableList<Enum> enums,
            final ImmutableList<Struct> structs,
            final ImmutableList<Request> requests) {
        this.enums = enums;
        this.structs = structs;
        this.requests = requests;
    }

    public ImmutableList<Enum> getEnums() {
        return enums;
    }

    public ImmutableList<Struct> getStructs() {
        return structs;
    }

    public ImmutableList<Request> getRequests() {
        return requests;
    }
}
