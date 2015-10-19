/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Requirement;
import com.spectralogic.ds3autogen.java.models.Arguments;
import com.spectralogic.ds3autogen.java.models.Request;

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
                requiredConstructorArguments,
                optionalArguments,
                imports);
    }

    public static Request toRequest(final Ds3Request ds3Request, final String packageName) {
        final RequestConverter converter = new RequestConverter(ds3Request, packageName);

        return converter.convert();
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

        if (ds3Request.getRequiredQueryParams() != null) {
            for (final Ds3Param ds3Param : ds3Request.getRequiredQueryParams()) {
                if (ds3Param.getType() != null) {
                    final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
                    requiredArgs.add(new Arguments(paramType, ds3Param.getName()));
                } else {
                    //TODO special case non-specified type
                }
            }
        }

        return requiredArgs.build();
    }

    private static ImmutableList<Arguments> getOptionalArgs(final Ds3Request ds3Request) {
        if (ds3Request.getOptionalQueryParams() == null) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> optionalArgs = ImmutableList.builder();

        for (final Ds3Param ds3Param : ds3Request.getOptionalQueryParams()) {
            if (ds3Param.getType() != null) {
                final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
                optionalArgs.add(new Arguments(paramType, ds3Param.getName()));
            } else {
                //TODO special case non-specified type
            }
        }
        return optionalArgs.build();
    }

    private static ImmutableList<String> getImports(final Ds3Request ds3Request) {
        final ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();

        importsBuilder.addAll(getImportsFromParamList(ds3Request.getRequiredQueryParams()));
        importsBuilder.addAll(getImportsFromParamList(ds3Request.getOptionalQueryParams()));

        return importsBuilder.build().asList();
    }

    private static ImmutableSet<String> getImportsFromParamList(final ImmutableList<Ds3Param> paramList) {
        if (paramList == null) {
            return ImmutableSet.of();
        }

        ImmutableSet.Builder<String> importsBuilder = ImmutableSet.builder();
        for (final Ds3Param ds3Param : paramList) {
            if (ds3Param.getType() != null) {
                if (ds3Param.getType().contains(".")) {
                    importsBuilder.add(ds3Param.getType());
                }
            } else {
                //TODO special case non-specified type
            }
        }
        return importsBuilder.build();
    }
}
