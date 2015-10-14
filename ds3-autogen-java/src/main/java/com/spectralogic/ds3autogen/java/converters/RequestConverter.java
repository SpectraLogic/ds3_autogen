package com.spectralogic.ds3autogen.java.converters;

import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Requirement;
import com.spectralogic.ds3autogen.java.models.Arguments;
import com.spectralogic.ds3autogen.java.models.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestConverter {

    private final Ds3Request ds3Request;
    private final String packageName;
    private final List<Arguments> requiredConstructorArguments;

    private RequestConverter(final Ds3Request ds3Request, final String packageName) {
        this.ds3Request = ds3Request;
        this.packageName = packageName;
        this.requiredConstructorArguments = new ArrayList<>();
    }

    private Request convert() {
        final String[] classParts = ds3Request.getName().split("\\.");

        return new Request(packageName, classParts[classParts.length - 1], ds3Request.getHttpVerb(), requestPath(ds3Request), requiredConstructorArguments);
    }

    public static Request toRequest(final Ds3Request ds3Request, final String packageName) {
        final RequestConverter converter = new RequestConverter(ds3Request, packageName);

        return converter.convert();
    }

    private String requestPath(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        builder.append("\"/\"");

        if (ds3Request.getClassification() == Classification.amazons3) {
            if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
                requiredConstructorArguments.add(new Arguments("String", "bucketName"));
                builder.append(" + this.bucketName");

                if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
                    requiredConstructorArguments.add(new Arguments("String", "objectName"));
                    builder.append(" + \"/\" + this.objectName");
                }
            }
        } else {
            builder.append("_rest_/");
        }

        return builder.toString();
    }

}
