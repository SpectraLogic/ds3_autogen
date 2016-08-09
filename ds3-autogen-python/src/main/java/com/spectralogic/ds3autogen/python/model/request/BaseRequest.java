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
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.python.model.request.queryparam.QueryParam;

public class BaseRequest {

    private final String name;
    private final String path;
    private final HttpVerb httpVerb;

    /** Additional python code used for special casing requests */
    private final String additionalContent;

    /** List of parameter names that are assigned within the constructor */
    private final ImmutableList<String> assignments;

    /** List of sorted constructor parameters */
    private final ImmutableList<String> constructorParams;

    private final ImmutableList<Arguments> optionalArgs;
    private final ImmutableList<QueryParam> queryParams;

    public BaseRequest(
            final String name,
            final String path,
            final HttpVerb httpVerb,
            final String additionalContent,
            final ImmutableList<String> assignments,
            final ImmutableList<String> constructorParams,
            final ImmutableList<Arguments> optionalArgs,
            final ImmutableList<QueryParam> queryParams) {
        this.name = name;
        this.path = path;
        this.httpVerb = httpVerb;
        this.additionalContent = additionalContent;
        this.assignments = assignments;
        this.constructorParams = constructorParams;
        this.optionalArgs = optionalArgs;
        this.queryParams = queryParams;
    }

    public String getName() {
        return name;
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

    public ImmutableList<QueryParam> getQueryParams() {
        return queryParams;
    }

    public String getAdditionalContent() {
        return additionalContent;
    }

    public ImmutableList<String> getAssignments() {
        return assignments;
    }

    public ImmutableList<String> getConstructorParams() {
        return constructorParams;
    }
}
