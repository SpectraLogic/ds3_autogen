/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

import static com.spectralogic.ds3autogen.java.utils.TestGeneratedCodeHelper.setupOutputStream;

/**
 * Used to generate code for a Ds3Request that contains a response payload with a
 * component type. This is used during testing to catch the generated encapsulating
 * type used to describe the previous component type.
 */
public class TestGeneratedComponentResponseCode extends TestGeneratedCode {

    private final ByteArrayOutputStream encapsulatingTypeOutputStream;
    private String encapsulatingTypeGeneratedCode;

    public TestGeneratedComponentResponseCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path,
            final String encapsulatingType) throws IOException {
        super(fileUtils, requestName, path);
        this.encapsulatingTypeOutputStream = setupOutputStream(fileUtils, encapsulatingType);
    }

    @Override
    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws IOException, TemplateModelException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGeneratedCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."), new Ds3DocSpecEmptyImpl());

        requestGeneratedCode = new String(requestOutputStream.toByteArray());
        responseGeneratedCode = new String(responseOutputStream.toByteArray());

        encapsulatingTypeGeneratedCode = new String(encapsulatingTypeOutputStream.toByteArray());

        ds3ClientGeneratedCode = new String(ds3ClientOutputStream.toByteArray());
        ds3ClientImplGeneratedCode = new String(ds3ClientImplOutputStream.toByteArray());
    }

    public String getEncapsulatingTypeGeneratedCode() {
        return encapsulatingTypeGeneratedCode;
    }
}
