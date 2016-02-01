/*
 * ******************************************************************************
 *   Copyright 2014-2015 Spectra Logic Corporation. All Rights Reserved.
 *   Licensed under the Apache License, Version 2.0 (the "License"). You may not use
 *   this file except in compliance with the License. A copy of the License is located at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   or in the "license" file accompanying this file.
 *   This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
 *   CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 * ****************************************************************************
 */

package com.spectralogic.ds3autogen.java.utils;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.api.models.Operation;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.utils.Helper;

public final class TestHelper {

    public enum Scope { PUBLIC, PROTECTED, PRIVATE }

    private TestHelper() { }

    public static boolean extendsClass(final String childClass, final String baseClass, final String code) {
        return code.contains("public class " + childClass + " extends " + baseClass);
    }

    public static boolean extendsInterface(final String childClass, final String baseClass, final String code) {
        return code.contains("public interface " + childClass + " extends " + baseClass);
    }

    public static boolean implementsInterface(final String childClass, final String baseClass, final String code) {
        return code.contains("public class " + childClass + " implements " + baseClass);
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
                    && isReqVariable(paramName, paramType, code)
                    && hasGetter(paramName, paramType, code);
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
                    && isOptVariable(paramName, paramType, code)
                    && hasGetter(paramName, paramType, code);
        }
        return isWithConstructorOfType(paramName, paramType, requestName, code);
    }

    public static boolean isOptVariable(
            final String paramName,
            final String paramType,
            final String code) {
        return code.contains("private " + paramType + " " + Helper.uncapFirst(paramName));
    }

    public static boolean isReqVariable(
            final String paramName,
            final String paramType,
            final String code) {
        return code.contains("private final " + paramType + " " + Helper.uncapFirst(paramName));
    }

    private static  boolean isWithConstructorOfType(
            final String paramName,
            final String paramType,
            final String requestName,
            final String code) {
        return code.contains("public " + requestName + " with" + Helper.capFirst(paramName)
                + "(final " + paramType + " " + Helper.uncapFirst(paramName) + ")");
    }

    public static boolean doesConstructorContainParam(
            final String paramName,
            final String paramType,
            final String requestName,
            final String code) {
        final String constructorLine = getLineContaining(code, "public " + requestName + "(");
        return constructorLine != null
                && constructorLine.contains("final " + paramType + " " + Helper.uncapFirst(paramName));
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
        return code.contains(commandLine(requestName));
    }

    public static boolean hasCommand(final String requestName, final Scope scope, final String code) {
        return code.contains(scope.toString().toLowerCase() + " " + commandLine(requestName));
    }

    private static String commandLine(final String requestName) {
        return requestName.replace("Request", "Response")
                + " " + Helper.uncapFirst(requestName.replace("Request", ""))
                + "(" + requestName + " request)";
    }

    public static boolean hasModelVariable(final String name, final String type, final String code) {
        return hasModelVariable(name, name, type, false, code);
    }

    public static boolean hasParamAttribute(
            final String name,
            final String type,
            final String code) {
        final String xmlLine = "@JacksonXmlProperty(isAttribute = true, localName = \""
                + name + "\")\n"
                + "    private " + type + " " + Helper.uncapFirst(name) + ";";
        return code.contains(xmlLine)
                && hasGetter(name, type, code)
                && hasSetter(name, type, code);
    }

    public static boolean hasModelVariable(
            final String name,
            final String xmlName,
            final String type,
            final boolean isList,
            final String code) {
        return hasModelParam(name, xmlName, type, isList, code)
                && hasGetter(name, type, code)
                && hasSetter(name, type, code);
    }

    private static boolean hasModelParam(
            final String name,
            final String xmlName,
            final String type,
            final boolean isList,
            final String code) {
        final StringBuilder builder = new StringBuilder();
        builder.append("    @JsonProperty(\"").append(Helper.capFirst(xmlName)).append("\")\n");
        if (isList) {
            builder.append("    @JacksonXmlElementWrapper(useWrapping = " +
                    Boolean.toString(name.equals(xmlName)).toLowerCase() + ")\n");
        }
        builder.append("    private ").append(type).append(" ").append(Helper.uncapFirst(name)).append(";");
        return code.contains(builder.toString());
    }

    public static boolean hasModelConstructor(
            final String modelName,
            final ImmutableList<Element> elements,
            final String code) {
        final String expected = "public " + modelName + "(" + JavaHelper.getModelConstructorArgs(elements) + ")";
        return code.contains(expected);
    }

    public static boolean hasGetter(final String name, final String type, final String code) {
        return code.contains("public " + type + " get" + Helper.capFirst(name) + "()");
    }

    private static boolean hasSetter(final String name, final String type, final String code) {
        return code.contains("public void set" + Helper.capFirst(name)
                + "(final " + type + " " + Helper.uncapFirst(name) + ") {");
    }

    public static boolean isEnumClass(final String className, final String code) {
        return code.contains("public enum " + className + " {");
    }

    public static boolean enumContainsValue(final String value, final String code) {
        return code.contains("    " + value);
    }

    public static boolean constructorHasVoidQueryParam(final String paramName, final String code) {
        return code.contains("this.getQueryParams().put(\"" + paramName + "\", null);");
    }

    public static boolean constructorHasVarAssignment(final String paramName, final String code) {
        return code.contains("this." + paramName + " = " + paramName + ";");
    }
}
