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

package com.spectralogic.ds3autogen.java.utils;

import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.java.JavaCodeGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;

public class TestGeneratedCode {
    private final static String CLIENT_PATH = "./ds3-sdk/src/main/java/com/spectralogic/ds3client/";

    private final ByteArrayOutputStream requestOutputStream;
    private final ByteArrayOutputStream responseOutputStream;
    private final ByteArrayOutputStream ds3ClientOutputStream;
    private final ByteArrayOutputStream ds3ClientImplOutputStream;
    private String requestGeneratedCode;
    private String responseGeneratedCode;
    private String ds3ClientGeneratedCode;
    private String ds3ClientImplGeneratedCode;

    private enum PathType { REQUEST, RESPONSE }

    public TestGeneratedCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path) throws IOException {
        this.requestOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.REQUEST));
        this.responseOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.RESPONSE));
        this.ds3ClientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Ds3Client.java");
        this.ds3ClientImplOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Ds3ClientImpl.java");
    }

    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws IOException, ParserException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGeneratedCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        requestGeneratedCode = new String(requestOutputStream.toByteArray());
        responseGeneratedCode = new String(responseOutputStream.toByteArray());
        ds3ClientGeneratedCode = new String(ds3ClientOutputStream.toByteArray());
        ds3ClientImplGeneratedCode = new String(ds3ClientImplOutputStream.toByteArray());
    }

    private static ByteArrayOutputStream setupOutputStream(
            final FileUtils fileUtils,
            final String pathName) throws IOException {
        final Path path = Paths.get(pathName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(path)).thenReturn(outputStream);
        return outputStream;
    }

    private static String getPathName(final String requestName, final String path, final PathType pathType) {
        final StringBuilder builder = new StringBuilder();
        builder.append(path);
        switch (pathType) {
            case REQUEST:
                builder.append(requestName);
                break;
            case RESPONSE:
                builder.append(requestName.replace("Request", "Response"));
        }
        builder.append(".java");
        return builder.toString();
    }

    public String getRequestGeneratedCode() {
        return requestGeneratedCode;
    }

    public String getResponseGeneratedCode() {
        return responseGeneratedCode;
    }

    public String getDs3ClientGeneratedCode() {
        return ds3ClientGeneratedCode;
    }

    public String getDs3ClientImplGeneratedCode() {
        return ds3ClientImplGeneratedCode;
    }
}
