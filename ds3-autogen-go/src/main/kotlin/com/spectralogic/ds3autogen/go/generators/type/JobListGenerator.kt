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

package com.spectralogic.ds3autogen.go.generators.type

import com.google.common.collect.ImmutableList
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element
import com.spectralogic.ds3autogen.go.models.type.StructElement
import com.spectralogic.ds3autogen.go.utils.toGoType
import com.spectralogic.ds3autogen.utils.ConverterUtil
import com.spectralogic.ds3autogen.utils.Ds3ElementUtil

/**
 * Generates the Go JobList type (called JobsApiBean within contract)
 */
class JobListGenerator : BaseTypeGenerator() {

    /**
     * Creates the list of elements that make up the struct. JobList should only have one
     * element, which should not have an encapsulating tag despite ds3 annotation.
     */
    override fun toStructElementsList(ds3Elements: ImmutableList<Ds3Element>?): ImmutableList<StructElement> {
        if (ConverterUtil.isEmpty(ds3Elements) || ds3Elements!!.size != 1) {
            throw IllegalArgumentException("JobsApiBean should only contain one ds3Elements")
        }
        val element = ds3Elements[0]
        val xmlTag = Ds3ElementUtil.getXmlTagName(element).capitalize()
        val jobList = StructElement(element.name,
                toGoType(element.type, element.componentType, element.nullable),
                "xml:\"$xmlTag\"")

        return ImmutableList.of(jobList)
    }
}