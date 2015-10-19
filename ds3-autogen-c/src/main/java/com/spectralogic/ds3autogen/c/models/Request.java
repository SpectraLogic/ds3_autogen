package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;

public class Request {
    private final String name;
    private final Ds3Request.HttpVerb verb;
    private final String path;
    private final ImmutableList<Arguments> requiredArguments;
    private final ImmutableList<Arguments> optionalArguments;

    public Request(
            final String name,
            final Ds3Request.HttpVerb verb,
            final String path,
            final ImmutableList<Arguments> requiredArguments,
            final ImmutableList<Arguments> optionalArguments) {
        this.name = name;
        this.verb = verb;
        this.path = path;
        this.requiredArguments = requiredArguments;
        this.optionalArguments = optionalArguments;
    }

    public String getName() {
        return name;
    }

    public Ds3Request.HttpVerb getVerb() {
        return verb;
    }

    public String getPath() {
        return path;
    }

    public ImmutableList<Arguments> getRequiredArguments() {
        return requiredArguments;
    }

    public ImmutableList<Arguments> getOptionalArguments() {
        return optionalArguments;
    }
}
