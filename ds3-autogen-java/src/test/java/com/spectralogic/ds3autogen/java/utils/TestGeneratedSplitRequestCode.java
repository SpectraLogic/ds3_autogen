/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
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

import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.java.JavaCodeGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.java.utils.TestGeneratedCodeHelper.getPathName;
import static com.spectralogic.ds3autogen.java.utils.TestGeneratedCodeHelper.setupOutputStream;
import static com.spectralogic.ds3autogen.utils.MultiResponseSplitConverter.nameSpaceSplitRequest;
import static com.spectralogic.ds3autogen.utils.ProjectConstants.SPECTRA_S3_NAMESPACING;

/**
 * Used to generate code for Ds3Requests that are split into multiple requests during
 * code generation because the original request had multiple payloads for a give response
 * code. This is used during testing.
 */
public class TestGeneratedSplitRequestCode extends TestGeneratedCode {

    private final ByteArrayOutputStream splitRequestOutputStream;
    private final ByteArrayOutputStream splitResponseOutputStream;

    private String splitRequestGeneratedCode;
    private String splitResponseGeneratedCode;

    public TestGeneratedSplitRequestCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path,
            final String nameSpacing) throws IOException {
        super(fileUtils, requestName, path);

        final String splitRequestName = renameRequestForCodegen(requestName, nameSpacing);
        this.splitRequestOutputStream = setupOutputStream(fileUtils, getPathName(splitRequestName, path, TestGeneratedCodeHelper.PathType.REQUEST));
        this.splitResponseOutputStream = setupOutputStream(fileUtils, getPathName(splitRequestName, path, TestGeneratedCodeHelper.PathType.RESPONSE));
    }

    private String renameRequestForCodegen(final String requestName, final String nameSpacing) {
        if (requestName.contains(SPECTRA_S3_NAMESPACING)) {
            return requestName.replace(SPECTRA_S3_NAMESPACING, nameSpacing + SPECTRA_S3_NAMESPACING);
        }
        return nameSpaceSplitRequest(requestName, nameSpacing);
    }

    @Override
    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws IOException, ParserException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGeneratedSplitRequestCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        requestGeneratedCode = new String(requestOutputStream.toByteArray());
        responseGeneratedCode = new String(responseOutputStream.toByteArray());

        splitRequestGeneratedCode = new String(splitRequestOutputStream.toByteArray());
        splitResponseGeneratedCode = new String(splitResponseOutputStream.toByteArray());

        ds3ClientGeneratedCode = new String(ds3ClientOutputStream.toByteArray());
        ds3ClientImplGeneratedCode = new String(ds3ClientImplOutputStream.toByteArray());
    }


    public String getSplitRequestGeneratedCode() {
        return splitRequestGeneratedCode;
    }

    public String getSplitResponseGeneratedCode() {
        return splitResponseGeneratedCode;
    }
}
