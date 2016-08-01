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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.generators.clientmodels.BaseClientGenerator;
import com.spectralogic.ds3autogen.net.generators.clientmodels.ClientModelGenerator;
import com.spectralogic.ds3autogen.net.generators.parsers.response.BaseResponseParserGenerator;
import com.spectralogic.ds3autogen.net.generators.parsers.response.JobListPayloadParserGenerator;
import com.spectralogic.ds3autogen.net.generators.parsers.response.ResponseParserModelGenerator;
import com.spectralogic.ds3autogen.net.generators.requestmodels.*;
import com.spectralogic.ds3autogen.net.generators.responsemodels.BaseResponseGenerator;
import com.spectralogic.ds3autogen.net.generators.responsemodels.ResponseModelGenerator;
import com.spectralogic.ds3autogen.net.generators.typemodels.BaseTypeGenerator;
import com.spectralogic.ds3autogen.net.generators.typemodels.NoneEnumGenerator;
import com.spectralogic.ds3autogen.net.generators.typemodels.ObjectsGenerator;
import com.spectralogic.ds3autogen.net.generators.typemodels.TypeModelGenerator;
import com.spectralogic.ds3autogen.net.generators.parsers.typeset.BaseTypeParserSetGenerator;
import com.spectralogic.ds3autogen.net.generators.parsers.typeset.TypeParserSetGenerator;
import com.spectralogic.ds3autogen.net.model.client.BaseClient;
import com.spectralogic.ds3autogen.net.model.parser.BaseParser;
import com.spectralogic.ds3autogen.net.model.request.BaseRequest;
import com.spectralogic.ds3autogen.net.model.response.BaseResponse;
import com.spectralogic.ds3autogen.net.model.type.BaseType;
import com.spectralogic.ds3autogen.net.model.typeparser.BaseTypeParserSet;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.ResponsePayloadUtil;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.spectralogic.ds3autogen.net.utils.GeneratorUtils.hasResponseHandlerAndParser;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.removeUnusedTypes;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isChecksumType;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isJobsApiBean;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isObjectsType;
import static com.spectralogic.ds3autogen.utils.ResponsePayloadUtil.hasSpecifiedPayload;

/**
 * Generates the .Net SDK code based on the contents of the Ds3ApiSpec
 */
