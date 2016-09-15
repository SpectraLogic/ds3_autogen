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
import com.spectralogic.ds3autogen.java.models.Constants;
import com.spectralogic.ds3autogen.java.models.ResponseParser;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.JavaModuleUtil.getCommandPackage;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getImportListFromResponseCodes;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.removeErrorResponseCodes;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseName;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseParserName;

public class BaseResponseParserGenerator implements ResponseParserGenerator<ResponseParser>, ResponseParserGeneratorUtil {

    private final static String ABSTRACT_PARSER_IMPORT = Constants.RESPONSE_PARSER_PACKAGE_PATH + ".interfaces.AbstractResponseParser";

    @Override
    public ResponseParser generate(final Ds3Request ds3Request, final String packageName) {
        final String parserName = toResponseParserName(ds3Request.getName());
        final String responseName = toResponseName(ds3Request.getName());
        final String parentClass = getParentClass();
        final ImmutableList<Ds3ResponseCode> responseCodes = toResponseCodes(ds3Request); //TODO change to template model for generating response switch
        final ImmutableSet<String> imports = toImportList(
                responseName,
                ds3Request,
                removeErrorResponseCodes(ds3Request.getDs3ResponseCodes()));

        return new ResponseParser(
                parserName,
                responseName,
                packageName,
                parentClass,
                imports);
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
            builder.add("com.spectralogic.ds3client.utils.ReadableByteChannelInputStream");
            builder.add("java.io.InputStream");
        }
        if (builder.build().contains("java.lang.String") || builder.build().contains("String")) {
            builder.add("java.nio.charset.StandardCharsets");
            builder.add("org.apache.commons.io.IOUtils");
        }

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
                "java.nio.channels.ReadableByteChannel",
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
