package com.spectralogic.ds3autogen.java.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.HttpVerb;

public class Request {
    private final String packageName;
    private final String name;
    private final HttpVerb verb;
    private final String path;
    private final String constructorArgs;
    private final ImmutableList<Arguments> requiredArguments;
    private final ImmutableList<Arguments> optionalArguments;
    private final ImmutableList<String> imports;

    public Request(final String packageName, final String name, final HttpVerb verb, final String path,
                   final String constructorArgs, final ImmutableList<Arguments> requiredArguments,
                   ImmutableList<Arguments> optionalArguments, ImmutableList<String> imports) {
        this.packageName = packageName;
        this.name = name;
        this.verb = verb;
        this.path = path;
        this.constructorArgs = constructorArgs;
        this.requiredArguments = requiredArguments;
        this.optionalArguments = optionalArguments;
        this.imports = imports;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
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

    public String getConstructorArguments() {
        return constructorArgs;
    }

    public ImmutableList<Arguments> getOptionalArguments() {
        return optionalArguments;
    }

    public ImmutableList<String> getImports() {
        return imports;
    }
}
