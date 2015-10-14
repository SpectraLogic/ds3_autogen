package com.spectralogic.ds3autogen.java;

import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Requirement;
import com.spectralogic.ds3autogen.java.converters.RequestConverter;
import com.spectralogic.ds3autogen.java.models.Request;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCodeGenerator implements CodeGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(JavaCodeGenerator.class);

    private static final Path baseProjectPath = Paths.get("ds3-sdk/src/main/java/");

    private static final String ROOT_PACKAGE = "com.spectralogic.ds3client";
    private static final String COMMANDS_PACKAGE = ROOT_PACKAGE + ".commands";

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

    private void generateCommands() throws IOException, TemplateException {
        for(final Ds3Request request : spec.getRequests()) {
            generateRequest(request);
        }
    }

    private void generateRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        final Template tmpl = config.getTemplate("request_template.tmpl");

        final Request request = RequestConverter.toRequest(ds3Request, COMMANDS_PACKAGE);
        final Path requestPath = destDir.resolve(baseProjectPath.resolve(Paths.get(COMMANDS_PACKAGE.replace(".", "/") + "/" + request.getName() + ".java")));

        LOG.info("Getting outputstream for file:" + requestPath.toString());

        try (final OutputStream outStream = fileUtils.getOutputFile(requestPath);
             final Writer writer = new OutputStreamWriter(outStream)) {
            tmpl.process(request, writer);
        }

    }


}
