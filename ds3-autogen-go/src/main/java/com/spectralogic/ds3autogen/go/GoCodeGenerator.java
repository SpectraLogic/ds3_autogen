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

package com.spectralogic.ds3autogen.go;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.go.generators.client.BaseClientGenerator;
import com.spectralogic.ds3autogen.go.generators.client.ClientModelGenerator;
import com.spectralogic.ds3autogen.go.generators.parser.BaseTypeParserGenerator;
import com.spectralogic.ds3autogen.go.generators.parser.JobListParserGenerator;
import com.spectralogic.ds3autogen.go.generators.parser.TypeParserModelGenerator;
import com.spectralogic.ds3autogen.go.generators.request.*;
import com.spectralogic.ds3autogen.go.generators.response.BaseResponseGenerator;
import com.spectralogic.ds3autogen.go.generators.response.GetObjectResponseGenerator;
import com.spectralogic.ds3autogen.go.generators.response.NoResponseGenerator;
import com.spectralogic.ds3autogen.go.generators.response.ResponseModelGenerator;
import com.spectralogic.ds3autogen.go.generators.type.BaseTypeGenerator;
import com.spectralogic.ds3autogen.go.generators.type.TypeModelGenerator;
import com.spectralogic.ds3autogen.go.models.client.Client;
import com.spectralogic.ds3autogen.go.models.parser.TypeParser;
import com.spectralogic.ds3autogen.go.models.request.Request;
import com.spectralogic.ds3autogen.go.models.response.Response;
import com.spectralogic.ds3autogen.go.models.type.Type;
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

import static com.spectralogic.ds3autogen.utils.ConverterUtil.*;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.hasWrapperAnnotations;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isJobsApiBean;
import static com.spectralogic.ds3autogen.utils.ResponsePayloadUtil.hasResponsePayload;
import static kotlin.text.StringsKt.decapitalize;

