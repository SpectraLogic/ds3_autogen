package com.spectralogic.ds3autogen.java.utils;

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
}
