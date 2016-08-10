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

package com.spectralogic.ds3autogen.testutil;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.*;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.enums.Requirement;
import com.spectralogic.ds3autogen.api.models.enums.Resource;
import com.spectralogic.ds3autogen.api.models.enums.ResourceType;

/**
 * This class provides utilities for generating partially filled in Ds3 models for use in testing.
 */
public final class Ds3ModelPartialDataFixture {

    private Ds3ModelPartialDataFixture() { }

    /**
     * Creates a Ds3Request that is empty except for the includedInPath, optional and required query params
     */
    public static Ds3Request createDs3RequestTestData(
            final boolean includeIdInPath,
            final ImmutableList<Ds3Param> optionalParams,
            final ImmutableList<Ds3Param> requiredParams) {
        return createDs3RequestTestData(includeIdInPath, null, optionalParams, requiredParams);
    }

    /**
     * Creates a Ds3Request that is empty except for the includedInPath, response codes, optional and
     * required query params
     */
    public static Ds3Request createDs3RequestTestData(
            final boolean includeIdInPath,
            final ImmutableList<Ds3ResponseCode> responseCodes,
            final ImmutableList<Ds3Param> optionalParams,
            final ImmutableList<Ds3Param> requiredParams) {
        return new Ds3Request(null, null, null, null, null, null, null, null, null,
                includeIdInPath,
                responseCodes,
                optionalParams,
                requiredParams);
    }

    /**
     * Creates a Ds3Request that is empty except for having the specified request name and classification
     */
    public static Ds3Request createDs3RequestTestData(final String requestName, final Classification classification) {
        return createDs3RequestTestData(requestName, classification, null, null);
    }

    /**
     * Creates a Ds3Request that is empty except for having the specified request name, classification,
     * and bucket and object requirement
     */
    public static Ds3Request createDs3RequestTestData(
            final String requestName,
            final Classification classification,
            final Requirement bucketRequirement,
            final Requirement objectRequirement) {
        return new Ds3Request(
                requestName,
                null,
                classification,
                bucketRequirement,
                objectRequirement,
                null, null, null, null, false, null, null, null);
    }

    /**
     * Creates a Ds3Request that is empty except for having the specified request name, classification,
     * resource, resource type, and whether the id should be included in the path
     */
    public static Ds3Request createDs3RequestTestData(
            final String requestName,
            final Classification classification,
            final Resource resource,
            final ResourceType resourceType,
            final boolean includeIdInPath
    ) {
        return new Ds3Request(
                requestName,
                null,
                classification,
                null, null, null,
                resource,
                resourceType,
                null,
                includeIdInPath,
                null, null, null);
    }

    /**
     * Creates an empty Ds3Type
     */
    public static Ds3Type createEmptyDs3Type() {
        return new Ds3Type(null, null, null, null);
    }

    /**
     * Creates an empty Ds3Element
     */
    public static Ds3Element createEmptyDs3Element() {
        return new Ds3Element(null, null, null, false);
    }

    /**
     * Creates a list of Ds3Elements of size 2, where each Ds3Element is empty
     */
    public static ImmutableList<Ds3Element> createEmptyDs3ElementList() {
        return ImmutableList.of(
                createEmptyDs3Element(),
                createEmptyDs3Element());
    }

    /**
     * Creates an empty Ds3EnumConstant
     */
    public static Ds3EnumConstant createEmptyDs3EnumConstant() {
        return new Ds3EnumConstant(null, null);
    }

    /**
     * Creates a list of Ds3EnumConstants of size 2, where each Ds3EnumConstant is empty
     */
    public static ImmutableList<Ds3EnumConstant> createEmptyDs3EnumConstantList() {
        return ImmutableList.of(
                createEmptyDs3EnumConstant(),
                createEmptyDs3EnumConstant());
    }

    /**
     * Creates a Ds3Type that is empty except for having a name
     */
    public static Ds3Type createDs3TypeTestData(final String typeName) {
        return createDs3TypeTestData(typeName, null);
    }

    /**
     * Creates a Ds3Type that has a name and elements
     */
    public static Ds3Type createDs3TypeTestData(final String typeName, final ImmutableList<Ds3Element> elements) {
        return new Ds3Type(typeName, null, elements, null);
    }

    /**
     * Creates a Ds3Element that is empty except for having a type
     */
    public static Ds3Element createDs3ElementTestData(
            final String elementType) {
        return createDs3ElementTestData(null, elementType);
    }

    /**
     * Creates a Ds3Element that is empty except for having a name and type
     */
    public static Ds3Element createDs3ElementTestData(
            final String elementName,
            final String elementType) {
        return createDs3ElementTestData(elementName, elementType, null);
    }

    /**
     * Creates a Ds3Element without a component type
     */
    public static Ds3Element createDs3ElementTestData(
            final String elementName,
            final String elementType,
            final String elementComponentType) {
        return new Ds3Element(elementName, elementType, elementComponentType, null, false);
    }

    /**
     * Creates a Ds3Element list which contains one Ds3Element with the specified name and type
     */
    public static ImmutableList<Ds3Element> createDs3ElementListTestData(final String elementName, final String elementType) {
        return ImmutableList.of(createDs3ElementTestData(elementName, elementType));
    }

    /**
     * Creates a Ds3Param that only has a type
     */
    public static Ds3Param createDs3ParamTestData(final String paramType) {
        return new Ds3Param(null, paramType, false);
    }
}
