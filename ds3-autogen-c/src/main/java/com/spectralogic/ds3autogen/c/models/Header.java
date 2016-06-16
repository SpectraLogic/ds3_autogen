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

package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

public class Header {
    private final ImmutableList<Enum> enums;
    private final ImmutableList<Struct> structs;
    private final ImmutableList<Request> requests;
    private final ImmutableSet<Parameter> optionalQueryParams;

    public Header(
            final ImmutableList<Enum> enums,
            final ImmutableList<Struct> structs,
            final ImmutableList<Request> requests,
            final ImmutableSet<Parameter> optionalQueryParams) {
        this.enums = enums;
        this.structs = structs;
        this.requests = requests;
        this.optionalQueryParams = optionalQueryParams;
    }

    public ImmutableList<Enum> getEnums() {
        return enums;
    }

    public ImmutableList<Struct> getStructs() {
        return structs;
    }

    public ImmutableList<Request> getRequests() {
        return requests;
    }

    public ImmutableSet<Parameter> getOptionalQueryParams() {
        return optionalQueryParams;
    }
}
