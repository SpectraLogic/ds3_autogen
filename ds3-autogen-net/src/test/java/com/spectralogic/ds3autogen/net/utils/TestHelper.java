package com.spectralogic.ds3autogen.net.utils;

public class TestHelper {
    public static boolean extendsClass(final String getObjectRequestHandler, final String abstractRequest, final String generatedCode) {
        final String searchString = "public class " + getObjectRequestHandler + " : " + abstractRequest;
        return generatedCode.contains(searchString);
    }
}
