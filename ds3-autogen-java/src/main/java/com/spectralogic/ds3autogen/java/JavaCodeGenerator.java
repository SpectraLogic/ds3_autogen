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
import com.spectralogic.ds3autogen.api.ResponseTypeNotFoundException;
import com.spectralogic.ds3autogen.api.TypeRenamingConflictException;
import com.spectralogic.ds3autogen.api.models.*;
import com.spectralogic.ds3autogen.java.converters.ClientConverter;
import com.spectralogic.ds3autogen.java.converters.RequestConverter;
import com.spectralogic.ds3autogen.java.converters.ResponseConverter;
import com.spectralogic.ds3autogen.java.generators.typemodels.BaseTypeGenerator;
import com.spectralogic.ds3autogen.java.generators.typemodels.ChecksumTypeGenerator;
import com.spectralogic.ds3autogen.java.generators.typemodels.TypeModelGenerator;
import com.spectralogic.ds3autogen.java.models.Client;
import com.spectralogic.ds3autogen.java.models.Model;
import com.spectralogic.ds3autogen.java.models.Request;
import com.spectralogic.ds3autogen.java.models.Response;
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

import static com.spectralogic.ds3autogen.java.models.Constants.*;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.*;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.*;

/**
 * Generates Java SDK code based on the contents of a Ds3ApiSpec.
 *
 * Generated Code:
 *   Request handlers
 *   Response handlers
 *   Ds3Client
 *   Ds3ClientImpl
 *   Models
 */
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
            final Path destDir) throws IOException, ResponseTypeNotFoundException, TypeRenamingConflictException {
        this.spec = spec;
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        try {
            generateCommands();
        } catch (final TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates all code associated with the Ds3ApiSpec
     * @throws IOException
     * @throws TemplateException
     */
    private void generateCommands() throws IOException, TemplateException {
        generateAllRequests();
        generateAllModels();
        generateClient();
    }

    /**
     * Generates the Models described within the Ds3ApiSpec that are being used
     * by at least one request
     * @throws IOException
     * @throws TemplateException
     */
    private void generateAllModels() throws IOException, TemplateException {
        final ImmutableMap<String, Ds3Type> types = removeUnusedTypes(
                spec.getTypes(),
                removeSpectraInternalRequests(spec.getRequests()));

        if (isEmpty(types)) {
            LOG.info("There were no models to generate");
            return;
        }
        for (final Ds3Type ds3Type : types.values()) {
            generateModel(ds3Type);
        }
    }

    /**
     * Generates code for a Model from a Ds3Type
     * @param ds3Type A Ds3Type
     * @throws IOException
     * @throws TemplateException
     */
    private void generateModel(final Ds3Type ds3Type) throws IOException, TemplateException {
        final Template modelTmpl = getModelTemplate(ds3Type);
        final Model model = toModel(ds3Type, getModelPackage());
        final Path modelPath = toModelFilePath(model.getName());

        LOG.info("Getting outputstream for file:" + modelPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(modelPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            modelTmpl.process(model, writer);
        }
    }

    /**
     * Converts a Ds3Type into a Model
     */
    private Model toModel(final Ds3Type ds3Type, final String packageName) {
        final TypeModelGenerator<?> modelGenerator =getModelGenerator(ds3Type);
        return modelGenerator.generate(ds3Type, packageName);
    }

    /**
     * Retrieves the associated type model generator for the specified Ds3TYpe
     */
    private TypeModelGenerator<?> getModelGenerator(final Ds3Type ds3Type) {
        if (isChecksum(ds3Type)) {
            return new ChecksumTypeGenerator();
        }
        return new BaseTypeGenerator();
    }

    /**
     * Gets the Model template that is used to generate the given Ds3Type content
     * @param ds3Type A Ds3Type
     * @return The appropriate template to generate the given Ds3Type
     * @throws IOException
     */
    private Template getModelTemplate(final Ds3Type ds3Type) throws IOException {
        if (isChecksum(ds3Type)) {
            return config.getTemplate("models/checksum_type_template.ftl");
        }
        if (hasContent(ds3Type.getEnumConstants())) {
            return config.getTemplate("models/enum_model_template.ftl");
        }
        if (hasContent(ds3Type.getElements())) {
            return config.getTemplate("models/model_template.ftl");
        }
        throw new IllegalArgumentException("Type must have Elements and/or EnumConstants");
    }

    /**
     * Determines if a given Ds3Type is the Checksum Type
     * @param ds3Type A Ds3Type
     * @return True if the Ds3Type describes the ChecksumType, else false
     */
    private boolean isChecksum(final Ds3Type ds3Type) {
        return ds3Type.getName().endsWith(".ChecksumType");
    }

    /**
     * Gets the package name for where the Models will be generated
     * @return The package name of where the Models are going to be generated
     */
    private String getModelPackage() {
        return ROOT_PACKAGE_PATH + MODELS_PACKAGE;
    }

    /**
     * Converts a Model name into a Model file path
     * @param modelName The name of a Model
     * @return The file path of a Model
     */
    private Path toModelFilePath(final String modelName) {
        return destDir.resolve(baseProjectPath.resolve(
                Paths.get(getModelPackage().replace(".", "/") + "/" + modelName + ".java")));
    }

    /**
     * Generates all of the Request and Response handlers described within the Ds3ApiSpec
     * excluding SpectraInternal requests.
     * @throws IOException
     * @throws TemplateException
     */
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

    /**
     * Generates the Client and ClientImpl code that contains all non-SpectraInternal requests
     * that are described within the Ds3ApiSpec
     * @throws IOException
     * @throws TemplateException
     */
    private void generateClient() throws IOException, TemplateException {
        final ImmutableList<Ds3Request> requests = removeSpectraInternalRequests(spec.getRequests());
        if (isEmpty(requests)) {
            LOG.info("Not generating client: no requests.");
            return;
        }
        final Template clientTmpl = config.getTemplate("client/ds3client_template.ftl");
        final Client client = ClientConverter.toClient(requests, ROOT_PACKAGE_PATH);
        final Path clientPath = toClientPath("Ds3Client.java");

        LOG.info("Getting outputstream for file:" + clientPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(clientPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            clientTmpl.process(client, writer);
        }

        final Template clientImplTmpl = config.getTemplate("client/ds3client_impl_template.ftl");
        final Path clientImplPath = toClientPath("Ds3ClientImpl.java");

        LOG.info("Getting outputstream for file:" + clientPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(clientImplPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            clientImplTmpl.process(client, writer);
        }
    }

    /**
     * Converts a file name into the path containing said file within the client path
     * @param fileName The name of a file
     * @return The client path to the given file
     */
    private Path toClientPath(final String fileName) {
        return destDir.resolve(baseProjectPath.resolve(Paths.get(ROOT_PACKAGE_PATH.replace(".", "/") + "/" + fileName)));
    }

    /**
     * Generates the code for the Response handler described in a Ds3Request
     * @param ds3Request A Ds3Request
     * @throws IOException
     * @throws TemplateException
     */
    private void generateResponse(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getResponseTemplate(ds3Request);

        final Response response = toResponse(ds3Request);
        final Path responsePath = getPathFromPackage(ds3Request, response.getName());

        LOG.info("Getting outputstream for file:" + responsePath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(responsePath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(response, writer);
        }
    }

    /**
     * Converts a Ds3Request into a Response model
     * @param ds3Request A Ds3Request
     * @return A Response
     */
    private Response toResponse(final Ds3Request ds3Request) {
        return ResponseConverter.toResponse(ds3Request, getCommandPackage(ds3Request));
    }

    /**
     * Gets the Response template that is used to generate the given Ds3Request's
     * Response handler
     * @param ds3Request A Ds3Request
     * @return The appropriate template to generate the required Response
     * @throws IOException
     */
    private Template getResponseTemplate(final Ds3Request ds3Request) throws IOException {
        if (isAllocateJobChunkRequest(ds3Request)) {
            return config.getTemplate("response/allocate_job_chunk_response_template.ftl");
        }
        if (isGetJobChunksReadyForClientProcessingRequest(ds3Request)) {
            return config.getTemplate("response/get_job_chunks_ready_response_template.ftl");
        }
        if (isHeadBucketRequest(ds3Request)) {
            return config.getTemplate("response/head_bucket_response_template.ftl");
        }
        if (isHeadObjectRequest(ds3Request)) {
            return config.getTemplate("response/head_object_response_template.ftl");
        }
        if (isBulkRequest(ds3Request)) {
            return config.getTemplate("response/bulk_response_template.ftl");
        } else {
            return config.getTemplate("response/response_template.ftl");
        }
    }

    /**
     * Determines if a Ds3Request is Head Bucket Request
     */
    private static boolean isHeadBucketRequest(final Ds3Request request) {
        return request.getClassification() == Classification.amazons3
                && request.getBucketRequirement() == Requirement.REQUIRED
                && request.getHttpVerb() == HttpVerb.HEAD
                && request.getObjectRequirement() == Requirement.NOT_ALLOWED;
    }

    /**
     * Determines if a Ds3Request is Head Object Request
     */
    private static boolean isHeadObjectRequest(final Ds3Request request) {
        return request.getClassification() == Classification.amazons3
                && request.getBucketRequirement() == Requirement.REQUIRED
                && request.getHttpVerb() == HttpVerb.HEAD
                && request.getObjectRequirement() == Requirement.REQUIRED;
    }

    /**
     * Determines if a Ds3Request is Allocate Job Chunk Request
     */
    private static boolean isAllocateJobChunkRequest(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getResource() == Resource.JOB_CHUNK
                && request.getAction() == Action.MODIFY
                && request.getHttpVerb() == HttpVerb.PUT
                && request.getOperation() == Operation.ALLOCATE;
    }

    /**
     * Determines if a Ds3Request is Get Job Chunks Ready For Client Processing Request
     */
    private static boolean isGetJobChunksReadyForClientProcessingRequest(final Ds3Request request) {
        return request.getClassification() == Classification.spectrads3
                && request.getResource() == Resource.JOB_CHUNK
                && request.getAction() == Action.LIST
                && request.getHttpVerb() == HttpVerb.GET;
    }

    /**
     * Gets the command package suitable for the given Ds3Request. SpectraDs3 commands
     * have a separate package, as do notifications.
     * @param ds3Request A Ds3Request
     * @return The command package that is suitable for the given Ds3Request
     */
    private static String getCommandPackage(final Ds3Request ds3Request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(COMMANDS_PACKAGE_PATH);
        if (ds3Request.getClassification() == Classification.spectrads3) {
            builder.append(SPECTRA_DS3_PACKAGE);
        }
        if (isNotificationRequest(ds3Request)) {
            builder.append(NOTIFICATION_PACKAGE);
        }
        return builder.toString();
    }

    /**
     * Generates the code for the Request handler described in a Ds3Request
     * @param ds3Request A Ds3Request
     * @throws IOException
     * @throws TemplateException
     */
    private void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getRequestTemplate(ds3Request);

        final Request request = toRequest(ds3Request);
        final Path requestPath = getPathFromPackage(ds3Request, request.getName());

        LOG.info("Getting outputstream for file:" + requestPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(requestPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(request, writer);
        }
    }

    /**
     * Returns the file system path for a request given it's package
     * @param ds3Request A Ds3Request
     * @param fileName The file name to be given to this Ds3Request
     * @return The system path to this Ds3Request's generated code
     */
    private Path getPathFromPackage(final Ds3Request ds3Request, final String fileName) {
        return destDir.resolve(baseProjectPath.resolve(
                Paths.get(getCommandPackage(ds3Request).replace(".", "/") + "/" + fileName + ".java")));
    }

    /**
     * Converts a Ds3Request into a Request model
     * @param ds3Request A Ds3Request
     * @return A Request model
     */
    private Request toRequest(final Ds3Request ds3Request) {
        return RequestConverter.toRequest(ds3Request, getCommandPackage(ds3Request));
    }

    /**
     * Gets the appropriate template that will generate the code for this
     * Ds3Request's request handler
     * @param ds3Request A Ds3Request
     * @return The appropriate template to generate the required Request
     * @throws IOException
     */
    private Template getRequestTemplate(final Ds3Request ds3Request) throws IOException {
        if (isBulkRequest(ds3Request)) {
            return config.getTemplate("request/bulk_request_template.ftl");
        } else if (isPhysicalPlacementRequest(ds3Request)) {
            return config.getTemplate("request/physical_placement_request_template.ftl");
        } else if (isMultiFileDeleteRequest(ds3Request)) {
            return config.getTemplate("request/multi_file_delete_request_template.ftl");
        } else if (isGetObjectRequest(ds3Request)) {
            return config.getTemplate("request/get_object_template.ftl");
        } else if (isCreateObjectRequest(ds3Request)) {
            return config.getTemplate("request/create_object_template.ftl");
        } else if (isDeleteNotificationRequest(ds3Request)) {
            return config.getTemplate("request/delete_notification_request_template.ftl");
        } else if (isCreateNotificationRequest(ds3Request)) {
            return config.getTemplate("request/create_notification_request_template.ftl");
        } else if (isGetNotificationRequest(ds3Request)) {
            return config.getTemplate("request/get_notification_request_template.ftl");
        } else {
            return config.getTemplate("request/request_template.ftl");
        }
    }
}
