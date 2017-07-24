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

/**
 * Contains utils for formatting generated Go code
 */

/** The standard indentation used in the Go SDK */
private val GO_INDENT = "    "

/**
 * Creates the specified indentation in accordance with the Go SDK formatting
 */
fun goIndent(indent: Int): String {
    var curIndent = ""
    repeat(indent) {
        curIndent += GO_INDENT
    }
    return curIndent
}