public class NetCodeGenerator implements CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(NetCodeGenerator.class);
    private static final String COMMANDS_NAMESPACE = "Ds3.Calls";
    private static final String CLIENT_NAMESPACE = "Ds3.";
    private static final String MODEL_PARSER_NAMESPACE = "Ds3.ResponseParsers";
    private static final String PARSER_NAMESPACE = CLIENT_NAMESPACE + "ResponseParsers";
    private static final String TYPES_NAMESPACE = CLIENT_NAMESPACE + "Models";
    private static final Path BASE_PROJECT_PATH = Paths.get("");

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private Ds3ApiSpec spec;
    private FileUtils fileUtils;
    private Path destDir;

    public NetCodeGenerator() throws TemplateModelException {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(NetCodeGenerator.class, "/tmpls/net");
        config.setSharedVariable("netHelper", NetHelper.getInstance());
        config.setSharedVariable("helper", Helper.getInstance());
    }

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) throws IOException {
        this.spec = spec;
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        try {
            final ImmutableList<Ds3Request> requests = spec.getRequests();
            final ImmutableMap<String, Ds3Type> typeMap = removeUnusedTypes(
                    spec.getTypes(),
                    spec.getRequests());

            generateCommands(requests, typeMap);
            generateClient(requests);
            generateModelParsers(typeMap);
            generateAllTypes(typeMap);
        } catch (final TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the .net code for the type/model parsers
     */
    private void generateModelParsers(
            final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        if (isEmpty(typeMap)) {
            LOG.info("Not generating model parsers: no types.");
            return;
        }
        final Template tmpl = config.getTemplate("parsers/typeset/all_type_parsers.ftl");
        final TypeParserSetGenerator<?> generator = new BaseTypeParserSetGenerator();
        final BaseTypeParserSet parser = generator.generate(typeMap);
        final Path path = destDir.resolve(
                BASE_PROJECT_PATH.resolve(Paths.get(MODEL_PARSER_NAMESPACE.replace(".", "/") + "/ModelParsers.cs")));

        LOG.info("Getting OutputStream for file:" + path.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(path);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(parser, writer);
        }
    }

    /**
     * Generates the .net code for the types
     */
    private void generateAllTypes(final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        if (isEmpty(typeMap)) {
            LOG.info("There were no types to generate");
            return;
        }
        for (final Ds3Type ds3Type : typeMap.values()) {
            generateType(ds3Type, typeMap);
        }
    }

    /**
     * Generates the .net code for the specified Ds3Type
     */
    private void generateType(final Ds3Type ds3Type, final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        final Template tmpl = getTypeTemplate(ds3Type);
        final TypeModelGenerator<?> modelGenerator = getTypeGenerator(ds3Type);
        final BaseType type = modelGenerator.generate(ds3Type, typeMap);
        final Path requestPath = destDir.resolve(BASE_PROJECT_PATH.resolve(Paths.get(TYPES_NAMESPACE.replace(".", "/") + "/" + type.getName() + ".cs")));

        LOG.info("Getting OutputStream for file:" + requestPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(requestPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(type, writer);
        }
    }

    /**
     * Gets the associated Type Generator for the specified Ds3Type
     */
    private TypeModelGenerator getTypeGenerator(final Ds3Type ds3Type) {
        if (isChecksumType(ds3Type)) {
            return new NoneEnumGenerator();
        }
        if (isObjectsType(ds3Type)) {
            return new ObjectsGenerator();
        }
        return new BaseTypeGenerator();
    }

    /**
     * Gets the template used to generate the .net code for the specified Ds3Type
     */
    private Template getTypeTemplate(final Ds3Type ds3Type) throws IOException {
        if (isChecksumType(ds3Type)) {
            return config.getTemplate("types/checksum_type.ftl");
        }
        if (hasContent(ds3Type.getEnumConstants())) {
            return config.getTemplate("types/enum_type.ftl");
        }
        if (hasContent(ds3Type.getElements())) {
            return config.getTemplate("types/type_template.ftl");
        }
        throw new IllegalArgumentException("Type must have Elements and/or EnumConstants");
    }

    /**
     * Generates the .net code for the client
     */
    private void generateClient(final ImmutableList<Ds3Request> requests) throws IOException, TemplateException {
        if (isEmpty(requests)) {
            LOG.info("Not generating client: no requests.");
            return;
        }
        final Template clientTmpl = config.getTemplate("client/ds3_client.ftl");
        final ClientModelGenerator<?> clientGenerator = new BaseClientGenerator();
        final BaseClient client = clientGenerator.generate(requests);
        final Path clientPath = toClientPath("Ds3Client.cs");

        LOG.info("Getting OutputStream for file:" + clientPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(clientPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            clientTmpl.process(client, writer);
        }

        final Template ids3ClientTmpl = config.getTemplate("client/ids3_client.ftl");
        final Path ids3ClientPath = toClientPath("IDs3Client.cs");

        LOG.info("Getting OutputStream for file:" + ids3ClientPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(ids3ClientPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            ids3ClientTmpl.process(client, writer);
        }
    }

    /**
     * Converts a file name into the path containing said file within the client path
     */
    private Path toClientPath(final String fileName) {
        return destDir.resolve(BASE_PROJECT_PATH.resolve(Paths.get(CLIENT_NAMESPACE.replace(".", "/") + "/" + fileName)));
    }

    /**
     * Generates all code associated with the Ds3ApiSpec
     */
    private void generateCommands(
            final ImmutableList<Ds3Request> requests,
            final ImmutableMap<String, Ds3Type> typeMap) throws TemplateException, IOException {
        if (isEmpty(requests)) {
            LOG.info("There were no requests to generate");
            return;
        }
        for (final Ds3Request request : requests) {
            generateRequest(request, typeMap);
            generateResponseAndParser(request);
        }
    }

    /**
     * Generates the .net code for the response handler and parser described in the Ds3Request
     */
    private void generateResponseAndParser(final Ds3Request ds3Request) throws IOException, TemplateException {
        if (!ResponsePayloadUtil.hasResponsePayload(ds3Request.getDs3ResponseCodes())) {
            //Check if the request is an exception for generating response and parser files
            if (hasResponseHandlerAndParser(ds3Request)) {
                generateResponse(ds3Request, null);
                generateResponseParser(ds3Request, null);
            }
            //There is no payload for this Ds3Request, so do not generate any response handling code
            return;
        }
        final String responsePayloadType = getResponsePayloadType(ds3Request.getDs3ResponseCodes());
        if ((isEmpty(responsePayloadType) || !responsePayloadType.equalsIgnoreCase("java.lang.String")) && isEmpty(spec.getTypes())) {
            LOG.error("Cannot generate response because type map is empty");
            return;
        }
        if (responsePayloadType == null) {
            throw new IllegalArgumentException("Cannot generate a response because there are no non-error payloads: " + ds3Request.getName());
        }

        generateResponse(ds3Request, responsePayloadType);

        if (responsePayloadType.equalsIgnoreCase("java.lang.String")) {
            generateResponseParser(ds3Request, null);
        } else {
            final Ds3Type ds3TypePayload = spec.getTypes().get(responsePayloadType);
            generateResponseParser(ds3Request, ds3TypePayload);
        }
    }

    /**
     * Generates the .net code for the response parser
     */
    private void generateResponseParser(final Ds3Request ds3Request, final Ds3Type responsePayload) throws IOException, TemplateException {
        final Template tmpl = getResponseParserTemplate(ds3Request);
        final ResponseParserModelGenerator<?> parserGenerator = getResponseParserGenerator(responsePayload);

        final BaseParser parser = generateBaseParser(ds3Request, responsePayload, parserGenerator);
        final Path parserPath = destDir.resolve(BASE_PROJECT_PATH.resolve(
                Paths.get(PARSER_NAMESPACE.replace(".", "/") + "/" + parser.getName() + ".cs")));

        LOG.info("Getting OutputStream for file:" + parserPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(parserPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(parser, writer);
        }
    }

    /**
     * Generates the BaseParser for use in response parser generation. This is used to handle a null Ds3Type due to
     * a string payload
     */
    private static BaseParser generateBaseParser(final Ds3Request ds3Request, final Ds3Type ds3Type, final ResponseParserModelGenerator<?> generator) {
        if (ds3Type == null) {
            return generator.generate(ds3Request, "java.lang.String", null);
        }
        return generator.generate(ds3Request, ds3Type.getName(), ds3Type.getNameToMarshal());
    }

    /**
     * Retrieves the response parser generator for the specified Ds3Request
     */
    private ResponseParserModelGenerator getResponseParserGenerator(final Ds3Type responsePayload) {
        if (isJobsApiBean(responsePayload)) {
            return new JobListPayloadParserGenerator();
        }
        return new BaseResponseParserGenerator();
    }

    /**
     * Retrieves the response parser template for the specified Ds3Request
     */
    private Template getResponseParserTemplate(final Ds3Request ds3Request) throws IOException {
        if (isAllocateJobChunkRequest(ds3Request)) {
            return config.getTemplate("parsers/response/allocate_job_chunk_parser.ftl");
        }
        if (isGetJobChunksReadyForClientProcessingRequest(ds3Request)) {
            return config.getTemplate("parsers/response/get_job_chunks_parser.ftl");
        }
        if (isGetObjectAmazonS3Request(ds3Request)) {
            return config.getTemplate("parsers/response/get_object_parser.ftl");
        }
        if (isHeadBucketRequest(ds3Request)) {
            return config.getTemplate("parsers/response/head_bucket_parser.ftl");
        }
        if (isHeadObjectRequest(ds3Request)) {
            return config.getTemplate("parsers/response/head_object_parser.ftl");
        }
        //Perform this check last so that individual special cased requests take precedence
        if (hasSpecifiedPayload(ds3Request, "MasterObjectList")) {
            return config.getTemplate("parsers/response/master_object_list_parser.ftl");
        }
        if (hasSpecifiedPayload(ds3Request, "String")) {
            return config.getTemplate("parsers/response/string_response_parser.ftl");
        }
        return config.getTemplate("parsers/response/parser_template.ftl");
    }

    /**
     * Generates the .net code for the response handler
     */
    private void generateResponse(final Ds3Request ds3Request, final String responsePayload) throws IOException, TemplateException {
        final Template tmpl = getResponseTemplate(ds3Request);
        final ResponseModelGenerator<?> responseGenerator = getResponseGenerator(ds3Request);
        final BaseResponse response = responseGenerator.generate(ds3Request, responsePayload);
        final Path responsePath = destDir.resolve(BASE_PROJECT_PATH.resolve(
                Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" +
                        response.getName() + ".cs")));

        LOG.info("Getting OutputStream for file:" + responsePath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(responsePath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(response, writer);
        }
    }

    /**
     * Retrieves the non-error response type from within the response codes.  If no non-error
     * response type is found, then null is returned.
     */
    private String getResponsePayloadType(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            LOG.error("There are no response codes to generate the response");
            return null;
        }
        for (final Ds3ResponseCode responseCode : responseCodes) {
            final String responseType = ResponsePayloadUtil.getResponseType(responseCode.getDs3ResponseTypes());
            if (ResponsePayloadUtil.isNonErrorCode(responseCode.getCode()) && !responseType.equals("null")) {
                return responseType;
            }
        }
        return null;
    }

    /**
     * Retrieves the associated .net response generator for the specified Ds3Request
     */
    private ResponseModelGenerator getResponseGenerator(final Ds3Request ds3Request) {
        return new BaseResponseGenerator();
    }

    /**
     * Retrieves the appropriate template that will generate the .net response handler
     * code for this Ds3Request
     */
    private Template getResponseTemplate(final Ds3Request ds3Request) throws IOException {
        if (isAllocateJobChunkRequest(ds3Request)) {
            return config.getTemplate("response/allocate_job_chunk_response.ftl");
        }
        if (isGetJobChunksReadyForClientProcessingRequest(ds3Request)) {
            return config.getTemplate("response/get_job_chunks_response.ftl");
        }
        if (isGetObjectAmazonS3Request(ds3Request)) {
            return config.getTemplate("response/get_object_response.ftl");
        }
        if (isHeadBucketRequest(ds3Request)) {
            return config.getTemplate("response/head_bucket_response.ftl");
        }
        if (isHeadObjectRequest(ds3Request)) {
            return config.getTemplate("response/head_object_response.ftl");
        }
        return config.getTemplate("response/response_template.ftl");
    }

    /**
     * Generates the .net code for the request handler described in the Ds3Request
     */
    private void generateRequest(
            final Ds3Request ds3Request,
            final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        final Template tmpl = getRequestTemplate(ds3Request);
        final RequestModelGenerator<?> modelGenerator = getTemplateModelGenerator(ds3Request);
        final BaseRequest request = modelGenerator.generate(ds3Request, typeMap);
        final Path requestPath = destDir.resolve(BASE_PROJECT_PATH.resolve(Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + request.getName() + ".cs")));

        LOG.info("Getting OutputStream for file:" + requestPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(requestPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(request, writer);
        }
    }

    /**
     * Retrieves the associated .net request generator for the specified Ds3Request
     */
    private RequestModelGenerator<?> getTemplateModelGenerator(final Ds3Request ds3Request) {
        if (isGetObjectRequest(ds3Request)) {
            return new GetObjectRequestGenerator();
        }
        if (isBulkPutRequest(ds3Request)) {
            return new BulkPutRequestGenerator();
        }
        if (isBulkGetRequest(ds3Request)) {
            return new BulkGetRequestGenerator();
        }
        if (isCreateObjectRequest(ds3Request)) {
            return new PutObjectRequestGenerator();
        }
        if (isCreateMultiPartUploadPartRequest(ds3Request)) {
            return new StreamRequestPayloadGenerator();
        }
        if (isEjectStorageDomainRequest(ds3Request)
                || isPhysicalPlacementRequest(ds3Request)
                || isMultiFileDeleteRequest(ds3Request)) {
            return new ObjectsRequestPayloadGenerator();
        }
        if (hasStringRequestPayload(ds3Request)) {
            return new StringRequestPayloadGenerator();
        }
        return new BaseRequestGenerator();
    }

    /**
     * Retrieves the appropriate template that will generate the .net request handler
     * code for this Ds3Request
     */
    private Template getRequestTemplate(final Ds3Request ds3Request) throws IOException {
        if (isGetObjectRequest(ds3Request)) {
            return config.getTemplate("request/get_object_request.ftl");
        }
        if (isBulkPutRequest(ds3Request)) {
            return config.getTemplate("request/bulk_put_request.ftl");
        }
        if (isBulkGetRequest(ds3Request)) {
            return config.getTemplate("request/bulk_get_request.ftl");
        }
        if (isCreateObjectRequest(ds3Request)) {
            return config.getTemplate("request/put_object_request.ftl");
        }
        if (isCreateMultiPartUploadPartRequest(ds3Request)) {
            return config.getTemplate("request/stream_request_payload.ftl");
        }
        if (isEjectStorageDomainRequest(ds3Request)) {
            return config.getTemplate("request/eject_storage_domain.ftl");
        }
        if (isPhysicalPlacementRequest(ds3Request)) {
            return config.getTemplate("request/objects_request_payload.ftl");
        }
        if (isMultiFileDeleteRequest(ds3Request)) {
            return config.getTemplate("request/multi_file_delete_request.ftl");
        }
        if (hasStringRequestPayload(ds3Request)) {
            return config.getTemplate("request/string_request_payload.ftl");
        }
        return config.getTemplate("request/request_template.ftl");
    }
}
