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
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.java.JavaCodeGenerator;
import freemarker.template.TemplateModelException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.java.utils.TestGeneratedCodeHelper.*;

public class TestGeneratedCode {

    protected final ByteArrayOutputStream requestOutputStream;
    protected final ByteArrayOutputStream responseOutputStream;
    protected final ByteArrayOutputStream ds3ClientOutputStream;
    protected final ByteArrayOutputStream ds3ClientImplOutputStream;
    protected String requestGeneratedCode;
    protected String responseGeneratedCode;
    protected String ds3ClientGeneratedCode;
    protected String ds3ClientImplGeneratedCode;

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
            final String inputFileName) throws IOException, TemplateModelException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGeneratedCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."), null);

        requestGeneratedCode = new String(requestOutputStream.toByteArray());
        responseGeneratedCode = new String(responseOutputStream.toByteArray());
        ds3ClientGeneratedCode = new String(ds3ClientOutputStream.toByteArray());
        ds3ClientImplGeneratedCode = new String(ds3ClientImplOutputStream.toByteArray());
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
