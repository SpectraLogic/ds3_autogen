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

package com.spectralogic.ds3autogen.python.generators.response;

import org.junit.Test;

import static com.spectralogic.ds3autogen.testutil.Ds3ModelFixtures.getHeadObjectRequest;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HeadObjectResponseGenerator_Test {
    private final static HeadObjectResponseGenerator generator = new HeadObjectResponseGenerator();

    @Test
    public void toParseResponsePayloadTest() {
        final String expected = "if self.response.status == 200:\n" +
                "            self.__process_checksum_headers(response.getheaders())\n" +
                "            self.result = HeadRequestStatus.EXISTS\n" +
                "        elif self.response.status == 403:\n" +
                "            self.result = HeadRequestStatus.NOTAUTHORIZED\n" +
                "        elif self.response.status == 404:\n" +
                "            self.result = HeadRequestStatus.DOESNTEXIST\n" +
                "        else:\n" +
                "            self.result = HeadRequestStatus.UNKNOWN";

        assertThat(generator.toParseResponsePayload(getHeadObjectRequest()), is(expected));
    }

    @Test
    public void toInitResponseTest() {
        final String expected = "def __init__(self, response, request):\n" +
                "        self.blob_checksums = {}\n" +
                "        self.blob_checksum_type = 'NONE'\n" +
                "        super(HeadObjectResponse, self).__init__(response, request)\n" +
                "\n" +
                "    def __process_checksum_headers(self, headers):\n" +
                "        \"\"\"\n" +
                "        Processes the blob checksum headers.\n" +
                "        :param headers: list of tuples containing the Http response headers\n" +
                "        \"\"\"\n" +
                "        self.__process_checksum_type(headers)\n" +
                "        self.__process_blob_checksums(headers)\n" +
                "\n" +
                "    def __process_checksum_type(self, headers):\n" +
                "        \"\"\"\n" +
                "        Parses the blob checksum type header. If there is no header, the default is NONE.\n" +
                "        If there are multiple headers, then an error is raised\n" +
                "        :param headers: list of tuples containing the Http response headers\n" +
                "        \"\"\"\n" +
                "        checksum_type_header = [item for item in headers if item[0] == 'ds3-blob-checksum-type']\n" +
                "        if len(checksum_type_header) == 0:\n" +
                "            return\n" +
                "        if len(checksum_type_header) > 1:\n" +
                "            raise ValueError(\"Expected only one header with key 'ds3-blob-checksum-type' but got: \" + str(checksum_type_header))\n" +
                "        self.blob_checksum_type = checksum_type_header[0][1]\n" +
                "\n" +
                "    def __process_blob_checksums(self, headers):\n" +
                "        \"\"\"\n" +
                "        Parses the blob checksum headers and adds them to a dictionary which maps\n" +
                "        blob offset to blob checksum.\n" +
                "        :param headers: list of tuples containing the Http response headers\n" +
                "        \"\"\"\n" +
                "        # Retrieves all the headers that start with 'ds3-blob-checksum-offset-'\n" +
                "        # and converts the offset at the end of the header key into an integer.\n" +
                "        checksum_list = [(int(key[25:]), val) for key, val in headers if key.startswith('ds3-blob-checksum-offset-')]\n" +
                "        self.blob_checksums = dict(checksum_list)\n";

        assertThat(generator.toInitResponse(), is(expected));
    }
}
