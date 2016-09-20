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

package com.spectralogic.ds3autogen.java.generators.responseparser;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.java.models.ResponseCode;

/**
 * Interface for utility functions used in response parser generators
 */
public interface ResponseParserGeneratorUtil {

    /**
     * Retrieves the name of the parent class
     */
    String getParentClass();

    /**
     * Retrieves the import for the parent class
     */
    String getParentClassImport();

    /**
     * Retrieves the java imports needed to generate the response parser
     */
    ImmutableSet<String> toImportList(final String responseName,
                                      final Ds3Request ds3Request,
                                      final ImmutableList<Ds3ResponseCode> responseCodes);

    /**
     * Converts a list of non-error Ds3ResponseCodes into a list of ResponseCode models
     * that are used to generate the response parsing code
     */
    ImmutableList<ResponseCode> toResponseCodeList(final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
                                                   final String responseName);
}
