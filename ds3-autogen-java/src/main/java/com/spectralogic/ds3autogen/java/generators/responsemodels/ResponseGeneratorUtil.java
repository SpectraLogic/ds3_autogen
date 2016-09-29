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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;

public interface ResponseGeneratorUtil {

    /**
     * Returns the import for the parent class for this response
     */
    String getParentImport();

    /**
     * Returns the parent class that the response extends
     */
    String getParentClass();

    /**
     * Gets all the imports associated with response types that the response will
     * need in order to properly generate the Java request code
     */
    ImmutableSet<String> getAllImports(final Ds3Request ds3Request);

    /**
     * Retrieves the list of parameters needed to create the response POJO
     */
    ImmutableList<Arguments> toParamList(final ImmutableList<Ds3ResponseCode> ds3ResponseCodes);

    /**
     * Converts a list of Arguments into a comma-separated list of parameters for the constructor
     */
    String toConstructorParams(final ImmutableList<Arguments> params);

    /**
     * Retrieves the list of parameters need to create the response POJO. If the response
     * has pagination headers, then the pagination parameters are appended to the end of
     * the list.
     */
    ImmutableList<Arguments> toParamListWithPagination(final Ds3Request ds3Request);
}
