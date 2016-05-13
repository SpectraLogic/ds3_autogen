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

import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures;
import com.spectralogic.ds3autogen.utils.TestFileUtilsImpl;
import freemarker.template.TemplateModelException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CCodeGeneratorSpectraS3InitRequests_Test {
    final static Logger LOG = LoggerFactory.getLogger(CCodeGeneratorSpectraS3InitRequests_Test.class);

    @Test
    public void testGenerateInitSpectraS3DeleteBucketRequest() throws TemplateModelException, IOException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.deleteBucketRequest());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_delete_bucket(const char* resource_id, const ds3_bool force) {" + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_DELETE, _build_path(\"/_rest_/bucket\", resource_id, NULL));" + "\n"
                + "    if (force) {"                                                                                                  + "\n"
                + "        _set_query_param((ds3_request*) request, \"force\", NULL);"                                                + "\n"
                + "    }"                                                                                                             + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                                + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3PutBucketRequest() throws TemplateModelException, IOException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.createBucketSpectraS3Request()); // requiredQueryParams
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_create_bucket(const char* resource_id, const char* name, const char* data_policy_id, const char* user_id) {" + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_POST, _build_path(\"/_rest_/bucket\", NULL, NULL));" + "\n"
                + "    if (name != NULL) {"                                                                                          + "\n"
                + "        _set_query_param((ds3_request*) request, \"name\", name);"                                                + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (data_policy_id != NULL) {"                                                                                + "\n"
                + "        _set_query_param((ds3_request*) request, \"data_policy_id\", data_policy_id);"                            + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (user_id != NULL) {"                                                                                       + "\n"
                + "        _set_query_param((ds3_request*) request, \"user_id\", user_id);"                                          + "\n"
                + "    }"                                                                                                            + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                               + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3GetSystemInfoRequest() throws TemplateModelException, IOException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.getSystemInformationRequest()); // requiredQueryParams
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_get_system_information(void) {" + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/_rest_/system_information\", NULL, NULL));" + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                               + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3GetBucketsRequest_WithOptionalQueryParams()
            throws IOException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.getBucketsRequest()); // requiredQueryParams
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_get_buckets(const char* resource_id, const char* data_policy_id, const ds3_bool last_page, const char* name, const int* page_length, const int* page_offset, const char* page_start_marker, const char* user_id) {" + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/_rest_/bucket\", NULL, NULL));" + "\n"
                + "    if (data_policy_id != NULL) {"                                                                                + "\n"
                + "        _set_query_param((ds3_request*) request, \"data_policy_id\", data_policy_id);"                            + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (last_page) {"                                                                                             + "\n"
                + "        _set_query_param((ds3_request*) request, \"last_page\", NULL);"                                           + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (name != NULL) {"                                                                                          + "\n"
                + "        _set_query_param((ds3_request*) request, \"name\", name);"                                                + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (page_length != NULL) {"                                                                                   + "\n"
                + "        char tmp_buff[32];"                                                                                       + "\n"
                + "        sprintf(tmp_buff, \"%d\", *page_length);"                                                                  + "\n"
                + "        _set_query_param((ds3_request*) request, \"page_length\", tmp_buff);"                                     + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (page_offset != NULL) {"                                                                                   + "\n"
                + "        char tmp_buff[32];"                                                                                       + "\n"
                + "        sprintf(tmp_buff, \"%d\", *page_offset);"                                                                  + "\n"
                + "        _set_query_param((ds3_request*) request, \"page_offset\", tmp_buff);"                                     + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (page_start_marker != NULL) {"                                                                             + "\n"
                + "        _set_query_param((ds3_request*) request, \"page_start_marker\", page_start_marker);"                      + "\n"
                + "    }"                                                                                                            + "\n"
                + "    if (user_id != NULL) {"                                                                                       + "\n"
                + "        _set_query_param((ds3_request*) request, \"user_id\", user_id);"                                          + "\n"
                + "    }"                                                                                                            + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                               + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3RequiredBoolQueryParamOmittedFromFunctionSig()
            throws IOException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.exampleRequestWithOptionalAndRequiredBooleanQueryParam());
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_example(const char* resource_id, const ds3_bool optional_bool) {"          + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_GET, _build_path(\"/_rest_/bucket\", NULL, NULL));" + "\n"
                + "    _set_query_param((ds3_request*) request, \"required_bool\", NULL);"                                          + "\n"
                + "\n"
                + "    if (optional_bool) {"                                                                                        + "\n"
                + "        _set_query_param((ds3_request*) request, \"optional_bool\", NULL);"                                      + "\n"
                + "    }"                                                                                                           + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                              + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }

    @Test
    public void testGenerateInitSpectraS3CreatePutJobRequest_WithRequiredOperationQueryParam_AndPriorityEnumOptionalQueryParam()
            throws IOException, TemplateModelException {
        final Map<String,Object> testMap = new HashMap<>();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.getRequestBulkPut()); // requiredQueryParams
        testMap.put("requestEntry", testRequest);

        final CCodeGenerator codeGenerator = new CCodeGenerator();
        final TestFileUtilsImpl fileUtils = new TestFileUtilsImpl();
        codeGenerator.processTemplate(testMap, "request-templates/InitRequest.ftl", fileUtils.getOutputStream());

        final ByteArrayOutputStream bstream = (ByteArrayOutputStream) fileUtils.getOutputStream();
        final String output = new String(bstream.toByteArray());

        final String expectedOutput = "ds3_request* init_create_put_job(const char* resource_id, const ds3_bool aggregating, const ds3_bool ignore_naming_conflicts, const uint64_t* max_upload_size, const char* name, const ds3_blob_store_task_priority* priority) {" + "\n"
                + "    struct _ds3_request* request = _common_request_init(HTTP_PUT, _build_path(\"/_rest_/bucket\", resource_id, NULL));"     + "\n"
                + "    _set_query_param((ds3_request*) request, \"operation\", \"START_BULK_PUT\");"                                           + "\n"
                + "\n"
                + "    if (aggregating) {"                                                                                                     + "\n"
                + "        _set_query_param((ds3_request*) request, \"aggregating\", NULL);"                                                   + "\n"
                + "    }"                                                                                                                      + "\n"
                + "    if (ignore_naming_conflicts) {"                                                                                         + "\n"
                + "        _set_query_param((ds3_request*) request, \"ignore_naming_conflicts\", NULL);"                                       + "\n"
                + "    }"                                                                                                                      + "\n"
                + "    if (max_upload_size != NULL) {"                                                                                         + "\n"
                + "        char tmp_buff[32];"                                                                                                 + "\n"
                + "        sprintf(tmp_buff, \"%llu\", (unsigned long long) *max_upload_size);"                                                + "\n"
                + "        _set_query_param((ds3_request*) request, \"max_upload_size\", tmp_buff);"                                           + "\n"
                + "    }"                                                                                                                      + "\n"
                + "    if (name != NULL) {"                                                                                                    + "\n"
                + "        _set_query_param((ds3_request*) request, \"name\", name);"                                                          + "\n"
                + "    }"                                                                                                                      + "\n"
                + "    if (priority != NULL) {"                                                                                                + "\n"
                + "        _set_query_param((ds3_request*) request, \"priority\", _get_ds3_blob_store_task_priority_str(*priority));"          + "\n"
                + "    }"                                                                                                                      + "\n"
                + "\n"
                + "    return (ds3_request*) request;"                                                                                         + "\n"
                + "}\n";
        assertEquals(expectedOutput, output);
    }
}
