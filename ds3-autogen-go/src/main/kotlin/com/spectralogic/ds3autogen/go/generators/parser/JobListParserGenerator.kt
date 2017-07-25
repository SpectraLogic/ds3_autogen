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

import com.google.common.collect.ImmutableMap
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type
import com.spectralogic.ds3autogen.go.models.parser.ParseChildNodeAddToSlice
import com.spectralogic.ds3autogen.go.models.parser.ParseElement
import com.spectralogic.ds3autogen.go.utils.toGoType
import org.apache.commons.lang3.StringUtils

/**
 * The Go generator for JobList parser. This is special-cased because there is
 * no encapsulating data tag for the response payload represented by the type.
 * As a result, the annotations for an encapsulating tag produce incorrect code.
 */
class JobListParserGenerator : BaseTypeParserGenerator() {

    /**
     * Converts a Ds3Element into a ParsingElements. The Ds3Element with name "Job"
     * is special-cased to ignore the encapsulating tag "Job".
     */
    override fun toChildNode(ds3Element: Ds3Element, typeName: String, typeMap: ImmutableMap<String, Ds3Type>): ParseElement {
        println(ds3Element)
        if (ds3Element.name == "Jobs") {
            val xmlTag = getXmlTagName(ds3Element)
            val modelName = StringUtils.uncapitalize(typeName)
            val paramName = StringUtils.capitalize(ds3Element.name)
            val childType = toGoType(ds3Element.componentType!!)
            return ParseChildNodeAddToSlice(xmlTag, modelName, paramName, childType)
        }

        return toStandardChildNode(ds3Element, typeName, typeMap)
    }
}