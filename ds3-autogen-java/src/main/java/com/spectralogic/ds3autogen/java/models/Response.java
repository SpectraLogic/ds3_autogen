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

package com.spectralogic.ds3autogen.java.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;

public class Response {
    private final String packageName;
    private final String name;
    private final ImmutableList<Ds3ResponseCode> responseCodes;
    private final ImmutableList<String> imports;

    public Response(
            final String packageName,
            final String name,
            final ImmutableList<Ds3ResponseCode> responseCodes,
            final ImmutableList<String> imports) {
        this.packageName = packageName;
        this.name = name;
        this.responseCodes = responseCodes;
        this.imports = imports;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Ds3ResponseCode> getResponseCodes() {
        return responseCodes;
    }

    public ImmutableList<String> getImports() {
        return imports;
    }
}
