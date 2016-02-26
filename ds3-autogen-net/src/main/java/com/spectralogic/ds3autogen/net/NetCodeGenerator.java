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
import com.spectralogic.ds3autogen.net.generators.requestmodels.BaseRequestGenerator;
import com.spectralogic.ds3autogen.net.generators.requestmodels.BulkPutRequestGenerator;
import com.spectralogic.ds3autogen.net.generators.requestmodels.GetObjectRequestGenerator;
import com.spectralogic.ds3autogen.net.generators.requestmodels.RequestModelGenerator;
import com.spectralogic.ds3autogen.net.model.request.BaseRequest;
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
import static com.spectralogic.ds3autogen.utils.ConverterUtil.removeSpectraInternalRequests;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isBulkPutRequest;
import static com.spectralogic.ds3autogen.utils.Ds3RequestClassificationUtil.isGetObjectRequest;

public class NetCodeGenerator implements CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(NetCodeGenerator.class);
    private static final String COMMANDS_NAMESPACE = "Ds3.Calls";
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
            generateCommands();
        } catch (final TemplateException e) {
            e.printStackTrace();
        }
    }

    private void generateCommands() throws TemplateException, IOException {
        final ImmutableList<Ds3Request> requests = removeSpectraInternalRequests(spec.getRequests());
        if (isEmpty(requests)) {
            LOG.info("There were no requests to generate");
            return;
        }
        for (final Ds3Request request : spec.getRequests()) {
            generateRequest(request);
        }
    }

    private void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = getRequestTemplate(ds3Request);
        final RequestModelGenerator<?> modelGenerator = getTemplateModelGenerator(ds3Request);
        final BaseRequest request = modelGenerator.generate(ds3Request);
        final Path requestPath = destDir.resolve(BASE_PROJECT_PATH.resolve(Paths.get(COMMANDS_NAMESPACE.replace(".", "/") + "/" + request.getName() + ".cs")));

        LOG.info("Getting outputstream for file:" + requestPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(requestPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(request, writer);
        }
    }

    private RequestModelGenerator<?> getTemplateModelGenerator(final Ds3Request ds3Request) {
        if (isGetObjectRequest(ds3Request)) {
            return new GetObjectRequestGenerator();
        }
        if (isBulkPutRequest(ds3Request)) {
            return new BulkPutRequestGenerator();
        }
        return new BaseRequestGenerator();
    }

    private Template getRequestTemplate(final Ds3Request request) throws IOException {
        if (isGetObjectRequest(request)) {
            return config.getTemplate("request/get_object_request.ftl");
        }
        if (isBulkPutRequest(request)) {
            return config.getTemplate("request/bulk_put_request.ftl");
        }
        return config.getTemplate("request/request_template.ftl");
    }
}
