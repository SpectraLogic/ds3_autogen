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

/**
 * Response handler generator for SpectraS3 bulk commands
 */
public class BulkResponseGenerator extends BaseResponseGenerator {

    private final static String BULK_RESPONSE_IMPORT = "com.spectralogic.ds3client.commands.interfaces.BulkResponse";

    /**
     * Returns the import for the parent class: Bulk Response
     */
    @Override
    public String getParentImport() {
        return BULK_RESPONSE_IMPORT;
    }
}
