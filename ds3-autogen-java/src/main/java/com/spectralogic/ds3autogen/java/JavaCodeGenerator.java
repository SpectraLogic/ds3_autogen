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
import com.spectralogic.ds3autogen.api.models.enums.Classification;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Request;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.java.converters.ClientConverter;
import com.spectralogic.ds3autogen.java.generators.requestmodels.*;
import com.spectralogic.ds3autogen.java.generators.responsemodels.BaseResponseGenerator;
import com.spectralogic.ds3autogen.java.generators.responsemodels.BulkResponseGenerator;
import com.spectralogic.ds3autogen.java.generators.responsemodels.CodesResponseGenerator;
import com.spectralogic.ds3autogen.java.generators.responsemodels.ResponseModelGenerator;
import com.spectralogic.ds3autogen.java.generators.typemodels.*;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import com.spectralogic.ds3autogen.java.models.Client;
import com.spectralogic.ds3autogen.java.models.Model;
import com.spectralogic.ds3autogen.java.models.Request;
import com.spectralogic.ds3autogen.java.models.Response;
import com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import com.spectralogic.ds3autogen.utils.Helper;
import freemarker.template.*;
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
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isCompleteMultiPartUploadRequest;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isChecksumType;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isCommonPrefixesType;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isHttpErrorType;

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

    public JavaCodeGenerator() throws TemplateModelException {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(JavaCodeGenerator.class, "/tmpls");
        config.setSharedVariable("javaHelper", JavaHelper.getInstance());
        config.setSharedVariable("helper", Helper.getInstance());
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
                spec.getRequests());

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
        final TypeModelGenerator<?> modelGenerator = getModelGenerator(ds3Type);
        return modelGenerator.generate(ds3Type, packageName);
    }

    /**
     * Retrieves the associated type model generator for the specified Ds3TYpe
     */
    private TypeModelGenerator<?> getModelGenerator(final Ds3Type ds3Type) {
        if (isChecksumType(ds3Type)) {
            return new ChecksumTypeGenerator();
        }
        if (Ds3TypeClassificationUtil.isJobsApiBean(ds3Type)) {
            return new JobsApiBeanTypeGenerator();
        }
        if (isCommonPrefixesType(ds3Type)) {
            return new CommonPrefixGenerator();
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
        if (isHttpErrorType(ds3Type)) {
            return config.getTemplate("models/http_error_template.ftl");
        }
        if (isChecksumType(ds3Type)) {
            return config.getTemplate("models/checksum_type_template.ftl");
        }
        if (isS3Object(ds3Type)) {
            return config.getTemplate("models/s3object_model_template.ftl");
        }
        if (isBulkObject(ds3Type)) {
            return config.getTemplate("models/bulk_object_template.ftl");
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
     * Determines if a given Ds3Type is a BulkObject
     */
    private boolean isBulkObject(final Ds3Type ds3type) {
        return ds3type.getName().endsWith(".BulkObject");
    }

    /**
     * Determines if a given Ds3Type is the S3Object
     */
    private boolean isS3Object(final Ds3Type ds3type) {
        return ds3type.getName().endsWith(".S3Object");
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
        final ImmutableList<Ds3Request> requests = spec.getRequests();
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
        final ImmutableList<Ds3Request> requests = spec.getRequests();
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
        final ResponseModelGenerator<?> modelGenerator = getResponseTemplateModelGenerator(ds3Request);
        return modelGenerator.generate(ds3Request, getCommandPackage(ds3Request));
    }
    
    /**
     * Retrieves the associated response generator for the specified Ds3Request
     */
    private ResponseModelGenerator<?> getResponseTemplateModelGenerator(final Ds3Request ds3Request) {
        if (isAllocateJobChunkRequest(ds3Request)
                || isHeadObjectRequest(ds3Request)
                || isHeadBucketRequest(ds3Request)) {
            return new CodesResponseGenerator();
        }
        if (isBulkRequest(ds3Request)) {
            return new BulkResponseGenerator();
        }
        return new BaseResponseGenerator();
    }

    /**
     * Gets the Response template that is used to generate the given Ds3Request's
     * Response handler
     * @param ds3Request A Ds3Request
     * @return The appropriate template to generate the required Response
     * @throws IOException
     */
    private Template getResponseTemplate(final Ds3Request ds3Request) throws IOException {
        if (supportsPaginationRequest(ds3Request)) {
            return config.getTemplate("response/pagination_headers_response_template.ftl");
        }
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
        }
        if (isGetObjectAmazonS3Request(ds3Request)) {
            return config.getTemplate("response/get_object_response_template.ftl");
        }
        return config.getTemplate("response/response_template.ftl");
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
        if (ds3Request.getClassification() == Classification.spectrainternal) {
            builder.append(SPECTRA_INTERNAL_PACKAGE);
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
        final RequestModelGenerator<?> modelGenerator = getTemplateModelGenerator(ds3Request);
        return modelGenerator.generate(ds3Request, getCommandPackage(ds3Request));
    }

    /**
     * Retrieves the associated request generator for the specified Ds3Request
     */
    private static RequestModelGenerator<?> getTemplateModelGenerator(final Ds3Request ds3Request) {
        if (hasStringRequestPayload(ds3Request)) {
            return new StringRequestPayloadGenerator();
        }
        if (isBulkRequest(ds3Request)) {
            return new BulkRequestGenerator();
        }
        if (hasListObjectsRequestPayload(ds3Request)) {
            return new ObjectsRequestPayloadGenerator();
        }
        if (isCreateObjectRequest(ds3Request)) {
            return new CreateObjectRequestGenerator();
        }
        if (isCreateNotificationRequest(ds3Request)) {
            return new CreateNotificationRequestGenerator();
        }
        if ((isGetNotificationRequest(ds3Request) || isDeleteNotificationRequest(ds3Request)) && ds3Request.includeIdInPath()) {
            return new NotificationRequestGenerator();
        }
        if (isGetObjectRequest(ds3Request)) {
            return new  GetObjectRequestGenerator();
        }
        if (isMultiFileDeleteRequest(ds3Request)) {
            return new MultiFileDeleteRequestGenerator();
        }
        if (isCreateMultiPartUploadPartRequest(ds3Request)) {
            return new StreamRequestPayloadGenerator();
        }
        if (isCompleteMultiPartUploadRequest(ds3Request)) {
            return new CompleteMultipartUploadRequestGenerator();
        }
        return new BaseRequestGenerator();
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
        } else if(hasStringRequestPayload(ds3Request)) {
            return config.getTemplate("request/request_with_string_payload_template.ftl");
        } else if (hasListObjectsRequestPayload(ds3Request)) {
            return config.getTemplate("request/objects_request_payload_request_template.ftl");
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
        } else if (isGetNotificationRequest(ds3Request) && ds3Request.includeIdInPath()) {
            return config.getTemplate("request/get_notification_request_template.ftl");
        } else if (isGetJobRequest(ds3Request)) {
            return config.getTemplate("request/get_job_request_template.ftl");
        } else if (isCompleteMultiPartUploadRequest(ds3Request)) {
            return config.getTemplate("request/complete_multipart_upload_template.ftl");
        }
        return config.getTemplate("request/request_template.ftl");
    }
}
