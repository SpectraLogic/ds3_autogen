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
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

import java.util.Optional;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;

/**
 * Response handler generator for SpectraS3 Allocate Job Chunk and
 * SpectraS3 Get Job Chunks Ready For Client Processing commands
 */
public class RetryAfterResponseGenerator extends BaseResponseGenerator {

    /**
     * Retrieves the arguments: response payload, retry after seconds, and status
     */
    @Override
    public ImmutableList<Arguments> toParamList(final ImmutableList<Ds3ResponseCode> ds3ResponseCodes) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.add(new Arguments("int", "RetryAfterSeconds"));
        builder.add(new Arguments("Status", "Status"));

        if (hasContent(ds3ResponseCodes)) {
            final ImmutableList<Arguments> responseCodeArgs = ds3ResponseCodes.stream()
                    .filter(i -> i.getCode() < ERROR_CODE_THRESHOLD) //Filter error codes
                    .map(BaseResponseGenerator::toParam)
                    .filter(Optional::isPresent) //Filters out empty optional arguments
                    .map(Optional::get) //Get the Arguments object out of the optional
                    .collect(GuavaCollectors.immutableList());

            builder.addAll(responseCodeArgs);
        }

        return ImmutableList.sortedCopyOf(new CustomArgumentComparator(), builder.build());
    }
}
