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

package com.spectralogic.ds3autogen.net.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecParserImpl;
import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.net.NetCodeGenerator;
import freemarker.template.TemplateModelException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;

/**
 * Used to generate .net request code
 */
public class TestGenerateCode {

    final static String CLIENT_PATH = "./Ds3/";
    final static String PARSER_PATH = CLIENT_PATH + "/ResponseParsers/";

    protected final ByteArrayOutputStream requestOutputStream;
    protected final ByteArrayOutputStream responseOutputStream;
    protected final ByteArrayOutputStream clientOutputStream;
    protected final ByteArrayOutputStream idsClientOutputStream;
    protected final ByteArrayOutputStream typeParserOutputStream;
    protected final ByteArrayOutputStream parserOutputStream;
    protected ByteArrayOutputStream responseTypeOutputStream;
    protected ImmutableList<ByteArrayOutputStream> ignorableFiles; /** List of generated files to ignore for this test */
    protected String typeCode;
    protected String requestCode;
    protected String responseCode;
    protected String clientCode;
    protected String idsClientCode;
    protected String typeParser; /** Type/model parser code */
    protected String parserCode; /** Request handler parser code */

    public enum PathType { REQUEST, RESPONSE }

    public TestGenerateCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path) throws IOException {
        this.requestOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.REQUEST));
        this.responseOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.RESPONSE));
        this.clientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Ds3Client.cs");
        this.idsClientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "IDs3Client.cs");
        this.typeParserOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "ResponseParsers/ModelParsers.cs");
        this.parserOutputStream = setupOutputStream(fileUtils, PARSER_PATH + requestName.replace("Request", "ResponseParser") + ".cs");
    }

    public TestGenerateCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path,
            final String responseType) throws IOException {
        this(fileUtils, requestName, path);
        this.responseTypeOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Models/" + responseType + ".cs");
    }

    /**
     * Captures generated files that are ignorable during testing
     */
    public void withIgnorableFiles(final FileUtils fileUtils, final ImmutableList<String> ignorableFileNames) throws IOException {
        final ImmutableList.Builder<ByteArrayOutputStream> builder = ImmutableList.builder();
        for (final String pathName : ignorableFileNames) {
            builder.add(setupOutputStream(fileUtils, pathName));
        }
        this.ignorableFiles = builder.build();
    }

    /**
     * Generates the .net code associated with an input file. This captures the
     * Request, Client, and IDsClient code. This utilizes the default Ds3DocSpec
     */
    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws IOException, TemplateModelException {
        final Ds3DocSpecParser docSpecParser = new Ds3DocSpecParserImpl(new NameMapper());
        generateCode(fileUtils, inputFileName, docSpecParser.getDocSpec());
    }

    /**
     * Generates the .net code associated with an input file. THis captures the
     * Request, Client and IDsClient code. This allows for a Ds3DocSpec parser to
     * be specified
     */
    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName,
            final Ds3DocSpec docSpec) throws IOException, TemplateModelException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGenerateCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new NetCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."), docSpec);

        requestCode = new String(requestOutputStream.toByteArray());
        responseCode = new String(responseOutputStream.toByteArray());
        clientCode = new String(clientOutputStream.toByteArray());
        idsClientCode = new String(idsClientOutputStream.toByteArray());
        typeParser = new String(typeParserOutputStream.toByteArray());
        parserCode = new String(parserOutputStream.toByteArray());

        if (responseTypeOutputStream != null) {
            typeCode = new String(responseTypeOutputStream.toByteArray());
        }
    }

    /**
     * Sets up an output stream to capture the content of generated code that would otherwise
     * have been written to a file
     */
    public static ByteArrayOutputStream setupOutputStream(
            final FileUtils fileUtils,
            final String pathName) throws IOException {
        final Path path = Paths.get(pathName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(path)).thenReturn(outputStream);
        return outputStream;
    }

    /**
     * Retrieves the full path name of a given request/response file located within the specified path
     */
    protected static String getPathName(final String requestName, final String path, final PathType pathType) {
        final StringBuilder builder = new StringBuilder();
        builder.append(path);
        switch (pathType) {
            case REQUEST:
                builder.append(requestName);
                break;
            case RESPONSE:
                builder.append(requestName.replace("Request", "Response"));
                break;
        }
        builder.append(".cs");
        return builder.toString();
    }

    public String getRequestCode() {
        return this.requestCode;
    }

    public String getResponseCode() {
        return this.responseCode;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public String getIdsClientCode() {
        return this.idsClientCode;
    }

    public String getTypeParser() {
        return this.typeParser;
    }

    public String getParserCode() {
        return this.parserCode;
    }

    public String getTypeCode() {
        return this.typeCode;
    }
}
