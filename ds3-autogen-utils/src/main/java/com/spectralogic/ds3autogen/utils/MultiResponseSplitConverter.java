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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;

/**
 * Converts Ds3Requests with multiple response types for a given response code
 * into separate Ds3Requests, each with only one response type.
 */
public final class MultiResponseSplitConverter {

    private static final Logger LOG = LoggerFactory.getLogger(MultiResponseSplitConverter.class);

    protected static final String NAMESPACE_FULL_DETAILS = "FullDetails";

    //Names of known request handlers that have two response types for a given response code
    private static final String GET_PHYSICAL_PLACEMENT_FOR_OBJECTS = "GetPhysicalPlacementForObjectsRequestHandler";
    private static final String VERIFY_PHYSICAL_PLACEMENT_FOR_OBJECTS = "VerifyPhysicalPlacementForObjectsRequestHandler";
    private static final String GET_TAPE_PARTITION = "GetTapePartitionRequestHandler";
    private static final String GET_TAPE = "GetTapeRequestHandler";

    private MultiResponseSplitConverter() { }

    /**
     * Splits Ds3Requests with multiple response types for a given response code into
     * distinct Ds3Requests with only one response type for each response code.
     * @param ds3ApiSpec A spec containing a list of Ds3ApiRequests to convert
     */
    public static Ds3ApiSpec splitAllMultiResponseRequests(final Ds3ApiSpec ds3ApiSpec) {
        return new Ds3ApiSpec(
                splitAllMultiResponseRequests(ds3ApiSpec.getRequests()),
                ds3ApiSpec.getTypes());
    }

    /**
     * Splits Ds3Requests with multiple response types for a given response code into
     * distinct Ds3Requests with only one response type for each response code.
     * @param ds3Requests A list of Ds3Requests to be converted
     */
    public static ImmutableList<Ds3Request> splitAllMultiResponseRequests(
            final ImmutableList<Ds3Request> ds3Requests) {
        if (isEmpty(ds3Requests)) {
            LOG.info("No requests");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request ds3Request : ds3Requests) {
            builder.addAll(splitMultiResponseRequest(ds3Request));
        }
        return builder.build();
    }

    /**
     * Splits a Ds3Request into multiple Ds3Requests if there are multiple response types for a
     * given response code. If there exists only one response type for each response code, then
     * the request is returned unmodified.
     * @param ds3Request A Ds3Request
     * @return A list of Ds3Requests with only one response type per response code.
     */
    protected static ImmutableList<Ds3Request> splitMultiResponseRequest(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        if (!hasMultipleResponseTypes(ds3Request)) {
            return builder.add(ds3Request).build();
        }

        final String requestName = stripPath(ds3Request.getName());
        switch (requestName) {
            case GET_PHYSICAL_PLACEMENT_FOR_OBJECTS:
                return splitGetPhysicalPlacementForObjects(ds3Request);
            case VERIFY_PHYSICAL_PLACEMENT_FOR_OBJECTS:
                return splitVerifyPhysicalPlacementForObjects(ds3Request);
            case GET_TAPE_PARTITION:
                return splitGetTapePartition(ds3Request);
            case GET_TAPE:
                return splitGetTape(ds3Request);
            default:
                throw new IllegalArgumentException("Unexpected request has multiple response types for a single response code:" + ds3Request.getName());
        }
    }

