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

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

/**
 * Set of static utility functions for normalizing contract names
 * into SDK names for use in models
 */
public final class NormalizingContractNamesUtil {

    /**
     * Converts a Ds3Request's name into a Response name by removing the path and converting
     * "Request" into "Response"
     */
    public static String toResponseName(final String requestName) {
        return removePath(requestName).replace("Request", "Response");
    }

    /**
     * Removes the path from a string. This is used to remove the contract path
     * path from request and type names
     * @param str A string
     * @return The subset of str that is located after the last period '.' within str
     */
    public static String removePath(final String str) {
        if (isEmpty(str)) {
            return "";
        }
        final String[] classParts = str.split("\\.");
        return classParts[classParts.length - 1];
    }

    /**
     * Converts a Ds3Request's name into a Response Parser name by removing the
     * path and converting "Request" into "ResponseParser"
     */
    public static String toResponseParserName(final String requestName) {
        return removePath(requestName).replace("Request", "ResponseParser");
    }
}
