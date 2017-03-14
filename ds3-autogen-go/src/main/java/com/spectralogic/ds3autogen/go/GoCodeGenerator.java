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
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.api.models.docspec.Ds3DocSpec;
import com.spectralogic.ds3autogen.api.models.enums.HttpVerb;
import com.spectralogic.ds3autogen.go.generators.client.BaseClientGenerator;
import com.spectralogic.ds3autogen.go.generators.client.ClientModelGenerator;
import com.spectralogic.ds3autogen.go.generators.request.BaseRequestGenerator;
import com.spectralogic.ds3autogen.go.generators.request.RequestModelGenerator;
import com.spectralogic.ds3autogen.go.generators.response.BaseResponseGenerator;
import com.spectralogic.ds3autogen.go.generators.response.ResponseModelGenerator;
import com.spectralogic.ds3autogen.go.generators.type.BaseTypeGenerator;
import com.spectralogic.ds3autogen.go.generators.type.TypeModelGenerator;
import com.spectralogic.ds3autogen.go.models.client.Client;
import com.spectralogic.ds3autogen.go.models.request.Request;
import com.spectralogic.ds3autogen.go.models.response.Response;
import com.spectralogic.ds3autogen.go.models.type.Type;
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

import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.removeUnusedTypes;

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
        config.setClassForTemplateLoading(GoCodeGenerator.class, "/tmpls");
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
            e.printStackTrace();
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
                        Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + request.getName() + ".go")));

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
    private RequestModelGenerator<?> getRequestGenerator(final Ds3Request ds3Request) {
        //TODO add special casing
        return new BaseRequestGenerator();
    }

    /**
     * Retrieves the appropriate template that will generate the Go request handler
     */
    private Template getRequestTemplate(final Ds3Request ds3Request) throws IOException {
        //TODO add special casing
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
                        Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + response.getName() + ".go")));

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
    private ResponseModelGenerator<?> getResponseGenerator(final Ds3Request ds3Request) {
        //TODO add special casing
        return new BaseResponseGenerator();
    }

    /**
     * Retrieves the appropriate template that will generate the Go response handler
     */
    private Template getResponseTemplate(final Ds3Request ds3Request) throws IOException {
        //TODO add special casing
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
        final Template tmpl = getClientTemplate(httpVerb);
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
     * Retrieves the appropriate template that will generate the Go Client
     */
    private Template getClientTemplate(final HttpVerb httpVerb) throws IOException {
        //TODO special case client template if necessary
        return config.getTemplate("client/client_template.ftl");
    }

    /**
     * Generates all of the response models
     */
    private void generateAllTypes(final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        if (isEmpty(typeMap)) {
            LOG.info("There were no types to generate");
            return;
        }
        for (final Ds3Type ds3Type : typeMap.values()) {
            generateType(ds3Type);
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
     * Generates the response model represented by the specified {@link Ds3Type}
     */
    private void generateType(final Ds3Type ds3Type) throws IOException, TemplateException {
        final Template tmpl = getTypeTemplate(ds3Type);
        final TypeModelGenerator<?> generator = getTypeGenerator(ds3Type);
        final Type type = generator.generate(ds3Type);
        final Path path = destDir.resolve(
                BASE_PROJECT_PATH.resolve(
                        Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + type.getName() + ".go")));

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
        //TODO add special casing
        return config.getTemplate("type/type_template.ftl");
    }

    /**
     * Retrieves the generator used to create the Go response model represented by the
     * specified {@link Ds3Type}
     */
    private TypeModelGenerator<?> getTypeGenerator(final Ds3Type ds3Type) {
        //TODO add special casing
        return new BaseTypeGenerator();
    }
}
