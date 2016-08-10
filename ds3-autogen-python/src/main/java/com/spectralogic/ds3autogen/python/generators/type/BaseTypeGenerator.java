/*
 * ******************************************************************************
 *   Copyright 2014-2016 Spectra Logic Corporation. All Rights Reserved.
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

package com.spectralogic.ds3autogen.python.generators.type;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Element;
import com.spectralogic.ds3autogen.api.models.apispec.Ds3Type;
import com.spectralogic.ds3autogen.python.model.type.TypeAttribute;
import com.spectralogic.ds3autogen.python.model.type.TypeDescriptor;
import com.spectralogic.ds3autogen.python.model.type.TypeElement;
import com.spectralogic.ds3autogen.python.model.type.TypeElementList;
import com.spectralogic.ds3autogen.utils.collections.GuavaCollectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.spectralogic.ds3autogen.python.utils.GeneratorUtils.getParserModelName;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.hasContent;
import static com.spectralogic.ds3autogen.utils.ConverterUtil.isEmpty;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.getEncapsulatingTagAnnotations;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.getXmlTagName;
import static com.spectralogic.ds3autogen.utils.Ds3ElementUtil.isAttribute;
import static com.spectralogic.ds3autogen.utils.Ds3TypeClassificationUtil.isEnumType;
import static com.spectralogic.ds3autogen.utils.NormalizingContractNamesUtil.removePath;

public class BaseTypeGenerator implements TypeModelGenerator<TypeDescriptor> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseTypeGenerator.class);

    @Override
    public TypeDescriptor generate(
            final Ds3Type ds3Type,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final String name = removePath(ds3Type.getName());
        final ImmutableList<String> attributes = toAttributes(ds3Type.getElements());
        final ImmutableList<String> elements = toElements(ds3Type.getElements(), typeMap);
        final ImmutableList<String> elementLists = toElementLists(ds3Type.getElements(), typeMap);

        return new TypeDescriptor(
                name,
                attributes,
                elements,
                elementLists);
    }

    /**
     * Converts all Ds3Elements that describe an attribute into their python code representation.
     * All other Ds3Elements are removed.
     */
    protected static ImmutableList<String> toAttributes(final ImmutableList<Ds3Element> ds3Elements) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        return ds3Elements.stream()
                .filter(ds3Element -> isAttribute(ds3Element.getDs3Annotations()))
                .map(attr -> toAttribute(attr).toPythonCode())
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3Element into a TypeAttribute
     */
    protected static TypeAttribute toAttribute(final Ds3Element ds3Element) {
        final String name = getXmlTagName(ds3Element);
        return new TypeAttribute(name);
    }

    /**
     * Converts all Ds3Elements that describe a single xml element into its python code representation.
     * All other Ds3Elements are removed.
     */
    protected static ImmutableList<String> toElements(
            final ImmutableList<Ds3Element> ds3Elements,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        return ds3Elements.stream()
                .filter(ds3Element -> isEmpty(ds3Element.getComponentType()) && !isAttribute(ds3Element.getDs3Annotations()))
                .map(element -> toElement(element, typeMap).toPythonCode())
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3Element into a TypeElement
     */
    protected static TypeElement toElement(final Ds3Element ds3Element, final ImmutableMap<String, Ds3Type> typeMap) {
        final String xmlTag = getXmlTagName(ds3Element);
        final String typeModel = getTypeModelName(ds3Element.getType(), ds3Element.getComponentType(), typeMap);
        return new TypeElement(xmlTag, typeModel);
    }

    /**
     * Gets the type model name that describes the specified type, or 'None' if there
     * is no associated type model
     */
    protected static String getTypeModelName(
            final String type,
            final String componentType,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(typeMap)) {
            LOG.debug("Cannot find model type because TypeMap is empty");
            return "None";
        }
        if (hasContent(componentType) && typeMap.containsKey(componentType) && !isEnumType(componentType, typeMap)) {
            return getParserModelName(componentType);
        } else if(isEmpty(componentType) && typeMap.containsKey(type) && !isEnumType(type, typeMap)) {
            return getParserModelName(type);
        }
        return "None";
    }

    /**
     * Converts all Ds3Elements that describe a list of xml elements into their python code representation.
     * All other Ds3Elements are removed.
     */
    protected static ImmutableList<String> toElementLists(
            final ImmutableList<Ds3Element> ds3Elements,
            final ImmutableMap<String, Ds3Type> typeMap) {
        if (isEmpty(ds3Elements)) {
            return ImmutableList.of();
        }
        return ds3Elements.stream()
                .filter(ds3Element -> hasContent(ds3Element.getComponentType()) && !isAttribute(ds3Element.getDs3Annotations()))
                .map(element -> toElementList(element, typeMap).toPythonCode())
                .collect(GuavaCollectors.immutableList());
    }

    /**
     * Converts a Ds3Element into a TypeElementList
     */
    protected static TypeElementList toElementList(
            final Ds3Element ds3Element,
            final ImmutableMap<String, Ds3Type> typeMap) {
        final String xmlTag = getXmlTagName(ds3Element);
        final String encapsulatingTag = getEncapsulatingTagAnnotations(ds3Element.getDs3Annotations());
        final String typeModel = getTypeModelName(ds3Element.getType(), ds3Element.getComponentType(), typeMap);

        if(hasContent(encapsulatingTag)) {
            if (encapsulatingTag.equals("CommonPrefixes")) {
                return new TypeElementList(encapsulatingTag, "None", encapsulatingTag);
            }
            if (encapsulatingTag.equals("Jobs")) {
                return new TypeElementList(xmlTag, "None", typeModel);
            }
            return new TypeElementList(xmlTag, encapsulatingTag, typeModel);
        }
        return new TypeElementList(xmlTag, "None", typeModel);
    }
}
