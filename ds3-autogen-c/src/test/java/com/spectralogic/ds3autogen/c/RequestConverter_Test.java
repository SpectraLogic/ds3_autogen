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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;
import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.Request;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestConverter_Test {

    @Test
    public void testHasResponsePayload() {
        final Ds3ResponseType responseType = new Ds3ResponseType("com.spectralogic.s3.server.domain.BucketObjectsApiBean", null);
        final Ds3ResponseCode responseCode = new Ds3ResponseCode(200, ImmutableList.of(responseType));

        assertEquals(RequestConverter.getResponseType(ImmutableList.of(responseCode)), "ds3_bucket_objects_api_bean_response");
    }

    @Test
    public void testEmptyResponsePayload() {
        final Ds3ResponseType responseTypeNull = new Ds3ResponseType("null", null);
        final Ds3ResponseCode responseCode200 = new Ds3ResponseCode(200, ImmutableList.of(responseTypeNull));

        final Ds3ResponseType responseTypeError = new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null);
        final Ds3ResponseCode responseCode400 = new Ds3ResponseCode(400, ImmutableList.of(responseTypeError));

        final Ds3ResponseCode responseCode403 = new Ds3ResponseCode(403, ImmutableList.of(responseTypeError));

        final Ds3ResponseCode responseCode409 = new Ds3ResponseCode(409, ImmutableList.of(responseTypeError));
        assertTrue(RequestConverter.getResponseType(ImmutableList.of(responseCode200, responseCode400, responseCode403, responseCode409)).isEmpty());
    }

    @Test
    public void testGetRequestParameterListNoResponseType() {
        final ImmutableList<Parameter> paramList = RequestConverter.getParamList("");
        assertEquals(paramList.size(), 2);
        assertEquals(RequestHelper.paramListToString(paramList), "const ds3_client* client, const ds3_request* request");
    }

    @Test
    public void testGetRequestParameterListWithResponseType() {
        final ImmutableList<Parameter> paramList = RequestConverter.getParamList("ds3_get_service_response");
        assertEquals(paramList.size(), 3);
        assertEquals(RequestHelper.paramListToString(paramList), "const ds3_client* client, const ds3_request* request, const ds3_get_service_response** response");
    }

    @Test
    public void testConvertDs3RequestWithRequestPayload() {
        final Request testRequest = RequestConverter.toRequest(
                new Ds3Request("CreateGetJobRequestHandler",
                        HttpVerb.PUT,
                        Classification.spectrads3,
                        null, // bucketRequirement
                        null, // objectRequirement
                        Action.MODIFY, // Action
                        Resource.BUCKET, //Resource
                        ResourceType.NON_SINGLETON, // ResourceType
                        Operation.START_BULK_GET, // Operation
                        true, // includeIdInPath
                        null, // ds3ResponseCodes,
                        ImmutableList.of(
                                new Ds3Param("Aggregating", "boolean", false),
                                new Ds3Param("ChunkClientProcessingOrderGuarantee", "com.spectralogic.s3.common.dao.domain.ds3.JobChunkClientProcessingOrderGuarantee", false),
                                new Ds3Param("Name", "java.lang.String", true),
                                new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false)), // optionalQueryParams
                        ImmutableList.of(
                                new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", true))));// requiredQueryParams
        assertTrue(testRequest.hasRequestPayload());
    }

    @Test
    public void testNullResponseForNoRequestPayload() {
        final ImmutableMap<String, Parameter> requestPayloadMap = RequestConverter.buildRequestPayloadMap();
        assertEquals(requestPayloadMap.get("unknownRequest"), null);
    }

    @Test
    public void testConvertDs3RequestWithoutRequestPayload() {
        final Request testRequest = RequestConverter.toRequest(
                new Ds3Request("CreateBucketRequestHandler",
                        HttpVerb.PUT,
                        Classification.amazons3,
                        Requirement.REQUIRED,
                        Requirement.NOT_ALLOWED,
                        null, // Action
                        null, //Resource
                        null, // ResourceType
                        null, // Operation
                        false, // includeIdInPath
                        null, // ds3ResponseCodes,
                        ImmutableList.of(), // optionalQueryParams
                        ImmutableList.of()));// requiredQueryParams
        assertFalse(testRequest.hasRequestPayload());
        assertEquals(testRequest.getRequestPayload(), null);
    }
}
