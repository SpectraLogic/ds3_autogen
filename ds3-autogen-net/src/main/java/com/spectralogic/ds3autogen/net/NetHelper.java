package com.spectralogic.ds3autogen.net;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Arguments;

import java.util.stream.Collectors;

import static com.spectralogic.ds3autogen.utils.Helper.capFirst;
import static com.spectralogic.ds3autogen.utils.Helper.sortConstructorArgs;
import static com.spectralogic.ds3autogen.utils.Helper.uncapFirst;

public final class NetHelper {
    private NetHelper() {
        // pass
    }

    public static String constructor(final ImmutableList<Arguments> args) {
        return sortConstructorArgs(args)
                .stream()
                .map(i -> getType(i) + " " + uncapFirst(i.getName()))
                .collect(Collectors.joining(", "));
    }

    /**
     * Gets the type of an argument, converting the type from Contract type to a .net type.
     * @param arg An Argument
     * @return The Java type of the Argument
     */
    public static String getType(final Arguments arg) {
        if (arg.getType() == null) {
            return "";
        }

        switch (arg.getType()) {
            case "void":
                return "bool";
            case "Integer":
                return "int";
            case "String":
                return "string";
            case "UUID":
                return "Guid";
            case "ChecksumType":
                return arg.getType() + ".Type";
            default:
                return arg.getType();
        }
    }

    /**
     * Creates the .net code for converting an argument to a String.
     */
    public static String argToString(final Arguments arg) {
        switch (arg.getType().toLowerCase()) {
            case "boolean":
            case "void":
                return "null";
            case "string":
                return capFirst(arg.getName());
            case "double":
            case "integer":
            case "int":
            case "long":
                return  capFirst(arg.getName()) + ".ToString()";
            default:
                return uncapFirst(arg.getName()) + ".ToString()";
        }
    }

    private final static NetHelper instance = new NetHelper();

    public static NetHelper getInstance() {
        return instance;
    }
}
