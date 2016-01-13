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

package com.spectralogic.ds3autogen.c.helpers;

import com.spectralogic.ds3autogen.c.models.Element;
import com.spectralogic.ds3autogen.utils.Helper;

import java.text.ParseException;

public final class ElementHelper {
    private ElementHelper() {}

    private final static ElementHelper elementHelper = new ElementHelper();

    public static ElementHelper getInstance() {
        return elementHelper;
    }

    public static String getFreeFunction(final Element element) throws ParseException {
        switch (element.getType()) {
            case "java.lang.String":
            case "java.util.Date":
            case "java.util.UUID":
                return "ds3_str_free";
            // The following primitive types don't require a free
            case "double":
                return "";
            case "java.lang.Long":
            case "long":
                return "";
            case "java.lang.Integer":
            case "int":
                return "";
            case "boolean":
                return "";

            // build the name of the free function for the embedded type
            case "java.util.Set":
            case "array":
            default:
                return getFreeFunctionName(element.getType());
        }
    }

    public static String getParser(final Element element) throws ParseException {
        switch (element.getType()) {
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
                return getParserName(element.getName()) + "(log, doc, child_node);";
        }
    }

    public static String getNameUnderscores(final String name) {
        return Helper.camelToUnderscore(Helper.removeTrailingRequestHandler(Helper.unqualifiedName(name)));
    }

    public static String getParserName(final String name) {
        return "_parse_" + getNameUnderscores(name) + "_response";
    }

    public static String getDs3TypeName(final String name) {
        return "ds3_" + getNameUnderscores(name);
    }

    public static String getResponseTypeName(final String name) {
        return getDs3TypeName(name) + "_response";
    }

    public static String getFreeFunctionName(final String name) {
        return getResponseTypeName(name) + "_free";
    }

    public static String getDs3Type(final Element element) throws ParseException {
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
                return getResponseTypeName(element.getName()) + "**";

            default:
                return getResponseTypeName(element.getName()) + "*";
        }
    }
}
