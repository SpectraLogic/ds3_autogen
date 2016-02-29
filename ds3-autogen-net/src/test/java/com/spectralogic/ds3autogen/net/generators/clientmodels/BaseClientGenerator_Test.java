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
import com.spectralogic.ds3autogen.api.models.Ds3ResponseType;
import com.spectralogic.ds3autogen.net.model.client.PayloadCommand;
import com.spectralogic.ds3autogen.net.model.client.VoidCommand;
import org.junit.Test;

import static com.spectralogic.ds3autogen.net.generators.clientmodels.BaseClientGenerator.*;
import static com.spectralogic.ds3autogen.net.test.helper.BaseClientGeneratorTestHelper.createTestRequest;
import static com.spectralogic.ds3autogen.net.test.helper.BaseClientGeneratorTestHelper.createTestResponseCodes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BaseClientGenerator_Test {



    @Test (expected = IllegalArgumentException.class)
    public void getResponseType_NullList_Test() {
        getResponseType(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponseType_EmptyList_Test() {
        getResponseType(ImmutableList.of());
    }

    @Test
    public void getResponseType_ListSizeOne_Test() {
        final String result = getResponseType(ImmutableList.of(
                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null)));
        assertThat(result, is("com.spectralogic.s3.server.domain.HttpErrorResultApiBean"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getResponseType_ListSizeTwo_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = ImmutableList.of(
                new Ds3ResponseType("null", null),
                new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null));

        getResponseType(responseTypes);
    }

    @Test
    public void getAllResponseTypes_NullList_Test() {
        final ImmutableList<String> result = getAllResponseTypes(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllResponseTypes_EmptyList_Test() {
        final ImmutableList<String> result = getAllResponseTypes(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void getAllResponseTypes_FullList_Test() {
        final ImmutableList<String> result = getAllResponseTypes(createTestResponseCodes(true));
        assertThat(result.size(), is(2));
        assertTrue(result.contains("com.spectralogic.s3.server.domain.JobWithChunksApiBean"));
        assertTrue(result.contains("null"));
    }

    @Test
    public void hasResponsePayload_NullResponses_Test() {
        assertFalse(hasResponsePayload(createTestResponseCodes(false)));
    }

    @Test
    public void hasResponsePayload_PayloadResponse_Test() {
        assertTrue(hasResponsePayload(createTestResponseCodes(true)));
    }

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
                createTestRequest("Request1", true),
                createTestRequest("Request2", false),
                createTestRequest("Request3", true),
                createTestRequest("Request4", false));

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
        final Ds3Request request = createTestRequest("com.test.WithPayloadRequest", true);

        final PayloadCommand result = toPayloadCommand(request);
        assertThat(result.getCommandName(), is("WithPayload"));
        assertThat(result.getRequestName(), is("WithPayloadRequest"));
        assertThat(result.getResponseType(), is("WithPayloadResponse"));
    }

    @Test
    public void toVoidCommand_Test() {
        final Ds3Request request = createTestRequest("com.test.WithoutPayloadRequest", false);

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
                createTestRequest("com.test.OneRequest", false),
                createTestRequest("com.test.TwoRequest", true),
                createTestRequest("com.test.ThreeRequest", false),
                createTestRequest("com.test.FourRequest", true));

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
                createTestRequest("com.test.OneRequest", false),
                createTestRequest("com.test.TwoRequest", true),
                createTestRequest("com.test.ThreeRequest", false),
                createTestRequest("com.test.FourRequest", true));

        final ImmutableList<VoidCommand> result = toVoidCommands(requests);
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getRequestName(), is("OneRequest"));
        assertThat(result.get(1).getRequestName(), is("ThreeRequest"));
    }
}
