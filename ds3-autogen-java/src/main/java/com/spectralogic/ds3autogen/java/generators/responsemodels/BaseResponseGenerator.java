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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import com.spectralogic.ds3autogen.java.converters.ConvertType;
import com.spectralogic.ds3autogen.java.models.Response;

import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.isSpectraDs3;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseResponseGenerator implements ResponseModelGenerator<Response>, ResponseGeneratorUtil {

    private final static String ABSTRACT_RESPONSE_IMPORT = "com.spectralogic.ds3client.commands.AbstractResponse";

    @Override
    public Response generate(final Ds3Request ds3Request, final String packageName) {
        final String responseName = toResponseName(ds3Request.getName());
        final ImmutableList<Ds3ResponseCode> responseCodes = toResponseCodes(ds3Request);
        final ImmutableList<String> imports = getAllImports(responseCodes, packageName);

        return new Response(
                packageName,
                responseName,
                responseCodes,
                imports);
    }

    /**
     * Converts the Ds3Request name into a Response name by removing the path and
     * changing the name ending from "Request" into "Response"
     */
    protected static String toResponseName(final String ds3RequestName) {
        if (isEmpty(ds3RequestName)) {
            return "";
        }
        final String[] classParts = ds3RequestName.split("\\.");
        return classParts[classParts.length - 1].replace("Request", "Response");
    }

    /**
     * Gets the response codes required to generate this response
     */
    @Override
    public ImmutableList<Ds3ResponseCode> toResponseCodes(
            final Ds3Request request) {
        return removeErrorResponseCodes(request.getDs3ResponseCodes());
    }

    /**
     * Returns the import for the parent class for standard response, which
     * is AbstractResponse
     */
    @Override
    public String getParentImport() {
        return ABSTRACT_RESPONSE_IMPORT;
    }

    /**
     * Gets all the imports associated with response types that the response will
     * need in order to properly generate the Java request code
     */
    @Override
    public ImmutableList<String> getAllImports(
            final ImmutableList<Ds3ResponseCode> responseCodes,
            final String packageName) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        builder.addAll(getAllImportsFromResponseCodes(responseCodes));
        //If a response type has an associated import, then the XmlOutput import is also needed
        if (builder.build().size() > 0) {
            builder.add("com.spectralogic.ds3client.serializer.XmlOutput");
        }

        if (isSpectraDs3(packageName)) {
            builder.add(getParentImport());
        }

        return builder.build().asList();
    }

    /**
     * Gets the imports associated with the payloads of a response code list
     */
    protected static ImmutableSet<String> getAllImportsFromResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            final String curImport = getImportFromResponseCode(responseCode);
            if (hasContent(curImport)) {
                builder.add(curImport);
            }
        }
        return builder.build();
    }

    /**
     * Gets the import associated with this response code if one exists, else it returns an
     * empty string. This assumes that there is only one response type associated with a
     * given response code.
     */
    protected static String getImportFromResponseCode(final Ds3ResponseCode responseCode) {
        if (isEmpty(responseCode.getDs3ResponseTypes())) {
            return "";
        }
        for (final Ds3ResponseType responseType : responseCode.getDs3ResponseTypes()) {
            if (hasContent(responseType.getComponentType())) {
                throw new IllegalArgumentException("Response type should not have a component type: " + responseType.getComponentType());
            }
            if (hasContent(responseType.getType())
                    && responseType.getType().contains(".")
                    && responseCode.getCode() < 400) {
                return ConvertType.toModelName(responseType.getType());
            }
        }
        return "";
    }

    /**
     * Removes response codes that are associated with errors from the list.
     * Error response codes are associated with values greater or equal to 400.
     */
    protected static ImmutableList<Ds3ResponseCode> removeErrorResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            if (responseCode.getCode() < 400) {
                builder.add(responseCode);
            }
        }
        return builder.build();
    }
}
