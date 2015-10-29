package com.spectralogic.ds3autogen.java.utils;

public class TestHelper {

    private final static TestHelper testHelper = new TestHelper();

    public enum Scope { PUBLIC, PRROTECTED, PRIVATE }

    private TestHelper() { }

    public static TestHelper getInstance() {
        return testHelper;
    }

    public static boolean extendsClass(final String childClass, final String baseClass, final String code) {
        return code.contains("public class " + childClass + " extends " + baseClass);
    }

    //Checks if code contains a non-static method.
    public static boolean hasMethod(
            final String methodName,
            final String returnType,
            final Scope scope,
            final String code) {
        return code.contains(scope.toString().toLowerCase() + " " + returnType + " " + methodName + "(");
    }
}
