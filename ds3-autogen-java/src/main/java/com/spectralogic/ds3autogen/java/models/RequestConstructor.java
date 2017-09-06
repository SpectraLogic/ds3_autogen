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

package com.spectralogic.ds3autogen.java.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;

public class RequestConstructor {

    /** Constructor parameters */
    private final ImmutableList<ConstructorParam> parameters;

    /** Parameters that are assigned to self within constructor */
    private final ImmutableList<Arguments> assignments;

    /** Parameters that are added to query params within constructor */
    private final ImmutableList<QueryParam> queryParams;

    private boolean isDeprecated = false;
    private ImmutableList<String> additionalLines = ImmutableList.of();

    private final String documentation;

    private final ImmutableList<Precondition> preconditions;

    public RequestConstructor(
            final ImmutableList<ConstructorParam> parameters,
            final ImmutableList<Arguments> assignments,
            final ImmutableList<QueryParam> queryParams,
            final ImmutableList<Precondition> preconditions,
            final String documentation) {
        this.parameters = parameters;
        this.assignments = assignments;
        this.queryParams = queryParams;
        this.preconditions = preconditions;
        this.documentation = documentation;
    }

    public RequestConstructor(
            final boolean isDeprecated,
            final ImmutableList<String> additionalLines,
            final ImmutableList<ConstructorParam> parameters,
            final ImmutableList<Arguments> assignments,
            final ImmutableList<QueryParam> queryParams,
            final ImmutableList<Precondition> preconditions,
            final String documentation) {
        this.isDeprecated = isDeprecated;
        this.additionalLines = additionalLines;
        this.parameters = parameters;
        this.assignments = assignments;
        this.queryParams = queryParams;
        this.preconditions = preconditions;
        this.documentation = documentation;
    }

    public ImmutableList<ConstructorParam> getParameters() {
        return parameters;
    }

    public ImmutableList<Arguments> getAssignments() {
        return assignments;
    }

    public ImmutableList<QueryParam> getQueryParams() {
        return queryParams;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }

    public ImmutableList<String> getAdditionalLines() {
        return additionalLines;
    }

    public String getDocumentation() {
        return documentation;
    }

    public ImmutableList<Precondition> getPreconditions() {
        return preconditions;
    }
}
