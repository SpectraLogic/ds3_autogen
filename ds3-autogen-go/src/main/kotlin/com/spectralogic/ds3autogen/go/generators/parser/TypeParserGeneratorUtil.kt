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

package com.spectralogic.ds3autogen.go.generators.parser

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type
import com.spectralogic.ds3autogen.go.models.parser.ParseElement

/**
 * Interface for utility functions used to generate Go model parsers. This
 * defines the methods that are overridden between the {@link BaseResponseParserGenerator}
 * and other implementations of response parsers during special casing.
 */
interface TypeParserGeneratorUtil {

    /**
     * Converts all non-attribute elements within a Ds3Element list into ParsingElements, which
     * contain the Go code for parsing the Ds3Elements as child nodes.
     */
    fun toChildNodeList(
            ds3Elements: ImmutableList<Ds3Element>?,
            typeName: String,
            typeMap: ImmutableMap<String, Ds3Type>): ImmutableList<ParseElement>

    /**
     * Converts a Ds3Element into a ParsingElements. This is overriden to special-case
     * individual Ds3Element parsing within a Ds3Type. Non-special-cased Ds3Elements
     * are passed onto {@link BaseTypeParserGenerator#toStandardChildNode}
     */
    fun toChildNode(ds3Element: Ds3Element, typeName: String, typeMap: ImmutableMap<String, Ds3Type>): ParseElement
}