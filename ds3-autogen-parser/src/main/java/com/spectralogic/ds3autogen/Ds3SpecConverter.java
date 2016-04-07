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

package com.spectralogic.ds3autogen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Converts all contract type names into SDK names as defined within
 * a NameMapper, and determines if a parameter type is nullable
 */
class Ds3SpecConverter {

    /**
     * Converts the contract names of all response codes, optional
     * params and required params to the SDK naming scheme, as defined
     * within the NameMapper
     */
    protected static ImmutableList<Ds3Request> convertRequests(
            final ImmutableList<Ds3Request> requests,
            final NameMapper nameMapper) {
        if (isEmpty(requests)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Ds3Request> builder = ImmutableList.builder();

        for (final Ds3Request request : requests) {
            final Ds3Request convertedRequest = new Ds3Request(
                    convertName(request.getName(), request.getClassification(), nameMapper),
                    request.getHttpVerb(),
                    request.getClassification(),
                    request.getBucketRequirement(),
                    request.getObjectRequirement(),
                    request.getAction(),
                    request.getResource(),
                    request.getResourceType(),
                    request.getOperation(),
                    request.includeIdInPath(),
                    convertAllResponseCodes(request.getDs3ResponseCodes(), nameMapper),
                    convertAllParams(request.getOptionalQueryParams(), nameMapper),
                    convertAllParams(request.getRequiredQueryParams(), nameMapper));
            builder.add(convertedRequest);
        }

        return builder.build();
    }

    /**
     * Converts the contract names of all Params' types to the
     * SDK naming scheme, as defined within the NameMapper
     */
    protected static ImmutableList<Ds3Param> convertAllParams(
            final ImmutableList<Ds3Param> params,
            final NameMapper nameMapper) {
        if (isEmpty(params)) {
            return params;
        }
        final ImmutableList.Builder<Ds3Param> builder = ImmutableList.builder();
        for (final Ds3Param param : params) {
            final Ds3Param convertedParam = new Ds3Param(
                    param.getName(),
                    convertName(param.getType(), nameMapper),
                    param.isNullable());
            builder.add(convertedParam);
        }
        return builder.build();
    }

    /**
     * Converts the contract names of all response code types and component
     * types to the SDK naming scheme, as defined within the NameMapper
     */
    protected static ImmutableList<Ds3ResponseCode> convertAllResponseCodes(
            final ImmutableList<Ds3ResponseCode> responseCodes,
            final NameMapper nameMapper) {
        if (isEmpty(responseCodes)) {
            return responseCodes;
        }
        final ImmutableList.Builder<Ds3ResponseCode> builder = ImmutableList.builder();
        for (final Ds3ResponseCode responseCode : responseCodes) {
            builder.add(convertResponseCode(responseCode, nameMapper));
        }
        return builder.build();
    }

    /**
     * Converts the contract names of the response code's type and component
     * type to the SDK naming scheme, as defined within the NameMapper
     */
    protected static Ds3ResponseCode convertResponseCode(
            final Ds3ResponseCode responseCode,
            final NameMapper nameMapper) {
        if (isEmpty(responseCode.getDs3ResponseTypes())) {
            return responseCode;
        }
        final ImmutableList.Builder<Ds3ResponseType> builder = ImmutableList.builder();
        for (final Ds3ResponseType responseType : responseCode.getDs3ResponseTypes()) {
            builder.add(new Ds3ResponseType(
                    convertName(responseType.getType(), nameMapper),
                    convertName(responseType.getComponentType(), nameMapper),
                    getOriginalType(responseType.getComponentType(), nameMapper)));
        }
        return new Ds3ResponseCode(responseCode.getCode(), builder.build());
    }

    /**
     * Gets the contract name of a type if said type is renamed within the NameMapper.
     * If the type name does not change, then null is returned.
     */
    protected static String getOriginalType(final String type, final NameMapper nameMapper) {
        if (isEmpty(type)) {
            return null;
        }
        final String converted = convertName(type, nameMapper);
        if (converted.equals(type)) {
            return null;
        }
        return stripPathAndDollarSign(type);
    }

    /**
     * This removes the path and anything proceeding a dollar sign. This is
     * used to retrieve the original type name of a response type.
     */
    protected static String stripPathAndDollarSign(final String type) {
        final String typeMinusPath = getNameFromPath(type);
        final String[] parts = typeMinusPath.split("\\$");
        switch (parts.length) {
            case 0:
            case 1:
                return typeMinusPath;
            case 2:
                return parts[1];
            default:
                throw new IllegalArgumentException("A type name cannot have two dollar signs within it: " + type);
        }
    }

    /**
     * Converts the contract names of all type names, elements and enum constants
     * to the SDK naming scheme, as defined within the NameMapper
     */
    protected static ImmutableMap<String, Ds3Type> convertTypes(
            final ImmutableMap<String, Ds3Type> types,
            final NameMapper nameMapper) {
        if (isEmpty(types)) {
            return types;
        }
        final ImmutableMap.Builder<String, Ds3Type> builder = ImmutableMap.builder();

        for (final ImmutableMap.Entry<String, Ds3Type> entry : types.entrySet()) {
            final Ds3Type ds3Type = new Ds3Type(
                    convertName(entry.getValue().getName(), nameMapper),
                    entry.getValue().getNameToMarshal(),
                    convertAllElements(entry.getValue().getElements(), nameMapper),
                    convertAllEnumConstants(entry.getValue().getEnumConstants(), nameMapper));
            builder.put(convertName(entry.getKey(), nameMapper), ds3Type);
        }

        return builder.build();
    }

    /**
     * Converts the contract names of all properties within he enum constants
     * to the SDK naming scheme, as defined within the NameMapper
     */
    protected static ImmutableList<Ds3EnumConstant> convertAllEnumConstants(
            final ImmutableList<Ds3EnumConstant> enumConstants,
            final NameMapper nameMapper) {
        if (isEmpty(enumConstants)) {
            return enumConstants;
        }
        final ImmutableList.Builder<Ds3EnumConstant> builder = ImmutableList.builder();
        for (final Ds3EnumConstant enumConstant : enumConstants) {
            final Ds3EnumConstant convertedEnumConstant = new Ds3EnumConstant(
                    enumConstant.getName(),
                    convertAllProperties(enumConstant.getDs3Properties(), nameMapper));
            builder.add(convertedEnumConstant);
        }
        return builder.build();
    }

    /**
     * Converts the contract names of all property types to the SDK naming
     * scheme, as defined within the NameMapper
     */
    protected static ImmutableList<Ds3Property> convertAllProperties(
            final ImmutableList<Ds3Property> properties,
            final NameMapper nameMapper) {
        if (isEmpty(properties)) {
            return properties;
        }
        final ImmutableList.Builder<Ds3Property> builder = ImmutableList.builder();
        for (final Ds3Property property : properties) {
            final Ds3Property convertedProperty = new Ds3Property(
                    property.getName(),
                    property.getValue(),
                    convertName(property.getValueType(), nameMapper));
            builder.add(convertedProperty);
        }
        return builder.build();
    }

    /**
     * Converts the contract names of all element types, component types, and
     * annotations to the SDK naming scheme, as defined within the NameMapper
     */
    protected static ImmutableList<Ds3Element> convertAllElements(
            final ImmutableList<Ds3Element> elements,
            final NameMapper nameMapper) {
        if (isEmpty(elements)) {
            return elements;
        }
        final ImmutableList.Builder<Ds3Element> builder = ImmutableList.builder();
        for (final Ds3Element element : elements) {
            final Ds3Element convertedElement = new Ds3Element(
                    element.getName(),
                    convertName(element.getType(), nameMapper),
                    convertName(element.getComponentType(), nameMapper),
                    element.getDs3Annotations(),
                    element.isNullable());
            builder.add(convertedElement);
        }
        return builder.build();
    }

    /**
     * Converts a contract name into an sdk name while maintaining the original path.
     * This assumes that one is renaming a type vs a request.
     */
    protected static String convertName(
            final String contractName,
            final NameMapper nameMapper) {
        return convertName(contractName, null, nameMapper);
    }

    /**
     * Converts a contract name into an sdk name while maintaining the original path. If the
     * contract name contained a '$', then everything proceeding the '$' is maintained, but
     * the name following the symbol is converted to the sdk name, if a name mapping exists.
     */
    protected static String convertName(
            final String contractName,
            final Classification classification,
            final NameMapper nameMapper) {
        if (isEmpty(contractName)) {
            return contractName;
        }
        final String path = getPathFromName(contractName);
        final String curName = getNameFromPath(contractName);

        final String[] parts = curName.split("\\$");
        if (parts.length < 2) {
            return path + nameMapper.getConvertedName(curName, classification);
        }
        return path + parts[0] + "$" + nameMapper.getConvertedName(parts[1], classification);
    }

    /**
     * Gets the path associated with a full contract name. If there is
     * no path, then the original name is returned.
     */
    protected static String getPathFromName(final String contractName) {
        if (isEmpty(contractName)) {
            return "";
        }
        if (contractName.contains(".")) {
            return contractName.substring(0, contractName.lastIndexOf('.') + 1);
        }
        return "";
    }

    /**
     * Gets the name at the end of a path. If there is no path, then the
     * original name is returned. If the provided contract name is a path
     * (i.e. it ends with a period '.', then an error is thrown.
     */
    protected static String getNameFromPath(final String contractName) {
        if (isEmpty(contractName)) {
            return "";
        }
        if (contractName.endsWith(".")) {
            throw new IllegalArgumentException("A Type and Request name cannot end with '.': " + contractName);
        }
        if (contractName.contains(".")) {
            return contractName.substring(contractName.lastIndexOf('.') + 1);
        }
        return contractName;
    }
}
