/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.go.generators.client;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.go.models.client.Client;
import com.spectralogic.ds3autogen.go.models.client.Command;
import com.spectralogic.ds3autogen.utils.ClientGeneratorUtil;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

public class BaseClientGenerator implements ClientModelGenerator<Client> {

    @Override
    public Client generate(final ImmutableList<Ds3Request> ds3Requests) {
        final ImmutableList<Command> commands = toCommandList(ds3Requests);

        return new Client(commands);
    }

    private static ImmutableList<Command> toCommandList(final ImmutableList<Ds3Request> ds3Requests) {
        if (ConverterUtil.isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }

        return ds3Requests.stream()
                .map(BaseClientGenerator::toCommand)
                .collect(GuavaCollectors.immutableList());
    }

    private static Command toCommand(final Ds3Request ds3Request) {
        final String name = ClientGeneratorUtil.toCommandName(ds3Request.getName());
        return new Command(name);
    }
}
