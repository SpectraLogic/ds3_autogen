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
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3ResponseCode;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.generators.clientmodels.BaseClientGenerator;
import com.spectralogic.ds3autogen.net.generators.clientmodels.ClientModelGenerator;
import com.spectralogic.ds3autogen.net.generators.requestmodels.*;
import com.spectralogic.ds3autogen.net.generators.responsemodels.BaseResponseGenerator;
import com.spectralogic.ds3autogen.net.generators.responsemodels.ResponseModelGenerator;
import com.spectralogic.ds3autogen.net.model.client.BaseClient;
import com.spectralogic.ds3autogen.net.model.request.BaseRequest;
import com.spectralogic.ds3autogen.net.model.response.BaseResponse;
import com.spectralogic.ds3autogen.utils.ResponsePayloadUtil;
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

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;

/**
 * Generates the .Net SDK code based on the contents of the Ds3ApiSpec
 */
public class NetCodeGenerator implements CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(NetCodeGenerator.class);
    private static final String COMMANDS_NAMESPACE = "Ds3.Calls";
    private static final String CLIENT_NAMESPACE = "Ds3.";
    private static final Path BASE_PROJECT_PATH = Paths.get("");

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private Ds3ApiSpec spec;
    private FileUtils fileUtils;
    private Path destDir;

    public NetCodeGenerator() {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(NetCodeGenerator.class, "/tmpls/net");
    }

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) throws IOException {
        this.spec = spec;
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        try {
            final ImmutableList<Ds3Request> requests = spec.getRequests();
            generateCommands(requests);
            generateClient(requests);
        } catch (final TemplateException e) {
            e.printStackTrace();
        }
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
    private void generateCommands(final ImmutableList<Ds3Request> requests) throws TemplateException, IOException {
        if (isEmpty(requests)) {
            LOG.info("There were no requests to generate");
            return;
        }
        for (final Ds3Request request : requests) {
            generateRequest(request);
            generateResponse(request);
        }
    }

    /**
     * Generates the .net code for the response handler described in the Ds3Request
     */
    private void generateResponse(final Ds3Request ds3Request) throws IOException, TemplateException {
        if (!ResponsePayloadUtil.hasResponsePayload(ds3Request.getDs3ResponseCodes())) {
            //There is no payload for this Ds3Request, so do not generate any response handling code
            return;
        }
        if (isEmpty(spec.getTypes())) {
            LOG.error("Cannot generate response because type map is empty");
            return;
        }
        final Ds3Type responsePayload = getResponsePayload(ds3Request.getDs3ResponseCodes());
        if (responsePayload == null) {
            throw new IllegalArgumentException("Cannot generate a response because there are no non-error payloads: " + ds3Request.getName());
        }
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
     * Retrieves the non-error response type from within the response codes. If no non-error
     * response type is found, then null is returned.
     */
    private Ds3Type getResponsePayload(final ImmutableList<Ds3ResponseCode> responseCodes) {
        if (isEmpty(responseCodes)) {
            LOG.error("There are no response codes to generate the response");
            return null;
        }
        for (final Ds3ResponseCode responseCode : responseCodes) {
            final String responseType = ResponsePayloadUtil.getResponseType(responseCode.getDs3ResponseTypes());
            if (ResponsePayloadUtil.isNonErrorCode(responseCode.getCode()) && !responseType.equals("null")) {
                return spec.getTypes().get(responseType);
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
        return config.getTemplate("response/response_template.ftl");
    }

    /**
     * Generates the .net code for the request handler described in the Ds3Request
     */
    private void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getRequestTemplate(ds3Request);
        final RequestModelGenerator<?> modelGenerator = getTemplateModelGenerator(ds3Request);
        final BaseRequest request = modelGenerator.generate(ds3Request);
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
        if (isCreateObjectRequest(ds3Request)
                || isCreateMultiPartUploadPartRequest(ds3Request)) {
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
        if (isEjectStorageDomainRequest(ds3Request) || isPhysicalPlacementRequest(ds3Request)) {
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
