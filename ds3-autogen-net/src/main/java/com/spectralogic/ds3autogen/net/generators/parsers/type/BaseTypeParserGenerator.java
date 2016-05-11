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

package com.spectralogic.ds3autogen.net.generators.parsers.type;

import com.google.common.collect.ImmutableList;
import com.spectralogic.ds3autogen.api.models.Ds3Element;
import com.spectralogic.ds3autogen.api.models.Ds3Type;
import com.spectralogic.ds3autogen.net.generators.parsers.element.NullableElement;
import com.spectralogic.ds3autogen.net.model.typeparser.TypeParser;

import static com.spectralogic.ds3autogen.net.utils.NetNullableElementUtils.createNullableElement;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.getEncapsulatingTagAnnotations;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.getXmlTagName;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.isAttribute;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isObjectsType;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

/**
 * Generates TypeParser model which describes how to create the .net parser for
 * the specified type.
 */
public class BaseTypeParserGenerator implements TypeParserGenerator<TypeParser>, TypeParserGeneratorUtils {

    @Override
    public TypeParser generate(final Ds3Type ds3Type) {
        final String typeName = removePath(ds3Type.getName());
        final ImmutableList<String> parseElements = toParseElements(ds3Type.getElements(), isObjectsType(ds3Type));

        return new TypeParser(typeName, parseElements);
    }

    /**
     * Converts a list of Ds3Elements into their .net parsing code
     */
    @Override
    public ImmutableList<String> toParseElements(
            final ImmutableList<Ds3Element> ds3Elements,
            final boolean isObjectsType) {
        final ImmutableList<NullableElement> elements = toNullableElementsList(ds3Elements, isObjectsType);
        if (isEmpty(elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<String> builder = ImmutableList.builder();
        for (final NullableElement nullableElement : elements) {
            builder.add(nullableElement.printParseElement());
        }
        return builder.build();
    }

    /**
     * Converts a list of Ds3Elements into a list of Nullable Element
     */
    @Override
    public ImmutableList<NullableElement> toNullableElementsList(
            final ImmutableList<Ds3Element> ds3Elements,
            final boolean isObjectsType) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        final ImmutableList.Builder<NullableElement> builder = ImmutableList.builder();
        for (final Ds3Element ds3Element : ds3Elements) {
            builder.add(toNullableElement(ds3Element, isObjectsType));
        }
        return builder.build();
    }

    /**
     * Converts a Ds3Element into a Nullable Element
     */
    protected static NullableElement toNullableElement(
            final Ds3Element ds3Element,
            final boolean isObjectsType) {

        return createNullableElement(
                toNullableElementName(ds3Element.getName(), isObjectsType),
                ds3Element.getType(),
                ds3Element.getComponentType(),
                ds3Element.isNullable(),
                getXmlTagName(ds3Element),
                getEncapsulatingTagAnnotations(ds3Element.getDs3Annotations()),
                isAttribute(ds3Element.getDs3Annotations()));
    }

    /**
     * Converts an element's contract name into its sdk name
     */
    protected static String toNullableElementName(
            final String elementName,
            final boolean isObjectsType) {
        if (isObjectsType && elementName.equals("Objects")) {
            return elementName + "List";
        }
        return elementName;
    }
}
