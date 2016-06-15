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
import com.spectralogic.ds3autogen.python.model.request.queryparam.QueryParam;

public class BaseRequest {

    private final String name;
    private final String path;
    private final HttpVerb httpVerb;
    private final RequestPayload requestPayload;
    private final ImmutableList<Arguments> requiredArgs;
    private final ImmutableList<Arguments> optionalArgs;
    private final ImmutableList<QueryParam> queryParams;

    public BaseRequest(
            final String name,
            final String path,
            final HttpVerb httpVerb,
            final RequestPayload requestPayload,
            final ImmutableList<Arguments> requiredArgs,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<QueryParam> queryParams) {
        this.name = name;
        this.path = path;
        this.httpVerb = httpVerb;
        this.requestPayload = requestPayload;
        this.requiredArgs = requiredArgs;
        this.optionalArgs = optionalArgs;
        this.queryParams = queryParams;
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

    public String getPath() {
        return path;
    }

    public RequestPayload getRequestPayload() {
        return requestPayload;
    }

    public ImmutableList<QueryParam> getQueryParams() {
        return queryParams;
    }
}
