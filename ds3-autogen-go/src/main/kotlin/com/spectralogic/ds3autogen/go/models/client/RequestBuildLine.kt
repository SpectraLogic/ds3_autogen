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
    override val line: String get() {
        return "WithPath($path)."
    }
}
