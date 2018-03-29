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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ResponseType;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.c.converters.ParameterConverter;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;
import com.spectralogic.ds3autogen.c.models.Parameter;
import com.spectralogic.ds3autogen.c.models.ParameterModifier;
import com.spectralogic.ds3autogen.c.models.ParameterPointerType;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecEmptyImpl;
import com.spectralogic.ds3autogen.docspec.Ds3DocSpecImpl;
import com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
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
    public void testGetRequestParameterListNoResponseType() throws IOException {
        final ImmutableList<Parameter> paramList = RequestConverter.getParamList("", new Ds3DocSpecEmptyImpl());
        assertEquals(paramList.size(), 2);
        assertEquals(RequestHelper.paramListToString(paramList), "const ds3_client* client, const ds3_request* request");
    }

    @Test
    public void testGetRequestParameterListWithResponseType() throws IOException {
        final ImmutableList<Parameter> paramList = RequestConverter.getParamList("ds3_get_service_response", new Ds3DocSpecEmptyImpl());
        assertEquals(paramList.size(), 3);
        assertEquals(RequestHelper.paramListToString(paramList), "const ds3_client* client, const ds3_request* request, ds3_get_service_response** response");
    }

    @Test
    public void testConvertDs3RequestWithRequestPayload() {
        final ImmutableMap<String, Parameter> hasRequestPayload = RequestConverter.buildRequestPayloadMap();
        assertNotNull(hasRequestPayload.get("get_bulk_job_spectra_s3_request"));
    }

    @Test
    public void testStageObjectsHasRequestPayload() {
        final ImmutableMap<String, Parameter> hasRequestPayload = RequestConverter.buildRequestPayloadMap();
        final Parameter result = hasRequestPayload.get("stage_objects_job_spectra_s3_request");
        assertNotNull(result);

        assertThat(result.getName(), is("object_list"));
        assertThat(result.getParameterType(), is("ds3_bulk_object_list_response"));
    }

    @Test
    public void testNullResponseForNoRequestPayload() {
        final ImmutableMap<String, Parameter> requestPayloadMap = RequestConverter.buildRequestPayloadMap();
        assertEquals(requestPayloadMap.get("unknownRequest"), null);
    }

    @Test
    public void testRequestPayloadMapDoesNotContainEjectStorageDomain() {
        final ImmutableMap<String, Parameter> requestPayloadMap = RequestConverter.buildRequestPayloadMap();
        assertEquals(requestPayloadMap.get("eject_storage_domain_spectra_s3_request"), null);
    }

    @Test
    public void testRequestPayloadMapContainsSuspectBlobCommands() {
        final ImmutableMap<String, Parameter> requestPayloadMap = RequestConverter.buildRequestPayloadMap();

        final Parameter expected = new Parameter(ParameterModifier.CONST, "ds3_ids_list", "ids", ParameterPointerType.SINGLE_POINTER, true);

        assertThat(requestPayloadMap.get("clear_suspect_blob_azure_targets_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("clear_suspect_blob_ds3_targets_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("clear_suspect_blob_pools_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("clear_suspect_blob_s3_targets_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("clear_suspect_blob_tapes_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("mark_suspect_blob_azure_targets_as_degraded_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("mark_suspect_blob_ds3_targets_as_degraded_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("mark_suspect_blob_pools_as_degraded_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("mark_suspect_blob_s3_targets_as_degraded_spectra_s3_request"), is(expected));
        assertThat(requestPayloadMap.get("mark_suspect_blob_tapes_as_degraded_spectra_s3_request"), is(expected));
    }

    @Test
    public void testConvertDs3RequestWithoutRequestPayload() {
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.createBucketRequest(), new Ds3DocSpecEmptyImpl());
        assertFalse(testRequest.hasRequestPayload());
        assertEquals(testRequest.getRequestPayload(), null);
    }

    private static Ds3DocSpec getTestDocSpec() {
        return new Ds3DocSpecImpl(
                ImmutableMap.of(
                        "TestRequest", "This is how you use test request",
                        "CreateBucketRequestHandler", "This is how you use create bucket",
                        "GetObjectRequestHandler", "This is how you use get object"),
                ImmutableMap.of(
                        "BucketName", "This is how you use bucket name",
                        "ParamOne", "This is how you use delimiter",
                        "ParamTwo", "This is how you use marker"));
    }

    @Test
    public void testConvertDs3RequestWithDocumentation() {
        final Ds3DocSpec docSpec = getTestDocSpec();
        final Request testRequest = RequestConverter.toRequest(Ds3ModelFixtures.createBucketRequest(), docSpec);

        assertEquals(testRequest.getDocumentation(), "This is how you use create bucket");
    }

    @Test
    public void testConvertDs3ParameterWithDocumentation() {
        final Ds3DocSpec docSpec = getTestDocSpec();
        final String paramName = "BucketName";
        final Ds3Param testDs3Param = new Ds3Param(paramName, "Ds3Bucket", false);
        final Parameter testParameter = ParameterConverter.toParameter(testDs3Param, true, RequestConverter.toParamDocs(paramName, docSpec));

        assertEquals(testParameter.getDocumentation(), "This is how you use bucket name");
    }

    @Test
    public void testEjectStorageDomainHasNoPayload() {
        final Request ejectStorageDomainRequest = RequestConverter.toRequest(Ds3ModelFixtures.getEjectStorageDomainRequest(), new Ds3DocSpecEmptyImpl());
        assertFalse(ejectStorageDomainRequest.hasRequestPayload());
    }
}
