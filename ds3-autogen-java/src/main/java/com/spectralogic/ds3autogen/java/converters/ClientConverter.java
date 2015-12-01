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

package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.java.models.Client;
import com.spectralogic.ds3autogen.java.models.Command;

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class ClientConverter {

    private final String packageName;
    private final ImmutableList<Ds3Request> ds3Requests;

    private ClientConverter(
            final ImmutableList<Ds3Request> ds3Requests,
            final String packageName) {
        this.ds3Requests = ds3Requests;
        this.packageName = packageName;
    }

    private Client convert() {
        return new Client(
                packageName,
                getCommands(ds3Requests));
    }

    protected static ImmutableList<Command> getCommands(final ImmutableList<Ds3Request> ds3Requests) {
        if (isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Command> builder = ImmutableList.builder();
        for (final Ds3Request ds3Request : ds3Requests) {
            builder.add(new Command(
                    getCommandName(ds3Request.getName()),
                    removePath(ds3Request.getName()),
                    getResponseName(ds3Request.getName())));
        }
        return builder.build();
    }

    public static Client toClient(
            final ImmutableList<Ds3Request> ds3Requests,
            final String packageName) {
        final ClientConverter converter = new ClientConverter(ds3Requests, packageName);

        return converter.convert();
    }

    private static String getCommandName(final String requestName) {
        return removePath(requestName).replace("Request", "");
    }

    private static String getResponseName(final String requestName) {
        return removePath(requestName).replace("Request", "Response");
    }

    private static String removePath(final String str) {
        final String[] classParts = str.split("\\.");
        return classParts[classParts.length - 1];
    }
}
