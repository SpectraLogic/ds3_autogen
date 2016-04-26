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

package com.spectralogic.ds3autogen.net.generators.parsermodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.net.model.parser.BaseParser;

import static com.spectralogic.ds3autogen.net.utils.GeneratorUtils.toModelParserName;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseName;
import static com.spectralogic.ds3autogen.utils.ResponsePayloadUtil.getPayloadResponseCode;

public class BaseResponseParserGenerator implements ResponseParserModelGenerator<BaseParser>, ResponseParserModelGeneratorUtils {

    @Override
    public BaseParser generate(final Ds3Request ds3Request, final String responsePayloadType, final String nameToMarshal) {
        final String parserName = getParserName(ds3Request.getName());
        final String requestName = removePath(ds3Request.getName());
        final String responseName = toResponseName(ds3Request.getName());
        final String netNameToMarshal = toNameToMarshal(nameToMarshal, responsePayloadType);
        final String modelParserName = toModelParserName(responsePayloadType);
        final Integer code = getResponseCode(ds3Request.getDs3ResponseCodes());

        return new BaseParser(
                parserName,
                requestName,
                responseName,
                netNameToMarshal,
                modelParserName,
                code);
    }

    /**
     * Gets the name of the encapsulating tag (i.e. name to marshal) for the response payload
     */
    @Override
    public String toNameToMarshal(final String nameToMarshal, final String typeName) {
        if (isEmpty(nameToMarshal)) {
            return "Data";
        }
        return nameToMarshal;
    }

    /**
     * Creates the name of the response parser
     */
    protected static String getParserName(final String requestName) {
        return removePath(requestName.replace("Request", "ResponseParser"));
    }

    /**
     * Gets the response code associated with the payload
     */
    protected static Integer getResponseCode(final ImmutableList<Ds3ResponseCode> responseCodes) {
        return getPayloadResponseCode(responseCodes);
    }
}
