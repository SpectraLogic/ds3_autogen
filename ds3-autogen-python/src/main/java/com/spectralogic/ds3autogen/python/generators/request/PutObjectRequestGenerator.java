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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import com.spectralogic.ds3autogen.utils.comparators.CustomArgumentComparator;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;
import static com.spectralogic.ds3autogen.utils.Helper.camelToUnderscore;

/**
 * Creates the python request model for the Amazon request Put Object which has the optional
 * request payload of real_file_name, and which opens the specified file
 */
public class PutObjectRequestGenerator extends BaseRequestGenerator {

    /** Used to specify the actual file name on the local machine if it differs from the BP name */
    private static final String PAYLOAD_NAME = "real_file_name";

    /**
     * Gets the sorted list of optional constructor params, including the request payload
     */
    @Override
    public ImmutableList<ConstructorParam> toOptionalConstructorParams(final Ds3Request ds3Request) {
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        builder.addAll(toOptionalArgumentsList(ds3Request.getOptionalQueryParams()));
        builder.add(new Arguments("string", PAYLOAD_NAME));
        builder.add(new Arguments("", "headers"));
        builder.add(new Arguments("", "stream"));

        return builder.build().stream()
                .sorted(new CustomArgumentComparator())
                .map(arg -> new ConstructorParam(camelToUnderscore(arg.getName()), true))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Gets the python code that handles processing the request payload and headers
     */
    @Override
    public String getAdditionalContent(final Ds3Request ds3Request, final String requestName) {
        return "if headers is not None:\n" +
                pythonIndent(3) + "for key, val in headers.iteritems():\n" +
                pythonIndent(4) + "if val:\n" +
                pythonIndent(5) + "self.headers[key] = val\n" +
                pythonIndent(2) + "self.object_name = typeCheckString(object_name)\n" +
                pythonIndent(2) + "object_data = None\n" +
                pythonIndent(2) + "if stream:\n" +
                pythonIndent(3) + "object_data = stream\n" +
                pythonIndent(2) + "else:\n" +
                pythonIndent(3) + "effectiveFileName = self.object_name\n" +
                pythonIndent(3) + "if " + PAYLOAD_NAME +":\n" +
                pythonIndent(4) + "effectiveFileName = typeCheckString(" + PAYLOAD_NAME + ")\n" +
                pythonIndent(3) + "object_data = open(effectiveFileName, \"rb\")\n" +
                pythonIndent(2) + "if offset:\n" +
                pythonIndent(3) + "object_data.seek(offset, 0)\n" +
                pythonIndent(2) + "self.body = object_data\n";
    }
}
