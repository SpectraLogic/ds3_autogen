/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.utils;

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.Classification;

import static com.spectralogic.ds3autogen.java.models.Constants.*;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isNotificationRequest;

/**
 * Utility functions that are use in multiple places throughout the java module.
 * This should only contain utilities that are used between multiple packages
 * within the java module with no clear placement within those packages.
 *
 * Purpose: reduce coupling
 */
public final class JavaModuleUtil {

    private JavaModuleUtil() {
        // Pass
    }

    /**
     * Gets the command package suitable for the given Ds3Request. SpectraDs3 commands
     * have a separate package, as do notifications.
     * @param ds3Request A Ds3Request
     * @return The command package that is suitable for the given Ds3Request
     */
    public static String getCommandPackage(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(COMMANDS_PACKAGE_PATH);
        if (ds3Request.getClassification() == Classification.spectrads3) {
            builder.append(SPECTRA_DS3_PACKAGE);
        }
        if (ds3Request.getClassification() == Classification.spectrainternal) {
            builder.append(SPECTRA_INTERNAL_PACKAGE);
        }
        if (isNotificationRequest(ds3Request)) {
            builder.append(NOTIFICATION_PACKAGE);
        }
        return builder.toString();
    }
}
