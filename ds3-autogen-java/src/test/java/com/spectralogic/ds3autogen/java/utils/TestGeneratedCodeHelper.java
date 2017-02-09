/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.java.utils;

import com.spectralogic.ds3autogen.api.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseName;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.toResponseParserName;
import static org.mockito.Mockito.when;

public final class TestGeneratedCodeHelper {

    public final static String CLIENT_PATH = "./ds3-sdk/src/main/java/com/spectralogic/ds3client/";
    public final static String PARSER_PATH = CLIENT_PATH + "commands/parsers/";

    public enum PathType { REQUEST, RESPONSE, RESPONSE_PARSER }

    private TestGeneratedCodeHelper() { }

    public static String getPathName(final String requestName, final String path, final PathType pathType) {
        final StringBuilder builder = new StringBuilder();
        builder.append(path);
        switch (pathType) {
            case REQUEST:
                builder.append(requestName);
                break;
            case RESPONSE:
                builder.append(toResponseName(requestName));
                break;
            case RESPONSE_PARSER:
                builder.append(toResponseParserName(requestName));
                break;
        }
        builder.append(".java");
        return builder.toString();
    }

    public static ByteArrayOutputStream setupOutputStream(
            final FileUtils fileUtils,
            final String pathName) throws IOException {
        final Path path = Paths.get(pathName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(path)).thenReturn(outputStream);
        return outputStream;
    }
}
