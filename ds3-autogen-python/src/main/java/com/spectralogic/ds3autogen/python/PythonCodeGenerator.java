/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.python.generators.client.BaseClientGenerator;
import com.spectralogic.ds3autogen.python.generators.client.ClientModelGenerator;
import com.spectralogic.ds3autogen.python.generators.request.BaseRequestGenerator;
import com.spectralogic.ds3autogen.python.generators.request.ObjectsPayloadGenerator;
import com.spectralogic.ds3autogen.python.generators.request.RequestModelGenerator;
import com.spectralogic.ds3autogen.python.generators.response.BaseResponseGenerator;
import com.spectralogic.ds3autogen.python.generators.response.HeadResponseGenerator;
import com.spectralogic.ds3autogen.python.generators.response.ResponseModelGenerator;
import com.spectralogic.ds3autogen.python.generators.type.BaseTypeGenerator;
import com.spectralogic.ds3autogen.python.generators.type.TypeModelGenerator;
import com.spectralogic.ds3autogen.python.helpers.PythonHelper;
import com.spectralogic.ds3autogen.python.model.client.BaseClient;
import com.spectralogic.ds3autogen.python.model.commands.CommandSet;
import com.spectralogic.ds3autogen.python.model.request.BaseRequest;
import com.spectralogic.ds3autogen.python.model.response.BaseResponse;
import com.spectralogic.ds3autogen.python.model.type.TypeDescriptor;
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

import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.hasFileObjectListPayload;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.removeUnusedTypes;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isHeadBucketRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isHeadObjectRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isCompleteMultiPartUploadRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isMultiFileDeleteRequest;

public class PythonCodeGenerator implements CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(PythonCodeGenerator.class);
    private static final Path BASE_PROJECT_PATH = Paths.get("ds3");

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private FileUtils fileUtils;
    private Path destDir;

    public PythonCodeGenerator() throws TemplateModelException {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(PythonCodeGenerator.class, "/tmpls");
        config.setSharedVariable("pythonHelper", PythonHelper.getInstance());
        config.setSharedVariable("helper", Helper.getInstance());
    }

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) {
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        try {
            final ImmutableList<Ds3Request> requests = spec.getRequests();
            final ImmutableMap<String, Ds3Type> typeMap = removeUnusedTypes(
                    spec.getTypes(),
                    spec.getRequests());

            generateCommands(requests, typeMap);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates the ds3.py python code which includes: request handlers, response handlers,
     * response payload descriptors, and the client
     */
    private void generateCommands(
            final ImmutableList<Ds3Request> ds3Requests,
            final ImmutableMap<String, Ds3Type> typeMap) throws IOException, TemplateException {
        if (isEmpty(ds3Requests)) {
            LOG.info("There are no requests to generate");
            return;
        }

        final ImmutableList<BaseRequest> baseRequests = toRequestModelList(ds3Requests);
        final ImmutableList<BaseResponse> baseResponses = toResponseModelList(ds3Requests, typeMap);
        final ImmutableList<TypeDescriptor> baseTypes = toTypeDescriptorList(typeMap);
        final ImmutableList<BaseClient> clientCommands = toClientCommands(ds3Requests);

        final CommandSet commandSet = new CommandSet(baseRequests, baseResponses, baseTypes, clientCommands);

        final Template tmpl = config.getTemplate("python/commands/all_commands.ftl");
        final Path path = toBaseProjectPath("ds3.py");

        LOG.info("Getting OutputStream for file: " + path.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(path);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(commandSet, writer);
        }
    }

    /**
     * Generates the python models for the client commands
     */
    protected static ImmutableList<BaseClient> toClientCommands(final ImmutableList<Ds3Request> ds3Requests) {
        if (isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }
        return ds3Requests.stream()
                .map(PythonCodeGenerator::toClientCommand)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Generates the python model for a client command
     */
    protected static BaseClient toClientCommand(final Ds3Request ds3Request) {
        final ClientModelGenerator<?> clientGenerator = new BaseClientGenerator();
        return clientGenerator.generate(ds3Request);
    }

    /**
     * Generates the python models for type descriptors, which are used to describe expected responses
     */
    protected static ImmutableList<TypeDescriptor> toTypeDescriptorList(final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeMap)) {
            return ImmutableList.of();
        }
        return typeMap.values().stream()
                .filter(type -> isEmpty(type.getEnumConstants())) //Do not generate descriptors for enums
                .map(ds3Type -> toTypeDescriptor(ds3Type, typeMap))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3Type into a python model descriptor
     */
    protected static  TypeDescriptor toTypeDescriptor(
            final Ds3Type ds3Type,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final TypeModelGenerator<?> typeGenerator = getTypeGenerator(ds3Type);
        return typeGenerator.generate(ds3Type, typeMap);
    }

    /**
     * Retrieves the Type Descriptor Generator associated with the Ds3Type
     */
    protected static TypeModelGenerator<?> getTypeGenerator(final Ds3Type ds3Type) {
        return new BaseTypeGenerator();
    }

    /**
     * Converts a file name into the path containing said file within the base project path
     */
    protected Path toBaseProjectPath(final String fileName) {
        return destDir.resolve(BASE_PROJECT_PATH + "/" + fileName);
    }

    /**
     * Converts all Ds3Requests into the python request handler models
     */
    protected static ImmutableList<BaseRequest> toRequestModelList(final ImmutableList<Ds3Request> ds3Requests) {
        if (isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }
        return ds3Requests.stream()
                .map(PythonCodeGenerator::toRequestModel)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3Request into a python request handler model
     */
    protected static BaseRequest toRequestModel(final Ds3Request ds3Request) {
        final RequestModelGenerator<?> requestGenerator = getRequestGenerator(ds3Request);
        return requestGenerator.generate(ds3Request);
    }

    /**
     * Retrieves the Request Generator associated with the Ds3Request
     */
    protected static RequestModelGenerator<?> getRequestGenerator(final Ds3Request ds3Request) {
        if (hasFileObjectListPayload(ds3Request)
                || isMultiFileDeleteRequest(ds3Request)
                || isCompleteMultiPartUploadRequest(ds3Request)) {
            return new ObjectsPayloadGenerator();
        }
        return new BaseRequestGenerator();
    }

    /**
     * Converts all Ds3Requests into the python response handler models
     */
    protected static ImmutableList<BaseResponse> toResponseModelList(
            final ImmutableList<Ds3Request> ds3Requests,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(ds3Requests)) {
            return ImmutableList.of();
        }
        return ds3Requests.stream()
                .map(response -> toResponseModel(response, typeMap))
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3Request into a python response handler model
     */
    protected static BaseResponse toResponseModel(
            final Ds3Request ds3Request,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final ResponseModelGenerator<?> responseGenerator = getResponseGenerator(ds3Request);
        return responseGenerator.generate(ds3Request, typeMap);
    }

    /**
     * Retrieves the Response Generator associated with the Ds3Request
     */
    protected static ResponseModelGenerator<?> getResponseGenerator(final Ds3Request ds3Request) {
        if(isHeadBucketRequest(ds3Request) || isHeadObjectRequest(ds3Request)) {
            return new HeadResponseGenerator();
        }
        return new BaseResponseGenerator();
    }
}
