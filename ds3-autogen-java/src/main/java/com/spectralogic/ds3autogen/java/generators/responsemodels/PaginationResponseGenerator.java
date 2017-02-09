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

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;

/**
 * Response generator for response handlers that have patination headers.
 * These handlers extend the AbstractPaginationResponse, and pass the
 * pagination parameters to the super constructor.
 */
public class PaginationResponseGenerator extends BaseResponseGenerator {

    private final static String PAGINATION_RESPONSE_IMPORT = "com.spectralogic.ds3client.commands.interfaces.AbstractPaginationResponse";

    /**
     * Returns the import for the parent class for standard response, which
     * is AbstractResponse
     */
    @Override
    public String getParentImport() {
        return PAGINATION_RESPONSE_IMPORT;
    }

    /**
     * Retrieves the list of parameters need to create the response POJO and
     * adds the pagination parameters to the end of the list.
     */
    @Override
    public String toConstructorParams(final ImmutableList<Arguments> params) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        if (hasContent(params)) {
            builder.addAll(params);
        }
        builder.add(new Arguments("Integer", "pagingTotalResultCount"));
        builder.add(new Arguments("Integer", "pagingTruncated"));

        return builder.build().stream()
                .map(i -> "final " + i.getType() + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }
}
