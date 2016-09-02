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

import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import com.spectralogic.ds3autogen.python.utils.TestPythonGeneratedCode;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import freemarker.template.TemplateModelException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PythonFunctionalDocs_Test {

    private final static Logger LOG = LoggerFactory.getLogger(PythonFunctionalDocs_Test.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.REQUEST, LOG);

    @Test
    public void getBucketRequest_Test() throws IOException, TemplateModelException {
        final String requestName = "GetBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        final Ds3DocSpec docSpec = new Ds3DocSpecImpl(
                ImmutableMap.of(
                        requestName, "This is how you use the request"
                ),
                ImmutableMap.of(
                        "Delimiter", "This is how you use delimiter",
                        "Marker", "This is how you use marker",
                        "MaxKeys", "This is how you use max keys",
                        "Prefix", "This is how you use prefix",
                        "BucketName", "This is how you use bucket name"));

        codeGenerator.generateCode(fileUtils, "/input/requests/getBucketRequest.xml", docSpec);

        //Test Request code
        final String ds3Code = codeGenerator.getDs3Code();
        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.REQUEST);

        final Pattern expectedConstructorDocs = Pattern.compile("'''\\s+" +
                        "This is how you use the request\\s+" +
                        "`bucket_name` This is how you use bucket name\\s+" +
                        "`delimiter` This is how you use delimiter\\s+" +
                        "`marker` This is how you use marker\\s+" +
                        "`max_keys` This is how you use max keys\\s+" +
                        "`prefix` This is how you use prefix\\s+" +
                        "'''\\s+" +
                        "def __init__\\(self, bucket_name, delimiter=None, marker=None, max_keys=None, prefix=None\\):",
                Pattern.MULTILINE | Pattern.UNIX_LINES);

        assertTrue(expectedConstructorDocs.matcher(ds3Code).find());

        //Test Client code
        final Pattern expectedCommandDoc = Pattern.compile("'''\\s+" +
                "This is how you use the request\\s+" +
                "'''\\s+" +
                "def get_bucket\\(self, request\\):",
                Pattern.MULTILINE | Pattern.UNIX_LINES);

        assertTrue(expectedCommandDoc.matcher(ds3Code).find());
    }
}
