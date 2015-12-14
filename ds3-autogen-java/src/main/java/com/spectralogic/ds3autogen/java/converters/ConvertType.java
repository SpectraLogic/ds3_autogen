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

package com.spectralogic.ds3autogen.java.converters;

import static com.spectralogic.ds3autogen.java.models.Constants.*;

public final class ConvertType {

    private ConvertType() {
        //pass
    }

    public static String convertType(final String type) {
        if (type.startsWith(CONTRACT_PACKAGE_PATH)) {
            final String[] packageParts = type.split("\\.");
            return MODELS_PACKAGE_PATH + packageParts[packageParts.length - 1];
        }
        return type;
    }
}
