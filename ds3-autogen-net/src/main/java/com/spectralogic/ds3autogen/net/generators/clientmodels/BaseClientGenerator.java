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

package com.spectralogic.ds3autogen.net.generators.clientmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import com.spectralogic.ds3autogen.net.model.client.BaseClient;
import com.spectralogic.ds3autogen.net.model.client.PayloadCommand;
import com.spectralogic.ds3autogen.net.model.client.VoidCommand;
import com.spectralogic.ds3autogen.utils.ClientGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseClientGenerator implements  ClientModelGenerator<BaseClient>{

    private static final Logger LOG = LoggerFactory.getLogger(BaseClientGenerator.class);

    @Override
    public BaseClient generate(final ImmutableList<Ds3Request> ds3Requests) {
        final ImmutableList<PayloadCommand> payloadCommands = toPayloadCommands(ds3Requests);
        final ImmutableList<VoidCommand> voidCommands = toVoidCommands(ds3Requests);

        return new BaseClient(payloadCommands, voidCommands);
    }

    /**
     * Converts the Ds3Requests with no response type into Void Commands
     */
    protected static ImmutableList<VoidCommand> toVoidCommands(
            final ImmutableList<Ds3Request> ds3Requests) {
        final ImmutableList.Builder<VoidCommand> builder = ImmutableList.builder();
        final ImmutableList<Ds3Request> voidRequests = getRequestsBasedOnResponsePayload(ds3Requests, false);
        for (final Ds3Request ds3Request : voidRequests) {
            builder.add(toVoidCommand(ds3Request));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Request into a Void Command. This assumes that the Ds3Request is
     * a command with only Null Response Types
     */
    protected static VoidCommand toVoidCommand(final Ds3Request ds3Request) {
        return new VoidCommand(
                ClientGeneratorUtil.removePath(ds3Request.getName()),
                ClientGeneratorUtil.toCommandName(ds3Request.getName()),
                getHttpStatusCode(ds3Request.getDs3ResponseCodes()));
    }

    /**
     * Gets the value of the HttpStatusCode associated with the void response type.
     * code > 300: empty string (error)
     * code == 204: HttpStatusCode.NoContent
     * Else: HttpStatusCode.OK
     */
    protected static String getHttpStatusCode(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            LOG.error("There are no response codes");
            return "";
        }
        final ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            if (isNotErrorCode(responseCode.getCode())) {
                builder.add(responseCode.getCode());
            }
        }
        final ImmutableList<Integer> codes = builder.build();
        if (isEmpty(codes)) {
            LOG.error("There are no non-error response codes within the list: " + codes.toString());
            return "";
        }
        if (codes.contains(204)) {
            return "NoContent";
        }
        return "OK";
    }

    /**
     * Determines if a given response code denotes an error response
     */
    protected static boolean isNotErrorCode(final int responseCode) {
        return responseCode < 300;
    }

    /**
     * Converts the Ds3Requests with a response type into Payload Commands
     */
    protected static ImmutableList<PayloadCommand> toPayloadCommands(
            final ImmutableList<Ds3Request> ds3Requests) {
        final ImmutableList.Builder<PayloadCommand> builder = ImmutableList.builder();
        final ImmutableList<Ds3Request> payloadRequests = getRequestsBasedOnResponsePayload(ds3Requests, true);
        for (final Ds3Request ds3Request : payloadRequests) {
            builder.add(toPayloadCommand(ds3Request));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Request into a Payload Command. This assumes that the Ds3Request is
     * a command with a non-null Response Type
     */
    protected static PayloadCommand toPayloadCommand(final Ds3Request ds3Request) {
        return new PayloadCommand(
                ClientGeneratorUtil.removePath(ds3Request.getName()),
                ClientGeneratorUtil.toCommandName(ds3Request.getName()),
                ClientGeneratorUtil.toResponseName(ds3Request.getName()));
    }

    /**
     * Retrieves a list of Ds3Requests that either have no payload, or which have payload, based
     * on hasPayload parameter.
     */
    protected static ImmutableList<Ds3Request> getRequestsBasedOnResponsePayload(
            final ImmutableList<Ds3Request> ds3Requests,
            final boolean hasPayload) {
        if (isEmpty(ds3Requests)) {
            LOG.debug("There were no Ds3Requests to generate the client");
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request ds3Request : ds3Requests) {
            if (hasResponsePayload(ds3Request.getDs3ResponseCodes()) == hasPayload) {
                builder.add(ds3Request);
            }
        }
        return builder.build();
    }

    /**
     * Determines if a list of Ds3Response codes has at least one response payload.
     * Note: Some commands can have multiple non-error codes with different response payloads.
     */
    protected static boolean hasResponsePayload(final ImmutableList<Ds3ResponseCode> responseCodes) {
        final ImmutableList<String> responseTypes = getAllResponseTypes(responseCodes);
        for (final String responseType : responseTypes) {
            if (!responseType.equals("null")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the list of Response Types from a list of Ds3ResponseCodes
     */
    protected static ImmutableList<String> getAllResponseTypes(final ImmutableList<Ds3ResponseCode> responseCodes) {
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        if (isEmpty(responseCodes)) {
            //No response codes is logged as an error instead of throwing an error
            //because some test may not contain response codes
            LOG.error("There are no Response Codes associated with this request");
            return ImmutableList.of();
        }
        for (final Ds3ResponseCode responseCode : responseCodes) {
            if (isNotErrorCode(responseCode.getCode())) {
                builder.add(getResponseType(responseCode.getDs3ResponseTypes()));
            }
        }
        return builder.build();
    }

    /**
     * Gets the Response Type associated with a Ds3ResponseCode. This assumes that all component
     * response types have already been converted into encapsulating types.
     */
    protected static String getResponseType(final ImmutableList<Ds3ResponseType> responseTypes) {
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
}
