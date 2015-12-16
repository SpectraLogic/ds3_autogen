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

/**
 * Converts a list of Ds3Requests into a Client model used for generating
 * the Ds3Client.java and Ds3ClientImpl.java
 */
public class ClientConverter {

    private final String packageName;
    private final ImmutableList<Ds3Request> ds3Requests;

    private ClientConverter(
            final ImmutableList<Ds3Request> ds3Requests,
            final String packageName) {
        this.ds3Requests = ds3Requests;
        this.packageName = packageName;
    }

    /**
     * Converts data stored within this ClientConverter into a Client model
     * @return A Client model
     */
    private Client convert() {
        return new Client(
                packageName,
                toCommandList(ds3Requests));
    }

    /**
     * Converts a list of Ds3Requests and a package name into a Client model
     * @param ds3Requests List of Ds3Requests
     * @param packageName The name of java package for the generated Client
     * @return A Client model containing information of the Ds3Requests and package name
     */
    public static Client toClient(
            final ImmutableList<Ds3Request> ds3Requests,
            final String packageName) {
        final ClientConverter converter = new ClientConverter(ds3Requests, packageName);

        return converter.convert();
    }

    /**
     * Converts a list of Ds3Requests into a list of Commands
     * @param ds3Requests List of Ds3Requests
     * @return A list of Commands
     */
    protected static ImmutableList<Command> toCommandList(final ImmutableList<Ds3Request> ds3Requests) {
        if (isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<Command> builder = ImmutableList.builder();
        for (final Ds3Request ds3Request : ds3Requests) {
            builder.add(new Command(
                    toCommandName(ds3Request.getName()),
                    removePath(ds3Request.getName()),
                    toResponseName(ds3Request.getName())));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Request's name into a Command name
     * @param requestName The name of a Ds3Request
     * @return The Command name
     */
    private static String toCommandName(final String requestName) {
        return removePath(requestName).replace("Request", "");
    }

    /**
     * Converts a Ds3Request's name into a Response name
     * @param requestName The name of a Ds3Request
     * @return The Response name
     */
    private static String toResponseName(final String requestName) {
        return removePath(requestName).replace("Request", "Response");
    }

    /**
     * Removes the path from a string. This is used to remove the contract path
     * path from Ds3Requests.
     * @param str A string
     * @return The subset of str that is located after the last period '.' within str
     */
    private static String removePath(final String str) {
        final String[] classParts = str.split("\\.");
        return classParts[classParts.length - 1];
    }
}
