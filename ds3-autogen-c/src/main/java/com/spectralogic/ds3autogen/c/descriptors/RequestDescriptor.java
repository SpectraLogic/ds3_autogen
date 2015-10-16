package com.spectralogic.ds3autogen.c.descriptors;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.c.models.*;

public class RequestDescriptor {
    private final Ds3Request ds3Request;
    private final ImmutableList<Arguments> requiredConstructorArguments;
    private final ImmutableList<Arguments> optionalArguments;

    private RequestDescriptor(final Ds3Request ds3Request) {
        this.ds3Request = ds3Request;
        this.requiredConstructorArguments = getRequiredArgs(ds3Request);
        this.optionalArguments = getOptionalArgs(ds3Request);
    }

    private Request convert() {
        return new Request(
                this.ds3Request.getName(),
                this.ds3Request.getHttpVerb(),
                requestPath(this.ds3Request),
                getRequiredArgs(ds3Request));
    }

    public static Request toRequest(final Ds3Request ds3Request) {
        System.out.println("Request::toRequest[" + ds3Request.getName() + "]");
        final RequestDescriptor converter = new RequestDescriptor(ds3Request);
        System.out.println("  converting...");
        return converter.convert();
    }

    private static String requestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"/\"");

        if (ds3Request.getClassification() == Ds3Request.Classification.amazons3) {
            if (ds3Request.getBucketRequirement() == Ds3Request.Requirement.REQUIRED) {
                builder.append(" + this.bucketName");

                if (ds3Request.getObjectRequirement() == Ds3Request.Requirement.REQUIRED) {
                    builder.append(" + \"/\" + this.objectName");
                }
            }
        } else {
            builder.append("_rest_/");
        }

        return builder.toString();
    }

    private static ImmutableList<Arguments> getRequiredArgs(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgsBuilder = ImmutableList.builder();
        System.out.println("Getting required args...");
        if (ds3Request.getBucketRequirement() == Ds3Request.Requirement.REQUIRED) {
            System.out.println("bucket name REQUIRED.");
            requiredArgsBuilder.add(new Arguments("String", "bucketName"));
            if (ds3Request.getObjectRequirement() == Ds3Request.Requirement.REQUIRED) {
                System.out.println("bucket name REQUIRED.");
                requiredArgsBuilder.add(new Arguments("String", "objectName"));
            }
        }

        System.out.println("Getting required query params...");
        if (ds3Request.getRequiredQueryParams() != null) {
            for (final Ds3Param ds3Param : ds3Request.getRequiredQueryParams()) {
                if (ds3Param == null) {
                    break;
                }
                System.out.println("  query param " + ds3Param.getType().toString());
                final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
                System.out.println("param " + paramType + " is required.");
                requiredArgsBuilder.add(new Arguments(paramType, ds3Param.getName()));
            }
        }

        System.out.println("Building required args!");
        return requiredArgsBuilder.build();
    }

    private static ImmutableList<Arguments> getOptionalArgs(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> optionalArgsBuilder = ImmutableList.builder();
        System.out.println("Getting optional args...");
        if (ds3Request.getOptionalQueryParams() != null) {
            for (final Ds3Param ds3Param : ds3Request.getOptionalQueryParams()) {
                final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
                optionalArgsBuilder.add(new Arguments(paramType, ds3Param.getName()));
            }
        }

        return optionalArgsBuilder.build();
    }
}
