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

package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class CCodeGenerator_Test {
    private static final Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    @Test
    public void testSingleDeleteRequestHandler() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("/tmp/ds3_c_sdk/src/requests/DeleteBucket.c");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/SingleRequestHandler.xml"));
        final CodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("/tmp"));

        LOG.info("Generated code:\n" + new String(outputStream.toByteArray()));
    }

    @Test
    public void testSingleTypeEnumConstant() throws IOException, ParserException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final Path requestPath = Paths.get("/tmp/ds3_c_sdk/src/types/job_status.c");
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(requestPath)).thenReturn(outputStream);

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypeEnumConstant.xml"));
        final CodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("/tmp"));

        LOG.info("Generated code:\n" + new String(outputStream.toByteArray()));
    }
}
