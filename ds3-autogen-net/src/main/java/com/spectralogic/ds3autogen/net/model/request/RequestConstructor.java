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

package com.spectralogic.ds3autogen.net.model.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.enums.Operation;

/**
 * Contains the data for generating request constructor
 */
public class RequestConstructor {

    /** List of constructor parameters */
    private final ImmutableList<Arguments> constructorArgs;
    /** List of arguments that are added to the query parameters within the constructor */
    private final ImmutableList<Arguments> queryParams;
    private final Operation operation;
    private final String documentation;

    public RequestConstructor(
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> queryParams,
            final Operation operation,
            final String documentation) {
        this.constructorArgs = constructorArgs;
        this.queryParams = queryParams;
        this.operation = operation;
        this.documentation = documentation;
    }

    public ImmutableList<Arguments> getConstructorArgs() {
        return constructorArgs;
    }

    public ImmutableList<Arguments> getQueryParams() {
        return queryParams;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getDocumentation() {
        return documentation;
    }
}
