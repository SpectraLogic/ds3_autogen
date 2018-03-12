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

package com.spectralogic.ds3autogen.python3.generators.request;

import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.clearSuspectBlobPoolsRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class P3IdListRequestGenerator_Test {

    private static final P3IdListRequestGenerator generator = new P3IdListRequestGenerator();

    @Test
    public void getAdditionalContent_Test() {
        final String expected = "if id_list is not None:\n" +
                "            if not (isinstance(cur_id, str) for cur_id in id_list):\n" +
                "                raise TypeError(\n" +
                "                    'ClearSuspectBlobPoolsSpectraS3Request should have request payload of type: list of strings')\n" +
                "            xml_id_list = IdsList(id_list)\n" +
                "            self.body = xmldom.tostring(xml_id_list.to_xml())\n";
        final String result = generator.getAdditionalContent(clearSuspectBlobPoolsRequest(), "ClearSuspectBlobPoolsSpectraS3Request");
        assertThat(result, is(expected));
    }
}
