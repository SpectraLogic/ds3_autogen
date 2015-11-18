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

import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.utils.Helper;

public class Request {
    private final String name;
    private final HttpVerb verb;
    private final String path;
    private final ImmutableList<Arguments> requiredArguments;
    private final ImmutableList<Arguments> optionalArguments;
    private final Helper helper;

    public Request(
            final String name,
            final HttpVerb verb,
            final String path,
            final ImmutableList<Arguments> requiredArguments,
            final ImmutableList<Arguments> optionalArguments) {
        this.name = name;
        this.verb = verb;
        this.path = path;
        this.requiredArguments = requiredArguments;
        this.optionalArguments = optionalArguments;
        this.helper = Helper.getInstance();
    }

    public String getName() {
        return name;
    }

    public String getUnqualifiedName() {
        return helper.unqualifiedName(name);
    }

    public String getNameRoot() {
        return helper.removeTrailingRequestHandler(getUnqualifiedName());
    }

    public String getNameRootUnderscores() {
        return helper.camelToUnderscore(getNameRoot());
    }

    public HttpVerb getVerb() {
        return verb;
    }

    public String getPath() {
        return path;
    }

    public ImmutableList<Arguments> getRequiredArguments() {
        return requiredArguments;
    }

    public ImmutableList<Arguments> getOptionalArguments() {
        return optionalArguments;
    }
}
