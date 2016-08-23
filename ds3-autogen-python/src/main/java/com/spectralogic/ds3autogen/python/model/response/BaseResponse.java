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

package com.spectralogic.ds3autogen.python.model.response;

import com.google.common.collect.ImmutableList;

public class BaseResponse {

    private final String name;
    private final String initResponseCode;
    private final String parseResponseCode;
    private final ImmutableList<Integer> codes;

    public BaseResponse(
            final String name,
            final String initResponseCode,
            final String parseResponseCode,
            final ImmutableList<Integer> codes) {
        this.name = name;
        this.initResponseCode = initResponseCode;
        this.parseResponseCode = parseResponseCode;
        this.codes = codes;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Integer> getCodes() {
        return codes;
    }

    public String getParseResponseCode() {
        return parseResponseCode;
    }

    public String getInitResponseCode() {
        return initResponseCode;
    }
}
