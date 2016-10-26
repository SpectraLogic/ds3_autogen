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
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.java.models.AnnotationInfo;
import com.spectralogic.ds3autogen.java.models.Client;
import com.spectralogic.ds3autogen.java.models.Command;
import com.spectralogic.ds3autogen.java.models.CustomCommand;
import com.spectralogic.ds3autogen.utils.ClientGeneratorUtil;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toCommandDocs;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isGetObjectAmazonS3Request;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseName;
import static com.spectralogic.ds3autogen.utils.ResponsePayloadUtil.getResponsePayload;

/**
 * Converts a list of Ds3Requests into a Client model used for generating
 * the Ds3Client.java and Ds3ClientImpl.java
 */
public class ClientConverter {

    private final String packageName;
    private final ImmutableList<Ds3Request> ds3Requests;
    private final Ds3DocSpec docSpec;

    private ClientConverter(
            final ImmutableList<Ds3Request> ds3Requests,
            final String packageName,
            final Ds3DocSpec docSpec) {
        this.ds3Requests = ds3Requests;
        this.packageName = packageName;
        this.docSpec = docSpec;
    }

    /**
     * Converts data stored within this ClientConverter into a Client model
     * @return A Client model
     */
    private Client convert() {
        return new Client(
                packageName,
                toCommandList(ds3Requests, docSpec),
                toCustomCommandList(ds3Requests, docSpec));
    }

    /**
     * Converts a list of Ds3Requests and a package name into a Client model
     * @param ds3Requests List of Ds3Requests
     * @param packageName The name of java package for the generated Client
     * @return A Client model containing information of the Ds3Requests and package name
     */
    public static Client toClient(
            final ImmutableList<Ds3Request> ds3Requests,
            final String packageName,
            final Ds3DocSpec docSpec) {
        final ClientConverter converter = new ClientConverter(ds3Requests, packageName, docSpec);

        return converter.convert();
    }

    /**
     * Creates a list of Custom Commands from the Ds3Request list assuming
     * that the request list contains custom commands.
     */
    protected static ImmutableList<CustomCommand> toCustomCommandList(
            final ImmutableList<Ds3Request> ds3Requests,
            final Ds3DocSpec docSpec) {
        if (isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }
        return ds3Requests.stream()
                .filter(ClientConverter::isCustomCommand)
                .map(request -> toCustomCommand(request, docSpec))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Creates a custom command from a Ds3Request. If the request does not
     * describe a custom command, then an error is thrown.
     */
    protected static CustomCommand toCustomCommand(final Ds3Request ds3Request, final Ds3DocSpec docSpec) {
        if (isGetObjectAmazonS3Request(ds3Request)) {
            return toGetObjectAmazonS3CustomCommand(ds3Request, docSpec);
        }
        throw new IllegalArgumentException("Ds3Request is not a special cased command: " + ds3Request.getName());
    }

    /**
     * Creates the AmazonS3 Get Object Custom command from the Ds3Request.
     * This assumes that the provided Ds3Request is the Get Object.
     */
    protected static CustomCommand toGetObjectAmazonS3CustomCommand(
            final Ds3Request ds3Request,
            final Ds3DocSpec docSpec) {
        final String responseName = toResponseName(ds3Request.getName());
        final String customBody = "return new " + responseName + "Parser(\n" +
                "                request.getChannel(),\n" +
                "                this.netClient.getConnectionDetails().getBufferSize(),\n" +
                "                request.getObjectName())\n" +
                "                .startResponse(this.netClient.getResponse(request));";

        final String requestName = removePath(ds3Request.getName());
        return new CustomCommand(
                ClientGeneratorUtil.toCommandName(ds3Request.getName()),
                requestName,
                responseName,
                toCommandDocs(requestName, docSpec, 1),
                toAnnotationInfo(ds3Request),
                customBody);
    }

    /**
     * Determines if the provided Ds3Request describes a custom command
     * i.e. a request/response that requires a unique function body
     * within the Ds3ClientImpl
     */
    protected static boolean isCustomCommand(final Ds3Request ds3Request) {
        return isGetObjectAmazonS3Request(ds3Request);
    }

    /**
     * Converts a list of Ds3Requests into a list of Commands
     * @param ds3Requests List of Ds3Requests
     * @return A list of Commands
     */
    protected static ImmutableList<Command> toCommandList(
            final ImmutableList<Ds3Request> ds3Requests,
            final Ds3DocSpec docSpec) {
        if (isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }
        return ds3Requests.stream()
                .filter(ds3Request -> !isCustomCommand(ds3Request))
                .map(ds3Request -> toCommand(ds3Request, docSpec))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Creates a Command model from a Ds3Request
     */
    protected static Command toCommand(
            final Ds3Request ds3Request,
            final Ds3DocSpec docSpec) {
        final String requestName = removePath(ds3Request.getName());
        return new Command(
                ClientGeneratorUtil.toCommandName(ds3Request.getName()),
                requestName,
                toResponseName(ds3Request.getName()),
                toCommandDocs(requestName, docSpec, 1),
                toAnnotationInfo(ds3Request));
    }

    /**
     * Creates the annotation information for a request
     */
    protected static AnnotationInfo toAnnotationInfo(final Ds3Request ds3Request) {
        if (ds3Request.getClassification() != Classification.spectrads3) {
            return null;
        }
        final String action = ds3Request.getAction() == null? null : ds3Request.getAction().toString();
        final String resource = ds3Request.getResource() == null? null : ds3Request.getResource().toString();
        final String responsePayload = removePath(getResponsePayload(ds3Request.getDs3ResponseCodes()));
        return new AnnotationInfo(
                responsePayload,
                action,
                resource);
    }
}
