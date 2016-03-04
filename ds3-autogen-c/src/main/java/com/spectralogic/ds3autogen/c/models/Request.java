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
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;

public class Request {
    private final String name;
    private final Classification classification;
    private final HttpVerb verb;
    private final String buildPathArgs;
    private final Operation operation;
    private final Action action;
    private final boolean isResourceRequired;
    private final boolean isResourceIdRequired;
    private final ImmutableMap<String, String> requiredArguments;
    private final ImmutableMap<String, String> optionalArguments;
    private final ImmutableList<Ds3ResponseCode> responseCodes;
    private final String responseType;
    private final boolean hasResponsePayload;
    private final RequestHelper requestHelper;

    public Request(
            final String name,
            final Classification classification,
            final HttpVerb verb,
            final String buildPathArgs,
            final Operation operation,
            final Action action,
            final boolean isResourceRequired,
            final boolean isResourceIdRequired,
            final ImmutableMap<String, String> requiredArguments,
            final ImmutableMap<String, String> optionalArguments,
            final String responseType,
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        this.name = name;
        this.classification = classification;
        this.verb = verb;
        this.buildPathArgs = buildPathArgs;
        this.operation = operation;
        this.action = action;
        this.isResourceRequired = isResourceRequired;
        this.isResourceIdRequired = isResourceIdRequired;
        this.requiredArguments = requiredArguments;
        this.optionalArguments = optionalArguments;
        this.responseCodes = responseCodes;
        this.responseType = responseType;
        this.hasResponsePayload = !responseType.isEmpty();
        this.requestHelper = RequestHelper.getInstance();
    }

    public String getName() {
        return name;
    }

    public Classification getClassification() {
        return classification;
    }

    public HttpVerb getVerb() {
        return verb;
    }

    public String getBuildPathArgs() {
        return buildPathArgs;
    }

    public Operation getOperation() {
        return operation;
    }

    public Action getAction() {
        return action;
    }

    public boolean isResourceRequired() {
        return isResourceRequired;
    }

    public boolean isResourceIdRequired() {
        return isResourceIdRequired;
    }

    /**
     * Key is arg name
     * Value is arg type
     */
    public ImmutableMap<String, String> getRequiredArguments() {
        return requiredArguments;
    }

    /**
     * Key is arg name
     * Value is arg type
     */
    public ImmutableMap<String, String> getOptionalArguments() {
        return optionalArguments;
    }

    public ImmutableList<Ds3ResponseCode> getResponseCodes() {
        return responseCodes;
    }

    public String getResponseType() {
        return responseType;
    }

    public boolean hasResponsePayload() {
        return hasResponsePayload;
    }

    public RequestHelper getRequestHelper() {
        return requestHelper;
    }
}
