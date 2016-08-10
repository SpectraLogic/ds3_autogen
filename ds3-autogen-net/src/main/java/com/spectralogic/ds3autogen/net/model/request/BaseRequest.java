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
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.net.model.common.NetNullableVariable;

public class BaseRequest {
    private final String name;
    private final String path;
    private final HttpVerb verb;
    private final ImmutableList<Arguments> requiredArgs;
    private final ImmutableList<NetNullableVariable> optionalArgs;
    /** List of NetNullableVariables that represent a With-Constructor */
    private final ImmutableList<NetNullableVariable> withConstructors;
    private final ImmutableList<RequestConstructor> constructors;

    public BaseRequest(
            final String name,
            final String path,
            final HttpVerb verb,
            final ImmutableList<Arguments> requiredArgs,
            final ImmutableList<NetNullableVariable> optionalArgs,
            final ImmutableList<NetNullableVariable> withConstructors,
            final ImmutableList<RequestConstructor> constructors) {
        this.name = name;
        this.path = path;
        this.verb = verb;
        this.requiredArgs = requiredArgs;
        this.optionalArgs = optionalArgs;
        this.withConstructors = withConstructors;
        this.constructors = constructors;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public HttpVerb getVerb() {
        return verb;
    }

    public ImmutableList<Arguments> getRequiredArgs() {
        return requiredArgs;
    }

    public ImmutableList<NetNullableVariable> getOptionalArgs() {
        return optionalArgs;
    }

    public ImmutableList<RequestConstructor> getConstructors() {
        return constructors;
    }

    public ImmutableList<NetNullableVariable> getWithConstructors() {
        return withConstructors;
    }
}
