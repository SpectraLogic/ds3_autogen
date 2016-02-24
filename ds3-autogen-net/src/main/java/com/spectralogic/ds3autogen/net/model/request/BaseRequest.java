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
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.net.NetHelper;

public class BaseRequest {
    private final NetHelper netHelper;
    private final String name;
    private final String path;
    private final HttpVerb verb;
    private final ImmutableList<Arguments> constructorArgs;
    private final ImmutableList<Arguments> requiredArgs;
    private final ImmutableList<Arguments> optionalArgs;

    public BaseRequest(
            final NetHelper netHelper,
            final String name,
            final String path,
            final HttpVerb verb,
            final ImmutableList<Arguments> constructorArgs,
            final ImmutableList<Arguments> requiredArgs,
            final ImmutableList<Arguments> optionalArgs) {
        this.netHelper = netHelper;
        this.name = name;
        this.path = path;
        this.verb = verb;
        this.constructorArgs = constructorArgs;
        this.requiredArgs = requiredArgs;
        this.optionalArgs = optionalArgs;
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

    public NetHelper getNetHelper() {
        return netHelper;
    }

    public ImmutableList<Arguments> getRequiredArgs() {
        return requiredArgs;
    }

    public ImmutableList<Arguments> getOptionalArgs() {
        return optionalArgs;
    }

    public ImmutableList<Arguments> getConstructorArgs() {
        return constructorArgs;
    }
}
