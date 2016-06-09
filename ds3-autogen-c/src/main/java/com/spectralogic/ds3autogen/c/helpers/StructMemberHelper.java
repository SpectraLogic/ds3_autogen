package com.spectralogic.ds3autogen.c.helpers;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.c.models.StructMember;
import com.spectralogic.ds3autogen.utils.Helper;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;

import java.text.ParseException;

import static com.spectralogic.ds3autogen.utils.Helper.indent;

public class StructMemberHelper {
    private StructMemberHelper() {}

    private final static StructMemberHelper structMemberHelper = new StructMemberHelper();

    public static StructMemberHelper getInstance() {
        return structMemberHelper;
    }


    public static ImmutableList<StructMember> getWrappedListChildNodes(final ImmutableList<StructMember> structMembers) {
        return structMembers.stream()
                .filter(sm -> sm.getType().isArray())
                .filter(StructMember::hasWrapper)
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Used by ResponseParse.ftl and ResponseParserTopLevel.ftl
     */
    public static ImmutableList<StructMember> getUnwrappedListChildNodes(final ImmutableList<StructMember> structMembers) {
        return structMembers.stream()
                .filter(sm -> sm.getType().isArray())
                .filter(sm -> !sm.hasWrapper())
                .collect(GuavaCollectors.immutableList());
    }

    public static String generateStructMemberParserLine(final StructMember structMember, final String parserFunction) throws ParseException {
        return indent(3) + "response->" + Helper.camelToUnderscore(structMember.getName()) + " = " + parserFunction + "\n";
    }

    /**
     * Also applies to any element whose CollectionValueRenderingMode is "BLOCK_FOR_EVERY_ELEMENT"
     */
    public static String generateStructMemberDs3StrArrayBlock(final StructMember structMember) {
        return indent(3) + "xmlNodePtr loop_node;\n"
             + indent(3) + "GPtrArray* " + structMember.getName() + "_array = g_ptr_array_new();\n"
             + indent(3) + "int num_nodes = 0;\n"
             + indent(3) + "for (loop_node = child_node->xmlChildrenNode; loop_node != NULL; loop_node = loop_node->next, num_nodes++) {\n"
             + indent(4) + structMember.getType().getTypeName() + "* " + structMember.getName() + " = xml_get_string(doc, loop_node);\n"
             + indent(4) + "g_ptr_array_add(" + structMember.getName() + "_array, " + structMember.getName() + ");\n"
             + indent(3) + "}\n"
             + indent(3) + "response->" + structMember.getName() + " = (" + structMember.getType().getTypeName() + "**)" + structMember.getName() +"_array->pdata;\n"
             + indent(3) + "response->num_" + structMember.getName() + " = " + structMember.getName() +"_array->len;\n"
             + indent(3) + "g_ptr_array_free(" + structMember.getName() + "_array, FALSE);\n";
    }

    public static String generateStructMemberArrayParserBlock(final StructMember structMember) throws ParseException {
        return indent(3) + "GPtrArray* " + structMember.getName() + "_array;\n"
             + indent(3) + "error = _parse_" + structMember.getType().getTypeName() + "_array(client, doc, child_node, &" + structMember.getName() + "_array);\n"
             + indent(3) + "response->" + structMember.getName() + " = (" + structMember.getType().getTypeName() + "**)" + structMember.getName() + "_array->pdata;\n"
             + indent(3) + "response->num_" + structMember.getName() + " = " + structMember.getName() + "_array->len;\n"
             + indent(3) + "g_ptr_array_free(" + structMember.getName() + "_array, FALSE);\n";
    }

    public static String generateUnwrappedStructMemberArrayParserBlock(final StructMember structMember) throws ParseException {
        return indent(3) + structMember.getType().getTypeName() + "* " + structMember.getName() + "_response = NULL;\n"
             + indent(3) + "error = _parse_" + structMember.getType().getTypeName() + "(client, doc, child_node, &" + structMember.getName() + "_response);\n"
             + indent(3) + "response->" + structMember.getName() + " = (" + structMember.getType().getTypeName() + "**)" + structMember.getName() + "_array->pdata;\n"
             + indent(3) + "g_ptr_array_add(" + structMember.getName() + "_array, " + structMember.getName() + "_response);\n";
    }

    public static String generateStructMemberEnumParserBlock(final StructMember structMember) {
        return indent(3) + "xmlChar* text = xmlNodeListGetString(doc, child_node, 1);\n"
             + indent(3) + "if (text == NULL) {\n"
             + indent(4) + "continue;\n"
             + indent(3) + "}\n"
             + indent(3) + "response->" + structMember.getName() + " = _match_" + structMember.getType().getTypeName() + "(client->log, text);\n";
    }

    public static String generateStructMemberEnumAttributeParserBlock(final StructMember structMember) {
        return indent(3) + "xmlChar* text = xmlNodeListGetString(doc, attribute->children, 1);\n"
                + indent(3) + "if (text == NULL) {\n"
                + indent(4) + "continue;\n"
                + indent(3) + "}\n"
                + indent(3) + "response->" + structMember.getName() + " = _match_" + structMember.getType().getTypeName() + "(client->log, text);\n";
    }

    public static String generateStructMemberEnumArrayParserBlock(final StructMember structMember) {
        return indent(3) + "xmlNodePtr loop_node;\n"
             + indent(3) + "int num_nodes = 0;\n"
             + indent(3) + "GByteArray* enum_array = g_byte_array_new();\n"
             + indent(3) + structMember.getType().getTypeName() + " " + structMember.getName() + ";\n"
             + indent(3) + "for (loop_node = child_node->xmlChildrenNode; loop_node != NULL; loop_node = loop_node->next, num_nodes++) {\n"
             + indent(4) + "xmlChar* text = xmlNodeListGetString(doc, loop_node, 1);\n"
             + indent(4) + "if (text == NULL) {\n"
             + indent(5) + "continue;\n"
             + indent(4) + "}\n"
             + indent(4) + structMember.getName() + " = _match_" + structMember.getType().getTypeName() + "(client->log, text);\n"
             + indent(4) + "g_byte_array_append(enum_array, (const guint8*) &" + structMember.getName() + ", sizeof(" + structMember.getType().getTypeName() + "));\n"
             + indent(3) + "}\n"
             + indent(3) + "response->" + structMember.getName() + " = (" + structMember.getType().getTypeName() + "*)enum_array->data;\n"
             + indent(3) + "response->num_" + structMember.getName() + " = enum_array->len;\n"
             + indent(3) + "g_byte_array_free(enum_array, FALSE);\n";
    }

    public static String getParseStructMemberBlock(final StructMember structMember) throws ParseException {
        if (structMember.getType().isPrimitive()) {
            switch (structMember.getType().getTypeName()) {
                case "uint64_t":
                case "size_t":
                case "double":
                case "float":
                case "long":
                    return generateStructMemberParserLine(structMember, "xml_get_uint64(doc, child_node);");
                case "int":
                    return generateStructMemberParserLine(structMember, "xml_get_uint16(doc, child_node);");
                case "ds3_bool":
                    // TODO c_sdk inconsistent: xml_get_bool is the only func to log a parse error
                    return generateStructMemberParserLine(structMember, "xml_get_bool(client->log, doc, child_node);");
                default: // Enum
                    if (structMember.getType().isArray()) {
                        return generateStructMemberEnumArrayParserBlock(structMember);
                    }
                    return generateStructMemberEnumParserBlock(structMember);
            }
        } else if (structMember.getType().getTypeName().equals("ds3_str")) { // special case
            if (structMember.getType().isArray()) {
                return generateStructMemberDs3StrArrayBlock(structMember);
            }
            return generateStructMemberParserLine(structMember, "xml_get_string(doc, child_node);");
        } else if (structMember.getType().isArray()) {
            if (structMember.hasWrapper()) {
                return generateStructMemberArrayParserBlock(structMember);
            }
            return generateUnwrappedStructMemberArrayParserBlock(structMember);
        }

        return indent(3) + "error = " + StructHelper.getParserFunctionName(structMember.getType().getTypeName()) + "(client, doc, child_node, &response->" + structMember.getName() + ");\n";
    }

    public static String getParseStructMemberAttributeBlock(final StructMember structMember) throws ParseException {
        if (structMember.getType().isPrimitive()) {
            switch (structMember.getType().getTypeName()) {
                case "uint64_t":
                case "size_t":
                case "double":
                case "float":
                case "long":
                    return generateStructMemberParserLine(structMember, "xml_get_uint64_from_attribute(doc, attribute);");
                case "int":
                    return generateStructMemberParserLine(structMember, "xml_get_uint16_from_attribute(doc, attribute);");
                case "ds3_bool":
                    // TODO c_sdk inconsistent: xml_get_bool is the only func to log a parse error
                    return generateStructMemberParserLine(structMember, "xml_get_bool_from_attribute(client->log, doc, attribute);");
                default: // Enum
                    if (structMember.getType().isArray()) {
                        throw new IllegalArgumentException("Attribute " + structMember.getType().getTypeName() + " is an array");
                    }
                    return generateStructMemberEnumAttributeParserBlock(structMember);
            }
        } else if (structMember.getType().getTypeName().equals("ds3_str")) { // special case
            if (structMember.getType().isArray()) {
                throw new IllegalArgumentException("Attribute " + structMember.getType().getTypeName() + " is an array");
            }
            return generateStructMemberParserLine(structMember, "xml_get_string_from_attribute(doc, attribute);");
        } else if (structMember.getType().isArray()) {
            return generateStructMemberArrayParserBlock(structMember);
        }

        throw new IllegalArgumentException("Attribute " + structMember.getType().getTypeName() + " is a complex (DS3) type");
    }

    public static String generateFreeArrayStructMember(final StructMember structMember) {
        return indent(1) + "for (index = 0; index < response->num_" + structMember.getName() + "; index++) {\n"
             + indent(2) + structMember.getType().getTypeName() + "_free(response->" + structMember.getName() + "[index]);\n"
             + indent(1) + "}\n"
             + indent(1) + "g_free(response->" + structMember.getName() + ");\n";
    }

    public static String generateFreeStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember structMember : structMembers) {
            if (structMember.getType().isPrimitive()) { // PrimitiveTypes only need to free the base pointer
                if (structMember.getType().isArray()) {
                    outputBuilder.append(indent(1)).append("g_free(response->").append(structMember.getName()).append(");\n");
                }
                // else do nothing for single PrimitiveType
            } else if (structMember.getType().isArray()) { // FreeableType needs to free each element
                outputBuilder.append(generateFreeArrayStructMember(structMember));
            } else {
                outputBuilder.append(indent(1)).append(structMember.getType().getTypeName()).append("_free(response->").append(structMember.getName()).append(");\n");
            }
        }

        return outputBuilder.toString();
    }

    public static String generateStructMembers(final ImmutableList<StructMember> structMembers) throws ParseException {
        final StringBuilder outputBuilder = new StringBuilder();

        for (final StructMember member : structMembers) {
            outputBuilder.append(indent(1)).append(member.getType()).append(" ").append(member.getName()).append(";\n");
        }

        return outputBuilder.toString();
    }
}
