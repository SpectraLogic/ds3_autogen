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
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.c.converters.*;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Type;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.spectralogic.ds3autogen.utils.ConverterUtil;
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
        } catch (final TemplateException e) {
            e.printStackTrace();
        }
    }

    private void generateCommands() throws IOException, TemplateException {
        if (ConverterUtil.hasContent(spec.getRequests())) {
            for (final Ds3Request request : spec.getRequests()) {
                generateRequest(request);
            }
        }

        if (ConverterUtil.hasContent(spec.getTypes())) {
            // Generate EnumConstant Types, which Element types can be dependant on
            // TODO Determine if dependency matters at code generation time... probably not.
            for (final Ds3Type typeEntry : spec.getTypes().values()) {
                if (typeEntry.getElements().isEmpty()) {
                    generateTypeEnumConstant(typeEntry);
                    generateTypeEnumConstantMatcher(typeEntry);
                }
            }

            // Generate Element Types
            for (final Ds3Type typeEntry : spec.getTypes().values()) {
                if (!typeEntry.getElements().isEmpty()) {
                    generateTypeElement(typeEntry);
                    //generateTypeEnumConstantMatcher(typeEntry);
                }
            }
        }
    }

    public void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        Template requestTemplate = null;
        Request request = null;

        if (ds3Request.getClassification() == Classification.amazons3) {
            request = RequestConverter.toRequest(ds3Request);
            requestTemplate = config.getTemplate("AmazonS3InitRequestHandler.ftl");
        } else if (ds3Request.getClassification() == Classification.spectrads3) {
            LOG.info("AmazonS3 request");
            // TODO
        } else if (ds3Request.getClassification() == Classification.spectrainternal) /* TODO && codeGenType != production */ {
            LOG.debug("Skipping Spectra internal request");
            return;
        } else {
            throw new TemplateException("Unknown dDs3Request Classification: " + ds3Request.getClassification().toString(), Environment.getCurrentEnvironment());
        }

        final Path outputPath = getRequestOutputPath(request);

        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        try {
            requestTemplate.process(request, writer);
        } catch (final NullPointerException e) {
            LOG.error("Encountered NullPointerException while processing template " + requestTemplate.getName(), e);
        }
    }

    public void generateTypeEnumConstant(final Ds3Type typeEntry) throws IOException {
        final Template typeTemplate = config.getTemplate("TypeEnumConstant.ftl");
        final Type type = TypeConverter.toType(typeEntry);

        final Path outputPath = getTypeOutputPath(type);

        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        try {
            typeTemplate.process(type, writer);
        } catch (final NullPointerException e) {
            LOG.error("Encountered NullPointerException while processing template " + typeTemplate.getName(), e);
        } catch (final TemplateException e) {
            LOG.error("Encountered TemplateException while processing template " + typeTemplate.getName(), e);
        }
    }

    public void generateTypeEnumConstantMatcher(final Ds3Type typeEntry) throws IOException {
        final Template typeTemplate = config.getTemplate("TypeEnumConstantMatcher.ftl");
        final Type type = TypeConverter.toType(typeEntry);

        final Path outputPath = getTypeMatcherOutputPath(type);

        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        try {
            typeTemplate.process(type, writer);
        } catch (final NullPointerException e) {
            LOG.error("Encountered NullPointerException while processing template " + typeTemplate.getName(), e);
        } catch (final TemplateException e) {
            LOG.error("Encountered TemplateException while processing template " + typeTemplate.getName(), e);
        }
    }

    public void generateTypeElement(final Ds3Type typeEntry) throws IOException {
        final Template typeTemplate = config.getTemplate("TypeElement.ftl");
        final Type type = TypeConverter.toType(typeEntry);

        final Path outputPath = getTypeOutputPath(type);

        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        try {
            typeTemplate.process(type, writer);
        } catch (final NullPointerException e) {
            LOG.error("Encountered NullPointerException while processing template " + typeTemplate.getName(), e);
        } catch (final TemplateException e) {
            LOG.error("Encountered TemplateException while processing template " + typeTemplate.getName(), e);
        }
    }

    public void generateTypeElementMatcher(final Ds3Type typeEntry) throws IOException {
        final Template typeTemplate = config.getTemplate("TypeElementMatcher.ftl");
        final Type type = TypeConverter.toType(typeEntry);

        final Path outputPath = getTypeMatcherOutputPath(type);

        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        try {
            typeTemplate.process(type, writer);
        } catch (final NullPointerException e) {
            LOG.error("Encountered NullPointerException while processing template " + typeTemplate.getName(), e);
        } catch (final TemplateException e) {
            LOG.error("Encountered TemplateException while processing template " + typeTemplate.getName(), e);
        }
    }

    public Path getRequestOutputPath(final Request request) {
        return Paths.get(outputDirectory + "/ds3_c_sdk/src/requests/" + request.getNameRootUnderscores() + ".c");
    }

    public Path getTypeOutputPath(final Type type) {
        return Paths.get(outputDirectory + "/ds3_c_sdk/src/types/" + type.getNameUnderscores() + ".h");
    }

    public Path getTypeMatcherOutputPath(final Type type) {
        return Paths.get(outputDirectory + "/ds3_c_sdk/src/types/" + type.getNameUnderscores() + "_matcher.c");
    }
}
