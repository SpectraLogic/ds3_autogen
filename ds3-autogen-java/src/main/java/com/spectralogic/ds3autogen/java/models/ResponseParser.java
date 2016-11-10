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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class ResponseParser {

    private final String name;
    private final String responseName;
    private final String packageName;
    private final String parentClass;

    /** contains a comma-separated list of all expected status codes */
    private final String expectedStatusCodes;

    private final boolean hasPaginationHeaders;
    private final ImmutableSet<String> imports;
    private final ImmutableList<ResponseCode> responseCodes;

    public ResponseParser(
            final String name,
            final String responseName,
            final String packageName,
            final String parentClass,
            final String expectedStatusCodes,
            final boolean hasPaginationHeaders,
            final ImmutableSet<String> imports,
            final ImmutableList<ResponseCode> responseCodes) {
        this.name = name;
        this.responseName = responseName;
        this.packageName = packageName;
        this.parentClass = parentClass;
        this.expectedStatusCodes = expectedStatusCodes;
        this.hasPaginationHeaders = hasPaginationHeaders;
        this.imports = imports;
        this.responseCodes = responseCodes;
    }

    public String getName() {
        return name;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getParentClass() {
        return parentClass;
    }

    public ImmutableSet<String> getImports() {
        return imports;
    }

    public String getResponseName() {
        return responseName;
    }

    public ImmutableList<ResponseCode> getResponseCodes() {
        return responseCodes;
    }

    public String getExpectedStatusCodes() {
        return expectedStatusCodes;
    }

    public boolean isHasPaginationHeaders() {
        return hasPaginationHeaders;
    }
}
