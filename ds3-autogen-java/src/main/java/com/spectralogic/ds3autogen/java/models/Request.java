package com.spectralogic.ds3autogen.java.models;

import com.spectralogic.ds3autogen.api.models.HttpVerb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Request {
    private final String packageName;
    private final String name;
    private final HttpVerb verb;
    private final String path;
    private final List<Arguments> requiredArguments;

    public Request(final String packageName, final String name, final HttpVerb verb, final String path, final List<Arguments> requiredArguments) {
        this.packageName = packageName;
        this.name = name;
        this.verb = verb;
        this.path = path;
        this.requiredArguments = requiredArguments;
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

    public List<Arguments> getRequiredArguments() {
        return requiredArguments;
    }

    public String getConstructorArguments() {

        if (requiredArguments.isEmpty()) {
            return "";
        }

        final List<String> argArray = new ArrayList<>();

        final Iterator<String> argIter = requiredArguments.stream().map(a -> "final " + a.getType() + " " + a.getName()).iterator();

        while(argIter.hasNext()) {
            argArray.add(argIter.next());
        }

        return String.join(", ", argArray);
    }
}
