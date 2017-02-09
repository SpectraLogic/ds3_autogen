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
import com.spectralogic.ds3autogen.api.models.enums.Action;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.api.models.enums.Operation;

public class Request {
    private final String name;
    private final String initName;
    private final Classification classification;
    private final HttpVerb verb;
    private final String buildPathArgs;
    private final Operation operation;
    private final Action action;
    private final ImmutableList<Parameter> paramList;
    private final ImmutableList<Parameter> requiredQueryParams;
    private final ImmutableList<Parameter> optionalQueryParams;
    private final boolean isResourceRequired;
    private final boolean isResourceIdRequired;
    private final Parameter  requestPayload;
    private final boolean hasRequestPayload;
    private final String responseType;
    private final boolean hasResponsePayload;
    private final boolean supportsPagination;
    private final String documentation;

    public Request(
            final String name,
            final Classification classification,
            final HttpVerb verb,
            final String buildPathArgs,
            final Operation operation,
            final Action action,
            final ImmutableList<Parameter> paramList,
            final ImmutableList<Parameter> requiredQueryParams,
            final ImmutableList<Parameter> optionalQueryParams,
            final boolean isResourceRequired,
            final boolean isResourceIdRequired,
            final Parameter requestPayload,
            final String responseType,
            final boolean supportsPagination,
            final String documentation) {
        this.name = "ds3_" + name;
        this.initName = "ds3_init_" + name;
        this.classification = classification;
        this.verb = verb;
        this.buildPathArgs = buildPathArgs;
        this.operation = operation;
        this.action = action;
        this.paramList = paramList;
        this.requiredQueryParams = requiredQueryParams;
        this.optionalQueryParams = optionalQueryParams;
        this.isResourceRequired = isResourceRequired;
        this.isResourceIdRequired = isResourceIdRequired;
        this.requestPayload = requestPayload;
        this.hasRequestPayload = requestPayload != null;
        this.responseType = responseType;
        this.hasResponsePayload = !responseType.isEmpty();
        this.supportsPagination = supportsPagination;
        this.documentation = documentation;
    }

    public String getName() {
        return name;
    }

    public String getInitName() {
        return initName;
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

    public ImmutableList<Parameter> getOptionalQueryParams() {
        return optionalQueryParams;
    }

    public ImmutableList<Parameter> getRequiredQueryParams() {
        return requiredQueryParams;
    }

    public boolean isResourceRequired() {
        return isResourceRequired;
    }

    public boolean isResourceIdRequired() {
        return isResourceIdRequired;
    }

    public Parameter getRequestPayload() {
        return requestPayload;
    }

    public boolean hasRequestPayload() {
        return hasRequestPayload;
    }

    public String getResponseType() {
        return responseType;
    }

    public boolean hasResponsePayload() {
        return hasResponsePayload;
    }

    public boolean supportsPagination() {
        return supportsPagination;
    }

    public String getDocumentation() {
        return documentation;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nRequest[").append(getName()).append("]\n");
        builder.append("  Classification[").append(getClassification()).append("]\n");
        builder.append("  Verb[").append(getVerb()).append("]\n");
        builder.append("  BuildPathArgs[").append(getBuildPathArgs()).append("]\n");
        builder.append("  Operation[").append(getOperation()).append("]\n");
        builder.append("  Action[").append(getAction()).append("]\n");
        builder.append("  ParameterList:\n");
        for (final Parameter parm: getParamList()) {
            builder.append(parm.toString()).append("\n");
        }
        builder.append("  OptionalQueryParams:\n");
        for (final Parameter parm: getOptionalQueryParams()) {
            builder.append(parm.toString()).append("\n");
        }
        builder.append("  RequiredQueryParams:\n");
        for (final Parameter parm: getRequiredQueryParams()) {
            builder.append(parm.toString()).append("\n");
        }
        builder.append("  isResourceRequired[").append(isResourceRequired()).append("]\n");
        builder.append("  isResourceIdRequired[").append(isResourceIdRequired()).append("]\n");
        builder.append("  RequestPayload[").append(getRequestPayload()).append("]\n");
        builder.append("  hasRequestPayload[").append(hasRequestPayload()).append("]\n");
        builder.append("  ResponseType[").append(getResponseType()).append("]\n");
        builder.append("  hasResponsePayload[").append(hasResponsePayload()).append("]\n");
        builder.append("  supportsPagination[").append(supportsPagination()).append("]\n");
        builder.append("  documentation[").append(getDocumentation()).append("]\n");
        return builder.toString();
    }
}
