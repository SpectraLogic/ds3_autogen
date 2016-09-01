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

package com.spectralogic.ds3autogen.python.utils;

import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.NameMapper;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3DocSpecParser;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecParserImpl;
import com.spectralogic.ds3autogen.python.PythonCodeGenerator;
import freemarker.template.TemplateModelException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;

/**
 * Used to generate python code in testing environment
 */
public class TestPythonGeneratedCode {

    final static String BASE_PATH = "./ds3/";

    private final ByteArrayOutputStream ds3OutputStream;
    private String ds3Code;

    public TestPythonGeneratedCode(
            final FileUtils fileUtils) throws IOException {
        this.ds3OutputStream = setupOutputStream(fileUtils, BASE_PATH + "ds3.py");
    }

    /**
     * Generates the python code associated with an input file. This utilizes the
     * default Ds3DocSpec.
     * Captured code: ds3.py
     */
    public void generateCode(final FileUtils fileUtils, final String inputFileName) throws IOException, TemplateModelException {
        final Ds3DocSpecParser docSpecParser = new Ds3DocSpecParserImpl(new NameMapper());
        generateCode(fileUtils, inputFileName, docSpecParser.getDocSpec());
    }

        /**
         * Generates the python code associated with an input file. This allows
         * for a Ds3DocSpec to be specified
         * Captured code: ds3.py
         */
    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName,
            final Ds3DocSpec docSpec) throws IOException, TemplateModelException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestPythonGeneratedCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new PythonCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."), docSpec);

        ds3Code = new String(ds3OutputStream.toByteArray());
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

    public String getDs3Code() {
        return ds3Code;
    }
}
