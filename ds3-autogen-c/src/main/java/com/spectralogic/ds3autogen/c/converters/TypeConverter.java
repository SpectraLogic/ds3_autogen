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

package com.spectralogic.ds3autogen.c.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.models.*;

public class TypeConverter {
    private final Ds3EnumConstant ds3EnumConstant;


    private TypeConverter(final Ds3EnumConstant ds3EnumConstant) {
        this.ds3EnumConstant = ds3EnumConstant;
    }

    private Type convert() {
        return new Type();
    }

    public static Type toType(final Ds3EnumConstant ds3EnumConstant) {
        System.out.println("Type::toType[" + ds3EnumConstant.getName() + "]");
        final TypeConverter converter = new TypeConverter(ds3EnumConstant);
        System.out.println("  converting...");
        return converter.convert();
    }

    private static ImmutableList<Ds3EnumConstant> getEnums(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> requiredArgsBuilder = ImmutableList.builder();
        System.out.println("Getting required args...");
        if (ds3Request.getBucketRequirement() == Requirement.REQUIRED) {
            System.out.println("bucket name REQUIRED.");
            requiredArgsBuilder.add(new Arguments("String", "bucketName"));
            if (ds3Request.getObjectRequirement() == Requirement.REQUIRED) {
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
}
