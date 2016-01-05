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

package com.spectralogic.ds3autogen.utils.test.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class provides utilities for testing the Multi Response Split Converter utility
 */
public final class SplitConverterTestHelper {

    private SplitConverterTestHelper() { }

    private static final Logger LOG = LoggerFactory.getLogger(SplitConverterTestHelper.class);

    /**
     * Creates a populated list of Ds3ResponseTypes, where one response type is typical, another has
     * a typename with a '$' character in it, and the final has a component type.
     */
    public static ImmutableList<Ds3ResponseType> getPopulatedDs3ResponseTypeList() {
        return ImmutableList.of(
                new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.tape.TapePartition", null),
                new Ds3ResponseType("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.GetTapePartitionRequestHandler$DetailedTapePartition", null),
                new Ds3ResponseType("array", "com.spectralogic.s3.common.dao.domain.tape.TapePartition"));
    }

    /**
     * Creates a populated list of Ds3ResponseCodes, where there are three response codes with
     * values 200, 204, and 409.
     */
    public static ImmutableList<Ds3ResponseCode> getPopulatedDs3ResponseCodeList() {
        return ImmutableList.of(
                new Ds3ResponseCode(200, getPopulatedDs3ResponseTypeList()),
                new Ds3ResponseCode(204, getPopulatedDs3ResponseTypeList()),
                new Ds3ResponseCode(409, ImmutableList.of(
                        new Ds3ResponseType("com.spectralogic.s3.server.domain.HttpErrorResultApiBean", null))));
    }

    /**
     * Creates a populated list of Params which consists of BucketId, FullDetails and Priority
     */
    public static ImmutableList<Ds3Param> getPopulatedDs3ParamList() {
        return ImmutableList.of(
                new Ds3Param("BucketId", "java.util.UUID"),
                new Ds3Param("FullDetails", "void"),
                new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority"));
    }

    /**
     * Checks if a modified request has expected response codes. This includes checking if
     * all response codes contain only one response type. It also checks the response type
     * of a specific response code.
     * @param request A Ds3Request
     * @param code The response code to verify
     * @param expectedResponseType The end of the expected response type of the specified
     *                             response code, such as .PhysicalPlacementApiBean
     */
    public static boolean checkResponseCodes(final Ds3Request request, final int code, final String expectedResponseType) {
        for (final Ds3ResponseCode responseCode : request.getDs3ResponseCodes()) {
            if (responseCode.getDs3ResponseTypes().size() != 1) {
                LOG.debug("Request has more than one response type for response code " + code + ": " + request.getName());
                return false;
            }
            if (responseCode.getCode() == code
                    && !responseCode.getDs3ResponseTypes().get(0).getType().endsWith(expectedResponseType)) {
                LOG.debug("Expected ResponseType that ends with " + expectedResponseType
                        + ", but got response type " + responseCode.getDs3ResponseTypes().get(0).getType());
                return false;
            }
        }
        return true;
    }

    /**
     * Creates the GetTapeRequestHandler Ds3Request
     */
    public static Ds3Request getRequestGetTape() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.GetTapeRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.SHOW,
                Resource.TAPE,
                ResourceType.NON_SINGLETON,
                null,
                ImmutableList.of(
                        new Ds3ResponseCode(
                                200,
                                ImmutableList.of(
                                        new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.tape.Tape", null),
                                        new Ds3ResponseType("com.spectralogic.s3.server.domain.TapeApiBean", null)))),
                ImmutableList.of(
                        new Ds3Param("FullDetails", "void")),
                null);
    }

    /**
     * Creates the GetTapePartitionRequestHandler Ds3Request
     */
    public static Ds3Request getRequestGetTapePartition() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.GetTapePartitionRequestHandler",
                null,
                Classification.spectrads3,
                null,
                null,
                Action.SHOW,
                Resource.TAPE_PARTITION,
                ResourceType.NON_SINGLETON,
                null,
                ImmutableList.of(
                        new Ds3ResponseCode(
                                200,
                                ImmutableList.of(
                                        new Ds3ResponseType("com.spectralogic.s3.common.dao.domain.tape.TapePartition", null),
                                        new Ds3ResponseType("com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.GetTapePartitionRequestHandler$DetailedTapePartition", null)))),
                ImmutableList.of(
                        new Ds3Param("FullDetails", "void")),
                null);
    }

    /**
     * Creates the GetPhysicalPlacementForObjects Ds3Request
     */
    public static Ds3Request getRequestGetPhysicalPlacementForObjects() {
        return new Ds3Request(
                "com.spectralogic.s3.server.handler.reqhandler.spectrads3.object.GetPhysicalPlacementForObjectsRequestHandler",
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
                        new Ds3Param("FullDetails", "void"),
                        new Ds3Param("StorageDomainId", "java.util.UUID")),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType")));
    }

}
