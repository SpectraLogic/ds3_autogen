/*
 * ******************************************************************************
 *   Copyright 2014-2017 Spectra Logic Corporation. All Rights Reserved.
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
import com.spectralogic.ds3autogen.api.models.enums.Operation;
import com.spectralogic.ds3autogen.java.helpers.JavaHelper;
import com.spectralogic.ds3autogen.java.models.Element;
import com.spectralogic.ds3autogen.java.models.parseresponse.BaseParseResponse;
import com.spectralogic.ds3autogen.utils.Helper;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.java.helpers.JavaHelper.getType;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;
import static org.junit.Assert.assertTrue;

public final class TestHelper {

    public enum Scope { PUBLIC, PRIVATE }

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
        final Pattern searchString = Pattern.compile("\\s+public " + requestName + "\\([^\n]+"
                + paramType + " " + Helper.uncapFirst(paramName) + "[^\n\\)]*\\)",
                Pattern.MULTILINE | Pattern.UNIX_LINES);

        return searchString.matcher(code).find();
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

    /**
     * Checks if the code contains a constructor with the specified sorted arguments
     */
    public static boolean hasConstructor(
            final String requestName,
            final ImmutableList<Arguments> arguments,
            final String code) {
        final String expected = "public " + requestName + "(" + JavaHelper.constructorArgs(arguments) + ")";
        return code.contains(expected);
    }

    /**
     * Checks if the code contains a constructor with the specified arguments
     */
    public static boolean hasUnsortedConstructor(
            final String requestName,
            final ImmutableList<Arguments> arguments,
            final String code) {
        final String params = arguments.stream()
                .map(i -> "final " + getType(i) + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
        final String expected = "public " + requestName + "(" + params + ")";
        return code.contains(expected);
    }

    public static boolean hasCopyright(final String code) {
        final String copyright =
                "/*\n" +
                " * ******************************************************************************\n" +
                " *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.\n" +
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
                + "(final " + requestName + " request)";
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
        builder.append("    @JsonProperty(\"")
                .append(Helper.capFirst(xmlName))
                .append("\")\n");
        if (isList) {
            builder.append("    @JacksonXmlElementWrapper(useWrapping = ")
                    .append(Boolean.toString(name.equals(xmlName)).toLowerCase())
                    .append(")\n");
        }
        builder.append("    private ")
                .append(type)
                .append(" ")
                .append(Helper.uncapFirst(name));
        if (type.contains("List")) {
            builder.append(" = new ArrayList<>()");
        }
        builder.append(";");
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

    public static void checkBulkResponseParserCode(final String responseName, final String code) {
        assertTrue(isOfPackage("com.spectralogic.ds3client.commands.parsers", code));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.interfaces.AbstractResponseParser", code));
        assertTrue(hasImport("com.spectralogic.ds3client.networking.WebResponse", code));
        assertTrue(hasImport("java.io.IOException", code));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.spectrads3." + responseName, code));
        assertTrue(hasImport("com.spectralogic.ds3client.commands.parsers.utils.ResponseParserUtils", code));

        final BaseParseResponse expectedParsing = new BaseParseResponse(responseName, "MasterObjectList");
        final String expectedParsingCode = "if (ResponseParserUtils.getSizeFromHeaders(response.getHeaders()) == 0) {\n" +
                "                    return new " + responseName + "(null, this.getChecksum(), this.getChecksumType());\n" +
                "                }\n" +
                "                " + expectedParsing.toJavaCode();

        assertTrue(code.contains(expectedParsingCode));
    }
}
