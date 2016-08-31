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
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.java.JavaCodeGenerator;
import freemarker.template.TemplateModelException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.java.utils.TestGeneratedCodeHelper.*;

public class TestGeneratedModelCode {

    private static final String placeHolderName = "PlaceHolderRequest";
    private static final String placeHolderPath = "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/";

    private final ByteArrayOutputStream modelOutputStream;
    private String modelGeneratedCode;

    private final ByteArrayOutputStream requestOutputStream;
    private final ByteArrayOutputStream responseOutputStream;
    private final ByteArrayOutputStream ds3ClientOutputStream;
    private final ByteArrayOutputStream ds3ClientImplOutputStream;

    public TestGeneratedModelCode(
            final FileUtils fileUtils,
            final String modelName,
            final String path) throws IOException {
        this.modelOutputStream = setupOutputStream(fileUtils, getPathName(modelName, path));

        this.requestOutputStream = setupOutputStream(fileUtils, TestGeneratedCodeHelper.getPathName(placeHolderName, placeHolderPath, PathType.REQUEST));
        this.responseOutputStream = setupOutputStream(fileUtils, TestGeneratedCodeHelper.getPathName(placeHolderName, placeHolderPath, PathType.RESPONSE));
        this.ds3ClientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Ds3Client.java");
        this.ds3ClientImplOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Ds3ClientImpl.java");
    }

    private static String getPathName(final String modelName, final String pathName) {
        return pathName + modelName + ".java";
    }

    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws IOException, TemplateModelException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGeneratedModelCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."), new Ds3DocSpecEmptyImpl());

        modelGeneratedCode = new String(modelOutputStream.toByteArray());
    }

    public String getModelGeneratedCode() {
        return modelGeneratedCode;
    }
}
