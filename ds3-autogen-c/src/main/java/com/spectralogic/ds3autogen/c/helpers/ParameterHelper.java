/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.c.helpers;

import com.spectralogic.ds3autogen.c.models.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParameterHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ParameterHelper.class);
    private final static ParameterHelper parameterHelper = new ParameterHelper();

    public static ParameterHelper getInstance() {
        return parameterHelper;
    }

    public static String getParameterTypeConversionSpecifier(final Parameter parm) {
        switch (parm.getParameterType()) {
            case "ds3_bool":
                return "VOID";

            case "int":
                return "%d";

            case "uint64_t":
                return "%llu";

            case "float":
            case "double":
                return "%e";

            default:
                return null;
        }
    }
}
