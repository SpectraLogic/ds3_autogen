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

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.python.model.request.ConstructorParam;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import org.junit.Test;

import static com.spectralogic.ds3autogen.python.generators.request.ObjectsPayloadGenerator.toPayloadAssignment;
import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class ObjectsPayloadGenerator_Test {

    private static final ObjectsPayloadGenerator generator = new ObjectsPayloadGenerator();

    @Test
    public void getAdditionalContent_OptionalFileObjectList_Test() {
        final String expected = "if object_list is not None:\n" +
                "      if not isinstance(object_list, FileObjectList):\n" +
                "        raise TypeError('com.spectralogic.s3.server.handler.reqhandler.spectrads3.tape.EjectStorageDomainRequestHandler should have request payload of type: FileObjectList')\n" +
                "      self.body = xmldom.tostring(object_list.to_xml())\n";
        final Ds3Request request = getEjectStorageDomainRequest();
        final String result = generator.getAdditionalContent(request, request.getName());
        assertThat(result, is(expected));
    }

    @Test
    public void getAdditionalContent_RequiredFileObjectList_Test() {
        final String expected = "if object_list is not None:\n" +
                "      if not isinstance(object_list, FileObjectList):\n" +
                "        raise TypeError('com.spectralogic.s3.server.handler.reqhandler.spectrads3.job.CreateGetJobRequestHandler should have request payload of type: FileObjectList')\n" +
                "      self.body = xmldom.tostring(object_list.to_xml())\n";
        final Ds3Request request = getRequestBulkGet();
        final String result = generator.getAdditionalContent(request, request.getName());
        assertThat(result, is(expected));
    }

    @Test
    public void getAdditionalContent_RequiredDeleteObjectList_Test() {
        final String expected = "if object_list is not None:\n" +
                "      if not isinstance(object_list, DeleteObjectList):\n" +
                "        raise TypeError('com.spectralogic.s3.server.handler.reqhandler.amazons3.DeleteObjectsRequestHandler should have request payload of type: DeleteObjectList')\n" +
                "      self.body = xmldom.tostring(object_list.to_xml())\n";
        final Ds3Request request = getRequestMultiFileDelete();
        final String result = generator.getAdditionalContent(request, request.getName());
        assertThat(result, is(expected));
    }

    @Test
    public void getAdditionalContent_RequiredPartList_Test() {
        final String expected = "if part_list is not None:\n" +
                "      if not isinstance(part_list, PartList):\n" +
                "        raise TypeError('com.spectralogic.s3.server.handler.reqhandler.amazons3.CompleteMultiPartUploadRequestHandler should have request payload of type: PartList')\n" +
                "      self.body = xmldom.tostring(part_list.to_xml())\n";
        final Ds3Request request = getCompleteMultipartUploadRequest();
        final String result = generator.getAdditionalContent(request, request.getName());
        assertThat(result, is(expected));
    }

    @Test (expected = IllegalArgumentException.class)
    public void getAdditionalContent_Error_Test() {
        final Ds3Request request = getRequestCreateNotification();
        generator.getAdditionalContent(request, request.getName());
    }

    @Test
    public void toPayloadAssignment_Test() {
        final String expected = "if PayloadName is not None:\n" +
                "      if not isinstance(PayloadName, PayloadType):\n" +
                "        raise TypeError('RequestName should have request payload of type: PayloadType')\n" +
                "      self.body = xmldom.tostring(PayloadName.to_xml())\n";

        final String result = toPayloadAssignment("RequestName", "PayloadName", "PayloadType");
        assertThat(result, is(expected));
    }

    @Test
    public void toOptionalConstructorParams_NoAdditions_Test() {
        final ImmutableList<ConstructorParam> optParams = generator
                .toOptionalConstructorParams(getCreateMultiPartUploadPart());
        final ImmutableList<String> result = optParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());
        assertThat(result, not(hasItem("object_list=None")));
    }

    @Test
    public void toOptionalConstructorParams_Test() {
        final ImmutableList<ConstructorParam> optParams = generator
                .toOptionalConstructorParams(getEjectStorageDomainRequest());
        final ImmutableList<String> result = optParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());
        assertThat(result, hasItem("object_list=None"));
    }

    @Test
    public void toRequiredConstructorParams_NoAdditions_Test() {
        final ImmutableList<ConstructorParam> reqParams = generator
                .toRequiredConstructorParams(getEjectStorageDomainRequest());
        final ImmutableList<String> result = reqParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());
        assertThat(result, not(hasItem("object_list")));
        assertThat(result, not(hasItem("part_list")));
    }

    @Test
    public void toRequiredConstructorParams_FileObjectList_Test() {
        final ImmutableList<ConstructorParam> reqParams = generator
                .toRequiredConstructorParams(getRequestBulkPut());
        final ImmutableList<String> result = reqParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());
        assertThat(result, hasItem("object_list"));
        assertThat(result, not(hasItem("part_list")));
    }

    @Test
    public void toRequiredConstructorParams_MultiFileDelete_Test() {
        final ImmutableList<ConstructorParam> reqParams = generator
                .toRequiredConstructorParams(getRequestMultiFileDelete());
        final ImmutableList<String> result = reqParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());
        assertThat(result, hasItem("object_list"));
        assertThat(result, not(hasItem("part_list")));
    }

    @Test
    public void toRequiredConstructorParams_MultiPartUpload_Test() {
        final ImmutableList<ConstructorParam> reqParams = generator
                .toRequiredConstructorParams(getCompleteMultipartUploadRequest());
        final ImmutableList<String> result = reqParams.stream()
                .map(ConstructorParam::toPythonCode)
                .collect(GuavaCollectors.immutableList());
        assertThat(result, not(hasItem("object_list")));
        assertThat(result, hasItem("part_list"));
    }
}
