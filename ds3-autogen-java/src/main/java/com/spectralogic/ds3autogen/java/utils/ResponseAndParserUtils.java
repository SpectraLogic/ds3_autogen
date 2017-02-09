/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.java.converters.ConvertType;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import java.util.NoSuchElementException;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;

/**
 * Contains util functions used in response and response parser generators
 */
public final class ResponseAndParserUtils {

    private ResponseAndParserUtils() {
        // Pass
    }

    /**
     * Gets the imports associated with the payload models of a response code list
     */
    public static ImmutableSet<String> getImportListFromResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableSet.of();
        }

        return responseCodes.stream()
                .map(ResponseAndParserUtils::getImportFromResponseCode)
                .filter(ConverterUtil::hasContent)
                .collect(GuavaCollectors.immutableSet());
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
    public static ImmutableList<Ds3ResponseCode> removeErrorResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }

        return responseCodes.stream()
                .filter(rc -> rc.getCode() < 400)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the list of response codes from within a list of Ds3ResponseCodes
     */
    public static ImmutableList<Integer> getResponseCodes(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }
        return responseCodes.stream()
                .map(Ds3ResponseCode::getCode)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Retrieves the response model name within a Ds3ResponseType
     */
    public static String getResponseModelName(final Ds3ResponseType ds3ResponseType) {
        return stripPath(
                convertType(
                        ds3ResponseType.getType(),
                        ds3ResponseType.getComponentType()));
    }

    /**
     * * Retrieves the fist instance of Ds3ResponseCode with the specified code
     * @throws NoSuchElementException
     */
    public static Ds3ResponseCode getDs3ResponseCode(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final int code) {
        if (isEmpty(ds3ResponseCodes)) {
            throw new NoSuchElementException("Ds3ResponseCode list is empty");
        }
        return ds3ResponseCodes.stream()
                .filter(i -> i.getCode() == code)
                .findFirst()
                .get();
    }

    /**
     * Creates the Java type from elements, converting component types into a List.
     */
    public static String convertType(final Element element) throws IllegalArgumentException {
        return convertType(element.getType(), element.getComponentType());
    }

    /**
     * Creates the Java type from elements, converting component types into a List,
     * and ChecksumType into ChecksumType.Type
     */
    public static String convertType(final String type, final String componentType) throws IllegalArgumentException {
        if (isEmpty(componentType)) {
            final String typeNoPath = stripPath(type);
            switch (typeNoPath.toLowerCase()) {
                case "checksumtype":
                    return "ChecksumType.Type";
                default:
                    return typeNoPath;
            }
        }
        if (type.equalsIgnoreCase("array")) {
            return "List<" + stripPath(componentType) + ">";
        }
        throw new IllegalArgumentException("Unknown element type: " + type);
    }
}
