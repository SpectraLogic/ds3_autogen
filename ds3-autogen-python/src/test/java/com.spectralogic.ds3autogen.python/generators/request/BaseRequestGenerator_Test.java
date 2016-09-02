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

package com.spectralogic.ds3autogen.python.generators.request;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.BaseQueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.OperationQueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.QueryParam;
import com.spectralogic.ds3autogen.python.model.request.queryparam.VoidQueryParam;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.generators.request.BaseRequestGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getCompleteMultipartUploadRequest;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelPartialDataFixture.createDs3RequestTestData;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class BaseRequestGenerator_Test {

    private final static BaseRequestGenerator generator = new BaseRequestGenerator();

    /**
     * Gets a list of Ds3Params for testing purposes
     */
    private static ImmutableList<Ds3Param> getTestParams() {
        return ImmutableList.of(
                new Ds3Param("ArgOne", "TypeOne", true),
                new Ds3Param("VoidArg", "void", false));
    }

    @Test
    public void toOptionalArgumentsList_NullList_Test() {
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalArgumentsList_EmptyList_Test() {
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }
    @Test
    public void toOptionalArgumentsList_List_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("ArgOne", "TypeOne", true),
                new Ds3Param("ArgTwo", "TypeTwo", false)
        );
        final ImmutableList<Arguments> result = generator.toOptionalArgumentsList(params);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("ArgOne"));
        assertThat(result.get(0).getType(), is("TypeOne"));
        assertThat(result.get(1).getName(), is("ArgTwo"));
        assertThat(result.get(1).getType(), is("TypeTwo"));
    }

    @Test
    public void getAssignmentArguments_NullList_Test() {
        final ImmutableList<Arguments> result = getAssignmentArguments(createDs3RequestTestData(false, null, null));
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAssignmentArguments_EmptyList_Test() {
        final ImmutableList<Arguments> result = getAssignmentArguments(createDs3RequestTestData(false, ImmutableList.of(), ImmutableList.of()));
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAssignmentArguments_ListWithVoidParam_Test() {
        final ImmutableList<Arguments> result = getAssignmentArguments(exampleRequestWithOptionalAndRequiredBooleanQueryParam());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAssignmentArguments_NotificationRequest_Test() {
        final ImmutableList<Arguments> result = getAssignmentArguments(getRequestDeleteNotification());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("NotificationId"));
        assertThat(result.get(0).getType(), is("UUID"));
    }

    @Test
    public void getAssignmentArguments_Test() {
        final ImmutableList<Arguments> result = getAssignmentArguments(getCompleteMultipartUploadRequest());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketName"));
        assertThat(result.get(1).getName(), is("ObjectName"));
    }

    @Test
    public void toQueryParam_VoidParam_Test() {
        final Arguments arg = new Arguments("void", "VoidArg");
        final QueryParam result = toQueryParam(arg);
        assertThat(result, instanceOf(VoidQueryParam.class));
        assertThat(result.getName(), is("void_arg"));
        assertThat(result.getAssignment(), is("None"));
    }

    @Test
    public void toQueryParam_Test() {
        final Arguments arg = new Arguments("int", "IntArg");
        final QueryParam result = toQueryParam(arg);
        assertThat(result, instanceOf(BaseQueryParam.class));
        assertThat(result.getName(), is("int_arg"));
        assertThat(result.getAssignment(), is("int_arg"));
    }

    @Test
    public void toRequiredQueryParamList_NullList_Test() {
        final ImmutableList<QueryParam> result = toRequiredQueryParamList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredQueryParamList_EmptyList_Test() {
        final ImmutableList<QueryParam> result = toRequiredQueryParamList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredQueryParamList_FullList_Test() {
        final ImmutableList<QueryParam> result = toRequiredQueryParamList(getTestParams());
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("arg_one"));
        assertThat(result.get(0).getAssignment(), is("arg_one"));
        assertThat(result.get(1).getName(), is("void_arg"));
        assertThat(result.get(1).getAssignment(), is("None"));
    }

    @Test
    public void toQueryParamList_NullList_Test() {
        final ImmutableList<QueryParam> result = toQueryParamList(null, null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toQueryParamList_NullListWithOperation_Test() {
        final ImmutableList<QueryParam> result = toQueryParamList(Operation.ALLOCATE, null);
        assertThat(result.size(), is(1));
        assertThat(result.get(0), instanceOf(OperationQueryParam.class));
    }

    @Test
    public void toQueryParamList_EmptyList_Test() {
        final ImmutableList<QueryParam> result = toQueryParamList(null, ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toQueryParamList_EmptyListWithOperation_Test() {
        final ImmutableList<QueryParam> result = toQueryParamList(Operation.ALLOCATE, ImmutableList.of());
        assertThat(result.size(), is(1));
        assertThat(result.get(0), instanceOf(OperationQueryParam.class));
    }

    @Test
    public void toQueryParamList_FullList_Test() {
        final ImmutableList<QueryParam> result = toQueryParamList(null, getTestParams());
        assertThat(result.size(), is(2));
        assertThat(result.get(0), instanceOf(BaseQueryParam.class));
        assertThat(result.get(1), instanceOf(VoidQueryParam.class));
    }

    @Test
    public void toQueryParamList_FullListWithOperation_Test() {
        final ImmutableList<QueryParam> result = toQueryParamList(Operation.ALLOCATE, getTestParams());
        assertThat(result.size(), is(3));
        assertThat(result.get(0), instanceOf(OperationQueryParam.class));
        assertThat(result.get(1), instanceOf(BaseQueryParam.class));
        assertThat(result.get(2), instanceOf(VoidQueryParam.class));
    }

    @Test
    public void toAssignments_EmptyRequest_Test() {
        final ImmutableList<String> result = generator
                .toAssignments(createDs3RequestTestData("com.test.TestRequestHandler", Classification.amazons3));
        assertThat(result.size(), is(0));
    }

    @Test
    public void toAssignments_Test() {
        final ImmutableList<String> result = generator.toAssignments(getCompleteMultipartUploadRequest());
        assertThat(result.size(), is(2));
        assertThat(result, hasItem("bucket_name"));
        assertThat(result, hasItem("object_name"));
    }

    @Test
    public void getAdditionalContent_Test() {
        final String result = generator.getAdditionalContent(null, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void toOptionalConstructorParams_EmptyRequest_Test() {
        final ImmutableList<ConstructorParam> result = generator
                .toOptionalConstructorParams(createDs3RequestTestData("com.test.TestRequestHandler", Classification.amazons3));
        assertThat(result.size(), is(0));
    }

    @Test
    public void toOptionalConstructorParams_Test() {
        final ImmutableList<ConstructorParam> result = generator.toOptionalConstructorParams(getRequestCreateNotification());
        assertThat(result.size(), is(3));
        assertThat(result.get(0).toPythonCode(), is("format=None"));
        assertThat(result.get(1).toPythonCode(), is("naming_convention=None"));
        assertThat(result.get(2).toPythonCode(), is("notification_http_method=None"));
    }

    @Test
    public void toRequiredConstructorParams_EmptyRequest_Test() {
        final ImmutableList<ConstructorParam> result = generator
                .toRequiredConstructorParams(createDs3RequestTestData("com.test.TestRequestHandler", Classification.amazons3));
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequiredConstructorParams_Test() {
        final ImmutableList<ConstructorParam> result = generator.toRequiredConstructorParams(getReplicatePutJob());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).toPythonCode(), is("bucket_name"));
    }

    @Test
    public void toConstructorParams_EmptyRequest_Test() {
        final ImmutableList<String> result = generator
                .toConstructorParams(createDs3RequestTestData("com.test.TestRequestHandler", Classification.amazons3));
        assertThat(result.size(), is(0));
    }

    @Test
    public void toConstructorParams_Test() {
        final ImmutableList<String> result = generator.toConstructorParams(getReplicatePutJob());
        assertThat(result.size(), is(3));
        assertThat(result, hasItem("bucket_name"));
        assertThat(result, hasItem("conflict_resolution_mode=None"));
        assertThat(result, hasItem("priority=None"));
    }

    @Test
    public void toDocumentation_Test() {
        final String expected = "'''\n" +
                "  This is how you use test one request\n" +
                "  `param_one` This is how you use param one\n" +
                "  `does_not_exist` \n" +
                "  '''\n";

        final Ds3DocSpec docSpec = new Ds3DocSpecImpl(
                ImmutableMap.of(
                        "TestOneRequest", "This is how you use test one request"),
                ImmutableMap.of(
                        "ParamOne", "This is how you use param one"));

        final ImmutableList<String> params = ImmutableList.of("param_one", "does_not_exist");
        final String result = toDocumentation("TestOneRequest", params, docSpec);
        assertThat(result, is(expected));
    }
}
