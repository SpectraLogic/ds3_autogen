package com.spectralogic.ds3autogen.java.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;
import com.spectralogic.ds3autogen.utils.Helper;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

public class JavaHelper {
    private final static JavaHelper javaHelper = new JavaHelper();
    private final static List<String> bulkBaseClassArgs = Arrays.asList("Priority", "WriteOptimization");
    private final static String indent = "    ";

    private JavaHelper() {}

    public static JavaHelper getInstance() {
        return javaHelper;
    }

    public static boolean isBulkRequestArg(final String name) {
        return bulkBaseClassArgs.contains(name);
    }

    public static String createWithConstructor(final Arguments arg, final String requestName) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (arg.getType().equals("void")) {
            stringBuilder.append(voidWithConstructor(arg, requestName));
        } else {
            stringBuilder.append(withConstructor(arg, requestName));
        }

        stringBuilder.append(indent(2) + "return this;\n" + indent(1) + "}\n");
        return stringBuilder.toString();
    }

    public static String createWithConstructorBulk(final Arguments arg, final String requestName) {
        final StringBuilder stringBuilder = new StringBuilder();
        if (bulkBaseClassArgs.contains(arg.getName())) {
            stringBuilder.append(indent(1) + "@Override\n"
                    + withConstructorFirstLine(arg, requestName)
                    + indent(2) + "super.with" + capFirst(arg.getName()) + "(" + uncapFirst(arg.getName()) + ");\n");
        } else if (arg.getName().equals("MaxUploadSize")) {
            stringBuilder.append(maxUploadSizeWithConstructor(arg, requestName));
        } else if (arg.getType().equals("void")) {
            stringBuilder.append(voidWithConstructor(arg, requestName));
        } else {
            stringBuilder.append(withConstructor(arg, requestName));
        }

        stringBuilder.append(indent(2) + "return this;\n" + indent(1) + "}\n");
        return stringBuilder.toString();
    }

    private static String withConstructor(final Arguments arg, final String requestName) {
        return withConstructorFirstLine(arg, requestName)
                + indent(2) + argAssignmentLine(arg.getName())
                + indent(2) + updateQueryParamLine(arg.getName(), argToString(arg));
    }

    private static String maxUploadSizeWithConstructor(final Arguments arg, final String requestName) {
        return withConstructorFirstLine(arg, requestName)
                + indent(2) + "if (" + uncapFirst(arg.getName()) + " > MIN_UPLOAD_SIZE_IN_BYTES) {\n"
                + indent(3) + putQueryParamLine(arg.getName(), argToString(arg)) + "\n"
                + indent(2) + "} else {\n"
                + indent(3) + putQueryParamLine(arg.getName(), "MAX_UPLOAD_SIZE_IN_BYTES") + "\n"
                + indent(2) + "}\n";
    }

    private static String voidWithConstructor(final Arguments arg, final String requestName) {
        return withConstructorFirstLine(arg, requestName)
                + indent(2) + "this." + uncapFirst(arg.getName()) + " = " + uncapFirst(arg.getName()) + ";\n"
                + indent(2) + "if (this." + uncapFirst(arg.getName()) + ") {\n"
                + indent(3) + putQueryParamLine(arg.getName(), "null") + "\n"
                + indent(2) + "} else {\n"
                + indent(3) + removeQueryParamLine(arg.getName())
                + indent(2) + "}\n";
    }

    private static String withConstructorFirstLine(final Arguments arg, final String requestName) {
        return indent(1) + "public " + requestName + " with" + capFirst(arg.getName()) + "(final " + Helper.getType(arg) + " " + uncapFirst(arg.getName()) + ") {\n";
    }

    private static String argAssignmentLine(final String name) {
        return "this." + uncapFirst(name) + " = " + uncapFirst(name) + ";\n";
    }

    private static String removeQueryParamLine(final String name) {
        return "this.getQueryParams().remove(\"" + Helper.camelToUnderscore(name) + "\");\n";
    }

    public static String putQueryParamLine(final Arguments arg) {
        return putQueryParamLine(arg.getName(), argToString(arg));
    }

    private static String putQueryParamLine(final String name, final String type) {
        return "this.getQueryParams().put(\"" + Helper.camelToUnderscore(name) + "\", " + type + ");";
    }

    private static String updateQueryParamLine(final String name, final String type) {
        return "this.updateQueryParam(\"" + Helper.camelToUnderscore(name) + "\", " + type + ");\n";
    }

    private static String indent(final int depth) {
        StringBuilder stringBuilder = new StringBuilder();
        if (depth <= 0) {
            return null;
        }
        for (int i = 0; i < depth; i++) {
            stringBuilder.append(indent);
        }
        return stringBuilder.toString();
    }

    public static String argToString(final Arguments arg) {
        if (arg.getType().equals("void")) {
            return "null";
        } else if (arg.getType().equals("String")) {
            return uncapFirst(arg.getName());
        } else if (arg.getType().equals("Integer") || arg.getType().equals("long")) {
            return capFirst(arg.getType()) + ".toString(" + uncapFirst(arg.getName()) + ")";
        } else {
            return uncapFirst(arg.getName()) + ".toString()";
        }
    }

    public static String argTypeList(final ImmutableList<Arguments> arguments) {
        if (arguments.isEmpty()) {
            return "";
        }
        final StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arguments.size(); i++) {
            stringBuilder.append(arguments.get(i).getType());
            if (i < arguments.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

    public static String capFirst(final String str) {
        return StringUtils.capitalize(str);
    }

    public static String uncapFirst(final String str) {
        return StringUtils.uncapitalize(str);
    }
}
