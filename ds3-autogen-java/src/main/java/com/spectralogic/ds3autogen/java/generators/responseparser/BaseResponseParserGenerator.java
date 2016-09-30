/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.generators.responseparser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.java.models.Constants;
import com.spectralogic.ds3autogen.java.models.ResponseCode;
import com.spectralogic.ds3autogen.java.models.ResponseParser;
import com.spectralogic.ds3autogen.java.models.parseresponse.*;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.java.utils.JavaModuleUtil.getCommandPackage;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.*;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.supportsPaginationRequest;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.*;
import static com.spectralogic.ds3autogen.utils.ResponsePayloadUtil.hasResponsePayload;

public class BaseResponseParserGenerator implements ResponseParserGenerator<ResponseParser>, ResponseParserGeneratorUtil {

    private final static String ABSTRACT_PARSER_IMPORT = Constants.RESPONSE_PARSER_PACKAGE_PATH + ".interfaces.AbstractResponseParser";

    @Override
    public ResponseParser generate(final Ds3Request ds3Request, final String packageName) {
        final String parserName = toResponseParserName(ds3Request.getName());
        final String responseName = toResponseName(ds3Request.getName());
        final String parentClass = getParentClass();
        final boolean hasPaginationHeaders = supportsPaginationRequest(ds3Request);

        final ImmutableList<ResponseCode> responseCodes = toResponseCodeList(
                ds3Request.getDs3ResponseCodes(),
                responseName,
                hasPaginationHeaders);

        final String expectedStatusCodes = toStatusCodeList(responseCodes);

        final ImmutableSet<String> imports = toImportList(
                responseName,
                ds3Request,
                removeErrorResponseCodes(ds3Request.getDs3ResponseCodes()));

        return new ResponseParser(
                parserName,
                responseName,
                packageName,
                parentClass,
                expectedStatusCodes,
                hasPaginationHeaders,
                imports,
                responseCodes);
    }

    /**
     * Creates a comma-separated list of all expected status codes
     */
    protected static String toStatusCodeList(final ImmutableList<ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return "";
        }
        return responseCodes.stream()
                .map(code -> Integer.toString(code.getCode()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Gets the non-error response codes required to generate this response
     */
    @Override
    public ImmutableList<ResponseCode> toResponseCodeList(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String responseName,
            final boolean hasPaginationHeaders) {
        final ImmutableList<Ds3ResponseCode> filteredResponseCodes = removeErrorResponseCodes(ds3ResponseCodes);
        final boolean hasResponsePayload = hasResponsePayload(filteredResponseCodes);
        return filteredResponseCodes.stream()
                .map(rc -> toResponseCode(rc, responseName, hasResponsePayload, hasPaginationHeaders))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3 Response Code into the Response Code model
     * @param responseName Name of the response handler
     */
    protected static ResponseCode toResponseCode(
            final Ds3ResponseCode ds3ResponseCode,
            final String responseName,
            final boolean hasResponsePayload,
            final boolean hasPaginationHeaders) {
        final ParseResponse parseResponse = toParseResponse(
                ds3ResponseCode,
                responseName,
                hasResponsePayload,
                hasPaginationHeaders);

        return new ResponseCode(
                ds3ResponseCode.getCode(),
                parseResponse.toJavaCode());
    }

    /**
     * Converts a Ds3ResponseCode into a Parse Response model which can be used to
     * generate the java code for parsing a response payload
     * @param responseName Name of the response handler
     */
    protected static ParseResponse toParseResponse(
            final Ds3ResponseCode ds3ResponseCode,
            final String responseName,
            final boolean hasResponsePayload,
            final boolean hasPaginationHeaders) {
        if (isEmpty(ds3ResponseCode.getDs3ResponseTypes())) {
            throw new IllegalArgumentException("Response code does not contain any response types: " + ds3ResponseCode.getCode());
        }
        final Ds3ResponseType ds3ResponseType = ds3ResponseCode.getDs3ResponseTypes().get(0);
        final String responseModelName = getResponseModelName(ds3ResponseType);

        if (responseModelName.equalsIgnoreCase("null") && !hasResponsePayload) {
            return new EmptyParseResponse(responseName, hasPaginationHeaders);
        }
        if (responseModelName.equalsIgnoreCase("null") && hasResponsePayload) {
            return new NullParseResponse(responseName, hasPaginationHeaders);
        }
        if (responseModelName.equalsIgnoreCase("string")) {
            return new StringParseResponse(responseName, hasPaginationHeaders);
        }
        return new BaseParseResponse(responseName, responseModelName, hasPaginationHeaders);
    }

    /**
     * Retrieves the java imports needed to generate the response parser
     */
    @Override
    public ImmutableSet<String> toImportList(
            final String responseName,
            final Ds3Request ds3Request,
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        builder.addAll(getImportListFromResponseCodes(responseCodes));
        //If a response type has an associated import, then the XmlOutput import is also needed
        if (builder.build().size() > 0) {
            builder.add("com.spectralogic.ds3client.serializer.XmlOutput");
            builder.add("java.io.InputStream");
        }
        if (builder.build().contains("java.lang.String") || builder.build().contains("String")) {
            builder.add("java.nio.charset.StandardCharsets");
            builder.add("org.apache.commons.io.IOUtils");
        }

        builder.add("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils");
        builder.addAll(requiredImportList());
        builder.add(getParentClassImport());
        builder.add(getCommandPackage(ds3Request) + "." + responseName);

        //Sort imports alphabetically for generated code aesthetics
        return builder.build().stream()
                .sorted()
                .collect(GuavaCollectors.immutableSet());
    }

    /**
     * Returns the set of imports that are required for all response parsers
     */
    protected static ImmutableSet<String> requiredImportList() {
        return ImmutableSet.of(
                "java.io.IOException",
                "com.spectralogic.ds3client.networking.WebResponse");
    }

    /**
     * Retrieves the name of the abstract response parser that is the parent
     * class for the generated request
     */
    @Override
    public String getParentClass() {
        return removePath(getParentClassImport());
    }

    /**
     * Retrieves the import for the abstract response parser
     */
    @Override
    public String getParentClassImport() {
        return ABSTRACT_PARSER_IMPORT;
    }
}
