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
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.EnumConverter;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.converters.StructConverter;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.Request;
import com.spectralogic.ds3autogen.c.models.Struct;
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
import java.text.ParseException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class CCodeGenerator implements CodeGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(CCodeGenerator.class);

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private Ds3ApiSpec spec;
    private FileUtils fileUtils;

    public CCodeGenerator() {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(CCodeGenerator.class, "/templates");
    }

    public void setFileUtils(final FileUtils fileUtils) {
        this.fileUtils = fileUtils;
    }

    public void setSpec(final Ds3ApiSpec spec) {
        this.spec = spec;
    }

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) throws IOException {
        this.spec = spec;
        this.fileUtils = fileUtils;

        try {
            generateDs3_H();
            generateDs3_C();
        } catch (final TemplateException e) {
            e.printStackTrace();
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    public void generateDs3_H() throws IOException, ParseException {
        final Path path = Paths.get("src/ds3.h");
        final OutputStream outputStream = fileUtils.getOutputFile(path);

        // Enums
        generateEnums(outputStream);

        // ResponseStructs
        generateTypedefStructs(outputStream);

        // InitFunctionPrototypes
        generateInitRequestPrototypes(outputStream);

        // request prototypes
        generateRequestPrototypes(outputStream);

        // Free ResponseStruct prototypes
        generateFreeResponseStructPrototypes(outputStream);
    }

    public void generateEnums(final OutputStream outputStream) throws IOException {
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                final Enum enumEntry = EnumConverter.toEnum(ds3TypeEntry);
                if (ConverterUtil.hasContent(enumEntry.getValues())) {
                    processTemplate(enumEntry, "TypedefEnum.ftl", outputStream);
                }
            }
        }
    }

    public void generateTypedefStructs(final OutputStream outputStream) throws IOException, ParseException {
        for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
            if (ConverterUtil.hasContent(ds3TypeEntry.getElements())) {
                final Struct structEntry = StructConverter.toStruct(ds3TypeEntry);
                processTemplate(structEntry, "TypedefStruct.ftl", outputStream);
            }
        }

    }

    public void generateInitRequestPrototypes(final OutputStream outputStream) {

    }

    private void generateRequestPrototypes(final OutputStream outputStream) {

    }

    public void generateFreeResponseStructPrototypes(final OutputStream outputStream) throws IOException, ParseException {
        for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
            if (ConverterUtil.hasContent(ds3TypeEntry.getElements())) {
                final Struct struct = StructConverter.toStruct(ds3TypeEntry);
                processTemplate(struct, "FreeStructPrototype.ftl", outputStream);
            }
        }
    }

    public void generateDs3_C() throws IOException, TemplateException, ParseException {
        final Path path = Paths.get("src/ds3.c");
        final OutputStream outputStream = fileUtils.getOutputFile(path);

        // EnumMatchers
        generateEnumMatchers(outputStream);

        // InitRequests functions
        generateInitRequests(outputStream);

        // ResponseStruct parsers
        generateResponseStructParsers(outputStream);

        // Requests
        generateRequests(outputStream);

        generateStructFreeFunctions(outputStream);
    }

    public void generateEnumMatchers(final OutputStream outputStream) throws IOException {
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                final Enum enumEntry = EnumConverter.toEnum(ds3TypeEntry);
                if (ConverterUtil.hasContent(enumEntry.getValues())) {
                    processTemplate(enumEntry, "TypedefEnumMatcher.ftl", outputStream);
                }
            }
        }
    }

    public void generateStructFreeFunctions(final OutputStream outputStream) throws IOException, ParseException {
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                final Struct structEntry = StructConverter.toStruct(ds3TypeEntry);
                if (ConverterUtil.hasContent(structEntry.getVariables())) {
                    processTemplate(structEntry, "FreeStruct.ftl", outputStream);
                }
            }
        }
    }

    private Queue<Struct> getAllStructs() throws ParseException {
        Queue<Struct> allStructs = new LinkedList<Struct>();
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                allStructs.add(StructConverter.toStruct(ds3TypeEntry));
            }
        }
        return allStructs;
    }

    // Generate TypeResponse parsers
    //   ensure that parsers for primitives are generated first, and then cascade for types that contain other types
    private Queue<Struct> getStructParsersOrderedList() throws ParseException {
        Queue<Struct> orderedStructs = new LinkedList<>();
        Queue<Struct> allStructs = getAllStructs();
        Set<String> existingTypes = new HashSet<>();
        int count = 0;
        while (!allStructs.isEmpty()) {
            final int allStructsSize = allStructs.size();
            final Struct structEntry = allStructs.peek();
            if (orderedStructs.contains(structEntry)) {
                continue;
            }

            if (StructHelper.isPrimitive(structEntry) || StructHelper.containsExistingStructs(structEntry, existingTypes)) {
                existingTypes.add(StructHelper.getResponseTypeName(structEntry.getName()));
                orderedStructs.add(allStructs.remove());
            } else {  // move to end to come back to
                allStructs.add(allStructs.remove());
            }

            if (allStructsSize == allStructs.size()) {
                count++;
                if (count == allStructsSize) {
                    LOG.warn("Unable to progress on remaining structs, aborting!");
                    LOG.warn("  Remaining structs[" + allStructs.size() + "]");
                    for (final Struct struct : allStructs) {
                        LOG.warn("    " + struct.getName());
                    }
                    break;
                }
            }
        }

        return orderedStructs;
    }

    public void generateResponseStructParsers(final OutputStream outputStream) throws ParseException, IOException {
        final Queue<Struct> orderedParsersList = getStructParsersOrderedList();
        for (final Struct structEntry : orderedParsersList) {
            processTemplate(structEntry, "ResponseParser.ftl", outputStream);
        }
    }

    public void generateInitRequests(final OutputStream outputStream) {

    }

    public void generateRequests(final OutputStream outputStream) throws IOException, TemplateException {
        if (ConverterUtil.hasContent(spec.getRequests())) {
            for (final Ds3Request ds3Request : spec.getRequests()) {
                String requestTemplateName = null;
                Request request = null;

                if (ds3Request.getClassification() == Classification.amazons3) {
                    request = RequestConverter.toRequest(ds3Request);
                    requestTemplateName = "AmazonS3InitRequestHandler.ftl";
                } else if (ds3Request.getClassification() == Classification.spectrads3) {
                    LOG.info("AmazonS3 request");
                    // TODO
                } else if (ds3Request.getClassification() == Classification.spectrainternal) /* TODO && codeGenType != production */ {
                    LOG.debug("Skipping Spectra internal request");
                    continue;
                } else {
                    throw new TemplateException("Unknown dDs3Request Classification: " + ds3Request.getClassification().toString(), Environment.getCurrentEnvironment());
                }

                processTemplate(request, requestTemplateName, outputStream);
            }
        }
    }

    public void processTemplate(final Object obj, final String templateName, final OutputStream outputStream) throws IOException {
        final Template template = config.getTemplate(templateName);

        final Writer writer = new OutputStreamWriter(outputStream);
        try {
            template.process(obj, writer);
        } catch (final NullPointerException e) {
            LOG.error("Encountered NullPointerException while processing template " + templateName, e);
        } catch (final TemplateException e) {
            LOG.error("Encountered TemplateException while processing template " + templateName, e);
        }
    }
}
