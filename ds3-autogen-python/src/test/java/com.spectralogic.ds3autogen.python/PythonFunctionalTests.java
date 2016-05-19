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

package com.spectralogic.ds3autogen.python;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.HttpVerb;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.python.utils.TestPythonGeneratedCode;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static com.spectralogic.ds3autogen.python.utils.FunctionalTestHelper.hasOperation;
import static com.spectralogic.ds3autogen.python.utils.FunctionalTestHelper.hasRequestHandler;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PythonFunctionalTests {

    private final static Logger LOG = LoggerFactory.getLogger(PythonFunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.REQUEST, LOG);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void simpleRequest() throws IOException, ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, TemplateModelException {
        final String requestName = "SimpleTestRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/requests/simpleRequest.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.REQUEST);

        final ImmutableList<String> reqArgs = ImmutableList.of("bucket_name");
        final ImmutableList<String> optArgs = ImmutableList.of("delimiter", "marker", "max_keys", "prefix");
        final ImmutableList<String> voidArgs = ImmutableList.of("test_void_param");

        hasRequestHandler(requestName, HttpVerb.GET, reqArgs, optArgs, voidArgs, ds3Code);
        hasOperation(Operation.START_BULK_PUT, ds3Code);
    }

    @Test
    public void deleteJobCreatedNotification() throws IOException, ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, TemplateModelException {
        final String requestName = "DeleteJobCreatedNotificationRegistrationSpectraS3Request";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/requests/deleteJobCreatedNotificationRegistrationRequest.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.REQUEST);

        final ImmutableList<String> reqArgs = ImmutableList.of("notification_id");

        hasRequestHandler(requestName, HttpVerb.DELETE, reqArgs, null, null, ds3Code);
        assertTrue(ds3Code.contains("self.path = '/_rest_/job_created_notification_registration/' + notification_id"));
    }
}
