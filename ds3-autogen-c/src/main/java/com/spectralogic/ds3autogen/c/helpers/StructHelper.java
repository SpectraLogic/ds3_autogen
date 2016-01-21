package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.c.models.Struct;
import com.spectralogic.ds3autogen.c.models.StructMember;
import com.spectralogic.ds3autogen.utils.Helper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Set;

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
                return getResponseTypeName(element.getComponentType()) + "**";

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
            builder.add(new StructMember(convertDs3ElementType(currentElement), getNameUnderscores(currentElement.getName())));
        }
        return builder.build();
    }

    public static boolean containsExistingStructs(final Struct struct, final Set<String> existingStructs) {
        for (final StructMember structMember: struct.getVariables()) {
            final String testType = StringUtils.stripEnd(structMember.getType(), "*");
            if (!existingStructs.contains(testType)) {
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

    public static String generateStructMemberParserLine(final StructMember structMember, final String parserFunction) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(indent(3)).
                append("response->").
                append(Helper.camelToUnderscore(structMember.getName())).
                append(" = ").
                append(parserFunction).append("\n");
        return outputBuilder.toString();
    }


    public static String generateStructMemberArrayParserBlock(final String structResponseTypeName, final StructMember structMember) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(indent(3)).append("GPtrArray* ").append(structMember.getName()).append("_array = _parse_").append(structResponseTypeName).append("(log, doc, child_node);\n").
                append(indent(3)).append("response").append("->").append(structMember.getName()).append(" = (").append(structMember.getType()).append(")").append(structMember.getName()).append("_array->pdata;\n").
                append(indent(3)).append("response").append("->num_").append(structMember.getName()).append(" = ").append(structMember.getName()).append("_array->len;\n").
                append(indent(3)).append("g_ptr_array_free(").append(structMember.getName()).append("_array, FALSE);\n");
        return outputBuilder.toString();
    }

    public static String getParseStructMemberBlock(final String structName, final StructMember structMember) throws ParseException {
        switch (structMember.getType()) {
            case "ds3_str*":
                return generateStructMemberParserLine(structMember, "xml_get_string(doc, child_node);");
            case "double":
            case "long":
                return generateStructMemberParserLine(structMember, "xml_get_uint64(doc, child_node);");
            case "int":
                return generateStructMemberParserLine(structMember, "xml_get_uint16(doc, child_node);");
            case "ds3_bool":
                return generateStructMemberParserLine(structMember, "xml_get_bool(doc, child_node);");

            default:
                if (structMember.getType().endsWith("**")) { // array of another type
                    return generateStructMemberArrayParserBlock(getResponseTypeName(structName), structMember);
                }
                return generateStructMemberParserLine(structMember, getParserFunctionName(structMember.getName()) + "(log, doc, child_node);");
        }
    }

    public static String generateResponseParser(final String structName, final ImmutableList<StructMember> variables) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (int structMemberIndex = 0; structMemberIndex < variables.size(); structMemberIndex++) {
            outputBuilder.append(indent(2));

            if (structMemberIndex > 0) {
                outputBuilder.append("} else ");
            }

            final StructMember currentStructMember = variables.get(structMemberIndex);

            outputBuilder.append("if (element_equal(child_node, \"").append(Helper.underscoreToCamel(currentStructMember.getName())).append("\")) {").append("\n");
            outputBuilder.append(getParseStructMemberBlock(structName, currentStructMember));
        }
        // TODO Leaving the catch case commented out unless needed.
        /*
        outputBuilder.append(indent(3)).append("} else {").append("\n");
        outputBuilder.append(indent(4)).
                append("ds3_log_message(log, DS3_ERROR, \"Unknown element[%s]\\n\", child_node->name);").append("\n");
        */
        outputBuilder.append(indent(2)).append("}").append("\n");

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
