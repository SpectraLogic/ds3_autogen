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
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;

/**
 * Converts Ds3ResponseTypes that contain component types into new types that
 * encapsulate the array of the original Ds3ResponseType. These new encapsulating
 * types are also added to the Types list within the Ds3ApiContract. This is used
 * to ensure that generated code can easily parse response payloads.
 */
public final class ResponseTypeConverter {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseTypeConverter.class);

    private static final String NAMESPACE_ARRAY_RESPONSE_TYPES = "List";

    private ResponseTypeConverter() { }

    //TODO unit tests
    /**
     * Converts a Ds3ApiSpec to use updated response types. This includes
     * updating all response types that have component types to new types
     * without component types.
     * These new types are then added to the Spec's Ds3Types list.
     */
    public static Ds3ApiSpec convertResponseTypes(final Ds3ApiSpec ds3ApiSpec) {
        return new Ds3ApiSpec(
                createAllUpdatedDs3RequestResponseTypes(
                        ds3ApiSpec.getRequests()),
                createEncapsulatingModelResponseTypes(
                        ds3ApiSpec.getRequests(),
                        ds3ApiSpec.getTypes()));
    }

    //TODO unit tests
    /**
     * Updates all Ds3ResponseTypes that have component types within the
     * list of Ds3Requests
     */
    protected static ImmutableList<Ds3Request> createAllUpdatedDs3RequestResponseTypes(
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            LOG.info("No requests to update response types");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request request : requests) {
            builder.add(toUpdatedDs3RequestResponseTypes(request));
        }
        return builder.build();
    }

    //TODO unit tests
    /**
     * Updates all Ds3ResponseTypes that have component types within a Ds3Request
     */
    protected static Ds3Request toUpdatedDs3RequestResponseTypes(final Ds3Request request) {
        return new Ds3Request(
                request.getName(),
                request.getHttpVerb(),
                request.getClassification(),
                request.getBucketRequirement(),
                request.getObjectRequirement(),
                request.getAction(),
                request.getResource(),
                request.getResourceType(),
                request.getOperation(),
                toUpdatedDs3ResponseCodeList(
                        request.getDs3ResponseCodes()),
                request.getOptionalQueryParams(),
                request.getRequiredQueryParams());
    }

    //TODO unit tests
    /**
     * Updates all Ds3ResponseTypes that have component types within the
     * list of Ds3ResponseCodes
     */
    protected static ImmutableList<Ds3ResponseCode> toUpdatedDs3ResponseCodeList(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            LOG.info("No response codes to update response types");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.add(new Ds3ResponseCode(
                            responseCode.getCode(),
                            toUpdatedDs3ResponseTypesList(
                                    responseCode.getDs3ResponseTypes())));
        }
        return builder.build();
    }

    //TODO unit tests
    /**
     * Updates all Ds3ResponseTypes that have component types
     */
    protected static ImmutableList<Ds3ResponseType> toUpdatedDs3ResponseTypesList(
            final ImmutableList<Ds3ResponseType> responseTypes) {
        if (isEmpty(responseTypes)) {
            LOG.info("No response types to update");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Ds3ResponseType> builder = ImmutableList.builder();
        for (final Ds3ResponseType responseType : responseTypes) {
            builder.add(toUpdatedDs3ResponseType(responseType));
        }
        return builder.build();
    }

    //TODO unit tests
    /**
     * Updates a Ds3ResponseType so that it will not have a component type. If
     * there is currently a component type, then the type is replaced with a
     * namespaced version of the component type, and the component type is set
     * to null.
     */
    protected static Ds3ResponseType toUpdatedDs3ResponseType(
            final Ds3ResponseType responseType) {
        if (isEmpty(responseType.getComponentType())) {
            return responseType;
        }

        return new Ds3ResponseType(
                responseType.getComponentType() + NAMESPACE_ARRAY_RESPONSE_TYPES,
                null);
    }

    //TODO unit tests
    /**
     * Creates new types for generating encapsulating models. This occurs when a response
     * handler has a return type with a component type. This is done to ensure that all
     * response types can be properly parsed within the generated code.
     * @param requests A Ds3Request
     * @param types A list of Ds3Types
     * @return The list of Ds3Types with the additionally generated types to handle the
     * response payloads that return arrays
     */
    protected static ImmutableMap<String, Ds3Type> createEncapsulatingModelResponseTypes(
            final ImmutableList<Ds3Request> requests,
            final ImmutableMap<String, Ds3Type> types) {
        if (isEmpty(requests)) {
            LOG.info("No requests to generate encapsulating models for");
            return types;
        }
        final ImmutableSet<String> componentTypes = getResponseComponentTypesFromRequests(requests);
        if (isEmpty(componentTypes)) {
            LOG.info("Requests do not contain any responses with component types");
            return types;
        }
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();
        builder.putAll(types);
        for (final String componentType : componentTypes) {
            final Ds3Type ds3Type = toDs3Type(componentType);
            builder.put(ds3Type.getName(), ds3Type);
        }
        return builder.build();
    }

    //TODO unit tests
    /**
     * Converts a response Component Type into a simple Ds3Type that contains a
     * list of the given component type, and where the new Ds3Type is namespaced.
     */
    protected static Ds3Type toDs3Type(final String componentType) {
        return new Ds3Type(
                componentType + NAMESPACE_ARRAY_RESPONSE_TYPES,
                ImmutableList.of(
                        new Ds3Element(
                                stripPath(componentType),
                                "array",
                                componentType,
                                null)),
                null);
    }

    //TODO unit tests
    /**
     * Gets a list of response Component Types that are being converted into new models
     * that are within the Ds3Requests list.
     */
    protected static ImmutableSet<String> getResponseComponentTypesFromRequests(
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            LOG.info("No requests for model generation of response component types");
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3Request request : requests) {
            builder.addAll(getResponseComponentTypesFromResponseCodes(request.getDs3ResponseCodes()));
        }
        return builder.build();
    }

    //TODO unit tests
    /**
     * Gets a list of response Component Types that are being converted into new models
     * that are within the Ds3ResponseCodes list.
     */
    protected static ImmutableSet<String> getResponseComponentTypesFromResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            LOG.info("No response codes for model generation of response component types");
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.addAll(getResponseTypesToUpdateFromResponseTypes(responseCode.getDs3ResponseTypes()));
        }
        return builder.build();
    }

    //TODO unit tests
    /**
     * Gets a list of response Component Types that are being converted into new models
     * that are within the Ds3ResponseTypes list.
     */
    protected static ImmutableSet<String> getResponseTypesToUpdateFromResponseTypes(
            final ImmutableList<Ds3ResponseType> responseTypes) {
        if (isEmpty(responseTypes)) {
            LOG.info("No response types for model generation of response component types");
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        for (final Ds3ResponseType responseType : responseTypes) {
            if (hasContent(responseType.getComponentType())) {
                builder.add(responseType.getComponentType());
            }
        }
        return builder.build();
    }
}
