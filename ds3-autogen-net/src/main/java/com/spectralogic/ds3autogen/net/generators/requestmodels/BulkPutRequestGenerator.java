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

package com.spectralogic.ds3autogen.net.generators.requestmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Ds3Param;
import com.spectralogic.ds3autogen.net.utils.GeneratorUtils;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BulkPutRequestGenerator extends ObjectsRequestPayloadGenerator {

    /**
     * Gets the list of optional Arguments from the Ds3Request list of optional Ds3Params
     * excluding the MaxUploadSize which is special cased within the template
     */
    @Override
    public ImmutableList<Arguments> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalParams) {
        if(isEmpty(optionalParams)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> argsBuilder = ImmutableList.builder();
        for (final Ds3Param ds3Param : optionalParams) {
            if (!ds3Param.getName().equals("MaxUploadSize")) {
                argsBuilder.add(GeneratorUtils.toArgument(ds3Param));
            }
        }
        return argsBuilder.build();
    }
}
