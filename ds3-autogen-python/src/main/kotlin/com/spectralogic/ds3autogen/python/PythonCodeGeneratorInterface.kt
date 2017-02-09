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

package com.spectralogic.ds3autogen.python

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec
import com.spectralogic.ds3autogen.python.generators.request.BaseRequestGenerator
import com.spectralogic.ds3autogen.python.generators.request.RequestModelGenerator
import com.spectralogic.ds3autogen.python.model.request.BaseRequest
import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateException
import java.io.IOException

interface PythonCodeGeneratorInterface {

    @Throws (IOException::class, TemplateException::class)
    fun generateCommands(ds3Requests: ImmutableList<Ds3Request>,
                         typeMap: ImmutableMap<String, Ds3Type>,
                         docSpec: Ds3DocSpec)

    @Throws (IOException::class)
    fun getCommandTemplate(config: Configuration) : Template

    fun getPutObjectRequestGenerator() : BaseRequestGenerator

    fun getRequestGenerator(ds3Request: Ds3Request) : BaseRequestGenerator

    fun toRequestModel(ds3Request: Ds3Request,
                       docSpec: Ds3DocSpec) : BaseRequest

    fun toRequestModelList(ds3Requests: ImmutableList<Ds3Request>,
                           docSpec: Ds3DocSpec) : ImmutableList<BaseRequest>
}