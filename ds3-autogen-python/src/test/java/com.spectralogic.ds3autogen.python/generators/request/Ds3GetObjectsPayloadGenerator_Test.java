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

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getRequestBulkGet;
import static org.assertj.core.api.Assertions.assertThat;

public class Ds3GetObjectsPayloadGenerator_Test {

    private static final Ds3GetObjectsPayloadGenerator generator = new Ds3GetObjectsPayloadGenerator();

    @Test
    public void getPayloadElementTypeTest() {
        assertThat(generator.getPayloadElementType()).isEqualTo("Ds3GetObject");
    }

    @Test
    public void getPayloadListTypeTest() {
        assertThat(generator.getPayloadListType()).isEqualTo("Ds3GetObjectList");
    }

    @Test
    public void getRequestPayloadNameTest() {
        assertThat(generator.getRequestPayloadName()).isEqualTo("object_list");
    }

    @Test
    public void toRequiredConstructorParamsTest() {
        assertThat(generator.toRequiredConstructorParams(getRequestBulkGet()))
                .extracting("name", "optional")
                .containsExactlyInAnyOrder(
                        Tuple.tuple("bucket_name", false),
                        Tuple.tuple("object_list", false));
    }

    @Test
    public void Test() {
        assertThat(generator.getAdditionalContent("GetBulkJobSpectraS3Request"))
                .isEqualTo("if object_list is not None:\n" +
                        "            if not (isinstance(cur_obj, Ds3GetObject) for cur_obj in object_list):\n" +
                        "                raise TypeError('GetBulkJobSpectraS3Request should have request payload of type: list of Ds3GetObject')\n" +
                        "            xml_object_list = Ds3GetObjectList(object_list)\n" +
                        "            self.body = xmldom.tostring(xml_object_list.to_xml())\n");
    }
}
