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
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.JavaModuleUtil.getCommandPackage;
import static com.spectralogic.ds3autogen.java.utils.ResponseAndParserUtils.getResponseCodes;

/**
 * Response parser generator for AmazonS3 Get Object command
 */
public class GetObjectParserGenerator extends BaseResponseParserGenerator {

    protected static final ImmutableList<Integer> EXPECTED_RESPONSE_CODES = ImmutableList.of(200, 206);

    /**
     * Gets the expected response codes with no java code values since they are
     * only being used to determine the expected response codes within the template
     */
    @Override
    public ImmutableList<ResponseCode> toResponseCodeList(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String responseName,
            final boolean hasPaginationHeaders) {
        final ImmutableList<Integer> codes = getResponseCodes(ds3ResponseCodes);
        if (!codes.containsAll(EXPECTED_RESPONSE_CODES)) {
            throw new IllegalArgumentException("Does not contain expected response codes: " + EXPECTED_RESPONSE_CODES.toString());
        }

        final ResponseCode code200 = new ResponseCode(200, "");
        final ResponseCode code206 = new ResponseCode(206, "");

        return ImmutableList.of(code200, code206);
    }

    /**
     * Retrieves the java imports needed to generate the response parser
     */
    @Override
    public ImmutableSet<String> toImportList(
            final String responseName,
            final Ds3Request ds3Request,
            final ImmutableList<Ds3ResponseCode> responseCodes) {
        final ImmutableSet.Builder<String> builder = ImmutableSet.builder();

        builder.add("com.spectralogic.ds3client.commands.interfaces.MetadataImpl");
        builder.add("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils");
        builder.add("com.spectralogic.ds3client.exceptions.ContentLengthNotMatchException");
        builder.add("com.spectralogic.ds3client.networking.Metadata");
        builder.add("com.spectralogic.ds3client.networking.WebResponse");
        builder.add("com.spectralogic.ds3client.utils.IOUtils");
        builder.add("com.spectralogic.ds3client.utils.PerformanceUtils");
        builder.add("java.io.IOException");
        builder.add("java.io.InputStream");
        builder.add("java.nio.channels.WritableByteChannel");

        builder.add(getParentClassImport());
        builder.add(getCommandPackage(ds3Request) + "." + responseName);

        //Sort imports alphabetically for generated code aesthetics
        return builder.build().stream()
                .sorted()
                .collect(GuavaCollectors.immutableSet());
    }
}
