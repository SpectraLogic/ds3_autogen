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

package com.spectralogic.ds3autogen.net.generators.responsemodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.NetHelper;
import com.spectralogic.ds3autogen.net.model.response.BaseResponse;
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;

public class BaseResponseGenerator implements ResponseModelGenerator<BaseResponse>, ResponseModelGeneratorUtils {

    @Override
    public BaseResponse generate(final Ds3Request ds3Request, final Ds3Type ds3Type) {
        final String name = NormalizingContractNamesUtil.toResponseName(ds3Request.getName());
        final ImmutableList<Arguments> arguments = toArgumentsList(ds3Type.getElements());

        return new BaseResponse(
                name,
                arguments);
    }

    /**
     * Converts a list of Ds3Elements into a list of Arguments
     */
    @Override
    public ImmutableList<Arguments> toArgumentsList(final ImmutableList<Ds3Element> elements) {
        if (isEmpty(elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Arguments> builder = ImmutableList.builder();
        for (final Ds3Element element : elements) {
            builder.add(toArgument(element));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Element into an Argument
     */
    protected static Arguments toArgument(final Ds3Element ds3Element) {
        return new Arguments(
                toArgType(ds3Element.getType(), ds3Element.getComponentType()),
                stripPath(ds3Element.getName()));
    }

    /**
     * Converts a type and component type into .net code for a composite type
     */
    protected static String toArgType(final String type, final String componentType) {
        if (isEmpty(componentType)) {
            return NetHelper.toNetType(stripPath(type));
        }
        if (!type.equals("array")) {
            throw new IllegalArgumentException("Unknown type: " + type + " associated with component type: " + componentType);
        }
        return "IEnumerable<" + NetHelper.toNetType(stripPath(componentType)) + ">";
    }
}
