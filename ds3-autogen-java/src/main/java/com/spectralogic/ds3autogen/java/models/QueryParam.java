/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.models;

import com.spectralogic.ds3autogen.api.models.Arguments;

import static com.spectralogic.ds3autogen.java.utils.WithConstructorUtil.putQueryParamLine;

public class QueryParam {

    private final Arguments param;

    public QueryParam(final Arguments param) {
        this.param = param;
    }

    public QueryParam(final String type, final String name) {
        this.param = new Arguments(type, name);
    }

    /**
     * Retrieves the java code for adding the query parameter to
     * the request handler
     */
    public String toPutJavaCode() {
        return putQueryParamLine(param);
    }

    public String getName() {
        return param.getName();
    }

    public String getType() {
        return param.getType();
    }
}