public class GoCodeGenerator implements CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(GoCodeGenerator.class);
    private static final Path BASE_PROJECT_PATH = Paths.get("ds3");
    private static final String COMMANDS_NAMESPACE = "models";

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private FileUtils fileUtils;
    private Path destDir;

    public GoCodeGenerator() throws TemplateModelException {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(GoCodeGenerator.class, "/tmpls/go/");
        config.setSharedVariable("helper", Helper.getInstance());
    }

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir, final Ds3DocSpec docSpec) throws IOException {
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        try {
            final ImmutableList<Ds3Request> ds3Requests = spec.getRequests();
            final ImmutableMap<String, Ds3Type> typeMap = removeUnusedTypes(
                    spec.getTypes(),
                    spec.getRequests());

            generateCommands(ds3Requests);
            generateClient(ds3Requests);
            generateAllTypes(typeMap);
        } catch (final Exception e) {
            LOG.error("Unable to generate Go SDK code", e);
        }
    }

    /**
     * Generates Go code for requests and responses
     */
    private void generateCommands(final ImmutableList<Ds3Request> ds3Requests) throws IOException, TemplateException {
        if (isEmpty(ds3Requests)) {
            LOG.info("There were no requests to generate.");
            return;
        }
        for (final Ds3Request ds3Request : ds3Requests) {
            generateRequest(ds3Request);
            generateResponse(ds3Request);
        }
    }

    /**
     * Generates the Go code for a request handler
     */
    private void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getRequestTemplate(ds3Request);
        final RequestModelGenerator<?> generator = getRequestGenerator(ds3Request);
        final Request request = generator.generate(ds3Request);
        final Path path = destDir.resolve(
                BASE_PROJECT_PATH.resolve(
                        Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + decapitalize(request.getName())  + ".go")));

        LOG.info("Getting Output Stream for file: {}", path.toString());

        try (final OutputStream outputStream = fileUtils.getOutputFile(path);
             final Writer writer = new OutputStreamWriter(outputStream)) {
            tmpl.process(request, writer);
        }
    }

    /**
     * Retrieves the generator used to create the Go request handler for the
     * specified {@link Ds3Request}
     */
    static RequestModelGenerator<?> getRequestGenerator(final Ds3Request ds3Request) {
        if (isAmazonCreateObjectRequest(ds3Request)) {
            return new PutObjectRequestGenerator();
        }
        if (isGetObjectAmazonS3Request(ds3Request)) {
            return new GetObjectRequestGenerator();
        }
        if (isCreateMultiPartUploadPartRequest(ds3Request)) {
            return new ReaderRequestPayloadGenerator();
        }
        if (hasPutObjectsWithSizeRequestPayload(ds3Request)) {
            return new Ds3PutObjectPayloadGenerator();
        }
        if (hasGetObjectsWithLengthOffsetRequestPayload(ds3Request)) {
            return new GetBulkJobRequestGenerator();
        }
        if (isPhysicalPlacementRequest(ds3Request) || isEjectStorageDomainBlobsRequest(ds3Request)) {
            return new RequiredObjectsPayloadGenerator();
        }
        if (hasIdsRequestPayload(ds3Request)) {
            return new IdsPayloadRequestGenerator();
        }
        if (hasStringRequestPayload(ds3Request)) {
            return new StringRequestPayloadGenerator();
        }
        if (isCompleteMultiPartUploadRequest(ds3Request)) {
            return new PartsRequestPayloadGenerator();
        }
        if (isMultiFileDeleteRequest(ds3Request)) {
            return new DeleteObjectsRequestGenerator();
        }
        return new BaseRequestGenerator();
    }

    /**
     * Retrieves the appropriate template that will generate the Go request handler
     */
    private Template getRequestTemplate(final Ds3Request ds3Request) throws IOException {
        if (isGetObjectAmazonS3Request(ds3Request)) {
            return config.getTemplate("request/get_object_request.ftl");
        }
        if (isAmazonCreateObjectRequest(ds3Request)) {
            return config.getTemplate("request/put_object_request.ftl");
        }
        if (hasGetObjectsWithLengthOffsetRequestPayload(ds3Request)) {
            return config.getTemplate("request/get_bulk_job_request.ftl");
        }
        if (hasPutObjectsWithSizeRequestPayload(ds3Request)
                || isCreateMultiPartUploadPartRequest(ds3Request)
                || isPhysicalPlacementRequest(ds3Request)
                || isEjectStorageDomainBlobsRequest(ds3Request)
                || hasStringRequestPayload(ds3Request)
                || isCompleteMultiPartUploadRequest(ds3Request)
                || isMultiFileDeleteRequest(ds3Request)
                || hasIdsRequestPayload(ds3Request)) {
            return config.getTemplate("request/request_with_stream.ftl");
        }
        return config.getTemplate("request/request_template.ftl");
    }

    /**
     * Generates the Go code for a response handler/parser
     */
    private void generateResponse(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getResponseTemplate(ds3Request);
        final ResponseModelGenerator<?> generator = getResponseGenerator(ds3Request);
        final Response response = generator.generate(ds3Request);
        final Path path = destDir.resolve(
                BASE_PROJECT_PATH.resolve(
                        Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + decapitalize(response.getName()) + ".go")));

        LOG.info("Getting Output Stream for file: {}", path.toString());

        try (final OutputStream outputStream = fileUtils.getOutputFile(path);
             final Writer writer = new OutputStreamWriter(outputStream)) {
            tmpl.process(response, writer);
        }
    }

    /**
     * Retrieves the generator used to create the Go response handler for the
     * specified {@link Ds3Request}
     */
    static ResponseModelGenerator<?> getResponseGenerator(final Ds3Request ds3Request) {
        if (isGetObjectAmazonS3Request(ds3Request)) {
            return new GetObjectResponseGenerator();
        }
        if (!hasResponsePayload(ds3Request.getDs3ResponseCodes())) {
            return new NoResponseGenerator();
        }
        return new BaseResponseGenerator();
    }

    /**
     * Retrieves the appropriate template that will generate the Go response handler
     */
    private Template getResponseTemplate(final Ds3Request ds3Request) throws IOException {
        //TODO special case if necessary
        return config.getTemplate("response/response_template.ftl");
    }

    /**
     * Generates the client files. Each file contains the client commands separated by {@link HttpVerb}
     */
    private void generateClient(final ImmutableList<Ds3Request> ds3Requests) throws IOException, TemplateException {
        for (final HttpVerb httpVerb : HttpVerb.values()) {
            final ImmutableList<Ds3Request> filteredRequests = ds3Requests.stream()
                    .filter(ds3Request -> ds3Request.getHttpVerb() == httpVerb)
                    .collect(GuavaCollectors.immutableList());

            generateClientFile(filteredRequests, httpVerb);
        }
    }

    /**
     * Generates a client file for commands that have the specified {@link HttpVerb}
     */
    private void generateClientFile(final ImmutableList<Ds3Request> ds3Requests, final HttpVerb httpVerb) throws IOException, TemplateException {
        if (isEmpty(ds3Requests)) {
            LOG.info("There were no commands with verb {} to generate the {} client file",
                    getClientFileName(httpVerb),
                    httpVerb.toString());
        }
        final Template tmpl = config.getTemplate("client/client_template.ftl");
        final ClientModelGenerator<?> generator = new BaseClientGenerator();
        final Client client = generator.generate(ds3Requests);
        final Path path = destDir.resolve(
                BASE_PROJECT_PATH.resolve(
                        Paths.get(getClientFileName(httpVerb) + ".go")));

        LOG.info("Getting OutputStream for file: {}", path.toString());

        try (final OutputStream outputStream = fileUtils.getOutputFile(path);
             final Writer writer = new OutputStreamWriter(outputStream)) {
            tmpl.process(client, writer);
        }
    }

    /**
     * Generates all of the response models and their parsers
     */
    private void generateAllTypes(final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        if (isEmpty(typeMap)) {
            LOG.info("There were no types to generate");
            return;
        }
        for (final Ds3Type ds3Type : typeMap.values()) {
            generateType(ds3Type);

            // Generate type parser only if the ds3Type is not an enum
            if (isEmpty(ds3Type.getEnumConstants())) {
                generateTypeParser(ds3Type, typeMap);
            }
        }
    }

    /**
     * Retrieves the name of the Go Client file associated with the specified {@link HttpVerb}
     */
    public static String getClientFileName(final HttpVerb httpVerb) {
        switch (httpVerb) {
            case DELETE:
                return "ds3Deletes";
            case GET:
                return "ds3Gets";
            case HEAD:
                return "ds3Heads";
            case POST:
                return "ds3Posts";
            case PUT:
                return "ds3Puts";
            default:
                throw new IllegalArgumentException("Unrecognized HttpVerb value " + httpVerb.toString());
        }
    }

    /**
     * Generates the response model parser for the specified {@link Ds3Type}
     */
    private void generateTypeParser(final Ds3Type ds3Type, final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        final ImmutableSet<String> typesParsedAsSlices = getTypesParsedAsSlices(typeMap);
        final Template tmpl = getTypeParserTemplate(ds3Type, typesParsedAsSlices);
        final TypeParserModelGenerator<?> generator = getTypeParserGenerator(ds3Type);
        final TypeParser typeParser = generator.generate(ds3Type, typeMap);
        final Path path = destDir.resolve(
                BASE_PROJECT_PATH.resolve(
                        Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + decapitalize(typeParser.getName()) + ".go")));

        LOG.info("Getting OutputStream for file: {}", path.toString());

        try (final OutputStream outputStream = fileUtils.getOutputFile(path);
             final Writer writer = new OutputStreamWriter(outputStream)) {
            tmpl.process(typeParser, writer);
        }
    }

    /**
     * Retrieves the appropriate template that will generate the Go model parser
     */
    private Template getTypeParserTemplate(final Ds3Type ds3Type, ImmutableSet<String> typesParsedAsSlices) throws IOException {
        if (typesParsedAsSlices.contains(ds3Type.getName())) {
            return config.getTemplate("parser/type_parser_with_list.ftl");
        }
        return config.getTemplate("parser/base_type_parser.ftl");
    }

    /**
     * Retrieves the generator used to create the Go model parser represented by the
     * specified {@link Ds3Type}
     */
    private static TypeParserModelGenerator<?> getTypeParserGenerator(final Ds3Type ds3Type) {
        if (isJobsApiBean(ds3Type)) {
            return new JobListParserGenerator();
        }
        return new BaseTypeParserGenerator();
    }

    /**
     * Retrieves the set of Ds3Type names that require a parse slice function to be generated.
     */
    static ImmutableSet<String> getTypesParsedAsSlices(final ImmutableMap<String, Ds3Type> typeMap) {
        return typeMap.values().stream()
                .filter(ds3Type -> hasContent(ds3Type.getElements()) && isEmpty(ds3Type.getEnumConstants())) // filter out enums
                .flatMap(ds3Type -> ds3Type.getElements().stream()) // iterate over all Ds3Elements
                .filter(ds3Element -> "array".equals(ds3Element.getType())) // filter out non-array types
                .filter(ds3Element -> hasWrapperAnnotations(ds3Element.getDs3Annotations())) // filter for arrays that have an encapsulating tag
                .map(Ds3Element::getComponentType) // get the component type
                .collect(GuavaCollectors.immutableSet());
    }

    /**
     * Generates the response model represented by the specified {@link Ds3Type}
     */
    private void generateType(final Ds3Type ds3Type) throws IOException, TemplateException {
        final Template tmpl = getTypeTemplate(ds3Type);
        final TypeModelGenerator<?> generator = getTypeGenerator();
        final Type type = generator.generate(ds3Type);
        final Path path = destDir.resolve(
                BASE_PROJECT_PATH.resolve(
                        Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + decapitalize(type.getName()) + ".go")));

        LOG.info("Getting OutputStream for file: {}", path.toString());

        try (final OutputStream outputStream = fileUtils.getOutputFile(path);
             final Writer writer = new OutputStreamWriter(outputStream)) {
            tmpl.process(type, writer);
        }
    }

    /**
     * Retrieves the appropriate template that will generate the Go response model
     */
    private Template getTypeTemplate(final Ds3Type ds3Type) throws IOException {
        if (isEnum(ds3Type)) {
            return config.getTemplate("type/enum_type.ftl");
        }
        return config.getTemplate("type/type_template.ftl");
    }

    /**
     * Retrieves the generator used to create the Go response model
     */
    private static TypeModelGenerator<?> getTypeGenerator() {
        return new BaseTypeGenerator();
    }
}
