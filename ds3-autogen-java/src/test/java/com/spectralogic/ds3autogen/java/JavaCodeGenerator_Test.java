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

package com.spectralogic.ds3autogen.java;

import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.java.utils.TestFileUtilImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JavaCodeGenerator_Test {

    private static final Logger LOG = LoggerFactory.getLogger(JavaCodeGenerator_Test.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void singleRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/GetObjectRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/singleRequestHandler.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        LOG.info("Generated code:\n" + new String(outputStream.toByteArray()));

    }

    @Test
    public void getBucket() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/GetBucketRequestHandler.java");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);

        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/singleRequestMissingParamTypes.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        LOG.info("Generated code:\n" + new String(outputStream.toByteArray()));
    }

    @Test
    public void wholeXmlDoc() throws IOException, ParserException {
        final FileUtils fileUtils = new TestFileUtilImpl(tempFolder);
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(JavaCodeGenerator_Test.class.getResourceAsStream("/input/fullXml.xml"));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

    }
}
