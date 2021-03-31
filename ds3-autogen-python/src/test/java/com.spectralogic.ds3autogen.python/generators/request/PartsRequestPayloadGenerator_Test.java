/*
 * ******************************************************************************
 *   Copyright 2014-2018 Spectra Logic Corporation. All Rights Reserved.
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

import org.assertj.core.groups.Tuple;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getCompleteMultipartUploadRequest;
import static org.assertj.core.api.Assertions.assertThat;

public class PartsRequestPayloadGenerator_Test {

    private static final PartsRequestPayloadGenerator generator = new PartsRequestPayloadGenerator();

    @Test
    public void getPayloadElementTypeTest() {
        assertThat(generator.getPayloadElementType()).isEqualTo("Part");
    }

    @Test
    public void getPayloadListTypeTest() {
        assertThat(generator.getPayloadListType()).isEqualTo("PartList");
    }

    @Test
    public void getRequestPayloadNameTest() {
        assertThat(generator.getRequestPayloadName()).isEqualTo("part_list");
    }

    @Test
    public void toRequiredConstructorParamsTest() {
        assertThat(generator.toRequiredConstructorParams(getCompleteMultipartUploadRequest()))
                .extracting("name", "optional")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("bucket_name", false),
                        Tuple.tuple("object_name", false),
                        Tuple.tuple("part_list", false),
                        Tuple.tuple("upload_id", false));
    }

    @Test
    public void Test() {
        assertThat(generator.getAdditionalContent("CompleteMultiPartUploadRequest"))
                .isEqualTo("if part_list is not None:\n" +
                        "            if not (isinstance(cur_obj, Part) for cur_obj in part_list):\n" +
                        "                raise TypeError('CompleteMultiPartUploadRequest should have request payload of type: list of Part')\n" +
                        "            xml_object_list = PartList(part_list)\n" +
                        "            self.body = xmldom.tostring(xml_object_list.to_xml())\n");
    }
}
