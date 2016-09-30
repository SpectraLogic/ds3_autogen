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

package com.spectralogic.ds3autogen.java.converters;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.*;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import com.spectralogic.ds3autogen.java.models.AnnotationInfo;
import com.spectralogic.ds3autogen.java.models.Command;
import com.spectralogic.ds3autogen.java.models.CustomCommand;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.converters.ClientConverter.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ClientConverter_Test {

    private static Ds3DocSpec getTestDocSpec() {
        return new Ds3DocSpecImpl(
                ImmutableMap.of(
                        "TestRequest", "This is how you use test request",
                        "GetObjectRequestHandler", "This is how you use get object"),
                ImmutableMap.of(
                        "BucketName", "This is how you use bucket name",
                        "ParamOne", "This is how you use delimiter",
                        "ParamTwo", "This is how you use marker"));
    }

    private static Ds3Request getTestRequest() {
        return new Ds3Request(
                "com.test.TestRequest",
                HttpVerb.GET,
                Classification.amazons3,
                Requirement.REQUIRED, // bucketRequirement
                Requirement.NOT_ALLOWED, // objectRequirement
                null, // action
                null, // resource
                null, // resourceType
                null, // operation
                false,// includeIdInPath
                ImmutableList.of(
                        new Ds3ResponseCode(200,
                                ImmutableList.of(new Ds3ResponseType("com.Test.TestResult", null, null)))),
                ImmutableList.of(
                        new Ds3Param("ParamOne", "java.lang.String", true)), // optionalQueryParams
                ImmutableList.of(
                        new Ds3Param("ParamTwo", "java.lang.String", true))); // requiredQueryParams
    }

    @Test
    public void toAnnotationInfo_AmazonRequest_Test() {
        final AnnotationInfo result = toAnnotationInfo(getRequestAmazonS3GetObject());
        assertThat(result, is(nullValue()));
    }

    @Test
    public void toAnnotationInfo_SpectraRequest_NoPayload_Test() {
        final AnnotationInfo result = toAnnotationInfo(getRequestDeleteNotification());
        assert result != null;
        assertThat(result.getAction(), is(Action.DELETE.toString()));
        assertThat(result.getResource(), is(Resource.JOB_CREATED_NOTIFICATION_REGISTRATION.toString()));
        assertThat(result.getResponsePayloadModel(), is(""));
    }

    @Test
    public void toAnnotationInfo_SpectraRequest_WithPayload_Test() {
        final AnnotationInfo result = toAnnotationInfo(getRequestGetJob());
        assert result != null;
        assertThat(result.getAction(), is(Action.SHOW.toString()));
        assertThat(result.getResource(), is(Resource.JOB.toString()));
        assertThat(result.getResponsePayloadModel(), is("JobWithChunksApiBean"));
    }

    @Test
    public void toCommand_EmptyDocSped_Test() {
        final Command result = toCommand(getTestRequest(), new Ds3DocSpecEmptyImpl());
        assertThat(result.getName(), is("Test"));
        assertThat(result.getRequestName(), is("TestRequest"));
        assertThat(result.getResponseName(), is("TestResponse"));
        assertThat(result.getDocumentation(), is(""));
        assertThat(result.getAnnotationInfo(), is(nullValue()));
    }

    @Test
    public void toCommand_Test() {
        final String expectedDocs = "/**\n" +
                "     * This is how you use test request\n" +
                "     */";

        final Command result = toCommand(getTestRequest(), getTestDocSpec());
        assertThat(result.getName(), is("Test"));
        assertThat(result.getRequestName(), is("TestRequest"));
        assertThat(result.getResponseName(), is("TestResponse"));
        assertThat(result.getDocumentation(), is(expectedDocs));
        assertThat(result.getAnnotationInfo(), is(nullValue()));
    }

    @Test
    public void toCommandList_NullList_Test() {
        final ImmutableList<Command> result = toCommandList(null, getTestDocSpec());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toCommandList_EmptyList_Test() {
        final ImmutableList<Command> result = toCommandList(ImmutableList.of(), getTestDocSpec());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toCommandList_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getTestRequest(),
                getRequestAmazonS3GetObject());
        final ImmutableList<Command> result = toCommandList(requests, getTestDocSpec());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Test"));
    }

    @Test
    public void isCustomCommand_Test() {
        assertThat(isCustomCommand(getRequestAmazonS3GetObject()), is(true));
        assertThat(isCustomCommand(getObjectsDetailsRequest()), is(false));
    }

    @Test
    public void toGetObjectAmazonS3CustomCommand_Test() {
        final String expectedDocs = "/**\n" +
                "     * This is how you use get object\n" +
                "     */";

        final String expectedBody = "return new GetObjectResponseHandlerParser(\n" +
                "                request.getChannel(),\n" +
                "                this.netClient.getConnectionDetails().getBufferSize(),\n" +
                "                request.getObjectName())\n" +
                "                .startResponse(this.netClient.getResponse(request));";

        final CustomCommand result = toGetObjectAmazonS3CustomCommand(getRequestAmazonS3GetObject(), getTestDocSpec());
        assertThat(result.getName(), is("GetObjectHandler"));
        assertThat(result.getRequestName(), is("GetObjectRequestHandler"));
        assertThat(result.getResponseName(), is("GetObjectResponseHandler"));
        assertThat(result.getDocumentation(), is(expectedDocs));
        assertThat(result.getAnnotationInfo(), is(nullValue()));
        assertThat(result.getCustomBody(), is(expectedBody));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toCustomCommand_Exception_Test() {
        toCustomCommand(getTestRequest(), getTestDocSpec());
    }

    @Test
    public void toCustomCommand_Test() {
        final CustomCommand result = toCustomCommand(getRequestAmazonS3GetObject(), getTestDocSpec());
        assertThat(result.getName(), is("GetObjectHandler"));
    }

    @Test
    public void toCustomCommandList_NullList() {
        final ImmutableList<CustomCommand> result = toCustomCommandList(null, getTestDocSpec());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toCustomCommandList_EmptyList() {
        final ImmutableList<CustomCommand> result = toCustomCommandList(ImmutableList.of(), getTestDocSpec());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toCustomCommandList_FullList() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getRequestAmazonS3GetObject(),
                getTestRequest());

        final ImmutableList<CustomCommand> result = toCustomCommandList(requests, getTestDocSpec());
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("GetObjectHandler"));
    }
}
