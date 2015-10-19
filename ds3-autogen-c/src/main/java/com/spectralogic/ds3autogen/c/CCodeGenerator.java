package com.spectralogic.ds3autogen.c;

import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.models.Ds3Request;
import com.spectralogic.ds3autogen.api.models.Classification;
import com.spectralogic.ds3autogen.c.converters.*;
import com.spectralogic.ds3autogen.c.models.Request;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Path;

import java.nio.file.Paths;

import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CCodeGenerator implements CodeGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(CCodeGenerator.class);

    private final Configuration config = new Configuration(Configuration.VERSION_2_3_23);

    private Ds3ApiSpec spec;
    private FileUtils fileUtils;
    private Path destDir;

    public CCodeGenerator() {
        config.setDefaultEncoding("UTF-8");
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setClassForTemplateLoading(CCodeGenerator.class, "/templates");
    }
    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) throws IOException {
        this.spec = spec;
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        try {
            generateCommands();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    private void generateCommands() throws IOException, TemplateException {
        for (final Ds3Request request : spec.getRequests()) {
            generateInitRequest(request);
        }

        /*
        for (Map.Entry<String, Ds3Type> typeEntry : spec.getTypes().entrySet()) {
            System.out.println("Generating Type[" + typeEntry.getKey() + "][" + typeEntry.getValue().getName() + "]");
        }
        */
    }

    public void generateInitRequest(final Ds3Request ds3Request) throws IOException, TemplateException {
        Template template = null;
        Request request = null;

        System.out.println("Generating Request[" + ds3Request.getName() + "]");
        if (ds3Request.getClassification() == Classification.amazons3) {
            request = RequestConverter.toRequest(ds3Request);
            System.out.println("Loading template AmazonS3InitRequestHandler.tmplt");
            template = config.getTemplate("AmazonS3InitRequestHandler.tmplt");
            System.out.println("Template loaded!");
        } else if (ds3Request.getClassification() == Classification.spectrads3) {
            System.out.println("amazonS3 request");
        } else if (ds3Request.getClassification() == Classification.spectrainternal) /* && codeGenType != production */ {
            System.out.println("spectra internal request");
        }

        final Path outputPath = Paths.get(destDir + "/ds3_c_sdk/src/DeleteBucketRequestHandler.c");
        System.out.println("outputPath[" + outputPath.toString() + "]");
        final OutputStream outStream = fileUtils.getOutputFile(outputPath);
        final Writer writer = new OutputStreamWriter(outStream);
        template.process(request, writer);
    }
}
