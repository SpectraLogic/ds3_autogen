package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Requirement;
import com.spectralogic.ds3autogen.java.models.Arguments;
import com.spectralogic.ds3autogen.java.models.Request;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RequestConverter {

    private final Ds3Request ds3Request;
    private final String packageName;
    private final ImmutableList<Arguments> requiredConstructorArguments;
    private final ImmutableList<Arguments> optionalArguments;
    private final ImmutableList<String> imports;

    private RequestConverter(final Ds3Request ds3Request, final String packageName) {
        this.ds3Request = ds3Request;
        this.packageName = packageName;
        this.requiredConstructorArguments = getRequiredArgs(ds3Request);
        this.optionalArguments = getOptionalArgs(ds3Request);
        this.imports = getImports(ds3Request);
    }

    private Request convert() {
        final String[] classParts = ds3Request.getName().split("\\.");

        return new Request(packageName,
                classParts[classParts.length - 1],
                ds3Request.getHttpVerb(),
                requestPath(ds3Request),
                constructorArgs(requiredConstructorArguments),
                requiredConstructorArguments,
                optionalArguments,
                imports);
    }

    public static Request toRequest(final Ds3Request ds3Request, final String packageName) {
        final RequestConverter converter = new RequestConverter(ds3Request, packageName);

        return converter.convert();
    }

    private static String constructorArgs(final ImmutableList<Arguments> requiredArguments) {
        if (requiredArguments.isEmpty()) {
            return "";
        }

        final List<String> argArray = new ArrayList<>();

        final Iterator<String> argIter = requiredArguments.stream().map(a -> "final " + a.getType() + " "
                + a.getName().substring(0,1).toLowerCase() + a.getName().substring(1)).iterator();

        while(argIter.hasNext()) {
            argArray.add(argIter.next());
        }

        return String.join(", ", argArray);
    }

    private static String requestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"/\"");

        if (ds3Request.getClassification() == Classification.amazons3) {
            if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
                builder.append(" + this.bucketName");

                if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
                    builder.append(" + \"/\" + this.objectName");
                }
            }
        } else {
            builder.append("_rest_/");
        }

        return builder.toString();
    }

    private static ImmutableList<Arguments> getRequiredArgs(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgs = ImmutableList.builder();

        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
            requiredArgs.add(new Arguments("String", "bucketName"));
            if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
                requiredArgs.add(new Arguments("String", "objectName"));
            }
        }

        for (final Ds3Param ds3Param : ds3Request.getRequiredQueryParams()) {
            final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
            requiredArgs.add(new Arguments(paramType, ds3Param.getName()));
        }

        return requiredArgs.build();
    }

    private static ImmutableList<Arguments> getOptionalArgs(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> optionalArgs = ImmutableList.builder();

        for (final Ds3Param ds3Param : ds3Request.getOptionalQueryParams()) {
            final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
            optionalArgs.add(new Arguments(paramType, ds3Param.getName()));
        }
        return optionalArgs.build();
    }

    private static ImmutableList<String> getImports(final Ds3Request ds3Request) {
        final ImmutableSet.Builder<String> imports = ImmutableSet.builder();

        for (final Ds3Param ds3Param : ds3Request.getRequiredQueryParams()) {
            if (ds3Param.getType().contains(".")) {
                imports.add(ds3Param.getType());
            }
        }
        for (final Ds3Param ds3Param : ds3Request.getOptionalQueryParams()) {
            if (ds3Param.getType().contains(".")) {
                imports.add(ds3Param.getType());
            }
        }

        return imports.build().asList();
    }
}
