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

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import com.spectralogic.ds3autogen.testutil.logging.FileTypeToLog;
import com.spectralogic.ds3autogen.testutil.logging.GeneratedCodeLogger;
import freemarker.template.TemplateModelException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.regex.Pattern;

import static com.spectralogic.ds3autogen.java.utils.JavaDocGenerator.toConstructorDocs;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class JavaFunctionalDocs_Test {

    private final static Logger LOG = LoggerFactory.getLogger(JavaFunctionalDocs_Test.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.REQUEST, LOG);

    @Test
    public void getBucketRequest_Test() throws IOException, TemplateModelException {
        final String requestName = "GetBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGeneratedCode codeGenerator = new TestGeneratedCode(
                fileUtils,
                requestName,
                "./ds3-sdk/src/main/java/com/spectralogic/ds3client/commands/");

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

        codeGenerator.generateCode(fileUtils, "/input/getBucketRequestHandler.xml", docSpec);

        //Generate Request code
        final String requestCode = codeGenerator.getRequestGeneratedCode();
        CODE_LOGGER.logFile(requestCode, FileTypeToLog.REQUEST);

        assertTrue(requestCode.contains(toConstructorDocs(requestName, ImmutableList.of("Delimiter"), docSpec, 1)));
        assertTrue(requestCode.contains(toConstructorDocs(requestName, ImmutableList.of("Marker"), docSpec, 1)));
        assertTrue(requestCode.contains(toConstructorDocs(requestName, ImmutableList.of("MaxKeys"), docSpec, 1)));
        assertTrue(requestCode.contains(toConstructorDocs(requestName, ImmutableList.of("Prefix"), docSpec, 1)));
        assertTrue(requestCode.contains(toConstructorDocs(requestName, ImmutableList.of("BucketName"), docSpec, 1)));

        //Generate Client code
        final String ds3ClientCode = codeGenerator.getDs3ClientGeneratedCode();
        CODE_LOGGER.logFile(ds3ClientCode, FileTypeToLog.CLIENT);

        final Pattern expectedCommandDoc = Pattern.compile("\\s+/\\*\\*" +
                        "\\s+\\* This is how you use the request" +
                        "\\s+\\*/" +
                        "\\s+GetBucketResponse getBucket\\(final GetBucketRequest request\\)",
                Pattern.MULTILINE | Pattern.UNIX_LINES);

        assertTrue(expectedCommandDoc.matcher(ds3ClientCode).find());
    }
}
