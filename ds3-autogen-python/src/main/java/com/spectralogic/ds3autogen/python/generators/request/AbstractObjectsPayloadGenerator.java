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
 * Abstract generator for creating request handlers that have a list of objects that
 * need to be marshaled into a request payload.
 */
public abstract class AbstractObjectsPayloadGenerator extends BaseRequestGenerator {

    /**
     * Gets the sorted list of required constructor parameters including the request payload
     */
    @Override
    public ImmutableList<ConstructorParam> toRequiredConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(getNonVoidArgsFromParamList(ds3Request.getRequiredQueryParams()));
        builder.addAll(getAssignmentArguments(ds3Request));

        builder.add(new Arguments("", getRequestPayloadName()));

        return builder.build().stream()
                .sorted(new CustomArgumentComparator())
                .map(arg -> new ConstructorParam(camelToUnderscore(arg.getName()), false))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the python code for assigning the payload type to the body of the request.
     */
    @Override
    public String getAdditionalContent(final String requestName) {
        final String payloadName = getRequestPayloadName();
        final String payloadElementType = getPayloadElementType();
        return  "if " + payloadName + " is not None:\n"
                + pythonIndent(3) + "if not (isinstance(cur_obj, " + payloadElementType + ") for cur_obj in " + payloadName + "):\n"
                + pythonIndent(4) + "raise TypeError('" + requestName + " should have request payload of type: list of " + payloadElementType + "')\n"
                + pythonIndent(3) + "xml_object_list = " + getPayloadListType() + "(" + payloadName + ")\n"
                + pythonIndent(3) + "self.body = xmldom.tostring(xml_object_list.to_xml())\n";
    }

    /** retrieves the class name of the request payload elements (i.e. what is type of the list object passed to the request) */
    abstract String getPayloadElementType();

    /** retrieves the class name of the python encapsulating class that is used for marshaling */
    abstract String getPayloadListType();

    /** retrieves the name of the constructor parameter that represents the request payload */
    abstract String getRequestPayloadName();
}
