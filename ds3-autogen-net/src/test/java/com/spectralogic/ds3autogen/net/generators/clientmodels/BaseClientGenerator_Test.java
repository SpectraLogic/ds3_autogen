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

package com.spectralogic.ds3autogen.net.generators.clientmodels;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.net.model.client.PayloadCommand;
import com.spectralogic.ds3autogen.net.model.client.SpecializedCommand;
import com.spectralogic.ds3autogen.net.model.client.VoidCommand;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.clientmodels.BaseClientGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ResponseCodeFixture.createTestRequestWithResponseCodes;
import static com.spectralogic.ds3autogen.testutil.Ds3ResponseCodeFixture.createTestResponseCodes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BaseClientGenerator_Test {

    @Test
    public void getRequestsBasedOnResponsePayload_NullList_Test() {
        final ImmutableList<Ds3Request> result = getRequestsBasedOnResponsePayload(null, false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getRequestsBasedOnResponsePayload_EmptyList_Test() {
        final ImmutableList<Ds3Request> result = getRequestsBasedOnResponsePayload(ImmutableList.of(), false);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getRequestsBasedOnResponsePayload_FullList_Test() {
        final ImmutableList<Ds3Request> input = ImmutableList.of(
                createTestRequestWithResponseCodes("Request1", true),
                createTestRequestWithResponseCodes("Request2", false),
                createTestRequestWithResponseCodes("Request3", true),
                createTestRequestWithResponseCodes("Request4", false));

        final ImmutableList<Ds3Request> payloadRequests = getRequestsBasedOnResponsePayload(input, true);
        assertThat(payloadRequests.size(), is(2));
        assertThat(payloadRequests.get(0).getName(), is("Request1"));
        assertThat(payloadRequests.get(1).getName(), is("Request3"));

        final ImmutableList<Ds3Request> voidRequests = getRequestsBasedOnResponsePayload(input, false);
        assertThat(voidRequests.size(), is(2));
        assertThat(voidRequests.get(0).getName(), is("Request2"));
        assertThat(voidRequests.get(1).getName(), is("Request4"));
    }

    @Test
    public void toPayloadCommand_Test() {
        final Ds3Request request = createTestRequestWithResponseCodes("com.test.WithPayloadRequest", true);

        final PayloadCommand result = toPayloadCommand(request);
        assertThat(result.getCommandName(), is("WithPayload"));
        assertThat(result.getRequestName(), is("WithPayloadRequest"));
        assertThat(result.getResponseType(), is("WithPayloadResponse"));
    }

    @Test
    public void toVoidCommand_Test() {
        final Ds3Request request = createTestRequestWithResponseCodes("com.test.WithoutPayloadRequest", false);

        final VoidCommand result = toVoidCommand(request);
        assertThat(result.getCommandName(), is("WithoutPayload"));
        assertThat(result.getRequestName(), is("WithoutPayloadRequest"));
        assertThat(result.getResponseType(), is("void"));
    }

    @Test
    public void toPayloadCommands_NullList_Test() {
        final ImmutableList<PayloadCommand> result = toPayloadCommands(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toPayloadCommands_EmptyList_Test() {
        final ImmutableList<PayloadCommand> result = toPayloadCommands(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toPayloadCommands_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createTestRequestWithResponseCodes("com.test.OneRequest", false),
                createTestRequestWithResponseCodes("com.test.TwoRequest", true),
                createTestRequestWithResponseCodes("com.test.ThreeRequest", false),
                createTestRequestWithResponseCodes("com.test.FourRequest", true));

        final ImmutableList<PayloadCommand> result = toPayloadCommands(requests);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getRequestName(), is("TwoRequest"));
        assertThat(result.get(1).getRequestName(), is("FourRequest"));
    }

    @Test
    public void toVoidCommands_NullList_Test() {
        final ImmutableList<VoidCommand> result = toVoidCommands(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toVoidCommands_EmptyList_Test() {
        final ImmutableList<VoidCommand> result = toVoidCommands(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toVoidCommands_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createTestRequestWithResponseCodes("com.test.OneRequest", false),
                createTestRequestWithResponseCodes("com.test.TwoRequest", true),
                createTestRequestWithResponseCodes("com.test.ThreeRequest", false),
                createTestRequestWithResponseCodes("com.test.FourRequest", true));

        final ImmutableList<VoidCommand> result = toVoidCommands(requests);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getRequestName(), is("OneRequest"));
        assertThat(result.get(1).getRequestName(), is("ThreeRequest"));
    }

    @Test
    public void getHttpStatusCode_NullList_Test() {
        final String result = getHttpStatusCode(null);
        assertThat(result, is(""));
    }

    @Test
    public void getHttpStatusCode_EmptyList_Test() {
        final String result = getHttpStatusCode(ImmutableList.of());
        assertThat(result, is(""));
    }

    @Test
    public void getHttpStatusCode_NoContent_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = createTestResponseCodes(false);
        final String result = getHttpStatusCode(responseCodes);
        assertThat(result, is("NoContent"));
    }

    @Test
    public void getHttpStatusCode_OK_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = ImmutableList.of(
                new Ds3ResponseCode(200, null),
                new Ds3ResponseCode(201, null));

        final String result = getHttpStatusCode(responseCodes);
        assertThat(result, is("OK"));
    }

    @Test
    public void toGetObjectCommand_Test() {
        final SpecializedCommand result = toGetObjectCommand(getRequestAmazonS3GetObject());
        assertThat(result.getRequestName(), is("GetObjectRequestHandler"));
        assertThat(result.getResponseType(), is("GetObjectResponseHandler"));
        assertThat(result.getCommandName(), is("GetObjectHandler"));
        assertThat(result.getFunctionBody(), is("return new GetObjectHandlerResponseParser(_netLayer.CopyBufferSize).Parse(request, _netLayer.Invoke(request));"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toGetObjectCommand_Error_Test() {
        toGetObjectCommand(getRequestSpectraS3GetObject());
    }

    @Test
    public void toSpecializedCommands_NullList_Test() {
        final ImmutableList<SpecializedCommand> result = toSpecializedCommands(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toSpecializedCommands_EmptyList_Test() {
        final ImmutableList<SpecializedCommand> result = toSpecializedCommands(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toSpecializedCommands_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getRequestAmazonS3GetObject(),
                getRequestSpectraS3GetObject());

        final ImmutableList<SpecializedCommand> result = toSpecializedCommands(requests);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getRequestName(), is("GetObjectRequestHandler"));
    }

    @Test
    public void requestPayloadStatus_Test() {
        assertFalse(requestPayloadStatus(getRequestAmazonS3GetObject(), true));
        assertFalse(requestPayloadStatus(getRequestAmazonS3GetObject(), false));

        assertTrue(requestPayloadStatus(getHeadBucketRequest(), true));
        assertFalse(requestPayloadStatus(getHeadBucketRequest(), false));

        //Request with payload
        assertTrue(requestPayloadStatus(getGetBlobPersistence(), true));
        assertFalse(requestPayloadStatus(getGetBlobPersistence(), false));

        //Request with no payload
        assertFalse(requestPayloadStatus(getRequestDeleteNotification(), true));
        assertTrue(requestPayloadStatus(getRequestDeleteNotification(), false));
    }
}
