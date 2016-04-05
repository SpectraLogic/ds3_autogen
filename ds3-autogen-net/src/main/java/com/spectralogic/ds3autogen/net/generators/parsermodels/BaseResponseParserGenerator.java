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

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.model.parser.BaseParser;

import static com.spectralogic.ds3autogen.net.utils.GeneratorUtils.toModelParserName;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseName;

public class BaseResponseParserGenerator implements ResponseParserModelGenerator<BaseParser>, ResponseParserModelGeneratorUtils {

    @Override
    public BaseParser generate(final Ds3Request ds3Request, final Ds3Type ds3Type) {
        final String parserName = getParserName(ds3Request.getName());
        final String requestName = removePath(ds3Request.getName());
        final String responseName = toResponseName(ds3Request.getName());
        final String nameToMarshal = toNameToMarshal(ds3Type.getNameToMarshal(), ds3Type.getName());
        final String modelParserName = toModelParserName(ds3Type.getName());

        return new BaseParser(
                parserName,
                requestName,
                responseName,
                nameToMarshal,
                modelParserName);
    }

    //TODO unit test
    /**
     * Creates the name of the response parser
     */
    protected static String getParserName(final String requestName) {
        return removePath(requestName.replace("Request", "ResponseParser"));
    }

    //TODO unit test
    /**
     * Gets the name of the encapsulating tag (i.e. name to marshal) for the response payload
     */
    public static String toNameToMarshal(final String nameToMarshal, final String typeName) {
        if (nameToMarshal == null) {
            return "Data";
        }
        if (nameToMarshal.equals("")) {
            throw new IllegalArgumentException("The name to marshal value is empty: " + typeName);
        }
        return nameToMarshal;
    }
}
