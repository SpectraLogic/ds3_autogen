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

package com.spectralogic.ds3autogen.go.models.client

import com.spectralogic.ds3autogen.api.models.enums.HttpVerb
import com.spectralogic.ds3autogen.api.models.enums.Operation

interface RequestBuildLine {
    val line: String
}

// Creates the Go request builder line for adding an http verb to an http request
data class HttpVerbBuildLine(private val httpVerb: HttpVerb) : RequestBuildLine {
    override val line: String get() {
        val verb = httpVerb.toString()
        return "WithHttpVerb(HTTP_VERB_$verb)."
    }
}

// Creates the Go request builder line for adding a path to an http request
data class PathBuildLine(private val path: String) : RequestBuildLine {
    override val line = "WithPath($path)."
}

// Creates the Go request builder line for adding the operation query param
data class OperationBuildLine(private val operation: Operation) : RequestBuildLine {
    override val line: String get() {
        val op = operation.toString().toLowerCase()
        return "WithQueryParam(\"operation\", \"$op\")."
    }
}

// Creates the Go request builder line for adding a required query param
data class QueryParamBuildLine(private val key: String, private val value: String) : RequestBuildLine {
    override val line = "WithQueryParam(\"$key\", $value)."
}

// Creates the Go request builder line for adding an optional query param.
// An optional parameter may be nil to indicate it has not been set.
data class OptionalQueryParamBuildLine(private val key: String, private val value: String) : RequestBuildLine {
    override val line = "WithOptionalQueryParam(\"$key\", $value)."
}

// Creates the Go request builder line for adding an optional void query param.
// Optional voids are represented by booleans within the Go request handler
data class VoidOptionalQueryParamBuildLine(private val key: String, private val paramName: String) : RequestBuildLine {
    override val line = "WithOptionalVoidQueryParam(\"$key\", request.$paramName)."

}

// Creates a custom request builder line.
data class CustomBuildLine(private val customLine: String) : RequestBuildLine {

    companion object {
        @JvmStatic
        val CHECKSUM_BUILD_LINE = CustomBuildLine("WithChecksum(request.Checksum).")
        @JvmStatic
        val METADATA_BUILD_LINE = CustomBuildLine("WithHeaders(request.Metadata).")
    }

    override val line = customLine
}

// Creates the GO request builder line for adding a reader that contains the request payload.
data class ReaderBuildLine(private val reader: String) : RequestBuildLine {
    override val line = "WithReader($reader)."
}

// Creates the Go request builder line for adding a readCloser that contains the request payload.
data class ReadCloserBuildLine(private val readCloser: String) : RequestBuildLine {
    override val line = "WithReadCloser($readCloser)."
}