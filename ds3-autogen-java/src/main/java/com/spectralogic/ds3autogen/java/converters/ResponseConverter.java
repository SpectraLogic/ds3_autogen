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

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.Response;

/**
 * Converts a Ds3Request into a Response model used for generating
 * the Java SDK response handler code
 */
public class ResponseConverter {

    private final Ds3Request ds3Request;
    private final String packageName;

    private ResponseConverter(
            final Ds3Request ds3Request,
            final String packageName) {
        this.ds3Request = ds3Request;
        this.packageName = packageName;
    }

    /**
     * Converts data stored within this ResponseConverter into a Response model
     * @return A Response model
     */
    private Response convert() {
        return new Response(
                packageName,
                toResponseName(ds3Request.getName()),
                ds3Request.getDs3ResponseCodes());
    }

    /**
     * Converts a Ds3Request and package name into a Response model
     * @param ds3Request A Ds3Request
     * @param packageName The name of the java package for the response generated code
     * @return A Response model containing information from the Ds3Request and package name
     */
    public static Response toResponse(
            final Ds3Request ds3Request,
            final String packageName) {
        final ResponseConverter converter = new ResponseConverter(ds3Request, packageName);
        return converter.convert();
    }

    /**
     * Converts the Ds3Request name into a Response name by removing the path and
     * changing the name ending from "Request" into "Response"
     * @param ds3RequestName
     * @return The response name
     */
    private static String toResponseName(final String ds3RequestName) {
        final String[] classParts = ds3RequestName.split("\\.");
        return classParts[classParts.length - 1].replace("Request", "Response");
    }
}
