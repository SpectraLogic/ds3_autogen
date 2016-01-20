package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Queue;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

public class StructHelper {
    private static final Logger LOG = LoggerFactory.getLogger(StructHelper.class);
    private StructHelper() {}

    private final static StructHelper structHelper = new StructHelper();

    public static StructHelper getInstance() {
        return structHelper;
    }

    public static String getNameUnderscores(final String name) {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name)));
    }

    public static String getDs3TypeName(final String name) {
        return "ds3_" + getNameUnderscores(name);
    }

    public static String getResponseTypeName(final String name) {
        return getDs3TypeName(name) + "_response";
    }

    public static String getParserFunctionName(final String name) {
        return "_parse_" + getResponseTypeName(name);
    }

    public static String getFreeFunctionName(final String name) {
        return getResponseTypeName(name) + "_free";
    }

    public static String getParser(final StructMember structMember) throws ParseException {
        switch (structMember.getType()) {
            case "ds3_str*":
                return "xml_get_string(doc, child_node);";
            case "double":
            case "long":
                return "xml_get_uint64(doc, child_node);";
            case "int":
                return "xml_get_uint16(doc, child_node);";
            case "ds3_bool":
                return "xml_get_bool(doc, child_node);";

            case "java.util.Set":
            case "array":
                //throw new ParseException("Unknown element type" + getType(), 0);
                return "Skipping Array / Set Element";

            default:
                return getParserFunctionName(structMember.getName()) + "(log, doc, child_node);";
        }
    }

    public static String getFreeFunction(final StructMember structMember) throws ParseException {
        switch (structMember.getType()) {
            case "ds3_str*":
                return "ds3_str_free";
            // The following primitive types don't require a free
            case "uint64_t":
            case "double":
                return "";
            case "long":
                return "";
            case "int":
                return "";
            case "ds3_bool":
                return "";

            // build the name of the free function for the embedded type
            case "java.util.Set":
            case "array":
            default:
                return getFreeFunctionName(structMember.getType());
        }
    }

    public static String convertDs3ElementType(final Ds3Element element) throws ParseException {
        switch (element.getType()) {
            case "boolean":
                return "ds3_bool";
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return "ds3_str*";
            case "double":
                return "double";   // ex: 0.82
            case "java.lang.Long":
            case "long":
                return "uint64_t"; // size_t
            case "java.lang.Integer":
            case "int":
                return "int";
            case "java.util.Set":
            case "array":
                return getResponseTypeName(element.getType()) + "**";

            default:
                return getResponseTypeName(element.getType()) + "*";
        }
    }

    public static boolean isPrimitiveDs3Type(final String type){
        switch (type) {
            case "ds3_bool":
            case "ds3_str*":
            case "double":
            case "uint64_t":
            case "int":
                return true;
            default:
                // any complex sub element such as "com.spectralogic.s3.server.domain.UserApiBean"
                return false;
        }
    }

    public static boolean isPrimitive(final Struct struct) {
        for (final StructMember member : struct.getVariables()) {
            if (!isPrimitiveDs3Type(member.getType())) {
                return false;
            }
        }
        return true;
    }

    public static ImmutableList<StructMember> convertDs3Elements(ImmutableList<Ds3Element> elementsList) throws ParseException {
        final ImmutableList.Builder<StructMember> builder = ImmutableList.builder();
        for (final Ds3Element currentElement : elementsList) {
            String type;
            if (currentElement.getType().equals("array")) {
                type = currentElement.getComponentType();
            } else {
                type = currentElement.getType();
            }
            builder.add(new StructMember(convertDs3ElementType(currentElement), getNameUnderscores(currentElement.getName())));
        }
        return builder.build();
    }

    public static boolean containsExistingStructs(final Struct struct, final Queue existingStructs) {
        for (final StructMember structMember: struct.getVariables()) {
            if (!existingStructs.contains(structMember.getType())) {
                return false;
            }
        }
        return true;
    }

    public static String generateStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember member : structMembers) {
            outputBuilder.append(indent(1)).
                    append(member.getType()).
                    append(" ").
                    append(member.getName()).
                    append(";").
                    append("\n");
        }

        return outputBuilder.toString();
    }

    public static String generateResponseParser(final Struct struct) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (int currentStructMember = 0; currentStructMember < struct.getVariables().size(); currentStructMember++) {
            outputBuilder.append(indent(3));

            if (currentStructMember > 0) {
                outputBuilder.append(indent(2)).append("} else ");
            }

            final String currentStructMemberName = struct.getVariables().get(currentStructMember).getName();

            outputBuilder.append("if (element_equal(child_node, \"").append(Helper.underscoreToCamel(currentStructMemberName)).append("\")) {").append("\n");
            outputBuilder.append(indent(4)).
                    append(getResponseTypeName(struct.getName())).
                    append("->").
                    append(Helper.camelToUnderscore(currentStructMemberName)).
                    append(" = ").
                    append(getParser(struct.getVariables().get(currentStructMember))).append("\n");
        }
        // TODO Leaving the catch case commented out unless needed.
        /*
        outputBuilder.append(indent(3)).append("} else {").append("\n");
        outputBuilder.append(indent(4)).
                append("ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);").append("\n");
        */
        outputBuilder.append(indent(3)).append("}").append("\n");

        return outputBuilder.toString();
    }

    public static String generateFreeStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember structMember : structMembers) {
            final String freeFunc = getFreeFunction(structMember);
            if (freeFunc.length() == 0) continue;

            outputBuilder.append(indent(1)).
                    append(freeFunc).
                    append("(response_data->").
                    append(StructHelper.getNameUnderscores(structMember.getName())).
                    append(");").
                    append("\n");
        }

        return outputBuilder.toString();
    }
}
