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

import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

/**
 * Set of static utility functions for generating the Client
 */
public final class ClientGeneratorUtil {

    /**
     * Converts a Ds3Request's name into a Command name by removing the path and "Request"
     */
    public static String toCommandName(final String requestName) {
        return removePath(requestName).replace("Request", "");
    }
}
