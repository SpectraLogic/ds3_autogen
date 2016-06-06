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

package com.spectralogic.ds3autogen.python.generators.request;

import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.RequestPayload;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.generators.request.ObjectsPayloadGenerator.toObjectsRequestPayload;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ObjectsPayloadGenerator_Test {

    private static final ObjectsPayloadGenerator generator = new ObjectsPayloadGenerator();

    @Test
    public void toRequestPayload_OptionalFileObjectList_Test() {
        final Ds3Request request = getEjectStorageDomainRequest();
        final RequestPayload result = generator.toRequestPayload(request, request.getName());
        assertThat(result.getName(), is("object_list"));
        assertThat(result.isOptional(), is(true));
    }

    @Test
    public void toRequestPayload_RequiredFileObjectList_Test() {
        final Ds3Request request = getRequestBulkGet();
        final RequestPayload result = generator.toRequestPayload(request, request.getName());
        assertThat(result.getName(), is("object_list"));
        assertThat(result.isOptional(), is(false));
    }

    @Test
    public void toRequestPayload_RequiredDeleteObjectList_Test() {
        final Ds3Request request = getRequestMultiFileDelete();
        final RequestPayload result = generator.toRequestPayload(request, request.getName());
        assertThat(result.getName(), is("object_list"));
        assertThat(result.isOptional(), is(false));
    }

    @Test
    public void toRequestPayload_RequiredPartList_Test() {
        final Ds3Request request = getCompleteMultipartUploadRequest();
        final RequestPayload result = generator.toRequestPayload(request, request.getName());
        assertThat(result.getName(), is("part_list"));
        assertThat(result.isOptional(), is(false));
    }

    @Test (expected = IllegalArgumentException.class)
    public void toRequestPayload_Error_Test() {
        final Ds3Request request = getRequestCreateNotification();
        generator.toRequestPayload(request, request.getName());
    }

    @Test
    public void toObjectsRequestPayload_Test() {
        final String expected = "if PayloadName is not None:\n" +
                "      if PayloadName not isinstance(PayloadName, PayloadType):\n" +
                "        raise TypeError('RequestName should have request payload of type: PayloadType')\n" +
                "      self.body = xmldom.tostring(PayloadName.to_xml())";

        final RequestPayload result = toObjectsRequestPayload("RequestName", "PayloadName", "PayloadType", true);
        assertThat(result.getName(), is("PayloadName"));
        assertThat(result.getAssignmentCode(), is(expected));
    }
}
