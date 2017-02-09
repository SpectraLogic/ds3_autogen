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

package com.spectralogic.ds3autogen.java.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

/**
 * Response handler generator for AmazonS3 Head Object command
 */
public class HeadObjectResponseGenerator extends BaseResponseGenerator {

    /**
     * Retrieves the list of parameters needed to create the response POJO
     * for head object
     */
    @Override
    public ImmutableList<Arguments> toParamList(final ImmutableList<Ds3ResponseCode> ds3ResponseCodes) {
        final ImmutableList<Arguments> params = ImmutableList.of(
                new Arguments("Metadata", "Metadata"),
                new Arguments("long", "ObjectSize"),
                new Arguments("Status", "Status"));

        return params.stream()
                .sorted(new CustomArgumentComparator())
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Retrieves the parent and metadata import
     */
    @Override
    public ImmutableSet<String> getAllImports(final Ds3Request ds3Request) {
        return ImmutableSet.of(
                getParentImport(),
                "com.spectralogic.ds3client.networking.Metadata",
                "com.spectralogic.ds3client.models.ChecksumType");
    }
}
