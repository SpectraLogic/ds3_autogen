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
import com.spectralogic.ds3autogen.python.model.type.TypeAttribute;
import com.spectralogic.ds3autogen.python.model.type.TypeContent;
import com.spectralogic.ds3autogen.python.model.type.TypeElement;
import com.spectralogic.ds3autogen.python.model.type.TypeElementList;
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

import static com.spectralogic.ds3autogen.python.utils.FunctionalTestHelper.*;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class PythonFunctionalTests {

    private final static Logger LOG = LoggerFactory.getLogger(PythonFunctionalTests.class);
    private final static GeneratedCodeLogger CODE_LOGGER = new GeneratedCodeLogger(FileTypeToLog.ALL, LOG);

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

    @Test
    public void simpleType() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String modelDescriptorName = "TestElementsType";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/types/simpleType.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(ds3Code));

        final ImmutableList<TypeContent> modelContents = ImmutableList.of(
                new TypeAttribute("InCache"),
                new TypeElement("MyBucket", "BucketDetails"),
                new TypeElement("ByteOffset", "None"),
                new TypeElementList("MyBucketList", "None", "BucketDetails"),
                new TypeElementList("Pool", "Pools", "None"));

        hasModelDescriptor(modelDescriptorName, modelContents, ds3Code);
    }

    @Test
    public void jobsApiBean() throws IOException, ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, TemplateModelException {
        final String modelDescriptorName = "JobList";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/types/jobsApiBean.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(ds3Code));

        final ImmutableList<TypeContent> modelContents = ImmutableList.of(
                new TypeElementList("Job", "Jobs", "Job"));

        hasModelDescriptor(modelDescriptorName, modelContents, ds3Code);
    }

    @Test
    public void listMultiPartUploadApiBean() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String modelDescriptorName = "ListMultiPartUploadsResult";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/types/listMultiPartUploadApiBean.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(ds3Code));

        final ImmutableList<TypeContent> modelContents = ImmutableList.of(
                new TypeElement("Bucket", "None"),
                new TypeElement("Delimiter", "None"),
                new TypeElement("KeyMarker", "None"),
                new TypeElement("MaxUploads", "None"),
                new TypeElement("NextKeyMarker", "None"),
                new TypeElement("NextUploadIdMarker", "None"),
                new TypeElement("Prefix", "None"),
                new TypeElement("IsTruncated", "None"),
                new TypeElement("UploadIdMarker", "None"),
                new TypeElementList("CommonPrefixes", "None", "CommonPrefixes"),
                new TypeElementList("Upload", "None", "None"));

        hasModelDescriptor(modelDescriptorName, modelContents, ds3Code);
    }

    @Test
    public void bucketObjectsApiBean() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String modelDescriptorName = "ListBucketResult";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/types/bucketObjectsApiBean.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(ds3Code));

        final ImmutableList<TypeContent> modelContents = ImmutableList.of(
                new TypeElement("CreationDate", "None"),
                new TypeElement("Delimiter", "None"),
                new TypeElement("Marker", "None"),
                new TypeElement("MaxKeys", "None"),
                new TypeElement("Name", "None"),
                new TypeElement("NextMarker", "None"),
                new TypeElement("Prefix", "None"),
                new TypeElement("IsTruncated", "None"),
                new TypeElementList("CommonPrefixes", "None", "CommonPrefixes"),
                new TypeElementList("Contents", "None", "Contents"));

        hasModelDescriptor(modelDescriptorName, modelContents, ds3Code);
    }

    @Test
    public void namedDetailedTapeList() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String modelDescriptorName = "NamedDetailedTapeList";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/types/namedDetailedTapeList.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.MODEL_PARSERS);
        assertTrue(hasContent(ds3Code));

        final ImmutableList<TypeContent> modelContents = ImmutableList.of(
                new TypeElementList("Tape", "None", "NamedDetailedTape"));

        hasModelDescriptor(modelDescriptorName, modelContents, ds3Code);
    }

    @Test
    public void headBucketRequest() throws ResponseTypeNotFoundException, TemplateModelException, ParserException, TypeRenamingConflictException, IOException {
        final String requestName = "HeadBucketRequest";
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestPythonGeneratedCode codeGenerator = new TestPythonGeneratedCode(fileUtils);

        codeGenerator.generateCode(fileUtils, "/input/requests/headBucketRequest.xml");
        final String ds3Code = codeGenerator.getDs3Code();

        CODE_LOGGER.logFile(ds3Code, FileTypeToLog.REQUEST);

        final ImmutableList<String> reqArgs = ImmutableList.of("bucket_name");

        hasRequestHandler(requestName, HttpVerb.GET, reqArgs, ImmutableList.of(), ImmutableList.of(), ds3Code);
        hasOperation(Operation.START_BULK_PUT, ds3Code);

        assertTrue(ds3Code.contains("self.__check_status_codes__([200, 403, 404])"));
        assertTrue(ds3Code.contains("self.status_code = self.response.status\n" +
                "    if self.response.status == 200:\n" +
                "      self.result = HeadBucketStatus.EXISTS\n" +
                "    elif self.response.status == 403:\n" +
                "      self.result = HeadBucketStatus.NOTAUTHORIZED\n" +
                "    elif self.response.status == 404:\n" +
                "      self.result = HeadBucketStatus.DOESNTEXIST\n" +
                "    else:\n" +
                "      self.result = HeadBucketStatus.UNKNOWN"));

        assertTrue(ds3Code.contains("class HeadBucketStatus(object):"));
    }
}
