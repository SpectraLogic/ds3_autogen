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

package com.spectralogic.ds3autogen.python3;

import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.python3.utils.TestPython3GeneratedCode;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class Python3FunctionalTests {

    private final static Logger LOG = LoggerFactory.getLogger(Python3FunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.REQUEST, LOG);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void simpleRequest() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPython3GeneratedCode codeGenerator = new TestPython3GeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/requests/simpleRequest.xml");
        final String ds3Code = codeGenerator.getDs3Code();
        assertThat(ds3Code, is(notNullValue()));

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.REQUEST);

        //test template imports from python 2 module
        assertTrue(ds3Code.contains("def createClientFromEnv():"));

        //test imports
        assertTrue(ds3Code.contains("from .ds3network import *"));
        assertFalse(ds3Code.contains("from ds3network import *"));

        //test abstract request
        assertTrue(ds3Code.contains("class AbstractRequest(object, metaclass=ABCMeta):"));
        assertFalse(ds3Code.contains("class AbstractRequest(object):"));

        //test abstract response
        assertTrue(ds3Code.contains("class AbstractResponse(object, metaclass=ABCMeta):"));
        assertFalse(ds3Code.contains("class AbstractResponse(object):"));

        assertTrue(ds3Code.contains("if header[0].lower() == key.lower():"));
        assertFalse(ds3Code.contains("if header[0] == key:"));
    }

    @Test
    public void putObjectRequestTest() throws IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPython3GeneratedCode codeGenerator = new TestPython3GeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/requests/putObject.xml");
        final String ds3Code = codeGenerator.getDs3Code();
        assertThat(ds3Code, is(notNullValue()));

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.REQUEST);

        assertTrue(ds3Code.contains("for key, val in headers.items():"));
        assertFalse(ds3Code.contains("for key, val in headers.iteritems():"));
    }
}
