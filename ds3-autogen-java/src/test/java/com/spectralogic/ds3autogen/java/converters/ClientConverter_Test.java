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

import com.spectralogic.ds3autogen.api.models.enums.Action;
import com.spectralogic.ds3autogen.api.models.enums.Resource;
import com.spectralogic.ds3autogen.java.models.AnnotationInfo;
import org.junit.Test;

import static com.spectralogic.ds3autogen.java.converters.ClientConverter.toAnnotationInfo;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class ClientConverter_Test {

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
}
