package com.spectralogic.ds3autogen.java.utils;

import com.spectralogic.d3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.CodeGenerator;
import com.spectralogic.ds3autogen.api.Ds3SpecParser;
import com.spectralogic.ds3autogen.api.FileUtils;
import com.spectralogic.ds3autogen.api.ParserException;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.java.JavaCodeGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;

public class TestGeneratedCode {
    private final ByteArrayOutputStream requestOutputStream;
    private final ByteArrayOutputStream responseOutputStream;
    private String requestGeneratedCode;
    private String responseGeneratedCode;

    private enum PathType { REQUEST, RESPONSE }

    public TestGeneratedCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path) throws IOException {
        this.requestOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.REQUEST));
        this.responseOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.RESPONSE));
    }

    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws IOException, ParserException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGeneratedCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new JavaCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        requestGeneratedCode = new String(requestOutputStream.toByteArray());
        responseGeneratedCode = new String(responseOutputStream.toByteArray());
    }

    private static ByteArrayOutputStream setupOutputStream(
            final FileUtils fileUtils,
            final String pathName) throws IOException {
        final Path path = Paths.get(pathName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(path)).thenReturn(outputStream);
        return outputStream;
    }

    private static String getPathName(final String requestName, final String path, final PathType pathType) {
        final StringBuilder builder = new StringBuilder();
        builder.append(path);
        switch (pathType) {
            case REQUEST:
                builder.append(requestName);
                break;
            case RESPONSE:
                builder.append(requestName.replace("Request", "Response"));
        }
        builder.append(".java");
        return builder.toString();
    }

    public String getRequestGeneratedCode() {
        return requestGeneratedCode;
    }

    public String getResponseGeneratedCode() {
        return responseGeneratedCode;
    }
}
