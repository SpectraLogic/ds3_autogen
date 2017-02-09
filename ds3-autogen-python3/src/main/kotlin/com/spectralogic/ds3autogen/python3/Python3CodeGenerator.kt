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

package com.spectralogic.ds3autogen.python3

import com.spectralogic.ds3autogen.python.PythonCodeGenerator
import com.spectralogic.ds3autogen.python.generators.request.BaseRequestGenerator
import com.spectralogic.ds3autogen.python3.generators.request.P3PutObjectRequestGenerator
import freemarker.template.Configuration
import freemarker.template.Template

class Python3CodeGenerator() : PythonCodeGenerator() {

    /**
     * Retrieves the base command template used to generate the Python 3 ds3.py
     */
    override fun getCommandTemplate(config: Configuration): Template {
        return config.getTemplate("python3/commands/p3_all_commands.ftl")
    }

    /**
     * Retrieves the Python 3 request generator for the Amazon PutObject command
     */
    override fun getPutObjectRequestGenerator() : BaseRequestGenerator {
        return P3PutObjectRequestGenerator()
    }
}
