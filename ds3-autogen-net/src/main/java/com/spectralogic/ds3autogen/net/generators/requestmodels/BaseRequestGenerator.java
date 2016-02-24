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
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.net.NetHelper;
import com.spectralogic.ds3autogen.net.model.request.BaseRequest;
import com.spectralogic.ds3autogen.net.util.GeneratorUtils;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class BaseRequestGenerator implements RequestModelGenerator<BaseRequest>, RequestModelGeneratorUtils {

    @Override
    public BaseRequest generate(final Ds3Request ds3Request) {

        final String name = GeneratorUtils.toRequestName(ds3Request);
        final String path = GeneratorUtils.toRequestPath(ds3Request);
        final ImmutableList<Arguments> requiredArgs = toRequiredArgumentsList(ds3Request);
        final ImmutableList<Arguments> optionalArgs = toOptionalArgumentsList(ds3Request.getOptionalQueryParams());

        return new BaseRequest(NetHelper.getInstance(), name, path, ds3Request.getHttpVerb(), requiredArgs, optionalArgs);
    }

    public static ImmutableList<Arguments> toOptionalArgumentsList(final ImmutableList<Ds3Param> optionalparams) {
        if(isEmpty(optionalparams)) {
            return ImmutableList.of();
        }

        final ImmutableList.Builder<Arguments> argsBuilder = ImmutableList.builder();
        for (final Ds3Param ds3Param : optionalparams) {
            final String paramType = ds3Param.getType().substring(ds3Param.getType().lastIndexOf(".") + 1);
            argsBuilder.add(new Arguments(paramType, ds3Param.getName()));
        }
        return argsBuilder.build();
    }

    @Override
    public ImmutableList<Arguments> toRequiredArgumentsList(final Ds3Request ds3Request) {
        return GeneratorUtils.getRequiredArgs(ds3Request);
    }
}
