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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

/**
 * Contains utilities for classifying Ds3Types that are special cased
 * within the language modules.
 */
public final class Ds3TypeClassificationUtil {

    public static final String COMMON_PREFIX_ELEMENT = "CommonPrefixes";

    /**
     * Determines if a Ds3Type is the HttpErrorResultApiBean Type
     */
    public static boolean isHttpErrorType(final Ds3Type ds3Type) {
        if (isEmpty(ds3Type.getNameToMarshal()) || isEmpty(ds3Type.getElements())) {
            return false;
        }
        final ImmutableList<String> elementNames = getElementNames(ds3Type);
        return ds3Type.getNameToMarshal().equalsIgnoreCase("Error")
                && elementNames.contains("Code")
                && elementNames.contains("HttpErrorCode")
                && elementNames.contains("Message")
                && elementNames.contains("Resource")
                && elementNames.contains("ResourceId");
    }

    /**
     * Determines if a Ds3Type contains the CommonPrefixes element
     */
    public static boolean isCommonPrefixesType(final Ds3Type ds3Type) {
        return containsElement(ds3Type, COMMON_PREFIX_ELEMENT);
    }

    /**
     * Determines if a Ds3Type contains the specified element
     */
    protected static boolean containsElement(final Ds3Type ds3Type, final String elementName) {
        final ImmutableList<String> elements = getElementNames(ds3Type);
        return elements.contains(elementName);
    }

    /**
     * Gets the list of Ds3Element names contained within the Ds3Type, or an
     * empty list if no elements exist.
     */
    protected static ImmutableList<String> getElementNames(final Ds3Type type) {
        if (isEmpty(type.getElements())) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final Ds3Element element : type.getElements()) {
            builder.add(element.getName());
        }
        return builder.build();
    }

    /**
     * Determines if a given Ds3Type is the JobsApiBean type
     * which is renamed to JobList in the NameMapper
     */
    public static boolean isJobsApiBean(final Ds3Type ds3Type) {
        if (ds3Type == null) {
            return false;
        }
        return removePath(ds3Type.getName()).equals("JobList");
    }

    /**
     * Determines if a given Ds3Type is the Checksum Type
     * @param ds3Type A Ds3Type
     * @return True if the Ds3Type describes the ChecksumType, else false
     */
    public static boolean isChecksumType(final Ds3Type ds3Type) {
        return removePath(ds3Type.getName()).equals("ChecksumType");
    }

    /**
     * Determines if a given Ds3Type is the Objects Type
     */
    public static boolean isObjectsType(final Ds3Type ds3Type) {
        return removePath(ds3Type.getName()).equals("Objects");
    }

    /**
     * Determines if a contract type is an enum defined within the contract
     */
    public static boolean isEnumType(
            final String typeName,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeName) || isEmpty(typeMap)) {
            return false;
        }
        final Ds3Type type = typeMap.get(typeName);
        return !(type == null || isEmpty(type.getEnumConstants()));
    }
}
