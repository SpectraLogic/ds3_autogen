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

package com.spectralogic.ds3autogen.go.utils

import com.spectralogic.ds3autogen.api.models.Arguments
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.utils.Helper
import com.spectralogic.ds3autogen.utils.RequestConverterUtil

/**
 * Determines if a Ds3Request's Resource describes a required argument or not.
 */
fun Ds3Request.isGoResourceAnArg(): Boolean {
    return resource != null && includeInPath
}

/**
 * Creates an argument from a resource. Notification resource args are simplified to notificationId.
 * If the resource does not describe a valid argument, such as a singleton, an error is thrown.
 */
fun Ds3Request.getGoArgFromResource(): Arguments {
    if (RequestConverterUtil.isResourceSingleton(resource)) {
        throw IllegalArgumentException("Cannot create an argument from a singleton resource: " + resource.toString())
    }
    if (RequestConverterUtil.isResourceNotification(resource)) {
        return Arguments("String", "NotificationId")
    }
    if (RequestConverterUtil.isResourceNamed(resource)) {
        return Arguments("String", Helper.underscoreToCamel(resource.toString()) + "Name")
    }
    if (RequestConverterUtil.isResourceId(resource)) {
        return Arguments("String", Helper.underscoreToCamel(resource.toString()) + "Id")
    }
    return Arguments("String", Helper.underscoreToCamel(resource.toString()))
}