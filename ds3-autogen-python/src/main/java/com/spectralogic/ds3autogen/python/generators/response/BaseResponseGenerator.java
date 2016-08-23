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

package com.spectralogic.ds3autogen.python.generators.response;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.python.model.response.*;
import com.spectralogic.ds3autogen.utils.ResponsePayloadUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.getParserModelName;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseName;
import static com.spectralogic.ds3autogen.utils.ResponsePayloadUtil.*;

public class BaseResponseGenerator implements ResponseModelGenerator<BaseResponse>, ResponseModelGeneratorUtils {

    private static final Logger LOG = LoggerFactory.getLogger(BaseResponseGenerator.class);

    @Override
    public BaseResponse generate(final Ds3Request ds3Request) {
        final String name = toResponseName(ds3Request.getName());
        final String initResponseCode = toInitResponse();
        final String parseResponseCode = toParseResponsePayload(ds3Request);
        final ImmutableList<Integer> codes = getStatusCodes(ds3Request.getDs3ResponseCodes(), ds3Request.getName());
        return new BaseResponse(name, initResponseCode, parseResponseCode, codes);
    }

    /**
     * Gets all non error response codes
     */
    @Override
    public ImmutableList<Integer> getStatusCodes(
            final ImmutableList<Ds3ResponseCode> ds3ResponseCodes,
            final String requestName) {
        if (isEmpty(ds3ResponseCodes)) {
            LOG.error("There are no response codes for request: " + requestName);
            return ImmutableList.of();
        }
        return ds3ResponseCodes.stream()
                .map(Ds3ResponseCode::getCode)
                .filter(ResponsePayloadUtil::isNonErrorCode)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the python code that will parse the response payload
     */
    @Override
    public String toParseResponsePayload(final Ds3Request ds3Request) {
        final ParsePayload parsePayload = getParsePayload(ds3Request);
        return parsePayload.toPythonCode();
    }

    /**
     * Gets the python code that initializes the response payload. For normal
     * responses, there is no init function.
     */
    @Override
    public String toInitResponse() {
        return "";
    }

    /**
     * Creates the Parse Payload object which describes how to parse the specified
     * response payload for this Ds3Request
     */
    protected static ParsePayload getParsePayload(final Ds3Request ds3Request) {
        if (!hasResponsePayload(ds3Request.getDs3ResponseCodes())) {
            return new NoPayload();
        }
        final String responsePayload = getResponsePayload(ds3Request.getDs3ResponseCodes());
        final Integer responseCode = getPayloadResponseCode(ds3Request.getDs3ResponseCodes());
        return new BaseParsePayload(getParserModelName(responsePayload), responseCode);
    }

    /**
     * Gets the name to marshal value of the specified type
     */
    protected static String getNameToMarshal(
            final String typeName,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeName) || isEmpty(typeMap)) {
            LOG.error("Could not find NameToMarshal because input was empty");
            return null;
        }
        final Ds3Type ds3Type = typeMap.get(typeName);
        if (ds3Type == null || isEmpty(ds3Type.getNameToMarshal())) {
            return null;
        }
        return ds3Type.getNameToMarshal();
    }
}
