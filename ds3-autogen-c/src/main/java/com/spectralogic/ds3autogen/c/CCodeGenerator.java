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
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.c.converters.*;
import com.spectralogic.ds3autogen.c.helpers.*;
import com.spectralogic.ds3autogen.c.models.Enum;
import com.spectralogic.ds3autogen.c.models.*;
import com.spectralogic.ds3autogen.utils.ConverterUtil;
import com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Collection;
import java.util.stream.Stream;


public class CCodeGenerator implements CodeGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(CCodeGenerator.class);

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private FileUtils fileUtils;

    public CCodeGenerator() throws TemplateModelException {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(CCodeGenerator.class, "/templates");
        config.setSharedVariable("cTypeHelper", C_TypeHelper.getInstance());
        config.setSharedVariable("enumHelper", EnumHelper.getInstance());
        config.setSharedVariable("requestHelper", RequestHelper.getInstance());
        config.setSharedVariable("helper", Helper.getInstance());
        config.setSharedVariable("structHelper", StructHelper.getInstance());
        config.setSharedVariable("structMemberHelper", StructMemberHelper.getInstance());
        config.setSharedVariable("parameterHelper", ParameterHelper.getInstance());
    }

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir, final Ds3DocSpec docSpec) throws IOException {
        this.fileUtils = fileUtils;

        try {
            final ImmutableList<Request> allRequests = getAllRequests(spec);
            final ImmutableList<Enum> allEnums = getAllEnums(spec);
            final ImmutableSet<String> enumNames = EnumHelper.getEnumNamesSet(allEnums);

            final ImmutableSet<String> arrayMemberTypes = getArrayMemberTypes(spec, enumNames);

            final ImmutableSet<String> embeddedTypes = getEmbeddedTypes(spec, enumNames);
            final ImmutableSet<String> responseTypes = RequestHelper.getResponseTypes(allRequests);
            final ImmutableSet<String> paginatedTypes = getPaginatedTypes(spec);

            final ImmutableList<Struct> allStructs = getAllStructs(spec, enumNames, responseTypes, embeddedTypes, arrayMemberTypes, paginatedTypes);


            generateHeader(allEnums, allStructs, allRequests);
            generateSource(allEnums, allStructs, allRequests);
            generateStaticFiles();
        } catch (final ParseException e) {
            LOG.error("Caught exception: ", e);
        }
    }

    public void generateHeader(
            final ImmutableList<Enum> allEnums,
            final ImmutableList<Struct> allStructs,
            final ImmutableList<Request> allRequests) throws IOException, ParseException {
        final Path path = Paths.get("src/ds3.h");
        final Header header = HeaderConverter.toHeader(allEnums, allStructs, allRequests);
        processTemplate(header, "header-templates/ds3_h.ftl", fileUtils.getOutputFile(path));
    }

    public void generateSource(
        final ImmutableList<Enum> allEnums,
        final ImmutableList<Struct> allStructs,
        final ImmutableList<Request> allRequests) throws IOException, ParseException {

        final Source source = SourceConverter.toSource(allEnums, allStructs, allRequests);
        final Path path = Paths.get("src/ds3.c");
        processTemplate(source, "source-templates/ds3_c.ftl", fileUtils.getOutputFile(path));

    }

    public void generateStaticFiles() throws IOException {
        final Path ds3RequestPath = Paths.get("src/ds3_request.h");
        processTemplate(null, "other-templates/ds3_request_h.ftl", fileUtils.getOutputFile(ds3RequestPath));

        final Path ds3NetPath = Paths.get("src/ds3_net.c");
        processTemplate(null, "other-templates/ds3_net_c.ftl", fileUtils.getOutputFile(ds3NetPath));
        final Path ds3NetHeaderPath = Paths.get("src/ds3_net.h");
        processTemplate(null, "other-templates/ds3_net_h.ftl", fileUtils.getOutputFile(ds3NetHeaderPath));

        final Path ds3ConnectionPath = Paths.get("src/ds3_connection.c");
        processTemplate(null, "other-templates/ds3_connection_c.ftl", fileUtils.getOutputFile(ds3ConnectionPath));
        final Path ds3ConnectionHeaderPath = Paths.get("src/ds3_connection.h");
        processTemplate(null, "other-templates/ds3_connection_h.ftl", fileUtils.getOutputFile(ds3ConnectionHeaderPath));
    }

    public static ImmutableList<Enum> getAllEnums(final Ds3ApiSpec spec) throws ParseException {
        final ImmutableList.Builder<Enum> allEnumsBuilder = ImmutableList.builder();
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                if (ConverterUtil.isEmpty(ds3TypeEntry.getEnumConstants())) continue;

                allEnumsBuilder.add(EnumConverter.toEnum(ds3TypeEntry));
            }
        }
        return allEnumsBuilder.build();
    }

    public static ImmutableList<Struct> getAllStructs(final Ds3ApiSpec spec,
                                                      final ImmutableSet<String> enumNames,
                                                      final ImmutableSet<String> responseTypes,
                                                      final ImmutableSet<String> embeddedTypes,
                                                      final ImmutableSet<String> arrayMemberTypes,
                                                      final ImmutableSet<String> paginatedTypes) throws ParseException {
        final ImmutableList.Builder<Struct> allStructsBuilder = ImmutableList.builder();
        if (ConverterUtil.hasContent(spec.getTypes())) {
            for (final Ds3Type ds3TypeEntry : spec.getTypes().values()) {
                if (ConverterUtil.hasContent(ds3TypeEntry.getEnumConstants())) continue;

                final Struct structEntry = StructConverter.toStruct(
                        ds3TypeEntry, enumNames, responseTypes, embeddedTypes, arrayMemberTypes, paginatedTypes);
                allStructsBuilder.add(structEntry);
            }
        }
        return allStructsBuilder.build();
    }

    /**
     * Find all types that are used as an array member, for generation of a parser for a list of a type.  Only applies
     * to types that are wrapped in a common element.
     * Example:
     *      <Ds3Targets>
                <Ds3Target>
                    <AccessControlReplication>NONE</AccessControlReplication>
                    <AdminAuthId>aid</AdminAuthId>
                    <AdminSecretKey>ask</AdminSecretKey>
                    <DataPathEndPoint>dp</DataPathEndPoint>
                    <DataPathHttps>true</DataPathHttps>
                    <DataPathPort />
                    <DataPathProxy />
                    <DataPathVerifyCertificate>true</DataPathVerifyCertificate>
                    <DefaultReadPreference>LAST_RESORT</DefaultReadPreference>
                    <Id>02a0faf9-c5c7-4b47-b4e0-29c4d2906f90</Id>
                    <Name>ds3t2</Name>
                    <PermitGoingOutOfSync>false</PermitGoingOutOfSync>
                    <Quiesced>NO</Quiesced>
                    <ReplicatedUserDefaultDataPolicy />
                    <State>ONLINE</State>
                </Ds3Target>
                <Ds3Target>
                    <AccessControlReplication>NONE</AccessControlReplication>
                    <AdminAuthId>aid</AdminAuthId>
                    <AdminSecretKey>ask</AdminSecretKey>
                    <DataPathEndPoint>dp</DataPathEndPoint>
                    <DataPathHttps>true</DataPathHttps>
                    <DataPathPort />
                    <DataPathProxy />
                    <DataPathVerifyCertificate>true</DataPathVerifyCertificate>
                    <DefaultReadPreference>LAST_RESORT</DefaultReadPreference>
                    <Id>1da26e0c-40e2-4ee7-b68b-b60d07f70b51</Id>
                    <Name>ds3t3</Name>
                    <PermitGoingOutOfSync>false</PermitGoingOutOfSync>
                    <Quiesced>NO</Quiesced>
                    <ReplicatedUserDefaultDataPolicy />
                    <State>ONLINE</State>
                </Ds3Target>
            </Ds3Targets>

     Types that are not wrapped in a common element are parsed differently and do no require a separate function:
         <Delete>
             <Object><Key>object/</Key></Object>
             <Object><Key>object/1</Key></Object>
             <Object><Key>object/2</Key></Object>
         </Delete>
     */
    public static ImmutableSet<String> getArrayMemberTypes(final Ds3ApiSpec spec, final ImmutableSet<String> enumTypes) {
        return spec.getTypes().values().stream()
                .flatMap(type -> type.getElements().stream())
                .filter(element -> element.getType().equalsIgnoreCase("array"))  // only want types that array member types
                .filter(element -> !element.getComponentType().contains("UUID")) // ignore UUID
                .filter(element ->element.getDs3Annotations().stream()
                        .flatMap(anno -> anno.getDs3AnnotationElements().stream())
                        .anyMatch(annoElem -> annoElem.getValue().equals("SINGLE_BLOCK_FOR_ALL_ELEMENTS"))) // only want wrapped array types
                .filter(element -> !enumTypes.contains(EnumHelper.getDs3Type(element.getComponentType()))) // ignore enums
                .map(element -> StructHelper.getResponseTypeName(element.getComponentType()))
                .collect(GuavaCollectors.immutableSet());
    }

    /**
     * Find all types that are embedded members.  Many 'top-level' types are not embedded and therefore those parsers
     * are useless.
     */
    public static ImmutableSet<String> getEmbeddedTypes(final Ds3ApiSpec spec, final ImmutableSet<String> enumTypes) {
        final ImmutableSet<String> embeddedTypes = spec.getTypes().values().stream()
                .flatMap(type -> type.getElements().stream())
                .filter(element -> !element.getType().equalsIgnoreCase("array"))
                .map(Ds3Element::getType)
                .collect(GuavaCollectors.immutableSet());
        final ImmutableSet<String> embeddedComponentTypes = spec.getTypes().values().stream()
                .flatMap(type -> type.getElements().stream())
                .filter(element -> element.getType().equalsIgnoreCase("array"))
                .map(Ds3Element::getComponentType)
                .collect(GuavaCollectors.immutableSet());

        final ImmutableSet<String> basicTypes = ImmutableSet.of(
                "boolean",
                "java.lang.Boolean",
                "int",
                "java.lang.Integer",
                "long",
                "java.lang.Long",
                "double",
                "java.lang.Double",
                "java.lang.String",
                "java.util.UUID",
                "java.util.Date",
                "java.lang.object",
                "com.spectralogic.util.db.lang.SqlOperation"
        );

        return Stream.of(embeddedTypes, embeddedComponentTypes)
                .flatMap(Collection::stream)
                .filter(type -> !enumTypes.contains(StructHelper.getResponseTypeName(type)))
                .filter(type -> !basicTypes.contains(type))
                .map(StructHelper::getResponseTypeName)
                .sorted()
                .collect(GuavaCollectors.immutableSet());
    }

    /**
     * Requests with optional paging require an extra "ds3_paging" member
     */
    public static ImmutableSet<String> getPaginatedTypes(final Ds3ApiSpec spec) {
        return spec.getRequests().stream()
                .filter(Ds3RequestClassificationUtil::supportsPaginationRequest)
                .map( req -> RequestConverter.getResponseType(req.getDs3ResponseCodes()))
                .collect(GuavaCollectors.immutableSet());
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
