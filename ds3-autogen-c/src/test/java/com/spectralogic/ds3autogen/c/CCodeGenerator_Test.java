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
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class CCodeGenerator_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGenerator_Test.class);

    @Test
    public void testSingleDeleteRequestHandler() throws IOException, ParserException, ResponseTypeNotFoundException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/SingleRequestHandler.xml"));
        final CodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("/tmp"));

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);
    }

    @Test
    public void testSingleTypeEnumConstant() throws IOException, ParserException, ResponseTypeNotFoundException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypeEnumConstant.xml"));
        final CodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, null);

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);
        assertTrue(output.contains("typedef enum {"));
        assertTrue(output.contains("    IN_PROGRESS,"));
        assertTrue(output.contains("    COMPLETED,"));
        assertTrue(output.contains("    CANCELED"));
        assertFalse(output.contains("    CANCELED,")); // verify no trailing comma
        assertTrue(output.contains("}ds3_job_status;"));
    }

    @Test
    public void testSingleTypeEnumConstantMatcher() throws IOException, ParserException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypeEnumConstant.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, null);

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);
        assertTrue(output.contains("static ds3_job_status _match_ds3_job_status(const ds3_log* log, const xmlChar* text) {"));
        assertTrue(output.contains("    if (xmlStrcmp(text, (const xmlChar*) \"IN_PROGRESS\") == 0) {"));
        assertTrue(output.contains("        return IN_PROGRESS;"));
        assertTrue(output.contains("    } else if (xmlStrcmp(text, (const xmlChar*) \"COMPLETED\") == 0) {"));
        assertTrue(output.contains("        return COMPLETED;"));
        assertTrue(output.contains("    } else if (xmlStrcmp(text, (const xmlChar*) \"CANCELED\") == 0) {"));
        assertTrue(output.contains("        return CANCELED;"));
        assertTrue(output.contains("    }"));
        assertTrue(output.contains("}"));
    }

    @Test
    public void testSingleTypeElement() throws IOException, ParserException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypeElement.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, null);

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("typedef enum {"));
        assertTrue(output.contains("    ds3_str* api_version;"));
        assertTrue(output.contains("    ds3_bool backend_activated;"));
        assertTrue(output.contains("    ds3_build_information* build_information;"));
        assertTrue(output.contains("    ds3_str* serial_number;"));
        assertTrue(output.contains("}ds3_get_system_information_response;"));
    }

    @Test
    public void testSingleFreeTypeElementPrototype() throws IOException, ParserException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypeElement.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, null);

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("void ds3_free_get_system_information(ds3_get_system_information_response* response_data);"));
    }

    @Test
    public void testSingleFreeTypeElement() throws IOException, ParserException {
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();

        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(CCodeGenerator_Test.class.getResourceAsStream("/input/TypeElement.xml"));
        final CCodeGenerator codeGenerator = new CCodeGenerator();

        codeGenerator.generate(spec, fileUtils, null);

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        LOG.info("Generated code:\n" + output);

        assertTrue(output.contains("void ds3_free_get_system_information(ds3_get_system_information_response* response_data) {"));
        assertTrue(output.contains("    if (response_data == NULL) {"));
        assertTrue(output.contains("        return;"));
        assertTrue(output.contains("    }"));
        assertTrue(output.contains("    ds3_str_free(response_data->api_version);"));
        assertTrue(output.contains("    ds3_free_build_information(response_data->build_information);"));
        assertTrue(output.contains("    ds3_str_free(response_data->serial_number);"));
        assertTrue(output.contains("}"));
    }
}
