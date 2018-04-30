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

package com.spectralogic.ds3autogen.go.utils;

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.go.GoCodeGenerator;
import freemarker.template.TemplateModelException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * Used to generate and capture Go code for a single command. Used in functional testing.
 */
public class GoTestCodeUtil {

    private final static String BASE_PATH = "./ds3/";
    private final static String COMMAND_PATH = BASE_PATH + "models/";

    private final ByteArrayOutputStream requestOutputStream;
    private final ByteArrayOutputStream responseOutputStream;
    private final ImmutableMap<HttpVerb, ByteArrayOutputStream> clientOutputStreams;
    private ByteArrayOutputStream typeOutputStream;

    private String requestCode;
    private String responseCode;
    private String typeCode;
    private ImmutableMap<HttpVerb, String> clientCode;

    /**
     * Constructor for generating Go files for a command with NO response payload
     */
    public GoTestCodeUtil(final FileUtils fileUtils) throws IOException {
        this.requestOutputStream = setupOutputStream(fileUtils, COMMAND_PATH + "requests.go");
        this.responseOutputStream = setupOutputStream(fileUtils, COMMAND_PATH + "responses.go");
        this.typeOutputStream = setupOutputStream(fileUtils, COMMAND_PATH + "responseModels.go");
        this.clientOutputStreams = setupClientStreams(fileUtils);
    }

    /**
     * Sets up output streams to capture all of the generated clients
     */
    private static ImmutableMap<HttpVerb, ByteArrayOutputStream> setupClientStreams(final FileUtils fileUtils) throws IOException {
        final ImmutableMap.Builder<HttpVerb, ByteArrayOutputStream> builder = ImmutableMap.builder();
        for (final HttpVerb httpVerb : HttpVerb.values()) {
            final String name = GoCodeGenerator.getClientFileName(httpVerb);
            builder.put(httpVerb, setupOutputStream(fileUtils, BASE_PATH + name + ".go"));
        }
        return builder.build();
    }

    /**
     * Sets up an output stream to capture the content of generated code that would otherwise
     * have been written to a file
     */
    private static ByteArrayOutputStream setupOutputStream(
            final FileUtils fileUtils,
            final String pathName) throws IOException {
        final Path path = Paths.get(pathName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(path)).thenReturn(outputStream);
        return outputStream;
    }

    /**
     * Generates the Go code associated with an input file containing a contract.
     */
    public void generateCode(final FileUtils fileUtils, final String inputFileName) throws IOException, TemplateModelException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(GoTestCodeUtil.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new GoCodeGenerator();
        final Ds3DocSpec docSpec = new Ds3DocSpecEmptyImpl();

        // Generate the Go Code from the input file
        codeGenerator.generate(spec, fileUtils, Paths.get("."), docSpec);

        // Capture the generated command files
        requestCode = new String(requestOutputStream.toByteArray());
        responseCode = new String(responseOutputStream.toByteArray());

        // Capture the generate response payload type if one exists
        if (typeOutputStream != null) {
            typeCode = new String(typeOutputStream.toByteArray());
        }

        // Capture the generated client files and associate them with the HttpVerb of
        // the commands contained within the file
        final ImmutableMap.Builder<HttpVerb, String> builder = ImmutableMap.builder();
        for (final Map.Entry<HttpVerb, ByteArrayOutputStream> entry : clientOutputStreams.entrySet()) {
            builder.put(entry.getKey(), new String(entry.getValue().toByteArray()));
        }
        clientCode = builder.build();
    }

    public String getRequestCode() {
        return requestCode;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getClientCode(final HttpVerb httpVerb) {
        return clientCode.get(httpVerb);
    }
}
