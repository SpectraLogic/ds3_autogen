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
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;

import static com.spectralogic.ds3autogen.java.generators.requestmodels.BaseRequestGenerator.isSpectraDs3;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BulkResponseGenerator extends BaseResponseGenerator {

    private final static String BULK_RESPONSE_IMPORT = "com.spectralogic.ds3client.commands.interfaces.BulkResponse";

    /**
     * Returns the import for the parent class for bulk responses, which
     * is BulkResponse
     */
    @Override
    public String getParentImport() {
        return BULK_RESPONSE_IMPORT;
    }

    /**
     * Gets the import for the bulk parent class if the command is a
     * Spectra S3 command.
     */
    @Override
    public ImmutableList<String> getAllImports(
            final ImmutableList<Ds3ResponseCode> responseCodes,
            final String packageName) {
        if (isEmpty(responseCodes)) {
            return ImmutableList.of();
        }

        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();
        builder.add(getParentImport());

        return builder.build().asList();
    }
}
