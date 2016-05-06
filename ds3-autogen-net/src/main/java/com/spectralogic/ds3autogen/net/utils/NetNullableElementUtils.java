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

package com.spectralogic.ds3autogen.net.utils;

import com.spectralogic.ds3autogen.net.generators.elementparsers.*;

import static com.spectralogic.ds3autogen.net.NetHelper.toNetType;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.Helper.capFirst;
import static com.spectralogic.ds3autogen.utils.Helper.stripPath;

/**
 * Static utility functions that create Nullable Elements. These are used in
 * the .net package type model generators to create the parse element .net code
 */
public final class NetNullableElementUtils {

    /**
     * Creates a Nullable Element from the provided parameters
     */
    public static NullableElement createNullableElement(
            final String name,
            final String type,
            final String componentType,
            final boolean nullable,
            final String xmlTag,
            final String encapsulatingXmlTag,
            final boolean isAttribute) {
        if (hasContent(componentType)) {
            if (hasContent(encapsulatingXmlTag)) {
                return toNullableEncapsulatedListElement(name, componentType, xmlTag, encapsulatingXmlTag);
            }
            return toNullableListElement(name, componentType, xmlTag);
        }
        if (isAttribute) {
            return toNullableAttributeElement(name, type, nullable, xmlTag);
        }
        return toBaseNullableElement(name, type, nullable, xmlTag);
    }

    /**
     * Creates the xml parser name for a given model/type
     */
    protected static String getParserName(
            final String netType,
            final boolean nullable) {
        final StringBuilder builder = new StringBuilder();
        builder.append("Parse");
        if (nullable) {
            builder.append("Nullable");
        }
        if (netType.equals("ChecksumType.Type")) {
            return builder.append("ChecksumType").toString();
        }
        if (netType.equals("Object")) {
            return builder.append("String").toString();
        }
        builder.append(capFirst(netType));
        return builder.toString();
    }

    /**
     * Creates a Nullable Attribute Element
     */
    public static NullableElement toNullableAttributeElement(
            final String name,
            final String type,
            final boolean nullable,
            final String xmlTag) {
        final String netType = toNetType(stripPath(type));
        final String parserName = getParserName(netType, nullable);
        final String parseAttributeFuncName = toParseAttributeFuncName(nullable);
        return new NullableAttributeElement(
                name,
                xmlTag,
                parserName,
                parseAttributeFuncName);
    }

    /**
     * Gets the name of the parse attribute function
     */
    public static String toParseAttributeFuncName(final boolean nullable) {
        if (nullable) {
            return "AttributeTextOrNull";
        }
        return "AttributeText";
    }


    /**
     * Creates a Nullable Encapsulated List element
     */
    public static NullableElement toNullableEncapsulatedListElement(
            final String name,
            final String componentType,
            final String xmlTag,
            final String encapsulatingXmlTag) {
        final String netType = toNetType(stripPath(componentType));
        final String parserName = getParserName(netType, false);
        return new NullableEncapsulatedListElement(name, xmlTag, parserName, encapsulatingXmlTag);
    }

    /**
     * Creates a Base Nullable Element
     */
    public static NullableElement toBaseNullableElement(
            final String name,
            final String type,
            final boolean nullable,
            final String xmlTag) {
        final String netType = toNetType(stripPath(type));
        final String parserName = getParserName(netType, nullable);
        return new BaseNullableElement(
                name,
                xmlTag,
                parserName);
    }

    /**
     * Creates a Nullable Element that represents a list
     */
    public static NullableElement toNullableListElement(
            final String name,
            final String componentType,
            final String xmlTag) {
        final String netType = toNetType(stripPath(componentType));
        final String parserName = getParserName(netType, false);
        return new NullableListElement(name, xmlTag, parserName);
    }
}
