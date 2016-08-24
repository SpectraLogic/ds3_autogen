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

package com.spectralogic.ds3autogen.python.generators.response;

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getObjectsDetailsRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PaginationResponseGenerator_Test {

    private final static PaginationResponseGenerator generator = new PaginationResponseGenerator();

    @Test
    public void toInitResponse_Test() {
        final String expected = "def __init__(self, response, request):\n" +
                "    self.paging_truncated = None\n" +
                "    self.paging_total_result_count = None\n" +
                "    super(self.__class__, self).__init__(response, request)\n";
        assertThat(generator.toInitResponse(), is(expected));
    }

    @Test
    public void toParseResponsePayload_GetObjects_Test() {
        final String expected = "if self.response.status == 200:\n" +
                "      self.result = parseModel(xmldom.fromstring(response.read()), array())\n" +
                "      self.paging_truncated = self.parse_int_header('page-truncated', response.getheaders())\n" +
                "      self.paging_total_result_count = self.parse_int_header('total-result-count', response.getheaders())";

        final Ds3Request ds3Request = getObjectsDetailsRequest();
        assertThat(generator.toParseResponsePayload(ds3Request), is(expected));
    }
}
