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

package com.spectralogic.ds3autogen.java.test.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Param;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.*;

public final class RequestGeneratorTestHelper {

    private RequestGeneratorTestHelper() { }

    /**
     * Creates a populated Ds3Request to be used by the Java generators
     * for testing.
     */
    public static Ds3Request createSimpleTestDs3Request() {
        return new Ds3Request(
                "RequestName",
                HttpVerb.DELETE,
                Classification.spectrads3,
                Requirement.REQUIRED,
                Requirement.REQUIRED,
                Action.BULK_DELETE,
                Resource.JOB,
                ResourceType.NON_SINGLETON,
                Operation.START_BULK_GET,
                true,
                null,
                ImmutableList.of(
                        new Ds3Param("RequestType", "com.spectralogic.s3.common.dao.domain.ds3.JobRequestType", false)),
                ImmutableList.of(
                        new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false),
                        new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false),
                        new Ds3Param("NotificationEndPoint", "java.lang.String", true)));
    }

    /**
     * Creates a populated list of Ds3Params which contain a variety of types for
     * testing the request generators.
     */
    public static ImmutableList<Ds3Param> createTestDs3ParamList() {
        return ImmutableList.of(
                new Ds3Param("IgnoreNamingConflicts", "void", false),
                new Ds3Param("MaxUploadSize", "long", false),
                new Ds3Param("Name", "java.lang.String", true),
                new Ds3Param("Operation", "com.spectralogic.s3.server.request.rest.RestOperationType", false),
                new Ds3Param("Priority", "com.spectralogic.s3.common.dao.domain.ds3.BlobStoreTaskPriority", false));
    }
}
