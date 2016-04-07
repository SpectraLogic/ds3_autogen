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

package com.spectralogic.ds3autogen.net.utils;

import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
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

    protected final ByteArrayOutputStream requestOutputStream;
    protected final ByteArrayOutputStream responseOutputStream;
    protected final ByteArrayOutputStream clientOutputStream;
    protected final ByteArrayOutputStream idsClientOutputStream;
    protected String requestCode;
    protected String responseCode;
    protected String clientCode;
    protected String idsClientCode;

    public enum PathType { REQUEST, RESPONSE }

    public TestGenerateCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path) throws IOException {
        this.requestOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.REQUEST));
        this.responseOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.RESPONSE));
        this.clientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Ds3Client.cs");
        this.idsClientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "IDs3Client.cs");
    }

    /**
     * Generates the .net code associated with an input file. This captures the
     * Request, Client, and IDsClient code
     */
    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGenerateCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new NetCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        requestCode = new String(requestOutputStream.toByteArray());
        responseCode = new String(responseOutputStream.toByteArray());
        clientCode = new String(clientOutputStream.toByteArray());
        idsClientCode = new String(idsClientOutputStream.toByteArray());
    }

    /**
     * Sets up an output stream to capture the content of generated code that would otherwise
     * have been written to a file
     */
    protected static ByteArrayOutputStream setupOutputStream(
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
}
