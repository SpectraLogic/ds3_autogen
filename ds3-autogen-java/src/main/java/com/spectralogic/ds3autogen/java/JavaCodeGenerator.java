package com.spectralogic.ds3autogen.java;

import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.models.Ds3Request;

import java.nio.file.Path;

public class JavaCodeGenerator implements CodeGenerator {

    private static final String ROOT_PACKAGE = "com.spectralogic.ds3client";
    private static final String COMMANDS_PACKAGE = ROOT_PACKAGE + ".commands";

    private Ds3ApiSpec spec;
    private FileUtils fileUtils;
    private Path destDir;

    @Override
    public void generate(final Ds3ApiSpec spec, final FileUtils fileUtils, final Path destDir) {
        this.spec = spec;
        this.fileUtils = fileUtils;
        this.destDir = destDir;

        generateCommands();
    }

    private void generateCommands() {
        for(final Ds3Request request : spec.getRequests()) {
            generateRequest(request);
        }
    }

    private void generateRequest(final Ds3Request request) {

    }
}
