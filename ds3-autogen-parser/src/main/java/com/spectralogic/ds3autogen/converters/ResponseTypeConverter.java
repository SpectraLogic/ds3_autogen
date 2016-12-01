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

package com.spectralogic.ds3autogen.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.*;
import com.spectralogic.ds3autogen.models.EncapsulatingTypeNames;
import org.atteo.evo.inflector.English;
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

    /**
     * Updates all Ds3ResponseTypes that have component types within the
     * list of Ds3Requests
     */
    protected static ImmutableList<Ds3Request> createAllUpdatedDs3RequestResponseTypes(
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            LOG.debug("No requests to update response types");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();
        for (final Ds3Request request : requests) {
            builder.add(toUpdatedDs3RequestResponseTypes(request));
        }
        return builder.build();
    }

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
                request.getIncludeInPath(),
                toUpdatedDs3ResponseCodeList(
                        request.getDs3ResponseCodes()),
                request.getOptionalQueryParams(),
                request.getRequiredQueryParams());
    }

    /**
     * Updates all Ds3ResponseTypes that have component types within the
     * list of Ds3ResponseCodes
     */
    protected static ImmutableList<Ds3ResponseCode> toUpdatedDs3ResponseCodeList(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            LOG.debug("No response codes to update response types");
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

    /**
     * Updates all Ds3ResponseTypes that have component types
     */
    protected static ImmutableList<Ds3ResponseType> toUpdatedDs3ResponseTypesList(
            final ImmutableList<Ds3ResponseType> responseTypes) {
        if (isEmpty(responseTypes)) {
            LOG.debug("No response types to update");
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Ds3ResponseType> builder = ImmutableList.builder();
        for (final Ds3ResponseType responseType : responseTypes) {
            builder.add(toUpdatedDs3ResponseType(responseType));
        }
        return builder.build();
    }

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
                null,
                responseType.getOriginalTypeName());
    }

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
            LOG.debug("No requests to generate encapsulating models for");
            return types;
        }
        final ImmutableSet<EncapsulatingTypeNames> encapsulatingTypes = getResponseComponentTypesFromRequests(requests);
        if (isEmpty(encapsulatingTypes)) {
            LOG.debug("Requests do not contain any responses with component types");
            return types;
        }
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();
        if (hasContent(types)) {
            builder.putAll(types);
        }
        for (final EncapsulatingTypeNames encapsulatingType : encapsulatingTypes) {
            final Ds3Type ds3Type = toDs3Type(encapsulatingType, types);
            builder.put(ds3Type.getName(), ds3Type);
        }
        return builder.build();
    }

    /**
     * Converts a response Component Type into a simple Ds3Type that contains a
     * list of the given component type, and where the new Ds3Type is namespaced.
     * The element name is pluralized, and an annotation is added to denote the
     * original name for xml parsing.
     */
    protected static Ds3Type toDs3Type(
            final EncapsulatingTypeNames encapsulatingType,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(encapsulatingType.getSdkName())) {
            throw new IllegalArgumentException("Cannot generate Ds3Type from empty response component type");
        }
        //Create the annotation for non-pluralized name scheme
        final ImmutableList<Ds3Annotation> annotations = ImmutableList.of(
                new Ds3Annotation("com.spectralogic.util.marshal.CustomMarshaledName",
                        ImmutableList.of(
                                new Ds3AnnotationElement("Value", getAnnotationName(encapsulatingType, typeMap), "java.lang.String"))));
        return new Ds3Type(
                encapsulatingType.getSdkName() + NAMESPACE_ARRAY_RESPONSE_TYPES,
                getNameToMarshalForEncapsulatingType(encapsulatingType),
                ImmutableList.of(
                        new Ds3Element(
                                English.plural(stripPath(encapsulatingType.getSdkName())),
                                "array",
                                encapsulatingType.getSdkName(),
                                annotations,
                                false)),
                null);
    }

    /**
     * Determines the name to marshal value for the encapsulating type.
     * Note: NameToMarshal = "" denotes no encapsulating xml tag
     */
    protected static String getNameToMarshalForEncapsulatingType(final EncapsulatingTypeNames encapsulatingType) {
        if (hasNoRootElement(encapsulatingType)) {
            return "";
        }
        return "Data";
    }

    /**
     * Determines if this encapsulating type lacks a root element. This
     * is used to special case NamedDetailedTypeList
     */
    protected static boolean hasNoRootElement(final EncapsulatingTypeNames encapsulatingType) {
        return hasContent(encapsulatingType.getSdkName())
                && encapsulatingType.getSdkName().endsWith(".NamedDetailedTape");
    }

    /**
     * Retrieves the annotation name (i.e. "Value") that should be used for
     * proper xml parsing of model created by this Encapsulating Type Names
     * object.
     */
    protected static String getAnnotationName(
            final EncapsulatingTypeNames encapsulatingType,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (hasNoRootElement(encapsulatingType)) {
            return "Tape";
        }
        if (hasContent(typeMap)) {
            final Ds3Type childType = typeMap.get(encapsulatingType.getSdkName());
            if (childType != null && hasContent(childType.getNameToMarshal()) && !childType.getNameToMarshal().equals("Data")) {
                return childType.getNameToMarshal();
            }
        }
        if (hasContent(encapsulatingType.getOriginalName())) {
            return encapsulatingType.getOriginalName();
        }
        return stripPath(encapsulatingType.getSdkName());
    }

    /**
     * Gets a list of response Component Types and original type names that are being
     * converted into new models that are within the Ds3Requests list.
     */
    protected static ImmutableSet<EncapsulatingTypeNames> getResponseComponentTypesFromRequests(
            final ImmutableList<Ds3Request> requests) {
        if (isEmpty(requests)) {
            LOG.debug("No requests for model generation of response component types");
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<EncapsulatingTypeNames> builder = ImmutableSet.builder();
        for (final Ds3Request request : requests) {
            builder.addAll(getResponseComponentTypesFromResponseCodes(request.getDs3ResponseCodes()));
        }
        return builder.build();
    }

    /**
     * Gets a list of response Component Types and original type names that are being converted
     * into new models that are within the Ds3ResponseCodes list.
     */
    protected static ImmutableSet<EncapsulatingTypeNames> getResponseComponentTypesFromResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            LOG.debug("No response codes for model generation of response component types");
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<EncapsulatingTypeNames> builder = ImmutableSet.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.addAll(getResponseTypesToUpdateFromResponseTypes(responseCode.getDs3ResponseTypes()));
        }
        return builder.build();
    }

    /**
     * Gets a list of response Component Types and original type names that are being converted
     * into new models that are within the Ds3ResponseTypes list.
     */
    protected static ImmutableSet<EncapsulatingTypeNames> getResponseTypesToUpdateFromResponseTypes(
            final ImmutableList<Ds3ResponseType> responseTypes) {
        if (isEmpty(responseTypes)) {
            LOG.debug("No response types for model generation of response component types");
            return ImmutableSet.of();
        }

        final ImmutableSet.Builder<EncapsulatingTypeNames> builder = ImmutableSet.builder();
        for (final Ds3ResponseType responseType : responseTypes) {
            if (hasContent(responseType.getComponentType())) {
                builder.add(new EncapsulatingTypeNames(
                        responseType.getComponentType(),
                        responseType.getOriginalTypeName()));
            }
        }
        return builder.build();
    }
}
