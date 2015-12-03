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

package com.spectralogic.ds3autogen.java;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.java.converters.ClientConverter;
import com.spectralogic.ds3autogen.java.converters.ModelConverter;
import com.spectralogic.ds3autogen.java.converters.RequestConverter;
import com.spectralogic.ds3autogen.java.converters.ResponseConverter;
import com.spectralogic.ds3autogen.java.models.*;
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

import static com.spectralogic.ds3autogen.java.converters.NameConverter.renameRequests;
import static com.spectralogic.ds3autogen.java.models.Constants.*;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.*;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;

public class JavaCodeGenerator implements CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(JavaCodeGenerator.class);

    private static final Path baseProjectPath = Paths.get("ds3-sdk/src/main/java/");

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private Ds3ApiSpec spec;
    private FileUtils fileUtils;
    private Path destDir;

    public JavaCodeGenerator() {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(JavaCodeGenerator.class, "/tmpls");
    }

    @Override
    public void generate(
            final Ds3ApiSpec spec,
            final FileUtils fileUtils,
            final Path destDir) throws IOException {
        this.spec = renameRequests(spec);
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        try {
            generateCommands();
        } catch (final TemplateException e) {
            e.printStackTrace();
        }
    }

    private void generateCommands() throws IOException, TemplateException {
        generateAllRequests();
        generateAllModels();
        generateClient();
    }

    private void generateAllModels() throws IOException, TemplateException {
        final ImmutableMap<String, Ds3Type> types = removeUnusedTypes(spec.getTypes(), spec.getRequests());
        if (isEmpty(types)) {
            LOG.info("There were no models to generate");
            return;
        }
        for (final Ds3Type ds3Type : types.values()) {
            generateModel(ds3Type);
        }
    }

    private void generateModel(final Ds3Type ds3Type) throws IOException, TemplateException {
        final Template modelTmpl = getModelTemplate(ds3Type);
        final Model model = ModelConverter.toModel(ds3Type, getModelPackage());
        final Path modelPath = getModelPath(model.getName());

        LOG.info("Getting outputstream for file:" + modelPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(modelPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            modelTmpl.process(model, writer);
        }
    }

    private Template getModelTemplate(final Ds3Type ds3Type) throws IOException {
        if (hasContent(ds3Type.getEnumConstants())) {
            return config.getTemplate("models/enum_model_template.tmpl");
        }
        if (hasContent(ds3Type.getElements())) {
            return config.getTemplate("models/model_template.tmpl");
        }
        throw new IllegalArgumentException("Type must have Elements and/or EnumConstants");
    }

    private String getModelPackage() {
        return ROOT_PACKAGE_PATH + MODELS_PACKAGE;
    }

    private Path getModelPath(final String fileName) {
        return destDir.resolve(baseProjectPath.resolve(
                Paths.get(getModelPackage().replace(".", "/") + "/" + fileName + ".java")));
    }

    private void generateAllRequests() throws IOException, TemplateException {
        final ImmutableList<Ds3Request> requests = removeSpectraInternalRequests(spec.getRequests());
        if (isEmpty(requests)) {
            LOG.info("There were no requests to generate");
            return;
        }
        for (final Ds3Request request : requests) {
            generateRequest(request);
            generateResponse(request);
        }
    }

    private void generateClient() throws IOException, TemplateException {
        final ImmutableList<Ds3Request> requests = removeSpectraInternalRequests(spec.getRequests());
        if (isEmpty(requests)) {
            LOG.info("Not generating client: no requests.");
            return;
        }
        final Template clientTmpl = config.getTemplate("client/ds3client_template.tmpl");
        final Client client = ClientConverter.toClient(requests, ROOT_PACKAGE_PATH);
        final Path clientPath = getClientPath("Ds3Client.java");

        LOG.info("Getting outputstream for file:" + clientPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(clientPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            clientTmpl.process(client, writer);
        }

        final Template clientImplTmpl = config.getTemplate("client/ds3client_impl_template.tmpl");
        final Path clientImplPath = getClientPath("Ds3ClientImpl.java");

        LOG.info("Getting outputstream for file:" + clientPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(clientImplPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            clientImplTmpl.process(client, writer);
        }
    }

    private Path getClientPath(final String fileName) {
        return destDir.resolve(baseProjectPath.resolve(Paths.get(ROOT_PACKAGE_PATH.replace(".", "/") + "/" + fileName)));
    }

    private void generateResponse(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getResponseTemplate(ds3Request);

        final Response response = getResponse(ds3Request);
        final Path responsePath = getPath(ds3Request, response.getName());

        LOG.info("Getting outputstream for file:" + responsePath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(responsePath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(response, writer);
        }
    }

    private Response getResponse(final Ds3Request ds3Request) {
        return ResponseConverter.toResponse(ds3Request, getCommandPackage(ds3Request));
    }

    private Template getResponseTemplate(final Ds3Request ds3Request) throws IOException {
        if (isBulkRequest(ds3Request)) {
            return config.getTemplate("response/bulk_response_template.tmpl");
        } else {
            return config.getTemplate("response/response_template.tmpl");
        }
    }

    private static String getCommandPackage(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(COMMANDS_PACKAGE_PATH);
        if (ds3Request.getClassification() == Classification.spectrads3) {
            builder.append(SPECTRA_DS3_PACKAGE);
        }
        if (RequestConverter.isNotificationRequest(ds3Request)) {
            builder.append(NOTIFICATION_PACKAGE);
        }
        return builder.toString();
    }

    private void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getRequestTemplate(ds3Request);

        final Request request = getRequest(ds3Request);
        final Path requestPath = getPath(ds3Request, request.getName());

        LOG.info("Getting outputstream for file:" + requestPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(requestPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(request, writer);
        }
    }

    private Path getPath(final Ds3Request ds3Request, final String fileName) {
        return destDir.resolve(baseProjectPath.resolve(
                Paths.get(getCommandPackage(ds3Request).replace(".", "/") + "/" + fileName + ".java")));
    }

    private Request getRequest(final Ds3Request ds3Request) {
        return RequestConverter.toRequest(ds3Request, getCommandPackage(ds3Request));
    }

    private Template getRequestTemplate(final Ds3Request ds3Request) throws IOException {
        final Template template;
        if (isBulkRequest(ds3Request)) {
            template = config.getTemplate("request/bulk_request_template.tmpl");
        } else if (isPhysicalPlacementRequest(ds3Request)) {
            template = config.getTemplate("request/physical_placement_request_template.tmpl");
        } else if (isMultiFileDelete(ds3Request)) {
            template = config.getTemplate("request/multi_file_delete_request_template.tmpl");
        } else if (isGetObject(ds3Request)) {
            template = config.getTemplate("request/get_object_template.tmpl");
        } else if (isCreateObject(ds3Request)) {
            template = config.getTemplate("request/create_object_template.tmpl");
        } else if (isDeleteNotificationRequest(ds3Request)) {
            template = config.getTemplate("request/delete_notification_request_template.tmpl");
        } else if (isCreateNotificationRequest(ds3Request)) {
            template = config.getTemplate("request/create_notification_request_template.tmpl");
        } else if (isGetNotificationRequest(ds3Request)) {
            template = config.getTemplate("request/get_notification_request_template.tmpl");
        } else {
            template = config.getTemplate("request/request_template.tmpl");
        }

        return template;
    }

    private static boolean isDeleteNotificationRequest(final Ds3Request ds3Request) {
        return RequestConverter.isNotificationRequest(ds3Request)
                && RequestConverter.getNotificationType(ds3Request) == NotificationType.DELETE;
    }

    private static boolean isCreateNotificationRequest(final Ds3Request ds3Request) {
        return RequestConverter.isNotificationRequest(ds3Request)
                && RequestConverter.getNotificationType(ds3Request) == NotificationType.CREATE;
    }

    private static boolean isGetNotificationRequest(final Ds3Request ds3Request) {
        return RequestConverter.isNotificationRequest(ds3Request)
                && RequestConverter.getNotificationType(ds3Request) == NotificationType.GET;
    }

    private static boolean isPhysicalPlacementRequest(final Ds3Request ds3Request) {
        return ds3Request.getOperation() != null && (
                ds3Request.getOperation() == Operation.GET_PHYSICAL_PLACEMENT
                || ds3Request.getOperation() == Operation.VERIFY_PHYSICAL_PLACEMENT
                || ds3Request.getOperation() == Operation.START_BULK_VERIFY);
    }

    private static boolean isBulkRequest(final Ds3Request ds3Request) {
        return ds3Request.getOperation() != null && (
                ds3Request.getOperation() == Operation.START_BULK_GET
                || ds3Request.getOperation() == Operation.START_BULK_PUT);
    }

    private static boolean isMultiFileDelete(final Ds3Request ds3Request) {
        return ds3Request.getName().endsWith("DeleteObjectsRequest");
    }

    private static boolean isCreateObject(final Ds3Request ds3Request) {
        return ds3Request.getName().endsWith("CreateObjectRequest");
    }

    private static boolean isGetObject(final Ds3Request ds3Request) {
        return ds3Request.getName().endsWith("GetObjectRequest");
    }
}
