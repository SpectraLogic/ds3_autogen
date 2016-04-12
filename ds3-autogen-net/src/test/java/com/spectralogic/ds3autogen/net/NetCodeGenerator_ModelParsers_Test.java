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

package com.spectralogic.ds3autogen.net;

import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.net.utils.TestGenerateCode;
import freemarker.template.TemplateModelException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class NetCodeGenerator_ModelParsers_Test {

    private final static Logger LOG = LoggerFactory.getLogger(NetCodeGenerator_Test.class);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void bucketObjectApiBean_Test() throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException, TemplateModelException {
        final FileUtils fileUtils = mock(FileUtils.class);
        final TestGenerateCode codeGenerator = new TestGenerateCode(
                fileUtils,
                "GetBucketRequest",
                "./Ds3/Calls/",
                "ListBucketResult");

        codeGenerator.generateCode(fileUtils, "/input/getBucketRequest.xml");
        final String typeParserCode = codeGenerator.getTypeParser();

        LOG.info("Generated code:\n" + typeParserCode);
    }
}
