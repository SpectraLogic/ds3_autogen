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

package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import freemarker.template.TemplateModelException;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CCodeGeneratorSpectraS3InitRequests_Test {

    @Test
    public void testGenerateInitSpectraS3DeleteBucketRequest() throws TemplateModelException, IOException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.deleteBucketRequest(), new Ds3DocSpecEmptyImpl());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* ds3_init_delete_bucket(const char *const resource_id) {" + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_DELETE, _build_path(\"/_rest_/bucket/\", resource_id, NULL));" + "\n"
                + "    return (ds3_request*) request;"                                                                                        + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3PutBucketRequest() throws TemplateModelException, IOException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.createBucketSpectraS3Request(), new Ds3DocSpecEmptyImpl());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* ds3_init_create_bucket(const char* name) {"                                                           + "\n"
                                    + "    struct _ds3_request* request = _common_request_init(HTTP_POST, _build_path(\"/_rest_/bucket/\", NULL, NULL));" + "\n"
                                    + "    if (name != NULL) {"                                                                                           + "\n"
                                    + "        _set_query_param((ds3_request*) request, \"name\", name);"                                                 + "\n"
                                    + "    }"                                                                                                             + "\n"
                                    + "    return (ds3_request*) request;"                                                                                + "\n"
                                    + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3GetSystemInfoRequest() throws TemplateModelException, IOException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.getSystemInformationRequest(), new Ds3DocSpecEmptyImpl());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* ds3_init_get_system_information(void) {"                                                     + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/_rest_/system_information/\", NULL, NULL));" + "\n"
                + "    return (ds3_request*) request;"                                                                                           + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3GetBucketsRequest_WithOptionalQueryParams()
            throws IOException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.getBucketsRequest(), new Ds3DocSpecEmptyImpl());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* ds3_init_get_buckets(void) {"                                                    + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/_rest_/bucket/\", NULL, NULL));" + "\n"
                + "    return (ds3_request*) request;"                                                                               + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3RequiredBoolQueryParamOmittedFromFunctionSig()
            throws IOException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.exampleRequestWithOptionalAndRequiredBooleanQueryParam(), new Ds3DocSpecEmptyImpl());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* ds3_init_example(void) {"                                                        + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/_rest_/bucket/\", NULL, NULL));" + "\n"
                + "    _set_query_param((ds3_request*) request, \"required_bool\", NULL);"                                           + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                               + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3CreatePutJobRequest_WithRequiredOperationQueryParam_AndPriorityEnumOptionalQueryParam()
            throws IOException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.getRequestBulkPut(), new Ds3DocSpecEmptyImpl());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* ds3_init_create_put_job(const char *const resource_id) {"                                        + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_PUT, _build_path(\"/_rest_/bucket/\", resource_id, NULL));"    + "\n"
                + "    _set_query_param((ds3_request*) request, \"operation\", \"START_BULK_PUT\");"                                           + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                                         + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }
}
