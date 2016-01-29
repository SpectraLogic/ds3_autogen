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

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.c.converters.EnumConverter;
import com.spectralogic.ds3autogen.c.converters.RequestConverter;
import com.spectralogic.ds3autogen.c.converters.StructConverter;
import com.spectralogic.ds3autogen.c.helpers.StructHelper;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) throws IOException {
        this.spec = spec;
        this.fileUtils = fileUtils;

        try {
            final ImmutableList<Enum> allEnums = getAllEnums(spec);
            final ImmutableList<Struct> allStructs = getStructsOrderedList(spec);
            final ImmutableList<Request> allRequests = getAllRequests(spec);

            generateHeader(allEnums, allStructs, allRequests);
            generateSource(allEnums, allStructs, allRequests);
        } catch (final ParseException e) {
            LOG.error("Caught exception: ", e);
        }
    }

    public void generateHeader(
            final ImmutableList<Enum> allEnums,
            final ImmutableList<Struct> allOrderedStructs,
            final ImmutableList<Request> allRequests) throws IOException, ParseException {
        final Path path = Paths.get("src/ds3.h");
        final Header header = new Header(allEnums,allOrderedStructs,allRequests);
        processTemplate(header, "ds3_h.ftl", fileUtils.getOutputFile(path));
    }

    public void generateSource(
        final ImmutableList<Enum> allEnums,
        final ImmutableList<Struct> allOrderedStructs,
        final ImmutableList<Request> allRequests) throws IOException, ParseException {
        final Path path = Paths.get("src/ds3.c");
        final Source source = new Source(allEnums,allOrderedStructs,allRequests);
        processTemplate(source, "ds3_c.ftl", fileUtils.getOutputFile(path));

    }

    public static ImmutableList<Enum> getAllEnums(final Ds3ApiSpec spec) throws ParseException {
        final ImmutableList.Builder<Enum> allEnumsBuilder = ImmutableList.builder();
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                allEnumsBuilder.add(EnumConverter.toEnum(ds3TypeEntry));
            }
        }
        return allEnumsBuilder.build();
    }

    public static ImmutableList<Struct> getAllStructs(final Ds3ApiSpec spec) throws ParseException {
        final ImmutableList.Builder<Struct> allStructsBuilder = ImmutableList.builder();
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                allStructsBuilder.add(StructConverter.toStruct(ds3TypeEntry));
            }
        }
        return allStructsBuilder.build();
    }

    private static boolean containsExistingStructs(final Struct struct, final Set<String> existingStructs) {
        for (final StructMember structMember: struct.getVariables()) {
            final String testType = StringUtils.stripEnd(structMember.getType(), "*");
            if (!existingStructs.contains(testType)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return structs which contain only primitive types first, and then cascade for structs that contain other structs
     *
     * @throws java.text.ParseException
     */
    public static ImmutableList<Struct> getStructsOrderedList(final Ds3ApiSpec spec) throws ParseException {
        final ImmutableList.Builder<Struct> orderedStructsBuilder = ImmutableList.builder();
        final Queue<Struct> allStructs = new LinkedList(getAllStructs(spec));
        final Set<String> existingTypes = new HashSet<>();
        int skippedStructsCount = 0;
        while (!allStructs.isEmpty()) {
            final int allStructsSize = allStructs.size();
            final Struct structEntry = allStructs.peek();
            if (existingTypes.contains(structEntry)) {
                LOG.warn("Skipping structEntry " + structEntry.getName());
                continue;
            }

            if (StructHelper.isPrimitive(structEntry) || containsExistingStructs(structEntry, existingTypes)) {
                existingTypes.add(StructHelper.getResponseTypeName(structEntry.getName()));
                orderedStructsBuilder.add(allStructs.remove());
            } else {  // move to end to come back to
                allStructs.add(allStructs.remove());
            }

            if (allStructsSize == allStructs.size()) {
                skippedStructsCount++;
                if (skippedStructsCount == allStructsSize) {
                    LOG.warn("Unable to progress on remaining structs, aborting!");
                    LOG.warn("  Remaining structs[" + allStructs.size() + "]");
                    for (final Struct struct : allStructs) {
                        LOG.warn("    " + struct.getName());
                    }
                    break;
                }
            }
        }

        return orderedStructsBuilder.build();
    }

    public static ImmutableList<Request> getAllRequests(final Ds3ApiSpec spec) throws ParseException {
        final ImmutableList.Builder<Request> allRequestsBuilder = ImmutableList.builder();
        if (ConverterUtil.hasContent(spec.getRequests())) {
            for (final Ds3Request ds3Request: spec.getRequests()) {
                allRequestsBuilder.add(RequestConverter.toRequest(ds3Request));
            }
        }
        return allRequestsBuilder.build();
    }

    public void processTemplate(final Object obj, final String templateName, final OutputStream outputStream) throws IOException {
        final Template template = config.getTemplate(templateName);

        final Writer writer = new OutputStreamWriter(outputStream);
        try {
            template.process(obj, writer);
        } catch (final NullPointerException | TemplateException e) {
            LOG.error("Unable to process template " + templateName, e);
        }
    }
}
