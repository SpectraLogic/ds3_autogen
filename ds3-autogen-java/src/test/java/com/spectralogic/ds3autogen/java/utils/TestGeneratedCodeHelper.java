package com.spectralogic.ds3autogen.java.utils;

import com.spectralogic.ds3autogen.api.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.when;

public final class TestGeneratedCodeHelper {

    public final static String CLIENT_PATH = "./ds3-sdk/src/main/java/com/spectralogic/ds3client/";

    public enum PathType { REQUEST, RESPONSE }

    private TestGeneratedCodeHelper() { }

    public static String getPathName(final String requestName, final String path, final PathType pathType) {
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

    public static ByteArrayOutputStream setupOutputStream(
            final FileUtils fileUtils,
            final String pathName) throws IOException {
        final Path path = Paths.get(pathName);
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024 * 8);
        when(fileUtils.getOutputFile(path)).thenReturn(outputStream);
        return outputStream;
    }
}
