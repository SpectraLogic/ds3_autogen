package com.spectralogic.ds3autogen.c.models;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Annotation;
import com.spectralogic.ds3autogen.utils.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;


public class Element {
    private static final Logger LOG = LoggerFactory.getLogger(Element.class);

    private final String name;
    private final String type;
    private final String componentType;
    private final ImmutableList<Ds3Annotation> annotations;

    public Element(
            final String name,
            final String type,
            final String componentType,
            final ImmutableList<Ds3Annotation> annotations) {
        this.name = name;
        this.type = type;
        this.componentType = componentType;
        this.annotations = annotations;
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public String getComponentType() {
        return this.componentType;
    }

    public String getUnqualifiedName() {
        return Helper.unqualifiedName(name);
    }

    public String getNameUnderscores() {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(getUnqualifiedName()));
    }

    public String getParserName() {
        return "_parse_" + getNameUnderscores() + "_response";
    }

    public String getDs3TypeName() {
        return "ds3_" + getNameUnderscores();
    }

    public String getResponseTypeName() {
        return getDs3TypeName() + "_response";
    }

    public String getFreeFunctionName() {
        return getResponseTypeName() + "_free";
    }

    public ImmutableList<Ds3Annotation> getAnnotations() {
        return this.annotations;
    }

    public String getDs3Type() throws ParseException {
        switch (getType()) {
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
                return getResponseTypeName() + "**";

            default:
                return getResponseTypeName() + "*";
        }
    }

    public String getParser() throws ParseException {
        switch (getType()) {
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return "xml_get_string(doc, child_node);";
            case "double":
            case "java.lang.Long":
            case "long":
                return "xml_get_uint64(doc, child_node);";
            case "java.lang.Integer":
            case "int":
                return "xml_get_uint16(doc, child_node);";
            case "boolean":
                return "xml_get_bool(doc, child_node);";

            case "java.util.Set":
            case "array":
                //throw new ParseException("Unknown element type" + getType(), 0);
                return "Skipping Array / Set Element";

            default:
                return getParserName() + "(log, doc, child_node);";
        }
    }

    public boolean isPrimitiveType() {
        switch (getType()) {
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
            case "double":
            case "java.lang.Long":
            case "long":
            case "java.lang.Integer":
            case "int":
            case "boolean":
                return true;
            case "java.util.Set":
            case "array":
            default:
                // any complex sub element such as "com.spectralogic.s3.server.domain.UserApiBean"
                return false;
        }
    }
}
