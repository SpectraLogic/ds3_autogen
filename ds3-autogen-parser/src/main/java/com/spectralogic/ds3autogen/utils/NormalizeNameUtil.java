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

import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.models.enums.Classification;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Utils for normalizing contract names to Autogen standards
 */
public final class NormalizeNameUtil {

    /**
     * Normalizes a request name while preserving the original path.
     *   Renames postfix from RequestHandler to Request
     *   Namespaces spectra requests with SpectraS3
     *   Performs name mapping from contract to sdk names
     */
    public static String normalizeRequestName(
            final String requestName,
            final Classification classification,
            final NameMapper nameMapper) {
        final String sdkName = toSdkName(requestName, classification, nameMapper);
        if (classification == Classification.spectrads3) {
            return sdkName.replace("RequestHandler", "SpectraS3Request");
        }
        return sdkName.replace("RequestHandler", "Request");
    }

    /**
     * Converts a contract name into an sdk name while maintaining the original path.
     * This assumes that one is renaming a type vs a request.
     */
    public static String toSdkName(
            final String contractName,
            final NameMapper nameMapper) {
        return toSdkName(contractName, null, nameMapper);
    }

    /**
     * Converts a contract name into an sdk name while maintaining the original path. If the
     * contract name contained a '$', then everything proceeding the '$' is maintained, but
     * the name following the symbol is converted to the sdk name, if a name mapping exists.
     */
    public static String toSdkName(
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
    public static String getPathFromName(final String contractName) {
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
    public static String getNameFromPath(final String contractName) {
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
