package com.spectralogic.ds3autogen.c.models;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.HttpVerb;

public class Request {
    private final String name;
    private final HttpVerb verb;
    private final String path;
    private final ImmutableList<Arguments> requiredArguments;
    private final ImmutableList<Arguments> optionalArguments;

    public Request(
            final String name,
            final HttpVerb verb,
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

    public String getNameRoot() {
        final String nameRoot =  name.substring(name.lastIndexOf('.') + 1);
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, nameRoot);
    }

    public HttpVerb getVerb() {
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
