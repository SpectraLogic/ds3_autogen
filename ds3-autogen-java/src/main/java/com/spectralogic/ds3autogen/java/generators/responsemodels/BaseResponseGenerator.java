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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.java.models.Response;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getImportListFromResponseCodes;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getResponseModelName;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

public class BaseResponseGenerator implements ResponseModelGenerator<Response>, ResponseGeneratorUtil {

    private final static String ABSTRACT_RESPONSE_IMPORT = "com.spectralogic.ds3client.commands.interfaces.AbstractResponse";

    /** The threshold number for error codes where all codes equal to or greater are error codes */
    protected final static int ERROR_CODE_THRESHOLD = 300;

    @Override
    public Response generate(final Ds3Request ds3Request, final String packageName) {
        final String responseName = NormalizingContractNamesUtil.toResponseName(ds3Request.getName());
        final String parentClass = getParentClass();

        final ImmutableList<Arguments> params = toParamList(ds3Request.getDs3ResponseCodes());
        final ImmutableSet<String> imports = getAllImports(ds3Request);

        final String constructorParams = toConstructorParams(params);

        return new Response(
                packageName,
                responseName,
                parentClass,
                constructorParams,
                imports,
                params);
    }

    /**
     * Converts a list of Arguments into a comma-separated list of parameters
     */
    @Override
    public String toConstructorParams(final ImmutableList<Arguments> params) {
        if (isEmpty(params)) {
            return "";
        }
        return params.stream()
                .map(i -> "final " + i.getType() + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Retrieves the list of parameters needed to create the response POJO
     */
    @Override
    public ImmutableList<Arguments> toParamList(final ImmutableList<Ds3ResponseCode> ds3ResponseCodes) {
        if (isEmpty(ds3ResponseCodes)) {
            return ImmutableList.of();
        }
        return ds3ResponseCodes.stream()
                .filter(i -> i.getCode() < ERROR_CODE_THRESHOLD) //Filter error codes
                .map(BaseResponseGenerator::toParam)
                .filter(Optional::isPresent) //Filters out empty optional arguments
                .map(Optional::get) //Get the Arguments object out of the optional
                .sorted(new CustomArgumentComparator()) //Sorts the arguments by name
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Creates an optional Argument that contains the parameter described in the
     * response code. If no parameter is described (i.e. type = "null") then an
     * empty optional is returned.
     */
    protected static Optional<Arguments> toParam(final Ds3ResponseCode responseCode) {
        if (isEmpty(responseCode.getDs3ResponseTypes())) {
            throw new IllegalArgumentException("Ds3ResponseCodes does not have a type: " + responseCode.getCode());
        }
        final Ds3ResponseType ds3ResponseType = responseCode.getDs3ResponseTypes().get(0);
        final String paramName = createDs3ResponseTypeParamName(ds3ResponseType);
        final String paramType = getResponseModelName(ds3ResponseType);

        if (isEmpty(paramName) || isEmpty(paramType)) {
            return Optional.empty();
        }
        return Optional.of(new Arguments(paramType, paramName));
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
     * Returns the parent class that the response extends
     */
    @Override
    public String getParentClass() {
        return removePath(getParentImport());
    }

    /**
     * Gets all the imports associated with response types that the response will
     * need in order to properly generate the Java request code
     */
    @Override
    public ImmutableSet<String> getAllImports(final Ds3Request ds3Request) {
        if (isEmpty(ds3Request.getDs3ResponseCodes())) {
            return ImmutableSet.of(getParentImport());
        }
        final ImmutableList<Ds3ResponseCode> responseCodes = ds3Request.getDs3ResponseCodes().stream()
                .filter(i -> i.getCode() < ERROR_CODE_THRESHOLD)
                .collect(GuavaCollectors.immutableList());

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.addAll(getImportListFromResponseCodes(responseCodes));
        builder.add(getParentImport());
        return builder.build();
    }

    /**
     * Creates the parameter name associated with a response type. Component types contain
     * name spacing of "List", and all type names end with "Result"
     * Example:
     *   Type is null:  null -> ""
     *   No Component Type:  MyType -> myTypeResult
     *   With Component Type:  MyComponentType -> myComponentTypeListResult
     */
    protected static String createDs3ResponseTypeParamName(final Ds3ResponseType responseType) {
        if (stripPath(responseType.getType()).equalsIgnoreCase("null")) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        if (hasContent(responseType.getComponentType())) {
            builder.append(uncapFirst(stripPath(responseType.getComponentType())))
                    .append("List");
        } else {
            builder.append(uncapFirst(stripPath(responseType.getType())));
        }
        if (!builder.toString().toLowerCase().endsWith("result")) {
            builder.append("Result");
        }
        return builder.toString();
    }
}
