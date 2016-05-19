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

package com.spectralogic.ds3autogen.python;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.python.generators.request.BaseRequestGenerator;
import com.spectralogic.ds3autogen.python.model.request.BaseRequest;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.PythonCodeGenerator.*;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestCreateNotification;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PythonCodeGenerator_Test {

    @Test
    public void getRequestGenerator_Test() {
        assertThat(getRequestGenerator(getRequestCreateNotification()), instanceOf(BaseRequestGenerator.class));
        //TODO add additional tests as requests are special cased
    }

    @Test
    public void toRequestModel_Test() {
        final BaseRequest result = toRequestModel(getRequestCreateNotification());
        assertThat(result.getName(), is("CreateJobCreatedNotificationRegistrationRequestHandler"));
        assertThat(result.getPath(), is("'/_rest_/job_created_notification_registration'"));
        assertThat(result.getOperation(), is(nullValue()));
        assertThat(result.getRequiredArgs().size(), is(1));
        assertThat(result.getOptionalArgs().size(), is(3));
        assertThat(result.getVoidArgs().size(), is(0));
    }

    @Test
    public void toRequestModelList_NullList_Test() {
        final ImmutableList<BaseRequest> result = toRequestModelList(null);
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequestModelList_EmptyList_Test() {
        final ImmutableList<BaseRequest> result = toRequestModelList(ImmutableList.of());
        assertThat(result.size(), is(0));
    }

    @Test
    public void toRequestModelList_FullList_Test() {
        final ImmutableList<Ds3Request> requests = ImmutableList.of(
                getRequestCreateNotification());

        final ImmutableList<BaseRequest> result = toRequestModelList(requests);
        assertThat(result.size(), is(1));
        assertThat(result.get(0).getName(), is("CreateJobCreatedNotificationRegistrationRequestHandler"));
    }
}
