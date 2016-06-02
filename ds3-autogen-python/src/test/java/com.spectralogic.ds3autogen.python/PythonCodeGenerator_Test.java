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
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.python.generators.request.BaseRequestGenerator;
import com.spectralogic.ds3autogen.python.generators.response.BaseResponseGenerator;
import com.spectralogic.ds3autogen.python.generators.response.HeadResponseGenerator;
import com.spectralogic.ds3autogen.python.generators.type.BaseTypeGenerator;
import com.spectralogic.ds3autogen.python.model.client.BaseClient;
import com.spectralogic.ds3autogen.python.model.request.BaseRequest;
import com.spectralogic.ds3autogen.python.model.response.BaseResponse;
import com.spectralogic.ds3autogen.python.model.type.TypeDescriptor;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.PythonCodeGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class PythonCodeGenerator_Test {

    @Test
    public void getRequestGenerator_Test() {
        assertThat(getRequestGenerator(getRequestCreateNotification()), instanceOf(BaseRequestGenerator.class));
        //TODO add additional tests as requests are special cased
    }

    @Test
    public void toRequestModel_Test() {
        final BaseRequest result = toRequestModel(getRequestCreateNotification());
        assertThat(result.getName(), is("CreateJobCreatedNotificationRegistrationRequestHandler"));
        assertThat(result.getPath(), is("'/_rest_/job_created_notification_registration'"));
        assertThat(result.getOperation(), is(nullValue()));
        assertThat(result.getRequiredArgs().size(), is(1));
        assertThat(result.getOptionalArgs().size(), is(3));
        assertThat(result.getVoidArgs().size(), is(0));
    }

    @Test
    public void toRequestModelList_NullList_Test() {
        final ImmutableList<BaseRequest> result = toRequestModelList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequestModelList_EmptyList_Test() {
        final ImmutableList<BaseRequest> result = toRequestModelList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequestModelList_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getRequestCreateNotification());

        final ImmutableList<BaseRequest> result = toRequestModelList(requests);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("CreateJobCreatedNotificationRegistrationRequestHandler"));
    }

    @Test
    public void getTypeGenerator_Test() {
        assertThat(getTypeGenerator(createDs3TypeTestData("com.test.EmptyType")), instanceOf(BaseTypeGenerator.class));
    }

    @Test
    public void toTypeDescriptor_Test() {
        final Ds3Type ds3Type = createDs3TypeTestData(
                "com.test.SimpleType",
                createDs3ElementListTestData("ElementName", "None"));
        final ImmutableMap<String, Ds3Type> testTypeMap = ImmutableMap.of(ds3Type.getName(), ds3Type);
        final TypeDescriptor result = toTypeDescriptor(ds3Type, testTypeMap);
        assertThat(result.getName(), is("SimpleType"));
        assertThat(result.getAttributes().size(), is(0));
        assertThat(result.getElementLists().size(), is(0));
        assertThat(result.getElements().size(), is(1));
        assertThat(result.getElements().get(0), is("'ElementName' : None"));
    }

    @Test
    public void toTypeDescriptorList_NullMap_Test() {
        final ImmutableList<TypeDescriptor> result = toTypeDescriptorList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toTypeDescriptorList_EmptyMap_Test() {
        final ImmutableList<TypeDescriptor> result = toTypeDescriptorList(ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toTypeDescriptorList_FullMap_Test() {
        final Ds3Type ds3Type = createDs3TypeTestData(
                "com.test.SimpleType",
                createDs3ElementListTestData("ElementName", "ElementType"));
        final ImmutableMap<String, Ds3Type> testTypeMap = ImmutableMap.of(ds3Type.getName(), ds3Type);
        final ImmutableList<TypeDescriptor> result = toTypeDescriptorList(testTypeMap);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("SimpleType"));
    }

    @Test
    public void getResponseGenerator_Test() {
        assertThat(getResponseGenerator(createDs3RequestTestData("com.test.EmptyRequest", Classification.spectrads3)),
                instanceOf(BaseResponseGenerator.class));

        assertThat(getResponseGenerator(getHeadObjectRequest()), instanceOf(HeadResponseGenerator.class));
    }

    @Test
    public void toResponseModel_Test() {
        final Ds3Request request = getBucketRequest();
        final BaseResponse result = toResponseModel(request, ImmutableMap.of());
        assertThat(result.getName(), is("GetBucketResponseHandler"));
        assertThat(result.getCodes().size(), is(1));

        final String expectedParseResponse = "if self.response.status == 200:\n" +
                "      self.result = parseModel(xmldom.fromstring(response.read()), ListBucketResult())";
        assertThat(result.getParseResponseCode(), is(expectedParseResponse));
    }

    @Test
    public void toResponseModelList_NullList_Test() {
        final ImmutableList<BaseResponse> result = toResponseModelList(null, ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toResponseModelList_EmptyList_Test() {
        final ImmutableList<BaseResponse> result = toResponseModelList(ImmutableList.of(), ImmutableMap.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toResponseModelList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(getBucketRequest());
        final ImmutableList<BaseResponse> result = toResponseModelList(requests, ImmutableMap.of());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("GetBucketResponseHandler"));
    }

    @Test
    public void toClientCommand_Test() {
        final BaseClient result = toClientCommand(createDs3RequestTestData("com.test.GetCompleteMultiPartUploadRequest", Classification.spectrads3));
        assertThat(result.getCommandName(), is("get_complete_multi_part_upload"));
        assertThat(result.getResponseName(), is("GetCompleteMultiPartUploadResponse"));
    }

    @Test
    public void toClientCommands_NullList_Test() {
        final ImmutableList<BaseClient> result = toClientCommands(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toClientCommands_EmptyList_Test() {
        final ImmutableList<BaseClient> result = toClientCommands(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toClientCommands_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createDs3RequestTestData("com.test.PutBucketRequest", Classification.amazons3),
                createDs3RequestTestData("com.test.PutBucketSpectraS3Request", Classification.spectrads3));

        final ImmutableList<BaseClient> result = toClientCommands(requests);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getResponseName(), is("PutBucketResponse"));
        assertThat(result.get(0).getCommandName(), is("put_bucket"));
        assertThat(result.get(1).getResponseName(), is("PutBucketSpectraS3Response"));
        assertThat(result.get(1).getCommandName(), is("put_bucket_spectra_s3"));
    }
}
