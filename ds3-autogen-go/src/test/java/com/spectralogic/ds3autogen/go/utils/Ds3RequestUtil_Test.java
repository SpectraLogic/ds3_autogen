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

package com.spectralogic.ds3autogen.go.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.enums.Resource;
import org.junit.Test;

import static com.spectralogic.ds3autogen.go.utils.Ds3RequestUtilKt.getGoArgFromResource;
import static com.spectralogic.ds3autogen.go.utils.Ds3RequestUtilKt.isGoResourceAnArg;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Ds3RequestUtil_Test {

    private static Ds3Request createTestRequest(final Resource resource, final boolean includeInPath) {
        return new Ds3Request(
                "com.test.TestRequest",
                null,
                Classification.amazons3,
                null,
                null,
                null,
                resource,
                null,
                null,
                includeInPath,
                null,
                null,
                null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getGoArgFromResource_ExceptionTest() {
        getGoArgFromResource(createTestRequest(Resource.CAPACITY_SUMMARY, true));
    }

    @Test
    public void getGoArgFromResource_Test() {
        final ImmutableList<Arguments> expectedArgs = ImmutableList.of(
                new Arguments("String", "NotificationId"),
                new Arguments("String", "BucketName"),
                new Arguments("String", "JobId"),
                new Arguments("String", "ActiveJobId")
        );

        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                createTestRequest(Resource.DS3_TARGET_FAILURE_NOTIFICATION_REGISTRATION, true),
                createTestRequest(Resource.BUCKET, true),
                createTestRequest(Resource.JOB, true),
                createTestRequest(Resource.ACTIVE_JOB, true)
        );

        assertThat(requests).hasSize(expectedArgs.size());
        for (int i = 0; i < requests.size(); i++) {
            final Arguments result = getGoArgFromResource(requests.get(i));
            assertThat(result.getName()).isEqualTo(expectedArgs.get(i).getName());
            assertThat(result.getType()).isEqualTo(expectedArgs.get(i).getType());
        }
    }

    @Test
    public void isGoResourceAnArg_Test() {
        assertFalse(isGoResourceAnArg(createTestRequest(null, true)));
        assertFalse(isGoResourceAnArg(createTestRequest(null, false)));
        assertFalse(isGoResourceAnArg(createTestRequest(Resource.ACTIVE_JOB, false)));

        assertTrue(isGoResourceAnArg(createTestRequest(Resource.ACTIVE_JOB, true)));
    }
}
