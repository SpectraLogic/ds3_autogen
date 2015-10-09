package com.spectralogic.ds3autogen.api.models;

import com.google.common.collect.ImmutableList;

public class Ds3ApiSpec {

    private final ImmutableList<Ds3Request> requests;
    private final ImmutableList<Ds3Type> types;

    public Ds3ApiSpec(final ImmutableList<Ds3Request> requests, final ImmutableList<Ds3Type> types) {
        this.requests = requests;
        this.types = types;
    }

    public ImmutableList<Ds3Request> getRequests() {
        return this.requests;
    }

    public ImmutableList<Ds3Type> getTypes() {
        return this.types;
    }
}
