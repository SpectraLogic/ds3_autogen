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

import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;

import static com.spectralogic.ds3autogen.python.helpers.PythonHelper.pythonIndent;

public class HeadObjectResponseGenerator extends HeadResponseGenerator {

    /**
     * Gets the python code that will parse the response payload
     */
    @Override
    public String toParseResponsePayload(final Ds3Request ds3Request) {
        return "if self.response.status == 200:\n" +
                pythonIndent(3) + "self.__process_checksum_headers(response.getheaders())\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.EXISTS\n" +
                pythonIndent(2) + "elif self.response.status == 403:\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.NOTAUTHORIZED\n" +
                pythonIndent(2) + "elif self.response.status == 404:\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.DOESNTEXIST\n" +
                pythonIndent(2) + "else:\n" +
                pythonIndent(3) + "self.result = HeadRequestStatus.UNKNOWN";
    }

    @Override
    public String toInitResponse() {
        return "def __init__(self, response, request):\n" +
                pythonIndent(2) + "self.blob_checksums = {}\n" +
                pythonIndent(2) + "self.blob_checksum_type = 'NONE'\n" +
                pythonIndent(2) + "super(HeadObjectResponse, self).__init__(response, request)\n\n" +

                pythonIndent(1) + "def __process_checksum_headers(self, headers):\n" +
                pythonIndent(2) + "\"\"\"\n" +
                pythonIndent(2) + "Processes the blob checksum headers.\n" +
                pythonIndent(2) + ":param headers: list of tuples containing the Http response headers\n" +
                pythonIndent(2) + "\"\"\"\n" +
                pythonIndent(2) + "self.__process_checksum_type(headers)\n" +
                pythonIndent(2) + "self.__process_blob_checksums(headers)\n\n" +

                pythonIndent(1) + "def __process_checksum_type(self, headers):\n" +
                pythonIndent(2) + "\"\"\"\n" +
                pythonIndent(2) + "Parses the blob checksum type header. If there is no header, the default is NONE.\n" +
                pythonIndent(2) + "If there are multiple headers, then an error is raised\n" +
                pythonIndent(2) + ":param headers: list of tuples containing the Http response headers\n" +
                pythonIndent(2) + "\"\"\"\n" +
                pythonIndent(2) + "checksum_type_header = [item for item in headers if item[0] == 'ds3-blob-checksum-type']\n" +
                pythonIndent(2) + "if len(checksum_type_header) == 0:\n" +
                pythonIndent(3) + "return\n" +
                pythonIndent(2) + "if len(checksum_type_header) > 1:\n" +
                pythonIndent(3) + "raise ValueError(\"Expected only one header with key 'ds3-blob-checksum-type' but got: \" + str(checksum_type_header))\n" +
                pythonIndent(2) + "self.blob_checksum_type = checksum_type_header[0][1]\n\n" +

                pythonIndent(1) + "def __process_blob_checksums(self, headers):\n" +
                pythonIndent(2) + "\"\"\"\n" +
                pythonIndent(2) + "Parses the blob checksum headers and adds them to a dictionary which maps\n" +
                pythonIndent(2) + "blob offset to blob checksum.\n" +
                pythonIndent(2) + ":param headers: list of tuples containing the Http response headers\n" +
                pythonIndent(2) + "\"\"\"\n" +
                pythonIndent(2) + "# Retrieves all the headers that start with 'ds3-blob-checksum-offset-'\n" +
                pythonIndent(2) + "# and converts the offset at the end of the header key into an integer.\n" +
                pythonIndent(2) + "checksum_list = [(int(key[25:]), val) for key, val in headers if key.startswith('ds3-blob-checksum-offset-')]\n" +
                pythonIndent(2) + "self.blob_checksums = dict(checksum_list)\n";
    }
}
