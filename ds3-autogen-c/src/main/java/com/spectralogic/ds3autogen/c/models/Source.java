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

package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * A model to represent the ds3.c source file
 */
public class Source {
    private final ImmutableList<Enum> enums;
    private final ImmutableSet<Enum> queryParamEnums;
    private final ImmutableList<Struct> structs;
    private final ImmutableList<Request> requests;
    private final ImmutableSet<Parameter> optionalQueryParams;

    public Source(
            final ImmutableList<Enum> enums,
            final ImmutableSet<Enum> queryParamEnums,
            final ImmutableSet<Parameter> optionalQueryParams,
            final ImmutableList<Struct> structs,
            final ImmutableList<Request> requests) {
        this.enums = enums;
        this.queryParamEnums = queryParamEnums;
        this.optionalQueryParams = optionalQueryParams;
        this.structs = structs;
        this.requests = requests;
    }

    public ImmutableList<Enum> getEnums() {
        return enums;
    }

    public ImmutableSet<Enum> getQueryParamEnums() {
        return queryParamEnums;
    }

    public ImmutableSet<Parameter> getOptionalQueryParams() {
        return optionalQueryParams;
    }

    public ImmutableList<Struct> getStructs() {
        return structs;
    }

    public ImmutableList<Request> getRequests() {
        return requests;
    }
}
