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

package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.*;
import com.spectralogic.ds3autogen.c.models.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCodeGenerator implements CodeGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(CCodeGenerator.class);

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private Ds3ApiSpec spec;
    private FileUtils fileUtils;
    private Path outputDirectory;

    public CCodeGenerator() {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(CCodeGenerator.class, "/templates");
    }
    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) throws IOException {
        this.spec = spec;
        this.fileUtils = fileUtils;
        this.outputDirectory = destDir;

        try {
            generateCommands();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void generateCommands() throws IOException, TemplateException {
        for (final Ds3Request request : spec.getRequests()) {
            generateRequest(request);
        }

        if (null != spec.getTypes()) {
            for ( Map.Entry<String, Ds3Type> typeEntry : spec.getTypes().entrySet()) {
                System.out.println("Generating Type[" + typeEntry.getKey() + "][" + typeEntry.getValue() + "]");
            }
        }
    }

    public void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        Template requestTemplate = null;
        Request request = null;

        System.out.println("Generating Request[" + ds3Request.getName() + "]");
        if (ds3Request.getClassification() == Classification.amazons3) {
            request = RequestConverter.toRequest(ds3Request);
            System.out.println("Loading template AmazonS3InitRequestHandler.tmplt");

            requestTemplate = config.getTemplate("AmazonS3InitRequestHandler.tmplt");
            System.out.println("Template loaded!");
        } else if (ds3Request.getClassification() == Classification.spectrads3) {
            System.out.println("AmazonS3 request");
            // TODO
        } else if (ds3Request.getClassification() == Classification.spectrainternal) /* TODO && codeGenType != production */ {
            System.out.println("Spectra internal request");
            // TODO
        } else {
            throw new TemplateException("Unknown dDs3Request Classification: " + ds3Request.getClassification().toString(), Environment.getCurrentEnvironment());
        }

        final Path outputPath = getOutputPath(request);

        System.out.println("outputPath[" + outputPath.toString() + "]");
        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        try {
            requestTemplate.process(request, writer);
        } catch ( NullPointerException e) {
            System.out.println("Encountered NullPointerException while processing template " + requestTemplate.getName());
            e.printStackTrace();
        }
    }

    public Path getOutputPath(final Request request) {
        return Paths.get(outputDirectory + "/ds3_c_sdk/src/requests/" + request.getNameRoot() + ".c");
    }
}
