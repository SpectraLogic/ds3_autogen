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

package com.spectralogic.ds3autogen.python.model.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.api.models.Operation;

public class BaseRequest {

    private final String name;
    private final String path;
    private final HttpVerb httpVerb;
    private final Operation operation;
    private final ImmutableList<Arguments> requiredArgs;
    private final ImmutableList<Arguments> optionalArgs;
    private final ImmutableList<String> voidArgs;

    public BaseRequest(
            final String name,
            final String path,
            final HttpVerb httpVerb,
            final Operation operation,
            final ImmutableList<Arguments> requiredArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<String> voidArgs) {
        this.name = name;
        this.path = path;
        this.httpVerb = httpVerb;
        this.operation = operation;
        this.requiredArgs = requiredArgs;
        this.optionalArgs = optionalArgs;
        this.voidArgs = voidArgs;
    }

    public String getName() {
        return name;
    }

    public ImmutableList<Arguments> getRequiredArgs() {
        return requiredArgs;
    }

    public String getHttpVerb() {
        return httpVerb.toString();
    }

    public ImmutableList<Arguments> getOptionalArgs() {
        return optionalArgs;
    }

    public ImmutableList<String> getVoidArgs() {
        return voidArgs;
    }

    public String getOperation() {
        if (operation == null) {
            return null;
        }
        return operation.toString().toLowerCase();
    }

    public String getPath() {
        return path;
    }
}
