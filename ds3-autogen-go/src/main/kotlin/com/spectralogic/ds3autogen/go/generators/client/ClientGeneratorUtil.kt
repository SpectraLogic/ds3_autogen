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

private const val PATH_REQUEST_REFERENCE = "request"

/**
 * Creates the Go request path code for a Ds3 request
 */
fun Ds3Request.toRequestPath(): String {
    if (classification == Classification.amazons3) {
        return getAmazonS3RequestPath()
    }
    if (classification == Classification.spectrads3) {
        return getSpectraDs3RequestPath()
    }

    throw IllegalArgumentException("Unsupported classification: " + classification.toString())
}

/**
 * Creates the Go request path code for an AmazonS3 request
 */
fun Ds3Request.getAmazonS3RequestPath(): String {
    if (classification != Classification.amazons3) {
        return ""
    }
    val builder = StringBuilder("\"/\"")
    if (bucketRequirement == Requirement.REQUIRED) {
        builder.append(" + ").append(PATH_REQUEST_REFERENCE).append(".BucketName")
    }
    if (objectRequirement == Requirement.REQUIRED) {
        builder.append(" + \"/\" + ").append(PATH_REQUEST_REFERENCE).append(".ObjectName")
    }
    return builder.toString()
}

/**
 * Creates the Go request path code for a SpectraS3 request
 */
fun Ds3Request.getSpectraDs3RequestPath(): String {
    if (classification != Classification.spectrads3) {
        return ""
    }
    if (resource == null) {
        return "\"/_rest_/\""
    }

    val builder = StringBuilder("\"/_rest_/")
            .append(resource!!.toString().toLowerCase())

    if (Ds3RequestClassificationUtil.isNotificationRequest(this)
            && includeInPath
            && (RequestConverterUtil.getNotificationType(this) == NotificationType.DELETE
                || RequestConverterUtil.getNotificationType(this) == NotificationType.GET)) {
        builder.append("/\"").append(" + ").append(PATH_REQUEST_REFERENCE).append(".NotificationId")
    } else if (Ds3RequestUtils.hasBucketNameInPath(this)) {
        builder.append("/\"").append(" + ").append(PATH_REQUEST_REFERENCE).append(".BucketName")
    } else if (this.isGoResourceAnArg()) {
        val resourceArg = this.getGoArgFromResource()
        builder.append("/\"").append(" + ").append(PATH_REQUEST_REFERENCE)
                .append(".").append(resourceArg.name.capitalize())
    } else {
        builder.append("\"")
    }
    return builder.toString()
}

/**
 * Creates the Go code for transforming a variable to string based on its Go type.
 * Requires that the specified Go type is a non-pointer variable or an interface.
 * This is used to convert required parameters into query parameters within the
 * Go client code.
 */
fun goQueryParamToString(name: String, goType: String): String {
    return when (goType) {
        "*int", "*int64", "*bool", "*float64", "*string" ->
            throw IllegalArgumentException("Expected Go variable to be a non-pointer or an interface, but was '$goType'")
        "" -> "\"\"" // Denotes void parameter in contract
        "bool" -> "strconv.FormatBool(request.$name)"
        "int" -> "strconv.Itoa(request.$name)"
        "int64" -> "strconv.FormatInt(request.$name, 10)"
        "float64" -> "strconv.FormatFloat(request.$name, 'f', -1, 64)"
        "string" -> "request.$name"
        else -> "request.$name.String()"
    }
}

/**
 * Creates the Go code for transforming a variable pointer to a string pointer based on its Go type.
 * Go code does not assume that it is safe to access pointer values. This is used to convert
 * optional request parameters into query parameters within the Go client code.
 */
fun goPtrQueryParamToStringPtr(name: String, goType: String): String {
    return when (goType) {
        "", "int", "bool", "int64", "float64", "string" ->
            throw IllegalArgumentException("Expected Go variable to be a pointer or an interface, but was '$goType'")
        "*int" -> "networking.IntPtrToStrPtr(request.$name)"
        "*int64" -> "networking.Int64PtrToStrPtr(request.$name)"
        "*bool" -> "networking.BoolPtrToStrPtr(request.$name)"
        "*float64" -> "networking.Float64PtrToStrPtr(request.$name)"
        "*string" -> "request.$name"
        else -> "networking.InterfaceToStrPtr(request.$name)"
    }
}