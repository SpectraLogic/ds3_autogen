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

import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.converters.TypeConverter;
import com.spectralogic.ds3autogen.c.helpers.RequestHelper;
import com.spectralogic.ds3autogen.c.helpers.TypeHelper;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Type;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public Path getRequestOutputPath(final Request request) {
        return Paths.get(outputDirectory + "/ds3_c_sdk/src/requests/" + RequestHelper.getNameRootUnderscores(request.getName()) + ".c");
    }

    public Path getTypeOutputPath(final Type type) {
        return Paths.get(outputDirectory + "/ds3_c_sdk/src/types/" + TypeHelper.getNameUnderscores(type.getName()) + ".h");
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
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                final Type typeEntry = TypeConverter.toType(ds3TypeEntry);
                if (ConverterUtil.hasContent(typeEntry.getEnumConstants())) {
                    generateTypeTemplate(typeEntry, "TypeEnumConstant.ftl");
                    generateTypeTemplate(typeEntry, "TypeEnumConstantMatcher.ftl");
                }
            }

            // Generate TypeResponse parsers
            //   ensure that parsers for primitives are generated first, and then cascade for types that contain other types
            final ImmutableSet.Builder<String> generatedTypesResponses = new ImmutableSet.Builder<>();
            LOG.debug(generatedTypesResponses.build().size() + " vs " + spec.getTypes().values().size());
            for (int depth = 0; generatedTypesResponses.build().size() < spec.getTypes().values().size(); depth++) {
                for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                    if (ConverterUtil.hasContent(ds3TypeEntry.getElements())) {
                        final Type typeEntry = TypeConverter.toType(ds3TypeEntry);
                        if (generatedTypesResponses.build().contains(typeEntry.getName())) {
                            continue;
                        }

                        if (TypeHelper.isPrimitiveType(typeEntry) || TypeHelper.containsExistingElements(typeEntry, generatedTypesResponses.build())) {
                            generateTypeTemplate(typeEntry, "ResponseParser.ftl");
                            generatedTypesResponses.add(typeEntry.getName());
                        }
                    }
                }
                if (depth > 6) {
                    LOG.warn("generating ResponseParsers up to depth " + depth + ", warning for potential infinite loop.");
                    break;
                }
            }

            // Generate Element Types
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                if (ConverterUtil.hasContent(ds3TypeEntry.getElements())) {
                    final Type typeEntry = TypeConverter.toType(ds3TypeEntry);
                    generateTypeTemplate(typeEntry, "TypeElement.ftl");
                    generateTypeTemplate(typeEntry, "FreeTypeElementPrototype.ftl");
                    generateTypeTemplate(typeEntry, "FreeTypeElement.ftl");
                }
            }
        }

        // Generate Requests last because they depend on the code generated above
        if (ConverterUtil.hasContent(spec.getRequests())) {
            for (final Ds3Request request : spec.getRequests()) {
                generateRequest(request);
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

    public void generateTypeTemplate(final Type typeEntry, final String templateName) throws IOException {
        final Template typeTemplate = config.getTemplate(templateName);

        final Path outputPath = getTypeOutputPath(typeEntry);

        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        try {
            typeTemplate.process(typeEntry, writer);
        } catch (final NullPointerException e) {
            LOG.error("Encountered NullPointerException while processing template " + typeTemplate.getName(), e);
        } catch (final TemplateException e) {
            LOG.error("Encountered TemplateException while processing template " + typeTemplate.getName(), e);
        }
    }
}
