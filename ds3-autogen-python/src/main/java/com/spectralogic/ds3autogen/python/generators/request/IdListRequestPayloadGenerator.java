/*
 * ******************************************************************************
 *   Copyright 2014-2018 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;
import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;
import static com.spectralogic.ds3autogen.utils.RequestConverterUtil.getNonVoidArgsFromParamList;

/**
 * Generates Python request handlers that take in a list of strings and marshals them into a list of ids.
 */
public class IdListRequestPayloadGenerator extends BaseRequestGenerator {

    protected static final String PAYLOAD_NAME = "id_list";

    /**
     * Gets the sorted list of required constructor parameters including the request payload
     */
    @Override
    public ImmutableList<ConstructorParam> toRequiredConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getNonVoidArgsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getAssignmentArguments(ds3Request));
        builder.add(new Arguments("string", PAYLOAD_NAME));

        return builder.build().stream()
                .sorted(new CustomArgumentComparator())
                .map(arg -> new ConstructorParam(camelToUnderscore(arg.getName()), false))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the python code that handles processing the request payload and headers
     */
    @Override
    public String getAdditionalContent(final String requestName) {
        return "if " + PAYLOAD_NAME + " is not None:\n" +
                pythonIndent(3) + "if not (isinstance(cur_id, basestring) for cur_id in " + PAYLOAD_NAME + "):\n" +
                pythonIndent(4) + "raise TypeError(\n" +
                pythonIndent(5) + "'" + requestName + " should have request payload of type: list of strings')\n" +
                pythonIndent(3) + "xml_id_list = IdsList(" + PAYLOAD_NAME + ")\n" +
                pythonIndent(3) + "self.body = xmldom.tostring(xml_id_list.to_xml())\n";
    }
}
