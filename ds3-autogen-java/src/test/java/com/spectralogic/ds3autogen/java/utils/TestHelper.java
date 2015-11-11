package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;

public class TestHelper {

    private final static TestHelper testHelper = new TestHelper();

    public enum Scope { PUBLIC, PROTECTED, PRIVATE }

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

    public static boolean hasStaticMethod(
            final String methodName,
            final String returnType,
            final Scope scope,
            final String code) {
        return code.contains(scope.toString().toLowerCase() + " static " + returnType + " " + methodName + "(");
    }

    //Checks if a parameter is of the specified type in the class constructor, get method,
    //and within the class parameter list.  It only checks the constructor if the parameter is inherited
    public static boolean isReqParamOfType(
            final String paramName,
            final String paramType,
            final String requestName,
            final String code,
            final boolean isInherited) {
        if (!isInherited) {
            return doesConstructorContainParam(paramName, paramType, requestName, code)
                    && code.contains("private final " + paramType + " " + JavaHelper.uncapFirst(paramName))
                    && code.contains("public " + paramType + " get" + JavaHelper.capFirst(paramName) + "()");
        }
        return doesConstructorContainParam(paramName, paramType, requestName, code);
    }

    //Checks if a parameter is of the specified type in the get method, in the 'with' constructor,
    //and the class parameter list. It only checks the with constructor if the parameter is inherited
    public static boolean isOptParamOfType(
            final String paramName,
            final String paramType,
            final String requestName,
            final String code,
            final boolean isInherited) {
        if (!isInherited) {
            return isWithConstructorOfType(paramName, paramType, requestName, code)
                    && code.contains("private " + paramType + " " + JavaHelper.uncapFirst(paramName))
                    && code.contains("public " + paramType + " get" + JavaHelper.capFirst(paramName) + "()");
        }
        return isWithConstructorOfType(paramName, paramType, requestName, code);
    }

    private static  boolean isWithConstructorOfType(
            final String paramName,
            final String paramType,
            final String requestName,
            final String code) {
        return code.contains("public " + requestName + " with" + JavaHelper.capFirst(paramName)
                + "(final " + paramType + " " + JavaHelper.uncapFirst(paramName) + ")");
    }

    private static boolean doesConstructorContainParam(
            final String paramName,
            final String paramType,
            final String requestName,
            final String code) {
        final String constructorLine = getLineContaining(code, "public " + requestName + "(");
        if (constructorLine == null) {
            return false;
        }
        return constructorLine.contains("final " + paramType + " " + JavaHelper.uncapFirst(paramName));
    }

    private static String getLineContaining(final String input, final String desiredContent) {
        final String[] separated = input.split("\n");
        for (final String str : separated) {
            if (str.contains(desiredContent)) {
                return str;
            }
        }
        return null;
    }

    public static boolean hasImport(final String importName, final String code) {
        return code.contains("import " + importName + ";");
    }

    public static boolean isOfPackage(final String packageName, final String code) {
        return code.contains("package " + packageName + ";");
    }

    public static boolean hasOperation(final Operation operation, final String code) {
        return code.contains("this.getQueryParams().put(\"operation\", \"" + operation.toString().toLowerCase() + "\");");
    }

    public static boolean doesNotHaveOperation(final String code) {
        return !code.contains("this.getQueryParams().put(\"operation\", ");
    }

    public static boolean hasConstructor(
            final String requestName,
            final ImmutableList<Arguments> arguments,
            final String code) {
        final String expected = "public " + requestName + "(" + JavaHelper.constructorArgs(arguments) + ")";
        return code.contains(expected);
    }

    public static boolean hasCopyright(final String code) {
        final String copyright =
                "/*\n" +
                " * ******************************************************************************\n" +
                " *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.\n" +
                " *   Licensed under the Apache License, Version 2.0 (the \"License\"). You may not use\n" +
                " *   this file except in compliance with the License. A copy of the License is located at\n" +
                " *\n" +
                " *   http://www.apache.org/licenses/LICENSE-2.0\n" +
                " *\n" +
                " *   or in the \"license\" file accompanying this file.\n" +
                " *   This file is distributed on an \"AS IS\" BASIS, WITHOUT WARRANTIES OR\n" +
                " *   CONDITIONS OF ANY KIND, either express or implied. See the License for the\n" +
                " *   specific language governing permissions and limitations under the License.\n" +
                " * ****************************************************************************\n" +
                " */\n" +
                "\n" +
                "// This code is auto-generated, do not modify";

        return code.replace("\r\n", "\n").contains(copyright);
    }

    public static boolean hasPath(final String path, final String code) {
        return code.contains("return " + path + ";");
    }

    public static boolean hasCommand(final String requestName, final String code) {
        return code.contains(requestName.replace("Request", "Response")
                + " " + JavaHelper.uncapFirst(requestName.replace("RequestHandler", ""))
                + "(" + requestName + " request)");
    }
}