    /**
     * Determines if a given Ds3Request contains a response code with more than one
     * response type.
     */
    protected static boolean hasMultipleResponseTypes(final Ds3Request ds3Request) {
        if (isEmpty(ds3Request.getDs3ResponseCodes())) {
            LOG.info("The Ds3Request " + ds3Request.getName() + " does not have any response codes");
            return false;
        }

        for (final Ds3ResponseCode  ds3ResponseCode : ds3Request.getDs3ResponseCodes()) {
            if (isEmpty(ds3ResponseCode.getDs3ResponseTypes())) {
                LOG.info("Ds3Request " + ds3Request.getName() + ": response code " + ds3ResponseCode.getCode() + " has no response types");
            }
            else if (ds3ResponseCode.getDs3ResponseTypes().size() > 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Splits the GetPhysicalPlacementForObjects request handler into two separate request
     * handlers. One of the request handlers will have FullDetails removed as an optional
     * query param with a return type of PhysicalPlacementApiBean. The other will have
     * FullDetails added as a required query param with a return type of BlobApiBeansContainer.
     * @param ds3Request The Get Physical Placement For Objects request handler
     */
    protected static ImmutableList<Ds3Request> splitGetPhysicalPlacementForObjects(
            final Ds3Request ds3Request) {
        if (!stripPath(ds3Request.getName()).equals(GET_PHYSICAL_PLACEMENT_FOR_OBJECTS)) {
            throw new IllegalArgumentException("The Ds3Request is not the expected " + GET_PHYSICAL_PLACEMENT_FOR_OBJECTS
                    + ": " + ds3Request.getName());
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();

        //Add request without Full Details param with a payload of PhysicalPlacementApiBean
        builder.add(removeOptionalParam(
                ds3Request,
                "FullDetails",
                200,
                "PhysicalPlacementApiBean",
                null));

        //Add request with required Full Details param with a payload of BlobApiBeansContainer
        builder.add(makeRequiredParam(
                ds3Request,
                NAMESPACE_FULL_DETAILS,
                "FullDetails",
                200,
                "BlobApiBeansContainer",
                null));

        return builder.build();
    }

    /**
     * Splits the VerifyPhysicalPlacementForObjects request handler into two separate request
     * handlers. One of the request handlers will have FullDetails removed as an optional
     * query param with a return type of PhysicalPlacementApiBean. The other will have
     * FullDetails added as a required query param with a return type of BlobApiBeansContainer.
     * @param ds3Request The Verify Physical Placement For Objects request handler
     */
    protected static ImmutableList<Ds3Request> splitVerifyPhysicalPlacementForObjects(
            final Ds3Request ds3Request) {
        if (!stripPath(ds3Request.getName()).equals(VERIFY_PHYSICAL_PLACEMENT_FOR_OBJECTS)) {
            throw new IllegalArgumentException("The Ds3Request is not the expected "
                    + VERIFY_PHYSICAL_PLACEMENT_FOR_OBJECTS
                    + ": " + ds3Request.getName());
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        //Add request without Full Details param with a payload of PhysicalPlacementApiBean
        builder.add(removeOptionalParam(
                ds3Request,
                "FullDetails",
                200,
                "PhysicalPlacementApiBean",
                null));

        //Add request with required Full Details param with a payload of BlobApiBeansContainer
        builder.add(makeRequiredParam(
                ds3Request,
                NAMESPACE_FULL_DETAILS,
                "FullDetails",
                200,
                "BlobApiBeansContainer",
                null));

        return builder.build();
    }

    /**
     * Splits the GetTapePartition request handler into two separate request
     * handlers. One of the request handlers will have FullDetails removed as an optional
     * query param with a return type of TapePartition. The other will have
     * FullDetails added as a required query param with a return type of DetailedTapePartition.
     * @param ds3Request The Get Tape Partition request handler
     */
    protected static ImmutableList<Ds3Request> splitGetTapePartition(
            final Ds3Request ds3Request) {
        if (!stripPath(ds3Request.getName()).equals(GET_TAPE_PARTITION)) {
            throw new IllegalArgumentException("The Ds3Request is not the expected "
                    + GET_TAPE_PARTITION
                    + ": " + ds3Request.getName());
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        //Add request without Full Details param with a payload of TapePartition
        builder.add(removeOptionalParam(
                ds3Request,
                "FullDetails",
                200,
                "TapePartition",
                null));

        //Add request with required Full Details param with a payload of DetailedTapePartition
        builder.add(makeRequiredParam(
                ds3Request,
                NAMESPACE_FULL_DETAILS,
                "FullDetails",
                200,
                "DetailedTapePartition",
                null));

        return builder.build();
    }

    /**
     * Splits the GetTape request handler into two separate request
     * handlers. One of the request handlers will have FullDetails removed as an optional
     * query param with a return type of Tape. The other will have
     * FullDetails added as a required query param with a return type of TapeApiBean.
     * @param ds3Request The Get Tape request handler
     */
    protected static ImmutableList<Ds3Request> splitGetTape(
            final Ds3Request ds3Request) {
        if (!stripPath(ds3Request.getName()).equals(GET_TAPE)) {
            throw new IllegalArgumentException("The Ds3Request is not the expected "
                    + GET_TAPE
                    + ": " + ds3Request.getName());
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        //Add request without Full Details param with a payload of Tape
        builder.add(removeOptionalParam(
                ds3Request,
                "FullDetails",
                200,
                "Tape",
                null));

        //Add request with required Full Details param with a payload of TapeApiBean
        builder.add(makeRequiredParam(
                ds3Request,
                NAMESPACE_FULL_DETAILS,
                "FullDetails",
                200,
                "TapeApiBean",
                null));

        return builder.build();
    }

    /**
     * Modifies a Ds3Request to change a specified optional param into a required
     * param, name spacing the request name, and modifying a specified response type.
     * The specified response code will have the specified response type. This is
     * used when splitting a Ds3Request with multiple response payloads associated
     * with a given response code.
     * @param ds3Request A Ds3Request
     * @param nameSpacing The name spacing used to differentiate this request in the split
     * @param optionalParamName The name of the optional parameter being modified
     * @param responseCode The response code to be modified
     * @param responseType The name of the response type to be associated with the response code
     * @param responseComponentType The name of the component type to be associated with the
     *                              the response code. This may be null
     */
    protected static Ds3Request makeRequiredParam(
            final Ds3Request ds3Request,
            final String nameSpacing,
            final String optionalParamName,
            final int responseCode,
            final String responseType,
            final String responseComponentType) {
        return new Ds3Request(
                ds3Request.getName().replace("Request", nameSpacing + "Request"),
                ds3Request.getHttpVerb(),
                ds3Request.getClassification(),
                ds3Request.getBucketRequirement(),
                ds3Request.getObjectRequirement(),
                ds3Request.getAction(),
                ds3Request.getResource(),
                ds3Request.getResourceType(),
                ds3Request.getOperation(),
                modifyResponseCode(
                        ds3Request.getDs3ResponseCodes(),
                        responseCode,
                        responseType,
                        responseComponentType),
                removeDs3Param(
                        ds3Request.getOptionalQueryParams(),
                        optionalParamName),
                addDs3Param(
                        ds3Request.getRequiredQueryParams(),
                        getDs3Param(ds3Request.getOptionalQueryParams(), optionalParamName))
        );
    }

    /**
     * Removes a specified optional param from a Ds3Request, as well as modifying
     * the specified response type. The specified response code will have the specified
     * response type. This is used when splitting a Ds3Request with multiple response
     * payloads associated with a given response code.
     * @param ds3Request A Ds3Request
     * @param optionalParamName The name of the optional parameter to be removed
     * @param responseCode The response code to be modified
     * @param responseType The name of the response type to be associated with the response code
     * @param responseComponentType The name of the component type to be associated with the
     *                              response code. This may be null
     */
    protected static Ds3Request removeOptionalParam(
            final Ds3Request ds3Request,
            final String optionalParamName,
            final int responseCode,
            final String responseType,
            final String responseComponentType) {
        return new Ds3Request(
                ds3Request.getName(),
                ds3Request.getHttpVerb(),
                ds3Request.getClassification(),
                ds3Request.getBucketRequirement(),
                ds3Request.getObjectRequirement(),
                ds3Request.getAction(),
                ds3Request.getResource(),
                ds3Request.getResourceType(),
                ds3Request.getOperation(),
                modifyResponseCode(
                        ds3Request.getDs3ResponseCodes(),
                        responseCode,
                        responseType,
                        responseComponentType),
                removeDs3Param(
                        ds3Request.getOptionalQueryParams(),
                        optionalParamName),
                ds3Request.getRequiredQueryParams()
        );
    }

    /**
     * Removes a Ds3Param from a list of Ds3Params by name.
     * @param ds3Params A list of Ds3Params
     * @param paramName The name of the param to be removed
     * @return The list of Ds3Params minus any param with the specified name.
     */
    protected static ImmutableList<Ds3Param> removeDs3Param(
            final ImmutableList<Ds3Param> ds3Params,
            final String paramName) {
        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        if (isEmpty(ds3Params)) {
            return builder.build();
        }
        for (final Ds3Param ds3Param : ds3Params) {
            if (!ds3Param.getName().equalsIgnoreCase(paramName)) {
                builder.add(ds3Param);
            }
        }
        return builder.build();
    }

    /**
     * Retries the specified Ds3Param from a list of Ds3Params, or null if it
     * was not found.
     * @param params A list of Ds3Params
     * @param paramName The name of the Ds3Param that is being searched for
     */
    protected static Ds3Param getDs3Param(
            final ImmutableList<Ds3Param> params,
            final String paramName) {
        if (isEmpty(params)) {
            return null;
        }
        for (final Ds3Param param : params) {
            if (param.getName().equalsIgnoreCase(paramName)) {
                return param;
            }
        }
        LOG.error("Did not find expected Ds3Param " + paramName);
        return null;
    }

    /**
     * Adds a Ds3Param to a list of Ds3Params.
     * @param ds3Params List of Ds3Params
     * @param param The param to be added to the list
     */
    protected static ImmutableList<Ds3Param> addDs3Param(
            final ImmutableList<Ds3Param> ds3Params,
            final Ds3Param param) {
        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        if (hasContent(ds3Params)) {
            builder.addAll(ds3Params);
        }
        builder.add(param);
        return builder.build();
    }

    /**
     * Modifies the response type associated with the specified response code.
     * This is used to specify which response type should be associated with the
     * specified response code.  All other response codes are unmodified.
     * @param ds3ResponseCodes A list of response codes
     * @param code The code whose response type will be modified
     * @param responseType The name of the response type that should remain
     *                     associated with the specified code
     * @param componentType The name of the component type that should remain
     *                      associated with the specified code
     * @return The list of response codes, with the specified code having only
     *         the specified response type.
     */
    protected static ImmutableList<Ds3ResponseCode> modifyResponseCode(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final int code,
            final String responseType,
            final String componentType) {
        if (isEmpty(ds3ResponseCodes)) {
            LOG.debug("Attempting to modify empty list of response codes");
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode ds3ResponseCode : ds3ResponseCodes) {
            if (ds3ResponseCode.getCode() == code) {
                builder.add(new Ds3ResponseCode(
                        ds3ResponseCode.getCode(),
                        modifyResponseType(
                                ds3ResponseCode.getDs3ResponseTypes(),
                                responseType,
                                componentType)));
            } else {
                builder.add(new Ds3ResponseCode(
                        ds3ResponseCode.getCode(),
                        ds3ResponseCode.getDs3ResponseTypes()));
            }
        }
        return builder.build();
    }

    /**
     * Modifies a list of response types to include only the specified response type
     * @param ds3ResponseTypes A Ds3ResponseType
     * @param responseType The name of the type
     * @param componentType The name of the component type, or null if a component type
     *                      does not exist for this type
     */
    protected static ImmutableList<Ds3ResponseType> modifyResponseType(
            final ImmutableList<Ds3ResponseType> ds3ResponseTypes,
            final String responseType,
            final String componentType) {
        if (isEmpty(ds3ResponseTypes)) {
            LOG.error("Attempting to modify empty list of response types");
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3ResponseType> builder = ImmutableList.builder();
        for (final Ds3ResponseType ds3ResponseType : ds3ResponseTypes) {
            if (isSpecifiedDs3ResponseType(ds3ResponseType, responseType, componentType)) {
                builder.add(ds3ResponseType);
            }
        }
        //Check that there is exactly one response type
        if (builder.build().size() != 1) {
            LOG.error("The modified response type list is not of size 1, but of size " + builder.build().size());
        }
        return builder.build();
    }

    /**
     * Determines if a Ds3ResponseType has the specified response type and component type. The
     * component type may be null.
     * @param ds3ResponseType A Ds3ResponseType
     * @param responseType The response type that is being compared to the Ds3ResponseType
     * @param componentType The component type that is being compared to the Ds3ResponseType
     */
    protected static boolean isSpecifiedDs3ResponseType(
            final Ds3ResponseType ds3ResponseType,
            final String responseType,
            final String componentType) {
        if (isEmpty(responseType) || isEmpty(ds3ResponseType.getType())) {
            LOG.error("Attempting to verify empty response type");
            return false;
        }
        if (getTypeNameWithoutPath(ds3ResponseType.getType()).equals(responseType)) {
            if (hasContent(componentType)
                    && hasContent(ds3ResponseType.getComponentType())
                    && getTypeNameWithoutPath(ds3ResponseType.getComponentType()).equals(componentType)) {
                return true;
            }
            if (isEmpty(componentType) && isEmpty(ds3ResponseType.getComponentType())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the name of a type. Any path is stripped, and if there is a '$' character
     * in the name, then the type name is taken from what occurs after the dollar sign.
     * @param typeName The name of a type or component type
     */
    protected static String getTypeNameWithoutPath(final String typeName) {
        if (isEmpty(typeName)) {
            return "";
        }
        final String typeWithoutPath = stripPath(typeName);
        if (typeWithoutPath.contains("$")) {
            return typeWithoutPath.substring(typeWithoutPath.lastIndexOf('$') + 1);
        }
        return typeWithoutPath;
    }
}
