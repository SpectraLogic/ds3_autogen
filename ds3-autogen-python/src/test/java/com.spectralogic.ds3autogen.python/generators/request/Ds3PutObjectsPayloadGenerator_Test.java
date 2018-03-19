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

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestBulkPut;
import static org.assertj.core.api.Assertions.assertThat;

public class Ds3PutObjectsPayloadGenerator_Test {

    private static final Ds3PutObjectsPayloadGenerator generator = new Ds3PutObjectsPayloadGenerator();

    @Test
    public void getPayloadElementTypeTest() {
        assertThat(generator.getPayloadElementType()).isEqualTo("Ds3PutObject");
    }

    @Test
    public void getPayloadListTypeTest() {
        assertThat(generator.getPayloadListType()).isEqualTo("Ds3PutObjectList");
    }

    @Test
    public void getRequestPayloadNameTest() {
        assertThat(generator.getRequestPayloadName()).isEqualTo("object_list");
    }

    @Test
    public void toRequiredConstructorParamsTest() {
        assertThat(generator.toRequiredConstructorParams(getRequestBulkPut()))
                .extracting("name", "optional")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("bucket_name", false),
                        Tuple.tuple("object_list", false));
    }

    @Test
    public void Test() {
        assertThat(generator.getAdditionalContent("PutBulkJobSpectraS3Request"))
                .isEqualTo("if object_list is not None:\n" +
                        "            if not (isinstance(cur_obj, Ds3PutObject) for cur_obj in object_list):\n" +
                        "                raise TypeError('PutBulkJobSpectraS3Request should have request payload of type: list of Ds3PutObject')\n" +
                        "            xml_object_list = Ds3PutObjectList(object_list)\n" +
                        "            self.body = xmldom.tostring(xml_object_list.to_xml())\n");
    }
}
