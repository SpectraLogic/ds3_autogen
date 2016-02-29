package com.spectralogic.ds3autogen.net.utils;

import com.spectralogic.ds3autogen.Ds3SpecParserImpl;
import com.spectralogic.ds3autogen.api.*;
import com.spectralogic.ds3autogen.api.models.Ds3ApiSpec;
import com.spectralogic.ds3autogen.net.NetCodeGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;

/**
 * Used to generate net request code
 */
public class TestGenerateCode {

    final static String CLIENT_PATH = "./Ds3/";

    protected final ByteArrayOutputStream requestOutputStream;
    protected final ByteArrayOutputStream clientOutputStream;
    protected final ByteArrayOutputStream idsClientOutputStream;
    protected String requestCode;
    protected String clientCode;
    protected String idsClientCode;

    public enum PathType { REQUEST, RESPONSE }

    public TestGenerateCode(
            final FileUtils fileUtils,
            final String requestName,
            final String path) throws IOException {
        this.requestOutputStream = setupOutputStream(fileUtils, getPathName(requestName, path, PathType.REQUEST));
        this.clientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "Ds3Client.cs");
        this.idsClientOutputStream = setupOutputStream(fileUtils, CLIENT_PATH + "IDs3Client.cs");
    }

    public void generateCode(
            final FileUtils fileUtils,
            final String inputFileName) throws ResponseTypeNotFoundException, ParserException, TypeRenamingConflictException, IOException {
        final Ds3SpecParser parser = new Ds3SpecParserImpl();
        final Ds3ApiSpec spec = parser.getSpec(TestGenerateCode.class.getResourceAsStream(inputFileName));
        final CodeGenerator codeGenerator = new NetCodeGenerator();

        codeGenerator.generate(spec, fileUtils, Paths.get("."));

        requestCode = new String(requestOutputStream.toByteArray());
        clientCode = new String(clientOutputStream.toByteArray());
        idsClientCode = new String(idsClientOutputStream.toByteArray());
    }

    protected static ByteArrayOutputStream setupOutputStream(
            final FileUtils fileUtils,
            final String pathName) throws IOException {
        final Path path = Paths.get(pathName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(path)).thenReturn(outputStream);
        return outputStream;
    }

    protected static String getPathName(final String requestName, final String path, final PathType pathType) {
        final StringBuilder builder = new StringBuilder();
        builder.append(path);
        switch (pathType) {
            case REQUEST:
                builder.append(requestName);
                break;
            case RESPONSE:
                builder.append(requestName.replace("Request", "Response"));
        }
        builder.append(".cs");
        return builder.toString();
    }

    public String getRequestCode() {
        return this.requestCode;
    }

    public String getClientCode() {
        return this.clientCode;
    }

    public String getIdsClientCode() {
        return this.idsClientCode;
    }
}
