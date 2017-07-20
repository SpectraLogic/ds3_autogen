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

package com.spectralogic.ds3autogen.go.generators.parser

import com.google.common.collect.ImmutableList
import com.google.common.collect.ImmutableMap
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type
import com.spectralogic.ds3autogen.go.models.parser.*
import com.spectralogic.ds3autogen.go.utils.toGoType
import com.spectralogic.ds3autogen.utils.ConverterUtil
import com.spectralogic.ds3autogen.utils.Ds3ElementUtil
import com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors
import org.apache.commons.lang3.StringUtils

open class BaseTypeParserGenerator : TypeParserModelGenerator<TypeParser>, TypeParserGeneratorUtil {

    override fun generate(ds3Type: Ds3Type, typeMap: ImmutableMap<String, Ds3Type>): TypeParser {
        val modelName = NormalizingContractNamesUtil.removePath(ds3Type.name)
        val name = modelName + "Parser"
        val attributes = toAttributeList(ds3Type.elements, modelName)
        val childNodes = toChildNodeList(ds3Type.elements, modelName, typeMap)

        return TypeParser(name, modelName, attributes, childNodes)
    }

    /**
     * Converts all non-attribute elements within a Ds3Element list into ParsingElements, which
     * contain the Go code for parsing the Ds3Elements as child nodes.
     */
    fun toChildNodeList(
            ds3Elements: ImmutableList<Ds3Element>?,
            typeName: String,
            typeMap: ImmutableMap<String, Ds3Type>): ImmutableList<ParseElement> {

        if (ConverterUtil.isEmpty(ds3Elements)) {
            return ImmutableList.of()
        }

        return ds3Elements!!.stream()
                .filter { ds3Element -> !Ds3ElementUtil.isAttribute(ds3Element.ds3Annotations) }
                .map { ds3Element -> toChildNode(ds3Element, typeName, typeMap) }
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Converts a Ds3Element into a ParsingElement, which contains the Go code for parsing the
     * specified Ds3Element as a child node. This assumes that the specified Ds3Element is not an attribute.
     */
    fun toChildNode(ds3Element: Ds3Element, typeName: String, typeMap: ImmutableMap<String, Ds3Type>): ParseElement {
        val xmlTag = getXmlTagName(ds3Element)
        val modelName = StringUtils.uncapitalize(typeName)
        val paramName = StringUtils.capitalize(ds3Element.name)

        // Handle case if there is an encapsulating tag around a list of elements
        if (Ds3ElementUtil.hasWrapperAnnotations(ds3Element.ds3Annotations)) {
            val encapsulatingTag = StringUtils.capitalize(Ds3ElementUtil.getEncapsulatingTagAnnotations(ds3Element.ds3Annotations))
            val childType = NormalizingContractNamesUtil.removePath(ds3Element.componentType)
            return ParseChildNodeAsSlice(encapsulatingTag, xmlTag, modelName, paramName, childType)
        }

        // Handle case if there is a slice to be parsed (no encapsulating tag)
        if (ds3Element.type.equals("array")) {
            return ParseChildNodeAddToSlice(xmlTag, modelName, paramName, toGoType(ds3Element.componentType!!))
        }

        // Handle case if the element is an enum
        if (isElementEnum(ds3Element.type!!, typeMap)) {
            if (ds3Element.nullable) {
                return ParseChildNodeAsNullableEnum(xmlTag, modelName, paramName)
            }
            return ParseChildNodeAsEnum(xmlTag, modelName, paramName)
        }

        val goType = toGoType(ds3Element.type, ds3Element.componentType, ds3Element.nullable)
        val parserNamespace = getPrimitiveTypeParserNamespace(ds3Element.type!!, ds3Element.nullable)
        when (goType) {
            "bool", "*bool", "int", "*int", "int64", "*int64", "float64", "*float64"  ->
                return ParseChildNodeAsPrimitiveType(xmlTag, modelName, paramName, parserNamespace)
            "string", "*string" ->
                return ParseChildNodeAsString(xmlTag, modelName, paramName, parserNamespace)
            else -> {
                // All remaining elements represent Ds3Types
                return ParseChildNodeAsDs3Type(xmlTag, modelName, paramName)
            }
        }
    }

    /**
     * Determines if the specified type is an enum.
     */
    fun isElementEnum(elementType: String, typeMap: ImmutableMap<String, Ds3Type>): Boolean {
        if (ConverterUtil.isEmpty(typeMap)) {
            return false
        }
        val ds3Type = typeMap[elementType] ?: return false
        return ConverterUtil.isEnum(ds3Type)
    }

    /**
     * Converts all attributes within a Ds3Element list into ParsingElements which contain the
     * Go code for parsing the attributes.
     */
    fun toAttributeList(ds3Elements: ImmutableList<Ds3Element>?, typeName: String): ImmutableList<ParseElement> {
        if (ConverterUtil.isEmpty(ds3Elements)) {
            return ImmutableList.of()
        }
        return ds3Elements!!.stream()
                .filter { ds3Element -> Ds3ElementUtil.isAttribute(ds3Element.ds3Annotations) }
                .map { ds3Element -> toAttribute(ds3Element, typeName) }
                .collect(GuavaCollectors.immutableList())
    }

    /**
     * Converts a Ds3Element into a ParsingElement, which contains the Go code for parsing the
     * specified Ds3Element attribute. This assumes that the specified Ds3Element is an attribute.
     */
    fun toAttribute(ds3Element: Ds3Element, typeName: String): ParseElement {
        val xmlName = getXmlTagName(ds3Element)

        val goType = toGoType(ds3Element.type, ds3Element.componentType, ds3Element.nullable)
        val modelName = StringUtils.uncapitalize(typeName)
        val paramName = StringUtils.capitalize(ds3Element.name)

        when (goType) {
            "bool", "*bool", "int", "*int", "int64", "*int64", "float64", "*float64"  -> {
                val parserNamespace = getPrimitiveTypeParserNamespace(ds3Element.type!!, ds3Element.nullable)
                return ParseSimpleAttr(xmlName, modelName, paramName, parserNamespace)
            }
            "string" ->
                return ParseStringAttr(xmlName, modelName, paramName)
            "*string" ->
                return ParseNullableStringAttr(xmlName, modelName, paramName)
            else -> {
                if (ds3Element.nullable) {
                    return ParseNullableEnumAttr(xmlName, modelName, paramName)
                }
                return ParseEnumAttr(xmlName, modelName, paramName)
            }
        }
    }

    /**
     * Retrieves the xml tag name for the specified Ds3Element. The result is capitalized.
     */
    fun getXmlTagName(ds3Element: Ds3Element): String {
        return StringUtils.capitalize(Ds3ElementUtil.getXmlTagName(ds3Element))
    }

    /**
     * Gets the namespace of the required primitive type parser used to parse
     * the specified type. Assumes that the provided type is a Go primitive, or
     * a pointer to a Go primitive type.
     */
    fun getPrimitiveTypeParserNamespace(type: String, nullable: Boolean): String {
        val parserPrefix = StringUtils.capitalize(toGoType(type))
        if (nullable) {
            return "Nullable$parserPrefix"
        }
        return parserPrefix
    }
}