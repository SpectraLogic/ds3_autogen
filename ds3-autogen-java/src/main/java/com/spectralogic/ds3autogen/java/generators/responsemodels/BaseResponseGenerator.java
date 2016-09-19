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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.java.models.Response;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;

import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getImportListFromResponseCodes;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.removeErrorResponseCodes;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.supportsPaginationRequest;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

public class BaseResponseGenerator implements ResponseModelGenerator<Response>, ResponseGeneratorUtil {

    private final static String ABSTRACT_RESPONSE_IMPORT = "com.spectralogic.ds3client.commands.interfaces.AbstractResponse";
    private final static String ABSTRACT_PAGINATION_RESPONSE_IMPORT = "com.spectralogic.ds3client.commands.interfaces.AbstractPaginationResponse";

    @Override
    public Response generate(final Ds3Request ds3Request, final String packageName) {
        final String responseName = NormalizingContractNamesUtil.toResponseName(ds3Request.getName());
        final String parentClass = getParentClass(ds3Request);
        final ImmutableList<Ds3ResponseCode> responseCodes = toResponseCodes(ds3Request);
        final ImmutableList<String> imports = getAllImports(ds3Request, responseCodes, packageName);

        return new Response(
                packageName,
                responseName,
                parentClass,
                responseCodes,
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
     * Returns the import for the parent class for standard response, which
     * is AbstractResponse
     */
    @Override
    public String getParentImport(final Ds3Request ds3Request) {
        if (supportsPaginationRequest(ds3Request)) {
            return ABSTRACT_PAGINATION_RESPONSE_IMPORT;
        }
        return ABSTRACT_RESPONSE_IMPORT;
    }

    /**
     * Returns the parent class that the response extends
     */
    @Override
    public String getParentClass(final Ds3Request ds3Request) {
        return removePath(getParentImport(ds3Request));
    }

    /**
     * Gets all the imports associated with response types that the response will
     * need in order to properly generate the Java request code
     */
    @Override
    public ImmutableList<String> getAllImports(
            final Ds3Request ds3Request,
            final ImmutableList<Ds3ResponseCode> responseCodes,
            final String packageName) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        builder.addAll(getImportListFromResponseCodes(responseCodes));
        //If a response type has an associated import, then the XmlOutput import is also needed
        if (builder.build().size() > 0) {
            builder.add("com.spectralogic.ds3client.serializer.XmlOutput");
        }
        if (builder.build().contains("java.lang.String") || builder.build().contains("String")) {
            builder.add("java.nio.charset.StandardCharsets");
            builder.add("org.apache.commons.io.IOUtils");
        }

        builder.add(getParentImport(ds3Request));
        return builder.build().asList();
    }
}
