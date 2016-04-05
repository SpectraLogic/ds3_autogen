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
import com.spectralogic.ds3autogen.api.models.Action;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;

public class Request {
    private final String name;
    private final Classification classification;
    private final HttpVerb verb;
    private final String buildPathArgs;
    private final Operation operation;
    private final Action action;
    private final ImmutableList<Parameter> paramList;
    private final boolean isResourceRequired;
    private final boolean isResourceIdRequired;
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
            final ImmutableList<Parameter> paramList,
            final boolean isResourceRequired,
            final boolean isResourceIdRequired,
            final String responseType) {
        this.name = name;
        this.classification = classification;
        this.verb = verb;
        this.buildPathArgs = buildPathArgs;
        this.operation = operation;
        this.action = action;
        this.paramList = paramList;
        this.isResourceRequired = isResourceRequired;
        this.isResourceIdRequired = isResourceIdRequired;
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

    public ImmutableList<Parameter> getParamList() {
        return paramList;
    }

    public boolean isResourceRequired() {
        return isResourceRequired;
    }

    public boolean isResourceIdRequired() {
        return isResourceIdRequired;
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
