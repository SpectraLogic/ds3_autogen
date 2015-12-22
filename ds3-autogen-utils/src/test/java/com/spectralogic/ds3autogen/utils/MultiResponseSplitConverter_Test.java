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

package com.spectralogic.ds3autogen.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import org.junit.Test;

import static com.spectralogic.ds3autogen.utils.MultiResponseSplitConverter.*;
import static com.spectralogic.ds3autogen.utils.test.utils.SplitConverterTestHelper.*;
import static com.spectralogic.ds3autogen.utils.test.utils.UtilTestHelper.getRequestDeleteNotification;
import static com.spectralogic.ds3autogen.utils.test.utils.UtilTestHelper.getRequestGetNotification;
import static com.spectralogic.ds3autogen.utils.test.utils.UtilTestHelper.getRequestVerifyPhysicalPlacement;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class MultiResponseSplitConverter_Test {

    @Test
    public void getTypeNameWithoutPath_Test() {
        assertThat(getTypeNameWithoutPath(null), is(""));
        assertThat(getTypeNameWithoutPath("com.spectralogic.s3.server.domain.TapeApiBean"), is("TapeApiBean"));
        assertThat(getTypeNameWithoutPath("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.GetTapePartitionRequestHandler$DetailedTapePartition"), is("DetailedTapePartition"));
    }

    @Test
    public void isSpecifiedDs3ResponseType_Test() {
        final Ds3ResponseType simpleResponseType = new Ds3ResponseType("ResponseType", null);
        assertTrue(isSpecifiedDs3ResponseType(simpleResponseType, "ResponseType", null));
        assertFalse(isSpecifiedDs3ResponseType(simpleResponseType, "ResponseType", "ComponentType"));

        final Ds3ResponseType simpleComponentResponseType = new Ds3ResponseType("ResponseType", "ComponentType");
        assertTrue(isSpecifiedDs3ResponseType(simpleComponentResponseType, "ResponseType", "ComponentType"));
        assertFalse(isSpecifiedDs3ResponseType(simpleComponentResponseType, "ResponseType", null));

        final Ds3ResponseType responseType = new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.tape.TapePartition", null);
        assertTrue(isSpecifiedDs3ResponseType(responseType, "TapePartition", null));
        assertFalse(isSpecifiedDs3ResponseType(responseType, "TapePartition", "ComponentType"));
        assertFalse(isSpecifiedDs3ResponseType(responseType, "com.spectralogic.s3.common.dao.domain.tape.TapePartition", null));

        final Ds3ResponseType responseTypeWithDollar = new Ds3ResponseType("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.GetTapePartitionRequestHandler$DetailedTapePartition", null);
        assertTrue(isSpecifiedDs3ResponseType(responseTypeWithDollar, "DetailedTapePartition", null));
        assertFalse(isSpecifiedDs3ResponseType(responseTypeWithDollar, "DetailedTapePartition", "ComponentType"));
        assertFalse(isSpecifiedDs3ResponseType(responseTypeWithDollar, "com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.GetTapePartitionRequestHandler$DetailedTapePartition", null));

        final Ds3ResponseType componentResponseType = new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.tape.TapePartition", "array");
        assertTrue(isSpecifiedDs3ResponseType(componentResponseType, "TapePartition", "array"));
        assertFalse(isSpecifiedDs3ResponseType(componentResponseType, "TapePartition", null));
        assertFalse(isSpecifiedDs3ResponseType(componentResponseType, "com.spectralogic.s3.common.dao.domain.tape.TapePartition", "array"));
    }

    @Test
    public void modifyResponseType_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = getPopulatedDs3ResponseTypeList();

        final ImmutableList<Ds3ResponseType> tapePartition = modifyResponseType(responseTypes, "TapePartition", null);
        assertThat(tapePartition.size(), is(1));
        assertTrue(tapePartition.get(0).getType().endsWith(".TapePartition"));
        assertThat(tapePartition.get(0).getComponentType(), is(nullValue()));
    }

    @Test
    public void modifyResponseType_TypeWithDollarSign_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = getPopulatedDs3ResponseTypeList();

        final ImmutableList<Ds3ResponseType> detailedTapePartition = modifyResponseType(responseTypes, "DetailedTapePartition", null);
        assertThat(detailedTapePartition.size(), is(1));
        assertTrue(detailedTapePartition.get(0).getType().endsWith("$DetailedTapePartition"));
        assertThat(detailedTapePartition.get(0).getComponentType(), is(nullValue()));
    }

    @Test
    public void modifyResponseType_TypeWithComponent_Test() {
        final ImmutableList<Ds3ResponseType> responseTypes = getPopulatedDs3ResponseTypeList();

        final ImmutableList<Ds3ResponseType> tapePartitionArray = modifyResponseType(responseTypes, "TapePartition", "array");
        assertThat(tapePartitionArray.size(), is(1));
        assertTrue(tapePartitionArray.get(0).getType().endsWith(".TapePartition"));
        assertThat(tapePartitionArray.get(0).getComponentType(), is("array"));
    }

    @Test
    public void modifyResponseCode_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = getPopulatedDs3ResponseCodeList();

        final ImmutableList<Ds3ResponseCode> result = modifyResponseCode(responseCodes, 200, "TapePartition", null);
        assertThat(result.size(), is(3));

        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(0).getDs3ResponseTypes().size(), is(1));
        assertTrue(result.get(0).getDs3ResponseTypes().get(0).getType().endsWith(".TapePartition"));
        assertThat(result.get(0).getDs3ResponseTypes().get(0).getComponentType(), is(nullValue()));

        assertThat(result.get(1).getDs3ResponseTypes().size(), is(3));
        assertThat(result.get(2).getDs3ResponseTypes().size(), is(1));
    }

    @Test
    public void modifyResponseCode_TypeWithDollarSign_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = getPopulatedDs3ResponseCodeList();

        final ImmutableList<Ds3ResponseCode> result = modifyResponseCode(responseCodes, 200, "DetailedTapePartition", null);
        assertThat(result.size(), is(3));

        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(0).getDs3ResponseTypes().size(), is(1));
        assertTrue(result.get(0).getDs3ResponseTypes().get(0).getType().endsWith("$DetailedTapePartition"));
        assertThat(result.get(0).getDs3ResponseTypes().get(0).getComponentType(), is(nullValue()));

        assertThat(result.get(1).getDs3ResponseTypes().size(), is(3));
        assertThat(result.get(2).getDs3ResponseTypes().size(), is(1));
    }

    @Test
    public void modifyResponseCode_TypeWithComponent_Test() {
        final ImmutableList<Ds3ResponseCode> responseCodes = getPopulatedDs3ResponseCodeList();

        final ImmutableList<Ds3ResponseCode> result = modifyResponseCode(responseCodes, 200, "TapePartition", "array");
        assertThat(result.size(), is(3));

        assertThat(result.get(0).getCode(), is(200));
        assertThat(result.get(0).getDs3ResponseTypes().size(), is(1));
        assertTrue(result.get(0).getDs3ResponseTypes().get(0).getType().endsWith(".TapePartition"));
        assertThat(result.get(0).getDs3ResponseTypes().get(0).getComponentType(), is("array"));

        assertThat(result.get(1).getDs3ResponseTypes().size(), is(3));
        assertThat(result.get(2).getDs3ResponseTypes().size(), is(1));
    }

    @Test
    public void addDs3Param_NullList_Test() {
        final ImmutableList<Ds3Param> result = addDs3Param(null, new Ds3Param("Name", "Type"));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Name"));
    }

    @Test
    public void addDs3Param_EmptyList_Test() {
        final ImmutableList<Ds3Param> result = addDs3Param(
                ImmutableList.of(),
                new Ds3Param("Name", "Type"));
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("Name"));
    }

    @Test
    public void addDs3Param_FullList_Test() {
        final ImmutableList<Ds3Param> params = ImmutableList.of(
                new Ds3Param("Arg1", "Type1"),
                new Ds3Param("Arg2", "Type2"));

        final ImmutableList<Ds3Param> result = addDs3Param(
                params,
                new Ds3Param("Arg3", "Type3"));

        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("Arg1"));
        assertThat(result.get(1).getName(), is("Arg2"));
        assertThat(result.get(2).getName(), is("Arg3"));
    }

    @Test
    public void getDs3Param_NullList_Test() {
        final Ds3Param result = getDs3Param(null, "FullDetails");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void getDs3Param_EmptyList_Test() {
        final Ds3Param result = getDs3Param(ImmutableList.of(), "FullDetails");
        assertThat(result, is(nullValue()));
    }

    @Test
    public void getDs3Param_Test() {
        final Ds3Param result = getDs3Param(getPopulatedDs3ParamList(), "FullDetails");
        assertThat(result.getName(), is("FullDetails"));
        assertThat(result.getType(), is("void"));
    }

    @Test
    public void removeDs3Param_NullList_Test() {
        final ImmutableList<Ds3Param> result = removeDs3Param(null, "FullDetails");
        assertThat(result.size(), is(0));
    }

    @Test
    public void removeDs3Param_EmptyList_Test() {
        final ImmutableList<Ds3Param> result = removeDs3Param(ImmutableList.of(), "FullDetails");
        assertThat(result.size(), is(0));
    }

    @Test
     public void removeDs3Param_Test() {
        final ImmutableList<Ds3Param> result = removeDs3Param(getPopulatedDs3ParamList(), "FullDetails");
        assertThat(result.size(), is(2));
        assertThat(result.get(0).getName(), is("BucketId"));
        assertThat(result.get(1).getName(), is("Priority"));
    }

    @Test
    public void removeDs3Param_NonPresentType_Test() {
        final ImmutableList<Ds3Param> result = removeDs3Param(getPopulatedDs3ParamList(), "NonPresentType");
        assertThat(result.size(), is(3));
        assertThat(result.get(0).getName(), is("BucketId"));
        assertThat(result.get(1).getName(), is("FullDetails"));
        assertThat(result.get(2).getName(), is("Priority"));
    }

    @Test
    public void removeOptionalParam_Test() {
        final Ds3Request result = removeOptionalParam(
                getRequestVerifyPhysicalPlacement(),
                "FullDetails",
                200,
                "PhysicalPlacementApiBean",
                null);

        assertThat(result.getOptionalQueryParams().size(), is(1));
        assertFalse(result.getOptionalQueryParams().get(0).getName().equals("FullDetails"));

        assertTrue(checkResponseCodes(result, 200, ".PhysicalPlacementApiBean"));
    }

    @Test
    public void makeRequiredParam_Test() {
        final Ds3Request result = makeRequiredParam(
                getRequestVerifyPhysicalPlacement(),
                NAMESPACE_FULL_DETAILS,
                "FullDetails",
                200,
                "BlobApiBeansContainer",
                null);

        assertTrue(result.getName().endsWith(".VerifyPhysicalPlacementForObjectsFullDetailsRequestHandler"));

        assertThat(result.getOptionalQueryParams().size(), is(1));
        assertFalse(result.getOptionalQueryParams().get(0).getName().equals("FullDetails"));

        assertThat(result.getRequiredQueryParams().size(), is(2));
        assertThat(result.getRequiredQueryParams().get(0).getName(), is("Operation"));
        assertThat(result.getRequiredQueryParams().get(1).getName(), is("FullDetails"));
        assertThat(result.getRequiredQueryParams().get(1).getType(), is("void"));

        assertTrue(checkResponseCodes(result, 200, ".BlobApiBeansContainer"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitGetTape_WrongRequest_Test() {
        splitGetTape(getRequestVerifyPhysicalPlacement());
    }

    @Test
    public void splitGetTape_Test() {
        final Ds3Request getTapeRequest = getRequestGetTape();

        final ImmutableList<Ds3Request> result = splitGetTape(getTapeRequest);
        assertThat(result.size(), is(2));

        //Verify that request without Full Details is as expected
        final Ds3Request withoutFullDetails = result.get(0);
        assertTrue(withoutFullDetails.getName().endsWith(".GetTapeRequestHandler"));
        assertThat(withoutFullDetails.getOptionalQueryParams().size(), is(0));
        assertThat(withoutFullDetails.getRequiredQueryParams(), is(nullValue()));
        assertTrue(checkResponseCodes(withoutFullDetails, 200, ".Tape"));

        //Verify that request with Full Details is as expected
        final Ds3Request withFullDetails = result.get(1);
        assertTrue(withFullDetails.getName().endsWith(".GetTapeFullDetailsRequestHandler"));
        assertThat(withFullDetails.getOptionalQueryParams().size(), is(0));
        assertThat(withFullDetails.getRequiredQueryParams().size(), is(1));
        assertThat(withFullDetails.getRequiredQueryParams().get(0).getName(), is("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().get(0).getType(), is("void"));
        assertTrue(checkResponseCodes(withFullDetails, 200, ".TapeApiBean"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitGetTapePartition_WrongRequest_Test() {
        splitGetTapePartition(getRequestVerifyPhysicalPlacement());
    }

    @Test
    public void splitGetTapePartition_Test() {
        final Ds3Request getTapePartitionRequest = getRequestGetTapePartition();

        final ImmutableList<Ds3Request> result = splitGetTapePartition(getTapePartitionRequest);
        assertThat(result.size(), is(2));

        //Verify that request without Full Details is as expected
        final Ds3Request withoutFullDetails = result.get(0);
        assertTrue(withoutFullDetails.getName().endsWith(".GetTapePartitionRequestHandler"));
        assertThat(withoutFullDetails.getOptionalQueryParams().size(), is(0));
        assertThat(withoutFullDetails.getRequiredQueryParams(), is(nullValue()));
        assertTrue(checkResponseCodes(withoutFullDetails, 200, ".TapePartition"));

        //Verify that request with Full Details is as expected
        final Ds3Request withFullDetails = result.get(1);
        assertTrue(withFullDetails.getName().endsWith(".GetTapePartitionFullDetailsRequestHandler"));
        assertThat(withFullDetails.getOptionalQueryParams().size(), is(0));
        assertThat(withFullDetails.getRequiredQueryParams().size(), is(1));
        assertThat(withFullDetails.getRequiredQueryParams().get(0).getName(), is("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().get(0).getType(), is("void"));
        assertTrue(checkResponseCodes(withFullDetails, 200, "$DetailedTapePartition"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitVerifyPhysicalPlacementForObjects_WrongRequest_Test() {
        splitVerifyPhysicalPlacementForObjects(getRequestGetNotification());
    }

    @Test
    public void splitVerifyPhysicalPlacementForObjects_Test() {
        final Ds3Request verifyPhysicalRequest = getRequestVerifyPhysicalPlacement();

        final ImmutableList<Ds3Request> result = splitVerifyPhysicalPlacementForObjects(verifyPhysicalRequest);
        assertThat(result.size(), is(2));

        //Verify that request without Full Details is as expected
        final Ds3Request withoutFullDetails = result.get(0);
        assertTrue(withoutFullDetails.getName().endsWith(".VerifyPhysicalPlacementForObjectsRequestHandler"));
        assertThat(withoutFullDetails.getOptionalQueryParams().size(), is(1));
        assertFalse(withoutFullDetails.getOptionalQueryParams().get(0).getName().equals("FullDetails"));
        assertThat(withoutFullDetails.getRequiredQueryParams().size(), is(1));
        assertFalse(withoutFullDetails.getRequiredQueryParams().get(0).getName().equals("FullDetails"));
        assertTrue(checkResponseCodes(withoutFullDetails, 200, ".PhysicalPlacementApiBean"));

        //Verify that request with Full Details is as expected
        final Ds3Request withFullDetails = result.get(1);
        assertTrue(withFullDetails.getName().endsWith(".VerifyPhysicalPlacementForObjectsFullDetailsRequestHandler"));
        assertThat(withFullDetails.getOptionalQueryParams().size(), is(1));
        assertFalse(withoutFullDetails.getOptionalQueryParams().get(0).getName().equals("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().size(), is(2));
        assertFalse(withFullDetails.getRequiredQueryParams().get(0).getName().equals("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().get(1).getName(), is("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().get(1).getType(), is("void"));
        assertTrue(checkResponseCodes(withFullDetails, 200, ".BlobApiBeansContainer"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitGetPhysicalPlacementForObjects_WrongRequest_Test() {
        splitGetPhysicalPlacementForObjects(getRequestVerifyPhysicalPlacement());
    }

    @Test
    public void splitGetPhysicalPlacementForObjects_Test() {
        final Ds3Request getPhysicalRequest = getRequestGetPhysicalPlacementForObjects();

        final ImmutableList<Ds3Request> result = splitGetPhysicalPlacementForObjects(getPhysicalRequest);
        assertThat(result.size(), is(2));

        //Verify that request without Full Details is as expected
        final Ds3Request withoutFullDetails = result.get(0);
        assertTrue(withoutFullDetails.getName().endsWith(".GetPhysicalPlacementForObjectsRequestHandler"));
        assertThat(withoutFullDetails.getOptionalQueryParams().size(), is(1));
        assertFalse(withoutFullDetails.getOptionalQueryParams().get(0).getName().equals("FullDetails"));
        assertThat(withoutFullDetails.getRequiredQueryParams().size(), is(1));
        assertFalse(withoutFullDetails.getRequiredQueryParams().get(0).getName().equals("FullDetails"));
        assertTrue(checkResponseCodes(withoutFullDetails, 200, ".PhysicalPlacementApiBean"));

        //Verify that request with Full Details is as expected
        final Ds3Request withFullDetails = result.get(1);
        assertTrue(withFullDetails.getName().endsWith(".GetPhysicalPlacementForObjectsFullDetailsRequestHandler"));
        assertThat(withFullDetails.getOptionalQueryParams().size(), is(1));
        assertFalse(withoutFullDetails.getOptionalQueryParams().get(0).getName().equals("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().size(), is(2));
        assertFalse(withFullDetails.getRequiredQueryParams().get(0).getName().equals("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().get(1).getName(), is("FullDetails"));
        assertThat(withFullDetails.getRequiredQueryParams().get(1).getType(), is("void"));
        assertTrue(checkResponseCodes(withFullDetails, 200, ".BlobApiBeansContainer"));
    }

    @Test
    public void hasMultipleResponseTypes_Test() {
        assertTrue(hasMultipleResponseTypes(getRequestVerifyPhysicalPlacement()));
        assertFalse(hasMultipleResponseTypes(getRequestDeleteNotification()));
        assertFalse(hasMultipleResponseTypes(getRequestGetNotification()));
    }

    @Test
    public void splitMultiResponseRequest_WrongRequest_Test() {
        ImmutableList<Ds3Request> result = splitMultiResponseRequest(getRequestDeleteNotification());
        assertThat(result.size(), is(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void splitMultiResponseRequest_UnknownRequest_Test() {
        final Ds3Request unknownRequest = new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.UnknownRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.MODIFY,
                Resource.BUCKET,
                ResourceType.NON_SINGLETON,
                Operation.GET_PHYSICAL_PLACEMENT,
                ImmutableList.of(
                        new Ds3ResponseCode(
                                200,
                                ImmutableList.of(
                                        new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.PhysicalPlacementApiBean", null),
                                        new Ds3ResponseType("com.spectralogic.s3.common.platform.domain.BlobApiBeansContainer", null)))),
                ImmutableList.of(
                        new Ds3Param("FullDetails", "void")),
                null);

        splitMultiResponseRequest(unknownRequest);
    }

    @Test
    public void splitMultiResponseRequest_GetPhysicalPlacement_Test() {
        final ImmutableList<Ds3Request> result = splitMultiResponseRequest(getRequestGetPhysicalPlacementForObjects());
        assertThat(result.size(), is(2));
        assertTrue(result.get(0).getName().endsWith(".GetPhysicalPlacementForObjectsRequestHandler"));
        assertTrue(result.get(1).getName().endsWith(".GetPhysicalPlacementForObjectsFullDetailsRequestHandler"));
    }

    @Test
    public void splitMultiResponseRequest_VerifyPhysicalPlacement_Test() {
        final ImmutableList<Ds3Request> result = splitMultiResponseRequest(getRequestVerifyPhysicalPlacement());
        assertThat(result.size(), is(2));
        assertTrue(result.get(0).getName().endsWith(".VerifyPhysicalPlacementForObjectsRequestHandler"));
        assertTrue(result.get(1).getName().endsWith(".VerifyPhysicalPlacementForObjectsFullDetailsRequestHandler"));
    }

    @Test
    public void splitMultiResponseRequest_GetTapePartition_Test() {
        final ImmutableList<Ds3Request> result = splitMultiResponseRequest(getRequestGetTapePartition());
        assertThat(result.size(), is(2));
        assertTrue(result.get(0).getName().endsWith(".GetTapePartitionRequestHandler"));
        assertTrue(result.get(1).getName().endsWith(".GetTapePartitionFullDetailsRequestHandler"));
    }

    @Test
    public void splitMultiResponseRequest_GetTape_Test() {
        final ImmutableList<Ds3Request> result = splitMultiResponseRequest(getRequestGetTape());
        assertThat(result.size(), is(2));
        assertTrue(result.get(0).getName().endsWith(".GetTapeRequestHandler"));
        assertTrue(result.get(1).getName().endsWith(".GetTapeFullDetailsRequestHandler"));
    }

    @Test
    public void splitAllMultiResponseRequests_RequestList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getRequestGetTape(),
                getRequestGetTapePartition(),
                getRequestGetPhysicalPlacementForObjects(),
                getRequestVerifyPhysicalPlacement(),
                getRequestDeleteNotification());

        final ImmutableList<Ds3Request> result = splitAllMultiResponseRequests(requests);
        assertThat(result.size(), is(9));
    }

    @Test
    public void splitAllMultiResponseRequests_Api_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getRequestGetTape(),
                getRequestGetTapePartition(),
                getRequestGetPhysicalPlacementForObjects(),
                getRequestVerifyPhysicalPlacement(),
                getRequestDeleteNotification());

        final Ds3ApiSpec result = splitAllMultiResponseRequests(new Ds3ApiSpec(requests, null));
        assertThat(result.getRequests().size(), is(9));
    }
}
