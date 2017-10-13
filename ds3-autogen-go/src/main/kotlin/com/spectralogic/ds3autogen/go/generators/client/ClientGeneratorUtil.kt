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

package com.spectralogic.ds3autogen.go.generators.client

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.api.models.enums.Classification
import com.spectralogic.ds3autogen.api.models.enums.Requirement
import com.spectralogic.ds3autogen.go.utils.getGoArgFromResource
import com.spectralogic.ds3autogen.go.utils.isGoResourceAnArg
import com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil
import com.spectralogic.ds3autogen.utils.Ds3RequestUtils
import com.spectralogic.ds3autogen.utils.RequestConverterUtil
import com.spectralogic.ds3autogen.utils.models.NotificationType

private val PATH_REQUEST_REFERENCE = "request"

/**
 * Creates the Go request path code for a Ds3 request
 */
fun toRequestPath(ds3Request: Ds3Request): String {
    if (ds3Request.classification == Classification.amazons3) {
        return getAmazonS3RequestPath(ds3Request)
    }
    if (ds3Request.classification == Classification.spectrads3) {
        return getSpectraDs3RequestPath(ds3Request)
    }

    throw IllegalArgumentException("Unsupported classification: " + ds3Request.classification.toString())
}

/**
 * Creates the Go request path code for an AmazonS3 request
 */
fun getAmazonS3RequestPath(ds3Request: Ds3Request): String {
    val builder = StringBuilder()

    if (ds3Request.classification != Classification.amazons3) {
        return builder.toString()
    }
    builder.append("\"/\"")
    if (ds3Request.bucketRequirement == Requirement.REQUIRED) {
        builder.append(" + ").append(PATH_REQUEST_REFERENCE).append(".BucketName")
    }
    if (ds3Request.objectRequirement == Requirement.REQUIRED) {
        builder.append(" + \"/\" + ").append(PATH_REQUEST_REFERENCE).append(".ObjectName")
    }
    return builder.toString()
}

/**
 * Creates the Go request path code for a SpectraS3 request
 */
fun getSpectraDs3RequestPath(ds3Request: Ds3Request): String {
    val builder = StringBuilder()

    if (ds3Request.classification != Classification.spectrads3) {
        return builder.toString()
    }
    if (ds3Request.resource == null) {
        return builder.append("\"/_rest_/\"").toString()
    }

    builder.append("\"/_rest_/").append(ds3Request.resource!!.toString().toLowerCase())
    if (Ds3RequestClassificationUtil.isNotificationRequest(ds3Request)
            && ds3Request.includeInPath
            && (RequestConverterUtil.getNotificationType(ds3Request) == NotificationType.DELETE
                || RequestConverterUtil.getNotificationType(ds3Request) == NotificationType.GET)) {
        builder.append("/\"").append(" + ").append(PATH_REQUEST_REFERENCE).append(".NotificationId")
    } else if (Ds3RequestUtils.hasBucketNameInPath(ds3Request)) {
        builder.append("/\"").append(" + ").append(PATH_REQUEST_REFERENCE).append(".BucketName")
    } else if (ds3Request.isGoResourceAnArg()) {
        val resourceArg = ds3Request.getGoArgFromResource()
        builder.append("/\"").append(" + ").append(PATH_REQUEST_REFERENCE)
                .append(".").append(resourceArg.name.capitalize())
    } else {
        builder.append("\"")
    }
    return builder.toString()
}
