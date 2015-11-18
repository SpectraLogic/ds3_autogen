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
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;

public class NameConverter {

    private final static NameConverter nameConverter = new NameConverter();

    private NameConverter() {}

    public static NameConverter getInstance() {
        return nameConverter;
    }

    //Removes "Handler" from all request names within the spec
    public static Ds3ApiSpec renameRequests(final Ds3ApiSpec spec) {
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();

        for (final Ds3Request request : spec.getRequests()) {
            builder.add(stripHandlerFromName(request));
        }

        return new Ds3ApiSpec(builder.build(), spec.getTypes());
    }

    private static Ds3Request stripHandlerFromName(final Ds3Request request) {
        return new Ds3Request(
                stripHandlerFromName(request.getName()),
                request.getHttpVerb(),
                request.getClassification(),
                request.getBucketRequirement(),
                request.getObjectRequirement(),
                request.getAction(),
                request.getResource(),
                request.getResourceType(),
                request.getOperation(),
                request.getDs3ResponseCodes(),
                request.getOptionalQueryParams(),
                request.getRequiredQueryParams());
    }

    private static String stripHandlerFromName(final String requestName) {
        final String nameEnding = "Handler";
        if (requestName == null || requestName.isEmpty()) {
            return null;
        }
        if (requestName.endsWith(nameEnding)) {
            return requestName.substring(0, requestName.length() - nameEnding.length());
        }
        return requestName;
    }
}
