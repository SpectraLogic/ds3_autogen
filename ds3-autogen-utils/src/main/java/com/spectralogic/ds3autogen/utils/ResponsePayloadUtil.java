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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Contains utility functions for categorizing and retrieving a
 * request handler's response payloads
 */
public final class ResponsePayloadUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ResponsePayloadUtil.class);

    /**
     * Determines if a given response code denotes a non-error response
     */
    public static boolean isNonErrorCode(final int responseCode) {
        return !isErrorCode(responseCode);
    }

    /**
     * Determines if a given response code denotes an error response
     */
    public static boolean isErrorCode(final int responseCode) {
        return responseCode >= 300;
    }

    /**
     * Determines if a list of Ds3Response codes has at least one response payload.
     * Note: Some commands can have multiple non-error codes with different response payloads.
     */
    public static boolean hasResponsePayload(final ImmutableList<Ds3ResponseCode> responseCodes) {
        final ImmutableList<String> responseTypes = getAllResponseTypes(responseCodes);
        for (final String responseType : responseTypes) {
            if (!responseType.equals("null")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the non-error non-null response payload associated with the request.
     * If one does not exist, then null is returned
     */
    public static String getResponsePayload(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (!hasResponsePayload(responseCodes)) {
            return null;
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final String payload : getAllResponseTypes(responseCodes)) {
            if (!payload.equalsIgnoreCase("null")) {
                builder.add(payload);
            }
        }
        final ImmutableList<String> responsePayloads = builder.build();
        switch (responsePayloads.size()) {
            case 0:
                return null;
            case 1:
                return responsePayloads.get(0);
            default:
                throw new IllegalArgumentException("Request has multiple non-error response payloads");
        }
    }

    /**
     * Gets the list of Response Types from a list of Ds3ResponseCodes
     */
    public static ImmutableList<String> getAllResponseTypes(final ImmutableList<Ds3ResponseCode> responseCodes) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        if (isEmpty(responseCodes)) {
            //No response codes is logged as an error instead of throwing an error
            //because some test may not contain response codes
            LOG.error("There are no Response Codes associated with this request");
            return ImmutableList.of();
        }
        for (final Ds3ResponseCode responseCode : responseCodes) {
            if (isNonErrorCode(responseCode.getCode())) {
                builder.add(getResponseType(responseCode.getDs3ResponseTypes()));
            }
        }
        return builder.build();
    }

    /**
     * Gets the non-error, non-null code associated with a list of Ds3ResponseCodes
     */
    public static Integer getPayloadResponseCode(final ImmutableList<Ds3ResponseCode> responseCodes) {

        final ImmutableList<Integer> codes = getAllNonErrorResponseCodes(responseCodes);
        switch (codes.size()) {
            case 0:
                //Log instead of throw error since GetObject and HeadBucket will have no values
                LOG.debug("There are no non-error non-null response codes for this request");
                return null;
            case 1:
                return codes.get(0);
            default:
                throw new IllegalArgumentException("There are multiple non-error response codes for this request");
        }
    }

    /**
     * Gets the list of non-error and non-null Response Codes from a list of Ds3ResponseCodes
     */
    protected static ImmutableList<Integer> getAllNonErrorResponseCodes(final ImmutableList<Ds3ResponseCode> responseCodes) {
        final ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        if (isEmpty(responseCodes)) {
            LOG.error("Could not retrieve response codes because list was empty");
            return ImmutableList.of();
        }
        final ImmutableList<Ds3ResponseCode> codesWithPayloads = removeNullPayloads(responseCodes);
        if (isEmpty(codesWithPayloads)) {
            LOG.error("There were no response codes with payloads");
            return ImmutableList.of();
        }
        for (final Ds3ResponseCode responseCode : codesWithPayloads) {
            if (isNonErrorCode(responseCode.getCode())) {
                builder.add(responseCode.getCode());
            }
        }
        return builder.build();
    }

    /**
     * Removes response codes with null payloads
     */
    protected static ImmutableList<Ds3ResponseCode> removeNullPayloads(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode code : responseCodes) {
            final String payloadType = getResponseType(code.getDs3ResponseTypes());
            if (!payloadType.equalsIgnoreCase("null")) {
                builder.add(code);
            }
        }
        return builder.build();
    }

    /**
     * Gets the Response Type associated with a Ds3ResponseCode. This assumes that all component
     * response types have already been converted into encapsulating types, which is done within
     * the parser module.
     */
    public static String getResponseType(final ImmutableList<Ds3ResponseType> responseTypes) {
        if (isEmpty(responseTypes)) {
            throw new IllegalArgumentException("Response code does not contain any associated types");
        }
        switch (responseTypes.size()) {
            case 1:
                return responseTypes.get(0).getType();
            default:
                throw new IllegalArgumentException("Response code has multiple associated types");
        }
    }

    /**
     * Determines if the provided Ds3Request contains the specified response payload.
     * The specified payload does not need to include the path
     */
    public static boolean hasSpecifiedPayload(final Ds3Request ds3Request, final String payload) {
        if (isEmpty(ds3Request.getDs3ResponseCodes())) {
            return false;
        }
        final ImmutableList<String> responseTypes = getAllResponseTypes(ds3Request.getDs3ResponseCodes());
        for (final String responseType : responseTypes) {
            if (responseType.endsWith(payload)) {
                return true;
            }
        }
        return false;
    }
}
